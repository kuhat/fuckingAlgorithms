package com.company.Notes;

import java.util.Arrays;
import java.util.Stack;

public class Note9_Stack {

    // 739 Daily Temperatures
    /**
     * Given an array of integers temperatures represents the daily temperatures,
     * return an array answer such that answer[i] is the number of days you have
     * to wait after the ith day to get a warmer temperature. If there is no future
     * day for which this is possible, keep answer[i] == 0 instead.
     *
     * Input: temperatures = [73,74,75,71,69,72,76,73]
     * Output: [1,1,4,2,1,1,0,0]
     *
     * Input: temperatures = [30,40,50,60]
     * Output: [1,1,1,0]
     *
     * Input: temperatures = [30,60,90]
     * Output: [1,1,0]
     */
    class Solution579{

        // 看到这道题我们首先想到的是暴力求解。他的原理是遍历每一个元素，然后再从当前元素往后找比它大的，
        // 找到之后记录下他俩位置的差值，然后停止内层循环，如果没找到默认为0。
        public int[] dailyTemperatures(int[] temperatures) {
            int length = temperatures.length;
            int[] res = new int[length];
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < i + 1; j++) {
                    if (temperatures[j] > temperatures[i]) {
                        res[i] = j - i;
                        break;
                    }
                }
            }
            return res;
        }

        //暴力求解，效率并不高，我们还可以使用栈来解决，栈存储的是元素的下标，不是元素的值。他的原理就是我们遍历到每个元素的时候用它和栈顶
        // （栈不为空，如果为空直接入栈）元素比较，如果比栈顶元素小就把它对应的下标压栈，如果比栈顶元素大，说明栈顶元素遇到了右边比它大的，
        // 然后栈顶元素出栈，在计算下标的差值……重复这样计算。
        public int[] dailyTemperaturesStack(int[] T) {
            Stack<Integer> stack = new Stack<>();  // 用栈来存储元素的下标
            int[] res = new int[T.length];
            for (int i = 0; i < T.length; i++) {
                while(!stack.isEmpty() && T[i] > T[stack.peek()]) {  // 如果当前遍历元素比栈顶元素大，说明元素遇到了右边比他大的
                    int idx = stack.pop();  // 元素出栈
                    res[idx] = i - idx;
                }
                stack.push(i);  // 如果比栈顶元素小就压栈
            }
            return res;
        }


    }


}
