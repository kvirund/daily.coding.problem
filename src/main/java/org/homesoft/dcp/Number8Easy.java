package org.homesoft.dcp;

import javafx.util.Pair;

/**
 * This problem was asked by Google.
 * <p>
 * A unival tree (which stands for "universal value") is a tree where all nodes under it have the same value.
 * <p>
 * Given the root to a binary tree, count the number of unival subtrees.
 * <p>
 * For example, the following tree has 5 unival subtrees:
 *
 * <pre>
 *    0
 *   / \
 *  1   0
 *     / \
 *    1   0
 *   / \
 *  1   1
 * </pre>
 */
public class Number8Easy {
    public static void main(String[] args) {
        Number8Easy solution = new Number8Easy();
        Number3Medium treeDeserializer = new Number3Medium();

        System.out.println(solution.get(treeDeserializer.deserialize("0,1,null,null,2,null,null")));
        System.out.println(solution.get(treeDeserializer.deserialize(
            "0,1,null,null,0,1,1,null,null,1,null,null,0,null,null")));
    }

    private int get(Number3Medium.Node tree) {
        Number3Medium.dumpTree(tree);

        if (null == tree) {
            return 0;
        }

        final Pair<Boolean, Integer> intermediateResult = getUniVal(tree);
        return intermediateResult.getValue();
    }

    private Pair<Boolean, Integer> getUniVal(Number3Medium.Node tree) {
        final int value = tree.data;
        int count = 0;
        boolean unival = true;

        if (null != tree.left) {
            Pair<Boolean, Integer> leftValue = getUniVal(tree.left);
            count += leftValue.getValue();
            unival = leftValue.getKey() && value == leftValue.getValue();
        }

        if (null != tree.right) {
            Pair<Boolean, Integer> rightValue = getUniVal(tree.right);
            count += rightValue.getValue();
            unival = rightValue.getKey() && value == rightValue.getValue();
        }

        return new Pair<>(unival, count + (unival ? 1 : 0));
    }
}
