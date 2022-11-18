package com.company.Notes;

import com.sun.nio.sctp.AbstractNotificationHandler;

import javax.lang.model.type.ArrayType;
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

    public String solu76(String s, String t){
        int[] map = new int[256];
        int left = 0, right = 0, strStart = 0;
        int minLen = Integer.MAX_VALUE, count = t.length();
        for (char ch : t.toCharArray()) {
            map[ch] ++;
        }

        while(right < s.length()) {
            if(map[s.charAt(right++)]-- > 0) count--;
            while(count == 0) {
                if(right - left < minLen) {
                    minLen = right - left;
                    strStart = left;
                }
                if (map[s.charAt(left++)]++ == 0) count ++;
            }
        }
        return minLen != Integer.MAX_VALUE ? s.substring(strStart, strStart +minLen) : "";
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

    public int CharacterReplacement1(String s, int k) {
        int[] count = new int[26];
        int res = 0, start = 0, max = 0;
        for (int i = 0; i < s.length(); i++) {
            max = Math.max(max, ++count[s.charAt(i) - 'A']);
            while (i - start + 1 - max > k) {  // 当i减去start+1减去max大于k时，也就是当前的窗口使用的字母个数小于k时，
                count[s.charAt(start) - 'A']--;  // 当前窗口左边的字母数减一
                start++;  // 窗口左边的值向右走
            }
            res = Math.max(res, i - start + 1);
        }
        return res;

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
            if (i > 0 && (p.charAt(i) - p.charAt(i - 1) == 1 || (p.charAt(i - 1) - p.charAt(i) == 25))) {
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
    class Solution567{
        public boolean checkInclusion(String s1, String s2) {
            int [] count = new int[128];
            for (char c : s1.toCharArray()) {
                count[c]++;  // 记录s1中每个字母出现的次数
            }
            //  左右指针，i为右
            for (int i = 0, j = 0; i < s2.length(); i++) {
                if (-- count[s2.charAt(i)] < 0) {
                    while (count[s2.charAt(i)] != 0) {
                        count[s2.charAt(j++)]++;  // 左指针向右走
                    }
                } else if (i - j + 1 == s1.length()) {
                    return true;
                }
            }
            return false;
        }

    }

    // 76: min Window 重要！！！ 背下来

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
    class Solution358 {
        public static String rearrangeString(String s, int k) {
            if (s == null || s.length() == 0) return s;
            int[] count = new int[26];
            int[] valid = new int[26];
            for (char c : s.toCharArray()) {
                count[c - 'a']++;  // 统计每个字母出现的次数
            }
            StringBuilder res = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                int nextLetter = findNext(count, valid, i);  //  找到下一个字母该放什么
                if (nextLetter == -1) return "";  // 如果找不到返回-1
                res.append((char) ('a' + nextLetter));
                valid[nextLetter] = i + k;
                count[nextLetter]--;
            }
            return res.toString();
        }

        private static int findNext(int[] count, int[] valid, int index) {
            int max = 0, res = -1;
            for (int i = 0; i < count.length; i++) {
                if (count[i] > max && valid[i] <= index) {  // 找到出现次数最大的一个先处理
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

        // O(nlogn)
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
            return res.length() == s.length() ? res.toString() : "";
        }
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
            int res = 0, start = -1;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '(') stack.push(i);  // 如果遇到左括号（，把左括号的起始位置放入stack中
                else {
                    if (stack.isEmpty()) start = i;  // 一开始就遇到）
                    else {
                        stack.pop();  // 如果stack不是空的，相当于遇到了右括号），把stack的第一个值pop出来
                        if (stack.isEmpty()) res = Math.max(res, i - start);  // 如果pop了一个出来过后stack为空了，则更新res为i-start
                        else res = Math.max(res, i - stack.peek());  // 如果pop了一个之后res不为空
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
            HashMap<String, Integer> map = new HashMap<>();

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


    //409 longest Palindrome

    /**
     * Given a string s which consists of lowercase or uppercase letters, return the length of the
     * longest palindrome that can be built with those letters.
     *
     * Letters are case sensitive, for example, "Aa" is not considered a palindrome here.
     *
     * Example 1:
     *
     * Input: s = "abccccdd"
     * Output: 7
     * Explanation: One longest palindrome that can be built is "dccaccd", whose length is 7.
     */
    class Solution409{
        /*
        因为这题让求的是可以构造的最长回文串，我们只需要把所有字符截取最大的偶数个，统计他们的和，如果还有剩下的字符，最后再加1，如果没有，最后不用再加了。
         */
        public int longestPalindrome(String s) {
            int[] map = new int[256];
            for (char c : s.toCharArray()) {
                map[c] ++;
            }
            int res = 0;
            int mask = -2;
            for (int count : map) res += count & mask;  //每个字符的个数取最大偶数，然后相加
            /*
            因为mask是-2，count&-2的意思就是如果count是偶数，计算的结果还是count，如果count是奇数，计算的结果是count-1。直接看可能不直观，把-2转化为二进制就明白了。
            11111111 11111111 11111111 11111110
             */
            return res < s.length() ? res + 1: res;  // //如果相加的和小于字符串的长度，最后还要加1
        }
    }

    // 565 Array Nesting

    /**
     * you are given an integer array nums of length n where nums is a permutation of the numbers in the range [0, n - 1].
     *
     * You should build a set s[k] = {nums[k], nums[nums[k]], nums[nums[nums[k]]], ... } subjected to the following rule:
     *
     * The first element in s[k] starts with the selection of the element nums[k] of index = k.
     * The next element in s[k] should be nums[nums[k]], and then nums[nums[nums[k]]], and so on.
     * We stop adding right before a duplicate element occurs in s[k].
     * Return the longest length of a set s[k].
     *
     *
     *
     * Example 1:
     *
     * Input: nums = [5,4,0,3,1,6,2]
     * Output: 4
     * Explanation:
     * nums[0] = 5, nums[1] = 4, nums[2] = 0, nums[3] = 3, nums[4] = 1, nums[5] = 6, nums[6] = 2.
     * One of the longest sets s[k]:
     * s[0] = {nums[0], nums[5], nums[6], nums[2]} = {5, 6, 2, 0}
     * Example 2:
     *
     * Input: nums = [0,1,2]
     * Output: 1
     */
    class Solution565{
        public int arrayNesting(int[] nums) {
            int res = 0;
            for (int i = 0; i < nums.length; i++) {
                int next = i;
                int count = 0;
                while (nums[next] != -1) {
                    count ++;
                    int temp = next;
                    next = nums[next];
                    nums[temp] = -1;
                }
                res = Math.max(res, count);
            }
            return res;
        }
    }

    //30 SubString with Concatenation All Words  sliding window
    /**
     * You are given a string s and an array of strings words of the same length. Return all starting indices of
     * substring(s) in s that is a concatenation of each word in words exactly once, in any order, and without any intervening characters.
     *
     * You can return the answer in any order.
     *
     * Example 1:
     *
     * Input: s = "barfoothefoobarman", words = ["foo","bar"]
     * Output: [0,9]
     * Explanation: Substrings starting at index 0 and 9 are "barfoo" and "foobar" respectively.
     * The output order does not matter, returning [9,0] is fine too.
     * Example 2:
     *
     * Input: s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
     * Output: []
     * Example 3:
     *
     * Input: s = "barfoofoobarthefoobarman", words = ["bar","foo","the"]
     * Output: [6,9,12]
     *
     * O（n^2）
     * space: O(N)
     */
    class Solution30 {
        public List<Integer> findSubstring(String s, String[] words) {
            if (s == null || words == null || words.length == 0) return new ArrayList<>();
            List<Integer> res = new ArrayList<>();
            int n = words.length;
            int m = words[0].length();
            HashMap<String, Integer> map = new HashMap<>();
            // 将words里的所有单词放入map
            for (String str : words) map.put(str, map.getOrDefault(str, 0) + 1);

            for (int i = 0; i <= s.length() - n * m; i++) {  // 找到匹配的话最后的终点肯定是s的长度减去words里面的总长度
                HashMap<String, Integer> copy = new HashMap<>(map);
                int k = n, j = i;  // 每次遍历一次都要看当前的
                while (k > 0) {
                    String str = s.substring(j, j + m);  // 判断当前单词是否属于words里面的单词
                    if (!copy.containsKey(str) || copy.get(str) < 1) break;  // 如果没有包含，或者出现的次数过多，直接跳过
                    copy.put(str, copy.get(str) - 1);  // 如果包含了，把当前单词的数量减一
                    k --;  // 要找的总单词数量减一
                    j += m;  // 越过当前找到的单词
                }
                if (k == 0) res.add(i);
            }
            return res;
        }
    }
    /*
    寻找字符串满足某个条件的子串，考虑双指针+滑动窗口思想（右指针一直前进，当遇到某个条件成立/不成立，更新左指针，然后右指针接着前进）。
     */

    // 340 至多包含k个不同字符的最长子串

    /**
     * 给定一个字符串s,找出至多包含k个不同字符的最长子串T  sliding window
     *
     * example1:
     * input: s = "eceba", k = 2
     * output: 3
     * explaination: T is "ece", the length is 3
     */
    class solution340{
        public int lengthOfLongestSubstringKDistinct(String s, int k) {
            if (s == null || s.length() == 0 || k == 0) return 0;
            int left = 0, right = 0, nums = 0/*记录重复的字母*/, res = 0;
            int[] count = new int[256];
            while(right < s.length()) {
                if (count[s.charAt(right++)]++ == 0) nums++;
                if (nums > k) {  // 当重复的字母大于了k时，将左指针向右移，减去相同的字母数nums
                    while (--count[s.charAt(left)] > 0) left++;
                    nums--;
                }
                res = Math.max(res, right - left + 1);
            }
            return res;
        }
    }

    // 49： Group Anagrams
    /**
     * Given an array of strings strs, group the anagrams together. You can return the answer in any order.
     *
     * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase,
     * typically using all the original letters exactly once.

     * Example 1:
     *
     * Input: strs = ["eat","tea","tan","ate","nat","bat"]
     * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
     * Example 2:
     *
     * Input: strs = [""]
     * Output: [[""]]
     * Example 3:
     *
     * Input: strs = ["a"]
     * Output: [["a"]]
     *
     */

    class Solution49 {
        public List<List<String>> groupAnagrams(String[] strs) {

            HashMap<String, List<String>> map = new HashMap<>();
            for (String str : strs) {
                int[] count = new int[26];
                for (Character ch : str.toCharArray()) {
                    count[ch - 'a'] ++;  // 统计每个字母出现的次数
                }
                String s = "";
                for (int i = 0; i < count.length; i ++) {
                    // 去数出现过的字母比如说 ababc将被转化成2a2b1c
                    if (count[i] != 0) s += String.valueOf(count[i]) + String.valueOf((char) (i + 'a'));
                }
                if (map.containsKey(s)) {  // 如果之前已经有这个key值了，将这个新的str加入lsit
                    List<String> list = map.get(s);
                    list.add(str);
                } else {  // 如果第一次出现，将这个s和list添加到map里
                    List<String> list = new ArrayList<>();
                    list.add(str);
                    map.put(s, list);
                }
            }
            return new ArrayList<>(map.values());
        }
    }

    // 438 find all Anagrams in a StringAnagram
    /**
     * Given two strings s and p, return an array of all the start indices of p's anagrams in s. You may return the answer in any order.
     *
     * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.
     *
     *
     *
     * Example 1:
     *
     * Input: s = "cbaebabacd", p = "abc"
     * Output: [0,6]
     * Explanation:
     * The substring with start index = 0 is "cba", which is an anagram of "abc".
     * The substring with start index = 6 is "bac", which is an anagram of "abc".
     * Example 2:
     *
     * Input: s = "abab", p = "ab"
     * Output: [0,1,2]
     * Explanation:
     * The substring with start index = 0 is "ab", which is an anagram of "ab".
     * The substring with start index = 1 is "ba", which is an anagram of "ab".
     * The substring with start index = 2 is "ab", which is an anagram of "ab".
     */
    class Solution438 {
        public List<Integer> findAnagrams(String s, String p) {
            List<Integer> res = new ArrayList<>();
            if (s == null || s.length() == 0 || p.length() == 0) return res;
            int[] chars = new int[26];
            for (Character ch : p.toCharArray()) {
                chars[ch - 'a'] ++;
            }
            int start = 0, end = 0;
            int count = p.length();  // 用count来记录是否该添加进res里，当count==0时代表找到了
            while (end < s.length()) {
                // 当end-start等于p的长 和 当前start位置的字母的数量大于0时（代表是p里面出现的字符），start位置的数字的数量加一，start向右走，count++(相当于restore一下)
                if (end - start == p.length() && chars[s.charAt(start ++) - 'a']++ >= 0) count++;
                // 如果当前end位置的字母减一还大于等于0，说明找到了p中的字母，count--
                if (--chars[s.charAt(end++) - 'a'] >= 0) count--;
                if (count == 0) res.add(start);
            }
            return res;
        }
    }

    // 9: Palindrome Number
    /**
     * Given an integer x, return true if x is palindrome integer.

     An integer is a palindrome when it reads the same backward as forward.

     For example, 121 is a palindrome while 123 is not.


     Example 1:

     Input: x = 121
     Output: true
     Explanation: 121 reads as 121 from left to right and from right to left.
     Example 2:

     Input: x = -121
     Output: false
     Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
     *
     */
    class Solution9 {
        public boolean isPalindrome(int x) {
            if(x < 0 || (x % 10 == 0 && x != 0)) return false;
            if(x > 0 && x <= 9) return true;
            int palind = x;
            int rev = 0;

            while(x > 0) {
                rev = rev * 10 + x % 10;
                x /= 10;
            }
            return palind == rev;
        }
    }

    // 409 longest palindrome

    /**
     * Given a string s which consists of lowercase or uppercase letters, return the length of the longest palindrome
     * that can be built with those letters.
     *
     * Letters are case sensitive, for example, "Aa" is not considered a palindrome here.
     *
     * Example 1:
     *
     * Input: s = "abccccdd"
     * Output: 7
     * Explanation: One longest palindrome that can be built is "dccaccd", whose length is 7.
     * Example 2:
     *
     * Input: s = "a"
     * Output: 1
     * Explanation: The longest palindrome that can be built is "a", whose length is 1.
     */
    class Solution4091{
        public int longestPalindrome (String s) {
            if (s == null || s.length() == 0) return 0;
            HashSet<Character> set = new HashSet<>();  // 用一个set来记录相同字母出现的奇偶数
            int count = 0;
            for (char c : s.toCharArray()) {
                // 如果之前加过这个字母了，说明出现了偶数个，count++
                if (set.contains(c)) {
                    set.remove(c);
                    count++;
                } else set.add(c);
            }
            // 到最后如果set里面剩下了，出现了奇数个的，就加一
            if (set.size() != 0) return count * 2 + 1;
            return count * 2;
        }

        public int longestPalindrome1(String s) {
            int[] count = new int[256];
            int res = 0;
            for (char ch : s.toCharArray()) {
                count[ch] ++;
            }
            for (int i : count) {
                res += i / 2 * 2;
                if (res % 2 == 0 && i % 2 == 1) res ++;

            }
            return res;
        }
    }

    // 266：palindrome permutation

    /**
     * Given a string, determine if a permutations of the string could form a palindrome
     * 出现一次的字母有一个，其他的都是两个
     * 或者都是偶数个
     */
    class Solution266{
        // HashSet:space: o(n)
        public boolean canPermutePalindrome(String s) {
            HashSet<Character> set = new HashSet<>();
            for (char c : s.toCharArray()) {
                if (set.contains(c)) set.remove(c);
                else set.add(c);
            }
            // set的size等于1代表出现了一个奇数，其他都是偶数，等于0代表全是偶数
            return set.size() <= 1;
        }

        public boolean canPermutePalindrome1(String s) {
            char[] count = new char[256];
            int res = 0;
            for (char c : s.toCharArray()) {
                if (count[c] > 0) {
                    // 如果出现这个字符，就--
                    count[c] --;
                } else {
                    count[c] ++;
                }
            }
            // 正常如果可以有回文出现，那只能有1个或者0个位置不是0（最多只能出现一个奇数个的字母）
            for (int i = 0; i < count.length; i++) {
                if (count[i] != 0) res ++;
            }
            return res <= 1;
        }
    }

    // 214：shortest palindrome
    /**
     * You are given a string s. You can convert s to a palindrome by adding characters in front of it.
     *
     * Return the shortest palindrome you can find by performing this transformation.
     *
     * Example 1:
     *
     * Input: s = "aacecaaa"
     * Output: "aaacecaaa"
     * Example 2:
     *
     * Input: s = "abcd"
     * Output: "dcbabcd"
     */
    class Solution214 {
        public String shortestPalindrome(String s) {
            // i  控制的是起始，j控制的是最后的指针，e控制的是要加的字符到底从哪里开始
            int i = 0, j = s.length() - 1;
            int end = s.length() - 1;
            char[] chars = s.toCharArray();
            while (i < j) {
                // 如果i和j对应的字符相等，这两个地方可以构成一个回文字符，直接向中间走
                if (chars[i] == chars[j]) {
                    j --;
                    i ++;
                } else {
                    i = 0;
                    end --;
                    j = end;
                }
            }
            return new StringBuilder(s.substring(end + 1)).reverse().toString() + s;
        }
    }

    // 5: longest palindrome subString
    /**
     * Given a string s, return the longest palindromic substring in s.
     *
     *
     *
     * Example 1:
     *
     * Input: s = "babad"
     * Output: "bab"
     * Explanation: "aba" is also a valid answer.
     * Example 2:
     *
     * Input: s = "cbbd"
     * Output: "bb"
     */
    class Solution51 {
        public String longestPalindrome(String s) {
            if (s == null || s.length() == 0) return s;
            String res = "";
            int max = 0;
            boolean[][] dp = new boolean[s.length()][s.length()];  // 起点到中点是否为palindrome，是否为最大的palindrome
            for (int j = 0; j < s.length(); j ++) {
                for (int i = 0; i <= j; i++) {
                    dp[i][j] = s.charAt(i) == s.charAt(j) && ((j - i <= 2) || dp[i + 1][j - 1]);
                    //         两端的字母相同                   只剩下三个字母      中间剩下的字母，i向右走一个和j向左走一个相同
                    if(dp[i][j]) {
                        if (j - i + 1 > max) {  // 更新max值
                            max = j - i + 1;
                            res = s.substring(i , j + 1);
                        }
                    }
                }
            }
            return res;
        }

        // 中心扩散法
        String res = "";
        public String longestPalindrome1(String s) {
            if (s == null || s.length() == 0) return s;
            for (int i = 0; i < s.length(); i ++) {
                // 回文的长度是奇数
                helper(s, i, i);
                // 回文的长度是偶数
                helper(s, i, i + 1);
            }
            return res;
        }

        // 以left和right为中心，求回文字符串的数量
        public void helper(String s, int left, int right)  {
            while(left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                left --;
                right ++;
            }
            String cur = s.substring(left + 1, right);
            if (cur.length() > res.length()) res =cur;
        }
    }

    // 336: palindrome pairs
    /**
     * Given a list of unique words, return all the pairs of the distinct indices (i, j) in the given list,
     * so that the concatenation of the two words words[i] + words[j] is a palindrome.

     * Example 1:
     *
     * Input: words = ["abcd","dcba","lls","s","sssll"]
     * Output: [[0,1],[1,0],[3,2],[2,4]]
     * Explanation: The palindromes are ["dcbaabcd","abcddcba","slls","llssssll"]
     * Example 2:
     *
     * Input: words = ["bat","tab","cat"]
     * Output: [[0,1],[1,0]]
     * Explanation: The palindromes are ["battab","tabbat"]
     */
    class Solution336 {
        public List<List<Integer>> palindromePairs(String[] words) {
            List<List<Integer>> res = new ArrayList<>();
            if (words == null || words.length < 2) return res;
            // 用map 把index和单词存进去
            HashMap<String, Integer>map = new HashMap<>();
            for (int i = 0 ; i < words.length; i++) {
                map.put(words[i], i);
            }
            // 遍历每个单词
            for (int i = 0; i < words.length; i++) {
                // 遍历单词的每个字母
                for (int j = 0; j <= words[i].length(); j++) {
                    // 拆分单词，看看有没有回文
                    String str1 = words[i].substring(0 , j);
                    String str2 = words[i].substring(j);
                    // 判断前半段是不是回文
                    if (isPalindrome(str1)){
                        // 将str2倒置，
                        String str2rvs = new StringBuilder(str2).reverse().toString();
                        // 如果map里面有str2的反向，而且不等于str1的话
                        if (map.containsKey(str2rvs) && map.get(str2rvs) != i) {
                            res.add(Arrays.asList(map.get(str2rvs), i));
                        }
                    }
                    if (str2.length() != 0 && isPalindrome(str2)) {
                        String str1rvs = new StringBuilder(str1).reverse().toString();
                        if (map.containsKey(str1rvs) && map.get(str1rvs) != i) {
                            res.add(Arrays.asList(i, map.get(str1rvs)));
                        }
                    }
                }
            }
            return res;
        }

        public boolean isPalindrome(String s) {
            int left = 0;
            int right = s.length() - 1;
            while(left <= right) {
                if (s.charAt(left) != s.charAt(right)) return false;
                left++;
                right --;
            }
            return true;
        }
    }

    // 131： Palindrome partition
    /**
     * Given a string s, partition s such that every substring of the partition is a palindrome. Return all possible
     * palindrome partitioning of s.
     *
     * A palindrome string is a string that reads the same backward as forward.
     *
     *
     *
     * Example 1:
     *
     * Input: s = "aab"
     * Output: [["a","a","b"],["aa","b"]]
     * Example 2:
     *
     * Input: s = "a"
     * Output: [["a"]]
     *
     * time: O(2^n)
     */
    class Solution131 {
        public List<List<String>> partition(String s) {
            List<List<String>> res = new ArrayList<>();
            if (s == null || s.length() == 0) return res;
            helper(s, res, new ArrayList<>(), 0);
            return res;
        }

        private void helper(String s, List<List<String>> res, List<String> temp, int index) {
            if (index >= s.length()) {
                res.add(new ArrayList<>(temp));
                return;
            }

            for (int i = index; i < s.length(); i++) {
                if (!isPalindrome(s.substring(index, i + 1))) continue;
                temp.add(s.substring(index, i +1));
                helper(s, res, temp, i + 1);
                temp.remove(temp.size() -1);
            }
        }

        private boolean isPalindrome(String s) {
            int left = 0, right = s.length() - 1;
            while(left < right) {
                if (s.charAt(left++) != s.charAt(right--)) return false;
            }
            return true;
        }
    }


    // 132: Palindrome partition II
    /**
     * Given a string s, partition s such that every substring of the partition is a palindrome.
     *
     * Return the minimum cuts needed for a palindrome partitioning of s.
     * Example 1:
     *
     * Input: s = "aab"
     * Output: 1
     * Explanation: The palindrome partitioning ["aa","b"] could be produced using 1 cut.
     * Example 2:
     *
     * Input: s = "a"
     * Output: 0
     * Example 3:
     *
     * Input: s = "ab"
     * Output: 1
     */
    class Solution132{

        /**
         * 判断回文用dp
         * [][] isPalindrome: 从i到j是否为回文
         * 判断字符串前i个字符构成的子串能分割的最少次数，只需返回cuts[len - 1]
         * [] cuts
         * 如果子串从[0..i]是回文，就不同分隔，即cuts[i] = 0
         *
         * s.charAt(i) == s.charAt(j) && isPalindrome[i + 1][j - 1]
         *
         */
        public int minCut(String s) {
            if (s == null || s.length() == 0) return 0;
            int len = s.length();
            int[] cuts = new int[len];
            //  判断字符串[j..i]是否为回文
            boolean[][] isPalindrome = new boolean[len][len];
            for (int i = 0; i < len; i++) {
                int min = i;  // 初始化是有几个字符就切几刀
                for (int j = 0; j <= i; j++) {
                    if (s.charAt(i) == s.charAt(j) && (i - j <= 2 || isPalindrome[j + 1][i - 1])) {
                        isPalindrome[j][i] = true;
                        // 如果前面的字母是回文，
                        min = j == 0 ? 0 : Math.min(min, cuts[j - 1] + 1);
                    }
                }
                cuts[i] = min;
            }
            return cuts[len - 1];
        }
    }

    // 267: Palindrome Permutation II (Backtracking)
    /**
     * Given a string s, return all the palindrome permutations (without duplicates) of it, return an empty list if no
     * palindromes are found
     *
     * given s= "aabb", return ["abba", "baab"]
     */
    class Solution267{
        /**
         * 如果奇数个的字母出现次数多于1的话肯定构成不了回文
         *
         */
        public List<String> generatePalindrome(String s) {
            int odd = 0;
            // 中间的字母，如果有一个字母出现了基数次，就为那个字母，如果全为偶数次，就为空
            String mid = "";
            List<String> res = new ArrayList<>();
            // 重新排列的list
            List<Character> list = new ArrayList<>();
            // 用map统计字母出现的次数
            HashMap<Character, Integer> map = new HashMap<>();
            for (int i = 0; i < s.length(); i ++) {
                char c = s.charAt(i);
                map.put(c, map.getOrDefault(c, 0)  + 1);
                odd += map.get(c) % 2 != 0 ? 1 : -1;  // 统计这个字母出现的是奇数次还是偶数次
            }
            // 如果奇数个字母的数量大于1，不能构成回文
            if(odd > 1) return res;

            for (Map.Entry<Character, Integer> entry : map.entrySet()) {
                char key = entry.getKey();
                int val = entry.getValue();
                // 如果出现次数是奇数，中间的字母就是这个
                if (val % 2 != 0) mid += key;
                // 即使出现了偶数次，只放入list中一半的数量，另一半直接翻转就可以了
                for (int i = 0; i < val / 2; i ++) {
                    list.add(key);
                }
            }
            helper(list, mid, new boolean[list.size()], new StringBuilder(), res);
            return res;
        }

        public void helper(List<Character> list, String mid, boolean[] used, StringBuilder sb, List<String> res) {
            // list中的所有元素在sb中都用了时为终止条件
            if (sb.length() == list.size()) {
                res.add(sb.toString() + mid + sb.reverse().toString());
                sb.reverse();  // 上一步翻转了，这里重置sb
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                // 去重，当第i个字母和i-1个相等时并且第i-1个没用过时，跳过
                if (i > 0 && list.get(i) == list.get(i - 1) && !used[i - 1]) continue;
                if (!used[i]) {
                    used[i] = true;
                    sb.append(list.get(i));
                    helper(list, mid, used, sb, res);
                    used[i] = false;
                    sb.deleteCharAt(sb.length() - 1);
                }
            }
        }
    }


    // 443：String Compression
    /**
     * Given an array of characters chars, compress it using the following algorithm:
     *
     * Begin with an empty string s. For each group of consecutive repeating characters in chars:
     *
     * If the group's length is 1, append the character to s.
     * Otherwise, append the character followed by the group's length.
     * The compressed string s should not be returned separately, but instead, be stored in the input character array
     * chars. Note that group lengths that are 10 or longer will be split into multiple characters in chars.
     *
     * After you are done modifying the input array, return the new length of the array.
     *
     * You must write an algorithm that uses only constant extra space.
     *
     *
     *
     * Example 1:
     *
     * Input: chars = ["a","a","b","b","c","c","c"]
     * Output: Return 6, and the first 6 characters of the input array should be: ["a","2","b","2","c","3"]
     * Explanation: The groups are "aa", "bb", and "ccc". This compresses to "a2b2c3".
     * Example 2:
     *
     * Input: chars = ["a"]
     * Output: Return 1, and the first character of the input array should be: ["a"]
     * Explanation: The only group is "a", which remains uncompressed since it's a single character.
     * Example 3:
     *
     * Input: chars = ["a","b","b","b","b","b","b","b","b","b","b","b","b"]
     * Output: Return 4, and the first 4 characters of the input array should be: ["a","b","1","2"].
     * Explanation: The groups are "a" and "bbbbbbbbbbbb". This compresses to "ab12".
     */
    class Solution443 {
        public int compress(char[] chars) {
            int res = 0, index = 0;  // res记录当前的位置， index记录遍历到字母的位置
            while (index < chars.length) {
                char cur = chars[index];
                int count = 0;
                while(index < chars.length && chars[index] == cur) {
                    index ++;  // 是重复的数一下有多少个
                    count++;
                }
                chars[res ++] = cur;  // 替换
                if (count != 1) {
                    for (char c : String.valueOf(count).toCharArray()) {
                        chars[res ++] = c;  // 数字的替换
                    }
                }
            }
            return res;
        }
    }

    // 273: Integer To English Words  考的次数不占少数
    /**
     * Convert a non-negative integer num to its English words representation.
     *
     *
     *
     * Example 1:
     *
     * Input: num = 123
     * Output: "One Hundred Twenty Three"
     * Example 2:
     *
     * Input: num = 12345
     * Output: "Twelve Thousand Three Hundred Forty Five"
     * Example 3:
     *
     * Input: num = 1234567
     * Output: "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
     */
    class Solution273 {

        String[] less20 = new String[]{"", "One","Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven","Twelve","Thirteen", "Fourteen", "Fifteen", "Sixteen",
        "Seventeen", "Eighteen", "Nineteen"};
        String[] tens = new String[]{"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
        String[] thousands = new String[]{"", "Thousand", "Million", "Billion"};

        /*
        1 2 3 4 5: Twelve thousand three hundred forty five
         */
        public String numberToWords(int num) {
            if (num == 0) return "Zero";
            String res = "";
            int i = 0;
            while(num > 0) {
                if (num % 1000 != 0) {
                    res = helper(num % 1000) + thousands[i] + " " +res;  // helper(345)
                }
                num /= 1000;
                i++;
            }
            return res.trim();
        }

        private String helper(int num ) {
            if (num == 0) return "";
            if (num < 20) {
                return less20[num % 20] + " ";
            } else if (num < 100) {
                return tens[num / 10] + " " + helper(num % 10);
            } else {
                return less20[num / 100] + " Hundred " + helper(num % 100);
            }
        }
    }

    // 395： longest Substring with at least k repeating characters
    /**
     * Given a string s and an integer k, return the length of the longest substring of s such that the frequency of
     * each character in this substring is greater than or equal to k.
     *
     * Example 1:http://localhost:8000
     *
     * Input: s = "aaabb", k = 3
     * Output: 3
     * Explanation: The longest substring is "aaa", as 'a' is repeated 3 times.
     * Example 2:
     *
     * Input: s = "ababbc", k = 2
     * Output: 5
     * Explanation: The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
     */
    class Solution395 {
        public int longestSubstring(String s, int k) {
            int res = 0;
            for (int numUniqueTarget = 1; numUniqueTarget <= 26; numUniqueTarget++) {
                res = Math.max(res, helper(s, k, numUniqueTarget));  // 只有26个字母，遍历26次，每次允许出现多一个字母
            }
            return res;
        }

        public int helper(String s, int k, int numUniqueTarget) {
            int [] count = new int[128];  // 记录出现字母的个数
            int start = 0, end = 0;  // 头指针和尾指针
            int numUnique = 0, res = 0, numNoLessThanK = 0;  // 出现不同字母的种类，结果，出现次数比K大的数

            while (end < s.length()) {
                if (count[s.charAt(end)]++ == 0) numUnique++;  // 如果尾指针的字母出现次数为0，出现的字母种类加一
                if (count[s.charAt(end++)] == k) numNoLessThanK ++;  // 如果尾指针出现的字母个数等于k了，尾指针向后移（都会执行），numNoLessThanK加一
                while (numUnique > numUniqueTarget) {  // 当出现不同字母的种类大于允许出现的字母种类时，要尝试start向后走
                    if(count[s.charAt(start)]-- == k) numNoLessThanK--;  // 如果start指针出现的数量等于k，减小numNoLessThanK的个数
                    if (count[s.charAt(start++)] == 0) numUnique--;  // 如果start指针的字母出现的数量等于0，出现的字母种类减一
                }
                if (numUnique == numUniqueTarget && numUnique == numNoLessThanK) {
                    res = Math.max(end - start, res);
                }
            }
            return res;
        }
    }

    // 1143 Longest Common Subsequence
    /**
     * Given two strings text1 and text2, return the length of their longest common subsequence. If there is no
     * common subsequence, return 0.
     *
     * A subsequence of a string is a new string generated from the original string with some characters (can be none)
     * deleted without changing the relative order of the remaining characters.
     *
     * For example, "ace" is a subsequence of "abcde".
     * A common subsequence of two strings is a subsequence that is common to both strings.
     *
     * Example 1:
     *
     * Input: text1 = "abcde", text2 = "ace"
     * Output: 3
     * Explanation: The longest common subsequence is "ace" and its length is 3.
     * Example 2:
     *
     * Input: text1 = "abc", text2 = "abc"
     * Output: 3
     * Explanation: The longest common subsequence is "abc" and its length is 3.
     * Example 3:
     *
     * Input: text1 = "abc", text2 = "def"
     * Output: 0
     * Explanation: There is no such common subsequence, so the result is 0.
     *
     */
    class Solution1143 {
        public int longestCommonSubsequence(String text1, String text2) {
            int m=text1.length();
            int n=text2.length();
            int[][] dp=new int[m+1][n+1];

            for(int i=1;i<=m;i++){
                for(int j=1;j<=n;j++){
                    if(text1.charAt(i-1)==text2.charAt(j-1))
                        dp[i][j]=dp[i-1][j-1]+1;
                    else
                        dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
            return dp[m][n];
        }
    }

    public int longestPalindrome(String[] words) {
        HashMap<String, Integer> same = new HashMap<>();
        HashMap<String, Integer> dif = new HashMap<>();
        int res = 0;

        for (int i = 0; i < words.length; i ++) {
            String s = words[i];
            if (s.equals("#?")) continue;
            if (s.charAt(0) == s.charAt(1)) {
                same.put(s, same.getOrDefault(s, 0)+1);
                continue;
            }
            for (int j = i + 1; j < words.length; j ++) {
                String t = words[j];
                if (s.charAt(0) == t.charAt(1) && s.charAt(1) == t.charAt(0)) {
                    dif.put(s, dif.getOrDefault(s, 0)+1);
                    words[j] = "#?";
                    break;
                }
            }
        }

        boolean mid = false;
        for (Map.Entry<String, Integer> entry : same.entrySet()) {
            int time = entry.getValue();
            if (time % 2 == 1) mid = true;
            res += 2 * (time / 2 * 2);
        }
        for (Map.Entry<String, Integer> entry : dif.entrySet()) {
            res += 4 * entry.getValue();
        }
        if (mid) res += 2;

        return res;
    }

    // 159 Longest Substring with At Most Two Distinct Characters

    /**
     * Given a string s, return the length of the longest substring that contains at most two distinct characters.
     *
     *
     *
     * Example 1:
     *
     * Input: s = "eceba"
     * Output: 3
     * Explanation: The substring is "ece" which its length is 3.
     * Example 2:
     *
     * Input: s = "ccaabbb"
     * Output: 5
     * Explanation: The substring is "aabbb" which its length is 5.
     */
    class Solution159 {
        public int lengthOfLongestSubstringTwoDistinct(String s) {
            int res = 0;
            if (s == null || s.length() == 0) return res;
            // 经典滑动窗口
            int left = 0, right = 0;
            HashMap<Character, Integer> map = new HashMap<>();

            while (right < s.length()) {
                map.put(s.charAt(right), right++);  // 先加入右边的字母
                if (map.size() > 2) {  // 如果存的字母超过2了
                    int leftMost = s.length();
                    for (int val : map.values()) {  // 找到最左的字母
                        leftMost = Math.min(leftMost, val);
                    }
                    map.remove(s.charAt(leftMost));  // 移除掉
                    left = leftMost + 1;
                }
                res = Math.max(res, right - left);
            }
            return res;
        }
    }

    // 243 shortest word distance

    /**
     * Given an array of strings wordsDict and two different strings that already exist in the array word1 and word2,
     * return the shortest distance between these two words in the list.
     *
     *Input: wordsDict = ["practice", "makes", "perfect", "coding", "makes"], word1 = "coding", word2 = "practice"
     * Output: 3
     * Example 2:
     *
     * Input: wordsDict = ["practice", "makes", "perfect", "coding", "makes"], word1 = "makes", word2 = "coding"
     * Output: 1
     */
    class Solution243 {
        public int shortestDistance(String[] wordsDict, String word1, String word2) {
            int res = wordsDict.length;
            int a = -1, b = -1;
            for (int i = 0; i < wordsDict.length; i++) {
                if (wordsDict[i].equals(word1)) a = i;
                else if (wordsDict[i].equals(word2)) b = i;
                if (a != -1 && b != -1) res = Math.min(res, Math.abs(a - b));
            }
            return res;
        }
    }


    // 246: Strobogrammatic Number

    /**
     * Given a string num which represents an integer, return true if num is a strobogrammatic number.
     *
     * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
     *
     * Example 1:
     *
     * Input: num = "69"
     * Output: true
     * Example 2:
     *
     * Input: num = "88"
     * Output: true
     * Example 3:
     *
     * Input: num = "962"
     * Output: false
     */

    class Solution246 {

        HashMap<Character, Character> map = new HashMap<>();

        public boolean isStrobogrammatic(String num) {
            map.put('6', '9');
            map.put('8', '8');
            map.put('1', '1');
            map.put('9', '6');
            map.put('0', '0');
            int left = 0, right = num.length() - 1;
            while (left < right) {
                if (!map.containsKey(num.charAt(left))) return false;
                if (map.get(num.charAt(left)) != num.charAt(right)) return false;
                left++;
                right--;
            }
            return true;
        }
    }

    // 248: Strobogrammatic Number II

    /**
     * Given an integer n, return all the strobogrammatic numbers that are of length n. You may return the answer in any order.
     *
     * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
     *
     *
     *
     * Example 1:
     *
     * Input: n = 2
     * Output: ["11","69","88","96"]
     * Example 2:
     *
     * Input: n = 1
     * Output: ["0","1","8"]
     *
     * 00 010 0110 不合法
     * %010%可以
     *
     */
    class solution248{
        public List<String> findStroboggrammatic(int n) {
            return helper(n, n);
        }

        public List<String> helper(int n, int m) {
            if (n == 0) return new ArrayList<>(Arrays.asList(""));
            if (n == 1) return new ArrayList<>(Arrays.asList("0", "1", "8"));

            List<String> list = helper(n -2, m);  // n: 当前backtracking进行到了哪里
            List<String> res = new ArrayList<>();

            for (int i = 0; i < list.size(); i++) {
                String s= list.get(i);
                if (n != m) {
                    res.add("0" + s + "0");
                }
                res.add("1" + s + "1");
                res.add("6" + s + "9");
                res.add("9" + s + "6");
                res.add("8" + s + "8");
            }
            return res;
        }

    }

    // 249: group shifted Strings

    /**
     * We can shift a string by shifting each of its letters to its successive letter.
     *
     * For example, "abc" can be shifted to be "bcd".
     * We can keep shifting the string to form a sequence.
     *
     * For example, we can keep shifting "abc" to form the sequence: "abc" -> "bcd" -> ... -> "xyz".
     * Given an array of strings strings, group all strings[i] that belong to the same shifting sequence. You may
     * return the answer in any order.
     *
     * Example 1:
     *
     * Input: strings = ["abc","bcd","acef","xyz","az","ba","a","z"]
     * Output: [["acef"],["a","z"],["abc","bcd","xyz"],["az","ba"]]
     * Example 2:
     *
     * Input: strings = ["a"]
     * Output: [["a"]]
     */
    class Solution249 {
        public List<List<String>> groupStrings(String[] strings) {
            List<List<String>> res = new ArrayList<>();
            HashMap<String, List<String>> map = new HashMap<>();
            for (String s : strings) {
                String idx = getIdx(s);
                List<String> pre = map.getOrDefault(idx, new ArrayList<>());
                pre.add(s);
                map.put(idx, pre);
            }
            return new ArrayList<>(map.values());
        }

        public String getIdx(String s) {
            String res = "";
            char[] arr = s.toCharArray();
            for (int i = 1; i < arr.length; i++) {
                int diff = arr[i] - arr[i - 1];  // 计算当前的字母与上一个字母的差值
                res += diff < 0 ? diff + 26 : diff;  // 如果差值小于0就加上26
                res += ",";  // 最后是以 比如说 abf就是 1,4 这种
            }
            return res;
        }

    }

    // 179： largest Number

    /**
     * Given a list of non-negative integers nums, arrange them such that they form the largest number and return it.
     *
     * Since the result may be very large, so you need to return a string instead of an integer.
     *
     * Example 1:
     *
     * Input: nums = [10,2]
     * Output: "210"
     * Example 2:
     *
     * Input: nums = [3,30,34,5,9]
     * Output: "9534330"
     *
     */
    class Solution179 {
        public String largestNumber(int[] nums) {
            if (nums ==null || nums.length == 0) return "";
            String[] res = new String[nums.length];
            for (int i = 0; i< nums.length; i ++) {
                res[i] = String.valueOf(nums[i]);
            }
            Arrays.sort(res, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String s1 = o1 + o2;
                    String s2 = o2 + o1;
                    return s2.compareTo(s1);
                }
            });
            if (res[0].charAt(0) == '0') {
                return "0";
            }
            StringBuilder sb = new StringBuilder();
            for (String s: res) {
                sb.append(s);
            }
            return sb.toString();

        }
    }

    // 833 Find and replace in string

    /**
     * You are given a 0-indexed string s that you must perform k replacement operations on. The replacement operations
     * are given as three 0-indexed parallel arrays, indices, sources, and targets, all of length k.
     *
     * To complete the ith replacement operation:
     *
     * Check if the substring sources[i] occurs at index indices[i] in the original string s.
     * If it does not occur, do nothing.
     * Otherwise if it does occur, replace that substring with targets[i].
     * For example, if s = "abcd", indices[i] = 0, sources[i] = "ab", and targets[i] = "eee", then the result of this
     * replacement will be "eeecd".
     *
     * All replacement operations must occur simultaneously, meaning the replacement operations should not affect the
     * indexing of each other. The testcases will be generated such that the replacements will not overlap.
     *
     * For example, a testcase with s = "abc", indices = [0, 1], and sources = ["ab","bc"] will not be generated because
     * the "ab" and "bc" replacements overlap.
     * Return the resulting string after performing all replacement operations on s.
     *
     * A substring is a contiguous sequence of characters in a string.
     */
    class Solution833 {
        public String findReplaceString(String s, int[] indices, String[] sources, String[] targets) {
            StringBuilder sb = new StringBuilder();
            HashMap<Integer, String[]> map = new HashMap<>();
            for (int i = 0; i < indices.length; i++) {
                map.put(indices[i], new String[]{sources[i], targets[i]});
            }

            for (int i = 0; i < s.length(); i++) {
                if (map.containsKey(i)) {
                    int sourceLen = i + map.get(i)[0].length();
                    if (s.substring(i, sourceLen).equals(map.get(i)[0])) {
                        sb.append(map.get(i)[1]);
                        i += map.get(i)[0].length() - 1;
                        continue;
                    }
                }
                sb.append(s.charAt(i) + "");
            }
            return sb.toString();
        }
    }

    /**
     * Missing words from subsequentString
     *
     * s = "I love programming"
     * t = "love"
     *
     * return a ArrayList: I programming
     */
    public List<String> missingSubsequence(String s, String t) {
        if (t.length() == 0) return Arrays.asList(s.split(" "));
        String[] parts = s.split(" ");
        String[] otherParts = t.split(" ");
        List<String> missing = new ArrayList<>();
        for (int i = 0, j =0; i < parts.length; i++) {
            if (j < otherParts.length && parts[i].equals(otherParts[j])) {
                j++;
            }else{
                missing.add(parts[i]);
            }
        }
        return missing;
    }

    // 1910: Remove All Occurrences of a Substring

    /**
     * Given two strings s and part, perform the following operation on s until all occurrences of the substring part are removed:
     *
     * Find the leftmost occurrence of the substring part and remove it from s.
     * Return s after removing all occurrences of part.
     *
     * A substring is a contiguous sequence of characters in a string.
     *
     * Example 1:
     *
     * Input: s = "daabcbaabcbc", part = "abc"
     * Output: "dab"
     * Explanation: The following operations are done:
     * - s = "daabcbaabcbc", remove "abc" starting at index 2, so s = "dabaabcbc".
     * - s = "dabaabcbc", remove "abc" starting at index 4, so s = "dababc".
     * - s = "dababc", remove "abc" starting at index 3, so s = "dab".
     * Now s has no occurrences of "abc".
     * Example 2:
     *
     * Input: s = "axxxxyyyyb", part = "xy"
     * Output: "ab"
     * Explanation: The following operations are done:
     * - s = "axxxxyyyyb", remove "xy" starting at index 4 so s = "axxxyyyb".
     * - s = "axxxyyyb", remove "xy" starting at index 3 so s = "axxyyb".
     * - s = "axxyyb", remove "xy" starting at index 2 so s = "axyb".
     * - s = "axyb", remove "xy" starting at index 1 so s = "ab".
     * Now s has no occurrences of "xy".
     */
    class Solution1910 {
        public String removeOccurrences(String s, String part) {
            StringBuilder sb = new StringBuilder(s);
            int idx = sb.toString().indexOf(part);
            while (idx != -1) {
                sb.replace(idx, idx + part.length(), "");
                idx = sb.toString().indexOf(part);
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        String[] words = new String[]{"lc","cl","gg"};
        ArrayList sdf = new ArrayList();
        sdf.add("asd");
        sdf.add(2);
        ArrayList<? extends Number> nList = new ArrayList<>();
        nList = new ArrayList<Integer>();
        nList = new ArrayList<Number>();
        nList = new ArrayList<Float>();
        for (Object s : sdf) {
            System.out.println(s);
        }
    }
}
