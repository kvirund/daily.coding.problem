package org.homesoft.dcp;

/**
 * This problem was asked by Facebook.
 * <p>
 * Implement regular expression matching with the following special characters:
 *
 * <pre>
 * . (period) which matches any single character
 * * (asterisk) which matches zero or more of the preceding element
 * </pre>
 * <p>
 * That is, implement a function that takes in a string and a valid regular expression and returns whether or not
 * the string matches the regular expression.
 * <p>
 * For example, given the regular expression "ra." and the string "ray", your function should return true.
 * The same regular expression on the string "raymond" should return false.
 * <p>
 * Given the regular expression ".*at" and the string "chat", your function should return true. The same regular
 * expression on the string "chats" should return false.
 */
public class Number25Hard {
    public static void main(String[] args) {
        final Number25Hard solution = new Number25Hard();

        System.out.println(solution.test("ra.", "ray"));
        System.out.println(!solution.test("ra.", "raymond"));
        System.out.println(solution.test(".*at", "chat"));
        System.out.println(!solution.test(".*at", "chats"));
        System.out.println(!solution.test("....", "chats"));
        System.out.println(solution.test(".....", "chats"));
        System.out.println(solution.test(".*", "chats"));
        System.out.println(solution.test(".*.*.*.*.*.*.*.*", "chats"));
        System.out.println(solution.test(".*c.*h.*a.*t.*s.*", "chats"));
        System.out.println(!solution.test(".*c.*h.*a.*t.*z.*", "chats"));
        System.out.println(solution.test("......*", "chats"));
        System.out.println(solution.test("a*b*", "aaabbb"));
        System.out.println(solution.test("a*b", "b"));
        System.out.println(solution.test("a*b*", ""));
        System.out.println(solution.test(".*.*", ""));
        System.out.println(!solution.test(".", ""));
        System.out.println(!solution.test("..*", ""));
        System.out.println(!solution.test(".*.", ""));
        System.out.println(solution.test(".*.*.*.*.*c", "c"));
    }

    private boolean test(String expression, String string) {
        return new Expression(expression).test(string);
    }

    private static class Expression {
        final String expression;

        public Expression(String expression) {
            this.expression = expression;
        }

        public boolean test(String string) {
            return testHelper(string, 0, 0);
        }

        private boolean testHelper(String string, int stringOffset, int patternOffset) {
            if (string.length() == stringOffset) {  // end of string
                return processPatternTail(string, stringOffset, patternOffset);
            } else if (expression.length() == patternOffset) {  // have string but end of pattern
                return false;
            } else if (1 + patternOffset == expression.length()) {    // have string and have one pattern character
                return processNextCharacter(string, stringOffset, patternOffset);
            } else {    // have string and have pattern
                if ('*' != expression.charAt(1 + patternOffset)) {
                    return processNextCharacter(string, stringOffset, patternOffset);
                } else {
                    return processAsterisk(string, stringOffset, patternOffset);
                }
            }
        }

        private boolean processAsterisk(String string, int stringOffset, int patternOffset) {
            int so = stringOffset;
            final char patternChar = expression.charAt(patternOffset);
            boolean match = true;
            while (so != string.length() && match) {
                final boolean result = testHelper(string, so, 2 + patternOffset);
                if (result) {
                    return true;
                }

                match = '.' == patternChar || string.charAt(so) == patternChar;
                ++so;
            }

            return match && expression.length() == 2 + patternOffset;
        }

        private boolean processNextCharacter(String string, int stringOffset, int patternOffset) {
            if ('.' == expression.charAt(patternOffset) || string.charAt(stringOffset) == expression.charAt(
                    patternOffset)) {
                return testHelper(string, 1 + stringOffset, 1 + patternOffset);
            } else {
                return false;
            }
        }

        private boolean processPatternTail(String string, int stringOffset, int patternOffset) {
            if (expression.length() == patternOffset) {
                return true;
            } else if (1 + patternOffset == expression.length()) {
                return false;
            } else {
                if ('*' == expression.charAt(1 + patternOffset)) {
                    return testHelper(string, stringOffset, 2 + patternOffset);
                } else {
                    return false;
                }
            }
        }
    }
}
