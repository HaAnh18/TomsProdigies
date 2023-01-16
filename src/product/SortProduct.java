/*
  RMIT University Vietnam
  Course: COSC2081 Programming 1
  Semester: 2022C
  Assessment: Assignment 3
  Author: Tom's Prodigies
  ID: Nguyen Tran Ha Anh - s3938490
      Hoang Tuan Minh - s3924716
      Dang Kim Quang Minh - s3938024
      Nguyen Gia Bao - s3938143
  Acknowledgement:

*/

package product;

import fileMethods.CreateTable;
import fileMethods.ReadDataFromTXTFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class SortProduct {

    // Constructor
    public SortProduct() {
    }

    // Take in an arraylist and sort the content ascending
    public static ArrayList<Long> sortAscending(ArrayList<Long> values) {
        Collections.sort(values);

        return values; // Return a sorted ArrayList
    }

    // Take in an arraylist and sort the content descending
    public static ArrayList<Long> sortDescending(ArrayList<Long> values) {
        values.sort(Collections.reverseOrder());

        return values; // Return a sorted ArrayList
    }

    // Sorting the product by price
    public static void sortItems(int input) throws IOException {
        // Create an product object
        Product product = new Product();

        // Use the get all prices method to create a prices list
        ArrayList<Long> prices = product.getAllPrice();

        // Check for user inputs whether to sort ascend or descend
        if (input == 1) {
            // Create an arraylist that sorted the prices in ascending order
            ArrayList<Long> priceAscend = SortProduct.sortAscending(prices);

            // Set up table
            CreateTable.setShowVerticalLines(true);
            CreateTable.setHeaders("ID", "TITLE", "PRICE", "CATEGORY");

            // Loop to add items description into a table
            for (int a = 0; a < prices.size(); a++) {

                // Use the given prices to determine the correct item then adding them into an array list
                String[] sortProducts = ReadDataFromTXTFile.readSpecificLine(Long.toString(priceAscend.get(a)), 2, "./src/dataFile/items.txt", ",");
                CreateTable.addRow(sortProducts[0], sortProducts[1], sortProducts[2], sortProducts[3]);
            }
        } else if (input == 2) {
            // Create an arraylist that sorted the prices in descending order
            ArrayList<Long> priceDescend = SortProduct.sortDescending(prices);

            // Set up table
            CreateTable.setShowVerticalLines(true);
            CreateTable.setHeaders("ID", "TITLE", "PRICE", "CATEGORY");

            // Loop to add items description into a table
            for (int a = 0; a < prices.size(); a++) {

                // Use the given prices to determine the correct item then adding them into an array list
                String[] sortProducts = ReadDataFromTXTFile.readSpecificLine(Long.toString(priceDescend.get(a)), 2, "./src/dataFile/items.txt", ",");
                CreateTable.addRow(sortProducts[0], sortProducts[1], sortProducts[2], sortProducts[3]);
            }
        }
        CreateTable.print();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
    }
}