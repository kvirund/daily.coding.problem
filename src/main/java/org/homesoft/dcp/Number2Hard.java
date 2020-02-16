package org.homesoft.dcp;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * This problem was asked by Uber.
 * <p>
 * Given an array of integers, return a new array such that each element at index i of the new array is the product of all the numbers in the original array except the one at i.
 * <p>
 * For example, if our input was [1, 2, 3, 4, 5], the expected output would be [120, 60, 40, 30, 24]. If our input was [3, 2, 1], the expected output would be [2, 3, 6].
 * <p>
 * Follow-up: what if you can't use division?
 */
public class Number2Hard {
    public static void main(String[] args) {
        Number2Hard solution = new Number2Hard();

        final boolean divisionAllowed = false;
        //noinspection ConstantConditions
        if (divisionAllowed) {
            solution.print(new int[]{1, 2, 3, 4, 5});
            solution.print(new int[]{3, 2, 1});
            solution.print(new int[]{1, 2, 3, 0, 5});
            solution.print(new int[]{1, 2, 3, 0, 5, 0});
        } else {
            solution.printNoDivision(new int[]{1, 2, 3, 4, 5});
            solution.printNoDivision(new int[]{3, 2, 1});
            solution.printNoDivision(new int[]{1, 2, 3, 0, 5});
            solution.printNoDivision(new int[]{1, 2, 3, 0, 5, 0});
        }
    }

    /*
     * - existence of 0 is special case. In this case only element where 0 is might be non-zero.
     * - if input array has more than one 0, then all elements of the results will be zeroes.
     */
    private void print(int[] input) {
        final int[] result = get(input);

        System.out.println(StringUtils.join(ArrayUtils.toObject(result), ", "));
    }

    // Solution 1, time: O(N); space: O(1) (not counting result)
    private int[] get(int[] input) {
        int product = 1;
        int zero = -1;  // -1 - no zeroes, -2 = more than one zero, index of the zero otherwise
        for (int i = 0; i != input.length; ++i) {
            if (0 == input[i]) {
                zero = -1 == zero ? i : -2;
            } else {
                product = product * input[i];
            }
        }

        final int[] result = new int[input.length];
        if (0 <= zero) {
            result[zero] = product;
        } else if (-1 == zero) {
            // no zeroes
            for (int i = 0; i != input.length; ++i) {
                result[i] = product / input[i];
            }
        }

        return result;
    }

    private void printNoDivision(int[] input) {
        final int[] result = getNoDivisionOn(input);

        System.out.println(StringUtils.join(ArrayUtils.toObject(result), ", "));
    }

    // Solution 2, time: O(N^2); space: O(1) (not counting result)
    private int[] getNoDivisionOn2(int[] input) {
        final int[] result = new int[input.length];
        Arrays.fill(result, 1);

        for (int i = 0; i != input.length; ++i) {
            for (int j = 0; j != result.length; ++j) {
                if (i != j) {
                    result[j] = result[j] * input[i];
                }
            }
        }

        return result;
    }

    // Solution 3, time: O(N); space: O(N)
    private int[] getNoDivisionOn(int[] input) {
        int productLeft = 1;
        final int[] partsLeft = new int[input.length];
        int productRight = 1;
        final int[] partsRight = new int[input.length];
        for (int i = 0; i != input.length; ++i) {
            productLeft = productLeft * input[i];
            partsLeft[i] = productLeft;

            productRight = productRight * input[input.length - i - 1];
            partsRight[input.length - i - 1] = productRight;
        }

        final int[] result = new int[input.length];
        for (int i = 0; i != input.length; ++i) {
            result[i] = (0 == i ? 1 : partsLeft[i - 1]) * (input.length == 1 + i ? 1 : partsRight[1 + i]);
        }

        return result;
    }
}
