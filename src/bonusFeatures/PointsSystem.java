/*
  RMIT University Vietnam
  Course: COSC2081 Programming 1
  Semester: 2022C
  Assessment: Assignment 3
  Author: Tom's Prodigies
  ID: Nguyen Tran Ha Anh - s3938490
      Hoang Tuan Minh - s3924716
      Dang Kim Quang Minh - s3938024
      Nguyen Gia Bao - s3938143
  Acknowledgement:

*/

package bonusFeatures;

import fileMethods.CreateTable;
import fileMethods.ReadDataFromTXTFile;
import fileMethods.UserInput;
import fileMethods.Write;
import order.Order;
import product.Product;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class PointsSystem {

    // Update total points earned for customer after he/she finished every order
    public static void pointsConversion(String cID, String oID) throws IOException {
        ArrayList<String[]> paymentConversion = ReadDataFromTXTFile.readAllLines("./src/dataFile/billingHistory.txt");
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/customers.txt");
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

        File file = new File("./src/dataFile/customers.txt");
        PrintWriter pw = new PrintWriter(file);

        pw.write(""); // The file would erase all the data in customers' file
        pw.close();

        for (String[] obj : database) {
            Write.rewriteFile("./src/dataFile/customers.txt", "#ID,Name,Email,Address,Phone,Membership,Username,Password,Total Spending,Total Points",
                    String.join(",", obj));
            // This method would allow system to write all data including new data into the customers' file
        }
    }

    // Display all the prizes for customer to exchange
    public static void viewPrizes() throws IOException {
        ArrayList<String[]> prizeItems = new ArrayList<>();
        Scanner fileProducts = new Scanner(new File("./src/dataFile/prizeItems.txt"));
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
        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("OPTION", "ID", "TITLE", "POINTS", "CATEGORY");

        // Add all lines into table with options
        for (int i = 1; i < prizeItems.size(); i++) {
            CreateTable.addRow(String.valueOf(i), prizeItems.get(i)[0], prizeItems.get(i)[1], prizeItems.get(i)[2], prizeItems.get(i)[3]);
        }
        CreateTable.print();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
    }

    // Update total points earned for customer after he/she finished every order
    public static void exchangeItem(String user, String itemID) throws IOException {
        ArrayList<String[]> pointCost = ReadDataFromTXTFile.readAllLines("./src/dataFile/prizeItems.txt");
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/customers.txt");
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
            File file = new File("./src/dataFile/customers.txt");
            PrintWriter pw = new PrintWriter(file);

            pw.write(""); // The file would erase all the data in customers' file
            pw.close();

            for (String[] obj : database) {
                Write.rewriteFile("./src/dataFile/customers.txt", "#ID,Name,Email,Address,Phone,Membership,Username,Password,Total Spending,Total Points",
                        String.join(",", obj));
                // This method would allow system to write all data including new data into the customers' file
            }

            logExchange(user, itemID, String.valueOf(newData));
        }
    }

    // Allow customer to exchange their points to have gift
    public static void logExchange(String user, String itemID, String Cost) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("./src/dataFile/prizeExHistory.txt", true));

        // log Exchange date and assign status and pickup notifications
        String exchangeDate = new SimpleDateFormat("MM/dd/yyyy_HH:mm").format(Calendar.getInstance().getTime());
        String exchangeStatus = "SUCCESSFUL";
        String pickupStatus = "PENDING";

        // Create oID for each exchange
        Random rd = new Random();
        int randNum = rd.nextInt(999);
        String oID = Order.oIDDataForValidate(String.format("O%03d", randNum));

        pw.println(oID + "," + user + "," + itemID + "," + Cost + ","
                + exchangeDate + "," + exchangeStatus + "," + pickupStatus);
        pw.close();

        PointsSystem.printConfirmation(oID);
    }

    // Print the order that exchange gift status
    public static void printConfirmation(String oID) throws IOException {
        // Set up table
        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("order.Order ID", "cID", "prizeID", "Point Cost", "Date Exchanged", "Exchange Status", "Pickup Status");

        // Get line in .txt file with parameter oID
        String[] exchangeConfirmation = ReadDataFromTXTFile.readSpecificLine(oID, 0, "./src/dataFile/prizeExHistory.txt", ",");

        // Add content to table
        CreateTable.addRow(exchangeConfirmation[0], exchangeConfirmation[1], exchangeConfirmation[2], exchangeConfirmation[3],
                exchangeConfirmation[4], exchangeConfirmation[5], exchangeConfirmation[6]);

        // Print out the table
        CreateTable.print();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
    }

    // Display all the exchange history of customer
    public static void getExchangeInfo(String cID) {
        ArrayList<String[]> orders = new ArrayList<>();

        // Read all orders in prizeExHistory
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/prizeExHistory.txt");
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
            CreateTable.setShowVerticalLines(true);
            CreateTable.setHeaders("OID", "CID", "PID", "POINT COSTS",
                    "EXCHANGE DATE", "EXCHANGE STATUS", "PICKUP STATUS");

            // Add all the exchangeHistory that have the corresponding cID
            for (String[] order : orders) {
                CreateTable.addRow(order[0], order[1], order[2], order[3],
                        order[4], order[5], order[6]);
            }
            CreateTable.print();
            CreateTable.setHeaders(new String[0]);
            CreateTable.setRows(new ArrayList<String[]>());
        }
    }

    // Read all line in prizeExHistory
    public static void viewExchangeLog() {
        ArrayList<String[]> orders = ReadDataFromTXTFile.readAllLines("./src/dataFile/prizeExHistory.txt");

        // Setting up table
        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("OID", "CID", "PID", "POINTS COST", "DATE EXCHANGED", "EXCHANGE STATUS", "PICKUP STATUS");

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

    // This method allow admin to modify a pickup status of order that had existed in items' file
    public static void updatePickupStatus(String newData, String oID) throws IOException {
        File targetFile = new File("./src/dataFile/prizeExHistory.txt");
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/prizeExHistory.txt");
        for (String[] strings : database) {
            if (strings[0].equals(oID))
                /* If the system could find out the oID in prizeExHistory file
                 * then the system allow admin to update the order's delivery status
                 */ {
                strings[6] = newData.toUpperCase(); // Modify the order's pickup status
            }
        }

        PrintWriter pw = new PrintWriter(targetFile);
        pw.write(""); // The file would erase all the data in items' file
        pw.close();

        for (String[] strings : database) {
            Write.rewriteFile(String.valueOf(targetFile), "#oID, cID, prizeID, Points Cost, Date Exchanged, Exchange Status, Pickup Status", String.join(",", strings));
            // This method would allow system to write all data including new data into the prizeExHistory file
        }
    }

    // Searching the order by using order ID
    public static void searchExchangeOrderByOID(String oId) {
        ArrayList<String[]> orders = new ArrayList<>(); // Create a new arraylist to store order information

        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/prizeExHistory.txt");
        // Read all line in prizeExHistory.txt file and put all data in arraylist
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[0].equals(oId))
            // If the system could find out the customer's ID in prizeExHistory's file
            {
                orders.add(database.get(i));
            }
        }
        if (!(orders.size() == 0)) {
            CreateTable.setShowVerticalLines(true);
            CreateTable.setHeaders("OID", "CID", "PID", "POINTS COST", "DATE EXCHANGED", "EXCHANGE STATUS", "PICKUP STATUS");
            // Set header for the order information table
            for (String[] order : orders) {
                CreateTable.addRow(order[0], order[1], order[2], order[3],
                        order[4], order[5], order[6]);
                // Add information to each row in table
            }
            CreateTable.print();
            CreateTable.setHeaders(new String[0]);
            CreateTable.setRows(new ArrayList<String[]>());
        } else {
            System.out.println("THERE IS NO ORDER HAVE THIS ID");
        }
    }

    // This method for admin to add new product
    public static void addPrize() throws IOException, ParseException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        PrintWriter pw;
        Product product = new Product();
        pw = new PrintWriter(new FileWriter("./src/dataFile/prizeItems.txt", true));
        Path path = Paths.get("./src/dataFile/prizeItems.txt");
        int id = (int) Files.lines(path).count(); // Define the id of this product
        System.out.println("Enter a year of this product: "); // Ask admin to input the product's year
        int year = Integer.parseInt(scanner.nextLine());
        String ID = String.format("I%03d-%04d", id, year); // Generate the product ID in items' file
        System.out.println("Enter category: "); // Ask admin to input the product's category
        String category = scanner.nextLine();
        product.registerCategory(category); // Increase the quantity if the category had existed or create new category
        System.out.println("Enter name: "); // Ask admin to input the product's name
        String name = scanner.nextLine();
        System.out.println("Enter points cost: "); // Ask admin to input the product's price
        long pointCost = Long.parseLong(scanner.nextLine());
        pw.println(ID + "," + name + "," + pointCost + "," + category);
