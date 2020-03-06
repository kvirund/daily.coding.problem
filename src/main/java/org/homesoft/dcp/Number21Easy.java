package org.homesoft.dcp;

import java.util.ArrayList;

/**
 * This problem was asked by Snapchat.
 * <p>
 * Given an array of time intervals (start, end) for classroom lectures (possibly overlapping),
 * find the minimum number of rooms required.
 * <p>
 * For example, given [(30, 75), (0, 50), (60, 150)], you should return 2.
 */
public class Number21Easy {
    public static void main(String[] args) {
        Number21Easy solution = new Number21Easy();
        final Pair[] input = new Pair[]{new Pair(30, 75), new Pair(0, 50), new Pair(60, 150)};
        System.out.println(solution.get(input));
    }

    private int get(Pair[] input) {
        final ArrayList<Pair> events = new ArrayList<>();
        for (Pair lecture : input) {
            events.add(new Pair(lecture.start, 1));
            events.add(new Pair(lecture.end, -1));
        }
        events.sort((Pair a, Pair b) -> (a.start < b.start || (((a.start == b.start) && (a.end < b.end)))
                ? -1
                : (((a.start == b.start) && (a.end == b.end)) ? 0 : 1)));

        int max = 0;
        int counter = 0;
        for (Pair event : events) {
            counter += event.end;
            if (max < counter) {
                max = counter;
            }
        }

        return max;
    }

    static class Pair {
        final int start;
        final int end;

        Pair(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
