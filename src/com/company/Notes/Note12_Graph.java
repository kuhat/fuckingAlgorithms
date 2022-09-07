package com.company.Notes;

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

    // 127 word ladder

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
     */
    class Solution207 {
        public boolean canFinish(int numCourses, int[][] prerequisites) {
            return true;
        }
    }

}
