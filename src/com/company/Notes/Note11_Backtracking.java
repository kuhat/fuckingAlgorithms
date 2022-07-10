package com.company.Notes;

import java.util.Arrays;

public class Note11_Backtracking {
    // LeetCode 869: Reorder of power 2

    /**
     * You are given an integer n. We reorder the digits in any order (including the original order) such that the leading digit is not zero.
     *
     *     Return true if and only if we can do this so that the resulting number is a power of two.
     *
     * Input: n = 1
     * Output: true
     *
     * Input: n = 10
     * Output: false
     */
    /*
    此题用回溯算法查找每一个数字的组合
    回溯算法的模板：
    private void backtrack("原始参数") {
        //终止条件(递归必须要有终止条件)
        if ("终止条件") {
            //一些逻辑操作（可有可无，视情况而定）
            return;
        }
        for (int i = "for循环开始的参数"; i < "for循环结束的参数"; i++) {
        //一些逻辑操作（可有可无，视情况而定）
        //做出选择
        //递归
        backtrack("新的参数");
        //一些逻辑操作（可有可无，视情况而定）
        //撤销选择
        }
    }
    */
    class Solution869 {
        public boolean reorderedPowerOf2(int n) {
            // 数字转化为字符数组
            char[] numChar = String.valueOf(n).toCharArray();
            // 先对数组排序
            Arrays.sort(numChar);
            return backtrack(numChar, new boolean[numChar.length], 0 , 0);
        }

        public boolean backtrack(char[] numChar, boolean[] visit, int index, int num) {
            // 所有数组都访问完了，判断num是否2的次幂
            if (index == numChar.length) {
                return isPowerOfTwo(num);
            }
            for (int i = 0; i < numChar.length; i++) {
                // 有前导0的跳过
                if (num == 0 && numChar[i] == '0') {
                    continue;
                }
                // 被访问过的字符直接跳过
                if (visit[i]) continue;
                // 剪枝，可以参考（47，全排列ii），选择同一个元素再前一个分支没有成功，那么
                // 再当前分支也不会成功，所以可以直接过滤掉
                if (i > 0 && numChar[i - 1]== numChar[i] && visit[i - 1]) continue;
                visit[i] = true;
                if (backtrack(numChar, visit, index + 1, num * 10 + numChar[i] - '0')) return true;
                // 如果没有成功
                visit[i] = false;
            }
            return false;
        }

        //
        public boolean isPowerOfTwo(int n) {
            return (n & (n - 1)) == 0;  // 二进制判断是否为2的次幂
        }
    }

}
