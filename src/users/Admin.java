package users;

import fileMethods.CreateTable;
import fileMethods.ReadDataFromTXTFile;
import fileMethods.UserInput;
import fileMethods.Write;
import product.Product;
import product.SortProduct;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Admin extends Account {
    // Constructor
    public Admin() {
        super();
    }

    public boolean verifyAdmin(String username, String password)
    // This method would verify username and password for admin account
    {
        String hashPassword = this.hashing(password); // Hash the input password
        if (username.equals("admin") && hashPassword.equals("751cb3f4aa17c36186f4856c8982bf27"))
        // If the username and password after hashing are correct
        {
            return true;
        }
        return false;
    }

    public static boolean dateValidate(String date)
    // Validate the date that customer input
    {
        //First, we will need to separate the day into three different component and reformat it into a string.

        String[] dateComponent = date.split("/");
        String month = dateComponent[0].replaceFirst("^0*", "");
        String day = dateComponent[1].replaceFirst("^0*", "");
        String year = dateComponent[2].replaceFirst("^0*", "");
        if (Integer.parseInt(day) < 10) {
            date = String.format("0%s" + "/" + "%s" + "/" + "%s", month, day, year);
        } else if (Integer.parseInt(month) < 10) {
            date = String.format("%s" + "/" + "0%s" + "/" + "%s", month, day, year);
        } else if (Integer.parseInt(day) < 10 && Integer.parseInt(month) < 10) {
            date = String.format("0%s" + "/" + "0%s" + "/" + "%s", month, day, year);
        } else {
            date = String.format("%s" + "/" + "%s" + "/" + "%s", month, day, year);
        }
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        format.setLenient(false);
        try {
            format.parse(date);
        } catch (ParseException e) {
            return true;
        }
        return false;
    }

    public void getAllCustomerInfo() throws FileNotFoundException
    // This method will display all the customers' information that existed in customers' file
    {
        ArrayList<String[]> user = new ArrayList<>(); // Create an arraylist to contain all customers' information
        Scanner fileScanner = new Scanner(new File("./src/dataFile/customers.txt"));

        while (fileScanner.hasNext())
        // While customers' file has next line
        {
            String[] data; // Create an array to store one customer's information
            String line = fileScanner.nextLine();
            StringTokenizer stringTokenizer = new StringTokenizer(line, ",");

            // Separate the line's information by comma
            String ID = stringTokenizer.nextToken();
            String name = stringTokenizer.nextToken();
            String email = stringTokenizer.nextToken();
            String address = stringTokenizer.nextToken();
            String phone = stringTokenizer.nextToken();
            String membership = stringTokenizer.nextToken();
            String username = stringTokenizer.nextToken();
            String password = stringTokenizer.nextToken();
            String totalSpending = String.valueOf(stringTokenizer.nextToken());
            data = new String[]{ID, name, username, email, address, phone, membership, totalSpending};
            // Add one customer's information to an array
            user.add(data); // Add one customer's information in an arraylist
        }

        CreateTable createTable = new CreateTable(); // Create table to display customers' information
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("CID", "NAME", "USERNAME", "EMAIL", "ADDRESS", "PHONE", "MEMBERSHIP", "TOTAL SPENDING");
        // Set header for the table

        for (int i = 1; i < user.size(); i++)
        // This for loop will add every single customer's information in the table to display
        {
            createTable.addRow(user.get(i)[0], user.get(i)[1], user.get(i)[2], user.get(i)[3],
                    user.get(i)[4], user.get(i)[5], user.get(i)[6], user.get(i)[7]);
        }

        createTable.print(); // Print the table
    }

    public void addProduct() throws IOException, ParseException, InterruptedException
    // This method for admin to add new product
    {
        Scanner scanner = new Scanner(System.in);
        PrintWriter pw;
        Product product = new Product();
        pw = new PrintWriter(new FileWriter("./src/dataFile/items.txt", true));
        Path path = Paths.get("./src/dataFile/items.txt");
        int id = (int) Files.lines(path).count(); // Define the id of this product
        System.out.println("Enter a year of this product: "); // Ask admin to input the product's year
        int year = Integer.parseInt(scanner.nextLine());
        String ID = String.format("I%03d-%04d", id, year); // Generate the product ID in items' file
        System.out.println("Enter category: "); // Ask admin to input the product's category
        String category = scanner.nextLine();
        product.registerCategory(category); // Increase the quantity if the category had existed or create new category
        System.out.println("Enter title: "); // Ask admin to input the product's title
        String title = scanner.nextLine();
        System.out.println("Enter price: "); // Ask admin to input the product's price
        Long price = Long.parseLong(scanner.nextLine());
        pw.println(ID + "," + title + "," + price + "," + category);
//        // FileMethods.Write product's information to items' file
        pw.close();
    }

    public void getAllCategory() throws FileNotFoundException
    // This method will display all the customers' information that existed in customers' file
    {
        ArrayList<String[]> categoryList = new ArrayList<>(); // Create an arraylist to contain all customers' information
        Scanner fileScanner = new Scanner(new File("./src/dataFile/categories.txt"));

        while (fileScanner.hasNext())
        // While customers' file has next line
        {
            String[] data; // Create an array to store one customer's information
            String line = fileScanner.nextLine();
            StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
            // Separate the line's information by comma
            String ID = stringTokenizer.nextToken();
            String category = stringTokenizer.nextToken();
            String quantity = stringTokenizer.nextToken();
            data = new String[]{ID, category, quantity};
            // Add one category's information to an array
            categoryList.add(data); // Add one customer's information in an arraylist
        }

        CreateTable createTable = new CreateTable(); // Create table to display customers' information
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("ID", "CATEGORY", "QUANTITY"); // Set header for the table

        for (int i = 1; i < categoryList.size(); i++)
        // This for loop will add every single category's information in the table to display
        {
            createTable.addRow(categoryList.get(i)[0], categoryList.get(i)[1], categoryList.get(i)[2]);
        }
        createTable.print(); // Print the table
    }

    public void updatePrice(String filepath, String newData, String pID) throws IOException
    // This method allow admin to modify a product's price that had existed in items' file
    {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/items.txt");
        for (String[] strings : database) {
            if (strings[0].equals(pID))
                /* If the system could find out the pID in items' file
                 * then the system allow admin to update the product's price
                 */ {
                strings[2] = newData; // Modify the product's price
            }
        }
        File file = new File(filepath);
        PrintWriter pw = new PrintWriter(file);

        pw.write(""); // The file would erase all the data in items' file
        pw.close();

        for (String[] strings : database) {
            Write.rewriteFile(filepath, "#ID,Title, Price, Category", String.join(",", strings));
            // This method would allow system to write all data including new data into the items' file
        }
    }

    public void updateDeliveryStatus(String filepath, String newData, String oID) throws IOException
    // This method allow admin to modify a delivery status of order that had existed in items' file
    {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/ordersHistory.txt");

        for (String[] strings : database) {
            if (strings[0].equals(oID))
                /* If the system could find out the oID in ordersHistory's file
                 * then the system allow admin to update the order's delivery status
                 */ {
                strings[8] = newData.toUpperCase(); // Modify the order's delivery status
            }
        }
        File file = new File(filepath);
        PrintWriter pw = new PrintWriter(file);

        pw.write(""); // The file would erase all the data in items' file
        pw.close();

        for (String[] strings : database) {
            Write.rewriteFile(filepath, "#OID,CID,PID,Membership,Payment price,Timestamp,Total spending,order.Order status,Delivery status", String.join(",", strings));
            // This method would allow system to write all data including new data into the items' file
        }
    }

    public void deleteProduct() throws IOException
    // This method allow admin to delete a product that had existed in items' file
    {
        Product products = new Product();
        products.getProductHaveId();
        String choiceOrder = UserInput.rawInput();
        ArrayList<String[]> productList = ReadDataFromTXTFile.readAllLines("./src/dataFile/items.txt");
        String[] productInfo = new String[3];
        for (int i = 0; i < productList.size(); i++) {
            if (i == Integer.parseInt(choiceOrder)) {
                productInfo = ReadDataFromTXTFile.readSpecificLine(productList.get(i)[1], 1, "./src/dataFile/items.txt", ",");
            }
        }
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/items.txt");
        ArrayList<String[]> newDatabase = new ArrayList<>();
        for (String[] strings : database) {
            if (!strings[0].equals(productInfo[0])) {
                newDatabase.add(strings); // Add all items except the deleted product
            }
        }
        PrintWriter pw = new PrintWriter("./src/dataFile/items.txt");

        pw.write(""); // The file would erase all the data in items' file
        pw.close();


        for (String[] obj : newDatabase) {
            Write.rewriteFile("./src/dataFile/items.txt", "#ID,Title,Price,Category", String.join(",", obj));
            // This method would allow system to write all data including new data into the items' file
        }
        System.out.println("Deletion successful");
    }

    public void deleteCustomer(String filepath, String delCustomer, int col) throws IOException
    // This method allow admin to delete a customer that had existed in customers' file
    {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/customers.txt");
        ArrayList<String[]> newDatabase = new ArrayList<>();
        for (int i = 0; i < database.size(); i++) {
            if (!database.get(i)[col].equals(delCustomer)) {
                newDatabase.add(database.get(i)); // Add all customers except the deleted customer
            }
        }
        PrintWriter pw = new PrintWriter("./src/dataFile/customers.txt");

        pw.write(""); // The file would erase all the data in customers' file
        pw.close();

        for (String[] obj : newDatabase) {
            Write.rewriteFile(filepath, "#ID,Name,Email,Address,Phone,Membership,Username,Password,Total spending,Total Points",
                    String.join(",", obj));
            // This method would allow system to write all data including new data into the customers' file
        }
        System.out.println("Deletion successful");
    }

    public void deleteCategory(String filepath, String delCategory) throws IOException
    // This method allow admin to delete a category that had existed in categories' file
    {
        ArrayList<String[]> categoryList = ReadDataFromTXTFile.readAllLines("./src/dataFile/categories.txt");
        ArrayList<String[]> newCategoryList = new ArrayList<>();


        // Loop through all the categories
        for (String[] strings : categoryList) {
            if (!strings[1].equals(delCategory)) {
                newCategoryList.add(strings); // Add all categories except the deleted category
            }
        }
        PrintWriter pw = new PrintWriter("./src/dataFile/categories.txt");

        pw.write(""); // The file would erase all the data in categories' file
        pw.close();


        for (String[] obj : newCategoryList) {
            Write.rewriteFile(filepath, "#ID,Category,Quantity", String.join(",", obj));
            // This method would allow system to write all data including new data into the customers' file
        }

        deleteProductCategory("./src/dataFile/items.txt", delCategory);
        System.out.println("Deletion successful");
    }


    /* All the methods deleteCustomer and deleteCategory and deleteProduct basically works in the same logic
     * First it finds the corresponding customer ID or name for category or product ID and exclude the information belong to
     * that specific input, then it adds all the remaining info into a temporary ArrayList (newDataBase) and deletes all the content in .txt file
     * and rewrite the file with the data in the newDataBase which will not have the "deleted data" since that has been excluded from the newDataBase
     */

    public void deleteProductCategory(String filepath, String delCategory) throws IOException {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/items.txt");

        // Loop through all the product ID
        for (int i = 0; i < database.size(); i++) {

            // If a category is the same, it will change the category of that product to None
            if (database.get(i)[3].equals(delCategory)) {
                database.get(i)[3] = "None";
            }
        }

        PrintWriter pw = new PrintWriter("./src/dataFile/items.txt");

        pw.write(""); // The file would erase all the data in products file
        pw.close();

        // This method would allow system to write all data including new data into the customers' file
        for (String[] obj : database) {
            Write.rewriteFile(filepath, "#ID,Title,Price,Category", String.join(",", obj));
        }
        System.out.println("Deletion successful");
    }

    public ArrayList<Long> getTotalRevenue() throws IOException {
        /*This method will give admin the total revenue of the store. */

        String[] revenue = ReadDataFromTXTFile.readColString(2, "./src/dataFile/billingHistory.txt", ",");
        // Creating an arraylist of prices
        ArrayList<Long> revenueList = new ArrayList<>(revenue.length);

        // Prepping the price list to be able to sort
        for (int i = 1; i < revenue.length; i++) {
            revenueList.add(Long.valueOf(revenue[i]));
        }
        return revenueList;
    }

    public void calculateRevenue(ArrayList<Long> moneyList) {
        /* This method will calculate the revenue*/

        long sum = 0;
        for (Long aLong : moneyList) {
            sum += aLong;
        }
        CreateTable revenueTable = new CreateTable();
        revenueTable.setShowVerticalLines(true);
        revenueTable.setHeaders("TOTAL REVENUE");
        revenueTable.addRow(String.valueOf(sum));
        revenueTable.print();
    }


    //This method is used to reformat the input date into a separate string for comparison.
    public static String dateInput(String date) {
        String[] dateComponent = date.split("/");
        String month = dateComponent[0].replaceFirst("^0*", "");
        String day = dateComponent[1].replaceFirst("^0*", "");
        String year = dateComponent[2].replaceFirst("^0*", "");
        date = month + "/" + day + "/" + year;
        return date;

    }

    public void getMostSpender() throws IOException
    // Display all information of customer who spend the most in our system
    {
        CreateTable createTable = new CreateTable();

        // Get total spending column
        String[] readSpending = ReadDataFromTXTFile.readColString(8, "./src/dataFile/customers.txt", ",");


        // Create an arraylist of all the total spending
        ArrayList<Long> spendingList = new ArrayList<>(readSpending.length);

        // Creating a new list to utilise the sort method
        for (int i = 1; i < readSpending.length; i++) {
            spendingList.add(Long.parseLong(readSpending[i]));
        }
        // Sort the product from min to max
        SortProduct.sortDescending(spendingList);

        // Creating and printing out the information
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("CID", "NAME", "USERNAME", "EMAIL", "ADDRESS", "PHONE", "MEMBERSHIP", "TOTAL SPENDING");

        // Get the first person on the list (Max spenders as the list have been sorted to Ascend from Max)
        String[] mostSpender = ReadDataFromTXTFile.readSpecificLine(Long.toString(spendingList.get(0)), 8, "./src/dataFile/customers.txt", ",");

        // Add that person into an ArrayList, so it can be displayed on the table
        createTable.addRow(mostSpender[0],
                mostSpender[1],
                mostSpender[6],
                mostSpender[2],
                mostSpender[3],
                mostSpender[4],
                mostSpender[5],
                mostSpender[8]);

        createTable.print();
    }

    /* This method allow admin to calculate daily revenue base on the timestamp of the purchase.*/
    public ArrayList<Long> getDailyRevenue() throws IOException {

        String[] dailyRevenue = ReadDataFromTXTFile.readColString(2, "./src/dataFile/billingHistory.txt", ",");
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/billingHistory.txt");
        ArrayList<Long> revenueList = new ArrayList<>(dailyRevenue.length);

        Scanner inputObj = new Scanner(System.in);
        System.out.println("Enter the date to get the daily revenue (MM/dd/yyyy):");
        String date = inputObj.nextLine();
        while (dateValidate(date)) /* validate if the timestamp is match to the user's input */ {
            System.out.println("Enter the date to get the daily revenue (MM/dd/yyyy):");
            date = inputObj.nextLine();
        }

        date = dateInput(date);

        //If the date is match, all the price will put in an Arraylist and total all the price.
        for (int i = 1; i < database.size(); i++) {
            String[] dateSplit = database.get(i)[3].split("_");
            String splitDate = dateSplit[0].replaceAll("//s", "");
            splitDate = dateInput(splitDate);
            if (splitDate.equals(date)) {
                revenueList.add(Long.valueOf(dailyRevenue[i]));
            }
        }
        return revenueList;
    }

}

