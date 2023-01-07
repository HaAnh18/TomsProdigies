import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

public class Order {
    // Attributes
    private String oID;
    private Long paymentPrice;
    private String orderDate;
    private String orderStatus;
    private String deliveryStatus;

    private String date;

    // Constructor
    public Order(String oID, Long paymentPrice, String orderDate, String orderStatus, String deliveryStatus) {
        this.oID = oID;
        this.paymentPrice = paymentPrice;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.deliveryStatus = deliveryStatus;
    }

    // Constructor
    public Order() {
    }

    public void createNewOrder(Customer customer, Product product, String oID, int quantity) throws IOException {
        PrintWriter pw;
        pw = new PrintWriter(new FileWriter("./src/ordersHistory.txt", true));

        // Assigning values to variables
        String customerID = customer.getcID();
        String productID = product.getID();
        Long singleUnitPrice = product.getPrice();
        String membership = customer.getCustomerType();
        orderDate = new SimpleDateFormat("MM/dd/yyyy_HH:mm").format(Calendar.getInstance().getTime());
        orderStatus = "SUCCESSFUL";
        deliveryStatus = "DELIVERING";

        // Getting payment price
        paymentPrice = product.getPrice() * quantity;

        // Log in product Sales based on the order
        productSales(String.valueOf(product.getTitle()), quantity);

        // Log order into orderHistory file
        pw.println(oID + "," + customerID + "," + membership + "," + productID + "," +
                singleUnitPrice + "," + quantity + "," + paymentPrice + "," + orderDate + "," + orderStatus + "," + deliveryStatus);
        pw.close();

        // Delete Item in cart after created an order
        Cart cart = new Cart();
        cart.deleteItemInCart("./src/customerCart.txt", customer.getcID(), product);
    }

    /* This method will help to get the order date out of ordersHistory.txt */
    public ArrayList<String[]> getOrderByDate() throws IOException, ParseException {

        ArrayList<String[]> dailyOrder = ReadDataFromTXTFile.readAllLines("./src/ordersHistory.txt");
        String[] dateAndTime = ReadDataFromTXTFile.readColString(7, "./src/ordersHistory.txt", ",");
        Scanner inputObj = new Scanner(System.in);
        Order order = new Order();
        System.out.println("Enter the date to get the daily order (MM/dd/yyyy):");
        String date = inputObj.nextLine();
        while (Admin.dateValidate(date)) /* validate if the timestamp is match to the user's input */ {
            System.out.println("Enter the date to get the daily order (MM/dd/yyyy):");
            date = inputObj.nextLine();

        }
        date = Admin.dateInput(date);
        do {
            order.getAllOrderInfo();
        }
        while (dateAndTime.equals(date));
        return dailyOrder;
    }

