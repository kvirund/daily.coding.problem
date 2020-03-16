package org.homesoft.dcp;

/**
 * This problem was asked by Google.
 * <p>
 * Given a singly linked list and an integer k, remove the kth last element from the list.
 * k is guaranteed to be smaller than the length of the list.
 * <p>
 * The list is very long, so making more than one pass is prohibitively expensive.
 * <p>
 * Do this in constant space and in one pass.
 */
public class Number26Medium {
    public static void main(String[] args) {
        final Number26Medium solution = new Number26Medium();

        solution.test(new int[]{1}, 0);
        solution.test(new int[]{1, 2}, 0);
        solution.test(new int[]{1, 2}, 1);
        solution.test(new int[]{1, 2, 3}, 0);
        solution.test(new int[]{1, 2, 3}, 1);
        solution.test(new int[]{1, 2, 3}, 2);
        solution.test(new int[]{1, 2, 3, 4}, 0);
        solution.test(new int[]{1, 2, 3, 4}, 1);
        solution.test(new int[]{1, 2, 3, 4}, 2);
        solution.test(new int[]{1, 2, 3, 4}, 3);
    }

    private void test(int[] input, int index) {
        final SinglyLinkedList result = get(SinglyLinkedList.build(input), index);
        if (null != result) {
            result.dump();
        } else {
            System.err.println("null");
        }
    }

    private SinglyLinkedList get(SinglyLinkedList head, int index) {
        if (null == head || null == head.next) {
            return null;
        }

        SinglyLinkedList pos = head.next;
        do {
            pos = pos.next;
        } while (0 != index-- && null != pos);

        if (null == pos && 0 == index) {
            return head.next;
        }

        SinglyLinkedList preKth = head;
        while (pos != null) {
            pos = pos.next;
            preKth = preKth.next;
        }

        preKth.next = preKth.next.next;

        return head;
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
