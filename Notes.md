# 1. 位运算

## 1.1 二进制

* byte: 8 bit (-2^7, 2^7-1)
* 1 KB = 1 K Byte = 1024 Byte,  1 MB = 1024 KB ...

1. 二进制表示数字：Positive/Minus

   1 byte 最大值: 0111 1111, 最小值: 1000 0000, 最高位代表符号位。首位0代表正数，1代表负数

    2. 码

       * 5原码：0000 0101 = 2^0 + 2^2 = 5 （绝对二进制）

         5反码：1111 1010 （取反）

         5补码：1111 1011 （加一）

         -5为5的补码

       * +127原码：0111 1111

         +127反码：1000 0000

         +127补码：1000 0001

         -127为127的补码

         -128 = 1000 0000

       Java: Integer.toBinaryString(-5) = 11111111111111111111111111111011 (32位)

## 1.2 Java八大类型

byte: 1 byte (-128, 127)				boolean: 0 or 1

short: 2 byte (-2^15, 2^15-1)		char: 2 byte

int: 4 byte (-2^31, 2^31-1)			float: 4 byte

long: 8 byte (-2 ^63, 2^63-1)		double: 8 byte     

* Java中，一切皆对象，但是八大基本类型不是对象

* int 只有0没有null, Integer 有null, 是一个class. 

```java
public class BaseType {
    public static void baseType(){
        Integer b = 130;
        Integer a = 130;
        Integer x = 3;
        Integer y = 3;
        System.out.println(a == b);
        System.out.println(x == y);
    }

    public static void main(String[] args) {
        baseType();
    }
}
```

以上代码会输出`false`, `true`,  因为Java源码种把-128到127之间的数都cache了，在这之间的数可以用等号，超出了就要用`x.equals(y);`.

## 1.3 位运算

* 按位与：a & b
* 按位或：a | b
* 按位异或：a ^ b
* 按位取反：~a
* 左移：a << b
* 右移：a >> b
* 无符号右移：a >>> b 

1. 按位与

   规则：两位同时为“1”，结果才为1，否则为0

   0 & 0 = 0；

   0 & 1 = 0；

   1 & 0 = 0；

   1 & 1 = 1；

2. 按位或

   规则：参加运算的两个对象只要一个为1，其为1

   0 | 0 = 0；

   0 | 1 = 1；

   1 | 0 = 1；

   1 | 1 = 1；

   ```java
   public class Main {
    public static void temp(int[] tmp) {
           if (tmp == null || tmp.length == 0) {
    		}           
    }
    
    public static void main(String[] args){
    	int[] nunms = null;
    	temp(nums);
    }
   }
   ```

   上面代码的 `（tmp == null）|| tmp.leng == 0`顺序一定不能反，不然先执行`tmp.length==0`会报错*NullPointerException*. 

3. 按位异或

   规则：相同为零，不同为一

   0 ^ 0 = 0;

   0 ^ 1 = 1;

   1 ^ 0 = 1;

   1 ^ ! = 0;

4. 移位：左移/右移

   1 << 0: 00001 = 1:       1 >> 0: 00001 = 1;

   1 << 1: 00010 = 2;       2 >> 1: 00001 = 1;

   1 << 2: 00100 = 4;        4 >> 1 00010 = 2;

   1 << 3: 01000 = 8;        8 >> 1 00100 = 4; (和除号差不多)    

5. 无符号右移 >>>: 不管正负标志位是0还是1，将该数的二进制码整体右移，左边部分总是以0填充，右边部分舍弃。

   * -5用二进制表示**1**111 1011。第一个1是该数标志位。

     -5 >> 2: **1**111 1011 -> 11**1**1 1110。标志位向右移动了两位。

     -5 >>> 2: **1**111 1011 -> *00* **1**1 1110。前两个*0*作为补充的零，最后两个1舍弃。

   * 没有无符号左移。

## 1.4 编码标准

* 国际标准：ASCⅡ （0-127）

* 扩展的 ASCⅡ （0-255）(一般写`int[256]`)

* 字符集：Unicode (全世界的文字字符集), UTF-8, 中国GB2312

* 算法种的编码：- '0' 或者 -'a'

  ```java
  public static void encode(){
      String s = "abc";
      for (char c : s.toCharArray()){
          System.out.println(c - 'a');
      }
      System.out.println((int)'a');
  }
  ```

​	output:

	```
	 0 // 'a'-'a'
	 1 // 'b'-'a'
	 2 // 'c'-'a'
	 97 // 'a'
	```

​    计算一个字符串中每个字母出现的次数：

