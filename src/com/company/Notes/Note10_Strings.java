package com.company.Notes;

import java.util.*;

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
     *
     * Given a string s, find the length of the longest substring without repeating characters.
     *
     * Input: s = "abcabcbb"
     * Output: 3
     * Explanation: The answer is "abc", with the length of 3.
     *
     * Input: s = "bbbbb"
     * Output: 1
     * Explanation: The answer is "b", with the length of 1.
     *
     * Input: s = "pwwkew"
     * Output: 3
     * Explanation: The answer is "wke", with the length of 3.
     * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
     */

    class lengthOfLongestSubstring{

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
            for (int i = 1; i < s.length(); i ++) {
                set = new HashSet<>();  // 用set来判断重复元素
                count = 1;
                int left = i - 1;  // left指针指到i之前
                set.add(s.charAt(i));
                while (left >= 0 && !set.contains(s.charAt(left))) {  // 如果set里有了当前的左指针的字母，跳过
                    set.add(s.charAt(left--));
                    count ++;
                }
                res = Math.max(res, count);
            }
            return res;
        }
    }

}
