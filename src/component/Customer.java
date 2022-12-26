package component;


import java.io.IOException;

public class Customer extends Account {
    public Customer(String cID, String name, String email, String address,
                    String phone, String customerType, String userName,
                    String password, Long totalSpending) throws IOException {
        super(cID, name, email, address, phone, customerType, userName, password, totalSpending);
    }

    public Customer() throws IOException {
    }
}
