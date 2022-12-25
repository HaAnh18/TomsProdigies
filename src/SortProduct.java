import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class SortProduct{
    public static ArrayList<Double> sortAscending(ArrayList<Double> prices){
        Collections.sort(prices);

        return prices;
    }

    public static ArrayList<Double> sortDescending(ArrayList<Double> prices){
        prices.sort(Collections.reverseOrder());

        return prices;
    }

    public SortProduct() throws IOException {
    }
}