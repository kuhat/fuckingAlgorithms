package com.company.huaweiprep;

import java.util.Scanner;

/**
 * @projectName: algorithms
 * @package: com.company.huaweiprep
 * @className: 验证密码合格程序
 * @author: Danny
 * @description: TODO
 * @date: 2023/2/11 17:12
 * @version: 1.0
 */
public class 验证密码合格程序 {
    /**
     *  密码要求:
     *
     * 1.长度超过8位
     *
     * 2.包括大小写字母.数字.其它符号,以上四种至少三种
     * 3.不能有长度大于2的包含公共元素的子串重复 （注：其他符号不含空格或换行）
     *
     * 数据范围：输入的字符串长度满足 1≤n≤100 1≤n≤100
     * 输入描述：
     *
     * 一组字符串。
     * 输出描述：
     *
     * 如果符合要求输出：OK，否则输出NG
     * 示例1
     * 输入：
     *
     * 021Abc9000
     * 021Abc9Abc1
     * 021ABC9000
     * 021$bc9000
     *
     * 输出：
     *
     * OK
     * NG
     * NG
     * OK
     */

    // 题目要求找大于等于3的重复字串，那就取最小值3，如果3个重复的字符串都没有，那3以上的也没有
    private static boolean getString(String str, int l, int r) {
        if (r >= str.length()) return false;
        if (str.substring(r).contains(str.substring(l,r))) {
            return true;
        } else {
            return getString(str, l + 1, r + 1);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String input = in.nextLine();
            if (input.length() < 8) {
                System.out.println("NG");
                continue;
            }
            int kind = 0;
            boolean hasNumber = false, hasUpper = false, hasLower = false, hasOther=false;
            for (int i = 0; i < input.length(); i++) {
                if (Character.isDigit(input.charAt(i))) {
                    if (!hasNumber) {
                        hasNumber = true;
                        kind++;
                    }
                } else if (Character.isUpperCase(input.charAt(i))) {
                    if (!hasUpper) {
                        hasUpper = true;
                        kind++;
                    }
                } else if (Character.isLowerCase(input.charAt(i))) {
                    if (!hasLower) {
                        hasLower = true;
                        kind++;
                    }
                } else {
                    if(!hasOther) {
                        hasOther = true;
                        kind++;
                    }
                }
            }
            if (kind < 3) {
                System.out.println("NG");
                continue;
            }

            if (getString(input, 0, 3)) {
                System.out.println("NG");
                continue;
            }
            System.out.println("OK");
        }
    }
}
