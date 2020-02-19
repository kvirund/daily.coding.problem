package org.homesoft.dcp;

import org.apache.commons.lang3.ArrayUtils;

import java.util.HashMap;

/**
 * This problem was asked by Google.
 * <p>
 * An XOR linked list is a more memory efficient doubly linked list.
 * Instead of each node holding next and prev fields, it holds a field named both,
 * which is an XOR of the next node and the previous node.
 * Implement an XOR linked list; it has an add(element) which adds the element to the end,
 * and a get(index) which returns the node at index.
 * <p>
 * If using a language that has no pointers (such as Python), you can assume
 * you have access to get_pointer and dereference_pointer functions that
 * converts between nodes and memory addresses.
 */
public class Number6Hard {
    public static void main(String[] argv) {
        Number6Hard solution = new Number6Hard();

        solution.test(new int[] { 1, 2, 3, 4, 5, 6, 7, 8 },
            new int[] { 0, 4, 8, 1, 7 },
            new int[] { 1, 5, -1, 2, 8 });
    }

    private void test(int[] list_values, int[] indexes, int[] expected) {
        final List list = new List();
        for (int i : list_values) {
            list.add(i);
        }

        for (int i = 0; i != indexes.length; ++i) {
            final Node node = list.get(indexes[i]);
            indexes[i] = null != node ? node.value : -1;
        }

        System.out.print(ArrayUtils.toString(indexes));

        boolean result = true;
        for (int i = 0; i != indexes.length; ++i) {
            result = result && indexes[i] == expected[i];
        }

        if (result) {
            System.out.println(" - passed");
        } else {
            System.out.println(" - failed");
        }
    }

    static class List {
        private Node head = null;
        private Node tail = null;
        private HashMap<Integer, Node> memory = new HashMap<>();   // emulation of pointers

        Node get(int index) {
            if (null == head) {
                return null;
            }

            int prev = 0;
            Node node = head;
            int next = prev ^ node.both;
            while (0 != index && 0 != next) {
                prev = get_pointer(node);
                node = dereference_pointer(next);
                --index;
                next = prev ^ node.both;
            }

            return 0 != index ? null : node;
        }

        int get_pointer(Node node) {
            return node.hashCode();
        }

        Node dereference_pointer(int pointer) {
            return memory.get(pointer);
        }

        void add(int value) {
            final Node newNode = newNode(value);
            if (null == head) {
                head = newNode;
                tail = head;
                return;
            }

            newNode.both = tail.both;
            tail.both = tail.both ^ get_pointer(newNode);
            newNode.both = get_pointer(tail);
            tail = newNode;
        }

        Node newNode(int value) {
            final Node node = new Node(value, 0);
            memory.put(node.hashCode(), node);
            return node;
        }
    }

    static class Node {
        int value;
        int both;

        Node(int value, int both) {
            this.value = value;
            this.both = both;
        }
    }
}
