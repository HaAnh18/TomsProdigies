import java.io.IOException;

public class TestAdmin {
    public static void main(String[] args) throws IOException {
        Admin admin = new Admin();
        admin.deleteProductCategory("./src/items.txt", "Laptop");
    }
}
