package com.company.Notes;

import java.util.Set;

public class Note15_SegmentTree {
    // 303 Range Sum Query - Immutable

    /**
     * Given an integer array nums, handle multiple queries of the following type:
     *
     * Calculate the sum of the elements of nums between indices left and right inclusive where left <= right.
     * Implement the NumArray class:
     *
     * NumArray(int[] nums) Initializes the object with the integer array nums.
     * int sumRange(int left, int right) Returns the sum of the elements of nums between indices left and right
     * inclusive (i.e. nums[left] + nums[left + 1] + ... + nums[right]).
     *
     * Input
     * ["NumArray", "sumRange", "sumRange", "sumRange"]
     * [[[-2, 0, 3, -5, 2, -1]], [0, 2], [2, 5], [0, 5]]
     * Output
     * [null, 1, -1, -3]
     */
    class NumArray{
        private int[] sum;

        public NumArray(int[] nums) {
            sum = new int[nums.length + 1];
            for (int i = 0; i < nums.length; i++) {
                sum[i + 1] = sum[i] + nums[i];
            }
        }

        public int sumRange(int left, int right) {
            return sum[right + 1] - sum[left];
        }
    }

    // 307 Range Sum Query - Mutable
    /**
     * Given an integer array nums, handle multiple queries of the following types:
     *
     * Update the value of an element in nums.
     * Calculate the sum of the elements of nums between indices left and right inclusive where left <= right.
     * Implement the NumArray class:
     *
     * NumArray(int[] nums) Initializes the object with the integer array nums.
     * void update(int index, int val) Updates the value of nums[index] to be val.
     * int sumRange(int left, int right) Returns the sum of the elements of nums between indices left and right
     * inclusive (i.e. nums[left] + nums[left + 1] + ... + nums[right]).
     *
     *
     * Example 1:
     *
     * Input
     * ["NumArray", "sumRange", "update", "sumRange"]
     * [[[1, 3, 5]], [0, 2], [1, 2], [0, 2]]
     * Output
     * [null, 9, null, 8]
     *
     * Explanation
     * NumArray numArray = new NumArray([1, 3, 5]);
     * numArray.sumRange(0, 2); // return 1 + 3 + 5 = 9
     * numArray.update(1, 2);   // nums = [1, 2, 5]
     * numArray.sumRange(0, 2); // return 1 + 2 + 5 = 8
     */

    class NumArray1 {

        SegmentTreeNode root;

        public NumArray1(int[] nums) {
            root = buildTree(nums, 0, nums.length - 1);
        }

        private SegmentTreeNode buildTree(int[] nums, int start, int end) {  // 分治进行插入节点
            if (start > end) return null;
            SegmentTreeNode cur = new SegmentTreeNode(start, end);
            if (start == end) cur.sum = nums[start];  // 如果start和end相等了，说明不能再分了，直接加入sum值
            else {
                int mid = start + (end - start) / 2;  // 取中间
                cur.left = buildTree(nums, start , mid);  // 左边进行拆分
                cur.right = buildTree(nums, mid + 1, end);  // 右边进行拆分
                cur.sum = cur.left.sum + cur.right.sum;
            }
            return cur;
        }

        public void update(int index, int val) {
            update(root, index, val);
        }

        public void update(SegmentTreeNode cur, int pos, int val) {
            if (cur.start == cur.end) {
                cur.sum = val;  // 如果start=end了直接替换value
                return;
            }
            int mid = cur.start + (cur.end - cur.start / 2);
            if (pos <= mid) {  //如果pos小于mid走左边
                update(cur.left, pos, val);
            } else {
                update(cur.right, pos, val);
            }
            cur.sum = cur.left.sum + cur.right.sum;
        }

        public int sumRange(int i, int j) {
            return sumRange(root, i, j);
        }

        private int sumRange(SegmentTreeNode cur, int start, int end) {
            if (cur.end == end && cur.start == start) return cur.sum;
            int mid = cur.start + (cur.end - cur.start) / 2;
            if (end <= mid) return sumRange(cur.left, start, end);  // 如果end小于中间的mid,走左边
            else if (start >= mid + 1) return sumRange(cur.right, start, end);  // 如果start大于mid+1,走右边
            else return sumRange(cur.right, mid + 1, end) + sumRange(root.left, start, mid);
        }

        class SegmentTreeNode{
            int start;
            int end;
            SegmentTreeNode left;
            SegmentTreeNode right;
            int sum;

            public SegmentTreeNode(int start, int end) {
                this.start = start;
                this.end = end;
                this.left = null;
                this.right = null;
                this.sum = 0;
            }
        }
    }

}
