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

        String membership = customer.getCustomerType();


        Random rd = new Random();
        int i = rd.nextInt(999);
        oID = String.format("0%3d", i);


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

    public String getoID() {
        return oID;
    }

    public void setoID(String oID) {
        this.oID = oID;
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

    //

//    public void getOrderId(Order order) throws  IOException {
//
//        ArrayList<Integer[]> orderID = new ArrayList<>();
//
//        ArrayList<Integer[]> database2 = ReadDataFromTXTFile.readColInt(0,"./src/ordersHistory.txt",",");
//        System.out.println(database2.get(1)[1]);
//        for (int i=1;i < database2.size();i++){
//            if(database2.get(i)[1].equals(order.getoID())){
//
//                orderID.add(database2.get(i));
//
//            }
//        }
//        CreateTable cT = new CreateTable();
//        cT.setShowVerticalLines(true);
//        cT.setHeaders("OID");
//
//        for (Integer[] position : orderID){
//            cT.addRow(String.valueOf(position[0]));
//        }
//        cT.print();
//    }

    // testing this method: this method will read 1 specific column of a text file which is oID column in ordersHistory
    // and store it into an array of arrayList
    public String oIDDataForValidate(String oId) {
//        String newoId = null;
        try {
            ArrayList<String> helo = new ArrayList<String>();
            BufferedReader reader = new BufferedReader(new FileReader("./src/OIDCheck.txt"));
            Pattern pattern = Pattern.compile("^([A-Z0-9]{3,4}).*");
            // "^" mean at the beginning of the string
            //"[A-Z0-9]" mean any character between A and Z , any number between 0-9
            //"{3,4}" mean between 3 and 4 time ( matching the most amount possible)

            reader.readLine(); // repeat as necessary to skip headers
            while (reader.ready()) {
                String line = reader.readLine();
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    helo.add(matcher.group(1));
                }
            }

//            for (String helos : helo) {
//                System.out.println(helos);
//            }
            for (int i = 0; i < helo.size(); i++) {
                if (helo.get(i).equals(oId)) {
                    Random random = new Random();
                    oId = String.valueOf(random.nextInt(999));
                    oIDDataForValidate(oId);
                } else {

                    this.oID = oId;
                }
            }
        } catch (FileNotFoundException err) {
            System.out.println("File not found");
        } catch (IOException err) {
            System.out.println("IO error");
        }
        return this.oID;
    }


    // this method will copy 1 column from ordersHistory which is oID column
    // and write that specific column in to another .txt file
    public static void onlyOID() throws FileNotFoundException {
        Scanner scanner =new Scanner(new File("./src/ordersHistory.txt"));

        //reading the first line, always hava header
        String nextLine = scanner.nextLine();
        String regex = ",";//break on any ","

        //
        String[] header = nextLine.split(regex);
        //this printing the column oID into a new file
        try (PrintWriter pw = new PrintWriter(new FileWriter("src/OIDCheck.txt", true))) {
            pw.println(Arrays.toString(new String[]{header[0]}));
            //reading the row
            while (scanner.hasNext()) {
                String[] row = scanner.nextLine().split(regex);
                //printing  rows in a specific column into a text file
                pw.println(Arrays.toString(new String[]{row[0]}));
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }



    //compare a file with ArrayList to check for similarity

}
