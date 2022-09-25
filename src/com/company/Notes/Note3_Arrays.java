package com.company.Notes;

import java.util.*;

import static com.company.Notes.Note3_Arrays.Solution724.pivotIndex;

public class Note3_Arrays {

    // LeetCode 243: shortestDistance

    /**
     * 给定一个单词列表和两个单词 word1 和 word2，返回列表中这两个单词之间的最短距离。
     * @param strings
     * @param s
     * @param m
     * @return
     */
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
     * Given an array of meeting time intervals consisting of start and end times [[s1, e1], [s2, e2, ...]] (si < ei),
     * determine if a person could attend all meetings.
     * <p>
     * For example: Given [[0, 30], [5, 10], [15, 20]], return false.
     * <p>
     * + 按start 排序
     * <p>
     * + 解题技巧： 前一个区间 end & 后一个区间  start
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

    //    LeetCode 253: Meeting Rooms 2  重要！！！！！会考到

    /**
     * Given an array of meeting time intervals consisting of start and end times [[s1, e1]. [s2, e2], ...]
     * [si < ei], find the minimum number of conference rooms required.
     * <p>
     * Example: input: [[0, 30], [5, 10], [15,20]]
     * <p>
     * output: 2
     *
     * @param intervals
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
            if (nums[index] == 0) {  // 如果遇到index的位置是0，将left和index交换，0要排在前面
                swap(nums, index++, left++);  // 再把index和left指针向后移动
            } else if (nums[index] == 1) {  // 如果index的位置是1，直接将index向后移动
                index++;
            } else {
                swap(nums, index, right--);  // 如果index的位置是2，将right和index交换，2要排在后面，再将右指针向前移动
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
        int start = 0;  // 用start来记录不是0的位置
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {  // 如果i的位置不是0，就直接让start的位置等于i的值，然后i再++
                nums[start++] = nums[i];
            }
        }
        while (start < nums.length) {
            nums[start++] = 0;  // 最后让start后的位置全等于0
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
     * Given an array of integer nums containing n + 1 integers where each integer is in the range [1, n] inclusive.
     * <p>
     * There is only one repeated number in nums, return this repeated number.
     * <p>
     * You must solve the problem without modifying the array nums and uses only constant extra space.
     * <p>
     * Input: nums = [1,3,4,2,2]
     * Output: 2
     *
     * @param nums
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
     * nums = [3, 2, 3, 1, 4, 3]
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
     * Input: nums = [1,2,3]
     * Output: 2
     * Explanation:
     * Only two moves are needed (remember each move increments or decrements one element):
     * [1,2,3]  =>  [2,2,3]  =>  [2,2,2]
     *
     *
     * Input: nums = [1,10,2,9]
     * [1, 2, 9 ,10]
     * Output: 16
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

    // 219： Contains Duplicate II

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

    // 220 :Contains duplicate III
    /**
     * Given an integer array nums and two integers k and t, return true if there are two distinct indices i and j in
     * the array such that abs(nums[i] - nums[j]) <= t and abs(i - j) <= k.
     *
     * Example 1:
     *
     * Input: nums = [1,2,3,1], k = 3, t = 0
     * Output: true
     * Example 2:
     *
     * Input: nums = [1,0,1,1], k = 1, t = 2
     * Output: true
     * Example 3:
     *
     * Input: nums = [1,5,9,1,5,9], k = 2, t = 3
     * Output: false
     */
    class Solution220{
        // TreeSet写法

        /**
         * TreeSet: 红黑树，自带排序
         *
         * ceiling: 返回大于或等于element的最小元素
         *
         * floor：发怒hi小于或等于element的最大元素
         *
         * @param nums
         * @param k
         * @param t
         * @return
         */
        public boolean containsDuplicate(int[] nums, int k, int t) {
            TreeSet<Long> set = new TreeSet<>();
            for (int i = 0; i < nums.length; i++) {
                Long floor = set.floor((long) nums[i] + t);
                Long ceil = set.ceiling((long)nums[i] - t);
                // 如果floor大于nums[i]或者ceiling小于nums[i]说明找到了
                if (floor != null && floor >= nums[i] || ceil != null && ceil <= nums[i]) return true;
                set.add((long)nums[i]);
                if (i >= k) set.remove((long) nums[i - k]);
            }
            return false;
        }
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

    // LeetCode 485： Max Consecutive ones 1
    /**
     * Given a binary array nums, return the maximum number of consecutive 1's in the array.
     *
     * Input: nums = [1,1,0,1,1,1]
     * Output: 3
     * Explanation: The first two digits or the last three digits are consecutive 1s. The maximum number of consecutive 1s is 3.
     *
     * Input: nums = [1,0,1,1,0,1]
     * Output: 2
     */

    class Solution485 {
        public int findMaxConsecutiveOnes(int[] nums) {
            int count = 0, max = 0;
            for (int x: nums) {
                if (x == 1) count++;
                else {
                    max = Math.max(count, max);
                    count = 0;
                }
            }
            return Math.max(max, count);
        }
    }

    // LeetCode 1004 Max Consecutive ones 3: 滑动窗口

    /**
     * Given a binary array nums and an integer k, return the maximum number of consecutive 1's in the
     * array if you can flip at most k 0's.
     *
     * Input: nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2
     * Output: 6
     * Explanation: [1,1,1,0,0,1,1,1,1,1,1]
     * Bolded numbers were flipped from 0 to 1. The longest subarray is underlined.
     *
     * Input: nums = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], k = 3
     * Output: 10
     * Explanation: [0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1]
     * Bolded numbers were flipped from 0 to 1. The longest subarray is underlined.
     */
    /*
    我们可以使用两个指针，一个指向窗口的左边，一个指向窗口的右边，每次遍历数组的时候窗口左边的指针先不动，窗口右边的指针始终都会往右移动，
    然后顺便统计窗口内0的个数，如果0的个数大于K的时候，说明我们即使使用魔法，也不能把窗口内的所有数字都变为1，这个时候我们在移动窗口左边的指针，
    直到窗口内0的个数不大于K为止……
     */

    class Solution {
        public int longestOnes(int[] nums, int k) {
            int left = 0, right = 0;
            int countZero = 0;
            int maxWindow = 0;
            for (; right < nums.length; right ++) {
                if (nums[right] == 0) countZero ++;
                while(countZero > k) {
                    if (nums[left++] == 0) countZero--;
                }
                maxWindow = Math.max(maxWindow, right - left + 1);
            }
            return maxWindow;
        }
    }

    // LeetCode 239: Sliding Window Maximum

    /**
     *
     * You are given an array of integers nums, there is a sliding window of size k which is moving from
     * the very left of the array to the very right. You can only see the k numbers in the window. Each time
     * the sliding window moves right by one position.
     *
     * Return the max sliding window.
     *
     * Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
     * Output: [3,3,5,5,6,7]
     * Explanation:
     * Window position                Max
     * ---------------               -----
     * [1  3  -1] -3  5  3  6  7       3
     *  1 [3  -1  -3] 5  3  6  7       3
     *  1  3 [-1  -3  5] 3  6  7       5
     *  1  3  -1 [-3  5  3] 6  7       5
     *  1  3  -1  -3 [5  3  6] 7       6
     *  1  3  -1  -3  5 [3  6  7]      7
     */
    class Solution239 {
        public int[] maxSlidingWindow(int[] nums, int k) {
            if (nums == null || nums.length == 0) return new int[0];
            int[] res = new int[nums.length - k + 1];
            for (int i = 0; i < res.length; i ++) {
                int max = nums[i];
                for (int j = 1; j < k; j ++) {  // 在每个窗口内找到最大值
                    max = Math.max(max, nums[i + j]);
                }
                res[i] = max;
            }
            return res;
        }
    }

    // 299：bulls and cows
    /**
     * You are playing the Bulls and Cows game with your friend.
     *
     * You write down a secret number and ask your friend to guess what the number is. When your friend makes a guess,
     * you provide a hint with the following info:
     *
     * The number of "bulls", which are digits in the guess that are in the correct position.
     * The number of "cows", which are digits in the guess that are in your secret number but are located in the wrong
     * position. Specifically, the non-bull digits in the guess that could be rearranged such that they become bulls.
     * Given the secret number secret and your friend's guess guess, return the hint for your friend's guess.
     *
     * The hint should be formatted as "xAyB", where x is the number of bulls and y is the number of cows. Note that
     * both secret and guess may contain duplicate digits.
     *
     * Example 1:
     *
     * Input: secret = "1807", guess = "7810"
     * Output: "1A3B"
     * Explanation: Bulls are connected with a '|' and cows are underlined:
     * "1807"
     *   |
     * "7810"
     * Example 2:
     *
     * Input: secret = "1123", guess = "0111"
     * Output: "1A1B"
     * Explanation: Bulls are connected with a '|' and cows are underlined:
     * "1123"        "1123"
     *   |      or     |
     * "0111"        "0111"
     * Note that only one of the two unmatched 1s is counted as a cow since the non-bull digits can only be rearranged
     * to allow one 1 to be a bull.
     */
    class Solution299 {
        public String getHint(String secret, String guess) {
            int bulls = 0, cows = 0;
            int[] count = new int[10];
            for (int i = 0; i < secret.length(); i++) {
                if (secret.charAt(i) == guess.charAt(i)) bulls++;
                else {
                    if (count[secret.charAt(i) - '0']++ < 0) cows++;  //
                    if (count[guess.charAt(i) - '0']-- > 0) cows++;  // 之前在secret里面出现过，然后减掉（已经用过了）
                }
            }
            return bulls + "A" + cows + "B";
        }
    }

    // 274 H-index
    /**
     * Given an array of integers citations where citations[i] is the number of citations a researcher received for their ith paper, return compute the researcher's h-index.
     *
     * According to the definition of h-index on Wikipedia: A scientist has an index h if h of their n papers have at least h citations each, and the other n − h papers have no more than h citations each.
     *
     * If there are several possible values for h, the maximum one is taken as the h-index.
     *
     *
     *
     * Example 1:
     *
     * Input: citations = [3,0,6,1,5]
     * Output: 3
     * Explanation: [3,0,6,1,5] means the researcher has 5 papers in total and each of them had received 3, 0, 6, 1, 5
     * citations respectively.
     * Since the researcher has 3 papers with at least 3 citations each and the remaining two with no more than 3
     *
     * citations each, their h-index is 3.
     * Example 2:
     *
     * Input: citations = [1,3,1]
     * Output: 1
     */
    // o(nlogn)
    class Solution274{
        /*
        3 0 6 1 5
        0 1 3 5 6
        res 0 -> 1
            6
            1 -> 2
            5
            2 -> 3
            3
         */
        public int hIndex(int[] citations) {
            Arrays.sort(citations);
            int res = 0;
            // 从后往前遍历，至少引用n次的文章有n篇，如果当前的文章引用次数大于res, res就加一
            while (res < citations.length && citations[citations.length - 1 -res] > res) res ++;
            return res;
        }
    }

    // 451 sort characters by frequency
    /**
     * Given a string s, sort it in decreasing order based on the frequency of the characters. The frequency of a
     * character is the number of times it appears in the string.
     *
     * Return the sorted string. If there are multiple answers, return any of them.
     *
     * Example 1:
     *
     * Input: s = "tree"
     * Output: "eert"
     * Explanation: 'e' appears twice while 'r' and 't' both appear once.
     * So 'e' must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.
     * Example 2:
     *
     * Input: s = "cccaaa"
     * Output: "aaaccc"
     * Explanation: Both 'c' and 'a' appear three times, so both "cccaaa" and "aaaccc" are valid answers.
     * Note that "cacaca" is incorrect, as the same characters must be together.
     */
    class Solution451{
        public String frequencySort(String s) {
            HashMap<Character, Integer> map = new HashMap<>();
            for (char c : s.toCharArray()) {
                map.put(c, map.getOrDefault(c, 0) + 1);  // 计算每个字母出现的频率
            }

            // 一个装了不同频数的桶，每个位置装了出现次数相同的字母，频数从小到大排列
            List<Character>[] bucket = new List[s.length() + 1];  // 0 的位置没有
            for (char c : map.keySet()) {
                int freq = map.get(c);  // 当前字母出现的次数
                if (bucket[freq] == null) bucket[freq] = new LinkedList<>();  // 如果桶对应的频率处为空，新建一个list
                bucket[freq].add(c);  // 将字母加入这个位置
            }
            // 从后往前加入桶的位置对应的字母
            StringBuilder sb = new StringBuilder();
            for (int i = bucket.length - 1; i >= 0; i --) {
                if (bucket[i] != null) {
                    for (char c : bucket[i]) {  // 将这个位置装的字母加到sb里
                        for (int j = 0; j < map.get(c); j ++) sb.append(c);
                    }
                }
            }
            return sb.toString();
        }
    }

    // 347： top K frequent elements  排名第一重要！！！！都出现过
    /**
     * Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in
     * any order.
     *
     * Example 1:
     *
     * Input: nums = [1,1,1,2,2,3], k = 2
     * Output: [1,2]
     * Example 2:
     *
     * Input: nums = [1], k = 1
     * Output: [1]
     */
    static class Solution347 {
        // bucket sort
        public static int[] topKFrequent(int[] nums, int k) {
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int num : nums) map.put(num, map.getOrDefault(num, 0) + 1);

