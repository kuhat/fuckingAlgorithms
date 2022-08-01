package com.company.Notes;

import java.util.ArrayList;
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
     * Input: board = [["o","a","a","n"],
     * ["e","t","a","e"],
     * ["i","h","k","r"],
     * ["i","f","l","v"]],
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

}
