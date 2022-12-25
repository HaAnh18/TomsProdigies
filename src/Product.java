import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Product {
    ArrayList<String> categories = new ArrayList<>(Arrays.asList(ReadDataFromTXTFile.readCol(3, "./src/items.txt", ",")));
    private String ID;
    private String title;
    private double price;
    private String category;

    public Product(String ID, String title, double price, String category) throws IOException {
        this.ID = ID;
        this.title = title;
        this.price = price;
        this.category = category;
    }

    public Product() throws IOException {
    }

    public void registerCategory(String category) throws IOException {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/categories.txt");
        PrintWriter writer = new PrintWriter(new FileWriter("./src/categories.txt", true));
        String capital = category.substring(0, 1).toUpperCase() + category.substring(1);
        for (String[] strings : database) {
            if (strings[0].equals(capital)) {
                strings[1] = String.valueOf(Integer.parseInt(strings[1]) + 1);
                File file = new File("./src/categories.txt");
                PrintWriter pw = new PrintWriter(file);
                pw.write("");
                pw.close();

                ArrayList<String[]> newDatabase = database;

                for (String[] obj : newDatabase) {
                    Write.rewriteFile("./src/categories.txt", "#Category,Quantity", String.join(",", obj));
                }
            }
        }
        if (!checkCategory(category)) {
            System.out.println("This category do not exist in category list yet!");
            writer.print("\n" + category + "," + 1);
            writer.close();
        }
    }

    public boolean checkCategory(String category) {
        String capital = category.substring(0, 1).toUpperCase() + category.substring(1);
        try {
            Scanner fileScanner = new Scanner(new File("./src/categories.txt"));

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] values = line.split(",");
                if (capital.equals(values[0])) {
                    return true;
                }
            }
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
        return true;
    }

    public void getAllProductInfo() throws FileNotFoundException {
        ArrayList<String[]> user = new ArrayList<>();
        Scanner fileProducts = new Scanner(new File("./src/items.txt"));

        while (fileProducts.hasNext()) {
//            ArrayList<String[]> user = null;
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
//        ArrayList<String[]> user = Read.readAllLine("users.txt");
//        ArrayList<String[]> allProducts = Read.readAllLine("products.txt");

        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("ID", "TITLE", "PRICE", "CATEGORY");
//        CreateTable.setShowVerticalLines(true);
//        CreateTable.setHeaders("ID","NAME","USERNAME","EMAIL","ADDRESS","PHONE","MEMBERSHIP");

        for (int i = 1; i < user.size(); i++) {
//            CreateTable.addRow(user.get(i)[0], user.get(i)[1],user.get(i)[2],user.get(i)[3],user.get(i)[4],user.get(i)[5],user.get(i)[6]);
            createTable.addRow(user.get(i)[0], user.get(i)[1], user.get(i)[2], user.get(i)[3]);
        }

        createTable.print();
//        createTable.setHeaders(new String[0]);
    }

    public ArrayList<Double> getAllPrice() throws IOException {
        // Use the read column method to get prices
        String[] readPrices = ReadDataFromTXTFile.readCol(2,"./src/items.txt",",");

        // Creating an arraylist of prices
        ArrayList<Double> pricesList = new ArrayList<>(readPrices.length);

        // Prepping the price list to be able to sort
        for(int i = 1; i < readPrices.length; i++){
            pricesList.add(Double.parseDouble(readPrices[i]));
        }

        return pricesList;
    }
    //getter and setter

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
