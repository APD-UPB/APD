#!/bin/bash

if [ ! -f "oets" ]
then
    echo "Nu exista binarul oets"
    exit
fi

if ./oets 100 3 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru N = 100 P = 3"
else
    echo "Sortare incorecta pentru N = 100 P = 3"  
fi

if ./oets 100 5 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru N = 100 P = 5"
else
    echo "Sortare incorecta pentru N = 100 P = 5" 
fi

if ./oets 100 6 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru N = 100 P = 6"
else
    echo "Sortare incorecta pentru N = 100 P = 6"
fi

if ./oets 100 7 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru N = 100 P = 7"
else
    echo "Sortare incorecta pentru N = 100 P = 7"
fi

if ./oets 500 7 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru N = 500 P = 7"
else
    echo "Sortare incorecta pentru N = 500 P = 7"
fi

if ./oets 1000 4 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru N = 1000 P = 4"
else
    echo "Sortare incorecta pentru N = 1000 P = 4"
fi

if ./oets 1000 6 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru N = 1000 P = 6"
else
    echo "Sortare incorecta pentru N = 1000 P = 6"
fi

if ./oets 1000 7 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru N = 1000 P = 7"
else
    echo "Sortare incorecta pentru N = 1000 P = 7"
fi