//        // FileMethods.Write product's information to items' file
        pw.close();
    }

    // This method allow admin to delete a product that had existed in items' file
    public static void deletePrize() throws IOException {
        PointsSystem.viewPrizes();
        String optionOrder = UserInput.rawInput();
        ArrayList<String[]> prizeItemList = ReadDataFromTXTFile.readAllLines("./src/dataFile/prizeItems.txt");
        String[] prizeInfo = new String[3];
        for (int i = 1; i < prizeItemList.size(); i++) {
            if (i == Integer.parseInt(optionOrder)) {
                prizeInfo = ReadDataFromTXTFile.readSpecificLine(prizeItemList.get(i)[0], 0, "./src/dataFile/prizeItems.txt", ",");
            }
        }

        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/prizeItems.txt");
        ArrayList<String[]> newDatabase = new ArrayList<>();
        for (String[] strings : database) {
            if (!strings[0].equals(prizeInfo[0])) {
                newDatabase.add(strings); // Add all items except the deleted product
            }
        }
        File targetFile = new File("./src/dataFile/prizeItems.txt");

        PrintWriter pw = new PrintWriter("./src/dataFile/prizeItems.txt");
        pw.write(""); // The file would erase all the data in items' file
        pw.close();


        for (String[] obj : newDatabase) {
            Write.rewriteFile(String.valueOf(targetFile), "#ID,Title,Points Cost,Category", String.join(",", obj));
            // This method would allow system to write all data including new data into the items' file
        }
        System.out.println("Deletion successful!");
    }
}
