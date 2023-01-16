/*
  RMIT University Vietnam
  Course: COSC2081 Programming 1
  Semester: 2022C
  Assessment: Assignment 3
  Author: Tom's Prodigies
  ID: Nguyen Tran Ha Anh - s3938490
      Hoang Tuan Minh - s3924716
      Dang Kim Quang Minh - s3938024
      Nguyen Gia Bao - s3938143
  Acknowledgement:

*/

package menu;

import fileMethods.CreateTable;
import fileMethods.UserInput;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AuthenticationSystem {

    // Main menu when user start our system
    public void mainMenu() throws IOException, InterruptedException, ParseException {
        System.out.println("COSC2081 GROUP ASSIGNMENT");
        System.out.println("STORE ORDER MANAGEMENT SYSTEM");
        System.out.println("Instructor: Mr. Tom Huynh & Dr. Phong Ngo");
        System.out.println("Group: Tom's Prodigies");
        CreateTable.setShowVerticalLines(true);

        CreateTable.setHeaders("sID", "FULL NAME");
        CreateTable.addRow("s3938490", "Nguyen Tran Ha Anh");
        CreateTable.addRow("s3924716", "Hoang Tuan Minh");
        CreateTable.addRow("s3938024", "Dang Kim Quang Minh");
        CreateTable.addRow("s3938143", "Nguyen Gia Bao");
        CreateTable.print();
        CreateTable.setHeaders(new String[0]);
        CreateTable.setRows(new ArrayList<String[]>());

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
