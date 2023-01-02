import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class FAQ {

    private int qaaID;
    private String question;
    private String answer;

    public FAQ(int qaaID, String question, String answer) {
        this.qaaID = qaaID;
        this.question = question;
        this.answer = answer;
    }

    public FAQ() {
    }

    public static void createDefaultFAQ(int qaaID, String question, String answer) throws IOException {
        PrintWriter pw;

        pw = new PrintWriter(new FileWriter("./src/FAQ.txt", true));

        pw.println(qaaID + "," + question + "," + answer);
        pw.close();
    }

//    public static void searchQNA(String qaaID) throws IOException{
//        ArrayList<String[]> FAQ = new ArrayList<>();
//        ArrayList<String[]> database = ReadDataFromTXTFile.readAllLines("./src/FAQ.txt");
//
//        for (int i = 1; i < database.size(); i++) {
//            if (database.get(i)[0].equals(qaaID))
//                /* If the system could find out the category in items.txt file
//                 */
//            {
//                FAQ.add(database.get(i));
//            }
//        }
//        CreateTable createTable = new CreateTable();
//
//        createTable.setShowVerticalLines(true);
//        createTable.setHeaders( "Question", "Answer");
//        for (String[] categoryOutput : FAQ) {
//            createTable.addRow(categoryOutput[1], categoryOutput[2]);
//        }
//        createTable.print();
//    }

    //

    public static void searchQNA() throws IOException {
        String[] faq = ReadDataFromTXTFile.readColString(0, "./src/FAQ.txt", ",");
        faq = Arrays.stream(faq).distinct().toArray(String[]::new);

        String option = UserInput.rawInput();

        ArrayList<String[]> matchResult = new ArrayList<>(getMatchResult(faq[0]).size());

        CreateTable table = new CreateTable();
        switch (option) {
            case "1":
                matchResult = getMatchResult(faq[1]);
                System.out.println("this is 1");
                break;
            case "2":
                matchResult = getMatchResult(faq[2]);
                System.out.println("this is 2");
                break;
            case "3":
                matchResult = getMatchResult(faq[3]);
                System.out.println("this is 3");
                break;
//            case "4":
//                matchResult = getMatchResult(faq[4]);
//                System.out.println("this is 4");
//                break;
            // for menu add 1 more but will be menu.something();
        }
        if (matchResult.size() == 0) {
            System.out.println("Sorry, there is no answer for this question");
        }
        if (matchResult.size() > 0) {
            System.out.println("Available Question");
            table.setShowVerticalLines(true);
            table.setHeaders("Answer");

            for (int i = 0; i < matchResult.size(); i++) {
                table.addRow(matchResult.get(i)[2]);
            }
            table.print();

            table.setHeaders(new String[0]);
            table.setRows(new ArrayList<String[]>());
        }

    }

    public static ArrayList<String[]> getMatchResult(String data) throws IOException {
        String[] faq = ReadDataFromTXTFile.readColString(0, "./src/FAQ.txt", ",");
        String[] question = ReadDataFromTXTFile.readColString(1, "./src/FAQ.txt", ",");

        ArrayList<String[]> matchResult = new ArrayList<>();

        for (int i = 0; i < question.length; i++) {
            // use Boyer Moore Searching Algorithm
            SearchAlgorithm text = new SearchAlgorithm(data);
            boolean isFound = text.boyerMooreSearch(faq[i], data);


            if (isFound) {
                String[] specificLine = ReadDataFromTXTFile.getSpecificLine(question[i], 1, "./src/FAQ.txt", ",");
                matchResult.add(specificLine);
            }
        }
        return matchResult;
    }

    public static void FAQPrint() throws IOException {

        //read data from text file
        String[] readQuestionList = ReadDataFromTXTFile.readColString(1, "./src/FAQ.txt", ",");
//        ArrayList<String[]> questionList = new ArrayList<>();
        for (int i = 1; i < readQuestionList.length; i++) {
            System.out.println(readQuestionList[i]);
        }
        FAQ.searchQNA();
    }

    //getter and setter

    public int getQaaID() {
        return qaaID;
    }

    public void setQaaID(int qaaID) {
        this.qaaID = qaaID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
