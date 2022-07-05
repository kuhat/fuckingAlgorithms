package com.company.Notes;

import java.util.*;

public class Note5_LinkedList {
    // 2:Add two Numbers

    /**
     * You are given two non-empty linked lists representing two non-negative integers.
     * The digits are stored in reverse order, and each of their nodes contains a single digit. Add the two numbers and
     * return the sum as a linked list.
     * <p>
     * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
     * <p>
     * <p>
     * Input: l1 = [2,4,3], l2 = [5,6,4]
     * Output: [7,0,8]
     * Explanation: 342 + 465 = 807.
     */
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode addTwoNumbers(ListNode listNode1, ListNode listNode2) {
        //创建 个哑节点 他的指针指向新链表的头节点
        //创建一个哑节点，他的指针指向新链表的头节点
        ListNode dummyNode = new ListNode(0);
        //preNode表示当前节点的前一个节点
        ListNode preNode = dummyNode;
        //表示两个节点相加进位的值，加法最多只进一位，所以carry要么是1要么是0
        int carry = 0;
        //两个链表只要有一个不为空，或者有进位就一直循环
        while (listNode1 != null || listNode2 != null || carry != 0) {
            //当前节点的累加值，需要加上前面进位的值
            int sum = carry;
            //如果第一个链表的当前节点不为空，加上第一个链表当前节点的值
            if (listNode1 != null) {
                sum += listNode1.val;
                listNode1 = listNode1.next;
            }
            //第二个链表，同上
            if (listNode2 != null) {
                sum += listNode2.val;
                listNode2 = listNode2.next;
            }
            //创建新的节点，preNode的next指针指向新的节点，因为链表节点
            //只能存储一位数字，所以这里要对sum求余，取个位数。
            preNode.next = new ListNode(sum % 10);
            //如果sum大于等于10，说明有进位，carry为1，
            //否则没有，carry为0
            carry = sum / 10;
            //更新preNode
            preNode = preNode.next;
        }
        return dummyNode.next;
    }

    // 递归写法
    public ListNode addTwoNumbersRecur(ListNode listNode1, ListNode listNode2) {
        ListNode dummy = new ListNode(0);
        ListNode pre = dummy;
        int carry = 0;
        recurHelper(pre, listNode1, listNode2, carry);
        return dummy.next;
    }

    public void recurHelper(ListNode pre, ListNode l1, ListNode l2, int carry) {
        if (l1 != null || l2 != null || carry == 0) return;
        int sum = carry;
        if (l1.next != null) {
            sum += l1.val;
            l1 = l1.next;
        }
        if (l2.next != null) {
            sum += l2.val;
            l2 = l2.next;
        }
        pre.next = new ListNode(sum % 10);
        carry = sum / 10;
        recurHelper(pre.next, l1, l2, carry);
    }

    // 109 Convert Sorted List to Binary Search Tree

    /**
     * Given the head of a singly linked list where elements are sorted in ascending order, convert it to a height balanced BST.
     * <p>
     * For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees
     * of every node never differ by more than 1.
     * <p>
     * Input: head = [-10,-3,0,5,9]
     * Output: [0,-3,9,-10,null,5]
     * Explanation: One possible answer is [0,-3,9,-10,null,5], which represents the shown height balanced BST.
     * <p>
     * Input: head = []
     * Output: []
     */
    public class TreeNode {
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

    // 二叉搜索树的特点是当前节点大于左子树的所有节点，并且小于右子树的所有节点，并
    //且每个节点都具有这个特性。
    //题中说了，是按照升序排列的单链表，我们只需要找到链表的中间节点，让他成为树的根节点，中间节点前面的就是根节点左子树的所有节点，中间节点后面的就是根节点右
    //子树的所有节点，然后使用递归的方式再分别对左右子树进行相同的操作……
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return new TreeNode(head.val);
        // 通过快慢指针找到链表的中间节点slow, pre就是中间节点slow的前一个节点
        ListNode slow = head, fast = head, pre = null;
        while (fast != null && fast.next != null) {
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        // 链表断开为两部分，一部分是node的左子节点，一部分是node的右子节点
        pre.next = null;
        TreeNode node = new TreeNode(slow.val);
        // 从head节点到pre节点是node左子树的节点
        node.left = sortedListToBST(head);
        // 从 slow.next 到链表的末尾是node的右子树的节点
        node.right = sortedListToBST(slow.next);
        return node;
    }

    // 141: Linked List Cycle

