package com.company.Notes;

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

}

