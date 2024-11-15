package org.apd.executor;

import org.apd.storage.EntryResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/* DO NOT MODIFY */
class ExecutorTests {
    private final static String[] testValues1 = {
            "Lorem ipsum odor amet, consectetuer adipiscing elit.",
            "Vulputate praesent ut himenaeos montes netus sit ac.",
            "Enim nisl mollis; placerat natoque ornare odio libero interdum.",
            "Curabitur ante potenti ullamcorper tellus at nullam euismod ornare venenatis.",
            "Ornare sem neque curae suscipit amet elit egestas.",
            "Urna aliquet facilisi convallis consectetur integer; lacus parturient aliquam."
    };

    private final static String[] testValues2 = {
            "fffkpk439to65mkbfkfhudsofffkpk439to65mkbfkfhudsof84nfkjnsfdgfdgfdgfdgfdgfkfhjewkfesfdsfffkpk439to65mkbfkfhudsof84nfkjnsfdgfdgfdgfdgfdgfkfffkpk439to65mkbfkfhudsof84nfkjnsfdgfdgfdgfdgfdgfkfhjewkfesfdsfffkpk439to65mkbfkfhudsof84nfkjnsfdgfdgfdgfdgfdgfkfhjewkfesfdsfffkpk439to65mkbfkfhudsof84nfkjnsfdgfdgfdgfdgfdgfkfhjewkfesfdsfhjewkfesfdsfffkpk439to65mkbfkfhudsof84nfkjnsfdgfdgfdgfdgfdgfkfhjewkfesfdsfffkpk439to65mkbfkfhudsof84nfkjnsfdgfdgfdgfdgfdgfkfhjewkfesfdsf84nfkjnsfdgfdgfdgfdgfdgfkfhjewkfesfds",
            "rohvitretretretfdgfdgfdgfdgfdgretrgfdg43545765764242354355sdhuvru5nngfgfgfgdffdgfdgfdgjxgfdgfdsfsdhiu45htkjgfdgfdgfdgfdgfdggfskjgfgrohvitretretretfdgfdgfdgfdgfdgretrgfdg43545765764242354355sdhuvru5nngfgfgfgdffdgfdgfdgjxgfdgfdsfsdhiu45htkjgfdgfdgfdgfdgfdggfskjgfgrohvitretretretfdgfdgfdgfdgfdgretrgfdg43545765764242354355sdhuvru5nngfgfgfgdffdgfdgfdgjxgfdgfdsfsdhiu45htkjgfdgfdgfdgfdgfdggfskjgfgrohvitretretretfdgfdgfdgfdgfdgretrgfdg43545765764242354355sdhuvru5nngfgfgfgdffdgfdgfdgjxgfdgfdsfsdhiu45htkjgfdgfdgfdgfdgfdggfskjgfgrohvitretretretfdgfdgfdgfdgfdgretrgfdg43545765764242354355sdhuvru5nngfgfgfgdffdgfdgfdgjxgfdgfdsfsdhiu45htkjgfdgfdgfdgfdgfdggfskjgfg",
            "a;spo;'spw;[2r;3[r;43[r;fkciuhucmrenk45yr 322c ;r'lf;s'dsfa;spo;'spw;[2r;3[r;43[r;fkciuhucmrenk45yr 322c ;r'lf;s'dsfa;spo;'spw;[2r;3[r;43[r;fkciuhucmrenk45yr 322c ;r'lf;s'dsfa;spo;'spw;[2r;3[r;43[r;fkciuhucmrenk45yr 322c ;r'lf;s'dsfa;spo;'spw;[2r;3[r;43[r;fkciuhucmrenk45yr 322c ;r'lf;s'dsfa;spo;'spw;[2r;3[r;43[r;fkciuhucmrenk45yr 322c ;r'lf;s'dsfa;spo;'spw;[2r;3[r;43[r;fkciuhucmrenk45yr 322c ;r'lf;s'dsfa;spo;'spw;[2r;3[r;43[r;fkciuhucmrenk45yr 322c ;r'lf;s'dsfa;spo;'spw;[2r;3[r;43[r;fkciuhucmrenk45yr 322c ;r'lf;s'dsfa;spo;'spw;[2r;3[r;43[r;fkciuhucmrenk45yr 322c ;r'lf;s'dsfa;spo;'spw;[2r;3[r;43[r;fkciuhucmrenk45yr 322c ;r'lf;s'dsf",
            "gfdsgdsfdsfdafdgfdnk5y32urf7vf3g3t4hj3rgfdgfdgsdfdsfdsgfdsgdsfdsfdafdgfdnk5y32urf7vf3g3t4hj3rgfdgfdgsdfdsfdsgfdsgdsfdsfdafdgfdnk5y32urf7vf3g3t4hj3rgfdgfdgsdfdsfdsgfdsgdsfdsfdafdgfdnk5y32urf7vf3g3t4hj3rgfdgfdgsdfdsfdsgfdsgdsfdsfdafdgfdnk5y32urf7vf3g3t4hj3rgfdgfdgsdfdsfdsgfdsgdsfdsfdafdgfdnk5y32urf7vf3g3t4hj3rgfdgfdgsdfdsfdsgfdsgdsfdsfdafdgfdnk5y32urf7vf3g3t4hj3rgfdgfdgsdfdsfdsgfdsgdsfdsfdafdgfdnk5y32urf7vf3g3t4hj3rgfdgfdgsdfdsfds",
            "fsdfdsfsdfds4rhfdgfdgfdgthtrfdfdh5bn435b432543nvnf6ryft32gbj3c r evbydf5g32d n32mu2yfh632hevx2x2iz10w20-80l9tnrfdjy32jfsdfdsfsdfds4rhfdgfdgfdgthtrfdfdh5bn435b432543nvnf6ryft32gbj3c r evbydf5g32d n32mu2yfh632hevx2x2iz10w20-80l9tnrfdjy32jfsdfdsfsdfds4rhfdgfdgfdgthtrfdfdh5bn435b432543nvnf6ryft32gbj3c r evbydf5g32d n32mu2yfh632hevx2x2iz10w20-80l9tnrfdjy32jfsdfdsfsdfds4rhfdgfdgfdgthtrfdfdh5bn435b432543nvnf6ryft32gbj3c r evbydf5g32d n32mu2yfh632hevx2x2iz10w20-80l9tnrfdjy32jfsdfdsfsdfds4rhfdgfdgfdgthtrfdfdh5bn435b432543nvnf6ryft32gbj3c r evbydf5g32d n32mu2yfh632hevx2x2iz10w20-80l9tnrfdjy32jfsdfdsfsdfds4rhfdgfdgfdgthtrfdfdh5bn435b432543nvnf6ryft32gbj3c r evbydf5g32d n32mu2yfh632hevx2x2iz10w20-80l9tnrfdjy32jfsdfdsfsdfds4rhfdgfdgfdgthtrfdfdh5bn435b432543nvnf6ryft32gbj3c r evbydf5g32d n32mu2yfh632hevx2x2iz10w20-80l9tnrfdjy32jfsdfdsfsdfds4rhfdgfdgfdgthtrfdfdh5bn435b432543nvnf6ryft32gbj3c r evbydf5g32d n32mu2yfh632hevx2x2iz10w20-80l9tnrfdjy32jfsdfdsfsdfds4rhfdgfdgfdgthtrfdfdh5bn435b432543nvnf6ryft32gbj3c r evbydf5g32d n32mu2yfh632hevx2x2iz10w20-80l9tnrfdjy32jfsdfdsfsdfds4rhfdgfdgfdgthtrfdfdh5bn435b432543nvnf6ryft32gbj3c r evbydf5g32d n32mu2yfh632hevx2x2iz10w20-80l9tnrfdjy32j",
            "tretretretfdgfdgfdgfdgfdgretrret45tr4e3rvi uycxchn32fvhjedfvjbskhtcbf43mf 4nm45tnvt ckf gtretretretfdgfdgfdgfdgfdgretrret45tr4e3rvi uycxchn32fvhjedfvjbskhtcbf43mf 4nm45tnvt ckf gtretretretfdgfdgfdgfdgfdgretrret45tr4e3rvi uycxchn32fvhjedfvjbskhtcbf43mf 4nm45tnvt ckf gtretretretfdgfdgfdgfdgfdgretrret45tr4e3rvi uycxchn32fvhjedfvjbskhtcbf43mf 4nm45tnvt ckf gtretretretfdgfdgfdgfdgfdgretrret45tr4e3rvi uycxchn32fvhjedfvjbskhtcbf43mf 4nm45tnvt ckf gtretretretfdgfdgfdgfdgfdgretrret45tr4e3rvi uycxchn32fvhjedfvjbskhtcbf43mf 4nm45tnvt ckf gtretretretfdgfdgfdgfdgfdgretrret45tr4e3rvi uycxchn32fvhjedfvjbskhtcbf43mf 4nm45tnvt ckf g",
            "gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34gfdgh7v4t4kjl323767095%^$%#$%#@!4idgfdgfdgfdgfdgfd4j24iytoerbewjrh43kjbrmnsmctx guwn4 n f p0dsoa2koed34"
    };

