/*
  RMIT University Vietnam
  Course: COSC2081 Programming 1
  Semester: 2022C
  Assessment: Assignment 3
  Author: Tom's Prodigies
  ID: Nguyen Tran Ha Anh - s3938490
      Hoang Tuan Minh - s3924716
      Dang Kim Quang Minh - s3938024
      Nguyen Gia Bao - s3938143
  Acknowledgement:

*/

package menu;

import bonusFeatures.Cart;
import bonusFeatures.Discount;
import bonusFeatures.FAQ;
import bonusFeatures.PointsSystem;
import fileMethods.ReadDataFromTXTFile;
import fileMethods.UserInput;
import order.Order;
import product.Product;
import product.SortProduct;
import users.Customer;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CustomerMenu {

    // This class will display customer menu
    private boolean cookies;

    // Display menu when customer did not login yet
    public void view() throws IOException, InterruptedException, ParseException {
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
        Customer customer = new Customer();
        Product product = new Product();
        AuthenticationSystem authenticationSystem = new AuthenticationSystem();

        switch (option) {
            case "1":
                System.out.println("\n================================================= REGISTRATION FORM =================================================");
                customer.register(); // Register customer's account
                System.out.println("Register Successfully!");
                customerMenu.view(); // Back to the viewpage

            case "2":
                System.out.println("\n================================================= LOGIN FORM =================================================");
                do {
                    try {
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();
                        String usernameNoSpace = username.trim();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();
                        /* Verify the username and password,
                        if it doesn't correct, the system will ask customer to input again
                        if the username and password are correct, the login status will become true and display the homepage
                         */
                        if (!customer.login(usernameNoSpace, password)) {
                            System.out.println("Incorrect login info, please try again!");
                            this.cookies = false;

                        } else {
                            this.cookies = true;
                            this.viewHomepage(usernameNoSpace);
                        }
                    } catch (Exception e) {
                        System.out.println("Incorrect login info, please try again!");
                        this.cookies = false;
                    }
                } while (!cookies);

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
                System.out.println("3. Search by category and price range");
                System.out.println("4. Back to homepage");
                String sort = UserInput.rawInput();
                switch (sort) {
                    case "1":
                        // Ask customer to enter a category and check if it has product in that category or not
                        product.getAllCategory();
                        product.searchByCategory();
                        TimeUnit.SECONDS.sleep(1);
                        customerMenu.view();

                    case "2":
                        // Ask customer to choose the price range that he/she wants to search
                        product.findItemByPriceRange();
                        TimeUnit.SECONDS.sleep(1);
                        customerMenu.view();

                    case "3":
                        product.getAllCategory();
                        product.searchCategoryByPriceRange();
                        TimeUnit.SECONDS.sleep(1);
                        customerMenu.view();

                    case "4":
                        customerMenu.view();

                    default:
                        // If customer input another option that don't have in the searching product menu
                        // then the system will give he/she message and back to the viewpage
                        System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                        TimeUnit.SECONDS.sleep(1);
                        customerMenu.view();
                }
            case "6":
                System.out.println("\n================================================= POINTS SHOP =========================================================");
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
                ArrayList<String[]> faq = ReadDataFromTXTFile.readAllLines("./src/dataFile/FAQ.txt");
                String question;
                do {
                    try {
                        System.out.println("\n================================================= FAQ =========================================================");
                        FAQ.FAQPrint();
                        System.out.println("Enter a question's number that you want to search: ");
                        question = scanner.nextLine();
                        for (int i = 1; i < faq.size(); i++) {
                            if (i == Integer.parseInt(question)) {
                                System.out.println(faq.get(i)[1]);
                                System.out.println("------> " + faq.get(i)[2]);
                            }
                        }
                        if (Integer.parseInt(question) == 7) {
                            FAQ.seeAll();
                        }
                    } catch (NumberFormatException e) {
                        customerMenu.view();
                    }
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Please press '=' to back to homepage or press any number to display question again");
                    question = scanner.nextLine();
                } while (!question.equals("="));
                customerMenu.view();

            case "9":
                // Back to the authentication system menu
                authenticationSystem.mainMenu();

            case "10":
                // Exit the system
                System.exit(1);

            default:
                // If customer input another option that don't have in the menu
                // then the system will give he/she message and back to the viewpage
                System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                TimeUnit.SECONDS.sleep(1);
                customerMenu.view();
        }
    }

    // Display homepage when customer log in successfully
    public void viewHomepage(String username) throws IOException, InterruptedException, ParseException {
        System.out.println("\n================================================= HOMEPAGE =================================================");
        // Display the login status when customer login successfully
        System.out.println("\nCookies (Login Status): " + this.cookies);
        // Read all information of the customer and store it in an array
        String[] customerInfo = ReadDataFromTXTFile.readSpecificLine(username, 6, "./src/dataFile/customers.txt", ",");
        // Display all information of the customer
        System.out.println("\nUsername:" + customerInfo[6] + "\t\tName:" + customerInfo[1]
                + "\t\tEmail:" + customerInfo[2] + "\t\tAddress:" + customerInfo[3]
                + "\t\tPhone:" + customerInfo[4] + "\t\tPoints:" + customerInfo[9]);
        System.out.println("\n1. List all products");
        System.out.println("2. Sort product");
        System.out.println("3. Search product");
        System.out.println("4. Create order");
        System.out.println("5. Search order by order ID");
        System.out.println("6. Order history");
        System.out.println("7. Update your information");
        System.out.println("8. Check your membership");
        System.out.println("9. List all membership types");
        System.out.println("10. Point store");
        System.out.println("11. FAQ");
        System.out.println("12. Log out");
        System.out.println("13. Exit");

        Scanner scanner = new Scanner(System.in);
        Customer member = new Customer(customerInfo[0], customerInfo[1], customerInfo[2], customerInfo[3],
                customerInfo[4], customerInfo[5], customerInfo[6], customerInfo[7],
                Long.parseLong(customerInfo[8]), Long.parseLong(customerInfo[9]));
        Product product = new Product();
        Cart cart = new Cart(member);
        ArrayList<String[]> cartList = cart.cartList();
        Order order = new Order();

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
                System.out.println("3. Search by category and price range");
                System.out.println("4. Back to homepage");
                String sort = UserInput.rawInput();
                switch (sort) {

                    case "1":
                        // Ask customer to enter a category and check if it has product in that category or not
                        product.getAllCategory();
                        product.searchByCategory();
                        TimeUnit.SECONDS.sleep(1);
                        this.viewHomepage(username);

                    case "2":
                        // Ask customer to choose the price range that he/she wants to search
                        product.printPriceRange();
                        product.findItemByPriceRange();
                        TimeUnit.SECONDS.sleep(1);
                        this.viewHomepage(username);

                    case "3":
                        product.getAllCategory();
                        product.searchCategoryByPriceRange();
                        TimeUnit.SECONDS.sleep(1);
                        this.viewHomepage(username);

                    case "4":
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
                            int quantity;
                            boolean check = false;
                            do {
                                try {
                                    System.out.print("Please enter a quantity that you want to add: ");
                                    quantity = Integer.parseInt(scanner.nextLine());
                                    check = true;
                                    // Ask customer to enter the quantity that he/she wants to add to cart
                                    ArrayList<String[]> productList = ReadDataFromTXTFile.readAllLines("./src/dataFile/items.txt");

                                    String[] productInfo = new String[3];
                                    if (Integer.parseInt(choiceOrder) < productList.size()) {
                                        for (int i = 0; i < productList.size(); i++)
                                    /* The system will loop through the items file
                                    and find the item that customer wants to add to cart,
                                    then the system will store that item in the customer's cart file
                                     */ {
                                            if (i == Integer.parseInt(choiceOrder)) {
                                                productInfo = ReadDataFromTXTFile.readSpecificLine(productList.get(i)[1],
                                                        1, "./src/dataFile/items.txt", ",");
                                            }
                                        }
                                        Product product1 = new Product((productInfo[0]), productInfo[1],
                                                Long.parseLong((productInfo[2])), productInfo[3]);
                                        cart.addToCart(product1, quantity);
                                    } else {
                                        System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                                        TimeUnit.SECONDS.sleep(1);
                                        this.viewHomepage(username);
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid quantity, please try again");
                                }
                            } while (!check);
                            // After add item to cart,
                            // the system will give a message that whether a customer wants to continue to shopping or create order now
                            System.out.println("\n================================================= CONTINUE TO SHOPPING OR CREATE ORDER =================================================");
                            System.out.println("1. Create order");
                            System.out.println("2. Continue to shopping");

                            String shoppingChoice = UserInput.rawInput();
                            switch (shoppingChoice) {
                                case "1":
                                    this.createOrder(member); // Create order based on customer's cart
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
                    cart.getCustomerCart(); // Display the customer's cart
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
                            int quantity;
                            boolean check = false;
                            do {
                                try {
                                    System.out.print("Please enter a quantity that you want to add: ");
                                    quantity = Integer.parseInt(scanner.nextLine());
                                    check = true;
                                    // Ask customer to enter the quantity that he/she wants to add to cart
                                    ArrayList<String[]> productList = ReadDataFromTXTFile.readAllLines("./src/dataFile/items.txt");

                                    String[] productInfo = new String[3];
                                    if (Integer.parseInt(choiceOrder) < productList.size()) {
                                        for (int i = 0; i < productList.size(); i++)
                                    /* The system will loop through the items file
                                    and find the item that customer wants to add to cart,
                                    then the system will store that item in the customer's cart file
                                     */ {
                                            if (i == Integer.parseInt(choiceOrder)) {
                                                productInfo = ReadDataFromTXTFile.readSpecificLine(productList.get(i)[1],
                                                        1, "./src/dataFile/items.txt", ",");
                                            }
                                        }
                                        Product product1 = new Product((productInfo[0]), productInfo[1],
                                                Long.parseLong((productInfo[2])), productInfo[3]);
                                        cart.addToCart(product1, quantity);
                                    } else {
                                        System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                                        TimeUnit.SECONDS.sleep(1);
                                        this.viewHomepage(username);
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid quantity, please try again");
                                }
                            } while (!check);
                            // After add item to cart,
                            // the system will give a message that whether a customer wants to continue to shopping or create order now
                            this.viewHomepage(username); // Back to the homepage

                        case "2":
                            cart.getCustomerCart(); // Display customer's cart
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
                                        cart.getCustomerCart(); // Display all customer's cart for customer to choose which item he/she wants to delete
                                        String delProduct = UserInput.rawInput();

                                        String[] itemInfo = new String[3];
                                        if (Integer.parseInt(delProduct) <= cartList.size()) {
                                            for (int i = 0; i < cartList.size(); i++)
                                            // The system will loop through all the items in customer's cart
                                            // to delete the item that customer to choose based on the option
                                            {
                                                if (i == (Integer.parseInt(delProduct) - 1)) {
                                                    itemInfo = ReadDataFromTXTFile.readSpecificLine(cartList.get(i)[1],
                                                            1, "./src/dataFile/items.txt", ",");
                                                }
                                            }
                                            Product product2 = new Product((itemInfo[0]), itemInfo[1],
                                                    Long.parseLong((itemInfo[2])), itemInfo[3]);
                                            // The system will delete the item that customer had chosen
                                            cart.deleteItemInCart("./src/dataFile/customerCart.txt", member.getcID(), product2);
                                            cart.getCustomerCart();
                                            this.viewHomepage(username);
                                        } else {
                                            System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                                            TimeUnit.SECONDS.sleep(1);
                                            this.viewHomepage(username);
                                        }

                                    case "2":
                                        this.createOrder(member); // Create order based on customer's cart
                                        TimeUnit.SECONDS.sleep(1);
                                        this.viewHomepage(username);
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
                            this.createOrder(member);
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
                System.out.print("Please enter your order ID: (e.g: T392)"); // Ask customer to enter an order ID that he/she wants to search
                String orderId = scanner.nextLine();
                order.searchOrder(orderId);
                TimeUnit.SECONDS.sleep(1);
                this.viewHomepage(username);

            case "6":
                order.getOrderInfoByCID(member.getcID());
                TimeUnit.SECONDS.sleep(1);
                this.viewHomepage(username);

            case "7":
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
                        System.out.print("Please enter your new name (e.g: Ha Anh): ");
                        String name = scanner.nextLine();
                        member.updateInfo("./src/dataFile/customers.txt", name, choice);
                        this.viewHomepage(username);

                    case "2":
                        System.out.print("Please enter your new email (e.g: tomsprodigies@gmail.com): ");
                        String email = scanner.nextLine();
                        member.updateInfo("./src/dataFile/customers.txt", email, choice);
                        this.viewHomepage(username);

                    case "3":
                        System.out.print("Please enter your new address (e.g: 702 Nguyen Van Linh Street): ");
                        String address = scanner.nextLine();
                        member.updateInfo("./src/dataFile/customers.txt", address, choice);
                        this.viewHomepage(username);

                    case "4":
                        System.out.print("Please enter your new phone (e.g: 09********): ");
                        String phone = scanner.nextLine();
                        member.updateInfo("./src/dataFile/customers.txt", phone, choice);
                        this.viewHomepage(username);

                    case "5":
                        System.out.print("Enter password \n" +
                                "        (Password must contain at least one digit [0-9].\n" +
                                "        Password must contain at least one lowercase Latin character [a-z].\n" +
                                "        Password must contain at least one uppercase Latin character [A-Z].\n" +
                                "        Password must contain a length of at least 8 characters and a maximum of 20 characters): ");
                        String password = scanner.nextLine();
                        member.updateInfo("./src/dataFile/customers.txt", password, choice);
                        this.viewHomepage(username);

                    case "6":
                        this.viewHomepage(username);

                    default:
                        System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                        TimeUnit.SECONDS.sleep(1);
                        this.viewHomepage(username);
                }

            case "8":
                // Display the current membership of the customer
                member.checkMembership();
                TimeUnit.SECONDS.sleep(1);
                this.viewHomepage(username);

            case "9":
                // Display all types of membership
                member.getAllMembershipTypes();
                TimeUnit.SECONDS.sleep(1);
                this.viewHomepage(username);

            case "10":
                // Display all the prizes that customers could exchange by their point
                // And then ask the customer to choose which item he/she wants to exchange
                // After that, the system will create a prize exchange order for customer
                PointsSystem.viewPrizes();
                System.out.print("Please enter an option to exchange: ");
                String userChoice = scanner.nextLine();
                ArrayList<String[]> productList = ReadDataFromTXTFile.readAllLines("./src/dataFile/prizeItems.txt");
                String[] prizeInfo = new String[3];
                boolean displayMsg = false;

                for (int i = 0; i < productList.size(); i++) {
                    if (i == (Integer.parseInt(userChoice))) {
                        prizeInfo = ReadDataFromTXTFile.readSpecificLine(productList.get(i)[1],
                                1, "./src/dataFile/prizeItems.txt", ",");
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

            case "11":
                // Display all the questions and then the customer will choose which question he/she want to find
                // After that, the system will display the answer of the customer's choice question
                // The system will display question again or back to viewpage based on customer's choice
                ArrayList<String[]> faq = ReadDataFromTXTFile.readAllLines("./src/dataFile/FAQ.txt");
                String question;
                do {
                    try {
                        System.out.println("\n================================================= FAQ =========================================================");
                        FAQ.FAQPrint();
                        System.out.println("Enter a question's number that you want to search: ");
                        question = scanner.nextLine();
                        for (int i = 1; i < faq.size(); i++) {
                            if (i == Integer.parseInt(question)) {
                                System.out.println(faq.get(i)[1]);
                                System.out.println("------> " + faq.get(i)[2]);
                            }
                        }
                        if (Integer.parseInt(question) == 7) {
                            FAQ.seeAll();
                        }
                    } catch (NumberFormatException e) {
                        this.viewHomepage(username);
                    }
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Please press '=' to back to homepage or press any number to display question again");
                    question = scanner.nextLine();
                } while (!question.equals("="));
                this.viewHomepage(username);

            case "12":
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
                                cart.getCustomerCart();
                                System.out.println("Do you want to delete any item?");
                                System.out.println("1. Yes");
                                System.out.println("2. No");
                                String delOption = UserInput.rawInput();
                                switch (delOption) {
                                    case "1":
                                        cart.getCustomerCart();
                                        String choiceOrder = UserInput.rawInput();

                                        String[] productInfo = new String[3];
                                        if (Integer.parseInt(choiceOrder) <= cartList.size()) {
                                            for (int i = 0; i < cartList.size(); i++) {
                                                if (i == Integer.parseInt(choiceOrder)) {
                                                    productInfo = ReadDataFromTXTFile.readSpecificLine(cartList.get(i)[1],
                                                            1, "./src/dataFile/items.txt", ",");
                                                }
                                            }
                                            Product product1 = new Product((productInfo[0]), productInfo[1],
                                                    Long.parseLong((productInfo[2])), productInfo[3]);
                                            cart.deleteItemInCart("./src/dataFile/customerCart.txt", member.getcID(), product1);
                                            if (cartList.size() == 0) {
                                                System.out.println("Your cart is empty!");
                                            }
                                        } else {
                                            System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                                            TimeUnit.SECONDS.sleep(1);
                                            this.viewHomepage(username);
                                        }

                                    case "2":
                                        this.createOrder(member);

                                    default:
                                        System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                                        TimeUnit.SECONDS.sleep(1);
                                        this.viewHomepage(username);
                                }
                            }
                            // Else create order
                        {
                            this.createOrder(member);
                        }
                        case "2":
                            this.view();

                        case "3":
                            cart.deleteAllItemsInCart("./src/dataFile/customerCart.txt", member.getcID());
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
            case "13":
                System.exit(1);

            default:
                System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                TimeUnit.SECONDS.sleep(1);
                this.viewHomepage(username);
        }
    }

    // Create a new order for customer
    public void createOrder(Customer member) throws IOException, InterruptedException, ParseException {
        Cart cart = new Cart(member);
        ArrayList<String[]> cartList = cart.cartList();
        Random rd = new Random();
        int i = rd.nextInt(999);
        String oID = Order.oIDDataForValidate(String.format("T%03d", i)); // Generate the order id randomly and it is unique
        Order newOrder = new Order(oID, member);
        for (String[] strings : cartList)
        /* This method will loop every item in customer's cart
        and create a new order for those items which have the same order id
         */ {
            String[] productInfo1 = new String[3];
            productInfo1 = ReadDataFromTXTFile.readSpecificLine(strings[1], 1, "./src/dataFile/items.txt", ",");
            Product product3 = new Product(productInfo1[0], productInfo1[1], Long.parseLong(productInfo1[2]), productInfo1[3]);
            newOrder.createNewOrder(product3, Integer.parseInt(strings[3]));
        }
        Discount discount = new Discount(member, newOrder);
        newOrder.searchOrder(oID);
        newOrder.getTotalPaymentEachOrderId();

        // Display a total payment before and after discount depends on membership type
        ArrayList<String[]> discountCode = discount.discountCodeList();
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
                    discount.displayCustomerDiscountCode();
                    String choiceOrder1 = UserInput.rawInput();
                    /* Users.Customer will input their choice
                    and the system will discount based on his/her used voucher
                     */
                    if (Integer.parseInt(choiceOrder1) - 1 < discountCode.size()) {
                        for (int m = 0; m < discountCode.size(); m++) {
                            if (m == Integer.parseInt(choiceOrder1) - 1) {
                                discountCodeCustomer = discountCode.get(m)[1];
                            }
                        }
                        newOrder.getTotalPaymentAfterApplyDiscountCode(discountCodeCustomer);
                    } else {
                        System.out.println("THERE IS NO MATCHING RESULT!!!");
                        newOrder.printPayment();
                    }
                    // Display the final total payment after customer apply discount voucher
                    TimeUnit.SECONDS.sleep(1);
                    PointsSystem.pointsConversion(member.getcID(), oID);
                    this.viewHomepage(member.getUsername());

                case "2":
                    /* If customer doesn't want to use voucher,
                    then the system will base on order's total spending after discount by membership to give customer discount voucher
                     */
                    discount.giveDiscountCode();
                    discount.displayCustomerDiscountCode();
                    TimeUnit.SECONDS.sleep(1);
                    PointsSystem.pointsConversion(member.getcID(), oID);
                    this.viewHomepage(member.getUsername());

                default:
                    System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                    TimeUnit.SECONDS.sleep(1);
                    this.viewHomepage(member.getUsername());
            }
        } else {
            /* If customer doesn't have discount voucher,
            then the system will base on order's total spending after discount by membership to give customer discount voucher
            */
            discount.giveDiscountCode();
            PointsSystem.pointsConversion(member.getcID(), oID);
            this.viewHomepage(member.getUsername());
        }
    }
}
