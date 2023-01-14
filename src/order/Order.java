package order;

import bonusFeatures.Cart;
import bonusFeatures.Discount;
import fileMethods.CreateTable;
import fileMethods.ReadDataFromTXTFile;
import fileMethods.Write;
import product.Product;
import users.Admin;
import users.Customer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

public class Order {
    // Attributes
    private String oID;
    ArrayList<Product> listOfProduct = new ArrayList<>();
    private Long paymentPriceBeforeDiscount;
    private Long paymentPriceDiscountByMembership;
    private String orderDate;
    private String orderStatus;
    private String deliveryStatus;
    private Long paymentPriceDiscountByVoucher;
    private Customer customer;


    public Order(String oID, String orderDate, String orderStatus, String deliveryStatus) {
        this.oID = oID;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.deliveryStatus = deliveryStatus;
    }

    // Constructor
    public Order(String oID, Customer customer) {
        this.oID = oID;
        this.customer = customer;
        this.paymentPriceBeforeDiscount = 0L;
        this.paymentPriceDiscountByMembership = 0L;
        this.paymentPriceDiscountByVoucher = 0L;
    }

    // Constructor
    public Order() {
    }

    public static String oIDDataForValidate(String oId) {
        try {
            // Read each line in ordersHistory
            Scanner fileScanner = new Scanner(new File("./src/dataFile/ordersHistory.txt"));
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] helo = line.split(",");

                // If an oID is the same as the one generated
                // It will generate a new oID and run it through the validation process again
                if (helo[0].equals(oId)) {
                    Random random = new Random();
                    oId = String.format("0%03d", random.nextInt(999));
                    oIDDataForValidate(oId);

                } else {    // The oID has no conflict and is able to be used
                    return oId;
                }
            }
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        }
        return oId;
    }

    public void createNewOrder(Product product, int quantity) throws IOException {
        PrintWriter pw;
        pw = new PrintWriter(new FileWriter("./src/dataFile/ordersHistory.txt", true));

        // Assigning values to variables
        String customerID = customer.getcID();
        String productID = product.getID();
        orderDate = new SimpleDateFormat("MM/dd/yyyy_HH:mm").format(Calendar.getInstance().getTime());
        orderStatus = "SUCCESSFUL";
        deliveryStatus = "DELIVERING";

        // Getting payment price
        paymentPriceBeforeDiscount += product.getPrice() * quantity;

        // Log in product Sales based on the order
        productSales(String.valueOf(product.getTitle()), quantity);

        // Log order into orderHistory file
        pw.println(oID + "," + customerID + "," + productID + "," +
                quantity + "," + orderDate + "," + orderStatus + "," + deliveryStatus);
        pw.close();

        // Delete Item in cart after created an order
        Cart cart = new Cart(customer);
        cart.deleteItemInCart("./src/dataFile/customerCart.txt", customer.getcID(), product);
    }

    public void searchOrder(String oId)
    // Searching the order by using order ID
    {
        ArrayList<String[]> orders = new ArrayList<>(); // Create a new arraylist to store order information
        ArrayList<String[]> listOfProduct = ReadDataFromTXTFile.readAllLines("./src/dataFile/items.txt");
        ArrayList<String[]> productInfo = new ArrayList<>();
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/ordersHistory.txt");
        // Read all line in ordersHistory.txt file and put all data in arraylist
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[0].equals(oId))
            // If the system could find out the customer's ID in ordersHistory's file
            {
                for (String[] strings : listOfProduct) {
                    if (strings[0].equals(database.get(i)[2])) {
                        productInfo.add(strings);
                    }
                }
                orders.add(database.get(i));
            }
        }
        if (!(orders.size() == 0)) {
            CreateTable.setShowVerticalLines(true);
            CreateTable.setHeaders("OID", "CID", "PID", "SINGLE UNIT PRICE", "QUANTITY", "PAYMENT PRICE",
                    "ORDER DATE", "ORDER STATUS", "DELIVERING STATUS");
            /* Set header for the order information table */
            for (int i = 0; i < orders.size(); i++) {
                Long totalPaymentEachProduct = Long.parseLong(productInfo.get(i)[2]) * Integer.parseInt(orders.get(i)[3]);
                CreateTable.addRow(orders.get(i)[0], orders.get(i)[1], orders.get(i)[2], productInfo.get(i)[2], orders.get(i)[3],
                        String.valueOf(totalPaymentEachProduct), orders.get(i)[4], orders.get(i)[5], orders.get(i)[6]);
                /* Add information to each row in table */
            }
//            }
            CreateTable.print();
            CreateTable.setHeaders(new String[0]);
            CreateTable.setRows(new ArrayList<String[]>());
        } else {
            System.out.println("THERE IS NO ORDER HAVE THIS ID");
        }
    }

    public void getTotalPaymentEachOrderId() throws IOException {
        ArrayList<String[]> orders = new ArrayList<>();
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/ordersHistory.txt");

        // Loop through all ordersHistory
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[0].equals(oID)) { // Finds the corresponding order
                orders.add(database.get(i)); // Add order to ArrayList of orders
            }
        }

