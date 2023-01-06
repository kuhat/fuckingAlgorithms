package com.company.leetCode;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

public class dp {

    /**
     * 至少有K个重复字符的最长子串
     * <p>
     * 递归最基本的是记住递归函数的含义（务必牢记函数定义）：本题的 longestSubstring(s, k) 函数表示的就是题意，即求一个最长的子字符串的长度，
     * 该子字符串中每个字符出现的次数都最少为 kk。函数入参 ss 是表示源字符串；kk 是限制条件，即子字符串中每个字符最少出现的次数；函数返回结果是满足题意的最长子字符串长度。
     * <p>
     * 递归的终止条件（能直接写出的最简单 case）：如果字符串 ss 的长度少于 kk，那么一定不存在满足题意的子字符串，返回 0；
     * <p>
     * 调用递归（重点）：如果一个字符 cc 在 ss 中出现的次数少于 kk 次，那么 ss 中所有的包含 cc 的子字符串都不能满足题意。所以，应该在 ss 的所有不包含
     * cc 的子字符串中继续寻找结果：把 ss 按照 cc 分割（分割后每个子串都不包含 cc），得到很多子字符串 tt；下一步要求 tt 作为源字符串的时候，
     * 它的最长的满足题意的子字符串长度（到现在为止，我们把大问题分割为了小问题(ss → tt)）。此时我们发现，恰好已经定义了函数 longestSubstring(s, k)
     * 就是来解决这个问题的！所以直接把 longestSubstring(s, k) 函数拿来用，于是形成了递归。
     * <p>
     * 未进入递归时的返回结果：如果 ss 中的每个字符出现的次数都大于 kk 次，那么 ss 就是我们要求的字符串，直接返回该字符串的长度。
     */
    class solutionLongestSubstring {
        public int longestSubstring(String s, int k) {
            if (s.length() < k) {
                return 0;
            }
            // 找到出现次数最少的字符 smallest.
            HashMap<Character, Integer> counter = new HashMap<Character, Integer>();
            for (int i = 0; i < s.length(); i++) {
                counter.put(s.charAt(i), counter.getOrDefault(s.charAt(0), 0) + 1);
            }
            int value = Integer.MAX_VALUE;
            char smallest = ' ';
            for (char c : counter.keySet()) {
                if (counter.get(c) < value) {
                    value = counter.get(c);
                    smallest = c;
                }
            }
            // 如果出现次数最少的字符都小于k了，直接返回母串的长度
            if (value < k) {
                return s.length();
            }
            // 把母串去除出现次数最少的字符，递归
            int res = 0;
            for (String a : s.split(String.valueOf(smallest))) {
                res = Math.max(res, longestSubstring(a, k));
            }
            return res;
        }
    }

    /**
     * 打家劫舍
     * <p>
     * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
     * <p>
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
     * <p>
     * 输入：[1,2,3,1]
     * 输出：4
     * 解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
     * 偷窃到的最高金额 = 1 + 3 = 4 。
     * <p>
     * 输入：[2,7,9,3,1]
     * 输出：12
     * 解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
     * 偷窃到的最高金额 = 2 + 9 + 1 = 12 。
     */

    class SolutionGrab {
        @Test
        public int rob(int[] nums) {
            if (nums.length == 0) return 0;
            if (nums.length == 1) return nums[0];
            int firstNum = nums[0];
            int secondNum = Math.max(nums[0], nums[1]);
            for (int i = 2; i < nums.length; i++) {
                int tmp = secondNum;
                secondNum = Math.max(secondNum, nums[i] + firstNum);
                firstNum = tmp;
            }
            return secondNum;
        }
    }

    /**
     * 最长上升子序列
     *
     * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
     *
     * 子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
     *
     * 输入：nums = [10,9,2,5,3,7,101,18]
     * 输出：4
     * 解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
     *
     * 输入：nums = [0,1,0,3,2,3]
     * 输出：4
     */

    class longestOfLIS {
        public int lengthOfLIS(int[] nums) {
            if (nums.length == 0) return 0;
            int[] dp = new int[nums.length];
            Arrays.fill(dp, 1);
            int res = 0;
            for (int i = 0; i < nums.length; i ++) {
                for (int j = 0; j < i; j ++) {
                    if (nums[i] < nums[j]) dp[i] = Math.max(dp[i], dp[j] + 1);
                }
                res = Math.max(res, dp[i]);
            }
            return res;
        }
    }


}
