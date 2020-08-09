package org.homesoft.dcp;

import java.util.Arrays;

/**
 * This problem was asked by Jane Street.
 * <p>
 * Suppose you are given a table of currency exchange rates, represented as a 2D array.
 * Determine whether there is a possible arbitrage: that is, whether there is some sequence of trades you can make,
 * starting with some amount A of any currency, so that you can end up with some amount greater than A of that currency.
 * <p>
 * There are no transaction costs and you can trade fractional quantities.
 */
public class Number32Hard {
    public static void main(String[] args) {
        final Number32Hard solution = new Number32Hard();

        final double[][] impossibleEqual = {{1.0f, 1.0f, 1.0f, 1.0f, 1.0f},
                {1.0f, 1.0f, 1.0f, 1.0f, 1.0f},
                {1.0f, 1.0f, 1.0f, 1.0f, 1.0f},
                {1.0f, 1.0f, 1.0f, 1.0f, 1.0f},
                {1.0f, 1.0f, 1.0f, 1.0f, 1.0f}};
        System.out.println(!solution.solve(impossibleEqual));

        final double[][] possible = {{1.0f, 1.0f, 1.0f, 1.0f, 1.0f},
                {1.0f, 1.0f, 1.0f, 1.0f, 1.0f},
                {1.0f, 1.0f, 1.0f, 1.0f, 2.0f},
                {1.0f, 1.0f, 1.0f, 1.0f, 1.0f},
                {1.0f, 1.0f, 1.0f, 1.0f, 1.0f}};
        System.out.println(solution.solve(possible));

        final double[][] impossibleLoss = {{1.0f, 1.0f, 1.0f, 1.0f, 1.0f},
                {1.0f, 1.0f, 1.0f, 1.0f, 1.0f},
                {1.0f, 1.0f, 1.0f, 1.0f, 1.0f},
                {1.0f, 1.0f, 0.9f, 1.0f, 1.0f},
                {1.0f, 1.0f, 1.0f, 1.0f, 1.0f}};
        System.out.println(!solution.solve(impossibleLoss));
    }

    // Bellman-Ford algorithm
    boolean solve(double[][] rates) {
        final double[][] flatRates = getFlatRates(rates);

        double[] row = new double[flatRates.length];
        Arrays.fill(row, Double.POSITIVE_INFINITY);
        row[0] = 0;

        double[] nextRow = new double[flatRates.length];
        double[] temp;
        for (int pass = 1; pass != flatRates.length - 1; ++pass) {
            for (int j = 0; j != flatRates.length; ++j) {
                nextRow[j] = getNextCost(flatRates, row, j);
            }

            temp = nextRow;
            nextRow = row;
            row = temp;
        }

        for (int j = 0; j != flatRates.length; ++j) {
            final double cost = getNextCost(flatRates, row, j);

            if (cost < row[j]) {
                return true;
            }
        }

        return false;
    }

    private double getNextCost(double[][] flatRates, double[] prevCosts, int targetNode) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i != flatRates.length; ++i) {
            double cost = flatRates[i][targetNode] + (prevCosts[i] == Double.POSITIVE_INFINITY ? 0 : prevCosts[i]);
            min = Math.min(min, cost);
        }

        return min;
    }

    private double[][] getFlatRates(double[][] rates) {
        final double[][] flatRates = new double[rates.length][rates.length];
        for (int i = 0; i != rates.length; ++i) {
            for (int j = 0; j != rates.length; ++j) {
                flatRates[i][j] = -Math.log(rates[i][j]);
            }
        }
        return flatRates;
    }
}