```java
 int[] count = new int[256];
        String d = "abc";

        for (char c: d.toCharArray()){
            int res = c - 'a';
            count[c - 'a']++;
            System.out.print(res); // 0, 1, 2
        }
System.out.println();
        for (int i : count) {
            System.out.print(i);
        } // 111000000....(253个0)
```

如果是数字，拿2应该减去0

```java
String digit = "1234";
for (char c : digit.toCharArray()){
    int res = c - '0';
    System.out.print(res);  // 1234
}
```

## 1.5 亦或XOR

* 判断唯一元素

* LeetCode136: Single Number

  Given an array of integers, every element appears twice except for one, Find the single one.

  ```java
  public static int singleNum(int[] num) {
      int res = 0;
      for (int i = 0; i < num.length; i ++) {
          res ^= num[i];  // 将数字转化为ASCⅡ编码，相同位置为0，不同位置为1
      }
      return res;
  }
  ```

* LeetCode 389: Find the Difference

  Given two Strings s and t which consist of only lowercase letters. String t is generated by random shuffling string s and then *add one more letter at a random position*. Find the letter that was added in t. 

  input:

  ```java
  S = "abcd"
  t = "abcde"
  ```

  output:

  ```java
  e
  ```

  Sol:

  ```java
  // LeetCode 389
  public static int findTheDifference(String s, String t){
      char c = t.charAt(t.length() - 1);
      for (int i = 0; i < s.length(); i++) {
          c ^= s.charAt(i);
          c ^= t.charAt(i);
      }
      return c;
  }
  ```



## 1.6 n & ( n - 1)

+ 判断是2的整数次幂：`n&(n-1) == 0;`  8: 1000, 7: 0111, 8&7 = 0;

  LeetCode 231. Power of Two

  Given a integer, write a function to determine if it is a power of two.

  ```java
  public static boolean isPowerOfTwo(int n ){
      return (n>0) && ((n&(n-1)) == 0);
  }
  ```

+ 最低位为1的改为0: `n &= (n-1);` 9: 1001, 9 & (9-1) = 1001 & 1000 = 1000 

  LeetCode 191. Number of 1 Bits

  Write a function that takes an unsigned integer and returns the number of '1' bits it has.

  ```java
  public static int hammingWeight(int n) {
      int res = 0;
      while(n != 0) {
          n &= (n-1);
          res ++;
      }
      return res;
  }
  ```



## 1.7 n&1

+ 判断奇偶数 n &1 (返回1表示奇数，0表示偶数) 偶数的第0位肯定为零。

+ 末尾取1

  LeetCode191: Number of 1 bits

  ```java
  public static int hammingWeight1(int n ){
      int res = 0;
      for (int i = 0; i < 32; i++) {
          res += n & 1;
          n >>= 1; // 向右移1位
      }
      return res;
  }
  ```

## 1.8 万能Follow Up

* 数据量过大怎么办

  1. 求数组之和，数组元素有1亿个，单机装不下

     Sol1：分机解决，分10台机器，分别存储计算（分布式计算）

     Sol2: BitMap (位图) 

  2. Given an array containing n distinct numbers taken from 0, 1, 2, 3, ...., n, find the one that is missing from the array.

     ```java
     public static int missingNumber(int[] nums){
         int res = nums.length;
         for (int i = 0; i < nums.length; i++) {
             res ^= i ^ nums[i];
         }
         return res;
     }
     ```

* BitMap: 用bit表示每一位数字。`int: 4 byte = 4 * 8 = 32 bit`有多少位就用多少个bit，每个位置的数字对应bitmap上面的一个bit。如果这个位置有数字，bit值为1，如果没有数字bit的值为0。**Int** 类型有32个bit，如果有1亿个数据，就用bitmap来存储数据，就会有1亿/32个bitmap.

  Java表示：BitSet

  + Initialize 的时候是64位的，之后扩容是每次加2^6

  + `BitSet.set(i)`: 将第i位的bit设置为1

  + `BitSet.get(i)`: 得到第i位的数值，true or false

    ```java
    public static void bitSet() {
        BitSet bitSet = new BitSet();
        System.out.println(bitSet.get(0));  // false
        System.out.println(bitSet.size());  // 64
        bitSet.set(0);				       
        System.out.println(bitSet.get(0));  // true
        System.out.println(bitSet.size());  //  64
    
        bitSet.set(65);
        System.out.println(bitSet.get(65));  // true
        System.out.println(bitSet.size());   // 128
    }
    ```
    
    
    
    Implement an algorithm to determine if a string has all unique characters, what if you cannot use additional data structures?
    
    
    
    + ```java
      public static boolean isUniqueChars(String str){
          if (str.length() > 256) {
              return false;
          }
          int checker = 0;
      
          for (int i = 0; i < str.length(); i++) {
              int val = str.charAt(i);  // 将此字符转化成ASC码
              if ((checker & (1 << val)) > 0) {  // 如果任意一个字母的ASC码左移一位与上之前的值大于0就代表此字母之前出现过
                  return false;
              }
              checker |= (1<<val);  // Val 左移一位再或上checker  任何一个不同的字母的ASC码左移一位再或上之前的值该位置都是1
          }
          return true;
      }
      ```



