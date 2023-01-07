package product;

import fileMethods.CreateTable;
import fileMethods.ReadDataFromTXTFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class SortProduct {

    public SortProduct() {
    }

    public static ArrayList<Long> sortAscending(ArrayList<Long> values) { // Take in an arraylist and sort the content ascending
        Collections.sort(values);

        return values; // Return a sorted ArrayList
    }

    public static ArrayList<Long> sortDescending(ArrayList<Long> values) { // Take in an arraylist and sort the content descending
        values.sort(Collections.reverseOrder());

        return values; // Return a sorted ArrayList
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

            // Set up table
            createTable.setShowVerticalLines(true);
            createTable.setHeaders("ID", "TITLE", "PRICE", "CATEGORY");

            // Loop to add items description into a table
            for (int a = 0; a < prices.size(); a++) {

                // Use the given prices to determine the correct item then adding them into an array list
                String[] sortProducts = ReadDataFromTXTFile.readSpecificLine(Long.toString(priceAscend.get(a)), 2, "./src/dataFile/items.txt", ",");
                createTable.addRow(sortProducts[0], sortProducts[1], sortProducts[2], sortProducts[3]);
            }
        } else if (input == 2) {
            // Create an arraylist that sorted the prices in descending order
            ArrayList<Long> priceDescend = SortProduct.sortDescending(prices);

            // Set up table
            createTable.setShowVerticalLines(true);
            createTable.setHeaders("ID", "TITLE", "PRICE", "CATEGORY");

            // Loop to add items description into a table
            for (int a = 0; a < prices.size(); a++) {

                // Use the given prices to determine the correct item then adding them into an array list
                String[] sortProducts = ReadDataFromTXTFile.readSpecificLine(Long.toString(priceDescend.get(a)), 2, "./src/dataFile/items.txt", ",");
                createTable.addRow(sortProducts[0], sortProducts[1], sortProducts[2], sortProducts[3]);
            }
        }
        createTable.print();
    }

    public void getBestSeller() throws IOException {
        // Initialise maxCount
        int maxCount = 0;

        ArrayList<String[]> productInfo = ReadDataFromTXTFile.readAllLines("./src/dataFile/items.txt");
        // Reading column quantities in productsSold file
        String[] productList = ReadDataFromTXTFile.readColString(2, "./src/dataFile/productsSold.txt", ",");

        // Read all products in productsSold
        ArrayList<String[]> allProduct = ReadDataFromTXTFile.readAllLines("./src/dataFile/productsSold.txt");

        // An ArrayList used to get the numbersSold for that item
        ArrayList<Integer> countProduct = new ArrayList<>();

        // Adding each item sales number into an Arraylist
        for (int a = 1; a < productList.length; a++) {
            int m = Integer.parseInt(productList[a]);
            countProduct.add(m);
        }

        // Getting the max amount of product sales as parameter
        for (Integer integer : countProduct) {
            if (integer > maxCount) {
                maxCount = integer;
            }
        }
        // ArrauList to store potential bestSeller product
        String[] bestSeller = new String[0];

        // Loop through all items to find corresponding numbersSold to compare
        for (int p = 1; p < allProduct.size(); p++) {
            // If the product sales amount match the max amount
            if (Integer.parseInt(allProduct.get(p)[2]) == maxCount) {
                bestSeller = (allProduct.get(p)); // The product is added into bestSeller
            }
        }

        // Setting up table
        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("ID", "TITLE", "PRICE", "CATEGORY", "NUMBER SOLD");

        for (int i = 1; i < productInfo.size(); i++) {
            if (productInfo.get(i)[1].equals(bestSeller[1])) {
                createTable.addRow(productInfo.get(i)[0], productInfo.get(i)[1], productInfo.get(i)[2],
                        productInfo.get(i)[2], bestSeller[2]);
            }

        }
        createTable.print();
        // Getting the info of bestSeller product and adding it into the table
//        for (int y = 0; y < bestSeller.size(); y++) {
//            String[] info = FileMethods.ReadDataFromTXTFile.readSpecificLine(bestSeller.get(y)[1], 0, "./src/productsSold.txt", ",");
//            bestSellerInfo.add(info);
//            createTable.addRow(bestSellerInfo.get(y)[0], bestSellerInfo.get(y)[1], bestSellerInfo.get(y)[2], bestSellerInfo.get(y)[3], String.valueOf(maxCount));
//        }
//        createTable.print();
    }

    public void getLeastSeller() throws IOException {
        // Initialise minCount
        int minCount = 1000000;

        ArrayList<String[]> productInfo = ReadDataFromTXTFile.readAllLines("./src/dataFile/items.txt");

        // Reading column quantities in productsSold file
        String[] productList = ReadDataFromTXTFile.readColString(2, "./src/dataFile/productsSold.txt", ",");

        // Read all products in productsSold
        ArrayList<String[]> allProduct = ReadDataFromTXTFile.readAllLines("./src/dataFile/productsSold.txt");

        // An ArrayList used to get the numbersSold for that item
        ArrayList<Integer> countProduct = new ArrayList<>();

        // Adding each item sales number into an Arraylist
        for (int a = 1; a < productList.length; a++) {
            int m = Integer.parseInt(productList[a]);
            countProduct.add(m);
        }

        // Getting the min amount of product sales as parameter
        for (Integer integer : countProduct) {
            if (integer < minCount) {
                minCount = integer;
            }
        }

        // Arrau to store potential bestSeller product
        String[] leastSeller = new String[0];

        for (int p = 1; p < allProduct.size(); p++) {
            // If the product sales amount match the max amount
            if (Integer.parseInt(allProduct.get(p)[2]) == minCount) {
                leastSeller = (allProduct.get(p)); // The product is added into bestSeller

            }
        }

        // Setting up table
        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("ID", "TITLE", "PRICE", "CATEGORY", "NUMBER SOLD");

        for (int i = 1; i < productInfo.size(); i++) {
            if (productInfo.get(i)[1].equals(leastSeller[1])) {
                createTable.addRow(productInfo.get(i)[0], productInfo.get(i)[1], productInfo.get(i)[2],
                        productInfo.get(i)[2], leastSeller[2]);
            }

        }
        createTable.print();
    }
}