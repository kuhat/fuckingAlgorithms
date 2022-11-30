package com.company.Notes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Note14_Trie {


    // LeetCode 211: Design add and search words data structure: Trie
    public class TrieNode {
        TrieNode[] children;
        boolean isWord;  // 判断是否是单词

        //        char c;  // 可以没有
        public TrieNode() {
            children = new TrieNode[26];  // 并不一定有26个
            isWord = false;
        }
        // 要点：仅是数组，就可以知道路径对应的字母
    }

    public class Trie {
        TrieNode root;

        private void add(String word) {
            TrieNode cur = root;
            for (char c : word.toCharArray()) {
                if (cur.children[c - 'a'] == null) {  // 如果这个字母的位置是空的，就进行初始化
                    cur.children[c - 'a'] = new TrieNode();
                }
                cur = cur.children[c - 'a'];  // 移动cur到下一个节点
            }
            cur.isWord = true;
        }

        private boolean contains(String word) {
            TrieNode cur = root;
            for (char c : word.toCharArray()) {
                if (cur.children[c - 'a'] == null) {  // 如果没有当前节点，对应于之后的路径都没有
                    return false;
                }
                cur = cur.children[c - 'a'];
            }


            return cur.isWord;
        }

        private boolean startsWith(String prefix) {
            TrieNode cur = root;
            for (char c : prefix.toCharArray()) {
                if (cur.children[c - 'a'] == null) return false;
                cur = cur.children[c - 'a'];
            }
            return true;
        }

        private boolean search(String word) {
            return search(root, word, 0);
        }

        private boolean search(TrieNode cur, String word, int index) {
            if (index == word.length()) return cur.isWord;
            if (word.charAt(index) == '.') {
                for (TrieNode temp : cur.children) {
                    if (temp != null && search(temp, word, index + 1)) return true;
                }
                return false;
            } else {
                char c = word.charAt(index);
                TrieNode temp = cur.children[c - 'a'];
                return temp != null && search(temp, word, index + 1);
            }
        }
    }

    // 212: word search II

    /**
     * Given an m x n board of characters and a list of strings words, return all words on the board.
     * <p>
     * Each word must be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally
     * or vertically neighboring. The same letter cell may not be used more than once in a word.
     * <p>
     * <p>
     * Input:
     * board = [["o","a","a","n"],
     * ["e","t","a","e"],
     * ["i","h","k","r"],
     * ["i","f","l","v"]],
     * <p>
     * words = ["oath","pea","eat","rain"]
     * Output: ["eat","oath"]
     */
    class Solution212 {
        public List<String> findWords(char[][] board, String[] words) {
            List<String> res = new ArrayList<>();
            TrieNode root = buildTrieNode(words);
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    dfs(board, i, j, root, res);
                }
            }
            return res;
        }

        private void dfs(char[][] board, int i, int j, TrieNode cur, List<String> res) {
            if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) return;  // 边界
            char c = board[i][j];  // 记录当前的值
            if (c == '#' || cur.children[c - 'a'] == null) return;
            cur = cur.children[c - 'a'];
            if (cur.word != null) {
                res.add(cur.word);
                cur.word = null;
            }
            board[i][j] = '#';  // 用过的地方变成#
            dfs(board, i - 1, j, cur, res);  // 上下左右遍历floodfill
            dfs(board, i + 1, j, cur, res);
            dfs(board, i, j - 1, cur, res);
            dfs(board, i, j + 1, cur, res);
            board[i][j] = c;  // 还原之前的字母
        }

        private TrieNode buildTrieNode(String[] words) {
            TrieNode root = new TrieNode();
            for (String word : words) {
                TrieNode cur = root;
                for (char c : word.toCharArray()) {
                    if (cur.children[c - 'a'] == null) cur.children[c - 'a'] = new TrieNode();
                    cur = cur.children[c - 'a'];
                }
                cur.word = word;
            }
            return root;
        }

        class TrieNode {
            TrieNode[] children;
            String word;

            public TrieNode() {
                children = new TrieNode[26];
            }
        }
    }

    // 208: 实现Trie

    /**
     * A trie (pronounced as "try") or prefix tree is a tree data structure used to efficiently store and retrieve
     * keys in a dataset of strings. There are various applications of this data structure, such as autocomplete and spellchecker.
     * <p>
     * Implement the Trie class:
     * <p>
     * Trie() Initializes the trie object.
     * void insert(String word) Inserts the string word into the trie.
     * boolean search(String word) Returns true if the string word is in the trie (i.e., was inserted before),
     * and false otherwise.
     * boolean startsWith(String prefix) Returns true if there is a previously inserted string word that has the
     * prefix prefix, and false otherwise.
     * <p>
     * Input
     * ["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
     * [[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
     * Output
     * [null, null, true, false, true, null, true]
     * <p>
     * Explanation
     * Trie trie = new Trie();
     * trie.insert("apple");
     * trie.search("apple");   // return True
     * trie.search("app");     // return False
     * trie.startsWith("app"); // return True
     * trie.insert("app");
     * trie.search("app");     // return True
     */
    class solution208 {
        //创建节点类
        class TrieNode {
            /**
             * 标记是否为完整字符串，如果是字符串的
             * 最后一个字符，则为true，否则为false。
             */
            boolean isWord;
            //26个子节点
            TrieNode[] children;

            public TrieNode() {
                isWord = false;
                children = new TrieNode[26];
            }
        }

        class Trie {
            // 根节点，不存储任何字母，从根节点的子节点开始存储
            private TrieNode root;

            public Trie() {
                root = new TrieNode();
            }

            // 插入字符串
            public void insert(String word) {
                TrieNode parentNode = root;
                for (char c : word.toCharArray()) {
                    if (parentNode.children[c - 'a'] == null) parentNode.children[c - 'a'] = new TrieNode();
                    parentNode = parentNode.children[c - 'a'];
                }
                parentNode.isWord = true;
            }

            public boolean search(String word) {
                TrieNode current = find(word);
                return current != null && current.isWord;
            }

            public TrieNode find(String word) {
                TrieNode parentNode = root;
                for (char c : word.toCharArray()) {
                    // 只要又一个字符节点不在就返回null
                    if ((parentNode = parentNode.children[c - 'a']) == null) return null;
                }
                return parentNode;
            }

            public boolean startWith(String prefix) {
                return find(prefix) != null;
            }
        }
    }

    // 472： Concatenated words

    /**
     * Given an array of strings words (without duplicates), return all the concatenated words in the given list of words.
     * <p>
     * A concatenated word is defined as a string that is comprised entirely of at least two shorter words in the given array.
     * <p>
     * Example 1:
     * <p>
     * Input: words = ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]
     * Output: ["catsdogcats","dogcatsdog","ratcatdogcat"]
     * Explanation: "catsdogcats" can be concatenated by "cats", "dog" and "cats";
     * "dogcatsdog" can be concatenated by "dog", "cats" and "dog";
     * "ratcatdogcat" can be concatenated by "rat", "cat", "dog" and "cat".
     * Example 2:
     * <p>
     * Input: words = ["cat","dog","catdog"]
     * Output: ["catdog"]
     */
    class Solution472 {
        TrieNode root = new TrieNode();

        class TrieNode {
            boolean isWord;
            TrieNode[] children;

            public TrieNode() {
                isWord = false;
                children = new TrieNode[26];
            }
        }

        public List<String> findAllConcatenatedWordsInADict(String[] words) {
            List<String> res = new ArrayList<>();
            build(words);  // 构建字典树
            for (String word : words) {   // 每个单词都遍历寻找
                if (search(word, 0, 0)) res.add(word);
            }
            return res;
        }

        // 构建trie树子
        public void build(String[] dict) {
            for (String word : dict) {  // 每个单词都遍历
                if (word == null || word.length() == 0) continue;
                TrieNode cur = root;  // 从根节点开始
                for (int i = 0; i < word.length(); i++) {  // 一个字母一个字母的走
                    char c = word.charAt(i);
                    // 如果当前字母的节点是空，新建一个节点
                    if (cur.children[c - 'a'] == null) cur.children[c - 'a'] = new TrieNode();
                    cur = cur.children[c - 'a'];  // cur向下走一格
                }
                cur.isWord = true;
            }
        }

        public boolean search(String word, int index, int count) {
            TrieNode cur = root;
            for (int i = index; i < word.length(); i++) {
                if (cur.children[word.charAt(i) - 'a'] == null) return false;
                cur = cur.children[word.charAt(i) - 'a'];
                if (cur.isWord && search(word, i + 1, count + 1)) {
                    return true;
                }
            }
            return count >= 1 && cur.isWord;
        }


    }
    // word squares https://leetcode.com/problems/word-squares/

    /**
     * 425
     * <p>
     * Given an array of unique strings words, return all the word squares you can build from words. The same word from words can be used multiple times. You can return the answer in any order.
     * <p>
     * A sequence of strings forms a valid word square if the kth row and column read the same string, where 0 <= k < max(numRows, numColumns).
     * <p>
     * For example, the word sequence ["ball","area","lead","lady"] forms a word square because each word reads the same both horizontally and vertically.
     * <p>
     * <p>
     * Example 1:
     * <p>
     * Input: words = ["area","lead","wall","lady","ball"]
     * Output: [["ball","area","lead","lady"],["wall","area","lead","lady"]]
     * Explanation:
     * The output consists of two word squares. The order of output does not matter (just the order of words in each word square matters).
     * Example 2:
     * <p>
     * Input: words = ["abat","baba","atan","atal"]
     * Output: [["baba","abat","baba","atal"],["baba","abat","baba","atan"]]
     * Explanation:
     * The output consists of two word squares. The order of output does not matter (just the order of words in each word square matters).
     */
    class Solution425 {
        TrieNode root;

        public List<List<String>> wordSquares(String[] words) {
            List<List<String>> res = new ArrayList<>();
            if (words.length == 0) {
                return res;
            }
            root = new TrieNode();
            buildTrie(words);
            findSquare(res, new ArrayList<>(), words[0].length());
            return res;
        }

        private void findSquare(List<List<String>> res, List<String> candidate, int len) {
            // 结束条件为当找到的数组等于square的长度
            if (candidate.size() == len) {
                res.add(new ArrayList<>(candidate));
                return;
            }
            // 当前遍历到的位置
            int index = candidate.size();
            StringBuilder sb = new StringBuilder();
            for (String s : candidate) {
                sb.append(s.charAt(index));  // 当前的前缀单词是什么
            }
            String s = sb.toString();
            TrieNode node = root;
            for (int i = 0; i < s.length(); i++) {
                if (node.next[s.charAt(i) - 'a'] != null) {
                    node = node.next[s.charAt(i) - 'a'];
                } else {
                    node = null;
                    break;
                }
            }

            // 如果当前的node不是空的，加入node的单词
            if (node != null) {
                for (String next : node.words) {
                    candidate.add(next);
                    findSquare(res, candidate, len);
                    candidate.remove(candidate.size() - 1);
                }
            }
        }

        // 每个节点对应加入当前的单词：
        //       root: wall, area, lead, lady....
        //       /  \        \
        //  w:wall a:area  l: lead, lady
        //    /     \         /      \
        //  a:wall  r:area  e:lead   a:lady
        //  /       \          /     \
        //l:wall    e:area   d:lead   d:lady
        //   .. ... .. .. ... .. ... ... . ...
        //
        private void buildTrie(String[] words) {
            for (String word : words) {
                TrieNode node = root;
                char[] wordChar = word.toCharArray();
                for (char c : wordChar) {
                    node.words.add(word);
                    if (node.next[c - 'a'] == null) {
                        node.next[c - 'a'] = new TrieNode();
                    }
                    node = node.next[c - 'a'];
                }
                node.words.add(word);
            }
        }

        // 一个 trie ndoe 下面有26个子节点， 每个子节点下面可能组成很多个单词
        class TrieNode {
            TrieNode[] next = new TrieNode[26];
            List<String> words = new ArrayList<>();
        }

        // Backtracking without trie
        public List<List<String>> wordSquaress(String[] words) {
            //  将每个单词的前缀存起来：
            // wall: w: wall
            //      wa: wall
            //     wal: wall
            //    wall: wall
            HashMap<String, HashSet<String>> prefix = new HashMap<>();
            for (String word : words) {
                for (int i = 0; i <= word.length(); i++) {
                    String s = word.substring(0, i);
                    prefix.putIfAbsent(s, new HashSet<>());
                    prefix.get(s).add(word);
                }
            }

            List<List<String>> res = new ArrayList<>();
            List<String> candidate;
            // 将每个单词放在第一行然后去找其它的单词
            for (String word : words) {
                candidate = new ArrayList<>();
                candidate.add(word);
                dfs(res, candidate, 1, words[0].length(), prefix);
            }
            return res;
        }

        /*    1     sb: a, 字典里有 a: area
            w a l l  candidate里加入area，继续走到le，
            a r e a  字典里有 le: lead, candidate里加入lead
            l e a d  走到lad, 字典里有lady, candidate加入lady,走完，结束
            l a d y
         */
        private void dfs(List<List<String>> res, List<String> candidate, int pos, int len,
                         HashMap<String, HashSet<String>> prefix) {
            if (pos == len) {
                res.add(new ArrayList<>(candidate));
                return;
            }
            StringBuilder sb = new StringBuilder();
            // 将目前找到的单词的前缀加入，
            for (String can : candidate) {
                sb.append(can.charAt(pos));
            }
            // 如果前缀里面没有当前需要找的前缀，返回
            if (!prefix.containsKey(sb.toString())) return;
            // 如果前缀map里有需要找的单词，加入candidate, 然后继续下一位置
            for (String next : prefix.get(sb.toString())) {
                candidate.add(next);
                dfs(res, candidate, pos + 1, len, prefix);
                candidate.remove(candidate.size() - 1);
            }
        }
    }

}
