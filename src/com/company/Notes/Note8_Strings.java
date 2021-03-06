package com.company.Notes;

import com.sun.nio.sctp.AbstractNotificationHandler;

import java.util.*;

public class Note8_Strings {

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
        for (char c : t.toCharArray()) {
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

    // 387: first unique character in a string

    /**
     * Given a string s, find the first non-repeating character in it and return its index.
     * If it does not exist, return -1.
     * <p>
     * Input: s = "leetcode"
     * Output: 0
     * <p>
     * Input: s = "loveleetcode"
     * Output: 2
     *
     * @param s
     */

    public static int firstUniqueChar(String s) {
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < s.length(); i++) {
            if (count[s.charAt(i) - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }

    // 383 can construct

    /**
     * Given two strings ransomNote and magazine, return true if ransomNote
     * can be constructed by using the letters from magazine and false
     * otherwise.
     * <p>
     * Each letter in magazine can only be used once in ransomNote.
     * <p>
     * Input: ransomNote = "a", magazine = "b"
     * Output: false
     * <p>
     * Input: ransomNote = "aa", magazine = "ab"
     * Output: false
     * <p>
     * Input: ransomNote = "aa", magazine = "aab"
     * Output: true
     *
     * @param ransomNote
     * @param magazine
     * @return true
     */
    public static boolean canConstruct(String ransomNote, String magazine) {
        int[] count = new int[27];
        for (char ch : magazine.toCharArray()) {
            count[ch - 'a']++;  // 计算magazine里面需要的字母个数
        }
        for (char ch : ransomNote.toCharArray()) {
            if (count[ch - 'a'] == 0) return false;  // 如果magazine里面的字母个数不够用返回 false
            count[ch - 'a']--;
        }
        return true;
    }

    // 242: valid Anagram

    /**
     * Given two strings s and t, return true if t is an anagram of s, and false otherwise.
     * <p>
     * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase,
     * typically using all the original letters exactly once.
     *
     * @param s t
     */

    public static boolean isAnagram(String s, String t) {

        if (s.length() != t.length()) return false; // anagrams always have equal length

        // make an array to store frequency of characters (total 26 characters in the alphabet)
        int[] fmap = new int[26];

        // loop through the strings because now they have equal length and store the frequency of characters of string s as positive
        // and frequency of string t characters as negative and if they are anagrams
        // then frequencies should cancel out each other and should be zero at every index.
        for (int i = 0; i < s.length(); i++) {
            char c1 = s.charAt(i);
            char t1 = t.charAt(i);
            fmap[c1 - 'a']++;
            fmap[t1 - 'a']--;
        }
        // if there is any value that is not zero then return false immediately
        for (int val : fmap) if (val != 0) return false;
        // return true if it safely came out of the loop
        return true;
    }

    // 467： unique substring in wrap around string

    /**
     * We define the string s to be the infinite wraparound string of
     * "abcdefghijklmnopqrstuvwxyz", so s will look like this:
     * <p>
     * "...zabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcd....".
     * Given a string p, return the number of unique non-empty substrings
     * of p are present in s.
     * <p>
     * Input: p = "a"
     * Output: 1
     * Explanation: Only the substring "a" of p is in s.
     * <p>
     * Input: p = "cac"
     * Output: 2
     * Explanation: There are two substrings ("a", "c") of p in s.
     * <p>
     * Input: p = "zab"
     * Output: 6
     * Explanation: There are six substrings ("z", "a", "b", "za", "ab", and "zab") of p in s.
     *
     * @param p
     */

    public static int findSubStringInWrapAround(String p) {
        int[] count = new int[26];  // 用一个26位的数组来记录当前index的最大循环子串
        int curMax = 0;
        for (int i = 0; i < p.length(); i++) {
            if (i > 0 && (p.charAt(i) - p.charAt(i - 1) == 1) || (p.charAt(i - 1) - p.charAt(i) == 25)) {
                curMax++;  // 如果相邻的两个字母的ascii码相差了1（挨着的）或者25（za这种）就将curMax++
            } else {
                curMax = 1;
            }
            count[p.charAt(i) - 'a'] = Math.max(curMax, count[p.charAt(i) - 'a']);  // 记录当时的最大值
        }
        int res = 0;  // 循环最大值map数组，加上每一位的值就是答案
        for (int num : count) {
            res += num;
        }
        return res;
    }

    // 87: scramble string

    /**
     * We can scramble a string s to get a string t using the following algorithm:
     * <p>
     * If the length of the string is 1, stop.
     * If the length of the string is > 1, do the following:
     * Split the string into two non-empty substrings at a random index, i.e.,
     * if the string is s, divide it to x and y where s = x + y.
     * Randomly decide to swap the two substrings or to keep them in the same order.
     * i.e., after this step, s may become s = x + y or s = y + x.
     * Apply step 1 recursively on each of the two substrings x and y.
     * Given two strings s1 and s2 of the same length, return true if s2 is a scrambled string of s1, otherwise, return false.
     * <p>
     * input: s1 = "great", s2 = "rgeat"
     * Output: true
     *
     * @param args
     */

    public static boolean isScramble(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        if (s1.equals(s2)) return true;

        int[] letters = new int[26];
        int len = s1.length();
        for (int i = 0; i < len; i++) {
            letters[s1.charAt(i) - 'a']++;
            letters[s2.charAt(i) - 'a']--;
        }
        for (int i = 0; i < 26; i++) {
            if (letters[i] != 0) return false;
        }
        HashSet<String> set = new HashSet<>();
        set.contains(s1);
        for (int i = 1; i < len; i++) {
            if (isScramble(s1.substring(0, i), s2.substring(0, i))
                    && isScramble(s1.substring(i), s2.substring(i)))
                return true;
            if (isScramble(s1.substring(0, i), s2.substring(len - i))
                    && isScramble(s1.substring(i), s2.substring(0, len - i)))
                return true;
        }
        return false;
    }


    // 567: permutation string

    /**
     *
     * Given two strings s1 and s2, return true if s2 contains a permutation of s1,
     * or false otherwise.
     *
     * In other words, return true if one of s1's permutations is the substring of s2.
     *
     *Input: s1 = "ab", s2 = "eidbaooo"
     * Output: true
     * Explanation: s2 contains one permutation of s1 ("ba").
     *
     * @param args
     */

    // 76: min Window 重要！！！

    /**
     * Given two strings s and t of lengths m and n respectively,
     * return the minimum window substring of s such that every character in t
     * (including duplicates) is included in the window. If there is no such substring,
     * return the empty string "".
     * <p>
     * The testcases will be generated such that the answer is unique.
     * <p>
     * A substring is a contiguous sequence of characters within the string.
     * <p>
     * Input: s = "ADOBECODEBANC", t = "ABC"
     * Output: "BANC"
     * Explanation: The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.
     *
     * @param args
     */

    public static String minWindow4(String s, String t) {
        if (s.length() == 0 || t.length() == 0) return "";

        return "";
    }

    //358 Rearrange String k Distance apart

    /**
     * Given a non-empty string s and integer k, rearrange the string such that the same characters are
     * at least distance k from each other.
     * <p>
     * s = "aabbcc", k = 3
     * result: "abcabc"
     * <p>
     * s = "aaabc", k = 3
     * result: ""
     */

    public static String rearrangeString(String s, int k) {
        if (s == null || s.length() == 0) return s;
        int[] count = new int[26];
        int[] valid = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            int nextLetter = findNext(count, valid, i);
            if (nextLetter == -1) return "";
            res.append((char) ('a' + nextLetter));
            valid[nextLetter] = i + k;
            count[nextLetter]--;
        }
        return res.toString();
    }

    private static int findNext(int[] count, int[] valid, int index) {
        int max = 0, res = -1;
        for (int i = 0; i < count.length; i++) {
            if (count[i] > max && valid[i] <= index) {  // 找到出现次数最大的一个
                res = i;
                max = count[i];
            }
        }
        return res;
    }

    /**
     * s = "aabbcc", k = 3
     * <p>
     * pq: (a,2)(b,2)(c,2)
     * <p>
     * cur = (a,2) -> (a,1）
     * res = a
     * queue = (a,1)
     * <p>
     * cur = (b,2) -> (b,1）
     * res = ab
     * queue = (a,1)(b,1)
     * <p>
     * cur = (c,2) -> (c,1）
     * res = abc
     * queue = (a,1)(b,1)(c,1)
     *
     * @param s
     * @param k
     * @return
     */

    public static String rearrangeString2(String s, int k) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }  // Count the number of appearance of all characters in the string s
        PriorityQueue<Map.Entry<Character, Integer>> pq =
                new PriorityQueue<>((a, b) -> Integer.compare(b.getValue(), a.getValue()));
        pq.addAll(map.entrySet());  // 将s中的字母按照出现次数从高到低排列

        Queue<Map.Entry<Character, Integer>> queue = new LinkedList<>();  // 控制是否进行了大小为k的轮回
        StringBuilder res = new StringBuilder();

        while (!pq.isEmpty()) {
            Map.Entry<Character, Integer> cur = pq.poll();  // 取出出现次数最多的一个键值对
            res.append(cur.getKey());  // 加入到result中
            cur.setValue(cur.getValue() - 1);  // 减去一次出现次数
            queue.offer(cur);  // 加入到轮回控制queue中
            if (queue.size() < k) continue;
            Map.Entry<Character, Integer> front = queue.poll();  // 如果一个轮回装满了，将头元素装入pq中
            if (front.getValue() > 0) {
                pq.offer(front);
            }
        }
        return res.length() == s.length() ? s.toString() : "";
    }