    public void searchOrder(String oId)
    // Searching the order by using order ID
    {
        ArrayList<String[]> orders = new ArrayList<>(); // Create a new arraylist to store order information

        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/ordersHistory.txt");
        // Read all line in ordersHistory.txt file and put all data in arraylist
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[0].equals(oId))
                /* If the system could find out the customer's ID in ordersHistory's file
                 */ {
                orders.add(database.get(i));
            }
        }
        if (!(orders.size() == 0)) {
            CreateTable createTable = new CreateTable();
            createTable.setShowVerticalLines(true);
            createTable.setHeaders("OID", "CID", "MEMBERSHIP", "PID", "SINGLE UNIT PRICE", "QUANTITY", "PAYMENT PRICE",
                    "ORDER DATE", "ORDER STATUS", "DELIVERING STATUS");
            /* Set header for the order information table */
            for (String[] order : orders) {
                createTable.addRow(order[0], order[1], order[2], order[3],
                        order[4], order[5], order[6], order[7], order[8], order[9]);
                /* Add information to each row in table */
            }
            createTable.print();
        } else {
            System.out.println("THERE IS NO ORDER HAVE THIS ID");
        }

    }

    public void getTotalPaymentEachOrderId(Customer customer, String oID) throws IOException {
        ArrayList<String[]> orders = new ArrayList<>();
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/ordersHistory.txt");

        // Loop through all ordersHistory
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[0].equals(oID)) { // Finds the corresponding order
                orders.add(database.get(i)); // Add order to ArrayList of orders
            }
        }

        Long totalPayment = (long) 0;
        Long totalPaymentAfterDiscount = (long) 0;
        String membership = "";

        // Adding in the total payment using each
        for (String[] order : orders) {
            totalPayment += (long) Integer.parseInt(order[6]);
            membership = order[2];
        }

        // Based on the membership assigned above, it will calculate totalPayment with discount
        switch (membership) {
            case "Silver":
                totalPaymentAfterDiscount = (long) (totalPayment * (1 - 0.05));
                break;
            case "Gold":
                totalPaymentAfterDiscount = (long) (totalPayment * (1 - 0.1));
                break;
            case "Platinum":
                totalPaymentAfterDiscount = (long) (totalPayment * (1 - 0.15));
                break;
            case "Regular":
                totalPaymentAfterDiscount = totalPayment;
                break;
        }

        // Calculate new totalSpending based on discount
        Long totalSpending = customer.setTotalSpending(customer.getTotalSpending() + totalPaymentAfterDiscount);

        // update methods for corresponding attributes
        customer.updateTotalSpending("./src/customers.txt", String.valueOf(totalSpending), customer.getUserName());
        customer.updateMembership("./src/customers.txt", customer.getUserName());

        // Setting up table and print out the before and after total payment with membership type
        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("TOTAL PAYMENT", "MEMBERSHIP", "TOTAL PAYMENT AFTER DISCOUNT");
        createTable.addRow(String.valueOf(totalPayment), membership,
                String.valueOf(totalPaymentAfterDiscount));
        createTable.print();


        // Get the corresponding info for order
        String cID = customer.getcID();
        String orderDate = new SimpleDateFormat("MM/dd/yyyy_HH:mm").format(Calendar.getInstance().getTime());

        // Update billingHistory with corresponding info
        PrintWriter pw;
        pw = new PrintWriter(new FileWriter("./src/billingHistory.txt", true));
        pw.println(oID + "," + cID + "," + totalPaymentAfterDiscount + "," + orderDate);
        pw.close();
    }

    public void getTotalPaymentAfterApplyDiscountCode(String oID, String id, Customer customer) throws IOException {
        // Reading from 2 different files to get the corresponding info with each other
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/customerDiscountCode.txt");
        ArrayList<String[]> orderInfo = ReadDataFromTXTFile.readAllLines("./src/billingHistory.txt");

        Long finalPrice = (long) 0;

        for (int i = 1; i < database.size(); i++) {
            for (String[] strings : orderInfo) {

                // Find the corresponding totalPayment
                if (database.get(i)[1].equals(id)) {

                    // Find the corresponding order using oID and calculate finalPrice (total payment)
                    if (strings[0].equals(oID)) {
                        long discountAmount = Long.parseLong(database.get(i)[3]);
                        strings[2] = String.valueOf(Long.parseLong(strings[2]) - discountAmount);
                        finalPrice = Long.parseLong(strings[2]);
                    }
                }
            }
        }
        PrintWriter pw = new PrintWriter("./src/billingHistory.txt");

        // Delete to get ready to rewrite
        pw.write("");
        pw.close();

        // Write all the orderInfo back with updated price for specific order
        for (String[] obj : orderInfo) {
            Write.rewriteFile("./src/billingHistory.txt", "#OID,CID,Total payment after discount,Order date",
                    String.join(",", obj));
        }

        // New totalSpending amount
        Long totalSpending = customer.setTotalSpending(customer.getTotalSpending() + (finalPrice));

        // Update TotalSpending and Membership for customer based on new totalSpending
        customer.updateTotalSpending("./src/customers.txt", String.valueOf(totalSpending), customer.getUserName());
        customer.updateMembership("./src/customers.txt", customer.getUserName());

        // Setting up table and adding content
        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("FINAL TOTAL PAYMENT");
        createTable.addRow(String.valueOf(finalPrice));
        createTable.print();

        Discount discount = new Discount();
        discount.deleteDiscountCode("./src/customerDiscountCode.txt", id);
    }

    public void getAllOrderInfo() {
        // Read all line in ordersHistory
        ArrayList<String[]> orders = ReadDataFromTXTFile.readAllLines("./src/ordersHistory.txt");
        CreateTable createTable = new CreateTable();

        // Setting up table
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("OID", "CID", "PID", "MEMBERSHIP", "TOTAL PAYMENT", "TIMESTAMP", "TOTAL SPENDING", "ORDER STATUS", "DELIVERING STATUS");

        // For each line, assign all content into table
        for (int i = 1; i < orders.size(); i++) {
            createTable.addRow(orders.get(i)[0], orders.get(i)[1], orders.get(i)[2],
                    orders.get(i)[3], orders.get(i)[4], orders.get(i)[5],
                    orders.get(i)[6], orders.get(i)[7], orders.get(i)[8]);
        }
        createTable.print();
    }

    public String oIDDataForValidate(String oId) {
        try {
            // Read each line in ordersHistory
            Scanner fileScanner = new Scanner(new File("./src/ordersHistory.txt"));
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
                    this.oID = oId;
                }
            }
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        }
        return this.oID;
    }

    public void getOrderInfoByCID(String cID) {
        ArrayList<String[]> orders = new ArrayList<>();

        // Read all orders in orderHistory
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/ordersHistory.txt");
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[1].equals(cID)) {
                orders.add(database.get(i)); //
            }
        }

        // If there is no order with the cID
        if (orders.size() == 0) {
            System.out.println("This customer does not make order yet!");
        } else {
            // Setting up table
            CreateTable createTable = new CreateTable();
            createTable.setShowVerticalLines(true);
            createTable.setHeaders("OID", "CID", "MEMBERSHIP", "PID", "SINGLE UNIT PRICE", "QUANTITY", "PAYMENT PRICE",
                    "ORDER DATE", "ORDER STATUS", "DELIVERING STATUS");

            // Add all the orders that have the corresponding cID
            for (String[] order : orders) {
                createTable.addRow(order[0], order[1], order[2], order[3],
                        order[4], order[5], order[6], order[7], order[8], order[9]);
            }
            createTable.print();
        }
    }


    // Everytime a product is bought or ordered it will log in the productsSold file
    public void productSales(String product, int quantity) throws IOException {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/productsSold.txt");
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[1].equals(product)) {
                database.get(i)[2] = String.valueOf(Integer.parseInt(database.get(i)[2]) + quantity);
                File file = new File("./src/productsSold.txt");
                PrintWriter pw = new PrintWriter(file);

                // Delete all content i file
                pw.write("");
                pw.close();

                // Rewrite file with new data
                for (String[] obj : database) {
                    Write.rewriteFile("./src/productsSold.txt", "#ID,Category,Quantity", String.join(",", obj));
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
            Scanner fileScanner = new Scanner(new File("./src/productsSold.txt"));
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
        Path path = Paths.get("./src/productsSold.txt");

        // Count existing lines in file
        int id = (int) Files.lines(path).count();
        PrintWriter writer = new PrintWriter(new FileWriter("./src/productsSold.txt", true));

        // Append new product based on product name and quantity
        writer.print("\n" + id + "," + product + "," + quantity);
        writer.close();
    }

    // Getter method for order date
    public String getOrderDate() {
        return orderDate;
    }

}