## 2. 数学题

### 2.1  越界

+ LeetCode 7：Reverse Number

Given a signed 32-bit integer `x`, return `x` *with its digits reversed*. If reversing `x` causes the value to go outside the signed 32-bit integer range `[-2^31, 2^31 - 1]`, then return `0`.

Java 种*Integer.Max_Value*  是 2147483647，reverse了过后就是7463847412, 肯定是越界了的。int: 4 byte (-2^31, 2^31-1), long: 8 byte (-2^63, 2^63-1), integer -> long. 

```java
// 必备代码！！！！背下来
long res = 0;
while (x != 0){
    res = res * 10 + x % 10; // 取出个位数，加上原来的数字
    x /= 10;  // 去除个位数字
    if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE){
        return 0;  //  越界的话return 0;
    }
}
return (int)res;
```

### 2.2   比较处理

```Java
System.out.println(Integer.MAX_VALUE + 2);
```

这一段代码的输出为-2147483647。

如果我们跟之前的数字对比，就会不同，可以利用这一点来解题。

```java
int res = 0;
while (x != 0){
    int cur = res;
    res = res * 10 + x % 10;
    if (res/10 != cur) return 0;
    res = res * 10 + x % 10; // 取出各位数，加上原来的数字
}
return res;
```

### 2.3  进位

从个位相加，最后进位，开辟新空间

加法：末尾开始

leetCode66: Plus one:

ou are given a **large integer** represented as an integer array `digits`, where each `digits[i]` is the `ith` digit of the integer. The digits are ordered from most significant to least significant in left-to-right order. The large integer does not contain any leading `0`'s.

ncrement the large integer by one and return *the resulting array of digits*.

```java
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
```

### 2.4  符号，字母

+ 符号：***sign***单独提取，最后处理

+ 字母：`!Character.isDihit(str.charAt(i))`.

+ LeetCode8: String to Integer (Atoi)

  ```java
  // leetCode 8: String to Integer
  class myAtoi{
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
              if (!Character.isDigit((s.charAt(i)))){  // 如果不是数字，直接return
                  return (int) res * sign;
              }
              res = res*10+s.charAt(i) - '0';
              if (sign == 1 && res > Integer.MAX_VALUE) return Integer.MAX_VALUE;  // 判断越界
              if (sign == -1 && res > Integer.MAX_VALUE) return Integer.MIN_VALUE;
          }
          return (int) res*sign;
      }    
  }
  
  ```

### 2.5 开方

Given a **positive** integer *num*, write a function which returns True if *num* is a perfect square else False.

**Follow up:** **Do not** use any built-in library function such as `sqrt`.

 

```java
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
```

```java
// 2. n*n . num
public static boolean isPerfectSquare1(int num){
    if ((num)< 0) return false;
    if (num == 1) return true;
    for (int i = 1; i <= num / i; i++) {
        if (i * i == num) return true;
    }
    return false;
}
```



<img src="C:\Users\zwh52\AppData\Roaming\Typora\typora-user-images\image-20220615112513296.png" alt="image-20220615112513296" style="zoom:67%;" />

```java
// 3. 牛顿法 （切线法）

public static boolean isPerfectSquare2(int num){
    long x = num;
    while (x * x > num){
        x = (x + num / x) /2;
    }
    return x * x == num;

}
```

## 3. 数组

### 3.1 双指针

数组问题大多数都可以用双指针解决

#### 3.1.1 模板一：单向双指针

```java
Object result; // 返回结果 int List String
Object temp； //临时变量，不一定有，看题
for (int i = 0; i < num.length; i ++) {
  // 处理
}

```

+ 区间问题

  1. 按start 排序

  2. 解题技巧：前一个区间 *end* & 后一个区间 *start*

     LeetCode 252: Meeting Rooms

     Given an array of meeting time intercals consisting of start and end times [[s1, e1], [s2, e2, ...]] (si < ei), determine if a person could attend all meetings.

     For example: Given [[0, 30], [5, 10], [15, 20]], return falsel.

     + 按start 排序

     + 解题技巧： 前一个区间 end & 后一个区间  start

       ```java
       public boolean conAttendMeetings(Interval[] intervals) {
       	Arrays.sort(intervals, (x, y) -> x.start - y.start);
       	for (int i = 0; i < interval.length; i ++) {
       		if (interval[i - 1].end > intervals[i].start) {
       			return false;
       		}
       	}
       	return true;
       }
       ```

       

