package org.homesoft.dcp;

/**
 * This problem was asked by Facebook.
 * <p>
 * You are given an array of non-negative integers that represents a two-dimensional elevation map where each element
 * is unit-width wall and the integer is the height. Suppose it will rain and all spots between two walls get filled up.
 * <p>
 * Compute how many units of water remain trapped on the map in O(N) time and O(1) space.
 * <p>
 * For example, given the input [2, 1, 2], we can hold 1 unit of water in the middle.
 * <p>
 * Given the input [3, 0, 1, 3, 0, 5], we can hold 3 units in the first index, 2 in the second, and 3 in the fourth
 * index (we cannot hold 5 since it would run off to the left), so we can trap 8 units of water.
 */
public class Number30Medium {
    public static void main(String[] args) {
        final Number30Medium solution = new Number30Medium();

        System.out.println(solution.get(new int[]{2, 1, 2}) == 1);
        System.out.println(solution.get(new int[]{3, 0, 1, 3, 0, 5}) == 8);
        System.out.println(solution.get(new int[]{1, 1, 1, 1, 1}) == 0);
        System.out.println(solution.get(new int[]{1, 0, 2, 0, 1}) == 2);
        System.out.println(solution.get(new int[]{2, 0, 2, 0, 2}) == 4);
        System.out.println(solution.get(new int[]{2, 0, 1, 0, 2}) == 5);
        System.out.println(solution.get(new int[]{1, 0, 2, 0, 2}) == 3);
        System.out.println(solution.get(new int[]{2, 0, 2, 0, 1}) == 3);
        System.out.println(solution.get(new int[]{2, 0, 1, 0, 1}) == 2);
        System.out.println(solution.get(new int[]{1, 0, 1, 0, 2}) == 2);
        System.out.println(solution.get(new int[]{1, 1}) == 0);
        System.out.println(solution.get(new int[]{1, 2}) == 0);
        System.out.println(solution.get(new int[]{1}) == 0);
        System.out.println(solution.get(new int[]{}) == 0);
    }

    private int get(int[] map) {
        if (3 > map.length) {
            return 0;
        }

        int max = map[0];
        for (int h : map) {
            if (max < h) {
                max = h;
            }
        }

        int sum = 0;
        if (map[0] == max && max == map[map.length - 1]) {
            for (int h : map) {
                sum += max - h;
            }
        } else {
            int left = 0;
            int bound = map[0];
            while (map[left] != max) {
                if (map[left] < bound) {
                    sum += bound - map[left];
                } else {
                    bound = map[left];
                }
                ++left;
            }

            int right = map.length - 1;
            bound = map[right];
            while (right != left) {
                if (map[right] < bound) {
                    sum += bound - map[right];
                } else {
                    bound = map[right];
                }
                --right;
            }
        }

        return sum;
    }
}
