import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class AdminMenu {
    public void view() throws IOException, ParseException, InterruptedException {
        // Display a menu for user before login
        System.out.println("\n================================================= WELCOME TO TOM'S PRODIGIES STORE =================================================");
        System.out.println("1. Login by admin account");
        System.out.println("2. Back to authentication system");
        System.out.println("3. Exit");

        boolean validUser = false;
        PointsSystem pointShop = new PointsSystem();
        Admin admin = new Admin();
        AuthenticationSystem authenticationSystem = new AuthenticationSystem();
        String option = UserInput.rawInput();
        switch (option) {
            case "1":
                System.out.println("\n================================================= LOGIN FORM =================================================");
                do {
                    // Ask user to input username and password
                    // if the username and password are not correct, the system will ask user to input again
                    // otherwise, the system will change to the homepage after login
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    if (!admin.verifyAdmin(username, password)) {
                        System.out.println("Wrong password, try again !!!!!");

                    } else {
                        this.viewHomepage();
                        validUser = true;
                    }
                } while (!validUser);

            case "2":
                authenticationSystem.mainMenu();
            case "3":
                System.exit(1);
            default:
                // If customer input another option that don't have in the menu
                // then the system will give he/she message and back to the viewpage
                System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                TimeUnit.SECONDS.sleep(1);
                this.view();
        }
    }

    public void viewHomepage() throws IOException, InterruptedException, ParseException {
        System.out.println("\n================================================= HOMEPAGE =================================================");
        System.out.println("\n1. List all products' information");
        System.out.println("2. List all orders' information");
        System.out.println("3. List all customers' information");
        System.out.println("4. List all statistics");
        System.out.println("5. List all orders in particular day");
        System.out.println("6. List all membership types");
        System.out.println("7. Search order by customer ID");
        System.out.println("8. Update product");
        System.out.println("9. Update price");
        System.out.println("10. Update status of the order");
        System.out.println("11. Update category");
        System.out.println("12. Manage point store");
        System.out.println("13. Remove customer by customer ID");
        System.out.println("14. Log out");
        System.out.println("15. Exit");

        Scanner scanner = new Scanner(System.in);
        Product product = new Product();
        Admin admin = new Admin();
        Order order = new Order();
        AdminMenu adminMenu = new AdminMenu();

        String choice = UserInput.rawInput();
        switch (choice) {
            case "1":
                // Display the information of all products
                product.getAllProductInfo();
                TimeUnit.SECONDS.sleep(1);
                adminMenu.viewHomepage();
            case "2":
                // Display the information of all orders
                order.getAllOrderInfo();
                TimeUnit.SECONDS.sleep(1);
                adminMenu.viewHomepage();
            case "3":
                // Display the information of all customers
                admin.getAllCustomerInfo();
                TimeUnit.SECONDS.sleep(1);
                adminMenu.viewHomepage();
            case "4":
                adminMenu.viewStatistic();
            case "5":
                order.getOrderByDate();
                TimeUnit.SECONDS.sleep(1);
                adminMenu.viewHomepage();
            case "6":
                // Display all the membership types
                admin.getAllMembershipTypes();
                TimeUnit.SECONDS.sleep(1);
                adminMenu.viewHomepage();
            case "7":
                // Search the customer information by customer ID
                System.out.print("Enter customer ID: ");
                String cID = scanner.nextLine();
                order.getOrderInfoByCID(cID);
                TimeUnit.SECONDS.sleep(1);
                adminMenu.viewHomepage();
            case "8":
                // Allow user to add more product or remove any product
                System.out.println("\n================================================= UPDATING PRODUCT =================================================");
                System.out.println("1. Add product");
                System.out.println("2. Remove product");
                System.out.println("3. Back to homepage");
                String option = UserInput.rawInput();
                switch (option) {
                    case "1":
                        admin.addProduct();
                        TimeUnit.SECONDS.sleep(1);
                        adminMenu.viewHomepage();
                        adminMenu.viewHomepage();
                    case "2":
                        admin.deleteProduct();
                        TimeUnit.SECONDS.sleep(1);
                        adminMenu.viewHomepage();
                        adminMenu.viewHomepage();
                    case "3":
                        adminMenu.viewHomepage();
                }
            case "9":
                // Allow admin to change the price of the product
                System.out.println("\n================================================= UPDATING PRICE =================================================");
                product.getProductHaveId();
                String choiceOrder = UserInput.rawInput();
                ArrayList<String[]> productList = ReadDataFromTXTFile.readAllLines("./src/items.txt");
                String[] productInfo = new String[3];
                for (int i = 0; i < productList.size(); i++) {
                    if (i == Integer.parseInt(choiceOrder)) {
                        productInfo = ReadDataFromTXTFile.readSpecificLine(productList.get(i)[1], 1, "./src/items.txt", ",");
                    }
                }
                System.out.print("Enter new price: ");
                Long price = Long.parseLong(scanner.nextLine());
                admin.updatePrice("./src/items.txt", String.valueOf(price), productInfo[0]);
                adminMenu.viewHomepage();

            case "10":
                // Search the order's information based on order ID
                System.out.println("\n================================================= SEARCHING ORDER =================================================");
                order.getAllOrderInfo();
                System.out.print("Enter order ID of the order that you want to update: ");
                String oId = scanner.nextLine();
                System.out.print("Update order status to:");
                String status = scanner.nextLine().toUpperCase();
                admin.updateDeliveryStatus("./src/ordersHistory.txt", status, oId);
                adminMenu.viewHomepage();

            case "11":
                // Allow user to add more product or remove any product
                System.out.println("\n================================================= UPDATING CATEGORY =================================================");
                System.out.println("1. Add new category");
                System.out.println("2. Delete category");
                System.out.println("3. Back to homepage");

                String categoryOption = UserInput.rawInput();
                switch (categoryOption) {
                    case "1":
                        System.out.print("Enter new category: ");
                        String category = scanner.nextLine();
                        product.createNewCategory(category, 0);
                        adminMenu.viewHomepage();
                    case "2":
                        admin.getAllCategory();
                        System.out.print("Enter category ID that you want to delete:");
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
            case "12":
                System.out.println("\n================================================= MANAGE POINT SHOP =================================================");
                System.out.println("1. View exchange log");
                System.out.println("2. View prizes");
                System.out.println("2. Search exchange order by oID");
                System.out.println("3. Search exchange order by cID");
                System.out.println("4. Update pickup status");
                System.out.println("5. Add new prize item");
                System.out.println("6. Delete prize item");
                System.out.println("7. Exit");

                String pShopOption = UserInput.rawInput();
                Scanner inputs = new Scanner(System.in);

                switch (pShopOption) {
                    case "1":
                        PointsSystem.viewExchangeLog();
                        TimeUnit.SECONDS.sleep(1);
                        adminMenu.viewHomepage();
                    case "2":
                        PointsSystem.viewPrizes();
                    case "3":
                        System.out.print("Please enter oID:");
                        String oID = inputs.nextLine();
                        PointsSystem.searchExchangeOrderByOID(oID);
                        TimeUnit.SECONDS.sleep(1);
                        adminMenu.viewHomepage();

                    case "4":
                        System.out.print("Please enter cID:");
                        String customerID = inputs.nextLine();
                        PointsSystem.getExchangeInfo(customerID);
                        TimeUnit.SECONDS.sleep(1);
                        adminMenu.viewHomepage();

                    case "5":
                        PointsSystem.viewExchangeLog();
                        System.out.print("Enter oID you want to update:");
                        String targetOID = inputs.nextLine();
                        System.out.print("Enter new status:");
                        String exStatus = inputs.nextLine();
                        PointsSystem.updatePickupStatus(exStatus, targetOID);
                        TimeUnit.SECONDS.sleep(1);
                        adminMenu.viewHomepage();

                    case "6":
                        PointsSystem.addPrize();
                        TimeUnit.SECONDS.sleep(1);
                        adminMenu.viewHomepage();

                    case "7":
                        PointsSystem.deletePrize();
                        TimeUnit.SECONDS.sleep(1);
                        adminMenu.viewHomepage();

                    case "8":
                        adminMenu.viewHomepage();

                    default:
                        System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                        TimeUnit.SECONDS.sleep(1);
                        adminMenu.viewHomepage();
                }
            case "13":
                // Admin could delete customer by using customer's ID
                System.out.println("\n================================================= DELETING CUSTOMER =================================================");
                admin.getAllCustomerInfo();
                System.out.print("Enter customer ID that you want to delete: ");
                String delCustomer = UserInput.rawInput();
                admin.deleteCustomer("./src/customers.txt", delCustomer, 0);
                adminMenu.viewHomepage();
            case "14":
                adminMenu.view();
            case "15":
                System.exit(1);
            default:
                System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                TimeUnit.SECONDS.sleep(1);
                adminMenu.viewHomepage();
        }
    }

    public void viewStatistic() throws IOException, InterruptedException, ParseException
    // Display all the statistic related to the revenue of the store as well as the most/the least popular product and the most spender in store
    {
        System.out.println("\n================================================= ALL STATISTICS =================================================");
        System.out.println("1. List total revenue");
        System.out.println("2. List total revenue in particular day");
        System.out.println("3. List the most popular product");
        System.out.println("4. List the least popular product");
        System.out.println("5. List the customer pays the most in store");
        System.out.println("6. Back to homepage");

        Admin admin = new Admin();
        AdminMenu adminMenu = new AdminMenu();
        String adChoice = UserInput.rawInput();
        SortProduct sortProduct = new SortProduct();
        switch (adChoice) {
            case "1":
                // Display the total revenue of the store
                admin.calculateRevenue(admin.getTotalRevenue());
                TimeUnit.SECONDS.sleep(1);
                adminMenu.viewStatistic();
            case "2":
                // Display the daily revenue of the store
                CreateTable dailyRevenue = new CreateTable();
                dailyRevenue.setShowVerticalLines(true);
                dailyRevenue.setHeaders("DAILY REVENUE");
                dailyRevenue.addRow(String.valueOf(admin.getDailyRevenue()));
                adminMenu.viewStatistic();
            case "3":
                // Display the most popular product in store
                sortProduct.getBestSeller();
                TimeUnit.SECONDS.sleep(1);
                adminMenu.viewStatistic();
            case "4":
                // Display the least popular product in store
                sortProduct.getLeastSeller();
                TimeUnit.SECONDS.sleep(1);
                adminMenu.viewStatistic();
            case "5":
                // Display the customer that spend the most in store
                admin.getMostSpender();
                TimeUnit.SECONDS.sleep(1);
                adminMenu.viewStatistic();
            case "6":
                adminMenu.viewHomepage();
            default:
                System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                TimeUnit.SECONDS.sleep(1);
                adminMenu.viewStatistic();

        }
    }
}
