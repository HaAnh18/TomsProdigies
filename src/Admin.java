import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
}
