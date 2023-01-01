import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.StringTokenizer;

public class PointsSystem {
    private String ID;
    private String name;
    private Long price;
    private String category;

    public PointsSystem(String ID, String name, Long price, String category) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.category = category;
    }


    public void pointsConversion(String cID, String oID) throws IOException
    // Update total points earned for customer after he/she finished every order
    {
        ArrayList<String[]> paymentConversion = ReadDataFromTXTFile.readAllLines("./src/billingHistory.txt");
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/customers.txt");
        long newData;
        Long newPoints = null;

        // Read all line in billingHistory.txt file and take out total payment
        for (int j = 0; j++ < paymentConversion.size(); j++) {
            if (paymentConversion.get(j)[1].equals(oID)) { // Check for similar cID
                newData = (Long.parseLong(paymentConversion.get(j)[2])); // Assigning a variable as the value of total payment
                newPoints = newData / 100000; // Calculate points (1 point per 100000VND spent)
            }
        // Read all line in customers.txt file and put all data in arraylist
            if (database.get(j)[0].equals(cID))
            /** If the system could find out the username in customers' file
             * then the system update their information
             */
                database.get(j)[9] = (String.valueOf(newPoints));  // The customer's information is changed (assigning new points)
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

    private void viewPrizes() throws IOException {
        ArrayList<String[]> prizeItems = new ArrayList<>();
        Scanner fileProducts = new Scanner(new File("./src/prizeItems.txt"));

        while (fileProducts.hasNext()) {
            String[] productData = new String[3];
            String line = fileProducts.nextLine();
            StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
            String ID = stringTokenizer.nextToken();
            String title = stringTokenizer.nextToken();
            String price = stringTokenizer.nextToken();
            String category = stringTokenizer.nextToken();
            productData = new String[]{ID, title, price, category};
            prizeItems.add(productData);
        }

        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("OPTION", "ID", "TITLE", "POINTS", "CATEGORY");

        for (int i = 1; i < prizeItems.size(); i++) {
            createTable.addRow(String.valueOf(i), prizeItems.get(i)[0], prizeItems.get(i)[1], prizeItems.get(i)[2], prizeItems.get(i)[3]);
        }

        createTable.print();
    }

    private void exchangeItem(String user, String itemID) throws IOException {
        // Update total points earned for customer after he/she finished every order
        ArrayList<String[]> pointCost = ReadDataFromTXTFile.readAllLines("./src/prizeItems.txt");
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/customers.txt");
        long newData;
        Long newPoints = null;

        // Read all line in prizeItems.txt file and take out pointCost
        for (int j = 0; j++ < pointCost.size(); j++) {
            if (pointCost.get(j)[0].equals(itemID)) { // Check for similar itemID
                newData = (Long.parseLong(pointCost.get(j)[2])); // Assigning a variable as the value of prizePoints
                newPoints = (Long.parseLong(database.get(j)[9]) - newData); // Deduct points
            }

            // Read all line in customers.txt file and put all data in arraylist
            if (database.get(j)[0].equals(user))
            /** If the system could find out the username in customers' file
             * then the system update their information
             */

                database.get(j)[9] = (String.valueOf(newPoints));  // The customer's information is changed (assigning new points)

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

    private void logExchange(Customer user, PointsSystem itemID, String oID) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("./src/prizeExHistory.txt"));

        String cID = user.getcID();
        String pID = itemID.getID();
        Long pointsCost = itemID.getPrice();
        String exchangeDate = new SimpleDateFormat("MM/dd/yyyy_HH:mm").format(Calendar.getInstance().getTime());
        String exchangeStatus = "SUCCESSFUL";
        String pickupStatus = "PENDING";

        pw.println(oID + "," + cID + "," + pID + "," + pointsCost + ","
                + exchangeDate + "," + exchangeStatus + "," + pickupStatus);
        pw.close();
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}
