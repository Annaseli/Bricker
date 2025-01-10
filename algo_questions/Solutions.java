package algo_questions;

import java.util.Arrays;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Solutions {
    /**
     * Method computing the maximal amount of tasks out of n tasks that can be completed with m time slots.
     * A task can only be completed in a time slot if the length of the time slot is grater than the no.
     * of hours needed to complete the task.
     * @param tasks array of integers of length n. tasks[i] is the time in hours required to complete task i.
     * @param timeSlots array of integersof length m. timeSlots[i] is the length in hours of the slot i.
     * @return maximal amount of tasks that can be completed
     */
    public static int alotStudyTime(int[] tasks, int[] timeSlots) {
        int result = 0;
        int tasksRemain = tasks.length;
        int timeSlotsRemain = timeSlots.length;
        Arrays.sort(tasks);
        Arrays.sort(timeSlots);
        while (tasksRemain > 0 && timeSlotsRemain > 0){
            if (tasks[tasks.length - tasksRemain] <= timeSlots[timeSlots.length - timeSlotsRemain]) {
                result++;
                tasksRemain--;
                timeSlotsRemain--;
            } else {
                timeSlotsRemain--;
            }
        }
        return result;
    }

    /**
     * Method computing the nim amount of leaps a frog needs to jumb across n waterlily leaves,
     * from leaf 1 to leaf n. The leaves vary in size and how stable they are, so some leaves allow larger
     * leaps than others. leapNum[i] is an integer telling you how many leaves ahead you can jump from leaf i.
     * If leapNum[3]=4, the frog can jump from leaf 3, and land on any of the leaves 4, 5, 6 or 7.
     * @param leapNum array of ints. leapNum[i] is how many leaves ahead you can jump from leaf i.
     * @return minimal no. of leaps to last leaf.
     */
    public static int minLeap(int[] leapNum) {
        int result = 0;
        int maxLeaf = 0;
        int lastLeaf = 0;
        for (int i = 0; i < leapNum.length; i++) {
            if (lastLeaf < i) {
                result++;
                lastLeaf = maxLeaf;
            }
            maxLeaf = max(maxLeaf, i + leapNum[i]);
        }
        return result;
    }

    /**
     * Method computing the solution to the following problem: A boy is filling the water trough for his
     * father's cows in their village. The trough holds n liters of water. With every trip to the village
     * well, he can return using either the 2 bucket yoke, or simply with a single bucket. A bucket holds 1
     * liter. In how many different ways can he fill the water trough? n can be assumed to be greater or
     * equal to 0, less than or equal to 48.
     * @param n amount of litres.
     * @return number of ways to fill the trough.
     */
    public static int bucketWalk(int n) {
        if (n == 0) return 1;
        int[] differentWaysToFill = new int[n + 1];
        differentWaysToFill[0] = 1;
        differentWaysToFill[1] = 1;
        for (int i = 2; i <= n; i++) {
            differentWaysToFill[i] = differentWaysToFill[i - 2] + differentWaysToFill[i - 1];
        }
        return differentWaysToFill[n];
    }

    /**
     * Method computing the solution to the following problem: Given an integer n, return the number of
     * structurally unique BSTs (binary search trees) which has exactly n nodes of unique values from 1 to n.
     * You can assume n is at least 1 and at most 19. (Definition: two trees S and T are structurally
     * distinct if one can not be obtained from the other by renaming of the nodes.)
     * @param n number of nodes in the tree from 1 to n.
     * @return amount of different BSTs.
     */
    public static int numTrees(int n) {
        int[] differentWaysOfTrees = new int[n + 1];
        differentWaysOfTrees[0] = 1;
        differentWaysOfTrees[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                differentWaysOfTrees[i] += (differentWaysOfTrees[j] * differentWaysOfTrees[i - 1 - j]);
            }
        }
        return differentWaysOfTrees[n];

    }
}

