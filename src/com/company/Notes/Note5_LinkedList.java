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
        while (head != null) {
            if (set.contains(head)) return true;
            set.add(head);
            head = head.next;
        }
        return false;
    }

    // 61: Rotate List

    /**
     * Given the head of a linked list, rotate the list to the right by k places.
     * <p>
     * Input: head = [1,2,3,4,5], k = 2
     * Output: [4,5,1,2,3]
     * <p>
     * Input: head = [0,1,2], k = 4
     * Output: [2,0,1]
     *
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
        while (fast.next != null) {
            len++;
            fast = fast.next;
        }
        // 首尾相连，先构成环
        fast.next = head;
        // 慢指针走的步数
        int step = len - k % len;
        // 移动步数，这里大于1实际上是少走了一步
        while (step-- > 1) {
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
     * Given a string s, find the length of the longest substring without repeating characters.
     *
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
        for (char c : s.toCharArray()) {
            while (queue.contains(c)) {
                // 如果有重复的，对头出队，直到没有重复的为止
                queue.poll();
            }
            // 添加到队尾
            queue.add(c);
            max = Math.max(max, queue.size());
        }
        return max;
    }

    // Leetcode 24: Swap Nodes in Pairs

    /**
     * Given a linked list, swap every two adjacent nodes and return its head. You must solve the problem without
     * modifying the values in the list's nodes (i.e., only nodes themselves may be changed.)
     * <p>
     * Input: head = [1,2,3,4]
     * Output: [2,1,4,3]
     * <p>
     * Input: head = []
     * Output: []
     */

    class Solution24 {
        public ListNode swapPairs(ListNode head) {
            if (head == null || head.next == null) return head;
            ListNode dummy = new ListNode(0);  // 头节点变了，要用dummy
            dummy.next = head;    // dummy ->  2 -> 1 -> 4 -> 3
            ListNode l1 = dummy;  // dummy ->  1 -> 2 -> 3 -> 4
            ListNode l2 = head;   //   ^       ^
            //   l1      l2                               //  l1 l2
            while (l2 != null && l2.next != null) {                             //   _______
                ListNode nextStart = l2.next.next;                              //  |       |
                l1.next = l2.next;  // 将dummy的下一个节点设为l2的下一个 画图画出来就理解了 d  1 -> 2 -> 3
                l2.next.next = l2;  // 将l2的下一个接在dummy后面
                l2.next = nextStart;
                l1 = l2;
                l2 = l2.next;
            }
            return dummy.next;
        }
    }

    // leetCode 328:

    /**
     * Given the head of a singly linked list, group all the nodes with odd indices together
     * followed by the nodes with even indices, and return the reordered list.
     * <p>
     * The first node is considered odd, and the second node is even, and so on.
     * <p>
     * Note that the relative order inside both the even and odd groups should remain as it was in the input.
     * <p>
     * You must solve the problem in O(1) extra space complexity and O(n) time complexity.
     * <p>
     * Input: head = [1,2,3,4,5]
     * Output: [1,3,5,2,4]
     * <p>
     * Input: head = [2,1,3,5,6,4,7]
     * Output: [2,3,6,7,1,5,4]
     */

    class Solution328 {
        public ListNode oddEvenList(ListNode head) {
            if (head == null || head.next == null) return head;
            ListNode odd = head;
            ListNode even = head.next;
            ListNode evenHead = even;  // 将evenHead 存储，然后将奇数的尾指向even的头
            while (even != null && even.next != null) {
                odd.next = odd.next.next;
                even.next = even.next.next;
                odd = odd.next;
                even = even.next;
            }
            odd.next = evenHead;
            return head;
        }
    }

    // LeetCode 206: Reverse Linked List

    /**
     * Given the head of a singly linked list, reverse the list, and return the reversed list.
     *
     * @param args
     */

    class Solution206 {
        public ListNode reverseList(ListNode head) {
            if (head == null || head.next == null) return head;
            ListNode pre = null;
            while (head != null) {
                ListNode temp = head.next;
                head.next = pre;
                pre = head;
                head = temp;
            }
            return pre;
        }
    }

    // leetCode 92: reverse LinkedList ii

    /**
     * Given the head of a singly linked list and two integers left and right where left <= right, reverse the
     * nodes of the list from position left to position right, and return the reversed list.
     * <p>
     * Input: head = [1,2,3,4,5], left = 2, right = 4
     * Output: [1,4,3,2,5]
     * <p>
     * <p>
     * Input: head = [5], left = 1, right = 1
     * Output: [5]
     *
     * @param args
     */


    class Solution92 {
        public ListNode reverseBetween(ListNode head, int m, int n) {
            ListNode dummy = new ListNode(0);
            dummy.next = head;
            ListNode pre = dummy;
            ListNode cur = dummy.next;

            for (int i = 1; i < m; i++) {
                cur = cur.next;
                pre = pre.next;
            }
            for (int i = 0; i < n - m; i++) {
                ListNode temp = cur.next;
                cur.next = temp.next;
                temp.next = pre.next;
                pre.next = temp;
            }
            return dummy.next;
        }
    }

    // LeetCode 142: Linked List Cycle II

    /**
     * Given the head of a linked list, return the node where the cycle begins. If there is no cycle, return null.
     * <p>
     * There is a cycle in a linked list if there is some node in the list that can be reached again by continuously
     * following the next pointer. Internally, pos is used to denote the index of the node that tail's next pointer
     * is connected to (0-indexed). It is -1 if there is no cycle. Note that pos is not passed as a parameter.
     * <p>
     * Do not modify the linked list.
     *
     * @param args
     */
    //通过快慢指针可以判断一个链表是否有环。如果有环，那么快指 针走过的路径就是图中a+b+c+b，慢指针走过的路径就是图中a+b，
    // 因为在相同的时 间内，快指针走过的路径是慢指针的2倍，所以这里有a+b+c+b=2* (a+b)，整理得到 a=c，也就是说图中a的路径长
    // 度和c的路径长度是一样的。 在相遇的时候再使用两个指针，一个从链表起始点开始，一个从相遇点开始，每次他们 都走一步，直到再
    // 次相遇，那么这个相遇点就是环的入口。
    public class Solution142 {
        public ListNode detectCycle(ListNode head) {
            if (head == null || head.next == null) return null;
            ListNode slow = head;
            ListNode fast = head;

            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;  //快慢指针，快指针每次走两步，慢指针每次走一步
                if (fast == slow) {  //先判断是否有环，
                    ListNode slow2 = head;
                    while (slow != slow2) {  //确定有环之后才能找环的入口
                        //两指针，一个从头结点开始，
                        //一个从相遇点开始每次走一步，直到
                        //再次相遇为止
                        slow = slow.next;
                        slow2 = slow2.next;
                    }
                    return slow;
                }
            }
            return null;
        }
    }

    // LeetCode 237: Delete Node in a Linked List
    /**
     *
     *Write a function to delete a node in a singly-linked list. You will not be given access
     * to the head of the list, instead you will be given access to the node to be deleted directly.
     *
     * It is guaranteed that the node to be deleted is not a tail node in the list.
     *
     * Input: head = [4,5,1,9], node = 5
     * Output: [4,1,9]
     * Explanation: You are given the second node with value 5, the linked list should
     * become 4 -> 1 -> 9 after calling your function
     */
    class Solution237 {
        public void deleteNode(ListNode node) {
            if (node == null) return;
            node.val = node.next.val;
            node.next = node.next.next;
        }
    }

    // LeetCode 83: Remove Duplicates form Sorted List

    /**
     * Given the head of a sorted linked list, delete all duplicates such that each element appears only once.
     * Return the linked list sorted as well.
     *
     *Input: head = [1,1,2]
     * Output: [1,2]
     *
     * Input: head = [1,1,2,3,3]
     * Output: [1,2,3]
     */
    //这题说 了链 表中的值是按照升序排列 的 ， 既然是排过序的，那么相同的节点肯定是挨着的。我们可以使用一个指针cur，
    // 每次都要判断是否和他后面的节点值相同，如果相同就 把后面的那个节点给删除

    class Solution83 {
        public ListNode deleteDuplicates(ListNode head) {
            if (head == null || head.next == null) return head;
            ListNode cur = head;
            while (cur.next != null) {
                if (cur.next.val == cur.val) { //如果当前节点的值和下一个节点的值相同，就把下一个节点值给删除
                    cur.next = cur.next.next;
                } else {  //否则cur就往后移一步
                    cur = cur.next;
                }
            }
            return head;
        }

        // 递归写法
        public ListNode deleteDuplicatesRecur(ListNode head) {
            //递归的边界条件判断
            if (head == null || head.next == null)
                return head;
            //递归，相当于从后往前遍历
            head.next = deleteDuplicatesRecur(head.next);
            //如果当前节点和下一个一样，直接返回下一个节点，否则
            //返回当前节点
            return head.val == head.next.val ? head.next : head;
        }
    }

    // LeetCode 82: Remove Duplicates From Sorted List II

    /**
     * Given the head of a sorted linked list, delete all nodes that have duplicate numbers,
     * leaving only distinct numbers from the original list. Return the linked list sorted as well.
     *
     * Input: head = [1,2,3,3,4,4,5]
     * Output: [1,2,5]
     *
     * Input: head = [1,1,1,2,3]
     * Output: [2,3]
     */
    class Solution82 {
        public ListNode deleteDuplicates(ListNode head) {
            if (head == null || head.next == null) return head;
            ListNode dummy = new ListNode(0);  // 添加一个dummy节点，让dummy.next指向head
            dummy.next = head;
            ListNode pre = dummy;
            while (pre.next != null && pre.next.next != null) {
                if (pre.next.val == pre.next.next.val){
                    int sameNum = pre.next.val;
                    while (pre.next != null && pre.next.val == sameNum) {
                        pre.next = pre.next.next;
                    }
                } else {
                    pre = pre.next;
                }
            }
            return dummy.next;
        }
    }


    public static void main(String[] args) {

    }
}
