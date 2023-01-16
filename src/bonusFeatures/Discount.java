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
import order.Order;
import users.Customer;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Discount {
    // Attributes
    private String discountCode;
    private Long discountAmount;
    private Customer customer;
    private Order order;

    // Constructor
    public Discount(Customer customer, Order order) {
        this.customer = customer;
        this.order = order;
    }

    // Constructor
    public Discount() {
    }

    // Give discount voucher based on customer's total payment
    public void giveDiscountCode() throws IOException {
        PrintWriter pw;
        pw = new PrintWriter(new FileWriter("./src/dataFile/customerDiscountCode.txt", true));
        Random rd = new Random();
        int i = rd.nextInt(999);
        String code = validateDiscountCode(String.format("%03d", i));
        ArrayList<String[]> discountType = ReadDataFromTXTFile.readAllLines("./src/dataFile/discountType.txt");

        /// Give discount code based on the totalPayment amount
        // 10000000 < totalPayment < 20000000
        if (order.getPaymentPriceDiscountByMembership() > Long.parseLong(discountType.get(1)[1]) && order.getPaymentPriceDiscountByMembership() < Long.parseLong(discountType.get(2)[1])) {
            System.out.println("Your bill have 1 voucher");
            setDiscountCode(discountType.get(1)[0]);
            setDiscountAmount(Long.parseLong(discountType.get(1)[2]));

            // 20000000 < totalPayment < 30000000
        } else if (order.getPaymentPriceDiscountByMembership() >= Long.parseLong(discountType.get(2)[1]) && order.getPaymentPriceDiscountByMembership() < Long.parseLong(discountType.get(3)[1])) {
            System.out.println("Your bill have 1 voucher");
            setDiscountCode(discountType.get(2)[0]);
            setDiscountAmount(Long.parseLong(discountType.get(2)[2]));

            // 30000000 < totalPayment < 50000000
        } else if (order.getPaymentPriceDiscountByMembership() >= Long.parseLong(discountType.get(3)[1]) && order.getPaymentPriceDiscountByMembership() < Long.parseLong(discountType.get(4)[1])) {
            System.out.println("Your bill have 1 voucher");
            setDiscountCode(discountType.get(3)[0]);
            setDiscountAmount(Long.parseLong(discountType.get(3)[2]));

            // 50000000 < totalPayment
        } else if (order.getPaymentPriceDiscountByMembership() >= Long.parseLong(discountType.get(4)[1])) {
            System.out.println("Your bill have 1 voucher");
            setDiscountCode(discountType.get(4)[0]);
            setDiscountAmount(Long.parseLong(discountType.get(4)[2]));

        } else {
            discountCode = null;
        }


        if (!(discountCode == null)) {
            pw.println(customer.getcID() + "," + code + "," + discountCode + "," + discountAmount);
            pw.close();
        }
    }


    // Used to create unique discount code for customer
    public String validateDiscountCode(String id) {
        try {
            Scanner fileScanner = new Scanner(new File("./src/dataFile/ordersHistory.txt"));
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] helo = line.split(",");
                if (helo[0].equals(id)) {
                    Random random = new Random();
                    id = String.format("%03d", random.nextInt(999));
                    validateDiscountCode(id);
                } else {
                    this.discountCode = id;
                }
            }
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        }
        return this.discountCode;
    }


    // Display all the discount voucher that customer has
    public void displayCustomerDiscountCode() {
        ArrayList<String[]> discountCode = new ArrayList<>();

        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/customerDiscountCode.txt");

        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[0].equals(customer.getcID())) {
                discountCode.add(database.get(i));
            }
        }

        // If there is a discount code
        if (!(discountCode.size() == 0)) { // Print out discount codes if available

            // Setting up table
            CreateTable.setShowVerticalLines(true);
            CreateTable.setHeaders("OPTION", "DISCOUNT CODE", "DISCOUNT AMOUNT");

            // Adding content of found/available discount codes
            for (int i = 0; i < discountCode.size(); i++) {
                CreateTable.addRow(String.valueOf(i + 1), discountCode.get(i)[2], discountCode.get(i)[3]);
            }
            CreateTable.print();
            CreateTable.setHeaders(new String[0]);
            CreateTable.setRows(new ArrayList<String[]>());
        }
    }


    // Get all the product that have in that customer's cart
    public ArrayList<String[]> discountCodeList() {
        ArrayList<String[]> discountCode = new ArrayList<>();

        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/customerDiscountCode.txt");

        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[0].equals(customer.getcID()))
            /// If the system could find out the customer's ID in ordersHistory's file
            {
                discountCode.add(database.get(i));
            }
        }
        return discountCode;
    }

    // This method allow admin to delete a customer that had existed in customers' file
    public void deleteDiscountCode(String filepath, String id) throws IOException {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/customerDiscountCode.txt");
        ArrayList<String[]> newDatabase = new ArrayList<>();

        for (String[] strings : database) {
            if (!strings[1].equals(id)) {
                newDatabase.add(strings); // Add all customers except the deleted customer
            }
        }
        PrintWriter pw = new PrintWriter("./src/dataFile/customerDiscountCode.txt");

        pw.write(""); // The file would erase all the data in customers' file
        pw.close();

        for (String[] obj : newDatabase) {
            Write.rewriteFile(filepath, "#CID,ID,Discount code,Discount amount",
                    String.join(",", obj));
            // This method would allow system to write all data including new data into the customers' file
        }
    }


    // Getter method for discount code
    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    // Setter method for discount amount
    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }
}
