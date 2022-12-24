import java.io.IOException;



public class Customer extends Account{
    //Attributes
    private double totalSpending;

    //Constructor

    public Customer(String cID, String name, String email, String address, String phone, String customerType, String userName, String password, double totalSpending) throws IOException {
        super(cID, name, email, address, phone, customerType, userName, password);
        this.totalSpending = totalSpending;
    }

    public Customer(double totalSpending) throws IOException {
        this.totalSpending = totalSpending;
    }
    //getter and setter

    public double getTotalSpending() {
        return totalSpending;
    }

    public double setTotalSpending(double totalSpending) {
        this.totalSpending = totalSpending;
        return totalSpending;
    }
}
