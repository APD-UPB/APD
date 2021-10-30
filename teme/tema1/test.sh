#!/bin/bash

correct=0
total=0
scalability=0
correctness=0
correct_scalability=2
times=()
seq_times=()
par_times=()
seq_runs=0

# afiseaza scorul final
function show_score {
	echo ""
	echo "Scor scalabilitate: $scalability/50"
	echo "Scor corectitudine: $correctness/30"
	echo "Scor total:         $((correctness + scalability))/80"
}

# se compara doua fisiere (parametri: fisier1 fisier2 test_scalabitate)
function compare_outputs {
	diff -q $1 $2 &> /dev/null
	if [ $? == 0 ]
	then
		correct=$((correct+1))
	else
		echo "W: Exista diferente intre fisierele $1 si $2"

		if [ $3 == 1 ]
		then
			correct_scalability=$((correct_scalability-1))
		fi
	fi
}

# se ruleaza o comanda si se masoara timpul (parametru: comanda)
function run_and_get_time_seq {
	{ time -p sh -c "$1"; } &> time.txt
	ret=$?

	if [ $ret != 0 ]
	then
		echo "E: Rularea nu s-a putut executa cu succes"
		cat time.txt | sed '$d' | sed '$d' | sed '$d'
		show_score
		exit
	fi

	res=`cat time.txt | grep real | awk '{print $2}'`
	seq_times+=(${res})
	times+=(${res%.*})

	rm -rf time.txt

	seq_runs=$((seq_runs+1))
}

# se ruleaza o comanda paralela (parametri: timeout comanda)
function run_par {
	{ time -p sh -c "timeout $1 $2" ; } &> time.txt
	ret=$?

	if [ $ret == 124 ]
	then
		echo "W: Programul a durat cu cel putin 2 secunde in plus fata de implementarea secventiala"
	elif [ $ret != 0 ]
	then
		echo "W: Rularea nu s-a putut executa cu succes"
		cat time.txt | sed '$d' | sed '$d' | sed '$d'
	fi

	total=$((total+1))
	rm -rf time.txt
}

# se ruleaza si masoara o comanda paralela (parametri: timeout comanda)
function run_par_and_measure {
	{ time -p sh -c "timeout $1 $2" ; } &> time.txt
	ret=$?

	if [ $ret == 124 ]
	then
		echo "W: Programul a durat cu cel putin 2 secunde in plus fata de implementarea secventiala"
	elif [ $ret != 0 ]
	then
		echo "W: Rularea nu s-a putut executa cu succes"
		cat time.txt | sed '$d' | sed '$d' | sed '$d'
	fi

	total=$((total+1))
	par_times+=(`cat time.txt | grep real | awk '{print $2}'`)
	rm -rf time.txt
}

# script-ul se asteapta sa existe un folder "skel" cu scheletul nemodificat
# si un folder "sol" cu implementarea paralela

if [ ! -d skel ]
then
    echo "E: Nu exista directorul cu implementarea secventiala"
    show_score
    exit
fi

if [ ! -d sol ]
then
    echo "E: Nu exista directorul cu implementarea paralela"
    show_score
    exit
fi

# se compileaza cele doua implementari (cea paralela si cea secventiala)
cd skel
make clean &> /dev/null
make build &> /dev/null

if [ ! -f tema1 ]
then
    echo "E: Nu s-a putut compila implementarea secventiala"
    show_score
    exit
fi

cd ..

cd sol
make clean &> /dev/null
make build &> /dev/null

if [ ! -f tema1_par ]
then
    echo "E: Nu s-a putut compila implementarea paralela"
    show_score
    exit
fi

cd ..

# se ruleaza cele 4 teste pe varianta secventiala
echo "Se ruleaza implementarea secventiala..."
run_and_get_time_seq "./skel/tema1 ./skel/inputs/in1 10 > test1_sec"
run_and_get_time_seq "./skel/tema1 ./skel/inputs/in2 5 > test2_sec"
run_and_get_time_seq "./skel/tema1 ./skel/inputs/in3 5 > test3_sec"
run_and_get_time_seq "./skel/tema1 ./skel/inputs/in4 5 > test4_sec"
echo "OK"
echo ""

