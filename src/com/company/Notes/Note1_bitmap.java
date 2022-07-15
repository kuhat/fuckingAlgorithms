package com.company.Notes;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;

public class Note1_bitmap {
    public static void baseType() {
        Integer b = 130;
        Integer a = 130;
        Integer x = 3;
        Integer y = 3;
        System.out.println(a == b);
        System.out.println(x == y);
    }

    public static void temp(int[] tmp) {
        if (tmp == null || tmp.length == 0) {

        }
    }

    public static void encode() {
        String s = "abc";
//        for (char c : s.toCharArray()){
//            System.out.println(c - 'a');
//        }
        System.out.println((int) 'a');
        // 计算一个字符串种每个字母出现的次数
        int[] count = new int[256];
        String d = "abc";

        for (char c : d.toCharArray()) {
            int res = c - 'a';
            count[c - 'a']++;
            System.out.print(res);
        }
        System.out.println();
        for (int i : count) {
            System.out.print(i);
        }
        System.out.println("  ");

        String digit = "1234";
        for (char c : digit.toCharArray()) {
            int res = c - '0';
            System.out.print(res);
        }
    }

    // leetocde 136
    public static int singleNum(int[] num) {
        int res = 0;
        for (int i = 0; i < num.length; i++) {
            res ^= num[i];  // 将数字转化为ASCⅡ编码，相同位置为0，不同位置为1
        }
        return res;
    }

    // LeetCode 389 找出两个字符串不同的字母
    public static int findTheDifference(String s, String t) {
        char c = t.charAt(t.length() - 1);
        for (int i = 0; i < s.length(); i++) {
            c ^= s.charAt(i);
            c ^= t.charAt(i);
        }
        return c;
    }

    // leetcode 231 二的整数次幂
    public static boolean isPowerOfTwo(int n) {
        return (n > 0) && ((n & (n - 1)) == 0);
    }

    // leetcode 191 返回二进制数里面1的数量

    /**
     * write a function that takes an unsigned integer and returns the number of '1' bit
     *
     * @param n
     * @return for example, 32 bits integer 11 has binary representation 00000000000000000000000000001011
     * So the function returns 3
     * 使用n&(n-1)的方法：n&(n-1)作用：将n的二进制表示中的最低为的1改为0
     * n           n-1         n&(n-1)
     * step1:  110101      110100       110100
     * step2:  110100      110011       110000
     * step3:  110000      101111       100000
     * step4:  100000      011111       000000
     */
    public static int hammingWeight(int n) {
        int res = 0;
        while (n != 0) {
            n &= (n - 1);
            res++;
        }
        return res;
    }

    public static int hammingWeight1(int n) {
        int res = 0;
        for (int i = 0; i < 32; i++) {
            res += n & 1;
            n >>= 1; // 向右移1位
        }
        return res;
    }

    public static int hammingWeight2(int n) {
        int res = 0;
        while (n != 0) {
            if ((n & 1) == 1) res += 1;
            n >>>= 1;  // 将右移数字最左边用0来替代

        }
        return res;
    }

    public static void bitSet() {
        BitSet bitSet = new BitSet();
        System.out.println(bitSet.get(0));
        System.out.println(bitSet.size());
        bitSet.set(0);
        System.out.println(bitSet.get(0));
        System.out.println(bitSet.size());

        bitSet.set(65);
        System.out.println(bitSet.get(65));
        System.out.println(bitSet.size());
    }

    public static boolean isUniqueChars(String str) {
        if (str.length() > 256) {
            return false;
        }
        int checker = 0;

        for (int i = 0; i < str.length(); i++) {
            int val = str.charAt(i);  // 将此字符转化成ASC码
            if ((checker & (1 << val)) > 0) {  // 如果任意一个字母的ASC码左移一位与上之前的值大于0就代表此字母之前出现过
                return false;
            }
            checker |= (1 << val);  // Val 左移一位再或上checker  任何一个不同的字母的ASC码左移一位再或上之前的值该位置都是1
        }
        return true;
    }

    // LeetCode 268
    // 1. ^=
    public static int missingNumber(int[] nums) {
        int res = nums.length;
        for (int i = 0; i < nums.length; i++) {
            res ^= i ^ nums[i];
        }
        return res;
    }

    // 2. math
    public static int missingNumberMath(int[] nums) {
        int expectedSum = (nums.length + 1) * nums.length / 2;
        int actualNum = 0;
        for (int i : nums) {
            actualNum += i;
        }
        return expectedSum - actualNum;
    }

    // 190. ReverseBits
    public static int reverseBits(int n) {
        if (n == 0) return 0;
        int res = 0;
        for (int i = 0; i < 32; i++) {
            res <<= 1;
            if ((n & 1) == 1) res++;
            n >>= 1;
        }
        return res;
    }

    // 318. Maximum product of word length

