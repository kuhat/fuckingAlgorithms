package com.company.Notes;

import java.util.LinkedList;
import java.util.Queue;

public class Note12_Graph {
    //200: Number of Islands

    /**
     *
     * Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands.
     *
     * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
     *
     * Input: grid = [
     *   ["1","1","1","1","0"],
     *   ["1","1","0","1","0"],
     *   ["1","1","0","0","0"],
     *   ["0","0","0","0","0"]
     * ]
     * Output: 1
     *
     * Input: grid = [
     *   ["1","1","0","0","0"],
     *   ["1","1","0","0","0"],
     *   ["0","0","1","0","0"],
     *   ["0","0","0","1","1"]
     * ]
     * Output: 3
     *
     */
    class solution200{
        private int m;
        private int n;
        public int numIslands(char[][] grid) {
            if (grid == null || grid.length == 0) return 0;
            m = grid.length;
            n = grid[0].length;
            int res =0;
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
            floodfill(grid, i , j + 1);  // 上下左右都进行遍历
            floodfill(grid, i , j - 1);
            floodfill(grid, i + 1 , j);
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
                if (i < m -1 && grid[i + 1][j] == '1') {  // 下面位置
                    queue.offer((i + 1) * n + j);
                    grid[i + 1][j] = '0';
                }
                if (j > 0 && grid[i][j -1] == '1') {  // 左边位置
                    queue.offer(i * n + j - 1);
                    grid[i][j - 1]='0';
                }if (i < n - 1 && grid[i][j + 1] == '1') {  // 右边位置
                    queue.offer((i * n + j + 1));
                    grid[i][j + 1] = '0';
                }
            }
        }

        // BFS 优化
        private void bfs(char[][] grid, int x, int y){
            int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};  // 上下左右四个方向
            Queue<Point> queue = new LinkedList<>();
            queue.offer(new Point(x, y));
            while(!queue.isEmpty()) {
                Point cur = queue.poll();
                for(int[] direction : directions) {  // 四个方向进行遍历
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

        static class Point{
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

    public class mazeOfChessBoard{
        private boolean hasPath(char[][] maze, int[] start, int[] end){  // 从七点走到终点
            int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};  // 上下左右四个方向

            boolean[][] visited = new boolean[maze.length][maze[0].length];  // 记录走的路径

            Queue<solution200.Point> queue = new LinkedList<>();
            queue.offer(new solution200.Point(start[0], start[1]));  // 初始化，加入起点
            visited[start[0]][start[1]] = true;

            while(!queue.isEmpty()) {
                solution200.Point cur = queue.poll();
                if (cur.x == end[0] && cur.y == end[1]) return true;  // 走到终点的时候return true
                for(int[] direction : directions) {  // 四个方向进行遍历
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

        class Point{
            int x;
            int y;
            public Point(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }

    }

}