//        Long totalPayment = (long) 0;
//        Long totalPaymentAfterDiscount = (long) 0;
//        String membership = "";

        // Adding in the total payment using each
//        for (String[] order : orders) {
//            totalPayment += (long) Integer.parseInt(order[6]);
//            membership = order[2];
//        }

        // Based on the membership assigned above, it will calculate totalPayment with discount
        switch (customer.getCustomerType()) {
            case "Silver":
                this.paymentPriceDiscountByMembership = (long) (paymentPriceBeforeDiscount * (1 - 0.05));
                break;
            case "Gold":
                this.paymentPriceDiscountByMembership = (long) (paymentPriceBeforeDiscount * (1 - 0.1));
                break;
            case "Platinum":
                this.paymentPriceDiscountByMembership = (long) (paymentPriceBeforeDiscount * (1 - 0.15));
                break;
            case "Regular":
                this.paymentPriceDiscountByMembership = paymentPriceBeforeDiscount;
                break;
        }

        // Calculate new totalSpending based on discount
        Long totalSpending = customer.setTotalSpending(customer.getTotalSpending() + getPaymentPriceDiscountByMembership());

        // update methods for corresponding attributes
        customer.updateTotalSpending("./src/dataFile/customers.txt", String.valueOf(totalSpending));
        customer.updateMembership("./src/dataFile/customers.txt");

        // Setting up table and print out the before and after total payment with membership type
        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("TOTAL PAYMENT", "MEMBERSHIP", "TOTAL PAYMENT AFTER DISCOUNT");
        CreateTable.addRow(String.valueOf(this.paymentPriceBeforeDiscount), customer.getCustomerType(),
                String.valueOf(this.paymentPriceDiscountByMembership));
        CreateTable.print();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());


        // Get the corresponding info for order
        String cID = customer.getcID();
        String orderDate = new SimpleDateFormat("MM/dd/yyyy_HH:mm").format(Calendar.getInstance().getTime());

        // Update billingHistory with corresponding info
        PrintWriter pw;
        pw = new PrintWriter(new FileWriter("./src/dataFile/billingHistory.txt", true));
        pw.println(oID + "," + cID + "," + this.paymentPriceDiscountByMembership + "," + orderDate);
        pw.close();
    }

    public void getTotalPaymentAfterApplyDiscountCode(String dId) throws IOException {
        // Reading from 2 different files to get the corresponding info with each other
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/customerDiscountCode.txt");
        ArrayList<String[]> orderInfo = ReadDataFromTXTFile.readAllLines("./src/dataFile/billingHistory.txt");

        Long finalPrice = (long) 0;

        for (int i = 1; i < database.size(); i++) {
            for (String[] strings : orderInfo) {

                // Find the corresponding totalPayment
                if (database.get(i)[1].equals(dId)) {

                    // Find the corresponding order using oID and calculate finalPrice (total payment)
                    if (strings[0].equals(oID)) {
                        long discountAmount = Long.parseLong(database.get(i)[3]);
                        this.setPaymentPriceDiscountByVoucher(Long.parseLong(strings[2]) - discountAmount);
                        strings[2] = String.valueOf(getPaymentPriceDiscountByVoucher());
                        finalPrice = Long.parseLong(strings[2]);
                    }
                }
            }
        }
        PrintWriter pw = new PrintWriter("./src/dataFile/billingHistory.txt");

        // Delete to get ready to rewrite
        pw.write("");
        pw.close();

        // Write all the orderInfo back with updated price for specific order
        for (String[] obj : orderInfo) {
            Write.rewriteFile("./src/dataFile/billingHistory.txt", "#OID,CID,Total payment after discount,order.Order date",
                    String.join(",", obj));
        }

        // New totalSpending amount
        Long totalSpending = customer.setTotalSpending(customer.getTotalSpending() + (finalPrice));

        // Update TotalSpending and Membership for customer based on new totalSpending
        customer.updateTotalSpending("./src/dataFile/customers.txt", String.valueOf(totalSpending));
        customer.updateMembership("./src/dataFile/customers.txt");

        // Setting up table and adding content
        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("FINAL TOTAL PAYMENT");
        CreateTable.addRow(String.valueOf(finalPrice));
        CreateTable.print();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());

        Discount discount = new Discount();
        discount.deleteDiscountCode("./src/dataFile/customerDiscountCode.txt", dId);
    }

    public void getAllOrderInfo() {
        // Read all line in ordersHistory
        ArrayList<String[]> orders = ReadDataFromTXTFile.readAllLines("./src/dataFile/ordersHistory.txt");

        // Setting up table
        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("OID", "CID", "PID", "QUANTITY", "ORDER DATE", "ORDER STATUS", "DELIVERING STATUS");

        // For each line, assign all content into table
        for (int i = 1; i < orders.size(); i++) {
            CreateTable.addRow(orders.get(i)[0], orders.get(i)[1], orders.get(i)[2],
                    orders.get(i)[3], orders.get(i)[4], orders.get(i)[5],
                    orders.get(i)[6]);
        }
        CreateTable.print();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
    }

    public void getAllBillingHistory() {
        ArrayList<String[]> orders = ReadDataFromTXTFile.readAllLines("./src/dataFile/billingHistory.txt");

        // Setting up table
        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("OID", "CID", "TOTAL PAYMENT AFTER DISCOUNT", "ORDER DATE");

        // For each line, assign all content into table
        for (int i = 1; i < orders.size(); i++) {
            CreateTable.addRow(orders.get(i)[0], orders.get(i)[1], orders.get(i)[2],
                    orders.get(i)[3]);
        }
        CreateTable.print();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
    }

    public void getOrderInfoByCID(String cID) {
        ArrayList<String[]> orders = new ArrayList<>();

        // Read all orders in orderHistory
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/ordersHistory.txt");
        ArrayList<String[]> listOfProduct = ReadDataFromTXTFile.readAllLines("./src/dataFile/items.txt");
        ArrayList<String[]> productInfo = new ArrayList<>();
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[1].equals(cID)) {
                for (String[] strings : listOfProduct) {
                    if (strings[0].equals(database.get(i)[2])) {
                        productInfo.add(strings);
                    }
                }
                orders.add(database.get(i)); //
            }
        }

        // If there is no order with the cID
        if (orders.size() == 0) {
            System.out.println("THIS CUSTOMER DOES NOT MAKE ORDER YET!");
        } else {
            // Setting up table
            CreateTable.setShowVerticalLines(true);
            CreateTable.setHeaders("OID", "CID", "PID", "SINGLE UNIT PRICE", "QUANTITY", "PAYMENT PRICE",
                    "ORDER DATE", "ORDER STATUS", "DELIVERING STATUS");

            // Add all the orders that have the corresponding cID
            for (int i = 0; i < orders.size(); i++) {
                Long totalPaymentEachProduct = Long.parseLong(productInfo.get(i)[2]) * Integer.parseInt(orders.get(i)[3]);
                CreateTable.addRow(orders.get(i)[0], orders.get(i)[1], orders.get(i)[2], productInfo.get(i)[2], orders.get(i)[3],
                        String.valueOf(totalPaymentEachProduct), orders.get(i)[4], orders.get(i)[5], orders.get(i)[6]);
                /* Add information to each row in table */
            }
            CreateTable.print();
            CreateTable.setHeaders(new String[0]);
            CreateTable.setRows(new ArrayList<String[]>());
        }
    }


    // Everytime a product is bought or ordered it will log in the productsSold file
    public void productSales(String product, int quantity) throws IOException {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/productsSold.txt");
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[1].equals(product)) {
                database.get(i)[2] = String.valueOf(Integer.parseInt(database.get(i)[2]) + quantity);
                File file = new File("./src/dataFile/productsSold.txt");
                PrintWriter pw = new PrintWriter(file);

                // Delete all content i file
                pw.write("");
                pw.close();

                // Rewrite file with new data
                for (String[] obj : database) {
                    Write.rewriteFile("./src/dataFile/productsSold.txt", "#ID,Category,Quantity", String.join(",", obj));
                }
            }
        }
        if (!checkProductSales(product)) {
            createNewProductSale(product, quantity);
        }
    }

    // This will read the productsSold file and check for existing product
    public boolean checkProductSales(String product) {
        boolean found = false;
        try {
            // Read lines in the file
            Scanner fileScanner = new Scanner(new File("./src/dataFile/productsSold.txt"));
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] values = line.split(",");

                // Check whether a product already existed in the file
                if (product.equals(values[1])) {
                    found = true;
                }
            }
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
        return found;
    }


    // If the checkProductSales return false, it will create a new line containing the name of that product and start to log the amount of sales
    public void createNewProductSale(String product, int quantity) throws IOException {
        Path path = Paths.get("./src/dataFile/productsSold.txt");

        // Count existing lines in file
        int id = (int) Files.lines(path).count();
        PrintWriter writer = new PrintWriter(new FileWriter("./src/dataFile/productsSold.txt", true));

        // Append new product based on product name and quantity
        writer.print("\n" + id + "," + product + "," + quantity);
        writer.close();
    }

    /* This method will help to get the order date out of ordersHistory.txt */
    public ArrayList<String[]> getOrderByDate() {

        ArrayList<String[]> orderList = ReadDataFromTXTFile.readAllLines("./src/dataFile/ordersHistory.txt");
        ArrayList<String[]> dailyOrder = new ArrayList<>();
        Scanner inputObj = new Scanner(System.in);
        System.out.println("Enter the date to get the daily order (MM/dd/yyyy):");
        String date = inputObj.nextLine();
        while (Admin.dateValidate(date)) /* validate if the timestamp is match to the user's input */ {
            System.out.println("Enter the date to get the daily order (MM/dd/yyyy):");
            date = inputObj.nextLine();

        }
        date = Admin.dateInput(date);
        for (int i = 1; i < orderList.size(); i++) {
            String[] dateSplit = orderList.get(i)[4].split("_");
            String splitDate = dateSplit[0].replaceAll("//s", "");
            splitDate = Admin.dateInput(splitDate);
            if (splitDate.equals(date)) {
                dailyOrder.add(orderList.get(i));
            }
        }
        return dailyOrder;
    }

    public void printOrder(ArrayList<String[]> dailyOrder) {
        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("OID", "CID", "PID", "QUANTITY",
                "ORDER DATE", "ORDER STATUS", "DELIVERING STATUS");

        // Add all the orders that have the corresponding cID
        for (String[] order : dailyOrder) {
            CreateTable.addRow(order[0], order[1], order[2], order[3],
                    order[4], order[5], order[6]);
        }
        CreateTable.print();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
    }

    public void updateDeliveryStatus(String filepath, String newData, String oID) throws IOException
    // This method allow admin to modify a delivery status of order that had existed in items' file
    {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/ordersHistory.txt");

        for (String[] strings : database) {
            if (strings[0].equals(oID))
                /* If the system could find out the oID in ordersHistory's file
                 * then the system allow admin to update the order's delivery status
                 */ {
                strings[6] = newData.toUpperCase(); // Modify the order's delivery status
            }
        }
        File file = new File(filepath);
        PrintWriter pw = new PrintWriter(file);

        pw.write(""); // The file would erase all the data in items' file
        pw.close();

        for (String[] strings : database) {
            Write.rewriteFile(filepath, "#OID,CID,PID,Quantity,Order date,Order status,Delivery status", String.join(",", strings));
            // This method would allow system to write all data including new data into the items' file
        }
    }

    public String getoID() {
        return oID;
    }

    public void setoID(String oID) {
        this.oID = oID;
    }

    public Long getPaymentPriceBeforeDiscount() {
        return paymentPriceBeforeDiscount;
    }

    public void setPaymentPriceBeforeDiscount(Long paymentPriceBeforeDiscount) {
        this.paymentPriceBeforeDiscount = paymentPriceBeforeDiscount;
    }

    public Long getPaymentPriceDiscountByMembership() {
        return paymentPriceDiscountByMembership;
    }

    public void setPaymentPriceDiscountByMembership(Long paymentPriceDiscountByMembership) {
        this.paymentPriceDiscountByMembership = paymentPriceDiscountByMembership;
    }

    public Long getPaymentPriceDiscountByVoucher() {
        return paymentPriceDiscountByVoucher;
    }

    public void setPaymentPriceDiscountByVoucher(Long paymentPriceDiscountByVoucher) {
        this.paymentPriceDiscountByVoucher = paymentPriceDiscountByVoucher;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}