    private static List<StorageTask> generateTasks(int storageSize, int numberOfTasks, double readersToWritersRatio, String[] values) {
        return IntStream.range(0, numberOfTasks).mapToObj(i -> {
            var random = new Random();
            return new StorageTask(random.nextInt(storageSize),
                    random.nextDouble() > readersToWritersRatio ?
                            values[random.nextInt(values.length)] :
                            null);
        }).toList();
    }

    void verifyResults(List<StorageTask> tasks, List<EntryResult> results, String[] values) {
        var resultIndexes = results.stream().collect(Collectors.groupingBy(EntryResult::index));

        resultIndexes.forEach((index, resultIndex) -> {
            var resultSequences = resultIndex.stream().collect(Collectors.groupingBy(EntryResult::sequence));

            resultSequences.forEach((sequence, value) -> {
                var first = value.stream().findFirst().map(EntryResult::data).orElse(null);
                Assertions.assertTrue(value.stream().reduce(true,
                                (a, b) -> a && Objects.equals(b.data(), first),
                                (acc, result) -> acc && result),
                        "All values on the same index and sequence must be the same, once a sequence number is set all the values read from that index must be the same!");
            });
        });

        Assertions.assertEquals(resultIndexes
                        .values()
                        .stream()
                        .mapToInt(entryResults -> entryResults
                                .stream()
                                .mapToInt(EntryResult::sequence)
                                .max()
                                .orElse(0))
                        .sum(),
                tasks.stream().filter(StorageTask::isWrite).count(),
                "The sum of generated sequence numbers should be the same as the number of writers!");

        Assertions.assertTrue(results.stream().map(EntryResult::data).map(e -> Objects.equals(e, "") || Arrays.asList(values).contains(e)).reduce((a, b) -> a && b).orElse(false),
                "All values form the results must appear in the initial values provided!");
    }

