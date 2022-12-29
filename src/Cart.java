import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Cart {
    // Constructor
    public Cart() {
    }

    public ArrayList<String[]> cartList(Customer customer)
    // Get all the product that have in that customer's cart
    {
        ArrayList<String[]> products = new ArrayList<>();

        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/customerCart.txt");
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[0].equals(customer.getcID()))
                /* If the system could find out the customer's ID in ordersHistory's file
                 */ {
                products.add(database.get(i));
            }
        } return products;
    }

    public void addToCart(Customer customer, Product product, int quantity) throws IOException {
        PrintWriter pw;
        pw = new PrintWriter(new FileWriter("./src/customerCart.txt", true));

        String customerID = customer.getcID();
        String productTitle = product.getTitle();
        Long singleUnitPrice = product.getPrice();
        Long paymentPrice = product.getPrice() * quantity;
//        switch (customer.getCustomerType()) {
//            case "Silver":
//                paymentPrice = (long)(paymentPrice * (1 - 0.05));
//                break;
//            case "Gold":
//                paymentPrice = (long)(paymentPrice * (1 - 0.1));
//                break;
//            case "Platinum":
//                paymentPrice = (long)(paymentPrice * (1 - 0.15));
//                break;
//            case "Regular":
//                break;
//        }
        pw.println(customerID + "," + productTitle + "," + singleUnitPrice + "," +
                quantity + "," + paymentPrice);
        pw.close();
    }

    public void getCustomerCart(Customer customer) {
        ArrayList<String[]> products = new ArrayList<>();

        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/customerCart.txt");
        for (int i = 1; i < database.size(); i++) {
            if (database.get(i)[0].equals(customer.getcID()))
                /* If the system could find out the customer's ID in ordersHistory's file
                 */ {
                products.add(database.get(i));
            }
        }
        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);
        createTable.setHeaders("OPTION", "CID", "PRODUCT'S TITLE", "QUANTITY", "SINGLE UNIT PRICE", "TOTAL PAYMENT");
        for (int i= 0 ; i< products.size(); i++) {
            createTable.addRow(String.valueOf(i+1),products.get(i)[0], products.get(i)[1], products.get(i)[2],
                    products.get(i)[3], products.get(i)[4]);
        }
        createTable.print();
    }

    public void deleteAllItemsInCart(String filepath, String cId) throws IOException {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/customerCart.txt");
        ArrayList<String[]> newDatabase = new ArrayList<>();
        for (int i = 0; i < database.size(); i++) {
            if (!database.get(i)[0].equals(cId)) {
                newDatabase.add(database.get(i));
            }
        }
        PrintWriter pw = new PrintWriter("./src/customerCart.txt");

        pw.write("");
        pw.close();

        for (String[] obj : newDatabase) {
            Write.rewriteFile(filepath, "#CID,Product's title,Single unit price,Quantity,Total payment",
                    String.join(",",obj));
        }
    }

    public void deleteItemInCart(String filepath, String cId, Product product) throws IOException {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/customerCart.txt");
        ArrayList<String[]> customerCart = new ArrayList<>();
        ArrayList<String[]> newDatabase = new ArrayList<>();
        customerCart.add(database.get(0));
        for (int i = 0; i < database.size(); i++) {
            if (database.get(i)[0].equals(cId)) {
                customerCart.add(database.get(i));
            }
        }
        for (int a=0; a< customerCart.size();a++) {
            if (!customerCart.get(a)[1].equals(product.getTitle())) {
                newDatabase.add(customerCart.get(a));
            }
        }
        PrintWriter pw = new PrintWriter("./src/customerCart.txt");

        pw.write("");
        pw.close();

        for (String[] obj : newDatabase) {
            Write.rewriteFile(filepath, "#CID,Product's title,Single unit price,Quantity,Total payment",
                    String.join(",",obj));
        }
    }
}
