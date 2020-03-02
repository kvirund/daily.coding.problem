package org.homesoft.dcp;

import java.util.Stack;

/**
 * This problem was asked by Google.
 * <p>
 * Suppose we represent our file system by a string in the following manner:
 * <p>
 * The string "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext" represents:
 *
 * <pre>
 * dir
 *     subdir1
 *     subdir2
 *         file.ext
 * </pre>>
 * The directory dir contains an empty sub-directory subdir1 and a sub-directory subdir2 containing a file file.ext.
 * <p>
 * The string "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext" represents:
 *
 * <pre>
 * dir
 *     subdir1
 *         file1.ext
 *         subsubdir1
 *     subdir2
 *         subsubdir2
 *             file2.ext
 * </pre>
 * The directory dir contains two sub-directories subdir1 and subdir2. subdir1 contains a file file1.ext and an empty
 * second-level sub-directory subsubdir1. subdir2 contains a second-level sub-directory subsubdir2 containing
 * a file file2.ext.
 * <p>
 * We are interested in finding the longest (number of characters) absolute path to a file within our file system.
 * For example, in the second example above, the longest absolute path is "dir/subdir2/subsubdir2/file2.ext", and its
 * length is 32 (not including the double quotes).
 * <p>
 * Given a string representing the file system in the above format, return the length of the longest absolute path
 * to a file in the abstracted file system. If there is no file in the system, return 0.
 * <p>
 * Note:
 * <p>
 * The name of a file contains at least a period and an extension.
 * <p>
 * The name of a directory or sub-directory will not contain a period.
 */
public class Number17Hard {
    public static void main(String[] args) {
        Number17Hard solution = new Number17Hard();

        System.out.println(solution.get("dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext"));
        System.out.println(solution.get(
                "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext"));
        System.out.println(solution.get("command.com"));
        System.out.println(solution.get("root\n\tdir1\n\tdir2\n\t\tdir3\n\tdir4"));
        System.out.println(solution.get("root\n\tdir1\n\tdir2\n\t\tdir3\n\tdir4\nasdf.txt"));
    }

    private int get(String input) {
        return new DirectoriesParser().parse(input);
    }

    /**
     * @implNote Assumes that input always correct.
     */
    static class DirectoriesParser {
        private final Stack<Integer> currentPath = new Stack<>();
        private int max;
        private int pos;
        private boolean isFile;
        private int level;
        private int currentLevel;
        private int nameLength;
        private int stackLength;
        private ParserState parserState;

        DirectoriesParser() {
            initParser();
        }

        private void initParser() {
            max = 0;
            pos = 0;
            level = 0;
            currentLevel = 0;
            nameLength = 0;
            stackLength = 0;
            currentPath.clear();
            switchToParsingName();
        }

        public int parse(String input) {
            if (0 == input.length()) {
                return 0;
            }

            while (pos != input.length()) {
                final char currentChar = input.charAt(pos);
                processChar(currentChar);
            }

            processName();

            return max;
        }

        private void processChar(char currentChar) {
            switch (parserState) {
                case PARSE_NAME:
                    parseNameChar(currentChar);
                    break;

                case PARSE_LEVEL:
                    parseLevelChar(currentChar);
                    break;
            }
        }

        private void parseLevelChar(char currentChar) {
            if ('\t' == currentChar) {
                ++currentLevel;
                ++pos;
            } else {
                adjustLevel();

                switchToParsingName();
            }
        }

        private void adjustLevel() {
            while (currentLevel < level) {
                stackLength -= currentPath.peek();
                currentPath.pop();
                --level;
            }
        }

        private void parseNameChar(char currentChar) {
            if ('\n' == currentChar) {
                processName();

                switchToParsingLevel();
            } else {
                if ('.' == currentChar) {
                    isFile = true;
                }

                ++nameLength;
            }

            ++pos;
        }

        private void processName() {
            if (!isFile) {
                processDirectoryName();
            } else {
                processFileName();
            }
        }

        private void processFileName() {
            final int pathLength = stackLength + nameLength + currentPath.size();
            if (max < pathLength) {
                max = pathLength;
            }
        }

        private void processDirectoryName() {
            currentPath.push(nameLength);
            stackLength += nameLength;
            ++level;
        }

        private void switchToParsingName() {
            isFile = false;
            nameLength = 0;
            parserState = ParserState.PARSE_NAME;
        }

        private void switchToParsingLevel() {
            currentLevel = 0;
            parserState = ParserState.PARSE_LEVEL;
        }

        enum ParserState {
            PARSE_NAME, PARSE_LEVEL
        }
    }
}
