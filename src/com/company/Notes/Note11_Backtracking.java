package com.company.Notes;

import jdk.jshell.execution.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
     * <p>
     * The same number may be chosen from candidates an unlimited number of times. Two combinations are unique
     * lif the frequency of at least one of the chosen numbers is different.
     * <p>
     * It is guaranteed that the number of unique combinations that sum up to target is less than 150 combinations for
     * the given input.
     * <p>
     * Input: candidates = [2,3,6,7], target = 7
     * Output: [[2,2,3],[7]]
     * Explanation:
     * 2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times.
     * 7 is a candidate, and 7 = 7.
     * These are the only two combinations.
     * <p>
     * Input: candidates = [2,3,5], target = 8
     * Output: [[2,2,2,2],[2,3,3],[3,5]]
     */

    /*

    从1-9中选出k个数字，他们的和等于n，并且这k个数字不能有重复的。所以第一次选择的时候可以从这9个数字中任选一个，沿着这个分支走下去，
    第二次选择的时候还可以从这9个数字中任选一个，但因为不能有重复的，所以要排除第一次选择的，第二次选择的时候只能从剩下的8个数字中选一个……。
    这个选择的过程就比较明朗了，我们可以把它看做一棵9叉树，除叶子结点外每个节点都有9个子节点

     */

    class Solution39 {
        public List<List<Integer>> combinationSum(int k, int n) {
            List<List<Integer>> res = new ArrayList<>();
            dfs(res, new ArrayList<>(), k, 1, n);
            return res;
        }

        private void dfs(List<List<Integer>> res, List<Integer> list, int k, int start, int n) {
            // 终止条件
            if (list.size() == k || n <= 0) {
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
     * <p>
     * Given an integer n, return all distinct solutions to the n-queens puzzle. You may return the answer in any order.
     * <p>
     * Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a queen and an empty space, respectively.
     * <p>
     * <p>
     * Input: n = 4
     * Output: [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
     * Explanation: There exist two distinct solutions to the 4-queens puzzle as shown above
     * <p>
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

    class Solution51 {
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
            for (int col = 0; col < chess.length; col++) {
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
            for (int i = row - 1, j = col + 1; i >= 0 && j < chess.length; i--, j++) {
                if (chess[i][j] == 'Q') return false;
            }
            // 判断当前坐标的左上角有没有皇后
            for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
                if (chess[i][j] == 'Q') return false;
            }
            return true;
        }
    }

    // 46: Permutation

    /**
     * Given an array nums of distinct integers, return all the possible permutations. You can return the answer in any order.
     * <p>
     * Input: nums = [1,2,3]
     * Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
     * <p>
     * Input: nums = [0,1]
     * Output: [[0,1],[1,0]]
     * <p>
     * Input: nums = [1]
     * Output: [[1]]
     * <p>
     * time: O(n! * n * n) 结果是n!, 每个数组的内部都是n, contains还有一个n
     * space: O(n! * n)
     */
    class Solution {
        public List<List<Integer>> permute(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            if (nums == null || nums.length == 0) return res;
            backtrack(res, new ArrayList<>(), nums);
            return res;
        }

        private void backtrack(List<List<Integer>> list, List<Integer> temp, int[] nums) {
            // 终止条件，如果数字被用完了，说明找到了一个排列（可以把它看成n叉树到叶子节点了，不能往下走了，要返回）
            if (temp.size() == nums.length) {
                list.add(new ArrayList<>(temp));
                return;
            }
            // 遍历n叉树的每个节点的子节点
            for (int i = 0; i < nums.length; i++) {
                // 不能有重复的，有就要跳过
                if (temp.contains(nums[i])) continue;
                temp.add(nums[i]);
                backtrack(list, temp, nums);
                // 撤销选择，把最后一次添加的删掉
                temp.remove(temp.size() - 1);
            }
        }

        // 78 Subsets

        /**
         * Given an integer array nums of unique elements, return all possible subsets (the power set).
         * <p>
         * The solution set must not contain duplicate subsets. Return the solution in any order.
         * <p>
         * <p>
         * Input: nums = [1,2,3]
         * Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
         * Time: O (n * 2^n)
         * Space O (n * 2 ^ n)
         */

        class Solution78 {
            public List<List<Integer>> subsets(int[] nums) {
                List<List<Integer>> res = new ArrayList<>();
                if (nums == null || nums.length == 0) return res;
                subsetsHelper(res, new ArrayList<>(), nums, 0);
                return res;
            }

            private void subsetsHelper(List<List<Integer>> res, List<Integer> list, int[] nums, int index) {
                res.add(new ArrayList<>(list));
                for (int i = index; i < nums.length; i++) {
                    list.add(nums[i]);
                    subsetsHelper(res, list, nums, i + 1);
                    list.remove(list.size() - 1);
                }
            }
        }
    }

    // 90: SubsetsII

    /**
     * Given an integer array nums that may contain duplicates, return all possible subsets (the power set).
     * <p>
     * The solution set must not contain duplicate subsets. Return the solution in any order.
     * <p>
     * Input: nums = [1,2,2]
     * Output: [[],[1],[1,2],[1,2,2],[2],[2,2]]
     */
    class solution90 {
        public List<List<Integer>> subsetsWithDup(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            if (nums == null || nums.length == 0) return res;
            Arrays.sort(nums);
            helper(nums, res, new ArrayList<>(), 0);
            return res;
        }

        private void helper(int[] nums, List<List<Integer>> res, List<Integer> list, int start) {
            res.add(new ArrayList<>(list));
            for (int i = start; i < nums.length; i++) {
                if (i != start && (nums[i] == nums[i - 1])) continue;  // 去重
                list.add(nums[i]);
                helper(nums, res, list, i + 1);
                list.remove(list.size() - 1);
            }
        }

    }

    // 77: Combinations

    /**
     * Given two integers n and k, return all possible combinations of k numbers out of the range [1, n].
     * <p>
     * You may return the answer in any order.
     * <p>
     * Input: n = 4, k = 2
     * Output:
     * [
     * [2,4],
     * [3,4],
     * [2,3],
     * [1,2],
     * [1,3],
     * [1,4],
     * ]
     * <p>
     * time: O(k * C(n, k))
     */

    class Solution77 {
        public List<List<Integer>> combine(int n, int k) {
            List<List<Integer>> res = new ArrayList<>();
            helper(res, new ArrayList<>(), n, k, 1);
            return res;
        }

        private void helper(List<List<Integer>> res, List<Integer> list, int n, int k, int index) {
            if (k == 0) {
                res.add(new ArrayList<>(list));
                return;
            }
            for (int i = index; i <= n; i++) {
                list.add(i);
                helper(res, list, n, k - 1, i + 1);
                list.remove(list.size() - 1);
            }
        }

        // 17

        /**
         * Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number
         * could represent. Return the answer in any order.
         * <p>
         * A mapping of digits to letters (just like on the telephone buttons) is given below. Note that 1 does not
         * <p>
         * map to any letters.
         * <p>
         * Input: digits = "23"
         * Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
         */
        class solution17 {
            String[] mapping = new String[]{"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

            public List<String> letterCombinations(String digits) {
                List<String> res = new ArrayList<>();
                if (digits == null || digits.length() == 0) return res;
                helper(res, "", digits, 0);
                return res;
            }

            public void helper(List<String> res, String s, String digits, int index) {
                if (s.length() == digits.length()) {
                    res.add(s);
                    return;
                }
                String letters = mapping[digits.charAt(index) - '0'];
                for (int i = 0; i < letters.length(); i++) {
                    helper(res, digits, s + letters.charAt(i), index + 1);
                }
            }
        }
    }

    // 47 Permutation II

    /**
     * 给定一个可包含重复数字的序列nums，按任意顺序返回所有不重复的全排列。
     * <p>
     * 输入：n u m s = [ 1 , 1 , 2 ] 输出： [ [ 1 , 1 , 2 ] , [ 1 , 2 , 1 ] , [ 2 , 1 , 1 ] ]
     */

    class solution47 {
        public List<List<Integer>> permuteUnique(int[] nums) {
            // 先对数组进行排序，这样重复的数字会排在一起，方便过滤掉重复的字母
            Arrays.sort(nums);
            List<List<Integer>> res = new ArrayList<>();
            boolean[] used = new boolean[nums.length];  // user[i]表示该数被用过没用
            if (nums == null || nums.length == 0) return res;
            helper(res, new ArrayList<>(), nums, used);
            return res;
        }

        private void helper(List<List<Integer>> res, List<Integer> list, int[] nums, boolean[] used) {
            if (list.size() == nums.length) {
                res.add(new ArrayList<>(list));
                return;
            }
            for (int i = 0; i < nums.length; i++) {
                // 如果当前字母已经被用过，则跳过
                if (used[i]) continue;
                //注意，这里要剪掉重复的组合
                //如果当前元素和前一个一样，并且前一个没有被使用过，我们也跳过
                if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue;
                used[i] = true;  //否则我们就使用当前元素，把他标记为已使用
                list.add(nums[i]);
                helper(res, list, nums, used);
                //递归完之后会往回走，往回走的时候要撤销选择
                used[i] = false;
                list.remove(list.size() - 1);
            }
        }
    }

    // 473: Matchsticks to Square (回溯法拼正方形)

    /**
     * You are given an integer array matchsticks where matchsticks[i] is the length of the ith matchstick.
     * You want to use all the matchsticks to make one square. You should not break any stick, but you can link them up,
     * and each matchstick must be used exactly one time.
     * <p>
     * Return true if you can make this square and false otherwise.
     * <p>
     * Input: matchsticks = [1,1,2,2,2]
     * Output: true
     * Explanation: You can form a square with length 2, one side of the square came two sticks with length 1.
     * <p>
     * Input: matchsticks = [3,3,3,3,4]
     * Output: false
     * Explanation: You cannot find a way to form a square with all the matchsticks.
     */
    class Solution473 {
        public boolean makesquare(int[] matchsticks) {
            // 如果数组的和不是4的倍数，直接返回false
            int sum = 0;
            for (int i : matchsticks) {
                sum += i;
            }
            if (sum % 4 != 0 || sum == 0) return false;
            return helper(matchsticks, sum / 4, matchsticks.length - 1, new int[4]);
        }

        // index 表示访问当前火柴的位置，target表示正方形的边长，size是长度为4 的数组
        private boolean helper(int[] nums, int target, int index, int[] size) {
            if (index < 0) {
                // 如果火柴都访问完了，并且正方形的4个边长度相等返回true
                int length = size[0];
                for (int i = 1; i < size.length; i++) {
                    if (size[i] != length) return false;
                }
                return true;
            }
            // 火柴还没有遍历完，遍历一个4叉数
            for (int i = 0; i < size.length; i++) {
                // 如果把当前的火柴放在边size[i]上，它的长度大于了target,直接跳过，剪枝。或者size[i]==size[i-1]上一个分支和当前的分支一样
                // 上一个没有成功，这个也跳过
                if (size[i] + nums[index] > target || (i > 0 && size[i] == size[i - 1])) continue;
                // 当前火柴放在size[i]这个边上
                size[i] += nums[index];
                helper(nums, target, index - 1, size);
                size[i] -= nums[index];
            }
            return false;
        }
    }

    // 698 划分为k个相等的子集

    /**
     * 给定一个整数数组nums和一个正整数k，找出是否有可能把这个数组分成k 个非空子集，其 总和都相等。
     * 输入 nums=[4,3,2,3,5,2,1], k = 4输出：True说明：有可能将其分成4个子集(5)，(1,4)，(2,3)，(2,3)等于总和。
     */
    class Solution698 {
        public boolean canPartitionKSubsets(int[] nums, int k) {
            int sum = 0;
            for (int i : nums) sum += i;
            if (sum % k != 0 || sum == 0) return false;
            Arrays.sort(nums);
            return helper(nums, sum / k, new int[k], nums.length - 1);
        }

        private boolean helper(int[] nums, int target, int[] size, int index) {
            if (index == -1) {
                for (int i = 1; i < size.length; i++) {
                    if (size[i] != size[i - 1]) return false;
                }
                return true;
            }
            for (int i = 0; i < size.length; i++) {
                if (size[i] + nums[index] > target || (i > 0 && size[i] == size[i - 1])) continue;
                size[i] += nums[index];
                if (helper(nums, target, size, index - 1)) return true;
                size[i] -= nums[index];
            }
            return false;
        }
    }

    // 131： Palindrome partition

    /**
     * Given a string s, partition s such that every substring of the partition is a palindrome. Return all possible
     * palindrome partitioning of s.
     * <p>
     * A palindrome string is a string that reads the same backward as forward.
     * <p>
     * Input: s = "aab"
     * Output: [["a","a","b"],["aa","b"]]
     * <p>
     * Input: s = "a"
     * Output: [["a"]]
     */

    class solution131 {
        public List<List<String>> partition(String s) {
            // 最终要返回的结果
            List<List<String>> res = new ArrayList<>();
            if (s == null || s.length() == 0) return res;
            helper(s, res, new ArrayList<>(), 0);
            return res;
        }

        /**
         * @param s     input
         * @param res   result
         * @param temp  当前找的字符串
         * @param index 字符串截取的位置
         */

        private void helper(String s, List<List<String>> res, List<String> temp, int index) {
            // 如果字符串s 中的字符都访问完了，（类似于找到叶子节点了），就停止找，将分支结果加入res
            if (index >= s.length()) {
                res.add(new ArrayList<>(temp));
                return;
            }
            // 遍历节点
            for (int i = index; i < s.length(); i++) {
                if (!isPalindrome(s.substring(index, i + 1))) continue;  // 如果当前截取字符串不是回文就剪枝
                temp.add(s.substring(index, i + 1));  // 是回文就加入temp
                helper(s, res, temp, i + 1);  // 递归到下一层到n叉树的子节点遍历
                temp.remove(temp.size() - 1);  //撤销选择，就是把之前截取放到集合cur中的子串给移除掉
            }
        }

        private boolean isPalindrome(String s) {
            int left = 0, right = s.length() - 1;
            while (left < right) {
                if (s.charAt(left++) != s.charAt(right--)) return false;
            }
            return true;
        }
    }

    // 39: Combination Sum

    /**
     * Given an array of distinct integers candidates and a target integer target, return a list of all unique
     * combinations of candidates where the chosen numbers sum to target. You may return the combinations in any order.
     * <p>
     * The same number may be chosen from candidates an unlimited number of times. Two combinations are unique if
     * the frequency of at least one of the chosen numbers is different.
     * <p>
     * It is guaranteed that the number of unique combinations that sum up to target is less than 150
     * combinations for the given input.
     * <p>
     * Input: candidates = [2,3,6,7], target = 7
     * Output: [[2,2,3],[7]]
     * Explanation:
     * 2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times.
     * 7 is a candidate, and 7 = 7.
     * These are the only two combinations.
     * <p>
     * Input: candidates = [2,3,5], target = 8
     * Output: [[2,2,2,2],[2,3,3],[3,5]]
     * <p>
     * Input: candidates = [2], target = 1
     * Output: []
     */
    class solution39 {
        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            List<List<Integer>> res = new ArrayList<>();
            if (candidates == null || candidates.length == 0) return res;
            helper(res, new ArrayList<>(), candidates, target, 0);
            return res;
        }

        private void helper(List<List<Integer>> res, List<Integer> list, int[] nums, int target, int start) {
            if (target < 0) return;
            if (target == 0) res.add(new ArrayList<>(list));
            for (int i = start; i < nums.length; i++) {
                list.add(nums[i]);
                helper(res, list, nums, target - nums[i], i);
                list.remove(list.size() - 1);
            }
        }
    }

    // 40: Combination Sum II

    /**
     * Given a collection of candidate numbers (candidates) and a target number (target), find all
     * unique combinations in candidates where the candidate numbers sum to target.
     * <p>
     * Each number in candidates may only be used once in the combination.
     * <p>
     * Note: The solution set must not contain duplicate combinations.
     * <p>
     * Input: candidates = [10,1,2,7,6,1,5], target = 8
     * Output:
     * [
     * [1,1,6],
     * [1,2,5],
     * [1,7],
     * [2,6]
     * ]
     * <p>
     * Input: candidates = [2,5,2,1,2], target = 5
     * Output:
     * [
     * [1,2,2],
     * [5]
     * ]
     */
    class Solution40 {
        public List<List<Integer>> combinationSum2(int[] candidates, int target) {
            List<List<Integer>> res = new ArrayList<>();
            if (candidates == null || candidates.length == 0) return res;
            Arrays.sort(candidates);
            helper(res, new ArrayList<>(), target, candidates, 0);
            return res;
        }

        private void helper(List<List<Integer>> res, List<Integer> list, int target, int[] nums, int index) {
            if (target < 0) return;  // 如果target小于0了说明这个分支没有找到
            if (target == 0) res.add(new ArrayList<>(list));  // 等于0了就说明找到了
            for (int i = index; i < nums.length; i++) {
                if (i != index && (nums[i] == nums[i - 1])) continue;
                list.add(nums[i]);
                helper(res, list, target - nums[i], nums, i + 1); // i + 1： 跳过已经选过的那个元素
                list.remove(list.size() - 1);
            }
        }
    }

    // 377 Combination4

    /**
     * Given an array of distinct integers nums and a target integer target, return the number of possible combinations
     * that add up to target.
     * <p>
     * The test cases are generated so that the answer can fit in a 32-bit integer.
     * <p>
     * <p>
     * Input: nums = [1,2,3], target = 4
     * Output: 7
     * Explanation:
     * The possible combination ways are:
     * (1, 1, 1, 1)
     * (1, 1, 2)
     * (1, 2, 1)
     * (1, 3)
     * (2, 1, 1)
     * (2, 2)
     * (3, 1)
     * Note that different sequences are counted as different combinations.
     */
    class solution377 {
        public int combinationSum4(int[] nums, int target) {
            int res = 0;
            if (nums.length == 0) return res;
            HashMap<Integer, Integer> map = new HashMap<>();
            return helper(nums, target, map);
        }

        /**
         * nums = [1, 2, 3]
         * target = 4
         *
         * i = 0 res = helper(3)
         *      i = 0 res = helper(2)
         *          i = 0 res = helper(1)
         *          i = 1 nums = 2 res = helper(0) = 1
         *          map.put(2,2)
         *              map.put(1,1)
         *              i = 0 res = helper(0)
         *              map.put(0,1)  //  中间的res进行记录
         *
         * @param nums
         * @param target
         * @param map
         * @return
         */

        private int helper(int[] nums, int target, HashMap<Integer, Integer> map) {
            if (target == 0) return 1;
            if (target < 0) return 0;
            if (map.containsKey(target)) return map.get(target);
            int res = 0;
            for (int i = 0; i < nums.length; i++) res += helper(nums, target - nums[i], map);
            map.put(target, res);
            return res;
        }
    }

    // 254: 分解质因数

    class solution254{
        public List<List<Integer>> getFactors(int n) {
            List<List<Integer>> res = new ArrayList<>();
            helper(res, new ArrayList<>(), n, 1);
            return res;
        }

        private void helper(List<List<Integer>> res, List<Integer> list, int n, int start) {
            if ( n == 1) {
                if (list.size() > 1) {
                    res.add(new ArrayList<>(list));
                    return;
                }
            }
            for (int i = start; i <= n; i++) {
                if (n % i == 0) {
                    list.add(i);
                    helper(res, list, n / i, i);
                    list.remove(list.size() - 1);
                }
            }

        }


    }


    public static void main(String[] args) {
        String s = "123456";
        System.out.println(s.substring(1, 3));
    }
}

