import menu.AuthenticationSystem;

import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        AuthenticationSystem authenticationSystem = new AuthenticationSystem();
        authenticationSystem.mainMenu();

//        order.getAllOrderInfo();
//        String[] customerInfo = ReadDataFromTXTFile.readSpecificLine("nana", 6, "./src/dataFile/customers.txt", ",");
//        Customer member = new Customer(customerInfo[0], customerInfo[1], customerInfo[2], customerInfo[3],
//                customerInfo[4], customerInfo[5], customerInfo[6], customerInfo[7],
//                Long.parseLong(customerInfo[8]), Long.parseLong(customerInfo[9]));
//        CustomerMenu customerMenu = new CustomerMenu();
//        customerMenu.createOrder(member);
//        order.searchOrder("");
//        String[] customerInfo = ReadDataFromTXTFile.readSpecificLine("nana123", 6, "./src/dataFile/customers.txt", ",");
//        Customer member = new Customer(customerInfo[0], customerInfo[1], customerInfo[2], customerInfo[3],
//                customerInfo[4], customerInfo[5], customerInfo[6], customerInfo[7],
//                Long.parseLong(customerInfo[8]), Long.parseLong(customerInfo[9]));
//        System.out.println(member.getCustomerType());
//        System.out.println(customerInfo[5]);
//        String[] orderInfo = ReadDataFromTXTFile.readSpecificLine("T842", 0,
//                "./src/dataFile/billingHistory.txt", ",");
//        Long totalPayment = Long.parseLong(orderInfo[2]);
//        Discount discount = new Discount();
//        String[] customerInfo = ReadDataFromTXTFile.readSpecificLine("nana", 6, "./src/dataFile/customers.txt", ",");
//
//        Customer member = new Customer(customerInfo[0], customerInfo[1], customerInfo[2], customerInfo[3],
//                customerInfo[4], customerInfo[5], customerInfo[6], customerInfo[7],
//                Long.parseLong(customerInfo[8]), Long.parseLong(customerInfo[9]));
//
//        discount.giveDiscountCode(member, totalPayment);
//        discount.displayCustomerDiscountCode(member);
////        CustomerMenu customerMenu = new CustomerMenu();
//        customerMenu.view();
//        customerMenu.viewHomepage("minhhoang");
//        Discount discount = new Discount();
//        Order order = new Order();
//        order.createNewProductSale("lap");
//        order.checkProductSales("lap");
//        order.productSales("lap");
//        Scanner scanner = new Scanner(System.in);
//        Account account = new Customer();
//        Admin admin = new Admin();
//        CustomerMenu1 customerMenu = new CustomerMenu1();
////        customerMenu.view();
//        Product product = new Product();
//        Order order = new Order();
////        System.out.println(account.hashing("2222222"));
//        String userName = "minhhoang";
//        String[] obj = ReadDataFromTXTFile.readSpecificLine(userName, 6, "./src/customers.txt", ",");
//////        System.out.println(Arrays.toString(obj));
//////        String[] productInfo = ReadDataFromTXTFile.readSpecificLine("Dell XPS 17", 1, "./src/items.txt", ",");
//        Customer member = new Customer(obj[0], obj[1], obj[2], obj[3], obj[4], obj[5], obj[6], obj[7], Long.parseLong(obj[8]));
//        Discount discount = new Discount();
//        discount.giveDiscountCode(member,50000000L);
//        product.getProductHaveId();
//        String choiceOrder = UserInput.rawInput();
//        System.out.println("Please enter a quantity that you want to add: ");
//        int quantity = Integer.parseInt(scanner.nextLine());
//        ArrayList<String[]> productList = ReadDataFromTXTFile.readAllLines("./src/items.txt");
//
//        String[] productInfo = new String[3];
//        for (int i = 0; i < productList.size(); i++) {
//            if (i == Integer.parseInt(choiceOrder)) {
//                productInfo = ReadDataFromTXTFile.readSpecificLine(productList.get(i)[1], 1, "./src/items.txt", ",");
//            }
//        }
//        Product product1 = new Product((productInfo[0]), productInfo[1], Long.parseLong((productInfo[2])), productInfo[3]);
////
//////        order.getCustomerCart(member);
//        Cart cart = new Cart();
//        cart.addToCart(member, product1, quantity);
////        cart.getCustomerCart(member);
////        System.out.println("Delete: ");
////        String choiceOrder = UserInput.rawInput();
//////        System.out.println("Please enter a quantity that you want to add: ");
//////        int quantity = Integer.parseInt(scanner.nextLine());
////        ArrayList<String[]> productList = ReadDataFromTXTFile.readAllLines("./src/customerCart.txt");
////
////        String[] productInfo = new String[3];
////        for (int i = 0; i< productList.size(); i++) {
////            if (i == Integer.parseInt(choiceOrder)) {
////                productInfo = ReadDataFromTXTFile.readSpecificLine(productList.get(i)[1],1,"./src/items.txt",",");
////            }
////        }
////        Product product1 = new Product((productInfo[0]), productInfo[1], Long.parseLong((productInfo[2])), productInfo[3]);
////        cart.deleteItemInCart("./src/customerCart.txt",member.getcID(),product1);
////        cart.addToCart(member,product1,quantity);
////        Cart cart = new Cart();

