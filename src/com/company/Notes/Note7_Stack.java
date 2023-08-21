package com.company.Notes;

import com.company.leetCode.NestedInteger;

import java.util.*;

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

        public int trap1(int[] height) {
            if (height == null || height.length <= 2) return 0;
            int left = 0, right = height.length - 1;
            //最开始的时候确定left和right的边界，这里的left和right是下标，如果一直是递增的不能装水
            while (left < right && height[left] < height[++left]) left++;
            while (left < right && height[right] < height[--right]) right--;

            int water = 0;
            while (left < right) {
                // 确定左边和右边的木桶高度
                int leftValue = height[left], rightValue = height[right];
                if (leftValue < rightValue) {
                    // //如果左边柱子高度小于等于右边柱子的高度，根据木桶原理，
                    // 桶的高度就是左边柱子的高度
                    while (left < right && height[leftValue] >= height[++left]) {
                        water += leftValue - height[left];
                    }
                } else {
                    //如果左边柱子高度大于右边柱子的高度，根据木桶原理，
                    // 桶的高度就是右边柱子的高度
                    while (right > left && height[rightValue] >= height[--right]) {
                        water += rightValue - height[right];
                    }
                }
            }
            return water;
        }

        // 简化代码
        public int trap2(int[] height) {
            if (height.length <= 2) return 0;
            int leftMax = 0, rightMax = 0, left = 0, right = height.length - 1;
            int water = 0;
            while (left < right) {
                leftMax = Math.max(leftMax, height[left]);
                rightMax = Math.max(rightMax, height[right]);
                if (leftMax < rightMax) {
                    water += leftMax - height[left++];
                } else {
                    water += rightMax - height[right--];
                }
            }
            return water;
        }

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
     * <p>
     * We repeatedly make duplicate removals on s until we no longer can.
     * <p>
     * Return the final string after all such duplicate removals have been made. It can be proven that the answer is unique.
     * <p>
     * Input: s = "abbaca"
     * Output: "ca"
     * Explanation:
     * For example, in "abbaca" we could remove "bb" since the letters are adjacent and equal, and this is the only possible move.
     * The result of this move is that the string is "aaca", of which only "aa" is possible, so the final string is "ca".
     * <p>
     * Input: s = "azxxzy"
     * Output: "ay"
     */
    class Solution1047 {


        /*
        我们遍历字符串中的所有字母：

        1，如果栈为空就把当前字母加入到栈中。
        2，如果栈不为空
            如果当前字母等于栈顶元素，说明他俩是相邻且相同的，让他俩同时消失。
            如果当前字母不等于栈顶元素，说明他俩是相邻但不相同，直接让当前字母入栈。
         */
        public String removeDuplicate(String s) {
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
         * <p>
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

        class Solution503 {
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

    // LeetCode 496: Next Great Element 1

    /**
     * The next greater element of some element x in an array is the first greater element that is to the right of x
     * in the same array.
     * <p>
     * You are given two distinct 0-indexed integer arrays nums1 and nums2, where nums1 is a subset of nums2.
     * <p>
     * For each 0 <= i < nums1.length, find the index j such that nums1[i] == nums2[j] and determine the next greater
     * element of nums2[j] in nums2. If there is no next greater element, then the answer for this query is -1.
     * <p>
     * Return an array ans of length nums1.length such that ans[i] is the next greater element as described above.
     * <p>
     * Input: nums1 = [4,1,2], nums2 = [1,3,4,2]
     * Output: [-1,3,-1]
     * Explanation: The next greater element for each value of nums1 is as follows:
     * - 4 is underlined in nums2 = [1,3,4,2]. There is no next greater element, so the answer is -1.
     * - 1 is underlined in nums2 = [1,3,4,2]. The next greater element is 3.
     * - 2 is underlined in nums2 = [1,3,4,2]. There is no next greater element, so the answer is -1.
     */
// 6
    class Solution496 {
        public int[] nextGreaterElement(int[] nums1, int[] nums2) {
            Stack<Integer> stack = new Stack<>();   // 单调栈，从栈顶到栈底是递增的
            Map<Integer, Integer> map = new HashMap<>();  // map的key是数组中元素的值， value是这个值遇到的右边比它大的数
            for (int num : nums2) {
                // 如果栈顶元素小于num,说明栈顶元素遇到了右边第一个比它大的值，然后栈顶元素出栈，记录这个值
                if (!stack.isEmpty() && stack.peek() < num) map.put(stack.pop(), num);
                stack.push(num);
            }
            int[] res = new int[nums1.length];
            for (int i = 0; i < nums1.length; i++) {  //  遍历nums1中的值，然后在map中查找，如果没有找到，说明没有遇到右边比它大的值，默认给-1
                res[i] = map.getOrDefault(nums1[i], -1);
            }
            return res;
        }
    }

    // LeetCode 946: Validate Stack Sequences

    /**
     * Given two integer arrays pushed and popped each with distinct values, return true if this could have been the
     * result of a sequence of push and pop operations on an initially empty stack, or false otherwise.
     * <p>
     * Input: pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
     * Output: true
     * Explanation: We might do the following sequence:
     * push(1), push(2), push(3), push(4),
     * pop() -> 4,
     * push(5),
     * pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1
     * <p>
     * Input: pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
     * Output: false
     * Explanation: 1 cannot be popped before 2.
     */
    /*
    我们可以把数组pushed中的元素一个个入栈，每入栈一个元素就要拿栈 顶元素和popped中的第一个元素比较，看是否一样，如果一样，就出栈，然后再拿新
    的栈顶元素和popped中第2个元素比较……。如果栈顶元素不等于popped中的第一个 元素，那么数组pushed中的元素就继续入栈，入栈之后再和popped中的
    第一个元素 比较……。 最后再判断栈是否为空，如果为空，说明pushed数组通过一系列的出栈和入栈能得到 popped数组，直接返回true。否则就表示没法
    得到popped数组，直接返回false。
     */
    class Solution946 {
        public boolean validateStackSequences(int[] pushed, int[] popped) {
            Stack<Integer> stack = new Stack<>();
            // 记录poped数组访问到哪一个元素了
            int index = 0;
            for (int num : pushed) {
                stack.push(num);
                while (!stack.isEmpty() && stack.peek() == popped[index++]) stack.pop();
            }
            return stack.isEmpty();
        }
    }

    // LeetCode 155: minStack

    /**
     * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
     * <p>
     * Implement the MinStack class:
     * <p>
     * MinStack() initializes the stack object.
     * void push(int val) pushes the element val onto the stack.
     * void pop() removes the element on the top of the stack.
     * int top() gets the top element of the stack.
     * int getMin() retrieves the minimum element in the stack.
     * You must implement a solution with O(1) time complexity for each function.
     */
    class MinStack {
        private Stack<Integer> stack;
        private Stack<Integer> minStack;  // 两个stack来存储

        public MinStack() {
            stack = new Stack<>();
            minStack = new Stack<>();
        }

        public void push(int val) {
            stack.push(val);
            if (!minStack.isEmpty()) {
                int min = minStack.peek();
                if (val <= min) minStack.push(val);
            } else {
                minStack.push(val);
            }
        }

        public void pop() {
            int x = stack.pop();
            if (!minStack.isEmpty()) {
                if (x == minStack.peek()) minStack.pop();
            }
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }

    class minStack2 {  // 用一个栈解决
        private Stack<Integer> stack;
        private int min;

        public minStack2() {
            stack = new Stack<>();
            min = Integer.MAX_VALUE;
        }

        public void push(int x) {
            if (x <= min) {
                stack.push(min);
                min = x;
            }
            stack.push(x);
        }

        public void pop() {
            if (stack.pop() == min) min = stack.pop();  // 如果stack的顶部元素正好是min值，就pop两次，第二次让min等于pop出的值
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return min;
        }

    }

    /**
     * Your MinStack object will be instantiated and called as such:
     * MinStack obj = new MinStack();
     * obj.push(val);
     * obj.pop();
     * int param_3 = obj.top();
     * int param_4 = obj.getMin();
     */

    // 22 Generate Parentheses

    /**
     * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
     * <p>
     * Example 1:
     * <p>
     * Input: n = 3
     * Output: ["((()))","(()())","(())()","()(())","()()()"]
     * Example 2:
     * <p>
     * Input: n = 1
     * Output: ["()"]
     */
    class Solution {
        public List<String> generateParenthesis(int n) {
            List<String> res = new ArrayList<>();
            if (n == 0) return res;
            helper(res, "", n, n);  // 左括号有n个，右括号有n个
            return res;
        }

        public void helper(List<String> res, String s, int left, int right) {
            // left一定要大于right, 一定要先有左括号再有右括号，左括号剩余的数量要比右括号的多的话就return
            if (left > right) return;
            if (left == 0 && right == 0) {
                res.add(s);
                return;
            }
            if (left > 0) helper(res, s + "(", left - 1, right);
            if (right > 0) helper(res, s + ")", left, right - 1);
        }
    }

    // 232： Implement queue using stack

    /**
     * Implement a first in first out (FIFO) queue using only two stacks. The implemented queue should support all the functions of a normal queue (push, peek, pop, and empty).
     * <p>
     * Implement the MyQueue class:
     * <p>
     * void push(int x) Pushes element x to the back of the queue.
     * int pop() Removes the element from the front of the queue and returns it.
     * int peek() Returns the element at the front of the queue.
     * boolean empty() Returns true if the queue is empty, false otherwise.
     * Notes:
     * <p>
     * You must use only standard operations of a stack, which means only push to top, peek/pop from top, size, and is empty operations are valid.
     * Depending on your language, the stack may not be supported natively. You may simulate a stack using a list or deque (double-ended queue) as long as you use only a stack's standard operations.
     * <p>
     * <p>
     * Example 1:
     * <p>
     * Input
     * ["MyQueue", "push", "push", "peek", "pop", "empty"]
     * [[], [1], [2], [], [], []]
     * Output
     * [null, null, null, 1, 1, false]
     * <p>
     * Explanation
     * MyQueue myQueue = new MyQueue();
     * myQueue.push(1); // queue is: [1]
     * myQueue.push(2); // queue is: [1, 2] (leftmost is front of the queue)
     * myQueue.peek(); // return 1
     * myQueue.pop(); // return 1, queue is [2]
     * myQueue.empty(); // return false
     */
    class MyQueue {

        Stack<Integer> s1;
        Stack<Integer> s2;

        public MyQueue() {
            s1 = new Stack<>();
            s2 = new Stack<>();
        }

        public void push(int x) {
            s1.push(x);
        }

        /*
        s1: 1 2 3  代表没进行处理的stack, queue的反方向
        s2: 3 2 1 颠倒顺序，和queue的方向一样
         */

        public int pop() {
            if (!s2.isEmpty()) return s2.pop();
            else while (!s1.isEmpty()) s2.push(s1.pop());  // 颠倒s1的顺序再pop
            return s2.pop();
        }

        public int peek() {
            if (!s2.isEmpty()) return s2.peek();
            else {
                while (!s1.isEmpty()) s2.push(s1.pop());
                return s2.peek();
            }
        }

        public boolean empty() {
            return s1.isEmpty() && s2.isEmpty();
        }
    }

    /**
     * Your MyQueue object will be instantiated and called as such:
     * MyQueue obj = new MyQueue();
     * obj.push(x);
     * int param_2 = obj.pop();
     * int param_3 = obj.peek();
     * boolean param_4 = obj.empty();
     */

    // 225: Implement stack using queue

    /**
     * Implement a last-in-first-out (LIFO) stack using only two queues. The implemented stack should support all the functions of a normal stack (push, top, pop, and empty).
     * <p>
     * Implement the MyStack class:
     * <p>
     * void push(int x) Pushes element x to the top of the stack.
     * int pop() Removes the element on the top of the stack and returns it.
     * int top() Returns the element on the top of the stack.
     * boolean empty() Returns true if the stack is empty, false otherwise.
     * Notes:
     * <p>
     * You must use only standard operations of a queue, which means that only push to back, peek/pop from front, size and is empty operations are valid.
     * Depending on your language, the queue may not be supported natively. You may simulate a queue using a list or deque (double-ended queue) as long as you use only a queue's standard operations.
     * <p>
     * <p>
     * Example 1:
     * <p>
     * Input
     * ["MyStack", "push", "push", "top", "pop", "empty"]
     * [[], [1], [2], [], [], []]
     * Output
     * [null, null, null, 2, 2, false]
     * <p>
     * Explanation
     * MyStack myStack = new MyStack();
     * myStack.push(1);
     * myStack.push(2);
     * myStack.top(); // return 2
     * myStack.pop(); // return 2
     * myStack.empty(); // return False
     */
    class MyStack {
        Queue<Integer> queue;

        public MyStack() {
            queue = new LinkedList<>();
        }

        public void push(int x) {
            queue.add(x);
            for (int i = 0; i < queue.size(); i++) {
                queue.add(queue.poll());  // 在queue的内部进行调换顺序
            }
        }

        public int pop() {
            return queue.poll();
        }

        public int top() {
            return queue.peek();
        }

        public boolean empty() {
            return queue.isEmpty();
        }
    }

    /**
     * Your MyStack object will be instantiated and called as such:
     * MyStack obj = new MyStack();
     * obj.push(x);
     * int param_2 = obj.pop();
     * int param_3 = obj.top();
     * boolean param_4 = obj.empty();
     */

    //388 ： longest Absolute file path

    /**
     * Suppose we have a file system that stores both files and directories. An example of one system is represented
     * in the following picture:
     * <p>
     * <p>
     * <p>
     * Here, we have dir as the only directory in the root. dir contains two subdirectories, subdir1 and subdir2.
     * subdir1 contains a file file1.ext and subdirectory subsubdir1. subdir2 contains a subdirectory subsubdir2,
     * which contains a file file2.ext.
     * <p>
     * In text form, it looks like this (with ⟶ representing the tab character):
     * <p>
     * dir
     * ⟶ subdir1
     * ⟶ ⟶ file1.ext
     * ⟶ ⟶ subsubdir1
     * ⟶ subdir2
     * ⟶ ⟶ subsubdir2
     * ⟶ ⟶ ⟶ file2.ext
     * If we were to write this representation in code, it will look like this:
     * "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext".
     * Note that the '\n' and '\t' are the new-line and tab characters.
     * <p>
     * Every file and directory has a unique absolute path in the file system, which is the order of directories that
     * must be opened to reach the file/directory itself, all concatenated by '/'s. Using the above example, the
     * absolute path to file2.ext is "dir/subdir2/subsubdir2/file2.ext". Each directory name consists of letters,
     * digits, and/or spaces. Each file name is of the form name.extension, where name and extension consist of letters, digits, and/or spaces.
     * <p>
     * Given a string input representing the file system in the explained format, return the length of the longest
     * absolute path to a file in the abstracted file system. If there is no file in the system, return 0.
     * <p>
     * Note that the testcases are generated such that the file system is valid and no file or directory name has length 0.
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: input = "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext"
     * Output: 20
     * Explanation: We have only one file, and the absolute path is "dir/subdir2/file.ext" of length 20.
     * Example 2:
     * <p>
     * <p>
     * Input: input = "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext"
     * Output: 32
     * Explanation: We have two files:
     * "dir/subdir1/file1.ext" of length 21
     * "dir/subdir2/subsubdir2/file2.ext" of length 32.
     * We return 32 since it is the longest absolute path to a file.
     * Example 3:
     * <p>
     * Input: input = "a"
     * Output: 0
     * Explanation: We do not have any files, just a single directory named "a".
     */

    class Solution388 {
        /**
         * dir \subdir2 \file.ext
         * 3    7         8      = 20
         */
        public int lengthLongestPath(String input) {
            Stack<Integer> stack = new Stack<>();
            stack.push(0);
            int res = 0;
            for (String s : input.split("\n")) {
                int level = s.lastIndexOf("\t") + 1;  //  找最后的\t，表示当前的level
                while (level + 1 < stack.size()) stack.pop();  // 找父节点是什么
                int len = stack.peek() + s.length() - level + 1;  //  -level 是减去
                stack.push(len);
                if (s.contains(".")) res = Math.max(res, len - 1);
            }
            return res;
        }
    }

    // 1209： Remove All Adjacent Duplicates in String II

    /**
     * You are given a string s and an integer k, a k duplicate removal consists of choosing k adjacent and equal
     * letters from s and removing them, causing the left and the right side of the deleted substring to concatenate together.
     * <p>
     * We repeatedly make k duplicate removals on s until we no longer can.
     * <p>
     * Return the final string after all such duplicate removals have been made. It is guaranteed that the answer is unique.
     * <p>
     * Example 1:
     * <p>
     * Input: s = "abcd", k = 2
     * Output: "abcd"
     * Explanation: There's nothing to delete.
     * Example 2:
     * <p>
     * Input: s = "deeedbbcccbdaa", k = 3
     * Output: "aa"
     * Explanation:
     * First delete "eee" and "ccc", get "ddbbbdaa"
     * Then delete "bbb", get "dddaa"
     * Finally delete "ddd", get "aa"
     * Example 3:
     * <p>
     * Input: s = "pbbcggttciiippooaais", k = 2
     * Output: "ps"
     */

    class Solution1209 {

        class pair {
            public char ch;
            public int num;

            public pair(char ch, int num) {
                this.ch = ch;
                this.num = num;
            }
        }

        public String removeDuplicates(String s, int k) {
            Stack<pair> stack = new Stack<>();  // 用stack存储，如果栈顶的元素出现次数等于3了就pop出来
            stack.push(new pair(s.charAt(0), 1));
            for (int i = 1; i < s.length(); i++) {
                char tmp = s.charAt(i);
                if (!stack.isEmpty()) {
                    pair cur = stack.peek();
                    if (cur.ch == tmp) {
                        if (++cur.num == k) {
                            stack.pop();
                        }
                    } else {
                        stack.push(new pair(tmp, 1));
                    }
                } else {
                    stack.push(new pair(tmp, 1));
                }
            }
            StringBuilder sb = new StringBuilder();
            while (!stack.isEmpty()) {
                pair cur = stack.pop();
                int num = cur.num;
                for (int i = 0; i < num; i++) {
                    sb.append(cur.ch);
                }
            }
            return sb.reverse().toString();
        }
    }

    // flatten nested list iterator

    /**
     * You are given a nested list of integers nestedList. Each element is either an integer or a list whose elements may also be integers or other lists. Implement an iterator to flatten it.
     *
     * Implement the NestedIterator class:
     *
     * NestedIterator(List<NestedInteger> nestedList) Initializes the iterator with the nested list nestedList.
     * int next() Returns the next integer in the nested list.
     * boolean hasNext() Returns true if there are still some integers in the nested list and false otherwise.
     * Your code will be tested with the following pseudocode:
     *
     * initialize iterator with nestedList
     * res = []
     * while iterator.hasNext()
     *     append iterator.next() to the end of res
     * return res
     * If res matches the expected flattened list, then your code will be judged as correct.
     *
     *
     *
     * Example 1:
     *
     * Input: nestedList = [[1,1],2,[1,1]]
     * Output: [1,1,2,1,1]
     * Explanation: By calling next repeatedly until hasNext returns false, the order of elements returned by next
     * should be: [1,1,2,1,1].
     * Example 2:
     *
     * Input: nestedList = [1,[4,[6]]]
     * Output: [1,4,6]
     * Explanation: By calling next repeatedly until hasNext returns false, the order of elements returned by next
     * should be: [1,4,6].
     */
    /**
     * // This is the interface that allows for creating nested lists.
     * // You should not implement it, or speculate about its implementation
     * public interface NestedInteger {
     * <p>
     * // @return true if this NestedInteger holds a single integer, rather than a nested list.
     * public boolean isInteger();
     * <p>
     * // @return the single integer that this NestedInteger holds, if it holds a single integer
     * // Return null if this NestedInteger holds a nested list
     * public Integer getInteger();
     * <p>
     * // @return the nested list that this NestedInteger holds, if it holds a nested list
     * // Return empty list if this NestedInteger holds a single integer
     * public List<NestedInteger> getList();
     * }
     */
    public class NestedIterator implements Iterator<Integer> {

        Stack<NestedInteger> stack = new Stack<>();

        public NestedIterator(List<NestedInteger> nestedList) {
            for (int i = nestedList.size() - 1; i >= 0; i--) {
                stack.push(nestedList.get(i));  //  从后往前加入，先进后出
            }
        }

        @Override
        public Integer next() {
            return stack.pop().getInteger();
        }

        @Override
        public boolean hasNext() {
            while (!stack.isEmpty()) {
                NestedInteger cur = stack.peek();
                if (cur.isInteger()) {
                    return true;  // 如果当前栈顶是个数字，返回true
                }
                stack.pop();  // 如果不是数字，pop出去
                for (int i = cur.getList().size() - 1; i >= 0; i--) {
                    stack.push(cur.getList().get(i));  // 遍历当前的list, 从后往前加入元素
                }
            }
            return false;
        }
    }

    /**
     * Your NestedIterator object will be instantiated and called as such:
     * NestedIterator i = new NestedIterator(nestedList);
     * while (i.hasNext()) v[f()] = i.next();
     */
    // 385： nested Integer

    /**
     * Given a string s represents the serialization of a nested list, implement a parser to
     * deserialize it and return the deserialized NestedInteger.
     * <p>
     * Each element is either an integer or a list whose elements may also be integers or other lists.
     * <p>
     * Example 1:
     * <p>
     * Input: s = "324"
     * Output: 324
     * Explanation: You should return a NestedInteger object which contains a single integer 324.
     * Example 2:
     * <p>
     * <p>
     * Input: s = "[123,[456,[789]]]"
     * Output: [123,[456,[789]]]
     * Explanation: Return a NestedInteger object containing a nested list with 2 elements:
     * 1. An integer containing value 123.
     * 2. A nested list containing two elements:
     * i.  An integer containing value 456.
     * ii. A nested list with one element:
     * a. An integer containing value 789
     */
    public class NestedInteger {
        // Constructor initializes an empty nested list.
        public NestedInteger() {
        }

        // Constructor initializes a single integer.
        public NestedInteger(int val) {
        }

        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger() {
            return true;
        }

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger() {
            return 0;
        }

        ;

        // Set this NestedInteger to hold a single integer.
        public void setInteger(int value) {
            return;
        }

        // Set this NestedInteger to hold a nested list and adds a nested integer to it.
        public void add(NestedInteger ni) {
            return;
        }

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return empty list if this NestedInteger holds a single integer
        public List<NestedInteger> getList() {
            return new ArrayList<>();
        }
    }

    class Solution385 {
        public NestedInteger deserialize(String s) {
            if (!s.startsWith("[")) return new NestedInteger(Integer.valueOf(s));
            Stack<NestedInteger> stack = new Stack<>();
            NestedInteger res = new NestedInteger();
            stack.push(res);
            int start = 1;
            for (int i = 1; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '[') {  // 如果是[，就把新的nestedInteger加到栈顶的元素里面
                    NestedInteger nestedInteger = new NestedInteger();
                    stack.peek().add(nestedInteger);
                    stack.push(nestedInteger);
                    start = i + 1;
                } else if (c == ',' || c == ']') {
                    if (i > start) {  // 如果当前的是,或者]代表要添加数字进栈顶的nestedInteger
                        int val = Integer.parseInt(s.substring(start, i));
                        stack.peek().add(new NestedInteger(val));
                    }
                    start = i + 1;
                    if (c == ']') stack.pop();
                }
            }
            return res;
        }

        // 402： remove K digits

        /**
         * Given string num representing a non-negative integer num, and an integer k, return the smallest
         * possible integer after removing k digits from num.
         * <p>
         * Example 1:
         * <p>
         * Input: num = "1432219", k = 3
         * Output: "1219"
         * Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.
         * Example 2:
         * <p>
         * Input: num = "10200", k = 1
         * Output: "200"
         * Explanation: Remove the leading 1 and the number is 200. Note that the output must not contain leading zeroes.
         * Example 3:
         * <p>
         * Input: num = "10", k = 2
         * Output: "0"
         * Explanation: Remove all the digits from the number and it is left with nothing which is 0.
         *
         * @param args
         */
        class Solution402 {
            public String removeKdigits(String num, int k) {
                if (k == num.length()) return "0";
                Stack<Character> stack = new Stack<>();
                for (int i = 0; i < num.length(); i++) {
                    // 当栈顶的元素比目前的值还要大的话，弹出
                    while (k > 0 && !stack.isEmpty() && stack.peek() > num.charAt(i)) {
                        stack.pop();
                        k--;
                    }
                    stack.push(num.charAt(i));
                }
                while (k > 0) {
                    stack.pop();
                    k--;
                }

                StringBuilder sb = new StringBuilder();
                while (!stack.isEmpty()) sb.append(stack.pop());
                sb.reverse();
                // 去掉首位的0
                int res = 0;
                while (res < sb.length() && sb.charAt(res) == '0') res++;
                return res == sb.length() ? "0" : sb.substring(res);
            }
        }

        // 793: Daily Temperature

        /**
         * Given an array of integers temperatures represents the daily temperatures, return an array answer such that
         * answer[i] is the number of days you have to wait after the ith day to get a warmer temperature. If there is
         * no future day for which this is possible, keep answer[i] == 0 instead.
         * <p>
         * Example 1:
         * <p>
         * Input: temperatures = [73,74,75,71,69,72,76,73]
         * Output: [1,1,4,2,1,1,0,0]
         * Example 2:
         * <p>
         * Input: temperatures = [30,40,50,60]
         * Output: [1,1,1,0]
         * Example 3:
         * <p>
         * Input: temperatures = [30,60,90]
         * Output: [1,1,0]
         */
        class Solution793 {
            /**
             * On each day, there are two possibilities. If the current day's temperature is not warmer than the
             * temperature on the top of the stack, we can just push the current day onto the stack - since it is
             * not as warm (equal or smaller), this will maintain the sorted property.
             * <p>
             * If the current day's temperature is warmer than the temperature on top of the stack, this is significant.
             * It means that the current day is the first day with a warmer temperature than the day associated with the
             * temperature on top of the stack. When we find a warmer temperature, the number of days is the difference
             * between the current index and the index on the top of the stack. We can declare an answer array before
             * iterating through the input and populate answer as we go along.
             *
             * @param temperatures
             * @return
             */
            public int[] dailyTemperatures(int[] temperatures) {
                int n = temperatures.length;
                int[] answer = new int[n];
                Deque<Integer> stack = new ArrayDeque<>();

                for (int currDay = 0; currDay < n; currDay++) {
                    int currentTemp = temperatures[currDay];
                    // Pop until the current day's temperature is not
                    // warmer than the temperature at the top of the stack
                    while (!stack.isEmpty() && temperatures[stack.peek()] < currentTemp) {
                        int prevDay = stack.pop();
                        answer[prevDay] = currDay - prevDay;
                    }
                    stack.push(currDay);
                }
                return answer;
            }
        }

        // 1249：Minimum move to make valid parenthesis

        /**
         * Given a string s of '(' , ')' and lowercase English characters.
         * <p>
         * Your task is to remove the minimum number of parentheses ( '(' or ')', in any positions ) so that the
         * resulting parentheses string is valid and return any valid string.
         * <p>
         * Formally, a parentheses string is valid if and only if:
         * <p>
         * It is the empty string, contains only lowercase characters, or
         * It can be written as AB (A concatenated with B), where A and B are valid strings, or
         * It can be written as (A), where A is a valid string.
         * <p>
         * <p>
         * Example 1:
         * <p>
         * Input: s = "lee(t(c)o)de)"
         * Output: "lee(t(c)o)de"
         * Explanation: "lee(t(co)de)" , "lee(t(c)ode)" would also be accepted.
         * Example 2:
         * <p>
         * Input: s = "a)b(c)d"
         * Output: "ab(c)d"
         * Example 3:
         * <p>
         * Input: s = "))(("
         * Output: ""
         * Explanation: An empty string is also valid.
         */
        class Solution1249 {
            public String minRemoveToMakeValid(String s) {
                Stack<Integer> stack = new Stack<>();
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) != '(' && s.charAt(i) != ')') continue;
                    else if (s.charAt(i) == '(') stack.push(i);
                    else {
                        if (!stack.isEmpty() && s.charAt(stack.peek()) == '(') stack.pop();
                        else stack.push(i);
                    }
                }
                Set<Integer> set = new HashSet<>(stack);
                StringBuilder res = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (!set.add(i)) {
                        continue;
                    } else {
                        res.append(s.charAt(i));
                    }
                }
                return res.toString();
            }
        }

        // 678： Valid Parenthesis string

        /**
         * Given a string s containing only three types of characters: '(', ')' and '*', return true if s is valid.
         * <p>
         * The following rules define a valid string:
         * <p>
         * Any left parenthesis '(' must have a corresponding right parenthesis ')'.
         * Any right parenthesis ')' must have a corresponding left parenthesis '('.
         * Left parenthesis '(' must go before the corresponding right parenthesis ')'.
         * '*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string "".
         * <p>
         * <p>
         * Example 1:
         * <p>
         * Input: s = "()"
         * Output: true
         * Example 2:
         * <p>
         * Input: s = "(*)"
         * Output: true
         * Example 3:
         * <p>
         * Input: s = "(*))"
         * Output: true
         */
        class Solution678 {
            public boolean checkValidString(String s) {
                Stack<Integer> brackets, stars;
                brackets = new Stack<>();
                stars = new Stack<>();

                for (int i = 0; i < s.length(); i++) {
                    char bracket = s.charAt(i);
                    if (bracket == '(') {
                        brackets.push(i);
                    } else if (bracket == '*') {
                        stars.push(i);
                    } else if (!brackets.isEmpty()) {
                        brackets.pop();
                    }
                    // When ( runs out if there's *, use star', else return false
                    else if (!stars.isEmpty()) {
                        stars.pop();
                    } else {
                        return false;
                    }
                }
                // If the ( haven't used up, see if * can substitude the )'
                while (!brackets.isEmpty() && !stars.isEmpty() && brackets.peek() < stars.peek()) {
                    // Attention, This time, the * must appear after (
                    brackets.pop();
                    stars.pop();
                }
                return brackets.isEmpty();
            }
        }

        // 907： Sum of Subarray Minimums

        /**
         * Given an array of integers arr, find the sum of min(b), where b ranges over every (contiguous) subarray of arr.
         * Since the answer may be large, return the answer modulo 109 + 7.
         *
         * Example 1:
         *
         * Input: arr = [3,1,2,4]
         * Output: 17
         * Explanation:
         * Subarrays are [3], [1], [2], [4], [3,1], [1,2], [2,4], [3,1,2], [1,2,4], [3,1,2,4].
         * Minimums are 3, 1, 2, 4, 1, 1, 2, 1, 1, 1.
         * Sum is 17.
         */

        /*

        As for case 73, 76, 72, 69,71, 75, 74, 73
        for the middle one 69, the minimum of subArrays that contains 69 will be 69, which are:

        73 76 72 69
        73 76 72 69 71
        73 76 72 69 71 75
        73 76 72 69 71 75 74
        73 76 72 69 71 75 74 73
        ......
        总共有20个子数组，怎么算的呢？左边取得的最大长度是3，右边取得的最大长度是4，结果就是(3+1)*(4+1)。那么现在的问题就是怎么计算左边的最大
        长度和右边的最大长度？可以通过单调栈。使用什么样的单调栈呢？不难想到我们需要求左边第一个小于当前元素的位置和右边第一个小于当前元素的位置，
        这就需要维护一个严格单调递增的栈。
         */
        class Solution {
            public int sumSubarrayMins(int[] arr) {
                int n = arr.length;
                //每个元素辐射范围的左边界
                int[] left = new int[n];
                //每个元素辐射范围的右边界
                int[] right = new int[n];
                Stack<Integer> stack = new Stack<>();
                //第一次循环先找到所有元素的左边界
                for (int i = 0; i < n; i++) {
                    //向左找第一个小于等于E的元素
                    while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                        stack.pop();
                    }
                    if (stack.isEmpty()) {
                        left[i] = -1;
                    } else {
                        left[i] = stack.peek();
                    }
                    stack.push(i);
                }
                stack.clear();
                //第二次循环找到所有元素的右边界
                for (int i = n - 1; i >= 0; i--) {
                    //找到第一个小于E的元素
                    while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                        stack.pop();
                    }
                    if (stack.isEmpty()) {
                        right[i] = n;
                    } else {
                        right[i] = stack.peek();
                    }
                    stack.push(i);
                }
                //按照贡献度计算即可
                //注意次数left[i]和right[i]实际上记录的是左边界-1和右边界+1，和思路中提到的有一点差别，不用单独+1了
                long res = 0;
                for (int i = 0; i < n; i++) {
                    res = (res + ((long) (i - left[i]) * (right[i] - i) * arr[i])) % 1000000007;
                }
                return (int) res;
            }

            private int getCount(int[] arr, int n, int i) {
                if (i == -1 || i == n) {
                    return Integer.MIN_VALUE;
                }
                return arr[i];
            }

            public int sumSubarrayMins1(int[] arr) {

                int n = arr.length;

                Stack<Integer> stack = new Stack<>();

                long res = 0;

                for (int i = -1; i <= n; i++) {

                    while (!stack.isEmpty() && getCount(arr, n, stack.peek()) > getCount(arr, n, i)) {
                        //对于弹出来的这个cur，i相当于cur的右边界
                        int cur = stack.pop();

                        res = (res + (long) (cur - stack.peek()) * (i - cur) * arr[cur]) % 1000000007;

                    }
                    stack.push(i);
                }
                return (int) res;
            }
        }


       public static void main(String[] args) {
            System.out.println(String.valueOf("asd"));
            System.out.println(String.valueOf("3"));
        }
    }
}
