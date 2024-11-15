#!/bin/bash

correct=0
total=0
scalability=0
correctness=0
correct_scalability=0
seq_runs=0
par_runs=0
full_speedups=(1.4 1.6 1.7)
int_speedups=(1.2 1.4 1.5)

# afiseaza scorul final
function show_score {
    echo ""
    echo "Scalabilitate: $scalability/48"
    echo "Corectitudine: $correctness/36"
    echo "Total:       $((correctness + scalability))/84"
}

# se compara output-urile din doua directoare (parametri: director1 director2)
function compare_outputs {
    ok=0

    for x in {a..z}
    do
        diff -w -q $1/$x.txt $2/$x.txt
        if [ $? == 0 ]
        then
            ok=$((ok+1))
        fi
    done

    if [ $ok == 26 ]
    then
        return 0
    else
        echo "W: Exista diferente intre output-urile din directorul $1 si cele din directorul $2"
        return 1 
    fi
}

# se ruleaza o comanda si se masoara timpul (parametru: comanda)
function run_and_get_time {
    seq_runs=$((seq_runs+1))

    { time -p sh -c "timeout 200 $1"; } &> time.txt
    ret=$?

    if [ $ret == 124 ]
    then
        echo "E: Programul a durat mai mult de 200 s"
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
    seq_time=${res}
    times=${res%.*}

    total=$((total+1))
    rm -rf time.txt
}

# se ruleaza si masoara o comanda paralela (parametri: timeout comanda)
function run_par_and_measure {
    par_runs=$((par_runs+1))

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
    par_time=`cat time.txt | grep real | awk '{print $2}'`
    rm -rf time.txt
}

# verifica daca testul curent este un test de speedup (parametri: mappers reducers)
function is_speedup_test {
    if [ $1 == 2 ] && [ $2 == 4 ]
    then
        return 1
    fi

    if [ $1 == 4 ] && [ $2 == 2 ]
    then
        return 1
    fi

    if [ $1 == 4 ] && [ $2 == 4 ]
    then
        return 1
    fi

    return 0
}

#echo "VMCHECKER_TRACE_CLEANUP"
date

# se compileaza tema
cd ../src
ls -lh
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

mv tema1 ../checker
cd ../checker

count=0

# se ruleaza implementarea paralela pe 2 si 4 thread-uri
for M in 1 2 4
do
    for R in 1 2 4
    do
        echo "Se ruleaza varianta cu M=$M si R=$R..."

        if [ $M == 1 ] && [ $R == 1 ]
        then
            # se creeaza directorul de output secvential
            mkdir -p test_sec

            run_and_get_time "./tema1 $M $R ./test.txt > test_sec/out.txt"
            for x in {a..z}
            do
                mv $x.txt test_sec 2>/dev/null
            done
            mv out*.txt test_sec/ 2>/dev/null

            echo "Rularea a durat ${seq_time} secunde"

            correct_scalability=3

            # se verifica daca rezultatele sunt corecte
            compare_outputs test_sec test_out

            if [[ $? == 0 ]]
            then
                correct=$((correct+1))
            else
                echo "Testul nu a dat rezultate corecte"
            fi

            rm -rf test_sec

            # se creste valoarea de timeout cu 2 secunde
            times=$((times+2))
        else
            is_speedup_test $M $R
            speedup_test=$?

            # se creeaza directorul de output paralel
            mkdir -p test_par

            # se masoara timpii paraleli (pentru calculul acceleratiei)
            run_par_and_measure ${times} "./tema1 $M $R ./test.txt > test_par/out.txt"
            for x in {a..z}
            do
                mv $x.txt test_par 2>/dev/null
            done
            mv out*.txt test_par/ 2>/dev/null

            echo "Rularea a durat ${par_time} secunde"

            if [[ $speedup_test == 1 ]]
            then

                # se tine minte daca rezultatul e corect (pentru punctajul de scalabilitate)
                compare_outputs test_par test_out

                if [[ $? == 1 ]]
                then
                    # daca rezultatul e incorect, scalabilitatea este zero
                    echo "Testul nu a dat rezultate corecte, scalabilitatea nu se puncteaza (acceleratia se considera 0)"
                    speedup=0
                else
                    speedup=$(echo "${seq_time}/${par_time}" | bc -l | xargs printf "%.2f")
                    printf "Acceleratie: %0.2f\n" $speedup
                    if [[ $speedup < 1 ]]
                    then
                        echo "Acceleratia este subunitara, corectitudinea testului nu se puncteaza"
                    else
                        correct=$((correct+1))
                    fi
                fi

                # se verifica acceleratia
                max=$(echo "${speedup} >= ${full_speedups[$count]}" | bc -l)
                part=$(echo "${speedup} >= ${int_speedups[$count]}" | bc -l)
                if [ $max == 1 ]
                then
                    scalability=$((scalability+16))
                elif [ $part == 1 ]
                then
                    scalability=$((scalability+8))
                    echo "W: Acceleratia de la acest test este prea mica (punctaj partial)"
                else
                    echo "W: Acceleratia de la acest test este prea mica (fara punctaj)"
                fi

                count=$((count+1))
            else
                # se tine minte daca rezultatul e corect (pentru punctajul de scalabilitate)
                compare_outputs test_par test_out

                if [[ $? == 0 ]]
                then
                    correct=$((correct+1))
                fi
            fi

            rm -rf test_par
        fi

        echo "OK"
        echo ""
    done
done

echo "=========================="
echo ""

correctness=$((correct * 4))

rm -rf tema1 &> /dev/null
cd ../src
make clean &> /dev/null

show_score