+ 扫描线算法
  1. 在计算机图形学种，多边形填充算法 （Sweep Line）
  
  2. 中心思想：单向扫描
  
     LeetCode 153: Meeting Rooms 2
  
     Given an array of meeting time intervals consisting of start and end times [[s1, e1]. [s2, e2], ...] [si < ei], find the minimum number of conference rooms required.
  
     Example: input: [[0, 30], [5, 10], [15,20]]
  
     ​				output: 2
  
     ```java
     public int minMeetingRooms(Interval[] internals) {
     	int[] starts = new int[intervals.length];
     	int[] ends = new int[intervals.length];
     	for (int i = 0; i < intervals.length; i ++) {
     		start[i] = intervals[i].start;
     		ends[i] = intervals[i].end;
     	}
     	Arrays.sort(starts);
     	Arrays.sort(ends);
     	int end = 0;
     	int res = 0;
     	for (int i= 0; i < intervals.length; i ++) {
     		if(start[i] < ends[end]) {
     			res ++;
     		} else {
     			end ++;
     		}
     	}
     	return res;
     }
     ```
     
  
+ 子数组

  1. 解题技巧：前缀和/前缀积

  2. 处理方式：进行种处理，分段式处理 （能写成进行中就写进行中）

  3. 模板：

     ```java
     for (int i = 1; i < nums.length; i ++ ) {
         nums[i] += nums[i-1]
     }
     ```

     进行中处理：
     
     LeetCode 209: Minimum subarray Sum
     
     Given an array of positive integers `nums` and a positive integer `target`, return the minimal length of a **contiguous subarray** `[numsl, numsl+1, ..., numsr-1, numsr]` of which the sum is greater than or equal to `target`. If there is no such subarray, return `0` instead. (Sliding window)
     
     ```java
     public int minSubArrayLen(int s, int[] nums) {
     	int res = Integer.MAX_VALUE;
     	int left = 0, sum = 0;
     	for (int i = 0; i < nums.length; i ++) {
     		sum += nums[i];
             while(left <= i && sum >= s) {
                 res = Math.min(res, i - left + 1);  // left pointer move right
                 sum -= nums[left++]; // Sliding window
             }
     	}
     	return res == Integer.MAX_VALUE ? 0: res;
     }
     ```
     
     分段式处理：
     
     LeetCode 238: Product of Array Except self:
     
     Given an integer array `nums`, return *an array* `answer` *such that* `answer[i]` *is equal to the product of all the elements of* `nums` *except* `nums[i]`.
     
     The product of any prefix or suffix of `nums` is **guaranteed** to fit in a **32-bit** integer.
     
     You must write an algorithm that runs in `O(n)` time and without using the division operation.
     
     ```java
     public int[] prodect public int[] productExceptSelf(int[] nums) {
             int[]leftmulti = new int [nums.length];
             int[]rightmulti = new int [nums.length];
             int []answer = new int[nums.length];
             int left = 1;
             int right = 1;
             
             //calculate the product for the left side
             for(int i =0; i< nums.length; i++){
                 leftmulti[i] = left;
                 left *= nums[i];
             }
             
             //calculate the product for the right side
             for(int i = nums.length -1; i >= 0; i--){
                 rightmulti[i] = right ;
                 right *= nums[i];
             }
             
             //multiplying both products together left side * right side
             for(int i = 0; i < nums.length; i++){
                 answer[i] = leftmulti[i] * rightmulti[i];
             }
             return answer;
         }
     ```
  
