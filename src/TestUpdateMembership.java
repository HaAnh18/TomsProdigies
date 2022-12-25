import java.io.IOException;
import java.util.ArrayList;

public class TestUpdateMembership {
    public void updateMembership(String filepath, String userName) throws IOException {
        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/customers.txt");
        for (int i = 0; i < database.size(); i++) {
            System.out.println(i);
            if (database.get(i)[6].equals(userName)) {

            }
        }
    }
}
