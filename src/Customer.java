import java.io.IOException;

public class Customer extends Account {
    public Customer(String cID, String name, String email, String address,
                    String phone, String customerType, String userName,
                    String password, Long totalSpending, Long totalPoints) throws IOException {
        super(cID, name, email, address, phone, customerType, userName, password, totalSpending, totalPoints);
    }

    public Customer() throws IOException {
    }
}
