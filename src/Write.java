import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Write {

    public static void rewriteFile(String filepath, String header, String data) throws IOException {
        FileWriter newFile = new FileWriter(filepath, true);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));

        if (filepath.length() == 0) {
            newFile.append(header);
            newFile.append("\n");
        }
        bufferedReader.close();

        try {
            ArrayList<String> newDatabase = new ArrayList<>();

            newDatabase.add(data);

            for (int i = 0; i < newDatabase.size(); i++) {
                newFile.append(newDatabase.get(i));
                newFile.append("\n");
            }
            newFile.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
