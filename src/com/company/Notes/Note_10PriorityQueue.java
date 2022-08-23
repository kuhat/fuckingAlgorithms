package com.company.Notes;


import java.util.*;

public class Note_10PriorityQueue {

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static void compare1(){
        int[] nums = new int[]{1, 2 ,3, 4};
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;  // 默认为升序排列，降序排列为2-1
            }
        });
        for (int num : nums) {
            priorityQueue.offer(num);
        }
    }

    public static void compare3(){
        PriorityQueue<ListNode> priorityQueue = new PriorityQueue<>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return o1.val - o2.val;  // 通过node的val进行升序排列
            }
        });

        String[] strings = new String[]{"12", "23"};
        Arrays.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String s1 = o1 + o2;
                String s2= o1;
                return s1.compareTo(s2);
            }
        });
    }

    public static void compare4() {
        int[] nums = new int[] {1 ,2 ,3 , 4};
        PriorityQueue<ListNode> priorityQueue = new PriorityQueue<>(5, (a, b) -> (a.val - b.val));  // lamda表达式
    }

    // 215: Kth Largest Element in an Array
    /**
     * Given an integer array nums and an integer k, return the kth largest element in the array.
     *
     * Note that it is the kth largest element in the sorted order, not the kth distinct element.
     *
     * You must solve it in O(n) time complexity.
     *
     * Input: nums = [3,2,1,5,6,4], k = 2
     * Output: 5
     */
    class Solution215 {
        public int findKthLargest(int[] nums, int k) {
            if (nums == null || nums.length == 0) {
                return 0;
            }
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();
            for (Integer num : nums) {
                minHeap.offer(num);
                if (minHeap.size() > k) {
                    minHeap.poll();
                }
            }
            return minHeap.poll();
        }
    }

    // 56: Merge Intervals
    /**
     * Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals,
     * and return an array of the non-overlapping intervals that cover all the intervals in the input.
     *
     *
     * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
     * Output: [[1,6],[8,10],[15,18]]
     * Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6].
     *
     * Input: intervals = [[1,4],[4,5]]
     * Output: [[1,5]]
     * Explanation: Intervals [1,4] and [4,5] are considered overlapping.
     */

    class Solution56 {
        public int[][] merge(int[][] intervals) {
            if (intervals == null || intervals.length == 0) return new int[][]{};
            Arrays.sort(intervals, (a,b) -> (a[0] - b[0]));  //  按起点的大小进行升序排列
            List<int[]> res = new ArrayList<>();
            int start = intervals[0][0];
            int end = intervals[0][1];
            for (int[] interval : intervals) {
                if (interval[0] <= end) end = Math.max(interval[1], end);  // 如果后一个的start小于end,将前一个的end更新为后一个的end
                else {
                    res.add(new int[]{start, end});
                    start = interval[0];
                    end = interval[1];
                }
            }
            res.add(new int[]{start, end});
            return res.toArray(new int[][]{});
        }
    }

    // 373: Find K Pairs with smallest sum
    /**
     * You are given two integer arrays nums1 and nums2 sorted in ascending order and an integer k.
     *
     * Define a pair (u, v) which consists of one element from the first array and one element from the second array.
     *
     * Return the k pairs (u1, v1), (u2, v2), ..., (uk, vk) with the smallest sums.
     *
     Input: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
     Output: [[1,2],[1,4],[1,6]]
     Explanation: The first 3 pairs are returned from the sequence: [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]

     Input: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
     Output: [[1,1],[1,1]]
     Explanation: The first 2 pairs are returned from the sequence: [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
     */

    class Solution373 {
        public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
            List<List<Integer>> res = new ArrayList<>();
            if (nums1.length == 0 || nums2.length == 0 || k == 0) return res;
            PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a,b) -> (a[0] + a[1] - b[0] - b[1]));  //  lamda重写compare方法
            for (int i = 0; i < k && i < nums1.length; i++) {  // 只需要加入k个元素，num1和num2[0]加入，第三个index位置对应的是nums2的index
                priorityQueue.offer(new int[]{nums1[i], nums2[0], 0});
            }
            while (!priorityQueue.isEmpty() && k-- > 0) {
                int[] cur = priorityQueue.poll();  // 取出第一小和的元素
                List<Integer> temp = new ArrayList<>();
                temp.add(cur[0]);
                temp.add(cur[1]);
                res.add(temp);
                if (cur[2] == nums2.length - 1) continue;  // cur[2]的最后一个元素了，相当于nums2没有元素可以加了
                priorityQueue.offer(new int[]{cur[0], nums2[cur[2] + 1], cur[2] + 1});  // 加入nums[2]的下一个位置的元素和num1当前的元素，index ++
            }
            return res;
        }
    }

    // 23:merge k sorted arrays

    /**
     * array1: [1, 3, 5, 7]
     * array2: [2, 4 ,6 ,8]
     * array3: [0, 9, 10, 11]
     * 将这三个array组合为一个sorted array
     * [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
     */
    class mergeKsortedArrays{
        public static int[] mergeKSortedArrays(int[][] arrays) {
            List<Integer> ret = new ArrayList<>();
            if (arrays == null || arrays.length == 0) return new int[]{};

            PriorityQueue<ArrayContainer> priorityQueue = new PriorityQueue<>();  // 比较每个array的起始位置的大小，一个个装进新的数组
            for (int i = 0; i < arrays.length; i++) {
                if (arrays[i].length != 0) {
                    ArrayContainer ac = new ArrayContainer(arrays[i], 0);
                    priorityQueue.offer(ac);
                }
            }

            while (!priorityQueue.isEmpty()) {
                ArrayContainer ac = priorityQueue.poll();  // 将当前位置最小的那个poll出来加入ret，并且把index++
                ret.add(ac.array[ac.index++]);
                if (ac.index < ac.array.length) priorityQueue.offer(new ArrayContainer(ac.array,ac.index));  // 加入下一个
            }

            int[] res = new int[ret.size()];
            for (int i = 0; i < res.length; i++) {
                res[i] = ret.get(i);
            }
            return res;

        }

        // 新建一个class来表示 array和它的起始值（当前遍历到的 index）
        private static class ArrayContainer implements Comparable<ArrayContainer> {
            // 放入array和它的起始位置
            int[] array;
            int index;

            public ArrayContainer(int[] array, int index) {
                this.array = array;
                this.index = index;
            }

            @Override
            public int compareTo(ArrayContainer o) {
                return this.array[index] - o.array[o.index];  // 按照index位置的大小升序排列
            }
        }

        public ListNode mergeKLists2(ListNode[] lists) {
            ListNode dummy = new ListNode(0);
            ListNode cur = dummy;
            if (lists == null || lists.length == 0) return dummy.next;

            PriorityQueue<ListNode> pq = new PriorityQueue<>((a,b) -> (a.val - b.val));  // 拉姆达表达式
            for (int i =0; i < lists.length; i++) {
                if (lists[i] != null) {
                    pq.offer(lists[i]);
                }
            }
            while(!pq.isEmpty()) {
                ListNode newNode = pq.poll();
                cur.next = newNode;
                cur = cur.next;
                if (newNode.next != null) pq.offer(newNode.next);
            }
            return dummy.next;
        }


        // 分治方法
        // O（nlogk）
        public ListNode mergeKLists(ListNode[] lists) {
            if (lists == null || lists.length == 0) return null;
            return sort(lists, 0, lists.length -1);
        }

        private ListNode sort(ListNode[] lists, int lo, int hi) {  // 将整体的链表array两个两个的分开
            if (lo > hi) return lists[lo];
            int mid = (hi - lo) / 2 + lo;
            ListNode L1 = sort(lists, lo, mid);
            ListNode L2 = sort(lists, mid + 1, hi);
            return merge(L1, L2);
        }

        private ListNode merge(ListNode L1, ListNode L2) {  // 两个两个的链表合在一起
            if (L1 == null) return L2;
            if (L2 == null) return L1;
            if (L1.val < L2.val){
                L1.next = merge(L1.next, L2);
                return L1;
            }
            L2.next = merge(L1, L2.next);
            return L2;
        }

    }

    // 313 super ugly number

    /**
     * A super ugly number is a positive integer whose prime factors are in the array primes.
     *
     * Given an integer n and an array of integers primes, return the nth super ugly number.
     *
     * The nth super ugly number is guaranteed to fit in a 32-bit signed integer.
     *
     * Input: n = 12, primes = [2,7,13,19]
     * Output: 32
     * Explanation: [1,2,4,7,8,13,14,16,19,26,28,32] is the sequence of the first 12 super ugly numbers given primes = [2,7,13,19].
     *
     * Input: n = 1, primes = [2,3,5]
     * Output: 1
     * Explanation: 1 has no prime factors, therefore all of its prime factors are in the array primes = [2,3,5].
     *
     * pq:
     * 2 1 2
     * 7 1 7
     * 13 1 13
     * 19 1 19
     *
     * pq:
     * 4 2 2
     *
     */
    class solution313{
        public int nthSuperUglyNumber(int n, int[] primes) {
            int[] res = new int[n];
            res[0] = 1;
            PriorityQueue<Num> pq = new PriorityQueue<>((a,b)->(a.val - b.val));
            for (int i = 0; i < primes.length; i++) {
                pq.add(new Num(primes[i], 1, primes[i]));  // pq 初始化
            }

            for (int i = 1; i < n; i++) {
                res[i] = pq.peek().val;
                while (pq.peek().val == res[i]) {
                    Num next = pq.poll();
                    pq.add(new Num(next.prime * res[next.index], next.index + 1, next.prime));
                }
            }

            return res[n -1];
        }

        class Num {
            int val;
            int index;
            int prime;

            public Num(int val, int index, int prime) {
                this.val = val;
                this.index = index;
                this.prime = prime;
            }
        }
    }

