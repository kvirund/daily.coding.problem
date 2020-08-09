package org.homesoft.dcp;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * This problem was asked by Microsoft.
 * <p>
 * Compute the running median of a sequence of numbers. That is, given a stream of numbers,
 * print out the median of the list so far on each new element.
 * <p>
 * Recall that the median of an even-numbered list is the average of the two middle numbers.
 * <p>
 * For example, given the sequence [2, 1, 5, 7, 2, 0, 5], your algorithm should print out:
 * <p>
 * <code>
 * 2
 * 1.5
 * 2
 * 3.5
 * 2
 * 2
 * 2
 * </code>
 */
public class Number33Easy {
    public static void main(String[] args) {
        final Number33Easy solution = new Number33Easy();

        solution.solve(new int[]{2, 1, 5, 7, 2, 0, 5});
        System.out.println("-----");
        solution.solve(new int[]{2, 3});
        System.out.println("-----");
        solution.solve(new int[]{2, 2, 2, 2});
    }

    void solve(int[] numbers) {
        PriorityQueue<Integer> right = new PriorityQueue<>();
        PriorityQueue<Integer> left = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 0; i != numbers.length; ++i) {
            right.add(numbers[i]);
            if (right.size() > left.size() + 1) {
                final Integer toMove = right.poll();
                left.add(toMove);
            }

            if (right.size() == left.size()) {
                //noinspection ConstantConditions
                System.out.println((right.peek() + left.peek()) / 2.0);
            } else {
                System.out.println((double) right.peek());
            }
        }
    }
}
