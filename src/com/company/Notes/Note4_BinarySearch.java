package com.company.Notes;

public class Note4_BinarySearch {

    // 递归二分查找 time: O(logn) space: O(logn)
    public static int binarySearch(int[] nums, int low, int high, int target){
        if (high <= low) return -1;
        int mid = low + (high - low)/2;
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
     *
     * You must write an algorithm with O(log n) runtime complexity.
     *
     * Input: nums = [1,3,5,6], target = 5
     * Output: 2
     *
     * Input: nums = [1,3,5,6], target = 2
     * Output: 1
     *
     * Input: nums = [1,3,5,6], target = 7
     * Output: 4
     *
     * @param target
     */

    public int serachInsert(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while(left <= right) {
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
     *
     * Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one, which causes all the following ones to be bad.
     *
     * You are given an API bool isBadVersion(version) which returns whether version is bad. Implement a function to
     * find the first bad version. You should minimize the number of calls to the API.
     *
     * Input: n = 5, bad = 4
     * Output: 4
     * Explanation:
     * call isBadVersion(3) -> false
     * call isBadVersion(5) -> true
     * call isBadVersion(4) -> true
     * Then 4 is the first bad version.
     *
     * Input: n = 1, bad = 1
     * Output: 1
     *
     * @param n
     */

    public int firstBadVersion(int n) {
        int start = 1, end = n;

        while(start < end) {
            int mid = (end - start) / 2 + start;
            if (isBadVersion(mid)) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }

    // 33: Search in Rotated Sorted Array

    /**
     *
     * There is an integer array nums sorted in ascending order (with distinct values).
     *
     * Prior to being passed to your function, nums is possibly rotated at an unknown pivot index k (1 <= k < nums.length)
     * such that the resulting array is [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed).
     * For example, [0,1,2,4,5,6,7] might be rotated at pivot index 3 and become [4,5,6,7,0,1,2].
     *
     * Given the array nums after the possible rotation and an integer target, return the index of target if
     * it is in nums, or -1 if it is not in nums.
     *
     * You must write an algorithm with O(log n) runtime complexity.
     *
     * Input: nums = [4,5,6,7,0,1,2], target = 0
     * Output: 4
     *
     * Input: nums = [4,5,6,7,0,1,2], target = 3
     * Output: -1
     *
     * Input: nums = [1], target = 0
     * Output: -1
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
        while(start <= end) {
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

    // private function given in the question that can check the version of the product
    private boolean isBadVersion(int n) {
        return false;
    }

}

