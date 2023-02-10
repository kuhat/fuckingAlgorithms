package com.company.huaweiprep;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @projectName: algorithms
 * @package: com.company.huaweiprep
 * @className: hexdecimal
 * @author: Danny
 * @description: TODO
 * @date: 2023/2/9 11:24
 * @version: 1.0
 */
public class hexdecimal {
    // 写出一个程序，接受一个十六进制的数，输出该数值的十进制表示。
    private final static int BASE = 16;
    private static Map<Character, Integer> map = new HashMap<Character, Integer>()
    {{
        put('0', 0);
        put('1', 1);
        put('2', 2);
        put('3', 3);
        put('4', 4);
        put('5', 5);
        put('6', 6);
        put('7', 7);
        put('8', 8);
        put('9', 9);
        put('A', 10);
        put('B', 11);
        put('C', 12);
        put('D', 13);
        put('E', 14);
        put('F', 15);
        put('a', 10);
        put('b', 11);
        put('c', 12);
        put('d', 13);
        put('e', 14);
        put('f', 15);
    }};

    public static int getDecimal(String number) {
        int res = 0;
        for (char ch : number.toCharArray()) {
            res = res * BASE + map.get(ch);
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String number = in.next();
            int res = getDecimal(number.substring(2));
            System.out.println(res);
        }
    }
}
