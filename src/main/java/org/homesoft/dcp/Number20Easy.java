package org.homesoft.dcp;

/**
 * This problem was asked by Google.
 * <p>
 * Given two singly linked lists that intersect at some point, find the intersecting node. The lists are non-cyclical.
 * <p>
 * For example, given A = 3 -> 7 -> 8 -> 10 and B = 99 -> 1 -> 8 -> 10, return the node with value 8.
 * <p>
 * In this example, assume nodes with the same value are the exact same node objects.
 * <p>
 * Do this in O(M + N) time (where M and N are the lengths of the lists) and constant space.
 */
public class Number20Easy {
    public static void main(String[] args) {
        final Number20Easy solution = new Number20Easy();

        final SinglyLinkedList tail = SinglyLinkedList.build(new int[] { 8, 10 });
        final SinglyLinkedList A = SinglyLinkedList.build(new int[] { 3, 7 }, tail);
        final SinglyLinkedList B = SinglyLinkedList.build(new int[] { 99, 1 }, tail);
        final SinglyLinkedList C = SinglyLinkedList.build(new int[] { 100500, 100501, 100502 }, tail);

        A.dump();
        B.dump();
        C.dump();

        System.out.println(solution.get(A, B));
        System.out.println(solution.get(A, C));
    }

    private int get(SinglyLinkedList a, SinglyLinkedList b) {
        SinglyLinkedList l1 = a;
        SinglyLinkedList l2 = b;
        while (l1 != l2) {
            l1 = l1.next;
            l2 = l2.next;

            if (null == l1) {
                l1 = a;
            }

            if (null == l2) {
                l2 = b;
            }
        }

        return l1.value;
    }

    private static class SinglyLinkedList {
        final int value;
        SinglyLinkedList next;

        public SinglyLinkedList(int value, SinglyLinkedList next) {
            this.value = value;
            this.next = next;
        }

        public static SinglyLinkedList build(int[] values) {
            return SinglyLinkedList.build(values, null);
        }

        public static SinglyLinkedList build(int[] values, SinglyLinkedList tail) {
            SinglyLinkedList head = tail;
            for (int i = 0; i != values.length; ++i) {
                head = new SinglyLinkedList(values[values.length - i - 1], head);
            }

            return head;
        }

        public void dump() {
            SinglyLinkedList head = this;
            boolean first = true;
            final StringBuilder stringBuilder = new StringBuilder();
            while (null != head) {
                if (!first) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(head.value);
                head = head.next;
                first = false;
            }
            System.err.println(stringBuilder.toString());
        }
    }
}
