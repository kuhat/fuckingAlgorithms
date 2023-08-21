package com.company;

import java.util.Arrays;

/**
 * @projectName: algorithms
 * @package: com.company
 * @className: question2
 * @author: Danny
 * @description: TODO
 * @date: 2023/8/3 21:01
 * @version: 1.0
 */
public class question2 {
    /**
     * 2.某一个大文件被拆成了N个小文件，每个小文件编号从0至N-1，相应大小分别记为S(i)。给定磁盘空间为C，试实现一个函数从N个文件中连续选出若干个
     * 文件拷贝到磁盘中，使得磁盘剩余空间最小。
     *
     * 函数定义如下：
     * int MaximumCopy(const std::vector<size_t>
     * &s, size_t C, size_t &start_index, size_t &end_index);
     *
     * 函数返回值为剩余空间，如无解返回-1。
     * 其中start_index, end_index为文件的编号。
     *
     * 如N=5，S = {1, 2, 3, 5, 4}，C = 7
     * 结果为start_index = 0, end_index = 2, return = 1
     */
    /**
     *
     * 1 5 7 3 2 5 2 1    C = 8  [5, 8)
     *     i
     *     j
     *
     * start
     * end
     * res = 1
     * cur = 6
     * min = MaxValue
     *
     * @return
     */

    public static int[] solve(int[] nums, int C, int N) {
        int start = 0, end = 0, cur = 0, max = Integer.MIN_VALUE;
        int i = 0, j = 0;
        while (j < N) {
            cur += nums[j++];
            while (cur > C && i < j) {
                cur -= nums[i ++];
            }
            if (cur > max) {
                start = i;
                end = j;
                max = cur;
            }
        }
        return new int[]{start, end, C - max};
    }

    public static void main(String[] args) {
        int[] test = new int[]{1, 5, 7, 3, 2, 5, 2, 1};
        int res[] = solve(test, 8, 8);
        System.out.println(Arrays.toString(res));
    }


}
