package com.company.Notes;

import jdk.jshell.execution.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Note11_Backtracking {
    // LeetCode 869: Reorder of power 2

    /**
     * You are given an integer n. We reorder the digits in any order (including the original order) such that the leading digit is not zero.
     * <p>
     * Return true if and only if we can do this so that the resulting number is a power of two.
     * <p>
     * Input: n = 1
     * Output: true
     * <p>
     * Input: n = 10
     * Output: false
     */
    /*
    此题用回溯算法查找每一个数字的组合
    回溯算法的模板：
    private void backtrack("原始参数") {
        //终止条件(递归必须要有终止条件)
        if ("终止条件") {
            //一些逻辑操作（可有可无，视情况而定）
            return;
        }
        for (int i = "for循环开始的参数"; i < "for循环结束的参数"; i++) {
        //一些逻辑操作（可有可无，视情况而定）
        //做出选择
        //递归
        backtrack("新的参数");
        //一些逻辑操作（可有可无，视情况而定）
        //撤销选择
        }
    }
    */
    class Solution869 {
        public boolean reorderedPowerOf2(int n) {
            // 数字转化为字符数组
            char[] numChar = String.valueOf(n).toCharArray();
            // 先对数组排序
            Arrays.sort(numChar);
            return backtrack(numChar, new boolean[numChar.length], 0, 0);
        }

        public boolean backtrack(char[] numChar, boolean[] visit, int index, int num) {
            // 所有数组都访问完了，判断num是否2的次幂
            if (index == numChar.length) {
                return isPowerOfTwo(num);
            }
            for (int i = 0; i < numChar.length; i++) {
                // 有前导0的跳过
                if (num == 0 && numChar[i] == '0') {
                    continue;
                }
                // 被访问过的字符直接跳过
                if (visit[i]) continue;
                // 剪枝，可以参考（47，全排列ii），选择同一个元素再前一个分支没有成功，那么
                // 再当前分支也不会成功，所以可以直接过滤掉
                if (i > 0 && numChar[i - 1] == numChar[i] && visit[i - 1]) continue;
                visit[i] = true;
                if (backtrack(numChar, visit, index + 1, num * 10 + numChar[i] - '0')) return true;
                // 如果没有成功
                visit[i] = false;
            }
            return false;
        }

        //
        public boolean isPowerOfTwo(int n) {
            return (n & (n - 1)) == 0;  // 二进制判断是否为2的次幂
        }

        //  排序比较：
        /*
        先把数字转化为数组比如numChar，然后再把int范围内所有2 的幂也转化为数组，然后判断numChar和2的幂转化的数组是否一样。
         */
        public boolean reorderedPowerOf21(int n) {
            //先把数字转化为字符
            char[] numChar = String.valueOf(n).toCharArray();
            //对字符进行排序
            Arrays.sort(numChar);
            for (int i = 0; i < 31; i++) {
                //把2的幂转化为字符，然后排序
                char[] bitChar = String.valueOf(1 << i).toCharArray();
                Arrays.sort(bitChar);
                //比较这两个数组
                if (Arrays.equals(numChar, bitChar)) return true;
            }
            return false;
        }
    }

    // LeetCode 282: Expression Add Operators

    /**
     * Given a string num that contains only digits and an integer target, return all possibilities to insert
     * the binary operators '+', '-', and/or '*' between the digits of num so that the resultant expression
     * evaluates to the target value.
     * <p>
     * Note that operands in the returned expressions should not contain leading zeros.
     * <p>
     * Input: num = "123", target = 6
     * Output: ["1*2*3","1+2+3"]
     * Explanation: Both "1*2*3" and "1+2+3" evaluate to 6.
     * <p>
     * Input: num = "232", target = 8
     * Output: ["2*3+2","2+3*2"]
     * Explanation: Both "2*3+2" and "2+3*2" evaluate to 8.
     * <p>
     * Input: num = "3456237490", target = 9191
     * Output: []
     * Explanation: There are no expressions that can be created from "3456237490" to evaluate to 9191.
     */
    class Solution282 {
        public List<String> addOperators(String num, int target) {
            List<String> res = new ArrayList<>();
            dfs(res, num, target, 0, 0, 0, "");
            return res;
        }

        /**
         * @param res    返回的结果
         * @param num    字符串num
         * @param target 目标值target
         * @param index  访问到字符串的第几个字符
         * @param preNum 前面的连续乘积（乘法的时候会用到）
         * @param sum    表达式前面计算得到的和
         * @param path   算术表达式，可以看做n叉树的路径
         */
        private void dfs(List<String> res, String num, int target, int index, long preNum, long sum, String path) {
            //字符串num中所有元素都遍历完了，要指针遍历
            if (index >= num.length()) {
                //如果表达式的值等于target，说明找到了一个合适的表达式，
                //就把他加入到集合res中
                if (sum == target) {
                    res.add(path);
                }
                return;
            }

            for (int i = index; i < num.length(); i++) {
                //类似于05，07这样以0开头的数字要过滤掉
                if (i != index && num.charAt(index) == '0')
                    break;
                //截取字符串，转化为数字
                long number = Long.parseLong(num.substring(index, i + 1));
                if (index == 0) {
                    //因为第一个数字前面是没有符号的，所以要单独处理
                    dfs(res, num, target, i + 1, number, number, "" + number);
                } else {
                    //在当前数字number前面可以添加"+","-","*"三种符号。
                    //数字number前添加"+"
                    dfs(res, num, target, i + 1, number, sum + number, path + "+" + number);
                    //数字number前添加"-"
                    dfs(res, num, target, i + 1, -number, sum - number, path + "-" + number);
                    //数字number前添加"*"
                    dfs(res, num, target, i + 1, preNum * number, sum + preNum * number - preNum, path + "*" + number);
                }
            }
        }
    }

    // LeetCode 39: Combination Sum
    /**
     * Given an array of distinct integers candidates and a target integer target, return a list of all unique
     * combinations of candidates where the chosen numbers sum to target. You may return the combinations in any order.
     *
     * The same number may be chosen from candidates an unlimited number of times. Two combinations are unique
     * lif the frequency of at least one of the chosen numbers is different.
     *
     * It is guaranteed that the number of unique combinations that sum up to target is less than 150 combinations for
     * the given input.
     *
     * Input: candidates = [2,3,6,7], target = 7
     * Output: [[2,2,3],[7]]
     * Explanation:
     * 2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times.
     * 7 is a candidate, and 7 = 7.
     * These are the only two combinations.
     *
     * Input: candidates = [2,3,5], target = 8
     * Output: [[2,2,2,2],[2,3,3],[3,5]]
     *
     */

    /*

    从1-9中选出k个数字，他们的和等于n，并且这k个数字不能有重复的。所以第一次选择的时候可以从这9个数字中任选一个，沿着这个分支走下去，
    第二次选择的时候还可以从这9个数字中任选一个，但因为不能有重复的，所以要排除第一次选择的，第二次选择的时候只能从剩下的8个数字中选一个……。
    这个选择的过程就比较明朗了，我们可以把它看做一棵9叉树，除叶子结点外每个节点都有9个子节点

     */

    class Solution39{
        public List<List<Integer>> combinationSum(int k, int n) {
            List<List<Integer>> res = new ArrayList<>();
            dfs(res, new ArrayList<>(), k, 1, n);
            return res;
        }

        private void dfs(List<List<Integer>> res, List<Integer> list, int k, int start, int n) {
            // 终止条件
            if (list.size() == k || n <= 0){
                // 如果找到一组合适的就把它加入到list中
                if (list.size() == k && n == 0) res.add(new ArrayList<>(list));
                return;
            }
            // 通过循环，遍历9个子树
            for (int i = start; i <= 9; i++) {
                // 选择当前值
                list.add(i);
                // 递归
                dfs(res, list, k, i + 1, n - i);
                // 撤销选择
                list.remove(list.size() - 1);
            }
        }
    }

    // LeetCode 51: N Queens problem
    /**
     * The n-queens puzzle is the problem of placing n queens on an n x n chessboard such that no two queens attack each other.
     *
     * Given an integer n, return all distinct solutions to the n-queens puzzle. You may return the answer in any order.
     *
     * Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a queen and an empty space, respectively.
     *
     *
     * Input: n = 4
     * Output: [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
     * Explanation: There exist two distinct solutions to the 4-queens puzzle as shown above
     *
     * Input: n = 1
     * Output: [["Q"]]
     */

    /*

    先从第1行的第1列开始往下找，然后再从第1行的第2列……，一直到第1行的第n列。就是一个n叉树的遍历
    轮廓代码：
    private void solve (char[][] chess, int row) {
        "终止条件"
        return;
        for (int col = 0; col < chess.length; col++) {
            // 判断这个位置可不可以放皇后
            if (valid(chess, row, col)) {
                char[][] temp = copy(chess);
                // 把这个位置放上皇后
                temp[col][row] = 'Q';
                // 解决下一行
                solve(temp, row + 1);
            }
        }
    }
    这里的终止条件就是最后一行走完了，也就是
    if (row == chess.length) return;
    第7行就是判断在这个位置我们能不能放皇后，如果不能放我们就判断这一行的下一列能不能放……，
    如果能放我们就把原来数组chess复制一份，然后把皇后放到这个位置，然后再判断下一行，
    注意这里的第9行为什么要复制一份，因为数组是引用传递，这涉及到递归的时候分支污染问题
    当然不复制一份也是可以的. 当我们把上面的问题都搞懂的时候，代码也就很容易写出来了，

    */

    class Solution51{
        public List<List<String>> solveNQueens(int n) {
            char[][] chess = new char[n][n];
            List<List<String>> res = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                Arrays.fill(chess[i], '.');
            }
            solve(res, chess, 0);
            return res;
        }

        private void solve(List<List<String>> res, char[][] chess, int row) {
            if (row == chess.length) {
                res.add(construct(chess));
                return;
            }
            for (int col = 0; col < chess.length; col ++) {
                if (valid(chess, row, col)) {
                    char[][] temp = copy(chess);
                    temp[row][col] = 'Q';
                    solve(res, temp, row + 1);
                }
            }
        }

        private List<String> construct(char[][] chess) {
            List<String> res = new ArrayList<>();
            for (int i = 0; i < chess.length; i++) {
                String s = new String(chess[i]);
                res.add(s);
            }
            return res;
        }

        // 把二维数组chess中的数据copy一份
        private char[][] copy(char[][] chess) {
            char[][] temp = new char[chess.length][chess[0].length];
            for (int i = 0; i < chess.length; i++) {
                for (int j = 0; j < chess[0].length; j++) {
                    temp[i][j] = chess[i][j];
                }
            }
            return temp;
        }

        private boolean valid(char[][] chess, int row, int col) {
            // 判断当前列有没有皇后，因为这是一行一行往下走的，我们值需要检测走过的行数即可。
            // 通俗一点就是判断当前坐标位置的上面有没有皇后
            for (int i = 0; i < row; i++) {
                if (chess[i][col] == 'Q') return false;
            }
            // 判断当前坐标的右上交有没有皇后
            for (int i = row - 1, j = col + 1; i >= 0 && j < chess.length ; i--, j ++) {
                if (chess[i][j] == 'Q') return false;
            }
            // 判断当前坐标的左上角有没有皇后
            for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i --, j--) {
                if (chess[i][j] == 'Q') return false;
            }
            return true;
        }
    }

    public static void main(String[] args) {
        List<Boolean> list = new ArrayList<>(Arrays.asList(new Boolean[5]));
        System.out.println(list);
    }
}