// 502
    /**
     * Suppose LeetCode will start its IPO soon. In order to sell a good price of its shares to Venture Capital, LeetCode would like to work on some projects to increase its capital before the IPO. Since it has limited resources, it can only finish at most k distinct projects before the IPO. Help LeetCode design the best way to maximize its total capital after finishing at most k distinct projects.
     *
     * You are given n projects where the ith project has a pure profit profits[i] and a minimum capital of capital[i] is needed to start it.
     *
     * Initially, you have w capital. When you finish a project, you will obtain its pure profit and the profit will be added to your total capital.
     *
     * Pick a list of at most k distinct projects from given projects to maximize your final capital, and return the final maximized capital.
     *
     * The answer is guaranteed to fit in a 32-bit signed integer.
     *
     * Example 1:
     *
     * Input: k = 2, w = 0, profits = [1,2,3], capital = [0,1,1]
     * Output: 4
     * Explanation: Since your initial capital is 0, you can only start the project indexed 0.
     * After finishing it you will obtain profit 1 and your capital becomes 1.
     * With capital 1, you can either start the project indexed 1 or the project indexed 2.
     * Since you can choose at most 2 projects, you need to finish the project indexed 2 to get the maximum capital.
     * Therefore, output the final maximized capital, which is 0 + 1 + 3 = 4.
     * Example 2:
     *
     * Input: k = 3, w = 0, profits = [1,2,3], capital = [0,1,2]
     * Output: 6
     */

    class Solution {
        public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
            PriorityQueue<int[]> cap = new PriorityQueue<>((a,b)->(a[0] - b[0]));  // capital 从小到大排序
            PriorityQueue<int[]> pro = new PriorityQueue<>((a,b)->(b[1] - a[1]));  // profit 从大到小排序
            for (int i = 0; i < profits.length; i++) {  // 将商品按照profits从小到大加进pq
                cap.add(new int[]{capital[i], profits[i]});
            }
            for (int i = 0; i < k; i++) {  // 当可以买的时候
                while(!cap.isEmpty() && cap.peek()[0] <= w) {
                    pro.add(cap.poll());  // 如果第一个 capital 的值小于w，说明可以负担，profit加进去，进行排序降序
                }
                if (pro.isEmpty()) break;
                w += pro.poll()[1];  //  把pro最大的拿出来
            }
            return w;
        }
    }

    // 295： find Median from data stream
    /**
     * The median is the middle value in an ordered integer list. If the size of the list is even, there is no middle
     * value and the median is the mean of the two middle values.
     *
     * For example, for arr = [2,3,4], the median is 3.
     * For example, for arr = [2,3], the median is (2 + 3) / 2 = 2.5.
     * Implement the MedianFinder class:
     *
     * MedianFinder() initializes the MedianFinder object.
     * void addNum(int num) adds the integer num from the data stream to the data structure.
     * double findMedian() returns the median of all elements so far. Answers within 10-5 of the actual answer
     * will be accepted.
     *
     * Example 1:
     *
     * Input
     * ["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
     * [[], [1], [2], [], [3], []]
     * Output
     * [null, null, null, 1.5, null, 2.0]
     *
     * Explanation
     * MedianFinder medianFinder = new MedianFinder();
     * medianFinder.addNum(1);    // arr = [1]
     * medianFinder.addNum(2);    // arr = [1, 2]
     * medianFinder.findMedian(); // return 1.5 (i.e., (1 + 2) / 2)
     * medianFinder.addNum(3);    // arr[1, 2, 3]
     * medianFinder.findMedian(); // return 2.0
     */

    class MedianFinder {

        private PriorityQueue<Long> small;
        private PriorityQueue<Long> large;

        public MedianFinder() {
            small = new PriorityQueue<>();
            large = new PriorityQueue<>();
        }

        public void addNum(int num) {
            large.add((long) num);
            small.add(-large.poll());
            if (large.size() < small.size()){
                large.add(-small.poll());
            }
        }

        public double findMedian() {
            return large.size() > small.size() ? large.peek() : (large.peek() - small.peek()) / 2.0;
        }
    }

    // 378: Kth smallest element in a sorted matrix
    /**
     * Given an n x n matrix where each of the rows and columns is sorted in ascending order, return the kth smallest element in the matrix.
     *
     * Note that it is the kth smallest element in the sorted order, not the kth distinct element.
     *
     * You must find a solution with a memory complexity better than O(n2).
     *
     *
     *
     * Example 1:
     *
     * Input: matrix = [[1,5,9],[10,11,13],[12,13,15]], k = 8
     * Output: 13
     * Explanation: The elements in the matrix are [1,5,9,10,11,12,13,13,15], and the 8th smallest number is 13
     * Example 2:
     *
     * Input: matrix = [[-5]], k = 1
     * Output: -5
     */
    class Solution378 {
        public int kthSmallest(int[][] matrix, int k) {
            PriorityQueue<Tuple> pq = new PriorityQueue<>(matrix.length, (a,b) -> (a.val - b.val));
            for (int i = 0; i < matrix.length; i++) {
                pq.offer(new Tuple(0, i, matrix[0][i]));  // 第一行加入
            }
            for (int i = 0; i < k - 1; i ++) {  // 把前k - 1个元素pop出来过后剩下的一个就是
                Tuple tuple = pq.poll();
                if (tuple.x == matrix.length - 1) continue;  // 边界条件
                pq.offer(new Tuple(tuple.x + 1, tuple.y, matrix[tuple.x + 1][tuple.y]));  // 下一行的数加入
            }
            return pq.poll().val;

        }

        class Tuple{
            int x, y, val;
            public Tuple(int x, int y, int val) {
                this.x = x;
                this.y = y;
                this.val = val;
            }
        }
    }

    // 1024：Last weight stone
    /**
     * You are given an array of integers stones where stones[i] is the weight of the ith stone.
     *
     * We are playing a game with the stones. On each turn, we choose the heaviest two stones and smash them together. Suppose the heaviest two stones have weights x and y with x <= y. The result of this smash is:
     *
     * If x == y, both stones are destroyed, and
     * If x != y, the stone of weight x is destroyed, and the stone of weight y has new weight y - x.
     * At the end of the game, there is at most one stone left.
     *
     * Return the weight of the last remaining stone. If there are no stones left, return 0.
     *
     *
     *
     * Example 1:
     *
     * Input: stones = [2,7,4,1,8,1]
     * Output: 1
     * Explanation:
     * We combine 7 and 8 to get 1 so the array converts to [2,4,1,1,1] then,
     * we combine 2 and 4 to get 2 so the array converts to [2,1,1,1] then,
     * we combine 2 and 1 to get 1 so the array converts to [1,1,1] then,
     * we combine 1 and 1 to get 0 so the array converts to [1] then that's the value of the last stone.
     * Example 2:
     *
     * Input: stones = [1]
     * Output: 1
     */
    class Solution1024 {
        public int lastStoneWeight(int[] stones) {
            PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> b - a);
            for (int i = 0; i < stones.length; i++) {
                pq.offer(stones[i]);
            }
            while (pq.size() > 1) {
                int remain = pq.poll() - pq.poll();
                if (remain != 0) pq.offer(remain);
            }
            return pq.size() != 0 ? pq.poll() : 0;
        }
    }


}
