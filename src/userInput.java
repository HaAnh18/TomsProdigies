import java.util.Scanner;

public class userInput {
    public static String rawInput() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Please enter the number to select an option:  ");
            String option = sc.nextLine();

            return option;
        }
    }
}
