package com.company.Notes;

import java.lang.reflect.Method;
import java.util.*;

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

    /**
     * TikTok 2023 10/31 - 11/4
     * There is a task recorded in the tow-dimensional array tasks in the format [start, end, period], indicating
     * that the task needs to be completed within the time range start to end, and period indicates the length of thime
     * required to complete the task
     *
     * The computer can handle an unlimited number of tasks at the same time
     */
    /*
    由题易知对于每个区间，尽可能把选择的时间点放在区间末尾比较优，所以以结束时间从小到大排序，然后对于每个区间直接暴力从start到end每个时间点数一数数量够不够，不够就从end倒着往前select即可
     */
    public static int process(int[][] tasks) {
        Set<Integer> set = new HashSet<>();
        Arrays.sort(tasks, (a, b)->a[1] - b[1]);
//        for (int[] i: tasks)
//            System.out.println(Arrays.toString(i));
        // 贪心，把每个数组从后往前加需要添加的次数，
        for (int i = tasks[0][1]; i > tasks[0][1] - tasks[0][2]; i--) {
            set.add(i);
        }
        for (int i = 1; i < tasks.length; i++) {
            int time = tasks[i][2];
            for (int j = tasks[i][0]; j < tasks[i][1]; j++) {
                if (set.contains(j)) {
                    time --;
                } else break;
            }
            for (int j = tasks[i][1]; j > tasks[i][1] - time; j--) {
                set.add(j);
            }
        }
        System.out.println(set.size());
        for (int i : set) System.out.print(i);
        return set.size();
    }

    // 991: broken calculator

    /**
     * There is a broken calculator that has the integer startValue on its display initially. In one operation, you can:
     *
     * multiply the number on display by 2, or
     * subtract 1 from the number on display.
     * Given two integers startValue and target, return the minimum number of operations needed to display target on the calculator.
     *
     * Example 1:
     *
     * Input: startValue = 2, target = 3
     * Output: 2
     * Explanation: Use double operation and then decrement operation {2 -> 4 -> 3}.
     * Example 2:
     *
     * Input: startValue = 5, target = 8
     * Output: 2
     * Explanation: Use decrement and then double {5 -> 4 -> 8}.
     * Example 3:
     *
     * Input: startValue = 3, target = 10
     * Output: 3
     * Explanation: Use double, decrement and double {3 -> 6 -> 5 -> 10}.
     */
    class Solution991 {
        /**
         * Instead of multiplying by 2 or subtracting 1 from startValue, we could divide by 2 (when target is even) or add 1 to target.
         * The motivation for this is that it turns out we always greedily divide by 2:
         *
         * While target is larger than startValue, add 1 if it is odd, else divide by 2. After,
         * we need to do startValue - target additions to reach startValue.
         */
        public int brokenCalc(int startValue, int target) {
            if (startValue > target) return startValue - target;
            int res = 0;

            while (target > startValue) {
                res ++;
                if (target %2 == 1) {
                    target++;
                } else {
                    target/=2;
                }
            }
            return res + startValue - target;

        }
    }

    //460: LFU cache

    /**
     * Design and implement a data structure for a Least Frequently Used (LFU) cache.
     *
     * Implement the LFUCache class:
     *
     * LFUCache(int capacity) Initializes the object with the capacity of the data structure.
     * int get(int key) Gets the value of the key if the key exists in the cache. Otherwise, returns -1.
     * void put(int key, int value) Update the value of the key if present, or inserts the key if not already present.
     * When the cache reaches its capacity, it should invalidate and remove the least frequently used key before inserting
     * a new item. For this problem, when there is a tie (i.e., two or more keys with the same frequency), the least
     * recently used key would be invalidated.
     * To determine the least frequently used key, a use counter is maintained for each key in the cache. The key with
     * the smallest use counter is the least frequently used key.
     *
     * When a key is first inserted into the cache, its use counter is set to 1 (due to the put operation).
     * The use counter for a key in the cache is incremented either a get or put operation is called on it.
     *
     * The functions get and put must each run in O(1) average time complexity.
     * Input
     * ["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "get"]
     * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [3], [4, 4], [1], [3], [4]]
     * Output
     * [null, null, null, 1, null, -1, 3, null, -1, 3, 4]
     *
     * Explanation
     * // cnt(x) = the use counter for key x
     * // cache=[] will show the last used order for tiebreakers (leftmost element is  most recent)
     * LFUCache lfu = new LFUCache(2);
     * lfu.put(1, 1);   // cache=[1,_], cnt(1)=1
     * lfu.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
     * lfu.get(1);      // return 1
     *                  // cache=[1,2], cnt(2)=1, cnt(1)=2
     * lfu.put(3, 3);   // 2 is the LFU key because cnt(2)=1 is the smallest, invalidate 2.
     *                  // cache=[3,1], cnt(3)=1, cnt(1)=2
     * lfu.get(2);      // return -1 (not found)
     * lfu.get(3);      // return 3
     *                  // cache=[3,1], cnt(3)=2, cnt(1)=2
     * lfu.put(4, 4);   // Both 1 and 3 have the same cnt, but 1 is LRU, invalidate 1.
     *                  // cache=[4,3], cnt(4)=1, cnt(3)=2
     * lfu.get(1);      // return -1 (not found)
     * lfu.get(3);      // return 3
     *                  // cache=[3,4], cnt(4)=1, cnt(3)=3
     * lfu.get(4);      // return 4
     *                  // cache=[4,3], cnt(4)=2, cnt(3)=3
     */
    class LFUCache {

        HashMap<Integer, Integer> vals;  // 一个map来装key, value
        HashMap<Integer, Integer> counts;  // 用来装key和对应出现的数量
        HashMap<Integer, LinkedHashSet<Integer>> list;  //  count有一个先后顺序
        // linkedhashset, 记录顺序的set
        int capacity;
        int min;  //  当前的

        public LFUCache(int capacity) {
            this.capacity = capacity;
            vals = new HashMap<>();
            counts = new HashMap<>();
            list = new HashMap<>();
            list.put(1, new LinkedHashSet<>());
            min = -1;
        }

        public int get(int key) {
            if (!vals.containsKey(key)) {
                return -1;
            }
            int count = counts.get(key);
            counts.put(key, count + 1);
            // 在list原来的地方加一，再放回去
            list.get(count).remove(key);
            if (count == min && list.get(count).size() == 0) min++;
            if (!list.containsKey(count + 1)) {
                list.put(count + 1, new LinkedHashSet<>());
            }
            list.get(count + 1).add(key);
            return vals.get(key);
        }

        public void put(int key, int value) {
            if (capacity <= 0) return;
            if (vals.containsKey(key)) {  // 如果之前已经加入过，只需更新值
                vals.put(key, value);
                get(key);
                return;
            }
            if (vals.size() >= capacity) {  // 如果装东西的数量大于了容量，弹出来出现次数最小的那个
                int evit = list.get(min).iterator().next();
                list.get(min).remove(evit);  // 要拿就全拿走
                vals.remove(evit);
            }
            vals.put(key, value);
            counts.put(key, 1);
            min = 1;
            list.get(1).add(key);
        }
    }

    /**
     * Your LFUCache object will be instantiated and called as such:
     * LFUCache obj = new LFUCache(capacity);
     * int param_1 = obj.get(key);
     * obj.put(key,value);
     */

    // 1663: Smallest String With A Given Numeric Value

    /**
     * The numeric value of a lowercase character is defined as its position (1-indexed) in the alphabet, so the
     * numeric value of a is 1, the numeric value of b is 2, the numeric value of c is 3, and so on.
     *
     * The numeric value of a string consisting of lowercase characters is defined as the sum of its characters'
     * numeric values. For example, the numeric value of the string "abe" is equal to 1 + 2 + 5 = 8.
     *
     * You are given two integers n and k. Return the lexicographically smallest string with length equal to n
     * and numeric value equal to k.
     *
     * Note that a string x is lexicographically smaller than string y if x comes before y in dictionary order,
     * that is, either x is a prefix of y, or if i is the first position such that x[i] != y[i], then x[i]
     * comes before y[i] in alphabetic order.
     *
     * Example 1:
     *
     * Input: n = 3, k = 27
     * Output: "aay"
     * Explanation: The numeric value of the string is 1 + 1 + 25 = 27, and it is the smallest string with such a value and length equal to 3.
     * Example 2:
     *
     * Input: n = 5, k = 73
     * Output: "aaszz"
     */

    class Solution1663 {
        public String getSmallestString(int n, int k) {
            StringBuilder sb = new StringBuilder();
            if (26 * n < k) return sb.toString();
            int sum = n;
            while (sum < k) {
                int cur = 1;
                while (cur < 26 && sum < k) {
                    cur++;
                    sum++;
                }
                sb.append((char)('a' + cur - 1));
            }
            // 注意这里sb的length在每次循环后会变，必须单独拿出来
            int len = sb.length();
            for (int i = 0; i < n - len; i ++) {
                sb.append("a");
            }
            return sb.reverse().toString();
        }
    }

    // 2405. Optimal Partition of String

    /**
     * Given a string s, partition the string into one or more substrings such that the characters in each substring
     * are unique. That is, no letter appears in a single substring more than once.
     *
     * Return the minimum number of substrings in such a partition.
     *
     * Note that each character should belong to exactly one substring in a partition.
     *
     * Example 1:
     *
     * Input: s = "abacaba"
     * Output: 4
     * Explanation:
     * Two possible partitions are ("a","ba","cab","a") and ("ab","a","ca","ba").
     * It can be shown that 4 is the minimum number of substrings needed.
     * Example 2:
     *
     * Input: s = "ssssss"
     * Output: 6
     * Explanation:
     * The only valid partition is ("s","s","s","s","s","s").
     * @param args
     */
    class Solution2405 {
        public int partitionString(String s) {
            s = s.toLowerCase();
            int count = s.length() == 0 ? 0 : 1;
            HashSet<Character> set = new HashSet<>();
            for (int i = 0; i < s.length(); i++) {
                if (set.contains(s.charAt(i))) {
                    count++;
                    set.clear();
                }
                set.add(s.charAt(i));
            }
            return count;
        }
    }

    // 2268. Minimum Number of Keypresses
    /**
     * You have a keypad with 9 buttons, numbered from 1 to 9, each mapped to lowercase English letters. You can choose which characters each button is matched to as long as:
     *
     * All 26 lowercase English letters are mapped to.
     * Each character is mapped to by exactly 1 button.
     * Each button maps to at most 3 characters.
     * To type the first character matched to a button, you press the button once. To type the second character, you press the button twice, and so on.
     *
     * Given a string s, return the minimum number of keypresses needed to type s using your keypad.
     *
     * Note that the characters mapped to by each button, and the order they are mapped in cannot be changed.
     *
     *
     *
     * Example 1:
     *
     *
     * Input: s = "apple"
     * Output: 5
     * Explanation: One optimal way to setup your keypad is shown above.
     * Type 'a' by pressing button 1 once.
     * Type 'p' by pressing button 6 once.
     * Type 'p' by pressing button 6 once.
     * Type 'l' by pressing button 5 once.
     * Type 'e' by pressing button 3 once.
     * A total of 5 button presses are needed, so return 5.
     * Example 2:
     *
     *
     * Input: s = "abcdefghijkl"
     * Output: 15
     * Explanation: One optimal way to setup your keypad is shown above.
     * The letters 'a' to 'i' can each be typed by pressing a button once.
     * Type 'j' by pressing button 1 twice.
     * Type 'k' by pressing button 2 twice.
     * Type 'l' by pressing button 3 twice.
     * A total of 15 button presses are needed, so return 15.
     * @param args
     */
    class Solution2268 {
        public int minimumKeypresses(String s) {
            if (s.length() <= 9) return s.length();
            Integer[] count = new Integer[26];
            Arrays.fill(count, 0);
            for (int i=  0; i < s.length(); i++) {
                count[s.charAt(i) - 'a']++;
            }
            Arrays.sort(count, (x, y) -> y - x);
            int res = 0;
            for (int i = 0; i < 26; i ++) {
                res += count[i] * ((i + 9) / 9);
            }
            return res;
        }
    }

    // 1647 Minimum Deletion to make character frequencies unique

    /**
     * A string s is called good if there are no two different characters in s that have the same frequency.
     *
     * Given a string s, return the minimum number of characters you need to delete to make s good.
     *
     * The frequency of a character in a string is the number of times it appears in the string. For example,
     * in the string "aab", the frequency of 'a' is 2, while the frequency of 'b' is 1.
     *
     *
     *
     * Example 1:
     *
     * Input: s = "aab"
     * Output: 0
     * Explanation: s is already good.
     * Example 2:
     *
     * Input: s = "aaabbbcc"
     * Output: 2
     * Explanation: You can delete two 'b's resulting in the good string "aaabcc".
     * Another way it to delete one 'b' and one 'c' resulting in the good string "aaabbc".
     * Example 3:
     *
     * Input: s = "ceabaacb"
     * Output: 2
     * Explanation: You can delete both 'c's resulting in the good string "eabaab".
     * Note that we only care about characters that are still in the string at the end (i.e. frequency of 0 is ignored).
     * @param args
     */
    /**
     * Store the frequency for each character in the given string s in a frequency array called frequency. We store the frequency for a character c at index c - 'a'. Thus, we will need 26 indices (from 0 to 25) to store the frequencies of the characters.
     * Initialize the variable deleteCount to 0, which stores the count of characters that need to be deleted. Also, initialize a HashSet seenFrequencies that stores the frequencies that have been occupied.
     * Iterate over the characters from a to z as 0 to 25, for each character:
     * Keep decrementing its frequency until it becomes a number that is not present in set seenFrequencies or it becomes zero. Every time we decrement the frequency we increment the variable deleteCount to mark the deletion of the character.
     * When the frequency becomes unique (or zero) insert it into the set seenFrequencies.
     * Return deleteCount.
     */

    class Solution1647 {
        public int minDel(String s) {
            int res = 0;
            int[] freq = new int[26];
            for (char c: s.toCharArray()) freq[c -'a']++;

            Set<Integer> set = new HashSet<>();
            for (int i = 0; i < freq.length; i++) {
                while (freq[i] > 0 && set.contains(freq[i])) {
                    freq[i] --;
                    res++;
                }
                set.add(freq[i]);
            }
            return res;
        }
    }

    public static void main(String[] args) {
        process(new int[][]{{1, 4, 2},{4, 6, 2},{8, 9, 2}, {3, 5, 2}});
        Class<?> clazz = String.class;
        Method[] method = clazz.getMethods();
        for (Method methods : method) {
            System.out.println(methods.getName());
        }
    }
}
