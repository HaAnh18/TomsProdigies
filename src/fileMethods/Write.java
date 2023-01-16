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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Write {

    // This method will rewrite all content in a specific file
    public static void rewriteFile(String filepath, String header, String data) throws IOException {
        FileWriter newFile = new FileWriter(filepath, true);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));

        if (filepath.length() == 0) {
            // This will check the file, if the file is completely blank,
            // it will then write in the header first, after that it will
            // move to the next row and await for further move.
            newFile.append(header);
            newFile.append("\n");
        }
        bufferedReader.close();

        try {
            // This will use an empty ArrayList, that will then take another data
            // from another ArrayList that is within another method that this method support.
            ArrayList<String> newDatabase = new ArrayList<>();

            newDatabase.add(data);

            // This will then take the new data that this method's ArrayList and write them back into the file.
            for (String s : newDatabase) {
                newFile.append(s);
                newFile.append("\n");
            }
            newFile.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
