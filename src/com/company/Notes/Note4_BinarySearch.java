package com.company.Notes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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


}

