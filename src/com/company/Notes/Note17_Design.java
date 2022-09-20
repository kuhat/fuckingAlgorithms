package com.company.Notes;

import java.util.*;

public class Note17_Design {

    // 519 Random flip Matrix
    /**
     * There is an m x n binary grid matrix with all the values set 0 initially. Design an algorithm to randomly pick
     * an index (i, j) where matrix[i][j] == 0 and flips it to 1. All the indices (i, j) where matrix[i][j] == 0 should
     * be equally likely to be returned.
     *
     * Optimize your algorithm to minimize the number of calls made to the built-in random function of your language
     * and optimize the time and space complexity.
     *
     * Implement the Solution class:
     *
     * Solution(int m, int n) Initializes the object with the size of the binary matrix m and n.
     * int[] flip() Returns a random index [i, j] of the matrix where matrix[i][j] == 0 and flips it to 1.
     * void reset() Resets all the values of the matrix to be 0.
     *
     *
     * Example 1:
     *
     * Input
     * ["Solution", "flip", "flip", "flip", "reset", "flip"]
     * [[3, 1], [], [], [], [], []]
     * Output
     * [null, [1, 0], [2, 0], [0, 0], null, [2, 0]]
     *
     * Explanation
     * Solution solution = new Solution(3, 1);
     * solution.flip();  // return [1, 0], [0,0], [1,0], and [2,0] should be equally likely to be returned.
     * solution.flip();  // return [2, 0], Since [1,0] was returned, [2,0] and [0,0]
     * solution.flip();  // return [0, 0], Based on the previously returned indices, only [0,0] can be returned.
     * solution.reset(); // All the values are reset to 0 and can be returned.
     * solution.flip();  // return [2, 0], [0,0], [1,0], and [2,0] should be equally likely to be returned.
     */

    class Solution {

        HashMap<Integer, Integer> map;
        int m, n;
        int total;
        Random random;

        public Solution(int m, int n) {
            map = new HashMap<>();
            random = new Random();
            this.m = m;
            this.n = n;
            reset();
        }

        public int[] flip() {
            int rand = random.nextInt(total--);
            int res = map.getOrDefault(rand, rand);
            map.put(rand, map.getOrDefault(total, total));
            map.put(total, res);
            return new int[]{res / n, res % n};
        }

        public void reset() {
            total = m * n;
        }
    }

    // 146: LRU Cache  非常重要 double linked list
    /**
     * Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
     *
     * Implement the LRUCache class:
     *
     * LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
     * int get(int key) Return the value of the key if the key exists, otherwise return -1.
     * void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the key-value pair
     * to the cache. If the number of keys exceeds the capacity from this operation, evict the least recently used key.
     * The functions get and put must each run in O(1) average time complexity.
     *
     * Example 1:
     *
     * Input
     * ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
     * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
     * Output
     * [null, null, null, 1, null, -1, null, -1, 3, 4]
     *
     * Explanation
     * LRUCache lRUCache = new LRUCache(2);
     * lRUCache.put(1, 1); // cache is {1=1}        1，1
     * lRUCache.put(2, 2); // cache is {1=1, 2=2}   1，1 2，2
     * lRUCache.get(1);    // return 1
     * lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}  3，3  1，1
     * lRUCache.get(2);    // returns -1 (not found)
     * lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}  4，4  3，3
     * lRUCache.get(1);    // return -1 (not found)
     * lRUCache.get(3);    // return 3
     * lRUCache.get(4);    // return 4
     */

    /*
    linkedHashMap
     */

    class lruChache extends LinkedHashMap<Integer, Integer> {
        public int capacity;

        public lruChache(int capacity) {
            super(capacity);
            this.capacity = capacity;
        }

        public int get(int key) {
            return super.getOrDefault(key, -1);
        }

        public void put(int key, int value) {
            super.put(key,value);
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() > capacity;
        }
    }
    /*
    HashMap + Double Linked List
     */
    class LRUCache {

        class Node{
            int key;
            int value;
            Node next;
            Node pre;
            public Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }

        private HashMap<Integer, Node> map;  // 将key和 node对应
        private int capacity;

        /*
        为什么使用double linked list: 删除的时候，要将删除的元素的上一个元素指向删除的元素的下一个元素，这里
        要知道上一个元素是什么
         */

        private Node head;
        private Node tail;

        public LRUCache(int capacity) {
            map = new HashMap<>();
            this.capacity = capacity;
            head = null;
            tail = null;
        }

        /**
         * 取出：1. 存在，调换顺序
         *      2. 不存在，返回-1
         *
         * @param key
         * @return
         */

