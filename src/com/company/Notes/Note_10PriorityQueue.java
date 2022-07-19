package com.company.Notes;


import java.util.*;

public class Note_10PriorityQueue {

    public class ListNode {
        int val;
        Note5_LinkedList.ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, Note5_LinkedList.ListNode next) {
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

    // merge k sorted arrays

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
    }

}
