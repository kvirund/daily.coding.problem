package org.homesoft.dcp;

/**
 * This problem was asked by Airbnb.
 * <p>
 * Given a list of integers, write a function that returns the largest sum of non-adjacent numbers. Numbers can be 0 or negative.
 * <p>
 * For example, [2, 4, 6, 2, 5] should return 13, since we pick 2, 6, and 5. [5, 1, 1, 5] should return 10, since we pick 5 and 5.
 * <p>
 * Follow-up: Can you do this in O(N) time and constant space?
 */
public class Number9Hard {
    public static void main(String[] args) {
        Number9Hard solution = new Number9Hard();

        System.out.println(solution.get(new int[]{2, 4, 6, 2, 5}));
        System.out.println(solution.get(new int[]{5, 1, 1, 5}));
        System.out.println(solution.get(new int[]{0, 0, 0, 1, 1, 0}));
        System.out.println(solution.get(new int[]{-1, -1, -1, 1, -1, -1}));
        System.out.println(solution.get(new int[]{-1}));
    }

    private int get(int[] input) {
        int excluding = 0;
        int including = input[0];

        for (int i = 0; i != input.length; ++i) {
            final int new_excluding = Math.max(excluding, including);
            including = excluding + input[i];
            excluding = new_excluding;
        }

        return Math.max(excluding, including);
    }
}