        public int get(int key) {
            Node node = map.get(key);
            if (node == null) return -1;
                if (node != tail) {
                    if (node == head) head = head.next;
                    else{
                        node.pre.next = node.next;  // 删除node，右指针更新
                        node.next.pre = node.pre;  // 左指针更新
                    }
                    tail.next = node;
                    node.pre = tail;
                    node.next = null;
                    tail = node;
                }
            return node.value;
        }

        /**
         * 插入： 1. 存在，更新顺序
         *       2. 不存在 : 容量等于0，不等于0
         *
         * @param key
         * @param value
         */

        public void put(int key, int value) {
            Node node = map.get(key);  // 先看存在与否
            if (node != null) {
                //  如果已经存在了
                node.value = value;  // 更新值，把put的node移动到最前面
                if (node != tail) {
                    if (node == head) head = head.next;
                    else{
                        node.pre.next = node.next;  // 删除node，右指针更新
                        node.next.pre = node.pre;  // 左指针更新
                    }
                    tail.next = node;
                    node.pre = tail;
                    node.next = null;
                    tail = node;
                }
            } else {
                // 如果为空，新建一个node，插入
                Node newNode = new Node(key, value);
                if (capacity == 0) {  // 如果容量为0了，移除第一个元素再加
                    Node temp = head;
                    head = head.next;  // 链表的删除
                    if(temp == tail) tail = null;  // 如果删除到了尾部，tail等于空
                    map.remove(temp.key);  // 删除一个原先头部的元素
                    capacity++;
                }
                if (head == null && tail == null) head = newNode;
                else {
                    // 否则将元素加到尾部
                    tail.next = newNode;
                    newNode.pre = tail;  // 双向链表左指针指向tail
                    newNode.next = null;
                }
                tail = newNode;  // tail指向newNode
                map.put(key, newNode);
                capacity--;
            }
        }
    }

    // 588: file system : Amazon Trie Tree
    /**
     * Design a data structure that simulates an in-memory file system.
     *
     * Implement the FileSystem class:
     *
     * FileSystem() Initializes the object of the system.
     * List<String> ls(String path)
     * If path is a file path, returns a list that only contains this file's name.
     * If path is a directory path, returns the list of file and directory names in this directory.
     * The answer should in lexicographic order.
     * void mkdir(String path) Makes a new directory according to the given path. The given directory path does not exist.
     * If the middle directories in the path do not exist, you should create them as well.
     * void addContentToFile(String filePath, String content)
     * If filePath does not exist, creates that file containing given content.
     * If filePath already exists, appends the given content to original content.
     * String readContentFromFile(String filePath) Returns the content in the file at filePath.
     *
     * Example 1:
     *
     * Input
     * ["FileSystem", "ls", "mkdir", "addContentToFile", "ls", "readContentFromFile"]
     * [[], ["/"], ["/a/b/c"], ["/a/b/c/d", "hello"], ["/"], ["/a/b/c/d"]]
     * Output
     * [null, [], null, null, ["a"], "hello"]
     *
     * Explanation
     * FileSystem fileSystem = new FileSystem();
     * fileSystem.ls("/");                         // return []
     * fileSystem.mkdir("/a/b/c");
     * fileSystem.addContentToFile("/a/b/c/d", "hello");
     * fileSystem.ls("/");                         // return ["a"]
     * fileSystem.readContentFromFile("/a/b/c/d"); // return "hello"
     */
    /*
    用Trie 树来解决。
    有两种类型，文件和文件夹，用isFile来判断当前节点是否为文件，还需要content来存储类容
    不知道有多少个节点，用hashMap来存储节点
     */

    class FileSystem588 {

        Node root;

        public FileSystem588() {
            root = new Node("/");
        }

        public List<String> ls(String path) {
            Node node = traverse(path);
            List<String> res = new ArrayList<>();
            if (node.isFile) {
                res.add(node.name);
            } else {
                for (String child : node.children.keySet()) res.add(child);
            }
            Collections.sort(res);
            return res;
        }

        public void mkdir(String path) {
            traverse(path);
        }

        public void addContentToFile(String filePath, String content) {
            Node node = traverse(filePath);
            node.isFile = true;
            node.content.append(content);
        }

        public String readContentFromFile(String filePath) {
            Node node = traverse(filePath);
            return node.content.toString();
        }

