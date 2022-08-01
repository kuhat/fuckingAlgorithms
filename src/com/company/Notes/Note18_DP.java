package com.company.Notes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Note18_DP {
    // 509 Fibonacci Number

    /**
     * The Fibonacci numbers, commonly denoted F(n) form a sequence, called the Fibonacci sequence, such that each
     * number is the sum of the two preceding ones, starting from 0 and 1. That is,
     * <p>
     * F(0) = 0, F(1) = 1
     * F(n) = F(n - 1) + F(n - 2), for n > 1.
     * Given n, calculate F(n).
     * <p>
     * <p>
     * <p>
     * Example 1:
     * <p>
     * Input: n = 2
     * Output: 1
     * Explanation: F(2) = F(1) + F(0) = 1 + 0 = 1.
     */
    class Solution509 {
        // 递归：大量重复计算
        public int fib(int n) {
            if (n <= 1) return n;
            return fib(n - 1) + fib(n - 2);
        }

        // 记忆化搜索
        int[] memorization = new int[15464691];  // 15464691是 java中数组开辟最大值

        public int fib2(int N) {
            if (N <= 1) return N;
            if (memorization[N] == 0) memorization[N] = fib2(N - 1) + fib2(N - 2);
            return memorization[N];
        }

        public int fib3(int N) {
            if (N <= 1) return N;
            int[] dp = new int[N + 1];
            dp[1] = 1;
            for (int i = 2; i <= N; i++) {
                dp[i] = dp[i - 1] + dp[i - 2];
            }
            return dp[N];
        }

        public int fib4(int N) {
            if (N <= 1) return N;
            int a = 0, b = 1;
            while (N-- > 1) {
                int sum = a + b;
                a = b;
                b = sum;
            }
            return b;
        }
    }

    // 139: word Break

    /**
     * Given a string s and a dictionary of strings wordDict, return true if s can be segmented into a space-separated sequence of one or more dictionary words.
     * <p>
     * Note that the same word in the dictionary may be reused multiple times in the segmentation.
     * <p>
     * <p>
     * <p>
     * Example 1:
     * <p>
     * Input: s = "leetcode", wordDict = ["leet","code"]
     * Output: true
     * Explanation: Return true because "leetcode" can be segmented as "leet code".
     */

    class Solution {
        public boolean wordBreak(String s, List<String> wordDict) {
            int n = s.length();
            boolean[] dp = new boolean[n + 1];
            dp[0] = true;  // 初始化空数组为true
            for (int j = 1; j <= n; j++) {  // 从底向上看
                for (String word : wordDict) {
                    int len = word.length();
                    if (len <= j && word.equals(s.substring(j - len, j))) {
                        dp[j] = dp[j] || dp[j - len];
                    }
                }
            }
            return dp[n];
        }
    }

    public boolean wordBreakDP1(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];  // 初始化为false
        dp[0] = true;
        for (int i = 1; i < s.length(); i++) {
            for (int j = 0; j < i; j++) {  // 从头开始看wordDict里面有没有，如果前面j的为true，后面j到i再dict里面找得到就
                if (dp[j] && wordDict.contains(s.substring(j, i))) {    // 把i前面的改为true
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

    // Backtracking
    public boolean wordBreak(String s, List<String> wordDict) {
        return helper(s, new HashSet<>(wordDict), 0);
    }

    private boolean helper(String s, HashSet<String> wordDict, int index) {
        if (index == s.length()) return true;
        for (int i = index + 1; i <= s.length(); i++) {
            if (wordDict.contains(s.substring(index, i)) && helper(s, wordDict, i)) return true;
        }
        return false;
    }

    // Memorization
    public boolean wordBreak1(String s, List<String> wordDict) {
        return helper1(s, new HashSet<>(wordDict), 0, new Boolean[s.length()]);
    }

    private boolean helper1(String s, HashSet<String> wordDict, int index, Boolean[] memo) {
        if (index == s.length()) return true;
        if (memo[index] != null) return memo[index];
        for (int i = index + 1; i <= s.length(); i++) {
            if (wordDict.contains(s.substring(index, i)) && helper1(s, wordDict, i, memo)) {
                memo[index] = true;
                return true;
            }
        }
        memo[index] = false;
        return false;
    }

    // 300: Longest Increasing Subsequence

    /**
     * Given an integer array nums, return the length of the longest strictly increasing subsequence.
     * <p>
     * A subsequence is a sequence that can be derived from an array by deleting some or no elements without
     * changing the order of the remaining elements. For example, [3,6,2,7] is a subsequence of the array [0,3,1,6,2,2,7].
     * <p>
     * Example 1:
     * <p>
     * Input: nums = [10,9,2,5,3,7,101,18]
     * Output: 4
     * Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
     */
    class Solution300 {
        public int lengthOfLTS(int[] nums) {
            if (nums == null || nums.length == 0) return 0;
            int[] dp = new int[nums.length];
            Arrays.fill(dp,1);
            for (int i = 1; i < nums.length; i++) {
                for (int j = 0; j < i; j++) {
                    if (nums[i] > nums[j]) dp[i] = Math.max(dp[i], dp[j] + 1);
                    // 方程：f(i) = max(1 + LIS(j)) if nums(i) > nums(j), j = 1, 2... i-1
                }
            }
            int res = dp[0];
            for (int i: dp) {
                res = Math.max(res, i);
            }
            return res;
        }
    }

}
