package org.homesoft.dcp;

import java.util.ArrayList;
import java.util.List;

/**
 * This problem was asked by Snapchat.
 * <p>
 * Given a string of digits, generate all possible valid IP address combinations.
 * <p>
 * IP addresses must follow the format A.B.C.D, where A, B, C, and D are numbers between 0 and 255. Zero-prefixed numbers, such as 01 and 065, are not allowed, except for 0 itself.
 * <p>
 * For example, given "2542540123", you should return ['254.25.40.123', '254.254.0.123'].
 */
public class Number1085Medium {
    public static void main(String[] args) {
        System.out.println(test("2542540123", List.of("254.25.40.123", "254.254.0.123")));
        System.out.println(test("254254123",
                List.of("2.54.254.123", "25.4.254.123", "25.42.54.123", "254.2.54.123", "254.25.4.123", "254.25.41.23", "254.254.1.23", "254.254.12.3")));
        System.out.println(test("1111", List.of("1.1.1.1")));
        System.out.println(test("", List.of()));
        System.out.println(test("1", List.of()));
        System.out.println(test("111", List.of()));
        System.out.println(test("0000", List.of("0.0.0.0")));
        System.out.println(test("100001", List.of("100.0.0.1")));
        System.out.println(test("111111111111", List.of("111.111.111.111")));
        System.out.println(test("1111111111111", List.of()));
        System.out.println(test("333333333333", List.of()));
        System.out.println(test("33333333", List.of("33.33.33.33")));
        System.out.println(test("3333.3333", List.of()));
        System.out.println(test("asdf", List.of()));
        System.out.println(test("255255255255", List.of("255.255.255.255")));
    }

    private static String test(String input, List<String> expected) {
        final var solution = new Number1085Medium();
        final var actual = solution.get(input);
        final var result = expected.equals(actual);
        return String.format("%sInput: %s; Result: %s; Expected: %s; Actual: %s", result ? "" : "!!! ", input, result, expected, actual);
    }

    private List<String> get(String input) {
        final var result = new ArrayList<String>();
        populateIpsCombinations(input, 0, 0, "", result);
        return result;
    }

    private void populateIpsCombinations(String input, int offset, int octetCounter, String prefix, List<String> combinations) {
        if (3 == octetCounter) {
            final var octetString = input.substring(offset);
            if (validOctet(octetString)) {
                combinations.add(prefix + octetString);
            }
            return;
        }

        for (int length = 1; length < Math.min(input.length() - offset, 4); ++length) {
            final var octetString = input.substring(offset, offset + length);
            if (!validOctet(octetString)) {
                break;
            }

            populateIpsCombinations(input, offset + length, 1 + octetCounter, prefix + octetString + ".", combinations);
        }
    }

    private boolean validOctet(String substring) {
        if (substring.startsWith("0") && 1 != substring.length()) {
            return false;
        }

        try {
            final var octet = Integer.parseInt(substring);
            return 0 <= octet && octet <= 255;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
