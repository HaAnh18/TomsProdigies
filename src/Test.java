import java.io.IOException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
//        Product.searchCategoryByPriceRange("laptop");
//        Product.searchByCategory("laptop");
//        Product.findItemByPriceRange();
//        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/items.txt");
//        database.get(1);
//        System.out.println(database.get(1)[0]);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter category: ");
        String category = scanner.nextLine();
        Product.searchCategoryByPriceRange(category);


    }
}
