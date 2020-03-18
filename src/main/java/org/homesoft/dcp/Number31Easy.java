package org.homesoft.dcp;

/**
 * This problem was asked by Google.
 * <p>
 * The edit distance between two strings refers to the minimum number of character insertions, deletions, and
 * substitutions required to change one string to the other. For example, the edit distance between “kitten” and
 * “sitting” is three: substitute the “k” for “s”, substitute the “e” for “i”, and append a “g”.
 * <p>
 * Given two strings, compute the edit distance between them.
 */
public class Number31Easy {
    public static void main(String[] args) {
        final Number31Easy solution = new Number31Easy();

        /*
         *     k i t t e n
         *   0 1 2 3 4 5 6
         * s 1 1 2 3 4 5 6
         * i 2 2 1 2 3 4 5
         * t 3 3 2 1 2 3 4
         * t 4 4 3 2 1 2 3
         * i 5 5 4 3 2 2 3
         * n 6 6 5 4 3 3 2
         * g 7 7 6 5 4 4 3
         */
        System.out.println(solution.get("kitten", "sitting") == 3);
        System.out.println(solution.get("war", "peace") == 4);
        System.out.println(solution.get("war", "") == 3);
        System.out.println(solution.get("", "peace") == 5);
        System.out.println(solution.get("aaa", "bbbbbb") == 6);
        System.out.println(solution.get("aaaaaa", "bbb") == 6);
    }

    private int get(String word1, String word2) {
        if (word1.length() > word2.length()) {
            String temp = word1;
            word1 = word2;
            word2 = temp;
        }

        int[] prevRow = new int[1 + word1.length()];
        for (int i = 0; i != prevRow.length; ++i) {
            prevRow[i] = i;
        }

        int[] newRow = new int[1 + word1.length()];
        for (int j = 0; j != word2.length(); ++j) {
            newRow[0] = 1 + j;
            for (int i = 0; i != word1.length(); ++i) {
                newRow[1 + i] = Math.min((word2.charAt(j) != word1.charAt(i) ? 1 : 0) + prevRow[i],
                        Math.min(1 + prevRow[1 + i], 1 + newRow[i]));
            }

            int[] temp = prevRow;
            prevRow = newRow;
            newRow = temp;
        }

        return prevRow[prevRow.length - 1];
    }
}
