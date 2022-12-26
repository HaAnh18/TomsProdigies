import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadDataFromTXTFile {
    public static String[] readCol(int col, String filepath, String delimiter) throws IOException
    // https://www.youtube.com/watch?v=Ek6HFMNi3fs
    {
        String[] data;
        String currentLine;
        ArrayList<String> colData = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(filepath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((currentLine = bufferedReader.readLine()) != null) {
                data = currentLine.split(delimiter);
                colData.add(data[col]);
            }
        } catch (Exception e) {
        }
        return colData.toArray(new String[0]);
    }

    public static ArrayList<String[]> readAllLines(String filepath) {
        String[] data;
        String currentLine;
        ArrayList<String[]> allFileData = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(filepath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((currentLine = bufferedReader.readLine()) != null) {
                data = currentLine.split(",");
                allFileData.add(data);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return allFileData;
    }

    public static String[] readSpecificLine(String search, int col, String filepath, String delimiter) throws IOException {
        String[] lineData;
        String currentLine;
        ArrayList<String> specificLineData = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(filepath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((currentLine = bufferedReader.readLine()) != null) {
                lineData = currentLine.split(delimiter);
                if (lineData[col].equals(search)) {
                    return lineData;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return new String[0];
    }
}
