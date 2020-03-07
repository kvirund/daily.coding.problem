package org.homesoft.dcp;

import javafx.util.Pair;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This problem was asked by Google.
 * <p>
 * You are given an M by N matrix consisting of booleans that represents a board. Each True boolean represents a wall.
 * Each False boolean represents a tile you can walk on.
 * <p>
 * Given this matrix, a start coordinate, and an end coordinate, return the minimum number of steps required
 * to reach the end coordinate from the start. If there is no possible path, then return null. You can move up,
 * left, down, and right. You cannot move through walls. You cannot wrap around the edges of the board.
 * <p>
 * For example, given the following board:
 * <pre>
 * [[f, f, f, f],
 * [t, t, f, t],
 * [f, f, f, f],
 * [f, f, f, f]]
 * {/prev>}
 * and start = (3, 0) (bottom left) and end = (0, 0) (top left), the minimum number of steps required to reach the
 * end is 7, since we would need to go through (1, 2) because there is a wall everywhere else on the second row.
 */
public class Number23Easy {
    public static void main(String[] args) {
        final Number23Easy solution = new Number23Easy();

        boolean[][] field = {{false, false, false, false},
                {true, true, false, true},
                {false, false, false, false},
                {false, false, false, false}};
        final Pair<Integer, Integer> start = new Pair<>(3, 0);
        final Pair<Integer, Integer> end = new Pair<>(0, 0);
        System.out.println(solution.get(field, start, end));
        System.out.println(solution.get(field, start, start));
        boolean[][] noWayField = {{false, false, false, false},
                {true, true, true, true},
                {false, false, false, false},
                {false, false, false, false}};
        System.out.println(solution.get(noWayField, start, end));
    }

    private Integer get(boolean[][] field, Pair<Integer, Integer> start, Pair<Integer, Integer> end) {
        if (0 == field.length) {
            return null;
        }

        final Queue<Pair<Integer, Integer>> queue = new LinkedBlockingQueue<>();
        final int[][] routeLengths = new int[field.length][field[0].length];
        for (int row = 0; row != routeLengths.length; ++row) {
            Arrays.fill(routeLengths[row], -1);
        }

        queue.add(start);
        routeLengths[start.getKey()][start.getValue()] = 0;

        while (!queue.isEmpty()) {
            final Pair<Integer, Integer> from = queue.poll();
            final int currentLength = routeLengths[from.getKey()][from.getValue()];
            if (from.equals(end)) {
                return currentLength;
            }

            if (0 < from.getKey()) {
                final Pair<Integer, Integer> nextPoint = new Pair<>(from.getKey() - 1, from.getValue());
                if (canGo(field, routeLengths, nextPoint)) {
                    queue.add(nextPoint);
                    routeLengths[nextPoint.getKey()][nextPoint.getValue()] = 1 + currentLength;
                }
            }
            if (field.length > 1 + from.getKey()) {
                final Pair<Integer, Integer> nextPoint = new Pair<>(1 + from.getKey(), from.getValue());
                if (canGo(field, routeLengths, nextPoint)) {
                    queue.add(nextPoint);
                    routeLengths[nextPoint.getKey()][nextPoint.getValue()] = 1 + currentLength;
                }
            }
            if (0 < from.getValue()) {
                final Pair<Integer, Integer> nextPoint = new Pair<>(from.getKey(), from.getValue() - 1);
                if (canGo(field, routeLengths, nextPoint)) {
                    queue.add(nextPoint);
                    routeLengths[nextPoint.getKey()][nextPoint.getValue()] = 1 + currentLength;
                }
            }
            if (field[0].length > 1 + from.getValue()) {
                final Pair<Integer, Integer> nextPoint = new Pair<>(from.getKey(), 1 + from.getValue());
                if (canGo(field, routeLengths, nextPoint)) {
                    queue.add(nextPoint);
                    routeLengths[nextPoint.getKey()][nextPoint.getValue()] = 1 + currentLength;
                }
            }
        }

        return null;
    }

    private boolean canGo(boolean[][] field, int[][] routeLengths, Pair<Integer, Integer> nextPoint) {
        return !field[nextPoint.getKey()][nextPoint.getValue()]
                && -1 == routeLengths[nextPoint.getKey()][nextPoint.getValue()];
    }
}
