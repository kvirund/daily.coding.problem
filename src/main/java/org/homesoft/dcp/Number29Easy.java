package org.homesoft.dcp;

/**
 * This problem was asked by Amazon.
 * <p>
 * Run-length encoding is a fast and simple method of encoding strings. The basic idea is to represent repeated
 * successive characters as a single count and character. For example, the string "AAAABBBCCDAA" would be encoded
 * as "4A3B2C1D2A".
 * <p>
 * Implement run-length encoding and decoding. You can assume the string to be encoded have no digits and consists
 * solely of alphabetic characters. You can assume the string to be decoded is valid.
 */
public class Number29Easy {
    public static void main(String[] args) {
        final Number29Easy solution = new Number29Easy();

        final String encoded = solution.encode("AAAABBBCCDAA");
        System.out.println(encoded);
        System.out.println(solution.decode(encoded));
    }

    private String decode(String encoded) {
        final StringBuilder stringBuilder = new StringBuilder();

        int pos = 0;
        while (pos != encoded.length()) {
            final int start = pos;
            while ('0' <= encoded.charAt(pos) && '9' >= encoded.charAt(pos)) {
                ++pos;
            }

            final int count = Integer.parseInt(encoded.substring(start, pos));
            final char character = encoded.charAt(pos++);

            for (int i = 0; i != count; ++i) {
                stringBuilder.append(character);
            }
        }

        return stringBuilder.toString();
    }

    private String encode(String input) {
        if (input.isEmpty()) {
            return "";
        }

        final StringBuilder stringBuilder = new StringBuilder();
        char lastCharacter = input.charAt(0);
        int counter = 1;
        for (int i = 1; i != input.length(); ++i) {
            if (lastCharacter == input.charAt(i)) {
                ++counter;
            } else {
                stringBuilder.append(counter).append(lastCharacter);
                lastCharacter = input.charAt(i);
                counter = 1;
            }
        }
        stringBuilder.append(counter).append(lastCharacter);

        return stringBuilder.toString();
    }
}
