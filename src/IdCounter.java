public class IdCounter {
    private static long counter = 1;

    public static long nextId() {
        return ++counter;
    }


}

