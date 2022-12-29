import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PointsSystem {
    public static void main(String[] args) throws IOException {

    }

    public void updateTotalPoints(String cID) throws IOException
    // Update total points earned for customer after he/she finished every order
    {
        ArrayList<String[]> paymentConversion = ReadDataFromTXTFile.readAllLines("./src/billingHistory.txt");
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/customers.txt");
        long newData;
        Long newPoints = null;

        // Read all line in billingHistory.txt file and take out total payment
        for (int j = 0; j++ < paymentConversion.size(); j++) {
            if (paymentConversion.get(j)[1].equals(cID)) { // Check for similar cID
                newData = (Long.parseLong(paymentConversion.get(j)[2])); // Assigning a variable as the value of total payment
                newPoints = newData / 100000; // Calculate points (1 point per 100000VND spent)
            }
        }

        // Read all line in customers.txt file and put all data in arraylist
        for (int i = 0; i < database.size(); i++) {
            if (database.get(i)[0].equals(cID))
            /** If the system could find out the username in customers' file
             * then the system update their information
             */ {

                database.get(i)[9] = (String.valueOf(newPoints));  // The customer's information is changed (assigning new points)
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
}
