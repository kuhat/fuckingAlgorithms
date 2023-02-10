package com.company.huaweiprep;

import java.util.Scanner;

/**
 * @projectName: algorithms
 * @package: com.company.huaweiprep
 * @className: 购物单
 * @author: Danny
 * @description: TODO
 * @date: 2023/2/10 17:09
 * @version: 1.0
 */
public class 购物单 {
    // 改变背包问题
    /**
     * 问题描述
     * 　　金明今天很开心，家里购置的新房就要领钥匙了，新房里有一间金明自己专用的很宽敞的房间。更让他高兴的是，妈妈昨天对他说：“你的房间需要购买哪些物品，
     * 怎么布置，你说了算，只要不超过N元钱就行”。今天一早，金明就开始做预算了，他把想买的物品分为两类：主件与附件，附件是从属于某个主件的，下表就是一些主件与附件的例子：
     * 主件 	附件
     * 电脑 	打印机，扫描仪
     * 书柜 	图书
     * 书桌 	台灯，文具
     * 工作椅 	无
     * <p>
     * 如果要买归类为附件的物品，必须先买该附件所属的主件。每个主件可以有0个、1个或2个附件。附件不再有从属于自己的附件。金明想买的东西很多，
     * 肯定会超过妈妈限定的N元。于是，他把每件物品规定了一个重要度，分为5等：用整数1~5表示，第5等最重要。他还从因特网上查到了每件物品的价格
     * （都是10元的整数倍）。他希望在不超过N元（可以等于N元）的前提下，使每件物品的价格与重要度的乘积的总和最大。
     * 　　设第j件物品的价格为v[j]，重要度为w[j]，共选中了k件物品，编号依次为j_1，j_2，……，j_k，则所求的总和为：
     * 　　v[j_1]*w[j_1]+v[j_2]*w[j_2]+ …+v[j_k]*w[j_k]。（其中*为乘号）
     * 　　请你帮助金明设计一个满足要求的购物单。
     * <p>
     * 输入格式
     * 　　输入文件budget.in 的第1行，为两个正整数，用一个空格隔开：
     * 　　N m
     * 　　（其中N（<32000）表示总钱数，m（<60）为希望购买物品的个数。）
     * 　　从第2行到第m+1行，第j行给出了编号为j-1的物品的基本数据，每行有3个非负整数
     * 　　v p q
     * 　　（其中v表示该物品的价格（v<10000），p表示该物品的重要度（1~5），q表示该物品是主件还是附件。如果q=0，表示该物品为主件，如果q>0，表示该物品为附件，q是所属主件的编号）
     * 输出格式
     * 　　输出文件budget.out只有一个正整数，为不超过总钱数的物品的价格与重要度乘积的总和的最大值（<200000）。
     * 样例输入
     * 1000 5
     * 800 2 0
     * 400 5 1
     * 300 5 1
     * 400 3 0
     * 500 2 0
     * 样例输出
     * 2200
     */
    /*
    具体思路就是构造物品类，然后对主件判断是否有附件，若有附件则依次添加，根据主件、附件1、附件2的组合有四种情况

    只有主件
    主件+附件1
    主件+附件2
    主件+附件1+附件2
    根据以上情况转化问题为经典的 01背包问题 ，接着就是套公式动态规划即可
    题很明显是一道01背包问题，对于这种问题，我们一般采用动态规划的方法来进行解决。我们定义动规数组f[i][j]来表示前i件物品，容量为j时的最大价值，则

        f[i][j]={max(f[i−1][j],f[i−1][j−w[i]]+v[i]) j≥w[i]
                f[i−1][j]                           else

    在本题中进行了一项变动，即物品分为主件和附件，考虑到一个主件最多可以购买两个附件，那我们可以细化分析，将是否购买该物品，细化为是否购买该物品，
    以及是否购买该物品的附件，即5种情况，不购买该物品，购买该物品，购买该物品及附件1，购买该物品及附件2，购买该物品及附件1及附件2，f[i][j]取这五种情况的最大值，这五种情况分别对应于

        f[i−1][j]，

        f[i−1][j−w[i]]+v[i]，

        f[i][j]=max(f[i−1][j],f[i−1][j−w[i]−w[a1]]+v[i]+v[a1])，

        f[i][j]=max(f[i−1][j],f[i−1][j−w[i]−w[a2]]+v[i]+v[a2])，

        f[i][j]=max(f[i−1][j],f[i−1][j−w[i]−w[a1]−w[a2]]+v[i]+v[a1]+v[a2])，

     其中a1和a2是该物品的附件。
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int m = sc.nextInt();

        Goods[] goods = new Goods[m];
        for (int i = 0; i < m; i++) {
            goods[i] = new Goods();
        }
        for (int i = 0; i < m; i++) {
            int v = sc.nextInt();
            int p = sc.nextInt();
            int q = sc.nextInt();
            goods[i].v = v;
            goods[i].p = p * v;  // 直接用p*v，方便后面计算
            if (q == 0) {
                goods[i].main = true;
            } else if (goods[q - 1].a1 == -1) {
                goods[q - 1].a1 = i;
            } else {
                goods[q - 1].a2 = i;
            }
        }

        int[][] dp = new int[m + 1][N + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 0; j <= N; j++) {
                dp[i][j] = dp[i - 1][j];
                if (!goods[i - 1].main) {
                    continue;
                }
                if (j >= goods[i - 1].v) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - goods[i - 1].v] + goods[i - 1].p);
                }
                if (goods[i - 1].a1 != -1 && j >= goods[i - 1].v + goods[goods[i - 1].a1].v) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - goods[i - 1].v - goods[goods[i - 1].a1].v] + goods[i - 1].p + goods[goods[i - 1].a1].p);
                }
                if (goods[i - 1].a2 != -1 && j >= goods[i - 1].v + goods[goods[i - 1].a2].v) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - goods[i - 1].v - goods[goods[i - 1].a2].v] + goods[i - 1].p + goods[goods[i - 1].a2].p);
                }
                if (goods[i - 1].a1 != -1 && goods[i - 1].a2 != -1 && j >= goods[i - 1].v + goods[goods[i - 1].a1].v + goods[goods[i - 1].a2].v) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - goods[i - 1].v - goods[goods[i - 1].a1].v - goods[goods[i - 1].a2].v] + goods[i - 1].p + goods[goods[i - 1].a1].p + goods[goods[i - 1].a2].p);
                }
            }
        }
        System.out.println(dp[m][N]);
    }


    static class Goods {
        int v;
        int p;
        boolean main = false;

        int a1 = -1;  //定义附件1的编号
        int a2 = -1;  //定义附件2的编号
    }

}
