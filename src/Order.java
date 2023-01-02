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
//        Long totalSpending = customer.setTotalSpending(customer.getTotalSpending() + paymentPrice);
        productSales(String.valueOf(product.getTitle()), quantity);
//        customer.updateTotalSpending("./src/customers.txt", String.valueOf(totalSpending), customer.getUserName());
//        customer.updateMembership("./src/customers.txt", customer.getUserName());
        String membership = customer.getCustomerType();
        orderDate = new SimpleDateFormat("MM/dd/yyyy_HH:mm").format(Calendar.getInstance().getTime());
        orderStatus = "SUCCESSFUL";
        deliveryStatus = "DELIVERING";

        pw.println(oID + "," + customerID + "," + membership + "," + productID + "," +
                singleUnitPrice + "," + quantity + "," + paymentPrice + "," + orderDate + "," + orderStatus + "," + deliveryStatus);
        pw.close();
        Cart cart = new Cart();
        cart.deleteItemInCart("./src/customerCart.txt", customer.getcID(), product);
    }

    public void getOrderInfo(Customer customer) {

        ArrayList<String[]> orders = new ArrayList<>();

        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/ordersHistory.txt");
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[1].equals(customer.getcID()))
                /* If the system could find out the customer's ID in ordersHistory's file
                 */ {
                orders.add(database.get(i));
            }
        }
        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("OID", "CID", "MEMBERSHIP", "PID", "SINGLE UNIT PRICE", "QUANTITY", "PAYMENT PRICE",
                "ORDER DATE", "ORDER STATUS", "DELIVERING STATUS");
        for (String[] order : orders) {
            createTable.addRow(order[0], order[1], order[2], order[3],
                    order[4], order[5], order[6], order[7], order[9], order[10]);
        }
        createTable.print();
    }

