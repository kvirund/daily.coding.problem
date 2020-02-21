package org.homesoft.dcp;

import java.util.HashMap;
import java.util.HashSet;

/**
 * This problem was asked by Facebook.
 * <p>
 * Given the mapping a = 1, b = 2, ... z = 26, and an encoded message, count the number of ways it can be decoded.
 * <p>
 * For example, the message '111' would give 3, since it could be decoded as 'aaa', 'ka', and 'ak'.
 * <p>
 * You can assume that the messages are decodable. For example, '001' is not allowed.
 */
public class Number7Medium {
    private HashSet<String> codes = new HashSet<>();

    Number7Medium() {
        for (int i = 1; i != 27; ++i) {
            codes.add(String.valueOf(i));
        }
    }

    public static void main(String[] args) {
        Number7Medium solution = new Number7Medium();

        System.out.println(3 == solution.get("111"));
        System.out.println(4 == solution.get("1919"));
        System.out.println(1 == solution.get("999"));
        System.out.println(1 == solution.get("2"));
        System.out.println(4 == solution.get("101512"));
        System.out.println(2 == solution.get("26"));
    }

    private int get(String message) {
        final HashMap<Integer, Integer> cache = new HashMap<>();
        return getImpl(message, 0, cache);
    }

    private int getImpl(String message, int start, HashMap<Integer, Integer> cache) {
        if (cache.containsKey(start)) {
            return cache.get(start);
        }

        int result = 0;
        int length = 1;
        while (message.length() >= start + length) {
            final String prefix = message.substring(start, length + start);
            if (codes.contains(prefix)) {
                result += (message.length() == start + length) ? 1 : getImpl(message, start + length, cache);
            } else {
                break;
            }
            ++length;
        }

        cache.put(length, result);

        return result;
    }
}
