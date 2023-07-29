package com.company;

import java.io.*;
import java.util.*;
import java.util.*;
import java.io.*;

public class Main {

    public static void sol(int n, int[] input) {
        int res = Integer.MIN_VALUE;
        int curSum = 0;
        int maxSum = input[0];
        res = getRes(input, res, curSum, maxSum);
        int[] tm = Arrays.stream(input).toArray();
        for (int i = 0; i < input.length; i++) {
            int tmp = tm[i];
            tm[i] = n;
            curSum = 0;
            maxSum = input[0];
            res = getRes(tm, res, curSum, maxSum);
            tm[i] = tmp;
        }
        System.out.println(res);
    }

    private static int getRes(int[] input, int res, int curSum, int maxSum) {
        for (int i : input) {
            curSum = Math.max(i, curSum + i);
            maxSum = Math.max(maxSum, curSum);
        }
        res = Math.max(res, maxSum);
//        System.out.println(res);
        return res;
    }

    public static void main(String[] args) {
//        int[] input = new int[]{5, -1, -5, -3, 2};
//        getRes(input, 0, 0, input[0]);
//        sol(10, input);
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int i = 0; i < t; i++) {
            while (sc.hasNext()) {
                String[] input1 = sc.nextLine().split(" ");
                int[] tmp = new int[2];
                tmp[0] = Integer.valueOf(input1[0]);
                tmp[1] = Integer.valueOf(input1[1]);
                String[] input2 = sc.nextLine().split(" ");
                int[] tmp1 = new int[input2.length];
                for (int k = 0; k < tmp[0]; i++) {
                    tmp1[k] = Integer.valueOf(input2[k]);
                }
                sol(tmp[1], tmp1);
            }
        }
    }
}
