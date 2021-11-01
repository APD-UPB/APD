package bug6;

/**
 * What is this design pattern?
 * What it could be wrong in this case? How many instances are created in this context?
 *
 * Modify a single line of code such that the Singleton to work as expected.
 */

public class Singleton {
    private static Singleton instance;
    private static int numberOfInstances = 0;

    private Singleton() {

    }

    public static Singleton getInstance() {
        if (instance == null) {
            System.out.println("Creating only one instance");
            instance = new Singleton();
            ++numberOfInstances;
        }
        return instance;
    }

    public static int getNumberOfInstances() {
        return numberOfInstances;
    }
}
