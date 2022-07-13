package com.company.Notes;

import java.util.HashMap;

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
        return res >0 ? 1 : 2;
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
                HashMap<String, Integer> map = new HashMap<>();
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

}
