package component;

import crud.CreateTable;
import crud.ReadDataFromTXTFile;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Order {
    private String oID;
    private Long totalPayment;
    private String orderDate;
    private String orderStatus;
    private String deliveryStatus;

    public Order(String oID, Long totalPayment, String orderDate, String orderStatus, String deliveryStatus) {
        this.oID = oID;
        this.totalPayment = totalPayment;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.deliveryStatus = deliveryStatus;
    }

    public Order() {
    }

    public void createNewOrder(Customer customer, Product product) throws IOException {
        PrintWriter pw;
        pw = new PrintWriter(new FileWriter("./src/ordersHistory.txt", true));

        String customerID = customer.getcID();
        String productID = product.getID();
        Random rd = new Random();
        int i = rd.nextInt(999);
        oID = String.format("0%03d", i);
        totalPayment = product.getPrice();
        //Set discount level for each membership level
        switch (customer.getCustomerType()) {
            case "Silver":
                this.totalPayment = (long)(this.totalPayment * (1 - 0.05));
                break;
            case "Gold":
                this.totalPayment = (long)(this.totalPayment * (1 - 0.1));
                break;
            case "Platinum":
                this.totalPayment = (long)(this.totalPayment * (1 - 0.15));
                break;
            case "Regular":
                break;
        }
        Long totalSpending = customer.setTotalSpending(customer.getTotalSpending() + totalPayment);
        customer.updateTotalSpending("./src/customers.txt", String.valueOf(totalSpending), customer.getUserName());
        customer.updateMembership("./src/customers.txt",customer.getUserName());
        String membership = customer.getCustomerType();
        orderDate = new SimpleDateFormat("MM/dd/yyyy_HH:mm").format(Calendar.getInstance().getTime());
        orderStatus = "SUCCESSFUL";
        deliveryStatus = "DELIVERING";

        pw.println( oID + "," + customerID + "," + productID + "," + membership + "," + totalPayment +
                "," + orderDate + "," + totalSpending + "," + orderStatus + "," + deliveryStatus);
        pw.close();

        getOrderInfo(customer);
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
        createTable.setHeaders("OID", "CID", "PID", "MEMBERSHIP", "TOTAL PAYMENT", "TIMESTAMP", "TOTAL SPENDING", "ORDER STATUS", "DELIVERING STATUS");
        for (String[] order : orders) {
            createTable.addRow(order[0], order[1], order[2], order[3],
                    order[4], order[5], order[6], order[7], order[8]);
        }
        createTable.print();
    }
}
