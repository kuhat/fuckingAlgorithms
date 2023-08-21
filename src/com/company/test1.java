package com.company;
import java.util.*;

/**
 * @projectName: algorithms
 * @package: com.company
 * @className: test1
 * @author: Danny
 * @description: TODO
 * @date: 2023/8/9 16:55
 * @version: 1.0
 */
public class test1 {

    public static int f(int[] arr) {
        int n = arr.length;
        if (n == 0) return 0;
        int count = 1;
        for (int i = 1; i < n; i ++) {
            if (arr[i] != arr[ i - 1]) {
                count ++;
            }
        }
        return count;
    }

    public static int maxSum(int[] arr, int k) {
        int n = arr.length;
        int total = 0;
        int left = 0;
        for (int i = 0; i < k - 1; i ++) {
            int right = left;
            while (right + 1< n && arr[right] == arr[right + 1]) {
                right ++;
            }
            total += f(Arrays.copyOfRange(arr,left, right + 1));
            left = right + 1;
        }
        total += f(Arrays.copyOfRange(arr, left, n));
        return total;
    }

    public static int maxUniqueSum(int[] sequence, int k) {
        List<Integer> uniqueList = new ArrayList<>();
        for (int i = 0; i < sequence.length; i ++) {
            if (i == 0 || sequence[i] != sequence[i - 1]) {
                uniqueList.add(sequence[i]);
            }
        }
        if ( k == 1) return uniqueList.size();
        int maxSum = 0;
        for (int i = 1; i < uniqueList.size(); i ++) {
            if (uniqueList.get(i).equals(uniqueList.get(i - 1))) {
                continue;
            }

            int firstPart = i;
            int secondPart = uniqueList.size() - i;
            secondPart += maxUniqueSum(toArray(uniqueList.subList(i, uniqueList.size())), k - 1) - (uniqueList.size() - i);
            maxSum = Math.max(maxSum, firstPart + secondPart);
        }
        return maxSum;
    }

    public static int[] toArray(List<Integer> list) {
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i ++) {
            res[i] = list.get(i);
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner sc  = new Scanner(System.in);
        String[] line1 = sc.nextLine().split(" ");
        String[] line2 = sc.nextLine().split(" ");
        int n = Integer.valueOf(line1[0]);
        int k = Integer.valueOf(line1[1]);
        int[] input = new int[n];
        for (int i = 0; i < n; i ++) {
            input[i] = Integer.valueOf(line2[i]);
        }
        System.out.println(maxUniqueSum(input, k));
    }

}