    void executeWork(int numberOfThreads, int numberOfTasks, double readersToWritersRatio, String[] values,
                     int storageSize, int blockSize, long readDuration, long writeDuration, LockType lockType, int timeout) {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(timeout), () -> {
            var tasks = generateTasks(storageSize, numberOfTasks, readersToWritersRatio, values);
            var results = new TaskExecutor(storageSize, blockSize, readDuration, writeDuration)
                    .ExecuteWork(numberOfThreads, tasks, lockType);

            verifyResults(tasks, results, values);
        });
    }

    @Test
    void executeWorkReaderPreferred1() {
        executeWork(10, 100, 0.2, testValues1, testValues1.length, 255, 100, 250, LockType.ReaderPreferred, 10);
    }

    @Test
    void executeWorkReaderPreferred2() {
        executeWork(12, 100, 0.2, testValues1, testValues1.length / 2, 255, 100, 250, LockType.ReaderPreferred, 15);
    }

    @Test
    void executeWorkReaderPreferred3() {
        executeWork(8, 100, 0.2, testValues1, testValues1.length * 2, 255, 100, 250, LockType.ReaderPreferred, 10);
    }

    @Test
    void executeWorkReaderPreferred4() {
        executeWork(10, 100, 0.3, testValues2, testValues2.length, 3000, 50, 300, LockType.ReaderPreferred, 10);
    }

    @Test
    void executeWorkReaderPreferred5() {
        executeWork(12, 100, 0.3, testValues2, testValues2.length / 2, 3000, 50, 300, LockType.ReaderPreferred, 15);
    }

    @Test
    void executeWorkReaderPreferred6() {
        executeWork(8, 100, 0.3, testValues2, testValues2.length * 2, 3000, 150, 150, LockType.ReaderPreferred, 10);
    }

    @Test
    void executeWorkWriterPreferred11() {
        executeWork(10, 200, 0.7, testValues1, testValues1.length, 255, 100, 250, LockType.WriterPreferred1, 15);
    }

    @Test
    void executeWorkWriterPreferred12() {
        executeWork(12, 200, 0.7, testValues1, testValues1.length / 2, 255, 100, 250, LockType.WriterPreferred1, 15);
    }

    @Test
    void executeWorkWriterPreferred13() {
        executeWork(8, 200, 0.7, testValues1, testValues1.length * 2, 255, 100, 250, LockType.WriterPreferred1, 15);
    }

    @Test
    void executeWorkWriterPreferred14() {
        executeWork(10, 500, 0.8, testValues2, testValues2.length, 3000, 50, 300, LockType.WriterPreferred1, 30);
    }

    @Test
    void executeWorkWriterPreferred15() {
        executeWork(12, 500, 0.8, testValues2, testValues2.length / 2, 3000, 50, 300, LockType.WriterPreferred1, 30);
    }

    @Test
    void executeWorkWriterPreferred16() {
        executeWork(8, 500, 0.5, testValues2, testValues2.length * 2, 3000, 150, 150, LockType.WriterPreferred1, 30);
    }

    @Test
    void executeWorkWriterPreferred21() {
        executeWork(10, 200, 0.7, testValues1, testValues1.length, 255, 100, 250, LockType.WriterPreferred2, 15);
    }

    @Test
    void executeWorkWriterPreferred22() {
        executeWork(12, 200, 0.7, testValues1, testValues1.length / 2, 255, 100, 250, LockType.WriterPreferred2, 15);
    }

    @Test
    void executeWorkWriterPreferred23() {
        executeWork(8, 200, 0.7, testValues1, testValues1.length * 2, 255, 100, 250, LockType.WriterPreferred2, 15);
    }

    @Test
    void executeWorkWriterPreferred24() {
        executeWork(10, 500, 0.8, testValues2, testValues2.length, 3000, 50, 300, LockType.WriterPreferred2, 30);
    }

    @Test
    void executeWorkWriterPreferred25() {
        executeWork(12, 500, 0.8, testValues2, testValues2.length / 2, 3000, 50, 300, LockType.WriterPreferred2, 30);
    }

    @Test
    void executeWorkWriterPreferred26() {
        executeWork(8, 500, 0.5, testValues2, testValues2.length * 2, 3000, 150, 150, LockType.WriterPreferred2, 30);
    }
}