package com.company.Notes;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: algorithms
 * @package: com.company.Notes
 * @className: test1
 * @author: Danny
 * @description: TODO
 * @date: 2023/8/22 22:47
 * @version: 1.0
 */
public class test1 {
    /**
     * 给你一个整数数组 nums ，数组中的元素互不相同 。返回该数组所有可能的子集（幂集）。
     *
     * 解集不能包含重复的子集。你可以按任意顺序返回解集。
     *
     * 示例 1：
     *
     * 输入：nums = [1,2,3]
     * 输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
     *
     * 示例 2：
     *
     * 输入：nums = [0]
     * 输出：[[],[0]]
     */
    /*
    1 2 3
    / | \
   1  2  3
  /\  \
  2 3  3
  |
  3
     */
    public static List<List<Integer>> test(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        helper(nums, new ArrayList<>(), 0, res);
        return res;
    }

    public static void helper(int[] nums, List<Integer> tmp, int cur, List<List<Integer>> res) {
        res.add(new ArrayList<>(tmp));
        for (int i = cur; i < nums.length; i ++) {
            tmp.add(nums[cur]);
            helper(nums, tmp, cur + 1,res);
            tmp.remove(tmp.size() - 1);
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3};
        System.out.println(test(nums));
    }
}
