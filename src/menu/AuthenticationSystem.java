package menu;

import fileMethods.CreateTable;
import fileMethods.UserInput;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

public class AuthenticationSystem {
    public void mainMenu() throws IOException, InterruptedException, ParseException {
        System.out.println("COSC2081 GROUP ASSIGNMENT");
        System.out.println("STORE ORDER MANAGEMENT SYSTEM");
        System.out.println("Instructor: Mr. Tom Huynh & Dr. Phong Ngo");
        System.out.println("Group: Tom's Prodigies");
        CreateTable createTable = new CreateTable();
        createTable.setShowVerticalLines(true);

        createTable.setHeaders("sID", "FULL NAME");
        createTable.addRow("s3938490", "Nguyen Tran Ha Anh");
        createTable.addRow("s3924716", "Hoang Tuan Minh");
        createTable.addRow("s3938024", "Dang Kim Quang Minh");
        createTable.addRow("s3938143", "Nguyen Gia Bao");
        createTable.print();

        System.out.println("\n================================================= WELCOME TO TOM'S PRODIGIES STORE =================================================");
        System.out.println("1. Use as a customer");
        System.out.println("2. Use as a administrator");
        System.out.println("3. Exit");

        AdminMenu adminMenu = new AdminMenu();
        CustomerMenu customerMenu = new CustomerMenu();
        String option = UserInput.rawInput();
        switch (option) {
            case "1":
                customerMenu.view();
            case "2":
                adminMenu.view();
            case "3":
                System.exit(1);
            default:
                System.out.println("THERE IS NO MATCHING RESULT, PLEASE TRY AGAIN!!!");
                TimeUnit.SECONDS.sleep(1);
                this.mainMenu();
        }
    }
}