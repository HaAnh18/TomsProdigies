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

package bonusFeatures;

import fileMethods.CreateTable;
import fileMethods.ReadDataFromTXTFile;
import fileMethods.Write;
import product.Product;
import users.Customer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Cart {
    private Customer customer = new Customer();

    // Constructor
    public Cart(Customer customer) {
        this.customer = customer;
    }

    // Constructor
    public Cart() {
    }

    // Get all the product that have in that customer's cart
    public ArrayList<String[]> cartList() {
        ArrayList<String[]> products = new ArrayList<>();

        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/customerCart.txt");
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[0].equals(customer.getcID()))
            // If the system could find out the customer's ID in ordersHistory's file
            {
                products.add(database.get(i));
            }
        } return products;
    }

    // Add new items into a customer's cart
    public void addToCart(Product product, int quantity) throws IOException {
        PrintWriter pw;
        pw = new PrintWriter(new FileWriter("./src/dataFile/customerCart.txt", true));

        String customerID = customer.getcID();
        String productTitle = product.getTitle();
        Long singleUnitPrice = product.getPrice();
        Long paymentPrice = product.getPrice() * quantity;
        // Assigning data into each column
        pw.println(customerID + "," + productTitle + "," + singleUnitPrice + "," +
                quantity + "," + paymentPrice);
        pw.close();
    }

    // Prompt a specific customer's cart into the terminal for them to view
    public void getCustomerCart() {
        ArrayList<String[]> products = new ArrayList<>();

        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/customerCart.txt");
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[0].equals(customer.getcID())) {
                // Read through the text file and store that file data into an ArrayList to check
                // If the system could find out the customer's ID in ordersHistory's file
                products.add(database.get(i));
            }
        }
        // Prompt a specific customer's cart contents into the terminal under the table format rule
        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("OPTION", "CID", "PRODUCT'S TITLE", "QUANTITY", "SINGLE UNIT PRICE", "TOTAL PAYMENT");
        for (int i = 0; i < products.size(); i++) {
            CreateTable.addRow(String.valueOf(i + 1), products.get(i)[0], products.get(i)[1], products.get(i)[2],
                    products.get(i)[3], products.get(i)[4]);
        }
        CreateTable.print();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
    }

    // Delete all the items from a specific customer's cart
    public void deleteAllItemsInCart(String filepath, String cId) throws IOException {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/customerCart.txt");
        ArrayList<String[]> newDatabase = new ArrayList<>();
        for (String[] strings : database) {
            if (!strings[0].equals(cId)) {
                // Read through the text file and store that file data into an ArrayList to check
                // If the system could find out the customer's ID in customerCart's file
                newDatabase.add(strings);
            }
        }
        // Delete all the file content
        PrintWriter pw = new PrintWriter("./src/dataFile/customerCart.txt");

        pw.write("");
        pw.close();

        // Rewrite the file with the updated content
        for (String[] obj : newDatabase) {
            Write.rewriteFile(filepath, "#CID,Product's title,Single unit price,Quantity,Total payment",
                    String.join(",", obj));
        }
    }

    // Delete a specific item form a particular customer's cart
    public void deleteItemInCart(String filepath, String cId, Product product) throws IOException {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/customerCart.txt");
        ArrayList<String[]> customerCart = new ArrayList<>();
        ArrayList<String[]> newDatabase = new ArrayList<>();
        customerCart.add(database.get(0));
        for (String[] strings : database) {
            if (strings[0].equals(cId)) {
                // Read through the text file and store that file data into an ArrayList to check
                // If the system could find out the customer's ID in customerCart's file
                customerCart.add(strings);
            }
        }
        for (String[] strings : customerCart) {
            if (!strings[1].equals(product.getTitle())) {
                // Read through the text file and store that file data into an ArrayList to check
                // If the system could find out the product's title in customerCart's file
                newDatabase.add(strings);
            }
        }
        // Delete all the file content
        PrintWriter pw = new PrintWriter("./src/dataFile/customerCart.txt");

        pw.write("");
        pw.close();

        // Rewrite the file with the updated content
        for (String[] obj : newDatabase) {
            Write.rewriteFile(filepath, "#CID,Product's title,Single unit price,Quantity,Total payment",
                    String.join(",", obj));
        }
    }
}
