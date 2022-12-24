import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Orders {
    //Attributes
    private String orderId;
    private double totalPayment;
    private String orderDate;
    private String orderStatus;
    private String deliveryStatus;

    //Constructor
    public Orders(String orderId, double totalSpending, String orderDate, String orderStatus, String deliveryStatus) {
        this.orderId = orderId;
        this.totalPayment = totalSpending;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.deliveryStatus = deliveryStatus;
    }
    public Orders(){

    }

    //Create an order
    public void createNewOrder(Customer customer, Product product) throws IOException{
        PrintWriter pw;
        pw = new PrintWriter(new FileWriter("./src/ordersHistory.txt", true));
        String customerID = customer.getcID();
        String productID = product.getID();
        String membership = customer.getCustomerType();
        Random rd = new Random();
        int i = rd.nextInt(999);
        orderId = String.format("0%5d",i);
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

        pw.println("\n" + orderId + "," + customerID + "," + productID + "," + membership + "," + totalPayment +
                "," + orderDate + "," + totalSpending + "," + orderStatus + "," + deliveryStatus);
        pw.close();

        getOrderInfo(customer);

    }

    // method that get information on orders
    private void getOrderInfo(Customer customer) throws  IOException{
            ArrayList<String[]> orders = new ArrayList<>();
            ArrayList<String[]> data = ReadDataFromTXTFile.readAllLines("./src/ordersHistory.txt");
            System.out.println(data.get(1)[1]);
            for (int i = 1; i < data.size(); i++) {
                if (data.get(i)[1].equals(customer.getcID())){
                //Validate customer information
                    orders.add(data.get(i));
                }
            }
            CreateTable createTable = new CreateTable();
            createTable.setShowVerticalLines(true);
            createTable.setHeaders("ORDER_ID", "CUSTOMER_ID", "PRODUCT_ID", "MEMBERSHIP", "TOTAL PAYMENT", "ORDER_DATE", "TOTAL SPENDING", "ORDER STATUS", "DELIVERING STATUS");
        for (String[] order : orders) {
            createTable.addRow(order[0], order[1], order[2], order[3], order[4], order[5], order[6], order[7], order[8]);
        }

            createTable.print();
        }

    //getter and setter
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
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