        public Node traverse(String filePath) {
            String[] path = filePath.split("/");  // 将当前的文件路径分隔
            Node cur = root;  // 从根节点开始遍历
            for (int i = 1; i < path.length; i++) {  // 第一个字符是空，跳过
                if (!cur.children.containsKey(path[i])) {  // 如果当前的孩子节点没有这个key, 加入
                    Node node =new Node(path[i]);
                    cur.children.put(path[i], node);
                }
                cur = cur.children.get(path[i]);  // cur向下走
            }
            return cur;
        }

        class Node{

            boolean isFile;  // 判断当前节点是否为一个文件
            String name;   // 当前节点的名字
            HashMap<String, Node> children;  // 当前节点的孩子, key为名字， value为节点
            StringBuilder content;  // 节点的内容

            public Node(String name) {
                this.name = name;
                isFile = false;
                children = new HashMap<>();
                content = new StringBuilder();
            }
        }


    }

/**
 * Your FileSystem object will be instantiated and called as such:
 * FileSystem obj = new FileSystem();
 * List<String> param_1 = obj.ls(path);
 * obj.mkdir(path);
 * obj.addContentToFile(filePath,content);
 * String param_4 = obj.readContentFromFile(filePath);
 */

    // 348： Design Tic-Tac-Toe
    /**
     * Assume the following rules are for the tic-tac-toe game on an n x n board between two players:
     *
     * A move is guaranteed to be valid and is placed on an empty block.
     * Once a winning condition is reached, no more moves are allowed.
     * A player who succeeds in placing n of their marks in a horizontal, vertical, or diagonal row wins the game.
     * Implement the TicTacToe class:
     *
     * TicTacToe(int n) Initializes the object the size of the board n.
     * int move(int row, int col, int player) Indicates that the player with id player plays at the cell (row, col)
     * of the board. The move is guaranteed to be a valid move.
     *
     * Example 1:
     *
     * Input
     * ["TicTacToe", "move", "move", "move", "move", "move", "move", "move"]
     * [[3], [0, 0, 1], [0, 2, 2], [2, 2, 1], [1, 1, 2], [2, 0, 1], [1, 0, 2], [2, 1, 1]]
     * Output
     * [null, 0, 0, 0, 0, 0, 0, 1]
     *
     * Explanation
     * TicTacToe ticTacToe = new TicTacToe(3);
     * Assume that player 1 is "X" and player 2 is "O" in the board.
     * ticTacToe.move(0, 0, 1); // return 0 (no one wins)
     * |X| | |
     * | | | |    // Player 1 makes a move at (0, 0).
     * | | | |
     *
     * ticTacToe.move(0, 2, 2); // return 0 (no one wins)
     * |X| |O|
     * | | | |    // Player 2 makes a move at (0, 2).
     * | | | |
     *
     * ticTacToe.move(2, 2, 1); // return 0 (no one wins)
     * |X| |O|
     * | | | |    // Player 1 makes a move at (2, 2).
     * | | |X|
     *
     * ticTacToe.move(1, 1, 2); // return 0 (no one wins)
     * |X| |O|
     * | |O| |    // Player 2 makes a move at (1, 1).
     * | | |X|
     *
     * ticTacToe.move(2, 0, 1); // return 0 (no one wins)
     * |X| |O|
     * | |O| |    // Player 1 makes a move at (2, 0).
     * |X| |X|
     *
     * ticTacToe.move(1, 0, 2); // return 0 (no one wins)
     * |X| |O|
     * |O|O| |    // Player 2 makes a move at (1, 0).
     * |X| |X|
     *
     * ticTacToe.move(2, 1, 1); // return 1 (player 1 wins)
     * |X| |O|
     * |O|O| |    // Player 1 makes a move at (2, 1).
     * |X|X|X|
     */

    class TicTacToe {

        private int[] rows;
        private int[] cols;
        private int diagnal;
        private int antiDiagnal;
        private int size;

        public TicTacToe(int n) {
            rows = new int[n];
            cols = new int[n];
            this.size = n;
        }

        public int move(int row, int col, int player) {
            int toAdd = player == 1 ? 1: -1;
            rows[row] += toAdd;
            rows[col] += toAdd;
            if (row == col) diagnal += toAdd;
            if (col == (cols.length - row - 1)) antiDiagnal += toAdd;
            if (Math.abs(rows[row]) == size || Math.abs(cols[col]) == size || Math.abs(diagnal) == size
            || Math.abs(antiDiagnal)== size) return player;
            return 0;
        }
    }

