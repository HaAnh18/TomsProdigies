//https://algs4.cs.princeton.edu/53substring/BoyerMoore.java.html
/* The algorithm is being used to enhance the search function. */
public class SearchAlgorithm {
    // Attributes
    private int R; //This is the Raix parameter to limit the amount of characters to 256.
    private int[] right;
    private String pattern;

    //This method is used to read and store data from user input.
    public SearchAlgorithm(String pattern) {
        this.R = 256;
        this.pattern = pattern;
        //For loop to loop through the String from right to left.
        this.right = new int[R];
        for (int character = 0; character < R; character++) {
            right[character] = -1;
        }

        for (int j = 0; j < pattern.length(); j++) {
            right[pattern.charAt(j)] = j;
        }
    }

    //BooyerMoore Algorithm to enhance the search for user input.
    public boolean boyerMooreSearch(String text, String pattern) {
        int lengthOfText = text.length(), lengthOfPattern = pattern.length();

        int numOfSkip;
        //for loop to read through the data input
        for (int i = 0; i <= lengthOfText - lengthOfPattern; i += numOfSkip) {
            numOfSkip = 0;
            for (int j = lengthOfPattern - 1; j >= 0; j--) {
                if (pattern.charAt(j) != text.charAt(i + j)) {
                    numOfSkip = Math.max(1, j - this.right[text.charAt(i + j)]);
                    break;
                }// if the pattern is not the same, the if function will skip the character and run to the next one until it can find a match pattern with the user input.
            }
            if (numOfSkip == 0) return true;
        }

        return false;
    }
}
