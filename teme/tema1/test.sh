#!/bin/bash

correct=0
total=0
scalability=0
correctness=0
correct_scalability=0
times=()
seq_times=()
par_times=()
seq_runs=0

# afiseaza scorul final
function show_score {
	echo ""
	echo "Scor scalabilitate: $scalability/70"
	echo "Scor corectitudine: $correctness/50"
	echo "Scor total:       $((correctness + scalability))/120"
}

# se compara output-urile din doua directoare (parametri: director1 director2 exponent)
function compare_outputs {
	ok=0

	for E in `seq 2 $3`
	do
		diff -q $1/out$E.txt $2/out$E.txt
		if [ $? == 0 ]
		then
			ok=$((ok+1))
		fi
	done

	count=$3
	count=$((count-1))

	if [ $ok == $count ]
	then
		correct=$((correct+1))
	else
		echo "W: Exista diferente intre output-urile din directorul $1 si cele din directorul $2"
		correct_scalability=$((correct_scalability-1))
	fi
}

# se ruleaza o comanda  (parametru: comanda)
function run {
	{ time -p sh -c "timeout 120 $1"; } &> time.txt
	ret=$?

	if [ $ret == 124 ]
	then
		echo "W: Programul a durat mai mult de 120 s"
	elif [ $ret != 0 ]
	then
		echo "W: Rularea nu s-a putut executa cu succes"
		cat time.txt | sed '$d' | sed '$d' | sed '$d'
	fi

	res=`cat time.txt | grep real | awk '{print $2}'`

	total=$((total+1))
	rm -rf time.txt

	seq_runs=$((seq_runs+1))
}

# se ruleaza o comanda si se masoara timpul (parametru: comanda)
function run_and_get_time {
	{ time -p sh -c "timeout 120 $1"; } &> time.txt
	ret=$?

	if [ $ret == 124 ]
	then
		echo "E: Programul a durat mai mult de 120 s"
		cat time.txt | sed '$d' | sed '$d' | sed '$d'
		show_score
		rm -rf test*_sec
		exit
	elif [ $ret != 0 ]
	then
		echo "E: Rularea nu s-a putut executa cu succes"
		cat time.txt | sed '$d' | sed '$d' | sed '$d'
		show_score
		rm -rf test*_sec
		exit
	fi

	res=`cat time.txt | grep real | awk '{print $2}'`
	seq_times+=(${res})
	times+=(${res%.*})

	total=$((total+1))
	rm -rf time.txt

	seq_runs=$((seq_runs+1))
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

# se compileaza tema
make clean &> /dev/null
make build &> build.txt

if [ ! -f tema1 ]
then
	echo "E: Nu s-a putut compila tema"
	cat build.txt
	show_score
	rm -rf build.txt
	exit
fi

rm -rf build.txt

for i in `seq 0 4`
do
	echo ""
	echo "======== Testul ${i} ========"
	echo ""
	echo "Se ruleaza varianta etalon cu M=1 si R=4..."

	# se creeaza directorul de output secvential
	mkdir -p test${i}_sec

	run_and_get_time "./tema1 1 4 ./test${i}/test.txt > test${i}_sec/out.txt"
	mv out*.txt test${i}_sec/ 2>/dev/null

	echo "Rularea a durat ${seq_times[$i]} secunde"

	correct_scalability=3

	# se verifica daca rezultatele sunt corecte
	compare_outputs test${i}_sec test${i} 5

	rm -rf test${i}_sec

	echo "OK"
	echo ""

	# se creste valoarea de timeout cu 2 secunde
	times[$i]=$((times[$i]+2))

	# se creeaza directorul de output paralel
	mkdir -p test${i}_par

	# se ruleaza implementarea paralela pe 2 si 4 thread-uri
	for P in 2 4
	do
		echo "Se ruleaza varianta cu M=$P si R=4..."

		# se masoara timpii paraleli (pentru calculul acceleratiei)
		run_par_and_measure ${times[$i]} "./tema1 $P 4 ./test${i}/test.txt > test${i}_par/out.txt"
		mv out*.txt test${i}_par/ 2>/dev/null

		if [ $P == 2 ]
		then
			echo "Rularea a durat ${par_times[$i * 2]} secunde"
		else
			echo "Rularea a durat ${par_times[$i * 2 + 1]} secunde"
		fi

		# se tine minte daca rezultatul e corect (pentru punctajul de scalabilitate)
		compare_outputs test${i}_par test${i} 5

		echo "OK"
		echo ""
	done

	rm -rf test${i}_par

	if [ $i != 0 ] && [ $i != 4 ]
	then
		# se calculeaza acceleratia
		speedup12=$(echo "${seq_times[$i]}/${par_times[$i * 2]}" | bc -l | xargs printf "%.2f")
		speedup14=$(echo "${seq_times[$i]}/${par_times[$i * 2 + 1]}" | bc -l | xargs printf "%.2f")

		# acceleratia se considera 0 daca testele de scalabilitate nu sunt corecte
		if [ $correct_scalability != 3 ]
		then
			speedup12=0
			speedup14=0
			echo "Testele nu dau rezultate corecte, acceleratia se considera 0"
		fi

		printf "Acceleratie 1-2 Mappers: %0.2f\n" $speedup12
		printf "Acceleratie 1-4 Mappers: %0.2f\n" $speedup14

		###
		if [ $i != 1 ]
		then
			# se verifica acceleratia de la secvential la 2 thread-uri
			max=$(echo "${speedup12} > 1.4" | bc -l)
			part=$(echo "${speedup12} > 1.1" | bc -l)
			if [ $max == 1 ]
			then
				scalability=$((scalability+14))
			elif [ $part == 1 ]
			then
				scalability=$((scalability+7))
				echo "W: Acceleratia de la 1 la 2 Mappers este prea mica (punctaj partial)"
			else
				echo "W: Acceleratia de la 1 la 2 Mappers este prea mica (fara punctaj)"
			fi
		fi

		# se verifica acceleratia de la secvential la 4 thread-uri
		max=$(echo "${speedup14} > 2.1" | bc -l)
		part=$(echo "${speedup14} > 1.8" | bc -l)
		if [ $max == 1 ]
		then
			scalability=$((scalability+14))
		elif [ $part == 1 ]
		then
			scalability=$((scalability+7))
			echo "W: Acceleratia de la 1 la 4 Mappers este prea mica (punctaj partial)"
		else
			echo "W: Acceleratia de la 1 la 4 Mappers este prea mica (fara punctaj)"
		fi
	fi

	echo "=========================="
	echo ""
done

correctness=$((correct * 50 / total))

make clean &> /dev/null

show_score
