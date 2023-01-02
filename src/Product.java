import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Product {
    ArrayList<String> categories = new ArrayList<>(Arrays.asList(ReadDataFromTXTFile.readColString(3, "./src/items.txt", ",")));
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

    public void registerCategory(String category) throws IOException {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/categories.txt");
        String capital = category.substring(0, 1).toUpperCase() + category.substring(1);
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[1].equals(capital)) {
                database.get(i)[2] = String.valueOf(Integer.parseInt(database.get(i)[2]) + 1);
                File file = new File("./src/categories.txt");
                PrintWriter pw = new PrintWriter(file);
                pw.write("");
                pw.close();

                ArrayList<String[]> newDatabase = database;

                for (String[] obj : newDatabase) {
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
            int option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    createNewCategory(category);
                    break;
            }
        }
    }

    public void createNewCategory(String category) throws IOException {
        Path path = Paths.get("./src/categories.txt");
        int id = (int) Files.lines(path).count();
        PrintWriter writer = new PrintWriter(new FileWriter("./src/categories.txt", true));
        writer.print("\n" + id + "," + category + "," + 1);
        writer.close();
    }

    public boolean checkCategory(String category) {
        String capital = category.substring(0, 1).toUpperCase() + category.substring(1);
        boolean found = false;
        try {
            Scanner fileScanner = new Scanner(new File("./src/categories.txt"));

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

    public void getAllProductInfo() throws FileNotFoundException {
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
        createTable.setHeaders("ID", "TITLE", "PRICE", "CATEGORY");

        for (int i = 1; i < user.size(); i++) {
            createTable.addRow(user.get(i)[0], user.get(i)[1], user.get(i)[2], user.get(i)[3]);
        }

        createTable.print();
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


    public static void findItemByPriceRange() throws IOException {
        ArrayList<String[]> items = ReadDataFromTXTFile.readAllLines("./src/items.txt");

        String option = UserInput.rawInput();


//        ArrayList<String[]> matchResult = new ArrayList<>(this.getMatchResult(category[0]).size());

        CreateTable table = new CreateTable();

        switch (option) {
            case "1":
                for (int i = 1; i < items.size(); i++) {
                    Long priceItem = Long.parseLong(items.get(i)[2]);
                    if (0 < priceItem && priceItem < 25000000) {
                        table.addRow(items.get(i)[0], items.get(i)[1], items.get(i)[2], items.get(i)[3]);
                    }
                }
                System.out.println("Price range: 0 --> 25 mil");
                break;
            case "2":

                for (int i = 1; i < items.size(); i++) {
                    Long priceItem = Long.parseLong(items.get(i)[2]);
                    if (25000000 < priceItem && priceItem < 50000000) {
                        table.addRow(items.get(i)[0], items.get(i)[1], items.get(i)[2], items.get(i)[3]);
                    }
                }
                System.out.println("Price range: 25 mil --> 50 mil");
                break;
            case "3":
                for (int i = 1; i < items.size(); i++) {
                    Long priceItem = Long.parseLong(items.get(i)[2]);
                    if (5000000 < priceItem && priceItem < 75000000) {
                        table.addRow(items.get(i)[0], items.get(i)[1], items.get(i)[2], items.get(i)[3]);
                    }
                }
                System.out.println("Price range: 50 mil --> 75 mil");
                break;
            case "4":
                for (int i = 1; i < items.size(); i++) {
                    Long priceItem = Long.parseLong(items.get(i)[2]);
                    if (75000000 < priceItem && priceItem < 100000000) {
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

    /* This method will help user to search by category */
  public static void searchByCategory(String category) throws IOException{
      String capital = category.substring(0, 1).toUpperCase() + category.substring(1);
      ArrayList<String[]> categories = new ArrayList<>();
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/items.txt");
      for (int i = 1; i < database.size(); i++) {
          if (database.get(i)[3].equals(capital))
              /* If the system could find out the category in items.txt file
               */ {
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
  }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }



    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
