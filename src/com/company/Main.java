package com.company;

import java.util.*;

public class Main {
    //                       2
    //           1
    // {1, 3, 2, 1, 0, 3, 2}

    public static int[] rearrange(int[] input) {
        int cur1 = 0, cur2 = 0;
        while (cur2 < input.length) {
            if (input[cur2] != 0) {
                input[cur1] = input[cur2];
                cur1++;
                cur2++;
            } else {
                cur2++;
            }
        }
        while (cur1 < input.length) {
            input[cur1++] = 0;
        }
        return input;
    }



    public static void main(String[] args) {
        int[] test = new int[]{0, 1, 2, 0, 0, 3};
       System.out.println(Arrays.toString(rearrange(test)));
    }

    static class Task {
        int index;
        int priority;

        Task(int index, int priority) {
            this.index = index;
            this.priority = priority;
        }
    }

    /**
     * x = 5
     * 3 2 1 0 0 3 2
     *
     * x = 2
     * 3 2 3 2 3 2 3
     * @param n num of tasks
     * @param priority priority array
     * @param x window size that a task cannot appear twice
     * @param y Length of time
     * @return
     */

    public static int maxPrioritySum(int n, int[] priority, int x, int y) {
        List<Task> tasks = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            tasks.add(new Task(i, priority[i]));
        }

        tasks.sort((a, b) -> Integer.compare(b.priority, a.priority));
        int sumPriority = 0;
        int sum = 0;
        if (x > n) {
            for (int i = 0; i < n; i++) {
                sum += tasks.get(i).priority;
            }
            sumPriority += (y / x) * sum;
            System.out.println("sumPriority: " + sumPriority);
            for (int i = 0 ; i < y % x; i ++) {
                sumPriority += tasks.get(i).priority;
            }
        } else {
            for (int i = 0; i < x; i ++) {
                sum += tasks.get(i).priority;
            }
            sumPriority += (y / x) * sum;
            System.out.println("sumPriority: " + sumPriority);
            for (int i = 0; i < y % x; i ++) {
                sumPriority += tasks.get(i).priority;
            }
        }

        return sumPriority;
    }

    public class GPUSelection {
        public static int minimumCost(int n, int[] cost, int[] compatible1, int[] compatible2, int min_compatible) {
            PriorityQueue<Integer> comp1 = new PriorityQueue<>();
            PriorityQueue<Integer> both = new PriorityQueue<>();
            PriorityQueue<Integer> comp2 = new PriorityQueue<>();
            for (int i = 0; i < n; i ++) {
                if (compatible1[i] == 1 && compatible2[i] == 1) {
                    both.add(cost[i]);
                } else if (compatible1[i] == 0 && compatible2[i] == 1) {
                    comp1.add(cost[i]);
                } else if (compatible2[i] == 0 && compatible1[i] == 1) {
                    comp2.add(cost[i]);
                }
            }
            int cur1 = 0, cur2 = 0, curCost = 0;
            while (cur1 < min_compatible && cur2 < min_compatible && !both.isEmpty() && !comp1.isEmpty() && !comp2.isEmpty()) {
                if (both.peek() < comp1.peek() + comp2.peek()) {
                    curCost += both.poll();
                } else {
                    curCost += comp1.poll();
                    curCost += comp2.poll();
                }
                cur1 ++;
                cur2 ++;
            }
            while (cur1 < min_compatible) {
                if (comp1.isEmpty()) return -1;
                curCost += comp1.poll();
                cur1 ++;
            }
            while (cur2 < min_compatible) {
                if (comp2.isEmpty()) return -1;
                curCost += comp2.poll();
                cur2++;
            }
            return curCost;
        }
    }
//    public static void main(String[] args) {
//        int n = 5;
//        int[] cost = {2, 4, 5, 6, 1};
//        int[] compatible1 = {1, 1, 1, 0, 1};
//        int[] compatible2 = {1, 0, 1, 1, 0};
//        int min_compatible = 3;
//        System.out.println(GPUSelection.minimumCost(n, cost, compatible1, compatible2, min_compatible));
//    }

}