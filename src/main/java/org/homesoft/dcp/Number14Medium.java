package org.homesoft.dcp;

import java.util.Random;

/**
 * This problem was asked by Google.
 * <p>
 * The area of a circle is defined as πr^2. Estimate π to 3 decimal places using a Monte Carlo method.
 * <p>
 * Hint: The basic equation of a circle is x^2 + y^2 = r^2.
 */
public class Number14Medium {
    public static void main(String[] args) {
        Number14Medium solution = new Number14Medium();

        System.out.println(solution.get(1));
        System.out.println(solution.get(2));
        System.out.println(solution.get(3));
        System.out.println(solution.get(4));
    }

    private double get(int precision) {
        final Random random = new Random();
        long inside = 0;
        long outside = 0;
        double square;

        final long iterations = getIterations(precision);

        for (long i = 0; i != iterations; ++i) {
            final double x = random.nextDouble();
            final double y = random.nextDouble();
            if (x * x + y * y > 1) {
                ++outside;
            } else {
                ++inside;
            }
        }

        square = getSquare(inside, outside);
        return square;
    }

    private long getIterations(int precision) {
        long result = 49;
        for (int i = 0; i != precision; ++i) {
            result *= 100;
        }
        return result;
    }

    private double getSquare(double inside, double outside) {
        return 4 * inside / (inside + outside);
    }
}
