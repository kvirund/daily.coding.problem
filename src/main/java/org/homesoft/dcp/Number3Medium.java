package org.homesoft.dcp;

/**
 * This problem was asked by Google.
 * <p>
 * Given the root to a binary tree, implement serialize(root), which serializes the tree into a string, and deserialize(s), which deserializes the string back into the tree.
 *
 * For example, given the following Node class
 *<code language="python">
 * class Node:
 *     def __init__(self, val, left=None, right=None):
 *         self.val = val
 *         self.left = left
 *         self.right = right
 * </code>
 * The following test should pass:
 * <code language="python">
 * node = Node('root', Node('left', Node('left.left')), Node('right'))
 * assert deserialize(serialize(node)).left.left.val == 'left.left'
 * </code>
 */
public class Number3Medium {
    static class Node {
        int data;

        Node left;
        Node right;

        Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        Number3Medium solution = new Number3Medium();

        Node oneElementTree = new Node(1, null, null);
        solution.solve(oneElementTree);

        Node balancedTree = new Node(1, new Node(2, null, null), new Node(3, null, null));
        solution.solve(balancedTree);

        Node unbalancedTree = new Node(1, new Node(2, null, new Node(3, null, new Node(4, null, null))), null);
        solution.solve(unbalancedTree);
    }

    @SuppressWarnings("ConstantConditions")
    private void solve(Node tree) {
        boolean dump_trees = false;
        
        if (dump_trees) {
            dumpTree(tree);
        }

        final String result = serialize(tree);
        System.out.println(result);

        if (dump_trees) {
            dumpTree(deserialize(result));
        }
    }

    private Node deserialize(String result) {
        int pos = 0;

        class DeserializeHelper {
            private String result;
            private int pos;

            private DeserializeHelper(String result, int pos) {
                this.result = result;
                this.pos = pos;
            }

            private Node parse() {
                int separator = result.indexOf(",", pos);
                final String value = 0 > separator ? result.substring(pos) : result.substring(pos, separator);
                pos = 1 + separator;

                if ("null".equals(value)) {
                    return null;
                }

                final Node left = parse();
                final Node right = parse();

                return new Node(Integer.parseInt(value), left, right);
            }
        }

        return new DeserializeHelper(result, pos).parse();
    }

    private String serialize(Node root) {
        StringBuilder stringBuilder = new StringBuilder();
        serializeHelper(root, stringBuilder);
        return stringBuilder.toString();
    }

    private void serializeHelper(Node tree, StringBuilder stringBuilder) {
        if (null == tree) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(tree.data).append(",");
            serializeHelper(tree.left, stringBuilder);
            stringBuilder.append(",");
            serializeHelper(tree.right, stringBuilder);
        }
    }

    private String indent(@SuppressWarnings("SameParameterValue") String c, int i) {
        StringBuilder stringBuilder = new StringBuilder();
        while (0 != i--) {
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private void dumpTree(Node root) {
        dumpTreeHelper(root, 0);
    }

    private void dumpTreeHelper(Node tree, int indent) {
        if (null != tree) {
            System.out.println(this.indent("\t", indent) + tree.data);
            dumpTreeHelper(tree.left, 1 + indent);
            dumpTreeHelper(tree.right, 1 + indent);
        } else {
            System.out.println(this.indent("\t", indent) + "-");
        }
    }
}
