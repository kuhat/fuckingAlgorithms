package com.company.Notes;

import java.util.*;

public class Note9_Shape {

    public class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x =x ;
            this.y = y;
        }
    }

    public boolean onSegment(Point p, Point q, Point r) {
        if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x)
                && q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y)) {
            return true;
        }
        return false;
    }

    public boolean pointOnSegment(Point C, Point A, Point B) {
        Point AC = new Point(C.x - A.x, C.y - A.y);
        Point BC = new Point(C.x - B.x, C.y - B.y);
        int res = AC.x * BC.y - AC.y * BC.x;
        return res == 0 && onSegment(A, C, B);
    }

    public int orientation(Point p, Point q, Point r) {
        int res = (q.y - p.y) * (r.x - q.x) - (q.x - p .x) * (r.y - q.y);
        if (res == 0) return 0;
        return res >0 ? 1 : 2;  // 0 是平行，1是在左边2是在右边
    }

    // 线段相交
    public boolean segmentIntersect(Point p1, Point q1, Point p2, Point q2) {
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);
        if (o1 != o2 && o3 != o4) return true;  // 不平行的情况
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;  // 平行的情况，其中一个点在另一个直线上
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;
        return false;
    }

    // 判断点是否在多边形内
    public boolean pointInPolygon(Point[] polygon, Point point){
        Point extreme = new Point(Integer.MAX_VALUE, point.y);
        int count = 0;
        for (int i =0; i < polygon.length; i ++) {
            int next = (i + 1) % polygon.length;
            if (segmentIntersect(polygon[i], polygon[next], point, extreme)){
                if (orientation(polygon[i], point, polygon[next]) == 0) return onSegment(polygon[i], point, polygon[next]);
                count++;
            }
        }
        return count % 2 ==0;
    }

    // 点是否在三角形里面
    // 计算三角形的面积
    public double area(int x1, int y1, int x2, int y2, int x3, int y3) {
        return Math.abs(x1 * (y2 - y3) + x2 * (y1 - y3) + x3 * (y1 - y2));
    }

    // 如果和三角形顶点相连的三个三角形之和等于大三角的面积，则在三角形之中
    public boolean isInside (int x1, int y1, int x2, int y2, int x3, int y3, int x, int y)  {
        double A = area (x1, x2, x2, y2, x3, y3);
        double A1 = area(x, y, x2, y2, x3, y3);
        double A2 = area(x1, y1, x, y, x3, y3);
        double A3 = area(x1, y1,x2,y2,x, y);
        if (A1 == 0 || A2 == 0 || A3 == 0) return true;
        return A == A1 + A2 + A3;
    }

    // 判断一堆点里可以数出多少个正方形（平行于x轴）
    public int countSquare(List<Point> list ) {
        if (list.size() == 0) return 0;
        int res = 0;
        HashSet<Point> set = new HashSet<>();  // 点的集合
        for (Point point : list) set.add(point);
        for (int i = 0; i < list.size(); i ++) {
            Point node1 = list.get(i); // 对角线的第一个点
            for (int j = i + 1; j < list.size(); j++) {
                Point node2 = list.get(j);  // 第二个点
                if (Math.abs(node1.x - node2.x) != Math.abs(node1.y - node2.y)  // 长方形去掉这一行
                        || node1.equals(node2)) continue;
                Point left = new Point(node1.x, node2.y);
                Point right = new Point(node2.x, node1.y);
                if (set.contains(left) && set.contains(right)) res++;
            }
        }
        return res / 2;  // 会重复计算两次
    }

    // LeetCode 149: Max Points on a Line
    /**
     * Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane, return the maximum
     * number of points that lie on the same straight line.
     */
    class Solution149{
        public int maxPoints(int[][] points) {
            if (points == null || points.length == 0) return 0;
            if (points.length < 2) return points.length;
            int res = 0;

            for (int i = 0; i < points.length; i ++) {
                HashMap<String, Integer> map = new HashMap<>();  // key: 斜率，value: 当前斜率的点个数
                int samePoint = 0;
                int sameXAxis = 1;  // x左边一样
                for (int j = 0; j < points.length; j++) {
                    if (i != j) {  // 两个点不能是相同的两个点
                        if (points[i][0] == points[j][0] && points[i][1] == points[j][1]) {
                            samePoint++;
                        }
                        if (points[i][0] == points[j][0]) {
                            sameXAxis++;  // x = x的时候不能计算斜率
                            continue;
                        }
                        int numerator = points[i][1] - points[j][1];
                        int denominator = points[i][0] - points[j][0];
                        int gcd = gcd(numerator, denominator);
                        String hashStr = (numerator / gcd) + "/" + (denominator / gcd);
                        map.put(hashStr, map.getOrDefault(hashStr, 1) + 1);
                        res = Math.max(res, map.get(hashStr) + samePoint);
                    }
                }
                res = Math.max(res, sameXAxis);
            }
            return res;
        }

        private int gcd(int a, int b) {  // 最大公约数
            if (a == 0) return b;
            return gcd(b % a, a);
        }
    }

    // LeetCode 785: Is Graph Bipartite
    /**
     * There is an undirected graph with n nodes, where each node is numbered between 0 and n - 1. You are given a
     * 2D array graph, where graph[u] is an array of nodes that node u is adjacent to. More formally, for each v in
     * graph[u], there is an undirected edge between node u and node v. The graph has the following properties:
     *
     * There are no self-edges (graph[u] does not contain u).
     * There are no parallel edges (graph[u] does not contain duplicate values).
     * If v is in graph[u], then u is in graph[v] (the graph is undirected).
     * The graph may not be connected, meaning there may be two nodes u and v such that there is no path between them.
     * A graph is bipartite if the nodes can be partitioned into two independent sets A and B such that every edge in
     * the graph connects a node in set A and a node in set B.
     *
     * Return true if and only if it is bipartite.
     *
     * Input: graph = [[1,2,3],[0,2],[0,1,3],[0,2]]
     * Output: false
     * Explanation: There is no way to partition the nodes into two independent sets such that every edge connects a node in one and a node in the other.
     *
     * Input: graph = [[1,3],[0,2],[1,3],[0,2]]
     * Output: true
     * Explanation: We can partition the nodes into two sets: {0, 2} and {1, 3}.
     */
    class Solution {
        public boolean isBipartite(int[][] graph) {
            // 先对点进行着色
            int[] colors = new int[graph.length];
            for (int i = 0; i < colors.length; i++) {
                if (colors[i] == 0 ) {
                    Queue<Integer> queue = new LinkedList<>();
                    queue.offer(i);
                    colors[i] = 1;  // 如果没有着色就将当前的位置着色
                    while (!queue.isEmpty()) {
                        Integer node = queue.poll();
                        for (int adjacent : graph[node]) {  // 找相邻的两个点
                            if (colors[adjacent] == colors[node]) return false;  // 一旦相邻的两个点产生冲突了就不行
                            else if (colors[adjacent] == 0) {  // 如果相邻的点没有着色
                                queue.offer(adjacent);  // 加入队列
                                colors[adjacent] = -colors[node];  // 将相邻的点着色成 -1
                            }
                        }
                    }
                }
            }
            return true;
        }
    }
}
