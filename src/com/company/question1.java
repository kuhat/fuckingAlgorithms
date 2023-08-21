package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: algorithms
 * @package: PACKAGE_NAME
 * @className: com.company.question1
 * @author: Danny
 * @description: TODO
 * @date: 2023/8/3 21:00
 * @version: 1.0
 */
public class question1 {
    /**
         *    4
         *   / \
         *  9   0
     *    /  \
     *   5    1
     *
     *
     */

    public static class treeNode {
        int val;
        treeNode left;
        treeNode right;
        public treeNode(int val) {
            this.val = val;
        }
    }

    static List<Integer> path;

    public static int solve(treeNode root) {
        path = new ArrayList<>();
        int sum = 0;
        helper(root, 0);
        for (int i: path) {
            sum += i;
        }
        return sum;
    }

    public static void helper(treeNode root, int cur){
        if (root == null) {
            path.add(cur);
            return;
        }
        cur = cur * 10 + root.val;
        helper(root.left, cur);
        helper(root.right, cur);
    }

    public static void main(String[] args) {
        treeNode root = new treeNode(4);
        treeNode node1 = new treeNode(9);
        treeNode node2 = new treeNode(0);
        treeNode node3 = new treeNode(5);
        treeNode node4 = new treeNode(1);
        root.left = node1;
        root.right = node2;
        node1.left = node3;
        node1.right = node4;
        int res = solve(root);
        System.out.println(res / 2);
    }
}
