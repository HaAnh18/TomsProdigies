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

    public static void sortItems(int input) throws IOException {
        // Create an product object
        Product product = new Product();

        // Use the get all prices method to create a prices list
        ArrayList<Long> prices = product.getAllPrice();

        // Create an createTable object
        CreateTable createTable = new CreateTable();

        // Check for user inputs whether to sort ascend or descend
        if (input == 1) {
            // Create an arraylist that sorted the prices in ascending order
            ArrayList<Long> priceAscend = SortProduct.sortAscending(prices);

            // Create the headers and lines
            createTable.setShowVerticalLines(true);
            createTable.setHeaders("ID", "TITLE", "PRICE", "CATEGORY");

            // Loop to add items description into a table
            for (int a = 0; a < prices.size(); a++) {
                // Use the given prices to determine the correct item then adding them into an array list
                String[] sortProducts = ReadDataFromTXTFile.readSpecificLine(Long.toString(priceAscend.get(a)), 2, "./src/items.txt", ",");
                createTable.addRow(sortProducts[0], sortProducts[1], sortProducts[2], sortProducts[3]);
            }
        } else if (input == 2) {
            ArrayList<Long> priceDescend = SortProduct.sortDescending(prices);

            createTable.setShowVerticalLines(true);
            createTable.setHeaders("ID", "TITLE", "PRICE", "CATEGORY");

            for (int a = 0; a < prices.size(); a++) {
                String[] sortProducts = ReadDataFromTXTFile.readSpecificLine(Long.toString(priceDescend.get(a)), 2, "./src/items.txt", ",");
                createTable.addRow(sortProducts[0], sortProducts[1], sortProducts[2], sortProducts[3]);
            }
        }
        createTable.print();
    }

    public void getBestSeller() throws IOException {
        // Initialise maxCount
        int maxCount = 0;
        // Reading column quantities in productsSold file
        String[] productList = ReadDataFromTXTFile.readColString(2, "./src/productsSold.txt", ",");
        // Read all products in productsSold
        ArrayList<String[]> allProduct = ReadDataFromTXTFile.readAllLines("./src/productsSold.txt");
        //
        ArrayList<Integer> countProduct = new ArrayList<>();

        for (int a = 1; a < productList.length; a++) {
            int m = Integer.parseInt(productList[a]);
            countProduct.add(m);
        }

        for (int i = 0; i < countProduct.size(); i++) {
            if (countProduct.get(i) > maxCount) {
                maxCount = countProduct.get(i);
            }
        }
        ArrayList<String[]> bestSeller = new ArrayList<>();
        for (int p = 1; p < allProduct.size(); p++) {
            if (allProduct.get(p)[2].equals(String.valueOf(maxCount))) {
                bestSeller.add(allProduct.get(p));
            }
        }
        ArrayList<String[]> bestSellerInfo = new ArrayList<>();
        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("ID", "TITLE", "PRICE", "CATEGORY", "NUMBER SOLD");
        for (int y = 0; y < bestSeller.size(); y++) {
            String[] info = ReadDataFromTXTFile.readSpecificLine(bestSeller.get(y)[1], 0, "./src/items.txt", ",");
            bestSellerInfo.add(info);
            createTable.addRow(bestSellerInfo.get(y)[0], bestSellerInfo.get(y)[1], bestSellerInfo.get(y)[2], bestSellerInfo.get(y)[3], String.valueOf(maxCount));
        }
        createTable.print();
    }

    public void getLeastSeller() throws IOException {
        // Initialise minCount
        int minCount = 100;
        // Reading column quantities in productsSold file
        String[] productList = ReadDataFromTXTFile.readColString(2, "./src/productsSold.txt", ",");
        // Read all products in productsSold
        ArrayList<String[]> allProduct = ReadDataFromTXTFile.readAllLines("./src/productsSold.txt");
        //
        ArrayList<Integer> countProduct = new ArrayList<>();

        for (int a = 1; a < productList.length; a++) {
            int m = Integer.parseInt(productList[a]);
            countProduct.add(m);
        }

        for (int i = 0; i < countProduct.size(); i++) {
            if (countProduct.get(i) < minCount) {
                minCount = countProduct.get(i);
            }
        }
        ArrayList<String[]> bestSeller = new ArrayList<>();
        for (int p = 1; p < allProduct.size(); p++) {
            if (allProduct.get(p)[2].equals(String.valueOf(minCount))) {
                bestSeller.add(allProduct.get(p));
            }
        }
        ArrayList<String[]> bestSellerInfo = new ArrayList<>();
        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("ID", "TITLE", "PRICE", "CATEGORY", "NUMBER SOLD");
        for (int y = 0; y < bestSeller.size(); y++) {
            String[] info = ReadDataFromTXTFile.readSpecificLine(bestSeller.get(y)[1], 0, "./src/items.txt", ",");
            bestSellerInfo.add(info);
            createTable.addRow(bestSellerInfo.get(y)[0], bestSellerInfo.get(y)[1], bestSellerInfo.get(y)[2], bestSellerInfo.get(y)[3], String.valueOf(minCount));
        }
        createTable.print();
    }

    public SortProduct() throws IOException {
    }
}