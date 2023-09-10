package com.company;

import java.util.*;

public class Main {

//    static class Pair implements Comparable<Pair> {
//        int value;
//        int idx;
//        Pair (int val, int idx) {
//            this.value = val;
//            this.idx = idx;
//        }
//        @Override
//        public int compareTo(Pair o) {
//            return Integer.compare(this.value, o.value);
//        }
//    }
//
//    public static long minimumSum(int[] arr, int k) {
//        PriorityQueue<Pair> pq = new PriorityQueue<>(Collections.reverseOrder());
//        for (int i = 0; i < arr.length; i ++) {
//            int value = arr[i];
//            int pos = 0;
//            while (value > 0) {
//                if ((value & 1) == 1) {
//                    pq.add(new Pair(1 << pos, i));
//                }
//                value >>= 1;
//                pos++;
//            }
//        }
//        int ops = 0;
//        while (ops < k && !pq.isEmpty()) {
//            Pair p = pq.poll();
//            arr[p.idx] -= p.value;
//            ops ++;
//        }
//        long sum = 0;
//        for (int num : arr) {
//            sum += num;
//        }
//        return sum;
//    }
//
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        String[] line1 = sc.nextLine().split(" ");
//        int n = Integer.valueOf(line1[0]);
//        int k = Integer.valueOf(line1[1]);
//        int[] input = new int[n];
//        for (int i = 0; i < n; i ++) {
//            input[i] = sc.nextInt();
//        }
//        System.out.println(minimumSum(input, k));
//    }
    // 1 2 3 5 7

    // 1 2 3 3 5 7
    // 7 4 1
    // 7 1 1 0 2
    // 1 1 1 0 2 2
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int n = sc.nextInt();
//        int[] input = new int[n];
//        for (int i = 0; i < n; i ++) {
//            input[i] = sc.nextInt();
//        }
//        Arrays.sort(input);
//        int res = input[input.length - 1];
//        for (int i = 0; i < (input.length - 1) / 2; i ++) {
//            res += input[input.length - 2 - i] - input[i];
//        }
//        System.out.println(res);
//    }

    static class Median{
        private PriorityQueue<Integer> max;
        private PriorityQueue<Integer> min;
        public Median() {
            max = new PriorityQueue<>((a, b) -> b - a);
            min = new PriorityQueue<>((a, b) -> a - b);
        }

        public void insert(int num) {
            if (max.isEmpty() || num <= max.peek()) max.offer(num);
            else min.offer(num);
            balance();
        }

        public void erase(int num) {
            if (num <= max.peek()) {
                max.remove(num);
            } else {
                min.remove(num);
            }
            balance();
        }

        public double getMedian() {
            if (max.size() < min.size()) return ((double) max.peek() + (double) min.peek());
            else return (double) max.peek();
        }

        private void balance() {
            while(max.size() < min.size()) max.offer(min.poll());
            while (max.size() > min.size() + 1) min.offer(max.poll());
        }
    }

    public static void solve(int[] a, int[] b) {
//        System.out.println("a: " + Arrays.toString(a));
//        System.out.println("b: " + Arrays.toString(b));
        Median median = new Median();
        for (int num: a) median.insert(num);
        List<Double> res = new ArrayList<>();
        res.add(median.getMedian());
        for (int idx: b) {
            median.erase(a[idx - 1]);
            res.add(median.getMedian());
        }
        for (double med: res) {
            System.out.println(med);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = Integer.valueOf(sc.nextLine());
        System.out.println("t: " + t);
        for (int i = 0; i < t; i ++) {
            int n = Integer.valueOf(sc.nextLine());
            System.out.println("n: " + n );
            int[] a = new int[n];
            int[] b = new int[n - 1];
            String[] aStr = sc.nextLine().split(" ");
            System.out.println("aStr: " + Arrays.toString(aStr));
            String[] bStr = sc.nextLine().split(" ");
            System.out.println("bStr: " + Arrays.toString(bStr));
            for (int j = 0; j < n;j ++) {
                a[j] = Integer.valueOf(aStr[i]);
            }
            for (int j = 0; j < n - 1; j ++) {
                b[j] = Integer.valueOf(bStr[i]);
            }
            solve(a, b);
        }
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



//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        String[] input1 = sc.nextLine().split(" ");
//        int n = Integer.valueOf(input1[0]);
//        int k = Integer.valueOf(input1[1]);
//        String str = sc.nextLine();
//        int i = 0, res = 0;
//        while (k > 0) {
//            if (str.charAt(i++)== 'A') k--;
//            i = i % n;
//            res ++;
//        }
//        System.out.println(res);
//    }

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