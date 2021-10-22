#!/bin/bash

if [ ! -f "merge" ]
then
    echo "Nu exista binarul merge"
    exit
fi

if ./merge 64 3 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru N = 64 P = 3"
else
    echo "Sortare incorecta pentru N = 64 P = 3"  
fi

if ./merge 128 4 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru N = 128 P = 4"
else
    echo "Sortare incorecta pentru N = 128 P = 4" 
fi

if ./merge 128 7 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru N = 128 P = 7"
else
    echo "Sortare incorecta pentru N = 128 P = 7"
fi

if ./merge 512 2 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru N = 512 P = 2"
else
    echo "Sortare incorecta pentru N = 512 P = 2"
fi

if ./merge 512 7 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru N = 512 P = 7"
else
    echo "Sortare incorecta pentru N = 512 P = 7"
fi

if ./merge 1024 4 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru N = 1024 P = 4"
else
    echo "Sortare incorecta pentru N = 1024 P = 4"
fi

if ./merge 1024 6 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru N = 1024 P = 6"
else
    echo "Sortare incorecta pentru N = 1024 P = 6"
fi

if ./merge 1024 7 | grep -q "Sortare corecta"; then
    echo "Sortare corecta pentru N = 1024 P = 7"
else
    echo "Sortare incorecta pentru N = 1024 P = 7"
fi