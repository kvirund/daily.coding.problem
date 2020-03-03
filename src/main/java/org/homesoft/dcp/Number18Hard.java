package org.homesoft.dcp;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;

/**
 * This problem was asked by Google.
 * <p>
 * Given an array of integers and a number k, where 1 <= k <= length of the array, compute the maximum values
 * of each subarray of length k.
 * <p>
 * For example, given array = [10, 5, 2, 7, 8, 7] and k = 3, we should get: [10, 7, 8, 8], since:
 *
 * <pre>
 * 10 = max(10, 5, 2)
 * 7 = max(5, 2, 7)
 * 8 = max(2, 7, 8)
 * 8 = max(7, 8, 7)
 * </pre>
 * <p>
 * Do this in O(n) time and O(k) space. You can modify the input array in-place and you do not need to store the
 * results. You can simply print them out as you compute them.
 */
public class Number18Hard {
    public static void main(String[] args) {
        Number18Hard solution = new Number18Hard();

        solution.bruteForce(new int[] { 10, 5, 2, 7, 8, 7 });
        solution.bruteForce(new int[] { 1, 2, 3, 4, 5 });
        solution.bruteForce(new int[] { 5, 4, 3, 2, 1 });
        solution.bruteForce(new int[] { 1, 2, 3, 0, -2, -1, 4, -3, -5, -4, 5 });
    }

    private void bruteForce(int[] array) {
        for (int k = 0; k != array.length; ++k) {
            System.out.printf("k = %d, maximums: %s\n",
                1 + k,
                StringUtils.join(ArrayUtils.toObject(get(array, 1 + k)), ", "));
        }
    }

    private int[] get(int[] array, int k) {
        if (0 == array.length || k <= 0) {
            return new int[0];
        }

        if (k >= array.length) {
            return getMax(array);
        }

        final LinkedList<Integer> maxs = new LinkedList<>();
        for (int i = 0; i != k; ++i) {
            while (!maxs.isEmpty() && array[maxs.peekLast()] < array[i]) {
                maxs.removeLast();
            }
            maxs.addLast(i);
        }

        int[] result = new int[array.length - k + 1];
        for (int i = k; i != array.length; ++i) {
            assert !maxs.isEmpty();
            result[i - k] = array[maxs.peekFirst()];

            while (!maxs.isEmpty() && maxs.peekFirst() <= i - k) {
                maxs.pollFirst();
            }

            while (!maxs.isEmpty() && array[maxs.peekLast()] < array[i]) {
                maxs.pollLast();
            }

            maxs.addLast(i);
        }

        assert !maxs.isEmpty();
        result[array.length - k] = array[maxs.poll()];

        return result;
    }

    private int[] getMax(int[] array) {
        int max = array[0];
        for (int i = 0; i != array.length; ++i) {
            if (max < array[i]) {
                max = array[i];
            }
        }
        return new int[] { max };
    }
}
