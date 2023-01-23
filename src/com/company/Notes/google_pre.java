package com.company.Notes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class google_pre {
    class UniqueEmailAddresses{
        /**
         * Every valid email consists of a local name and a domain name, separated by the '@' sign. Besides lowercase
         * letters, the email may contain one or more '.' or '+'.
         *
         * For example, in "alice@leetcode.com", "alice" is the local name, and "leetcode.com" is the domain name.
         * If you add periods '.' between some characters in the local name part of an email address, mail sent there
         * will be forwarded to the same address without dots in the local name. Note that this rule does not apply to
         * domain names.
         *
         * For example, "alice.z@leetcode.com" and "alicez@leetcode.com" forward to the same email address.
         * If you add a plus '+' in the local name, everything after the first plus sign will be ignored. This allows
         * certain emails to be filtered. Note that this rule does not apply to domain names.
         *
         * For example, "m.y+name@email.com" will be forwarded to "my@email.com".
         * It is possible to use both of these rules at the same time.
         *
         * Given an array of strings emails where we send one email to each emails[i], return the number of different
         * addresses that actually receive mails.
         *
         * Example 1:
         *
         * Input: emails = ["test.email+alex@leetcode.com","test.e.mail+bob.cathy@leetcode.com","testemail+david@lee.tcode.com"]
         * Output: 2
         * Explanation: "testemail@leetcode.com" and "testemail@lee.tcode.com" actually receive mails.
         * Example 2:
         *
         * Input: emails = ["a@leetcode.com","b@leetcode.com","c@leetcode.com"]
         * Output: 3
         */
        public int numUniqueEmails(String[] emails) {
            HashSet<String> set = new HashSet<>();
            for (int i = 0; i < emails.length; i++) {
                String tmp = emails[i];
                String[] split = tmp.split("@");
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < split[0].length(); j++) {
                    String s = split[0].substring(j, j+1);
                    if (s.equals(".")) {
                        continue;
                    } else if (s.equals("+")) {
                        break;
                    }
                    sb.append(s);
                }
                sb.append("@");
                for(int j = 0; j < split[1].length(); j++) {
                    sb.append(split[1].substring(j,j+1));
                }
                if (!set.contains(sb.toString())){
                    set.add(sb.toString());
                }
            }
            return set.size();
        }
    }
    // odd even jump
    /**
     * You are given an integer array arr. From some starting index, you can make a series of jumps. The (1st, 3rd, 5th, ...)
     * jumps in the series are called odd-numbered jumps, and the (2nd, 4th, 6th, ...) jumps in the series are called
     * even-numbered jumps. Note that the jumps are numbered, not the indices.
     *
     * You may jump forward from index i to index j (with i < j) in the following way:
     *
     * During odd-numbered jumps (i.e., jumps 1, 3, 5, ...), you jump to the index j such that arr[i] <= arr[j] and arr[j] is t
     * he smallest possible value. If there are multiple such indices j, you can only jump to the smallest such index j.
     * During even-numbered jumps (i.e., jumps 2, 4, 6, ...), you jump to the index j such that arr[i] >= arr[j] and arr[j]
     * is the largest possible value. If there are multiple such indices j, you can only jump to the smallest such index j.
     * It may be the case that for some index i, there are no legal jumps.
     * A starting index is good if, starting from that index, you can reach the end of the array (index arr.length - 1) by
     * jumping some number of times (possibly 0 or more than once).
     *
     * Return the number of good starting indices.
     *
     * Example 1:
     *
     * Input: arr = [10,13,12,14,15]
     * Output: 2
     * Explanation:
     * From starting index i = 0, we can make our 1st jump to i = 2 (since arr[2] is the smallest among arr[1], arr[2],
     * arr[3], arr[4] that is greater or equal to arr[0]), then we cannot jump any more.
     * From starting index i = 1 and i = 2, we can make our 1st jump to i = 3, then we cannot jump any more.
     * From starting index i = 3, we can make our 1st jump to i = 4, so we have reached the end.
     * From starting index i = 4, we have reached the end already.
     * In total, there are 2 different starting indices i = 3 and i = 4, where we can reach the end with some number of
     * jumps.
     * Example 2:
     *
     * Input: arr = [2,3,1,1,4]
     * Output: 3
     * Explanation:
     * From starting index i = 0, we make jumps to i = 1, i = 2, i = 3:
     * During our 1st jump (odd-numbered), we first jump to i = 1 because arr[1] is the smallest value in [arr[1], arr[2],
     * arr[3], arr[4]] that is greater than or equal to arr[0].
     * During our 2nd jump (even-numbered), we jump from i = 1 to i = 2 because arr[2] is the largest value in [arr[2],
     * arr[3], arr[4]] that is less than or equal to arr[1]. arr[3] is also the largest value, but 2 is a smaller index,
     * so we can only jump to i = 2 and not i = 3
     * During our 3rd jump (odd-numbered), we jump from i = 2 to i = 3 because arr[3] is the smallest value in [arr[3],
     * arr[4]] that is greater than or equal to arr[2].
     * We can't jump from i = 3 to i = 4, so the starting index i = 0 is not good.
     * In a similar manner, we can deduce that:
     * From starting index i = 1, we jump to i = 4, so we reach the end.
     * From starting index i = 2, we jump to i = 3, and then we can't jump anymore.
     * From starting index i = 3, we jump to i = 4, so we reach the end.
     * From starting index i = 4, we are already at the end.
     * In total, there are 3 different starting indices i = 1, i = 3, and i = 4, where we can reach the end with some
     * number of jumps.
     */
    /*
    我们仅考虑第奇数次跳跃（偶数次跳跃的分析一模一样），从题目分析所得，对于第奇数次跳跃，其下一个位置是唯一的，一定是该位置后边并且值大于它的最小值。
    在java中，有一个维护有序数据的绝佳数据结构TreeMap，我们通过TreeMap将值和对应索引存储起来
    N-2 到 i = 0 的遍历过程中，对于 v = A[i]， 我们想知道比它略大一点和略小一点的元素是谁。

    First let's create a boolean DP array.
    dp[i][0] stands for you can arrive index n - 1 starting from index i at an odd step.
    dp[i][1] stands for you can arrive index n - 1 starting from index i at an even step.
    Initialization:
    Index n - 1 is always a good start point, regardless it's odd or even step right now. Thus dp[n - 1][0] = dp[n - 1][1] = true.
    DP formula:
    dp[i][0] = dp[index_next_greater_number][1] - because next is even step
    dp[i][1] = dp[index_next_smaller_number][0] - because next is odd step
    Result:
    Since first step is odd step, then result is count of dp[i][0] with value true.

    To quickly find the next greater or smaller number and its index: traverse the array reversely and store data into a TreeMap using the number as Key and its index as Value.

    Time complexity O(nlgn), Space complexity O(n). n is the length of the array.
     */
    class Solution {
        public int oddEvenJumps(int[] arr) {
            int res  = 1, len = arr.length;
            boolean[][] dp = new boolean[arr.length][2];
            dp[len - 1][0] = true;  // 最后一个位置不论是奇数跳还是偶数跳都等于true
            dp[len - 1][1] = true;
            TreeMap<Integer, Integer> map = new TreeMap<>();
            for (int i = len - 2; i >= 0; i++) {
                // odd step
                Integer nextGreater = map.ceilingKey(arr[i]);
                if (nextGreater != null) {
                    // 当前奇数位是否可以到达n - 1位取决于下一个比它大的位置的偶数位能否到达n - 1
                    dp[i][0] = dp[map.get(nextGreater)][1];
                }
                // even step
                Integer nextSmaller = map.floorKey(arr[i]);
                if (nextSmaller != null) {
                    // 当前偶数位是否可以到达n - 1位取决于下一个比它小的位置的奇数位能否到达n - 1
                    dp[i][1] = dp[map.get(nextSmaller)][0];
                }
                map.put(arr[i], i);
                res += dp[i][0] ? 1 : 0;
            }
            return res;
        }
    }

    // fruits into basket
    /**
     * You are visiting a farm that has a single row of fruit trees arranged from left to right. The trees are
     * represented by an integer array fruits where fruits[i] is the type of fruit the ith tree produces.
     *
     * You want to collect as much fruit as possible. However, the owner has some strict rules that you must follow:
     *
     * You only have two baskets, and each basket can only hold a single type of fruit. There is no limit on the amount
     * of fruit each basket can hold.
     * Starting from any tree of your choice, you must pick exactly one fruit from every tree (including the start tree)
     * while moving to the right. The picked fruits must fit in one of your baskets.
     * Once you reach a tree with fruit that cannot fit in your baskets, you must stop.
     * Given the integer array fruits, return the maximum number of fruits you can pick.
     *Example 1:
     *
     * Input: fruits = [1,2,1]
     * Output: 3
     * Explanation: We can pick from all 3 trees.
     * Example 2:
     *
     * Input: fruits = [0,1,2,2]
     * Output: 3
     * Explanation: We can pick from trees [1,2,2].
     * If we had started at the first tree, we would only pick from trees [0,1].
     * Example 3:
     *
     * Input: fruits = [1,2,3,2,2]
     * Output: 4
     * Explanation: We can pick from trees [2,3,2,2].
     * If we had started at the first tree, we would only pick from trees [1,2].
     */
    class Solution904 {
        public int totalFruit(int[] fruits) {
            // Hash map 'basket' to store the types of fruits.
            Map<Integer, Integer> basket = new HashMap<>();
            int left = 0, right, res = 0;

            // Add fruit from right side (right) of the window.
            for (right = 0; right < fruits.length; ++right) {
                basket.put(fruits[right], basket.getOrDefault(fruits[right], 0) + 1);

                // If the current window has more than 2 types of fruit,
                // we remove one fruit from the left index (left) of the window.
                while (basket.size() > 2) {
                    basket.put(fruits[left], basket.get(fruits[left]) - 1);
                    if (basket.get(fruits[left]) == 0) basket.remove(fruits[left]);
                    left++;
                }
                res = Math.max(res, right - left + 1);
            }

            // Once we finish the iteration, the indexes left and right
            // stands for the longest valid subarray we encountered.
            return res;
        }
    }

}
