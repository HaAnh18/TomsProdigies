import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class SortProduct{
    public static ArrayList<Long> sortAscending(ArrayList<Long> prices) {
        Collections.sort(prices);

        return prices;
    }

    public static ArrayList<Long> sortDescending(ArrayList<Long> prices) {
        prices.sort(Collections.reverseOrder());

        return prices;
    }

    public SortProduct() throws IOException {
    }
}