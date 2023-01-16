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

package fileMethods;

import java.util.Scanner;

// This class is used to collect input of the users(option 1,2,3 etc...)
public class UserInput {
    public static String rawInput() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Please enter the number to select an option:  ");
            return sc.nextLine();
        }
    }
}
