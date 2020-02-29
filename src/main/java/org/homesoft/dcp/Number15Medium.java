package org.homesoft.dcp;

import java.util.HashMap;
import java.util.Random;

/**
 * This problem was asked by Facebook.
 * <p>
 * Given a stream of elements too large to store in memory, pick a random element from the stream with uniform probability.
 */
public class Number15Medium {
    public static void main(String[] args) {
        Number15Medium solution = new Number15Medium();

        solution.test(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 10000);
    }

    private void test(int[] input, @SuppressWarnings("SameParameterValue") int iterations) {
        final HashMap<Integer, Integer> counters = new HashMap<>();
        for (int i = 0; i != input.length; ++i) {
            counters.put(i, 0);
        }

        for (int i = 0; i != iterations; ++i) {
            final Element number = get(input);
            assert number != null;
            counters.put(number.index, 1 + counters.get(number.index));
        }

        final int mean = iterations / input.length;
        for (int i = 0; i != input.length; ++i) {
            final double error = Math.abs((double) (mean - counters.get(i)) / mean);
            System.out.printf("%d: %d; %f\n", input[i], counters.get(i), error);
        }
    }

    private Element get(int[] input) {
        if (0 == input.length) {
            return null;
        }

        final Random random = new Random();
        int index = 0;
        int result = input[0];
        int result_index = 0;
        while (++index < input.length) {
            int dice = random.nextInt(1 + index);
            result = dice == index - 1 ? input[index] : result;
            result_index = dice == index - 1 ? index : result_index;
        }

        return new Element(result_index, result);
    }

    static class Element {
        final int index;
        final int value;

        Element(int index, int value) {
            this.index = index;
            this.value = value;
        }
    }
}
