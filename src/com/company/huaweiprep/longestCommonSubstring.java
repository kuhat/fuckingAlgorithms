package com.company.huaweiprep;

import java.util.Scanner;

/**
 * @projectName: algorithms
 * @package: com.company.huaweiprep
 * @className: longestCommonSubstring
 * @author: Danny
 * @description: TODO
 * @date: 2023/7/5 16:04
 * @version: 1.0
 */
public class longestCommonSubstring {
    /**
     *  查找两个字符串a,b中的最长公共子串。若有多个，输出在较短串中最先出现的那个。
     * 注：子串的定义：将一个字符串删去前缀和后缀（也可以不删）形成的字符串。请和“子序列”的概念分开！
     *
     * 数据范围：字符串长度1≤length≤300 1≤length≤300
     * 进阶：时间复杂度：O(n3) O(n3) ，空间复杂度：O(n) O(n)
     * 输入描述：
     *
     * 输入两个字符串
     * 输出描述：
     * 返回重复出现的字符
     * 示例1
     * 输入：
     *
     * abcdefghijklmnop
     * abcsafjklmnopqrstuvw
     *
     * 输出：
     *
     * jklmnop
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s1 = sc.nextLine();
        String s2 = sc.nextLine();
        if (s1.length() > s2.length()) {
            String tmp = s1;
            s2 = s1;
            s1 = tmp;
        }
        String res = "";
        int left = 0, right = 1;
        while (right <= s1.length()) {
            String tmp = s1.substring(left, right);
            if (s2.contains(tmp)) {
                res = tmp.length() > res.length() ? tmp : res;
                right ++;
            } else {
                left++;
                right = left + 1;
            }
        }
        System.out.println(res);
    }
}
