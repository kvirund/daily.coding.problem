package org.homesoft.dcp;

import java.util.Arrays;

/**
 * This problem was asked by Stripe.
 * <p>
 * Given an array of integers, find the first missing positive integer in linear time and constant space.
 * In other words, find the lowest positive integer that does not exist in the array. The array can contain
 * duplicates and negative numbers as well.
 * <p>
 * For example, the input [3, 4, -1, 1] should give 2. The input [1, 2, 0] should give 3.
 * <p>
 * You can modify the input array in-place.
 */
public class Number4Hard {
    public static void main(String[] args) {
        final Number4Hard solution = new Number4Hard();

        System.out.println(solution.get(new int[]{3, 4, -1, 1}));
        System.out.println(solution.get(new int[]{1, 2, 0}));
        System.out.println(solution.get(new int[]{1, 2, 3}));
        System.out.println(solution.get(new int[]{-1}));
        System.out.println(solution.get(new int[]{0}));
        System.out.println(solution.get(new int[]{1}));
        System.out.println(solution.get(new int[]{10}));
    }

    private int get(int[] input) {
        for (int i = 0; i != input.length; ++i) {
            input[i] = 0 < input[i] ? input[i] : -1;
        }

        for (int i = 0; i != input.length; ++i) {
            int value = input[i];
            while (0 < value && input.length >= value) {
                final int referencedValue = input[value - 1];
                input[value - 1] = 0;
                value = referencedValue;
            }
        }

        for (int i = 0; i != input.length; ++i) {
            if (input[i] != 0) {
                return 1 + i;
            }
        }

        return 1 + input.length;
    }
}
