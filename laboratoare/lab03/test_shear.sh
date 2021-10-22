#!/bin/bash

if [ ! -f "shear" ]
then
    echo "Nu exista binarul shear"
    exit
fi

if ./shear 10 3 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru L = 10 P = 3"
else
    echo "Sortare incorecta pentru L = 10 P = 3"  
fi

if ./shear 10 5 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru L = 10 P = 5"
else
    echo "Sortare incorecta pentru L = 10 P = 5" 
fi

if ./shear 10 6 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru L = 10 P = 6"
else
    echo "Sortare incorecta pentru L = 10 P = 6"
fi

if ./shear 10 7 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru L = 10 P = 7"
else
    echo "Sortare incorecta pentru L = 10 P = 7"
fi

if ./shear 50 7 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru L = 50 P = 7"
else
    echo "Sortare incorecta pentru L = 50 P = 7"
fi

if ./shear 100 4 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru L = 100 P = 4"
else
    echo "Sortare incorecta pentru L = 100 P = 4"
fi

if ./shear 100 6 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru L = 100 P = 6"
else
    echo "Sortare incorecta pentru L = 100 P = 6"
fi

if ./shear 100 7 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru L = 100 P = 7"
else
    echo "Sortare incorecta pentru L = 100 P = 7"
fi