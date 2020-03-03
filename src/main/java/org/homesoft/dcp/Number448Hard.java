package org.homesoft.dcp;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

/**
 * This problem was asked by Google.
 * <p>
 * Given an array of strictly the characters 'R', 'G', and 'B', segregate the values of the array so that all
 * the Rs come first, the Gs come second, and the Bs come last. You can only swap elements of the array.
 * <p>
 * Do this in linear time and in-place.
 * <p>
 * For example, given the array ['G', 'B', 'R', 'R', 'B', 'R', 'G'], it should
 * become ['R', 'R', 'R', 'G', 'G', 'B', 'B'].
 */
public class Number448Hard {
    public static void main(String[] args) {
        Number448Hard solution = new Number448Hard();
        System.out.println(Arrays.toString(solution.get(new char[] { 'G', 'B', 'R', 'R', 'B', 'R', 'G' })));
        System.out.println(Arrays.toString(solution.get(new char[] { 'G', 'R', 'G', 'B', 'R', 'B' })));
    }

    private char[] get(char[] input) {
        int redPointer = 0;
        int currentPointer = 0;
        int bluePointer = input.length;

        /*
         * #1: rg'G', 'B', 'R', 'R', 'B', 'R', 'G', b -> r'G', g'B', 'R', 'R', 'B', 'R', 'G', b
         * #2: r'G', g'B', 'R', 'R', 'B', 'R', 'G', b -> r'G', 'G', g'R', 'R', 'B', 'R', b'B'
         * #3: r'G', 'G', g'R', 'R', 'B', 'R', b'B' -> r'R', 'G', 'G', g'R', 'B', 'R', b'B'
         * #4: r'R', 'G', 'G', g'R', 'B', 'R', b'B' -> r'R', 'R', 'G', 'G', g'B', 'R', b'B'
         * #5: r'R', 'R', 'G', 'G', g'B', 'R', b'B' -> r'R', 'R', 'G', 'G', g'R', b'B', 'B'
         * #6: r'R', 'R', 'G', 'G', g'R', b'B', 'B' -> r'R', 'R', 'R', 'G', 'G', gb'B', 'B'
         */
        while (currentPointer != bluePointer) {
            char currentCharacter = input[currentPointer];
            if ('R' == currentCharacter) {
                ArrayUtils.swap(input, currentPointer, redPointer);
                ++redPointer;
                ++currentPointer;
            } else if ('B' == currentCharacter) {
                ArrayUtils.swap(input, currentPointer, bluePointer - 1);
                --bluePointer;
            } else {
                ++currentPointer;
            }
        }

        return input;
    }
}