    // 205. Isomorphic Strings

    /**
     * Given two strings s and t, determine if they are isomorphic.
     * <p>
     * Two strings s and t are isomorphic if the characters in s can be replaced to get t.
     * <p>
     * All occurrences of a character must be replaced with another character while preserving the order of characters.
     * No two characters may map to the same character, but a character may map to itself.
     * <p>
     * Input: s = "egg", t = "add"
     * Output: true
     * <p>
     * Input: s = "foo", t = "bar"
     * Output: false
     *
     * @param s
     */

    public boolean isIsomorphic(String s, String t) {
        if (s == null || t == null) return true;
        HashMap<Character, Character> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i);
            char b = t.charAt(i);
            if (map.containsKey(a)) {
                if (map.get(a).equals(b)) continue;
                else return false;
            } else {
                if (!map.containsValue(b)) {
                    map.put(a, b);
                } else return false;
            }
        }
        return true;
    }

    // Counting Sort:

    /**
     * @param s
     * @param t
     * @return
     */
    public boolean isIsomorphic1(String s, String t) {
        int[] sChars = new int[256];  // 一共256个字符，将每个字符串出现的字符映射到ASCii码对应的位置上
        int[] tChars = new int[256];
        for (int i = 0; i < s.length(); i++) {
            if (sChars[s.charAt(i)] != tChars[t.charAt(i)]) {  // 如果s和t对应位置上的字符的映射ASCii码不同
                return false;
            } else {
                sChars[s.charAt(i)] = tChars[t.charAt(i)] = t.charAt(i);
            }
        }
        return true;
    }

    // LeetCode 3:

    /**
     * Given a string s, find the length of the longest substring without repeating characters.
     * <p>
     * Input: s = "abcabcbb"
     * Output: 3
     * Explanation: The answer is "abc", with the length of 3.
     * <p>
     * Input: s = "bbbbb"
     * Output: 1
     * Explanation: The answer is "b", with the length of 1.
     * <p>
     * Input: s = "pwwkew"
     * Output: 3
     * Explanation: The answer is "wke", with the length of 3.
     * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
     */

    class lengthOfLongestSubstring {

        /*
        滑动窗口
        i为右边的界限，left为左边的
         */
        public int lengthOfLongestSubstring(String s) {
            if (s == null || s.length() == 0) return 0;
            int len = s.length();
            HashSet<Character> set;
            int res = 1;
            int count;
            for (int i = 1; i < s.length(); i++) {
                set = new HashSet<>();  // 用set来判断重复元素
                count = 1;
                int left = i - 1;  // left指针指到i之前
                set.add(s.charAt(i));
                while (left >= 0 && !set.contains(s.charAt(left))) {  // 如果set里有了当前的左指针的字母，跳过
                    set.add(s.charAt(left--));
                    count++;
                }
                res = Math.max(res, count);
            }
            return res;
        }
    }

    // LeetCode5：longest palindrome

    /**
     * Given a string s, return the longest palindromic substring in s.
     * <p>
     * Input: s = "babad"
     * Output: "bab"
     * Explanation: "aba" is also a valid answer.
     * <p>
     * Input: s = "cbbd"
     * Output: "bb"
     */
    class Solution5 {
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

        // DP
        public static String longestPalindromeDP(String s) {
            //边界条件判断
            if (s.length() < 2) return s;
            //start表示最长回文串开始的位置，
            //maxLen表示最长回文串的长度
            int start = 0, maxLen = 1;
            int length = s.length();
            boolean[][] dp = new boolean[length][length];
            for (int right = 1; right < length; right++) {
                for (int left = 0; left < right; left++) {
                    //如果两种字符不相同，肯定不能构成回文子串
                    if (s.charAt(left) != s.charAt(right)) continue;

                    //下面是s.charAt(left)和s.charAt(right)两个
                    //字符相同情况下的判断
                    //如果只有一个字符，肯定是回文子串
                    if (right == left) {
                        dp[left][right] = true;
                    } else if (right - left <= 2) {
                        //类似于"aa"和"aba"，也是回文子串
                        dp[left][right] = true;
                    } else {
                        //类似于"a******a"，要判断他是否是回文子串，只需要
                        //判断"******"是否是回文子串即可
                        dp[left][right] = dp[left + 1][right - 1];
                    }
                    //如果字符串从left到right是回文子串，只需要保存最长的即可
                    if (dp[left][right] && right - left + 1 > maxLen) {
                        maxLen = right - left + 1;
                        start = left;
                    }
                }
            }
            //截取最长的回文子串
            return s.substring(start, start + maxLen);
        }
    }

    // LeetCode28: Implement strStr()

    /**
     * Implement strStr().
     * <p>
     * Given two strings needle and haystack, return the index of the first occurrence of needle in haystack, or -1 if
     * needle is not part of haystack.
     * <p>
     * Clarification:
     * <p>
     * What should we return when needle is an empty string? This is a great question to ask during an interview.
     * <p>
     * For the purpose of this problem, we will return 0 when needle is an empty string. This is consistent to C's
     * strstr() and Java's indexOf().
     * <p>
     * Input: haystack = "hello", needle = "ll"
     * Output: 2
     * <p>
     * Input: haystack = "aaaaa", needle = "bba"
     * Output: -1
     */

    class Solution28 {
        public int strStr(String haystack, String needle) {
            if (needle == null || needle.length() == 0) return 0;
            for (int i = 0; i < haystack.length(); i++) {
                if (i + needle.length() > haystack.length()) break;
                for (int j = 0; j < needle.length(); j++) {
                    if (haystack.charAt(i + j) != needle.charAt(j)) break;
                    if (j == needle.length() - 1) return i;
                }
            }
            return -1;
        }
    }

    // LeetCode 14: Longest Common Prefix

    /**
     * Write a function to find the longest common prefix string amongst an array of strings.
     * <p>
     * If there is no common prefix, return an empty string "".
     * <p>
     * Input: strs = ["flower","flow","flight"]
     * Output: "fl"
     * <p>
     * Input: strs = ["dog","racecar","car"]
     * Output: ""
     * Explanation: There is no common prefix among the input strings.
     */
    class Solution14 {
        public String longestCommonPrefix(String[] strs) {
            if (strs == null || strs.length == 0) return "";
            for (int i = 0; i < strs[0].length(); i++) {  // 将第一个单词的字母拿出来一个接着一个遍历
                char c = strs[0].charAt(i);
                for (int j = 1; j < strs.length; j++) {  // 将接着的字符串的相应位置的字母拿去和第一个字母的对应位置比较
                    if (i == strs[j].length() || strs[j].charAt(i) != c) {
                        return strs[0].substring(0, i);
                    }
                }
            }
            return strs[0];  // 如果第一个单词遍历完了，直接输出第一个单词
        }
    }

    // LeetCode 165: Compare Version Numbers

    /**
     * Given two version numbers, version1 and version2, compare them.
     * <p>
     * Version numbers consist of one or more revisions joined by a dot '.'. Each revision consists of digits and may
     * contain leading zeros. Every revision contains at least one character. Revisions are 0-indexed from left to right,
     * with the leftmost revision being revision 0, the next revision being revision 1, and so on. For example 2.5.33 and 0.1 are valid version numbers.
     * <p>
     * To compare version numbers, compare their revisions in left-to-right order. Revisions are compared using their
     * integer value ignoring any leading zeros. This means that revisions 1 and 001 are considered equal. If a version
     * number does not specify a revision at an index, then treat the revision as 0. For example, version 1.0 is less
     * than version 1.1 because their revision 0s are the same, but their revision 1s are 0 and 1 respectively, and 0 < 1.
     * <p>
     * Return the following:
     * <p>
     * If version1 < version2, return -1.
     * If version1 > version2, return 1.
     * Otherwise, return 0.
     */
    /*
    Input: version1 = "1.01", version2 = "1.001"
    Output: 0
    Explanation: Ignoring leading zeroes, both "01" and "001" represent the same integer "1".
     */

    class Solution165 {
        public int compareVersion(String version1, String version2) {
            String[] v1 = version1.split("\\.");
            String[] v2 = version2.split("\\.");
            for (int i = 0; i < Math.max(v2.length, v1.length); i++) {
                int num1 = i < v1.length ? Integer.parseInt(v1[i]) : 0;
                int num2 = i < v2.length ? Integer.parseInt(v2[i]) : 0;
                if (num1 < num2) return -1;
                else if (num1 > num2) return 1;
            }
            return 0;
        }
    }

    // LeetCode 168: Excel Column Title

    /**
     * Given an integer columnNumber, return its corresponding column title as it appears in an Excel sheet.
     * <p>
     * For example:
     * <p>
     * A -> 1
     * B -> 2
     * C -> 3
     * ...
     * Z -> 26
     * AA -> 27
     * AB -> 28
     * ...
     * <p>
     * <p>
     * Example 1:
     * <p>
     * Input: columnNumber = 1
     * Output: "A"
     * Example 2:
     * <p>
     * Input: columnNumber = 28
     * Output: "AB"
     */

    class Solution168 {

        public String convertToTitle(int columnNumber) {
            StringBuilder sb = new StringBuilder();
            while (columnNumber > 0) {
                columnNumber--;
                sb.append((char) ('A' + columnNumber % 26));
                columnNumber /= 26;
            }
            return sb.reverse().toString();
        }
    }

    //LeetCode 32: Longest Valid Parentheses

    /**
     * Given a string containing just the characters '(' and ')', find the length of the longest valid
     * (well-formed) parentheses substring.
     * <p>
     * Input: s = "(()"
     * Output: 2
     * Explanation: The longest valid parentheses substring is "()".
     * <p>
     * Input: s = ")()())"
     * Output: 4
     * Explanation: The longest valid parentheses substring is "()()".
     */
    class Solution32 {
        public int longestValidParentheses(String s) {
            Stack<Integer> stack = new Stack<>();
            int res = 0, j = -1;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '(') stack.push(i);
                else {
                    if (stack.isEmpty()) j = i;  // 一开始就遇到（
                    else {
                        stack.pop();
                        if (stack.isEmpty()) res = Math.max(res, i - j);
                        else res = Math.max(res, i - stack.peek());
                    }
                }
            }
            return res;
        }
    }

    // LeetCode 392: Is Subsequence

    /**
     * Given two strings s and t, return true if s is a subsequence of t, or false otherwise.
     * <p>
     * A subsequence of a string is a new string that is formed from the original string by deleting some (can be none)
     * of the characters without disturbing the relative positions of the remaining characters.
     * (i.e., "ace" is a subsequence of "abcde" while "aec" is not).
     * <p>
     * Input: s = "abc", t = "ahbgdc"
     * Output: true
     * <p>
     * Input: s = "axc", t = "ahbgdc"
     * Output: false
     */
    class Solution392 {
        public boolean isSubsequence(String s, String t) {
            if (s == null || s.length() == 0) return true;
            int i = 0, j = 0;  // i控制的是s
            while (i < s.length() && j < t.length()) {
                if (s.charAt(i) == t.charAt(j)) i++;
                j++;
            }
            return i == s.length();
        }
    }

    // LeetCode 521: Longest Uncommon Subsequence I

    /**
     * Given two strings a and b, return the length of the longest uncommon subsequence between a and b. If the longest
     * uncommon subsequence does not exist, return -1.
     * <p>
     * An uncommon subsequence between two strings is a string that is a subsequence of one but not the other.
     * <p>
     * A subsequence of a string s is a string that can be obtained after deleting any number of characters from s.
     * <p>
     * For example, "abc" is a subsequence of "aebdc" because you can delete the underlined characters in "aebdc" to
     * get "abc". Other subsequences of "aebdc" include "aebdc", "aeb", and "" (empty string).
     * <p>
     * Input: a = "aba", b = "cdc"
     * Output: 3
     * Explanation: One longest uncommon subsequence is "aba" because "aba" is a subsequence of "aba" but not "cdc".
     * Note that "cdc" is also a longest uncommon subsequence.
     * <p>
     * Input: a = "aaa", b = "bbb"
     * Output: 3
     * Explanation: The longest uncommon subsequences are "aaa" and "bbb".
     * <p>
     * Input: a = "aaa", b = "aaa"
     * Output: -1
     * Explanation: Every subsequence of string a is also a subsequence of string b. Similarly, every subsequence of string b is also a subsequence of string a.
     */

    class Solution521 {
        public int findLUSlength(String a, String b) {
            return a.equals(b) ? -1 : Math.max(a.length(), b.length());
        }
    }

    // LeetCode 58: LengthOf Last Word

    /**
     * Given a string s consisting of words and spaces, return the length of the last word in the string.
     * <p>
     * A word is a maximal substring consisting of non-space characters only.
     * <p>
     * Input: s = "Hello World"
     * Output: 5
     * Explanation: The last word is "World" with length 5.
     * <p>
     * Input: s = "   fly me   to   the moon  "
     * Output: 4
     * Explanation: The last word is "moon" with length 4.
     */

    class Solution58 {
        public int lengthOfLastWord(String s) {
            String[] strings = s.split(" ");
            if (strings.length == 0) return 0;
            return strings[strings.length - 1].length();
        }
    }

    //LeetCode 171: Excel sheet column number

    /**
     * Given a string columnTitle that represents the column title as appears in an Excel sheet, return its corresponding column number.
     * <p>
     * For example:
     * <p>
     * A -> 1
     * B -> 2
     * C -> 3
     * ...
     * Z -> 26
     * AA -> 27
     * AB -> 28
     * ...
     * <p>
     * Input: columnTitle = "A"
     * Output: 1
     * <p>
     * Input: columnTitle = "AB"
     * Output: 28
     * res = 1 * 26 + 2 = 28
     */
    class Solution171 {
        public int titleToNumber(String columnTitle) {
            int res = 0;
            for (int i = 0; i < columnTitle.length(); i++) {
                res = res * 26 + (columnTitle.charAt(i) - 'A' + 1);
            }
            return res;
        }
    }

    // LeetCode 161: one Edit Distance

    /**
     * GIven two Strings s and t, determine if they are both one edit distance apart
     * <p>
     * 1. abcre abere
     * 2. abdc abc
     * 3. abc abdc
     */
    class Solution161 {
        public boolean isOneEditDistance(String s, String t) {
            for (int i = 0; i < Math.min(s.length(), t.length()); i++) {
                if (s.charAt(i) != t.charAt(i)) return s.substring(i + 1).equals(t.substring(i + 1));
                else if (s.length() > t.length()) return s.substring(i + 1).equals(t.substring(i));
                else return t.substring(i + 1).equals(s.substring(i));
            }
            return Math.abs(s.length() - t.length()) == 1;
        }
    }

    // LeetCode 434: Number of Segments in a String

    /**
     * Given a string s, return the number of segments in the string.
     * <p>
     * A segment is defined to be a contiguous sequence of non-space characters.
     * <p>
     * Input: s = "Hello, my name is John"
     * Output: 5
     * Explanation: The five segments are ["Hello,", "my", "name", "is", "John"]
     * <p>
     * time: o(n):trim为O(n)
     */
    class Solution434 {
        public int conutSegments(String s) {
            s = s.trim();
            if (s.length() == 0) return 0;
            return s.split("\\s+").length;
        }
    }

    // LeetCode 482: License key formatting

    /**
     * You are given a license key represented as a string s that consists of only alphanumeric characters and dashes.
     * The string is separated into n + 1 groups by n dashes. You are also given an integer k.
     * <p>
     * We want to reformat the string s such that each group contains exactly k characters, except for the first group,
     * which could be shorter than k but still must contain at least one character. Furthermore, there must be a dash
     * inserted between two groups, and you should convert all lowercase letters to uppercase.
     * <p>
     * Return the reformatted license key.
     * <p>
     * Input: s = "5F3Z-2e-9-w", k = 4
     * Output: "5F3Z-2E9W"
     * Explanation: The string s has been split into two parts, each part has 4 characters.
     * Note that the two extra dashes are not needed and can be removed.
     * <p>
     * Input: s = "2-5g-3-J", k = 2
     * Output: "2-5G-3J"
     * Explanation: The string s has been split into three parts, each part has 2 characters except the first part as it could be shorter as mentioned above.
     * <p>
     * time:O(n)
     * space:O(n)
     */
    class solution482 {
        public String licenseKey(String s, int k) {
            StringBuilder sb = new StringBuilder();
            for (int i = s.length() - 1; i >= 0; i--) {
                if (s.charAt(i) != '-') sb.append(sb.length() % (k + 1) == k ? '-' : "").append(s.charAt(i));
            }
            return sb.reverse().toString().toUpperCase();
        }
    }

    //LeetCode 344: Reverse String

    /**
     * Write a function that reverses a string. The input string is given as an array of characters s.
     * <p>
     * You must do this by modifying the input array in-place with O(1) extra memory.
     * <p>
     * Input: s = ["h","e","l","l","o"]
     * Output: ["o","l","l","e","h"]
     * <p>
     * Input: s = ["H","a","n","n","a","h"]
     * Output: ["h","a","n","n","a","H"]
     */

    class Solution344 {
        public void reverseString(char[] s) {
            if (s == null || s.length == 0) return;
            int left = 0, right = s.length - 1;
            while (left < right) {
                char temp = s[left];
                s[left++] = s[right];
                s[right--] = temp;
            }
        }
    }

    // LeetCode 186: reverse words in a string II

    /**
     * input : the sky is blue (char array)
     * output: blue is sky the (char array)
     * <p>
     * time: O(n)
     * space: O(1)
     */

    class Solution186 {
        public void reverseWords(char[] s) {
            reverse(s, 0, s.length - 1);  // 先整体进行一个翻转
            int r = 0;
            while (r < s.length) {
                int l = r;
                while (r < s.length && s[r] != ' ') r++;  // 内部的单词进行翻转
                reverse(s, l, r - 1);
                r++;
            }
        }

        private void reverse(char[] s, int i, int j) {
            while (i < j) {
                char temp = s[i];
                s[i++] = s[j];
                s[j--] = temp;
            }
        }
    }

    //LeetCode 151: Reverse words in a String

    /**
     * Given an input string s, reverse the order of the words.
     * <p>
     * A word is defined as a sequence of non-space characters. The words in s will be separated by at least one space.
     * <p>
     * Return a string of the words in reverse order concatenated by a single space.
     * <p>
     * Note that s may contain leading or trailing spaces or multiple spaces between two words. The returned string
     * should only have a single space separating the words. Do not include any extra spaces.
     * <p>
     * Input: s = "the sky is blue"
     * Output: "blue is sky the"
     * <p>
     * Input: s = "  hello world  "
     * Output: "world hello"
     * Explanation: Your reversed string should not contain leading or trailing spaces.
     * <p>
     * time：O(n)
     * space: O(n)
     */
    class Solution151 {
        public String reverseWords(String s) {
            if (s == null || s.length() == 0) return s;
            String[] words = s.trim().split("\\s+");  // 去掉前后的空格，分成string的array
            StringBuilder sb = new StringBuilder();
            for (int i = words.length - 1; i >= 0; i--) {  // 从后往前加到sb后面
                sb.append(words[i] + " ");
            }
            return sb.toString().trim();  // 去掉最后一个空格
        }
    }

    // LeetCode 345

    /**
     * Given a string s, reverse only all the vowels in the string and return it.
     * <p>
     * The vowels are 'a', 'e', 'i', 'o', and 'u', and they can appear in both cases.
     * <p>
     * Input: s = "hello"
     * Output: "holle"
     * <p>
     * Input: s = "leetcode"
     * Output: "leotcede"
     */
    class Solution345 {

        public String reverseVowels(String s) {
            if (s == null || s.length() == 0) return s;
            String vowels = "aeiouAEIOU";
            char[] str = s.toCharArray();

            int left = 0;
            int right = s.length() - 1;
            while (left < right) {
                while (left < right && vowels.indexOf(str[left]) == -1) left++;
                while (right > left && vowels.indexOf(str[right]) == -1) right--;
                char temp = str[left];
                str[left++] = str[right];
                str[right--] = temp;
            }
            return new String(str);
        }
    }

    // LeetCide541: Reverse String II

    /**
     * Given a string s and an integer k, reverse the first k characters for every 2k characters counting
     * from the start of the string.
     * <p>
     * If there are fewer than k characters left, reverse all of them. If there are less than 2k but greater than or
     * equal to k characters, then reverse the first k characters and leave the other as original.
     * <p>
     * Input: s = "abcdefg", k = 2
     * Output: "bacdfeg"
     * <p>
     * Input: s = "abcd", k = 2
     * Output: "bacd"
     */
    class Solution541 {
        public String reverseStr(String s, int k) {
            char[] arr = s.toCharArray();
            int index = 0;
            while (index < s.length()) {
                int j = Math.min(index + k - 1, s.length() - 1);
                swap(arr, index, j);
                index += 2 * k;
            }
            return String.valueOf(arr);
        }

        private void swap(char[] arr, int i, int j) {
            while (i < j) {
                char temp = arr[i];
                arr[i++] = arr[j];
                arr[j--] = temp;
            }
        }
    }

    // LeetCode 557: Reverse Words in a String III

    /**
     * Given a string s, reverse the order of characters in each word within a sentence while still preserving whitespace and initial word order.
     * <p>
     * Input: s = "Let's take LeetCode contest"
     * Output: "s'teL ekat edoCteeL tsetnoc"
     * <p>
     * Input: s = "God Ding"
     * Output: "doG gniD"
     */
    class Solution557 {
        public String reverseWords(String s) {
            if (s == null || s.length() == 0) return s;
            char[] arr = s.toCharArray();
            int i = 0;
            for (int j = 0; j < arr.length; j++) {
                if (arr[j] == ' ') {
                    reverse(arr, i, j - 1);  // 遇到空格就把之前的单词转换
                    i = j + 1;  // i 移动到下一个字母开始的位置
                }
            }
            reverse(arr, i, arr.length - 1); // 最后一个单词是没有空格的
            return String.valueOf(arr);
        }

        private void reverse(char[] arr, int i, int j) {
            while (i < j) {
                char temp = arr[i];
                arr[i++] = arr[j];
                arr[j--] = temp;
            }
        }
    }

    public String reverseWords2(String s) {
        if (s == null || s.length() == 0) return s;
        String[] str = s.split(" ");
        for (int i = 0; i < str.length; i++) {
            str[i] = new StringBuilder(str[i]).reverse().toString();
        }
        StringBuilder sb = new StringBuilder();
        for (String st : str) {
            sb.append(st + " ");
        }
        return sb.toString().trim();
    }

    // LeetCode 556: Next Greater Element III

    /**
     * Given a positive integer n, find the smallest integer which has exactly the same digits existing in the integer
     * n and is greater in value than n. If no such positive integer exists, return -1.
     * <p>
     * Note that the returned integer should fit in 32-bit integer, if there is a valid answer but it does
     * not fit in 32-bit integer, return -1.
     * <p>
     * 123 4 7655 5 3
     * 123 5 7655 4 3 找到降序的那个数字然后和从右往左数第一个比它大的数交换
     * 123 5 345567 然后再 把降序变成升序
     * <p>
     * Input: n = 12
     * Output: 21
     * <p>
     * Input: n = 21
     * Output: -1
     */
    class Solution556 {
        public int nextGreaterElement(int n) {
            char[] res = ("" + n).toCharArray();
            int i = res.length - 2;  // 从倒数第二个数字开始找
            while (i >= 0 && res[i + 1] <= res[i]) i--;  // 找到开始下降的那个位置
            if (i < 0) return -1;  // 如果没有找到下降的那个直接没有答案
            int j = res.length - 1;
            while (j >= 0 && res[j] <= res[i]) j--;  // 找到第一个比刚刚找到的数字大的数
            swap(res, i, j);  // 交换两个数字
            reverse(res, i + 1);  // 将降序变成升序
            long val = Long.parseLong(new String(res));
            return val <= Integer.MAX_VALUE ? (int) val : -1;
        }

        private void reverse(char[] chars, int start) {
            int i = start, j = chars.length - 1;
            while (i < j) {
                swap(chars, i++, j--);
            }
        }

        private void swap(char[] chars, int i, int j) {
            char temp = chars[i];
            chars[i++] = chars[j];
            chars[j--] = temp;
        }
    }

    // LeetCode 459： Repeated SubString Pattern

    /**
     * Given a string s, check if it can be constructed by taking a substring of
     * it and appending multiple copies of the substring together.
     * <p>
     * Input: s = "abab"
     * Output: true
     * Explanation: It is the substring "ab" twice.
     * <p>
     * Input: s = "aba"
     * Output: false
     */
    class Solution459 {
        public boolean repeatedSubstringPattern(String str) {
            String s = str + str; // 将str重复两遍，去掉首尾，如果中间剩下的字符串包含str的话就可以
            return s.substring(1, s.length() - 1).contains(str);
        }

        public boolean repeatedSubStringPattern1(String str) {
            int len = str.length();
            for (int i = len / 2; i >= 1; i--) {
                if (len % i == 0) {
                    int num = len / i;
                    String sub = str.substring(0, i);
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < num; j++) {
                        sb.append(sub);
                    }
                    if (sb.toString().equals(str)) return true;
                }
            }
            return false;
        }
    }

    //LeetCode 520: Detect Capital

    /**
     * We define the usage of capitals in a word to be right when one of the following cases holds:
     * <p>
     * All letters in this word are capitals, like "USA".
     * All letters in this word are not capitals, like "leetcode".
     * Only the first letter in this word is capital, like "Google".
     * Given a string word, return true if the usage of capitals in it is right.
     */
    class solution520 {
        public boolean detectCapitalUse(String word) {
            int numUpper = 0;
            for (int i = 0; i < word.length(); i++) {
                if (Character.isUpperCase(word.charAt(i))) numUpper++;
            }
            if (numUpper == 1) return Character.isUpperCase(word.charAt(0));
            return numUpper == 0 || numUpper == word.length();
        }
    }

    //LeetCode 38: Count and Stay

    /**
     * The count-and-say sequence is a sequence of digit strings defined by the recursive formula:
     * <p>
     * countAndSay(1) = "1"
     * countAndSay(n) is the way you would "say" the digit string from countAndSay(n-1), which is then converted into
     * a different digit string.
     * To determine how you "say" a digit string, split it into the minimal number of substrings such that each
     * substring contains exactly one unique digit. Then for each substring, say the number of digits, then say the digit. Finally, concatenate every said digit.
     * <p>
     * For example, the saying and conversion for digit string "3322251":
     * <p>
     * <p>
     * Given a positive integer n, return the nth term of the count-and-say sequence.
     * <p>
     * Input: n = 1
     * Output: "1"
     * Explanation: This is the base case.
     * <p>
     * Input: n = 4
     * Output: "1211"
     * Explanation:
     * countAndSay(1) = "1"
     * countAndSay(2) = say "1" = one 1 = "11"
     * countAndSay(3) = say "11" = two 1's = "21"
     * countAndSay(4) = say "21" = one 2 + one 1 = "12" + "11" = "1211"
     */

    class Solution38 {
        public String countAndSay(int n) {
            int i = 1;
            String res = "1";
            while (i < n) {  // i 控制的是n
                int count = 0;
                StringBuilder sb = new StringBuilder();
                char c = res.charAt(0);
                for (int j = 0; j <= res.length(); j++) {  // j控制的是res的长度
                    if (j != res.length() && res.charAt(j) == c) {
                        count++;
                    } else {
                        sb.append(count);
                        sb.append(c);
                        if (j != res.length()) {
                            count = 1;
                            c = res.charAt(j);
                        }
                    }
                }
                res = sb.toString();
                i++;
            }
            return res;
        }
    }

    // LeetCode 522: Longest Uncommon Subsequence II

    /**
     * Given an array of strings strs, return the length of the longest uncommon subsequence between them. If the
     * longest uncommon subsequence does not exist, return -1.
     * <p>
     * An uncommon subsequence between an array of strings is a string that is a subsequence of one string but not the others.
     * <p>
     * A subsequence of a string s is a string that can be obtained after deleting any number of characters from s.
     * <p>
     * For example, "abc" is a subsequence of "aebdc" because you can delete the underlined characters in "aebdc" to get
     * "abc". Other subsequences of "aebdc" include "aebdc", "aeb", and "" (empty string).
     * <p>
     * Input: strs = ["aba","cdc","eae"]
     * Output: 3
     * <p>
     * Input: strs = ["aaa","aaa","aa"]
     * Output: -1
     */

    class Solution522 {
        public int findLUSlength(String[] strs) {
            int res = -1, j = 0;
            for (int i = 0; i < strs.length; i++) {
                for (j = 0; j < strs.length; j++) {
                    if (j == i) continue;
                    if (isSUbsequence(strs[i], strs[j])) break;
                }
                if (j == strs.length) res = Math.max(res, strs[i].length());
            }
            return res;
        }

        public boolean isSUbsequence(String s, String t) {
            if (s == null || s.length() == 0) return true;
            int i = 0, j = 0;
            while (i < s.length() && j < t.length()) {
                if (s.charAt(i) == t.charAt(j)) i++;
                j++;
            }
            return i == s.length();
        }
    }

    // LeetCode 553: Optimal DIvision

    /**
     * You are given an integer array nums. The adjacent integers in nums will perform the float division.
     * <p>
     * For example, for nums = [2,3,4], we will evaluate the expression "2/3/4".
     * However, you can add any number of parenthesis at any position to change the priority of operations.
     * You want to add these parentheses such the value of the expression after the evaluation is maximum.
     * <p>
     * Return the corresponding expression that has the maximum value in string format.
     * <p>
     * Note: your expression should not contain redundant parenthesis.
     * <p>
     * Input: nums = [1000,100,10,2]
     * Output: "1000/(100/10/2)"
     * Explanation:
     * 1000/(100/10/2) = 1000/((100/10)/2) = 200
     * However, the bold parenthesis in "1000/((100/10)/2)" are redundant, since they don't influence the operation priority. So you should return "1000/(100/10/2)".
     * Other cases:
     * 1000/(100/10)/2 = 50
     * 1000/(100/(10/2)) = 50
     * 1000/100/10/2 = 0.5
     * 1000/100/(10/2) = 2
     */
    class Solution553 {
        public String optimalDivision(int[] nums) {
            if (nums.length == 1) return nums[0] + "";
            if (nums.length == 2) return nums[0] + "/" + nums[1];
            StringBuilder res = new StringBuilder(nums[0] + "/(" + nums[1]);
            for (int i = 2; i < nums.length; i++) res.append("/" + nums[i]);
            res.append(")");
            return res.toString();
        }
    }

    // LeetCode 551: Student Attendance Record 1

    /**
     * You are given a string s representing an attendance record for a student where each character signifies whether
     * the student was absent, late, or present on that day. The record only contains the following three characters:
     * <p>
     * 'A': Absent.
     * 'L': Late.
     * 'P': Present.
     * The student is eligible for an attendance award if they meet both of the following criteria:
     * <p>
     * The student was absent ('A') for strictly fewer than 2 days total.
     * The student was never late ('L') for 3 or more consecutive days.
     * Return true if the student is eligible for an attendance award, or false otherwise.
     * <p>
     * Input: s = "PPALLP"
     * Output: true
     * Explanation: The student has fewer than 2 absences and was never late 3 or more consecutive days.
     * <p>
     * Input: s = "PPALLL"
     * Output: false
     * Explanation: The student was late 3 consecutive days in the last 3 days, so is not eligible for the award.
     */
    class Solution551 {
        public boolean checkRecord(String s) {
            int count = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == 'A') count++;
                if (i <= s.length() - 3 && s.charAt(i) == 'L' && s.charAt(i + 1) == 'L' && s.charAt(i + 2) == 'L') {
                    return false;
                }
            }
            return count < 2;
        }
    }


    // leetCode 506: Relative rankings

    /**
     * You are given an integer array score of size n, where score[i] is the score of the ith athlete in a competition.
     * All the scores are guaranteed to be unique.
     * <p>
     * The athletes are placed based on their scores, where the 1st place athlete has the highest score, the 2nd place
     * athlete has the 2nd highest score, and so on. The placement of each athlete determines their rank:
     * <p>
     * The 1st place athlete's rank is "Gold Medal".
     * The 2nd place athlete's rank is "Silver Medal".
     * The 3rd place athlete's rank is "Bronze Medal".
     * For the 4th place to the nth place athlete, their rank is their placement number (i.e., the xth place athlete's rank is "x").
     * Return an array answer of size n where answer[i] is the rank of the ith athlete.
     * <p>
     * Input: score = [5,4,3,2,1]
     * Output: ["Gold Medal","Silver Medal","Bronze Medal","4","5"]
     * Explanation: The placements are [1st, 2nd, 3rd, 4th, 5th].
     */

    class Solution {
        public String[] findRelativeRanks(int[] score) {
            String[] res = new String[score.length];
            int max = 0;
            for (int num : score) {
                max = Math.max(num, max);  // 找到最大值
            }
            // 桶排序
            int[] bucket = new int[max + 1];
            for (int i = 0; i < score.length; i++) {
                bucket[score[i]] = i + 1;  // 将score的index存入bucket里
            }
            // 倒序遍历bucket
            int medal = 1;
            for (int i = bucket.length - 1; i >= 0; i--) {
                if (bucket[i] != 0) {
                    if (medal == 1) res[bucket[i] - 1] = "Gold Medal";
                    else if (medal == 2) res[bucket[i] - 1] = "Silver Medal";
                    else if (medal == 3) res[bucket[i] - 1] = "Bronze Medal";
                    else res[bucket[i] - 1] = String.valueOf(medal);
                    medal++;
                }
            }
            return res;
        }
    }

    // LeetCode 290: word Pattern

    /**
     * Given a pattern and a string s, find if s follows the same pattern.
     * <p>
     * Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty word in s.
     * <p>
     * <p>
     * Input: pattern = "abba", s = "dog cat cat dog"
     * Output: true
     * <p>
     * Input: pattern = "abba", s = "dog cat cat fish"
     * Output: false
     */
    class Solution290 {
        public boolean wordPattern(String pattern, String s) {
            String[] arr = s.split(" ");
            if (arr.length != pattern.length()) return false;
            HashMap<Character, String> map = new HashMap<>();
            for (int i = 0; i < arr.length; i++) {
                char c = pattern.charAt(i);
                if (map.containsKey(c)) {
                    if (map.get(c).equals(arr[i])) continue;
                    else return false;
                } else {
                    if (!map.containsValue(arr[i])) map.put(c, arr[i]);
                    else return false;
                }
            }
            return true;
        }
    }

    // LeetCOde 500 : keyBoard Row

    /**
     * Given an array of strings words, return the words that can be typed using letters of
     * the alphabet on only one row of American keyboard like the image below.
     * <p>
     * In the American keyboard:
     * <p>
     * the first row consists of the characters "qwertyuiop",
     * the second row consists of the characters "asdfghjkl", and
     * the third row consists of the characters "zxcvbnm".
     * <p>
     * Input: words = ["Hello","Alaska","Dad","Peace"]
     * Output: ["Alaska","Dad"]
     * <p>
     * nput: words = ["adsdf","sfd"]
     * Output: ["adsdf","sfd"]
     */
    class Solution500 {
        public String[] findWords(String[] words) {
            String[] rows = {"qwertyuiop", "asdfghjkl", "zxcvbnm"};
            HashMap<Character, Integer> keyBoard = new HashMap<>();
            for (int i = 0; i < rows.length; i++) {
                for (char c : rows[i].toCharArray()) {
                    keyBoard.put(c, i);  //  将每个字符和行数匹配存储
                }
            }
            List<String> res = new ArrayList<>();
            for (String word : words) {
                char[] letters = word.toLowerCase().toCharArray();
                boolean sameRow = true;
                int index = keyBoard.get(letters[0]);
                for (char c : letters) {
                    if (index != keyBoard.get(c)) {
                        sameRow = false;
                        break;
                    }
                }
                if (sameRow) {
                    res.add(word);
                }
            }
            return res.stream().toArray(String[]::new);
        }
    }

    // 316: remove duplicate letters
    /**
     * Given a string s, remove duplicate letters so that every letter appears once and only once.
     * You must make sure your result is the smallest in lexicographical order among all possible results.
     *
     * Input: s = "bcabc"
     * Output: "abc"
     *
     * Input: s = "cbacdcbc"
     * Output: "acdb"
     */
    class Solution316 {
        public String removeDuplicateLetters(String s) {
            if (s == null || s.length() == 0) return s;
            HashMap<Character, Integer> map = new HashMap<>();
            for (int i = 0; i < s.length(); i++) {
                map.put(s.charAt(i), i);  // map存的是key:每个字母，value:最后一次出现的index
            }
            char[] res = new char[map.size()];
            int start = 0, end = findMinLastPos(map);  // 查找map中出现次数的最小值

            for (int i = 0; i < res.length; i++) {  // 放字母
                char minChar = 'z' + 1;  // 相当于integer.max_value,一个上限
                for(int k = start; k <= end; k ++) {  // 找字母，按照字母排序最小的
                    if (map.containsKey(s.charAt(k)) && s.charAt(k) < minChar) {
                        minChar = s.charAt(k);
                        start = k + 1;  // 下一次找的位置
                    }
                }
                res[i] = minChar;
                map.remove(minChar);
                if (s.charAt(end) == minChar) end = findMinLastPos(map);
            }
            return new String(res);
        }

        private int findMinLastPos(HashMap<Character, Integer> map) {
            int res = Integer.MAX_VALUE;
            for (int nunm : map.values()) {
                res = Math.min(res, nunm);
            }
            return res;
        }

    }

    //535：
    /**
     * Note: This is a companion problem to the System Design problem: Design TinyURL.
     * TinyURL is a URL shortening service where you enter a URL such as https://leetcode.com/problems/design-tinyurl
     * and it returns a short URL such as http://tinyurl.com/4e9iAk. Design a class to encode a URL and decode a tiny URL.
     *
     * There is no restriction on how your encode/decode algorithm should work. You just need to ensure that a URL can
     * be encoded to a tiny URL and the tiny URL can be decoded to the original URL.
     *
     * Implement the Solution class:
     *
     * Solution() Initializes the object of the system.
     * String encode(String longUrl) Returns a tiny URL for the given longUrl.
     * String decode(String shortUrl) Returns the original long URL for the given shortUrl. It is guaranteed that the
     * given shortUrl was encoded by the same object.
     *
     * Input: url = "https://leetcode.com/problems/design-tinyurl"
     * Output: "https://leetcode.com/problems/design-tinyurl"
     *
     * Explanation:
     * Solution obj = new Solution();
     * string tiny = obj.encode(url); // returns the encoded tiny url.
     * string ans = obj.decode(tiny); // returns the original url after decoding it.
     */
    public class Codec {

        HashMap<String, String> map = new HashMap<>();
        String mapping = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        // Encodes a URL to a shortened URL.
        public String encode(String longUrl) {
            Random random = new Random();
            StringBuilder res = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                int index = random.nextInt(mapping.length());  // 在mapping 中随机选择一个index
                res.append(mapping.charAt(index));  // 在mapping 中选择index位置的字母接在res 后面
            }
            String url = "https://tinyurl.com/" + res.toString();
            if (!map.containsKey(url)) map.put(url, longUrl);
            return url;
        }

        // Decodes a shortened URL to its original URL.
        public String decode(String shortUrl) {
            return map.get(shortUrl);
        }
    }

    //228: Summary Ranges
    /**
     * You are given a sorted unique integer array nums.
     *
     * A range [a,b] is the set of all integers from a to b (inclusive).
     *
     * Return the smallest sorted list of ranges that cover all the numbers in the array exactly.
     * That is, each element of nums is covered by exactly one of the ranges, and there is no
     * integer x such that x is in one of the ranges but not in nums.
     *
     * Each range [a,b] in the list should be output as:
     *
     * "a->b" if a != b
     * "a" if a == b
     *
     * Input: nums = [0,1,2,4,5,7]
     * Output: ["0->2","4->5","7"]
     * Explanation: The ranges are:
     * [0,2] --> "0->2"
     * [4,5] --> "4->5"
     * [7,7] --> "7"
     *
     * Input: nums = [0,2,3,4,6,8,9]
     * Output: ["0","2->4","6","8->9"]
     * Explanation: The ranges are:
     * [0,0] --> "0"
     * [2,4] --> "2->4"
     * [6,6] --> "6"
     * [8,9] --> "8->9"
     */

    class Solution228 {
        public List<String> summaryRanges(int[] nums) {
            List<String> res = new ArrayList<>();
            if (nums == null || nums.length == 0) return res;
            for (int i = 0; i < nums.length; i ++) {
                int num = nums[i];
                while (i < nums.length - 1 && nums[i] + 1 == nums[i + 1]) i++;
                if (num != nums[i]) res.add(num + "->" + nums[i]);
                else res.add(num + "");
            }
            return res;
        }
    }


    // 475： Heaters
    /**
     *
     * Winter is coming! During the contest, your first job is to design a standard heater with a fixed warm radius to
     * warm all the houses.
     *
     * Every house can be warmed, as long as the house is within the heater's warm radius range.
     *
     * Given the positions of houses and heaters on a horizontal line, return the minimum radius standard of heaters
     * so that those heaters could cover all houses.
     *
     * Notice that all the heaters follow your radius standard, and the warm radius will the same.
     *
     * Input: houses = [1,2,3], heaters = [2]
     * Output: 1
     * Explanation: The only heater was placed in the position 2, and if we use the radius 1 standard, then all the
     * houses can be warmed.
     *
     * Input: houses = [1,2,3,4], heaters = [1,4]
     * Output: 1
     * Explanation: The two heater was placed in the position 1 and 4. We need to use radius 1 standard, then all the
     * houses can be warmed.
     */
    class Solution475 {
        public int findRadius(int[] houses, int[] heaters) {
            Arrays.sort(houses);
            Arrays.sort(heaters);
            int i = 0, res = 0;
            for (int house : houses) {
                while (i < heaters.length - 1
                        && Math.abs(heaters[i + 1] - house) <= Math.abs(heaters[i] - house)) {
                    // 如果下一个heater到目前房子的距离小于这个heater到目前房子的距离
                    i ++;  // 找下一个heater
                }
                res = Math.max(res, Math.abs(heaters[i] - house));
            }
            return res;
        }
    }




}
