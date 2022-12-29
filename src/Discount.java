import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Discount {
    private String discountCode;

    public Discount(String discountCode) {
        this.discountCode = discountCode;
    }

    public Discount() {
    }

    public void giveDiscountCode(Customer customer, Long totalPayment) throws IOException {
        PrintWriter pw;
        pw = new PrintWriter(new FileWriter("./src/customerDiscountCode.txt", true));
        Random rd = new Random();
        int i = rd.nextInt(999);
        String code = validateDiscountCode(String.format("%03d", i));
        String cID = customer.getcID();
        String discountCode = new String();
        Long discountAmount = (long) 0;
        ArrayList<String[]> discountType = ReadDataFromTXTFile.readAllLines("./src/discountType.txt");
        if (totalPayment > Long.parseLong(discountType.get(1)[1]) && totalPayment < Long.parseLong(discountType.get(2)[1])) {
            discountCode = discountType.get(1)[0];
            discountAmount = Long.parseLong(discountType.get(1)[2]);
        } else if (totalPayment >= Long.parseLong(discountType.get(2)[1]) && totalPayment < Long.parseLong(discountType.get(3)[1])) {
            discountCode = discountType.get(2)[0];
            discountAmount = Long.parseLong(discountType.get(2)[2]);
        } else if (totalPayment >= Long.parseLong(discountType.get(3)[1]) && totalPayment < Long.parseLong(discountType.get(4)[1])) {
            discountCode = discountType.get(3)[0];
            discountAmount = Long.parseLong(discountType.get(3)[2]);
        } else if (totalPayment >= Long.parseLong(discountType.get(4)[1])) {
            discountCode = discountType.get(4)[0];
            discountAmount = Long.parseLong(discountType.get(4)[2]);
        }
        if (!(discountCode == null)) {
            pw.println(cID + "," + code + "," + discountCode + "," + discountAmount);
            pw.close();
        }
    }

    public String validateDiscountCode(String id) {
        try {
            Scanner fileScanner = new Scanner(new File("./src/ordersHistory.txt"));

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] helo = line.split(",");
                if (helo[0].equals(id)) {
                    Random random = new Random();
                    id = String.format("0%03d", random.nextInt(999));
                    validateDiscountCode(id);
                } else {
                    this.discountCode = id;
                }
            }
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        }
        return this.discountCode;
    }

    public void displayCustomerDiscountCode(Customer customer) {
        ArrayList<String[]> discountCode = new ArrayList<>();

        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/customerDiscountCode.txt");
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[0].equals(customer.getcID()))
                /* If the system could find out the customer's ID in ordersHistory's file
                 */ {
                discountCode.add(database.get(i));
            }
        }
        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("OPTION", "ID", "DISCOUNT CODE", "DISCOUNT AMOUNT");
        for (int i = 0; i < discountCode.size(); i++) {
            createTable.addRow(String.valueOf(i + 1), discountCode.get(i)[1], discountCode.get(i)[2], discountCode.get(i)[3]);
        }
        createTable.print();
    }

    public ArrayList<String[]> discountCodeList(Customer customer)
    // Get all the product that have in that customer's cart
    {
        ArrayList<String[]> discountCode = new ArrayList<>();

        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/customerDiscountCode.txt");
        discountCode.add(database.get(0));
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[0].equals(customer.getcID()))
                /* If the system could find out the customer's ID in ordersHistory's file
                 */ {
                discountCode.add(database.get(i));
            }
        }
        return discountCode;
    }

    public void deleteDiscountCode(String filepath, String id) throws IOException
    // This method allow admin to delete a customer that had existed in customers' file
    {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/customerDiscountCode.txt");
        ArrayList<String[]> newDatabase = new ArrayList<>();
        for (int i = 0; i < database.size(); i++) {
            if (!database.get(i)[1].equals(id)) {
                newDatabase.add(database.get(i)); // Add all customers except the deleted customer
            }
        }
        PrintWriter pw = new PrintWriter("./src/customerDiscountCode.txt");

        pw.write(""); // The file would erase all the data in customers' file
        pw.close();

        for (String[] obj : newDatabase) {
            Write.rewriteFile(filepath, "#CID,Discount code,Discount amount",
                    String.join(",", obj));
            // This method would allow system to write all data including new data into the customers' file
        }
    }
}
