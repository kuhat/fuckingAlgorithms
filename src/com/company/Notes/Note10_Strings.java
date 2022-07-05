package com.company.Notes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Note10_Strings {

    // 680: Valid Palindrome ii

    /**
     * Given a string s, return true if the s can be palindrome after deleting at most one character from it.
     * <p>
     * Input: s = "aba"
     * Output: true
     * <p>
     * Input: s = "abca"
     * Output: true
     * Explanation: You could delete the character 'c'.
     */

    public boolean validPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            //如果两个指针指向的字符不一样，我们要删除一个，要么删除left指针指向的值，要么删除right指针指向的值
            if (s.charAt(left) != s.charAt(right)) {
                return isPalindrome(s, left + 1, right) || isPalindrome(s, left, right - 1);
            }
            left++;
            right--;
        }
        return true;
    }

    //判断子串[left,right]是否是回文串
    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) return false;
        }
        return true;
    }

    // LeetCode 76: Minimum Window Substring

    /**
     * Given two strings s and t of lengths m and n respectively, return the minimum window substring of s
     * such that every character in t (including duplicates) is included in the window. If there is no such substring,
     * return the empty string "".
     * <p>
     * The testcases will be generated such that the answer is unique.
     * <p>
     * A substring is a contiguous sequence of characters within the string.
     * <p>
     * Input: s = "ADOBECODEBANC", t = "ABC"
     * Output: "BANC"
     * Explanation: The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.
     * <p>
     * Input: s = "a", t = "a"
     * Output: "a"
     * Explanation: The entire string s is the minimum window.
     */

    public String minWindow(String s, String t) {
        // 把t全部放入map中
        Map<Character, Integer> map = new HashMap<>();
        for (char c : t.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        //左指针和右指针
        int left = 0;
        int right = 0;

        int strStart = 0;
        int windowLength = Integer.MAX_VALUE;

        while (right < s.length()) {
            char rightChar = s.charAt(right);
            // 如果右指针指到的字母在map里，就把value减一
            if (map.containsKey(rightChar)) {
                map.put(rightChar, map.getOrDefault(rightChar, 0) - 1);
            }
            right++;

            // 检查窗口是否把t中所有的字符都覆盖了，如果覆盖，更新窗口的左边界， 找到最小的能全部覆盖的窗口
            while (check(map)) {
                //如果现在窗口比之前保存的还要小，就更新窗口的长度
                //以及窗口的起始位置
                if (right - left < windowLength) {
                    windowLength = right - left;
                    strStart = left;
                }
                // 移除窗口最左边的元素，也就是缩小窗口
                char leftChar = s.charAt(left);
                if (map.containsKey(leftChar)) map.put(leftChar, map.getOrDefault(leftChar, 0) + 1);
                left++;
            }
        }
        //如果找到合适的窗口就截取，否则就返回空
        if (windowLength != Integer.MAX_VALUE)
            return s.substring(strStart, strStart + windowLength);
        return "";
    }

    private boolean check(Map<Character, Integer> map) {
        for (int value : map.values()) {
            if (value > 0) return false;
        }
        return true;
    }

    // 用数组来记录每个字母出现的次数
    public String minWindow1(String s, String t) {
        int[] map = new int[128];
        for (char c : s.toCharArray()) {
            map[c]++;  // 记录字符串t中每个字符的数量
        }
        int count = t.length();
        int left = 0;  // 窗口左边界
        int right = 0;  // 窗口右边界
        int minWindowLen = Integer.MAX_VALUE;  // 覆盖t的最小长度
        int strStart = 0;  // 覆盖字符串t的开始位置
        while (right < s.length()) {
            if (map[s.charAt(right++)]-- > 0) count--;  // 如果找到一个在t中的字符，count减一
            while (count == 0) {  // 如果全部覆盖
                if (right - left < minWindowLen) {  // 如果有更小的窗口就更新窗口
                    minWindowLen = right - left;
                    strStart = left;
                }
                if (map[s.charAt(left++)]++ == 0) {  // 移除最左的元素，左指针向右走
                    count++;
                }
            }
        }
        if (minWindowLen != Integer.MAX_VALUE) {
            return s.substring(strStart, strStart + minWindowLen);
        }
        return "";
    }
    /*
    总结：滑动窗口类型的题也是最常见的，一般会有两个指针，分别指向窗口的左边界和右边界，
    如果窗口不满足条件我们就移动右边界来扩大窗口，如果满足条件我们可以移动左边界来
    缩小窗口，确定这个更小的窗口是否还满足条件……
     */

    // Leetcode 424: Longest Repeating Character Replacement

    /**
     * You are given a string s and an integer k. You can choose any character of the string and change it to any other
     * uppercase English character. You can perform this operation at most k times.
     * <p>
     * Return the length of the longest substring containing the same letter you can get after performing the above operations.
     * <p>
     * Input: s = "ABAB", k = 2
     * Output: 4
     * Explanation: Replace the two 'A's with two 'B's or vice versa.
     * <p>
     * Input: s = "AABABBA", k = 1
     * Output: 4
     * Explanation: Replace the one 'A' in the middle with 'B' and form "AABBBBA".
     * The substring "BBBB" has the longest repeating letters, which is 4.
     *
     * @param args
     */

    public int CharacterReplacement(String s, int k) {
        int len = s.length();
        int[] map = new int[26];  // 字母出现的次数map,比如字母B出现的次数是map[1]
        int left = 0;  // 窗口左边的位置
        int maxSameCount = 0;  // 此时相同字母出现的最大的数量
        int right = 0;
        int maxWindow = 0;
        // 窗口的左边不动，移动右边的位置
        for (; right < len; right++) {
            // 统计窗口内曾今出现过相同字母最多的数量
            maxSameCount = Math.max(maxSameCount, ++map[s.charAt(right) - 'A']);
            //如果相同字母最多的数量加上k还小于窗口的大小，说明其他的字母不能全部替换为
            //最多的那个字母，我们要缩小窗口的大小，顺便减去窗口左边那个字母的数量，因为他被移除窗口了，所以数量要减去
            if (k + maxSameCount < right - left + 1) {
                map[s.charAt(left) - 'A']--;
                left++;
            } else {  // 满足条件，要记录下最大的窗口
                maxWindow = Math.max(maxWindow, right - left + 1);
            }
        }
        return maxWindow;
    }

    // 125: Valid Palindrome

    /**
     * A phrase is a palindrome if, after converting all uppercase letters into lowercase letters and removing all
     * non-alphanumeric characters, it reads the same forward and backward. Alphanumeric characters include letters and numbers.
     * <p>
     * Given a string s, return true if it is a palindrome, or false otherwise.
     * <p>
     * Input: s = "A man, a plan, a canal: Panama"
     * Output: true
     * Explanation: "amanaplanacanalpanama" is a palindrome.
     * <p>
     * Input: s = "race a car"
     * Output: false
     * Explanation: "raceacar" is not a palindrome.
     */
    public boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;  // 如果不是数字或者字母要过滤掉
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) return false;
            left++;
            right--;
        }
        return true;
    }

    // 递归写法
    public boolean isPalindromeRecur(String s) {
        return isPalindromeHelper(s, 0, s.length() - 1);
    }

    private boolean isPalindromeHelper(String s, int left, int right) {
        if (left >= right) return true;
        while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;
        while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;
        return Character.toLowerCase(s.charAt(left)) == Character.toLowerCase(s.charAt(right))
                && isPalindromeHelper(s, ++left, ++right);
    }
}
