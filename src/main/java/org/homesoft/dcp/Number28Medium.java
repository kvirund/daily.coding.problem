package org.homesoft.dcp;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * This problem was asked by Palantir.
 * <p>
 * Write an algorithm to justify text. Given a sequence of words and an integer line length k, return a list of
 * strings which represents each line, fully justified.
 * <p>
 * More specifically, you should have as many words as possible in each line. There should be at least one space
 * each word. Pad extra spaces when necessary so that each line has exactly length k. Spaces should be distributed
 * as equally as possible, with the extra spaces, if any, distributed starting from the left.
 * <p>
 * If you can only fit one word on a line, then you should pad the right-hand side with spaces.
 * <p>
 * Each word is guaranteed not to be longer than k.
 * <p>
 * For example, given the list of words ["the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"]
 * and k = 16, you should return the following:
 *
 * <pre>
 *  ["the  quick brown", # 1 extra space on the left
 * "fox  jumps  over", # 2 extra spaces distributed evenly
 * "the   lazy   dog"] # 4 extra spaces distributed evenly
 * </pre>
 */
public class Number28Medium {
    public static void main(String[] args) {
        final Number28Medium solution = new Number28Medium();

        final String[] input = {"the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"};

        int sum = 0;
        int max = input[0].length();
        for (int i = 0; i != input.length; ++i) {
            if (max < input[i].length()) {
                max = input[i].length();
            }

            sum += input[i].length();
        }

        for (int k = max; k != 1 + sum + 2 * (input.length - 1); ++k) {
            if (max != k) {
                System.out.println();
            }

            solution.test(input, k);
        }
    }

    private void test(String[] input, int k) {
        for (int i = 0; i != k; ++i) {
            System.out.print((1 + i) % 10);
        }
        System.out.println();

        System.out.println(StringUtils.join(justify(input, k), "\n").replace(' ', '.'));
    }

    private List<String> justify(String[] input, int k) {
        final List<String> result = new LinkedList<>();

        int wordsInLine = 0;
        int currentLength = 0;
        int wordIndex = 0;
        int startingWord = 0;
        while (wordIndex != input.length) {
            if (currentLength + input[wordIndex].length() + (0 == wordsInLine ? 0 : 1) <= k) {
                currentLength += input[wordIndex].length() + (0 == wordsInLine ? 0 : 1);
                ++wordIndex;
                ++wordsInLine;
            } else {    // overflow
                final String toAdd = getNextLine(input, startingWord, wordsInLine, k - currentLength);
                result.add(toAdd);

                currentLength = 0;
                wordsInLine = 0;
                startingWord = wordIndex;
            }
        }

        if (0 != wordsInLine) {
            final String toAdd = getNextLine(input, startingWord, wordsInLine, k - currentLength);
            result.add(toAdd);
        }

        return result;
    }

    private String getNextLine(String[] input, int startingWord, int wordsInLine, int additionalSpaces) {
        final StringBuilder stringBuilder = new StringBuilder();
        final int endWord = startingWord + wordsInLine;
        for (int i = startingWord; i != endWord; ++i) {
            stringBuilder.append(input[i]);
            if (1 + i != endWord) {
                final int spacePlaces = wordsInLine - 1;
                final int wordIndex = i - startingWord;
                final int extraSpaces = additionalSpaces / spacePlaces + (wordIndex < (additionalSpaces % spacePlaces)
                        ? 1
                        : 0);
                final int spaces = 1 + extraSpaces;
                for (int j = 0; j != spaces; ++j) {
                    stringBuilder.append(' ');
                }
            } else if (1 == wordsInLine) {
                for (int j = 0; j != additionalSpaces; ++j) {
                    stringBuilder.append(' ');
                }
            }
        }
        return stringBuilder.toString();
    }
}