+ 双指针 (Two Pointer)

  + 数组问题大多都可以用双指针解决

  + 模板2： 双指针：

    ```java
    int left = 0;
    int right = nums.length - 1;
    Object res // 一般结果 int list stirng...
    while(left < right) {
    	//处理
    }
    ```

    LeetCode11: Container with most water

    ![img](https://s3-lc-upload.s3.amazonaws.com/uploads/2018/07/17/question_11.jpg)

    ```java
    Input: height = [1,8,6,2,5,4,8,3,7]
    Output: 49
    Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case, the max area of water (blue section) the container can contain is 49.
    ```

    Solution:

    ```java
    public int maxArea(int[] height) {
    	int res = 0;
    	int l = 0; r = height.length - 1;
    	while(l < r) {
    		res = Math.max(res, Math.min(height[l], height[r]) * (r - l));
    		if (height[i] < height[r]) {
    			l ++;
    		} else {
    			r --;
    		}
    	}
    	return res;
    }
    ```

+ 二维数组

  1. 左右翻转

  2. 上下翻转

  3. 对角线翻转（主，副）

     ```java
     // 对角线
     public static void diagonal(int[][] matrix) {
     	for(int i = 0; i < matrix.length; i ++) {
     		for (int j = 0; j < i; j ++) {
     			int temp = matrix[i][j];
     			matrix[i][j] = matrix[j][i];
     			matrix[j][i] = temp;
     		}
     	}
     }
     
     // 反对角线
     public static void counterDiagnal(int[][] matrix) {
         int len = matrix.length;
         for(int i = 0; i < len; i ++) {
     		for (int j = 0; j < len - 1- i; j ++) {
     			int temp = matrix[i][j];
     			matrix[i][j] = matrix[len - 1 -j][len - 1- i];
     			matrix[len - 1- j][len - 1- i] = temp;
     		}
     	}
     }
     
     //水平
     public static void horizontal(int[][] matrix) {
         int len = matrix.length;
         for (int i = 0; i < len; i ++) {
             for(int j = 0; j < len; j ++) {
                 int temp = matrix[i][j];
                 matrix[i][j] = matrix[len - 1- i][j];
                 matrix[len - 1 -i][j] = temp;
             }
         }
     }
     
     // 竖直
     public static void vertical(int[][] matrix) {
         int len = matrix.length;
         for (int i = 0; i < len; i ++) {
             for(int j = 0; j < len; j ++) {
                 int temp = matrix[i][j];
                 matrix[i][j] = matrix[i][len - 1 - j];
                 matrix[i][len - 1 -j] = temp;
             }
         }
     }
     ```
     
     
     
     LeetCode 48: Rotate Image
     
     You are given an `n x n` 2D `matrix` representing an image, rotate the image by **90** degrees (clockwise).
     
     You have to rotate the image [**in-place**](https://en.wikipedia.org/wiki/In-place_algorithm), which means you have to modify the input 2D matrix directly. **DO NOT** allocate another 2D matrix and do the rotation.
     
      ![img](https://assets.leetcode.com/uploads/2020/08/28/mat1.jpg)
     
     ```java
     Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
     Output: [[7,4,1],[8,5,2],[9,6,3]]
     ```
     
     Solution:
     
     ```java
     //先对角再竖直
     public static void diagonal(int[][] matrix) {
         
     }
     
     
     ```
     
     LeadCode54: Spiral matrix (原型题背)
     
     Given an `m x n` `matrix`, return *all elements of the* `matrix` *in spiral order*.
     
     ![img](https://assets.leetcode.com/uploads/2020/11/13/spiral1.jpg)
     
     ```java
     Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
     Output: [1,2,3,6,9,8,7,4,5]
     ```
     
     Solution:
     
     ```java
     Public List<Integer> SpiralOrder(int[][] matrix){
     	List<Integer> res = new ArrayList<>();
     	if (matrix == null || matrix.length ==0 || matrix[0] == null || matrix[0].length == 0) {
             return res;
         }
         int rowBegin = 0;
         int rowEnd = matrix.length -1;
         int colBegin = 0;
         int colEnd = matrix[0].length - 1;
         
         while (rowBegin <= rowEnd && colBegin <= colEnd) {
             for(int i = colBegin; i <= colEnd; i ++) {
                 res.add(matrix[rowBegin][i]);
             }
             rowBegin ++;
             for(int i = rowBegin; i <= rowEnd; i ++) {
                 res.add(matrix[i][colEnd]);
             }
             colEnd--;
             
             if(rowBegin <= rowEnd) {
                 for(int i = colEnd; i >= colBegin; i --) {
                    res.add(matrix[rowEnd][i]);
                 }
             }
             rowEnd --;
             
             if(colBegin <= colEnd){
                 for (int i = rowEnd; i >= rowBegin; i --) {
                     res.add(matrix[i][colBegin]);
                 }
             }
             colBegin++;
         }
         return res;
     }
     ```
  
+ 实现题

  假设你是一个专业的狗仔，参加了一个 n 人派对，其中每个人被从 0 到 n - 1 标号。在这个派对人群当中可能存在一位 “名人”。所谓 “名人” 的定义是：其他所有 n - 1 个人都认识他/她，而他/她并不认识其他任何人。

  现在你想要确认这个 “名人” 是谁，或者确定这里没有 “名人”。而你唯一能做的就是问诸如 “A 你好呀，请问你认不认识 B呀？” 的问题，以确定 A 是否认识 B。你需要在（渐近意义上）尽可能少的问题内来确定这位 “名人” 是谁（或者确定这里没有 “名人”）。

  在本题中，你可以使用辅助函数 bool knows(a, b) 获取到 A 是否认识 B。请你来实现一个函数 int findCelebrity(n)。

  派对最多只会有一个 “名人” 参加。若 “名人” 存在，请返回他/她的编号；若 “名人” 不存在，请返回 -1。
  Solution:

  ```java
  public int findCelebrity(int n) {
  	if (n < 2) return -1;
      int res = 0;
      for(int i = 1; i < n; i ++) { // 如果有的话一定会遍历到名人
          if(knows(res, i)) {
              res = i;  // 名人不认识任何人，之前的名人不是了
          }
      }
      
      // 如果名人不存在
      for(int i = 0; i < n; i ++) {
          if(re != i && ((knows(res, i) || !knows(i, res)))) {
              return -1;  // 名人不认识任何人，任何人都认识名人
          }
      }
      return res;
  }
  ```

+ 数学定理

  Moore Voting Algorithm:

  每次找出一对不同的元素，从数组中删掉，直到数组为空或者只剩下一种元素，如果存在元素e出现频率超过一半，那么数组中最后剩下的就只有e。

  LeetCode169: Majority Element

  Given an array `nums` of size `n`, return *the majority element*.

  The majority element is the element that appears more than `⌊n / 2⌋` times. You may assume that the majority element always exists in the array.

  ```java
  Input: nums = [3,2,3]
  Output: 3
  ```

  Solution：

  ```java
  // 暴力解
  public int majorityElement(int[] nums) {
  	Arrays.sort(nums);
  	return nums[nums.length / 2];
  }
  
  // Moore Voting
  public int majorityElementMoore(int[] nums) {
      int count = 0;
      int res = 0;
      for (int num : nums ) {
          if(count == 0) {
              res = nums;
          }
          if(nums != res) {
              count --;
          } 
      }
  }
  ```

  


## 4. 二分查找

+ 数组 “增删改查“的最优解

  1. 必须采用顺序存储结构
  2. 必须按关键字大小有序排列

+ 注意点：

  ```java
  int mid = (right - left) / 2 + left;
  // left + right 可能会溢出问题（大于Integer.MAX_VALUE）
  ```

+ 递归：time: O(logn), space: O(logn)

  ```java
   public static int binarySearch(int[] nums, int low, int high, int target){
          if (high < low) return -1;
          int mid = low + (high - low)/2;
          if (nums[mid] > target) {
              return binarySearch(nums, low, mid - 1, target);
          } else if (nums[mid] < target) {
              return binarySearch(nums, mid + 1, high, target);
          } else {
              return mid;
          }
      }
  ```

  logN 时间复杂度：1. 二分查找 2. PriorityQueue (Heap)
  
+ 迭代：

  1.  迭代写法 <=, `right < target < left`, `right + 1 =left`.

  ```java
  public static int binarySearch(int[] nums, int target) {
  	int left = 0;
      int right = nums.length - 1;  // [left, right]
      while(left <= right) {        // left = right
          int mid = (right - left) / 2 + left;
          if (nums[mid] > target) { // [left, mid - 1]
              right = mid - 1;
          } else if (nums[mid] < target) {  // [mid + 1, right]
              left = mid + 1;
          } else {
              return mid;
          }
      }
      return  -1;
  }
  ```

  2. 迭代写法 <， `target < left = right`, `left = right`.

     ```java
     public static int binarySearch(int[] nums, int target) {
     	int left = 0;
         int right = nums.length;  	 // [left, right) 开区间
         while(left < right) {        // left = right不执行 
             int mid = (right - left) / 2 + left;
             if (nums[mid] > target) { // [left, mid）
                 right = mid;
             } else if (nums[mid] < target) {  // [mid + 1, right）
                 left = mid + 1;
             } else {
                 return mid;
             }
         }
         return  -1;
     }
     ```

  3. 迭代写法 `left + 1 < right`

     ```java
     public static int binarySearch3(int[] nums, int target) {
     	int left = 0;
         int right = nums.length - 1;  	 // [left, right] 闭区间
         while(left + 1 < right) {        // left 和 right 挨在一起时不执行 
             int mid = (right - left) / 2 + left;
             if (nums[mid] > target) { // [left, mid]
                 right = mid;
             } else if (nums[mid] < target) {  // [mid, right]
                 left = mid;
             } else {
                 return mid;
             }
         }
         if (target == nums[left]) {  // 由于left和right挨在一起时不会执行，需要额外判断停止的条件
             return left;
         } else if (target == nums[right]) {
             return right;
         }
         return  -1;
     }
     ```

     *查看LeetCode 81 和  LeetCode 33*
  
+ 数组中的二分查找

  1. **Search**, **Find** 等字眼，涉及“查”， 必定二分查找

  2. 子数组题型：LeetCode 300 (出现次数多)

  3. 一维  -> 二维，二维查找***最优解***必定是二分法

     ```java
     // Leetcode 74 Search Matrix
     
         /**
          * Write an efficient algorithm that searches for a value target in an m x n integer matrix matrix.
          * This matrix has the following properties:
          * <p>
          * Integers in each row are sorted from left to right.
          * The first integer of each row is greater than the last integer of the previous row.
          * <p>
          * Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
          * Output: true
          * <p>
          * Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
          * Output: false
          */
         class SearchMatrix74 {
             public boolean searchMatrix(int[][] matrix, int target) {
                 int row = matrix.length;
                 int col = matrix[0].length;
                 int start = 0;
                 int end = row * col - 1;
     
                 while (start <= end) {
                     int mid = (end - start) / 2 + start;
                     int value = matrix[mid / col][mid % col];  // 当前这个值处于二维数组中的哪里（背下来）
                     if (value == target) {
                         return true;
                     } else if (value < target) {
                         start = mid + 1;
                     } else {
                         end = mid - 1;
                     }
                 }
                 return false;
             }
         }
     ```


## 5. Linked List

1. 链表单独操作

2. 和各种数据结构结合

3. ArrayList **VS** LinkedList

### 5.1 常考题型 1 —— 基本操作

1. 有无头节点: 头节点变动的时候一定会有`dummy` 节点
2. 循环逻辑：不会去写`for`循环，一般用`while`. 
3. 结束条件

```java
// LeetCode 24:
 /**
     *
     * Given a linked list, swap every two adjacent nodes and return its head. You must solve the problem without
     * modifying the values in the list's nodes (i.e., only nodes themselves may be changed.)
     *
     *Input: head = [1,2,3,4]
     * Output: [2,1,4,3]
     *
     *Input: head = []
     * Output: []
     *
     */

    class Solution24 {
        public ListNode swapPairs(ListNode head) {
            if (head == null || head.next == null) return head;
            ListNode dummy = new ListNode(0);  // 头节点变了，要用dummy
            dummy.next = head;    // dummy ->  2 -> 1 -> 4 -> 3
            ListNode l1 = dummy;  // dummy ->  1 -> 2 -> 3 -> 4
            ListNode l2 = head;   //   ^       ^
                                  //   l1      l2                               //  l1 l2
            while (l2 != null && l2.next != null) {                             //   _______
                ListNode nextStart = l2.next.next;                              //  |       |
                l1.next = l2.next;  // 将dummy的下一个节点设为l2的下一个 画图画出来就理解了 d  1 -> 2 -> 3
                l2.next.next = l2;  // 将l2的下一个接在dummy后面
                l2.next = nextStart;
                l1 = l2;
                l2 = l2.next;
            }
            return dummy.next;
        }
    }
```

LeetCode 238: Odd Even LinkedList:

Given the `head` of a singly linked list, group all the nodes with odd indices together followed by the nodes with even indices, and return *the reordered list*.

The **first** node is considered **odd**, and the **second** node is **even**, and so on.

Note that the relative order inside both the even and odd groups should remain as it was in the input.

You must solve the problem in `O(1)` extra space complexity and `O(n)` time complexity.

```java
class Solution {
    public ListNode oddEvenList(ListNode head) {
         if (head == null || head.next == null) return head;
            ListNode odd = head;
            ListNode even = head.next;
            ListNode evenHead = even;
            while (even != null && even.next != null) {
                odd.next = odd.next.next;
                even.next = even.next.next;
                odd = odd.next;
                even = even.next;
            }
            odd.next = evenHead;
            return head;
    }
}
```

### 5.1 翻转

+ LeetCode 206：
	 Given the head of a singly linked list, reverse the list, and return the reversed list.


  ```java
  class Solution206 {  // 背下来
          public ListNode reverseList(ListNode head) {
              if (head == null || head.next == null) return head;
              ListNode pre = null;
              while (head != null) {
                  ListNode temp = head.next;
                  head.next = pre;
                  pre = head;
                  head = temp;
              }
              return pre;
          }
      }
  ```

+ LeetCode: 92: Reverse LinkedList II

  Given the head of a singly linked list and two integers left and right where `left <= right`, reverse the nodes of the list from position left to position right, and return the reversed list.
  
  ```
  Input: head = [1,2,3,4,5], left = 2, right = 4
  Output: [1,4,3,2,5]
  ```
  
  ```
  Input: head = [5], left = 1, right = 1
  Output: [5]
  ```
  
  Sol:
  
  ```java
  class Solution {
      public ListNode reverseBetween(ListNode head, int m, int n) {
           ListNode dummy = new ListNode(0);
              dummy.next = head;
              ListNode pre = dummy;
              ListNode cur = dummy.next;
              
              for (int i = 1; i < m; i++) {
                  cur = cur.next;
                  pre = pre.next;
              }
              for (int i = 0; i < n - m; i++) {
                  ListNode temp = cur.next;
                  cur.next = temp.next;
                  temp.next = pre.next;
                  pre.next = temp;
              }
              return dummy.next;
      }
  }
  ```

### 5.2 环

+ LeetCode 141: Linked List Cycle

  Given a Linked List, determine if it has a cycle in it.

  ```java
   // 快慢指针解决
      public boolean hasCycle(ListNode head) {
          if (head == null) return false;
          // 快慢两个指针 
          // 如果有环，快指针和慢指针一定会相遇
          ListNode slow = head, fast = head;
          while (fast != null && fast.next != null) {
              slow = slow.next;
              fast = fast.next.next;
              if (slow == fast) return true;
          }
          return false;
      }
  ```

+ LeetCode 142: Linked List Cycle II

  Given the `head` of a linked list, return *the node where the cycle begins. If there is no cycle, return* `null`.

  There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the `next` pointer. Internally, `pos` is used to denote the index of the node that tail's `next` pointer is connected to (**0-indexed**). It is `-1` if there is no cycle. **Note that** `pos` **is not passed as a parameter**.

  **Do not modify** the linked list.

  ```
  Input: head = [3,2,0,-4], pos = 1
  Output: tail connects to node index 1
  Explanation: There is a cycle in the linked list, where tail connects to the second node.
  ```

  ```
  Input: head = [1,2], pos = 0
  Output: tail connects to node index 0
  Explanation: There is a cycle in the linked list, where tail connects to the first node.
  ```

  Sol:

  通过快慢指针可以判断一个链表是否有环。如果有环，那么**快指** 

  **针走过的路径就是图中a+b+c+b**，**慢指针走过的路径就是图中a+b**，因为在相同的时 间内，快指针走过的路径是慢指针的2倍，所以这里有`a+b+c+b=2* (a+b)`，整理得到 `a=c`，也就是说图中a的路径长度和c的路径长度是一样的。 

  ![LinkedListCycle](C:\zwh\Documents\myBlog\LinkedListCycle.png)

  在相遇的时候再使用两个指针，一个从链表起始点开始，一个从相遇点开始，每次他们 都走一步，直到再次相遇，那么这个相遇点就是环的入口。

  ```java
  public class Solution {
      public ListNode detectCycle(ListNode head) {
           if (head == null || head.next == null) return null;
              ListNode slow = head;
              ListNode fast = head;
  
              while (fast != null && fast.next != null) {
                  slow = slow.next;
                  fast = fast.next.next;
                  if (fast == slow) {
                      ListNode slow2 = head;
                      while (slow != slow2) {
                          slow = slow.next;  // 第二个指针从源头开始走直到和慢指针相遇
                          slow2 = slow2.next;
                      }
                      return slow;
                  }
              }
              return null;
      }
  }
  ```


### 5.5 删除

+ LeetCode 237: Delete Node in a Linked List

  Write a function to **delete a node** in a singly-linked list. You will **not** be given access to the `head` of the list, instead you will be given access to **the node to be deleted** directly.

  It is **guaranteed** that the node to be deleted is **not a tail node** in the list.

  ```
  Input: head = [4,5,1,9], node = 5
  Output: [4,1,9]
  Explanation: You are given the second node with value 5, the linked list should become 4 -> 1 -> 9 after calling your function.
  ```

  ```
  Input: head = [4,5,1,9], node = 1
  Output: [4,5,9]
  Explanation: You are given the third node with value 1, the linked list should become 4 -> 5 -> 9 after calling your function.
  ```

  Sol:

  ```java
  class Solution {
      public void deleteNode(ListNode node) {
            if (node == null) return;
              node.val = node.next.val;
              node.next = node.next.next;
      }
  }
  ```

+ LeetCode 83: Remove Duplicates from Sorted List

  Given the `head` of a sorted linked list, *delete all duplicates such that each element appears only once*. Return *the linked list **sorted** as well*.

  ```
  Input: head = [1,1,2]
  Output: [1,2]
  ```

  ```
  Input: head = [1,1,2,3,3]
  Output: [1,2,3]
  ```

  Sol:

  ```java
  class Solution {
      public ListNode deleteDuplicates(ListNode head) {
          
      }
  }
  ```

  