    // 254 ： shortest word distance 2
    /**
     *Design a data structure that will be initialized with a string array, and then it should answer queries of the
     * shortest distance between two different strings from the array.
     *
     * Implement the WordDistance class:
     *
     * WordDistance(String[] wordsDict) initializes the object with the strings array wordsDict.
     * int shortest(String word1, String word2) returns the shortest distance between word1 and word2 in the array wordsDict.
     *
     * Example 1:
     *
     * Input
     * ["WordDistance", "shortest", "shortest"]
     * [[["practice", "makes", "perfect", "coding", "makes"]], ["coding", "practice"], ["makes", "coding"]]
     * Output
     * [null, 3, 1]
     *
     * Explanation
     * WordDistance wordDistance = new WordDistance(["practice", "makes", "perfect", "coding", "makes"]);
     * wordDistance.shortest("coding", "practice"); // return 3
     * wordDistance.shortest("makes", "coding");    // return 1
     */
    class WordDistance {

        HashMap<String, List<Integer>> map;

        public WordDistance(String[] wordsDict) {
            map = new HashMap<>();
            for (int i = 0; i < wordsDict.length; i++) {
                if (map.containsKey(wordsDict[i])) {
                    map.get(wordsDict[i]).add(i);
                } else {
                    List<Integer> tmp = new ArrayList<>();
                    tmp.add(i);
                    map.put(wordsDict[i], tmp);

                }
            }
        }

        public int shortest(String word1, String word2) {
            List<Integer> l1 = map.get(word1);
            List<Integer> l2 = map.get(word2);
            int i = 0, j =0, res = Integer.MAX_VALUE;
            while(i < l1.size() && j < l2.size()) {
                res = Math.min(res, Math.abs(l1.get(i) - l2.get(j)));
                if (l1.get(i) < l2.get(j)) i++;
                else j++;
            }
            return res;
        }
    }

    // 245: shortest word distance3
    /**
     * Given an array of strings wordsDict and two strings that already exist in the array word1 and word2, return the
     * shortest distance between these two words in the list.
     *
     * Note that word1 and word2 may be the same. It is guaranteed that they represent two individual words in the list.
     *
     * Example 1:
     *
     * Input: wordsDict = ["practice", "makes", "perfect", "coding", "makes"], word1 = "makes", word2 = "coding"
     * Output: 1
     * Example 2:
     *
     * Input: wordsDict = ["practice", "makes", "perfect", "coding", "makes"], word1 = "makes", word2 = "makes"
     * Output: 3
     */
    class Solution245 {
        public int shortestWordDistance(String[] wordsDict, String word1, String word2) {
            int a = -1, b = -1, res = wordsDict.length;
            for (int i = 0; i < wordsDict.length; i++) {
                if (wordsDict[i].equals(word1)) a = i;
                if (wordsDict[i].equals(word2)) {
                    if (word1.equals(word2)) {
                        a = b;
                    }
                    b = i;
                }
                if (a != -1 && b != -1) res = Math.min(res, Math.abs(a - b));
            }
            return res;
        }
    }

    // 251： Flatten 2D vector
    /**
     * Design an iterator to flatten a 2D vector. It should support the next and hasNext operations.
     *
     * Implement the Vector2D class:
     *
     * Vector2D(int[][] vec) initializes the object with the 2D vector vec.
     * next() returns the next element from the 2D vector and moves the pointer one step forward. You may assume that all the calls to next are valid.
     * hasNext() returns true if there are still some elements in the vector, and false otherwise.
     *
     *
     * Example 1:
     *
     * Input
     * ["Vector2D", "next", "next", "next", "hasNext", "hasNext", "next", "hasNext"]
     * [[[[1, 2], [3], [4]]], [], [], [], [], [], [], []]
     * Output
     * [null, 1, 2, 3, true, true, 4, false]
     *
     * Explanation
     * Vector2D vector2D = new Vector2D([[1, 2], [3], [4]]);
     * vector2D.next();    // return 1
     * vector2D.next();    // return 2
     * vector2D.next();    // return 3
     * vector2D.hasNext(); // return True
     * vector2D.hasNext(); // return True
     * vector2D.next();    // return 4
     * vector2D.hasNext(); // return False
     */
    class Vector2D {

        int indexList, indexElement;
        int[][] vec;

        public Vector2D(int[][] vec) {
            this.vec = vec;
            indexList = 0;  // 当前遍历到的数组
            indexElement = 0;  // 当前数组的第一个
        }

        public int next() {
            if(hasNext()) return vec[indexList][indexElement++];
            return -1;
        }

        public boolean hasNext() {
            while (indexList < vec.length) {
                if (indexElement < vec[indexList].length) return true;
                else {
                    indexList++;
                    indexElement = 0;
                }
            }
            return false;
        }
    }


}
