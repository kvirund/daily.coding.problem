package org.homesoft.dcp;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This problem was asked by Twitter.
 * <p>
 * Implement an autocomplete system. That is, given a query string s and a set of all possible query strings, return
 * all strings in the set that have s as a prefix.
 * <p>
 * For example, given the query string de and the set of strings [dog, deer, deal], return [deer, deal].
 * <p>
 * Hint: Try preprocessing the dictionary into a more efficient data structure to speed up queries.
 */
public class Number11Medium {
    public static void main(String[] args) {
        final Dictionary trieDictionary = new TrieDictionary();
        test(trieDictionary);

        final Dictionary naiveDictionary = new NaiveDictionary();
        test(naiveDictionary);
    }

    private static void test(Dictionary dictionary) {
        for (String word : new String[] { "dog", "deer", "deal" }) {
            dictionary.add(word);
        }

        System.out.println(StringUtils.join(dictionary.suggest("de")));
    }

    interface Dictionary {
        void add(String word);

        List<String> suggest(String prefix);
    }

    static class TrieDictionary implements Dictionary {
        HashMap<Character, Node> root = new HashMap<>();

        public void add(final String word) {
            Node currentNode = null;
            HashMap<Character, Node> currentTie = root;
            for (int i = 0; i != word.length(); ++i) {
                final char currentChar = word.charAt(i);
                if (!currentTie.containsKey(currentChar)) {
                    currentTie.put(currentChar, new Node());
                }
                currentNode = currentTie.get(currentChar);
                currentTie = currentNode.next;
            }

            if (null != currentNode) {
                currentNode.terminal = true;
            }
        }

        public List<String> suggest(final String prefix) {
            final List<String> result = new LinkedList<>();
            Node currentNode = null;
            HashMap<Character, Node> currentTie = root;
            for (int i = 0; i != prefix.length(); ++i) {
                final char currentChar = prefix.charAt(i);
                if (!currentTie.containsKey(currentChar)) {
                    return result;  // no words with such prefix
                }

                currentNode = currentTie.get(currentChar);
                currentTie = currentNode.next;
            }

            // Build suffixes
            final LinkedList<String> suffixes = new LinkedList<>();
            populateSuffixes(currentNode, new LinkedList<>(), suffixes);

            for (String suffix : suffixes) {
                result.add(prefix + suffix);
            }
            return result;
        }

        private void populateSuffixes(Node currentNode, LinkedList<Character> suffix, LinkedList<String> suffixes) {
            if (null == currentNode) {
                return;
            }

            if (currentNode.terminal) {
                suffixes.add(StringUtils.join(suffix, ""));
            }

            for (Map.Entry<Character, Node> nextEntry : currentNode.next.entrySet()) {
                suffix.add(nextEntry.getKey());
                populateSuffixes(nextEntry.getValue(), suffix, suffixes);
                suffix.pop();
            }
        }

        static class Node {
            final HashMap<Character, Node> next = new HashMap<>();
            boolean terminal = false;
        }
    }

    static class NaiveDictionary implements Dictionary {
        private List<String> dictionary = new LinkedList<>();

        @Override
        public void add(String word) {
            dictionary.add(word);
        }

        @Override
        public List<String> suggest(String prefix) {
            final List<String> result = new LinkedList<>();
            for (String word : dictionary) {
                if (word.startsWith(prefix)) {
                    result.add(word);
                }
            }

            return result;
        }
    }
}
