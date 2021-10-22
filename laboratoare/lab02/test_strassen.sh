#!/bin/bash

N=1000
P=2

if [ ! -f "strassen" ]
then
    echo "Nu exista binarul strassen"
    exit
fi

if [ ! -f "strassen_par" ]
then
    echo "Nu exista binarul strassen_par"
    exit
fi

./strassen $N > seq.txt
./strassen_par $N > par.txt

diff seq.txt par.txt

rm -rf seq.txt par.txt