//        ArrayList<String[]> discountCode = discount.discountCodeList(member);
//        String discountCodeCustomer = new String();
//        Random rd = new Random();
//        int i = rd.nextInt(999);
//        String oID = order.oIDDataForValidate(String.format("O%03d", i));
//        ArrayList<String[]> cartList = cart.cartList(member);
//        for (int a = 0; a < cartList.size(); a++) {
//            String[] productInfo1 = new String[3];
//            productInfo1 = ReadDataFromTXTFile.readSpecificLine(cartList.get(a)[1], 1, "./src/items.txt", ",");
//            Product product2 = new Product(productInfo1[0], productInfo1[1], Long.parseLong(productInfo1[2]), productInfo1[3]);
//            order.createNewOrder(member, product2, oID, Integer.parseInt(cartList.get(a)[3]));
//        }
//        member.searchOrder(oID);
//        order.getTotalPaymentEachOrderId(member, oID);
//        discount.displayCustomerDiscountCode(member);
////
//////
////////
//        String choiceOrder1 = UserInput.rawInput();
////        System.out.println(Arrays.toString(ReadDataFromTXTFile.readSpecificLine(discountCode.get(0)[1], 1, "./src/customerDiscountCode.txt", ",")));
//        for (int m = 0; m < discountCode.size(); m++) {
//            if (m == Integer.parseInt(choiceOrder1)) {
//                discountCodeCustomer = discountCode.get(m)[1];
////                System.out.println(discountCode.get(m)[1]);
////                discountCodeCustomer = ReadDataFromTXTFile.readSpecificLine(discountCode.get(m)[1], 1, "./src/customerDiscountCode.txt", ",");
//            }
//        }
////        TimeUnit.SECONDS.sleep(1);
////        System.out.println(discountCodeCustomer[1]);
////        order.getOrderInfoById(oID);
//        order.getTotalPaymentAfterApplyDiscountCode(oID,discountCodeCustomer,member);
//        cart.deleteAllItemsInCart("./src/customerCart.txt","C003");
//        CreateTable createTable = new CreateTable();
//        createTable.setShowVerticalLines(true);
//        createTable.setHeaders("TOTAL SPENDING");
//        createTable.addRow(String.valueOf(spending));
//        order.getOrderInfo(member);
//        createTable.print();
//        order.createNewOrder(member,product1);
//        product.getProductToCreateOrder();
//        account.searchOrder("0289");
//        Scanner scanner = new Scanner(System.in);
//        Admin.calculateRevenue(Admin.getTotalRevenue());
//        customerMenu.viewHomepage("minhhoang");
//        String[] customerInfo = ReadDataFromTXTFile.readSpecificLine("minhhoang",6,"./src/customers.txt",",");

