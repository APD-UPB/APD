#!/bin/bash

folder="sol"
tout=5
topo_tests=0
comp_tests=0
invalid_comm=0
bonus=0

# afiseaza scorul final
function show_score {
	local invalid=0
	local total=0

	if [ $invalid_comm == 1 ]
	then
		invalid=100
		total=0
	else
		invalid=0
		total=$((topo_tests+comp_tests+bonus))
	fi

	echo ""
	echo "Scor topologie: $topo_tests/40"
	echo "Scor corectitudine: $comp_tests/40"
	echo "Depunctare mesaje: $invalid/100"
	echo "Bonus: $bonus/20"
	echo "TOTAL: $total/80"
}

# se ruleaza un test (parametru: comanda)
function run_test {
	timeout $tout $1 &> out.txt
	ret=$?

	if [ $ret == 124 ]
	then
		echo "W: Programul a durat prea mult"
	elif [ $ret != 0 ]
	then
		echo "W: Rularea nu s-a putut executa cu succes"
		cat out.txt
	fi
}

# se verifica topologia (parametru: numarul de procese)
function check_topology {
	local topology=`cat topology.txt`
	local procs=$(($1-1))
	local ok=0

	for i in `seq 0 $procs`
	do
		cat out.txt | grep -q -i "$i -> $topology"
		ok=$((ok+$?))
	done

	if [ $ok == 0 ]
	then
		topo_tests=$((topo_tests+10))
	else
		echo "E: Topologia nu este afisata corect de toate procesele"
	fi
}

# se verifica daca rezultatul calculelor este corect
function check_computation {
	local output=`cat output.txt`
	local ok=0

	cat out.txt | grep -q -i "$output"
	
	if [ $? == 0 ]
	then
		comp_tests=$((comp_tests+10))
	else
		echo "E: Rezultatul final nu este corect"
		echo "Se astepta:"
		echo $output
		echo "S-a gasit:"
		cat out.txt | grep "Rezultat"
	fi
}

# se verifica daca sunt mesaje incorecte (parametru out: rezultate invalide)
function check_messages {
	local __resultvar=$1
	local messages=`cat out.txt | grep "M("`
	local count=0
	local good=0

	for m in $messages
	do
		count=$((count+1))

		if grep -Fxq "$m" allowed.txt
		then
		    good=$((good+1))
		else
		    echo "E: S-a detectat comunicatie intre doua procese neconectate: $m"
		fi
	done

	if [ $count == $good ]
	then
		eval $__resultvar=1
	else
		eval $__resultvar=0
	fi
}

# se verifica daca bonusul este implementat corect
function check_bonus {
	local procs=`sed '1q;d' inputs.txt`
	local N=`sed '2q;d' inputs.txt`
	local topology=`cat topology.txt`
	local output=`cat output.txt`
	local ok=0
	local count=0
	local good=0

	run_test "mpirun -np $procs ./tema3 $N 1"

	# verificare topologie
	procs=$(($procs-1))

	for i in `seq 0 $procs`
	do
		cat out.txt | grep -q -i "$i -> $topology"
		ok=$((ok+$?))
	done

	if [ $ok != 0 ]
	then
		echo "E: Topologia nu este afisata corect de toate procesele"
		return
	fi

	# verificare rezultat calcul
	ok=0
	cat out.txt | grep -q -i "$output"

	if [ $? != 0 ]
	then
		echo "E: Rezultatul final nu este corect"
		echo "Se astepta:"
		echo $output
		echo "S-a gasit:"
		cat out.txt | grep "Rezultat"
		return
	fi

	#verificare mesaje intre procese
	local messages=`cat out.txt | grep "M("`

	for m in $messages
	do
		count=$((count+1))

		if grep -Fxq "$m" allowed.txt
		then
		    good=$((good+1))
		else
		    echo "E: S-a detectat comunicatie intre doua procese neconectate: $m"
		    return
		fi
	done

	bonus=20
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

cd $folder
rm -rf tema3

# se compileaza tema
make clean
make build

if [ ! -f tema3 ]
then
    echo "E: Nu s-a putut compila tema"
    show_score
    exit
fi

# se ruleaza 4 teste
for i in `seq 1 4`
do
	echo ""
	echo "Se ruleaza testul $i..."

	cp ../tests/test$i/*.txt .

	procs=`sed '1q;d' inputs.txt`
	N=`sed '2q;d' inputs.txt`

	run_test "mpirun -np $procs ./tema3 $N 0"

	# verificare topologie
	check_topology $procs

	# verificare rezultat calcul
	check_computation

	#verificare mesaje intre procese
	check_messages invalid
	if [ $invalid == 0 ]
	then
		invalid_comm=1
	fi

	rm -rf *.txt
done

# se ruleaza testul bonus
echo ""
echo "Se ruleaza testul de bonus..."
cp ../tests/testbonus/*.txt .
check_bonus
rm -rf *.txt

make clean

cd ..

show_score
