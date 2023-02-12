package com.company.huaweiprep;

import java.util.Scanner;

/**
 * @projectName: algorithms
 * @package: com.company.huaweiprep
 * @className: 合唱队
 * @author: Danny
 * @description: TODO
 * @date: 2023/2/11 23:08
 * @version: 1.0
 */
public class 合唱队 {
    /**
     *  N 位同学站成一排，音乐老师要请最少的同学出列，使得剩下的 K 位同学排成合唱队形。
     * 设KK位同学从左到右依次编号为 1，2…，K ，他们的身高分别为T1,T2,…,TKT1,T2,…,TK ，若存在i(1≤i≤K)i(1≤i≤K)
     * 使得T1<T2<......<Ti−1<TiT1<T2<......<Ti−1<Ti且 Ti>Ti+1>......>TKTi>Ti+1>......>TK，则称这KK名同学排成了合唱队形。
     * 通俗来说，能找到一个同学，他的两边的同学身高都依次严格降低的队形就是合唱队形。
     * 例子：
     * 123 124 125 123 121 是一个合唱队形
     * 123 123 124 122不是合唱队形，因为前两名同学身高相等，不符合要求
     * 123 122 121 122不是合唱队形，因为找不到一个同学，他的两侧同学身高递减。
     *
     *
     * 你的任务是，已知所有N位同学的身高，计算最少需要几位同学出列，可以使得剩下的同学排成合唱队形。
     *
     * 注意：不允许改变队列元素的先后顺序 且 不要求最高同学左右人数必须相等
     *
     * 数据范围： 1≤n≤3000 1≤n≤3000
     *
     * 输入描述：
     *
     * 用例两行数据，第一行是同学的总数 N ，第二行是 N 位同学的身高，以空格隔开
     * 输出描述：
     *
     * 最少需要几位同学出列
     * 示例1
     * 输入：
     *
     * 8
     * 186 186 150 200 160 130 197 200
     *
     * 输出：
     * 4
     * 说明：
     * 由于不允许改变队列元素的先后顺序，所以最终剩下的队列应该为186 200 160 130或150 200 160 130
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
            }

            int[] left = new int[n]; //存储每个数左边小于其的数的个数
            int[] right = new int[n];//存储每个数右边小于其的数的个数
            left[0] = 1;            //最左边的数设为1
            right[n - 1] = 1;        //最右边的数设为1
            //计算每个位置左侧的最长递增
            for (int i = 0; i < n; i++) {
                left[i] = 1;
                for (int j = 0; j < i; j++) {
                    if (arr[i] > arr[j]) {   //动态规划
                        left[i] = Math.max(left[j] + 1, left[i]);
                    }
                }
            }
            //计算每个位置右侧的最长递减
            for (int i = n - 1; i >= 0; i--) {
                right[i] = 1;
                for (int j = n - 1; j > i; j--) {
                    if (arr[i] > arr[j]) {   //动态规划
                        right[i] = Math.max(right[i], right[j] + 1);
                    }
                }
            }
            // 记录每个位置的值
            int max = 1;
            int[] result = new int[n];
            for (int i = 0; i < n; i++) {
                //位置 i计算了两次 所以需要－1
                result[i] = left[i] + right[i] - 1; //两个都包含本身
                max = Math.max(max, result[i]);
            }
            System.out.println(n - max);
        }

    }
}
