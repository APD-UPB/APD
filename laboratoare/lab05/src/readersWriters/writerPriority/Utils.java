package readersWriters.writerPriority;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.List;

public class Utils {
    public static String get_current_time_str() {
        // Get the current time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Define the format pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        return currentDateTime.format(formatter);
    }


    public static <T extends SuperType, SuperType> List<SuperType> concatenateAndShuffleArrays(T[] array1, T[] array2) {
        List<SuperType> concatenatedList = new ArrayList<>();

        Collections.addAll(concatenatedList, array1);
        Collections.addAll(concatenatedList, array2);

        // Shuffle the elements
        Collections.shuffle(concatenatedList, new Random());

        return concatenatedList;
    }
}
