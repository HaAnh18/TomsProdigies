import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadDataFromTXTFile {
    public static String[] readColString(int col, String filepath, String delimiter) throws IOException
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
            throw new RuntimeException();
        }
        return colData.toArray(new String[0]);
    }

    public static Integer[] readColInt(int col, String filepath, String delimiter) throws IOException{
        String[] data;
        String currentLine;
        ArrayList<Integer> colData = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(filepath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((currentLine = bufferedReader.readLine()) != null) {
                data = currentLine.split(delimiter);
                colData.add(Integer.valueOf(data[col]));
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return colData.toArray(new Integer[0]);

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

    public static String[] getSpecificLine(String fetch, int column, String filePath, String delimiter) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        String currentLine;
        String[] data;
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            while ((currentLine = br.readLine()) != null) {
                data = currentLine.split(delimiter);
                if (data[column].equals(fetch)) {
                    return data;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new String[0];
    }
}
