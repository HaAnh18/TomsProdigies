import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

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
}
