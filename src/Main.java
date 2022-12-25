import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {
        Customer account = new Customer();
        Admin admin = new Admin();
        Scanner scanner = new Scanner(System.in);
//        admin.getAllCustomerInfo();
//        account.register();
//        String username = scanner.nextLine();
//        account.registerUsername(username);
//        File file = new File("./src/test.txt");
        String path = "./src/categories.txt";
//        admin.addProduct();
//        Product product = new Product();
//        File orderSession = new File("orderSession.txt");


        Order order = new Order();
        String userName = "minhhoang";
        String[] obj = ReadDataFromTXTFile.readSpecificLine(userName, 6, "./src/customers.txt", ",");
//        System.out.println(Arrays.toString(obj));
        String[] productInfo = ReadDataFromTXTFile.readSpecificLine("Dell XPS 17", 1, "./src/items.txt", ",");
        Customer member = new Customer(obj[0], obj[1], obj[2], obj[3], obj[4], obj[5], obj[6], obj[7], Double.parseDouble(obj[8]));
        Product product = new Product((productInfo[0]), productInfo[1], Double.parseDouble(productInfo[2]), productInfo[3]);
        ;
        ;
        order.createNewOrder(member, product);
        String[] data = ReadDataFromTXTFile.readSpecificLine("C001", 1, "./src/ordersHistory.txt", ",");
        System.out.println(Arrays.toString(data));
        ArrayList<String[]> orders = new ArrayList<>(); // Create an arraylist to contain all customers' information
        Scanner fileScanner = new Scanner(new File("./src/ordersHistory.txt"));
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
        product.getAllProductInfo();
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
    }
}