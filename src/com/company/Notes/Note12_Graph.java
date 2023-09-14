package com.company.Notes;

import com.company.leetCode.NestedInteger;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Note12_Graph {
    //200: Number of Islands

    /**
     * Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands.
     * <p>
     * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
     * <p>
     * Input: grid = [
     * ["1","1","1","1","0"],
     * ["1","1","0","1","0"],
     * ["1","1","0","0","0"],
     * ["0","0","0","0","0"]
     * ]
     * Output: 1
     * <p>
     * Input: grid = [
     * ["1","1","0","0","0"],
     * ["1","1","0","0","0"],
     * ["0","0","1","0","0"],
     * ["0","0","0","1","1"]
     * ]
     * Output: 3
     */
    class solution200 {
        private int m;
        private int n;

        public int numIslands(char[][] grid) {
            if (grid == null || grid.length == 0) return 0;
            m = grid.length;
            n = grid[0].length;
            int res = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == '1') {  // 当遍历到1的时候进行flood fill
                        floodfill(grid, i, j);
                        // floodfillBFS(grid, i, j);
                        res++;
                    }
                }
            }
            return res;
        }

        private void floodfill(char[][] grid, int i, int j) {
            if (i < 0 || j < 0 || i >= m || j >= n || grid[i][j] == '0') return;  // 如果遍历到边界或者遇到了0就返回
            grid[i][j] = '0';  // 把当前位置变为0
            floodfill(grid, i, j + 1);  // 上下左右都进行遍历
            floodfill(grid, i, j - 1);
            floodfill(grid, i + 1, j);
            floodfill(grid, i - 1, j);
        }

        private void floodfillBFS(char[][] grid, int x, int y) {
            grid[x][y] = '0';
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(x * n + y);  // 将2d的元素扯成一条线，x*行数+y
            while (!queue.isEmpty()) {
                int cur = queue.poll();
                int i = cur / n;  // 行数
                int j = cur % n;  // 列数
                if (i > 0 && grid[i - 1][j] == '1') {  // 判断上下左右四个位置
                    queue.offer((i - 1) * n + j);  // 上面位置是1
                    grid[i - 1][j] = '0'; // 加入进去，把当前位置变成0
                }
                if (i < m - 1 && grid[i + 1][j] == '1') {  // 下面位置
                    queue.offer((i + 1) * n + j);
                    grid[i + 1][j] = '0';
                }
                if (j > 0 && grid[i][j - 1] == '1') {  // 左边位置
                    queue.offer(i * n + j - 1);
                    grid[i][j - 1] = '0';
                }
                if (i < n - 1 && grid[i][j + 1] == '1') {  // 右边位置
                    queue.offer((i * n + j + 1));
                    grid[i][j + 1] = '0';
                }
            }
        }

        // BFS 优化
        private void bfs(char[][] grid, int x, int y) {
            int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};  // 上下左右四个方向
            Queue<Point> queue = new LinkedList<>();
            queue.offer(new Point(x, y));
            while (!queue.isEmpty()) {
                Point cur = queue.poll();
                for (int[] direction : directions) {  // 四个方向进行遍历
                    int newX = cur.x + direction[0];
                    int newY = cur.y + direction[1];
                    if (isValid(grid, newX, newY)) {  // 判断边界条件
                        queue.offer(new Point(newX, newY));
                        grid[newX][newY] = '0';
                    }
                }
            }
        }

        private boolean isValid(char[][] grid, int x, int y) {
            return x >= 0 && y >= 0 && x < grid.length && y < grid[0].length && grid[x][y] == '1';
        }

        static class Point {
            int x;
            int y;

            public Point(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
    }

    // 迷宫棋盘
    /*
    0代表障碍物，1代表路，求到目的地的最段路径
     */

    public class mazeOfChessBoard {

        private boolean hasPath1(char[][] maze, int[] start, int[] end) {
            int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            boolean[][] visited = new boolean[maze.length][maze[0].length];

            Queue<int[]> qu = new LinkedList<>();
            qu.offer(start);
            visited[start[0]][start[1]] = true;

            while (!qu.isEmpty()) {
                int[] cur = qu.poll();
                if (cur[0] == end[0] && cur[1] == end[1]) return true;
                for (int[] dir : dirs) {
                    int newX = cur[0] + dir[0];
                    int newY = cur[1] + dir[1];
                    if (isValid(maze, newX, newY) && !visited[newX][newY]) {
                        qu.offer(new int[]{newX, newY});
                        visited[newX][newY] = true;
                    }
                }
            }

            return false;
        }


        private boolean hasPath(char[][] maze, int[] start, int[] end) {  // 从七点走到终点
            int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};  // 上下左右四个方向

            boolean[][] visited = new boolean[maze.length][maze[0].length];  // 记录走的路径

            Queue<solution200.Point> queue = new LinkedList<>();
            queue.offer(new solution200.Point(start[0], start[1]));  // 初始化，加入起点
            visited[start[0]][start[1]] = true;

            while (!queue.isEmpty()) {
                solution200.Point cur = queue.poll();
                if (cur.x == end[0] && cur.y == end[1]) return true;  // 走到终点的时候return true
                for (int[] direction : directions) {  // 四个方向进行遍历
                    int newX = cur.x + direction[0];
                    int newY = cur.y + direction[1];
                    if (isValid(maze, newX, newY) && !visited[newX][newY]) {  // 判断边界条件, 如果没有走过，再走它
                        queue.offer(new solution200.Point(newX, newY));
                        visited[newX][newY] = true;  // 将当前节点变为true
                    }
                }
            }
            return false;
        }

        private boolean isValid(char[][] grid, int x, int y) {
            return x >= 0 && y >= 0 && x < grid.length && y < grid[0].length && grid[x][y] == '1';
        }

        class Point {
            int x;
            int y;

            public Point(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
    }

    // 127 word ladder  要考

    /**
     * A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of
     * words beginWord -> s1 -> s2 -> ... -> sk such that:
     * <p>
     * Every adjacent pair of words differs by a single letter.
     * Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
     * sk == endWord
     * Given two words, beginWord and endWord, and a dictionary wordList, return the number of words in the shortest
     * transformation sequence from beginWord to endWord, or 0 if no such sequence exists.
     * <p>
     * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
     * Output: 5
     * Explanation: One shortest transformation sequence is "hit" -> "hot" -> "dot" -> "dog" -> cog", which is 5 words long.
     * <p>
     * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
     * Output: 0
     * Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.
     */

    class solution127 {
        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            Graph graph = new Graph();
            for (String word : wordList) graph.addNode(new Node(word));  // 将所有单词加入graph
            if (!wordList.contains(beginWord)) {
                graph.addNode(new Node(beginWord));
                wordList.add(beginWord);
            }

            for (String word : wordList) {
                Node node = graph.getNode(word);
                for (int i = 0; i < word.length(); i++) {
                    char[] wordUnit = word.toCharArray();  // 每一个字母都变换
                    for (char j = 'a'; j <= 'z'; j++) {
                        wordUnit[i] = j;  // 一个字母进行一个替换
                        String temp = new String(wordUnit);
                        if (graph.getNode(temp) != null && !node.neighbors.contains(temp) && !temp.equals(word)) {
                            // 在list之中如果没有加过，不等与它本身
                            node.neighbors.add(graph.getNode(temp));
                        }
                    }
                }
            }

            // BFS
            HashSet<Node> visited = new HashSet<>();
            Queue<Node> queue = new LinkedList<>();

            visited.add(graph.getNode(beginWord));
            queue.offer(graph.getNode(beginWord));

            int res = 0;
            while (!queue.isEmpty()) {
                res++;
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    Node node = queue.poll();
                    if (node.word.equals(endWord)) return res;  // 如果碰到了endword就停止
                    for (Node neighbor : node.neighbors) {  // 在node的neighbor里面找临结点
                        if (!visited.contains(neighbor)) {  // 如果没有走过就加入queue
                            queue.offer(neighbor);
                            visited.add(neighbor);
                        }
                    }
                }

            }
            return 0;
        }

        class Graph {  // 将整个word list转化成一个图
            List<Node> nodes;
            HashMap<String, Integer> map;  // 加入了一个string后就把index也加进去，后面要找这个词就找得到了

            public Graph() {
                this.nodes = new ArrayList<>();
                this.map = new HashMap<>();
            }

            public void addNode(Node node) {  //  将node节点放入graph 之中
                map.put(node.word, nodes.size());  // 通过word在hashMap中找到index, 通过index在nodes里面找到对应的Node
                nodes.add(node);
            }

            public Node getNode(String word) {
                if (map.containsKey(word)) return nodes.get(map.get(word));
                return null;
            }
        }

        class Node {
            String word;
            List<Node> neighbors;  // 每个单词的临结点

            public Node(String word) {
                this.word = word;
                this.neighbors = new ArrayList<>();
            }
        }

        // BFS
        public int ladderlength1(String beginWord, String endWord, List<String> wordList) {
            HashSet<String> set = new HashSet<>(wordList);
            if (set.contains(beginWord)) set.remove(beginWord);
            Queue<String> queue = new LinkedList<>();
            HashMap<String, Integer> map = new HashMap<>();
            map.put(beginWord, 1);  // map的 key值是当前的单词，value是level,也就是第几次遍历
            queue.offer(beginWord);
            while (!queue.isEmpty()) {
                String word = queue.poll();
                int curLevel = map.get(word);  // 当前的层数
                for (int i = 0; i < word.length(); i++) {  // word的每个位置进行替换
                    char[] wordUnit = word.toCharArray();
                    for (char j = 'a'; j <= 'z'; j++) {  // 将a到z一个一个去填充当前的位置
                        wordUnit[i] = j;
                        String temp = new String(wordUnit);  // 替换成新的string
                        if (set.contains(temp)) {
                            // 如果set里面有当前替换过的单词
                            if (temp.equals(endWord)) return curLevel + 1;  // 如果当前遍历到最后一个单词，直接return level + 1
                            map.put(temp, curLevel + 1);  // 否则进行下次
                            queue.offer(temp);
                            set.remove(temp);
                        }
                    }
                }
            }
            return 0;
        }
    }

    // 126: word ladder2

    /**
     * A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words
     * beginWord -> s1 -> s2 -> ... -> sk such that:
     * <p>
     * Every adjacent pair of words differs by a single letter.
     * Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
     * sk == endWord
     * Given two words, beginWord and endWord, and a dictionary wordList, return all the shortest transformation sequences
     * from beginWord to endWord, or an empty list if no such sequence exists. Each sequence should be returned as a list of the words [beginWord, s1, s2, ..., sk].
     * <p>
     * Example 1:
     * <p>
     * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
     * Output: [["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
     * Explanation: There are 2 shortest transformation sequences:
     * "hit" -> "hot" -> "dot" -> "dog" -> "cog"
     * "hit" -> "hot" -> "lot" -> "log" -> "cog"
     * Example 2:
     * <p>
     * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
     * Output: []
     * Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.
     */
    class Solution126 {
        /*
        转化成无向图 -> BFS -> 树 -> DFS -> 结果
         */
        public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
            List<List<String>> res = new ArrayList<>();
            if (wordList.size() == 0) return res;
            int curNum = 1;
            int nextNum = 0;
            boolean found = false;
            Queue<String> queue = new LinkedList<>();
            HashSet<String> unvisited = new HashSet<>(wordList);  // 访问过后就不能再访问了
            HashSet<String> visited = new HashSet<>();  // 将访问的点和没有访问的区分

            HashMap<String, List<String>> map = new HashMap<>();

            queue.offer(beginWord);
            while (!queue.isEmpty()) {
                String word = queue.poll();
                curNum--;
                for (int i = 0; i < word.length(); i++) {
                    StringBuilder builder = new StringBuilder(word);
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        builder.setCharAt(i, ch);
                        String newWord = builder.toString();  // 对单词的预处理，将每个位置替换成a到z的字母，
                        if (unvisited.contains(newWord)) {
                            if (visited.add(newWord)) {  // 如果可以加入, 代表没有找过
                                nextNum++;
                                queue.offer(newWord);
                            }
                            if (map.containsKey(newWord)) {
                                map.get(newWord).add(word);
                            } else {
                                List<String> list = new ArrayList<>();
                                list.add(word);
                                map.put(newWord, list);
                            }
                            if (newWord.equals(endWord)) {
                                found = true;
                            }
                        }
                    }
                }
                if (curNum == 0) {  //  curNum: 当前的层数
                    if (found) break;
                    curNum = nextNum;
                    nextNum = 0;
                    unvisited.removeAll(visited);
                    visited.clear();
                }
            }
            dfs(res, new ArrayList<>(), map, endWord, beginWord);
            return res;
        }

        public void dfs(List<List<String>> res, List<String> list, HashMap<String, List<String>> map, String word, String start) {
            if (word.equals(start)) {
                list.add(0, start);
                res.add(new ArrayList<>(list));
                list.remove(0);
                return;
            }
            list.add(0, word);
            if (map.get(word) != null) {
                for (String s : map.get(word)) {
                    dfs(res, list, map, s, start);
                }
            }
            list.remove(0);
        }

    }

    // 匈牙利算法

    /**
     * 有m个人，n个地方，每个人分配去一个地点（吗 >= n）.地方都占满，一个萝卜一个坑
     * 若干骰子，每面都有字母，拼指定单词
     */
    class Hungry {
        int[] person;
        int[] places;

        public void match(int[][] matrix) {
            person = new int[4];
            places = new int[3];
            Arrays.fill(person, -1);
            Arrays.fill(places, -1);

            boolean[] visited;  // 判断让还是不让
            int res = 0;
            for (int i = 0; i < person.length; i++) {  // 遍历每个人去匹配places
                visited = new boolean[places.length];
                if (hungary(matrix, i, visited)) res++;
            }
        }

        public boolean hungary(int[][] matrix, int start, boolean[] visited) {
            for (int end = 0; end < places.length; end++) {
                // 没有边/已经访问过,就不能访问
                if (matrix[start][end + 4] == 0 || visited[end]) continue;
                visited[end] = true;
                // 1. 没有占用， 2.是否可以让位置（再走一遍算法，看看可不可以选其他的位置）
                if (places[end] == -1 || hungary(matrix, places[end], visited)) {
                    person[start] = end;
                    places[end] = start;
                    return true;
                }
            }
            return false;
        }
    }

    // 130 surrounded region

    /**
     * Given an m x n matrix board containing 'X' and 'O', capture all regions that are 4-directionally surrounded by 'X'.
     * <p>
     * A region is captured by flipping all 'O's into 'X's in that surrounded region.
     * <p>
     * Example 1:
     * <p>
     * Input: board = [["X","X","X","X"],
     *                 ["X","O","O","X"],
     *                 ["X","X","O","X"],
     *                 ["X","O","X","X"]]
     * Output: [["X","X","X","X"],
     *          ["X","X","X","X"],
     *          ["X","X","X","X"],
     *          ["X","O","X","X"]]
     * Explanation: Notice that an 'O' should not be flipped if:
     * - It is on the border, or
     * - It is adjacent to an 'O' that should not be flipped.
     * The bottom 'O' is on the border, so it is not flipped.
     * The other three 'O' form a surrounded region, so they are flipped.
     * Example 2:
     * <p>
     * Input: board = [["X"]]
     * Output: [["X"]]
     */

    class Solution {
        /**
         * O 在边缘位置，第一行，第一列，最后一行，最后一列,不会被围住
         *
         * @param board
         */
        public void solve(char[][] board) {
            if (board == null || board.length == 0 || board[0].length == 0) return;
            int m = board.length - 1; // 行数
            int n = board[0].length - 1; // 列数
            for (int i = 0; i <= m; i++) {
                if (board[i][0] == 'O') dfs(board, i, 0); // 第一列
                if (board[i][n] == 'O') dfs(board, i, n);  // 最后一列
            }
            for (int i = 0; i <= n; i++) {
                if (board[0][i] == 'O') dfs(board, 0, i);
                if (board[m][i] == 'O') dfs(board, m, i);
            }

            for (int i = 0; i <= m; i++) {
                for (int j = 0; j <= n; j++) {
                    if (board[i][j] == 'O') {
                        board[i][j] = 'X';  // 如果遇到该变的O, 变到X
                    } else if (board[i][j] == '1') {  // 如果遇到不该变的，变回O
                        board[i][j] = 'O';
                    }
                }
            }
        }

        private void dfs(char[][] board, int i, int j) {
            if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != 'O') return;
            board[i][j] = '1';  // 先把不该变的变成一个1
            dfs(board, i, j + 1);
            dfs(board, i, j - 1);
            dfs(board, i + 1, j);
            dfs(board, i - 1, j);
        }
    }

    // 490 The Maze

    /**
     * 在迷宫中有一个球，里面有空的空间和墙壁。球可以通过滚上，下，左或右移动，
     * 但它不会停止滚动直到撞到墙上。当球停止时，它可以选择下一个方向。
     * <p>
     * 给定球的起始位置，目的地和迷宫，确定球是否可以停在终点。
     * <p>
     * 迷宫由二维数组表示。1表示墙和0表示空的空间。你可以假设迷宫的边界都是墙。开始和目标坐标用行和列索引表示。
     * 样例
     * <p>
     * 例1:
     * <p>
     * 输入:
     * map =
     * [
     * [0,0,1,0,0],
     * [0,0,0,0,0],
     * [0,0,0,1,0],
     * [1,1,0,1,1],
     * [0,0,0,0,0]
     * ]
     * start = [0,4]
     * end = [3,2]
     * 输出:
     * false
     */
    class Solution490 {
        public boolean hasPath(int[][] maze, int[] start, int[] destination) {
            boolean[][] visited = new boolean[maze.length][maze[0].length];  // 走过的点
            int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            Queue<Point> queue = new LinkedList<>();
            queue.offer(new Point(start[0], start[1]));
            while (!queue.isEmpty()) {
                Point cur = queue.poll();
                visited[cur.x][cur.y] = true;  // 当前的位置设为访问过
                if (cur.x == destination[0] && cur.y == destination[1]) {  // 判断是否到终点
                    return true;
                }
                for (int[] direction : directions) {
                    int newX = cur.x;
                    int newY = cur.y;
                    // 如果当前的方向是可以通过的
                    while (isValid(maze, newX + direction[0], newY + direction[1])) {
                        newX += direction[0];
                        newY += direction[1];
                    }
                    if (!visited[newX][newY]) queue.offer(new Point(newX, newY));
                }
            }
            return false;
        }

        private boolean isValid(int[][] maze, int x, int y) {
            return x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0;
        }

        class Point {
            int x;
            int y;

            public Point(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }


        public boolean isValis(int[][] maze, int x, int y) {
            return x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0;
        }

    }

    // 79: word search

    /**
     * Given an m x n grid of characters board and a string word, return true if word exists in the grid.
     * <p>
     * The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally
     * or vertically neighboring. The same letter cell may not be used more than once.
     * <p>
     * Example 1:
     * <p>
     * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
     * Output: true
     * Example 2:
     * <p>
     * <p>
     * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
     * Output: true
     * Example 3:
     * <p>
     * <p>
     * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
     * Output: false
     */

    class Solution79 {
        public boolean exist(char[][] board, String word) {
            for (int i = 0; i <= board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (exist(board, i, j, word, 0)) return true;  // 每个位置都去找
                }
            }
            return false;
        }

        private boolean exist(char[][] board, int i, int j, String word, int start) {
            if (start >= word.length()) return true;  // word里的字母都找完了
            if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) return false;  // 边界条件
            if (board[i][j] == word.charAt(start++)) {
                char c = board[i][j];
                board[i][j] = '#';  // 这个地方不能再用了
                boolean res = exist(board, i + 1, j, word, start) ||
                        exist(board, i - 1, j, word, start) ||
                        exist(board, i, j + 1, word, start) ||
                        exist(board, i, j - 1, word, start);
                board[i][j] = c;
                return res;
            }
            return false;
        }
    }

    // leetcode 994: rotting oranges

    /**
     * You are given an m x n grid where each cell can have one of three values:
     * <p>
     * 0 representing an empty cell,
     * 1 representing a fresh orange, or
     * 2 representing a rotten orange.
     * Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.
     * <p>
     * Return the minimum number of minutes that must elapse until no cell has a fresh orange. If this is impossible, return -1.
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: grid = [[2,1,1],[1,1,0],[0,1,1]]
     * Output: 4
     * Example 2:
     * <p>
     * Input: grid = [[2,1,1],[0,1,1],[1,0,1]]
     * Output: -1
     * Explanation: The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens 4-directionally.
     * Example 3:
     * <p>
     * Input: grid = [[0,2]]
     * Output: 0
     * Explanation: Since there are already no fresh oranges at minute 0, the answer is just 0.
     */

    class Solution994 {

        private int rotedCnt = 0;
        private int orangeCnt = 0;

        public int orangesRotting(int[][] grid) {

            // 统计共有多少橘子
            for (int i = 0; i < grid.length; ++i) {
                for (int j = 0; j < grid[i].length; ++j) {
                    if (grid[i][j] > 0) {
                        orangeCnt += 1;
                    }
                }
            }
            //System.out.println("oranges " + orangeCnt);
            // rotting(grid) 返回这一分钟有多少橘子会腐败
            int minutes = 0;
            while (rotting(grid) > 0) {
                minutes++;
            }
            //System.out.println("minutes " + minutes);
            //System.out.println("rotedCnt " + rotedCnt);

            // 腐败过程结束，查看结果
            if (rotedCnt == orangeCnt) {
                return minutes;
            } else {
                return -1;
            }
        }

        public int rotting(int[][] grid) {

            int rottingCnt = 0;
            rotedCnt = 0;
            // 这一秒钟腐败的
            int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int i = 0; i < grid.length; ++i) {
                for (int j = 0; j < grid[i].length; ++j) {
                    if (grid[i][j] == 2) {
                        rotedCnt += 1;
                        for (int[] direction : directions) {
                            int xx = i + direction[0];
                            int yy = j + direction[1];
                            if (xx >= 0 && xx < grid.length && yy >= 0 && yy < grid[i].length && grid[xx][yy] == 1) {
                                grid[xx][yy] = 3;  // 把这一分钟要腐败的先标记为 3 防止与之前已经腐败的混淆
                                rottingCnt++;
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < grid.length; ++i) {
                for (int j = 0; j < grid[i].length; ++j) {
                    if (grid[i][j] == 3) {
                        grid[i][j] = 2;
                    }
                }
            }
            // 这一分钟的腐败过程结束，标记重置为2
            return rottingCnt;
        }
    }

    // 417 Pacific Atlantic Water Flow

    /**
     * There is an m x n rectangular island that borders both the Pacific Ocean and Atlantic Ocean. The Pacific Ocean
     * touches the island's left and top edges, and the Atlantic Ocean touches the island's right and bottom edges.
     * <p>
     * The island is partitioned into a grid of square cells. You are given an m x n integer matrix heights where
     * heights[r][c] represents the height above sea level of the cell at coordinate (r, c).
     * <p>
     * The island receives a lot of rain, and the rain water can flow to neighboring cells directly north, south,
     * east, and west if the neighboring cell's height is less than or equal to the current cell's height. Water
     * can flow from any cell adjacent to an ocean into the ocean.
     * <p>
     * Return a 2D list of grid coordinates result where result[i] = [ri, ci] denotes that rain water can flow
     * from cell (ri, ci) to both the Pacific and Atlantic oceans.
     * <p>
     * Example 1:
     * <p>
     * Input: heights = [[1,2,2,3,5],[3,2,3,4,4],[2,4,5,3,1],[6,7,1,4,5],[5,1,1,2,4]]
     * Output: [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]
     * Explanation: The following cells can flow to the Pacific and Atlantic oceans, as shown below:
     * [0,4]: [0,4] -> Pacific Ocean
     * [0,4] -> Atlantic Ocean
     * [1,3]: [1,3] -> [0,3] -> Pacific Ocean
     * [1,3] -> [1,4] -> Atlantic Ocean
     * [1,4]: [1,4] -> [1,3] -> [0,3] -> Pacific Ocean
     * [1,4] -> Atlantic Ocean
     * [2,2]: [2,2] -> [1,2] -> [0,2] -> Pacific Ocean
     * [2,2] -> [2,3] -> [2,4] -> Atlantic Ocean
     * [3,0]: [3,0] -> Pacific Ocean
     * [3,0] -> [4,0] -> Atlantic Ocean
     * [3,1]: [3,1] -> [3,0] -> Pacific Ocean
     * [3,1] -> [4,1] -> Atlantic Ocean
     * [4,0]: [4,0] -> Pacific Ocean
     * [4,0] -> Atlantic Ocean
     * Note that there are other possible paths for these cells to flow to the Pacific and Atlantic oceans.
     * Example 2:
     * <p>
     * Input: heights = [[1]]
     * Output: [[0,0]]
     * Explanation: The water can flow from the only cell to the Pacific and Atlantic oceans.
     */
    class Solution417 {

        int m, n;
        int dir[][] = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        public List<List<Integer>> pacificAtlantic(int[][] heights) {
            List<List<Integer>> res = new ArrayList<>();
            m = heights.length;
            if (m == 0) return res;
            n = heights[0].length;

            boolean[][] pac = new boolean[m][n];
            boolean[][] atl = new boolean[m][n];

            for (int i = 0; i < m; i++) {  // 从第一行开始遍历
                helper(heights, pac, i, 0);  // 太平洋是第一行
                helper(heights, atl, i, n - 1);  // 大西洋是最后一行
            }
            for (int i = 0; i < n; i++) {  // 从第一列开始遍历
                helper(heights, pac, 0, i);
                helper(heights, atl, m - 1, i);
            }
            for (int i = 0; i < m; i++) {  // 当大西洋和太平洋都是true时说明水可以留到两边
                for (int j = 0; j < n; j++) {
                    if (pac[i][j] && atl[i][j]) res.add(Arrays.asList(i, j));
                }
            }
            return res;
        }

        public void helper(int[][] heights, boolean[][] isVisited, int i, int j) {
            isVisited[i][j] = true;
            for (int[] d : dir) {
                int x = i + d[0];
                int y = j + d[1];
                // 如果下一个数字小于之前的数字，或者越界或者已经访问了，跳过
                if (x < 0 || y < 0 || x >= m || y >= n || isVisited[x][y] || heights[i][j] > heights[x][y]) continue;
                helper(heights, isVisited, x, y);  // 否则遍历到下一个位置
            }
        }
    }

    // 207： course schedule

    /**
     * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an
     * array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.
     * <p>
     * For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
     * Return true if you can finish all courses. Otherwise, return false.
     * <p>
     * Example 1:
     * <p>
     * Input: numCourses = 2, prerequisites = [[1,0]]
     * Output: true
     * Explanation: There are a total of 2 courses to take.
     * To take course 1 you should have finished course 0. So it is possible.
     * Example 2:
     * <p>
     * Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
     * Output: false
     * Explanation: There are a total of 2 courses to take.
     * To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. So it is impossible.
     */
    /*
    有环的话返回false

    用bfs
    拓扑排序：找入度为0的课，删掉

     */
    class Solution207 {
        public boolean canFinish(int numCourses, int[][] prerequisites) {
            int[] indegree = new int[numCourses];
            int res = numCourses;
            for (int[] pair : prerequisites) {
                indegree[pair[0]]++;  // 找出每个点的入度
            }
            Queue<Integer> queue = new LinkedList<>();
            for (int i = 0; i < indegree.length; i++) {
                if (indegree[i] == 0) queue.offer(i);  // 将入度为0的元素加入queue
            }
            while (!queue.isEmpty()) {
                int pre = queue.poll();  // 删除入度为0的元素
                res--;  // 有一个课程已经用过了
                for (int[] pair : prerequisites) {  // 找出当前入度为0的先修课
                    if (pair[1] == pre) {
                        indegree[pair[0]]--;
                        if (indegree[pair[0]] == 0) queue.offer(pair[0]);
                    }
                }
            }
            return res == 0;
        }

        public boolean canFinishDfs(int numCourses, int[][] prerequisites) {
            List<List<Integer>> graph = new ArrayList<>();
            for (int i = 0; i < numCourses; i ++) {
                graph.add(new ArrayList<>());
            }
            for (int[] pair: prerequisites) {
                graph.get(pair[0]).add(pair[1]);
            }
            int[] visited = new int[numCourses];  // 0: not visited, 1: visiting, 2: visited
            for (int i = 0; i < numCourses; i ++) {
                if (hasCycle(i, visited, graph)) return false;
            }
            return true;
        }

        public boolean hasCycle(int course, int[] visited, List<List<Integer>> graph) {
            if (visited[course] == 1) return true;
            if (visited[course] == 2) return false;
            visited[course] = 1;
            for (int pre: graph.get(course)) {
                if (hasCycle(pre, visited, graph)) return true;
            }
            visited[course] = 2;
            return false;
        }
    }

    //133 ：clone graph

    /**
     * Given a reference of a node in a connected undirected graph.
     * <p>
     * Return a deep copy (clone) of the graph.
     * <p>
     * Each node in the graph contains a value (int) and a list (List[Node]) of its neighbors.
     * <p>
     * class Node {
     * public int val;
     * public List<Node> neighbors;
     * }
     * <p>
     * Test case format:
     * <p>
     * For simplicity, each node's value is the same as the node's index (1-indexed). For example, the first node with
     * val == 1, the second node with val == 2, and so on. The graph is represented in the test case using an adjacency list.
     * <p>
     * An adjacency list is a collection of unordered lists used to represent a finite graph. Each list describes the
     * set of neighbors of a node in the graph.
     * <p>
     * The given node will always be the first node with val = 1. You must return the copy of the given node as a reference to the cloned graph.
     * <p>
     * Example 1:
     * <p>
     * Input: adjList = [[2,4],[1,3],[2,4],[1,3]]
     * Output: [[2,4],[1,3],[2,4],[1,3]]
     * Explanation: There are 4 nodes in the graph.
     * 1st node (val = 1)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).
     * 2nd node (val = 2)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).
     * 3rd node (val = 3)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).
     * 4th node (val = 4)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).
     */
    class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    class Solution133 {
        public Node cloneGraph(Node node) {
            if (node == null) return node;
            List<Node> nodes = getNodes(node);  // 获取所有的node
            HashMap<Node, Node> map = new HashMap<>();
            for (Node cur : nodes) {  // 将新的节点和旧的节点对应
                map.put(cur, new Node(cur.val));
            }

            for (Node cur : nodes) {
                Node newNode = map.get(cur);  // 将新节点的neighbor连起来
                for (Node neighbor : cur.neighbors) {
                    newNode.neighbors.add(map.get(neighbor));  // neighbor为旧的节点对应的新的节点
                }
            }
            return map.get(node);
        }

        public List<Node> getNodes(Node node) {
            Queue<Node> queue = new LinkedList<>();
            HashSet<Node> set = new HashSet<>();
            queue.offer(node);
            set.add(node);
            while (!queue.isEmpty()) {
                Node cur = queue.poll();
                for (Node neighbor : cur.neighbors) {
                    if (!set.contains(neighbor)) {
                        queue.offer(neighbor);
                        set.add(neighbor);
                    }
                }
            }
            return new ArrayList<>(set);
        }
    }

    // 399 : Evaluate Division

    /**
     * You are given an array of variable pairs equations and an array of real numbers values, where equations[i] = [Ai, Bi]
     * and values[i] represent the equation Ai / Bi = values[i]. Each Ai or Bi is a string that represents a single variable.
     * <p>
     * You are also given some queries, where queries[j] = [Cj, Dj] represents the jth query where you must find the answer for Cj / Dj = ?.
     * <p>
     * Return the answers to all queries. If a single answer cannot be determined, return -1.0.
     * <p>
     * Note: The input is always valid. You may assume that evaluating the queries will not result in division by
     * zero and that there is no contradiction.
     * <p>
     * Example 1:
     * <p>
     * Input: equations = [["a","b"],["b","c"]], values = [2.0,3.0], queries = [["a","c"],["b","a"],["a","e"],["a","a"],["x","x"]]
     * Output: [6.00000,0.50000,-1.00000,1.00000,-1.00000]
     * Explanation:
     * Given: a / b = 2.0, b / c = 3.0
     * queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ?
     * return: [6.0, 0.5, -1.0, 1.0, -1.0 ]
     * Example 2:
     * <p>
     * Input: equations = [["a","b"],["b","c"],["bc","cd"]], values = [1.5,2.5,5.0], queries = [["a","c"],["c","b"],["bc","cd"],["cd","bc"]]
     * Output: [3.75000,0.40000,5.00000,0.20000]
     * Example 3:
     * <p>
     * Input: equations = [["a","b"]], values = [0.5], queries = [["a","b"],["b","a"],["a","c"],["x","y"]]
     * Output: [0.50000,2.00000,-1.00000,-1.00000]
     */
    class Solution399 {

        class GraphNode {
            String den;
            double val;

            public GraphNode(String den, double val) {
                this.den = den;
                this.val = val;
            }
        }
        /*
        每个元素后面对应有几个除数 比如说 a : b, 2.0
                                    b : a, 1/ 2.0
                                    c : b, 1/ 3.0
         a -- b -- c
         */

        HashMap<String, List<GraphNode>> map = new HashMap<>();  // 每个元素后面对应有几个除数 比如说 a : b, 2.0


        public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
            map = new HashMap<>();
            for (int i = 0; i < equations.size(); i++) {
                List<String> equation = equations.get(i);
                if (!map.containsKey(equation.get(0))) {
                    map.put(equation.get(0), new ArrayList<>());  //  将每个equation以及对应的值加入map
                }
                map.get(equation.get(0)).add(new GraphNode(equation.get(1), values[i]));  //  将  a/b加入
                if (!map.containsKey(equation.get(1))) {
                    map.put(equation.get(1), new ArrayList<>());
                }
                map.get(equation.get(1)).add(new GraphNode(equation.get(0), 1 / values[i]));  // 将 b/a加入
            }

            double[] res = new double[queries.size()];
            for (int i = 0; i < res.length; i++) {
                res[i] = find(queries.get(i).get(0), queries.get(i).get(1), 1, new HashSet<>());
            }
            return res;
        }

        /*
        start = "a" end = "c" value = 1 set(empty)
            sub = start = "b" end = "c" value = 2.0 set("a")
                sub = start = "a" return -1; (X)
                sub = start = "c" end = "c" value = 6.0 set("a", "b")
         */


        private double find(String start, String end, double value, HashSet<String> visited) {  //  visited: 防止死循环
            if (visited.contains(start)) return -1;  // 如果之前找过了，返回-1
            if (!map.containsKey(start)) return -1;  // 如果map里没有这个值，返回-1

            if (start.equals(end)) return value;   // 如果start和end 相等了，返回value
            visited.add(start);  // visited加入这个数
            for (GraphNode next : map.get(start)) {
                // 计算除数
                double sub = find(next.den, end, value * next.val, visited);  //  遍历下一个相连的元素
                if (sub != -1.0) return sub;  // 如果碰到之前按访问过的返回-1
            }
            visited.remove(start);  // 走下一条路时要更新
            return -1;
        }
    }

    // 332： Reconstruct Itinerary

    /**
     * you are given a list of airline tickets where tickets[i] = [fromi, toi] represent the departure and the arrival
     * airports of one flight. Reconstruct the itinerary in order and return it.
     * <p>
     * All of the tickets belong to a man who departs from "JFK", thus, the itinerary must begin with "JFK". If there
     * are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read
     * as a single string.
     * <p>
     * For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
     * You may assume all tickets form at least one valid itinerary. You must use all the tickets once and only once.
     * <p>
     * Example 1:
     * <p>
     * Input: tickets = [["MUC","LHR"],["JFK","MUC"],["SFO","SJC"],["LHR","SFO"]]
     * Output: ["JFK","MUC","LHR","SFO","SJC"]
     * Example 2:
     * <p>
     * // JFK 可以到SFO和ATL, 优先选择ATL
     * Input: tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
     * Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
     * Explanation: Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"] but it is larger in lexical
     */
    class Solution332 {
        /*
        用到QriorityQueue 进行排序，用到DFS（HashMap）进行深度优先遍历
         */
        HashMap<String, PriorityQueue<String>> map;
        List<String> res;

        public List<String> findItinerary(List<List<String>> tickets) {
            map = new HashMap<>();
            res = new LinkedList<>();
            for (List<String> ticket : tickets) {
                // 将map进行构建，所有的ticket对应一个priorityQueue, 按照字母顺序排列
                map.computeIfAbsent(ticket.get(0), k -> new PriorityQueue<>()).add(ticket.get(1));
            }
            helper("JFK");
            return res;
        }

        public void helper(String airport) {
            // 起点或者中点不为空
            while (map.containsKey(airport) && !map.get(airport).isEmpty()) {
                helper(map.get(airport).poll());  // 按照字母排序出来最小的
            }
            //  如果正常的dfs是逆序的添加，这里需要将所有元素放在第一位
            res.add(0, airport);
        }
    }

    // 305 : number of islands 2

    /**
     * You are given an empty 2D binary grid grid of size m x n. The grid represents a map where 0's represent water
     * and 1's represent land. Initially, all the cells of grid are water cells (i.e., all the cells are 0's).
     * <p>
     * We may perform an add land operation which turns the water at position into a land. You are given an array
     * positions where positions[i] = [ri, ci] is the position (ri, ci) at which we should operate the ith operation.
     * <p>
     * Return an array of integers answer where answer[i] is the number of islands after turning the cell (ri, ci) into a land.
     * <p>
     * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You
     * may assume all four edges of the grid are all surrounded by water.
     * <p>
     * Example 1:
     * <p>
     * Input: m = 3, n = 3, positions = [[0,0],[0,1],[1,2],[2,1]]
     * Output: [1,1,2,3]
     * Explanation:
     * Initially, the 2d grid is filled with water.
     * - Operation #1: addLand(0, 0) turns the water at grid[0][0] into a land. We have 1 island.
     * - Operation #2: addLand(0, 1) turns the water at grid[0][1] into a land. We still have 1 island.
     * - Operation #3: addLand(1, 2) turns the water at grid[1][2] into a land. We have 2 islands.
     * - Operation #4: addLand(2, 1) turns the water at grid[2][1] into a land. We have 3 islands.
     * Example 2:
     * <p>
     * Input: m = 1, n = 1, positions = [[0,0]]
     * Output: [1]
     */

    class Solution305 {
        // union find 261 323
        int[][] dirs = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

        public List<Integer> numIslands2(int m, int n, int[][] positions) {
            List<Integer> res = new ArrayList<>();
            if (m <= 0 || n <= 0) return res;
            int count = 0;
            int[] root = new int[m * n];  //  union find root，二维化为一维
            Arrays.fill(root, -1);

            for (int[] pair : positions) {
                int position = n * pair[0] + pair[1];  //  二维化一维的position
                if (root[position] != -1) {
                    res.add(count);
                    continue;
                }
                root[position] = position;
                count++;  //  有几个连着的

                for (int[] dir : dirs) {  // 遍历上下左右的位置
                    int x = pair[0] + dir[0];
                    int y = pair[1] + dir[1];
                    int curPos = n * x + y;  // 一维对应的位置
                    if (x < 0 || x >= n || y < 0 || y >= n || root[curPos] == -1) {
                        continue;  // 越界了就跳过
                    }
                    int anoIsland = find(root, curPos);  // 找到另外的岛
                    if (position != anoIsland) {
                        root[position] = anoIsland;
                        position = anoIsland;
                        count--;
                    }
                }
                res.add(count);
            }
            return res;
        }

        public int find(int[] roots, int i) {
            while (i != roots[i]) i = roots[i];
            return i;
        }
    }

    // 261: Graph valid tree

    /**
     * You have a graph of n nodes labeled from 0 to n - 1. You are given an integer n and a list of edges where
     * edges[i] = [ai, bi] indicates that there is an undirected edge between nodes ai and bi in the graph.
     * <p>
     * Return true if the edges of the given graph make up a valid tree, and false otherwise.
     * <p>
     * Example 1:
     * <p>
     * 2 - 0 - 1 - 4
     * |
     * 3
     * Input: n = 5, edges = [[0,1],[0,2],[0,3],[1,4]]
     * Output: true
     * Example 2:
     * 4
     * |
     * 0 - 1 - 2
     * \  /
     * 3
     * Input: n = 5, edges = [[0,1],[1,2],[2,3],[1,3],[1,4]]
     * Output: false
     */
    class Solution261 {
        /*
        判断有没有环
        通过dfs判断当前节点访问过与否，若访问过则说明有环
         */
        public boolean validTree(int n, int[][] edges) {
            List<List<Integer>> graph = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                graph.add(new ArrayList<>());  // 初始化list
            }
            // 比如说例二，将图转化为：0: 1
            //                     1: 0, 2, 3, 4,
            //                     2: 1, 3
            //                     3: 1, 2
            //                     4: 1
            for (int i = 0; i < edges.length; i++) {
                graph.get(edges[i][0]).add(edges[i][1]);
                graph.get(edges[i][1]).add(edges[i][0]);
            }

            HashSet<Integer> visited = new HashSet<>(); // 判断当前节点访问过没
            visited.add(0);
            boolean res = helper(graph, visited, 0, -1);
            if (res == false) return false;
            return visited.size() == n;
        }

        private boolean helper(List<List<Integer>> graph, HashSet<Integer> visited, int node, int parent) {
            List<Integer> sub = graph.get(node);
            for (int v : sub) {
                if (v == parent) continue;
                if (visited.contains(v)) return false;
                visited.add(v);
                boolean res = helper(graph, visited, v, node);
                if (res == false) return false;
            }
            return true;
        }

        // union find
        /*
         *             4
         *     |
         * 0 - 1 - 2
         *     \  /
         *      3
         *
         */
        public boolean validTree2(int n, int[][] edges) {
            if (n == 1 && edges.length == 0) return true;
            if (n < 1 || edges == null || edges.length != n - 1) return false;

            int[] roots = new int[n];
            Arrays.fill(roots, -1);  // 初始化所有值为-1
            for (int[] pair : edges) {
                int x = find(roots, pair[0]);
                int y = find(roots, pair[1]);
                if (x == y) return false;
                roots[x] = y;
            }
            return true;
        }

        private int find(int[] roots, int i) {
            while (roots[i] != -1) i = roots[i];
            return i;
        }
    }

    class countPythagoreanTriples {
        static int res = 0;

        public static int countPythagoreanTriples(int tree_nodes, int[] tree_from, int[] tree_to, int x, int y, int z) {

            List<List<Integer>> graph = new ArrayList<>();
            for (int i = 0; i < tree_nodes; i++) graph.add(new ArrayList<>());  // 初始化list
            for (int i = 0; i < tree_from.length; i++) {
                graph.get(tree_from[i]).add(tree_to[i]);
                graph.get(tree_to[i]).add(tree_from[i]);
            }

            for (int i = 0; i < tree_nodes; i++) {
                if (i == x || i == y || i == z) continue;
                find(graph, i, x, y, z);
            }
            return res;
        }

        public static void find(List<List<Integer>> graph, int i, int x, int y, int z) {
            Queue<Integer> qu = new LinkedList<>();
            qu.offer(i);
            int dis = 0;
            int[] triple = new int[3];
            int num = 0;
            System.out.println(i + ": ");
            Set<Integer> visited = new HashSet<>();
            while (!qu.isEmpty()) {
                int cur = qu.poll();
                if (visited.contains(cur)) continue;
                System.out.print(cur + " ");
                visited.add(cur);
                List<Integer> sub = graph.get(cur);
                if (sub.size() > 0 && !visited.containsAll(sub)) dis += 1;
                for (int v : sub) {
                    if (v == x || v == y || v == z) {
                        triple[num] = dis;
                        num++;
                        dis = 0;
                        if (num == 3) {
                            if (isValidTriple(triple)) res++;
                            System.out.println(Arrays.toString(triple));
                            num = 0;
                            break;
                        }
                    }
                    qu.offer(v);
                }
            }
            System.out.println();
        }

        private static boolean isValidTriple(int[] triple) {
            if ((triple[0] ^ 2) + (triple[1] ^ 2) == (triple[2] ^ 2)) return true;
            if ((triple[0] ^ 2) + (triple[2] ^ 2) == (triple[1] ^ 2)) return true;
            if ((triple[1] ^ 2) + (triple[2] ^ 2) == (triple[0] ^ 2)) return true;
            return false;
        }
    }

    @Test
    public void test_1() {
        int[] from = new int[]{0, 0, 1, 1, 3, 3, 5, 7, 8};
        int[] to = new int[]{4, 1, 2, 3, 5, 7, 6, 8, 9};
        int num = countPythagoreanTriples.countPythagoreanTriples(10, from, to, 4, 6, 9);
        System.out.println(num);
    }


    // 339：nested list weight sum

    /**
     * You are given a nested list of integers nestedList. Each element is either an integer or a list whose elements
     * may also be integers or other lists.
     * <p>
     * The depth of an integer is the number of lists that it is inside of. For example, the nested list [1,[2,2],[[3],2],1]
     * has each integer's value set to its depth.
     * <p>
     * Return the sum of each integer in nestedList multiplied by its depth.
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: nestedList = [[1,1],2,[1,1]]
     * Output: 10
     * Explanation: Four 1's at depth 2, one 2 at depth 1. 1*2 + 1*2 + 2*1 + 1*2 + 1*2 = 10.
     * Example 2:
     * <p>
     * <p>
     * Input: nestedList = [1,[4,[6]]]
     * Output: 27
     * Explanation: One 1 at depth 1, one 4 at depth 2, and one 6 at depth 3. 1*1 + 4*2 + 6*3 = 27.
     */
    class Solution339 {

        // dfs
        public int depthSum(List<NestedInteger> nestedList) {
            if (nestedList == null) return 0;
            return helper(nestedList, 1);
        }

        public int helper(List<NestedInteger> nestedList, int depth) {
            int res = 0;
            for (NestedInteger nest : nestedList) {
                if (nest.isInteger()) {  // 如果当前遍历的是个数字
                    res += nest.getInteger() * depth;  //res+
                } else {  // 如果是一个list就继续往下走
                    res += helper(nest.getList(), depth + 1);
                }
            }
            return res;
        }

        // bfd
        public int depthSum2(List<NestedInteger> nestedList) {
            if (nestedList == null) return 0;
            int depth = 1;
            int res = 0;
            Queue<NestedInteger> queue = new LinkedList<>(nestedList);
            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    NestedInteger nest = queue.poll();
                    if (nest.isInteger()) {
                        res += nest.getInteger() * depth;
                    } else {
                        queue.addAll(nest.getList());
                    }
                }
            }
            return res;
        }
    }

    // 364: nested list weight sum2

    /**
     * You are given a nested list of integers nestedList. Each element is either an integer or a list whose elements
     * may also be integers or other lists.
     * <p>
     * The depth of an integer is the number of lists that it is inside of. For example, the nested list [1,[2,2],[[3],2],1]
     * has each integer's value set to its depth. Let maxDepth be the maximum depth of any integer.
     * <p>
     * The weight of an integer is maxDepth - (the depth of the integer) + 1.
     * <p>
     * Return the sum of each integer in nestedList multiplied by its weight.
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: nestedList = [[1,1],2,[1,1]]
     * Output: 8
     * Explanation: Four 1's with a weight of 1, one 2 with a weight of 2.
     * 1*1 + 1*1 + 2*2 + 1*1 + 1*1 = 8
     * Example 2:
     * <p>
     * <p>
     * Input: nestedList = [1,[4,[6]]]
     * Output: 17
     * Explanation: One 1 at depth 3, one 4 at depth 2, and one 6 at depth 1.
     * 1*3 + 4*2 + 6*1 = 17
     */
    class Solution364 {
        // 和上一题比没什么区别，是到这来的
        public int depthSumInverse(List<NestedInteger> nestedList) {
            if (nestedList == null) return 0;
            return helper(nestedList, 0);
        }

        public int helper(List<NestedInteger> nestedList, int res) {
            List<NestedInteger> nextList = new LinkedList<>();
            for (NestedInteger nest : nestedList) {
                if (nest.isInteger()) {
                    res += nest.getInteger();
                } else {
                    nextList.addAll(nest.getList());
                }
            }
            res += nextList.isEmpty() ? 0 : helper(nextList, res);
            return res;
        }
    }

    // 323: Number of Connected Compunents in an Undirected Graph

    /**
     * You have a graph of n nodes. You are given an integer n and an array edges where edges[i] = [ai, bi] indicates
     * that there is an edge between ai and bi in the graph.
     * <p>
     * Return the number of connected components in the graph.
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: n = 5, edges = [[0,1],[1,2],[3,4]]
     * Output: 2
     * Example 2:
     * <p>
     * <p>
     * Input: n = 5, edges = [[0,1],[1,2],[2,3],[3,4]]
     * Output: 1
     */
    // Union find
    class Solution323 {
        /*
        Create an adjacency list such that adj[v] contains all the adjacent vertices of vertex v.
        Initialize a hashmap or array, visited, to track the visited vertices.
        Define a counter variable and initialize it to zero.
        Iterate over each vertex in edges, and if the vertex is not already in visited, start a DFS from it. Add every vertex visited during the DFS to visited.
        Every time a new DFS starts, increment the counter variable by one.
        At the end, the counter variable will contain the number of connected components in the undirected graph.
         */
        private void dfs(List<Integer>[] adjList, int[] visited, int startNode) {
            visited[startNode] = 1;
            for (int i = 0; i < adjList[startNode].size(); i++) {
                if (visited[adjList[startNode].get(i)] == 0) {
                    dfs(adjList, visited, adjList[startNode].get(i));
                }
            }
        }

        public int countComponents(int n, int[][] edges) {
            int components = 0;
            int[] visited = new int[n];

            List<Integer>[] adjList = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                adjList[i] = new ArrayList<Integer>();
            }

            for (int i = 0; i < edges.length; i++) {
                adjList[edges[i][0]].add(edges[i][1]);
                adjList[edges[i][1]].add(edges[i][0]);
            }

            for (int i = 0; i < n; i++) {
                if (visited[i] == 0) {
                    components++;
                    dfs(adjList, visited, i);
                }
            }
            return components;
        }
    }

    // 482: Robot Room Cleaner

    /**
     * You are controlling a robot that is located somewhere in a room. The room is modeled as an m x n binary grid where
     * 0 represents a wall and 1 represents an empty slot.
     * <p>
     * The robot starts at an unknown location in the room that is guaranteed to be empty, and you do not have access
     * to the grid, but you can move the robot using the given API Robot.
     * <p>
     * You are tasked to use the robot to clean the entire room (i.e., clean every empty cell in the room). The robot
     * with the four given APIs can move forward, turn left, or turn right. Each turn is 90 degrees.
     * <p>
     * When the robot tries to move into a wall cell, its bumper sensor detects the obstacle, and it stays on the current cell.
     * <p>
     * Design an algorithm to clean the entire room using the following APIs:
     * <p>
     * interface Robot {
     * // returns true if next cell is open and robot moves into the cell.
     * // returns false if next cell is obstacle and robot stays on the current cell.
     * boolean move();
     * <p>
     * // Robot will stay on the same cell after calling turnLeft/turnRight.
     * // Each turn will be 90 degrees.
     * void turnLeft();
     * void turnRight();
     * <p>
     * // Clean the current cell.
     * void clean();
     * }
     * Note that the initial direction of the robot will be facing up. You can assume all four edges of the grid are all
     * surrounded by a wall.
     * <p>
     * Custom testing:
     * <p>
     * The input is only given to initialize the room and the robot's position internally. You must solve this problem
     * "blindfolded". In other words, you must control the robot using only the four mentioned APIs without knowing the room layout and the initial robot's position.
     * <p>
     * Example 1:
     * <p>
     * Input: room = [[1,1,1,1,1,0,1,1],[1,1,1,1,1,0,1,1],[1,0,1,1,1,1,1,1],[0,0,0,1,0,0,0,0],[1,1,1,1,1,1,1,1]], row = 1, col = 3
     * Output: Robot cleaned all rooms.
     * Explanation: All grids in the room are marked by either 0 or 1.
     * 0 means the cell is blocked, while 1 means the cell is accessible.
     * The robot initially starts at the position of row=1, col=3.
     * From the top left corner, its position is one row below and three columns right.
     * Example 2:
     * <p>
     * Input: room = [[1]], row = 0, col = 0
     * Output: Robot cleaned all rooms.
     */
    class Solution489 {

        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        public void cleanRoom(Robot robot) {
            backtracking(robot, 0, 0, 0, new HashSet<>());
        }

        public void backtracking(Robot robot, int x, int y, int curDir, HashSet<String> visited) {
            visited.add(x + "-" + y);
            robot.clean();
            for (int i = 0; i < 4; i++) {
                int nextDir = (curDir + i) % 4;
                int newX = x + dirs[nextDir][0];
                int newY = y + dirs[nextDir][1];
                if (!visited.contains(newX + "-" + newY) && robot.move()) {
                    backtracking(robot, newX, newY, nextDir, visited);
                }
                robot.turnRight();
            }
            robot.turnRight();
            robot.turnRight();
            robot.move();
            robot.turnRight();
            robot.turnRight();
        }

        interface Robot {
            // Returns true if the cell in front is open and robot moves into the cell.
            // Returns false if the cell in front is blocked and robot stays in the current cell.
            public boolean move();

            // Robot will stay in the same cell after calling turnLeft/turnRight.
            // Each turn will be 90 degrees.
            public void turnLeft();

            public void turnRight();

            // Clean the current cell.
            public void clean();
        }
    }

    // 529: Minesweeper

    /**
     * Let's play the minesweeper game (Wikipedia, online game)!
     * <p>
     * You are given an m x n char matrix board representing the game board where:
     * <p>
     * 'M' represents an unrevealed mine,
     * 'E' represents an unrevealed empty square,
     * 'B' represents a revealed blank square that has no adjacent mines (i.e., above, below, left, right, and all 4 diagonals),
     * digit ('1' to '8') represents how many mines are adjacent to this revealed square, and
     * 'X' represents a revealed mine.
     * You are also given an integer array click where click = [clickr, clickc] represents the next click position among
     * all the unrevealed squares ('M' or 'E').
     * <p>
     * Return the board after revealing this position according to the following rules:
     * <p>
     * If a mine 'M' is revealed, then the game is over. You should change it to 'X'.
     * If an empty square 'E' with no adjacent mines is revealed, then change it to a revealed blank 'B' and all of its
     * adjacent unrevealed squares should be revealed recursively.
     * If an empty square 'E' with at least one adjacent mine is revealed, then change it to a digit ('1' to '8')
     * representing the number of adjacent mines.
     * Return the board when no more squares will be revealed.
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: board = [["E","E","E","E","E"],
     * ["E","E","M","E","E"],
     * ["E","E","E","E","E"],
     * ["E","E","E","E","E"]], click = [3,0]
     * Output: [["B","1","E","1","B"],
     * ["B","1","M","1","B"],
     * ["B","1","1","1","B"],
     * ["B","B","B","B","B"]]
     * Example 2:
     * <p>
     * <p>
     * Input: board = [["B","1","E","1","B"],
     * ["B","1","M","1","B"],
     * ["B","1","1","1","B"],
     * w["B","B","B","B","B"]], click = [1,2]
     * Output: [["B","1","E","1","B"],
     * ["B","1","X","1","B"],
     * ["B","1","1","1","B"],
     * ["B","B","B","B","B"]]
     */
    class Solution529 {
        public char[][] updateBoard(char[][] board, int[] click) {
            int x = click[0];
            int y = click[1];  // 找到坐标
            if (board[x][y] == 'M') {
                board[x][y] = 'X';  //点到地雷
                return board;
            }
            dfs(board, x, y);
            return board;
        }

        public void dfs(char[][] board, int x, int y) {
            if (x < 0 || y < 0 || x >= board.length || y >= board[0].length || board[x][y] != 'E') {
                return;  // 如果越界或者当前的位置是空的直接返回
            }
            int mine = findMines(board, x, y);  // 找当前地雷的数量
            if (mine == 0) {  // 如果当前位置四周没有雷
                board[x][y] = 'B';  // 把当前位置变成blank
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        dfs(board, x + i, y + j);  // 四周进行dfs遍历
                    }
                }
            } else {
                board[x][y] = (char) ('0' + mine);  // 如果四周有雷，变成雷的个数
            }

        }

        // 找地雷
        public int findMines(char[][] board, int x, int y) {
            int count = 0;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int x1 = x + i;
                    int y1 = y + j;
                    if (x1 < 0 || y1 < 0 || x1 >= board.length || y1 >= board[0].length) {
                        continue;
                    }
                    if (board[x1][y1] == 'M') count++;
                }
            }
            return count;
        }
    }

    // 505 the maze 2

    /**
     * There is a ball in a maze with empty spaces (represented as 0) and walls (represented as 1). The ball can go
     * through the empty spaces by rolling up, down, left or right, but it won't stop rolling until hitting a wall.
     * When the ball stops, it could choose the next direction.
     * <p>
     * Given the m x n maze, the ball's start position and the destination, where start = [startrow, startcol] and
     * destination = [destinationrow, destinationcol], return the shortest distance for the ball to stop at the destination. If the ball cannot stop at destination, return -1.
     * <p>
     * The distance is the number of empty spaces traveled by the ball from the start position (excluded) to the
     * destination (included).
     * <p>
     * You may assume that the borders of the maze are all walls (see examples).
     * <p>
     * <p>
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: maze = [[0,0,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[1,1,0,1,1],[0,0,0,0,0]], start = [0,4], destination = [4,4]
     * Output: 12
     * Explanation: One possible way is : left -> down -> left -> down -> right -> down -> right.
     * The length of the path is 1 + 1 + 3 + 1 + 2 + 2 + 2 = 12.
     * Example 2:
     * <p>
     * <p>
     * Input: maze = [[0,0,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[1,1,0,1,1],[0,0,0,0,0]], start = [0,4], destination = [3,2]
     * Output: -1
     * Explanation: There is no way for the ball to stop at the destination. Notice that you can pass through the
     * destination but you cannot stop there.
     * Example 3:
     * <p>
     * Input: maze = [[0,0,0,0,0],[1,1,0,0,1],[0,0,0,0,0],[0,1,0,0,1],[0,1,0,0,0]], start = [4,3], destination = [0,1]
     * Output: -1
     */
    class Solution505 {
        public int shortestDistance(int[][] maze, int[] start, int[] destination) {
            // 用一个dists数组来表示到每个点的距离
            int[][] dists = new int[maze.length][maze[0].length];
            int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            for (int[] dist : dists) {
                Arrays.fill(dist, -1);  // 每个位置都初始化为-1
            }
            dists[start[0]][start[1]] = 0;  // start点距离为0

            Queue<Point> queue = new LinkedList<>();
            queue.offer(new Point(start[0], start[1]));
            while (!queue.isEmpty()) {  // BFS
                Point cur = queue.poll();
                for (int[] direction : directions) {  // 四个方向都去走
                    int newX = cur.x;
                    int newY = cur.y;
                    int dist = dists[newX][newY];
                    // 如果当前的方向是可以通过的, 相应位置的dist+1
                    while (isValid(maze, newX + direction[0], newY + direction[1])) {
                        newX += direction[0];
                        newY += direction[1];
                        dist++;
                    }
                    // 如果dist大于了新的点的dist值，不执行（不倒回去）
                    if (dists[newX][newY] == -1 || dist < dists[newX][newY]) {
                        queue.offer(new Point(newX, newY));
                        dists[newX][newY] = dist;
                    }
                }
            }
            return dists[destination[0]][destination[1]];
        }

        private boolean isValid(int[][] maze, int x, int y) {
            return x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0;
        }

        class Point {
            int x;
            int y;

            public Point(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
    }

    // 499：

    /**
     * There is a ball in a maze with empty spaces (represented as 0) and walls (represented as 1). The ball can go
     * through the empty spaces by rolling up, down, left or right, but it won't stop rolling until hitting a wall.
     * When the ball stops, it could choose the next direction. There is also a hole in this maze. The ball will drop
     * into the hole if it rolls onto the hole.
     * <p>
     * Given the m x n maze, the ball's position ball and the hole's position hole, where ball = [ballrow, ballcol]
     * and hole = [holerow, holecol], return a string instructions of all the instructions that the ball should follow
     * to drop in the hole with the shortest distance possible. If there are multiple valid instructions, return the
     * lexicographically minimum one. If the ball can't drop in the hole, return "impossible".
     * <p>
     * If there is a way for the ball to drop in the hole, the answer instructions should contain the characters 'u'
     * (i.e., up), 'd' (i.e., down), 'l' (i.e., left), and 'r' (i.e., right).
     * <p>
     * The distance is the number of empty spaces traveled by the ball from the start position (excluded) to the
     * destination (included).
     * <p>
     * You may assume that the borders of the maze are all walls (see examples).
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: maze = [[0,0,0,0,0],[1,1,0,0,1],[0,0,0,0,0],[0,1,0,0,1],[0,1,0,0,0]], ball = [4,3], hole = [0,1]
     * Output: "lul"
     * Explanation: There are two shortest ways for the ball to drop into the hole.
     * The first way is left -> up -> left, represented by "lul".
     * The second way is up -> left, represented by 'ul'.
     * Both ways have shortest distance 6, but the first way is lexicographically smaller because 'l' < 'u'. So the output is "lul".
     * Example 2:
     * <p>
     * <p>
     * Input: maze = [[0,0,0,0,0],[1,1,0,0,1],[0,0,0,0,0],[0,1,0,0,1],[0,1,0,0,0]], ball = [4,3], hole = [3,0]
     * Output: "impossible"
     * Explanation: The ball cannot reach the hole.
     * Example 3:
     * <p>
     * Input: maze = [[0,0,0,0,0,0,0],[0,0,1,0,0,1,0],[0,0,0,0,1,0,0],[0,0,0,0,0,0,1]], ball = [0,4], hole = [3,5]
     * Output: "dldr"
     */
    class Solution499 {
        public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
            // 用一个dists数组来表示到每个点的距离
            int[][] dists = new int[maze.length][maze[0].length];
            int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            String[] ori = new String[]{"d", "u", "r", "L"};  // 四个方向

            for (int[] dist : dists) {
                Arrays.fill(dist, Integer.MAX_VALUE);  // 每个位置都初始化为MAX_VALUE
            }
            dists[ball[0]][ball[1]] = 0;  // start点距离为0
            String[][] res = new String[maze.length][maze[0].length];
            for (String[] s : res) {
                Arrays.fill(s, "impossible");  // 每个点都初始化为不可能
            }
            res[ball[0]][ball[1]] = "";

            Queue<Point> queue = new LinkedList<>();
            queue.offer(new Point(ball[0], ball[1]));
            while (!queue.isEmpty()) {  // BFS
                Point cur = queue.poll();
                for (int i = 0; i < 4; i++) {  // 四个方向都去走
                    int newX = cur.x;
                    int newY = cur.y;
                    int dist = dists[newX][newY];
                    String curOri = res[newX][newY];
                    curOri += ori[i];
                    // 如果当前的方向是可以通过的, 相应位置的dist+1
                    while (isValid(maze, newX + directions[i][0], newY + directions[i][1])) {
                        if (newX == hole[0] && newY == hole[1]) {
                            break;
                        }
                        newX += directions[i][0];
                        newY += directions[i][1];
                        dist++;
                    }
                    if (dist <= dists[newX][newY]) {
                        if (dist < dists[newX][newY]) {
                            dists[newX][newY] = dist;
                            res[newX][newY] = curOri;
                        } else if (curOri.compareTo(res[newX][newY]) < 0) {
                            res[newX][newY] = curOri;
                        }
                        queue.offer(new Point(newX, newY));
                    }
                }
            }
            return res[hole[0]][hole[1]];
        }

        private boolean isValid(int[][] maze, int x, int y) {
            return x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0;
        }

        class Point {
            int x;
            int y;

            public Point(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
    }


    // 329

    /**
     * 329. Longest Increasing Path in a Matrix
     * Hard
     * <p>
     * 7247
     * <p>
     * 111
     * <p>
     * Add to List
     * <p>
     * Share
     * Given an m x n integers matrix, return the length of the longest increasing path in matrix.
     * <p>
     * From each cell, you can either move in four directions: left, right, up, or down. You may not move
     * diagonally or move outside the boundary (i.e., wrap-around is not allowed).
     * <p>
     * <p>
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: matrix = [[9,9,4],[6,6,8],[2,1,1]]
     * Output: 4
     * Explanation: The longest increasing path is [1, 2, 6, 9].
     * Example 2:
     * <p>
     * <p>
     * Input: matrix = [[3,4,5],[3,2,6],[2,2,1]]
     * Output: 4
     * Explanation: The longest increasing path is [3, 4, 5, 6]. Moving diagonally is not allowed.
     * Example 3:
     * <p>
     * Input: matrix = [[1]]
     * Output: 1
     */
    class Solution329 {
        public int longestIncreasingPath(int[][] matrix) {
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
            int m = matrix.length;
            int n = matrix[0].length;
            int[][] cache = new int[m][n];  // 使用cache来存储当前位置的最小值，避免重复计算
            int res = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    res = Math.max(res, dfs(matrix, Integer.MIN_VALUE, cache, m, n, i, j));
                }
            }
            return res;
        }

        public int dfs(int[][] matrix, int min, int[][] cache, int m, int n, int i, int j) {
            if (i < 0 || i >= m || j < 0 || j >= n || matrix[i][j] <= min) return 0;
            if (cache[i][j] != 0) return cache[i][j];  // 如果当前位置值之前计算过直接返回cache
            min = matrix[i][j];  // 更新最小值
            int left = dfs(matrix, min, cache, m, n, i - 1, j) + 1;  // dfs
            int right = dfs(matrix, min, cache, m, n, i + 1, j) + 1;
            int up = dfs(matrix, min, cache, m, n, i, j + 1) + 1;
            int down = dfs(matrix, min, cache, m, n, i, j - 1) + 1;
            int max = Math.max(left, Math.max(right, Math.max(up, down)));
            cache[i][j] = max;  // 更新当前位置的cache
            return max;
        }
    }

    public static void main(String[] args) {
        String s = "hit";
        for (int i = 0; i < s.length(); i++) {
            StringBuilder sb;
            for (int j = 0; j < 26; j++) {
                char c = (char) (j + 'a');
                sb = new StringBuilder(s);
                sb.replace(i, i + 1, c + "");
                System.out.println(sb.toString());
            }
        }
    }

    // 407 rain water 2:

    /**
     * Given an m x n integer matrix heightMap representing the height of each unit cell in a 2D elevation map, return
     * the volume of water it can trap after raining.
     * Example 1:
     * <p>
     * Input: heightMap = [[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]]
     * Output: 4
     * Explanation: After the rain, water is trapped between the blocks.
     * We have two small ponds 1 and 3 units trapped.
     * The total volume of water trapped is 4.
     * Example 2:
     * <p>
     * <p>
     * Input: heightMap = [[3,3,3,3,3],[3,2,2,2,3],[3,2,1,2,3],[3,2,2,2,3],[3,3,3,3,3]]
     * Output: 10
     */
    class Solution407 {
        // 相比之前的trapping rain water 变成了三维的数组
        public int trapRainWater(int[][] heightMap) {
            if (heightMap == null || heightMap.length <= 1 || heightMap[0].length <= 1) return 0;
            int m = heightMap.length;
            int n = heightMap[0].length;
            boolean[][] visited = new boolean[m][n];
            // pq里存的是每个位置的index: i, j, 和高度height
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
            // 把四周加入priorityQueue, 因为四周永远装不了水
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == 0 || j == 0 || i == m - 1 || j == n - 1) {
                        pq.offer(new int[]{i, j, heightMap[i][j]});
                        visited[i][j] = true;
                    }
                }
            }
            int res = 0;
            int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, -1}, {0, 1}};
            while (!pq.isEmpty()) {
                // 将当前最短的柱子拿出来
                int[] cell = pq.poll();
                // 上下左右进行遍历
                for (int[] dir : dirs) {
                    int x = cell[0] + dir[0];
                    int y = cell[1] + dir[1];
                    // 如果没有越界或者之前没有遍历过
                    if (x >= 0 && x < m && y >= 0 && y < n && !visited[x][y]) {
                        visited[x][y] = true;
                        // cell[2] - heightMap[x][y] 指当前最短的柱子和新柱子的高度差
                        // 如果小于0就不加，大于0就加
                        res += Math.max(0, cell[2] - heightMap[x][y]);
                        // 将当前高的柱子的高度加入pq, 因为当前位置已经装了水，之后的高度和装了水的高度一样高了
                        pq.offer(new int[]{x, y, Math.max(cell[2], heightMap[x][y])});
                    }
                }
            }
            return res;
        }
    }

    // 841： Keys and rooms

    /**
     * There are n rooms labeled from 0 to n - 1 and all the rooms are locked except for room 0. Your goal is to visit
     * all the rooms. However, you cannot enter a locked room without having its key.
     * <p>
     * When you visit a room, you may find a set of distinct keys in it. Each key has a number on it, denoting which
     * room it unlocks, and you can take all of them with you to unlock the other rooms.
     * <p>
     * Given an array rooms where rooms[i] is the set of keys that you can obtain if you visited room i, return true
     * if you can visit all the rooms, or false otherwise.
     * <p>
     * Example 1:
     * <p>
     * Input: rooms = [[1],[2],[3],[]]
     * Output: true
     * Explanation:
     * We visit room 0 and pick up key 1.
     * We then visit room 1 and pick up key 2.
     * We then visit room 2 and pick up key 3.
     * We then visit room 3.
     * Since we were able to visit every room, we return true.
     * Example 2:
     * <p>
     * Input: rooms = [[1,3],[3,0,1],[2],[0]]
     * Output: false
     * Explanation: We can not enter room number 2 since the only key that unlocks it is in that room.
     */
    class Solution841 {
        public boolean canVisitAllRooms(List<List<Integer>> rooms) {
            Queue<Integer> qu = new LinkedList<>();
            boolean[] visited = new boolean[rooms.size()];
            visited[0] = true;
            qu.offer(0);

            // General BFS
            while (!qu.isEmpty()) {
                int cur = qu.poll();
                for (int i : rooms.get(cur)) {
                    if (visited[i] == false) {
                        qu.offer(i);
                        if (i != cur) visited[i] = true;
                    }
                }
            }
            for (boolean v : visited) {
                if (v == false) return false;
            }
            return true;
        }
    }

    // 886 Possible Partition

    /**
     * We want to split a group of n people (labeled from 1 to n) into two groups of any size. Each person may dislike
     * some other people, and they should not go into the same group.
     * <p>
     * Given the integer n and the array dislikes where dislikes[i] = [ai, bi] indicates that the person labeled ai
     * does not like the person labeled bi, return true if it is possible to split everyone into two groups in this way.
     * <p>
     * Example 1:
     * <p>
     * Input: n = 4, dislikes = [[1,2],[1,3],[2,4]]
     * Output: true
     * Explanation: group1 [1,4] and group2 [2,3].
     * Example 2:
     * <p>
     * Input: n = 3, dislikes = [[1,2],[1,3],[2,3]]
     * Output: false
     * Example 3:
     * <p>
     * Input: n = 5, dislikes = [[1,2],[2,3],[3,4],[4,5],[1,5]]
     * Output: false
     */
    class Solution886 {
        public boolean possibleBipartition(int n, int[][] dislikes) {
            Map<Integer, ArrayList<Integer>> graph = new HashMap<>();
            for (int[] like : dislikes) {
                if (!graph.containsKey(like[0])) {
                    graph.put(like[0], new ArrayList<>());
                }
                if (!graph.containsKey(like[1])) {
                    graph.put(like[1], new ArrayList<>());
                }
                graph.get(like[0]).add(like[1]);
                graph.get(like[1]).add(like[0]);
            }
            int[] color = new int[n + 1];
            Arrays.fill(color, -1);
            /*
            最开始的时候每个节点都没有标记颜色。我们逐个扫描图中的节点，如果发现一个节点p没有被标记过颜色，那么给他标记颜色c1。
            接着我们看这个节点p都有哪些节点和它相邻，如果相邻的节点q之前没有标记过颜色，我们给它们标记成c2。这是因为p和q相邻，
            表示他们之间相互不喜欢，不能分在同一组，因此不能用相同的颜色标记。如果节点q之前已经被标记过颜色了，那么看之前标记的是什么颜色。
            如果之前标记的是c2，那么没有必要再重复标记了。如果之前标记的颜色是c1，那么出现冲突了，一个人不能分到两个组里，
            因此我们不能按照这个分组规则把所有人分成两组。
             */
            for (int i = 1; i <= n; ++i) {
                if (color[i] == -1 && !dfs(color, graph, i, 0)) {
                    return false;
                }
            }
            return true;
        }

        private boolean dfs(int[] colors, Map<Integer, ArrayList<Integer>> graph, int cur, int color) {
            if (colors[cur] >= 0) return colors[cur] == color;
            colors[cur] = color;
            for (int neighbor : graph.getOrDefault(cur, new ArrayList<>())) {
                if (!dfs(colors, graph, neighbor, 1 - color)) return false;
            }
            return true;
        }
    }

    // 997: find the town judge

    /**
     * In a town, there are n people labeled from 1 to n. There is a rumor that one of these people is secretly the town judge.
     * <p>
     * If the town judge exists, then:
     * <p>
     * The town judge trusts nobody.
     * Everybody (except for the town judge) trusts the town judge.
     * There is exactly one person that satisfies properties 1 and 2.
     * You are given an array trust where trust[i] = [ai, bi] representing that the person labeled ai trusts the person labeled bi.
     * <p>
     * Return the label of the town judge if the town judge exists and can be identified, or return -1 otherwise.
     * <p>
     * Example 1:
     * <p>
     * Input: n = 2, trust = [[1,2]]
     * Output: 2
     * Example 2:
     * <p>
     * Input: n = 3, trust = [[1,3],[2,3]]
     * Output: 3
     * Example 3:
     * <p>
     * Input: n = 3, trust = [[1,3],[2,3],[3,1]]
     * Output: -1
     */
    class Solution997 {
        public int findJudge(int N, int[][] trust) {
            if (trust.length < N - 1) {
                return -1;
            }

            int[] trustScores = new int[N + 1];
            // 一个人如果信任另一个人，这个人得分减一
            // 一个人如果被另一个信任，这个人得分加一
            for (int[] relation : trust) {
                trustScores[relation[0]]--;
                trustScores[relation[1]]++;
            }
            // 最后法官的分数一定是N-1， 因为他被其他所有人信任
            for (int i = 1; i <= N; i++) {
                if (trustScores[i] == N - 1) {
                    return i;
                }
            }
            return -1;
        }
    }

    // dfs 求解最短城市距离问题

    /**
     * intput:
     * graph: [[1, 3, 5], [1, 4, 6], [2, 4, 5], [6, 1, 3], [3, 1, 2], [2, 4, 1], [6, 1, 2]], 二维数组，其中第一个参数为起点，第二个为
     * 与这个点相连的点，最后一个是这条路径的权重
     * int dist: 想要到的目的地
     * int n: 共有多少个城市
     * int source: 起点
     */
    class dfs {
        int min = Integer.MAX_VALUE;

        public int solution(int[][] map, int n, int source, int dist) {
            int[][] graph = new int[n][n];
            for (int i = 0; i < graph.length; i++) {
                Arrays.fill(graph[i], Integer.MAX_VALUE);
            }
            for (int i = 0; i < map.length; i++) {
                graph[map[i][0]][map[i][1]] = map[i][2];
            }
            dfs(graph, source, dist, 0, new int[n]);
            return min;
        }

        public void dfs(int[][] graph, int cur, int dist, int sum, int[] book) {
            if (sum > min) return;
            if (cur == dist) {
                min = Math.min(min, sum);
                return;
            }
            for (int i = 0; i < graph.length; i++) {
                if (graph[cur][i] != Integer.MAX_VALUE && book[i] == 0) {
                    book[i] = 1;
                    dfs(graph, i, dist, sum + graph[cur][i], book);
                    book[i] = 0;
                }
            }
        }
    }

    // leetcode 2359 Find closest Node at given two nodes

    /**
     * You are given a directed graph of n nodes numbered from 0 to n - 1, where each node has at most one outgoing edge.
     * <p>
     * The graph is represented with a given 0-indexed array edges of size n, indicating that there is a directed edge
     * from node i to node edges[i]. If there is no outgoing edge from i, then edges[i] == -1.
     * <p>
     * You are also given two integers node1 and node2.
     * <p>
     * Return the index of the node that can be reached from both node1 and node2, such that the maximum between the
     * distance from node1 to that node, and from node2 to that node is minimized. If there are multiple answers,
     * return the node with the smallest index, and if no possible answer exists, return -1.
     * <p>
     * Note that edges may contain cycles.
     * <p>
     * Input: edges = [2,2,3,-1], node1 = 0, node2 = 1
     * Output: 2
     * Explanation: The distance from node 0 to node 2 is 1, and the distance from node 1 to node 2 is 1.
     * The maximum of those two distances is 1. It can be proven that we cannot get a node with a smaller
     * maximum distance than 1, so we return node 2.
     * <p>
     * Input: edges = [1,2,-1], node1 = 0, node2 = 2
     * Output: 2
     * Explanation: The distance from node 0 to node 2 is 2, and the distance from node 2 to itself is 0.
     * The maximum of those two distances is 2. It can be proven that we cannot get a node with a smaller
     * maximum distance than 2, so we return node 2.
     */
    class Solution2359 {
        /*
        In this approach, we begin BFS traversals for both node1 and node2 to compute the shortest distances from node1
         and node2 to all other nodes. We store the results in arrays labeled dist1 and dist2, respectively. We also set
         two variables: minDistNode = -1, which is the answer to our problem, and minDistTillNow, which is the maximum
          between the distances from node1 to minDistNode and from node2 to minDistNode.

        Now, we iterate over all of the nodes from 0 to n - 1. For each node, say currNode we check if the maximum distance
        from node1 and node2 is smaller than the other nodes previously seen. If minDistTillNow > max(dist1[currNode],
        dist2[currNode]), we have a node currNode with a smaller maximum value between the distances from node1 to
        currNode and from node2 to currNode. In this case, we update the minDistTillNow to minDistTillNow = max(dist1[currNode],
        dist2[currNode]) and update the minDistNode to minDistNode = currNode.

        Otherwise, if minDistTillNow <= max(dist1[currNode], dist2[currNode]) we do not do anything. We return minDistNode
        at the end of all the iterations over every node. We would never update the variable currNode if we couldn't reach
        any node that is reachable from node1 and node2. In that case, we'd return the currNode variable with its original value of -1.
         */
        public int closestMeetingNode(int[] edges, int node1, int node2) {
            int n = edges.length;
            int[] dist1 = new int[n], dist2 = new int[n];  // dist1 and dist2 to store the smallest dist from the node1 and node2 to all the other nodes
            Arrays.fill(dist1, Integer.MAX_VALUE);
            Arrays.fill(dist2, Integer.MAX_VALUE);
            bfs(edges, node1, dist1);
            bfs(edges, node2, dist2);

            int minNode = -1, minDist = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (minDist > Math.max(dist1[i], dist2[i])) {
                    minDist = Math.max(dist1[i], dist2[i]);
                    minNode = i;
                }
            }
            return minNode;
        }

        // run bfs to get the distance from node to all the other nodes
        public void bfs(int[] edges, int node, int[] dist) {
            Queue<Integer> qu = new LinkedList<>();
            Set<Integer> visited = new HashSet<>();
            qu.offer(node);
            visited.add(node);
            dist[node] = 0;
            while (!qu.isEmpty()) {
                int cur = qu.poll();
                if (visited.contains(cur)) continue;
                visited.add(cur);
                int neighbor = edges[cur];
                if (neighbor != -1 && !visited.contains(neighbor)) {
                    dist[neighbor] = 1 + dist[cur];
                    qu.offer(neighbor);
                }
            }
        }
    }

    // 684: redundant connections

    /**
     * In this problem, a tree is an undirected graph that is connected and has no cycles.
     * <p>
     * You are given a graph that started as a tree with n nodes labeled from 1 to n, with one additional edge added.
     * The added edge has two different vertices chosen from 1 to n, and was not an edge that already existed. The graph
     * is represented as an array edges of length n where edges[i] = [ai, bi] indicates that there is an edge between nodes
     * ai and bi in the graph.
     * <p>
     * Return an edge that can be removed so that the resulting graph is a tree of n nodes. If there are multiple answers,
     * return the answer that occurs last in the input.
     */

    class Solution684 {

        // 查并集
        int[] parent;

        public int[] findRedundantConnection(int[][] edges) {
            int len = edges.length;
            // 初始化父节点们 （每个节点的父节点为他自己）
            parent = new int[len + 1];
            for (int i = 0; i <= len; i++) {
                parent[i] = i;
            }
            for (int i = 0; i < len; i++) {
                int[] cur = edges[i];
                int a = cur[0];
                int b = cur[1];
                // if the parents of a and b are not the same, combine them to the same set
                if (find(a) != find(b)) union(a, b);
                    // 如果两个当前节点的根相同了代表找到环了，返回这个边
                else return cur;
            }
            return new int[2];
        }

        public int find(int k) {
            if (parent[k] != k) parent[k] = find(parent[k]);
            return parent[k];
        }

        public void union(int a, int b) {
            int pa = find(a);  // find the root of a
            int pb = find(b);  // find the root of b
            parent[pa] = pb;  // 将a的根的根设为b的根，合并两个集合
        }
    }

    // Number of provinces

    /**
     * There are n cities. Some of them are connected, while some are not. If city a is connected directly with city b,
     * and city b is connected directly with city c, then city a is connected indirectly with city c.
     * <p>
     * A province is a group of directly or indirectly connected cities and no other cities outside of the group.
     * <p>
     * You are given an n x n matrix isConnected where isConnected[i][j] = 1 if the ith city and the jth city are
     * directly connected, and isConnected[i][j] = 0 otherwise.
     * <p>
     * Return the total number of provinces.
     * <p>
     * Input: isConnected = [[1,0,0],[0,1,0],[0,0,1]]
     * Output: 3
     * <p>
     * Input: isConnected = [[1,1,0],[1,1,0],[0,0,1]]
     * Output: 2
     */
    class Solution547 {
        public int findCircleNum(int[][] isConnected) {
            boolean[] isVisited = new boolean[isConnected.length];
            Arrays.fill(isVisited, false);
            int res = 0;
            for (int i = 0; i < isConnected.length; i++) {
                if (!isVisited[i]) {
                    res++;
                    dfs(isConnected, isVisited, i);
                }
            }
            return res;
        }

        public void dfs(int[][] map, boolean[] visited, int idx) {
            for (int j = 0; j < map.length; j++) {
                if (map[idx][j] == 1 && !visited[j]) {
                    visited[j] = true;
                    dfs(map, visited, j);
                }
            }
        }
    }

    // 302： Smallest Rectangle Enclosing Black Pixels

    /**
     * You are given an m x n binary matrix image where 0 represents a white pixel and 1 represents a black pixel.
     * <p>
     * The black pixels are connected (i.e., there is only one black region). Pixels are connected horizontally and vertically.
     * <p>
     * Given two integers x and y that represents the location of one of the black pixels, return the area of the
     * smallest (axis-aligned) rectangle that encloses all black pixels.
     * <p>
     * You must write an algorithm with less than O(mn) runtime complexity
     */
    class Solution302 {
        // 全局变量，在dfs时更新
        int l, r, u, d;

        public int minArea(char[][] image, int x, int y) {
            if (image.length == 0 || image[0].length == 0) return 0;
            l = y;
            r = y;
            u = x;
            d = x;
            dfs(image, x, y);
            // 包含黑色的区域就是这个矩形的面积
            return (r - l) * (d - u);
        }

        public void dfs(char[][] image, int x, int y) {
            if (x >= image.length || y >= image[0].length || x < 0 || y < 0) return;
            if (image[x][y] == '0') return;
            image[x][y] = '0';
            l = Math.min(l, y);
            r = Math.max(r, y + 1);
            u = Math.min(u, x);
            d = Math.max(x + 1, d);
            dfs(image, x + 1, y);
            dfs(image, x - 1, y);
            dfs(image, x, y + 1);
            dfs(image, x, y - 1);
        }
    }

    /**
     * Currency Exchange
     * <p>
     * Programming challenge description:
     * <p>
     * Given
     * <p>
     * - A list of foreign exchange rates
     * <p>
     * - A selected curreny
     * <p>
     * - A target currency
     * <p>
     * Your goal is to calculate the max amount of the target currency to 1 unit of the selected currency through the FX transactions. Assume all transactions are free and done immediately. If you cannot finish the exchange, return -1.0.
     * <p>
     * e.g.
     * <p>
     * Input:
     * <p>
     * You will be provided a list of fx rates, a selected currency, and a target currency.
     * <p>
     * For example:
     * <p>
     * FX rates list:
     * <p>
     * USD to JPY 1 to 109
     * <p>
     * USD to GBP 1 to 0.71
     * <p>
     * GBP to JPY 1 to 155
     * <p>
     * Original currency: USD
     * <p>
     * Target currency : JPY
     * <p>
     * Output:
     * <p>
     * Calculated the max target currency will can get.
     * <p>
     * For example:
     * <p>
     * USD to JPY -> 109
     * <p>
     * USD to GBP to JPY = 0.71 * 155 = 110.05
     * <p>
     * > 109,
     * <p>
     * so the max value will be 110.05
     * <p>
     * USD,JPY,109;USD,GBP,0.71;GBP,JPY,166
     * USD
     * JPY
     */
    public void maxExchange() throws IOException {
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(r);
        String conversion_rate = in.readLine(); //read first line
        String conversion[] = conversion_rate.split(";"); //Separate string into array of string by ";"
        // one e.g. {"USD,JPY,109" , "USD,GBP,0.71"}
        String currency1 = in.readLine();// read first currency
        String currency2 = in.readLine();// read target currency
        if (currency2.equals(currency1)) { //if both currencies are same return 0;
            System.out.println(0);
            return;
        }
        Queue<String> queue = new LinkedList<>(); //to store graph and search on breadth first search
        ArrayList<String> visited = new ArrayList<>();//to keep check of visited currencies
        for (String temp : conversion) {
            String conversion_one[] = temp.split(",");// seperate currencies and conversion rate
            // e.g. {"USD" , "JPY", 109}
            if (conversion_one[0].equals(currency1)) {
                queue.add(conversion_one[1]); //add currency not visited yet
                queue.add(conversion_one[2]); //add rate
            } else if (conversion_one[1].equals(currency1)) {
                double d = Double.parseDouble(conversion_one[2]);
                d = 1 / d; // invert the conversion because conversion is given from 1st to 2nd
                //but we want 2nd to 1st.
                d = Math.round(d * 100.0) / 100.0; //round upto two decimal
                queue.add(conversion_one[0]); //add currency not visited
                queue.add(d + "");// add its rate
            }
        }
        visited.add(currency1); // currency1 already visited add in visited list
        double max = -1.0; // to calculate maximum conversion rate
        while (!queue.isEmpty()) { //iterate until queue is empty

            String currency = queue.poll(); // pop first element of queue i.e. currency
            double rate = Double.parseDouble(queue.poll()); //pop first element of queue i.e. rate

            if (currency.equals(currency2)) { //check whether it is target currency
                if (rate > max) { //update max
                    max = rate;
                }
            } else { // if not target currency
                for (String temp : conversion) {
                    String conversion_one[] = temp.split(",");
                    if (conversion_one[0].equals(currency) && !visited.contains(currency)) {
                        //check new currency which is not visited earlier

                        double d = Double.parseDouble(conversion_one[2]);
                        d = d * rate; //multiply the rate of multiple currencies to achieve result
                        d = Math.round(d * 100.0) / 100.0; //round upto two decimal
                        queue.add(conversion_one[1]);//add currency
                        queue.add(d + ""); //add to queue
                    } else if (conversion_one[1].equals("currency") && !visited.contains(currency)) {
                        double d = Double.parseDouble(conversion_one[2]);
                        d = 1 / d;// invert the rate
                        d = d * rate;//multiply the rate of multiple currencies to achieve result
                        d = Math.round(d * 100.0) / 100.0;//round upto two decimal
                        queue.add(conversion_one[0]);// add currency
                        queue.add(d + "");//add its rate
                    }
                }
                visited.add(currency); // update visited list all the nodes of currency had been visited
            }
        }
        System.out.println(max);//print maximum currency conversion
    }

    // 909： snake and ladders
    /**
     *You are given an n x n integer matrix board where the cells are labeled from 1 to n2 in a Boustrophedon style starting from the bottom left of the board (i.e. board[n - 1][0]) and alternating direction each row.
     *
     * You start on square 1 of the board. In each move, starting from square curr, do the following:
     *
     * Choose a destination square next with a label in the range [curr + 1, min(curr + 6, n2)].
     * This choice simulates the result of a standard 6-sided die roll: i.e., there are always at most 6 destinations, regardless of the size of the board.
     * If next has a snake or ladder, you must move to the destination of that snake or ladder. Otherwise, you move to next.
     * The game ends when you reach the square n2.
     * A board square on row r and column c has a snake or ladder if board[r][c] != -1. The destination of that snake or ladder is board[r][c]. Squares 1 and n2 do not have a snake or ladder.
     *
     * Note that you only take a snake or ladder at most once per move. If the destination to a snake or ladder is the start of another snake or ladder, you do not follow the subsequent snake or ladder.
     *
     * For example, suppose the board is [[-1,4],[-1,3]], and on the first move, your destination square is 2. You follow the ladder to square 3, but do not follow the subsequent ladder to 4.
     * Return the least number of moves required to reach the square n2. If it is not possible to reach the square, return -1.
     *
     *
     *
     * Example 1:
     *
     *
     * Input: board = [[-1,-1,-1,-1,-1,-1],[-1,-1,-1,-1,-1,-1],[-1,-1,-1,-1,-1,-1],[-1,35,-1,-1,13,-1],[-1,-1,-1,-1,-1,-1],[-1,15,-1,-1,-1,-1]]
     * Output: 4
     * Explanation:
     * In the beginning, you start at square 1 (at row 5, column 0).
     * You decide to move to square 2 and must take the ladder to square 15.
     * You then decide to move to square 17 and must take the snake to square 13.
     * You then decide to move to square 14 and must take the ladder to square 35.
     * You then decide to move to square 36, ending the game.
     * This is the lowest possible number of moves to reach the last square, so return 4.
     */

    class solution909 {
        public int snakesAndLadders(int[][] board) {

            int n = board.length*board.length; // n = total squares on the board

            int i = board.length - 1; // i and j to iterate through the board
            int j = 0;
            boolean flag = true; //For switching between even and odd rows.
            int[] positions = new int[n];
            int index = 0;
            while(index < n){
                positions[index] = board[i][j];
                index++;
                j = flag ? j + 1 : j - 1;
                if(j == board.length){ //Switch if at the edge
                    j--;
                    i--;
                    flag = !flag;
                }else if(j == -1){ //Switch if at the edge
                    j++;
                    i--;
                    flag = !flag;
                }
            }
            boolean[] visited = new boolean[n]; //Keeping track of visited nodes during Breadth First Search.
            Queue<Integer> q = new LinkedList<>();
            int start = positions[0] > -1 ? positions[0] - 1: 0; //Start position is the destination of the first square, or 0 if square is not a ladder or snake
            q.offer(start);
            visited[start] = true;
            int moves = 0;
            while(!q.isEmpty()){
                int size = q.size();
                for(i = 0; i < size; i++){
                    int curr = q.poll();
                    if(curr == n - 1){ //If current square is the last square return total number of moves
                        return moves;
                    }
                    for(int next = curr + 1; next <= Math.min(curr + 6, n - 1); next++){ //Adding all the next possible squares to the queue
                        int pos = positions[next] > -1 ? positions[next] - 1 : next;
                        if(!visited[pos]){
                            visited[pos] = true;
                            q.offer(pos);
                        }
                    }
                }
                moves++;
            }
            return -1; //If final position can't be reached, return -1;
        }
    }

    // 1197： minimum knight moves
    /**
     * In an infinite chess board with coordinates from -infinity to +infinity, you have a knight at square [0, 0].
     * A knight has 8 possible moves it can make, as illustrated below. Each move is two squares in a cardinal direction, then one square in an orthogonal direction.
     *
     *
     * Return the minimum number of steps needed to move the knight to the square [x, y]. It is guaranteed the answer exists.
     *
     * Example 1:
     *
     * Input: x = 2, y = 1
     * Output: 1
     * Explanation: [0, 0] → [2, 1]
     * Example 2:
     *
     * Input: x = 5, y = 5
     * Output: 4
     * Explanation: [0, 0] → [2, 1] → [4, 2] → [3, 4] → [5, 5]
     */

    /**
     * How to approach this in an interview
     * Intuition:
     * There are lots of great solutions on the discussion board as well as the premium solutions demonstrating different
     * ways to solve the problem. In this post, I will go through the best way to tackle this question in an interview.
     * Hope it helps!
     *
     * When you're first presented this question, you probably realised that the solution would probably involve some
     * kind of traversal algorithm. Great, this is a good start. Many 2D grid traversal algorithms exist but you should
     * first try and see whether breadth-first search (BFS) or depth-first search (DFS) would work. Chances are,
     * there's probably a way to solve this question using either algorithm, but there's likely a more efficient choice
     * between the two.
     *
     * Interview tip: If the question requires finding the shortest path or the minimum number of steps in a graph or grid,
     * choose BFS.
     *
     * Why BFS?
     * In a 2D grid, BFS can ALWAYS find the shortest path as long as at least one valid path exists. This is because
     * BFS works by processing all nodes at the current level before processing the next level. In other words, we're
     * working level-by-level. In this question, consider each level to be 1 knight move. What this means is that if
     * you've landed on the target square, we can just return the current level (i.e. the number of moves so far) and
     * that's guaranteed to be the shortest path since it's the first time we've seen our target.
     *
     * Ask Clarifying Questions
     * Before continuing to code, it's extremely important to make sure you're aware of all the details of this question.
     * In particular, ask:
     *
     * What is the range of x and y / can x or y be negative?
     * How large is the chessboard?
     * Is it strictly square or rectangular?
     * In Leetcode, you're given all these details but in an interview, you likely won't be. So it's extremely important
     * to ask these clarifying questions as they result in significant alterations to our code.
     *
     * Interview tip: If the interviewer says the board is infinite, first ask if it's okay to code a solution assuming
     * there are max bounds to the board size and then later mention how you would change this for an infinite / really
     * large board size.
     *
     * A Template for BFS:
     * In BFS, we use a queue to process each step whereas in DFS, we use a stack. To find the shortest path, we need to
     * process the entire level before moving to the next one (often called level-order traversal). To do this, we empty
     * all values in the queue in the current level and add all their neighbours to fill in the next level. With the below
     * template, we replace "cell" with whatever we're dealing with (e.g. coordinates, graph nodes, etc...).
     *
     * // create a queue and add the starting point to it.
     * // create a data structure "visited" to keep track of visited cells
     * //
     * // int minSteps
     * // while (queue is not empty):
     * //     levelSize = queue.size
     * //
     * //     for (0 -> levelSize):
     * //          currentCell = top of the queue
     * //          if (currentCell == target): return minSteps
     * //
     * //          for (nextCell: neighbouringCells):
     * //               if (nextCell is out of bounds or we've visited nextCell): skip
     * //               mark nextCell as visited
     * //               add nextCell to queue
     * //
     * // return minSteps
     * Accommodate the Template for This Question
     * The above template fortunately applies to pretty much all BFS level-order questions. However, you'll probably
     * have to accommodate some changes to the template to suit the given problem statement. The first thing to do is
     * identify what's different / what needs changing. In this question, we need to:
     *
     * Find out how to store our coordinates.
     * Pick a data structure to keep track of visited coordinates.
     * Find out how to get our neighbouring coordinates.
     * For the first point, we need to keep track of both x and y. In a lot of grid questions, we can enumerate these
     * values into a single integer. But for this question, storing them in an integer array of size 2 is sufficient.
     *
     * For the second point, both a set and a 2D boolean array have support for our chosen types. As mentioned in the
     * premium solution, Java hash sets are slow so we'll use a boolean array since we have the option to.
     *
     * For the third point, our neighbours could be up to eight different coordinates. Each coordinate is some offset of
     * 1 and 2 away from the current coordinate. Keeping this in mind, we can simply use a direction array (or an offset array)
     * to store the values of all possible offsets. Then, we simply loop through our direction array and add each offset
     * to our current coordinates.
     *
     * Awesome, now we're ready to code!
     *
     * Evaluating Trade-offs and Optimisations
     * Chances are, the regular BFS / level-order traversal method is sufficient for an interview (at least for coding).
     *
     * Interview tip: If you really want to go above and beyond, it's important to recognise alternative ways of approaching
     * the problem as well as their trade-offs.
     *
     * A good place to start is to recognise inefficiencies in our current approach. The main one is that regular BFS is
     * quite slow for really large boards.
     *
     * Option 1: Use bi-directional BFS.
     * Option 2: Use A*.
     * I recommend familiarising yourself with both of the above approaches. One of the premium solutions discusses
     * bi-directional
     * BFS in-depth and some posts on the discussion board talk about the A* algorithm.
     *
     * There is an extremely small chance you'll actually be asked to code up either one of these in an interview. What's
     * important here isn't necessarily knowing how to code them up but simply knowing how they work, why they're optimisations
     * and understanding their trade-offs. If you understand them well enough, simply bringing them up in an interview will
     * put you miles ahead of your competition.
     */
    class Solution1197 {
        public int minKnightMoves(int x, int y) {
            // We want to make x and y positive, since the chessboard is symmetrical  [2,1] == [-2,-1]
            x = Math.abs(x);
            y = Math.abs(y);
            // Common set up for BFS, but we use a 1D integer --> {x-pos, y-pos}
            Queue<int[]> q = new LinkedList<>();
            // Add our starting point [0,0]
            q.add(new int[]{0,0});
            // Our legal moves --> Our knight can move all 8 ways
            int[][] legalMoves = {{1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}};
            int counter = 0;
            // 305?
            // The contraints given to us is that "0 <= |x| + |y| <= 300"
            // Keep in mind, in order to get position [1,1], the knight will have to go outside the positives and enter the negative values
            // The highest a knight can enter the negative values is two. Meaning the positions a knight can be is -2 - 300.
            // Keep in mind, the same logic applies for the positive side, meaning the knight can be "outside" this 300 range just for one turn. Making our range -2 - 302
            // - 2 - 302 is --> 0 - 304 --> [305]
            // We have to offset this by 2, since we can't index a negative value
            boolean[][] seen = new boolean[305][305];
            // Standard BFS procedure
            while(!q.isEmpty()){
                int size = q.size();
                for(int i = 0; i < size; i++){
                    int[] cur = q.poll();
                    // Check if we already hit our target
                    if(cur[0] == x && cur[1] == y) return counter;
                    // Iterate the legalMoves array --> moves[0] gives us the {value, IGNORE} and moves[1] gives us {IGNORE, value}
                    for(int[] moves: legalMoves){
                        // Add the legal move set to our current position
                        int moveX = moves[0] + cur[0];
                        int moveY = moves[1] + cur[1];
                        // Keep in mind, we don't want to go further into a negatives, so -2 is our max!
                        // We also don't want to go too far into the positives, so keep the value below 305
                        // Remember, we add +2 to offset the lowest value (-2) to equal 0. Since, we can't index -2
                        if(moveX >= -2 && moveY >= -2 && moveX < 305 && moveY < 305 &&!seen[moveX + 2][moveY + 2]){
                            // Change the seen array to true and add the position to our queue
                            seen[moveX + 2][moveY + 2] = true;
                            q.add(new int[]{moveX, moveY});
                        }
                    }
                }
                counter++;
            }
            return counter;
        }
    }

    // 1293: Shortest Path in a Grid with Obstacles Elimination
    /**
     * You are given an m x n integer matrix grid where each cell is either 0 (empty) or 1 (obstacle). You can move up, down, left, or right from and to an empty cell in one step.
     *
     * Return the minimum number of steps to walk from the upper left corner (0, 0) to the lower right corner (m - 1, n - 1) given that you can eliminate at most k obstacles. If it is not possible to find such walk return -1.
     *
     *
     *
     * Example 1:
     *
     *
     * Input: grid = [[0,0,0],[1,1,0],[0,0,0],[0,1,1],[0,0,0]], k = 1
     * Output: 6
     * Explanation:
     * The shortest path without eliminating any obstacle is 10.
     * The shortest path with one obstacle elimination at position (3,2) is 6. Such path is (0,0) -> (0,1) -> (0,2) -> (1,2) -> (2,2) -> (3,2) -> (4,2).
     * Example 2:
     */
    /*
    Use BFS
    BFS on (x,y,r) x,y is coordinate, r is remain number of obstacles you can remove.
     */
    class Solution1293 {
        public int shortestPath(int[][] grid, int k) {
            int m = grid.length, n = grid[0].length;
            Queue<int[]> q = new LinkedList<>();
            boolean[][][] visited = new boolean[m][n][k + 1];
            q.add(new int[]{0, 0, k});
            int res = 0;
            while (!q.isEmpty()) {
                int size = q.size();
                while (size-- > 0) {
                    int[] cur = q.poll();
                    int x = cur[0], y = cur[1], obs = cur[2];
                    if (x == m - 1 && y == n - 1 && obs >= 0) return res;
                    if (obs < 0 || visited[x][y][obs] == true) continue;
                    visited[x][y][obs] = true;
                    if (x - 1 >= 0) q.add(new int[]{x - 1, y, obs - grid[x - 1][y]});
                    if (x + 1 < m) q.add(new int[]{x + 1, y, obs-grid[x + 1][y]});
                    if (y - 1 >= 0) q.add(new int[]{x, y - 1, obs - grid[x][y - 1]});
                    if (y + 1 < n) q.add(new int[]{x , y + 1, obs- grid[x][y + 1]});
                }
                res ++;
            }
            return -1;

        }
    }

    // 815: Bus Routes
    /**
     * You are given an array routes representing bus routes where routes[i] is a bus route that the ith bus repeats forever.
     *
     * For example, if routes[0] = [1, 5, 7], this means that the 0th bus travels in the sequence 1 -> 5 -> 7 -> 1 -> 5 -> 7 -> 1 -> ... forever.
     * You will start at the bus stop source (You are not on any bus initially), and you want to go to the bus stop target.
     * You can travel between bus stops by buses only.
     *
     * Return the least number of buses you must take to travel from source to target. Return -1 if it is not possible.
     * Example 1:
     *
     * Input: routes = [[1,2,7],[3,6,7]], source = 1, target = 6
     * Output: 2
     * Explanation: The best strategy is take the first bus to the bus stop 7, then take the second bus to the bus stop 6.
     * Example 2:
     *
     * Input: routes = [[7,12],[4,5,15],[6],[15,19],[9,12,13]], source = 15, target = 12
     * Output: -1
     */
    class Solution815 {
        /*
        Graph里面的key为stop， value为经过这个stop的bus值
         */
        public int numBusesToDestination(int[][] routes, int source, int target) {
            if (source == target) return 0;
            HashMap<Integer, List<Integer>> graph = new HashMap<>(); // map: KEY: Stop, VALUE: bus[]
            for (int i = 0; i < routes.length; i ++) {
                for(int j = 0; j < routes[i].length; j++) {
                    if(!graph.containsKey(routes[i][j])) {
                        graph.put(routes[i][j], new ArrayList<Integer>());
                    }
                    graph.get(routes[i][j]).add(i);
                }
            }
            int ans = 0;
            HashSet<Integer> visited = new HashSet<>();
            Queue<Integer> q = new LinkedList<>();
            q.offer(source);
            while (!q.isEmpty()) {
                ans++;
                int len = q.size();
                for (int i = 0; i < len; i++) {
                    int cur = q.poll();
                    List<Integer> buses = graph.get(cur);
                    for (int bus: buses) {
                        if (visited.contains(bus)) continue;
                        visited.add(bus);
                        for (int j = 0; j < routes[bus].length; j ++) {
                            if (routes[bus][j] == target) return ans;
                            q.offer(routes[bus][j]);
                        }
                    }
                }
            }
            return -1;
        }
    }
}