    /**
     * Given head, the head of a linked list, determine if the linked list has a cycle in it.
     * <p>
     * There is a cycle in a linked list if there is some node in the list that can be reached again by continuously
     * following the next pointer. Internally, pos is used to denote the index of the node that tail's next pointer is connected to.
     * Note that pos is not passed as a parameter.
     * <p>
     * Return true if there is a cycle in the linked list. Otherwise, return false.
     * <p>
     * Input: head = [3,2,0,-4], pos = 1
     * Output: true
     * Explanation: There is a cycle in the linked list, where the tail connects to the 1st node (0-indexed).
     * <p>
     * Input: head = [1,2], pos = 0
     * Output: true
     * Explanation: There is a cycle in the linked list, where the tail connects to the 0th node.
     *
     * @param head
     * @return
     */

    // 快慢指针解决
    public boolean hasCycle(ListNode head) {
        if (head == null) return false;
        // 快慢两个指针
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }

    // 放到set中
    public boolean hasCycleSet(ListNode head) {
        if (head == null) return false;
        HashSet<ListNode> set = new HashSet<>();
        while(head != null) {
            if (set.contains(head)) return true;
            set.add(head);
            head = head.next;
        }
        return false;
    }

    // 61: Rotate List
    /**
     * Given the head of a linked list, rotate the list to the right by k places.
     *
     * Input: head = [1,2,3,4,5], k = 2
     * Output: [4,5,1,2,3]
     *
     * Input: head = [0,1,2], k = 4
     * Output: [2,0,1]
     * @param args
     */

    //这题k是非负数，但k有可能比链表的长度还要大，所以先要计算链表的长度len，需要旋转的步数就是（k%len）。
    // 一种比较简单的方式就是先把链表连接成一个环，然后再把链表在某个合适的位置断开。
    // 我们可以使用两个指针，一个快指针fas t从头开始遍历直到走到链表的末尾，然后再把链表串成一个环形。还一个指针s low也是从头开始，
    // 走（len-k%len）步就是我们要返回的链表头，这里可能有点疑问，为什么不是走（k%len）步，这是因为我们需要把链
    // 表后面的（k%len）个移到前面，因为单向链表我们没法从后往前遍历，所以我们只能
    // 从前往后移动（len-k%len）步。

    public ListNode rotateRight(ListNode head, int k) {
        if (head == null) return head;
        ListNode fast = head, slow = head;
        int len = 1;
        // 统计链表的长度，找到链表的结尾点
        while(fast.next != null) {
            len++;
            fast = fast.next;
        }
        // 首尾相连，先构成环
        fast.next = head;
        // 慢指针走的步数
        int step = len - k % len;
        // 移动步数，这里大于1实际上是少走了一步
        while (step -- > 1) {
            slow = slow.next;
        }
        // temp就是需要返回的节点
        ListNode temp = slow.next;
        // 因为链表是环形的，slow就相当于尾节点了
        slow.next = null;
        return temp;
    }

    // 3：Longest Substring without Repeating Characters

    /**
     *
     * Given a string s, find the length of the longest substring without repeating characters.
     * @param s
     */
    // 我们使用一个map来存储扫描过的元素，其中i指针是一直往右移动的，如果i指向的元素在map中出现过，
    // 说明出现了重复的元素，要更新j的值。并且这个j的值只能增大不能减小，也就是说j只能往右移动，不能往左移动，
    // 所以下面代码中j取的是重复元素位置的下一个值和j这两个值的最大值
    public int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) return 0;
        HashMap<Character, Integer> map = new HashMap<>();
        int max = 0;
        for (int i = 0, j = 0; i < s.length(); ++i) {
            // 如果有重复的，就修改j的值
            if (map.containsKey(s.charAt(i))) j = Math.max(j, map.get(s.charAt(i) + 1));
            map.put(s.charAt(i), i);
            // 记录查找的最大值
            max = Math.max(max, i - j + 1);
        }
        return max;
    }

    // 队列求解
    public int lengthOfLongestSubstringQueue(String s) {
        // 队列先进先出
        Queue<Character> queue = new LinkedList<>();
        int max = 0;
        for(char c : s.toCharArray()) {
            while(queue.contains(c)) {
                // 如果有重复的，对头出队，直到没有重复的为止
                queue.poll();
            }
            // 添加到队尾
            queue.add(c);
            max = Math.max(max, queue.size());
        }
        return max;
    }

    public static void main(String[] args) {

    }
}
