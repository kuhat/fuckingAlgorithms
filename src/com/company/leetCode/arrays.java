package com.company.leetCode;

import java.util.*;

public class arrays {

    /**
     * 17.
     * 乘积最大子数组
     * 给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
     * <p>
     * 输入: [2,3,-2,4]
     * 输出: 6
     * 解释: 子数组 [2,3] 有最大乘积 6。
     */
    public int maxProduct(int[] nums) {
        int max = Integer.MIN_VALUE, imax = 1, imin = 1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < 0) {
                int tmp = imin;
                imin = imax;
                imax = tmp;
            }
            imax = Math.max(nums[i], nums[i] * imax);
            imin = Math.min(nums[i], nums[i] * imin);

            max = Math.max(imax, max);
        }

        return max;
    }

    /**
     * 18.
     * 多数元素
     * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
     * <p>
     * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
     * 输入：[3,2,3]
     * 输出：3
     * <p>
     * 输入：[2,2,1,1,1,2,2]
     * 输出：2
     */

    public int majorityElements(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    /**
     * 19. 旋转数组
     * 给你一个数组，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
     * <p>
     * 输入: nums = [1,2,3,4,5,6,7], k = 3
     * 输出: [5,6,7,1,2,3,4]
     * 解释:
     * 向右轮转 1 步: [7,1,2,3,4,5,6]
     * 向右轮转 2 步: [6,7,1,2,3,4,5]
     * 向右轮转 3 步: [5,6,7,1,2,3,4]
     */
    public void rotate(int[] nums, int k) {
        int[] n = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            n[i] = nums[i];
        }
        for (int i = 0; i < nums.length; i++) {
            nums[(k + i) % nums.length] = n[i];
        }
    }

    /**
     * 20.
     * 存在重复元素
     * 给你一个整数数组 nums 。如果任一值在数组中出现 至少两次 ，返回 true ；如果数组中每个元素互不相同，返回 false 。
     * 输入：nums = [1,2,3,1]
     * 输出：true
     * <p>
     * 输入：nums = [1,2,3,4]
     * 输出：false
     */

    public boolean containsDuplicate(int[] nums) {
        Set numSet = new HashSet<>();
        for (int num :
                nums) {
            numSet.add(num);
        }
        if (nums.length != numSet.size()) {
            return true;
        }
        return false;
    }

    /**
     * 21. 移动零
     * <p>
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     * <p>
     * 请注意 ，必须在不复制数组的情况下原地对数组进行操作。
     * 输入: nums = [0,1,0,3,12]
     * 输出: [1,3,12,0,0]
     *
     * [0,0,0,2,0,3,0,0,4]
     * [2,3,4,0,0,0,0,0,0]
     */

    public void moveZeros(int[] nums) {
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                continue;
            } else {
                nums[k] = nums[i];
                k++;
            }
        }
        while (k < nums.length) nums[k++] = 0;
    }

    /**
     * 22. 打乱数组
     * <p>
     * 给你一个整数数组 nums ，设计算法来打乱一个没有重复元素的数组。打乱后，数组的所有排列应该是 等可能 的。
     * <p>
     * 实现 Solution class:
     * <p>
     * Solution(int[] nums) 使用整数数组 nums 初始化对象
     * int[] reset() 重设数组到它的初始状态并返回
     * int[] shuffle() 返回数组随机打乱后的结果
     * <p>
     * 输入
     * ["Solution", "shuffle", "reset", "shuffle"]
     * [[[1, 2, 3]], [], [], []]
     * 输出
     * [null, [3, 1, 2], [1, 2, 3], [1, 3, 2]]
     * <p>
     * 解释
     * Solution solution = new Solution([1, 2, 3]);
     * solution.shuffle();    // 打乱数组 [1,2,3] 并返回结果。任何 [1,2,3]的排列返回的概率应该相同。例如，返回 [3, 1, 2]
     * solution.reset();      // 重设数组到它的初始状态 [1, 2, 3] 。返回 [1, 2, 3]
     * solution.shuffle();    // 随机返回数组 [1, 2, 3] 打乱后的结果。例如，返回 [1, 3, 2]
     */

    class Solution {
        int[] nums;
        int n;
        Random random = new Random();

        public Solution(int[] _nums) {
            nums = _nums;
            n = nums.length;
        }

        public int[] reset() {
            return nums;
        }

        public int[] shuffle() {
            int[] ans = nums.clone();
            for (int i = 0; i < n; i++) {
                swap(ans, i, i + random.nextInt(n - i));
            }
            return ans;
        }

        void swap(int[] arr, int i, int j) {
            int c = arr[i];
            arr[i] = arr[j];
            arr[j] = c;
        }

        /**
         * Your Solution object will be instantiated and called as such:
         * Solution obj = new Solution(nums);
         * int[] param_1 = obj.reset();
         * int[] param_2 = obj.shuffle();
         */
    }

    /**
     * 两个数组的交集 II
     * 给你两个整数数组 nums1 和 nums2 ，请你以数组形式返回两数组的交集。返回结果中每个元素出现的次数，应与元素在两个数组中都出现的次数一致（如果出现次数不一致，则考虑取较小值）。可以不考虑输出结果的顺序。
     * <p>
     * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
     * 输出：[2,2]
     * <p>
     * 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
     * 输出：[4,9]
     */

    public int[] intersect(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            return intersect(nums2, nums1);
        }
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int num : nums1) {
            int count = map.getOrDefault(num, 0) + 1;
            map.put(num, count);
        }
        int[] intersection = new int[nums1.length];
        int index = 0;
        for (int num :
                nums2) {
            int count = map.getOrDefault(num, 0);
            if (count > 0){
                intersection[index++] = num;
                count --;
                if (count > 0){
                    map.put(num, count);
                }else{
                    map.remove(num);
                }
            }
        }
        return Arrays.copyOf(intersection, index);
    }

    /**
     * 递增的三元子序列
     * 给你一个整数数组 nums ，判断这个数组中是否存在长度为 3 的递增子序列。
     *
     * 如果存在这样的三元组下标 (i, j, k) 且满足 i < j < k ，使得 nums[i] < nums[j] < nums[k] ，返回 true ；否则，返回 false 。
     *
     * 输入：nums = [1,2,3,4,5]
     * 输出：true
     * 解释：任何 i < j < k 的三元组都满足题意
     *
     * 输入：nums = [5,4,3,2,1]
     * 输出：false
     * 解释：不存在满足题意的三元组
     *
     * 输入：nums = [5,4,3,6,1,9]
     * 输出：true
     */

    public boolean increasingTriplet(int[] nums) {
        int a = nums[0], b = Integer.MAX_VALUE;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] <= a) a = nums[i];
            else if (nums[i] <= b) b = nums[i];
            else return true;
        }
        return false;
    }

    // 238
    /**
     * 除自身以外数组的乘积
     * 给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。
     *
     * 题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。
     *
     * 请不要使用除法，且在 O(n) 时间复杂度内完成此题。
     * 输入: nums = [1,2,3,4]
     * 输出: [24,12,8,6]
     *
     * 输入: nums = [-1,1,0,-3,3]
     * 输出: [0,0,9,0,0]
     */

    public int[] productExceptSelf(int[] nums) {
        int length = nums.length;
        int[] L = new int[length]; // L为i元素左侧的累计乘积
        int[] R = new int[length]; // L为i元素右侧的累计乘积
        int[] answer = new int[length];

        L[0] = 1;
        // Computer the accumulated product of the first i item
        for (int i = 1; i < nums.length; i++){
            L[i] = nums[i - 1] * L[i - 1];
        }

        // Compute the accumulated product of the end i item
        R[nums.length - 1] = 1;
        for (int i = nums.length - 2; i >= 0 ; i--) {
            R[i] = nums[i + 1] * R[i + 1];
        }

        for (int i = 0; i < length; i ++) {
            answer[i] = L[i] * R[i];
        }

        return answer;
    }

    //

}
