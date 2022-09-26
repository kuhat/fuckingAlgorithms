package com.company.Notes;

import com.company.leetCode.NestedInteger;

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

            for (String word: wordList){
                Node node = graph.getNode(word);
                for (int i = 0; i < word.length(); i ++) {
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
                res ++;
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    Node node = queue.poll();
                    if (node.word.equals(endWord)) return res;  // 如果碰到了endword就停止
                    for (Node neighbor: node.neighbors) {  // 在node的neighbor里面找临结点
                        if(!visited.contains(neighbor)) {  // 如果没有走过就加入queue
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
            if (set.contains( beginWord)) set.remove(beginWord);
            Queue<String> queue = new LinkedList<>();
            HashMap<String, Integer> map = new HashMap<>();
            map.put(beginWord, 1);  // map的 key值是当前的单词，value是level,也就是第几次遍历
            queue.offer(beginWord);
            while (!queue.isEmpty()) {
                String word = queue.poll();
                int curLevel = map.get(word);  // 当前的层数
                for (int i =0; i < word.length(); i++) {  // word的每个位置进行替换
                    char[] wordUnit = word.toCharArray();
                    for (char j = 'a'; j <= 'z'; j++) {  // 将a到z一个一个去填充当前的位置
                        wordUnit[i] = j;
                        String temp = new String(wordUnit);  // 替换成新的string
                        if (set.contains(temp)){
                            // 如果set里面有当前替换过的单词
                            if(temp.equals(endWord)) return curLevel + 1;  // 如果当前遍历到最后一个单词，直接return level + 1
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
     *
     * Every adjacent pair of words differs by a single letter.
     * Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
     * sk == endWord
     * Given two words, beginWord and endWord, and a dictionary wordList, return all the shortest transformation sequences
     * from beginWord to endWord, or an empty list if no such sequence exists. Each sequence should be returned as a list of the words [beginWord, s1, s2, ..., sk].
     *
     * Example 1:
     *
     * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
     * Output: [["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
     * Explanation: There are 2 shortest transformation sequences:
     * "hit" -> "hot" -> "dot" -> "dog" -> "cog"
     * "hit" -> "hot" -> "lot" -> "log" -> "cog"
     * Example 2:
     *
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
            HashSet<String> unvisited = new HashSet<>();  // 访问过后就不能再访问了
            HashSet<String> visited = new HashSet<>();  // 将访问的点和没有访问的区分

            HashMap<String, List<String>> map = new HashMap<>();

            queue.offer(beginWord);
            while (!queue.isEmpty()) {
                String word = queue.poll();
                curNum --;
                for (int i = 0; i < word.length(); i++) {
                    StringBuilder builder = new StringBuilder(word);
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        builder.setCharAt(i, ch);
                        String newWord = builder.toString();  // 对单词的预处理，将每个位置替换成a到z的字母，
                        if(unvisited.contains(newWord)) {
                            if (visited.add(newWord)) {  // 如果可以加入, 代表没有找过
                                nextNum++;
                                queue.offer(newWord);
                            }
                            if (map.containsKey(newWord)){
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
                for (String s: map.get(word)) {
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
    class Hungry{
        int[] person;
        int[] places;

        public void match(int[][] matrix) {
            person = new int[4];
            places = new int[3];
            Arrays.fill(person, -1);
            Arrays.fill(places, -1);

            boolean[] visited;  // 判断让还是不让
            int res= 0;
            for (int i = 0; i < person.length; i++) {  // 遍历每个人去匹配places
                visited = new boolean[places.length];
                if (hungary(matrix, i , visited)) res++;
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
     *
     * A region is captured by flipping all 'O's into 'X's in that surrounded region.
     *
     * Example 1:
     *
     * Input: board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
     * Output: [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
     * Explanation: Notice that an 'O' should not be flipped if:
     * - It is on the border, or
     * - It is adjacent to an 'O' that should not be flipped.
     * The bottom 'O' is on the border, so it is not flipped.
     * The other three 'O' form a surrounded region, so they are flipped.
     * Example 2:
     *
     * Input: board = [["X"]]
     * Output: [["X"]]
     */

    class Solution {
        /**
         * O 在边缘位置，第一行，第一列，最后一行，最后一列,不会被围住
         * @param board
         */
        public void solve(char[][] board) {
            if (board == null || board.length == 0 || board[0].length == 0) return;
            int m = board.length - 1; // 行数
            int n = board[0].length - 1; // 列数
            for (int i = 0; i <= m; i++) {
                if (board[i][0] == 'O') dfs(board, i, 0); // 第一列
                if (board[i][n] == 'O') dfs(board, i , n);  // 最后一列
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
            dfs(board, i ,j + 1);
            dfs(board, i ,j - 1);
            dfs(board, i + 1 ,j);
            dfs(board, i - 1 ,j);

        }
    }

    // 490 The Maze
    /**
     * 在迷宫中有一个球，里面有空的空间和墙壁。球可以通过滚上，下，左或右移动，
     * 但它不会停止滚动直到撞到墙上。当球停止时，它可以选择下一个方向。
     *
     * 给定球的起始位置，目的地和迷宫，确定球是否可以停在终点。
     *
     * 迷宫由二维数组表示。1表示墙和0表示空的空间。你可以假设迷宫的边界都是墙。开始和目标坐标用行和列索引表示。
     * 样例
     *
     * 例1:
     *
     *     输入:
     *     map =
     *     [
     *      [0,0,1,0,0],
     *      [0,0,0,0,0],
     *      [0,0,0,1,0],
     *      [1,1,0,1,1],
     *      [0,0,0,0,0]
     *     ]
     *     start = [0,4]
     *     end = [3,2]
     *     输出:
     *     false
     */
    class Solution490{
        public boolean hasPath(int[][] maze, int[] start, int[] destination) {
            boolean[][] visited = new boolean[maze.length][maze[0].length];
            int[][] directions = {{1,0}, {-1,0}, {0,1},{0, -1}};

            Queue<Point> queue = new LinkedList<>();
            queue.offer(new Point(start[0], start[1]));
            while (!queue.isEmpty()) {
                Point cur = queue.poll();
                visited[cur.x][cur.y] = true;
                if (cur.x == destination[0] && cur.y == destination[1]) {
                    return true;
                }
                for (int[] direction: directions) {
                    int newX = cur.x;
                    int newY = cur.y;
                    while(isValid(maze, newX + direction[0], newY + direction[1])) {
                        newX += direction[0];
                        newY += direction[1];
                    }
                    if (!visited[newX][newY]) queue.offer(new Point(newX, newY));
                }
            }
            return false;
        }

        private boolean isValid(int[][] maze, int x, int y) {
            return x>= 0 && y >=0 && x<maze.length && y <maze[0].length && maze[x][y] == 0;
        }

        class Point{
            int x;
            int y;

            public Point(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
    }

    // 79: word search
    /**
     * Given an m x n grid of characters board and a string word, return true if word exists in the grid.
     *
     * The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally
     * or vertically neighboring. The same letter cell may not be used more than once.
     *
     * Example 1:
     *
     * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
     * Output: true
     * Example 2:
     *
     *
     * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
     * Output: true
     * Example 3:
     *
     *
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
            if (i <0 || i >= board.length || j < 0 || j >= board[0].length ) return false;  // 边界条件
            if (board[i][j] == word.charAt(start ++)) {
                char c = board[i][j];
                board[i][j] = '#';  // 这个地方不能再用了
                boolean res = exist(board, i + 1, j, word, start) ||
                        exist(board, i - 1, j, word, start) ||
                        exist(board, i, j + 1, word, start) ||
                        exist(board, i , j - 1, word, start);
                board[i][j] = c;
                return res;
            }
            return false;
        }
    }

    // leetcode 994: rotting oranges

    /**
     * You are given an m x n grid where each cell can have one of three values:
     *
     * 0 representing an empty cell,
     * 1 representing a fresh orange, or
     * 2 representing a rotten orange.
     * Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.
     *
     * Return the minimum number of minutes that must elapse until no cell has a fresh orange. If this is impossible, return -1.
     *
     * Example 1:
     *
     *
     * Input: grid = [[2,1,1],[1,1,0],[0,1,1]]
     * Output: 4
     * Example 2:
     *
     * Input: grid = [[2,1,1],[0,1,1],[1,0,1]]
     * Output: -1
     * Explanation: The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens 4-directionally.
     * Example 3:
     *
     * Input: grid = [[0,2]]
     * Output: 0
     * Explanation: Since there are already no fresh oranges at minute 0, the answer is just 0.
     */

    class Solution994 {

        private int rotedCnt   = 0;
        private int orangeCnt  = 0;

        public int orangesRotting(int[][] grid) {

            // 统计共有多少橘子
            for(int i=0; i<grid.length; ++i){
                for(int j=0; j<grid[i].length; ++j){
                    if(grid[i][j] > 0){
                        orangeCnt += 1;
                    }
                }
            }
            //System.out.println("oranges " + orangeCnt);
            // rotting(grid) 返回这一分钟有多少橘子会腐败
            int minutes = 0;
            while(rotting(grid) > 0){
                minutes ++;
            }
            //System.out.println("minutes " + minutes);
            //System.out.println("rotedCnt " + rotedCnt);

            // 腐败过程结束，查看结果
            if(rotedCnt == orangeCnt){
                return minutes;
            }else{
                return -1;
            }
        }
        public int rotting(int[][] grid){

            int rottingCnt = 0;
            rotedCnt       = 0;
            // 这一秒钟腐败的
            int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}};
            for(int i=0; i<grid.length; ++i){
                for(int j=0; j<grid[i].length; ++j){
                    if(grid[i][j] == 2){
                        rotedCnt += 1;
                        for(int[] direction : directions){
                            int xx = i + direction[0];
                            int yy = j + direction[1];
                            if(xx >=0 && xx < grid.length && yy >= 0 && yy < grid[i].length && grid[xx][yy] == 1){
                                grid[xx][yy] = 3;  // 把这一分钟要腐败的先标记为 3 防止与之前已经腐败的混淆
                                rottingCnt ++;
                            }
                        }
                    }
                }
            }
            for(int i=0; i<grid.length; ++i){
                for(int j=0; j<grid[i].length; ++j){
                    if(grid[i][j] == 3){
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
     *
     * The island is partitioned into a grid of square cells. You are given an m x n integer matrix heights where
     * heights[r][c] represents the height above sea level of the cell at coordinate (r, c).
     *
     * The island receives a lot of rain, and the rain water can flow to neighboring cells directly north, south,
     * east, and west if the neighboring cell's height is less than or equal to the current cell's height. Water
     * can flow from any cell adjacent to an ocean into the ocean.
     *
     * Return a 2D list of grid coordinates result where result[i] = [ri, ci] denotes that rain water can flow
     * from cell (ri, ci) to both the Pacific and Atlantic oceans.
     *
     * Example 1:
     *
     * Input: heights = [[1,2,2,3,5],[3,2,3,4,4],[2,4,5,3,1],[6,7,1,4,5],[5,1,1,2,4]]
     * Output: [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]
     * Explanation: The following cells can flow to the Pacific and Atlantic oceans, as shown below:
     * [0,4]: [0,4] -> Pacific Ocean
     *        [0,4] -> Atlantic Ocean
     * [1,3]: [1,3] -> [0,3] -> Pacific Ocean
     *        [1,3] -> [1,4] -> Atlantic Ocean
     * [1,4]: [1,4] -> [1,3] -> [0,3] -> Pacific Ocean
     *        [1,4] -> Atlantic Ocean
     * [2,2]: [2,2] -> [1,2] -> [0,2] -> Pacific Ocean
     *        [2,2] -> [2,3] -> [2,4] -> Atlantic Ocean
     * [3,0]: [3,0] -> Pacific Ocean
     *        [3,0] -> [4,0] -> Atlantic Ocean
     * [3,1]: [3,1] -> [3,0] -> Pacific Ocean
     *        [3,1] -> [4,1] -> Atlantic Ocean
     * [4,0]: [4,0] -> Pacific Ocean
     *        [4,0] -> Atlantic Ocean
     * Note that there are other possible paths for these cells to flow to the Pacific and Atlantic oceans.
     * Example 2:
     *
     * Input: heights = [[1]]
     * Output: [[0,0]]
     * Explanation: The water can flow from the only cell to the Pacific and Atlantic oceans.
     */
    class Solution417 {

        int m , n;
        int dir[][] = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        public List<List<Integer>> pacificAtlantic(int[][] heights) {
            List<List<Integer>> res = new ArrayList<>();
            m = heights.length;
            if (m == 0) return res;
            n = heights[0].length;

            boolean[][] pac = new boolean[m][n];
            boolean[][] atl = new boolean[m][n];

            for (int i= 0 ; i < m; i ++) {  // 从第一行开始遍历
                helper(heights, pac, i, 0);  // 太平洋是第一行
                helper(heights, atl, i, n -1);  // 大西洋是最后一行
            }
            for (int i = 0; i < n; i ++) {  // 从第一列开始遍历
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
     *
     * For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
     * Return true if you can finish all courses. Otherwise, return false.
     *
     * Example 1:
     *
     * Input: numCourses = 2, prerequisites = [[1,0]]
     * Output: true
     * Explanation: There are a total of 2 courses to take.
     * To take course 1 you should have finished course 0. So it is possible.
     * Example 2:
     *
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
            for (int[] pair: prerequisites) {
                indegree[pair[0]] ++;  // 找出每个点的入度
            }
            Queue<Integer> queue = new LinkedList<>();
            for (int i = 0; i < indegree.length; i++) {
                if (indegree[i] == 0) queue.offer(i);  // 将入度为0的元素加入queue
            }
            while(!queue.isEmpty()) {
                int pre = queue.poll();  // 删除入度为0的元素
                res--;  // 有一个课程已经用过了
                for (int[] pair: prerequisites) {  // 找出当前入度为0的先修课
                    if (pair[1] == pre) {
                        indegree[pair[0]]--;
                        if (indegree[pair[0]] == 0) queue.offer(pair[0]);
                    }
                }
            }
            return res == 0;
        }
    }

    //133 ：clone graph
    /**
     * Given a reference of a node in a connected undirected graph.
     *
     * Return a deep copy (clone) of the graph.
     *
     * Each node in the graph contains a value (int) and a list (List[Node]) of its neighbors.
     *
     * class Node {
     *     public int val;
     *     public List<Node> neighbors;
     * }
     *
     * Test case format:
     *
     * For simplicity, each node's value is the same as the node's index (1-indexed). For example, the first node with
     * val == 1, the second node with val == 2, and so on. The graph is represented in the test case using an adjacency list.
     *
     * An adjacency list is a collection of unordered lists used to represent a finite graph. Each list describes the
     * set of neighbors of a node in the graph.
     *
     * The given node will always be the first node with val = 1. You must return the copy of the given node as a reference to the cloned graph.
     *
     * Example 1:
     *
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
     *
     * You are also given some queries, where queries[j] = [Cj, Dj] represents the jth query where you must find the answer for Cj / Dj = ?.
     *
     * Return the answers to all queries. If a single answer cannot be determined, return -1.0.
     *
     * Note: The input is always valid. You may assume that evaluating the queries will not result in division by
     * zero and that there is no contradiction.
     *
     * Example 1:
     *
     * Input: equations = [["a","b"],["b","c"]], values = [2.0,3.0], queries = [["a","c"],["b","a"],["a","e"],["a","a"],["x","x"]]
     * Output: [6.00000,0.50000,-1.00000,1.00000,-1.00000]
     * Explanation:
     * Given: a / b = 2.0, b / c = 3.0
     * queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ?
     * return: [6.0, 0.5, -1.0, 1.0, -1.0 ]
     * Example 2:
     *
     * Input: equations = [["a","b"],["b","c"],["bc","cd"]], values = [1.5,2.5,5.0], queries = [["a","c"],["c","b"],["bc","cd"],["cd","bc"]]
     * Output: [3.75000,0.40000,5.00000,0.20000]
     * Example 3:
     *
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
            for (int i =0; i < res.length; i++) {
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

            if(start.equals(end)) return value;   // 如果start和end 相等了，返回value
            visited.add(start);  // visited加入这个数
            for (GraphNode next: map.get(start)) {
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
     *
     * All of the tickets belong to a man who departs from "JFK", thus, the itinerary must begin with "JFK". If there
     * are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read
     * as a single string.
     *
     * For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
     * You may assume all tickets form at least one valid itinerary. You must use all the tickets once and only once.
     *
     * Example 1:
     *
     * Input: tickets = [["MUC","LHR"],["JFK","MUC"],["SFO","SJC"],["LHR","SFO"]]
     * Output: ["JFK","MUC","LHR","SFO","SJC"]
     * Example 2:
     *
     *                    // JFK 可以到SFO和ATL, 优先选择ATL
     * Input: tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
     * Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
     * Explanation: Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"] but it is larger in lexical
     *
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

        public void helper(String airport){
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
     *
     * We may perform an add land operation which turns the water at position into a land. You are given an array
     * positions where positions[i] = [ri, ci] is the position (ri, ci) at which we should operate the ith operation.
     *
     * Return an array of integers answer where answer[i] is the number of islands after turning the cell (ri, ci) into a land.
     *
     * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You
     * may assume all four edges of the grid are all surrounded by water.
     *
     * Example 1:
     *
     * Input: m = 3, n = 3, positions = [[0,0],[0,1],[1,2],[2,1]]
     * Output: [1,1,2,3]
     * Explanation:
     * Initially, the 2d grid is filled with water.
     * - Operation #1: addLand(0, 0) turns the water at grid[0][0] into a land. We have 1 island.
     * - Operation #2: addLand(0, 1) turns the water at grid[0][1] into a land. We still have 1 island.
     * - Operation #3: addLand(1, 2) turns the water at grid[1][2] into a land. We have 2 islands.
     * - Operation #4: addLand(2, 1) turns the water at grid[2][1] into a land. We have 3 islands.
     * Example 2:
     *
     * Input: m = 1, n = 1, positions = [[0,0]]
     * Output: [1]
     */

    class Solution305 {
        // union find 261 323
        int[][] dirs = {{0, 1}, {1, 0},{-1, 0}, {0, -1}};
        public List<Integer> numIslands2(int m, int n, int[][] positions) {
            List<Integer> res = new ArrayList<>();
            if (m <= 0 || n <= 0) return res;
            int count = 0;
            int[] root = new int[m * n];  //  union find root，二维化为一维
            Arrays.fill(root, -1);

            for (int[] pair: positions) {
                int position = n * pair[0] + pair[1];  //  二维化一维的position
                if(root[position] != -1) {
                    res.add(count);
                    continue;
                }
                root[position] = position;
                count++;  //  有几个连着的

                for (int[] dir: dirs) {  // 遍历上下左右的位置
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
                        count --;
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
     *
     * Return true if the edges of the given graph make up a valid tree, and false otherwise.
     *
     * Example 1:
     *
     * 2 - 0 - 1 - 4
     *     |
     *     3
     * Input: n = 5, edges = [[0,1],[0,2],[0,3],[1,4]]
     * Output: true
     * Example 2:
     *     4
     *     |
     * 0 - 1 - 2
     *     \  /
     *      3
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
            for (int i = 0; i < n; i ++) {
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
            return visited.size() == n ? true : false;
        }

        private boolean helper(List<List<Integer>> graph, HashSet<Integer> visited, int node, int parent) {
            List<Integer> sub = graph.get(node);
            for (int v : sub) {
                if (v == parent ) continue;
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

    // 339：nested list weight sum

    /**
     * You are given a nested list of integers nestedList. Each element is either an integer or a list whose elements
     * may also be integers or other lists.
     *
     * The depth of an integer is the number of lists that it is inside of. For example, the nested list [1,[2,2],[[3],2],1]
     * has each integer's value set to its depth.
     *
     * Return the sum of each integer in nestedList multiplied by its depth.
     *
     * Example 1:
     *
     *
     * Input: nestedList = [[1,1],2,[1,1]]
     * Output: 10
     * Explanation: Four 1's at depth 2, one 2 at depth 1. 1*2 + 1*2 + 2*1 + 1*2 + 1*2 = 10.
     * Example 2:
     *
     *
     * Input: nestedList = [1,[4,[6]]]
     * Output: 27
     * Explanation: One 1 at depth 1, one 4 at depth 2, and one 6 at depth 3. 1*1 + 4*2 + 6*3 = 27.
     */
    class Solution339{

        // dfs
        public int depthSum(List<NestedInteger> nestedList) {
            if (nestedList == null) return 0;
            return helper(nestedList, 1);
        }

        public int helper(List<NestedInteger> nestedList, int depth) {
            int res = 0;
            for (NestedInteger nest: nestedList) {
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
                for (int i =0 ; i < size; i++) {
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
     *
     * The depth of an integer is the number of lists that it is inside of. For example, the nested list [1,[2,2],[[3],2],1]
     * has each integer's value set to its depth. Let maxDepth be the maximum depth of any integer.
     *
     * The weight of an integer is maxDepth - (the depth of the integer) + 1.
     *
     * Return the sum of each integer in nestedList multiplied by its weight.
     *
     * Example 1:
     *
     *
     * Input: nestedList = [[1,1],2,[1,1]]
     * Output: 8
     * Explanation: Four 1's with a weight of 1, one 2 with a weight of 2.
     * 1*1 + 1*1 + 2*2 + 1*1 + 1*1 = 8
     * Example 2:
     *
     *
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
            res += nextList.isEmpty() ? 0 : helper(nextedList, res);
            return res;
        }
    }

    // 323: Number of Connected Compunents in an Undirected Graph

    /**
     * You have a graph of n nodes. You are given an integer n and an array edges where edges[i] = [ai, bi] indicates
     * that there is an edge between ai and bi in the graph.
     *
     * Return the number of connected components in the graph.
     *
     * Example 1:
     *
     *
     * Input: n = 5, edges = [[0,1],[1,2],[3,4]]
     * Output: 2
     * Example 2:
     *
     *
     * Input: n = 5, edges = [[0,1],[1,2],[2,3],[3,4]]
     * Output: 1
     */
    // Union find
    class Solution323 {
        public int countComponents(int n, int[][] edges) {
            return 0;
        }
    }

    // 482: Robot Room Cleaner

    /**
     * You are controlling a robot that is located somewhere in a room. The room is modeled as an m x n binary grid where
     * 0 represents a wall and 1 represents an empty slot.
     *
     * The robot starts at an unknown location in the room that is guaranteed to be empty, and you do not have access
     * to the grid, but you can move the robot using the given API Robot.
     *
     * You are tasked to use the robot to clean the entire room (i.e., clean every empty cell in the room). The robot
     * with the four given APIs can move forward, turn left, or turn right. Each turn is 90 degrees.
     *
     * When the robot tries to move into a wall cell, its bumper sensor detects the obstacle, and it stays on the current cell.
     *
     * Design an algorithm to clean the entire room using the following APIs:
     *
     * interface Robot {
     *   // returns true if next cell is open and robot moves into the cell.
     *   // returns false if next cell is obstacle and robot stays on the current cell.
     *   boolean move();
     *
     *   // Robot will stay on the same cell after calling turnLeft/turnRight.
     *   // Each turn will be 90 degrees.
     *   void turnLeft();
     *   void turnRight();
     *
     *   // Clean the current cell.
     *   void clean();
     * }
     * Note that the initial direction of the robot will be facing up. You can assume all four edges of the grid are all
     * surrounded by a wall.
     *
     * Custom testing:
     *
     * The input is only given to initialize the room and the robot's position internally. You must solve this problem
     * "blindfolded". In other words, you must control the robot using only the four mentioned APIs without knowing the room layout and the initial robot's position.
     *
     * Example 1:
     *
     * Input: room = [[1,1,1,1,1,0,1,1],[1,1,1,1,1,0,1,1],[1,0,1,1,1,1,1,1],[0,0,0,1,0,0,0,0],[1,1,1,1,1,1,1,1]], row = 1, col = 3
     * Output: Robot cleaned all rooms.
     * Explanation: All grids in the room are marked by either 0 or 1.
     * 0 means the cell is blocked, while 1 means the cell is accessible.
     * The robot initially starts at the position of row=1, col=3.
     * From the top left corner, its position is one row below and three columns right.
     * Example 2:
     *
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
            for(int i = 0; i < 4; i++) {
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
     *
     * You are given an m x n char matrix board representing the game board where:
     *
     * 'M' represents an unrevealed mine,
     * 'E' represents an unrevealed empty square,
     * 'B' represents a revealed blank square that has no adjacent mines (i.e., above, below, left, right, and all 4 diagonals),
     * digit ('1' to '8') represents how many mines are adjacent to this revealed square, and
     * 'X' represents a revealed mine.
     * You are also given an integer array click where click = [clickr, clickc] represents the next click position among
     * all the unrevealed squares ('M' or 'E').
     *
     * Return the board after revealing this position according to the following rules:
     *
     * If a mine 'M' is revealed, then the game is over. You should change it to 'X'.
     * If an empty square 'E' with no adjacent mines is revealed, then change it to a revealed blank 'B' and all of its
     * adjacent unrevealed squares should be revealed recursively.
     * If an empty square 'E' with at least one adjacent mine is revealed, then change it to a digit ('1' to '8')
     * representing the number of adjacent mines.
     * Return the board when no more squares will be revealed.
     *
     * Example 1:
     *
     *
     * Input: board = [["E","E","E","E","E"],
     *                 ["E","E","M","E","E"],
     *                 ["E","E","E","E","E"],
     *                 ["E","E","E","E","E"]], click = [3,0]
     * Output: [["B","1","E","1","B"],
     *          ["B","1","M","1","B"],
     *          ["B","1","1","1","B"],
     *          ["B","B","B","B","B"]]
     * Example 2:
     *
     *
     * Input: board = [["B","1","E","1","B"],
     *                 ["B","1","M","1","B"],
     *                 ["B","1","1","1","B"],
     *                 w["B","B","B","B","B"]], click = [1,2]
     * Output: [["B","1","E","1","B"],
     *          ["B","1","X","1","B"],
     *          ["B","1","1","1","B"],
     *          ["B","B","B","B","B"]]
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
        public int findMines(char[][] board, int x,int y) {
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



    public static void main(String[] args) {
        char[] ar = new char[3];
        ar[0] = 's';
        ar[1] = 'g';
        ar[2] = '3';

        System.out.println(Arrays.toString(ar));
    }

}
