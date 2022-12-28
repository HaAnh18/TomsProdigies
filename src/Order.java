import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

//        String membership = customer.getCustomerType();


        Random rd = new Random();
        int i = rd.nextInt(999);
        oID = oIDDataForValidate(String.format("0%03d", i));
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

    public String oIDDataForValidate(String oId) {
        try {
            Scanner fileScanner = new Scanner(new File("./src/ordersHistory.txt"));

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] helo = line.split(",");
                if (helo[0].equals(oId)) {
                    Random random = new Random();
                    oId = String.format("0%03d",random.nextInt(999));
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

    public String getoID() {
        return oID;
    }

    public void setoID(String oID) {
        this.oID = oID;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Long totalPayment) {
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


