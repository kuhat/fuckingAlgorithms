package com.company.Notes;

import java.util.Arrays;

public class Note16_Greedy {
    // 55: Jump Game
    /**
     * You are given an integer array nums. You are initially positioned at the array's first index, and each element
     * in the array represents your maximum jump length at that position.
     *
     * Return true if you can reach the last index, or false otherwise.
     *
     * Example 1:
     *
     * Input: nums = [2,3,1,1,4]
     * Output: true
     * Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
     * Example 2:
     *
     * Input: nums = [3,2,1,0,4]
     * Output: false
     * Explanation: You will always arrive at index 3 no matter what. Its maximum jump length is 0, which makes it impossible to reach the last index.
     */

    class Solution55 {
        // 253 扫描线或贪心
        public boolean canJump(int[] nums) {
            int max = 0;
            for (int i = 0; i < nums.length; i++) {
                if (i > max) return false;  // 如果i大于当前可以跳的最远距离，说明不能到达最后一个元素
                max = Math.max(nums[i] + i, max);  // 当前可以跳的最远距离
            }
            return true;
        }
    }

    // 45 Jump Game II
    /**
     * Given an array of non-negative integers nums, you are initially positioned at the first index of the array.
     *
     * Each element in the array represents your maximum jump length at that position.
     *
     * Your goal is to reach the last index in the minimum number of jumps.
     *
     * You can assume that you can always reach the last index.
     *
     *
     *
     * Example 1:
     *
     * Input: nums = [2,3,1,1,4]
     * Output: 2
     * Explanation: The minimum number of jumps to reach the last index is 2. Jump 1 step from index 0 to 1, then 3 steps to the last index.
     * Example 2:
     *
     * Input: nums = [2,3,0,1,4]
     * Output: 2
     */
    class Solution {

        /**
         *          [2, 3, 1, 1, 4]
         *           0, 1, 2, 3, 4
         *  maxNext: 2  4  3
         *      res: 1  1  2
         *  maxArea: 2  2  3
         *
         *
         * @param nums
         * @return
         */

        public int jump(int[] nums) {
            if (nums == null || nums.length == 0) return 0;
            int res = 0, maxArea = 0, maxNext = 0;
            for (int i = 0 ; i < nums.length - 1; i++) {
                maxNext = Math.max(nums[i] + i, maxNext);
                if (i == maxArea){
                    res++;
                    maxArea = maxNext;
                }
            }
            return res;
        }
    }

    // 452： Minimum number of arrows to burst balloons
    /**
     * There are some spherical balloons taped onto a flat wall that represents the XY-plane. The balloons are represented
     * as a 2D integer array points where points[i] = [xstart, xend] denotes a balloon whose horizontal diameter stretches
     * between xstart and xend. You do not know the exact y-coordinates of the balloons.
     *
     * Arrows can be shot up directly vertically (in the positive y-direction) from different points along the x-axis.
     * A balloon with xstart and xend is burst by an arrow shot at x if xstart <= x <= xend. There is no limit to the
     * number of arrows that can be shot. A shot arrow keeps traveling up infinitely, bursting any balloons in its path.
     *
     * Given the array points, return the minimum number of arrows that must be shot to burst all balloons.
     *
     *
     *
     * Example 1:
     *
     * Input: points = [[10,16],[2,8],[1,6],[7,12]]
     * Output: 2
     * Explanation: The balloons can be burst by 2 arrows:
     * - Shoot an arrow at x = 6, bursting the balloons [2,8] and [1,6].
     * - Shoot an arrow at x = 11, bursting the balloons [10,16] and [7,12].
     * Example 2:
     *
     * Input: points = [[1,2],[3,4],[5,6],[7,8]]
     * Output: 4
     * Explanation: One arrow needs to be shot for each balloon for a total of 4 arrows.
     * Example 3:
     *
     * Input: points = [[1,2],[2,3],[3,4],[4,5]]
     * Output: 2
     * Explanation: The balloons can be burst by 2 arrows:
     * - Shoot an arrow at x = 2, bursting the balloons [1,2] and [2,3].
     * - Shoot an arrow at x = 4, bursting the balloons [3,4] and [4,5].
     */
    class Solution452 {
        public int findMinArrowShots(int[][] points) {
            if(points == null || points.length == 0) return 0;
            int res = 1;
            Arrays.sort(points, (a,b) -> {
                if (a[1] < b[1]) {
                    return -1;
                } else if (a[1] > b[1]) {
                    return 1;
                } else return 0;
            });
            int end = points[0][1];  // 第一个气球的末尾
            for (int i = 1; i < points.length; i++) {
                if (points[i][0] > end) {
                    res++;  // 如果下一个点的起始大于上一个的结束，res++
                    end = points[i][1]; // 更新end为下一个气球的end
                } else {
                    end = Math.min(end, points[i][1]);
                }
            }
            return res;
        }
    }

    // 135: candies
    /**
     * There are n children standing in a line. Each child is assigned a rating value given in the integer array ratings.
     *
     * You are giving candies to these children subjected to the following requirements:
     *
     * Each child must have at least one candy.
     * Children with a higher rating get more candies than their neighbors.
     * Return the minimum number of candies you need to have to distribute the candies to the children.
     *
     *
     *
     * Example 1:
     *
     * Input: ratings = [1,0,2]
     * Output: 5
     * Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.
     * Example 2:
     *
     * Input: ratings = [1,2,2]
     * Output: 4
     * Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
     * The third child gets 1 candy because it satisfies the above two conditions.
     */
    class Solution135 {
        public int candy(int[] ratings) {
            if (ratings == null || ratings.length == 0) return 0;
            int[] candies = new int[ratings.length];
            Arrays.fill(candies, 1);
            // 从左到右，如果右边一个数大于左边的那个数，就等于左的数加一
            for (int i = 1; i < ratings.length; i++) {
                if (ratings[i] > ratings[i - 1]) {
                    candies[i] = candies[i - 1] + 1;
                }
            }
            // 从右往左，如果左边的数大于右边的数，左边的数的值就等于右边的数加一和它本身取最大值
            for (int i = ratings.length - 2; i  >= 0; i--) {
                if (ratings[i] > ratings[i + 1]) {
                    candies[i] = Math.max(candies[i], candies[i + 1] + 1);
                }
            }
            int res= 0;
            for (int i : candies) {
                res += i;
            }
            return res;

        }
    }
}
