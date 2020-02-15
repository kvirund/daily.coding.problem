package org.homesoft.dcp;

import java.util.HashSet;
import java.util.Set;

/**
 * Given a list of numbers and a number k, return whether any two numbers from the list add up to k.
 * <p>
 * For example, given [10, 15, 3, 7] and k of 17, return true since 10 + 7 is 17.
 * <p>
 * Bonus: Can you do this in one pass?
 */
public class Number1Easy {
    public static void main(String[] args) {
        final Number1Easy solution = new Number1Easy();

        System.out.println(solution.answer(17, new int[]{10, 20, 3, -3}));
    }

    private boolean answer(int k, int[] input) {
        final Set<Integer> addUps = new HashSet<>();
        for (int value : input) {
            if (addUps.contains(value)) {
                return true;
            } else {
                addUps.add(k - value);
            }
        }

        return false;
    }
}
