package org.homesoft.dcp;

import java.util.ArrayList;

/**
 * This problem was asked by Google.
 * <p>
 * Implement locking in a binary tree. A binary tree node can be locked or unlocked only if all of its descendants
 * or ancestors are not locked.
 * <p>
 * Design a binary tree node class with the following methods:
 * <ul>
 * <li>is_locked, which returns whether the node is locked</li>
 * <li>lock, which attempts to lock the node. If it cannot be locked, then it should return false. Otherwise,
 * it should lock it and return true.</li>
 * <li>unlock, which unlocks the node. If it cannot be unlocked, then it should return false. Otherwise,
 * it should unlock it and return true.</li>
 * </ul>
 * <p>
 * You may augment the node to add parent pointers or any other property you would like. You may assume the class
 * is used in a single-threaded program, so there is no need for actual locks or mutexes. Each method should
 * run in O(h), where h is the height of the tree.
 */
public class Number24Medium {
    public static void main(String[] args) {
        final Number24Medium solution = new Number24Medium();

        solution.test();
    }

    @SuppressWarnings("unused")
    static void dumpTree(Node root) {
        dumpTreeHelper(root, 0, 0);
    }

    private static int dumpTreeHelper(Node tree, int indent, int counter) {
        if (null != tree) {
            System.err.println(String.format("%s%d, %s, %s (%d)",
                    indent("\t", indent),
                    tree.value,
                    tree.locked ? "locked" : "unlocked",
                    tree.lockTrace ? "part of lock trace" : "not a part of lock trace",
                    counter++));
            counter = dumpTreeHelper(tree.left, 1 + indent, counter);
            counter = dumpTreeHelper(tree.right, 1 + indent, counter);
        } else {
            System.err.println(indent("\t", indent) + "-");
        }

        return counter;
    }

    private static String indent(@SuppressWarnings("SameParameterValue") String c, int i) {
        StringBuilder stringBuilder = new StringBuilder();
        while (0 != i--) {
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private void test() {
        final Tree tree = buildBalancedTree(3);
        System.out.println(!tree.nodes.get(3).is_locked());
        System.out.println(!tree.nodes.get(2).is_locked());
        System.out.println(!tree.nodes.get(6).is_locked());

        System.out.println(tree.nodes.get(3).lock());

        treeLocked(tree);

        System.out.println(!tree.nodes.get(2).unlock());
        System.out.println(!tree.nodes.get(6).unlock());

        System.out.println(tree.nodes.get(3).unlock());

        System.out.println(tree.nodes.get(4).lock());

        treeLocked(tree);

        System.out.println(!tree.nodes.get(3).unlock());
        System.out.println(!tree.nodes.get(2).unlock());
        System.out.println(!tree.nodes.get(6).unlock());

        System.out.println(tree.nodes.get(4).unlock());
    }

    private void treeLocked(Tree tree) {
        System.out.println(tree.nodes.get(3).is_locked());
        System.out.println(tree.nodes.get(2).is_locked());
        System.out.println(tree.nodes.get(6).is_locked());

        System.out.println(!tree.nodes.get(3).lock());
        System.out.println(!tree.nodes.get(2).lock());
        System.out.println(!tree.nodes.get(6).lock());
    }

    private Tree buildBalancedTree(@SuppressWarnings("SameParameterValue") int height) {
        final ArrayList<Node> nodes = new ArrayList<>();
        final Node root = buildBalancedSubtree(height, nodes, 1 << (height - 1), null);

        return new Tree(root, nodes);
    }

    private Node buildBalancedSubtree(int height, ArrayList<Node> nodes, int rootValue, Node parent) {
        final Node root = new Node(rootValue, parent);
        nodes.add(root);

        if (1 < height) {
            root.left = buildBalancedSubtree(height - 1, nodes, rootValue - (1 << (height - 1) - 1), root);
            root.right = buildBalancedSubtree(height - 1, nodes, rootValue + (1 << (height - 1) - 1), root);
        }

        return root;
    }

    static class Tree {
        final Node root;
        final ArrayList<Node> nodes;

        Tree(Node root, ArrayList<Node> nodes) {
            this.root = root;
            this.nodes = nodes;
        }
    }

    static class Node {
        final int value;
        final Node parent;
        Node left;
        Node right;
        boolean locked = false;
        boolean lockTrace = false;

        Node(int value, Node parent) {
            this.value = value;
            this.parent = parent;
        }

        boolean is_locked() {
            Node pos = this;
            while (null != pos) {
                if (pos.lockTrace) {
                    return true;
                }

                pos = pos.parent;
            }

            return false;
        }

        boolean lock() {
            if (is_locked()) {
                return false;
            }

            locked = true;
            Node pos = this;
            while (null != pos) {
                pos.lockTrace = true;
                pos = pos.parent;
            }

            return true;
        }

        boolean unlock() {
            if (!locked) {
                return false;
            }

            locked = false;
            Node pos = this;
            while (null != pos) {
                pos.lockTrace = false;
                pos = pos.parent;
            }

            return true;
        }
    }
}