import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CustomerMenu {
    private boolean cookies;

    public void view() throws IOException, InterruptedException {
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. List all products");
        System.out.println("4. Search product");
        System.out.println("5. Sort product");
        System.out.println("6. Point store");
        System.out.println("7. List all membership types");
        System.out.println("8. FAQ");
        System.out.println("9. Back to authentication system");
        System.out.println("10. Exit");

        CustomerMenu customerMenu = new CustomerMenu();
        Scanner scanner = new Scanner(System.in);
        String option = UserInput.rawInput();
        Account customer = new Account();
        Product product = new Product();


        switch (option) {
            case "1":
                customer.register();
                System.out.println("Register Successfully");
                customerMenu.view();
            case "2":
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                if (!customer.login(username,password)) {
                    System.out.print("Enter username: ");
                    username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    password = scanner.nextLine();
                }
                this.cookies = true;
                this.viewHomepage(username);
            case "3":
                product.getAllProductInfo();
                TimeUnit.SECONDS.sleep(1);
                customerMenu.view();
            case "4":
                System.out.println("1. Search by category");
                System.out.println("2. Search by price range");
                String choice = UserInput.rawInput();
                switch (choice) {
                    case "1":
                    case "2":
                }
            case "5":
                System.out.println("1. Ascending");
                System.out.println("2. Descending");
                String sortOption = UserInput.rawInput();
                SortProduct.sortItems(Integer.parseInt(sortOption));
                TimeUnit.SECONDS.sleep(1);
                customerMenu.view();
            case "6":
                customerMenu.view();
            case "7":
                customer.getAllMembershipTypes();
                TimeUnit.SECONDS.sleep(1);
                customerMenu.view();
            case "8":
                customerMenu.view();
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

    public void viewHomepage(String username) throws IOException, InterruptedException {
        System.out.println("\nCookies (Login Status): " + this.cookies);
        String[] customerInfo = ReadDataFromTXTFile.readSpecificLine(username, 6, "./src/customers.txt", ",");
        System.out.println("\nUsername:" + customerInfo[6] + "\t\tName:" + customerInfo[1]
                + "\t\tEmail:" + customerInfo[2] + "\t\tAddress:" + customerInfo[3]
                + "\t\tPhone:" + customerInfo[4]);
        System.out.println("\n1. List all products");
        System.out.println("2. Sort product");
        System.out.println("3. Search product");
        System.out.println("4. Create order");
        System.out.println("5. Search order by order ID");
        System.out.println("6. Update your information");
        System.out.println("7. Check your membership");
        System.out.println("8. List all membership types");
        System.out.println("9. Point store");
        System.out.println("10. FAQ");
        System.out.println("11. Log out");
        System.out.println("12. Exit");

        Order order = new Order();
        Scanner scanner = new Scanner(System.in);
        String[] obj = ReadDataFromTXTFile.readSpecificLine(username, 6, "./src/customers.txt", ",");
        Customer member = new Customer(obj[0], obj[1], obj[2], obj[3], obj[4], obj[5], obj[6], obj[7], Long.parseLong(obj[8]), Long.parseLong(obj[9]));
        CustomerMenu customerMenu = new CustomerMenu();
        Product product = new Product();
        Cart cart = new Cart();
        Discount discount = new Discount();
        ArrayList<String[]> cartList = cart.cartList(member);

        String option = UserInput.rawInput();
        switch (option) {
            case "1":
                product.getAllProductInfo();
                TimeUnit.SECONDS.sleep(1);
                customerMenu.viewHomepage(username);
            case "2":
                System.out.println("1. Ascending");
                System.out.println("2. Descending");
                String sortOption = UserInput.rawInput();
                SortProduct.sortItems(Integer.parseInt(sortOption));
                TimeUnit.SECONDS.sleep(1);
                customerMenu.viewHomepage(username);
            case "3":
                System.out.println("1. Search by category");
                System.out.println("2. Search by price range");
                String sort = UserInput.rawInput();
                switch (sort) {
                    case "1":
                    case "2":
                }
            case "4":
                if (cartList.size() == 0) {
                    System.out.println("You don't have any items in your cart. Do you want to add any product to your cart");
                    System.out.println("1. Yes");
                    System.out.println("2. No");
                    String cartOption = UserInput.rawInput();
                    switch (cartOption) {
                        case "1":
                            product.getProductHaveId();
                            String choiceOrder = UserInput.rawInput();
                            System.out.print("Please enter a quantity that you want to add: ");
                            int quantity = Integer.parseInt(scanner.nextLine());
                            ArrayList<String[]> productList = ReadDataFromTXTFile.readAllLines("./src/items.txt");

                            String[] productInfo = new String[3];
                            for (int i = 0; i < productList.size(); i++) {
                                if (i == Integer.parseInt(choiceOrder)) {
                                    productInfo = ReadDataFromTXTFile.readSpecificLine(productList.get(i)[1],
                                            1, "./src/items.txt", ",");
                                }
                            }
                            Product product1 = new Product((productInfo[0]), productInfo[1],
                                    Long.parseLong((productInfo[2])), productInfo[3]);
                            cart.addToCart(member, product1, quantity);
                            System.out.println("Do you want to continue to shopping or create order now");
                            System.out.println("1. Create order");
                            System.out.println("2. Continue to shopping");

                            String shoppingChoice = UserInput.rawInput();
                            switch (shoppingChoice) {
                                case "2":
                                    customerMenu.viewHomepage(username);
                                case "1":
                                    Random rd = new Random();
                                    int i = rd.nextInt(999);
                                    String oID = order.oIDDataForValidate(String.format("O%03d", i));
                                    order.createNewOrder(member, product1, oID, quantity);
                                    member.searchOrder(oID);
                                    order.getTotalPaymentEachOrderId(member, oID);
                                    ArrayList<String[]> discountCode = discount.discountCodeList(member);
                                    if (!(discountCode.size() == 0)) {
                                        System.out.println("You have discount voucher, do you want to use it?");
                                        System.out.println("1. Yes");
                                        System.out.println("2. No");
                                        String discountOption = UserInput.rawInput();
                                        String discountCodeCustomer = new String();
                                        switch (discountOption) {
                                            case "1":
                                                discount.displayCustomerDiscountCode(member);
                                                String choiceOrder1 = UserInput.rawInput();
                                                for (int m = 0; m < discountCode.size(); m++) {
                                                    if (m == Integer.parseInt(choiceOrder1) - 1) {
                                                        discountCodeCustomer = discountCode.get(m)[1];
                                                    }
                                                }
                                                order.getTotalPaymentAfterApplyDiscountCode(oID, discountCodeCustomer, member);
                                                PointsSystem.pointsConversion(oID, member.getcID());
                                                customerMenu.viewHomepage(username);
//                                                    discount.deleteDiscountCode("./customerDiscountCode.txt",discountCodeCustomer);
                                            case "2":
                                                String[] orderInfo = ReadDataFromTXTFile.readSpecificLine(oID, 0,
                                                        "./src/billingHistory.txt", ",");
                                                Long totalPayment = Long.parseLong(orderInfo[2]);
                                                discount.giveDiscountCode(member, totalPayment);
                                                discount.displayCustomerDiscountCode(member);
                                                TimeUnit.SECONDS.sleep(1);
                                                PointsSystem.pointsConversion(oID, member.getcID());
                                                customerMenu.viewHomepage(username);
                                        }
                                    } else {
                                        String[] orderInfo = ReadDataFromTXTFile.readSpecificLine(oID, 0,
                                                "./src/billingHistory.txt", ",");
                                        Long totalPayment = Long.parseLong(orderInfo[2]);
                                        PointsSystem.pointsConversion(oID, member.getcID());
                                        discount.giveDiscountCode(member, totalPayment);
                                    }
                            }
                        case "2":
                            customerMenu.viewHomepage(username);
                    }
                } else {
                    cart.getCustomerCart(member);
                    System.out.println("Do you want to add item(s) to your cart or create order now");
                    System.out.println("1. Continue add item(s)");
                    System.out.println("2. Create order");
                    System.out.println("3. Back to homepage");

                    String continueShopping = UserInput.rawInput();
                    switch (continueShopping) {
                        case "3":
                            customerMenu.viewHomepage(username);
                        case "1":
                            product.getProductHaveId();
                            String choiceOrder = UserInput.rawInput();
                            System.out.println("Please enter a quantity that you want to add: ");
                            int quantity = Integer.parseInt(scanner.nextLine());
                            ArrayList<String[]> productList = ReadDataFromTXTFile.readAllLines("./src/items.txt");

                            String[] productInfo = new String[3];
                            for (int i = 0; i < productList.size(); i++) {
                                if (i == Integer.parseInt(choiceOrder)) {
                                    productInfo = ReadDataFromTXTFile.readSpecificLine(productList.get(i)[1],
                                            1, "./src/items.txt", ",");
                                }
                            }
                            Product product1 = new Product((productInfo[0]), productInfo[1],
                                    Long.parseLong((productInfo[2])), productInfo[3]);
                            cart.addToCart(member, product1, quantity);
                            customerMenu.viewHomepage(username);
                        case "2":
                            cart.getCustomerCart(member);
                            while (cartList.size() > 1) {
                                System.out.println("Do you want to delete any item?");
                                System.out.println("1. Yes");
                                System.out.println("2. No");
                                String delOption = UserInput.rawInput();
                                switch (delOption) {
                                    case "1":
                                        cart.getCustomerCart(member);
                                        String delProduct = UserInput.rawInput();

                                        String[] itemInfo = new String[3];
                                        for (int i = 0; i < cartList.size(); i++) {
                                            if (i == Integer.parseInt(delProduct)) {
                                                itemInfo = ReadDataFromTXTFile.readSpecificLine(cartList.get(i)[1],
                                                        1, "./src/items.txt", ",");
                                            }
                                        }
                                        Product product2 = new Product((itemInfo[0]), itemInfo[1],
                                                Long.parseLong((itemInfo[2])), itemInfo[3]);
                                        cart.deleteItemInCart("./src/customerCart.txt", member.getcID(), product2);
                                        cart.getCustomerCart(member);
                                        customerMenu.viewHomepage(username);
                                    case "2":
                                        Random rd = new Random();
                                        int i = rd.nextInt(999);
                                        String oID = order.oIDDataForValidate(String.format("O%03d", i));
                                        for (int a = 0; a < cartList.size(); a++) {
                                            String[] productInfo1 = new String[3];
                                            productInfo1 = ReadDataFromTXTFile.readSpecificLine(cartList.get(a)[1], 1, "./src/items.txt", ",");
                                            Product product3 = new Product(productInfo1[0], productInfo1[1], Long.parseLong(productInfo1[2]), productInfo1[3]);
                                            order.createNewOrder(member, product3, oID, Integer.parseInt(cartList.get(a)[3]));
                                        }
                                        order.getTotalPaymentEachOrderId(member, oID);
                                        ArrayList<String[]> discountCode = discount.discountCodeList(member);
                                        if ((discountCode.size() == 0)) {
                                            System.out.println("You have discount voucher, do you want to use it?");
                                            System.out.println("1. Yes");
                                            System.out.println("2. No");
                                            String discountOption = UserInput.rawInput();
                                            String discountCodeCustomer = new String();
                                            switch (discountOption) {
                                                case "1":
                                                    discount.displayCustomerDiscountCode(member);
                                                    String choiceOrder1 = UserInput.rawInput();
                                                    for (int m = 0; m < discountCode.size(); m++) {
                                                        if (m == Integer.parseInt(choiceOrder1) - 1) {
                                                            discountCodeCustomer = discountCode.get(m)[1];
                                                        }
                                                    }
                                                    order.getTotalPaymentAfterApplyDiscountCode(oID, discountCodeCustomer, member);
                                                    PointsSystem.pointsConversion(oID, member.getcID());
                                                    TimeUnit.SECONDS.sleep(1);
                                                    customerMenu.viewHomepage(username);
                                                case "2":
                                                    String[] orderInfo = ReadDataFromTXTFile.readSpecificLine(oID, 0,
                                                            "./src/billingHistory.txt", ",");
                                                    Long totalPayment = Long.parseLong(orderInfo[2]);
                                                    discount.giveDiscountCode(member, totalPayment);
                                                    discount.displayCustomerDiscountCode(member);
                                                    PointsSystem.pointsConversion(oID, member.getcID());
                                                    TimeUnit.SECONDS.sleep(1);
                                                    customerMenu.viewHomepage(username);
                                            }
                                        } else {
                                            String[] orderInfo = ReadDataFromTXTFile.readSpecificLine(oID, 0,
                                                    "./src/billingHistory.txt", ",");
                                            Long totalPayment = Long.parseLong(orderInfo[2]);
                                            System.out.println(totalPayment);
                                            discount.giveDiscountCode(member, totalPayment);
                                            PointsSystem.pointsConversion(oID, member.getcID());
                                            customerMenu.viewHomepage(username);
                                        }
                                }
                            }
                        {
                            Random rd = new Random();
                            int i = rd.nextInt(999);
                            String oID = order.oIDDataForValidate(String.format("O%03d", i));
                            for (int a = 0; a < cartList.size(); a++) {
                                String[] productInfo1 = new String[3];
                                productInfo1 = ReadDataFromTXTFile.readSpecificLine(cartList.get(a)[1], 1, "./src/items.txt", ",");
                                Product product3 = new Product(productInfo1[0], productInfo1[1], Long.parseLong(productInfo1[2]), productInfo1[3]);
                                order.createNewOrder(member, product3, oID, Integer.parseInt(cartList.get(a)[3]));
                            }
                            order.getTotalPaymentEachOrderId(member, oID);
                            ArrayList<String[]> discountCode = discount.discountCodeList(member);
                            if ((discountCode.size() == 0)) {
                                System.out.println("You have discount voucher, do you want to use it?");
                                System.out.println("1. Yes");
                                System.out.println("2. No");
                                String discountOption = UserInput.rawInput();
                                String discountCodeCustomer = new String();
                                switch (discountOption) {
                                    case "1":
                                        discount.displayCustomerDiscountCode(member);
                                        String choiceOrder1 = UserInput.rawInput();
                                        for (int m = 0; m < discountCode.size(); m++) {
                                            if (m == Integer.parseInt(choiceOrder1) - 1) {
                                                discountCodeCustomer = discountCode.get(m)[1];
                                            }
                                        }
                                        order.getTotalPaymentAfterApplyDiscountCode(oID, discountCodeCustomer, member);
                                        PointsSystem.pointsConversion(oID, member.getcID());
                                        customerMenu.viewHomepage(username);
                                    case "2":
                                        String[] orderInfo = ReadDataFromTXTFile.readSpecificLine(oID, 0,
                                                "./src/billingHistory.txt", ",");
                                        Long totalPayment = Long.parseLong(orderInfo[2]);
                                        discount.giveDiscountCode(member, totalPayment);
                                        discount.displayCustomerDiscountCode(member);
                                        PointsSystem.pointsConversion(oID, member.getcID());
                                        TimeUnit.SECONDS.sleep(1);
                                        customerMenu.viewHomepage(username);
                                }
                            } else {
                                String[] orderInfo = ReadDataFromTXTFile.readSpecificLine(oID, 0,
                                        "./src/billingHistory.txt", ",");
                                Long totalPayment = Long.parseLong(orderInfo[2]);
                                System.out.println(totalPayment);
                                discount.giveDiscountCode(member, totalPayment);
                                PointsSystem.pointsConversion(oID, member.getcID());
                                customerMenu.viewHomepage(username);
                            }
                        }
                    }
                }
            case "5":
                System.out.print("Please enter your order ID: ");
                String orderId = scanner.nextLine();
                member.searchOrder(orderId);
                TimeUnit.SECONDS.sleep(1);
                customerMenu.viewHomepage(username);
            case "6":
                System.out.println("Which information you want to update?");
                System.out.println("1. Update your name");
                System.out.println("2. Update your email");
                System.out.println("3. Update your address");
                System.out.println("4. Update your phone");
                System.out.println("5. Update your password");

                String choice = UserInput.rawInput();
                switch (choice) {
                    case "1":
                        System.out.print("Please enter your new name: ");
                        String name = scanner.nextLine();
                        member.updateName("./src/customers.txt", name, username);
                        customerMenu.viewHomepage(username);
                    case "2":
                        System.out.print("Please enter your new email: ");
                        String email = scanner.nextLine();
                        member.updateEmail("./src/customers.txt", email, username);
                        customerMenu.viewHomepage(username);
                    case "3":
                        System.out.print("Please enter your new address: ");
                        String address = scanner.nextLine();
                        member.updateAddress("./src/customers.txt", address, username);
                        customerMenu.viewHomepage(username);
                    case "4":
                        System.out.print("Please enter your new phone: ");
                        String phone = scanner.nextLine();
                        member.updatePhone("./src/customers.txt", phone, username);
                        customerMenu.viewHomepage(username);
                    case "5":
                        System.out.print("Please enter your new password: ");
                        String password = scanner.nextLine();
                        member.updatePassword("./src/customers.txt", password, username);
                }
            case "7":
                member.checkMembership(username);
                TimeUnit.SECONDS.sleep(1);
                customerMenu.viewHomepage(username);
            case "8":
                member.getAllMembershipTypes();
                TimeUnit.SECONDS.sleep(1);
                customerMenu.viewHomepage(username);
            case "9":
                PointsSystem.viewPrizes();
                String userChoice = UserInput.rawInput();
                ArrayList<String[]> productList = ReadDataFromTXTFile.readAllLines("./src/prizeItems.txt");
                String[] prizeInfo = new String[3];
                boolean displayMsg = false;

                for (int i = 0; i < productList.size(); i++) {
                    if (i == Integer.parseInt(userChoice)) {
                        prizeInfo = ReadDataFromTXTFile.readSpecificLine(productList.get(i)[1],
                                1, "./src/prizeItems.txt", ",");
                        displayMsg = true;
                    }
                }
                if (displayMsg) {
                    PointsSystem.exchangeItem(username, String.valueOf(prizeInfo[0]));
                } else {
                    System.out.println("Invalid input please try again later, you will now return to the main menu.");
                    customerMenu.viewHomepage(username);
                }


            case "10":
                FAQ.FAQPrint();
                System.out.print("\n" + "Press '=' to confirm exit or 1 to display the Questions again" + "\n");
                String exit = UserInput.rawInput();
                if (exit.equals("=")) {
                    customerMenu.viewHomepage(username);
                } else {
                    System.out.println(" ");
                    FAQ.FAQPrint();
                }
            case "11":
                if (!(cartList.size() == 0)) {
                    System.out.println("You still have item(s) in your cart! Do you want to create order or log out");
                    System.out.println("1. Create order");
                    System.out.println("2. Keep item(s) in your cart and log out");
                    System.out.println("3. Delete all item(s) in your cart and log out");

                    String finalChoice = UserInput.rawInput();
                    switch (finalChoice) {
                        case "1":
                            cart.getCustomerCart(member);
                            System.out.println("Do you want to delete any item?");
                            System.out.println("1. Yes");
                            System.out.println("2. No");
                            String delOption = UserInput.rawInput();
                            switch (delOption) {
                                case "1":
                                    cart.getCustomerCart(member);
                                    String choiceOrder = UserInput.rawInput();

                                    String[] productInfo = new String[3];
                                    for (int i = 0; i < cartList.size(); i++) {
                                        if (i == Integer.parseInt(choiceOrder)) {
                                            productInfo = ReadDataFromTXTFile.readSpecificLine(cartList.get(i)[1],
                                                    1, "./src/items.txt", ",");
                                        }
                                    }
                                    Product product1 = new Product((productInfo[0]), productInfo[1],
                                            Long.parseLong((productInfo[2])), productInfo[3]);
                                    cart.deleteItemInCart("./src/customerCart.txt", member.getcID(), product1);
                                    if (cartList.size() == 0) {
                                        System.out.println("Your cart is empty!");
                                    }
                                case "2":
                                    Random rd = new Random();
                                    int i = rd.nextInt(999);
                                    String oID = order.oIDDataForValidate(String.format("O%03d", i));
                                    for (int a = 0; a < cartList.size(); a++) {
                                        String[] productInfo1 = new String[3];
                                        productInfo1 = ReadDataFromTXTFile.readSpecificLine(cartList.get(a)[1], 1, "./src/items.txt", ",");
                                        Product product2 = new Product(productInfo1[0], productInfo1[1], Long.parseLong(productInfo1[2]), productInfo1[3]);
                                        order.createNewOrder(member, product2, oID, Integer.parseInt(cartList.get(a)[3]));
                                    }
                                    order.getTotalPaymentEachOrderId(member, oID);
                                    ArrayList<String[]> discountCode = discount.discountCodeList(member);
                                    if (!(discountCode.get(1) == null)) {
                                        System.out.println("You have discount voucher, do you want to use it?");
                                        System.out.println("1. Yes");
                                        System.out.println("2. No");
                                        String discountOption = UserInput.rawInput();
                                        String discountCodeCustomer = new String();
                                        switch (discountOption) {
                                            case "1":
                                                discount.displayCustomerDiscountCode(member);
                                                String choiceOrder1 = UserInput.rawInput();
                                                for (int m = 0; m < discountCode.size(); m++) {
                                                    if (m == Integer.parseInt(choiceOrder1)) {
                                                        discountCodeCustomer = discountCode.get(m)[1];
                                                    }
                                                    order.getTotalPaymentAfterApplyDiscountCode(oID, discountCodeCustomer, member);
                                                }
                                            case "2":
                                                String[] orderInfo = ReadDataFromTXTFile.readSpecificLine(oID, 0,
                                                        "./src/billingHistory.txt", ",");
                                                Long totalPayment = Long.parseLong(orderInfo[2]);
                                                discount.giveDiscountCode(member, totalPayment);
                                                discount.displayCustomerDiscountCode(member);
                                                TimeUnit.SECONDS.sleep(1);
                                                customerMenu.viewHomepage(username);
                                        }
                                    }
                            }
                        case "2":
                            customerMenu.view();
                        case "3":
                            cart.deleteAllItemsInCart("./src/customerCart.txt", member.getcID());
                            System.out.println("Delete all items successfully!");
                            customerMenu.view();
                    }
                } else {
                    customerMenu.view();
                }
            case "12":
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
