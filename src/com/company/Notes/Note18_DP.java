package com.company.Notes;

import java.util.ArrayList;
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
            Arrays.fill(dp, 1);
            for (int i = 1; i < nums.length; i++) {
                for (int j = 0; j < i; j++) {
                    if (nums[i] > nums[j]) dp[i] = Math.max(dp[i], dp[j] + 1);
                    // 方程：f(i) = max(1 + LIS(j)) if nums(i) > nums(j), j = 1, 2... i-1
                }
            }
            int res = dp[0];
            for (int i : dp) {
                res = Math.max(res, i);
            }
            return res;
        }
    }

    // 背包问题重要！！
    class knapsack {

        /**
         * @param weight 物品的重量数组
         * @param value  物品的价值数组
         * @param C      背包的容量
         * @param N      物品的个数
         */
        public int knapsack01(int[] weight, int[] value, int C, int N) {
            int[][] memo = new int[N][C + 1];
            for (int i = 0; i <= C; i++) {
                // 首先初始化第一排(第一个物品)，如果背包的容量容得下第一个物品，就更新value为第一个物品的价值
                memo[0][i] = (i >= weight[0] ? value[0] : 0);
            }
            // 遍历接下来的行数（物品），列数（背包容量）
            for (int i = 1; i < N; i++) {
                for (int j = 0; j <= C; j++) {
                    // 如果背包的容量小于当前物品的重量，当前的价值就是之前(上一行)的那个值
                    if (j < weight[i]) memo[i][j] = memo[i - 1][j];
                        // 如果背包容量可以装下当前物品的重量，当前的价值就算不放这个物品的价值和放这个物品加上之前的物品的价值之中的最大值
                    else
                        memo[i][j] = Math.max(memo[i - 1][j], value[i] + memo[i - 1][j - weight[i]]);  // j-weight[i]是指减去上一个物品的重量
                }
            }
            return memo[N - 1][C];
        }

        // 升级版

        /**
         * 因为每次更新只涉及到了两行的值的改变，我们可以省区很多步骤
         * 初始化memo数组可以只用两行
         */
        public int knapsack02(int[] weight, int[] value, int C, int N) {
            int[][] memo = new int[2][C + 1];   // 每行的更新只与上一行有关系，只需要两行数组
            for (int i = 0; i <= C; i++) {
                // 首先初始化第一排(第一个物品)，如果背包的容量容得下第一个物品，就更新value为第一个物品的价值
                memo[0][i] = (i >= weight[0] ? value[0] : 0);
            }
            // 遍历接下来的行数（物品），列数（背包容量）
            for (int i = 1; i < N; i++) {
                for (int j = 0; j <= C; j++) {
                    // 如果背包的容量小于当前物品的重量，当前的价值就是之前(上一行)的那个值
                    if (j < weight[i]) memo[i % 2][j] = memo[(i - 1) % 2][j];
                        // 如果背包容量可以装下当前物品的重量，当前的价值就算不放这个物品的价值和放这个物品加上之前的物品的价值之中的最大值
                    else
                        memo[i % 2][j] = Math.max(memo[(i - 1) % 2][j], value[i] + memo[(i - 1) % 2][j - weight[i]]);  // j-weight[i]是指减去上一个物品的重量
                }
            }
            return memo[(N - 1) % 2][C];
        }

        // 优化成一个一维数组
        public int knapsack03(int[] weight, int[] value, int C, int N) {
            int[] memo = new int[C + 1];   // 只用一维数组
            for (int i = 0; i <= C; i++) {
                // 首先初始化第一排(第一个物品)，如果背包的容量容得下第一个物品，就更新value为第一个物品的价值
                memo[i] = (i >= weight[0] ? value[0] : 0);
            }
            // 遍历接下来物品，列数（背包容量）
            for (int i = 1; i < N; i++) {
                for (int j = C; j >= weight[i]; j--) {  // 从后往前遍历，当容量C大于当前物品的重量时就可以进行加法的操作，容量C小于时就不管
                    memo[j] = Math.max(memo[j], value[i] + memo[j - weight[i]]);
                }
            }
            return memo[C];
        }

        // 打印过程
        public int knapsack04(int[] weight, int[] value, int C, int N) {
            int[][] memo = new int[N + 1][C + 1];  //  干掉初始化的第一排

            String[][] path = new String[N + 1][C + 1];

            // 遍历接下来的行数（物品），列数（背包容量）
            for (int i = 1; i <= N; i++) {
                for (int j = 0; j <= C; j++) {
                    // 如果背包的容量小于当前物品的重量，当前的价值就是之前(上一行)的那个值
                    if (j < weight[i]) {
                        memo[i][j] = memo[i - 1][j];
                        if (path[i - 1][j] != null) path[i][j] = path[i - 1][j];  // 如果之前没有添加到path里则加入
                    }
                    // 如果背包容量可以装下当前物品的重量，当前的价值就算不放这个物品的价值和放这个物品加上之前的物品的价值之中的最大值
                    else {
                        // memo[i][j] = Math.max(memo[i - 1][j], value[i] + memo[i - 1][j - weight[i]]);
                        if (memo[i - 1][j] > value[i - 1] + memo[i - 1][j - weight[i - 1]]) {
                            memo[i][j] = memo[i - 1][j];
                            if (path[i - 1][j] != null) {
                                path[i][j] = path[i - 1][j];  // 添加到path里
                            }
                        } else {
                            memo[i][j] = value[i - 1] + memo[i - 1][j - weight[i - 1]];
                            if (path[i - 1][j - weight[i - 1]] == null) path[i][j] = i + "";
                            else path[i][j] = path[i - 1][j - weight[i - 1]] + " " + i;
                        }
                    }
                }
            }
            for (String[] s : path) {
                System.out.println(Arrays.toString(s));
            }
            return memo[N - 1][C];
        }
    }

    // 多重背包

    /**
     * 每个背包可以放多个
     */

    class MultipleNnapsack {
        /**
         * @param weight 物品的重量数组
         * @param value  物品的价值数组
         * @param nums   每个物品可以放多少个
         * @param C      背包的大小
         * @param N      物品的个数
         * @return ee
         */
        public int multipleKnaosack(int[] weight, int[] value, int[] nums, int C, int N) {
            int[] memo = new int[C + 1];
            for (int i = 0; i < N; i++) {  // 每个物品遍历
                for (int j = C; j >= weight[i]; j--) {  // 从后往前遍历，当背包的大小可以容得下当前的物品的重量时
                    for (int k = 0; k <= nums[i]; k++) {  // 将同一个物品一个一个的装进背包
                        if (j - k * weight[i] >= 0) {  // 如果背包的大小减去相同物体的重量的总和大于0
                            memo[j] = Math.max(memo[j], k * value[i] + memo[j - k * weight[i]]);  // 可以放进背包，当前的值就是不放这个物体的值和放了这个物体的值加上原来的值的最大值
                        }
                    }
                }
            }
            return memo[C];
        }
    }


    // 5. Longest Palindromic Substring

    /**
     * Given a string s, return the longest palindromic substring in s.
     * <p>
     * Example 1:
     * <p>
     * Input: s = "babad"
     * Output: "bab"
     * Explanation: "aba" is also a valid answer.
     * Example 2:
     * <p>
     * Input: s = "cbbd"
     * Output: "bb"
     */
    class Solution5 {

        public String longestPalindromeDP(String s) {
            if (s == null || s.length() == 0) return s;
            String res = "";
            int max = 0;
            boolean[][] dp = new boolean[s.length()][s.length()];  // 起点到中点是否为palindrome，是否为最大的palindrome
            for (int j = 0; j < s.length(); j++) {
                for (int i = 0; i <= j; i++) {
                    dp[i][j] = s.charAt(i) == s.charAt(j) && ((j - i <= 2) || dp[i + 1][j - 1]);
                    //         两端的字母相同                   只剩下三个字母      中间剩下的字母，i向右走一个和j向左走一个相同
                    if (dp[i][j]) {
                        if (j - i + 1 > max) {  // 更新max值
                            max = j - i + 1;
                            res = s.substring(i, j + 1);
                        }
                    }
                }
            }
            return res;
        }

        public String longestPalindrome(String s) {
            String res = "";
            for (int l = 0; l < s.length(); l++) {
                for (int i = l; i < s.length(); i++) {
                    if (s.charAt(l) != s.charAt(i)) continue;
                    String tmp = s.substring(l, i + 1);
                    int n = tmp.length(), flag = 1;
                    for (int j = 0; j < n / 2; j++) {
                        if (tmp.charAt(j) != tmp.charAt(n - 1 - j)) {
                            flag = 0;
                            break;
                        }
                    }
                    if (flag == 1) res = tmp.length() > res.length() ? tmp : res;
                }
                if (res.length() > s.length() - l) return res;
            }
            return res;
        }
    }

    // LeetCode62: unique path

    /**
     * There is a robot on an m x n grid. The robot is initially located at the top-left corner (i.e., grid[0][0]).
     * The robot tries to move to the bottom-right corner (i.e., grid[m - 1][n - 1]). The robot can only move either down or right at any point in time.
     * <p>
     * Given the two integers m and n, return the number of possible unique paths that the robot can take to reach the bottom-right corner.
     * <p>
     * The test cases are generated so that the answer will be less than or equal to 2 * 109.
     * <p>
     * Input: m = 3, n = 2
     * Output: 3
     * Explanation: From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
     * 1. Right -> Down -> Down
     * 2. Down -> Down -> Right
     * 3. Down -> Right -> Down
     */

    class Solution62 {
        public int uniquePaths(int m, int n) {
            int[][] dp = new int[m][n];
            // 一直往前或者往下都是一种
            for (int i = 0; i < m; i++) {
                dp[i][0] = 1;
            }
            for (int i = 0; i < n; i++) {
                dp[0][i] = 1;
            }

            // 走到一个位置时，当前的路线种数是上面的种类数量加上左边的种类数量之和
            for (int i = 1; i < m; i++) {
                for (int j = 1; j < n; j++) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
            return dp[m - 1][n - 1];
        }

        // 当前行的某一位置的值只跟此行和上一行有关，可以只用一维数组 空间：O（n）
        public int uniquePaths1(int m, int n) {
            int[] res = new int[n];  // 只用一维数组
            res[0] = 1;
            for (int i = 0; i < m; i++) {
                for (int j = 1; j < n; j++) {
                    res[j] = res[j] + res[j - 1];  // 这里的意思是当前格子的值等于上一行的对应值加上此行此格的左边一个的值
                }
            }
            return res[n - 1];
        }
    }

    // 63: unique path 2
    /**
     * You are given an m x n integer array grid. There is a robot initially located at the top-left corner (i.e., grid[0][0]).
     * The robot tries to move to the bottom-right corner (i.e., grid[m-1][n-1]). The robot can only move either down or right at any point in time.
     *
     * An obstacle and space are marked as 1 or 0 respectively in grid. A path that the robot takes cannot include any square that is an obstacle.
     *
     * Return the number of possible unique paths that the robot can take to reach the bottom-right corner.
     *
     * Input: obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
     * Output: 2
     * Explanation: There is one obstacle in the middle of the 3x3 grid above.
     * There are two ways to reach the bottom-right corner:
     * 1. Right -> Right -> Down -> Down
     * 2. Down -> Down -> Right -> Right
     */
    class Solution63{
        public int uniquePathsWithObstacles(int[][] obstacleGrid) {
            int length = obstacleGrid[0].length;
            int[] res = new int[length];
            res[0] = 1;
            for (int i = 0; i < obstacleGrid.length; i++) { // 行
                for (int j = 0; j < obstacleGrid[0].length; j++) { // 列
                    if (obstacleGrid[i][j] == 1) res[j] = 0;  // 如果遇到障碍物就设为0
                    else if (j > 0) {
                        res[j] += res[j - 1];
                    }
                }
            }
            return res[length - 1];
        }
    }

    // 72: edit Distance 重要 基础题原型题

    /**
     * Given two strings word1 and word2, return the minimum number of operations required to convert word1 to word2.
     * <p>
     * You have the following three operations permitted on a word:
     * <p>
     * Insert a character
     * Delete a character
     * Replace a character
     * <p>
     * <p>
     * Example 1:
     * <p>
     * Input: word1 = "horse", word2 = "ros"
     * Output: 3
     * Explanation:
     * horse -> rorse (replace 'h' with 'r')
     * rorse -> rose (remove 'r')
     * rose -> ros (remove 'e')
     * Example 2:
     * <p>
     * Input: word1 = "intention", word2 = "execution"
     * Output: 5
     * Explanation:
     * intention -> inention (remove 't')
     * inention -> enention (replace 'i' with 'e')
     * enention -> exention (replace 'n' with 'x')
     * exention -> exection (replace 'n' with 'c')
     * exection -> execution (insert 'u')
     */
    class Solution72 {
        /**
         * dp[i][j] 表示：从字符串1的i位置转换到字符串2的j位置，所需的最少步数
         * <p>
         * 1，字符串种的字符相等：dp[i][j] = dp[i - 1][j - 1]
         * 2，字符串中点字符不等：
         * insert: dp[i][j] = dp[i][j - 1] + 1;
         * replace: dp[i][j] = dp[i - 1][j - 1] + 1;
         * delete: dp[i][j] = dp[i - 1][j] + 1;
         *     a b c d
         *   0 1 2 3 4
         * a 1 0 1 2 3
         * e 2 1 1 2 3
         * f 3 2 2 2 3
         *
         * @param word1
         * @param word2
         * @return
         */
        public int minDistance(String word1, String word2) {
            int len1 = word1.length();
            int len2 = word2.length();

            int[][] dp = new int[len1 + 1][len2 + 1]; // 二维矩阵，从0到len
            for (int i = 0; i <= len1; i++) {  // 第一行为0到len1
                dp[i][0] = i;
            }
            for (int i = 0; i <= len2; i++) {  // 第一列为0到len2
                dp[0][i] = i;
            }

            for (int i = 1; i <= len1; i++) {
                for (int j = 1; j <= len2; j++) {
                    // 如果两个字符串的i和j位置相等，当前的值等于左上的格子的值
                    if (word1.charAt(i - 1) == word2.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1];
                        // 如果不等，当前的值等于insert，replace, 和delete三个操作种的最小值 + 1
                    else dp[i][j] = Math.min(Math.min(dp[i][j - 1], dp[i - 1][j]), dp[i - 1][j - 1]) + 1;
                }
            }
            return dp[len1][len2];
        }
    }


    // 53： Maximum Subarray

    /**
     * Given an integer array nums, find the contiguous subarray (containing at
     * least one number) which has the largest sum and return its sum.
     * <p>
     * A subarray is a contiguous part of an array.
     * <p>
     * <p>
     * <p>
     * Example 1:
     * <p>
     * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
     * Output: 6
     * Explanation: [4,-1,2,1] has the largest sum = 6.
     * Example 2:
     * <p>
     * Input: nums = [1]
     * Output: 1
     * Example 3:
     * <p>
     * Input: nums = [5,4,-1,7,8]
     * Output: 23
     */
    class Solution53 {
        public int maxSubArray(int[] nums) {
            int[] dp = new int[nums.length];
            dp[0] = nums[0];
            int res = nums[0];
            for (int i = 1; i < nums.length; i++) {
                dp[i] = nums[i] + Math.max(0, dp[i - 1]);  //  当前的dp值等于nums[i]加上上一个dp的值，如果上一个值为负数就不加
                res = Math.max(dp[i], res);
            }
            return res;
        }

        // 升级版,相当于只保留dp的最后两个值
        public int maxSubArray1(int[] nums) {
            int sum = nums[0];
            int res = nums[0];
            for (int i = 1; i < nums.length; i++) {
                sum = Math.max(nums[i], nums[i] + sum);
                res = Math.max(sum, res);
            }
            return res;
        }
    }

    // 152： Maximum Product SUbArray

    /**
     * Given an integer array nums, find a contiguous non-empty subarray within the array that has the largest
     * product, and return the product.
     * <p>
     * The test cases are generated so that the answer will fit in a 32-bit integer.
     * <p>
     * A subarray is a contiguous subsequence of the array.
     * <p>
     * <p>
     * <p>
     * Example 1:
     * <p>
     * Input: nums = [2,3,-2,4]
     * Output: 6
     * Explanation: [2,3] has the largest product 6.
     * Example 2:
     * <p>
     * Input: nums = [-2,0,-1]
     * Output: 0
     * Explanation: The result cannot be 2, because [-2,-1] is not a subarray.
     */

    class Solution152 {
        public int maxProduct(int[] nums) {
            if (nums == null || nums.length == 0) return 0;
            int max = nums[0], min = nums[0], res = nums[0];
            for (int i = 1; i < nums.length; i++) {
                int temp = max;
                max = Math.max(Math.max(max * nums[i], min * nums[i]), nums[i]);
                min = Math.min(Math.min(min * nums[i], temp * nums[i]), nums[i]);
                res = Math.max(res, max);
            }
            return res;
        }
    }

    // 120 triangle

    /**
     * Given a triangle array, return the minimum path sum from top to bottom.
     * <p>
     * For each step, you may move to an adjacent number of the row below. More formally, if you are on index i
     * on the current row, you may move to either index i or index i + 1 on the next row.
     * <p>
     * Example 1:
     * <p>
     * Input: triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
     * Output: 11
     * Explanation: The triangle looks like:
     *    2
     *   3 4
     *  6 5 7
     * 4 1 8 3
     * The minimum path sum from top to bottom is 2 + 3 + 5 + 1 = 11 (underlined above).
     * Example 2:
     * <p>
     * Input: triangle = [[-10]]
     * Output: -10
     */

    class Solution120 {
        /**
         * 第i层的第j个元素的下一层i+1相邻的元素是j和j+1
         * 最后一层的元素的个数也是它的层数
         *
         * @param triangle
         * @return
         */
        public int minimumTotal(List<List<Integer>> triangle) {
            int[] res = new int[triangle.size() + 1];  // 没有+1最后一层会越界
            //从下往上走
            for (int i = triangle.size() - 1; i >= 0; i--) {
                for (int j = 0; j < triangle.get(i).size(); j++) {
                    res[j] = Math.min(res[j], res[j + 1])+ triangle.get(i).get(j);  // 上一层的第j个数字对应数字的最小值是这一层的第j和第j+1个
                }
            }
            return res[0];
        }
    }

    // 494 target Sum
    /**
     * You are given an integer array nums and an integer target.
     *
     * You want to build an expression out of nums by adding one of the symbols '+' and '-' before each integer in nums and then concatenate all the integers.
     *
     * For example, if nums = [2, 1], you can add a '+' before 2 and a '-' before 1 and concatenate them to build the expression "+2-1".
     * Return the number of different expressions that you can build, which evaluates to target.
     *
     * Example 1:
     *
     * Input: nums = [1,1,1,1,1], target = 3
     * Output: 5
     * Explanation: There are 5 ways to assign symbols to make the sum of nums be target 3.
     * -1 + 1 + 1 + 1 + 1 = 3
     * +1 - 1 + 1 + 1 + 1 = 3
     * +1 + 1 - 1 + 1 + 1 = 3
     * +1 + 1 + 1 - 1 + 1 = 3
     * +1 + 1 + 1 + 1 - 1 = 3
     * Example 2:
     *
     * Input: nums = [1], target = 1
     * Output: 1
     *
     */
    class Solution494 {
        int count = 0;
        public int findTargetSumWays(int[] nums, int target) {

            dfs(nums, target, 0, 0);
            return count;
        }

        private void dfs(int[] nums, int target, int sum, int index) {
            if (index == nums.length) {
                if (sum == target) count++;
                return;
            }
            dfs(nums, target, sum - nums[index], index + 1);
            dfs(nums, target, sum + nums[index], index + 1);
        }
    }

    // 70Climbing Stairs 很重要

    /**
     * You are climbing a staircase. It takes n steps to reach the top.
     *
     * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
     *
     * Example 1:
     *
     * Input: n = 2
     * Output: 2
     * Explanation: There are two ways to climb to the top.
     * 1. 1 step + 1 step
     * 2. 2 steps
     * Example 2:
     *
     * Input: n = 3
     * Output: 3
     * Explanation: There are three ways to climb to the top.
     * 1. 1 step + 1 step + 1 step
     * 2. 1 step + 2 steps
     * 3. 2 steps + 1 step
     */
    class solution70{
        public int climbStairs(int n) {
            if (n <= 2) return n;
            else return climbStairs(n - 1) + climbStairs(n - 2);
        }

        public int climbStairs2(int n) {
            if (n <= 1) return 1;
            int oneStep = 1, twoStep = 1, res = 0;
            for (int i = 2; i <= n; i++) {
                res = oneStep + twoStep;
                twoStep = oneStep;
                oneStep = res;
            }
            return res;
        }
    }

    // 115 Distinct Subsequences  重要，基础题

    /**
     *
     * Given two strings s and t, return the number of distinct subsequences of s which equals t.
     *
     * A string's subsequence is a new string formed from the original string by deleting some (can be none) of the
     * characters without disturbing the remaining characters' relative positions. (i.e., "ACE" is a subsequence of "ABCDE" while "AEC" is not).
     *
     * The test cases are generated so that the answer fits on a 32-bit signed integer.
     *
     * Example 1:
     *
     * Input: s = "rabbbit", t = "rabbit"
     * Output: 3
     * Explanation:
     * As shown below, there are 3 ways you can generate "rabbit" from S.
     * rabbbit
     * rabbbit
     * rabbbit
     */
    class Solution115{

        /**
         * S = b a b g b a g
         * t = b a g
         *
         *     0 1 2 3 4 5 6 7
         *       b a b g b a g
         * 0   1 1 1 1 1 1 1 1
         * 1 b 0 1 1 2 2 3 3 3
         * 2 a 0 0 1 1 1 1 4 4
         * 3 g 0 0 0 0 1 1 1 5
         *
         * 比如说 （6，2）这个位置
         * 当前要匹配b a, 已经走到了 b a b g b a
         * 为什么会得到这个4呢：
         * s的前面已经出现了三个 b，正如dp[i - 1][j - 1]的数值，代表可以组成三个b a了
         * 这时t出现了一个a, 前面已经有了一个a，也就是说前面已经可以组成一个b a了
         * 总的就是3+1=4
         *
         * 状态转移方程：
         * 如果字符串t的第i个字符和s的第j个字符一样
         * 当前的值就等于坐上方的那个值加上左边的值
         * dp[i][j] = dp[i - 1][j - 1] + dp[i][j - 1]
         * 如果不一样当前的值就等于左边的值
         * dp[i][j] = dp[i][j - 1]
         *
         * @param s String to provide subsequence choices
         * @param t String to be found in s
         */
        public int numDistinct(String s, String t) {
            int slen = s.length(), tlen = t.length();
            int[][] dp = new int[tlen + 1][slen + 1];

            // base case
            for (int j = 0; j <= slen; j ++) dp[0][j] = 1;

            for (int i = 1; i <= tlen; i++) {
                for (int j = 1; j <= slen; j++) {
                    // 递推公式
                    if (t.charAt(i - 1) == s.charAt(j - 1)) dp[i][j] = dp[i - 1][j -1] + dp[i][j - 1];
                    else dp[i][j] = dp[i][j - 1];
                }
            }
            return dp[tlen][slen];
        }
    }

    // 64 Minimum Path Sum

    /**
     * Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right,
     * which minimizes the sum of all numbers along its path.
     *
     * Note: You can only move either down or right at any point in time.
     *
     * Input: grid = [[1,3,1],[1,5,1],[4,2,1]]
     * Output: 7
     * Explanation: Because the path 1 → 3 → 1 → 1 → 1 minimizes the sum.
     */

    class Solution64 {
        public int minPathSum(int[][] grid) {
            int row = grid.length, col = grid[0].length;
            for (int i = 0; i < row ; i++) {
                for (int j = 0; j < col; j ++) {
                    if (i == 0 && j != 0) grid[i][j] += grid[i][j - 1];
                    if (i != 0 && j == 0) grid[i][j] += grid[i - 1][j];
                    if (i != 0 && j!= 0) grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
                }
            }
            return grid[row - 1][col- 1];
        }
    }

    // 174: Dungeon game

    /**
     * The demons had captured the princess and imprisoned her in the bottom-right corner of a dungeon. The dungeon
     * consists of m x n rooms laid out in a 2D grid. Our valiant knight was initially positioned in the top-left room
     * and must fight his way through dungeon to rescue the princess.
     *
     * The knight has an initial health point represented by a positive integer. If at any point his health point drops
     * to 0 or below, he dies immediately.
     *
     * Some of the rooms are guarded by demons (represented by negative integers), so the knight loses health upon
     * entering these rooms; other rooms are either empty (represented as 0) or contain magic orbs that
     * increase the knight's health (represented by positive integers).
     *
     * To reach the princess as quickly as possible, the knight decides to move only rightward or downward in each step.
     *
     * Return the knight's minimum initial health so that he can rescue the princess.
     *
     * Note that any room can contain threats or power-ups, even the first room the knight enters and the bottom-right room where the princess is imprisoned.
     *
     * Input: dungeon =
     * [[-2,-3,3],
     * [-5,-10,1],
     * [10,30,-5]]
     *
     * [[-2,-3, 2],
     *  [-5,-10,5],
     *  [1,  1, 6]]
     *
     * Output: 7
     * Explanation: The initial health of the knight must be at least 7 if he follows the optimal path: RIGHT-> RIGHT -> DOWN -> DOWN.
     *
     */

    class Solution174{

        // 我们知道最后一格的数值，骑士的血量知道就为最后一个的数值加一
        class Solution {
            public int calculateMinimumHP(int[][] dungeon) {
                if (dungeon == null || dungeon.length == 0 || dungeon[0].length == 0) return 0;
                int m = dungeon.length, n = dungeon[0].length;
                int[][] dp = new int[m][n];
                // 骑士的血量初始值为最后一格的数值加一，或者最后一格为正数的话就为1
                dp[m - 1][n - 1] = Math.max(1 - dungeon[m - 1][ n - 1], 1);
                // 最后一列初始化
                for (int i = m -2; i >= 0; i--) {
                    dp[i][n - 1] = Math.max(dp[i + 1][n - 1] - dungeon[i][n - 1], 1);  // 此格的值等于下一行的数值减去此格的值, 如果为负数，说明骑士的血为1就可以了
                }
                // 最后一行初始化
                for (int i = n - 2; i >= 0; i--) {
                    dp[m - 1][i] = Math.max(dp[m - 1][i + 1] - dungeon[m - 1][i], 1);
                }

                for (int i = m - 2; i >= 0;i --) {
                    for(int j = n - 2; j >= 0; j--) {
                        int down = Math.max(dp[i + 1][j] - dungeon[i][j], 1);
                        int right = Math.max(dp[i][j + 1] - dungeon[i][j], 1);
                        dp[i][j] = Math.min(down, right);  // 当前的值为向下走和向右走中的最小值
                    }
                }
                return dp[0][0];
            }
        }
    }

    // 221 Maximum Square

    /**
     * Given an m x n binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.
     *
     * Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
     * Output: 4
     *
     * Input: matrix = [["0","1"],["1","0"]]
     * Output: 1
     */
    class Solution221{
        /**
         * 判断一个格子是否为正方形需要判断左边上边斜左边是否同时为1
         * 
         * 1 0 1 0 0
         * 1 0 1 1 1
         * 1 1 1 1 1 
         * 1 0 0 1 0
         *
         * time: O(m * n)
         * space: O(m * n)
         *
         * @param matrix
         * @return
         */
        public int maximalSquare(char[][] matrix) {
            if (matrix.length == 0) return 0;
            int m = matrix.length, n = matrix[0].length;
            int res =0;
            int[][] dp = new int[m + 1][n + 1];
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (matrix[i - 1][j - 1]== '1') {  // 当前的格子为1，代表可能形成正方形。如果是0，肯定构成不了正方形
                        dp[i][j] = Math.min(Math.min(dp[i][j - 1], dp[i - 1][j - 1]), dp[i- 1][j]) + 1;  // 取左方上方和斜上方的最小值，然后再加一
                        res = Math.max(res, dp[i][j]);
                    }
                }
            }
            return res * res;
        }
    }

    // 91: Decode ways. 很重要，排名第一位 facebook高频
    /**
     *
     * A message containing letters from A-Z can be encoded into numbers using the following mapping:
     *
     * 'A' -> "1"
     * 'B' -> "2"
     * ...
     * 'Z' -> "26"
     * To decode an encoded message, all the digits must be grouped then mapped back into letters using the reverse of
     * the mapping above (there may be multiple ways). For example, "11106" can be mapped into:
     *
     * "AAJF" with the grouping (1 1 10 6)
     * "KJF" with the grouping (11 10 6)
     * Note that the grouping (1 11 06) is invalid because "06" cannot be mapped into 'F' since "6" is different from "06".
     *
     * Given a string s containing only digits, return the number of ways to decode it.
     *
     * The test cases are generated so that the answer fits in a 32-bit integer.
     *
     * Example 1:
     *
     * Input: s = "12"
     * Output: 2
     * Explanation: "12" could be decoded as "AB" (1 2) or "L" (12).
     * Example 2:
     *
     * Input: s = "226"
     * Output: 3
     * Explanation: "226" could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).
     * Example 3:
     *
     * Input: s = "06"
     * Output: 0
     * Explanation: "06" cannot be mapped to "F" because of the leading zero ("6" is different from "06").
     *
     */
    class Solution91{

        /*
        1231：
        first = 2, second = 12

        i = 2
        AB, L
        dp[0] = 1, dp[1] = 1, dp[2] = 2
        i = 3
        ABC, AW, LC
        first = 3, second = 23
        i = 4
        first = 1 second = 31
        Space O(n)
         */

        /**
         *
         * 如果s[i]不为0，则可以单独解码s[i]，由于求的是方案数，如果确定了第i个数字的翻译方式，那么解码前i个数字和解码前i - 1个数的方案数就是相同的，即f[i] = f[i - 1]。(s[]数组下标从1开始)
         * 将s[i]和s[i - 1]组合起来解码（ 组合的数字范围在10 ~ 26之间 ）。如果确定了第i个数和第i - 1个数的解码方式，那么解码前i个数字和解码前i - 2个数的方案数就是相同的，即f[i] = f[i - 2]。(s[]数组下标从1开始)
         *
         * @param s
         * @return
         */
        public int numDecodings(String s){
            if (s ==null || s.length() == 0) return 0;
            int len = s.length();
            int[] dp = new int[len + 1];  // 从1开始遍历
            dp[0] = 1;
            dp[1] = s.charAt(0) != '0' ? 1 : 0;
            for (int i = 2; i <= len; i++) {
                int first = Integer.valueOf(s.substring(i - 1, i));  // 截取第一个数字
                int second = Integer.valueOf(s.substring(i - 2, i));  // 截取连续的两个数字
                if (first >= 1 && first <= 9) {  // 只要不是0就可以组成一个字母，当前的组合就等于前一个字母的组合数
                    dp[i] += dp[i - 1];
                }
                if (second >= 10 && second <= 26) {  // 如果当前的值（两个连选的数）可以可以组成一个新的字母，就和之前两个的那一位相等
                    dp[i] += dp[i - 2];
                }
            }
            return dp[len];
        }
    }
    
   // 97 Interleaving String

    /**
     * Given strings s1, s2, and s3, find whether s3 is formed by an interleaving of s1 and s2.
     *
     * An interleaving of two strings s and t is a configuration where s and t are divided into n and m non-empty substrings respectively, such that:
     *
     * s = s1 + s2 + ... + sn
     * t = t1 + t2 + ... + tm
     * |n - m| <= 1
     * The interleaving is s1 + t1 + s2 + t2 + s3 + t3 + ... or t1 + s1 + t2 + s2 + t3 + s3 + ...
     * Note: a + b is the concatenation of strings a and b.
     *
     * Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
     * Output: true
     * Explanation: One way to obtain s3 is:
     * Split s1 into s1 = "aa" + "bc" + "c", and s2 into s2 = "dbbc" + "a".
     * Interleaving the two splits, we get "aa" + "dbbc" + "bc" + "a" + "c" = "aadbbcbcac".
     * Since s3 can be obtained by interleaving s1 and s2, we return true.
     *
     * Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
     * Output: false
     * Explanation: Notice how it is impossible to interleave s2 with any other string to obtain s3.
     */
    class Solution97{


        /**
         *
         * s1 = "aabcc"
         * s2 = "dbbca"
         *
         * s3 = "aadbbcbcac" return true
         *
         * [true, true, true, false, false, false]
         * [false, false, true, true, false, false]
         * [false, false true, true, true ,false]
         * [false, false ,true, false ,true ,true]
         * [false, false, true ,true, true false]
         * [false, false, false, false ,true ,true]
         */
        public boolean isInterLeave(String s1, String s2, String s3) {
            if (s1.length() + s2.length() != s3.length()) return false;
            boolean[][] dp = new boolean[s2.length() + 1][s1.length() + 1];
            dp[0][0] =true;
            // 第一行和第一列初始化
            for (int i = 1; i < dp.length; i++) {
                dp[i][0] = dp[i - 1][0] && (s2.charAt(i - 1) == s3.charAt(i - 1));  // s2的第i行的值为true当上一行为true和
            }
            for (int i = 1; i < dp[0].length; i++) {
                dp[0][i] = dp[0][i - 1] && (s1.charAt(i - 1)== s3.charAt(i - 1));
            }
            for (int i = 1; i < dp.length; i++) {
                for (int j = 1; j < dp[0].length; j++) {
                    // s1 的前 i - 1 个元素和 s2 的前 j 个元素可以交错组合成 s3[0 ： i + j - 1],并且 s1 的第 i 个元素等于 s3 的第 i + j - 1 个元素。
                    // s1 的前 i - 1 个元素和 s2 的前 j 个元素可以交错组合成 s3[0 : i + j - 1],并且 s2 的第 j 个元素等于 s3 的第 i + j - 1 个元素。
                    dp[i][j] = (dp[i - 1][j] && s2.charAt(i - 1) == s3.charAt(i + j - 1)
                             || dp[i][j - 1] && s1.charAt(j - 1) == s3.charAt(i + j - 1));
                }
            }
            return dp[s2.length()][s1.length()];
        }
   }

   // 583 Delete Operation for two Strings

    /**
     * Given two strings word1 and word2, return the minimum number of steps required to make word1 and word2 the same.
     *
     * In one step, you can delete exactly one character in either string.
     *
     * example 1:
     *
     * Input: word1 = "sea", word2 = "eat"
     * Output: 2
     * Explanation: You need one step to make "sea" to "ea" and another step to make "eat" to "ea".
     */
    class Solution583{
        /**
         *     s e a
         *   0 1 2 3
         * e 1 2 1 2
         * a 2 3 2 1
         * t 3 4 3 2
         *
         * 两个位置的字母一样时即s.charAt(i) == t.charAt(j)，dp[i][j] = dp[i - 1][j - 1]
         * 两个位置的字母不一样时即s.charAt(i) != t.charAt(j), dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + 1
         *
         */
        public int minDistance(String word1, String word2) {
            int row = word1.length() + 1, col = word2.length() + 1;
            int dp[][] = new int[row][col];
            dp[0][0] = 0;
            //  初始化，相当于两个字符串分别去匹配空字符串，每个位置都等于i值
            for (int i = 1; i < row; i ++) {  // 第一列初始化
                dp[i][0] = i;
            }
            for (int i = 1; i < col; i++) {  // 第一列初始化
                dp[0][i] = i;
            }
            for (int i = 1; i < row; i++) {
                for (int j = 1; j < col; j++) {
                    // 两个位置的字母一样时，当前的值等于坐上角的值
                    if (word1.charAt(i - 1) == word2.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1];
                    // 两个位置的字母不一样时，等于左边和上边的最小值加一
                    else dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + 1;
                }
            }
            return dp[row - 1][col - 1];
        }
    }

    // 471： Encode String with shortest length

    /**
     *
     *
     */
    class solution471{


    }

    // 474: ones and zeros

    /**
     * You are given an array of binary strings strs and two integers m and n.
     *
     * Return the size of the largest subset of strs such that there are at most m 0's and n 1's in the subset.
     *
     * A set x is a subset of a set y if all elements of x are also elements of y.
     *
     *
     *
     * Example 1:
     *
     * Input: strs = ["10","0001","111001","1","0"], m = 5, n = 3
     * Output: 4
     * Explanation: The largest subset with at most 5 0's and 3 1's is {"10", "0001", "1", "0"}, so the answer is 4.
     * Other valid but smaller subsets include {"0001", "1"} and {"10", "1", "0"}.
     * {"111001"} is an invalid subset because it contains 4 1's, greater than the maximum of 3.
     * Example 2:
     *
     * Input: strs = ["10","0","1"], m = 1, n = 1
     * Output: 2
     * Explanation: The largest subset is {"0", "1"}, so the answer is 2.
     */
    class Solution474{

        /**
         * dp[i][j]: i代表有多少个0，j代表有多少个1，dp[i][j]代表当前最大的个数 = Max(dp[i][j], 1 + dp[i - 当前0的个数][j - 当前1的个数])
         * dp[i][j] = dp[][]
         */
        public int findMaxForm(String[] strs, int m, int n) {
            int[][] dp = new int[m + 1][n + 1];
            for (String s : strs) {
                int[] count = helper(s);  // 数当前字符串有多少个0多少个1
                for (int i = m; i >= count[0]; i--) {  // 从m开始遍历，当i大于0的个数时，代表还有0可以选择
                    for (int j = n; j >= count[1]; j--) {  // 当j大于1的个数时，代表可以有1选择
                        dp[i][j] = Math.max(1 + dp[i - count[0]][j - count[1]], dp[i][j]);
                    }
                }
            }
            return dp[m][n];
        }

        public int[] helper(String s) {
            int[] count = new int[2];  // 对应0的个数和1的个数
            for (int i = 0; i < s.length(); i++) {
                count[s.charAt(i) - '0'] ++;
            }
            return count;
        }
    }

    // 516 Longest Palindrome subsequence

    /**
     * Given a string s, find the longest palindromic subsequence's length in s.
     *
     * A subsequence is a sequence that can be derived from another sequence by deleting some or no elements
     * without changing the order of the remaining elements.
     *
     * Example 1:
     *
     * Input: s = "bbbab"
     * Output: 4
     * Explanation: One possible longest palindromic subsequence is "bbbb".
     * Example 2:
     *
     * Input: s = "cbbd"
     * Output: 2
     * Explanation: One possible longest palindromic subsequence is "bb".
     */
    class Solution516{
        /**
         * /*
         * 本题是求回文子序列，也就是不要求连续咯~
         * 本题初始化非常特殊，要着重记忆一下。
         *
         * 这个类型题中的 dp 数组，需要把一个字符串从两个维度来分析，展成一个二维的，有点不太习惯，需要画图做题适应一下。
         *
         * 动态规划五部曲：
         *
         * * 1、确定 dp 数组以及下标的含义
         *   * dp[i] [j]  ： 字符串 s 在 [i, j] 范围内最长的回文子序列的长度 dp[i] [j]
         * * 2、确定递推公式
         *   * 情况一：s[i] = s[j]
         *     * dp[i] [j] = dp[i + 1] [j - 1] + 2
         *   * 情况二：s[i] ！= s[j]
         *     * 说明s[i]和s[j]的同时加入 并不能增加[i,j]区间回文子串的长度，那么分别加入s[i]、s[j]看看哪一个可以组成最长的回文子序列。
         *     * 加入s[j]的回文子序列长度为dp[i + 1] [j]。
         *     * 加入s[i]的回文子序列长度为dp[i] [j - 1]。
         *     * 所以此时： dp[i] j] = max(dp[i + 1] [j], dp[i] [j - 1]);
         * * 3、dp 数组初始化
         *   * 首先要考虑当 i 和 j 相同的情况，从递推公式可以看出，是计算不到 i=j 的情况的，所以需要手动初始化一下，
         *   *  i=j ，则 dp[i] [j] = 1，也就是一个字符的回文子序列的长度就是 1。
         *   * 其他情况的 dp[i] [j] 初始化为 0 即可。
         *   * 这样情况二中的递推公式 dp[i] [j] 才不会被初始值覆盖。
         *
         * * 4、确定遍历顺序
         *   * 从递推公式可以看出，dp[i] [j] 依赖于 dp[i + 1] [j - 1] 和 dp[i + 1] [j]
         *   * 所以遍历顺序一定是从下到上，从左到右，才能保证CIA一行的数据是经过计算的
         */
        public int longestPalindromeSubsequence(String s) {
            int[][] dp = new int[s.length()][s.length()];

            for (int i = s.length() - 1; i >= 0; i--) {
                dp[i][i] = 1;  // 一个字母的回文为1
                for (int j = i + 1; j < s.length(); j++) {
                    if (s.charAt(i) == s.charAt(j)) dp[i][j] = dp[i + 1][j - 1] + 2;
                    else dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
            return dp[0][s.length() - 1];
        }
    }

    // 518 coin change2 完全背包问题

    /**
     * You are given an integer array coins representing coins of different denominations and
     * an integer amount representing a total amount of money.
     *
     * Return the number of combinations that make up that amount. If that amount of money cannot be made up
     * by any combination of the coins, return 0.
     *
     * You may assume that you have an infinite number of each kind of coin.
     *
     * The answer is guaranteed to fit into a signed 32-bit integer.
     *
     * Example 1:
     *
     * Input: amount = 5, coins = [1,2,5]
     * Output: 4
     * Explanation: there are four ways to make up the amount:
     * 5=5
     * 5=2+2+1
     * 5=2+1+1+1
     * 5=1+1+1+1+1
     *
     * Example 2:
     *
     * Input: amount = 3, coins = [2]
     * Output: 0
     * Explanation: the amount of 3 cannot be made up just with coins of 2.
     * Example 3:
     *
     * Input: amount = 10, coins = [10]
     * Output: 1
     *
     */
    class Soution518{
        /**
         * dp[i]: amount = i时有几种匹配方法
         * dp[i] = dp[i - coin] + dp[i]
         * @param amount
         * @param coins
         * @return
         */
        public int change(int amount, int[] coins) {
            int[] dp = new int[amount + 1];
            dp[0] = 1;   // 没有钱的时候为一种
            for (int coin: coins) {
                for (int i = coin; i <= amount; i ++) {
                    dp[i] += dp[i - coin];
                }
            }
            return dp[amount];
        }
    }

    // 322 coin change

    /**
     * You are given an integer array coins representing coins of different denominations and an integer amount
     * representing a total amount of money.
     *
     * Return the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up
     * by any combination of the coins, return -1.
     *
     * You may assume that you have an infinite number of each kind of coin.
     *
     * Example 1:
     *
     * Input: coins = [1,2,5], amount = 11
     * Output: 3
     * Explanation: 11 = 5 + 5 + 1
     * Example 2:
     *
     * Input: coins = [2], amount = 3
     * Output: -1
     * Example 3:
     *
     * Input: coins = [1], amount = 0
     * Output: 0
     */
    class Solution322 {
        /**
         *     硬币相当于我们的物品，每种硬币可以选择「无限次」，我们应该很自然的想到「完全背包」。
         *     如果不能，那么从现在开始就要培养这样的习惯：
         *     当看到题目是给定一些「物品」，让我们从中进行选择，以达到「最大价值」或者「特定价值」时，我们应该联想到「背包问题」。
         *     这本质上其实是一个组合问题：被选物品之间不需要满足特定关系，只需要选择物品，以达到「全局最优」或者「特定状态」即可。
         *     再根据物品的「选择次数限制」来判断是何种背包问题。
         *     本题每种硬币可以被选择「无限次」，我们可以直接套用「完全背包」的状态定义进行微调：
         *     定义 dp[i][j] 为考虑前 i 件物品，凑成总和为 j 所需要的最少硬币数量。
         *     为了方便初始化，我们一般让 dp[0][x] 代表不考虑任何物品的情况。
         *     因此我们有显而易见的初始化条件：dp[0][0]=0，其余dp[0][x]=INF 。
         *     代表当没有任何硬币的时候，存在凑成总和为 0 的方案，方案所使用的硬币为 0；凑成其他总和的方案不存在。
         *     由于我们要求的是「最少」硬币数量，因此我们不希望「无效值」参与转移，可设 INF=INT_MAX。
         *     当「状态定义」与「基本初始化」有了之后，我们不失一般性的考虑 dp[i][j] 该如何转移。
         *     对于第 i 个硬币我们有两种决策方案：
         *     不使用该硬币：dp[i][j]=dp[i-1][j]
         *     使用该硬币，由于每种硬币可以被选择多次（容量允许的情况下），因此最优解应当是所有方案中的最小值。即dp[i][j]=min(dp[i-1][j-k*coin]+ k)
         */
        public int coinChange(int[] coins, int amount) {
            if (amount == 0) return 0;
            if (coins == null || coins.length == 0) return -1;
            int[] dp = new int[amount + 1];
            for (int i = 1; i <= amount; i++) {  // 背包的容量，列
                int min = Integer.MAX_VALUE;
                for (int j = 0; j < coins.length; j++) {  // 硬币的数量，行
                    // 如果背包的容量容得下当前的价值，而且之前的那个硬币对应的位置不是-1
                    if (i >= coins[j] && dp[i - coins[j]] != -1) min = Math.min(min, dp[i - coins[j]] + 1);
                }
                dp[i] = min == Integer.MAX_VALUE ? -1 : min;
            }
            return dp[amount];
        }
    }

    // 312: burst balloons

    /**
     * You are given n balloons, indexed from 0 to n - 1. Each balloon is painted with a number on it represented by an
     * array nums. You are asked to burst all the balloons.
     *
     * If you burst the ith balloon, you will get nums[i - 1] * nums[i] * nums[i + 1] coins. If i - 1 or i + 1 goes out
     * of bounds of the array, then treat it as if there is a balloon with a 1 painted on it.
     *
     * Return the maximum coins you can collect by bursting the balloons wisely.
     *
     * Example 1:
     *
     * Input: nums = [3,1,5,8]
     * Output: 167
     * Explanation:
     * nums = [3,1,5,8] --> [3,5,8] --> [3,8] --> [8] --> []
     * coins =  3*1*5    +   3*5*8   +  1*3*8  + 1*8*1 = 167
     * Example 2:
     *
     * Input: nums = [1,5]
     * Output: 10
     */
    class Solution312{
        /**
         * dp[i][j] 为打破的气球为i~j之间的最大值
         * 为了方便计算我们 还是来申请一个临时数组，他的长度比原数组长度大2，那么这题就可以变成求戳破开区间(0, length+1)中所有气球所获得的最大硬币数
         *                                          左指针的前一个 扎的这个气球 右指针的后一个
         * dp[i][j] = max(dp[i][j], dp[i][x - 1] + nums[i - 1] * nums[x] * nums[j + 1] + dp[x + 1][j]);
         * @param nums
         * @return
         */
        public int maxCoins(int[] nums) {
            int n = nums.length;
            // 将最左和最右都加上一个1
            int[] arr = new int[n + 2];
            for (int i = 0; i < n; i++) {
                arr[i + 1] = nums[i];
            }
            arr[0] = arr[n + 1] = 1;
            int[][] dp = new int[n + 2][n + 2];
            return helper(1, n, arr, dp);
        }

        public int helper(int i, int j, int[] nums, int[][] dp) {
            if (i > j) return 0;
            if (dp[j][j] > 0) return dp[i][j];
            for (int x = i; x <= j; x++) {
                dp[i][j] = Math.max(dp[i][j], helper(i, x - 1, nums, dp)
                        + nums[i - 1] * nums[x] * nums[j + 1]
                        + helper(x + 1, j, nums, dp));
            }
            return dp[i][j];
        }
    }



    public static void main(String[] args) {
        System.out.println(Arrays.toString(new int[5]));
    }

}