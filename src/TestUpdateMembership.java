import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TestUpdateMembership {
    public static void main(String[] args) throws IOException {

        Account.updateMembership("./src/customers.txt","minhhoang");
    }
}
