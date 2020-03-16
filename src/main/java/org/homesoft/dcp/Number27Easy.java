package org.homesoft.dcp;

import java.util.Stack;

/**
 * This problem was asked by Facebook.
 * <p>
 * Given a string of round, curly, and square open and closing brackets, return whether the brackets
 * are balanced (well-formed).
 * <p>
 * For example, given the string "([])[]({})", you should return true.
 * <p>
 * Given the string "([)]" or "((()", you should return false.
 */
public class Number27Easy {
    public static void main(String[] args) {
        final Number27Easy solution = new Number27Easy();

        System.out.println(solution.check("([])[]({})"));
        System.out.println(!solution.check("([)]"));
        System.out.println(!solution.check("((()"));
        System.out.println(!solution.check(")"));
        System.out.println(!solution.check("["));
        System.out.println(solution.check("[]"));
    }

    private boolean check(String input) {
        final Stack<Counter> brackets = new Stack<>();

        for (int i = 0; i != input.length(); ++i) {
            final char bracket = input.charAt(i);
            if ('(' == bracket || '{' == bracket || '[' == bracket) {
                if (brackets.empty() || brackets.peek().character != bracket) {
                    brackets.push(new Counter(bracket));
                } else {
                    ++brackets.peek().counter;
                }
            } else if (!brackets.isEmpty()) {
                char counterpart;
                switch (bracket) {
                    case ')':
                        counterpart = '(';
                        break;

                    case '}':
                        counterpart = '{';
                        break;

                    case ']':
                        counterpart = '[';
                        break;

                    default:
                        throw new RuntimeException("Unexpected character");
                }

                if (brackets.peek().character != counterpart) {
                    return false;
                } else {
                    --brackets.peek().counter;
                }

                if (0 == brackets.peek().counter) {
                    brackets.pop();
                }
            } else {
                return false;
            }
        }

        return brackets.isEmpty();
    }

    static class Counter {
        final char character;
        int counter = 1;

        Counter(char character) {
            this.character = character;
        }
    }
}
