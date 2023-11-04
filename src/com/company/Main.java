package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Question Resources
 * Question Description
 * Let’s define a cool string: the ASCII code differences between any character in the current string are always less than equal to the given number k.
 *
 * For example, "bcad" will be considered a cool string if k=3 since the maximum difference between a and d is 3.
 *
 * Now, your task is to split the given string into a minimal number of cool substrings. You should return a list of
 * cool strings split from the original string in the same order they are in the original string.
 *
 * Example:
 * Input: s = "zzzaazza", k = 20
 * Output: ["zzz","aa","zz","a"]
 *
 * Explanation: Since the ASCII difference between z and a is 25, we cannot form them into a cool string. Although we
 * can also split it into [“zzz, ”a”, “a”, ”zz”, ”a”], we only want the minimal number of substrings.
 *
 * Constraints:
 *
 * The given string contains lowercase english letters only.
 * s.length() > 0 and k >= 0
 *
 * Please use the method signature provided below:
 */
class Main {
    public static List<String> formMinCoolStrings(String s, int k){
        //your code here ...
        List<String> res = new ArrayList<>();
        char[] chars = s.toCharArray();
        int l = 0, r = 0;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        while (r < chars.length) {
            int cur = (int) s.charAt(r);
            max = Math.max(max, cur);
            min = Math.min(min, cur);
            if (max - min > k) {
                res.add(s.substring(l ,r));
                l = r;
                max = cur;
                min = cur;
            }
            r++;
        }
        res.add(s.substring(l));
        return res;
    }

    /**
     * 596. Find Sum of Borders
     * Question Resources
     * Question Description
     * Given a n *n matrix, your task is to find the sum of all the borders that appears in the matrix.
     *
     * Example:
     *
     * Input:
     * [[9,  7,  4,  5],
     *  [1,  6,  2, -6],
     *  [12, 20, 2,  0],
     *  [-5, -6, 7, -2]]
     * Output: [26, 30]
     *
     * Explanation:
     * We have two border here:
     * [9,7,4,5,-6,0,-2,7,-6,-5,12,1] as the first border, the sum of this border is 26;
     * [6,2,2,20] as the second border, the sum of this border is 30
     * Thus, the result is [26, 30].
     */
    public static int[] sumOfBorders(int[][] matrix){
        //your code here...
        List<Integer> res=  new ArrayList<>();
        int n = matrix.length;
        int layers = (n + 1) / 2;
        for (int i = 0; i < layers; i ++) {
            int sum = 0;
            for (int j = i; j < n - i; j ++) {
                sum += matrix[i][j];
                System.out.println(matrix[i][j]);
            }
            for (int j = i + 1; j < n - i; j ++) {
                sum += matrix[j][n - i - 1];
                System.out.println(matrix[j][n - i - 1]);
            }
            if (n - i - 1 > i) {
                for (int j = i; j < n - i - 1; j ++) {
                    sum += matrix[n - i - 1][j];
                    System.out.println(matrix[n - i - 1][j]);
                }
            }
            if (i < n - i - 1) {
                for (int j = i + 1; j < n- i - 1;j ++) {
                    sum += matrix[j][i];
                    System.out.println( matrix[j][i]);
                }
            }
            System.out.println(sum);
            res.add(sum);
        }
        int[] ret = new int[res.size()];
        for (int i = 0; i < ret.length; i ++) {
            ret[i] = res.get(i);
        }
        return ret;
    }
    public static void main(String[] args) {
        List<String> test = formMinCoolStrings("zzzaazza", 3);
        System.out.println(Arrays.toString(new List[]{test}));

        int[][] matrix = new int[][]{{9,  7,  4,  5}, {1,  6,  2, -6}, {12, 20, 2,  0}, {-5, -6, 7, -2}};
        System.out.println(Arrays.toString(sumOfBorders(matrix)));
    }
}