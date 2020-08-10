package org.homesoft.dcp;

/**
 * This problem was asked by Quora.
 * <p>
 * Given a string, find the palindrome that can be made by inserting the fewest number of characters as possible
 * anywhere in the word. If there is more than one palindrome of minimum length that can be made, return the
 * lexicographically earliest one (the first one alphabetically).
 * <p>
 * For example, given the string "race", you should return "ecarace", since we can add three letters to it
 * (which is the smallest amount to make a palindrome). There are seven other palindromes that can be made from
 * "race" by adding three letters, but "ecarace" comes first alphabetically.
 * <p>
 * As another example, given the string "google", you should return "elgoogle".
 */
public class Number34Medium {

    public static void main(String[] args) {
        final Number34Medium solution = new Number34Medium();

        System.out.println("ecarace".equals(solution.get("race")));
        System.out.println("elgooogle".equals(solution.get("google")));
        System.out.println(solution.get("арозаупаланалапу").equals("арозаупаланалапуазора"));
    }

    private String get(String input) {
        if (isPalindrome(input)) {
            return input;
        }

        final int length = input.length();
        if (input.charAt(0) != input.charAt(length - 1)) {
            final String left = input.charAt(length - 1) + get(input.substring(0, length - 1)) + input.charAt(length
                    - 1);
            final String right = input.charAt(0) + get(input.substring(1)) + input.charAt(0);

            return left.length() < right.length() || (left.length() == right.length() && 0 > left.compareTo(right))
                    ? left
                    : right;
        }

        return input.charAt(0) + get(input.substring(1, length - 1)) + input.charAt(length - 1);
    }

    private boolean isPalindrome(String input) {
        final int length = input.length();
        for (int i = 0; i != length >> 1; ++i) {
            if (input.charAt(i) != input.charAt(length - i - 1)) {
                return false;
            }
        }

        return true;
    }
}
