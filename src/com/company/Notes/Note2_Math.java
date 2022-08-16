package com.company.Notes;

import java.util.ArrayList;
import java.util.List;

public class Note2_Math {

    // Leetcode 7: reverse Integer
    public static int reverse(int x){
        long res = 0;
        while (x != 0){
            res = res * 10 + x % 10; // 取出各位数，加上原来的数字
            x /= 10;  // 去除个位数字
            if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE){
                return 0;
            }
        }
        return (int)res;
    }
    public static int reverse1(int x){
        int res = 0;
        while (x != 0){
            int cur = res;
            res = res * 10 + x % 10;
            if (res/10 != cur) return 0;
            res = res * 10 + x % 10; // 取出各位数，加上原来的数字

        }
        return res;
    }

    // LeetCode66： Plus one
    public static int[] plusOne(int[] digits){
        if (digits == null || digits.length == 0) {
            return digits;
        }

        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i] ++;
                return digits;
            }else {
                digits[i] = 0;
            }
        }
        int[] res = new int[digits.length + 1];  // 999 + 1 = 1000这种情况
        res[0] = 1;
        return res;
    }

    // leetCode 8: String to Integer
    public static int myAtoi(String s){
        s = s.trim();
        if (s == null || s.length() == 0){
            return 0;
        }
        char firstChar = s.charAt(0);
        int sign = 1;
        int start = 0;
        long res = 0;
        if (firstChar == '+'){
            start++;
        } else if (firstChar == '-') {
            sign = -1;
            start ++;
        }
        for (int i = start;i < s.length(); i++){
            if (!Character.isDigit((s.charAt(i)))){
                return (int) res * sign;
            }
            res = res*10+s.charAt(i) - '0';
            if (sign == 1 && res > Integer.MAX_VALUE) return Integer.MAX_VALUE;
            if (sign == -1 && res > Integer.MAX_VALUE) return Integer.MIN_VALUE;
        }
        return (int) res*sign;
    }

    // LeetCode 367: valid Perfect Square
    // 1. 二分法 o(log(n))
    public static boolean isPerfectSquare(int num){
        int low = 1;
        int high = num;
        while (low <= high){
            long mid = (high - low) / 2 + low;
            if (mid * mid == num) {
                return true;
            } else if (mid * mid < num) {
                low = (int)mid + 1;
            }else {
                high = (int)mid - 1;
            }
        }
        return false;
    }

    // 2. n*n > num
    public static boolean isPerfectSquare1(int num){
        if ((num)< 0) return false;
        if (num == 1) return true;
        for (int i = 0; i <= num / i; i++) {
            if (i * i == num) return true;
        }
        return false;
    }

    // 3. 牛顿法 （切线法）
    public static boolean isPerfectSquare2(int num){
        long x = num;
        while (x * x > num){
            x = (x + num / x) /2;
        }
        return x * x == num;
    }

    // LeetCode504:
    public String convertToBase7(int num) {
        if (num == 0) return "0";
        StringBuilder sb = new StringBuilder();
        boolean negative = false;

        if (num < 0) {
            negative = true;
        }
        while(num != 0) {
            sb.append(Math.abs(num % 7));
            num /= 7;
        }
        if (negative) {
            sb.append("-");
        }
        return sb.reverse().toString();
    }

    public String convertToBase72(int num ) {
        if (num < 0) {
            return "-" + convertToBase72(-num);
        }
        if (num < 7) {
            return num + "";
        }
        return convertToBase72(num / 7 + num % 7);
    }

    // LeetCode 67: Add binary

    /**
     * a = "11"
     * b = "1"
     * return "100"
     *
     * sum = 2
     * sum % 2 = 0
     * sb: 0
     * remainder = 1
     *
     * sum = 1
     * sum = 2
     * sum % 2 = 0
     * sb: 00
     * remainder: 1
     *
     * @param a
     * @param b
     * @return
     */
    public static String addBinary(String a, String b){
        StringBuilder sb = new StringBuilder();
        int[] mark = {1, 2};
        int i = a.length() - 1;
        int j = b.length() - 1;
        int remainder = 0;
        while (i >= 0 || j >= 0) {
            int sum = remainder;
            if (i >= 0) {
                sum += a.charAt(i) - '0';
                i --;
            }
            if (j >= 0) {
                sum += b.charAt(j) - '0';
                j --;
            }
            sb.append(sum %2);
            remainder = sum / 2;
        }
        if (remainder != 0) {
            sb.append(remainder);
        }
        return sb.reverse().toString();
    }

    // LeetCode 258: Add digits
    public static int addDigits(int num) {
        int sum = 0;
        while (num != 0) {
            sum += num % 10;
            num /= 10;
        }
        if (sum >= 10) {
            return addDigits(sum);
        } else return sum;
    }

    /**
     * 1    1
     * 2    2
     * 3    3
     * 4    4
     * 5    5
     * 6    6
     * 7    7
     * 8    8
     * 9    9
     * 10   1
     * 11   2
     * 12   3
     * 13   4
     * 14   5
     * 15   6
     * 16   7
     * 对9来说是一个循环
     *
     * @param num
     * @return
     */
    public static int addDigit1(int num) {
        return (num -1) % 9 + 1;
    }

    // LeetCode 415: add Strings
    public static String addStrings(String num1, String num2){
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        int carry = 0;
        StringBuilder sb = new StringBuilder();
        while(i >= 0 || j >= 0 || carry == 1) {
            int a = i >= 0 ? num1.charAt(i--) - '0' : 0;
            int b = j >= 0 ? num2.charAt(j--) - '0' : 0;
            int sum = a + b + carry;
            sb.append(sum % 10);
            carry = sum / 10;
        }
        return sb.reverse().toString();
    }

    //LeetCode 43: Multiply Strings
    public static String multiplyStrings(String num1, String num2) {
        if (num1 == null || num2 == null ) return "0";
        int[] digits = new int[num1.length() + num2.length()];
        for (int i = num1.length() -1; i >= 0 ; i--) {
            for (int j = num2.length() -1; j >= 0; j++) {
                int product = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int p1 = i + j, p2 = i + j + 1;
                int sum = product + digits[p2];
                digits[p1] += sum/10;
                digits[p2] = sum % 10;
            }
        }
        StringBuilder res = new StringBuilder();
        // digits: [0, 0, 1, 0, 3]
        for (int digit : digits){
            if(!(digit == 0 && res.length() == 0)) {
                res.append(digit);
            }
        }
        return res.length() == 0 ? "0" : res.toString();
    }

    // LeetCode19: DivideTwoStrings

    /**
     * 背下来**
     * 1. +-
     * 2. 越界
     * 3. = 0 3/5
     * 4. 正常
     *
     * time : o(logn)
     * space: o (logn)
     * @return
     */
    public static int divide(int dividend, int divisor){
        int sign = 1;  // 符号
        if (dividend > 0 & divisor < 0 || dividend< 0 && divisor > 0) sign = -1;
        long ldividend = Math.abs((long) dividend);
        long ldivisor = Math.abs((long) divisor);
        if (ldividend < ldivisor || ldividend == 0) return 0;
        long lres = divide(ldividend, ldivisor); // 越界
        int res = 0;
        if (lres > Integer.MAX_VALUE) {
            res = (sign == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        } else res = (int)(sign * lres);
        return res;
    }

    public static long divide(long ldividend, long ldivisor){
        if(ldividend < ldivisor) return 0;
        long sum = ldivisor;
        long multiple = 1;
        while((sum + sum) < ldividend) {
            sum += sum;
            multiple += multiple;
        }
        return multiple + divide(ldividend - sum, ldivisor);
    }

    // LeetCode 69: sqrt(x)

    /**
     * 二分法
     * 看367
     * @param x
     * @return
     */
    public static int mySqirt(int x){
        if(x <=0) return 0;
        int low = 1, high = x;
        while(low <= high) {
            long mid = (high - low) / 2 + low;
            if (mid * mid == x) {
                return (int) mid;
            } else if (mid * mid < x) {
                low = (int) mid + 1;
            } else {
                high = (int)mid + 1;
            }
        }
        if (high * high < x) {
            return (int) high;
        } else {
            return (int) low;
        }
    }

    // LeetCode50 power
    public static double power(double x, int n) {
        if (n > 0) {
            return Pow(x, n);
        } else return 1.0 / Pow(x, n);
    }

    public static double Pow(double x, int n) {
        if (n == 0 ) return 1;
        double y = Pow(x, n / 2);
        if (n % 2 == 0) {
            return y * y;
        } else return y * y * x;
    }

    public static double Pow2(double x, int n) {
        if (n == 0) return 1.0;
        double res = 1;
        long abs = Math.abs((long) n);
        while ( abs > 0) {
            if (abs % 2 != 0) {
                res *= x;
            }
            x *= x;
            abs /= 2;
        }
        System.out.println(res);
        if (n > 0) {
          return res;
        } else {
            return 1.0 / res;
        }
    }

    // LeetCode 5.7 Perfect Number
    public static boolean perfectNum(int num){
        if (num == 1) return false;
        int res = 0;
        for (int i = 2; i < Math.sqrt(num); i ++) {
            if (num % i == 0) {
                res += i + num / i;
            }
        }
        return res == num;
    }

    // LeetCode 172

    /**
     * 求阶乘末尾有多少个0其实就是求5出现的个数
     *
     * @param n
     * @return
     */
    public static int trailingZeros(int n) {
        if (n == 0) {
            return 0;
        } else {
            return n / 5 + trailingZeros(n / 5);
        }
    }

    // LeetCode412： FizzBuzz
    public static List<String> fissBuzz(int n) {
        List<String> res = new ArrayList<>();
        if (n == 0) return res;
        for (int i = 1; i <= n; i++) {
            if ((i % 3 == 0) && (i % 5 == 0)) {
                res.add("FizzBuzz");
            } else if (i % 3 == 0) {
                res.add("Fizz");
            } else if (i % 5 == 0) {
                res.add("Buzz");
            } else {
                res.add(String.valueOf(i));
            }
        }
        return res;
    }

    //LeetCode236 Ugly Number
    public static boolean isUgly(int num) {
        if (num == 1) return true;
        if (num == 0) return false;
        while(num % 2 == 0) num /= 2;
        while(num % 3 == 0) num /= 3;
        while(num % 5 == 0) num /= 5;
        return num == 1;


    }

    //LeetCode263 ugly number 2
    public static int uglyNumber2(int n) {
        int nums[] = new int[n];
        int index2 = 0, index3 = 0, index5 = 0;
        nums[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            nums[i] = Math.min(nums[index2] * 2, Math.min(nums[index3] * 3, nums[index5] * 5));
            if (nums[i] == nums[index2] * 2) index2++;
            if (nums[i] == nums[index3] * 3) index3++;
            if (nums[i] == nums[index5] * 5) index5++;
        }
        return nums[n -1];
    }

    // 13: Roman to Integer

    /**
     * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
     *
     * Symbol       Value
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     * For example, 2 is written as II in Roman numeral, just two ones added together. 12 is written as XII, which is simply X + II.
     * The number 27 is written as XXVII, which is XX + V + II.
     *
     * Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII.
     * Instead, the number four is written as IV. Because the one is before the five we subtract it making four. The same principle
     * applies to the number nine, which is written as IX. There are six instances where subtraction is used:
     *
     * I can be placed before V (5) and X (10) to make 4 and 9.
     * X can be placed before L (50) and C (100) to make 40 and 90.
     * C can be placed before D (500) and M (1000) to make 400 and 900.
     * Given a roman numeral, convert it to an integer.
     *
     *
     *
     * Example 1:
     *
     * Input: s = "III"
     * Output: 3
     * Explanation: III = 3.
     * Example 2:
     *
     * Input: s = "LVIII"
     * Output: 58
     * Explanation: L = 50, V= 5, III = 3.
     * Example 3:
     *
     * Input: s = "MCMXCIV"
     * Output: 1994
     * Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
     */
    class Solution13 {
        public int romanToInt (String s) {
            if (s == null || s.length() == 0) return 0;
            int sum = 0;
            int preSum = getValue(s.charAt(0));
            for (int i = 1; i < s.length(); i ++) {
                int num = getValue(s.charAt(i));
                if (preSum < num) sum -= preSum;
                else sum += preSum;
                preSum = num;
            }
            sum += preSum;
            return sum;
        }

        private int getValue(char ch) {
            switch (ch) {
                case 'I': return 1;
                case 'V': return 5;
                case 'X': return 10;
                case 'L': return 50;
                case 'C': return 100;
                case 'D': return 500;
                case 'M': return 1000;
            }
            return 0;
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
        System.out.println(myAtoi("  -334  22"));
        System.out.println(Pow2(2,-2));


    }
}
