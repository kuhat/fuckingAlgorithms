package com.company.huaweiprep;

/**
 * @projectName: algorithms
 * @package: com.company.huaweiprep
 * @className: 火车进站
 * @author: Danny
 * @description: TODO
 * @date: 2023/7/29 0:33
 * @version: 1.0
 */
import java.util.*;


public class 火车进站 {
    /**
     *  给定一个正整数N代表火车数量，0<N<10，接下来输入火车入站的序列，一共N辆火车，每辆火车以数字1-9编号，火车站只有一个方向进出，同时停靠在火车站的列车中，只有后进站的出站了，先进站的才能出站。
     * 要求输出所有火车出站的方案，以字典序排序输出。
     * 数据范围：1≤n≤10 1≤n≤10
     * 进阶：时间复杂度：O(n!) O(n!) ，空间复杂度：O(n) O(n)
     * 输入描述：
     *
     * 第一行输入一个正整数N（0 < N <= 10），第二行包括N个正整数，范围为1到10。
     * 输出描述：
     *
     * 输出以字典序从小到大排序的火车出站序列号，每个编号以空格隔开，每个输出序列换行，具体见sample。
     * 示例1
     * 输入：
     *
     * 3
     * 1 2 3
     *
     * 输出：
     *
     * 1 2 3
     * 1 3 2
     * 2 1 3
     * 2 3 1
     * 3 2 1
     *
     * 说明：
     *
     * 第一种方案：1进、1出、2进、2出、3进、3出
     * 第二种方案：1进、1出、2进、3进、3出、2出
     * 第三种方案：1进、2进、2出、1出、3进、3出
     * 第四种方案：1进、2进、2出、3进、3出、1出
     * 第五种方案：1进、2进、3进、3出、2出、1出
     * 请注意，[3,1,2]这个序列是不可能实现的。
     */
    static Stack<Integer> stack;
    static List<String> res;
    static int[] nums;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = in.nextInt();
        stack = new Stack<>();
        res = new ArrayList<>();
        dfs(0, 0, n, "");
        Collections.sort(res);  // 对结果排序
        for (String str: res) System.out.println(str);
    }
    // i 为入栈次数， cnt 为出栈次数 Str 记录出栈顺序
    public static void dfs(int i, int cnt, int n, String str) {
        if (i == n) res.add(str);
        if (!stack.isEmpty()) {
            int tmp = stack.pop();
            dfs(i + 1, cnt, n, str + tmp + " ");
            stack.push(tmp);  // 恢复现场
        }
        if (cnt < n) {
            stack.push(nums[cnt]);
            dfs(i, cnt + 1, n, str);
            stack.pop();
        }
    }

}
