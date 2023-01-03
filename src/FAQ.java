import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class FAQ {

    private int qaaID;
    private String question;
    private String answer;
//Constructor for FAQ class.
    public FAQ(int qaaID, String question, String answer) {
        this.qaaID = qaaID;
        this.question = question;
        this.answer = answer;
    }

    public FAQ() {
    }

    //This method create FAQ text file to store questions and answers for the FAQ session.
public static void createDefaultFAQ(int qaaID, String question, String answer) throws IOException {

    PrintWriter pw;

    pw = new PrintWriter(new FileWriter("./src/FAQ.txt", true));

    pw.println(qnaID + "," + question + "," + answer);
    pw.close();
}


    //This method create a menu for customer to select their FAQ option.

    public static void searchQNA() throws IOException {
        //using method ReadDataFromTXTFile to read specific column which is QID in the text file
        String[] faq = ReadDataFromTXTFile.readColString(0, "./src/FAQ.txt", ",");
        faq = Arrays.stream(faq).distinct().toArray(String[]::new);

        String option = UserInput.rawInput(); // using rawInput method to input a string that ask user to choose an option

        //Using matchResult method to put all the data that this method get from specific columns to put into an arraylist
        ArrayList<String[]> matchResult = new ArrayList<>(getMatchResult(faq[0]).size());

        boolean run = true;

        CreateTable table = new CreateTable();

        switch (option) {
            case "1":
                matchResult = getMatchResult(faq[1]);

                break;
            case "2":
                matchResult = getMatchResult(faq[2]);

                break;
            case "3":
                matchResult = getMatchResult(faq[3]);

                break;
            case "4":
                matchResult = getMatchResult(faq[4]);

                break;
            case "5":
                matchResult = getMatchResult(faq[5]);

                break;

            case "6":
                matchResult = getMatchResult(faq[6]);

                break;

            case "=":
                run = false; // This will not print out msg for no message found and give option to exit
                break;

            // for menu add 1 more but will be menu.something();
        }
        //set this condition for when user input a number that is out of choices range
        if (run) {
            if (matchResult.size() == 0) {
                System.out.println("Sorry, there is no answer for this question");
            }
            //set this condition for when user input a number that is within choices range
            if (matchResult.size() > 0) {
                System.out.println("Here is your answer:");
                table.setShowVerticalLines(true);
                table.setHeaders("Answer");

                //using this for loop read and adding the next rows into a table from a specific column
                for (int i = 0; i < matchResult.size(); i++) {
                    table.addRow(matchResult.get(i)[2]);
                }
                table.print();

                table.setHeaders(new String[0]);
                table.setRows(new ArrayList<String[]>());
            }
        }
    }
    public static ArrayList<String[]> getMatchResult(String data) throws IOException {
        // using Read method that will read a specific column which is QID form FAQ text file
        String[] faq = ReadDataFromTXTFile.readColString(0, "./src/FAQ.txt", ",");
        // using Read method that will read a specific column which is Question form FAQ text file
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

    //This method will print the question from a FAQ text file and the function into the terminal
    public static void FAQPrint() throws IOException {

        //read data from text file
        String[] readQuestionList = ReadDataFromTXTFile.readColString(1, "./src/FAQ.txt", ",");
        for (int i = 1; i < readQuestionList.length; i++) {
            System.out.println(readQuestionList[i]);
        }
        System.out.println("=.Exit");
//        System.out.println(" ");

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
