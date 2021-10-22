#!/bin/bash

N=1000
P=2

if [ ! -f "multiply_seq" ]
then
    echo "Nu exista binarul multiply_seq"
    exit
fi

if [ ! -f "multiply_outer" ]
then
    echo "Nu exista binarul multiply_outer"
    exit
fi

if [ ! -f "multiply_middle" ]
then
    echo "Nu exista binarul multiply_middle"
    exit
fi

if [ ! -f "multiply_inner" ]
then
    echo "Nu exista binarul multiply_inner"
    exit
fi

./multiply_seq $N > seq.txt
./multiply_outer $N $P > par_outer.txt
./multiply_middle $N $P > par_middle.txt
./multiply_inner $N $P > par_inner.txt

diff seq.txt par_outer.txt
diff seq.txt par_middle.txt
diff seq.txt par_inner.txt

rm -rf seq.txt par_outer.txt par_middle.txt par_inner.txt