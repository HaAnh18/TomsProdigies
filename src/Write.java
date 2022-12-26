import java.io.*;
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

    public static void deleteEmptyLine(String filepath) {
        String tempFile = "./src/temp.txt";
        File oldFile = new File(filepath);
        File newFile = new File(tempFile);

        String currentLine;

        try {
            FileWriter fileWriter = new FileWriter(tempFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);

            FileReader fileReader = new FileReader(filepath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((currentLine = bufferedReader.readLine()) != null) {
                if (!currentLine.equals("")) {
                    printWriter.println(currentLine);
                }
            }
            printWriter.flush();
            printWriter.close();
            fileWriter.close();
            bufferedReader.close();
            bufferedWriter.close();
            fileWriter.close();

            oldFile.delete();
            File dump = new File(filepath);
            newFile.renameTo(dump);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
