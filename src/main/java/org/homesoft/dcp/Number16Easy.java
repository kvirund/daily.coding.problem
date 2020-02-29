package org.homesoft.dcp;

import java.util.ArrayList;

/**
 * This problem was asked by Twitter.
 * <p>
 * You run an e-commerce website and want to record the last N order ids in a log. Implement a data structure to accomplish this, with the following API:
 * <p>
 * record(order_id): adds the order_id to the log
 * get_last(i): gets the ith last element from the log. i is guaranteed to be smaller than or equal to N.
 * You should be as efficient with time and space as possible.
 */
public class Number16Easy {
    final int N;
    final ArrayList<Integer> orders;
    int offset = 0;

    Number16Easy(int N) {
        this.N = N;
        this.orders = new ArrayList<>(N);
    }

    public static void main(String[] args) {
        final int N = 11;
        Number16Easy solution = new Number16Easy(N);

        for (int i = 0; i != 1000; ++i) {
            solution.record(i);
        }

        for (int i = 0; i != N; ++i) {
            System.out.printf("%d. %d\n", i, solution.get_last(i));
        }
    }

    public void record(int order_id) {
        if (orders.size() < N) {
            orders.add(order_id);
        } else {
            orders.set(offset % N, order_id);
        }

        offset = 1 + offset;

        // not necessary but allows to avoid integer overflow if
        // N <= Integer.MAX_VALUE >> 1 and number of orders > Integer.MAX_VALUE
        offset = 2 * N == offset ? N : offset;
    }

    public int get_last(int index) {
        final int last_index = offset - 1;
        if (index > last_index && last_index < N) {
            return 0;   // order's index out of bounds
        }

        // we assume that index < N and we checked that if offset < N then index <= offset
        final int element_index = (last_index - index) % N;
        return orders.get(element_index);
    }
}
