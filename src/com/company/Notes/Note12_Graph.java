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



}
