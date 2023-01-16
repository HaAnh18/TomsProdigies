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
package bonusFeatures;

import fileMethods.ReadDataFromTXTFile;

import java.io.IOException;
import java.util.ArrayList;

public class FAQ {

    //This method will print the question from a FAQ text file and the function into the terminal
    public static void FAQPrint() throws IOException {

        // Read data from text file
        String[] readQuestionList = ReadDataFromTXTFile.readColString(1, "./src/dataFile/FAQ.txt", ",");
        for (int i = 1; i < readQuestionList.length; i++) {
            System.out.println(readQuestionList[i]);
        }
        System.out.println("7. See all");
        System.out.println("=. Exit");
    }

    // Display all the question for customer
    public static void seeAll() {
        ArrayList<String[]> faq = ReadDataFromTXTFile.readAllLines("./src/dataFile/FAQ.txt");
        for (int i = 1; i < faq.size(); i++) {
            System.out.println(faq.get(i)[1]);
            System.out.println("------> " + faq.get(i)[2]);
        }
    }
}
