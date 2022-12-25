import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Admin extends Account {
    public Admin() throws IOException {
        super();
    }

    public String adminLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter admin username: ");
        String adminUsername = scanner.nextLine();
        if (!adminUsername.equals("admin")) {
            System.out.println("This admin account is not existed! Please try again");
        }
        return adminUsername;
    }

    public boolean verifyAdmin(String username, String password)
    // This method would verify username and password for admin account
    {
        String hashPassword = this.hashing(password); // Hash the input password
        if (username.equals("admin") && hashPassword.equals("751cb3f4aa17c36186f4856c8982bf27"))
        /**
         * If the username and hash password are correct
         */ {
            return true;
        }
        return false;
    }


    public void getAllCustomerInfo() throws FileNotFoundException
    // This method will display all the customers' information that existed in customers' file
    {
        ArrayList<String[]> user = new ArrayList<>(); // Create an arraylist to contain all customers' information
        Scanner fileScanner = new Scanner(new File("./src/customers.txt"));

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
            data = new String[]{ID, name, username, email, address, phone, membership};
            // Add one customer's information to an array
            user.add(data); // Add one customer's information in an arraylist
        }

        CreateTable createTable = new CreateTable(); // Create table to display customers' information
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("ID", "NAME", "USERNAME", "EMAIL", "ADDRESS", "PHONE", "MEMBERSHIP"); // Set header for the table

        for (int i = 1; i < user.size(); i++)
        // This for loop will add every single customer's information in the table to display
        {
            createTable.addRow(user.get(i)[0], user.get(i)[1], user.get(i)[2], user.get(i)[3], user.get(i)[4], user.get(i)[5], user.get(i)[6]);
        }

        createTable.print(); // Print the table
        createTable.setHeaders(new String[0]);
    }

    public void addProduct() throws IOException
    // This method for admin to add new product
    {
        Scanner scanner = new Scanner(System.in);
        PrintWriter pw;
        Product product = new Product();
        pw = new PrintWriter(new FileWriter("./src/items.txt", true));
        Path path = Paths.get("./src/items.txt");
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
        double price = scanner.nextDouble();
        scanner.nextLine();
        pw.println(ID + "," + title + "," + price + "," + category + "\n");
//        // Write product's information to items' file
        pw.close();

    }

    public void updatePrice(String filepath, String newData, String pID) throws IOException
    // This method allow admin to modify a product's price that had existed in items' file
    {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/items.txt");
        for (int i = 0; i < database.size(); i++) {
            if (database.get(i)[0].equals(pID))
            /** If the system could find out the pID in items' file
             * then the system allow admin to update the product's price
             */ {
                database.get(i)[2] = newData; // Modify the product's price
            }
        }
        File file = new File(filepath);
        PrintWriter pw = new PrintWriter(file);

        pw.write(""); // The file would erase all the data in items' file
        pw.close();

        ArrayList<String[]> newDatabase = database;

        for (int i = 0; i < newDatabase.size(); i++) {
            System.out.println(Arrays.toString(newDatabase.get(i)));
            Write.rewriteFile(filepath, "#ID,Title, Price, Catetory", String.join(",", newDatabase.get(i)));
            // This method would allow system to write all data including new data into the items' file
        }
    }


    public void deleteProduct(String filepath, String delProduct) throws IOException {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/items.txt");
        ArrayList<String[]> newDatabase = new ArrayList<>();
        for (int i = 0; i < database.size(); i++) {
            if (!database.get(i)[1].equals(delProduct)) {
                newDatabase.add(database.get(i)); // The customer's information is changed
            }
        }
        PrintWriter pw = new PrintWriter("./src/items.txt");

        pw.write(""); // The file would erase all the data in customers' file
        pw.close();


        for (String[] obj : newDatabase) {
            Write.rewriteFile(filepath, "#ID,Title, Price, Catetory", String.join(",", obj));
            // This method would allow system to write all data including new data into the customers' file
        }
    }


}
