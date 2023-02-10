package com.company.huaweiprep;

/**
 * @projectName: algorithms
 * @package: com.company.huaweiprep
 * @className: 质数因子
 * @author: Danny
 * @description: TODO
 * @date: 2023/2/9 11:51
 * @version: 1.0
 */
import java.util.Scanner;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class 质数因子 {
    //  功能:输入一个正整数，按照从小到大的顺序输出它的所有质因子（重复的也要列举）（如180的质因子为2 2 3 3 5 ）
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        long n=sc.nextLong();
        //取输入n的平方根
        long q=(long)Math.sqrt(n);
        //遍历所有可能的质数因子
        for(int i=2;i<=q;i++){
            //因为是从最小的因子开始分解质因子，所以不会存在分解出合数的情况
            while(n%i==0){
                n/=i;
                System.out.print(i+" ");
            }
        }
        //如果最后不为1，说明剩下的是一个质数，直接输出
        if(n!=1){
            System.out.print(n+" ");
        }
    }

}