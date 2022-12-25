import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class searchProduct {
    public static void viewSearch(String uInput) throws IOException {
        String[] categories = ReadDataFromTXTFile.readCol(3, "./scr/items.txt", ",");
        Menu newMenu = new Menu();
        categories = Arrays.stream(categories).distinct().toArray(String[]::new);
        String option = userInput.rawInput();
        ArrayList<String[]> matchResult = new ArrayList<>();

    }

    public static ArrayList<String[]> getMatchResult(String data) throws IOException {
        String[] category = ReadDataFromTXTFile.readSpecificLine("", 3, "./src/items.txt", ",");
        String[] productsName = ReadDataFromTXTFile.readSpecificLine("", 1, "./src/items.txt", ",");
        ArrayList<String[]> matchResult = new ArrayList<>();

        for (int i = 0; i < productsName.length; i++) {
            // Implement Boyer Moore Searching Algorithm
            searchAlgorithm text = new searchAlgorithm(data);
            boolean isFound = text.boyerMooreSearch(category[i], data);

            if (isFound) {
                ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/items.txt");
                System.out.println(database.get(3)[3]);
                for (i = 3; i < database.size(); i++) {
                    matchResult.add(database.get(i));
                }
            }
        }
        return matchResult;
    }

    public static void getAllSearchInfo(String product) throws IOException {
        ArrayList<String[]> results = searchProduct.getMatchResult(product);


    }
}
