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

package product;

import fileMethods.CreateTable;
import fileMethods.ReadDataFromTXTFile;
import fileMethods.UserInput;
import fileMethods.Write;
import menu.AdminMenu;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class Product {
    // Attributes
    private String ID;
    private String title;
    private Long price;
    private String category;

    public Product(String ID, String title, Long price, String category) {
        this.ID = ID;
        this.title = title;
        this.price = price;
        this.category = category;
    }

    public Product() {
    }

    // This method will help user to search by category
    public void searchByCategory() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the category (e.g: laptop):");
        String category = sc.nextLine();
        String capital = category.toUpperCase();
        try {
            Scanner fileScanner = new Scanner(new File("./src/dataFile/categories.txt"));
            boolean found = false;
            while (fileScanner.hasNext()) {
                String data = fileScanner.nextLine();
                String[] items = data.split(",");
                if (capital.equals(items[1])) {
                    found = true;
                    break;
                }
            }
            if (found) {
                ArrayList<String[]> categories = new ArrayList<>();
                ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/items.txt");
                for (int i = 1; i < database.size(); i++) {
                    if (database.get(i)[3].equals(capital))
                    // If the system could find out the category in items.txt file
                    {
                        categories.add(database.get(i));
                    }
                }
                if (categories.size() > 0) {
                    CreateTable.setShowVerticalLines(true);
                    CreateTable.setHeaders("ID", "TITLE", "PRICE", "CATEGORY");
                    for (String[] categoryOutput : categories) {
                        CreateTable.addRow(categoryOutput[0], categoryOutput[1], categoryOutput[2], categoryOutput[3]);
                    }
                    CreateTable.print();
                    CreateTable.setHeaders(new String[0]);
                    CreateTable.setRows(new ArrayList<String[]>());
                } else {
                    System.out.println("THIS CATEGORY DOES NOT HAVE ITEMS YET!!!");
                }
            } else {
                System.out.println("This category is not existed!");
                searchByCategory();
            }
        } catch (FileNotFoundException e) {
            searchByCategory();
        }
    }

    // Transforming user input to the right format.
    public void searchCategoryByPriceRange() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter category (e.g: laptop): ");
        String category = scanner.nextLine();
        String capital = category.toUpperCase();

        // Create a empty Arraylist to store data after searching.
        ArrayList<String[]> categories = new ArrayList<>();

        // Temporary database to store data.
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/items.txt");
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[3].equals(capital))
            // If the system could find out the category in items.txt file
            {
                categories.add(database.get(i));

            }
        }
        printPriceRange();
        String option = UserInput.rawInput();
        boolean check = false;
        switch (option) {
            // Check for item's prices in a particular range defined
            case "1":
                for (String[] strings : categories) {
                    Long priceItem = Long.parseLong(strings[2]);
                    if (0 <= priceItem && priceItem < 25000000) {
                        CreateTable.addRow(strings[0], strings[1], strings[2], strings[3]);
                    }
                }
                System.out.println("Price range: 0 --> 25 mil");
                check = true;
                break;
            case "2":
                for (String[] strings : categories) {
                    Long priceItem = Long.parseLong(strings[2]);
                    if (25000000 <= priceItem && priceItem < 50000000) {
                        CreateTable.addRow(strings[0], strings[1], strings[2], strings[3]);
                    }
                }
                System.out.println("Price range: 25 mil --> 50 mil");
                check = true;
                break;
            case "3":
                for (String[] strings : categories) {
                    Long priceItem = Long.parseLong(strings[2]);
                    if (50000000 <= priceItem && priceItem < 75000000) {
                        CreateTable.addRow(strings[0], strings[1], strings[2], strings[3]);
                    }
                }
                System.out.println("Price range: 50 mil --> 75 mil");
                check = true;
                break;
            case "4":
                for (String[] strings : categories) {
                    Long priceItem = Long.parseLong(strings[2]);
                    if (75000000 <= priceItem && priceItem < 100000000) {
                        CreateTable.addRow(strings[0], strings[1], strings[2], strings[3]);
                    }
                }
                System.out.println("Price range: 75 mil --> 100 mil");
                check = true;
                break;
            default:
                System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                TimeUnit.SECONDS.sleep(1);
                check = false;
                searchCategoryByPriceRange();
                // for menu add 1 more but will be menu.something();
        }
        if (check) {
            CreateTable.setShowVerticalLines(true);
            CreateTable.setHeaders("ID", "TITLE", "PRICE", "CATEGORY");
            CreateTable.print();
            CreateTable.setHeaders(new String[0]);
            CreateTable.setRows(new ArrayList<String[]>());
        }
    }

    // Register new category
    public void registerCategory(String category) throws IOException, InterruptedException, ParseException {
        AdminMenu adminMenu = new AdminMenu();
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/categories.txt");
        String capital = category.toUpperCase();
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[1].equals(capital)) {
                database.get(i)[2] = String.valueOf(Integer.parseInt(database.get(i)[2]) + 1);
                File file = new File("./src/dataFile/categories.txt");
                PrintWriter pw = new PrintWriter(file);
                pw.write("");
                pw.close();

                for (String[] obj : database) {
                    Write.rewriteFile("./src/dataFile/categories.txt", "#ID,pID,Quantity", String.join(",", obj));
                }
            }
        }
        if (!checkCategory(category)) {
            System.out.println("This category do not exist in category list yet!");
            System.out.println("Please choose your option: ");
            System.out.println("1. Create new category automatically");
            System.out.println("2. Exit");
            String option = UserInput.rawInput();
            switch (option) {
                case "1":
                    createNewCategory(capital, 1);
                    break;
                case "2":
                    adminMenu.viewHomepage();
            }
        }
    }

    // This method is used to create new category and add to the categories.txt file.
    public void createNewCategory(String category, int quantity) throws IOException {
        Path path = Paths.get("./src/dataFile/categories.txt");
        int id = (int) Files.lines(path).count();
        PrintWriter pw;
        pw = new PrintWriter(new FileWriter("./src/dataFile/categories.txt", true));
        pw.println(id + "," + category + "," + quantity);
        pw.close();
    }

    // This method is used to check if the category belongs to the category list in categories.txt file.
    public boolean checkCategory(String category) {

        //Reformat the input to suitable for matching values.
        String capital = category.toUpperCase();
        boolean found = false;
        try {
            Scanner fileScanner = new Scanner(new File("./src/dataFile/categories.txt"));

            // while loop to check if the category belong to exist categories list.
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] values = line.split(",");
                if (capital.equals(values[1])) {
                    found = true;
                }
            }
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
        return found;
    }

    // Display all the product with the id for customer to choose
    public void getProductHaveId() throws FileNotFoundException {
        ArrayList<String[]> user = new ArrayList<>();
        Scanner fileProducts = new Scanner(new File("./src/dataFile/items.txt"));

        while (fileProducts.hasNext()) {
            String[] productData = new String[3];
            String line = fileProducts.nextLine();
            StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
            String ID = stringTokenizer.nextToken();
            String title = stringTokenizer.nextToken();
            String price = stringTokenizer.nextToken();
            String category = stringTokenizer.nextToken();
            productData = new String[]{ID, title, price, category};
            user.add(productData);
        }

        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("OPTION", "ID", "TITLE", "PRICE", "CATEGORY");

        for (int i = 1; i < user.size(); i++) {
            CreateTable.addRow(String.valueOf(i), user.get(i)[0], user.get(i)[1], user.get(i)[2], user.get(i)[3]);
        }

        CreateTable.print();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
    }

    // Display the price range
    public void printPriceRange() {
        System.out.println("1. Below 25 million VND.");
        System.out.println("2. 25 million VND to 50 million VND.");
        System.out.println("3. 50 million VND to 75 million VND.");
        System.out.println("4. 75 million VND to 100 million VND.");
    }

    public ArrayList<Long> getAllPrice() throws IOException {
        // Use the read column method to get prices
        String[] readPrices = ReadDataFromTXTFile.readColString(2, "./src/dataFile/items.txt", ",");

        // Creating an arraylist of prices
        ArrayList<Long> pricesList = new ArrayList<>(readPrices.length);

        // Prepping the price list to be able to sort
        for (int i = 1; i < readPrices.length; i++) {
            pricesList.add(Long.parseLong(readPrices[i])); // Created an ArrayList of Long value for prices
        }
        return pricesList;
    }

    public void findItemByPriceRange() throws InterruptedException {
        ArrayList<String[]> items = ReadDataFromTXTFile.readAllLines("./src/dataFile/items.txt");
        printPriceRange();
        String option = UserInput.rawInput();
        boolean check = false;
        switch (option) {
            // Case 1: find the product between the price of 0 to 25000000.
            case "1":
                for (int i = 1; i < items.size(); i++) {
                    Long priceItem = Long.parseLong(items.get(i)[2]);
                    if (0 <= priceItem && priceItem < 25000000) {
                        CreateTable.addRow(items.get(i)[0], items.get(i)[1], items.get(i)[2], items.get(i)[3]);
                    }
                }
                System.out.println("Price range: 0 --> 25 mil");
                check = true;
                break;

            // Case 2: find the product between the price of 25000000 to 50000000.
            case "2":
                for (int i = 1; i < items.size(); i++) {
                    Long priceItem = Long.parseLong(items.get(i)[2]);
                    if (25000000 <= priceItem && priceItem < 50000000) {
                        CreateTable.addRow(items.get(i)[0], items.get(i)[1], items.get(i)[2], items.get(i)[3]);
                    }
                }
                System.out.println("Price range: 25 mil --> 50 mil");
                check = true;
                break;

            // Case 3: find the product between the price of 50000000 to 75000000.
            case "3":
                for (int i = 1; i < items.size(); i++) {
                    Long priceItem = Long.parseLong(items.get(i)[2]);
                    if (50000000 <= priceItem && priceItem < 75000000) {
                        CreateTable.addRow(items.get(i)[0], items.get(i)[1], items.get(i)[2], items.get(i)[3]);
                    }
                }
                System.out.println("Price range: 50 mil --> 75 mil");
                check = true;
                break;

            // Case 4: find the product between the price of 75000000 to 100000000.
            case "4":
                for (int i = 1; i < items.size(); i++) {
                    Long priceItem = Long.parseLong(items.get(i)[2]);
                    if (75000000 <= priceItem && priceItem < 100000000) {
                        CreateTable.addRow(items.get(i)[0], items.get(i)[1], items.get(i)[2], items.get(i)[3]);
                    }
                }
                System.out.println("Price range: 75 mil --> 100 mil");
                check = true;
                break;
            default:
                System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                TimeUnit.SECONDS.sleep(1);
                check = false;
                findItemByPriceRange();
                // for menu add 1 more but will be menu.something();
        }
        if (check) {
            CreateTable.setShowVerticalLines(true);
            CreateTable.setHeaders("ID", "Title", "Prices", "Category");
            CreateTable.print();
            CreateTable.setHeaders(new String[0]);
            CreateTable.setRows(new ArrayList<String[]>());
        }
    }

    // This method is used to gather and print all the product information.
    public void getAllProductInfo() throws FileNotFoundException {
        ArrayList<String[]> user = new ArrayList<>();
        Scanner fileProducts = new Scanner(new File("./src/dataFile/items.txt"));

        // The while loop is used to get info of each product in the scanner.
        while (fileProducts.hasNext()) {
            String[] productData = new String[3];
            String line = fileProducts.nextLine();
            StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
            String ID = stringTokenizer.nextToken();
            String title = stringTokenizer.nextToken();
            String price = stringTokenizer.nextToken();
            String category = stringTokenizer.nextToken();
            productData = new String[]{ID, title, price, category};
            user.add(productData);

        }
        // Print out the table contain all the product information.
        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("ID", "TITLE", "PRICE", "CATEGORY");

        for (int i = 1; i < user.size(); i++) {
            CreateTable.addRow(user.get(i)[0], user.get(i)[1], user.get(i)[2], user.get(i)[3]);
        }

        CreateTable.print();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
    }

    // This method allow admin to modify a product's price that had existed in items' file
    public void updatePrice(String filepath, String newData) throws IOException {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/dataFile/items.txt");
        for (String[] strings : database) {
            if (strings[0].equals(this.getID()))
                /* If the system could find out the pID in items' file
                 * then the system allow admin to update the product's price
                 */ {
                this.setPrice(Long.parseLong(newData));
                strings[2] = String.valueOf(this.getPrice()); // Modify the product's price
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

    // Display all the category
    public void getAllCategory() {
        ArrayList<String[]> allCategory = ReadDataFromTXTFile.readAllLines("./src/dataFile/categories.txt");
        ArrayList<String> category = new ArrayList<>();
        for (String[] strings : allCategory) {
            category.add(strings[1]);
        }
        CreateTable.setShowVerticalLines(true);
        CreateTable.setHeaders("CATEGORY");
        for (int i = 1; i < category.size(); i++) {
            CreateTable.addRow(category.get(i));
        }
        CreateTable.print();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());
    }

    //     Getter method for pID
    public String getID() {
        return ID;
    }

    // Setter method for pID
    public void setID(String ID) {
        this.ID = ID;
    }

    // Getter method for title
    public String getTitle() {
        return title;
    }

    // Getter method for price
    public Long getPrice() {
        return price;
    }

    // Setter method for price
    public void setPrice(Long price) {
        this.price = price;
    }
}
