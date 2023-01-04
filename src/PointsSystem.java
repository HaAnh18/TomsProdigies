import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class PointsSystem {

    public static void pointsConversion(String cID, String oID) throws IOException
    // Update total points earned for customer after he/she finished every order
    {
        ArrayList<String[]> paymentConversion = ReadDataFromTXTFile.readAllLines("./src/billingHistory.txt");
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/customers.txt");
        long newData;
        Long newPoints = null;

        // Read all line in billingHistory.txt file and take out total payment
        for (int j = 1; j < paymentConversion.size(); j++) {
            if (paymentConversion.get(j)[0].equals(oID)) { // Check for similar cID
                newData = (Long.parseLong(paymentConversion.get(j)[2])); // Assigning a variable as the value of total payment
                newPoints = newData / 100000; // Calculate points (1 point per 100000VND spent)
            }
            // Read all line in customers.txt file and put all data in arraylist
        }

        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[0].equals(cID)) {
                /* If the system could find out the username in customers' file
                 * then the system update their information
                 */
                long appendPoints = (Long.parseLong(database.get(i)[9]) + newPoints);
                database.get(i)[9] = (String.valueOf(appendPoints));  // The customer's information is changed (assigning new points)
            }
        }
        File file = new File("./src/customers.txt");
        PrintWriter pw = new PrintWriter(file);

        pw.write(""); // The file would erase all the data in customers' file
        pw.close();

        for (String[] obj : database) {
            Write.rewriteFile("./src/customers.txt", "#ID,Name,Email,Address,Phone,Membership,Username,Password,Total Spending,Total Points",
                    String.join(",", obj));
            // This method would allow system to write all data including new data into the customers' file
        }
    }

    public static void viewPrizes() throws IOException {
        ArrayList<String[]> prizeItems = new ArrayList<>();
        Scanner fileProducts = new Scanner(new File("./src/prizeItems.txt"));
        while (fileProducts.hasNext()) { // Read all lines in .txt file
            String[] productData;
            String line = fileProducts.nextLine();
            StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
            String ID = stringTokenizer.nextToken();
            String title = stringTokenizer.nextToken();
            String price = stringTokenizer.nextToken();
            String category = stringTokenizer.nextToken();
            productData = new String[]{ID, title, price, category}; // Put all split String into an Array
            prizeItems.add(productData); // Add that Array into an ArrayList
        }

        // Set up table
        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("OPTION", "ID", "TITLE", "POINTS", "CATEGORY");

        // Add all lines into table with options
        for (int i = 1; i < prizeItems.size(); i++) {
            createTable.addRow(String.valueOf(i), prizeItems.get(i)[0], prizeItems.get(i)[1], prizeItems.get(i)[2], prizeItems.get(i)[3]);
        }
        createTable.print();
        System.out.println("Please enter an option to exchange: ");
    }

    public static void exchangeItem(String user, String itemID) throws IOException {
        // Update total points earned for customer after he/she finished every order
        ArrayList<String[]> pointCost = ReadDataFromTXTFile.readAllLines("./src/prizeItems.txt");
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/customers.txt");
        Long newData = null;
        Long newPoints;
        Long pointWallet;
        boolean completeExchange = false;

        // Read all line in prizeItems.txt file and take out pointCost
        for (int j = 1; j < pointCost.size(); j++) {
            if (pointCost.get(j)[0].equals(itemID)) { // Check for similar itemID
                newData = (Long.parseLong(pointCost.get(j)[2])); // Assigning a variable as the value of prizePoints
            }
        }

        // Read all line in customers.txt file and put all data in arraylist
        for (int i = 1; i < database.size(); i++) {
            pointWallet = Long.parseLong(database.get(i)[9]);
            if (database.get(i)[0].equals(user) && pointWallet >= newData) {
                /* If the system could find out the username in customers' file
                 * then the system update their information
                 */
                newPoints = pointWallet - newData; // Deduct points
                database.get(i)[9] = (String.valueOf(newPoints));  // The customer's information is changed (assigning new points)

                completeExchange = true;
            } else if (database.get(i)[0].equals(user) && !(pointWallet >= newData)) {
                System.out.println("You don't have enough points to exchange for that product!");
            }
        }

        if (completeExchange) {
            File file = new File("./src/customers.txt");
            PrintWriter pw = new PrintWriter(file);

            pw.write(""); // The file would erase all the data in customers' file
            pw.close();

            for (String[] obj : database) {
                Write.rewriteFile("./src/customers.txt", "#ID,Name,Email,Address,Phone,Membership,Username,Password,Total Spending,Total Points",
                        String.join(",", obj));
                // This method would allow system to write all data including new data into the customers' file
            }

            logExchange(user, itemID, String.valueOf(newData));
        }
    }

    public static void logExchange(String user, String itemID, String Cost) throws IOException {
        Order order = new Order();
        PrintWriter pw = new PrintWriter(new FileWriter("./src/prizeExHistory.txt", true));

        // log Exchange date and assign status and pickup notifications
        String exchangeDate = new SimpleDateFormat("MM/dd/yyyy_HH:mm").format(Calendar.getInstance().getTime());
        String exchangeStatus = "SUCCESSFUL";
        String pickupStatus = "PENDING";

        // Create oID for each exchange
        Random rd = new Random();
        int randNum = rd.nextInt(999);
        String oID = order.oIDDataForValidate(String.format("O%03d", randNum));

        pw.println(oID + "," + user + "," + itemID + "," + Cost + ","
                + exchangeDate + "," + exchangeStatus + "," + pickupStatus);
        pw.close();

        PointsSystem.printConfirmation(oID);
    }

    public static void printConfirmation(String oID) throws IOException {
        // Set up table
        CreateTable table = new CreateTable();
        table.setShowVerticalLines(true);
        table.setHeaders("Order ID", "cID", "prizeID", "Point Cost", "Date Exchanged", "Exchange Status", "Pickup Status");

        // Get line in .txt file with parameter oID
        String[] exchangeConfirmation = ReadDataFromTXTFile.readSpecificLine(oID, 0, "./src/prizeExHistory.txt", ",");

        // Add content to table
        table.addRow(exchangeConfirmation[0], exchangeConfirmation[1], exchangeConfirmation[2], exchangeConfirmation[3],
                exchangeConfirmation[4], exchangeConfirmation[5], exchangeConfirmation[6]);

        // Print out the table
        table.print();
    }

    public static void getExchangeInfo(String cID) {
        ArrayList<String[]> orders = new ArrayList<>();

        // Read all orders in prizeExHistory
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/prizeExHistory.txt");
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[1].equals(cID)) {
                orders.add(database.get(i)); //
            }
        }

        // If there is no exchange with the cID
        if (orders.size() == 0) {
            System.out.println("This customer did not make an exchange yet!");
        } else {

            // Setting up table
            CreateTable createTable = new CreateTable();
            createTable.setShowVerticalLines(true);
            createTable.setHeaders("OID", "CID", "PID", "POINT COSTS",
                    "EXCHANGE DATE", "EXCHANGE STATUS", "PICKUP STATUS");

            // Add all the exchangeHistory that have the corresponding cID
            for (String[] order : orders) {
                createTable.addRow(order[0], order[1], order[2], order[3],
                        order[4], order[5], order[6]);
            }
            createTable.print();
        }
    }

    public static void viewExchangeLog() {
        // Read all line in prizeExHistory
        ArrayList<String[]> orders = ReadDataFromTXTFile.readAllLines("./src/prizeExHistory.txt");
        CreateTable createTable = new CreateTable();

        // Setting up table
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("OID", "CID", "PID", "POINTS COST", "DATE EXCHANGED", "EXCHANGE STATUS", "PICKUP STATUS");

        // For each line, assign all content into table
        for (int i = 1; i < orders.size(); i++) {
            createTable.addRow(orders.get(i)[0], orders.get(i)[1], orders.get(i)[2],
                    orders.get(i)[3], orders.get(i)[4], orders.get(i)[5],
                    orders.get(i)[6]);
        }
        createTable.print();
    }

    public void updatePickupStatus(String filepath, String newData, String oID) throws IOException
    // This method allow admin to modify a pickup status of order that had existed in items' file
    {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/prizeExHistory.txt");
        for (String[] strings : database) {
            if (strings[0].equals(oID))
                /* If the system could find out the oID in prizeExHistory file
                 * then the system allow admin to update the order's delivery status
                 */ {
                strings[5] = newData; // Modify the order's pickup status
            }
        }
        File file = new File(filepath);
        PrintWriter pw = new PrintWriter(file);

        pw.write(""); // The file would erase all the data in items' file
        pw.close();

        for (String[] strings : database) {
            Write.rewriteFile(filepath, "#oID, cID, prizeID, Points Cost, Date Exchanged, Exchange Status, Pickup Status", String.join(",", strings));
            // This method would allow system to write all data including new data into the prizeExHistory file
        }
    }
}
