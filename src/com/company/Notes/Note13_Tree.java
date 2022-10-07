package com.company.Notes;

import com.sun.source.tree.Tree;

import java.util.*;

public class Note13_Tree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

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
     * int val;
     * TreeNode left;
     * TreeNode right;
     * TreeNode() {}
     * TreeNode(int val) { this.val = val; }
     * TreeNode(int val, TreeNode left, TreeNode right) {
     * this.val = val;
     * this.left = left;
     * this.right = right;
     * }
     * }
     * <p>
     * Given the root of a binary tree and an integer targetSum, return true if the tree has a root-to-leaf path
     * such that adding up all the values along the path equals targetSum.
     * <p>
     * A leaf is a node with no children.
     * <p>
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

    // 230: KthSmallest In a BST

    /**
     * Given the root of a binary search tree, and an integer k, return the kth smallest value (1-indexed)
     * of all the values of the nodes in the tree.
     * <p>
     * <p>
     * Input: root = [3,1,4,null,2], k = 1
     * Output: 1
     * <p>
     * Input: root = [5,3,6,2,4,null,null,1], k = 3
     * Output: 3
     */
    class Solution230 {
        int count;
        int res;

        public int kthSmallest(TreeNode root, int k) {
            count = k;
            res = 0;
            helper(root);
            return res;
        }

        public void helper(TreeNode root) {
            if (root == null) return;
            helper(root.left);
            count--;
            if (count == 0) res = root.val;
            helper(root.right);
        }
    }

    // 124:BinaryTree MaxPath

    /**
     * A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge
     * connecting them. A node can only appear in the sequence at most once. Note that the path does not need to pass through the root.
     * <p>
     * The path sum of a path is the sum of the node's values in the path.
     * <p>
     * Given the root of a binary tree, return the maximum path sum of any non-empty path.
     * <p>
     * Input: root = [1,2,3]
     * Output: 6
     * Explanation: The optimal path is 2 -> 1 -> 3 with a path sum of 2 + 1 + 3 = 6.
     * <p>
     * Input: root = [-10,9,20,null,null,15,7]
     * Output: 42
     * Explanation: The optimal path is 15 -> 20 -> 7 with a path sum of 15 + 20 + 7 = 42.
     */
    class Solution124 {
        int res;

        public int maxPathSum(TreeNode root) {
            if (root == null) return 0;
            res = Integer.MIN_VALUE;
            helper(root);
            return res;
        }

        private int helper(TreeNode root) {
            if (root == null) return 0;
            int left = Math.max(0, helper(root.left));  // 舍去负数
            int right = Math.max(0, helper(root.right));
            res = Math.max(res, left + right + root.val);
            return Math.max(left, right) + root.val;  // 左边和右边取最大的那一条路径
        }
    }

    // 104： Maximum Depth of Binary Tree

    /**
     * Given the root of a binary tree, return its maximum depth.
     * <p>
     * A binary tree's maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
     * <p>
     * Input: root = [3,9,20,null,null,15,7]
     * Output: 3
     * <p>
     * Input: root = [1,null,2]
     * Output: 2
     */
    class Solution104 {
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
     * <p>
     * Input: root = [1,2,3,null,5,null,4]
     * Output: [1,3,4]
     */
    class Solution199 {
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
     * <p>
     * A leaf is a node with no children.
     * <p>
     * Input: root = [1,2,3,null,5]
     * Output: ["1->2->5","1->3"]
     */
    class solution257 {
        public List<String> binaryTreePaths(TreeNode root) {
            List<String> res = new ArrayList<>();
            if (root == null) return res;
            helper(res, root, "");
            return res;
        }

        private void helper(List<String> res, TreeNode root, String path) {
            if (root.left == null && root.right == null) res.add(path + root.val);
            if (root.left != null) helper(res, root.left, path + root.val + "->");
            if (root.right != null) helper(res, root.right, path + root.val + "->");
        }
    }

    //113 : path sum II

    /**
     * Given the root of a binary tree and an integer targetSum, return all root-to-leaf paths where the sum of the node
     * values in the path equals targetSum. Each path should be returned as a list of the node values, not node references.
     * <p>
     * A root-to-leaf path is a path starting from the root and ending at any leaf node. A leaf is a node with no children.
     * <p>
     * Input: root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
     * Output: [[5,4,11,2],[5,8,4,5]]
     * Explanation: There are two paths whose sum equals targetSum:
     * 5 + 4 + 11 + 2 = 22
     * 5 + 8 + 4 + 5 = 22
     */
    class solution113 {
        public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
            List<List<Integer>> res = new ArrayList<>();
            if (root == null) return res;
            helper(res, new ArrayList<>(), root, targetSum);
            return res;
        }

        private void helper(List<List<Integer>> res, List<Integer> list, TreeNode root, int sum) {
            if (root == null) return;
            list.add(root.val);
            if (root.left == null && root.right == null) {
                if (sum == root.val) res.add(new ArrayList<>(list));
            }
            helper(res, list, root.left, sum - root.val);
            helper(res, list, root.right, sum - root.val);
            list.remove(list.size() - 1);
        }
    }

    // 235 Lowest Common Ancestor of a Binary Search Tree

    /**
     * Given a binary search tree (BST), find the lowest common ancestor (LCA) node of two given nodes in the BST.
     * <p>
     * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p
     * and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”
     * <p>
     * Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
     * Output: 6
     * Explanation: The LCA of nodes 2 and 8 is 6.
     * <p>
     * Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
     * Output: 2
     * Explanation: The LCA of nodes 2 and 4 is 2, since a node can be a descendant of itself according to the LCA definition.
     */
    class solution235 {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if (root.val > p.val && root.val > q.val) return lowestCommonAncestor(root.left, p, q);
            else if (root.val < p.val && root.val < q.val) return lowestCommonAncestor(root.right, p, q);
            else return root;
        }
    }

    // 99： Recover Binary search tree

    /**
     * You are given the root of a binary search tree (BST), where the values of exactly two nodes of the tree were
     * swapped by mistake. Recover the tree without changing its structure.
     * <p>
     * Input: root = [1,3,null,null,2]
     * Output: [3,1,null,null,2]
     * Explanation: 3 cannot be a left child of 1 because 3 > 1. Swapping 1 and 3 makes the BST valid.
     * <p>
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
     * <p>
     * The "linked list" should use the same TreeNode class where the right child pointer points to the next node
     * in the list and the left child pointer is always null.
     * The "linked list" should be in the same order as a pre-order traversal of the binary tree.
     * <p>
     * Input: root = [1,2,5,3,4,null,6]
     * Output: [1,null,2,null,3,null,4,null,5,null,6]
     */
    class Solution114 {
        public void flatten(TreeNode root) {
            List<TreeNode> list = new ArrayList<>();
            //获取二叉树前序遍历的节点
            preorderTraversal(root, list);
            // 如果二叉树是空，直接返回
            if (list.size() == 0) return;
            // 重构二叉树
            TreeNode parent = root;
            for (int i = 1; i < list.size(); i++) {
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
     * int val;
     * TreeNode left;
     * TreeNode right;
     * TreeNode() {}
     * TreeNode(int val) { this.val = val; }
     * TreeNode(int val, TreeNode left, TreeNode right) {
     * this.val = val;
     * this.left = left;
     * this.right = right;
     * }
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
     * <p>
     * Two binary trees are considered the same if they are structurally identical, and the nodes have the same value.
     * <p>
     * Input: p = [1,2,3], q = [1,2,3]
     * Output: true
     * <p>
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
     * <p>
     * Nary-Tree input serialization is represented in their level order traversal. Each group of children
     * is separated by the null value (See examples)
     * <p>
     * Input: root = [1,null,3,2,4,null,5,6]
     * Output: [1,3,5,6,2,4]
     */
    class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    ;

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
            for (Node child : root.children) {
                helper(res, child);
            }
        }
    }

    // 559 Maximum of n-array tree

    /**
     * Given a n-ary tree, find its maximum depth.
     * <p>
     * The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
     * <p>
     * Nary-Tree input serialization is represented in their level order traversal, each group of children is separated by the null value (See examples).
     * <p>
     * Input: root = [1,null,3,2,4,null,5,6]
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
                height = depth;
                res = root.val;
            }
            helper(root.left, depth + 1);
            helper(root.right, depth + 1);
        }
    }

    // 113. Path Sum II

    /**
     * Given the root of a binary tree and an integer targetSum, return all root-to-leaf paths where the sum of
     * the node values in the path equals targetSum. Each path should be returned as a list of the node values,
     * not node references.
     * <p>
     * A root-to-leaf path is a path starting from the root and ending at any leaf node. A leaf is a node with no children.
     * <p>
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
     * <p>
     * The path does not need to start or end at the root or a leaf, but it must go downwards (i.e.,
     * traveling only from parent nodes to child nodes).
     * <p>
     * Input: root = [10,5,-3,3,2,null,11,3,-2,null,1], targetSum = 8
     * Output: 3
     * Explanation: The paths that sum to 8 are shown.
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
     * <p>
     * Each root-to-leaf path in the tree represents a number.
     * <p>
     * For example, the root-to-leaf path 1 -> 2 -> 3 represents the number 123.
     * Return the total sum of all root-to-leaf numbers. Test cases are generated so that the answer will fit in a
     * 32-bit integer.
     * <p>
     * A leaf node is a node with no children.
     * <p>
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

    // 98: valid BST
    /**
     * Given the root of a binary tree, determine if it is a valid binary search tree (BST).
     *
     * A valid BST is defined as follows:
     *
     * The left subtree of a node contains only nodes with keys less than the node's key.
     * The right subtree of a node contains only nodes with keys greater than the node's key.
     * Both the left and right subtrees must also be binary search trees.
     *
     *
     * Example 1:
     *
     *
     * Input: root = [2,1,3]
     * Output: true
     * Example 2:
     *
     *
     * Input: root = [5,1,4,null,null,3,6]
     * Output: false
     * Explanation: The root node's value is 5 but its right child's value is 4.
     */
    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     * int val;
     * TreeNode left;
     * TreeNode right;
     * TreeNode() {}
     * TreeNode(int val) { this.val = val; }
     * TreeNode(int val, TreeNode left, TreeNode right) {
     * this.val = val;
     * this.left = left;
     * this.right = right;
     * }
     * }
     */
    class Solution98 {
        public boolean isValidBST(TreeNode root) {
            return helper(root, Long.MIN_VALUE, Long.MAX_VALUE);
        }

        /**
         * 陷阱1：
         * 不能单纯的比较左节点小于中间节点，右节点大于中间节点就够了。我们要比较的是左子树所有节点小于中间节点，右子树左右节点大于中间节点。
         * 陷阱2：
         * 样例中的最小节点，可能是int的最小值，如果用最小的int(Integer.MIN_VALUE)来比较是不行的。此时可以初始化为Long.MIN_VALUE
         * 树的每一个节点都有一个取址范围，根节点root的取址范围是（-∞，+∞），而它的左节点的范围则是（-∞，root.val）,
         * 它的右节点的取址范围是（root.val，+∞）。后面就可以递归啦。
         */

        public boolean helper(TreeNode root, long left, long right) {
            if (root == null) return true;
            if (root.val <= left || root.val >= right) return false;
            return helper(root.left, left, root.val) && helper(root.right, root.val, right);

        }
    }

    // Minimum Depth of Binary Tree

    /**
     * Given a binary tree, find its minimum depth.
     * <p>
     * The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.
     * <p>
     * Note: A leaf is a node with no children.
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: root = [3,9,20,null,null,15,7]
     * Output: 2
     * Example 2:
     * <p>
     * Input: root = [2,null,3,null,4,null,5,null,6]
     * Output: 5
     */
    class Solution111 {
        public int minDepth(TreeNode root) {
            if (root == null) return 0;
            // 如果碰到左子树或者右子树为空，就得取最大值，不然会出现漏层数的情况
            if (root.left == null || root.right == null) {
                return Math.max(minDepth(root.left), minDepth(root.right)) + 1;
            }
            return Math.min(minDepth(root.left), minDepth(root.right)) + 1;

        }
    }

    // 572： Subtree Of Another tree

    /**
     * Given the roots of two binary trees root and subRoot, return true if there is a subtree of root with the same
     * structure and node values of subRoot and false otherwise.
     * <p>
     * A subtree of a binary tree tree is a tree that consists of a node in tree and all of this node's descendants.
     * The tree tree could also be considered as a subtree of itself.
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: root = [3,4,5,1,2], subRoot = [4,1,2]
     * Output: true
     * Example 2:
     * <p>
     * <p>
     * Input: root = [3,4,5,1,2,null,null,null,null,0], subRoot = [4,1,2]
     * Output: false
     */

    class Solution572 {
        public boolean isSubtree(TreeNode root, TreeNode subRoot) {
            if (root == null) return false;
            if (isSameTree(root, subRoot)) return true;
            return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
        }

        public boolean isSameTree(TreeNode p, TreeNode q) {
            if (p == null && q == null) return true;
            if (p == null || q == null) return false;
            if (p.val != q.val) return false;
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        }
    }

    // 107: Binary Tree Level order traversal II (BFS)

    /**
     * Given the root of a binary tree, return the bottom-up level order traversal of its nodes' values.
     * (i.e., from left to right, level by level from leaf to root).
     * <p>
     * Example 1:
     * <p>
     * Input: root = [3,9,20,null,null,15,7]
     * Output: [[15,7],[9,20],[3]]
     * Example 2:
     * <p>
     * Input: root = [1]
     * Output: [[1]]
     * Example 3:
     * <p>
     * Input: root = []
     * Output: []
     */
    class Solution107 {
        public List<List<Integer>> levelOrderBottom(TreeNode root) {
            List<List<Integer>> res = new ArrayList<>();
            if (root == null) return res;
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                int size = queue.size();
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    TreeNode cur = queue.poll();
                    if (cur.left != null) queue.offer(cur.left);
                    if (cur.right != null) queue.offer(cur.right);
                    list.add(cur.val);
                }
                res.add(0, list);
            }
            return res;
        }
    }

    // 103 : Binary tree zigzag level order traversal

    /**
     * Given the root of a binary tree, return the zigzag level order traversal of its nodes' values. (i.e., from left to right, then right to left for the next level and alternate between).
     * <p>
     * <p>
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: root = [3,9,20,null,null,15,7]
     * Output: [[3],[20,9],[15,7]]
     * Example 2:
     * <p>
     * Input: root = [1]
     * Output: [[1]]
     * Example 3:
     * <p>
     * Input: root = []
     * Output: []
     */
    class Solution103 {
        public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            List<List<Integer>> res = new ArrayList<>();
            if (root == null) return res;
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            boolean x = true;
            while (!queue.isEmpty()) {
                int size = queue.size();
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    TreeNode cur = queue.poll();
                    if (x) list.add(cur.val);  // 如果x为true，从尾部加
                    else list.add(0, cur.val);  // 否则，从头部加
                    if (cur.left != null) queue.offer(cur.left);
                    if (cur.right != null) queue.offer(cur.right);
                }
                res.add(list);
                x = x ? false : true;
            }
            return res;
        }
    }

    // 226： Invert Binary tree

    /**
     * Given the root of a binary tree, invert the tree, and return its root.
     * <p>
     * Example 1:
     * <p>
     * Input: root = [4,2,7,1,3,6,9]
     * Output: [4,7,2,9,6,3,1]
     * Example 2:
     * <p>
     * <p>
     * Input: root = [2,1,3]
     * Output: [2,3,1]
     * Example 3:
     * <p>
     * Input: root = []
     * Output: []
     */
    class Solution226 {
        public TreeNode invertTree(TreeNode root) {
            if (root == null) return root;
            TreeNode left = invertTree(root.left);
            TreeNode right = invertTree(root.right);
            root.left = right;
            root.right = left;
            return root;
        }

        public TreeNode invertree1(TreeNode root) {
            if (root == null) return root;
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    TreeNode cur = queue.poll();
                    TreeNode tmp = cur.left;
                    cur.left = cur.right;
                    cur.right = tmp;
                    if (cur.left != null) queue.offer(cur.left);
                    if (cur.right != null) queue.offer(cur.right);
                }
            }
            return root;
        }
    }

    // 404: Sum of left leaves

    /**
     * Given the root of a binary tree, return the sum of all left leaves.
     * <p>
     * A leaf is a node with no children. A left leaf is a leaf that is the left child of another node.
     * <p>
     * <p>
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: root = [3,9,20,null,null,15,7]
     * Output: 24
     * Explanation: There are two left leaves in the binary tree, with values 9 and 15 respectively.
     * Example 2:
     * <p>
     * Input: root = [1]
     * Output: 0
     */
    class Solution404 {
        public int sumOfLeftLeaves(TreeNode root) {
            if (root == null) return 0;
            int res = 0;
            if (root.left != null) {  // 判断左节点是否为空
                if (root.left.left == null && root.left.right == null) {  // 判断是否为叶子节点
                    res += root.left.val;
                } else res += sumOfLeftLeaves(root.left);
            }
            res += sumOfLeftLeaves(root.right);
            return res;
        }

        public int sumOfLeftLeavesQueue(TreeNode root) {
            if (root == null) return 0;
            int res = 0;
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                TreeNode cur = queue.poll();
                if (cur.left != null) {
                    if (cur.left.left == null && cur.left.right == null) res += cur.left.val;
                    else queue.offer(cur.left);
                }
                if (cur.right != null) queue.offer(cur.right);
            }
            return res;
        }
    }

    // 429:N-ary tree level order traversl

    /**
     * Given an n-ary tree, return the level order traversal of its nodes' values.
     * <p>
     * Nary-Tree input serialization is represented in their level order traversal, each group of children is
     * separated by the null value (See examples).
     * <p>
     * Example 1:
     * Input: root = [1,null,3,2,4,null,5,6]
     * Output: [[1],[3,2,4],[5,6]]
     * Example 2:
     * <p>
     * Input: root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
     * Output: [[1],[2,3,4,5],[6,7,8,9,10],[11,12,13],[14]]
     */

    class Solution429 {
        public List<List<Integer>> levelOrder(Node root) {
            List<List<Integer>> res = new ArrayList<>();
            if (root == null) return res;
            Queue<Node> queue = new LinkedList<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                List<Integer> curLevel = new ArrayList<>();
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    Node cur = queue.poll();
                    curLevel.add(cur.val);
                    for (Node child : cur.children) queue.offer(child);
                }
                res.add(curLevel);
            }
            return res;
        }
    }

    // 515: Find Largest value in Each Tree Row (BFS)

    /**
     * Given the root of a binary tree, return an array of the largest value in each row of the tree (0-indexed).
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: root = [1,3,2,5,3,null,9]
     * Output: [1,3,9]
     * Example 2:
     * <p>
     * Input: root = [1,2,3]
     * Output: [1,3]
     * <p>
     * 都是O（N）
     */
    class Solution515 {
        public List<Integer> largestValues(TreeNode root) {
            List<Integer> res = new ArrayList<>();
            if (root == null) return res;

            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            int max = Integer.MIN_VALUE;
            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    TreeNode cur = queue.poll();
                    max = Math.max(max, cur.val);
                    if (cur.left != null) queue.offer(cur.left);
                    if (cur.right != null) queue.offer(cur.right);
                }
                res.add(max);
                max = Integer.MIN_VALUE;
            }
            return res;
        }
    }

    // 110 Binary Balanced Tree

    /**
     * Given a binary tree, determine if it is height-balanced.
     * <p>
     * For this problem, a height-balanced binary tree is defined as:
     * <p>
     * a binary tree in which the left and right subtrees of every node differ in height by no more than 1.
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: root = [3,9,20,null,null,15,7]
     * Output: true
     * Example 2:
     * <p>
     * <p>
     * Input: root = [1,2,2,3,3,null,null,4,4]
     * Output: false
     */
    class Solution110 {
        public boolean isBalanced(TreeNode root) {
            if (root == null) return true;
            return helper(root) != -1;
        }

        public int helper(TreeNode root) {
            if (root == null) return 0;
            int l = helper(root.left);
            int r = helper(root.right);
            if (Math.abs(l - r) > 1 || l == -1 || r == -1) return -1;
            return Math.max(l, r) + 1;

        }
    }

    // 563: Binary Tree Tilt

    /**
     * Given the root of a binary tree, return the sum of every tree node's tilt.
     * <p>
     * The tilt of a tree node is the absolute difference between the sum of all left subtree node values and all right
     * subtree node values. If a node does not have a left child, then the sum of the left subtree node values is
     * treated as 0. The rule is similar if the node does not have a right child.
     * <p>
     * <p>
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: root = [1,2,3]
     * Output: 1
     * Explanation:
     * Tilt of node 2 : |0-0| = 0 (no children)
     * Tilt of node 3 : |0-0| = 0 (no children)
     * Tilt of node 1 : |2-3| = 1 (left subtree is just left child, so sum is 2; right subtree is just right child, so sum is 3)
     * Sum of every tilt : 0 + 0 + 1 = 1
     * Example 2:
     * <p>
     * <p>
     * Input: root = [4,2,9,3,5,null,7]
     * Output: 15
     * Explanation:
     * Tilt of node 3 : |0-0| = 0 (no children)
     * Tilt of node 5 : |0-0| = 0 (no children)
     * Tilt of node 7 : |0-0| = 0 (no children)
     * Tilt of node 2 : |3-5| = 2 (left subtree is just left child, so sum is 3; right subtree is just right child, so sum is 5)
     * Tilt of node 9 : |0-7| = 7 (no left child, so sum is 0; right subtree is just right child, so sum is 7)
     * Tilt of node 4 : |(3+5+2)-(9+7)| = |10-16| = 6 (left subtree values are 3, 5, and 2, which sums to 10; right
     * subtree values are 9 and 7, which sums to 16)
     * Sum of every tilt : 0 + 0 + 0 + 2 + 7 + 6 = 15
     */

    class Solution563 {
        int res = 0;

        public int findTilt(TreeNode root) {
            helper(root);
            return res;
        }

        public int helper(TreeNode root) {
            if (root == null) return 0;
            int left = helper(root.left);
            int right = helper(root.right);
            res += Math.abs(left - right);
            return left + right + root.val;
        }
    }

    // 543: Diameter of Binary Tree

    /**
     * Given the root of a binary tree, return the length of the diameter of the tree.
     * <p>
     * The diameter of a binary tree is the length of the longest path between any two nodes in a tree. This path may
     * or may not pass through the root.
     * <p>
     * The length of a path between two nodes is represented by the number of edges between them.
     * <p>
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: root = [1,2,3,4,5]
     * Output: 3
     * Explanation: 3 is the length of the path [4,2,1,3] or [5,2,1,3].
     * Example 2:
     * <p>
     * Input: root = [1,2]
     * Output: 1
     */

    class Solution543 {
        private int res = 0;

        public int diameterOfBinaryTree(TreeNode root) {
            helper(root);
            return res;
        }

        public int helper(TreeNode root) {
            if (root == null) return 0;
            int left = helper(root.left);  // 左子树的最大深度
            int right = helper(root.right);  // 右子树的最大深度
            res = Math.max(res, left + right);
            return Math.max(left, right) + 1;
        }
    }

    // find leaves of Binary Tree
    class Solution366 {
        public List<List<Integer>> findLeaves(TreeNode root) {
            List<List<Integer>> res = new ArrayList<>();
            helper(res, root);
            return res;
        }

        public int helper(List<List<Integer>> res, TreeNode root) {
            if (root == null) return -1;
            int left = helper(res, root.left);
            int right = helper(res, root.right);
            int level = Math.max(left, right) + 1;
            if (res.size() == level) res.add(new ArrayList<>());
            res.get(level).add(root.val);
            root.left = null;
            root.right = null;
            return level;
        }
    }

    // 337 : House Robber III

    /**
     * The thief has found himself a new place for his thievery again. There is only one entrance to this area, called root.
     * <p>
     * Besides the root, each house has one and only one parent house. After a tour, the smart thief realized that all
     * houses in this place form a binary tree. It will automatically contact the police if two directly-linked houses
     * were broken into on the same night.
     * <p>
     * Given the root of the binary tree, return the maximum amount of money the thief can rob without alerting the police.
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: root = [3,2,3,null,3,null,1]
     * Output: 7
     * Explanation: Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
     * Example 2:
     * <p>
     * <p>
     * Input: root = [3,4,5,1,3,null,1]
     * Output: 9
     * Explanation: Maximum amount of money the thief can rob = 4 + 5 = 9.
     */
    class Solution337 {
        public int rob(TreeNode root) {
            if (root == null) return 0;
            int val = 0;
            if (root.left != null) {
                val += rob(root.left.left) + rob(root.left.right);
            }
            if (root.right != null) {
                val += rob(root.right.left) + rob(root.right.right);
            }
            return Math.max(val + root.val, rob(root.left) + rob(root.right));
        }

        public int rob2(TreeNode root) {
            int[] res = robSub(root);
            return Math.max(res[0], res[1]);  // 1：第一层，第三次取去偷，0：第2层去偷
        }

        public int[] robSub(TreeNode root) {
            if (root == null) return new int[2];
            int[] left = robSub(root.left);
            int[] right = robSub(root.right);
            int[] res = new int[2];

            res[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);  // 二四层
            res[1] = root.val + left[0] + right[0];   // 偷一三层
            return res;
        }
    }

    // 236： lowest common Ancestor of a binary tree 经典必考题

    /**
     * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
     * <p>
     * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and
     * q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
     * Output: 3
     * Explanation: The LCA of nodes 5 and 1 is 3.
     * Example 2:
     * <p>
     * <p>
     * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
     * Output: 5
     * Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according
     * to the LCA definition.
     */
    class Solution236 {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if (root == null || root == p || root == q) return root;
            TreeNode left = lowestCommonAncestor(root.left, p, q);
            TreeNode right = lowestCommonAncestor(root.right, p, q);
            if (left != null && root != null) return root;
            return left == null ? right : left;
        }
    }

    // 508： Most Frequent subtree sum

    /**
     * Given the root of a binary tree, return the most frequent subtree sum. If there is a tie, return all the values with the highest frequency in any order.
     * <p>
     * The subtree sum of a node is defined as the sum of all the node values formed by the subtree rooted at that node (including the node itself).
     * Example 1:
     * <p>
     * Input: root = [5,2,-3]
     * Output: [2,-3,4]
     * Example 2:
     * <p>
     * Input: root = [5,2,-5]
     * Output: [2]
     */
    class Solution508 {

        int max;  // 最大的值
        HashMap<Integer, Integer> map;  // 记录出现的sum的次数

        public int[] findFrequentTreeSum(TreeNode root) {
            if (root == null) return new int[]{};
            map = new HashMap<>();
            max = 0;
            helper(root);  //  postOrder
            List<Integer> list = new ArrayList<>();
            for (int key : map.keySet()) {  // 遍历sum的map
                if (map.get(key) == max) list.add(key);  // 如果找到了最大值的key，添加到结果中
            }
            int[] res = new int[list.size()];
            for (int i = 0; i < list.size(); i++) res[i] = list.get(i);

            return res;
        }

        public int helper(TreeNode root) {
            if (root == null) return 0;
            int left = helper(root.left);
            int right = helper(root.right);

            int sum = left + right + root.val;
            int count = map.getOrDefault(sum, 0) + 1;
            map.put(sum, count);  // 计算这个sum出现的次数
            max = Math.max(max, count);
            return sum;
        }
    }

    // 173： Binary search tree iterator

    /**
     * Implement the BSTIterator class that represents an iterator over the in-order traversal of a binary search tree (BST):
     * <p>
     * BSTIterator(TreeNode root) Initializes an object of the BSTIterator class. The root of the BST is given as part
     * of the constructor. The pointer should be initialized to a non-existent number smaller than any element in the BST.
     * boolean hasNext() Returns true if there exists a number in the traversal to the right of the pointer, otherwise returns false.
     * int next() Moves the pointer to the right, then returns the number at the pointer.
     * Notice that by initializing the pointer to a non-existent smallest number, the first call to next() will return
     * the smallest element in the BST.
     * <p>
     * You may assume that next() calls will always be valid. That is, there will be at least a next number in the in-order
     * traversal when next() is called.
     * <p>
     * Example 1:
     * <p>
     * Input
     * ["BSTIterator", "next", "next", "hasNext", "next", "hasNext", "next", "hasNext", "next", "hasNext"]
     * [[[7, 3, 15, null, null, 9, 20]], [], [], [], [], [], [], [], [], []]
     * Output
     * [null, 3, 7, true, 9, true, 15, true, 20, false]
     * <p>
     * Explanation
     * BSTIterator bSTIterator = new BSTIterator([7, 3, 15, null, null, 9, 20]);
     * bSTIterator.next();    // return 3
     * bSTIterator.next();    // return 7
     * bSTIterator.hasNext(); // return True
     * bSTIterator.next();    // return 9
     * bSTIterator.hasNext(); // return True
     * bSTIterator.next();    // return 15
     * bSTIterator.hasNext(); // return True
     * bSTIterator.next();    // return 20
     * bSTIterator.hasNext(); // return False
     */
    class BSTIterator {

        private TreeNode cur;
        private Stack<TreeNode> stack;

        public BSTIterator(TreeNode root) {
            cur = root;
            stack = new Stack<>();
        }

        public int next() {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }  // 相当于中序遍历，先走到最左边
            cur = stack.pop();  // 再处理当前的值
            int val = cur.val;
            cur = cur.right;  // 最后指针向右走
            return val;
        }

        public boolean hasNext() {
            if (!stack.isEmpty() || cur != null) return true;
            return false;
        }
    }

    // 538: Convert BST to Greater Tree

    /**
     * Given the root of a Binary Search Tree (BST), convert it to a Greater Tree such that every key of the original
     * BST is changed to the original key plus the sum of all keys greater than the original key in BST.
     * <p>
     * As a reminder, a binary search tree is a tree that satisfies these constraints:
     * <p>
     * The left subtree of a node contains only nodes with keys less than the node's key.
     * The right subtree of a node contains only nodes with keys greater than the node's key.
     * Both the left and right subtrees must also be binary search trees.
     * <p>
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: root = [4,1,6,0,2,5,7,null,null,null,3,null,null,null,8]
     * Output: [30,36,21,36,35,26,15,null,null,null,33,null,null,null,8]
     * Example 2:
     * <p>
     * Input: root = [0,null,1]
     * Output: [1,null,1]
     */
    class Solution538 {

        private int sum = 0;

        public TreeNode convertBST(TreeNode root) {
            if (root == null) return null;
            convertBST(root.right);  // 先遍历大的，从大到小逆序遍历
            sum += root.val;  // 加上当前的值
            root.val = sum;
            convertBST(root.left);
            return root;
        }
    }

    // 108： Convert Sorted Array to Binary Search Tree

    /**
     * Given an integer array nums where the elements are sorted in ascending order, convert it to a height-balanced
     * binary search tree.
     *
     * A height-balanced binary tree is a binary tree in which the depth of the two subtrees of every node
     * never differs by more than one.
     *
     * Input: nums = [-10,-3,0,5,9]
     * Output: [0,-3,9,-10,null,5]
     * Explanation: [0,-10,5,null,-3,null,9] is also accepted:
     */
    /**
     * 二叉搜索树的性质为中间节点大于左边孩子，小于右边孩子，排完序的数组是可以用二分法找到中间节点的
     */
    class Solution108 {
        public TreeNode sortedArrayToBST(int[] nums) {
            if (nums == null || nums.length == 0) return null;
            return helper(nums, 0, nums.length - 1);
        }

        public TreeNode helper(int[] nums, int left, int right) {
            if (left > right) return null;
            int mid = (right - left) / 2 + left;
            TreeNode node = new TreeNode(nums[mid]);
            node.left = helper(nums, left, mid - 1);
            node.right = helper(nums, mid + 1, right);
            return node;
        }
    }

    //285 ： Inorder Successor

    /**
     * Given the root of a binary search tree and a node p in it, return the in-order successor of that node in the BST.
     * If the given node has no in-order successor in the tree, return null.
     * <p>
     * The successor of a node p is the node with the smallest key greater than p.val.
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: root = [2,1,3], p = 1
     * Output: 2
     * Explanation: 1's in-order successor node is 2. Note that both p and the return value is of TreeNode type.
     * Example 2:
     * <p>
     * <p>
     * Input: root = [5,3,6,2,4,null,null,1], p = 6
     * Output: null
     * Explanation: There is no in-order successor of the current node, so the answer is null.
     */
    class Solution285 {
        public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
            if (root == null) return null;
            TreeNode res = null;
            while (root != null) {
                if (root.val <= p.val) {  // 要找的值肯定是在p节点的右边，如果右边的节点还有左子树，就是那个左子树
                    root = root.right;
                } else {
                    res = root;
                    root = root.left;
                }

            }
            return res;
        }
    }

    // 545： Boundary of Binary Tree

    /**
     * The boundary of a binary tree is the concatenation of the root, the left boundary, the leaves ordered from
     * left-to-right, and the reverse order of the right boundary.
     * <p>
     * The left boundary is the set of nodes defined by the following:
     * <p>
     * The root node's left child is in the left boundary. If the root does not have a left child, then the left boundary is empty.
     * If a node in the left boundary and has a left child, then the left child is in the left boundary.
     * If a node is in the left boundary, has no left child, but has a right child, then the right child is in the left boundary.
     * The leftmost leaf is not in the left boundary.
     * The right boundary is similar to the left boundary, except it is the right side of the root's right subtree. Again,
     * the leaf is not part of the right boundary, and the right boundary is empty if the root does not have a right child.
     * <p>
     * The leaves are nodes that do not have any children. For this problem, the root is not a leaf.
     * <p>
     * Given the root of a binary tree, return the values of its boundary.
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: root = [1,null,2,3,4]
     * Output: [1,3,4,2]
     * Explanation:
     * - The left boundary is empty because the root does not have a left child.
     * - The right boundary follows the path starting from the root's right child 2 -> 4.
     * 4 is a leaf, so the right boundary is [2].
     * - The leaves from left to right are [3,4].
     * Concatenating everything results in [1] + [] + [3,4] + [2] = [1,3,4,2].
     * Example 2:
     * <p>
     * <p>
     * Input: root = [1,2,3,4,5,6,null,null,null,7,8,9,10]
     * Output: [1,2,4,7,8,9,10,6,3]
     * Explanation:
     * - The left boundary follows the path starting from the root's left child 2 -> 4.
     * 4 is a leaf, so the left boundary is [2].
     * - The right boundary follows the path starting from the root's right child 3 -> 6 -> 10.
     * 10 is a leaf, so the right boundary is [3,6], and in reverse order is [6,3].
     * - The leaves from left to right are [4,7,8,9,10].
     * Concatenating everything results in [1] + [2] + [4,7,8,9,10] + [6,3] = [1,2,4,7,8,9,10,6,3].
     */
    class Solution545 {

        List<Integer> res = new ArrayList<>();

        public List<Integer> boundaryOfBinaryTree(TreeNode root) {
            if (root == null) return res;
            res.add(root.val);
            leftBoundary(root.left);
            leaves(root.left);
            leaves(root.right);
            rightBoundary(root.right);
            return res;
        }

        // 只走左边的一侧
        public void leftBoundary(TreeNode root) {
            // 如果是叶子节点直接return
            if (root == null || (root.left == null && root.right == null)) return;
            res.add(root.val);
            if (root.left == null) leftBoundary(root.right);
            else leftBoundary(root.left);
        }

        public void rightBoundary(TreeNode root) {
            if (root == null || (root.left == null && root.right == null)) return;
            if (root.right == null) rightBoundary(root.left);
            else rightBoundary(root.right);
            res.add(root.val);  // 右边是后加入的，相当于后序
        }

        // 叶子节点遍历
        public void leaves(TreeNode root) {
            if (root == null) return;
            if (root.left == null && root.right == null) {
                res.add(root.val);
                return;
            }
            leaves(root.left);
            leaves(root.right);
        }
    }

    // 297: Serialize and Deserialize Binary Tree 很重要！！

    /**
     * Serialization is the process of converting a data structure or object into a sequence of bits so that it can be
     * stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in
     * the same or another computer environment.
     * <p>
     * Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your
     * serialization/deserialization algorithm should work. You just need to ensure that a binary tree can
     * be serialized to a string and this string can be deserialized to the original tree structure.
     * <p>
     * Clarification: The input/output format is the same as how LeetCode serializes a binary tree. You do
     * not necessarily need to follow this format, so please be creative and come up with different approaches yourself.
     * <p>
     * Example 1:
     * <p>
     * Input: root = [1,2,3,null,null,4,5]
     * Output: [1,2,3,null,null,4,5]
     * Example 2:
     * <p>
     * Input: root = []
     * Output: []
     */

    public class Codec {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null) return "";
            Queue<TreeNode> queue = new LinkedList<>();
            StringBuilder sb = new StringBuilder();
            queue.offer(root);
            while (!queue.isEmpty()) {
                TreeNode cur = queue.poll();
                if (cur == null) {
                    sb.append("null ");
                    continue;
                }
                sb.append(cur.val + " ");
                queue.offer(cur.left);
                queue.offer(cur.right);
            }
            return sb.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            if (data == "") return null;
            String[] str = data.split(" ");
            TreeNode root = new TreeNode(Integer.parseInt(str[0]));
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            for (int i = 0; i < str.length; i++) {
                TreeNode cur = queue.poll();
                if (!str[i].equals("null")) {
                    cur.left = new TreeNode(Integer.parseInt(str[i]));
                    queue.offer(cur.left);
                }
                if (!str[++i].equals("null")) {
                    cur.right = new TreeNode(Integer.parseInt(str[i]));
                    queue.offer(cur.right);
                }
            }
            return root;
        }
    }

    // 449: SerializeAndDeserializeBST

    /**
     * Serialization is converting a data structure or object into a sequence of bits so that it can be stored in a
     * file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.
     * <p>
     * Design an algorithm to serialize and deserialize a binary search tree. There is no restriction on how your
     * serialization/deserialization algorithm should work. You need to ensure that a binary search tree can be serialized to a string, and this string can be deserialized to the original tree structure.
     * <p>
     * The encoded string should be as compact as possible.
     * <p>
     * Example 1:
     * <p>
     * Input: root = [2,1,3]
     * Output: [2,1,3]
     * Example 2:
     * <p>
     * Input: root = []
     * Output: []
     */
    public class Codec1 {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null) return "";
            StringBuilder res = new StringBuilder();
            Stack<TreeNode> stack = new Stack<>();
            stack.push(root);

            while (!stack.isEmpty()) {
                TreeNode cur = stack.pop();
                res.append(cur.val + " ");
                if (cur.right != null) stack.push(cur.right);
                if (cur.left != null) stack.push(cur.left);
            }
            return res.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            if (data == "") return null;
            String[] str = data.split(" ");
            Queue<Integer> qu = new LinkedList<>();
            for (String s : str) {
                qu.offer(Integer.parseInt(s));
            }
            return getNode(qu);
        }

        public TreeNode getNode(Queue<Integer> queue) {
            if (queue.isEmpty()) return null;
            TreeNode root = new TreeNode(queue.poll());
            Queue<Integer> smallerQ = new LinkedList<>();  //  存放左节点
            while (!queue.isEmpty() && queue.peek() < root.val) {
                smallerQ.offer(queue.poll());  // 所有左节点放入（小于root的）
            }
            root.left = getNode(smallerQ);
            root.right = getNode(queue);  // queue存放大于的
            return root;
        }
    }

    // 510：Inorder Successor in BST II

    /**
     * Given a node in a binary search tree, return the in-order successor of that node in the BST. If that node has
     * no in-order successor, return null.
     * <p>
     * The successor of a node is the node with the smallest key greater than node.val.
     * <p>
     * You will have direct access to the node but not to the root of the tree. Each node will have a reference to
     * its parent node. Below is the definition for Node:
     * <p>
     * class Node {
     * public int val;
     * public Node left;
     * public Node right;
     * public Node parent;
     * }
     * <p>
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: tree = [2,1,3], node = 1
     * Output: 2
     * Explanation: 1's in-order successor node is 2. Note that both the node and the return value is of Node type.
     * Example 2:
     * <p>
     * <p>
     * Input: tree = [5,3,6,2,4,null,null,1], node = 6
     * Output: null
     * Explanation: There is no in-order successor of the current node, so the answer is null.
     */
    class Solution510 {
        class Node {
            public int val;
            public Node left;
            public Node right;
            public Node parent;

            public Node(int val) {
                this.val = val;
            }
        }

        ;

        public Node inorderSuccessor(Node node) {
            if (node.right != null) {
                node = node.right;
                while (node.left != null) node = node.left;
                return node;
            }
            while (node.parent != null && node == node.parent.right) {
                node = node.parent;
            }
            return node.parent;
        }
    }

    // 250: Count Univalue SubTrees

    /**
     * Given the root of a binary tree, return the number of uni-value subtrees.
     * <p>
     * A uni-value subtree means all nodes of the subtree have the same value.
     * <p>
     * Input: root = [5,1,5,5,5,null,5]
     * Output: 4
     * Example 2:
     * <p>
     * Input: root = []
     * Output: 0
     * Example 3:
     * <p>
     * Input: root = [5,5,5,5,5,null,5]
     * Output: 6
     */
    class Solution250 {
        int res;

        public int countUnivalSubtrees(TreeNode root) {
            res = 0;
            helper(root);
            return res;
        }

        public boolean helper(TreeNode root) {
            if (root == null) return true;

            boolean left = helper(root.left);
            boolean right = helper(root.right);
            if (left && right) {
                if (root.left != null && root.val != root.left.val) return false;
                if (root.right != null && root.val != root.right.val) return false;
                res++;
                return true;
            }
            return false;
        }
    }

    // 255:

    /**
     * Given an array of unique integers preorder, return true if it is the correct preorder traversal sequence of a binary search tree.
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: preorder = [5,2,1,3,6]
     * Output: true
     * Example 2:
     * <p>
     * Input: preorder = [5,2,6,1,3]
     * Output: false
     */
    class Solution255 {
        public boolean verifyPreorder(int[] preorder) {
            Stack<Integer> stack = new Stack<>();  // stack存的是递减的序列,为当前取出的最小值
            int min = Integer.MIN_VALUE;  // 保存当前的最小值（前序遍历的子树都小于当前的节点）
            for (int num : preorder) {
                if (num < min) {  // 如果发现当前的值小于最小值的直接返回false
                    return false;
                }
                while (!stack.isEmpty() && num > stack.peek()) min = stack.pop();
                stack.push(num);
            }
            return true;
        }
    }

    // 336 Find leaves of BST

    /**
     * Given the root of a binary tree, collect a tree's nodes as if you were doing this:
     *
     * Collect all the leaf nodes.
     * Remove all the leaf nodes.
     * Repeat until the tree is empty.
     *
     *
     * Example 1:
     *
     *
     * Input: root = [1,2,3,4,5]
     * Output: [[4,5,3],[2],[1]]
     * Explanation:
     * [[3,5,4],[2],[1]] and [[3,4,5],[2],[1]] are also considered correct answers since per each level it does
     * not matter the order on which elements are returned.
     * Example 2:
     *
     * Input: root = [1]
     * Output: [[1]]
     */
    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     * int val;
     * TreeNode left;
     * TreeNode right;
     * TreeNode() {}
     * TreeNode(int val) { this.val = val; }
     * TreeNode(int val, TreeNode left, TreeNode right) {
     * this.val = val;
     * this.left = left;
     * this.right = right;
     * }
     * }
     */
    class Solution336 {
        public List<List<Integer>> findLeaves(TreeNode root) {
            List<List<Integer>> res = new ArrayList<>();
            if (root == null) return res;
            helper(root, res);
            return res;
        }

        /**
         * 由于要遍历到叶子节点，使用后序遍历，要一层一层的添加元素，要使用level来记录当前的层数
         *
         * @param root
         * @param res
         * @return
         */

        public int helper(TreeNode root, List<List<Integer>> res) {
            if (root == null) return -1;
            int left = helper(root.left, res);
            int right = helper(root.right, res);
            int level = Math.max(left, right) + 1;
            if (res.size() == level) res.add(new ArrayList<>());
            res.get(level).add(root.val);
            root.left = null;
            root.right = null;
            return level;
        }
    }

    // 116： populating Next right pointers in each node

    /**
     * You are given a perfect binary tree where all leaves are on the same level, and every parent has two children.
     * The binary tree has the following definition:
     * <p>
     * struct Node {
     * int val;
     * Node *left;
     * Node *right;
     * Node *next;
     * }
     * Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.
     * <p>
     * Initially, all next pointers are set to NULL.
     * <p>
     * <p>
     * <p>
     * Example 1:
     * <p>
     * <p>
     * Input: root = [1,2,3,4,5,6,7]
     * Output: [1,#,2,3,#,4,5,6,7,#]
     * Explanation: Given the above perfect binary tree (Figure A), your function should populate each next pointer to
     * point to its next right node, just like in Figure B. The serialized output is in level order as connected by
     * the next pointers, with '#' signifying the end of each level.
     *
     */
    class Solution116 {

        // Definition for a Node.
        class Node {
            public int val;
            public Node left;
            public Node right;
            public Node next;

            public Node() {
            }

            public Node(int _val) {
                val = _val;
            }

            public Node(int _val, Node _left, Node _right, Node _next) {
                val = _val;
                left = _left;
                right = _right;
                next = _next;
            }

            public Node connect(Node root) {
                if (root == null) return root;
                if (root.left != null) root.left.next = root.right;
                if (root.next != null && root.right != null) {
                    root.right.next = root.next.left;
                }
                connect(root.left);
                connect(root.right);
                return root;
            }


        }


        public static void main(String[] args) {
            TreeNode root = new TreeNode(0);
        }
    }
}
