//https://algs4.cs.princeton.edu/53substring/BoyerMoore.java.html
/* The algorithm is being used to enhance the search function. */
public class SearchAlgorithm {
    private int R;
    private int[] right;
    private String pattern;

    public SearchAlgorithm(String pattern) {
        this.R = 256;
        this.pattern = pattern;

        this.right = new int[R];

        for (int character = 0; character < R; character++) {
            right[character] = -1;
        }

        for (int j = 0; j < pattern.length(); j++) {
            right[pattern.charAt(j)] = j;
        }
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }


    public boolean boyerMooreSearch(String text, String pattern) {
        int lengthOfText = text.length(), lengthOfPattern = pattern.length();

        int numOfSkip;

        for (int i = 0; i <= lengthOfText - lengthOfPattern; i += numOfSkip) {
            numOfSkip = 0;
            for (int j = lengthOfPattern - 1; j >= 0; j--) {
                if (pattern.charAt(j) != text.charAt(i + j)) {
                    numOfSkip = Math.max(1, j - this.right[text.charAt(i + j)]);
                    break;
                }
            }
            if (numOfSkip == 0) return true;
        }

        return false;
    }

    public int getRadix() {
        return R;
    }

    public int[] getRight() {
        return right;
    }

    public void setRight(int[] right) {
        this.right = right;
    }
}
