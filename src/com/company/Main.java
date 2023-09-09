package com.company;

import java.util.*;

public class Main {

    public void treeHash() {
        Scanner sc = new Scanner(System.in);
        String[] inputStrArg = sc.nextLine().split(" ");
        int[] inputArg = new int[4];
        for (int i = 0; i < inputStrArg.length; i ++) {
            inputArg[i] = Integer.valueOf(inputStrArg[i]);
        }
        int[] tree = new int[inputArg[0] - 1];


    }



    /**
     * 5
     * 1 1 3 3
     * 1 1 1 1 1
     *
     *     1
     *   2    3
     *      4    5
     */

    public void tree() {
        Scanner sc = new Scanner(System.in);
        int num = Integer.valueOf(sc.nextLine());
        String[] parentStr = sc.nextLine().split(" ");
        int[] parent = new int[parentStr.length];
        for (int i = 0; i < parentStr.length; i ++) {
            parent[i] = Integer.valueOf(parentStr[i]);
        }
        String[] colorStr = sc.nextLine().split(" ");
        int[] color = new int[parentStr.length];
        for (int i = 0; i < parentStr.length; i ++) {
            color[i] = Integer.valueOf(colorStr[i]);
        }
        int res = 0;
        int[] tree = new int[num];
        Arrays.fill(tree, 1);
        for (int i = parent.length - 1; i > 0; i -= 2) {
            int curParent = parent[i];
            if (color[curParent] == 1) {
                tree[curParent - 1] = tree[i] + tree[i + 1];
            } else {
                tree[curParent - 1] = tree[i] * tree[i + 1];
            }
        }
        System.out.println(tree[0]);
    }



    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] input1 = sc.nextLine().split(" ");
        int n = Integer.valueOf(input1[0]);
        int k = Integer.valueOf(input1[1]);
        String str = sc.nextLine();
        int i = 0, res = 0;
        while (k > 0) {
            if (str.charAt(i++)== 'A') k--;
            i = i % n;
            res ++;
        }
        System.out.println(res);
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