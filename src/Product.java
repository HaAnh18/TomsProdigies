import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Product {
    // Attributes
    // ArrayList<String> categories = new ArrayList<>(Arrays.asList(ReadDataFromTXTFile.readColString(3, "./src/items.txt", ",")));
    private String ID;
    private String title;
    private Long price;
    private String category;

    public Product(String ID, String title, Long price, String category) throws IOException {
        this.ID = ID;
        this.title = title;
        this.price = price;
        this.category = category;
    }

    public Product() throws IOException {
    }

    public void registerCategory(String category) throws IOException, InterruptedException, ParseException {
        AdminMenu adminMenu = new AdminMenu();
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/categories.txt");
        String capital = category.substring(0, 1).toUpperCase() + category.substring(1);
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[1].equals(capital)) {
                database.get(i)[2] = String.valueOf(Integer.parseInt(database.get(i)[2]) + 1);
                File file = new File("./src/categories.txt");
                PrintWriter pw = new PrintWriter(file);
                pw.write("");
                pw.close();

                for (String[] obj : database) {
                    Write.rewriteFile("./src/productsSold.txt", "#ID,pID,Quantities", String.join(",", obj));
                }
            }
        }
        if (!checkCategory(category)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("This category do not exist in category list yet!");
            System.out.println("Please choose your option: ");
            System.out.println("1. Create new category automatically");
            System.out.println("2. Exit");
            String option = UserInput.rawInput();
            switch (option) {
                case "1":
                    createNewCategory(category);
                    break;
                case "2":
                    adminMenu.viewHomepage();
            }
        }
    }

    /* This method will help user to search by category */
    public static void searchByCategory() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the category:");
        String category = sc.nextLine();
        String capital = category.substring(0, 1).toUpperCase() + category.substring(1);
        try {
            Scanner fileScanner = new Scanner(new File("./src/items.txt"));
            boolean found = false;
            while (fileScanner.hasNext()) {
                String data = fileScanner.nextLine();
                String[] items = data.split(",");
                if (capital.equals(items[3])) {
                    found = true;
                    break;
                } else {
                    found = false;
                }

            }
            if (found) {
                ArrayList<String[]> categories = new ArrayList<>();
                ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/items.txt");
                for (int i = 1; i < database.size(); i++) {
                    if (database.get(i)[3].equals(capital))
                    // If the system could find out the category in items.txt file
                    {
                        categories.add(database.get(i));
                    }
                }
                CreateTable createTable = new CreateTable();
                createTable.setShowVerticalLines(true);
                createTable.setHeaders("ID", "Title", "Prices", "Category");
                for (String[] categoryOutput : categories) {
                    createTable.addRow(categoryOutput[0], categoryOutput[1], categoryOutput[2], categoryOutput[3]);
                }
                createTable.print();
            } else {
                System.out.println("This category is not existed!");
                searchByCategory();
            }
        } catch (FileNotFoundException e) {
            searchByCategory();
        }
    }

    //This method is used to create new category and add to the categories.txt file.
    public void createNewCategory(String category) throws IOException {
        Path path = Paths.get("./src/categories.txt");
        int id = (int) Files.lines(path).count();
        PrintWriter writer = new PrintWriter(new FileWriter("./src/categories.txt", true));
        writer.print("\n" + id + "," + category + "," + 1);
        writer.close();
    }

    //This method is used to check if the category belongs to the category list in categories.txt file.
    public boolean checkCategory(String category) {

        //Reformat the input to suitable for matching values.

        String capital = category.substring(0, 1).toUpperCase() + category.substring(1);
        boolean found = false;
        try {
            Scanner fileScanner = new Scanner(new File("./src/categories.txt"));

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

    public void getProductHaveId() throws FileNotFoundException {
        ArrayList<String[]> user = new ArrayList<>();
        Scanner fileProducts = new Scanner(new File("./src/items.txt"));

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

        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("OPTION","ID", "TITLE", "PRICE", "CATEGORY");

        for (int i = 1; i < user.size(); i++) {
            createTable.addRow(String.valueOf(i),user.get(i)[0], user.get(i)[1], user.get(i)[2], user.get(i)[3]);
        }

        createTable.print();
    }

    public ArrayList<Long> getAllPrice() throws IOException {
        // Use the read column method to get prices
        String[] readPrices = ReadDataFromTXTFile.readColString(2, "./src/items.txt", ",");

        // Creating an arraylist of prices
        ArrayList<Long> pricesList = new ArrayList<>(readPrices.length);

        // Prepping the price list to be able to sort
        for (int i = 1; i < readPrices.length; i++) {
            pricesList.add(Long.parseLong(readPrices[i])); // Created an ArrayList of Long value for prices
        }
        return pricesList;
    }

    public static void printPriceRange() {
        System.out.println("1. Below 25 million VND.");
        System.out.println("2. 25 million VND to 50 million VND.");
        System.out.println("3. 50 million VND to 75 million VND.");
        System.out.println("4. 75 million VND to 100 million VND.");
    }

    public void findItemByPriceRange() throws IOException {
        ArrayList<String[]> items = ReadDataFromTXTFile.readAllLines("./src/items.txt");

        String option = UserInput.rawInput();
        CreateTable table = new CreateTable();

        switch (option) {
            //case 1: find the product between the price of 0 to 25000000.
            case "1":
                for (int i = 1; i < items.size(); i++) {
                    Long priceItem = Long.parseLong(items.get(i)[2]);
                    if (0 <= priceItem && priceItem < 25000000) {
                        table.addRow(items.get(i)[0], items.get(i)[1], items.get(i)[2], items.get(i)[3]);
                    }
                }
                System.out.println("Price range: 0 --> 25 mil");
                break;

            //case 2: find the product between the price of 25000000 to 50000000.
            case "2":

                for (int i = 1; i < items.size(); i++) {
                    Long priceItem = Long.parseLong(items.get(i)[2]);
                    if (25000000 <= priceItem && priceItem < 50000000) {
                        table.addRow(items.get(i)[0], items.get(i)[1], items.get(i)[2], items.get(i)[3]);
                    }
                }
                System.out.println("Price range: 25 mil --> 50 mil");
                break;

            //case 3: find the product between the price of 50000000 to 75000000.
            case "3":
                for (int i = 1; i < items.size(); i++) {
                    Long priceItem = Long.parseLong(items.get(i)[2]);
                    if (50000000 <= priceItem && priceItem < 75000000) {
                        table.addRow(items.get(i)[0], items.get(i)[1], items.get(i)[2], items.get(i)[3]);
                    }
                }
                System.out.println("Price range: 50 mil --> 75 mil");
                break;


            //case 4: find the product between the price of 75000000 to 100000000.
            case "4":
                for (int i = 1; i < items.size(); i++) {
                    Long priceItem = Long.parseLong(items.get(i)[2]);
                    if (75000000 <= priceItem && priceItem < 100000000) {
                        table.addRow(items.get(i)[0], items.get(i)[1], items.get(i)[2], items.get(i)[3]);
                    }
                }
                System.out.println("Price range: 75 mil --> 100 mil");
                break;
            // for menu add 1 more but will be menu.something();
        }
        table.setShowVerticalLines(true);
        table.setHeaders("ID", "Title", "Prices", "Category");
        table.print();
    }


    // This method is used to gather and print all the product information.
    public void getAllProductInfo() throws FileNotFoundException {
        ArrayList<String[]> user = new ArrayList<>();
        Scanner fileProducts = new Scanner(new File("./src/items.txt"));

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
    //Print out the table contain all the product information.
        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("ID", "TITLE", "PRICE", "CATEGORY");

        for (int i = 1; i < user.size(); i++) {
            createTable.addRow(user.get(i)[0], user.get(i)[1], user.get(i)[2], user.get(i)[3]);
        }

        createTable.print();
    }

    public static void searchCategoryByPriceRange(String category) throws IOException {
        //transforming user input to the right format.
        String capital = category.substring(0, 1).toUpperCase() + category.substring(1);

        //create a empty Arraylist to store data after searching.
        ArrayList<String[]> categories = new ArrayList<>();

        //temporary database to store data.
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/items.txt");
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[3].equals(capital))
                /* If the system could find out the category in items.txt file
                 */ {
                categories.add(database.get(i));

            }
        }

        Product.printPriceRange();
        String option = UserInput.rawInput();


//        ArrayList<String[]> matchResult = new ArrayList<>(this.getMatchResult(category[0]).size());

        CreateTable table = new CreateTable();

        switch (option) {
            case "1":
                for (int i = 0; i < categories.size(); i++) {
                    Long priceItem = Long.parseLong(categories.get(i)[2]);
                    if (0 <= priceItem && priceItem < 25000000) {
                        table.addRow(categories.get(i)[0], categories.get(i)[1], categories.get(i)[2], categories.get(i)[3]);
                    }
                }
                System.out.println("Price range: 0 --> 25 mil");
                break;
            case "2":
                for (int i = 0; i < categories.size(); i++) {
                    Long priceItem = Long.parseLong(categories.get(i)[2]);
                    if (25000000 <= priceItem && priceItem < 50000000) {
                        table.addRow(categories.get(i)[0], categories.get(i)[1], categories.get(i)[2], categories.get(i)[3]);
                    }
                }
                System.out.println("Price range: 25 mil --> 50 mil");
                break;
            case "3":
                for (int i = 0; i < categories.size(); i++) {
                    Long priceItem = Long.parseLong(categories.get(i)[2]);
                    if (50000000 <= priceItem && priceItem < 75000000) {
                        table.addRow(categories.get(i)[0], categories.get(i)[1], categories.get(i)[2], categories.get(i)[3]);
                    }
                }
                System.out.println("Price range: 50 mil --> 75 mil");
                break;
            case "4":
                for (int i = 0; i < categories.size(); i++) {
                    Long priceItem = Long.parseLong(categories.get(i)[2]);
                    if (75000000 <= priceItem && priceItem < 100000000) {
                        table.addRow(categories.get(i)[0], categories.get(i)[1], categories.get(i)[2], categories.get(i)[3]);
                    }
                }
                System.out.println("Price range: 75 mil --> 100 mil");
                break;
            // for menu add 1 more but will be menu.something();
        }
        table.setShowVerticalLines(true);
        table.setHeaders("ID", "Title", "Prices", "Category");
        table.print();

    }
    // Getter method for pID
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

    // Getter method for category
    public String getCategory() {
        return category;
    }

    // Setter method for category
    public void setCategory(String category) {
        this.category = category;
    }
}
