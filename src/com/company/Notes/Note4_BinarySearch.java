package com.company.Notes;

import java.util.*;

public class Note4_BinarySearch {

    // 递归二分查找 time: O(logn) space: O(logn)
    public static int binarySearch(int[] nums, int low, int high, int target) {
        if (high <= low) return -1;
        int mid = low + (high - low) / 2;
        if (nums[mid] > target) {
            return binarySearch(nums, low, mid, target);
        } else if (nums[mid] < target) {
            return binarySearch(nums, mid + 1, high, target);
        } else {
            return mid;
        }
    }
    // 二分查找迭代写法：
//    public static

    // 35 Search Insert Position
    // binary search***

    /**
     * Given a sorted array of distinct integers and a target value, return the index if the target is found. If not,
     * return the index where it would be if it were inserted in order.
     * <p>
     * You must write an algorithm with O(log n) runtime complexity.
     * <p>
     * Input: nums = [1,3,5,6], target = 5
     * Output: 2
     * <p>
     * Input: nums = [1,3,5,6], target = 2
     * Output: 1
     * <p>
     * Input: nums = [1,3,5,6], target = 7
     * Output: 4
     *
     * @param target
     */

    public int serachInsert(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    // 278: First Bad Version

    /**
     * You are a product manager and currently leading a team to develop a new product. Unfortunately, the latest version
     * of your product fails the quality check. Since each version is developed based on the previous version, all the
     * versions after a bad version are also bad.
     * <p>
     * Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one, which causes all the following ones to be bad.
     * <p>
     * You are given an API bool isBadVersion(version) which returns whether version is bad. Implement a function to
     * find the first bad version. You should minimize the number of calls to the API.
     * <p>
     * Input: n = 5, bad = 4
     * Output: 4
     * Explanation:
     * call isBadVersion(3) -> false
     * call isBadVersion(5) -> true
     * call isBadVersion(4) -> true
     * Then 4 is the first bad version.
     * <p>
     * Input: n = 1, bad = 1
     * Output: 1
     *
     * @param n
     */

    public int firstBadVersion(int n) {
        int start = 1, end = n;

        while (start < end) {
            int mid = (end - start) / 2 + start;
            if (isBadVersion(mid)) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }

    // private function given in the question that can check the version of the product （given in the question）
    private boolean isBadVersion(int n) {
        return false;
    }

    // 33: Search in Rotated Sorted Array

    /**
     * There is an integer array nums sorted in ascending order (with distinct values).
     * <p>
     * Prior to being passed to your function, nums is possibly rotated at an unknown pivot index k (1 <= k < nums.length)
     * such that the resulting array is [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed).
     * For example, [0,1,2,4,5,6,7] might be rotated at pivot index 3 and become [4,5,6,7,0,1,2].
     * <p>
     * Given the array nums after the possible rotation and an integer target, return the index of target if
     * it is in nums, or -1 if it is not in nums.
     * <p>
     * You must write an algorithm with O(log n) runtime complexity.
     * <p>
     * Input: nums = [4,5,6,7,0,1,2], target = 0
     * Output: 4
     * <p>
     * Input: nums = [4,5,6,7,0,1,2], target = 3
     * Output: -1
     * <p>
     * Input: nums = [1], target = 0
     * Output: -1
     *
     * @param nums
     * @return
     */
    // 分两种情况来找
    // 找左边递增还是右边递增
    //
    // 1. 4 5 6 7 0 1 2
    // 2. 4 5 6 0 1 2 3
    public int search(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            // 前半部分递增
            if (nums[start] <= nums[mid]) {
                if (target < nums[mid] && target >= nums[start]) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }
            // 后半部分递增
            if (nums[mid] <= nums[end]) {
                if (target > nums[mid] && target <= nums[end]) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
        }
        return -1;
    }

    // leetCode 81: follow Up Search in Rotated Sorted Array II （比上一道题有重复的元素）

    /**
     * There is an integer array nums sorted in non-decreasing order (not necessarily with distinct values).
     * <p>
     * Before being passed to your function, nums is rotated at an unknown pivot index k (0 <= k < nums.length)
     * such that the resulting array is [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed).
     * For example, [0,1,2,4,4,4,5,6,6,7] might be rotated at pivot index 5 and become [4,5,6,6,7,0,1,2,4,4].
     * <p>
     * Given the array nums after the rotation and an integer target, return true if target is in nums, or false if
     * it is not in nums.
     * <p>
     * You must decrease the overall operation steps as much as possible.
     * <p>
     * Input: nums = [2,5,6,0,0,1,2], target = 0
     * Output: true
     * <p>
     * Input: nums = [2,5,6,0,0,1,2], target = 3
     * Output: false
     */

    class SearchRotateii {
        public boolean search(int[] nums, int target) {
            int start = 0, end = nums.length - 1;
            while (start <= end) {
                int mid = (start + end) / 2;
                if (nums[mid] == target) {
                    return true;
                }
                if (nums[start] == nums[mid] && nums[mid] == nums[end]) {
                    start++;  // 如果有重复的就缩小范围
                    end--;
                }
                // 前半部分递增
                else if (nums[start] <= nums[mid]) {
                    if (target < nums[mid] && target >= nums[start]) {
                        end = mid - 1;
                    } else {
                        start = mid + 1;
                    }
                }
                // 后半部分递增
                else {
                    if (target > nums[mid] && target <= nums[end]) {
                        start = mid + 1;
                    } else {
                        end = mid - 1;
                    }
                }
            }
            return false;
        }
    }

    // Leetcode 74 Search Matrix

    /**
     * Write an efficient algorithm that searches for a value target in an m x n integer matrix matrix.
     * This matrix has the following properties:
     * <p>
     * Integers in each row are sorted from left to right.
     * The first integer of each row is greater than the last integer of the previous row.
     * <p>
     * Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
     * Output: true
     * <p>
     * Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
     * Output: false
     */
    class SearchMatrix74 {
        public boolean searchMatrix(int[][] matrix, int target) {
            int row = matrix.length;
            int col = matrix[0].length;
            int start = 0;
            int end = row * col - 1;

            while (start <= end) {
                int mid = (end - start) / 2 + start;
                int value = matrix[mid / col][mid % col];  // 当前这个值处于二维数组中的哪里（背下来）
                if (value == target) {
                    return true;
                } else if (value < target) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
            return false;
        }
    }

    // LeetCode 240: Search a 2D matrix ii

    /**
     * Write an efficient algorithm that searches for a value target in an m x n integer matrix matrix.
     * This matrix has the following properties:
     *
     * Integers in each row are sorted in ascending from left to right.
     * Integers in each column are sorted in ascending from top to bottom.
     *
     * Input: matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 5
     * Output: true
     *
     * Input: matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 20
     * Output: false
     */

    class Search2DMatrix74{
        public boolean searchMatrix(int[][] matrix, int target) {
            int row = matrix.length;
            int col = matrix[0].length - 1;

            while (col >= 0 && row <= matrix.length - 1) {
                if (target == matrix[row][col]) {
                        return true;
                } else if (target < matrix[row][col]) {  // 如果比col元素小的话肯定在此行的中间，也就是col--
                    col--;
                } else {  // 如果当前元素比col指针的元素大的话肯定在下一行
                    row++;
                }
            }
            return false;
        }
    }

    // 374: guess number
    /**
     * We are playing the Guess Game. The game is as follows:
     *
     * I pick a number from 1 to n. You have to guess which number I picked.
     *
     * Every time you guess wrong, I will tell you whether the number I picked is higher or lower than your guess.
     *
     * You call a pre-defined API int guess(int num), which returns three possible results:
     *
     * -1: Your guess is higher than the number I picked (i.e. num > pick).
     * 1: Your guess is lower than the number I picked (i.e. num < pick).
     * 0: your guess is equal to the number I picked (i.e. num == pick).
     * Return the number that I picked.
     *
     * Example 1:
     *
     * Input: n = 10, pick = 6
     * Output: 6
     * Example 2:
     *
     * Input: n = 1, pick = 1
     * Output: 1
     * Example 3:
     *
     * Input: n = 2, pick = 1
     * Output: 1
     */
    /**
     * Forward declaration of guess API.
     *          num   your guess
     * @return 	     -1 if num is higher than the picked number
     *			      1 if num is lower than the picked number
     *               otherwise return 0
     * int guess(int num);
     */

    public class Solution374 {
        public int guessNumber(int n) {
            int start = 1, end = n;
            while(start <= end) {
                int mid = (end - start) / 2 + start;
                if (guess(mid) == 0) return mid;
                else if (guess(mid) == 1) start = mid + 1;
                else end = mid - 1;
            }
            return -1;
        }

        public int guess(int i) {return 0;}
    }

    // 315： Count of smaller after self: 递减
    /**
     * Given an integer array nums, return an integer array counts where counts[i] is the number of smaller elements
     * to the right of nums[i].
     *
     * Example 1:
     *
     * Input: nums = [5,2,6,1]
     * Output: [2,1,1,0]
     * Explanation:
     * To the right of 5 there are 2 smaller elements (2 and 1).
     * To the right of 2 there is only 1 smaller element (1).
     * To the right of 6 there is 1 smaller element (1).
     * To the right of 1 there is 0 smaller element.
     * Example 2:
     *
     * Input: nums = [-1]
     * Output: [0]
     * Example 3:
     *
     * Input: nums = [-1,-1]
     * Output: [0,0]
     */
    class Solution315 {
        /*
        正常将数组排序：[5, 2, 6, 1]
        从后往前遍历
            list: 1    6    1 2 6   1 2 5 6

        插入的idx: 0    1    0 1 1   0 1 1 2

        要返回一个List, 要倒着来Arrays.asList()只能放对象, 用Integer来初始化
         */
        public List<Integer> countSmaller(int[] nums) {
            Integer[] res = new Integer[nums.length];  // Arrays.asList只能接受对象
            List<Integer> list = new ArrayList<>();
            for (int i = nums.length - 1; i >= 0; i--) {  // 从后往前遍历
                int index = findIndex(list, nums[i]);  // 二分法找到当前数应该在那个位置插入
                res[i] = index;  // 从后往前更新res数组
                list.add(index, nums[i]);  // 插入，排序
            }
            return Arrays.asList(res);
        }

        public int findIndex(List<Integer> list, int target) {
            if (list.size() == 0) return 0;
            int start= 0;
            int end = list.size() - 1;
            if (list.get(end) < target) return end + 1;  // 如果list的最后一个数字比target小直接放在最后一位
            if (list.get(start) > target) return 0;
            while (start + 1 < end) {  //  binary search
                int mid = (end - start) / 2 + start;
                if (list.get(mid) < target) start = mid + 1;
                else end = mid;
            }
            if (list.get(start) >= target) return start;
            return end;
        }
    }

    // 528： Random Pick with weight
    /**
     * You are given a 0-indexed array of positive integers w where w[i] describes the weight of the ith index.
     *
     * You need to implement the function pickIndex(), which randomly picks an index in the range [0, w.length - 1]
     * (inclusive) and returns it. The probability of picking an index i is w[i] / sum(w).
     *
     * For example, if w = [1, 3], the probability of picking index 0 is 1 / (1 + 3) = 0.25 (i.e., 25%), and the
     * probability of picking index 1 is 3 / (1 + 3) = 0.75 (i.e., 75%).
     *
     * Example 1:
     *
     * Input
     * ["Solution","pickIndex"]
     * [[[1]],[]]
     * Output
     * [null,0]
     *
     * Explanation
     * Solution solution = new Solution([1]);
     * solution.pickIndex(); // return 0. The only option is to return 0 since there is only one element in w.
     * Example 2:
     *
     * Input
     * ["Solution","pickIndex","pickIndex","pickIndex","pickIndex","pickIndex"]
     * [[[1,3]],[],[],[],[],[]]
     * Output
     * [null,1,1,1,1,0]
     *
     * Explanation
     * Solution solution = new Solution([1, 3]);
     * solution.pickIndex(); // return 1. It is returning the second element (index = 1) that has a probability of 3/4.
     * solution.pickIndex(); // return 1
     * solution.pickIndex(); // return 1
     * solution.pickIndex(); // return 1
     * solution.pickIndex(); // return 0. It is returning the first element (index = 0) that has a probability of 1/4.
     *
     * Since this is a randomization problem, multiple answers are allowed.
     * All of the following outputs can be considered correct:
     * [null,1,1,1,1,0]
     * [null,1,1,1,1,1]
     * [null,1,1,1,0,0]
     * [null,1,1,1,0,1]
     * [null,1,0,1,0,0]
     * ......
     * and so on.
     */

    class Solution528 {

        Random rdm;
        int[] sum;

        public Solution528(int[] w) {
            rdm = new Random();
            sum = new int[w.length];
            sum[0] = w[0];
            for (int i = 1; i < w.length; i++) {
                sum[i] = sum[i - 1] + w[i];
            }
        }
        public int pickIndex() {
            int idx = rdm.nextInt(sum[sum.length - 1]) + 1;
            int left = 0, right = sum.length - 1;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (sum[mid] > idx) {
                    right = mid;
                } else if (sum[mid] == idx) {
                    return mid;
                } else {
                    left = mid + 1;
                }
            }
            return left;
        }
    }

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(w);
 * int param_1 = obj.pickIndex();
 */

  // 410： Split array Largest sum
    /**
     * Given an integer array nums and an integer k, split nums into k non-empty subarrays such that the largest
     * sum of any subarray is minimized.
     *
     * Return the minimized largest sum of the split.
     *
     * A subarray is a contiguous part of the array.
     *
     *
     *
     * Example 1:
     *
     * Input: nums = [7,2,5,10,8], k = 2
     * Output: 18
     * Explanation: There are four ways to split nums into two subarrays.
     * The best way is to split it into [7,2,5] and [10,8], where the largest sum among the two subarrays is only 18.
     * Example 2:
     *
     * Input: nums = [1,2,3,4,5], k = 2
     * Output: 9
     * Explanation: There are four ways to split nums into two subarrays.
     * The best way is to split it into [1,2,3] and [4,5], where the largest sum among the two subarrays is only 9.
     */
    class Solution410{
        public int splitArray(int[] nums, int m) {
            int max = 0;
            int sum = 0;
            for (int num : nums) {
                sum += num;
                max = Math.max(max, num);
            }
            // 分割数组的和的部分最大值的最小值在所有值的和SUM（分割值为0）和最大值（分割数为数组的长度减一）之间
            if(m == 1) return sum;
            long left = max;
            long right = sum;
            // 从最大值到和进行二分查找，结果一定在这里面
            while (left <= right) {
                long mid = (left + right) / 2;
                // 判断当前的最大值是否可以满足把数组 nums分成m段
                if (isValid(mid, nums, m)) {  // 可以的话最大值还可以缩小
                    right = mid -1;
                } else {
                    left = mid + 1;
                }
            }
            return (int) left;
        }

        public boolean isValid(long target, int[] nums, int m) {
            int count = 1;  // 可以分成的节数
            long total = 0;  // 当前的和
            for (int num : nums) {
                total += num;
                if (total > target) { // 如果当前的和大于了最大值，可以分的数量加一
                    total = num;  // 重置total为当前的遍历到的数
                    count++;
                    // 如果可以分的数量大于了M，该方案不行
                    if (count > m) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    // Leetcode 875: KOKO eat banana
    /**
     * Koko loves to eat bananas. There are n piles of bananas, the ith pile has piles[i] bananas. The guards have
     * gone and will come back in h hours.
     *
     * Koko can decide her bananas-per-hour eating speed of k. Each hour, she chooses some pile of bananas and
     * eats k bananas from that pile. If the pile has less than k bananas, she eats all of them instead and
     * will not eat any more bananas during this hour.
     *
     * Koko likes to eat slowly but still wants to finish eating all the bananas before the guards return.
     *
     * Return the minimum integer k such that she can eat all the bananas within h hours.
     *
     * Example 1:
     *
     * Input: piles = [3,6,7,11], h = 8
     * Output: 4
     * Example 2:
     *
     * Input: piles = [30,11,23,4,20], h = 5
     * Output: 30
     * Example 3:
     *
     * Input: piles = [30,11,23,4,20], h = 6
     * Output: 23
     */
    /*
    由于Koko在一个小时内把一堆香蕉吃完之后不会再去吃其他的香蕉，那么它一小时能吃掉的香蕉的数目不会超过最多的一堆香蕉的数目（记为M）。同时，
    它每小时最少会吃1个香蕉，所以最终Koko决定的吃香蕉的速度K应该是在1到M之间。
    我们可以应用二分查找的思路，先选取1和M的平均数，(1+M)/2，看以这个速度Koko能否在H小时内吃掉所有香蕉。如果不能在H小时内吃掉所有的香蕉，
    那么它需要尝试更快的速度，也就是K应该在(1+M)/2到M之间，下一次我们尝试(1+M)/2和M的平均值。
    如果Koko以(1+M)/2的速度能够在H小时内吃完所有的香蕉，那么我们来判断这是不是最慢的速度。可以尝试一下稍微慢一点的速度，(1+m)/2 - 1。
    如果Koko以这个速度不能在H小时之内吃完所有香蕉，那么(1+M)/2就是最慢的可以在H小时吃完香蕉的速度。如果以(1+m)/2 - 1的速度也能在H小时内吃完香蕉，
    那么接下来Koko尝试更慢的速度，1和(1+M)/2的平均值。
    以此类推，我们按照二分查找的思路总能找到让Koko在H小时内吃完所有香蕉的最慢速度K。
     */
    class Solution875 {
        public int minEatingSpeed(int[] piles, int h) {
            int max = Integer.MIN_VALUE;
            for (int i : piles) {
                max = Math.max(max, i);
            }
            int left = 1, right = max;
            while (left <= right) {
                int mid = (right - left) / 2  + left;
                int hours = getHours(piles, mid);
                if (hours > h) {
                    left = mid + 1;
                } else {
                    if (mid == left && getHours(piles, mid - 1) > h) {
                        return mid;
                    }
                    right = mid;
                }
            }
            return -1;
        }

        private int getHours(int[] piles, int speed) {
            int res = 0;
            for (int i : piles) {
                res += Math.ceil((double) i / speed);
            }
            return res;
        }
    }

    //4 median of two sorted arrays
    /**
     * Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.
     *
     * The overall run time complexity should be O(log (m+n)).
     *
     * Example 1:
     *
     * Input: nums1 = [1,3], nums2 = [2]
     * Output: 2.00000
     * Explanation: merged array = [1,2,3] and median is 2.
     * Example 2:
     *
     * Input: nums1 = [1,2], nums2 = [3,4]
     * Output: 2.50000
     * Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.
     */
    // Naive solution
    class Solution4 {
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < nums1.length; i++) {
                list.add(nums1[i]);
            }

            for (int i= 0; i < nums2.length; i++) {
                list.add(nums2[i]);
            }
            Collections.sort(list);
            return list.size() % 2 == 1 ? (double)list.get(list.size() / 2) : (double)(list.get(list.size() / 2) +list.get(list.size() / 2 - 1)) / 2;
        }

        // binary seach
        /**
         * 首先，在一个随机的位置 i 将集合 A 划分为两部分。
         *
         *       left_A             |           right_A
         * A[0], A[1], ..., A[i-1]  |  A[i], A[i+1], ..., A[m-1]
         *
         * 由于A有m个元素，所以就有m+1 种分法（i=0~m）。由此可知： len(left_A) = i, len(right_A) = m - i。注意：当i = 0时，left_A为空，而当i = m时，right_A为空。
         *
         *
         * 同样的，在一个随机的位置 j 将集合 B 划分为两部分。：
         *
         *      left_B              |        right_B
         * B[0], B[1], ..., B[j-1]  |  B[j], B[j+1], ..., B[n-1]
         *
         *
         * 将 left_A 和 left_B 放入同一个集合，将 right_A 和 right_B 放入另外一个集合。 分别称他们为 left_part 和 right_part ：
         *
         *       left_part          |        right_part
         * A[0], A[1], ..., A[i-1]  |  A[i], A[i+1], ..., A[m-1]
         * B[0], B[1], ..., B[j-1]  |  B[j], B[j+1], ..., B[n-1]
         *
         *
         * 如果我们能达成这两个条件：
         *
         * 1) len(left_part) == len(right_part)
         * 2) max(left_part) <= min(right_part)
         *
         * 我们就能将 {A, B} 中所有元素分成两个长度相等的部分，并且其中一个部分总是大于另外一个部分。那么中位数就是 median = (max(left_part) + min(right_part))/2。
         *
         *
         * 为了达成这两个条件，我们只需要确保：
         *
         * (1) i + j == m - i + n - j (或者: m - i + n - j + 1) 即让左半边元素数量等于与右半边
         *     对于 n >= m 的情况，我们只需要让 : i = 0 ~ m, j = (m + n + 1)/2 - i
         * (2) B[j-1] <= A[i] 并且 A[i-1] <= B[j]  即让左边最大元素小于右边最小元素
         */
        public double findMedianSortedArrays1(int[] nums1, int[] nums2) {
            if (nums1.length > nums2.length) return findMedianSortedArrays1(nums2, nums1);
            int len = nums1.length + nums2.length;
            int cut1 = 0, cut2 = 0, cutL = 0, cutR = nums1.length;
            // 在num1里进行binary search, 找到合适的分割点（找到了nums1的分割点后nums2的分割点自然就确定了，为(len(nums2) + len(nums1)) / 2 - cut1）
            while (cut1 <= nums1.length) {
                cut1 = (cutR - cutL) / 2 + cutL;
                cut2 = len / 2 - cut1;
                double L1 = (cut1 == 0) ? Integer.MIN_VALUE : nums1[cut1 - 1];
                double L2 = (cut2 == 0) ? Integer.MIN_VALUE : nums2[cut2 - 1];
                double R1 = (cut1 == nums1.length) ? Integer.MAX_VALUE : nums1[cut1];
                double R2 = (cut2 == nums2.length) ? Integer.MAX_VALUE : nums2[cut2];
                if (L1 > R2) {
                    cutR = cut1 - 1;
                } else if (L2 > R1) {
                    cutL = cut1 + 1;
                } else {
                    if (len % 2 == 0) {
                        L1 = L1 > L2 ? L1 : L2;
                        R1 = R1 < R2 ? R1 : R2;
                        return (L1 + R1) / 2;
                    } else {
                        R1 = (R1 < R2) ? R1: R2;
                        return R1;
                    }
                }
            }
            return -1;
        }
    }

    // 981: Time Based Key-Value Store
    /**
     * Design a time-based key-value data structure that can store multiple values for the same key at different time
     * stamps and retrieve the key's value at a certain timestamp.
     *
     * Implement the TimeMap class:
     *
     * TimeMap() Initializes the object of the data structure.
     * void set(String key, String value, int timestamp) Stores the key key with the value value at the given time timestamp.
     * String get(String key, int timestamp) Returns a value such that set was called previously,
     * with timestamp_prev <= timestamp. If there are multiple such values, it returns the value associated with the
     * largest timestamp_prev. If there are no values, it returns "".
     *
     *
     * Example 1:
     *
     * Input
     * ["TimeMap", "set", "get", "get", "set", "get", "get"]
     * [[], ["foo", "bar", 1], ["foo", 1], ["foo", 3], ["foo", "bar2", 4], ["foo", 4], ["foo", 5]]
     * Output
     * [null, null, "bar", "bar", null, "bar2", "bar2"]
     *
     * Explanation
     * TimeMap timeMap = new TimeMap();
     * timeMap.set("foo", "bar", 1);  // store the key "foo" and value "bar" along with timestamp = 1.
     * timeMap.get("foo", 1);         // return "bar"
     * timeMap.get("foo", 3);         // return "bar", since there is no value corresponding to foo at timestamp 3 and timestamp 2, then the only value is at timestamp 1 is "bar".
     * timeMap.set("foo", "bar2", 4); // store the key "foo" and value "bar2" along with timestamp = 4.
     * timeMap.get("foo", 4);         // return "bar2"
     * timeMap.get("foo", 5);         // return "bar2"
     */
    class TimeMap {
        HashMap<String, TreeMap<Integer, String>> keyTimeMap;

        public TimeMap() {
            keyTimeMap = new HashMap<String, TreeMap<Integer, String>>();
        }

        public void set(String key, String value, int timestamp) {
            if (!keyTimeMap.containsKey(key)) {
                keyTimeMap.put(key, new TreeMap<Integer, String>());
            }
            // Store '(timestamp, value)' pair in 'key' bucket.
            keyTimeMap.get(key).put(timestamp, value);
        }

        public String get(String key, int timestamp) {
            // If the 'key' does not exist in map we will return empty string.
            if (!keyTimeMap.containsKey(key)) {
                return "";
            }
            Integer floorKey = keyTimeMap.get(key).floorKey(timestamp);
            // Return searched time's value, if exists.
            if (floorKey != null) {
                return keyTimeMap.get(key).get(floorKey);
            }
            return "";
        }
    }

/**
 * Your TimeMap object will be instantiated and called as such:
 * TimeMap obj = new TimeMap();
 * obj.set(key,value,timestamp);
 * String param_2 = obj.get(key,timestamp);
 */


}

