package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @projectName: algorithms
 * @package: com.company
 * @className: question3
 * @author: Danny
 * @description: TODO
 * @date: 2023/8/3 21:01
 * @version: 1.0
 */
public class question3 {

    /**
     * 1.输入入2个字符串，找出他们最长的公共子串，长度相同的输出任意一个。
     * 例如：str1="abcdefgh"，str2="ghdefgcc",，输出result="defg"
     */
    /**
     *    dp[i][j]
     *    str1  a b c d e f g h
     *        g 0 0 0 0 0 0 1 1
     *        h 0 0 0 0 0 0 1 2
     *        d 0 0 0 1 1 1 1 2
     *        e 0 0 0 1 2 2 2 2
     *        f 0 0 0 1 2 3 3 3
     *        g 0 0 0 1 2 3 4 4
     *        c
     *        c
     *
     *        if (str1.charAt(i) != str2.charAt(2)) {
     *            dp[i][j] = dp[i - 1][j]
     *        } else {
     *            dp[i][j] = dp[i - 1][j - 1] + 1
     *        }
     */
    public static String solve(String str1, String str2) {
        if (str1.length() == 0 || str2.length() == 0) return "";
        String res = "";
        int max = 0;
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];
        for (int i = 1; i <= str1.length(); i ++) {
            for (int j = 1; j <= str2.length(); j ++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    if (dp[i][j] > max) {
                        max = dp[i][j];
                        res = str1.substring(i - max, i);
                    }
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        System.out.println("max length: " + max);
        return res;
    }

    public static List<String> getAllSubstrings(String input) {
        List<String> substrings = new ArrayList<>();

        int n = input.length();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j <= n; j++) {
                substrings.add(input.substring(i, j));
            }
        }

        return substrings;
    }

    public static int getMappedValue(char c) {
        if (c >= 'a' && c <= 'b') return 1;
        if (c >= 'c' && c <= 'e') return 2;
        if (c >= 'f' && c <= 'h') return 3;
        if (c >= 'i' && c <= 'k') return 4;
        if (c >= 'l' && c <= 'n') return 5;
        if (c >= 'o' && c <= 'q') return 6;
        if (c >= 'r' && c <= 't') return 7;
        if (c >= 'u' && c <= 'v') return 8;
        if (c >= 'x' && c <= 'z') return 9;
        return 0;
    }

    public static int countSubstring(String s) {
        int n = s.length();
        int[] prefixSum = new int[n + 1];
        int ans = 0;

        for (int i = 1; i <= n; i++) {
            prefixSum[i] = prefixSum[i - 1] + getMappedValue(s.charAt(i - 1));
        }

        for (int i = 1; i <= n; i++) {
            for (int len = 1; len <= i; len++) {
                int subSum = prefixSum[i] - prefixSum[i - len];
                if (subSum % len == 0) {
                    ans++;
                }
            }
        }

        return ans;
    }

    public static void main(String[] args) {
//        String str1 = "abcdefgh";
//        String str2 = "ghdefgcc";
//        String res = solve(str1, str2);
//        System.out.println(res);
//
//        String input = "Hello";
//        List<String> allSubstrings = getAllSubstrings(input);
//
//        for (String substring : allSubstrings) {
//            System.out.println(substring);
//        }

        String inpu = "abcd";
        int totalSubstrings = countSubstring(inpu);
        System.out.println("Total substrings with divisible sum: " + totalSubstrings);
    }




}