    /**
     * 第一个nested for
     * 1 << 0   00001 = 1   a |
     * 1 << 1   00010 = 2   b |
     * 1 << 2   00100 = 4   c |
     * 1 << 3   01000 = 8
     * abc = 00111 = 7
     * 把每个字符串进行或操作，转换成2进制的形式
     * <p>
     * 第二个nested for:
     * byte[i] & byte[j] == 0
     * <p>
     * “abc”, "ab", "ef"
     * 000111  000011 110000
     * 7       3      32
     * 000111
     * 000011
     * 110000
     * <p>
     * 找出对应的位置有没有相同的bit，如果有，字母当前出现过
     *
     * @param words
     * @return
     */
    public static int maxProduct(String[] words) {
        int res = 0;
        int[] bytes = new int[words.length];
        for (int i = 0; i < words.length; i++) {
            int val = 0;
            for (int j = 0; j < words[i].length(); j++) {
                val |= 1 << (words[i].charAt(j) - 'a');  // 将每个单词转化成2进制的形式
            }
            bytes[i] = val;
        }
        for (int i = 0; i < bytes.length; i++) {
            for (int j = i + 1; j < bytes.length; j++) {
                if ((bytes[i] & bytes[j]) == 0) {
                    res = Math.max(res, words[i].length() * words[j].length());
                }
            }
        }
        return res;
    }

    // 187: repeatedDNASequence
    public static List<String> findRepeatedDnaSequence(String s) {
        HashSet<String> seen = new HashSet<>();
        HashSet<String> repeated = new HashSet<>();
        for (int i = 0; i < s.length() - 9; i++) {
            String temp = s.substring(i, i + 10);
            if (!seen.add(temp)) { // 如果已经加过了就在repeated里面加
                repeated.add(temp);
            }
        }
        return new ArrayList<>(repeated);
    }

    // 78. subsets 重要 backtracking
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        subsetsHelper(res, new ArrayList<>(), nums, 0);
        return res;
    }

    private static void subsetsHelper(List<List<Integer>> res, List<Integer> list, int[] nums, int index) {
        res.add(new ArrayList<>(list));
        for (int i = index; i < nums.length; i++) {
            list.add(nums[i]);
            subsetsHelper(res, list, nums, i + 1);
            list.remove(list.size() - 1);
        }
    }

    // LeetCode 397: IntegerReolacement

    /**
     * 如果倒数第二位是0，那么n-1的操作会比n+1的操作消去更多的1
     * 9
     * 1001 + 1 = 1010
     * 1001 - 1 = 1000
     * <p>
     * 如果倒数第二位是1，那么n+1的操作比n-1的操作可以消去更多的1
     * 11
     * 1011 + 1 = 1100
     * 1111 + 1 = 10000
     *
     * @param
     */

    public static int integerReplacement(int n) {
        long N = n;
        int res = 0;
        while (N != 1) {
            if (N % 2 == 0) {
                N >>= 1;
            } else {
                if (N == 3) {
                    res += 2;
                    break;
                }
                N = (N & 2) == 2 ? N + 1 : N - 1;
            }
            res++;
        }
        return res;
    }

    // 判断除以4，除以4的操作比加一再除以2少
    public static int integerReplacement1(int n) {
        if (n == Integer.MAX_VALUE) return 32;
        int res = 0;
        while (n != 1) {
            if (n % 2 == 0) {
                n /= 2;
            } else {
                if ((n + 1) % 4 == 0 && (n - 1 != 2)) {
                    n++;
                } else n--;
            }
            res++;
        }
        return res;
    }

    // LeetCode 393 UTF-8 validation
    public static boolean validUtf8(int[] data) {
        if (data == null || data.length == 0) return false;
        boolean isValid = true;
        for (int i = 0; i < data.length; i++) {
            if (data[i] > 255) return false;
            int numberOfBytes = 0;
            if ((data[i] & 128) == 0) {  // 0xxxxxxx, 1 byte 128(10000000)
                numberOfBytes = 1;
            } else if ((data[i] & 224) == 192) { // 110xxxxx, 2 bytes, 224(11100000), 192(11000000)
                numberOfBytes = 2;
            } else if ((data[i] & 240) == 224) { // 1110xxxx, 3 bytes, 240(11110000), 224(11100000)
                numberOfBytes = 3;
            } else if ((data[i] & 248) == 240) { // 11110xxx, 4 bytes, 248(11111000), 240(11110000)
                numberOfBytes = 4;
            } else {
                return false;
            }
            for (int j = 0; j < numberOfBytes; j++) {
                if (i + j > data.length) return false;
                if ((data[i + j] & 192) != 128) return false;
            }
            i = i + numberOfBytes - 1;
        }
        return isValid;
    }

    // LeetCode 201: BitwiseANDofNumberRange

    public static int rangeBitWise(int m, int n) {
        int offset = 0;
        while (m != n) {
            m >>= 1;
            n >>= 1;
            offset ++;
        }
        return m << offset;
    }

    // LeetCode 371 Sum 不能用+

    /**
     * 用与操作和亦或解决
     * 0 + 0 = 00 亦或
     * 1 + 0 = 01
     * 0 + 1 = 01
     *
     * 1 + 1 = 1 与
     *
     * 3：00011
     * 5：00101
     * 8: 01000
     *
     * carry: 1
     * a: 00110
     * b: 00010
     *
     * carry:10
     * a: 00100
     * b: 00100
     *
     * carry: 00100
     * a: 0
     *
     *
     * 8：01000
     * @param a
     * @param b
     */

    public static int getSum(int a, int b) {
        if (a==0) return b;
        if (b == 0) return a;

        while (b != 0) {
            int carry = a & b;
            a = a ^ b;
            b = carry << 1;
        }
        return a;
    }


    public static void main(String[] args) {
//        baseType();
//
//        int [] nums = null;
//        temp(nums);
//
//        encode();
        int[] nums = new int[]{1, 2, 2, 3, 4, 3, 1};
        System.out.println(singleNum(nums));
        bitSet();
    }
}
