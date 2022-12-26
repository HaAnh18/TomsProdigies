import java.io.IOException;
import java.util.Random;

public class OIDRun {
    public static void main(String[]args) throws IOException{
//        Order.onlyOID();
        Order yes = new Order();
        Random random = new Random();
        int id = random.nextInt(999);
        String oID = yes.oIDDataForValidate( String.format("O%03d", id));
        System.out.println(oID);
//        yes.oIDDataForValidate("0553");
//        System.out.println(yes.oIDDataForValidate("O553"));
////        System.out.printf(yes.toString());
    }
}
