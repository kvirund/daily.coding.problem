package org.homesoft.dcp;

import java.util.HashMap;

/**
 * This problem was asked by Facebook.
 * <p>
 * A builder is looking to build a row of N houses that can be of K different colors. He has a goal of minimizing
 * cost while ensuring that no two neighboring houses are of the same color.
 * <p>
 * Given an N by K matrix where the nth row and kth column represents the cost to build the nth house with kth color,
 * return the minimum cost which achieves this goal.
 */
public class Number19Medium {
    public static void main(String[] args) {
        Number19Medium solution = new Number19Medium();

        final int[][] input = {{1, 2, 3, 4, 5}, {2, 1, 3, 4, 5}, {5, 4, 3, 2, 1}, {4, 3, 5, 1, 2}, {1, 5, 4, 2, 3}, {1, 5, 4, 2, 3}};
        System.out.println(solution.get(input));
    }

    private int get(int[][] input) {
        assert 0 < input.length;

        final int N = input.length;
        final int K = input[0].length;
        for (int i = 1; i != N; ++i) {
            assert K == input[i].length;
        }

        final HashMap<CachePoint, Integer> cache = new HashMap<>();

        return getMin(input, N, -1, cache);
    }

    private int getHelper(int[][] input, int rowLength, int blockedColor, HashMap<CachePoint, Integer> cache) {
        if (0 == rowLength) {
            return 0;
        }

        final CachePoint cachePoint = new CachePoint(rowLength, blockedColor);
        if (cache.containsKey(cachePoint)) {
            return cache.get(cachePoint);
        }

        final int min = getMin(input, rowLength, blockedColor, cache);
        cache.put(cachePoint, min);

        return min;
    }

    private int getMin(int[][] input, int rowLength, int blockedColor, HashMap<CachePoint, Integer> cache) {
        final int K = input[0].length;

        int min = Integer.MAX_VALUE;
        for (int k = 0; k != K; ++k) {
            if (k == blockedColor) {
                continue;
            }

            final int cost = input[rowLength - 1][k] + getHelper(input, rowLength - 1, k, cache);
            if (cost < min) {
                min = cost;
            }
        }

        return min;
    }

    static class CachePoint {
        final int rowLength;
        final int blockedColor;

        CachePoint(int rowLength, int blockedColor) {
            this.rowLength = rowLength;
            this.blockedColor = blockedColor;
        }
    }
}
