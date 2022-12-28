import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class AdminMenu {
    public void view() throws IOException, InterruptedException {
        System.out.println("1. Login by admin account");
        System.out.println("2. Back to authentication system");
        System.out.println("3. Exit");

        AuthenticationSystem authenticationSystem = new AuthenticationSystem();
        String option = UserInput.rawInput();
        switch (option) {
            case "1":
                this.login();
            case "2":
                authenticationSystem.mainMenu();
            case "3":
                System.out.println("Thank you so much for using our system. See you soon !!!!");
                System.out.println("COSC2081 GROUP ASSIGNMENT");
                System.out.println("STORE ORDER MANAGEMENT SYSTEM");
                System.out.println("Instructor: Mr. Tom Huynh & Dr. Phong Ngo");
                System.out.println("Group: Tom's Prodigies");
                CreateTable createTable = new CreateTable();
                createTable.setShowVerticalLines(true);

                createTable.setHeaders("sID", "FULL NAME");
                createTable.addRow("s3938490", "Nguyen Tran Ha Anh");
                createTable.addRow("s3924716", "Hoang Tuan Minh");
                createTable.addRow("s3938024", "Dang Kim Quang Minh");
                createTable.addRow("s3938143", "Nguyen Gia Bao");
                createTable.print();
                System.exit(1);
        }
    }

    public void login() throws IOException, InterruptedException {
        boolean cookies = false;
        Admin admin = new Admin();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        if (!admin.verifyAdmin(username, password)) {
            System.out.println("Wrong password, try again !!!!!");
            System.out.println("Enter username: ");
            username = scanner.nextLine();
            System.out.println("Enter password: ");
            password = scanner.nextLine();
        }
        cookies = true;
        System.out.println("Login Status: " + cookies);
        this.viewHomepage();
    }

    public void viewHomepage() throws IOException, InterruptedException {
        System.out.println("\n1. List all products' information");
        System.out.println("2. List all orders' information");
        System.out.println("3. List all customers' information");
        System.out.println("4. Update product");
        System.out.println("5. Update price");
        System.out.println("6. Search order by customer ID");
        System.out.println("7. Update status of the order");
        System.out.println("8. List all statistics");
        System.out.println("9. List all orders in particular day");
        System.out.println("10. Update category");
        System.out.println("11. List all membership types");
        System.out.println("12. Remove customer by customer ID");
        System.out.println("13. Log out");
        System.out.println("14. Exit");

        Scanner scanner = new Scanner(System.in);
        Product product = new Product();
        Admin admin = new Admin();
        Order order = new Order();
        AdminMenu adminMenu = new AdminMenu();

        String choice = UserInput.rawInput();
        switch (choice) {
            case "1":
                product.getAllProductInfo();
                TimeUnit.SECONDS.sleep(1);
                adminMenu.viewHomepage();
            case "2":
                order.getAllOrderInfo();
                TimeUnit.SECONDS.sleep(1);
                adminMenu.viewHomepage();
            case "3":
                admin.getAllCustomerInfo();
                TimeUnit.SECONDS.sleep(1);
                adminMenu.viewHomepage();
            case "4":
                System.out.println("1. Add product");
                System.out.println("2. Remove product");
                System.out.println("3. Back to homepage");
                String option = UserInput.rawInput();
                switch (option) {
                    case "1":
                        admin.addProduct();
                        adminMenu.viewHomepage();
                    case "2":
                        product.getProductHaveId();
                        String choiceOrder = UserInput.rawInput();
                        ArrayList<String[]> productList = ReadDataFromTXTFile.readAllLines("./src/items.txt");
                        String[] productInfo = new String[3];
                        for (int i = 0; i < productList.size(); i++) {
                            if (i == Integer.parseInt(choiceOrder)) {
                                productInfo = ReadDataFromTXTFile.readSpecificLine(productList.get(i)[1], 1, "./src/items.txt", ",");
                            }
                        }
                        admin.deleteProduct("./src/items.txt", productInfo[1], 1);
                        adminMenu.viewHomepage();
                    case "3":
                        adminMenu.viewHomepage();
                }
            case "5":
                product.getProductHaveId();
                String choiceOrder = UserInput.rawInput();
                ArrayList<String[]> productList = ReadDataFromTXTFile.readAllLines("./src/items.txt");
                String[] productInfo = new String[3];
                for (int i = 0; i < productList.size(); i++) {
                    if (i == Integer.parseInt(choiceOrder)) {
                        productInfo = ReadDataFromTXTFile.readSpecificLine(productList.get(i)[1], 1, "./src/items.txt", ",");
                    }
                }
                System.out.println("Enter new price: ");
                Long price = Long.parseLong(scanner.nextLine());
                admin.updatePrice("./src/items.txt", String.valueOf(price), productInfo[0]);
                adminMenu.viewHomepage();
            case "6":
                adminMenu.viewHomepage();
            case "7":
                order.getAllOrderInfo();
                System.out.println("Enter order ID of the order that you want to update: ");
                String oId = scanner.nextLine();
                String status = scanner.nextLine().toUpperCase();
                admin.updateDeliveryStatus("./src/ordersHistory.txt",status,oId);
                adminMenu.viewHomepage();
            case "8":
                adminMenu.viewStatistic();
            case "9":
                adminMenu.viewHomepage();
            case "10":
                System.out.println("1. Add new category");
                System.out.println("2. Delete category");
                System.out.println("3. Back to homepage");

                String categoryOption = UserInput.rawInput();
                switch (categoryOption) {
                    case "1":
                        System.out.println("Enter new category: ");
                        String category = scanner.nextLine();
                        product.createNewCategory(category);
                        adminMenu.viewHomepage();
                    case "2":
                        admin.getAllCategory();
                        System.out.println("Enter category ID that you want to delete:");
                        String delCategory = UserInput.rawInput();
                        ArrayList<String[]> categoryList = ReadDataFromTXTFile.readAllLines("./src/categories.txt");
                        String[] categoryInfo = new String[3];
                        for (int i = 0; i < categoryList.size(); i++) {
                            if (i == Integer.parseInt(delCategory)) {
                                categoryInfo = ReadDataFromTXTFile.readSpecificLine(categoryList.get(i)[1], 1, "./src/categories.txt", ",");
                            }
                        }
                        admin.deleteCategory("./src/categories.txt", categoryInfo[1]);
                        adminMenu.viewHomepage();
                    case "3":
                        adminMenu.viewHomepage();
                }
            case "11":
                admin.getAllMembershipTypes();
                TimeUnit.SECONDS.sleep(1);
                adminMenu.viewHomepage();
            case "12":
                admin.getAllCustomerInfo();
                System.out.println("Enter customer ID that you want to delete: ");
                String delCustomer = UserInput.rawInput();
                admin.deleteCustomer("./src/customers.txt",delCustomer,0);
                adminMenu.viewHomepage();
            case "13":
                adminMenu.view();
            case "14":
                System.out.println("Thank you so much for using our system. See you soon !!!!");
                System.out.println("COSC2081 GROUP ASSIGNMENT");
                System.out.println("STORE ORDER MANAGEMENT SYSTEM");
                System.out.println("Instructor: Mr. Tom Huynh & Dr. Phong Ngo");
                System.out.println("Group: Tom's Prodigies");
                CreateTable createTable = new CreateTable();
                createTable.setShowVerticalLines(true);

                createTable.setHeaders("sID", "FULL NAME");
                createTable.addRow("s3938490", "Nguyen Tran Ha Anh");
                createTable.addRow("s3924716", "Hoang Tuan Minh");
                createTable.addRow("s3938024", "Dang Kim Quang Minh");
                createTable.addRow("s3938143", "Nguyen Gia Bao");
                createTable.print();
                System.exit(1);
        }
    }

    public void viewStatistic() throws IOException, InterruptedException {
        System.out.println("1. List total revenue");
        System.out.println("2. List total revenue in particular day");
        System.out.println("3. List the most popular product");
        System.out.println("4. List the least popular product");
        System.out.println("5. List the customer pays the most in store");
        System.out.println("6. List all the types of membership");
        System.out.println("7. Back to homepage");

        Admin admin = new Admin();
        AdminMenu adminMenu = new AdminMenu();
        String adChoice = UserInput.rawInput();
        switch (adChoice) {
            case "1":
                admin.calculateRevenue(admin.getTotalRevenue());
                TimeUnit.SECONDS.sleep(1);
                adminMenu.viewStatistic();
            case "2" :
                adminMenu.viewStatistic();
            case "3":
//                admin.getBestSeller();
                TimeUnit.SECONDS.sleep(1);
                adminMenu.viewStatistic();
            case "4":
                adminMenu.viewStatistic();
            case "5":
                admin.getMostSpender();
                TimeUnit.SECONDS.sleep(1);
                adminMenu.viewStatistic();
            case "6":
                adminMenu.viewStatistic();
            case "7":
                adminMenu.viewHomepage();
        }
    }
}