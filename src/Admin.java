import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
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

    public boolean verifyAdmin(String username, String password) {
        String hashPassword = this.hashing(password);
        if (username.equals("admin") && hashPassword.equals("751cb3f4aa17c36186f4856c8982bf27")) {
            return true;
        }
        return false;
    }


    public void getAllCustomerInfo() throws FileNotFoundException {
        ArrayList<String[]> user = new ArrayList<>();
        Scanner fileScanner = new Scanner(new File("./src/customers.txt"));

        while (fileScanner.hasNext()) {
            String[] data = new String[6];
            String line = fileScanner.nextLine();
            StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
            String ID = stringTokenizer.nextToken();
            String name = stringTokenizer.nextToken();
            String email = stringTokenizer.nextToken();
            String address = stringTokenizer.nextToken();
            String phone = stringTokenizer.nextToken();
            String membership = stringTokenizer.nextToken();
            String username = stringTokenizer.nextToken();
            data = new String[]{ID, name, username, email, address, phone, membership};
            user.add(data);
        }

        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("ID", "NAME", "USERNAME", "EMAIL", "ADDRESS", "PHONE", "MEMBERSHIP");

        for (int i = 1; i < user.size(); i++) {
            createTable.addRow(user.get(i)[0], user.get(i)[1], user.get(i)[2], user.get(i)[3], user.get(i)[4], user.get(i)[5], user.get(i)[6]);
        }

        createTable.print();
        createTable.setHeaders(new String[0]);
    }

    public void addProduct() throws IOException
    // Register the customer's account
    {
        Scanner scanner = new Scanner(System.in);
        PrintWriter pw;
        Product product = new Product();
        pw = new PrintWriter(new FileWriter("./src/items.txt", true));
        Path path = Paths.get("./src/items.txt");
        int id = (int) Files.lines(path).count();
        Random random = new Random();
        String ID = String.format("I%03d-%04d", id, random.nextInt(2024));
        System.out.println("Enter category: ");
        String category = scanner.nextLine();
        product.registerCategory(category);
        System.out.println("Enter title: ");
        String title = scanner.nextLine();
        System.out.println("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        pw.println("\n" + ID + "," + title + "," + price + "," + category);
//        // Write customer's information to customers file
        pw.close();
    }

    public void updatePrice(String filepath, String newData, String pID) throws IOException {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/items.txt");
        for (int i = 0; i < database.size(); i++) {
            if (database.get(i)[0].equals(pID)) {
                database.get(i)[2] = newData;
            }
        }
        File file = new File(filepath);
        PrintWriter pw = new PrintWriter(file);

        pw.write("");
        pw.close();

        ArrayList<String[]> newDatabase = database;

        for (String[] obj : newDatabase) {
            Write.rewriteFile(filepath, "#ID,Title, Price, Catetory", String.join(",", obj));
        }
    }
}
