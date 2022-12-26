import java.util.Scanner;

public class OptionInput {
    public static String input() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Please enter the number to select an option:  ");
            String option = sc.nextLine();

            return option;
        }
    }
}
