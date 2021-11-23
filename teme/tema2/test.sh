#!/bin/bash

correct=0
total=0
tout=3
folder="skel"

# afiseaza scorul final
function show_score {
	if [ $total == 0 ]
	then
		correctness=0
	else
		correctness=$((correct * 30 / total))
	fi

	echo ""
	echo "TOTAL: $correctness/30"
}

# se ruleaza un test (parametru: comanda)
function run_test {
	timeout $tout $1
	ret=$?

	if [ $ret == 124 ]
	then
		echo "W: Programul a durat prea mult"
	elif [ $ret != 0 ]
	then
		echo "W: Rularea nu s-a putut executa cu succes"
	fi

	total=$((total+1))
}

# se compara doua fisiere (parametri: fisier1 fisier2)
function compare_outputs {
	diff -wq $1 $2 &> /dev/null
	if [ $? == 0 ]
	then
		correct=$((correct+1))
	else
		echo "W: Exista diferente intre fisierele $1 si $2"
		echo "Se astepta:"
		cat $1
		echo "S-a gasit:"
		cat $2
	fi
}

function cleanup {
	rm -rf *.txt
	rm -rf *.class
	rm -rf tests
	cd ..
}

# printeaza informatii despre rulare
echo "Timp de start: $(date)"
echo "V1.0"

if [ ! -d $folder ]
then
    echo "E: Nu exista directorul cu sursele"
    show_score
    exit
fi

cp -R tests $folder/

cd $folder
rm -rf *.class

javac *.java

if [ ! -f Tema2.class ]
then
    echo "E: Nu s-a putut compila tema"
    show_score
    cleanup
    exit
fi

rm -rf *.txt

# se ruleaza cele 5 teste cu numar diferiti de workeri
for test in 0 1 2 3 4
do
	for workers in 1 2 3 4
	do
		echo "Se ruleaza testul $test cu $workers worker(i)"
		run_test "java Tema2 $workers tests/in/test${test}.txt test${test}_out.txt"
		compare_outputs tests/out/test${test}_out.txt test${test}_out.txt
		rm -rf test${test}_out.txt
	done
done

cleanup

echo "Teste corecte: $correct/$total"
show_score
