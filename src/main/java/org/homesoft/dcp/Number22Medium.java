package org.homesoft.dcp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * This problem was asked by Microsoft.
 * <p>
 * Given a dictionary of words and a string made up of those words (no spaces), return the original sentence in a list.
 * If there is more than one possible reconstruction, return any of them. If there is no possible reconstruction,
 * then return null.
 * <p>
 * For example, given the set of words 'quick', 'brown', 'the', 'fox', and the string "thequickbrownfox",
 * you should return ['the', 'quick', 'brown', 'fox'].
 * <p>
 * Given the set of words 'bed', 'bath', 'bedbath', 'and', 'beyond', and the string "bedbathandbeyond",
 * return either ['bed', 'bath', 'and', 'beyond] or ['bedbath', 'and', 'beyond'].
 */
public class Number22Medium {
    public static void main(String[] args) {
        final Number22Medium solution = new Number22Medium();

        solution.print(new String[]{"the", "quick", "brown", "fox"}, "thequickbrownfox");
        solution.print(new String[]{"bed", "bath", "bedbath", "and", "beyond"}, "bedbathandbeyond");
        solution.print(new String[]{"bed"}, "bedbedbed");
    }

    private void print(String[] dictionary, String input) {
        final List<String> result = get(dictionary, input);
        System.out.println(null == result ? "<null>" : String.join(", ", result));
    }

    private List<String> get(String[] dictionary, String input) {
        final HashTrie trie = new HashTrie();
        for (String word : dictionary) {
            trie.add(word);
        }

        final LinkedList<String> result = new LinkedList<>();
        return test(trie, trie, input, 0, 0, result) ? result : null;
    }

    private boolean test(HashTrie root, HashTrie trie, String input, int start, int end, LinkedList<String> result) {
        if (input.length() < end) {
            return false;
        } else if (input.length() == end) {
            result.addLast(input.substring(start, end));
            return trie.terminal;
        } else {
            if (trie.terminal) {
                result.addLast(input.substring(start, end));
                if (test(root, root, input, end, end, result)) {
                    return true;
                } else {
                    result.pollLast();
                }
            }

            final char character = input.charAt(end);
            return trie.characters.containsKey(character) && test(root,
                    trie.characters.get(character),
                    input,
                    start,
                    1 + end,
                    result);
        }
    }

    static class HashTrie {
        final HashMap<Character, HashTrie> characters = new HashMap<>();
        boolean terminal = false;

        void add(String word) {
            if (word.isEmpty()) {
                throw new RuntimeException("Empty word in trie is not allowed");
            }

            addCharacter(word, 0);
        }

        private void addCharacter(String word, int offset) {
            assert offset <= word.length();

            if (offset == word.length()) {
                terminal = true;
                return;
            }

            final char character = word.charAt(offset);
            final HashTrie next = characters.computeIfAbsent(character, (Character c) -> new HashTrie());
            next.addCharacter(word, 1 + offset);
        }
    }
}
