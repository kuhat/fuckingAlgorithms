package com.company.Notes;

import java.util.*;

public class Note3_Arrays {

    // LeetCode 243: shortestDistance
    // [abcd, abc, ssss, abcd, ssss, wwww]
    public static int shortDistance(String[] strings, String s, String m) {
        int res = strings.length;
        int a = -1;
        int b = -1;
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].equals(s)) {
                a = i;
            } else if (strings[i].equals(m)) {
                b = i;
            }
            if (a != -1 && b != -1) {
                res = Math.min(res, Math.abs(a - b));
            }
        }
        return res;
    }

    // LeetCode 252 Meeting Rooms:

    /**
     * Given an array of meeting time intercals consisting of start and end times [[s1, e1], [s2, e2, ...]] (si < ei), determine if a person could attend all meetings.
     * <p>
     * For example: Given [[0, 30], [5, 10], [15, 20]], return false.
     * <p>
     * + 按start 排序
     * <p>
     * + 解题技巧： 前一个区间 end & 后一个区间  start
     *
     * @param args
     */

    public class Interval {
        int start;
        int end;

        public Interval(int start, int end) {
            start = this.start;
            end = this.end;
        }
    }

    public boolean conAttendMeetings(Interval[] intervals) {
        Arrays.sort(intervals, (x, y) -> x.start - y.start);
        for (int i = 0; i < intervals.length; i++) {
            if (intervals[i - 1].end > intervals[i].start) {
                return false;
            }
        }
        return true;
    }

    //    LeetCode 153: Meeting Rooms 2

    /**
     * Given an array of meeting time intervals consisting of start and end times [[s1, e1]. [s2, e2], ...]
     * [si < ei], find the minimum number of conference rooms required.
     * <p>
     * Example: input: [[0, 30], [5, 10], [15,20]]
     * <p>
     * output: 2
     *
     * @param args
     */

    public int minMeetingRooms(Interval[] intervals) {
        int[] starts = new int[intervals.length];
        int[] ends = new int[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            starts[i] = intervals[i].start;
            ends[i] = intervals[i].end;
        }
        Arrays.sort(starts);  // 将起始时间和结束时间都排序
        Arrays.sort(ends);
        int end = 0;
        int res = 0;
        for (int i = 0; i < intervals.length; i++) {
            if (starts[i] < ends[end]) {
                res++;  // 如果起始时间小于另一个的结束时间，说明需要多一间房
            } else {
                end++;  // 否则比较下一个的结束时间
            }
        }
        return res;
    }

    // LeetCode202: minimumSubArrayLeng

    /**
     * Given an array of positive integers nums and a positive integer target,
     * return the minimal length of a contiguous subarray [numsl, numsl+1, ..., numsr-1, numsr] of
     * which the sum is greater than or equal to target.
     * If there is no such subarray, return 0 instead.
     * <p>
     * Input: target = 7, nums = [2,3,1,2,4,3]
     * Output: 2
     * Explanation: The subarray [4,3] has the minimal length under the problem
     * constraint.
     * <p>
     * sliding window
     *
     * @param nums
     */
    public int minSubArrayLen(int target, int[] nums) {
        int res = Integer.MAX_VALUE;
        int left = 0, sum = 0;  // 滑动窗口，左指针
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];  // 当前的前i项和
            while (left <= i && sum >= target) {  // 如果当前的前i项和大于了target,left指针向后移，减去left的值
                res = Math.min(res, i - left + 1);  // 直到sum值小于target，相当于一个窗口向后走
                sum -= nums[left++];
            }
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }

    // Leetcode 75: sort Colors

    /**
     * Given an array nums with n objects colored red, white, or blue, sort them in-place so that objects of the
     * same color are adjacent, with the colors in the order red, white, and blue.
     * <p>
     * We will use the integers 0, 1, and 2 to represent the color red, white, and blue, respectively.
     * <p>
     * You must solve this problem without using the library's sort function.
     * <p>
     * 1, 0, 2, 1
     * l        r
     * i
     * <p>
     * 1, 0, 2, 1
     * l        r
     * i
     * <p>
     * 0, 1, 2, 1
     * l     r
     * i
     * <p>
     * 0, 1, 1, 2
     * l  r
     * i
     * <p>
     * 0, 1, 1, 2
     * l  r
     * i
     *
     * @param nums
     */

    public static void sortColors(int[] nums) {
        if (nums == null || nums.length == 0) return;
        int left = 0;  // 控制0出现的最后的位置
        int right = nums.length - 1;  //控制1出现的最开始的位置
        int index = 0;
        while (index <= right) {
            if (nums[index] == 0) {
                swap(nums, index++, left++);
            } else if (nums[index] == 1) {
                index++;
            } else {
                swap(nums, index, right--);
            }
        }
    }

    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // LeetCode 283: move Zeros

    /**
     * Given an integer array nums, move all 0's to the end of it while maintaining the relative order of the non-zero elements.
     * <p>
     * Note that you must do this in-place without making a copy of the array.
     * <p>
     * Input: nums = [0,1,0,3,12]
     * Output: [1,3,12,0,0]
     *
     * @param nums
     */

    public static void moveZeros(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        int start = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[start++] = nums[i];
            }
        }

        while (start < nums.length) {
            nums[start++] = 0;
        }
    }

    public void moveZero2(int[] nums) {
        if (nums == null || nums.length == 0) return;
        for (int i = 0, j = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j++] = temp;
            }
        }
    }

    //LeetCode287: Find Duplicate Numbers

    /**
     * Given an array of integers nums containing n + 1 integers where each integer is in the range [1, n] inclusive.
     * <p>
     * There is only one repeated number in nums, return this repeated number.
     * <p>
     * You must solve the problem without modifying the array nums and uses only constant extra space.
     * <p>
     * Input: nums = [1,3,4,2,2]
     * Output: 2
     *
     * @param args
     */

    // 二分法
    public static int findDuplicate(int[] nums) {
        int min = 0;
        int max = nums.length - 1;
        while (min <= max) {
            int mid = (min + max) / 2 + min;
            int count = 0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] <= mid) {
                    count++;
                }
            }
            if (count > mid) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return min;
    }

    // 快慢指针 linked List

    /***
     * 2 1 3 1
     * 0 1 2 3
     *
     * 0 - 2
     * 1 - 1
     * 2 - 3
     * 3 - 1
     *
     * 0 - 2 - 3 -1 - 1 - 1
     *
     * @param nums
     * @return
     */
    public static int findDuplicate2(int[] nums) {
        int slow = nums[0];
        int fast = nums[nums[0]];
        // 找到快慢指针相遇的地方
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }
        //用一个新的指针从头开始，知道和慢指针相遇
        fast = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }

    // leetCode 334 Increasing Triple Subsequence

    /**
     * Given an integer array nums, return true if there
     * exists a triple of indices (i, j, k) such that i < j < k and nums[i] < nums[j] < nums[k].
     * If no such indices exists, return false.
     * <p>
     * Input: nums = [1,2,3,4,5]
     * Output: true
     * Explanation: Any triplet where i < j < k is valid.
     *
     * @param nums
     */

    public static boolean increasingTriple(int[] nums) {
        int min = Integer.MAX_VALUE, sedMin = Integer.MAX_VALUE;
        for (int num : nums) {
            if (num <= min) min = num;
            else if (num < sedMin) {
                sedMin = num;
            } else if (num > sedMin) {
                return true;
            }
        }
        return false;
    }

    //LeetCode Longest Increasing Subsequence 重要！！

    /**
     * Given an integer array nums, return the length of the longest strictly
     * increasing subsequence.
     * <p>
     * A subsequence is a sequence that can be derived from an array by deleting
     * some or no elements without changing the order of the remaining elements.
     * For example, [3,6,2,7] is a subsequence of the array [0,3,1,6,2,2,7].
     *
     * @param nums
     */


    // array: [10, 9, 2, 5, 3, 7, 101 , 18]
    // dp[]:  [1,  1, 1, 2, 2, 3, 4,    4 ]
    public static int lengthOfLTS(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        int res = dp[0];
        for (int i = 0; i < dp.length; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    // 88： mergeSortArray:
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;
        while (i >= 0 && j >= 0) {
            nums1[k--] = nums1[i] >= nums2[j] ? nums1[i--] : nums2[j--];
        }
        while (j >= 0) {
            nums1[k--] = nums2[j--];
        }
    }

    //215：kthlargest 重要！！！

    /**
     * Given an integer array nums and an integer k,
     * return the kth largest element in the array.
     * <p>
     * Note that it is the kth largest element in the sorted order,
     * not the kth distinct element.
     * <p>
     * Input: nums = [3,2,1,5,6,4], k = 2
     * Output: 5
     *
     * @param nums
     * @param k
     * @return
     */
    // 快速排序 -> 快速选择
    public static int kthlargest(int[] nums, int k) {
        if (nums == null || nums.length == 0) return 0;
        int left = 0;
        int right = nums.length - 1;
        while (true) {
            int pos = partition(nums, left, right);
            if (pos + 1 == k) {
                return nums[pos];
            } else if (pos + 1 > k) {
                right = pos - 1;
            } else {
                left = pos + 1;
            }
        }
    }

    // 3, 2, 1, 5, 6, 4    k = 3
    // 0, 1, 2, 3, 4, 5
    // pivot: 3 [3, 2, 1, 5, 6, 4]  4 > 3, 2 < 3, 交换位置
    //          [3, 4, 1, 5, 6, 2]  l: 2, r: 4
    //
    //          [3, 4, 6, 5, 1, 2]  6 > 3, 1 < 3, swap l: 3, r :3
    //          [3, 4, 6, 5, 1, 2]  5 > 3, l: 4, swap: [5, 4, 6 ,3, 1, 2]
    public static int partition(int[] nums, int left, int right) {
        int pivot = nums[left];
        int l = left + 1;
        int r = right;
        while (l <= r) {
            if (nums[l] < pivot && nums[r] > pivot) {  // 从大到小排序
                Swap(nums, l++, r--);
            }
            if (nums[l] >= pivot) l++;
            if (nums[r] <= pivot) r--;
        }
        swap(nums, left, r);
        return r;
    }

    public static void Swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // priority quque time: o(nlogk) space:o(n)
    public static int findKthLargest2(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (Integer num : nums) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        return minHeap.poll();
    }

    // 462 Minimum Moves to Equal Array Elements

    /**
     * Given an integer array nums of size n, return the minimum number of moves required to
     * make all array elements equal.
     * <p>
     * In one move, you can increment or decrement an element of the array by 1.
     * <p>
     * Test cases are designed so that the answer will fit in a 32-bit integer.
     *
     * @param nums
     */

    // time: o(nlogn) space:o(1)
    public static int minMove1(int[] nums) {
        Arrays.sort(nums);
        int i = 0;
        int j = nums.length - 1;
        int res = 0;
        while (i < j) {
            res += nums[j--] - nums[i++];
        }
        return res;
    }

    // 快速排序找到中点值然后计算每个数到中点的距离
    public static int minMove2(int[] nums) {
        int res = 0;
        int median = findKthLargest2(nums, nums.length / 2 + 1); // leetcode 215

        for (int num : nums) {
            res += Math.abs(median - num);
        }
        return res;
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
     * @param args
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
            if (count[ch - 'a'] == 0)
                return false;  // 如果magazine里面的字母个数不够用返回 false
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
        for (int val : fmap)
            if (val != 0) return false;
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
     * @param args
     */

    public static int findSubStringInWrapAround(String p) {
        int[] count = new int[26];
        int curMax = 0;
        for (int i = 0; i < p.length(); i++) {
            if (i > 0 && (p.charAt(i) - p.charAt(i - 1) == 1) || (p.charAt(i - 1) - p.charAt(i) == 25)) {
                curMax++;
            } else {
                curMax = 1;
            }
            count[p.charAt(i) - 'a'] = Math.max(curMax, count[p.charAt(i) - 'a']);
        }
        int res = 0;
        ;
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

    public static String minWindow(String s, String t) {
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
     * @param args
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

    // 15: three sum

    /**
     * 给你一个包含n个整数的数组nums，判断nums中是否存在三个元素a，b，c，使得 a+b+c=0？请你找出所有和为0且不重复的三元组。
     * <p>
     * 输入：n u m s = [-1 , 0 , 1 , 2 ,-1 ,-4 ] 输出：[ [-1 ,-1 , 2 ] , [-1 , 0 , 1 ] ]
     *
     * @param args
     */

    // 双指针：
    /*
    我们这样来思考一下，如果数组是排序的，3个数字我们固定一个，就变成了从有序数 组中选择两个数字，让他的和等于一个固定的值了。
    这样就可以使用双指针了。

    但这里有 可能会出现重复的结果，所以我们需要过滤掉重复的结果。怎么过滤，我们来思考这样 一个问题，在数组 [a,a,b,c]
    如果a+b+c=0，因为有两个a，那么就会出现两个a+b+c=0的结果，所以我们需要 过滤掉一个，怎么过滤呢，就是当前数字如果和前面一个数字相同，
    我们就跳过。比如 上面第2个a和第一个a相同，我们直接跳过。来看下最终代码
     */
    public static List<List<Integer>> threeSum(int[] num) {
        List<List<Integer>> res = new ArrayList<>();
        if (num == null || num.length == 0) return res;
        Arrays.sort(num);
        for (int i = 0; i < num.length - 2; i++) {
            if (i > 0 && num[i] == num[i - 1]) continue;  // 过滤掉重复的
            if (num[i] > 0) break;
            int left = i + 1;
            int right = num.length - 1;
            int target = -num[i];
            while (left < right) {
                int sum = num[left] + num[right];
                if (sum == target) {
                    ArrayList<Integer> res1 = new ArrayList<>();
                    res1.add(num[i]);
                    res1.add(num[left]);
                    res1.add(num[right]);
                    res.add(res1);
                    // res.add(Arrays.asList(num[i], num[left], num[right]));
                    while (left < right && num[left] == num[left + 1])  // 过滤掉重复的
                        left++;
                    while (left < right && num[right] == num[right - 1])
                        right--;
                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return res;
    }

    // 219： Contains Duplicate

    /**
     * Given an integer array nums and an integer k, return true if there are two distinct indices i and j in
     * the array such that nums[i] == nums[j] and abs(i - j) <= k.
     * <p>
     * Input: nums = [1,2,3,1], k = 3
     * Output: true
     *
     * @param k
     */

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        //map中key存储的是数组中的元素，value存储的是
        //对应元素在数组中的下标
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                //判断数组中是否出现过相同的元素nums[i]，如果出现
                //过就计算之前元素下标和当前元素下标的差值是否小于
                //等于k，如果小于等于k，直接返回true。
                int index = map.get(nums[i]);
                if (i - index <= k) return true;
            }
            map.put(nums[i], i);
        }
        return false;
    }

    //如果有重复的元素，那么map的put方法会返回重复元素的下标，我们可以先把数组中的元素一个个存储到map中，
    // 如果put方法返回不为空，说明出现了重复的元素，所以 上面的代码还可以这样写。
    public boolean containsNearbyDuplicate1(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            //如果有重复的元素，put方法会把它覆盖掉，并且返回上一个元素的value值
            Integer index = map.put(nums[i], i);
            //如果index不为空，说明出现过重复的元素，我们只需要判断这两个元素的下标差值是否小于等于k即可。
            if (index != null && i - index <= k) {
                return true;
            }
        }
        return false;
    }

    // 滑动窗口
    public boolean containsNearbyDuplicate2(int[] nums, int k) {
        //windowSet相当于一个窗口，窗口的大小是k+1
        Set<Integer> windowSet = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            //如果窗口中的元素个数超过k，就把最前面的给移除
            if (i > k) {
                windowSet.remove(nums[i - k - 1]);
            }
            //这个时候窗口中的元素都是不超过k+1的，然后我们判断这个窗口
            //中是否有重复的元素
            if (!windowSet.add(nums[i]))
                return true;
        }
        return false;
    }

    // 1423: Maximum points you can obtain from cards

    /**
     * There are several cards arranged in a row, and each card has an associated number of points.
     * The points are given in the integer array cardPoints.
     * <p>
     * In one step, you can take one card from the beginning or from the end of the row. You have to take exactly k cards.
     * <p>
     * Your score is the sum of the points of the cards you have taken.
     * <p>
     * Given the integer array cardPoints and the integer k, return the maximum score you can obtain.
     * <p>
     * Input: cardPoints = [1,2,3,4,5,6,1], k = 3
     * Output: 12
     * Explanation: After the first step, your score will always be 1. However, choosing the
     * rightmost card first will maximize your total score. The optimal strategy is to take the three cards on
     * the right, giving a final score of 1 + 6 + 5 = 12.
     *
     * @param cardPoints
     */

    // 每次拿的时候只能从开头和末尾拿，而不能从中间拿。我们换种思路，如果把数组的首尾相连，串成一个环形，那么最终拿掉的k个元素肯定是连续的，
    // 问题就转化为求k个连续 元素的最大和，所以我们很容易想到的就是滑动窗口。 但这个窗口有个限制条件，就是窗口内的元素至少包含原数组首尾元素中的一个。
    public int maxScore(int[] cardPoints, int k) {
        int max = 0, len = cardPoints.length;
        for (int i = 0; i < k; i++) {
            max += cardPoints[i];
        }
        int cur = max;
        for (int i = 0; i < len - k; i++) {
            //窗口移动的时候一个元素会出窗口，一个元素会进入窗口。
            //cardPoints[k - (length - i)]是移除窗口的元素
            cur -= cardPoints[k - (len - i)];
            //cardPoints[i]是进入窗口的元素
            cur += cardPoints[i];
            //记录窗口的最大值
            max = Math.max(max, cur);
        }
        return max;
    }

    // 26 Remove Duplicates from sorted Array

    /**
     * Given an integer array nums sorted in non-decreasing order, remove the duplicates in-place such that each unique
     * element appears only once. The relative order of the elements should be kept the same.
     * <p>
     * Since it is impossible to change the length of the array in some languages, you must instead have the result
     * be placed in the first part of the array nums. More formally, if there are k elements after removing the
     * duplicates, then the first k elements of nums should hold the final result. It does not matter what you leave
     * beyond the first k elements.
     * <p>
     * Return k after placing the final result in the first k slots of nums.
     * <p>
     * Do not allocate extra space for another array. You must do this by modifying the input array in-place with
     * O(1) extra memory.
     * <p>
     * Input: nums = [1,1,2]
     * Output: 2, nums = [1,2,_]
     * Explanation: Your function should return k = 2, with the first two elements of nums being 1 and 2 respectively.
     * It does not matter what you leave beyond the returned k (hence they are underscores).
     * <p>
     * Input: nums = [0,0,1,1,1,2,2,3,3,4]
     * Output: 5, nums = [0,1,2,3,4,_,_,_,_,_]
     * Explanation: Your function should return k = 5, with the first five elements of nums being 0, 1, 2, 3, and 4
     * respectively.
     * It does not matter what you leave beyond the returned k (hence they are underscores).
     */

    public int removeDuplicate(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int left = 0;
        for (int right = 1; right < nums.length; right++) {
            //如果左指针和右指针指向的值一样，说明有重复的，
            //这个时候，左指针不动，右指针继续往右移。如果他俩
            // 指向的值不一样就把右指针指向的值往前挪
            if (nums[left] != nums[right]) nums[++left] = nums[right];
        }
        return left++;
    }

    // 349: Intersection of Two Arrays

    /**
     * Given two integer arrays nums1 and nums2, return an array of their intersection. Each element in the result must
     * be unique and you may return the result in any order.
     * <p>
     * Input: nums1 = [1,2,2,1], nums2 = [2,2]
     * Output: [2]
     * <p>
     * Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
     * Output: [9,4]
     * Explanation: [4,9] is also accepted.
     *
     * @param nums2
     */

    //先对两个数组进行排序，然后使用两个指针，分别指向两个数组开始的位置。
    //如果两个指针指向的值相同，说明这个值是他们的交集，就把这个值加入到集合list中，然后两个指针在分别往后移一步。
    //如果两个指针指向的值不一样，那么指向的值相对小的往后移一步，相对大的先不动，然后再比较
    public static int[] intersect(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int i = 0, j = 0;
        List<Integer> list = new ArrayList<>();
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) {
                i++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            } else {
                if (!list.contains(nums1[i])) list.add(nums1[i]);
                i++;
                j++;
            }
        }
        //把list转化为数组
        int index = 0;
        int[] res = new int[list.size()];
        for (int k = 0; k < list.size(); k++) {
            res[index++] = list.get(k);
        }
        return res;
    }



    public static void main(String[] args) {
        System.out.println(intersect(new int[]{1, 2, 3}, new int[]{1, 3}));
    }
}



