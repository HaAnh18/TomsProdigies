import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        Account account = new Account();
        Admin admin = new Admin();
        Scanner scanner = new Scanner(System.in);
//        admin.getAllCustomerInfo();
//        account.register();
//        String username = scanner.nextLine();
//        account.registerUsername(username);
//        File file = new File("./src/test.txt");
        String path = "./src/categories.txt";
//        admin.addProduct();
        Product product = new Product();
//        admin.addProduct();
//        System.out.println(product.checkCategory("laptop"));
        product.registerCategory("laptop");
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