            List<Integer>[] bucket = new List[nums.length + 1];
            for (int num : map.keySet()) {
                int freq = map.get(num);
                if (bucket[freq] == null) bucket[freq] = new LinkedList<>();
                bucket[freq].add(num);
            }
            System.out.print(Arrays.toString(bucket));

            int[] res = new int[k];
            for (int i = bucket.length - 1; i >= 0 ; i--) {
                int j = 0;
                while (bucket[i] != null && j < bucket[i].size() && k > 0) {
                    res[j] = bucket[i].get(j++);
                }
            }
            return res;
        }

        // PriorityQueue time: O(klogn) space: O(n)
        public static int[] topFrequent2(int[] nums, int k) {
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int num: nums) map.put(num, map.getOrDefault(num, 0) + 1);
            // 优先队列是一个最小堆，开头的元素最小，我们要把它变成一个最大堆，重写compare
            PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = new PriorityQueue<>((a,b) -> (b.getValue() - a.getValue()));
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                maxHeap.add(entry);
            }

            // 将优先队列的前k个元素给加到res里
            int[] res = new int[k];
            while (k-- >= 0) {
                res[k - 1] = maxHeap.poll().getKey();
            }
            return res;
        }

        // TreeMap
        public static List<Integer> topKFrequent3(int[] nums, int k) {
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int num: nums) map.put(num, map.getOrDefault(num, 0) + 1);
            TreeMap<Integer, List<Integer>> freqMap = new TreeMap<>();
            for (int num : map.keySet()) {
                int freq = map.get(num);
                if (freqMap.containsKey(freq)) freqMap.get(freq).add(num);
                else {
                    freqMap.put(freq, new LinkedList<>());
                    freqMap.get(freq).add(num);
                }
            }
            List<Integer> res = new ArrayList<>();
            while (res.size() < k) {
                Map.Entry<Integer, List<Integer>> entry = freqMap.pollLastEntry();
                res.addAll(entry.getValue());
            }
            return res;

        }

    }

    // 27: Remove Element

    /**
     * Given an integer array nums and an integer val, remove all occurrences of val in nums in-place. The relative order of the elements may be changed.
     *
     * Since it is impossible to change the length of the array in some languages, you must instead have the result be placed in the first part of the array nums. More formally, if there are k elements after removing the duplicates, then the first k elements of nums should hold the final result. It does not matter what you leave beyond the first k elements.
     *
     * Return k after placing the final result in the first k slots of nums.
     *
     * Do not allocate extra space for another array. You must do this by modifying the input array in-place with O(1) extra memory.
     *
     * Custom Judge:
     *
     * The judge will test your solution with the following code:
     *
     * int[] nums = [...]; // Input array
     * int val = ...; // Value to remove
     * int[] expectedNums = [...]; // The expected answer with correct length.
     *                             // It is sorted with no values equaling val.
     *
     * int k = removeElement(nums, val); // Calls your implementation
     *
     * assert k == expectedNums.length;
     * sort(nums, 0, k); // Sort the first k elements of nums
     * for (int i = 0; i < actualLength; i++) {
     *     assert nums[i] == expectedNums[i];
     * }
     * If all assertions pass, then your solution will be accepted.
     *
     *
     *
     * Example 1:
     *
     * Input: nums = [3,2,2,3], val = 3
     * Output: 2, nums = [2,2,_,_]
     * Explanation: Your function should return k = 2, with the first two elements of nums being 2.
     * It does not matter what you leave beyond the returned k (hence they are underscores).
     * Example 2:
     *
     * Input: nums = [0,1,2,2,3,0,4,2], val = 2
     * Output: 5, nums = [0,1,4,0,3,_,_,_]
     * Explanation: Your function should return k = 5, with the first five elements of nums containing 0, 0, 1, 3, and 4.
     * Note that the five elements can be returned in any order.
     * It does not matter what you leave beyond the returned k (hence they are underscores).
     *
     */
    class Solution27 {
        public int removeElement(int[] nums, int val) {
            if (nums == null || nums.length == 0) return 0;
            int res = 0;
            for (int i = 0; i < nums.length; i++) {
                // 如果当前位置的值不等同于val，把res指针的位置变成当前的值
                if (nums[i] != val) nums[res++] = nums[i];
            }
            return res;
        }
    }

    // 26： remove duplicates from sorted Array

    /**
     * Given an integer array nums sorted in non-decreasing order, remove the duplicates in-place such that
     * each unique element appears only once. The relative order of the elements should be kept the same.
     *
     * Since it is impossible to change the length of the array in some languages, you must instead have the
     * result be placed in the first part of the array nums. More formally, if there are k elements after
     * removing the duplicates, then the first k elements of nums should hold the final result. It does not
     * matter what you leave beyond the first k elements.
     *
     * Return k after placing the final result in the first k slots of nums.
     *
     * Do not allocate extra space for another array. You must do this by modifying the input array in-place
     * with O(1) extra memory.
     *
     * Custom Judge:
     *
     * The judge will test your solution with the following code:
     *
     * int[] nums = [...]; // Input array
     * int[] expectedNums = [...]; // The expected answer with correct length
     *
     * int k = removeDuplicates(nums); // Calls your implementation
     *
     * assert k == expectedNums.length;
     * for (int i = 0; i < k; i++) {
     *     assert nums[i] == expectedNums[i];
     * }
     * If all assertions pass, then your solution will be accepted.
     *
     *
     *
     * Example 1:
     *
     * Input: nums = [1,1,2]
     * Output: 2, nums = [1,2,_]
     * Explanation: Your function should return k = 2, with the first two elements of nums being 1 and 2 respectively.
     * It does not matter what you leave beyond the returned k (hence they are underscores).
     * Example 2:
     *
     * Input: nums = [0,0,1,1,1,2,2,3,3,4]
     * Output: 5, nums = [0,1,2,3,4,_,_,_,_,_]
     * Explanation: Your function should return k = 5, with the first five elements of nums being 0, 1, 2, 3, and
     * 4 respectively.
     * It does not matter what you leave beyond the returned k (hence they are underscores).
     */
    static class Solution26{
        public static int removeDuplicates(int[] nums) {
            if(nums.length==0) return 0;
            int i=0;
            for(int j=1;j<nums.length;j++){
                if(nums[i]!=nums[j]){  // 如果左右指针不一样，左指针向右移，把右指针的值给左指针
                    i++;
                    nums[i]=nums[j];
                }
            }
            return i+1;
        }
    }

    // 80： Remove Duplicates from sorted Array II

    /**
     * Given an integer array nums sorted in non-decreasing order, remove some duplicates in-place such that each unique element appears at most twice. The relative order of the elements should be kept the same.
     *
     * Since it is impossible to change the length of the array in some languages, you must instead have the result be placed in the first part of the array nums. More formally, if there are k elements after removing the duplicates, then the first k elements of nums should hold the final result. It does not matter what you leave beyond the first k elements.
     *
     * Return k after placing the final result in the first k slots of nums.
     *
     * Do not allocate extra space for another array. You must do this by modifying the input array in-place with O(1) extra memory.
     *
     * Custom Judge:
     *
     * The judge will test your solution with the following code:
     *
     * int[] nums = [...]; // Input array
     * int[] expectedNums = [...]; // The expected answer with correct length
     *
     * int k = removeDuplicates(nums); // Calls your implementation
     *
     * assert k == expectedNums.length;
     * for (int i = 0; i < k; i++) {
     *     assert nums[i] == expectedNums[i];
     * }
     * If all assertions pass, then your solution will be accepted.
     *
     *
     *
     * Example 1:
     *
     * Input: nums = [1,1,1,2,2,3]
     * Output: 5, nums = [1,1,2,2,3,_]
     * Explanation: Your function should return k = 5, with the first five elements of nums being 1, 1, 2, 2 and 3
     * respectively.
     * It does not matter what you leave beyond the returned k (hence they are underscores).
     * Example 2:
     *
     * Input: nums = [0,0,1,1,1,1,2,3,3]
     * Output: 7, nums = [0,0,1,1,2,3,3,_,_]
     * Explanation: Your function should return k = 7, with the first seven elements of nums being 0, 0, 1, 1, 2, 3 and
     * 3 respectively.
     * It does not matter what you leave beyond the returned k (hence they are underscores).
     */
    class Solution80 {
        public int removeDuplicates80(int[] nums) {
            if (nums.length <= 2) return nums.length;
            int count = 2;
            for (int i = 2; i < nums.length; i ++) {
                if (nums[i] != nums[count - 2]) {
                    nums[count++] = nums[i];
                }
            }
            return count;
        }
    }

    // 57：Insert Interval 重要！！

    /**
     * You are given an array of non-overlapping intervals intervals where intervals[i] = [starti, endi] represent
     * the start and the end of the ith interval and intervals is sorted in ascending order by starti. You are also
     * given an interval newInterval = [start, end] that represents the start and end of another interval.
     *
     * Insert newInterval into intervals such that intervals is still sorted in ascending order by starti and intervals
     * still does not have any overlapping intervals (merge overlapping intervals if necessary).
     *
     * Return intervals after the insertion.
     *
     * Example 1:
     *
     * Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
     * Output: [[1,5],[6,9]]
     * Example 2:
     *
     * Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
     * Output: [[1,2],[3,10],[12,16]]
     * Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
     */
    class Solution57{
        public int[][] insert(int[][] intervals, int[] newInterval) {
            if (newInterval == null) return intervals;
            List<int[]> res = new ArrayList<>();
            int i = 0;
            // 当老interval的结束小于newInterval的开始的时候，说明他俩没有overlap, 直接加入老的interval
            while (i < intervals.length && intervals[i][1] < newInterval[0]) {
                res.add(intervals[i++]);
            }
            // 当老 interval的开始小于新interval的结束时，新interval的开始就等于新老interval的起点的最小值
            while (i < intervals.length && intervals[i][0] <= newInterval[1]) {
                newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
                newInterval[1] = Math.max(newInterval[1], intervals[i++][1]);
            }
            res.add(newInterval);
            // 加上剩下的intervals
            while (i < intervals.length) {
                res.add(intervals[i++]);
            }
            return res.toArray(new int[res.size()][]);
        }
    }

    // 56 Merge Intervals

    /**
     * Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, and return an
     * array of the non-overlapping intervals that cover all the intervals in the input.
     *
     * Example 1:
     *
     * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
     * Output: [[1,6],[8,10],[15,18]]
     * Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6].
     * Example 2:
     *
     * Input: intervals = [[1,4],[4,5]]
     * Output: [[1,5]]
     * Explanation: Intervals [1,4] and [4,5] are considered overlapping.
     */
    class Solution56{
        public int[][] merge(int[][] intervals) {
            if (intervals == null || intervals.length == 0) return new int[][]{};
            Arrays.sort(intervals, (a ,b) -> (a[0] - b[0]));  // 通过起点排序
            int start = intervals[0][0];
            int end = intervals[0][1];
            List<int[]> res = new ArrayList<>();
            for (int[] interval : intervals) {
                // 如果后一个的start小于end,将前一个的end更新为后一个的end
                if (interval[0] <= end) end = Math.max(interval[1], end);
                else {
                    res.add(new int[]{start, end});
                    start = interval[0];
                    end = interval[1];
                }
            }
            res.add(new int[]{start,end});  // 没有办法判断最后一个
            return res.toArray(new int[][]{});
        }
    }

    // 435：Non-overlapping intervals

    /**
     * Given an array of intervals intervals where intervals[i] = [starti, endi], return the minimum number of
     * intervals you need to remove to make the rest of the intervals non-overlapping.
     *
     * Example 1:
     *
     * Input: intervals = [[1,2],[2,3],[3,4],[1,3]]
     * Output: 1
     * Explanation: [1,3] can be removed and the rest of the intervals are non-overlapping.
     * Example 2:
     *
     * Input: intervals = [[1,2],[1,2],[1,2]]
     * Output: 2
     * Explanation: You need to remove two [1,2] to make the rest of the intervals non-overlapping.
     * Example 3:
     *
     * Input: intervals = [[1,2],[2,3]]
     * Output: 0
     * Explanation: You don't need to remove any of the intervals since they're already non-overlapping.
     *
     * [[1,2], [1,3], [2,3], [3,4]]
     *
     */
    class Solution435{
        public int eraseOverLaoIntervals(int[][] intervals) {
            if (intervals.length == 0) return 0;
            Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
            int end = intervals[0][1];
            int count = 1;
            for (int i = 1; i < intervals.length; i ++) {
                // 当下一个interval的开始值大于end的时候，说明找到了一个没有overlap的，更新end值，count加一
                if (intervals[i][0] >= end) {
                    end = intervals[i][1];
                    count++;
                }
            }
            return intervals.length - count;
        }
    }

    // 436: Find right interval

    /**
     * You are given an array of intervals, where intervals[i] = [starti, endi] and each starti is unique.
     *
     * The right interval for an interval i is an interval j such that startj >= endi and startj is minimized. Note that i may equal j.
     *
     * Return an array of right interval indices for each interval i. If no right interval exists for interval i, then put -1 at index i.
     *
     * Example 1:
     *
     * Input: intervals = [[1,2]]
     * Output: [-1]
     * Explanation: There is only one interval in the collection, so it outputs -1.
     * Example 2:
     *
     * Input: intervals = [[3,4],[2,3],[1,2]]
     * Output: [-1,0,1]
     * Explanation: There is no right interval for [3,4].
     * The right interval for [2,3] is [3,4] since start0 = 3 is the smallest start that is >= end1 = 3.
     * The right interval for [1,2] is [2,3] since start1 = 2 is the smallest start that is >= end2 = 2.
     */
    class Solution436 {
        // 根据start进行排序，看到interval就想到排序，使用treemap 将start和index对应
        public int[] findRightInterval(int[][] intervals) {
            int[] res = new int[intervals.length];
            TreeMap<Integer, Integer> map = new TreeMap<>(); // treemap 从小到大排列，又排序又对应
            // 将intervals按照起始位置进行升序排列，index也要进行对应
            for (int i = 0; i < intervals.length; i++) {
                map.put(intervals[i][0], i);
            }
            for (int i = 0; i < intervals.length; i ++) {
                Integer key = map.ceilingKey(intervals[i][1]);  //  返回大于等于当前给定的key值
                res[i] = key != null ? map.get(key) : -1;
            }
            return res;
        }
    }

    // 325 Data Stream as Disjoint Intervals

    /**
     * Given a data stream input of non-negative integers a1, a2, ..., an, summarize the numbers seen
     * so far as a list of disjoint intervals.
     *
     * Implement the SummaryRanges class:
     *
     * SummaryRanges() Initializes the object with an empty stream.
     * void addNum(int val) Adds the integer val to the stream.
     * int[][] getIntervals() Returns a summary of the integers in the stream currently as a list of disjoint intervals [starti, endi].
     *
     * Example 1:
     *
     * Input
     * ["SummaryRanges", "addNum", "getIntervals", "addNum", "getIntervals", "addNum", "getIntervals", "addNum", "getIntervals", "addNum", "getIntervals"]
     * [[], [1], [], [3], [], [7], [], [2], [], [6], []]
     * Output
     * [null, null, [[1, 1]], null, [[1, 1], [3, 3]], null, [[1, 1], [3, 3], [7, 7]], null, [[1, 3], [7, 7]], null, [[1, 3], [6, 7]]]
     *
     * Explanation
     * SummaryRanges summaryRanges = new SummaryRanges();
     * summaryRanges.addNum(1);      // arr = [1]
     * summaryRanges.getIntervals(); // return [[1, 1]]
     * summaryRanges.addNum(3);      // arr = [1, 3]
     * summaryRanges.getIntervals(); // return [[1, 1], [3, 3]]
     * summaryRanges.addNum(7);      // arr = [1, 3, 7]
     * summaryRanges.getIntervals(); // return [[1, 1], [3, 3], [7, 7]]
     * summaryRanges.addNum(2);      // arr = [1, 2, 3, 7]
     * summaryRanges.getIntervals(); // return [[1, 3], [7, 7]]
     * summaryRanges.addNum(6);      // arr = [1, 2, 3, 6, 7]
     * summaryRanges.getIntervals(); // return [[1, 3], [6, 7]]
     */

    /**
     * TreeMap: 红黑树，排序
     * lowerKey: Returns the greatest key less than the given key
     * higherKey: returns the least key greater than the given key
     * treeMap kay: 1 3 7 12 val = 9
     * lowerKey: 7 higherKey: 12
     */
    class SummaryRanges {

        TreeMap<Integer, int[]> Map;

        public SummaryRanges() {
            Map = new TreeMap<>();
        }

        // O(logn)
        public void addNum(int val) {
            if (Map.containsKey(val)) return;
            Integer lowerKey = Map.lowerKey(val);
            Integer higherKey = Map.higherKey(val);
            // 如果lowerkey和higherKey都不是空的话，并且lowerKey的end +1等于val, val + 1等于higherKey的start
            // 说明这两个个interval 可以合并,
            // 将lowerKey的end等于higherKey的end, 并移除higherKey
            if (lowerKey != null && higherKey != null && Map.get(lowerKey)[1] + 1== val && val + 1== Map.get(higherKey)[0]) {
                Map.get(lowerKey)[1] = Map.get(higherKey)[1];
                Map.remove(higherKey);
            }else if (lowerKey != null && val <= Map.get(lowerKey)[1] + 1) {
                // 当higherKey为空并且val小于lowerKey的end+1时，代表当前加进的val值可以和之前的形成一个合并区间
                Map.get(lowerKey)[1] = Math.max(Map.get(lowerKey)[1], val);
            } else if (higherKey != null && Map.get(higherKey)[0] - 1 == val) {
                // 当higherKey不是空，lowerKey是空，并且higherKey的start - 1等于val时
                // 将新的interval和higherKey融合
                Map.put(val, new int[] {val, Map.get(higherKey)[1]});
                Map.remove(higherKey);
            } else {
                Map.put(val, new int[] {val, val});
            }
        }

        public int[][] getIntervals() {
            return Map.values().toArray(new int[][]{});
        }
    }

    /**
     * Your SummaryRanges object will be instantiated and called as such:
     * SummaryRanges obj = new SummaryRanges();
     * obj.addNum(val);
     * int[][] param_2 = obj.getIntervals();
     */

    // 406 : Queue Reconstruction by Height

    /**
     * You are given an array of people, people, which are the attributes of some people in a queue (not necessarily in order).
     * Each people[i] = [hi, ki] represents the ith person of height hi with exactly ki other people in front who have a height greater than or equal to hi.
     *
     * Reconstruct and return the queue that is represented by the input array people. The returned queue should be formatted
     * as an array queue, where queue[j] = [hj, kj] is the attributes of the jth person in the queue (queue[0] is the person at the front of the queue).
     *
     * Example 1:
     *
     * Input: people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]
     * Output: [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]
     * Explanation:
     * Person 0 has height 5 with no other people taller or the same height in front.
     * Person 1 has height 7 with no other people taller or the same height in front.
     * Person 2 has height 5 with two persons taller or the same height in front, which is person 0 and 1.
     * Person 3 has height 6 with one person taller or the same height in front, which is person 1.
     * Person 4 has height 4 with four people taller or the same height in front, which are people 0, 1, 2, and 3.
     * Person 5 has height 7 with one person taller or the same height in front, which is person 1.
     * Hence [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]] is the reconstructed queue.
     * Example 2:
     *
     * Input: people = [[6,0],[5,0],[4,0],[3,2],[2,2],[1,4]]
     * Output: [[4,0],[5,0],[2,2],[3,2],[1,4],[6,0]]
     *
     */
    class Solution406 {
        /*
        [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]
        第一次排序后：
        [7,0],[7,1],[6,1],[5,0],[5,2],[4,4]
        第二次排序后：
        [5,0][7,0],[5,2],[6,1],[4,4],[7,1]
         */
        public int[][] reconstructQueue(int[][] people) {
            if (people == null || people.length == 0 || people[0].length == 0) return new int[0][0];
            List<int[]> res=  new ArrayList<>();
            // 如果people的起始数字也就是高度相同的，按照第二个数字升序排列，如果不同，按照身高降序排列
            Arrays.sort(people, (a,b) -> (a[0] == b[0] ? a[1] - b[1] : b[0] - a[0]));
            for (int[] cur : people) {
                res.add(cur[1], cur);  // 将当前的people按照第二个数字插入当前的res里。
            }
            return res.toArray(new int[people.length][]);
        }
    }

    // 325 Maximum Size Subarray sum Equals K (子数组)

    /**
     *

     Given an array nums and a target value k, find the maximum length of a subarray that sums to k. If there isn't one, return 0 instead.

     Note:

     The sum of the entire nums array is guaranteed to fit within the 32-bit signed integer range.

     */
    /*
        设 sum[i] 表示 nums[0] + nums[1] + … + nums[i-1] 的和，称为第 i 位的前缀和。

        于是，如果存在两个索引 i 和 j，使得 sum[j] - sum[i] == k，说明找到一个子数组 [i, j-1] ，子数组的和为 k。
        定义一个 HashMap ，把 sum[i] 作为 key ，把 i 作为 value。如果有相同的 sum[i] ，我们保存 i 最小的那个。

        从 i == sum.length-1 开始遍历 map：

        遍历到的为 sum[i] ,如果在 map 中存在 sum[i]-k ，说明存在一个长度为 k 的子数组，现在我们得找到这个子数组的起始索引，
        即 map.get(sum[i]-k)，于是我们统计从 map.get(sum[i]-k) 到 i-1 长度为，并更新 maxLength。
     */
    class solution325{
        public int maxSubarrayLen(int[] nums, int k) {
            if (nums == null || nums.length == 0) return 0;
            int res = 0;
            HashMap<Integer, Integer> map = new HashMap<>();
            map.put(0, -1);

            // 计算前i项的和
            for (int i = 1; i < nums.length; i ++) {
                nums[i] += nums[i - 1];
            }
            for (int i = 0; i < nums.length; i++) {
                // 如果map里有nums[i] - k的key值, 说明之前有
                if (map.containsKey(nums[i] - k)){
                    res = Math.max(res, i - map.get(nums[i] - k));
                }
                if (!map.containsKey(nums[i])) {
                    map.put(nums[i], i);
                }
            }
            return res;
        }
    }

    // 209： Minimum Size SubArray Sum

    /**
     * Given an array of positive integers nums and a positive integer target, return the minimal length of a contiguous
     * subarray [numsl, numsl+1, ..., numsr-1, numsr] of which the sum is greater than or equal to target. If there
     * is no such subarray, return 0 instead.
     *
     * Example 1:
     *
     * Input: target = 7, nums = [2,3,1,2,4,3]
     * Output: 2
     * Explanation: The subarray [4,3] has the minimal length under the problem constraint.
     * Example 2:
     *
     * Input: target = 4, nums = [1,4,4]
     * Output: 1
     * Example 3:
     *
     * Input: target = 11, nums = [1,1,1,1,1,1,1,1]
     * Output: 0
     *
     */
    class Solujtion209{
        public int minSubArrayLen(int target, int[] nums) {
            int res = Integer.MAX_VALUE;
            int left = 0, sum = 0;
            for (int i = 0; i < nums.length; i ++) {
                sum += nums[i];
                while(left <= i && sum >= target) {
                    res = Math.min(res, i - left + 1);
                    sum -= nums[left++];
                }
            }
            return res == Integer.MAX_VALUE ? 0: res;
        }
    }

    // 523: Continuous Subarray Sum

    /**
     * Given an integer array nums and an integer k, return true if nums has a continuous subarray of size at
     * least two whose elements sum up to a multiple of k, or false otherwise.
     *
     * An integer x is a multiple of k if there exists an integer n such that x = n * k. 0 is always a multiple of k.
     *
     * Example 1:
     *
     * Input: nums = [23,2,4,6,7], k = 6
     * Output: true
     * Explanation: [2, 4] is a continuous subarray of size 2 whose elements sum up to 6.
     * Example 2:
     *
     * Input: nums = [23,2,6,4,7], k = 6
     * Output: true
     * Explanation: [23, 2, 6, 4, 7] is an continuous subarray of size 5 whose elements sum up to 42.
     * 42 is a multiple of 6 because 42 = 7 * 6 and 7 is an integer.
     */
    class Solution523{

        /**
         * 这道题如果使用暴力法的话会超时，对于一个中等题也不能指望用暴力法通过
         *
         * 也想到了前缀和，但就是想不出然后怎么利用前缀和降低时间复杂度
         *
         * 看了题解后发现自己未掌握一个定理：同余定理
         *
         * 如果 “(i − j) mod k = 0”， 那么 "i mod k = j mod k"
         *
         * 这样一来，如果前 i 个元素的前缀和对 k 取余后等于前 j 个元素的前缀和对 k 取余，那么这两个前缀和一减便是一个连续子区间的元素和（i、j之差必须大于等于2），且是 k 的倍数，正好符合题意
         *
         * 另外这个“等于”可以转换为“存在过”，而且为了判断下标之差要大于等于2，所以采用HashMap的方式存放“{取余的值, 下标}”
         *
         * 发现有一行代码为put(0, -1)，这是做什么？
         *
         * 其实源代码返回true的前提标准就是Map中出现过此余数，但是如果在计算前缀和的时候就出现余数为0的情况，讲道理应该直接返回true，但是由
         * 于0这个余数没有存放在Map中，所以不会立刻返回true，因此需要提前放置一个key为0的键值对。
         * 至于value，因为返回true的第二个标准为下标之差大于等于2，最极端的就是nums[]的前两个元素即满足题意，此时 j 为1，为了满足下标之差
         * ，只要设定一个小于0的value即可（也别太小，防止在计算时越界，按题目nums[]长度的取值范围来看，取-1准没错）
         */
        public boolean checkSubarraySum(int[] nums, int k) {
            int sum = 0;
            HashMap<Integer, Integer> map = new HashMap<>();
            map.put(0, -1);
            for (int i = 0; i < nums.length; i ++) {
                sum += nums[i];
                if (k != 0) sum = sum % k;
                if (map.containsKey(sum)) {
                    if (i - map.get(sum) > 1) return true;
                } else {
                    map.put(sum, i);
                }
            }
            return false;
        }

        // 暴力解法：
        public boolean check2(int[] nums, int k){
            int length = nums.length;
            if( length < 2 ) return false;
            for(int i = 0; i < length; i++) {
                int sum = nums[i];
                for(int j = i+1; j < length; j++) {
                    sum += nums[j];
                    //当k=0，且数组中所有元素也为0时也满足，但此时k不能作为除数
                    if( k == 0 && sum == k) return true;
                    if( k !=0 && sum % k == 0 ) return true;
                }
            }
            return false;
        }
    }

    // 560： SubArray sum equals K (子数组)

    /**
     * Given an array of integers nums and an integer k, return the total number of subarrays whose sum equals to k.
     *
     * A subarray is a contiguous non-empty sequence of elements within an array.
     * Example 1:
     *
     * Input: nums = [1,1,1], k = 2
     * Output: 2
     * Example 2:
     *
     * Input: nums = [1,2,3], k = 3
     * Output: 2
     */
    class solution560 {
        // 暴力解
        public int subArraySum(int[] nums, int k) {
            int res = 0;
            for (int i = 0; i < nums.length; i++) {
                int sum = 0;
                for (int j = i; j < nums.length; j++) {
                    sum += nums[j];
                    if (sum == k) {
                        res ++;
                    }
                }
            }
            return res;
        }

        public int subArraySum1(int[] nums, int k) {
            int res = 0;
            int sum = 0;
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
                if (map.containsKey(sum - k)) {
                    res += map.get(sum - k);
                }
                map.put(sum, map.getOrDefault(sum, 0) + 1);
            }
            return res;
        }
    }

    // 11: Container with most Water

    /**
     * You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints
     * of the ith line are (i, 0) and (i, height[i]).
     *
     * Find two lines that together with the x-axis form a container, such that the container contains the most water.
     *
     * Return the maximum amount of water a container can store.
     *
     * Notice that you may not slant the container.
     *
     * Input: height = [1,8,6,2,5,4,8,3,7]
     * Output: 49
     * Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7].
     * In this case, the max area of water (blue section) the container can contain is 49.
     * Example 2:
     *
     * Input: height = [1,1]
     * Output: 1
     */
    class Solution11 {
        public int maxArea(int[] height) {
            int res = 0;
            int l = 0, r = height.length - 1;
            while(l < r) {
                res = Math.max(res, Math.min(height[l], height[r]) * (r - l));  // 木桶原理，取l 和 r短的那个乘以长度就是面积
                if (height[l] < height[r]) {
                    l ++;  // 左边小于右边，左指针向右走
                } else r --;
            }
            return res;
        }
    }

    // 724 Find pivot index

    /**
     * Given an array of integers nums, calculate the pivot index of this array.
     *
     * The pivot index is the index where the sum of all the numbers strictly to the left of the index is
     * equal to the sum of all the numbers strictly to the index's right.
     *
     * If the index is on the left edge of the array, then the left sum is 0 because there are no elements
     * to the left. This also applies to the right edge of the array.
     *
     * Return the leftmost pivot index. If no such index exists, return -1.
     *
     *
     * Example 1:
     *
     * Input: nums = [1,7,3,6,5,6]
     * Output: 3
     * Explanation:
     * The pivot index is 3.
     * Left sum = nums[0] + nums[1] + nums[2] = 1 + 7 + 3 = 11
     * Right sum = nums[4] + nums[5] = 5 + 6 = 11
     * Example 2:
     *
     * Input: nums = [1,2,3]
     * Output: -1
     * Explanation:
     * There is no index that satisfies the conditions in the problem statement.
     * Example 3:
     *
     * Input: nums = [2,1,-1]
     * Output: 0
     * Explanation:
     * The pivot index is 0.
     * Left sum = 0 (no elements to the left of index 0)
     * Right sum = nums[1] + nums[2] = 1 + -1 = 0
     */
    class Solution724{
        public static int pivotIndex(int[] nums) {
            if (nums == null || nums.length == 0) return 0;
            int[] left = new int[nums.length];
            int[] right = new int[nums.length];

            left[0] = 0;
            for (int i = 1; i < nums.length; i++) {
                left[i] += nums[i - 1] + left[ i -  1];
            }

            right[nums.length - 1] = 0;
            for(int i = nums.length - 2; i >= 0; i--) {
                right[i] += nums[i + 1] + right[i + 1];
            }
            System.out.println(Arrays.toString(right));

            for (int i = 0; i < nums.length; i++) {
                if (left[i] == right[i]) return i;
            }
            return -1;
        }

        
        // 前缀和解法
        public int pivotIndex1(int[] nums) {
            int sum = 0, leftsum = 0;
            for (int x: nums) sum += x;
            for (int i = 0; i < nums.length; ++i) {
                if (leftsum == sum - leftsum - nums[i]) return i;
                leftsum += nums[i];
            }
            return -1;
        }
    }

    // 16 3 Sum Closest

    /**
     * Given an integer array nums of length n and an integer target, find three integers in nums such that the sum is closest to target.
     *
     * Return the sum of the three integers.
     *
     * You may assume that each input would have exactly one solution.
     *
     * Example 1:
     *
     * Input: nums = [-1,2,1,-4], target = 1
     * Output: 2
     * Explanation: The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
     * Example 2:
     *
     * Input: nums = [0,0,0], target = 1
     * Output: 0
     *
     */
    class Solution16 {
        public int threeSumClosest(int[] nums, int target) {
            int res = nums[0] + nums[1] + nums[nums.length - 1];
            Arrays.sort(nums);
            for (int i = 0; i < nums.length - 2; i ++) {
                int start = i + 1, end = nums.length - 1;
                while (start < end) {
                    int sum = nums[i] + nums[start] + nums[end];
                    if (sum > target) {
                        end--;
                    } else {
                        start++;
                    }
                    if (Math.abs(sum - target) < Math.abs(res - target)) {
                        res = sum;
                    }
                }
            }
            return res;
        }
    }

    // 18： 4Sum

    /**
     * Given an array nums of n integers
     * return an array of all the unique quadruplets [nums[a], nums[b], nums[c], nums[d]] such that:
     *
     * 0 <= a, b, c, d < n
     * a, b, c, and d are distinct.
     * nums[a] + nums[b] + nums[c] + nums[d] == target
     * You may return the answer in any order.
     *
     *
     *
     * Example 1:
     *
     * Input: nums = [1,0,-1,0,-2,2], target = 0
     * Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
     * Example 2:
     *
     * Input: nums = [2,2,2,2,2], target = 8
     * Output: [[2,2,2,2]]
     *
     */

    class Solution18{
        public List<List<Integer>> fourSum(int[] nums, int target) {
            List<List<Integer>> res = new ArrayList<>();
            if (nums.length < 4) return res;
            Arrays.sort(nums);

            for (int i = 0; i < nums.length - 3; i ++) {
                if (i > 0 && nums[i] == nums[i - 1]) continue;
                for (int j = i + 1; j < nums.length - 2; j++) {
                    if (j > i + 1 && nums[j] == nums[j - 1]) continue;
                    int low = j + 1, high = nums.length - 1;
                    while(low < high) {
                        long sum = (long)nums[i] + (long)nums[j] + (long)nums[low] + (long)nums[high];
                        if (sum == target) {
                            res.add(Arrays.asList(nums[i], nums[j], nums[low], nums[high]));
                            while(low < high && nums[low] == nums[low + 1]) low++;
                            while (low < high && nums[high] == nums[high - 1]) high--;
                            low++;
                            high--;
                        } else if (sum < target) {
                            low++;
                        } else{
                            high--;
                        }
                    }
                }
            }
            return res;
        }
    }

    // 454 4 sumII

    /**
     * Given four integer arrays nums1, nums2, nums3, and nums4 all of length n, return the number of tuples (i, j, k, l) such that:
     *
     * 0 <= i, j, k, l < n
     * nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0
     *
     *
     * Example 1:
     *
     * Input: nums1 = [1,2], nums2 = [-2,-1], nums3 = [-1,2], nums4 = [0,2]
     * Output: 2
     * Explanation:
     * The two tuples are:
     * 1. (0, 0, 0, 1) -> nums1[0] + nums2[0] + nums3[0] + nums4[1] = 1 + (-2) + (-1) + 2 = 0
     * 2. (1, 1, 0, 0) -> nums1[1] + nums2[1] + nums3[0] + nums4[0] = 2 + (-1) + (-1) + 0 = 0
     * Example 2:
     *
     * Input: nums1 = [0], nums2 = [0], nums3 = [0], nums4 = [0]
     * Output: 1
     */
    class Solution454 {
        public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
            HashMap<Integer, Integer> map = new HashMap<>();
            int res = 0;
            for (int a : nums1) {
                for (int b: nums2) {
                    int sum = a + b;
                    map.put(sum, map.getOrDefault(sum, 0) + 1);
                }
            }
            for (int c : nums3) {
                for (int d : nums4) {
                    int sum = -c -d;
                    res += map.getOrDefault(sum, 0);
                }
            }
            return res;
        }
    }

    // 189: Rotate Array
    class Solution189{
        public void ratate(int[] nums, int k) {
            int[] temp = new int[nums.length];
            for (int i = 0; i< nums.length; i++) {
                temp[(i + k) % nums.length]  = nums[i];
            }
            for (int i = 0; i < nums.length; i++) {
                nums[i] = temp[i];
            }
        }
    }

    // 280 wiggle sort

    /**
     * given an unsorted array nums, reorder it in-place such that nums[0] <= nums[1] >= nums[2] <= nums[3]....
     *
     * Example:
     * nums = [3, 5, 2, 1, 6, 4], one answer is: [1, 6 ,2, 5, 3, 4]
     */
    class Solution280{
        public void wiggleSort(int[] nums) {
            for (int i = 1; i < nums.length; i ++) {
                //  如果是奇数位，肯定是要大于前一个数的。如果当前奇数位小于前一个数，交换。
                // 如果当前位是哥偶数，当前数大于上一个数，就交换
                if ((i % 2 == 1) && nums[i] < nums[i - 1] || (i % 2 == 0 && nums[i] > nums[i - 1])) {
                    int temp = nums[ i - 1];
                    nums[i - 1] = nums[ i];
                    nums[i] = temp;
                }
            }
        }
    }

    // 41： first missing positive

    /**
     * Given an unsorted integer array nums, return the smallest missing positive integer.
     *
     * You must implement an algorithm that runs in O(n) time and uses constant extra space.
     *
     *
     *
     * Example 1:
     *
     * Input: nums = [1,2,0]
     * Output: 3
     * Example 2:
     *
     * Input: nums = [3,4,-1,1]
     * Output: 2
     * Example 3:
     *
     * Input: nums = [7,8,9,11,12]
     * Output: 1
     */
    class Solution41{
        public int firstMissingPositive(int[] nums) {
            if (nums == null || nums.length == 0) return 1;
            for (int i = 0; i < nums.length; i++) {
                //                    当前的index位置的值小于数组的长度  当前index的数字应该放在哪个位置
                while (nums[i] > 0 && nums[i] <= nums.length && nums[nums[i] - 1] != nums[i]) {
                    int temp = nums[nums[i] - 1];  // 将当前 index的数字放在它的值对应的位置上
                    nums[nums[i] - 1] = nums[i];
                    nums[i] = temp;
                }
            }
            // 将原数组上的数字排好序后，index相对应的值应该和i + 1相等
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] != i + 1) return i + 1;
            }
            return nums.length + 1;
        }
    }

    // 217 contains duplicate

    /**
     * Given an integer array nums, return true if any value appears at least twice in the array,
     * and return false if every element is distinct.
     *
     * Example 1:
     *
     * Input: nums = [1,2,3,1]
     * Output: true
     * Example 2:
     *
     * Input: nums = [1,2,3,4]
     * Output: false
     *
     */
    class Solution217{
        public boolean containsDuplicate(int[] nums) {
            HashSet<Integer> set = new HashSet<>();
            for (int i = 0; i < nums.length; i++) {
                // 如果能加入返回true, 不能加入（里面已经有了），返回false
                if (!set.add(nums[i])) return true;
            }
            return false;
        }

        public boolean containsDuplicate1(int[] nums) {
            Arrays.sort(nums);
            for (int i = 1; i < nums.length; i ++) {
                if (nums[i] == nums[i - 1]) return true;
            }
            return false;
        }
    }


    // 575 Distribute Candies

    /**
     * Alice has n candies, where the ith candy is of type candyType[i]. Alice noticed that she started to gain weight,
     * so she visited a doctor.
     *
     * The doctor advised Alice to only eat n / 2 of the candies she has (n is always even). Alice likes her candies very
     * much, and she wants to eat the maximum number of different types of candies while still following the doctor's advice.
     *
     * Given the integer array candyType of length n, return the maximum number of different types of candies she can
     * eat if she only eats n / 2 of them.
     *
     * Example 1:
     *
     * Input: candyType = [1,1,2,2,3,3]
     * Output: 3
     * Explanation: Alice can only eat 6 / 2 = 3 candies. Since there are only 3 types, she can eat one of each type.
     * Example 2:
     *
     * Input: candyType = [1,1,2,3]
     * Output: 2
     * Explanation: Alice can only eat 4 / 2 = 2 candies. Whether she eats types [1,2], [1,3], or [2,3], she still can only eat 2 different types.
     */

    class Solution575{
        /**
         * 此题就是返回 candyType / 2 和 糖的种类的最小值
         * @param candyType
         * @return
         */
        public int distributeCandies(int[] candyType) {
            HashSet<Integer> set = new HashSet<>();
            for (int candy : candyType) set.add(candy);
            return Math.min(set.size(), candyType.length / 2);

        }
    }

    // 525: Contiguous Array

    /**
     * Given a binary array nums, return the maximum length of a contiguous subarray with an equal number of 0 and 1.
     *
     * Example 1:
     *
     * Input: nums = [0,1]
     * Output: 2
     * Explanation: [0, 1] is the longest contiguous subarray with an equal number of 0 and 1.
     * Example 2:
     *
     * Input: nums = [0,1,0]
     * Output: 2
     * Explanation: [0, 1] (or [1, 0]) is a longest contiguous subarray with equal number of 0 and 1.
     */
    class Solution525{

        public int findMaxLength(int[] nums) {
            // key是count, value是index
            // 0和1数量相等时，count的值就为0
            HashMap<Integer, Integer> map = new HashMap<>();
            // index从0开始
            map.put(0, -1);
            int count = 0;
            int res = 0;
            for (int i = 0; i < nums.length; i++) {
                count = count + (nums[i] == 1 ? 1 : -1);
                if (map.containsKey(count)) res = Math.max(res, i - map.get(count));
                else map.put(count, i);
            }
            return res;
        }
    }

    // valid sudoku 重要，面经里有

    /**
     * Determine if a 9 x 9 Sudoku board is valid. Only the filled cells need to be validated according to the following rules:
     *
     * Each row must contain the digits 1-9 without repetition.
     * Each column must contain the digits 1-9 without repetition.
     * Each of the nine 3 x 3 sub-boxes of the grid must contain the digits 1-9 without repetition.
     * Note:
     *
     * A Sudoku board (partially filled) could be valid but is not necessarily solvable.
     * Only the filled cells need to be validated according to the mentioned rules.
     *
     * Example 1:
     *
     * Input: board =
     * [["5","3",".",".","7",".",".",".","."]
     * ,["6",".",".","1","9","5",".",".","."]
     * ,[".","9","8",".",".",".",".","6","."]
     * ,["8",".",".",".","6",".",".",".","3"]
     * ,["4",".",".","8",".","3",".",".","1"]
     * ,["7",".",".",".","2",".",".",".","6"]
     * ,[".","6",".",".",".",".","2","8","."]
     * ,[".",".",".","4","1","9",".",".","5"]
     * ,[".",".",".",".","8",".",".","7","9"]]
     * Output: true
     * Example 2:
     *
     * Input: board =
     * [["8","3",".",".","7",".",".",".","."]
     * ,["6",".",".","1","9","5",".",".","."]
     * ,[".","9","8",".",".",".",".","6","."]
     * ,["8",".",".",".","6",".",".",".","3"]
     * ,["4",".",".","8",".","3",".",".","1"]
     * ,["7",".",".",".","2",".",".",".","6"]
     * ,[".","6",".",".",".",".","2","8","."]
     * ,[".",".",".","4","1","9",".",".","5"]
     * ,[".",".",".",".","8",".",".","7","9"]]
     * Output: false
     * Explanation: Same as Example 1, except with the 5 in the top left corner being modified to 8. Since there are two 8's in the top left 3x3 sub-box, it is invalid.
     *
     */
    class Solution36{
        /**
         *
         * int rowIndex = 3 * (i / 3)
         * int colIndex = 3 * (i % 3)
         *
         * 0 1 2 3 4 5 6 7 8
         * 0 0 0 3 3 3 6 6 6    rowIndex = 0
         * 0 3 6 0 3 6 0 3 6    colIndex = 0
         *
         * 0 0 0 1 1 1 2 2 2    j / 3
         * 0 1 2 0 1 2 0 1 2    j % 3
         *
         * @param board
         * @return
         */
        public boolean isValidSudoku1(char[][] board) {
            for (int i = 0; i < board.length; i++) {
                HashSet<Character> rows = new HashSet<>();
                HashSet<Character> cols = new HashSet<>();
                HashSet<Character> cube = new HashSet<>();
                for (int j = 0; j < board[0].length; j++) {
                    // 有重复，set.add()返回false
                    if (board[i][j] != '.' && !rows.add(board[i][j])) return false;  // 行
                    if (board[j][i] != '.' && !cols.add(board[j][i])) return false;  // 列

                    int rowIndex = 3 * (i / 3);
                    int colIndex = 3 * (i % 3);

                    if (board[rowIndex + j /3][colIndex + j % 3] != '.' && !cube.add(board[rowIndex + j /3][colIndex + j % 3])) return false;
                }
            }
            return true;
        }

        public boolean isValidSudoku2(char[][] board) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] == '.') continue;
                    if(!valid(board, i, j)) return false;
                }
            }
            return true;
        }

        public boolean valid(char[][] board, int i, int j) {
            for (int row = 0; row < board.length; row ++) {
                if (row == i) continue;
                if (board[row][j] == board[i][j]) return false;
            }
            for (int col = 0; col < board.length; col ++) {
                if (col == i) continue;
                if (board[i][col] == board[i][j]) return false;
            }
            for (int row = (i / 3) * 3;row < (i / 3 + 1) * 3; row++){
                for (int col = (j / 3) * 3; col < (j / 3 + 1) * 3; col++) {
                    if (row == i && col == j) continue;
                    if (board[row][col] == board[i][j]) return false;
                }
            }
            return true;
        }
    }

    // 37: Sudoku solver

    /**
     * Write a program to solve a Sudoku puzzle by filling the empty cells.
     *
     * A sudoku solution must satisfy all of the following rules:
     *
     * Each of the digits 1-9 must occur exactly once in each row.
     * Each of the digits 1-9 must occur exactly once in each column.
     * Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
     * The '.' character indicates empty cells.
     *
     *
     *
     * Example 1:
     *
     *
     * Input: board =
     * [["5","3",".",".","7",".",".",".","."],
     * ["6",".",".","1","9","5",".",".","."],
     * [".","9","8",".",".",".",".","6","."],
     * ["8",".",".",".","6",".",".",".","3"],
     * ["4",".",".","8",".","3",".",".","1"],
     * ["7",".",".",".","2",".",".",".","6"],
     * [".","6",".",".",".",".","2","8","."],
     * [".",".",".","4","1","9",".",".","5"],
     * [".",".",".",".","8",".",".","7","9"]]
     * Output:
     * [["5","3","4","6","7","8","9","1","2"],
     * ["6","7","2","1","9","5","3","4","8"],
     * ["1","9","8","3","4","2","5","6","7"],
     * ["8","5","9","7","6","1","4","2","3"],
     * ["4","2","6","8","5","3","7","9","1"],
     * ["7","1","3","9","2","4","8","5","6"],
     * ["9","6","1","5","3","7","2","8","4"],
     * ["2","8","7","4","1","9","6","3","5"],
     * ["3","4","5","2","8","6","1","7","9"]]
     *
     */
    class Solution37{
        public void solveSudoku(char[][] board) {
            if (board == null || board.length == 0) return;
            solve(board);
        }

        public boolean solve(char[][] board) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] == '.') {
                        for (char c = '1'; c <= '9'; c ++) {  // 从1到9选择字母填充
                            if (isvalid(board, i , j, c)) {  // 如果当前位置填充后可以组成数独就替换
                                board[i][j] = c;
                                if (solve(board)) return true;
                                else board[i][j] = '.';
                            }
                        }
                        return false;  // 一到九都不行的话直接返回false
                    }
                }
            }
            return true;
        }

        public boolean isvalid(char[][] board, int row, int col, char c) {
            for (int i = 0; i < 9; i ++) {
                if (board[i][col] != '.' && board[i][col] == c) return false;  //  列
                if (board[row][i] != '.' && board[row][i] == c) return false;  // 行
                if (board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3]  != '.' &&  // 九宫格
                        board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == c) {
                    return false;
                }
            }
            return true;
        }

    }

    // 54: Spiral Matrix

    /**
     * Given an m x n matrix, return all elements of the matrix in spiral order.
     *
     *
     *
     * Example 1:
     *
     *
     * Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
     * Output: [1,2,3,6,9,8,7,4,5]
     * Example 2:
     *
     *
     * Input: matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
     * Output: [1,2,3,4,8,12,11,10,9,5,6,7]
     */

    class Solution54 {
        public List<Integer> spiralOrder(int[][] matrix) {
            List<Integer> res = new ArrayList<>();
            if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) return res;

            int rowEnd = matrix.length - 1, colEnd = matrix[0].length - 1;
            int rowBegin = 0, colBegin = 0;
            while (rowBegin <= rowEnd && colBegin <= colEnd) {
                for (int i = colBegin; i < colEnd; i++) res.add(matrix[rowBegin][i]);
                rowBegin++;
                for (int i = rowBegin; i <= rowEnd; i++) res.add(matrix[i][colEnd]);
                colEnd--;
                if (rowBegin <= rowEnd) {
                    for (int i = colEnd; i >= rowBegin; i--) res.add(matrix[rowEnd][i]);
                }
                rowEnd--;
                if (colBegin <= colEnd) {
                    for (int i = rowEnd; i >= rowBegin; i--) res.add(matrix[i][colBegin]);
                }
                colBegin++;
            }
            return res;
        }
    }

    // 1706: Where will the ball fall

    /**
     * You have a 2-D grid of size m x n representing a box, and you have n balls. The box is open on the top and bottom sides.
     *
     * Each cell in the box has a diagonal board spanning two corners of the cell that can redirect a ball to the right or to the left.
     *
     * A board that redirects the ball to the right spans the top-left corner to the bottom-right corner and is represented in the grid as 1.
     * A board that redirects the ball to the left spans the top-right corner to the bottom-left corner and is represented in the grid as -1.
     * We drop one ball at the top of each column of the box. Each ball can get stuck in the box or fall out of the bottom.
     * A ball gets stuck if it hits a "V" shaped pattern between two boards or if a board redirects the ball into either wall of the box.
     *
     * Return an array answer of size n where answer[i] is the column that the ball falls out of at the bottom after
     * dropping the ball from the ith column at the top, or -1 if the ball gets stuck in the box.
     *
     * Example 1:
     *
     *
     *
     * Input: grid = [[1,1,1,-1,-1],[1,1,1,-1,-1],[-1,-1,-1,1,1],[1,1,1,1,-1],[-1,-1,-1,-1,-1]]
     * Output: [1,-1,-1,-1,-1]
     * Explanation: This example is shown in the photo.
     * Ball b0 is dropped at column 0 and falls out of the box at column 1.
     * Ball b1 is dropped at column 1 and will get stuck in the box between column 2 and 3 and row 1.
     * Ball b2 is dropped at column 2 and will get stuck on the box between column 2 and 3 and row 0.
     * Ball b3 is dropped at column 3 and will get stuck on the box between column 2 and 3 and row 0.
     * Ball b4 is dropped at column 4 and will get stuck on the box between column 2 and 3 and row 1.
     * Example 2:
     *
     * Input: grid = [[-1]]
     * Output: [-1]
     * Explanation: The ball gets stuck against the left wall.
     * Example 3:
     *
     * Input: grid = [[1,1,1,1,1,1],[-1,-1,-1,-1,-1,-1],[1,1,1,1,1,1],[-1,-1,-1,-1,-1,-1]]
     * Output: [0,1,2,3,4,-1]
     */

    class Solution1706 {
        public int[] findBall(int[][] grid) {
            int n = grid[0].length;
            int[] ans = new int[n];
            for (int j = 0; j < n; j++) {
                int col = j;  // 球的初始列
                for (int[] row : grid) {
                    int dir = row[col];
                    col += dir;  // 移动球
                    if (col < 0 || col == n || row[col] != dir) {  // 到达侧边或 V 形
                        col = -1;
                        break;
                    }
                }
                ans[j] = col;  // col >= 0 为成功到达底部
            }
            return ans;
        }
    }

    // 532 K-diff pairs in an array

    /**
     * Given an array of integers nums and an integer k, return the number of unique k-diff pairs in the array.
     *
     * A k-diff pair is an integer pair (nums[i], nums[j]), where the following are true:
     *
     * 0 <= i, j < nums.length
     * i != j
     * nums[i] - nums[j] == k
     * Notice that |val| denotes the absolute value of val.
     *
     *
     *
     * Example 1:
     *
     * Input: nums = [3,1,4,1,5], k = 2
     * Output: 2
     * Explanation: There are two 2-diff pairs in the array, (1, 3) and (3, 5).
     * Although we have two 1s in the input, we should only return the number of unique pairs.
     * Example 2:
     *
     * Input: nums = [1,2,3,4,5], k = 1
     * Output: 4
     * Explanation: There are four 1-diff pairs in the array, (1, 2), (2, 3), (3, 4) and (4, 5).
     */
    class Solution532 {
        public int findPairs(int[] nums, int k) {
            if (nums == null || nums.length == 0 || k < 0) return 0;
            HashMap<Integer, Integer> map = new HashMap<>();
            int res = 0;
            for (int num : nums) {
                map.put(num, map.getOrDefault(num, 0) + 1);
            }

            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if (k == 0) {
                    if (entry.getValue() >= 2) {
                        res++;
                    }
                } else {
                    if (map.containsKey(entry.getKey() + k) ) res++;
                }
            }
            return res;
        }
    }

    // 554: Brick wall

    /**
     * There is a rectangular brick wall in front of you with n rows of bricks. The ith row has some number of bricks each of the same height (i.e., one unit) but they can be of different widths. The total width of each row is the same.
     *
     * Draw a vertical line from the top to the bottom and cross the least bricks. If your line goes through the edge of a brick, then the brick is not considered as crossed. You cannot draw a line just along one of the two vertical edges of the wall, in which case the line will obviously cross no bricks.
     *
     * Given the 2D array wall that contains the information about the wall, return the minimum number of crossed bricks after drawing such a vertical line.
     *
     *
     *
     * Example 1:
     *
     *
     * Input: wall = [[1,2,2,1],[3,1,2],[1,3,2],[2,4],[3,1,2],[1,3,1,1]]
     * Output: 2
     * Example 2:
     *
     * Input: wall = [[1],[1],[1]]
     * Output: 3
     */
    class Solution554 {

        // 找缝隙出现次数最大的
        // hashmap的key存砖头到哪里的位置， value存出现的次数，在value出现次数最大的地方一刀切
        public int leastBricks(List<List<Integer>> wall) {
            HashMap<Integer, Integer> map = new HashMap<>();
            for (List<Integer> list : wall) {
                int sum = 0;
                for (int i = 0 ; i < list.size() - 1; i++) {  // 在最后一个元素之前一个停止
                    sum += list.get(i);
                    map.put(sum, map.getOrDefault(sum, 0) + 1);
                }
            }
            int res= wall.size();
            for (int key: map.keySet()) {
                res = Math.min(res, wall.size() - map.get(key));
            }
            return res;
        }
    }

    // 599： minimum index sum of two lists

    /**
     * Suppose Andy and Doris want to choose a restaurant for dinner, and they both have a list of favorite restaurants
     * represented by strings.
     *
     * You need to help them find out their common interest with the least list index sum. If there is a choice tie
     * between answers, output all of them with no order requirement. You could assume there always exists an answer.
     *
     * Example 1:
     *
     * Input: list1 = ["Shogun","Tapioca Express","Burger King","KFC"], list2 = ["Piatti","The Grill at Torrey Pines",
     * "Hungry Hunter Steakhouse","Shogun"]
     * Output: ["Shogun"]
     * Explanation: The only restaurant they both like is "Shogun".
     * Example 2:
     *
     * Input: list1 = ["Shogun","Tapioca Express","Burger King","KFC"], list2 = ["KFC","Shogun","Burger King"]
     * Output: ["Shogun"]
     * Explanation: The restaurant they both like and have the least index sum is "Shogun" with index sum 1 (0+1).
     *
     */
    class Solution599 {
        public String[] findRestaurant(String[] list1, String[] list2) {
            HashMap<String, Integer> map=  new HashMap<>();
            for (int i = 0 ; i < list1.length; i++) {
                map.put(list1[i], i);
            }

            List<String> res= new LinkedList<>();
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < list2.length; i ++) {
                Integer j = map.get(list2[i]);
                if (j != null && i + j <= min) {  // 不一定只有一个结果
                    if (i + j < min) {
                        res.clear();  // 之前的结果不能要了
                        min = i + j;  // 更新最小值
                    }
                    res.add(list2[i]);
                }
            }
            return res.toArray(new String[res.size()]);
        }
    }


    // 442： Find all duplicates in an array

    /**
     * Given an integer array nums of length n where all the integers of nums are in the range [1, n] and each integer
     * appears once or twice, return an array of all the integers that appears twice.
     *
     * You must write an algorithm that runs in O(n) time and uses only constant extra space.
     *
     * Example 1:
     *
     * Input: nums = [4,3,2,7,8,2,3,1]
     * Output: [2,3]
     * Example 2:
     *
     * Input: nums = [1,1,2]
     * Output: [1]
     * Example 3:
     *
     * Input: nums = [1]
     * Output: []
     */
    class Solution442 {
        public List<Integer> findDuplicates(int[] nums) {
            List<Integer> res= new ArrayList<>();
            if (nums == null || nums.length == 0) return res;
            for (int i = 0; i < nums.length; i++) {
                int index = Math.abs(nums[i]) -1 ;
                if (nums[index] < 0) res.add(Math.abs(index + 1));  // 如果找到了负数，代表之前已经找过了，加入结果
                nums[index] = -nums[index];  // 用负数表示当前的数已经查找过一次
            }
            return res;
        }
    }

    // 414： Third Maximum Number

    /**
     * Given an integer array nums, return the third distinct maximum number in this array. If the third maximum does
     * not exist, return the maximum number.
     *
     * Example 1:
     *
     * Input: nums = [3,2,1]
     * Output: 1
     * Explanation:
     * The first distinct maximum is 3.
     * The second distinct maximum is 2.
     * The third distinct maximum is 1.
     * Example 2:
     *
     * Input: nums = [1,2]
     * Output: 2
     * Explanation:
     * The first distinct maximum is 2.
     * The second distinct maximum is 1.
     * The third distinct maximum does not exist, so the maximum (2) is returned instead.
     * Example 3:
     *
     * Input: nums = [2,2,3,1]
     * Output: 1
     * Explanation:
     * The first distinct maximum is 3.
     * The second distinct maximum is 2 (both 2's are counted together since they have the same value).
     * The third distinct maximum is 1.
     */
    class Solution414 {
        public int thirdMax(int[] nums) {
            Integer max1 = null;
            Integer max2 = null;
            Integer max3 = null;
            for (Integer num : nums) {
                if (nums.equals(max1) || num.equals(max2) || num.equals(max3)) continue;
                if (max1 == null || num > max1) {
                    max3 = max2;
                    max2 = max1;
                    max1 = num;
                } else if (max2 == null || num > max2) {
                    max3= max2;
                    max2 = num;
                } else if (max3 == null || num > max3) {
                    max3 = num;
                }
            }
            return max3 == null ? max1 : max3;
        }


        public int thirdMax2(int[] nums) {
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            HashSet<Integer> set = new HashSet<>();
            for (int num : nums) {
                if (set.add(num)) {
                    pq.offer(num);
                    if (pq.size() > 3) pq.poll();
                }
            }
            if (pq.size() == 2) pq.poll();
            return pq.peek();
        }
    }

    // 448: Find all numbers Disappeared in an array

    /**
     * Given an array nums of n integers where nums[i] is in the range [1, n], return an array of all the integers in the range [1, n] that do not appear in nums.
     *
     * Example 1:
     *
     * Input: nums = [4,3,2,7,8,2,3,1]
     * Output: [5,6]
     * Example 2:
     *
     * Input: nums = [1,1]
     * Output: [2]
     */

    class Solution448 {
        public List<Integer> findDisappearedNumbers(int[] nums) {
            List<Integer> res= new ArrayList<>();
            for (int i = 0 ; i < nums.length; i ++) {
                int index = Math.abs(nums[i]) - 1;  // 将index对应的数变为负数
                if (nums[index] > 0) nums[index] = -nums[index];  // 如果数字最后没有变成负数，那就是没有出现的
            }
            for (int i = 0; i < nums.length; i ++) {
                if (nums[i] > 0) res.add(i + 1);
            }
            return res;
        }
    }

    // 621: Task Scheduler

    /**
     * Given a characters array tasks, representing the tasks a CPU needs to do, where each letter represents a different
     * task. Tasks could be done in any order. Each task is done in one unit of time. For each unit of time, the CPU
     * could complete either one task or just be idle.
     *
     * However, there is a non-negative integer n that represents the cooldown period between two same tasks (the same
     * letter in the array), that is that there must be at least n units of time between any two same tasks.
     *
     * Return the least number of units of times that the CPU will take to finish all the given tasks.
     *
     *
     *
     * Example 1:
     *
     * Input: tasks = ["A","A","A","B","B","B"], n = 2
     * Output: 8
     * Explanation:
     * A -> B -> idle -> A -> B -> idle -> A -> B
     * There is at least 2 units of time between any two same tasks.
     * Example 2:
     *
     * Input: tasks = ["A","A","A","B","B","B"], n = 0
     * Output: 6
     * Explanation: On this case any permutation of size 6 would work since n = 0.
     * ["A","A","A","B","B","B"]
     * ["A","B","A","B","A","B"]
     * ["B","B","B","A","A","A"]
     * ...
     * And so on.
     * Example 3:
     *
     * Input: tasks = ["A","A","A","A","A","A","B","C","D","E","F","G"], n = 2
     * Output: 16
     * Explanation:
     * One possible solution is
     * A -> B -> C -> A -> D -> E -> A -> F -> G -> A -> idle -> idle -> A -> idle -> idle -> A
     *
     */

    class Solution621 {
        /**
         * 假设所给任务列表当中所需执行次数最多的是任务A，那么我们可以用一个矩阵来展现执行任务A的时间点。由于CPU执行相同种类的任务之间必须至少有
         * 长度为 n 的冷却时间，为了让冷却时间最短，该矩阵的宽度就应该设置为 n+1，而该矩阵的高度就是任务A所需执行的次数
         *
         *      n = 2
         *
         *      n + 1
         *      A * *
         *      A * *
         *      A * *
         *      A * *
         *      A * *
         *      A * *
         *
         * 如果任务列表当中只有任务A，那么该矩阵中除了执行任务A的位置之外，其他位置所对应的就是 “待命状态”，并且当执行完最后一个任务A时，
         * 所有任务就都已经执行完毕了，因此矩阵最后一行当中剩下的 n−1 位置此时不算入完成任务的所需时间
         *
         *  A idle idle
         *  A idle idle
         *  A idle idle
         *  A idle idle
         *  A idle idle
         *  A X    X
         *
         *  此时完成所有任务所需的时间就是： (MaxExec − 1) × (n + 1) + 1
         * 如果任务列表当中除了任务A之外还有其他种类的任务，那么这些任务就可以添加到图中 “待命状态” 的位置，如果这些任务能够全部被安排在 “待命状态”
         * 当中，那么此时完成所有任务所需的时间与之前是相同的。
         *
         * A B C
         * A B C
         * A B C
         * A B idle
         * A B idle
         * A X X
         *
         * 任务A是所需执行次数最多的任务，因此不会有比任务A所需执行次数更多的任务，但我们无法保证没有所需执行次数与任务A相同的任务。如果出现了这种情况，
         * 我们就需要在矩阵的最后一行添加位置来执行该任务。
         *
         * 因此，如果任务列表当中所需执行次数等于 MaxExec 的任务个数为 num，那么完成所有任务所需的时间就是：(MaxExec−1)×(n+1)+num
         *
         * 现在我们就是将其他种类的任务添加到矩阵当中 “待命状态” 的位置，如果该任务所需执行的次数与 M a x E x e c MaxExec MaxExec
         * 相等，那么我们需要在矩阵的最后一行添加对应的位置。但如果矩阵当中的 “待命状态” 的位置都被填满了呢？此时任务列表当中剩余的任务应该怎么办？
         *
         * 此时我们可以将矩阵中某些行的宽度进行扩充，然后将任务插入到这些扩展的位置当中即可。因为在矩阵没有扩充时，
         * 矩阵中同种任务之间执行的间隔时间恰好是 n n n，既然现在矩阵当中 “待命状态” 的位置被填满了，那么我们可以直接将剩下的任务插入到矩阵的行后面，
         * 插入后原矩阵当中的任务没有违反 “同种任务之间执行的间隔时间至少是 n n n ” 的规定，并且新插入的任务也是满足冷却要求的。
         *
         * 因此，如果任务列表当中所需执行任务的总数大于我们计算得到的矩阵的大小，那么此时完成所有任务所需的时间就是任务列表当中任务的个数
         *
         *
         * A B C E
         * A B C E
         * A B D
         * A B D
         * A B X
         */
        public int leastInterval(char[] tasks, int n) {
            int[] count = new int[26];
            for (char c : tasks) {
                count[c - 'A']++;  // 统计每个字母出现次数
            }
            Arrays.sort(count);  // 词频升序排列，count25是最高的
            int maxCount = 0;  // 统计有多少个频率最高的字母
            for (int i = 25; i >= 0; i--) {
                if (count[i] != count[25]) {
                    break;
                }
                maxCount++;
            }
            // n小于种类的时候，会比长度小
            return Math.max((count[25] - 1) * (n + 1) + maxCount, tasks.length);
        }
    }

    // 277：Find Celebrity 谷歌考过

    /**
     * 假设你是一个专业的狗仔，参加了一个 n 人派对，其中每个人被从 0 到 n - 1 标号。在这个派对人群当中可能存在一位 “名人”。所谓 “名人”
     * 的定义是：其他所有 n - 1 个人都认识他/她，而他/她并不认识其他任何人。
     * 现在你想要确认这个 “名人” 是谁，或者确定这里没有 “名人”。而你唯一能做的就是问诸如 “A 你好呀，请问你认不认识 B呀？” 的问题，以确定 A
     * 是否认识 B。你需要在（渐近意义上）尽可能少的问题内来确定这位 “名人” 是谁（或者确定这里没有 “名人”）。
     * 在本题中，你可以使用辅助函数 bool knows(a, b) 获取到 A 是否认识 B。请你来实现一个函数 int findCelebrity(n)。
     *
     * 派对最多只会有一个 “名人” 参加。若 “名人” 存在，请返回他/她的编号；若 “名人” 不存在，请返回 -1。
     */
    class Solution277 {
        public int findCelebrity(int n){
            if (n < 2) return -1;
            int possible = 0;
            for (int i = 0; i < n; i++) {
                if (knows(possible, i)) possible = i;  // 如果这个人认识了 i，possible 肯定不是名人， i可能是名人
            }
            for (int i = 0 ; i < n; i ++) {
                // possible 认识其中一个人，或者任何人不认识possible都返回-1
                if (possible != i && (knows(possible, i) || !knows(i, possible))) return -1;
            }
            return possible;
        }

        public boolean knows(int a, int b) {
            return true;
        }
    }

    // 495:Temo attacking

    /**
     * Our hero Teemo is attacking an enemy Ashe with poison attacks! When Teemo attacks Ashe, Ashe gets poisoned for a
     * exactly duration seconds. More formally, an attack at second t will mean Ashe is poisoned during the inclusive
     * time interval [t, t + duration - 1]. If Teemo attacks again before the poison effect ends, the timer for it is
     * reset, and the poison effect will end duration seconds after the new attack.
     *
     * You are given a non-decreasing integer array timeSeries, where timeSeries[i] denotes that Teemo attacks Ashe
     * at second timeSeries[i], and an integer duration.
     *
     * Return the total number of seconds that Ashe is poisoned.
     *
     * Example 1:
     *
     * Input: timeSeries = [1,4], duration = 2
     * Output: 4
     * Explanation: Teemo's attacks on Ashe go as follows:
     * - At second 1, Teemo attacks, and Ashe is poisoned for seconds 1 and 2.
     * - At second 4, Teemo attacks, and Ashe is poisoned for seconds 4 and 5.
     * Ashe is poisoned for seconds 1, 2, 4, and 5, which is 4 seconds in total.
     * Example 2:
     *
     * Input: timeSeries = [1,2], duration = 2
     * Output: 3
     * Explanation: Teemo's attacks on Ashe go as follows:
     * - At second 1, Teemo attacks, and Ashe is poisoned for seconds 1 and 2.
     * - At second 2 however, Teemo attacks again and resets the poison timer. Ashe is poisoned for seconds 2 and 3.
     * Ashe is poisoned for seconds 1, 2, and 3, which is 3 seconds in total.
     *
     */

    class Solution495 {
        public int findPoisonedDuration(int[] timeSeries, int duration) {
            if (timeSeries == null || timeSeries.length == 0) return 0;
            int sum = 0;
            for (int i = 0;  i< timeSeries.length; i++) {
                sum += Math.min(timeSeries[i] - timeSeries[ i - 1], duration);
            }
            return sum + duration;
        }
    }

    // 370: Range Addition

    /**
     * 假设你有一个长度为 n 的数组，初始情况下所有的数字均为 0，你将会被给出 k 个更新的操作。
     *
     * 其中，每个操作会被表示为一个三元组：[startIndex, endIndex, inc]，你需要将子数组 A[startIndex … endIndex]（包括 startIndex 和 endIndex）增加 inc。
     *
     * 请你返回 k 次操作后的数组。
     */
    class Solution370{
        // O（K+ N）
        public int[] getModifiedArray(int length, int [][] updates) {
            int[] res = new int[length];
            for (int[] update: updates) {
                int value = update[2];
                int start = update[0];
                int end  = update[1];
                res[start] += value;  // 把开始的位置设为value, 把最后的位置的后一个位置变成负的，相当于一个终止符
                if (end + 1 < length) res[end  + 1] -= value;
            }
            for (int i = 0; i < length; i++) {
                res[i] += res[i - 1];
            }
            return res;
        }
    }

    // 296: best Meeging point

    /**
     * 有一队人，（两人或以上）想要在一个地方碰面，他们希望能够最小化他们的总行走距离
     *
     * 给你一个2D网格，其中各个格子内的值要么是0，要么是1
     *
     * 1表示某个人的家所处的位置，这里，我们将用曼哈顿距离来计算，其中Distance(p1, p2) = |p2.x - p1.x| + |p2.y - p1.y|
     *
     * 1 - 0 - 0 - 0 - 1
     * |   |   |   |   |
     * 0 - 0 - 0 - 0 - 0
     * |   |   |   |   |
     * 0 - 0 - 1 - 0 - 0
     *
     * return 6
     *
     */
    class Solution296{
        /**
         * 1. 降维
         *     横向：
         *     A      C  E       F   D         B
         *     将横坐标降维后需要找一个点到ABCDEF的距离和最小，肯定在 EF 之间，距离为 |B - A| + |D - c| + |F - E|
         *     纵向一样的
         */
        public int minTotalDistance(int[][] grid) {
            // time： O（MN）
            // space: O(N)
            int m  = grid.length;
            int n = grid[0].length;
            List<Integer> I = new ArrayList<>();
            List<Integer> J = new ArrayList<>();
            for (int i = 0; i < m; i++) {  // 按行去扫，
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        I.add(i);   //  从小到大添加
                    }
                }
            }

            for (int j = 0; j < n; j++) {
                for (int i = 0; i < m; i++) {
                    if (grid[i][j] == 1) {
                        J.add(j);  // 按列从小到大添加
                    }
                }
            }
            return min(I) + min(J);
        }

        private int min(List<Integer> list) {
            int i = 0, j = list.size();
            int sum = 0;
            while(i < j) sum+= list.get(j--) - list.get(i++);  // 计算尾部减去头部的差值
            return sum;
        }

    }


    // 1122 : Relative sort array

    /**
     * Given two arrays arr1 and arr2, the elements of arr2 are distinct, and all elements in arr2 are also in arr1.
     *
     * Sort the elements of arr1 such that the relative ordering of items in arr1 are the same as in arr2. Elements
     * that do not appear in arr2 should be placed at the end of arr1 in ascending order.
     *
     *
     *
     * Example 1:
     *
     * Input: arr1 = [2,3,1,3,2,4,6,7,9,2,19], arr2 = [2,1,4,3,9,6]
     * Output: [2,2,2,1,4,3,3,9,6,7,19]
     * Example 2:
     *
     * Input: arr1 = [28,6,22,8,44,17], arr2 = [22,28,8,6]
     * Output: [22,28,8,6,17,44]
     */
    class Solution1122 {
        public int[] relativeSortArray(int[] arr1, int[] arr2) {
            HashMap<Integer, Integer> map = new HashMap<>();
            List<Integer> others = new ArrayList<>();
            int[] res = new int[arr1.length];

            for (int i : arr2) {  // 将arr2的数字全部放入map
                map.put(i, 0);
            }

            for (int i : arr1) {  // 数arr1中的数字，在map中的直接加加数量， 不在的加入tmp
                if (map.containsKey(i)) {
                    map.put(i, map.get(i) + 1);
                } else {
                    others.add(i);
                }
            }

            int start = 0;
            for (int i : arr2) {
                int times = map.get(i);
                for (int j = 0;  j< times; j++) {  // 将map中的数字按顺序加入
                    res[start++] = i;
                }
            }

            if (start < arr1.length) {
                Collections.sort(others);  // others中的数字排序后加入
                for (int i = 0; i < others.size(); i++) {
                    res[start++] = others.get(i);
                }
            }

            return res;
        }
    }

    // 163: Missing ranges

    /**
     * You are given an inclusive range [lower, upper] and a sorted unique integer array nums, where all elements are in
     * the inclusive range.
     *
     * A number x is considered missing if x is in the range [lower, upper] and x is not in nums.
     *
     * Return the smallest sorted list of ranges that cover every missing number exactly. That is, no element of nums is
     * in any of the ranges, and each missing number is in one of the ranges.
     *
     * Each range [a,b] in the list should be output as:
     *
     * "a->b" if a != b
     * "a" if a == b
     *
     * Example 1:
     *
     * Input: nums = [0,1,3,50,75], lower = 0, upper = 99
     * Output: ["2","4->49","51->74","76->99"]
     * Explanation: The ranges are:
     * [2,2] --> "2"
     * [4,49] --> "4->49"
     * [51,74] --> "51->74"
     * [76,99] --> "76->99"
     * Example 2:
     *
     * Input: nums = [-1], lower = -1, upper = -1
     * Output: []
     * Explanation: There are no missing ranges since there are no missing numbers.
     */
    class solution163{

        // 小心越界：
        // [2147483647] 0 2147483647
        // [0 -> 2147483646]
        public List<String> findMissingRanges(int[] nums, int lower, int upper){
            List<String> res = new ArrayList<>();
            long alower = (long) lower, aupper = (long)upper;
            for (int num : nums) {
                if (num == alower) {
                    alower++;  // 是否是下界
                } else if (alower < num){
                    if (alower + 1 == num) res.add(String.valueOf(alower));
                    else {
                        res.add(alower + "->" + (num - 1));
                    }
                    alower = (long)num + 1;
                }
            }
            if (alower == aupper) res.add(String.valueOf(alower));
            else if (alower < aupper) res.add(alower + "->" + aupper);
            return res;
        }
    }

    // 719: Find K-th smallest pair Distance

    /**
     * The distance of a pair of integers a and b is defined as the absolute difference between a and b.
     *
     * Given an integer array nums and an integer k, return the kth smallest distance among all the pairs nums[i]
     * and nums[j] where 0 <= i < j < nums.length.
     *
     * Example 1:
     *
     * Input: nums = [1,3,1], k = 1
     * Output: 0
     * Explanation: Here are all the pairs:
     * (1,3) -> 2
     * (1,1) -> 0
     * (3,1) -> 2
     * Then the 1st smallest distance pair is (1,1), and its distance is 0.
     * Example 2:
     *
     * Input: nums = [1,1,1], k = 2
     * Output: 0
     * Example 3:
     *
     * Input: nums = [1,6,1], k = 3
     * Output: 5
     */
    class Solution719{
        // PriorityQueue: Time limit exceed
        public int smallestDistancePair(int[] nums, int k) {
            if (nums == null || nums.length == 0) return 0;
            List<Integer> list = new ArrayList<>();
            for (int i =0; i < nums.length - 1; i++) {
                for (int j = i + 1; j < nums.length; j++) {
                    list.add(Math.abs(nums[j] - nums[i]));
                }
            }
            PriorityQueue<Integer> pq = new PriorityQueue<>((a, b)->b - a);
            for (int idx : list) {
                pq.offer(idx);
                if (pq.size() > k) {
                    pq.poll();
                }
            }
            return pq.poll();
        }

        // 二分法
        // 题目要求数组中两两数字之间第 k 小的数对距离，需求就是两个：计算数对距离、找到第 k 小个距离。那么我们就根据题目分析，
        // 数对距离的范围是在从 0 到数组最大值减数组最小值，即 [0, max(nums)-min(nums)] 这个范围上。那么我们就可以转换一下思路，
        // 将原题改成在 [0, max(nums)-min(nums)] 这个范围上寻找数组中的第 k 小数对距离。
        //思考到这一步之后这道题就容易多了，我们首先对数组按照从小到大进行排序，主体使用二分查找进行，在每一次计算 mid 之后，在数组中计算小于等于
        // mid 的数堆个数，如果大于 k ，则向左半区域继续查找；如果小于等于 k，则向右半区域继续查找，直到 left 等于 right ，最后返回 left 或 right 。
        //以题目示例 3 为例再对过程进行分析：
        //数组为 [1, 6, 1]，k 为 3，此时对数组排序后数组变为 [1, 1, 6]，那么我们的查找范围就是 [0, 5]
        public int smallestDistancePair1(int[] nums, int k) {
            Arrays.sort(nums);
            int left = 0;
            int right = nums[nums.length - 1] - nums[0];
            while (left < right) {
                int mid = (left + right) / 2;
                int countPairs = countPairs(nums, mid);
                if (countPairs < k) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            return left;
        }

        // 查找数组中两数距离小于mid的对数
        public int countPairs(int[] nums, int mid) {
            int l = 0;
            int r = 0;
            int count = 0;
            while (r < nums.length) {
                while (l < r && nums[r] - nums[l] > mid) {
                    l++;
                }
                count += r - l;
                r++;
            }
            return count;
        }
    }

    // 289: game of life

    /**
     *
     * According to Wikipedia's article: "The Game of Life, also known simply as Life, is a cellular automaton devised
     * by the British mathematician John Horton Conway in 1970."
     *
     * The board is made up of an m x n grid of cells, where each cell has an initial state: live (represented by a 1)
     * or dead (represented by a 0). Each cell interacts with its eight neighbors (horizontal, vertical, diagonal)
     * using the following four rules (taken from the above Wikipedia article):
     *
     * Any live cell with fewer than two live neighbors dies as if caused by under-population.
     * Any live cell with two or three live neighbors lives on to the next generation.
     * Any live cell with more than three live neighbors dies, as if by over-population.
     * Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
     * The next state is created by applying the above rules simultaneously to every cell in the current state,
     * where births and deaths occur simultaneously. Given the current state of the m x n grid board, return the next state.
     *
     *
     *
     * Example 1:
     *
     *
     * Input: board = [[0,1,0],[0,0,1],[1,1,1],[0,0,0]]
     * Output: [[0,0,0],[1,0,1],[0,1,1],[0,1,0]]
     * Example 2:
     *
     *
     * Input: board = [[1,1],[1,0]]
     * Output: [[1,1],[1,1]]
     */
    class Solution289 {

        // 00: 死  下一轮： 10 >> 01
        // 01： 活  下一轮: 11 >> 01

        /*
        下一轮如果是活着的话，通过移位的运算可以变成下一个状态
        01 00 01
        01 00 00
        00 00 01

         */
        public void gameOfLife(int[][] board) {
            if (board == null || board.length == 0) return;
            int m = board.length;
            int n = board[0].length;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    // 算周围有多少存活的
                    int count = countNeighbor(board, i, j);
                    if (board[i][j] == 1) {  // 如果当前是活细胞
                        if (count == 2 || count == 3) board[i][j] += 2; // +2相当于在00的第一位加一
                    } else if (count == 3) board[i][j] += 2;  // 如果是死细胞，变活
                }

            }
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    board[i][j] = board[i][j] >> 1;  //  整体向右移动一位
                }
            }
        }

        private int countNeighbor(int[][] board, int i, int j) {
            int count = 0;
            // 算九宫格里的
            for (int row = Math.max(0, i - 1); row <= Math.min(i + 1, board.length - 1); row ++) {
                for (int col = Math.max(0, j - 1); col <= Math.min(j + 1, board[0].length - 1); col++) {
                    if (row == i && col == j) continue;
                    // 这里&算的是当前的状态比如说00和10, 第一位是下一刻的第二位是此刻的状态
                    if ((board[row][col] & 1) == 1) count++;
                }
            }
            return count;
        }
    }

    // 128： Longest consecutive subsequence

    /**
     * Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
     *
     * You must write an algorithm that runs in O(n) time.
     *
     * Example 1:
     *
     * Input: nums = [100,4,200,1,3,2]
     * Output: 4
     * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
     * Example 2:
     *
     * Input: nums = [0,3,7,2,5,8,4,6,0,1]
     * Output: 9
     */
    class Solution128 {
        public int longestConsecutive(int[] nums) {
            if (nums == null || nums.length == 0) return 0;
            HashSet<Integer> set = new HashSet<>();
            int res = 0;
            for (int i : nums) {
                set.add(i);  // 加入set中，之后查看有没有连续的
            }
            for (int i = 0; i < nums.length; i++) {
                int down = nums[i] - 1;  // down等于下一个数字
                while (set.contains(down)) {
                    set.remove(down);  // 如果set里有下一个数字，继续找
                    down --;
                }
                int up = nums[i] + 1;
                while(set.contains(up)) {
                    set.remove(up);
                    up++;
                }
                res = Math.max(res, up - down - 1);
            }
            return res;
        }
    }

    // 561: Array Partition

    /**
     *
     * Given an integer array nums of 2n integers, group these integers into n pairs (a1, b1), (a2, b2), ..., (an, bn)
     * such that the sum of min(ai, bi) for all i is maximized. Return the maximized sum.
     *
     * Example 1:
     *
     * Input: nums = [1,4,3,2]
     * Output: 4
     * Explanation: All possible pairings (ignoring the ordering of elements) are:
     * 1. (1, 4), (2, 3) -> min(1, 4) + min(2, 3) = 1 + 2 = 3
     * 2. (1, 3), (2, 4) -> min(1, 3) + min(2, 4) = 1 + 2 = 3
     * 3. (1, 2), (3, 4) -> min(1, 2) + min(3, 4) = 1 + 3 = 4
     * So the maximum possible sum is 4.
     * Example 2:
     *
     * Input: nums = [6,2,6,5,1,2]
     * Output: 9
     * Explanation: The optimal pairing is (2, 1), (2, 5), (6, 6). min(2, 1) + min(2, 5) + min(6, 6) = 1 + 2 + 6 = 9.
     */
    class Solution561{
        public int arrayPairSum(int[] nums) {
            Arrays.sort(nums);
            int res = 0;
            for (int i = 0; i < nums.length; i+= 2) {
                res += nums[i];  // 贪心的添加，最大值为排外序后的技术位的数字
            }
            return res;
        }
    }

    // 376: wiggle Subsequence

    /**
     *
     * A wiggle sequence is a sequence where the differences between successive numbers strictly alternate between
     * positive and negative. The first difference (if one exists) may be either positive or negative. A sequence
     * with one element and a sequence with two non-equal elements are trivially wiggle sequences.
     *
     * For example, [1, 7, 4, 9, 2, 5] is a wiggle sequence because the differences (6, -3, 5, -7, 3) alternate between
     * positive and negative.
     * In contrast, [1, 4, 7, 2, 5] and [1, 7, 4, 5, 5] are not wiggle sequences. The first is not because its first two
     * differences are positive, and the second is not because its last difference is zero.
     * A subsequence is obtained by deleting some elements (possibly zero) from the original sequence, leaving the
     * remaining elements in their original order.
     *
     * Given an integer array nums, return the length of the longest wiggle subsequence of nums.
     *
     * Example 1:
     *
     * Input: nums = [1,7,4,9,2,5]
     * Output: 6
     * Explanation: The entire sequence is a wiggle sequence with differences (6, -3, 5, -7, 3).
     * Example 2:
     *
     * Input: nums = [1,17,5,10,13,15,10,5,16,8]
     * Output: 7
     * Explanation: There are several subsequences that achieve this length.
     * One is [1, 17, 10, 13, 10, 16, 8] with differences (16, -7, 3, -3, 6, -8).
     */
    class Solution376{
        // DP
        public int wiggleMaxLength(int[] nums) {
            if (nums == null || nums.length == 0) return 0;
            int[] up = new int[nums.length];
            int[] down = new int[nums.length];

            up[0] = 1;
            down[0] = 1;
            for (int i = 1; i < nums.length; i++) {
                if (nums[i] > nums[i - 1]) {
                    up[i] = down[i - 1] + 1;
                    down[i] = down[i - 1];
                } else if (nums[i] < nums[i - 1]) {
                    down[i] = up[i - 1] + 1;
                    up[i] = up[i - 1];
                } else {
                    down[i] = down[i - 1];
                    up[i] = up[i - 1];
                }
            }
            return Math.max(down[nums.length - 1], up[nums.length - 1]);
        }
    }

    // 463: Island Perimeter

    /**
     *
     * You are given row x col grid representing a map where grid[i][j] = 1 represents land and grid[i][j] = 0 represents water.
     *
     * Grid cells are connected horizontally/vertically (not diagonally). The grid is completely surrounded by water,
     * and there is exactly one island (i.e., one or more connected land cells).
     *
     * The island doesn't have "lakes", meaning the water inside isn't connected to the water around the island.
     * One cell is a square with side length 1. The grid is rectangular, width and height don't exceed 100. Determine the perimeter of the island.
     *
     * Example 1:
     *
     * Input: grid = [[0,1,0,0],[1,1,1,0],[0,1,0,0],[1,1,0,0]]
     * Output: 16
     * Explanation: The perimeter is the 16 yellow stripes in the image above.
     * Example 2:
     *
     * Input: grid = [[1]]
     * Output: 4
     * Example 3:
     *
     * Input: grid = [[1,0]]
     * Output: 4
     */
    class Solution463 {
        public int islandPerimeter(int[][] grid) {
            int islands = 0;
            int neighbor = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] == 1) {
                        islands++;
                        if (i < grid.length - 1 && grid[i + 1][j] == 1) {
                            neighbor++;
                        }
                        if (j < grid[i].length - 1 && grid[i][j + 1] == 1) {
                            neighbor++;
                        }
                    }
                }
            }
            return islands * 4 - neighbor * 2;
        }
    }

    // whole minute dilemma

    /**
     * find the pairs which add up to form a multiple fo 60
     *
     * [40, 20, 60]
     * return 1
     *
     * [10, 50, 90 ,30]
     * return 2
     *
     */

    public long wholedilemma(ArrayList<Integer> len){
        long res = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i : len) {
            if (map.containsKey((60 - i % 60) % 60)) {
                res += map.get((60 - i % 60) % 60);
            }
            map.put(i, map.getOrDefault(60 - i % 60, 0) + 1);
        }
        return res;
    }

    // 164 ： Maximum gap

    /**
     * Given an integer array nums, return the maximum difference between two successive elements in its sorted form.
     * If the array contains less than two elements, return 0.
     *
     * You must write an algorithm that runs in linear time and uses linear extra space.
     *
     * Example 1:
     *
     * Input: nums = [3,6,9,1]
     * Output: 3
     * Explanation: The sorted form of the array is [1,3,6,9], either (3,6) or (6,9) has the maximum difference 3.
     * Example 2:
     *
     * Input: nums = [10]
     * Output: 0
     * Explanation: The array contains less than 2 elements, therefore return 0.
     */
    class Solution164 {
        public int maximumGap(int[] nums) {
            if (nums == null || nums.length < 2) return 0;
            int len = nums.length;
            int max = nums[0], min = nums[0];
            for (int i = 0; i < nums.length; i ++) {
                max = Math.max(max, nums[i]);
                min = Math.min(min, nums[i]);
            }
            //  最大的间隔是多少
            int  gap = (int) Math.ceil((double) (max - min) / (len - 1));
            int[] backetsMin = new int[len - 1];  // 有n个数就有n-1个区间，把区间的最大值和最小值表示
            int[] backetsMax = new int[len - 1];
            Arrays.fill(backetsMax, Integer.MIN_VALUE);
            Arrays.fill(backetsMin, Integer.MAX_VALUE);
            for (int num: nums) {
                if (num == min || num == max) continue;
                int bucket = (num - min) / gap;  // 当前数是属于什么区间
                backetsMin[bucket] = Math.min(backetsMin[bucket], num);
                backetsMax[bucket] = Math.max(num, backetsMax[bucket]);
            }
            int res = 0;
            int pre = min;
            for (int i = 0; i < len - 1; i++) {
                if (backetsMin[i] == Integer.MAX_VALUE && backetsMax[i] == Integer.MIN_VALUE){
                    continue;
                }
                res = Math.max(res, backetsMin[i] - pre);  // 我们的做法是要将数组中的数放到一个个桶里面，不断更新更大的
                // （后一个桶内元素的最小值 - 前一个桶内元素的最大值），最后就得到了答案。
                pre = backetsMax[i];
            }
            res = Math.max(res, max - pre);
            return res;

        }
    }

    // 487： Max Consecutive ones

    /**
     * Given a binary array nums, return the maximum number of consecutive 1's in the array if you can flip at most one 0.
     *
     * Example 1:
     *
     * Input: nums = [1,0,1,1,0]
     * Output: 4
     * Explanation:
     * - If we flip the first zero, nums becomes [1,1,1,1,0] and we have 4 consecutive ones.
     * - If we flip the second zero, nums becomes [1,0,1,1,1] and we have 3 consecutive ones.
     * The max number of consecutive ones is 4.
     * Example 2:
     *
     * Input: nums = [1,0,1,1,0,1]
     * Output: 4
     * Explanation:
     * - If we flip the first zero, nums becomes [1,1,1,1,0,1] and we have 4 consecutive ones.
     * - If we flip the second zero, nums becomes [1,0,1,1,1,1] and we have 4 consecutive ones.
     * The max number of consecutive ones is 4.
     */

    class Solution487{

        // Follow up: 可以翻转 K 次
        //  sliding window

        public int findConsecutiveOnes(int[] nums) {
            int res = 0;
            int k = 1;
            Queue<Integer> queue = new LinkedList<>();
            int left = 0, right = 0;

            while (right < nums.length) {
                if (nums[right] == 0) queue.offer(right);  // 记录窗口右边的位置
                if (queue.size() > k) {
                    left = queue.poll() + 1;  // 左指针等于第一个0的右边一个位置
                }
                res = Math.max(res, right - left + 1);
                right++;
            }
            return res;


        }

    }

    public static void main(String[] args) {

    }

}



