package org.homesoft.dcp;

import java.util.HashMap;
import java.util.Map;

/**
 * This problem was asked by Amazon.
 * <p>
 * Given an integer k and a string s, find the length of the longest substring that contains at most k distinct characters.
 * <p>
 * For example, given s = "abcba" and k = 2, the longest substring with k distinct characters is "bcb".
 */
public class Number13Hard {
    public static void main(String[] args) {
        Number13Hard solution = new Number13Hard();

        solution.test("abcba", 2);
        solution.test("abcba", 1);
        solution.test("", 1);
        solution.test("abracadabra", 2);
        solution.test("abracadabra", 3);
        solution.test("abracadabra", 4);
        solution.test("abracadabra", 5);
        solution.test("abracadabra", 6);
    }

    private void test(String input, int k) {
        System.out.printf("Input string: \"%s\", k == %d. Result: %s\n", input, k, get(input, k));
    }

    private String get(String input, int k) {
        if (input.isEmpty()) {
            return "";
        }

        int start = 0;
        int end = 0;
        int current = 0;
        final Map<Character, Integer> characters = new HashMap<>();
        for (int i = 0; i != input.length(); ++i) {
            final char nextChar = input.charAt(i);
            if (!characters.containsKey(nextChar)) {
                if (i - current > end - start) {
                    start = current;
                    end = i;
                }

                while (characters.size() == k && current != i) {
                    final char firstCharacter = input.charAt(current);
                    final int count = characters.get(firstCharacter);
                    if (1 == count) {
                        characters.remove(firstCharacter);
                    } else {
                        characters.put(firstCharacter, count - 1);
                    }
                    ++current;
                }

                assert (current < i && i - current >= characters.size()) || (current == i && 0 == characters.size());
            }

            if (characters.containsKey(nextChar)) {
                characters.put(nextChar, 1 + characters.get(nextChar));
            } else {
                characters.put(nextChar, 1);
            }
        }

        if (input.length() - current > end - start) {
            start = current;
            end = input.length();
        }

        assert start < end;
        return input.substring(start, end);
    }
}
