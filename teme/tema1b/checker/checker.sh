#!/bin/bash

test_results=()
total=0

cd /apd

run_test() {
    test_name=$1
    test="mvn test -Dtest=org.apd.executor.ExecutorTests#$test_name"
    $test

    if [ $? -eq 0 ]; then
        test_results+=("Test $test_name: 5p\r\n\t")
        total=$((total + 5))
    else
        test_results+=("Test $test_name: 0p\r\n\t")
    fi
}

run_test "executeWorkReaderPreferred1"
run_test "executeWorkReaderPreferred2"
run_test "executeWorkReaderPreferred3"
run_test "executeWorkReaderPreferred4"
run_test "executeWorkReaderPreferred5"
run_test "executeWorkReaderPreferred6"
run_test "executeWorkWriterPreferred11"
run_test "executeWorkWriterPreferred12"
run_test "executeWorkWriterPreferred13"
run_test "executeWorkWriterPreferred14"
run_test "executeWorkWriterPreferred15"
run_test "executeWorkWriterPreferred16"
run_test "executeWorkWriterPreferred21"
run_test "executeWorkWriterPreferred22"
run_test "executeWorkWriterPreferred23"
run_test "executeWorkWriterPreferred24"
run_test "executeWorkWriterPreferred25"
run_test "executeWorkWriterPreferred26"

results=$(IFS=""; echo "${test_results[*]}")
echo -ne "Results:\r\n\t${results}"
echo -ne "\r\n"
echo -ne "Total: ${total}/90"
