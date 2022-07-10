package com.company.Notes;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Stack;

public class Note7_Stack {

    // 739 Daily Temperatures

    /**
     * Given an array of integers temperatures represents the daily temperatures,
     * return an array answer such that answer[i] is the number of days you have
     * to wait after the ith day to get a warmer temperature. If there is no future
     * day for which this is possible, keep answer[i] == 0 instead.
     * <p>
     * Input: temperatures = [73,74,75,71,69,72,76,73]
     * Output: [1,1,4,2,1,1,0,0]
     * <p>
     * Input: temperatures = [30,40,50,60]
     * Output: [1,1,1,0]
     * <p>
     * Input: temperatures = [30,60,90]
     * Output: [1,1,0]
     */
    class Solution579 {

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
                while (!stack.isEmpty() && T[i] > T[stack.peek()]) {  // 如果当前遍历元素比栈顶元素大，说明元素遇到了右边比他大的
                    int idx = stack.pop();  // 元素出栈
                    res[idx] = i - idx;
                }
                stack.push(i);  // 如果比栈顶元素小就压栈
            }
            return res;
        }
    }

    // LeetCode 20: Valid Parentheses

    /**
     * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
     * <p>
     * An input string is valid if:
     * <p>
     * 1.Open brackets must be closed by the same type of brackets.
     * 2.Open brackets must be closed in the correct order.
     * <p>
     * Input: s = "()"
     * Output: true
     * <p>
     * Input: s = "()[]{}"
     * Output: true
     * <p>
     * Input: s = "(]"
     * Output: false
     */

    class Solution20 {
        public boolean isValid(String s) {
            if (s == null || s.length() == 0) return true;
            Stack<Character> stack = new Stack<>();
            for (Character ch : s.toCharArray()) {
                if (ch == '(') stack.push(')');
                else if (ch == '[') stack.push(']');
                else if (ch == '{') stack.push('}');
                else {
                    if (stack.isEmpty() || stack.pop() != ch) return false;
                }
            }
            return stack.isEmpty();
        }
    }

    // LeetCode 71: Simplify Path

    /**
     * Given a string path, which is an absolute path (starting with a slash '/') to a file or directory in a
     * Unix-style file system, convert it to the simplified canonical path.
     * <p>
     * In a Unix-style file system, a period '.' refers to the current directory, a double period '..' refers to
     * the directory up a level, and any multiple consecutive slashes (i.e. '//') are treated as a single slash '/'.
     * For this problem, any other format of periods such as '...' are treated as file/directory names.
     * <p>
     * The canonical path should have the following format:
     * <p>
     * The path starts with a single slash '/'.
     * Any two directories are separated by a single slash '/'.
     * The path does not end with a trailing '/'.
     * The path only contains the directories on the path from the root directory to the target file
     * or directory (i.e., no period '.' or double period '..')
     * Return the simplified canonical path.
     */

    class Solution71 {
        public String simplifyPath(String path) {
            Stack<String> stack = new Stack<>();
            String[] paths = path.split("/+");
            for (String s : paths) {
                if (s.equals("..")) {
                    if (!stack.isEmpty()) stack.pop();
                } else if (!s.equals(".") && !s.equals("")) {
                    stack.push(s);
                }
            }
            String res = "";
            while (!stack.isEmpty()) res = "/" + stack.pop() + res;
            if (res.length() == 0) return "/";
            return res;
        }
    }

    // LeetCode 394: Decode String

    /**
     * Given an encoded string, return its decoded string.
     * <p>
     * The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated
     * exactly k times. Note that k is guaranteed to be a positive integer.
     * <p>
     * You may assume that the input string is always valid; there are no extra white spaces, square brackets are
     * well-formed, etc. Furthermore, you may assume that the original data does not contain any digits and that
     * digits are only for those repeat numbers, k. For example, there will not be input like 3a or 2[4].
     * <p>
     * The test cases are generated so that the length of the output will never exceed 105.
     * <p>
     * Input: s = "3[a]2[bc]"
     * Output: "aaabcbc"
     * <p>
     * Input: s = "3[a2[c]]"
     * Output: "accaccacc"
     * <p>
     * Input: s = "2[abc]3[cd]ef"
     * Output: "abcabccdcdcdef"
     */
    class Solution394 {
        public String decodeString(String s) {
            if (s == null || s.length() == 0) {
                return s;
            }
            Stack<Integer> numStack = new Stack<>();  // 一个存数字一个存String
            Stack<String> resStack = new Stack<>();
            String res = "";
            int idx = 0;
            while (idx < s.length()) {
                if (Character.isDigit(s.charAt(idx))) {
                    int num = 0;
                    while (Character.isDigit(s.charAt(idx))) {  // 只要是数字，那必须往下走
                        num = num * 10 + s.charAt(idx) - '0';
                        idx++;
                    }
                    numStack.push(num);
                } else if (s.charAt(idx) == '[') {
                    resStack.push(res);  // 将当前的res String 存入 String stack
                    res = "";  // 重置res
                    idx++;
                } else if (s.charAt(idx) == ']') {
                    StringBuilder temp = new StringBuilder(resStack.pop());  // String stack 弹出
                    int time = numStack.pop();  // 最近的数字弹出
                    for (int i = 0; i < time; i++) {
                        temp.append(res);  //
                    }
                    res = temp.toString();  // 对接已经重复的新字母
                    idx++;
                } else {  // 遇到的是字母就
                    res += s.charAt(idx++);
                }

            }
            return res;
        }
    }

    // LeetCodde 224: Basic calculator

    /**
     * Given a string s representing a valid expression, implement a basic calculator to evaluate it, and return the result of the evaluation.
     * <p>
     * Note: You are not allowed to use any built-in function which evaluates strings as mathematical expressions, such as eval().
     * <p>
     * Input: s = "1 + 1"
     * Output: 2
     * <p>
     * Input: s = "(1+(4+5+2)-3)+(6+8)"
     * Output: 23
     */
    class Solution224 {
        public int calculator(String s) {
            Stack<Integer> stack = new Stack<>();
            int sign = 1;
            int res = 0;
            for (int i = 0; i < s.length(); i++) {
                if (Character.isDigit(s.charAt(i))) {
                    int num = s.charAt(i) - '0';
                    while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                        num = num * 10 + s.charAt(i) - '0';
                        i++;
                    }
                    res += num * sign;
                } else if (s.charAt(i) == '+') {
                    sign = 1;
                } else if (s.charAt(i) == '-') {
                    sign = -1;
                } else if (s.charAt(i) == '(') {  // 遇到（，push所有算过的值放入stack，符号位也放进去
                    stack.push(res);
                    stack.push(sign);
                    res = 0;
                    sign = 1;
                } else if (s.charAt(i) == ')') {
                    res = res * stack.pop() + stack.pop();
                }
            }
            return res;
        }
    }

    // LeetCode 150: Evaluate Reverse Polish Notation

    /**
     * valuate the value of an arithmetic expression in Reverse Polish Notation.
     * <p>
     * Valid operators are +, -, *, and /. Each operand may be an integer or another expression.
     * <p>
     * Note that division between two integers should truncate toward zero.
     * <p>
     * It is guaranteed that the given RPN expression is always valid. That means the expression would always
     * evaluate to a result, and there will not be any division by zero operation.
     * <p>
     * Input: tokens = ["2","1","+","3","*"]
     * Output: 9
     * Explanation: ((2 + 1) * 3) = 9
     * <p>
     * Input: tokens = ["4","13","5","/","+"]
     * Output: 6
     * Explanation: (4 + (13 / 5)) = 6
     */
    class Solution150 {
        public int evalRPN(String[] tokens) {
            Stack<Integer> stack = new Stack<>();
            for (String s : tokens) {
                if (s.equals("+")) {
                    stack.push(stack.pop() + stack.pop());
                } else if (s.equals("-")) {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a - b);
                } else if (s.equals("*")) {
                    stack.push(stack.pop() * stack.pop());
                } else if (s.equals("/")) {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a / b);
                } else {  // 当前的是数字
                    stack.push(Integer.parseInt(s));
                }
            }
            return stack.pop();
        }
    }

    // LeetCode 42: trapping rain water

    /*
     * Given n non-negative integers representing an elevation map where the width of each bar is 1,
     * compute how much water it can trap after raining.

                     *
           *       * *   *
       *   * *   * * * * * *
     0 1 2 3 4 5 6 7 8 9 0 1

     */
    class Solution42 {
        public int trap(int[] height) {
            if (height == null || height.length == 0) {
                return 0;
            }
            Stack<Integer> stack = new Stack<>();
            int res = 0;
            int idx = 0;
            while (idx < height.length) {
                while (!stack.isEmpty() && height[idx] > height[stack.peek()]) {  // 新的index对应的高度比上一个对应的高度高时
                    // 左中右三个index
                    int heightIndex = stack.pop();  // 中index,剩下的为左
                    if (stack.isEmpty()) break;
                    int dis = idx - stack.peek() - 1; // 求出长度
                    int high = Math.min(height[idx], height[stack.peek()]) - height[heightIndex];  // 求出左边和右边较低的一个再减去原来有的高度
                    res += dis * high;
                }
                stack.push(idx++);
            }
            return res;
        }

        //确定left 和 right的值之后，在left和 right 之间相当于构成了一个桶，桶的高度是最矮的那根柱子。然后我们从两边往中间逐个查找，
        // 如果查找的柱子高 度小于桶的高度，那么盛水量就是桶的高度减去我们查找的柱子高度，如果查找的柱子 大于桶的高度，我们要更新桶的高度。
        public int trapTwoPointer(int[] height) {
            int left = 0, right = height.length - 1, water = 0, bucketHeight = 0;
            while (left < right) {
                //取height[left]和height[right]的最小值
                int minHeight = Math.min(height[left], height[right]);
                //如果最小值minHeight大于桶的高度bucketHeight，要更新桶的高度到minHeight
                bucketHeight = bucketHeight < minHeight ? minHeight : bucketHeight;
                water += height[left] >= height[right] ? (bucketHeight - height[right--]) : (bucketHeight - height[left++]);
            }
            return water;
        }

        // LeetCode 84: Largest Rectangle in Histogram

        /**
         * Given an array of integers heights representing the histogram's bar height where the width of each bar is 1, return the area of the largest rectangle in the histogram.
         */
        class Solution84 {
            public int largestRectangle(int[] heights) {
                if (heights == null || heights.length == 0) return 0;
                Stack<Integer> stack = new Stack<>();
                int res = 0;
                for (int i = 0; i <= heights.length; i++) {
                    int hi = i == heights.length ? 0 : heights[i];
                    while (!stack.isEmpty() && hi < heights[stack.peek()]) {
                        int height = heights[stack.pop()];
                        int start = stack.isEmpty() ? -1 : stack.peek();
                        int area = height * (i - start - 1);
                        res = Math.max(res, area);
                    }
                    stack.push(i);
                }
                return res;
            }
        }

        // LeetCode 227 Basic Calculator II

        /**
         * Given a string s which represents an expression, evaluate this expression and return its value.
         * <p>
         * The integer division should truncate toward zero.
         * <p>
         * You may assume that the given expression is always valid. All intermediate results will be in the range of [-231, 231 - 1].
         * <p>
         * Note: You are not allowed to use any built-in function which evaluates strings as mathematical expressions, such as eval().
         */
        /*
        Input: s = "3+2*2"
        Output: 7
         */
        class solution227 {
            public int calculate(String s) {
                // 记录每个数字前面的符号，如果是乘法和除法就直接和前面的数字运算
                // 然后再存放再栈中，如果是加法和剑法就直接存放到栈中
                int preSign = '+';
                Stack<Integer> stack = new Stack<>();
                int length = s.length();
                for (int i = 0; i < length; i++) {
                    int ch = s.charAt(i);
                    if (ch == ' ') continue;  // 过滤掉空格
                    // 如果是数字
                    if (ch >= '0' && ch <= '9') {
                        // 找到连续的数字字符串，把它转化成整数
                        int num = 0;
                        while (i < length && (ch = s.charAt(i)) >= '0' && ch <= '9') {
                            num = num * 10 + ch - '0';
                            i++;
                        }
                        // 抵消上面for循环中的 i++
                        i--;
                        // 乘法和除法，运算之后再存放到栈中，加法和剑法直接存放在栈中
                        if (preSign == '*') {
                            stack.push(num * stack.pop());
                        } else if (preSign == '/') {
                            stack.push(stack.pop() / num);
                        } else if (preSign == '+') {
                            stack.push(num);
                        } else if (preSign == '-') {
                            stack.push(-num);
                        }
                    } else {  // 记录前一个符号
                        preSign = ch;
                    }
                }
                // 把栈中所有的元素都取出来，求他们的和
                int res = 0;
                while (!stack.isEmpty()) res += stack.pop();
                return res;
            }
        }
    }


    // LeetCode 1047: Remove All Adjacent Duplicates in String

    /**
     * You are given a string s consisting of lowercase English letters. A duplicate removal consists of choosing two
     * adjacent and equal letters and removing them.
     *
     * We repeatedly make duplicate removals on s until we no longer can.
     *
     * Return the final string after all such duplicate removals have been made. It can be proven that the answer is unique.
     *
     *Input: s = "abbaca"
     * Output: "ca"
     * Explanation:
     * For example, in "abbaca" we could remove "bb" since the letters are adjacent and equal, and this is the only possible move.
     * The result of this move is that the string is "aaca", of which only "aa" is possible, so the final string is "ca".
     *
     * Input: s = "azxxzy"
     * Output: "ay"
     */
    class Solution1047{


        /*
        我们遍历字符串中的所有字母：

        1，如果栈为空就把当前字母加入到栈中。
        2，如果栈不为空
            如果当前字母等于栈顶元素，说明他俩是相邻且相同的，让他俩同时消失。
            如果当前字母不等于栈顶元素，说明他俩是相邻但不相同，直接让当前字母入栈。
         */
        public String removeDuplicate(String s){
            char[] chars = s.toCharArray();
            Stack<Character> stack = new Stack<>();
            for (int i = 0; i < chars.length; i++) {
                if (stack.isEmpty()) {
                    stack.push(chars[i]);
                } else {
                    if (stack.peek().equals(chars[i])) {
                        stack.pop();  //  如果当前的值和栈顶的值相等，直接让他俩消失
                    } else {
                        stack.push(chars[i]);  // 如果不相等，入栈
                    }
                }
            }
            // 将栈中的元素转化为字符串
            StringBuilder stringBuilder = new StringBuilder(stack.size());
            for (Character character : stack) {
                stringBuilder.append(character);
            }
            return stringBuilder.reverse().toString();
        }

        // LeetCode 503: Next Greater Element
        /**
         * Given a circular integer array nums (i.e., the next element of nums[nums.length - 1] is nums[0]),
         * return the next greater number for every element in nums.
         *
         * The next greater number of a number x is the first greater number to its traversing-order next in the array,
         * which means you could search circularly to find its next greater number. If it doesn't exist, return -1 for this number.
         */
        /*
        Input: nums = [1,2,1]
        Output: [2,-1,2]
        Explanation: The first 1's next greater number is 2;
        The number 2 can't find next greater number.
        The second 1's next greater number needs to search circularly, which is also 2.
         */

        class Solution503{
            public int[] nextGreaterElement(int[] nums) {
                int len = nums.length;
                int[] res = new int[len];
                Arrays.fill(res, -1);  // 默认为1
                Stack<Integer> stack = new Stack<>();
                // 把数组循环两遍
                for (int i = 0; i < len * 2; i++) {
                    // 遍历数组的第index (index从0开始) 个元素，因为数组会遍历两遍， 会导致越界异常
                    // 所以治理要对数组长度进行求余
                    int index = i % len;
                    // 单调栈，其存储的是元素的下标，不是元素具体的值，从栈顶到栈底所对应的值是递增的（栈顶元素
                    // 在数组中对应的值最小，栈底元素对应的元素的值最大），如果栈顶元素对应的值比nums[index]小
                    // 说明栈顶元素的值遇到了右边第一个比他大的值，然后栈顶元素出栈，
                    // 让他对应的位置变为nums[index], 也就是他右边第一个比他大的值，然后继续判断
                    while (!stack.isEmpty() && nums[stack.peek()] < nums[index]) {
                        res[stack.pop()] = nums[index];
                    }
                    stack.push(index);
                }
            return res;
            }

        }

    }

    public static void main(String[] args) {
        System.out.println(String.valueOf("3"));
    }

}