//        admin.getAllCustomerInfo();
//        account.register();
//        String username = scanner.nextLine();
//        account.registerUsername(username);
//        File file = new File("./src/test.txt");
//        String path = "./src/categories.txt";
//        admin.addProduct();
//        Product product = new Product();
//        File orderSession = new File("orderSession.txt");
//        customerMenu.viewHomepage("minhhoang");
//        Order order = new Order();
//        String userName = "minhhoang";
//        String[] obj = ReadDataFromTXTFile.readSpecificLine(userName, 6, "./src/customers.txt", ",");
////        System.out.println(Arrays.toString(obj));
//        String[] productInfo = ReadDataFromTXTFile.readSpecificLine("Dell XPS 17", 1, "./src/items.txt", ",");
//        Customer member = new Customer(obj[0], obj[1], obj[2], obj[3], obj[4], obj[5], obj[6], obj[7], Long.parseLong(obj[8]));
//        Product product = new Product((productInfo[0]), productInfo[1], Long.parseLong((productInfo[2])), productInfo[3]);
//        ;
//        ;
//        order.createNewOrder(member, product);
//        Product product = new Product();
//        product.getProductToCreateOrder();
//        String choiceOrder = UserInput.rawInput();
//        ArrayList<String[]> productList = ReadDataFromTXTFile.readAllLines("./src/items.txt");
//        for (int i = 0; i< productList.size(); i++) {
//            if (i == Integer.parseInt(choiceOrder)) {
//                String[] productInfo = ReadDataFromTXTFile.readSpecificLine(productList.get(i)[1],1,"./src/items.txt",",");
//                System.out.println(Arrays.toString(productInfo));
//            }
//        }
//        admin.deleteCategory("./src/categories.txt","Laptop");
//        String[] data = ReadDataFromTXTFile.readSpecificLine("C001", 1, "./src/ordersHistory.txt", ",");
//        System.out.println(Arrays.toString(data));
//        ArrayList<String[]> orders = new ArrayList<>(); // Create an arraylist to contain all customers' information
//        Scanner fileScanner = new Scanner(new File("./src/ordersHistory.txt"));
//        order.createNewOrder(member,product);
//        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/ordersHistory.txt");
//        for (int i = 0; i < database.size(); i++) {
//            if (database.get(i)[1].equals(member.getcID()))
//            /** If the system could find out the username in customers' file and the new name is validated
//             * then the system allow customer to update their information
//             */ {
//                 orders.add(database.get(i));
//            }
//        }
////        System.out.println(Arrays.toString(orders.get(1)));
//        CreateTable createTable = new CreateTable();
//        createTable.setShowVerticalLines(true);
//        createTable.setHeaders("OID", "CID", "PID", "MEMBERSHIP","TOTAL PAYMENT","TIMESTAMP","TOTAL SPENDING","ORDER STATUS", "DELIVERING STATUS");
//        for (int i = 0; i < orders.size(); i++) {
////            CreateTable.addRow(user.get(i)[0], user.get(i)[1],user.get(i)[2],user.get(i)[3],user.get(i)[4],user.get(i)[5],user.get(i)[6]);
//            createTable.addRow(orders.get(i)[0], orders.get(i)[1], orders.get(i)[2], orders.get(i)[3],
//                    orders.get(i)[4],orders.get(i)[5],orders.get(i)[6],orders.get(i)[7],orders.get(i)[8]);
//        }
//
//        createTable.print();
//        product.getAllProductInfo();
//        admin.updatePrice("./src/items.txt", "50", "I001-2001");
//        Path path1 = Paths.get("./src/customers.txt");
//        int id = (int) Files.lines(path1).count();
//        System.out.println(id);
//        ArrayList<String[]> customerOrder = new ArrayList<>();
//        String[] data = ReadDataFromTXTFile.readSpecificLine("C001",0,"./src/orderHistory.txt",",");
//        customerOrder.add(data);
//        System.out.println(customerOrder);
//        admin.addProduct();
//        System.out.println(product.checkCategory("iphone"));
//        product.registerCategory("iphone");
//        System.out.println(product.checkCategory("iPhone"));
//        System.out.println(product.registerCategory("Laptop"));
//        System.out.println(product.validateTile("iphone new 12"));
//        ReadDataFromTXTFile.readSpecificLine("minhhoang",6,"./src/customers.txt",",");
//        String[] database = ReadDataFromTXTFile.readSpecificLine("minhhoang",6,"./src/customers.txt",",");
//        System.out.println(database[5]);
//        account.checkMembership("minhhoang");
//        System.out.println(account.validatePhoneNumber("0424173255"));
//        account.updateName("./src/customers.txt","Nana Nana", "minhhoang");
//        account.updateAddress("./src/customers.txt","20 Irwin Street", "minhhoang");
//        account.updateEmail(path,"hong.wang@gmail.com", "minhhoang");
//        account.updatePhone(path,"0424173255", "minhhoang");
//        account.updatePassword(path,"Admin1234", "minhhoang");
//        admin.getAllCustomerInfo();
//        System.out.println(product.checkCategory("Laptop"));
//        admin.updatePrice("./src/items.txt","45","I001-2001");
//        admin.addProduct();
//        admin.addProduct();
//        Product product =new Product();
//        product.registerCategory("laptop");
        //create FAQ file
//                try {
//                    File file = new File("./src/FAQ.txt");
//                    file.createNewFile();
//                    System.out.println("File: " + file);
//                } catch(Exception e) {
//                    e.printStackTrace();
//                }
//        System.out.println(Admin.dateValidate("29/12/2022"));
//        Admin admin = new Admin();
//        System.out.println(admin.getDailyRevenue());

//        FAQ.searchQNA();
    }
}