//    public void getOrderInfoById(String oID) {
//
//        ArrayList<String[]> orders = new ArrayList<>();
//
//        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/ordersHistory.txt");
//        for (int i = 1; i < database.size(); i++) {
//            if (database.get(i)[0].equals(oID))
//                /* If the system could find out the customer's ID in ordersHistory's file
//                 */ {
//                orders.add(database.get(i));
//            }
//        }
//        CreateTable createTable = new CreateTable();
//        createTable.setShowVerticalLines(true);
//        createTable.setHeaders("OID", "CID", "MEMBERSHIP", "PID", "SINGLE UNIT PRICE", "QUANTITY", "PAYMENT PRICE",
//                "ORDER DATE", "ORDER STATUS", "DELIVERING STATUS");
//        for (String[] order : orders) {
//            createTable.addRow(order[0], order[1], order[2], order[3],
//                    order[4], order[5], order[6], order[7], order[9], order[10]);
//        }
//        createTable.print();
//    }

    public void getTotalPaymentEachOrderId(Customer customer, String oID) throws IOException {
        ArrayList<String[]> orders = new ArrayList<>();

        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/ordersHistory.txt");
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[0].equals(oID))
                /* If the system could find out the customer's ID in ordersHistory's file
                 */ {
                orders.add(database.get(i));
            }
        }

        Long totalPayment = (long) 0;
        Long totalPaymentAfterDiscount = (long) 0;
        String membership = "";
        for (String[] order : orders) {
            totalPayment += (long) Integer.parseInt(order[6]);
            membership = order[2];
        }
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
                break;
        }
        Long totalSpending = customer.setTotalSpending(customer.getTotalSpending() + totalPaymentAfterDiscount);
        customer.updateTotalSpending("./src/customers.txt", String.valueOf(totalSpending), customer.getUserName());
        customer.updateMembership("./src/customers.txt", customer.getUserName());

        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("TOTAL PAYMENT", "MEMBERSHIP", "TOTAL PAYMENT AFTER DISCOUNT");
        createTable.addRow(String.valueOf(totalPayment), membership,
                String.valueOf(totalPaymentAfterDiscount));
        createTable.print();
        String cID = customer.getcID();
        String orderDate = new SimpleDateFormat("MM/dd/yyyy_HH:mm").format(Calendar.getInstance().getTime());
        PrintWriter pw;
        pw = new PrintWriter(new FileWriter("./src/billingHistory.txt", true));
        pw.println(oID + "," + cID + "," + totalPaymentAfterDiscount + "," + orderDate);
        pw.close();
    }

    public void getTotalPaymentAfterApplyDiscountCode(String oID, String id, Customer customer) throws IOException {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/customerDiscountCode.txt");
        ArrayList<String[]> orderInfo = ReadDataFromTXTFile.readAllLines("./src/billingHistory.txt");
        Long finalPrice = (long) 0;
        for (int i = 1; i < database.size(); i++) {
            for (int a = 0; a < orderInfo.size(); a++) {
                if (database.get(i)[1].equals(id)) {
                    if (orderInfo.get(a)[0].equals(oID)) {
                        long discountAmount = Long.parseLong(database.get(i)[3]);
                        orderInfo.get(a)[2] = String.valueOf(Long.parseLong(orderInfo.get(a)[2]) - discountAmount);
                        finalPrice = Long.parseLong(orderInfo.get(a)[2]);
                    }
                }
                PrintWriter pw = new PrintWriter("./src/billingHistory.txt");

                pw.write("");
                pw.close();

                for (String[] obj : orderInfo) {
                    Write.rewriteFile("./src/billingHistory.txt", "#OID,CID,Total payment after discount,Order date",
                            String.join(",", obj));
                }
            }
            Long totalSpending = customer.setTotalSpending(customer.getTotalSpending() + (finalPrice));
            customer.updateTotalSpending("./src/customers.txt", String.valueOf(totalSpending), customer.getUserName());
            customer.updateMembership("./src/customers.txt", customer.getUserName());
//            System.out.println(finalPrice);
            CreateTable createTable = new CreateTable();
            createTable.setShowVerticalLines(true);
            createTable.setHeaders("FINAL TOTAL PAYMENT");
            createTable.addRow(String.valueOf(finalPrice));
            createTable.print();

            Discount discount = new Discount();
            discount.deleteDiscountCode("./src/customerDiscountCode.txt", id);
        }
    }

    public void getAllOrderInfo() {
        ArrayList<String[]> orders = ReadDataFromTXTFile.readAllLines("./src/ordersHistory.txt");
        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("OID", "CID", "PID", "MEMBERSHIP", "TOTAL PAYMENT", "TIMESTAMP", "TOTAL SPENDING", "ORDER STATUS", "DELIVERING STATUS");
        for (int i = 1; i < orders.size(); i++) {
            createTable.addRow(orders.get(i)[0], orders.get(i)[1], orders.get(i)[2],
                    orders.get(i)[3], orders.get(i)[4], orders.get(i)[5],
                    orders.get(i)[6], orders.get(i)[7], orders.get(i)[8]);
        }
        createTable.print();
    }

    public String oIDDataForValidate(String oId) {
        try {
            Scanner fileScanner = new Scanner(new File("./src/ordersHistory.txt"));

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] helo = line.split(",");
                if (helo[0].equals(oId)) {
                    Random random = new Random();
                    oId = String.format("0%03d", random.nextInt(999));
                    oIDDataForValidate(oId);
                } else {
                    this.oID = oId;
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
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[1].equals(product)) {
                database.get(i)[2] = String.valueOf(Integer.parseInt(database.get(i)[2]) + quantity);
                File file = new File("./src/productsSold.txt");
                PrintWriter pw = new PrintWriter(file);
                pw.write("");
                pw.close();

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
            Scanner fileScanner = new Scanner(new File("./src/productsSold.txt"));

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] values = line.split(",");
                if (product.equals(values[1])) {
                    found = true;
                }
            }
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
        return found;
    }

    // If the checkProductSales return false, it will create a new line containing the pID of that product and start to log the amount of sales
    public void createNewProductSale(String product, int quantity) throws IOException {
        Path path = Paths.get("./src/productsSold.txt");
        int id = (int) Files.lines(path).count();
        PrintWriter writer = new PrintWriter(new FileWriter("./src/productsSold.txt", true));
        writer.print("\n" + id + "," + product + "," + quantity);
        writer.close();
    }

    public String getOrderDate() {
        return orderDate;
    }

}