# se cresc valorile de timeout cu cate 2 secunde pentru fiecare test
for (( i=0; i<$seq_runs; i++ ))
do
   times[$i]=$((times[$i]+2))
done

# se ruleaza implementarea paralela pe diferite combinatii de numar de thread-uri
for P in 2 3 4
do
	echo "Se ruleaza testele pentru P=$P..."

	# pentru primul test, se masoara timpii ptr P=2 si P=4 (pentru calculul acceleratiei)
	if [ "$P" == "2" ] || [ "$P" == "4" ]
	then
		run_par_and_measure ${times[0]} "./sol/tema1_par ./skel/inputs/in1 10 $P > test1_par"
	else
		run_par ${times[0]} "./sol/tema1_par ./skel/inputs/in1 10 $P > test1_par"
	fi

	run_par ${times[1]} "./sol/tema1_par ./skel/inputs/in2 5 $P > test2_par"
	run_par ${times[2]} "./sol/tema1_par ./skel/inputs/in3 5 $P > test3_par"
	run_par ${times[3]} "./sol/tema1_par ./skel/inputs/in4 5 $P > test4_par"

	# pentru primul test, se tine minte daca rezultatul e corect pentru P=2 si P=4 (pentru punctajul de scalabilitate)
	if [ "$P" == "2" ] || [ "$P" == "4" ]
	then
		compare_outputs test1_sec test1_par 1
	else
		compare_outputs test1_sec test1_par 0
	fi

	compare_outputs test2_sec test2_par 0
	compare_outputs test3_sec test3_par 0
	compare_outputs test4_sec test4_par 0

	rm -rf test*_par

	echo "OK"
	echo ""
done

echo "Teste corecte: $correct/$total"

# se calculeaza acceleratia (este folosit primul test)
speedup12=$(echo "${seq_times[0]}/${par_times[0]}" | bc -l)
speedup14=$(echo "${seq_times[0]}/${par_times[1]}" | bc -l)
speedup24=$(echo "${par_times[0]}/${par_times[1]}" | bc -l)

# acceleratia se considera 0 daca testele de scalabilitate nu sunt corecte
if [ $correct_scalability != 2 ]
then
	speedup12=0
	speedup14=0
	speedup24=0
	echo "Testele de scalabilitate nu sunt corecte, acceleratia se considera 0"
fi

printf "Acceleratie 1-2: %0.2f\n" $speedup12
printf "Acceleratie 1-4: %0.2f\n" $speedup14
printf "Acceleratie 2-4: %0.2f\n" $speedup24

# se verifica acceleratia de la secvential la 2 thread-uri
max=$(echo "${speedup12} > 1.3" | bc -l)
part=$(echo "${speedup12} > 1.1" | bc -l)
if [ $max == 1 ]
then
	scalability=$((scalability+20))
elif [ $part == 1 ]
then
	scalability=$((scalability+10))
	echo "W: Acceleratia de la 1 la 2 thread-uri este prea mica (punctaj partial)"
else
    echo "W: Acceleratia de la 1 la 2 thread-uri este prea mica (fara punctaj)"
fi

# se verifica acceleratia de la secvential la 4 thread-uri
max=$(echo "${speedup14} > 1.8" | bc -l)
part=$(echo "${speedup14} > 1.5" | bc -l)
if [ $max == 1 ]
then
	scalability=$((scalability+20))
elif [ $part == 1 ]
then
	scalability=$((scalability+10))
	echo "W: Acceleratia de la 1 la 4 thread-uri este prea mica (punctaj partial)"
else
    echo "W: Acceleratia de la 1 la 4 thread-uri este prea mica (fara punctaj)"
fi

# se verifica acceleratia de la 2 la 4 thread-uri
max=$(echo "${speedup24} > 1.1" | bc -l)
if [ $max == 1 ]
then
	scalability=$((scalability+10))
else
    echo "W: Acceleratia de la 2 la 4 thread-uri este prea mica (fara punctaj)"
fi

rm -rf test*_sec

cd skel
make clean &> /dev/null
cd ../sol
make clean &> /dev/null
cd ..

if [ $scalability != 0 ]
then
	correctness=$((correct * 30 / total))
fi

show_score
