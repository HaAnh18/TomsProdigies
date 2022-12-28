import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class Order {
    private String oID;
    private Long paymentPrice;
    private String orderDate;
    private String orderStatus;
    private String deliveryStatus;

    public Order(String oID, Long paymentPrice, String orderDate, String orderStatus, String deliveryStatus) {
        this.oID = oID;
        this.paymentPrice = paymentPrice;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.deliveryStatus = deliveryStatus;
    }

    public Order() {
    }

    public void createNewOrder(Customer customer, Product product, String oID, int quantity) throws IOException {
        PrintWriter pw;
        pw = new PrintWriter(new FileWriter("./src/ordersHistory.txt", true));

        String customerID = customer.getcID();
        String productID = product.getID();
        Long singleUnitPrice = product.getPrice();
        paymentPrice = product.getPrice() * quantity;
        Long totalSpending = customer.setTotalSpending(customer.getTotalSpending() + paymentPrice); // Updating the total spending value by adding in the paymentPrice total
        productSales(String.valueOf(product), quantity); // Adding a sales value depending on the user order
        customer.updateTotalSpending("./src/customers.txt", String.valueOf(totalSpending), customer.getUserName());
        customer.updateMembership("./src/customers.txt",customer.getUserName()); // Update the corresponding user membership by checking their total spending
        String membership = customer.getCustomerType();// Assign new membership type
        orderDate = new SimpleDateFormat("MM/dd/yyyy_HH:mm").format(Calendar.getInstance().getTime());
        orderStatus = "SUCCESSFUL";
        deliveryStatus = "DELIVERING";

        pw.println( oID + "," + customerID +"," + membership + "," + productID + "," +
                singleUnitPrice +"," + quantity + ","+ paymentPrice + "," + orderDate + "," + totalSpending + "," + orderStatus + "," + deliveryStatus);
        pw.close();
    }

    public void getOrderInfo(Customer customer) {

        ArrayList<String[]> orders = new ArrayList<>();
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/ordersHistory.txt"); // Read all lines in file

        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[1].equals(customer.getcID()))
                /* If the system could find out the customer's ID in ordersHistory's file
                 */ {
                orders.add(database.get(i));
            }
        }
        // Setting up the table
        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("OID", "CID", "MEMBERSHIP", "PID", "SINGLE UNIT PRICE", "QUANTITY", "PAYMENT PRICE",
                "ORDER DATE", "ORDER STATUS", "DELIVERING STATUS");

        // Adding the corresponding information into the createTable object
        for (String[] order : orders) {
            createTable.addRow(order[0], order[1], order[2], order[3],
                    order[4], order[5], order[6], order[7], order[9], order[10]);
        }
        createTable.print();
    }

    public void getOrderInfoById(String oID) {
        // Print out the order info by finding the corresponding oID
        ArrayList<String[]> orders = new ArrayList<>();

        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/ordersHistory.txt");
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[0].equals(oID))
                /* If the system could find out the oID
                 */ {
                orders.add(database.get(i));
            }
        }
        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("OID", "CID", "MEMBERSHIP", "PID", "SINGLE UNIT PRICE", "QUANTITY", "PAYMENT PRICE",
                "ORDER DATE", "ORDER STATUS", "DELIVERING STATUS");
        // Adding corresponding info
        for (String[] order : orders) {
            createTable.addRow(order[0], order[1], order[2], order[3],
                    order[4], order[5], order[6], order[7], order[9], order[10]);
        }
        createTable.print();
    }

    public void getTotalPaymentEachOrderId(Customer customer, String oID) throws IOException {
        // Print out the total payment needed for that oID and their total payment after membership discount with the corresponding membership
        ArrayList<String[]> orders = new ArrayList<>();

        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/ordersHistory.txt");
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[0].equals(oID))
                /* If the system could find out the orderID
                 */ {
                orders.add(database.get(i));
            }
        }

        // Initialise variables for calculations and a blank String to later display membership status
        Long totalPayment = (long) 0;
        Long totalPaymentAfterDiscount = (long) 0;
        String membership = "";

        // For each item in the order
        for (String[] order : orders) {
            totalPayment += (long) Integer.parseInt(order[6]); // Calculate total payment
            membership = order[2]; // Determine current membership for discount
        }
        switch (membership) { // A discount level is selected based on determined membership
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
                break;
        }

        // Setting up the table
        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("TOTAL PAYMENT","MEMBERSHIP","TOTAL PAYMENT AFTER DISCOUNT");
        createTable.addRow(String.valueOf(totalPayment),membership,
                String.valueOf(totalPaymentAfterDiscount));

        // Print table
        createTable.print();

        // Create a new line in billingHistory file for each order
        String cID = customer.getcID();
        String orderDate = new SimpleDateFormat("MM/dd/yyyy_HH:mm").format(Calendar.getInstance().getTime());
        PrintWriter pw;
        pw = new PrintWriter(new FileWriter("./src/billingHistory.txt", true));
        pw.println(oID + "," + cID + "," + totalPaymentAfterDiscount + "," + orderDate);
        pw.close();
    }

    public void getAllOrderInfo() { // display all the orders in ordersHistory file
        ArrayList<String[]> orders = ReadDataFromTXTFile.readAllLines("./src/ordersHistory.txt");

        // Setting up the table
        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("OID", "CID", "PID", "MEMBERSHIP", "TOTAL PAYMENT", "TIMESTAMP", "TOTAL SPENDING", "ORDER STATUS", "DELIVERING STATUS");

        // Adding all the corresponding info with their respective indexes
        for (int i=1; i<orders.size();i++) {
            createTable.addRow(orders.get(i)[0], orders.get(i)[1], orders.get(i)[2],
                    orders.get(i)[3], orders.get(i)[4], orders.get(i)[5],
                    orders.get(i)[6], orders.get(i)[7], orders.get(i)[8]);
        }
        createTable.print();
    }



    public String oIDDataForValidate(String oId) { // This method is used to validate oID to avoid duplicate oID

        try {
            Scanner fileScanner = new Scanner(new File("./src/ordersHistory.txt"));

            // Loop through orderHistory file for oIDs
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] helo = line.split(",");

                // If there is an existing oID
                if (helo[0].equals(oId)) {
                    Random random = new Random();
                    oId = String.format("0%03d",random.nextInt(999));
                    oIDDataForValidate(oId); // A new one is created and given back for validation
                } else {
                    this.oID = oId; // If there is no existing oID the oID created is accepted
                }
            }
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        }
        return this.oID;
    }


    /* This method will help to get the order date out of ordersHistory.txt */
    public static ArrayList<Order> getOrderByDate(String date){
        ArrayList<Order> dailyOrder = new ArrayList<>();
        for (Order strings : Order.getOrderByDate(date)) {
            String orderDate = strings.getOrderDate();
            if (date.equalsIgnoreCase(orderDate)) {
                dailyOrder.add(strings);
            }
        }
        return dailyOrder;}

    // Everytime a product is bought or ordered it will log in the productsSold file
    public void productSales(String product, int quantity) throws IOException {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/productsSold.txt");

        // Loop through the productsSold file to find the corresponding product
        for (int i = 1; i < database.size(); i++) {

            // If found will start to change the quantities value based on ordered item
            if (database.get(i)[1].equals(product)) {

                // New value is calculate by adding in the new quantities that was ordered
                database.get(i)[2] = String.valueOf(Integer.parseInt(database.get(i)[2]) + (1 * quantity));
                File file = new File("./src/productsSold.txt");
                PrintWriter pw = new PrintWriter(file);
                pw.write("");
                pw.close();

                // A new list that contains the old + new data
                ArrayList<String[]> newDatabase = database;

                // Overide old file with new data
                for (String[] obj : newDatabase) {
                    Write.rewriteFile("./src/productsSold.txt", "#ID,Category,Quantity", String.join(",", obj));
                }
            }
        }
        // Check whether the product existed or not if not will initiate createNewProductSale function and add that product sales in
        if (!checkProductSales(product)) {
            createNewProductSale(product, quantity);
        }
    }

    // This will read the productsSold file and check for existing product
    public boolean checkProductSales(String product) {
        boolean found = false; //
        try {
            Scanner fileScanner = new Scanner(new File("./src/productsSold.txt"));

            // Read each line in the productsSold file
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] values = line.split(","); // Split up element in ArrayList
                if (product.equals(values[1])) { // If a product already has logged sale it will return true
                    found = true;
                }
            }
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
        return found;
    }


    public void createNewProductSale(String product, int quantity) throws IOException { // If the checkProductSales return false, it will create
                                                                                        // a new line containing the pID of that product and start to log the amount of sales

        Path path = Paths.get("./src/productsSold.txt"); // Declare file path
        int id = (int) Files.lines(path).count();
        PrintWriter writer = new PrintWriter(new FileWriter("./src/productsSold.txt", true)); // printwriter use to append a new product
        writer.print("\n" + id + "," + product + "," + (1 * quantity)); // adding a product info and amount that was ordered
        writer.close();
    }

    public String getoID() {
        return oID;
    }

    public void setoID(String oID) {
        this.oID = oID;
    }

    public double getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(Long paymentPrice) {
        this.paymentPrice = paymentPrice;
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


        }


