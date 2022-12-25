import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Order {
    private String oID;
    private double totalPayment;
    private String orderDate;
    private String orderStatus;
    private String deliveryStatus;

    public Order(String oID, double totalPayment, String orderDate, String orderStatus, String deliveryStatus) {
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
        String membership = customer.getCustomerType();
        Random rd = new Random();
        int i = rd.nextInt(999);
        oID = String.format("0%3d", i);
        totalPayment = product.getPrice();
        orderDate = new SimpleDateFormat("MM/dd/yyyy_HH:mm").format(Calendar.getInstance().getTime());
        double totalSpending = customer.setTotalSpending(customer.getTotalSpending() + totalPayment);
        orderStatus = "SUCCESSFUL";
        deliveryStatus = "DELIVERING";

        //Set discount level for each membership level
        switch (customer.getCustomerType()) {
            case "Silver":
                this.totalPayment = this.totalPayment * (1 - 0.05);
                break;
            case "Gold":
                this.totalPayment = this.totalPayment * (1 - 0.1);
                break;
            case "Platinum":
                this.totalPayment = this.totalPayment * (1 - 0.15);
                break;
            case "Regular":
                break;
        }

        pw.println("\n" + oID + "," + customerID + "," + productID + "," + membership + "," + totalPayment +
                "," + orderDate + "," + totalSpending + "," + orderStatus + "," + deliveryStatus);
        pw.close();

        getOrderInfo(customer);

    }

    public void getOrderInfo(Customer customer) throws IOException {

        ArrayList<String[]> orders = new ArrayList<>();

        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/ordersHistory.txt");
        System.out.println(database.get(1)[1]);
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[1].equals(customer.getcID()))
            /** If the system could find out the customer's ID in ordersHistory' file
             */ {
                orders.add(database.get(i));
            }
        }
        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("OID", "CID", "PID", "MEMBERSHIP", "TOTAL PAYMENT", "TIMESTAMP", "TOTAL SPENDING", "ORDER STATUS", "DELIVERING STATUS");
        for (int i = 0; i < orders.size(); i++) {
            createTable.addRow(orders.get(i)[0], orders.get(i)[1], orders.get(i)[2], orders.get(i)[3],
                    orders.get(i)[4], orders.get(i)[5], orders.get(i)[6], orders.get(i)[7], orders.get(i)[8]);
        }

        createTable.print();
    }
}
