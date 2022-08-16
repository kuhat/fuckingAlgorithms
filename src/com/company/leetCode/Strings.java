package com.company.leetCode;

import org.junit.Test;

import java.util.*;

public class Strings {

    /**
     * 1.
     * 只出现一次的数字：
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     * 输入: [2,2,1]
     * 输出: 1
     */
    @Test
    public void numOnce(int[] nums) {
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            result ^= nums[i];  // 异或运算符
        }
        System.out.println(result);
    }

    /**
     * 2.
     * 多数元素：
     * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
     * 输入：[2,2,1,1,1,2,2]
     * 输出：2
     */
    @Test
    public void majorityElement(int[] nums) {
        Arrays.sort(nums);
        System.out.println(nums[nums.length / 2]);  // 肯定有一个元素的个数大于数组长度的一半。我们只需要把这个数组排序，那么数组中间的值肯定是存在最多的元素。
    }

    /*********************************************
     * 3.
     搜索二维矩阵：
     编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target 。该矩阵具有以下特性：
     + 每行的元素从左到右升序排列。
     + 每列的元素从上到下升序排列。
     ********************************************/
    @Test
    public void searchMatrix(int[][] matrix, int target) {
        // 从矩阵的左下角开始比较（该二维数组类似于一棵排序二叉树，对于每个数来说，上方的数都小于它，
        // 右方的数都大于它，所以把左下角作为根节点开始比较）
        int col = 0, row = matrix.length - 1;
        while (row >= 0 && col < matrix[0].length) {
            if (target > matrix[row][col]) {
                col++;
            } else if (target < matrix[row][col]) {
                row--;
            } else {
                System.out.println(true);
            }
        }
        System.out.println(false);
    }

    /********************************************
     * 4.
     合并两个有序数组：
     给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。

     请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。

     注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，
     其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
     输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
     输出：[1,2,2,3,5,6]
     解释：需要合并 [1,2,3] 和 [2,5,6] 。
     合并结果是 [1,2,2,3,5,6] ，其中斜体加粗标注的为 nums1 中的元素。
     **********************************************/
    @Test
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int j = 0;
        for (int i = m; i < nums1.length; i++) {  // Copy the second array to the first one
            nums1[i] = nums2[j];
            j++;
        }
        Arrays.sort(nums1);
    }

    /***********************************
     * 5.
     * 验证回文串：
     * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
     * 左右指针同时往中间走
     *********************************/
    @Test
    public void plalindromeSol() {
        String s = "asDsab";
        s = s.toLowerCase();
        System.out.println(s);
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left)))
                left++;
            while (left < right && !Character.isLetterOrDigit(s.charAt(right)))
                right--;
            if (s.charAt(left) != s.charAt(right)) {
                System.out.println("False");
            }
            left++;
            right--;
        }
        System.out.println("True");
    }

    /**
     * 6.
     * 鸡蛋掉落：DP DIFFICUILT GOOGLE CLASSICAL
     * 给你 k 枚相同的鸡蛋，并可以使用一栋从第 1 层到第 n 层共有 n 层楼的建筑。
     * <p>
     * 已知存在楼层 f ，满足 0 <= f <= n ，任何从 高于 f 的楼层落下的鸡蛋都会碎，从 f 楼层或比它低的楼层落下的鸡蛋都不会破。
     * <p>
     * 每次操作，你可以取一枚没有碎的鸡蛋并把它从任一楼层 x 扔下（满足 1 <= x <= n）。如果鸡蛋碎了，你就不能再次使用它。如果某枚鸡蛋扔下后没有摔碎，则可以在之后的操作中 重复使用 这枚鸡蛋。
     * <p>
     * 请你计算并返回要确定 f 确切的值 的 最小操作次数 是多少？
     **/
    static class Egg {
        public static int recursive(int k, int n) {
            if (n == 0 || n == 1 || k == 1) return n;
            int minimum = n;
            for (int i = 1; i < n; i++) {
                int tMin = Math.max(recursive(k - 1, i - 1), recursive(k, n - i));
                minimum = Math.min(minimum, 1 + tMin);
            }
            return minimum;
        }

        public int eggDrop(int K, int N) {
            return 0;
        }


    }

    /**
     * 7.
     * https://leetcode-cn.com/problems/palindrome-partitioning/solution/hui-su-you-hua-jia-liao-dong-tai-gui-hua-by-liweiw/
     * 给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是 回文串 。返回 s 所有可能的分割方案。
     * <p>
     * 回文串 是正着读和反着读都一样的字符串。
     */
    public class findPalindrome {
        public List<List<String>> partition(String s) {
            int len = s.length();
            List<List<String>> res = new ArrayList<>();
            if (len == 0) return res;
            Deque<String> stack = new ArrayDeque<>();
            char[] charArray = s.toCharArray();
            dfs(charArray, 0, len, stack, res);
            return res;
        }

        /**
         * @param charArray 输入
         * @param index     起始字符的索引
         * @param len       字符串 s 的长度，可以设置为全局变量
         * @param path      记录从根结点到叶子结点的路径
         * @param res       记录所有的结果
         */
        public void dfs(char[] charArray, int index, int len, Deque<String> path, List<List<String>> res) {
            // 如果开头元素的下标已经越界了表示找到了一个可行的划分
            if (index == len) {
                res.add(new ArrayList<>(path));
                return;
            }
            // 开头元素下标是index,则结束元素下标就可以是index ~ s.length()-1
            for (int i = index; i < len; i++) {
                // 如果index ~ i的子串不是回文串，就跳过它，判断index ~ i+1的子串
                if (!checkPalindrome(charArray, index, i)) {
                    continue;
                }
                // 如果index ~ i的子串是回文串，就将该子串加入当前结果中
                path.addLast(new String(charArray, index, i + 1 - index));
                // 以i+1为开头元素继续划分
                dfs(charArray, i + 1, len, path, res);
                // 回溯
                path.removeLast();
            }
        }

        /**
         * 8.
         * 这一步的时间复杂度是 O(N)，优化的解法是，先采用动态规划，把回文子串的结果记录在一个表格里
         *
         * @param charArray
         * @param left      子串的左边界，可以取到
         * @param right     子串的右边界，可以取到
         * @return
         */
        private boolean checkPalindrome(char[] charArray, int left, int right) {
            while (left < right) {
                if (charArray[left] != charArray[right]) {
                    return false;
                }
                left++;
                right--;
            }
            return true;
        }
    }

    /**
     * 9.
     * 单词拆分：
     * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。请你判断是否可以利用字典中出现的单词拼接出 s 。
     * <p>
     * 注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。
     * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。请你判断是否可以利用字典中出现的单词拼接出 s 。
     * <p>
     * 注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。
     * <p>
     * 示例 1：
     * <p>
     * 输入: s = "leetcode", wordDict = ["leet", "code"]
     * 输出: true
     * 解释: 返回 true 因为 "leetcode" 可以由 "leet" 和 "code" 拼接成。
     * 示例 2：
     * <p>
     * 输入: s = "applepenapple", wordDict = ["apple", "pen"]
     * 输出: true
     * 解释: 返回 true 因为 "applepenapple" 可以由 "apple" "pen" "apple" 拼接成。
     *      注意，你可以重复使用字典中的单词。
     */

    class SolutionWord {
        public boolean wordBreak(String s, List<String> wordDict) {

            boolean[] dp = new boolean[s.length() + 1];
            dp[0] = true;
            for (int i = 0; i < s.length(); i++) {
                if (!dp[i]) {
                    continue;
                }
                for (String str : wordDict) {
                    if (i + str.length() <= s.length() && s.substring(i, i + str.length()).equals(str))
                        dp[i + str.length()] = true;
                }
            }
            return dp[s.length()];
        }
    }


    /**
     * 10.
     * 给定一个字符串 s 和一个字符串字典 wordDict ，在字符串 s 中增加空格来构建一个句子，使得句子中所有的单词都在词典中。以任意顺序 返回所有这些可能的句子。
     * 注意：词典中的同一个单词可能在分段中被重复使用多次
     * <p>
     * 示例 1：
     * <p>
     * 输入:s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
     * 输出:["cats and dog","cat sand dog"]
     * <p>
     * 示例 2：
     * <p>
     * 输入:s = "pineapplepenapple", wordDict = ["apple","pen","applepen","pine","pineapple"]
     * 输出:["pine apple pen apple","pineapple pen apple","pine applepen apple"]
     * 解释: 注意你可以重复使用字典中的单词。
     * <p>
     * 示例 3:
     * 输入:s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
     * 输出:[]
     */

    class SolutionWordPlus {
        public List<String> wordBreak(String s, List<String> wordDict) {
            StringBuffer t = new StringBuffer();
            List<String> rst = new LinkedList<>();
            recur(s, t, rst, wordDict);
            return rst;
        }

        public void recur(String s, StringBuffer t, List<String> rst, List<String> wordDict) {
            if (s.length() == 0) {
                rst.add(t.substring(1));  // remove the first " ".
                return;
            }
            for (String word : wordDict) {
                if (s.startsWith(word)) {  // If the String starts with one of the word in the word dict
                    StringBuffer tmp = new StringBuffer(t).append(" ").append(word);  // Add the word into the t
                    recur(s.substring(word.length()), tmp, rst, wordDict);
                }
            }
        }
    }

    /**
     * 11.
     * 实现 Trie (前缀树)
     * Trie（发音类似 "try"）或者说 前缀树 是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。这一数据结构有相当多的应用情景，例如自动补完和拼写检查。
     * <p>
     * 请你实现 Trie 类：
     * <p>
     * Trie() 初始化前缀树对象。
     * void insert(String word) 向前缀树中插入字符串 word 。
     * boolean search(String word) 如果字符串 word 在前缀树中，返回 true（即，在检索之前已经插入）；否则，返回 false 。
     * boolean startsWith(String prefix) 如果之前已经插入的字符串 word 的前缀之一为 prefix ，返回 true ；否则，返回 false
     * <p>
     * 输入
     * ["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
     * [[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
     * 输出
     * [null, null, true, false, true, null, true]
     * <p>
     * 解释
     * Trie trie = new Trie();
     * trie.insert("apple");
     * trie.search("apple");   // 返回 True
     * trie.search("app");     // 返回 False
     * trie.startsWith("app"); // 返回 True
     * trie.insert("app");
     * trie.search("app");     // 返回 True
     */
    class Trie {
        private HashSet<String> trie;

        public Trie() {
            trie = new HashSet<>();
        }

        public void insert(String word) {
            this.trie.add(word);
        }

        public boolean search(String word) {
            return trie.contains(word);
        }

        public boolean startsWith(String prefix) {
            for (String str :
                    trie) {
                if (str.startsWith(prefix)) {
                    return true;
                }
            }
            return false;
        }
        /**
         * Your Trie object will be instantiated and called as such:
         * Trie obj = new Trie();
         * obj.insert(word);
         * boolean param_2 = obj.search(word);
         * boolean param_3 = obj.startsWith(prefix);
         */
    }

    /**
     * 12.
     * 单词搜索 II
     * 给定一个 m x n 二维字符网格 board 和一个单词（字符串）列表 words， 返回所有二维网格上的单词 。
     * <p>
     * 单词必须按照字母顺序，通过 相邻的单元格 内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母在一个单词中不允许被重复使用。
     * <p>
     * 输入：board = [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]], words = ["oath","pea","eat","rain"]
     * 输出：["eat","oath"]
     * <p>
     * 输入：board = [["a","b"],["c","d"]], words = ["abcb"]
     * 输出：[]
     */


    //  首先，大家肯定能想到的是遍历整个board数组，先看第一个字母在不在words单词列表的前缀中，如果在，再向上下左右扩散看前两个字母在不在words单词列表的前缀中，这样每次都要在words中查询相关前缀的效率是非常低下的，所以，我们可以考虑将words转换成前缀树（Trie）（又叫字典树），这样查找起来就快多了，而且我们可以记录沿途查询过哪些前缀了，这样就不用每次都从前缀树的根节点开始查找了。
    //
    // 关于前缀树，其实很简单，题目限定了所有字符都是英文小写字母，所以，我们这里使用26位的数组来表示它的孩子节点：
    // class TrieNode {
    //     // 记录到这个节点是否是一个完整的单词
    //     boolean isEnd = false;
    //     // 孩子节点，题目说了都是小写字母，所以用数组，否则可以用HashMap替换
    //     TrieNode[] children = new TrieNode[26];
    // }
    // 我们每遍历一个字符，判断其是否在前缀树当前层，如果在，就向上下左右扩散，同时，前缀树也向下移动一层。
    //
    // 除了使用到前缀树和DFS外，我们需要求得所有的结果，所以，需要回溯遍历整个board，对于每一个遇到的字符，都要判断其是否在前缀树中，同时，不在前缀树中的字符，直接剪枝掉即可。
    //

    class Solution1 {

        // 上下左右移动的方向
        int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        public List<String> findWords(char[][] board, String[] words) {
            // 结果集，去重
            Set<String> resultSet = new HashSet<>();

            // 构建字典树
            TrieNode root = buildTrie(words);

            int m = board.length;
            int n = board[0].length;
            // 记录某个下标是否访问过
            boolean[][] visited = new boolean[m][n];
            // 记录沿途遍历到的元素
            StringBuilder result = new StringBuilder();

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    // 从每个元素开始遍历
                    dfs(resultSet, result, board, i, j, root, visited);
                }
            }

            // 题目要求返回List
            return new ArrayList<>(resultSet);
        }

        private void dfs(Set<String> resultSet, StringBuilder result, char[][] board,
                         int i, int j, TrieNode node, boolean[][] visited) {
            // 判断越界，或者访问过，或者不在字典树中，直接返回
            if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || visited[i][j]
                    || node.children[board[i][j] - 'a'] == null) {
                return;
            }

            // 记录当前字符
            result.append(board[i][j]);

            // 如果有结束字符，加入结果集中
            if (node.children[board[i][j] - 'a'].isEnd) {
                resultSet.add(result.toString());
            }

            // 记录当前元素已访问
            visited[i][j] = true;

            // 按四个方向去遍历
            for (int[] dir : dirs) {
                dfs(resultSet, result, board, i + dir[0], j + dir[1], node.children[board[i][j] - 'a'], visited);
            }

            // 还原状态
            visited[i][j] = false;
            result.deleteCharAt(result.length() - 1);
        }

        private TrieNode buildTrie(String[] words) {
            TrieNode root = new TrieNode();
            for (String word : words) {
                char[] arr = word.toCharArray();
                TrieNode curr = root;
                for (char c : arr) {
                    if (curr.children[c - 'a'] == null) {
                        curr.children[c - 'a'] = new TrieNode();
                    }
                    curr = curr.children[c - 'a'];
                }
                curr.isEnd = true;
            }
            return root;
        }

        class TrieNode {
            // 记录到这个节点是否是一个完整的单词
            boolean isEnd = false;
            // 孩子节点，题目说了都是小写字母，所以用数组，否则可以用HashMap替换
            TrieNode[] children = new TrieNode[26];
        }
    }


    /**
     * 13.
     * 一般看到这种题目就想到了使用回溯法，由于1 <= m, n <= 12，因此只要退出条件设置完善，应该不会出现超时情况。
     * <p>
     * 使用set集合来存储搜索到的单词。
     * 对网格中每一个字符进行搜索，如果该字符与字符串中某个未在set集合中的单词的首字母相同，那么就进入dfs搜索。
     * 在dfs搜索中，注意网格下标i , j i,ji,j和单词中的字符下标不能越界，如果该字符之前访问过或者该字符和目前指向的单词字符不相等就直接退出；
     * 如果经过了上面的判断，并且单词的字符下标指向了最后一位，那么就找到了一个单词，将该单词加入集合中；
     * 如果没有就设置访问过该字符，分别往上下左右进行搜索，最后还原访问设置；
     * 在网格搜索中，如果找到了所有的单词就直接return结果；
     * 最后将集合转为List返回。
     * ————————————————
     */
    class Solution2 {
        Set<String> res = new HashSet<>();
        int m;
        int n;

        public List<String> findWords(char[][] board, String[] words) {   // 用dfs
            m = board.length;
            n = board[0].length;
            boolean[][] visited = new boolean[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    for (String word : words) {  // 遍历每单词，如果开头的一个字母等于正在查阅的字母，进行dfs搜索
                        if (word.charAt(0) == board[i][j] && !res.contains(word)) {
                            dfs(board, word, visited, 0, i, j);
                            if (res.size() == words.length)
                                return new ArrayList<String>(res);
                        }
                    }
                }
            }
            return new ArrayList<String>(res);
        }

        public void dfs(char[][] board, String word, boolean[][] visited, int idx, int i, int j) {
            if (i < 0 || j < 0 || i >= m || j >= n || idx >= word.length() || visited[i][j] == true || board[i][j] != word.charAt(idx))
                return;
            if (idx == word.length() - 1) {
                res.add(word);
                return;
            }
            visited[i][j] = true;  // 设置当前位置为检查过，进行上下左右的遍历
            dfs(board, word, visited, idx + 1, i + 1, j);
            dfs(board, word, visited, idx + 1, i - 1, j);
            dfs(board, word, visited, idx + 1, i, j + 1);
            dfs(board, word, visited, idx + 1, i, j - 1);
            visited[i][j] = false;
        }
    }

    /**
     * 14. 有效的字母异位
     * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
     * <p>
     * 注意：若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。
     * <p>
     * 输入: s = "anagram", t = "nagaram"
     * 输出: true
     * <p>
     * 输入: s = "anagram", t = "nagaram"
     * 输出: true
     */

    class SolutionIsAnogram {

        @Test
        public boolean isAnagram(String s, String t) {
            if (s.length() != t.length()) {
                return false;
            }

            char[] charsS = s.toCharArray();
            char[] charsT = t.toCharArray();

            Arrays.sort(charsS);
            Arrays.sort(charsT);
            return Arrays.equals(charsS, charsT);
        }
    }

    /**
     * 15.
     * 字符串 的zhon第一个唯一字符
     * 给定一个字符串 s ，找到 它的第一个不重复的字符，并返回它的索引 。如果不存在，则返回 -1 。
     */
    class SolutionOnlyOne {
        public int firstUniqueChar(String s) {
            for (int i = 0; i < s.length(); i++) {
                if (s.indexOf(s.charAt(i)) == s.lastIndexOf(s.charAt(i))) {
                    return i;
                }
            }
            return -1;
        }
    }

    /**
     * 16.
     * 反转字符串
     * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出。
     * <p>
     * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
     * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出。
     * <p>
     * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
     */
    class SOlutionReverse {
        public void reverseString(char[] s) {
            int start = 0;
            int end = s.length - 1;
            while (start < end) {
                char tmp = s[end];
                s[end] = s[start];
                s[start] = tmp;
                start++;
                end--;
            }
        }
    }

    /**
     * 至少有K个重复字符的最长子串
     *
     * 递归最基本的是记住递归函数的含义（务必牢记函数定义）：本题的 longestSubstring(s, k) 函数表示的就是题意，即求一个最长的子字符串的长度，
     * 该子字符串中每个字符出现的次数都最少为 kk。函数入参 ss 是表示源字符串；kk 是限制条件，即子字符串中每个字符最少出现的次数；函数返回结果是满足题意的最长子字符串长度。
     *
     * 递归的终止条件（能直接写出的最简单 case）：如果字符串 ss 的长度少于 kk，那么一定不存在满足题意的子字符串，返回 0；
     *
     * 调用递归（重点）：如果一个字符 cc 在 ss 中出现的次数少于 kk 次，那么 ss 中所有的包含 cc 的子字符串都不能满足题意。所以，应该在 ss 的所有不包含
     *   cc 的子字符串中继续寻找结果：把 ss 按照 cc 分割（分割后每个子串都不包含 cc），得到很多子字符串 tt；下一步要求 tt 作为源字符串的时候，
     *   它的最长的满足题意的子字符串长度（到现在为止，我们把大问题分割为了小问题(ss → tt)）。此时我们发现，恰好已经定义了函数 longestSubstring(s, k)
     *   就是来解决这个问题的！所以直接把 longestSubstring(s, k) 函数拿来用，于是形成了递归。
     *
     * 未进入递归时的返回结果：如果 ss 中的每个字符出现的次数都大于 kk 次，那么 ss 就是我们要求的字符串，直接返回该字符串的长度。
     */
    class solutionLongestSubstring {
        public int longestSubstring(String s, int k) {
            if (s.length() < k) {
                return 0;
            }
            // 找到出现次数最少的字符 smallest.
            HashMap<Character, Integer> counter = new HashMap<Character, Integer>();
            for (int i = 0; i < s.length(); i++) {
                counter.put(s.charAt(i), counter.getOrDefault(s.charAt(0), 0) + 1);
            }
            int value = Integer.MAX_VALUE;
            char smallest = ' ';
            for (char c : counter.keySet()) {
                if (counter.get(c) < value) {
                    value = counter.get(c);
                    smallest = c;
                }
            }
            // 如果出现次数最少的字符都小于k了，直接返回母串的长度
            if (value < k){
                return s.length();
            }
            // 把母串去除出现次数最少的字符，递归
            int res = 0;
            for (String a: s.split(String.valueOf(smallest))){
                res = Math.max(res, longestSubstring(a, k));
            }
            return res;
        }
    }
}













