import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CustomerMenu {
    private boolean cookies = false;

    public void view() throws IOException, InterruptedException {
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. List all product");
        System.out.println("4. Search product");
        System.out.println("5. Sort product");
        System.out.println("6. List all membership types");
        System.out.println("6. Back to authentication menu");
        System.out.println("7. Exit");

        CustomerMenu customerMenu = new CustomerMenu();
        AuthenticationSystem authenticationSystem = new AuthenticationSystem();
        Scanner scanner = new Scanner(System.in);
        String option = UserInput.rawInput();
//        int option = Integer.parseInt(scanner.nextLine());
        Account customer = new Account();
        Product product = new Product();

        switch (option) {
            case "1":
                customer.register();
                System.out.println("Register Successfully");
                customerMenu.view();
            case "2":
                this.cookies = false;
                System.out.println("Enter username: ");
                String username = scanner.nextLine();
                System.out.println("Enter password: ");
                String password = scanner.nextLine();
                if (!customer.login(username,password)) {
                    System.out.println("Wrong password, try again !!!!!");
                    System.out.println("Enter username: ");
                    username = scanner.nextLine();
                    System.out.println("Enter password: ");
                    password = scanner.nextLine();
                }
                this.cookies = true;
                this.viewHomepage(username);
            case "3":
                product.getAllProductInfo();
                TimeUnit.SECONDS.sleep(1);
                customerMenu.view();
            case "4":
                System.out.println("1. Search product by category");
                System.out.println("2. Search product by price range");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        customerMenu.view();
                    case 2:
                        customerMenu.view();
                }
            case "5":
                System.out.println("1. Ascending");
                System.out.println("2. Descending");
                int sortOption = Integer.parseInt(scanner.nextLine());
                customer.sortItems(sortOption);
                TimeUnit.SECONDS.sleep(1);
                customerMenu.view();
            case "6":
                customer.getAllMembershipTypes();
                TimeUnit.SECONDS.sleep(1);
                customerMenu.view();
            case "7":
                authenticationSystem.mainMenu();
            case "8":
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

    public void viewHomepage(String userName) throws IOException, InterruptedException {
        System.out.println("\nCookies (Login Status): " + this.cookies);
        String[] customerInfo = ReadDataFromTXTFile.readSpecificLine(userName,6,"./src/customers.txt",",");
        System.out.println("\nUsername:" + customerInfo[6]+ "\t\tName:" + customerInfo[1]
        + "\t\tEmail:" + customerInfo[2] + "\t\tAddress:" + customerInfo[3]
        + "\t\tPhone:" + customerInfo[4]);
        System.out.println("\n1. Create new order");
        System.out.println("2. List all your order");
        System.out.println("3. Update your information");
        System.out.println("4. List all product");
        System.out.println("5. Search product");
        System.out.println("6. Search order");
        System.out.println("7. Sort product");
        System.out.println("8. List all membership types");
        System.out.println("8. Log out");
        System.out.println("9. Exit");

        Order order = new Order();
        Scanner scanner = new Scanner(System.in);
        String[] obj = ReadDataFromTXTFile.readSpecificLine(userName, 6, "./src/customers.txt", ",");
        Customer member = new Customer(obj[0], obj[1], obj[2], obj[3], obj[4], obj[5], obj[6], obj[7], Long.parseLong(obj[8]));
        CustomerMenu customerMenu = new CustomerMenu();
        Product product = new Product();

        String option = UserInput.rawInput();
        switch (option) {
            case "1":
                product.getProductHaveId();
                String choiceOrder = UserInput.rawInput();
                ArrayList<String[]> productList = ReadDataFromTXTFile.readAllLines("./src/items.txt");
                String[] productInfo = new String[3];
                for (int i = 0; i< productList.size(); i++) {
                    if (i == Integer.parseInt(choiceOrder)) {
                        productInfo = ReadDataFromTXTFile.readSpecificLine(productList.get(i)[1],1,"./src/items.txt",",");
                    }
                }
                Product product1 = new Product((productInfo[0]), productInfo[1], Long.parseLong((productInfo[2])), productInfo[3]);
                order.createNewOrder(member,product1);
                customerMenu.viewHomepage(userName);
            case "2":
                order.getOrderInfo(member);
                TimeUnit.SECONDS.sleep(1);
                customerMenu.viewHomepage(userName);
            case "3":
                System.out.println("Which information you want to update?");
                System.out.println("1. Update your name");
                System.out.println("2. Update your email");
                System.out.println("3. Update your address");
                System.out.println("4. Update your phone");

                String choice = UserInput.rawInput();
                switch (choice) {
                    case "1":
                        System.out.println("Please enter your new name: ");
                        String name = scanner.nextLine();
                        member.updateName("./src/customers.txt", name, userName);
                        customerMenu.viewHomepage(userName);
                    case "2":
                        System.out.println("Please enter your new email: ");
                        String email = scanner.nextLine();
                        member.updateEmail("./src/customers.txt", email, userName);
                        customerMenu.viewHomepage(userName);
                    case "3":
                        System.out.println("Please enter your new address: ");
                        String address = scanner.nextLine();
                        member.updateAddress("./src/customers.txt", address, userName);
                        customerMenu.viewHomepage(userName);
                    case "4":
                        System.out.println("Please enter your new phone: ");
                        String phone = scanner.nextLine();
                        member.updatePhone("./src/customers.txt", phone, userName);
                        customerMenu.viewHomepage(userName);
                }
            case "4":
                product.getAllProductInfo();
                TimeUnit.SECONDS.sleep(1);
                customerMenu.viewHomepage(userName);
            case "5":
                System.out.println("1. Search product by category");
                System.out.println("2. Search product by price range");
                String searchChoice = UserInput.rawInput();
                switch (searchChoice) {
                    case "1":
                        customerMenu.viewHomepage(userName);
                    case "2":
                        customerMenu.viewHomepage(userName);
                }
            case "6":
                System.out.println("Please enter your order ID: ");
                String oId = scanner.nextLine();
                member.searchOrder(oId);
                customerMenu.viewHomepage(userName);
            case "7":
                System.out.println("1. Ascending");
                System.out.println("2. Descending");
                String sortOption = UserInput.rawInput();
                member.sortItems(Integer.parseInt(sortOption));
                TimeUnit.SECONDS.sleep(1);
                customerMenu.viewHomepage(userName);
            case "8":
                member.getAllMembershipTypes();
                TimeUnit.SECONDS.sleep(1);
                customerMenu.viewHomepage(userName);
            case "9":
                customerMenu.view();
            case "10":
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
}
