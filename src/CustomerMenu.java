import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CustomerMenu {
    // This class will display customer menu
    private boolean cookies;

    public void view() throws IOException, InterruptedException
    // Display menu when customer did not login yet
    {
        System.out.println("\n================================================= WELCOME TO TOM'S PRODIGIES STORE =================================================");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. List all products");
        System.out.println("4. Sort product");
        System.out.println("5. Search product");
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
                System.out.println("\n================================================= REGISTRATION FORM =================================================");
                customer.register(); // Register customer's account
                System.out.println("Register Successfully!");
                customerMenu.view(); // Back to the viewpage
            case "2":
                System.out.println("\n================================================= LOGIN FORM =================================================");
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                if (!customer.login(username, password))
                /* Verify the username and password,
                if it doesn't correct, the system will ask customer to input again
                if the username and password are correct, the login status will become true and display the homepage
                 */ {
                    System.out.print("Enter username: ");
                    username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    password = scanner.nextLine();
                }
                this.cookies = true;
                this.viewHomepage(username);
            case "3":
                System.out.println("\n================================================= OUR PRODUCTS =================================================");
                product.getAllProductInfo(); // Display all products information
                TimeUnit.SECONDS.sleep(1); // Wait for 1 second and then display a viewpage
                customerMenu.view();
            case "4":
                // Ask customer whether he/she wants to sort the product's price ascending or descending
                System.out.println("\n================================================= SORTING PRODUCT =================================================");
                System.out.println("1. Ascending");
                System.out.println("2. Descending");
                String sortOption = UserInput.rawInput();
                SortProduct.sortItems(Integer.parseInt(sortOption));
                TimeUnit.SECONDS.sleep(1);
                customerMenu.view();
            case "5":
                // Searching product by category or price range
                System.out.println("\n================================================= SEARCHING PRODUCT =================================================");
                System.out.println("1. Search by category");
                System.out.println("2. Search by price range");
                System.out.println("3. Back to homepage");
                String sort = UserInput.rawInput();
                switch (sort) {
                    case "1":
                        // Ask customer to enter a category and check if it has product in that category or not
                        System.out.print("Enter category: ");
                        String category = scanner.nextLine();
                        product.searchByCategory(category);
                        TimeUnit.SECONDS.sleep(1);
                        customerMenu.view();
                    case "2":
                        // Ask customer to choose the price range that he/she wants to search
                        product.findItemByPriceRange();
                        TimeUnit.SECONDS.sleep(1);
                        customerMenu.view();
                    case "3":
                        customerMenu.view();
                    default:
                        // If customer input another option that don't have in the searching product menu
                        // then the system will give he/she message and back to the viewpage
                        System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                        TimeUnit.SECONDS.sleep(1);
                        customerMenu.view();
                }
            case "6":
                PointsSystem.viewPrizes(); // Display all the prizes that customers could exchange by his/her point
                TimeUnit.SECONDS.sleep(1);
                customerMenu.view();
            case "7":
                System.out.println("\n================================================= MEMBERSHIP TYPES =================================================");
                customer.getAllMembershipTypes(); // Display all types of membership and its discount for customer
                TimeUnit.SECONDS.sleep(1);
                customerMenu.view();
            case "8":
                // Display all the questions and then the customer will choose which question he/she want to find
                // After that, the system will display the answer of the customer's choice question
                // The system will display question again or back to viewpage based on customer's choice
                FAQ.FAQPrint();
                System.out.print("\n" + "Press '=' to confirm exit or 1 to display the Questions again" + "\n");
                String exit = UserInput.rawInput();
                if (exit.equals("=")) {
                    customerMenu.view();
                } else {
                    System.out.println(" ");
                    FAQ.FAQPrint();
                }
            case "9":
//                customerMenu.view(); // Back to the authentication system menu
            case "10":
                // Display our course's information and our group's information
                // and then exit the system
                System.out.println("Thank you so much for using our system. See you soon !!!!!");
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
            default:
                // If customer input another option that don't have in the menu
                // then the system will give he/she message and back to the viewpage
                System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                TimeUnit.SECONDS.sleep(1);
                customerMenu.view();
        }
    }

    public void viewHomepage(String username) throws IOException, InterruptedException {
        System.out.println("\n================================================= HOMEPAGE =================================================");
        // Display the login status when customer login successfully
        System.out.println("\nCookies (Login Status): " + this.cookies);
        // Read all information of the customer and store it in an array
        String[] customerInfo = ReadDataFromTXTFile.readSpecificLine(username, 6, "./src/customers.txt", ",");
        // Display all information of the customer
        System.out.println("\nUsername:" + customerInfo[6] + "\t\tName:" + customerInfo[1]
                + "\t\tEmail:" + customerInfo[2] + "\t\tAddress:" + customerInfo[3]
                + "\t\tPhone:" + customerInfo[4] + "\t\tPoints:" + customerInfo[9]);
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

        Scanner scanner = new Scanner(System.in);
        Customer member = new Customer(customerInfo[0], customerInfo[1], customerInfo[2], customerInfo[3],
                customerInfo[4], customerInfo[5], customerInfo[6], customerInfo[7],
                Long.parseLong(customerInfo[8]), Long.parseLong(customerInfo[9]));
        CustomerMenu customerMenu = new CustomerMenu();
        Product product = new Product();
        Cart cart = new Cart();
        ArrayList<String[]> cartList = cart.cartList(member);

        String option = UserInput.rawInput();
        switch (option) {
            case "1":
                System.out.println("\n================================================= OUR PRODUCTS =================================================");
                product.getAllProductInfo(); // Display all products information
                TimeUnit.SECONDS.sleep(1);
                this.viewHomepage(username);
            case "2":
                // Ask customer whether he/she wants to sort the product's price ascending or descending
                System.out.println("\n================================================= SORTING PRODUCT =================================================");
                System.out.println("1. Ascending");
                System.out.println("2. Descending");
                String sortOption = UserInput.rawInput();
                SortProduct.sortItems(Integer.parseInt(sortOption));
                TimeUnit.SECONDS.sleep(1);
                this.viewHomepage(username);
            case "3":
                // Searching product by category or price range
                System.out.println("\n================================================= SEARCHING PRODUCT =================================================");
                System.out.println("1. Search by category");
                System.out.println("2. Search by price range");
                System.out.println("3. Back to homepage");
                String sort = UserInput.rawInput();
                switch (sort) {
                    case "1":
                        // Ask customer to enter a category and check if it has product in that category or not
                        System.out.print("Enter category: ");
                        String category = scanner.nextLine();
                        product.searchByCategory(category);
                        TimeUnit.SECONDS.sleep(1);
                        this.viewHomepage(username);
                    case "2":

                        // Ask customer to choose the price range that he/she wants to search
                        Product.printPriceRange();

                        product.findItemByPriceRange();
                        TimeUnit.SECONDS.sleep(1);
                        this.viewHomepage(username);
                    case "3":
                        this.viewHomepage(username);
                    default:
                        // If customer input another option that don't have in the searching product menu
                        // then the system will give he/she message and back to the homepage
                        System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                        TimeUnit.SECONDS.sleep(1);
                        this.viewHomepage(username);
                }
            case "4":
                if (cartList.size() == 0)
                /* If customer's cart is empty,
                the system will ask customer to add item to cart
                 */ {
                    System.out.println("You don't have any items in your cart. Do you want to add any product to your cart");
                    System.out.println("1. Yes");
                    System.out.println("2. No");
                    String cartOption = UserInput.rawInput();
                    switch (cartOption) {
                        case "1":
                            product.getProductHaveId(); // Display all products having ID option
                            String choiceOrder = UserInput.rawInput();
                            System.out.print("Please enter a quantity that you want to add: ");
                            int quantity = Integer.parseInt(scanner.nextLine());
                            // Ask customer to enter the quantity that he/she wants to add to cart
                            ArrayList<String[]> productList = ReadDataFromTXTFile.readAllLines("./src/items.txt");

                            String[] productInfo = new String[3];
                            for (int i = 0; i < productList.size(); i++)
                            /* The system will loop through the items file
                            and find the item that customer wants to add to cart,
                            then the system will store that item in the customer's cart file
                             */ {
                                if (i == Integer.parseInt(choiceOrder)) {
                                    productInfo = ReadDataFromTXTFile.readSpecificLine(productList.get(i)[1],
                                            1, "./src/items.txt", ",");
                                }
                            }
                            Product product1 = new Product((productInfo[0]), productInfo[1],
                                    Long.parseLong((productInfo[2])), productInfo[3]);
                            cart.addToCart(member, product1, quantity);
                            // After add item to cart,
                            // the system will give a message that whether a customer wants to continue to shopping or create order now
                            System.out.println("\n================================================= CONTINUE TO SHOPPING OR CREATE ORDER =================================================");
                            System.out.println("1. Create order");
                            System.out.println("2. Continue to shopping");

                            String shoppingChoice = UserInput.rawInput();
                            switch (shoppingChoice) {
                                case "1":
                                    createOrder(member); // Create order based on customer's cart
                                case "2":
                                    this.viewHomepage(username);
                                default:
                                    // If customer input another option that don't have in the menu
                                    // then the system will give he/she message and back to the homepage
                                    System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                                    TimeUnit.SECONDS.sleep(1);
                                    this.viewHomepage(username);
                            }
                        case "2":
                            this.viewHomepage(username);
                        default:
                            // If customer input another option that don't have in the menu
                            // then the system will give he/she message and back to the homepage
                            System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                            TimeUnit.SECONDS.sleep(1);
                            this.viewHomepage(username);
                    }
                } else {
                    cart.getCustomerCart(member); // Display the customer's cart
                    // Ask customer whether he/she wants to continue adding item or create order now
                    System.out.println("\n================================================= CONTINUE ADD ITEM(S) OR CREATE ORDER NOW =================================================");
                    System.out.println("1. Continue add item(s)");
                    System.out.println("2. Create order");
                    System.out.println("3. Back to homepage");

                    String continueShopping = UserInput.rawInput();
                    switch (continueShopping) {
                        case "1":
                            product.getProductHaveId(); // Display all products having ID option
                            String choiceOrder = UserInput.rawInput();
                            System.out.println("Please enter a quantity that you want to add: ");
                            int quantity = Integer.parseInt(scanner.nextLine());
                            // Ask customer to enter the quantity that he/she wants to add to cart
                            ArrayList<String[]> productList = ReadDataFromTXTFile.readAllLines("./src/items.txt");

                            String[] productInfo = new String[3];
                            for (int i = 0; i < productList.size(); i++)
                            /* The system will loop through the items file
                            and find the item that customer wants to add to cart,
                            then the system will store that item in the customer's cart file
                             */ {
                                if (i == Integer.parseInt(choiceOrder)) {
                                    productInfo = ReadDataFromTXTFile.readSpecificLine(productList.get(i)[1],
                                            1, "./src/items.txt", ",");
                                }
                            }
                            Product product1 = new Product((productInfo[0]), productInfo[1],
                                    Long.parseLong((productInfo[2])), productInfo[3]);
                            cart.addToCart(member, product1, quantity);
                            // After add item to cart,
                            // the system will give a message that whether a customer wants to continue to shopping or create order now
                            this.viewHomepage(username); // Back to the homepage
                        case "2":
                            cart.getCustomerCart(member); // Display customer's cart
                            while (cartList.size() > 1)
                            // While the customer's cart has more than 1 item,
                            // the system will ask customer does he/she want to delete any item
                            {
                                System.out.println("Do you want to delete any item?");
                                System.out.println("1. Yes");
                                System.out.println("2. No");
                                String delOption = UserInput.rawInput();
                                switch (delOption) {
                                    case "1":
                                        cart.getCustomerCart(member); // Display all customer's cart for customer to choose which item he/she wants to delete
                                        String delProduct = UserInput.rawInput();

                                        String[] itemInfo = new String[3];
                                        for (int i = 0; i < cartList.size(); i++)
                                        // The system will loop through all the items in customer's cart
                                        // to delete the item that customer to choose based on the option
                                        {
                                            if (i == (Integer.parseInt(delProduct) - 1)) {
                                                itemInfo = ReadDataFromTXTFile.readSpecificLine(cartList.get(i)[1],
                                                        1, "./src/items.txt", ",");
                                            }
                                        }
                                        Product product2 = new Product((itemInfo[0]), itemInfo[1],
                                                Long.parseLong((itemInfo[2])), itemInfo[3]);
                                        // The system will delete the item that customer had chosen
                                        cart.deleteItemInCart("./src/customerCart.txt", member.getcID(), product2);
                                        cart.getCustomerCart(member);
                                        this.viewHomepage(username);

                                    case "2":
                                        createOrder(member); // Create order based on customer's cart
                                    default:
                                        // If customer input another option that don't have in the menu
                                        // then the system will give he/she message and back to the homepage
                                        System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                                        TimeUnit.SECONDS.sleep(1);
                                        this.viewHomepage(username);
                                }
                            }
                            // If the customer's has only 1 item, then the system will create order
                        {
                            createOrder(member);
                        }

                        case "3":
                            this.viewHomepage(username);
                        default:
                            // If customer input another option that don't have in the menu
                            // then the system will give he/she message and back to the homepage
                            System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                            TimeUnit.SECONDS.sleep(1);
                            this.viewHomepage(username);
                    }
                }
            case "5":
                System.out.println("\n================================================= SEARCHING ORDER =================================================");
                System.out.print("Please enter your order ID: "); // Ask customer to enter an order ID that he/she wants to search
                String orderId = scanner.nextLine();
                member.searchOrder(orderId);
                TimeUnit.SECONDS.sleep(1);
                this.viewHomepage(username);
            case "6":
                // Ask customer which information he/she wants to update
                // After that the system will change his/her information in the text file
                System.out.println("\n================================================= UPDATING INFORMATION =================================================");
                System.out.println("Which information you want to update?");
                System.out.println("1. Update your name");
                System.out.println("2. Update your email");
                System.out.println("3. Update your address");
                System.out.println("4. Update your phone");
                System.out.println("5. Update your password");
                System.out.println("6. Back to homepage");

                String choice = UserInput.rawInput();
                switch (choice) {
                    case "1":
                        System.out.print("Please enter your new name: ");
                        String name = scanner.nextLine();
                        member.updateName("./src/customers.txt", name, username);
                        this.viewHomepage(username);
                    case "2":
                        System.out.print("Please enter your new email: ");
                        String email = scanner.nextLine();
                        member.updateEmail("./src/customers.txt", email, username);
                        this.viewHomepage(username);
                    case "3":
                        System.out.print("Please enter your new address: ");
                        String address = scanner.nextLine();
                        member.updateAddress("./src/customers.txt", address, username);
                        this.viewHomepage(username);
                    case "4":
                        System.out.print("Please enter your new phone: ");
                        String phone = scanner.nextLine();
                        member.updatePhone("./src/customers.txt", phone, username);
                        this.viewHomepage(username);
                    case "5":
                        System.out.print("Please enter your new password: ");
                        String password = scanner.nextLine();
                        member.updatePassword("./src/customers.txt", password, username);
                        this.viewHomepage(username);
                    case "6":
                        this.viewHomepage(username);
                    default:
                        System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                        TimeUnit.SECONDS.sleep(1);
                        this.viewHomepage(username);
                }
            case "7":
                // Display the current membership of the customer
                member.checkMembership(username);
                TimeUnit.SECONDS.sleep(1);
                this.viewHomepage(username);
            case "8":
                // Display all types of membership
                member.getAllMembershipTypes();
                TimeUnit.SECONDS.sleep(1);
                this.viewHomepage(username);
            case "9":
                // Display all the prizes that customers could exchange by their point
                // And then ask the customer to choose which item he/she wants to exchange
                // After that, the system will create a prize exchange order for customer
                PointsSystem.viewPrizes();
                System.out.print("Please enter an option to exchange: ");
                String userChoice = scanner.nextLine();
                ArrayList<String[]> productList = ReadDataFromTXTFile.readAllLines("./src/prizeItems.txt");
                String[] prizeInfo = new String[3];
                boolean displayMsg = false;

                for (int i = 0; i < productList.size(); i++) {
                    if (i == (Integer.parseInt(userChoice))) {
                        prizeInfo = ReadDataFromTXTFile.readSpecificLine(productList.get(i)[1],
                                1, "./src/prizeItems.txt", ",");
                        displayMsg = true;
                    }
                }
                if (displayMsg) {
                    PointsSystem.exchangeItem(member.getcID(), String.valueOf(prizeInfo[0]));
                    TimeUnit.SECONDS.sleep(1);
                    this.viewHomepage(username);
                } else {
                    System.out.println("Invalid input please try again later, you will now return to the main menu.");
                    this.viewHomepage(username);
                }
            case "10":
                // Display all the questions and then the customer will choose which question he/she want to find
                // After that, the system will display the answer of the customer's choice question
                // The system will display question again or back to viewpage based on customer's choice
                FAQ.FAQPrint();
                System.out.print("\n" + "Press '=' to confirm back to homepage or 1 to display the Questions again" + "\n");
                String exit = UserInput.rawInput();
                if (exit.equals("=")) {
                    customerMenu.viewHomepage(username);
                } else {
                    System.out.println(" ");
                    FAQ.FAQPrint();
                }
            case "11":
                if (!(cartList.size() == 0))
                // If in the customer's cart still has any item, the system will display a message
                // that will show three options for customer to choose:
                // create order based on customer's cart
                // or keep all items in cart and log out
                // or delete all items in cart and log out
                {
                    System.out.println("\n================================================= WARNING =================================================");
                    System.out.println("You still have item(s) in your cart! Do you want to create order or log out");
                    System.out.println("1. Create order");
                    System.out.println("2. Keep item(s) in your cart and log out");
                    System.out.println("3. Delete all item(s) in your cart and log out");

                    String finalChoice = UserInput.rawInput();
                    switch (finalChoice) {
                        case "1":
                            while (cartList.size() > 1)
                            // If customer's cart has more than 1 item, the system will ask if customer wants to delete any items
                            {
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
                                        createOrder(member);

                                    default:
                                        System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                                        TimeUnit.SECONDS.sleep(1);
                                        this.viewHomepage(username);
                                }
                            }
                            // Else create order
                        {
                            createOrder(member);
                        }
                        case "2":
                            this.view();
                        case "3":
                            cart.deleteAllItemsInCart("./src/customerCart.txt", member.getcID());
                            System.out.println("Delete all items successfully!");
                            this.view();
                        default:
                            System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                            TimeUnit.SECONDS.sleep(1);
                            this.viewHomepage(username);
                    }
                } else {
                    this.view();
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
            default:
                System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                TimeUnit.SECONDS.sleep(1);
                this.viewHomepage(username);
        }
    }

    public void createOrder(Customer member) throws IOException, InterruptedException
    // Create a new order for customer
    {
        Order order = new Order();
        Cart cart = new Cart();
        Discount discount = new Discount();
        ArrayList<String[]> cartList = cart.cartList(member);
        Random rd = new Random();
        int i = rd.nextInt(999);
        String oID = order.oIDDataForValidate(String.format("O%03d", i)); // Generate the order id randomly and it is unique
        for (String[] strings : cartList)
        /* this method will loop every item in customer's cart
        and create a new order for those items which have the same order id
         */ {
            String[] productInfo1 = new String[3];
            productInfo1 = ReadDataFromTXTFile.readSpecificLine(strings[1], 1, "./src/items.txt", ",");
            Product product3 = new Product(productInfo1[0], productInfo1[1], Long.parseLong(productInfo1[2]), productInfo1[3]);
            order.createNewOrder(member, product3, oID, Integer.parseInt(strings[3]));
        }
        member.searchOrder(oID);
        order.getTotalPaymentEachOrderId(member, oID);
        // Display a total payment before and after discount depends on membership type
        ArrayList<String[]> discountCode = discount.discountCodeList(member);
        if (!(discountCode.size() == 0))
        // If customer has discount voucher, then system will ask whether the customer wants to use it or not
        {
            System.out.println("You have discount voucher, do you want to use it?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            String discountOption = UserInput.rawInput();
            String discountCodeCustomer = new String();
            switch (discountOption) {
                case "1":
                    /* If customer wants to use discount voucher,
                    then system will show all the vouchers that customer has
                     */
                    discount.displayCustomerDiscountCode(member);
                    String choiceOrder1 = UserInput.rawInput();
                    /* Customer will input their choice
                    and the system will discount based on his/her used voucher
                     */
                    for (int m = 0; m < discountCode.size(); m++) {
                        if (m == Integer.parseInt(choiceOrder1) - 1) {
                            discountCodeCustomer = discountCode.get(m)[1];
                        }
                    }
                    order.getTotalPaymentAfterApplyDiscountCode(oID, discountCodeCustomer, member);
                    // Display the final total payment after customer apply discount voucher
                    TimeUnit.SECONDS.sleep(1);
                    this.viewHomepage(member.getUserName());
                    PointsSystem.pointsConversion(member.getUserName(), oID);
                case "2":
                    /* If customer doesn't want to use voucher,
                    then the system will base on order's total spending after discount by membership to give customer discount voucher
                     */
                    String[] orderInfo = ReadDataFromTXTFile.readSpecificLine(oID, 0,
                            "./src/billingHistory.txt", ",");
                    Long totalPayment = Long.parseLong(orderInfo[2]);
                    discount.giveDiscountCode(member, totalPayment);
                    discount.displayCustomerDiscountCode(member);
                    TimeUnit.SECONDS.sleep(1);
                    this.viewHomepage(member.getUserName());
                    PointsSystem.pointsConversion(member.getUserName(), oID);
                default:
                    System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                    TimeUnit.SECONDS.sleep(1);
                    this.viewHomepage(member.getUserName());
            }
        } else {
            /* If customer doesn't have discount voucher,
            then the system will base on order's total spending after discount by membership to give customer discount voucher
            */
            String[] orderInfo = ReadDataFromTXTFile.readSpecificLine(oID, 0,
                    "./src/billingHistory.txt", ",");
            Long totalPayment = Long.parseLong(orderInfo[2]);
            discount.giveDiscountCode(member, totalPayment);
            this.viewHomepage(member.getUserName());
            PointsSystem.pointsConversion(member.getUserName(), oID);
        }
    }
}
