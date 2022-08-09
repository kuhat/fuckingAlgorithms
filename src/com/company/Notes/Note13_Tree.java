package com.company.Notes;

import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Note13_Tree {
    public class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) {
            this.val = val;
        }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // 112:PathSum
    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode() {}
     *     TreeNode(int val) { this.val = val; }
     *     TreeNode(int val, TreeNode left, TreeNode right) {
     *         this.val = val;
     *         this.left = left;
     *         this.right = right;
     *     }
     * }
     *
     * Given the root of a binary tree and an integer targetSum, return true if the tree has a root-to-leaf path
     * such that adding up all the values along the path equals targetSum.
     *
     * A leaf is a node with no children.
     *
     * Input: root = [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22
     * Output: true
     * Explanation: The root-to-leaf path with the target sum is shown.
     */
    class Solution {
        public boolean hasPathSum(TreeNode root, int targetSum) {
            if (root == null) return false;
            if (root.left == null && root.right == null) {
                return targetSum == root.val;
            }
            return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
        }
    }

    // 230: KthSmallest

    /**
     * Given the root of a binary search tree, and an integer k, return the kth smallest value (1-indexed)
     * of all the values of the nodes in the tree.
     *
     *
     * Input: root = [3,1,4,null,2], k = 1
     * Output: 1
     *
     * Input: root = [5,3,6,2,4,null,null,1], k = 3
     * Output: 3
     */
    class Solution230{
        int count;
        int res;
        public int kthSmallest(TreeNode root, int k) {
            count = k;
            res = 0;
            helper(root);
            return res;
        }

        public void helper(TreeNode root) {
            if(root == null) return;
            helper(root.left);
            count --;
            if (count == 0) res = root.val;
            helper(root.right);
        }
    }

    // 124:BinaryTree MaxPath

    /**
     * A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge
     * connecting them. A node can only appear in the sequence at most once. Note that the path does not need to pass through the root.
     *
     * The path sum of a path is the sum of the node's values in the path.
     *
     * Given the root of a binary tree, return the maximum path sum of any non-empty path.
     *
     * Input: root = [1,2,3]
     * Output: 6
     * Explanation: The optimal path is 2 -> 1 -> 3 with a path sum of 2 + 1 + 3 = 6.
     *
     * Input: root = [-10,9,20,null,null,15,7]
     * Output: 42
     * Explanation: The optimal path is 15 -> 20 -> 7 with a path sum of 15 + 20 + 7 = 42.
     *
     */
    class Solution124{
        int res;
        public int maxPathSum(TreeNode root) {
            if (root == null) return 0;
            res = Integer.MIN_VALUE;
            helper(root);
            return res;
        }

        private int helper(TreeNode root) {
            if(root == null) return 0;
            int left = Math.max(0, helper(root.left));  // 舍去负数
            int right = Math.max(0, helper(root.right));
            res = Math.max(res, left + right + root.val);
            return Math.max(left, right) + root.val;  // 左边和右边取最大的那一条路径
        }
    }

    // 104： Maximum Depth of Binary Tree
    /**
     *
     * Given the root of a binary tree, return its maximum depth.
     *
     * A binary tree's maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
     *
     * Input: root = [3,9,20,null,null,15,7]
     * Output: 3
     *
     * Input: root = [1,null,2]
     * Output: 2
     */
    class Solution104{
        public int maxDepth(TreeNode root) {
            if (root == null) return 0;
            int left = maxDepth(root.left) + 1;
            int right = maxDepth(root.right) + 1;
            return Math.max(left, right);
        }
    }


    // 199 BinaryTree right side view

    /**
     * Given the root of a binary tree, imagine yourself standing on the right side of it, return the values
     * of the nodes you can see ordered from top to bottom.
     *
     * Input: root = [1,2,3,null,5,null,4]
     * Output: [1,3,4]
     */
    class Solution199{
        public List<Integer> rightSideView(TreeNode root) {
            List<Integer> res = new ArrayList<>();
            if (root == null) return res;
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            while (!queue.isEmpty()) {  // 一层一层的去扫
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    TreeNode cur = queue.poll();
                    if (i == 0) res.add(cur.val);  // 如果是第一个元素直接加入
                    if (cur.right != null) queue.offer(cur.right);
                    if (cur.left != null) queue.offer(cur.left);
                }
            }
            return res;
        }
    }

    //257： binaryTree paths

    /**
     * Given the root of a binary tree, return all root-to-leaf paths in any order.
     *
     * A leaf is a node with no children.
     *
     * Input: root = [1,2,3,null,5]
     * Output: ["1->2->5","1->3"]
     */
    class solution257{
        public List<String> binaryTreePaths(TreeNode root) {
            List<String> res= new ArrayList<>();
            if (root == null) return res;
            helper(res, root, "");
            return res;
        }

        private void helper(List<String> res, TreeNode root, String path) {
            if (root.left == null && root.right == null) res.add(path + root.val);
            if (root.left != null) helper(res,root.left, path + root.val + "->");
            if (root.right != null) helper(res, root.right, path + root.val + "->");
        }
    }

    //113 : path sum II
    /**
     * Given the root of a binary tree and an integer targetSum, return all root-to-leaf paths where the sum of the node
     * values in the path equals targetSum. Each path should be returned as a list of the node values, not node references.
     *
     * A root-to-leaf path is a path starting from the root and ending at any leaf node. A leaf is a node with no children.
     *
     * Input: root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
     * Output: [[5,4,11,2],[5,8,4,5]]
     * Explanation: There are two paths whose sum equals targetSum:
     * 5 + 4 + 11 + 2 = 22
     * 5 + 8 + 4 + 5 = 22
     *
     */
    class solution113{
        public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
            List<List<Integer>> res = new ArrayList<>();
            if (root == null) return res;
            helper(res, new ArrayList<>(), root, targetSum);
            return res;
        }

        private void helper(List<List<Integer>> res, List<Integer> list, TreeNode root, int sum) {
            if (root == null) return;
            list.add(root.val);
            if (root.left == null && root.right ==null){
                if (sum == root.val) res.add(new ArrayList<>(list));
            }
            helper(res,list, root.left, sum - root.val);
            helper(res, list, root.right, sum - root.val);
            list.remove(list.size() - 1);
        }
    }

    // 235 Lowest Common Ancestor of a Binary Search Tree
    /**
     *Given a binary search tree (BST), find the lowest common ancestor (LCA) node of two given nodes in the BST.
     *
     * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p
     * and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”
     *
     * Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
     * Output: 6
     * Explanation: The LCA of nodes 2 and 8 is 6.
     *
     * Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
     * Output: 2
     * Explanation: The LCA of nodes 2 and 4 is 2, since a node can be a descendant of itself according to the LCA definition.
     */
    class solution235{
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if (root.val > p.val && root.val > q.val) return lowestCommonAncestor(root.left, p ,q);
            else if (root.val < p.val && root.val < q.val) return lowestCommonAncestor(root.right, p, q);
            else return root;
        }
    }

    // 99： Recover Binary search tree
    /**
     * You are given the root of a binary search tree (BST), where the values of exactly two nodes of the tree were
     * swapped by mistake. Recover the tree without changing its structure.
     *
     * Input: root = [1,3,null,null,2]
     * Output: [3,1,null,null,2]
     * Explanation: 3 cannot be a left child of 1 because 3 > 1. Swapping 1 and 3 makes the BST valid.
     *
     * Input: root = [3,1,4,null,null,2]
     * Output: [2,1,4,null,null,3]
     * Explanation: 2 cannot be in the right subtree of 3 because 2 < 3. Swapping 2 and 3 makes the BST valid.
     */

    class Solution99 {
        /*
        这题说的是二叉搜索树的两个节点被错误的交换，做这道题的时候必须要了解什么是二 叉搜索树，二叉搜索树的定义是：若它的左子树不空，
        则左子树上所有结点的值均小于 它的根结点的值；若它的右子树不空，则右子树上所有结点的值均大于它的根结点的 值；它的左、右子树也分别为二叉搜索树。
        中序遍历结果是有序的。
        我们只需要对这棵二叉树进行中序遍历，如果当前节点比前一个节点 小，
        也就是出现了逆序，那么其中有一个节点肯定是错误了，我们只需要找出这两个错 误的节点，最后再交换他们的值即可。
         */
        private TreeNode pre;  // 当前节点的前一个节点
        private TreeNode first;  // 第一个错误的节点
        private TreeNode second;  // 第二个错误的节点

        public void recoverTree(TreeNode root) {
            // 中序遍历
            inorder(root);
            // 交换两个节点的值
            int tmp = first.val;
            first.val = second.val;
            second.val = tmp;
        }

        private void inorder(TreeNode root) {
            if (root == null) return;
            inorder(root.left);
            // 二叉搜索树的中序遍历是有序的，如果前一个节点比当前节点的值大
            // 也就是出现了逆序，如果first为空，我们就把pre节点赋值给first
            if (first == null && pre != null && pre.val > root.val) first = pre;
            //如果first不为空，并且前一个节点比当前节点的值大，
            //我们就把当前节点赋值给second。注意上面是把pre
            //节点保存下来，而这里是把当前节点给保存下来。因为
            //我们是拿当前节点和前一个节点比较的，比如二叉树中
            //序遍历的结果是[1,5,3,4,2,6]，明显是5和2进行了
            //交换，第一个错误的地方是3小于5，所以要把5（pre）
            //保存下来，而第二个错误的地方是2小于4（pre），所
            //以要把2（当前节点）保存下来
            if (first != null && pre.val > root.val) second = root;
            pre = root;
            inorder(root.right);
        }
    }

    // 114： Flatten Binary Tree to Linked List
    /**
     * Given the root of a binary tree, flatten the tree into a "linked list":
     *
     * The "linked list" should use the same TreeNode class where the right child pointer points to the next node
     * in the list and the left child pointer is always null.
     * The "linked list" should be in the same order as a pre-order traversal of the binary tree.
     *
     * Input: root = [1,2,5,3,4,null,6]
     * Output: [1,null,2,null,3,null,4,null,5,null,6]
     */
    class Solution114{
        public void flatten(TreeNode root) {
           List<TreeNode> list = new ArrayList<>();
            //获取二叉树前序遍历的节点
            preorderTraversal(root, list);
            // 如果二叉树是空，直接返回
            if (list.size() == 0) return;
            // 重构二叉树
            TreeNode parent = root;
            for (int i = 1;  i < list.size(); i ++) {
                parent.right = list.get(i);
                parent.left = null;
                parent = parent.right;
            }
        }

        // 通过前序遍历获取二叉树的节点
        private void preorderTraversal(TreeNode root, List<TreeNode> list) {
            if (root == null) return;
            list.add(root);
            preorderTraversal(root.left, list);
            preorderTraversal(root.right, list);
        }

        public void flattenMorris(TreeNode root) {
            TreeNode cur = root;
            while (cur != null) {
                TreeNode left = cur.left;
                //判断左子节点是否为空
                if (left != null) {
                    //找到pre节点，他是左子节点的最
                    //右节点(如果左子节点有右子节点)，
                    //否则他就是左子节点
                    TreeNode pre = left;
                    while (pre.right != null)
                        pre = pre.right;
                        //把当前节点的右子节点挂到pre节点的右边
                    pre.right = cur.right;
                    //把当前节点的左子节点变为右子节点
                    cur.right = left;
                    //最后再把当前节点的左子节点设置为空
                    cur.left = null;
                }
                    //继续下一个节点
                cur = cur.right;
            }
        }
    }

    // 101. Symmetric Tree
    /**
     *Given the root of a binary tree, check whether it is a mirror of itself (i.e., symmetric around its center).
     *
     *Input: root = [1,2,2,3,4,4,3]
     * Output: true
     *
     */

    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode() {}
     *     TreeNode(int val) { this.val = val; }
     *     TreeNode(int val, TreeNode left, TreeNode right) {
     *         this.val = val;
     *         this.left = left;
     *         this.right = right;
     *     }
     * }
     */
    class Solution101 {
        public boolean isSymmetric(TreeNode root) {
            if (root == null) return true;
            return helper(root.left, root.right);
        }

        public boolean helper(TreeNode p, TreeNode q) {
            if (p == null && q == null) return true;
            if (p == null || q == null) return false;
            if (p.val != q.val) return false;
            return helper(p.left, q.right) && helper(p.right, q.left);
        }
    }

    // 100 Same Tree
    /**
     * Given the roots of two binary trees p and q, write a function to check if they are the same or not.
     *
     * Two binary trees are considered the same if they are structurally identical, and the nodes have the same value.
     *
     * Input: p = [1,2,3], q = [1,2,3]
     * Output: true
     *
     * Input: p = [1,2], q = [1,null,2]
     * Output: false
     */
    class Solution100 {
        public boolean isSameTree(TreeNode p, TreeNode q) {
            if (p == null && q == null) return true;
            if (p == null || q == null) return false;
            if (p.val != q.val) return false;
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        }
    }

    // 589 N-array
    /**
     * Given the root of an n-ary tree, return the preorder traversal of its nodes' values.
     *
     * Nary-Tree input serialization is represented in their level order traversal. Each group of children
     * is separated by the null value (See examples)
     *
     * Input: root = [1,null,3,2,4,null,5,6]
     * Output: [1,3,5,6,2,4]
     */
    class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };
    class Solution589 {
        public List<Integer> preorder(Node root) {
            List<Integer> res = new ArrayList<>();
            if (root == null) return res;
            helper(res, root);
            return res;
        }

        public void helper(List<Integer> res, Node root) {
            if (root == null) return;
            res.add(root.val);
            for (Node child: root.children) {
                helper(res, child);
            }
        }
    }

    // 559 Maximum of n-array tree
    /**
     * Given a n-ary tree, find its maximum depth.
     *
     * The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
     *
     * Nary-Tree input serialization is represented in their level order traversal, each group of children is separated by the null value (See examples).
     *
     *Input: root = [1,null,3,2,4,null,5,6]
     * Output: 3
     */
    class Solution559 {
        int res = 0;

        public int maxDepth(Node root) {
            if (root == null) return res;
            int depth = 1;
            helper(root, depth);
            return res;
        }

        public void helper(Node root, int depth) {
            if (root == null) return;
            res = Math.max(res, depth);
            for (Node child : root.children) {
                helper(child, depth + 1);
            }
        }
    }

    // 513. Find Bottom Left Tree Value
    /**
     * Given the root of a binary tree, return the leftmost value in the last row of the tree.
     */
    class Solution513 {

        int res = 0;
        int height = 0;

        public int findBottomLeftValue(TreeNode root) {
            if (root == null) return res;
            helper(root, 1);
            return res;
        }

        public void helper(TreeNode root, int depth) {
            if (root == null) return;
            if (height < depth) {
                height =  depth;
                res = root.val;
            }
            helper(root.left, depth +1);
            helper(root.right, depth + 1);
        }
    }

    // 113. Path Sum II
    /**
     * Given the root of a binary tree and an integer targetSum, return all root-to-leaf paths where the sum of
     * the node values in the path equals targetSum. Each path should be returned as a list of the node values,
     * not node references.
     *
     * A root-to-leaf path is a path starting from the root and ending at any leaf node. A leaf is a node with no children.
     *
     * Input: root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
     * Output: [[5,4,11,2],[5,8,4,5]]
     * Explanation: There are two paths whose sum equals targetSum:
     * 5 + 4 + 11 + 2 = 22
     * 5 + 8 + 4 + 5 = 22
     */
    class Solution113 {
        public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
            List<List<Integer>> res = new ArrayList<>();
            if (root == null) return res;
            helper(res, root, targetSum, new ArrayList<Integer>());
            return res;
        }

        public void helper(List<List<Integer>> res, TreeNode root, int sum, List<Integer> list) {
            if (root == null) return;
            list.add(root.val);
            if (root.left == null && root.right == null) {
                if (root.val == sum) {
                    res.add(new ArrayList<>(list));
                }
            }
            helper(res, root.left, sum - root.val, list);
            helper(res, root.right, sum - root.val, list);
            list.remove(list.size() - 1);
        }
    }

    // 437： Path III
    /**
     * Given the root of a binary tree and an integer targetSum, return the number of paths where the
     * sum of the values along the path equals targetSum.
     *
     * The path does not need to start or end at the root or a leaf, but it must go downwards (i.e.,
     * traveling only from parent nodes to child nodes).
     *
     * Input: root = [10,5,-3,3,2,null,11,3,-2,null,1], targetSum = 8
     * Output: 3
     * Explanation: The paths that sum to 8 are shown.
     *
     */

    class Solution437 {
        public int pathSum(TreeNode root, int targetSum) {
            if (root == null) return 0;
            // 把左子数和右子树都看成一颗整体的树重新遍历
            return helper(root, targetSum) + pathSum(root.left, targetSum) + pathSum(root.right, targetSum);
        }

        public int helper(TreeNode root, int sum) {
            int res = 0;
            if (root == null) return res;  // 如果遇到数底了就返回res
            if (sum == root.val) res++;  // 如果sum等于root的值了说明找到了就把res加一
            // res加上root的左子树的值和右子数的值
            res += helper(root.left, sum - root.val) + helper(root.right, sum - root.val);
            return res;
        }
    }

    // 129 Sum Root to Leaf Numbers
    /**
     * You are given the root of a binary tree containing digits from 0 to 9 only.
     *
     * Each root-to-leaf path in the tree represents a number.
     *
     * For example, the root-to-leaf path 1 -> 2 -> 3 represents the number 123.
     * Return the total sum of all root-to-leaf numbers. Test cases are generated so that the answer will fit in a
     * 32-bit integer.
     *
     * A leaf node is a node with no children.
     *
     * Input: root = [1,2,3]
     * Output: 25
     * Explanation:
     * The root-to-leaf path 1->2 represents the number 12.
     * The root-to-leaf path 1->3 represents the number 13.
     * Therefore, sum = 12 + 13 = 25.
     */
    class Solution129 {
        public int sumNumbers(TreeNode root) {
            return helper(root, 0);
        }

        public int helper(TreeNode root, int num) {
            if (root == null) return 0;
            if (root.left == null && root.right == null) return num * 10 + root.val;
            return helper(root.left, num * 10 + root.val) + helper(root.right, num * 10 + root.val);
        }
    }


}
