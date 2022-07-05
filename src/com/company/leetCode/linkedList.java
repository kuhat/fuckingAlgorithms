package com.company.leetCode;

import org.junit.Test;

import java.util.*;

public class linkedList {
    /**
     * 复制带随机指针的链表
     *给你一个长度为 n 的链表，每个节点包含一个额外增加的随机指针 random ，该指针可以指向链表中的任何节点或空节点。
     *
     * 构造这个链表的深拷贝。深拷贝应该正好由 n 个 全新 节点组成，其中每个新节点的值都设为其对应的原节点的值。新节点的 next 指针和 random 指针也都应指向复制链表中的新节点，
     *并使原链表和复制链表中的这些指针能够表示相同的链表状态。复制链表中的指针都不应指向原链表中的节点 。
     *
     * 例如，如果原链表中有 X 和 Y 两个节点，其中 X.random --> Y 。那么在复制链表中对应的两个节点 x 和 y ，同样有 x.random --> y 。
     *
     * 返回复制链表的头节点。
     *
     * 用一个由n个节点组成的链表来表示输入/输出中的链表。每个节点用一个[val, random_index]表示：
     *
     * val：一个表示 Node.val 的整数。
     * random_index：随机指针指向的节点索引（范围从 0 到 n-1）；如果不指向任何节点，则为  null 。
     * 你的代码 只 接受原链表的头节点 head 作为传入参数。
     *
     * 作者：力扣 (LeetCode)
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions/xam1wr/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     *
     */


// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}


    class Solution {
        public Node copyRandomList(Node head) {
            if (head == null) return null;
            Node p = head;

            // First step: create a new node at the back of each original node
            // 1 -> 1' -> 2 -> 2' -> 3 -> 3'
            while (p != null) {
                Node newNode = new Node(p.val);
                newNode.next = p.next;
                p.next = newNode;
                p = newNode.next;
            }

            p = head;
            // Second step: set the new random node
            while (p != null) {
                if (p.random != null) {
                    p.next.random = p.random.next;
                }
                p = p.next.next;
            }
            Node dummy = new Node(-1);
            p = head;
            Node cur = dummy;

            // Step3: detach the two linkedList
            while (p != null) {
                cur.next = p.next;
                cur = cur.next;
                p.next = cur.next;
                p = p.next;
            }
            return dummy.next;
        }
    }

    /**
     *环形链表
     *
     * 给你一个链表的头节点 head ，判断链表中是否有环。
     *
     * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，评测系统内部使用整数 pos
     * 来表示链表尾连接到链表中的位置（索引从 0 开始）。注意：pos 不作为参数进行传递 。仅仅是为了标识链表的实际情况。
     *
     * 如果链表中存在环，则返回 true 。 否则，返回 false 。
     */

    // Definition for singly-linked list.
    static class ListNode {
          int val;
          ListNode next;
          ListNode(int x) {
              val = x;
              next = null;
          }
      }

      // 1. fast and slow two pointers
    public class SolutionListNode {
        public boolean hasCycle(ListNode head) {
            if (head == null || head.next == null) {
                return false;
            }
            ListNode slow = head;
            ListNode fast = head.next;
            while (slow != fast) {
                if (fast == null || fast.next == null) {
                    return false;
                }
                slow = slow.next;
                fast = fast.next.next;
            }
            return true;
        }

        // 2. Hashset to check duplication
        public boolean hasCycle1(ListNode head) {
            Set<ListNode> seen = new HashSet<ListNode>();
            while (head != null) {
                if (!seen.add(head)) {
                    return true;
                }
                head = head.next;
            }
            return false;
        }
    }

    /**
     * 排序链表
     *
     *给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
     *
     * 输入：head = [4,2,1,3]
     * 输出：[1,2,3,4]
     *
     * 输入：head = [-1,5,3,4,0]
     * 输出：[-1,0,3,4,5]
     */

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode() {}
     *     ListNode(int val) { this.val = val; }
     *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */
    class Solution3 {
        public ListNode sortList(ListNode head) {
            if (head == null || head.next == null) return head;

            // 利用快慢链表来切割链表为均等的两半
            ListNode fast = head.next;
            ListNode slow = head;
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }
            ListNode tmp = slow.next;
            slow.next = null;  // 切断链表
            ListNode left = sortList(head);
            ListNode right = sortList(tmp);
            ListNode h = new ListNode(0);
            ListNode rst = h;
            while (left != null && right != null) {
                if (left.val < right.val) {
                    h.next = left;
                    left = left.next;
                } else {
                    h.next = right;
                    right = right.next;
                }
                h = h.next;
            }
            h.next = left != null ? left : right;
            return rst.next;
        }

        // 利用priority queue 来进行排序
        public ListNode sortList1(ListNode head) {
            if (head == null || head.next == null) return head;
            PriorityQueue<Integer> pq = new PriorityQueue<Integer>();  // 使用小根堆来排序

            // 元素加入pq
            ListNode cur = head;
            while (cur != null) {
                pq.offer(cur.val);
                cur = cur.next;
            }

            // 创建新的链表返回排序后的listNode
            ListNode q = new ListNode(0);
            ListNode rst = q;
            while (pq.size() > 0) {
                q.val = pq.poll();
                if (pq.size() > 0) {
                    ListNode tmp = new ListNode(0);
                    q.next = tmp;
                }
                q = q.next;
            }
            return rst;
        }
    }

    /**
     * 相交链表
     * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。
     *
     * 图示两个链表在节点 c1 开始相交：
     *
     *
     *
     */

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode(int x) {
     *         val = x;
     *         next = null;
     *     }
     * }
     */
    public class SolutionIntersection {
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            if (headA == null || headB == null) return null;
            ListNode pA = headA, pB = headB;
            while (pA != pB) {
                pA = pA == null ? headB : pA.next;
                pB = pB == null ? headA : pB.next;
            }
            return pA;
        }

        //核心思路：就是想办法从链表尾部开始循环，一开始想的是先反转两个链表但是好像不符合提议不能改变链表！
        //所以想到了利用下标加结点存入map中
        //利用map模拟反转链表开始扫描，找到第一个不同的结点
        //则相交结点就是后一个结点！
        public ListNode SOl(ListNode headA, ListNode headB) {
            int sizeA = 0;
            HashMap<Integer,ListNode> mapA = new HashMap<>();
            ListNode temp = headA;
            while (temp != null){
                mapA.put(sizeA++,temp);
                temp = temp.next;
            }
            int sizeB = 0;
            HashMap<Integer,ListNode> mapB = new HashMap<>();
            temp = headB;
            while (temp != null){
                mapB.put(sizeB++,temp);
                temp = temp.next;
            }
            for (int i = sizeA - 1,j = sizeB - 1;i >= 0 && j >= 0;i--,j--){
                if(mapA.get(i) != mapB.get(j)){
                    return i == sizeA - 1 ? null : mapA.get(i + 1);
                }
            }
            return sizeA > sizeB ? headB : headA;
        }
    }

    /**
     *
     * 反转链表
     * 给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
     *
     */

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode() {}
     *     ListNode(int val) { this.val = val; }
     *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */
    static class Solutionreverse {
        public static ListNode reverseList(ListNode head) {
            //申请节点，pre和 cur，pre指向null
            ListNode pre = null;
            ListNode cur = head;
            ListNode tmp = null;
            while(cur!=null) {
                //记录当前节点的下一个节点
                tmp = cur.next;
                //然后将当前节点指向pre
                cur.next = pre;
                //pre和cur节点都前进一位
                pre = cur;
                cur = tmp;
            }
            return pre;
        }

        public ListNode reverseListRecur(ListNode head) {
            //递归终止条件是当前为空，或者下一个节点为空
            if(head==null || head.next==null) {
                return head;
            }
            //这里的cur就是最后一个节点
            ListNode cur = reverseListRecur(head.next);
            //如果链表是 1->2->3->4->5，那么此时的cur就是5
            //而head是4，head的下一个是5，下下一个是空
            //所以head.next.next 就是5->4
            head.next.next = head;
            //防止链表循环，需要将head.next设置为空
            head.next = null;
            //每层递归函数都返回cur，也就是最后一个节点
            return cur;
        }
    }

    /**
     *
     * 回文链表
     * 给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。
     *
     * 输入：head = [1,2,2,1]
     * 输出：true
     */

    class SolutionPalindrome {
        public boolean isPalindrome(ListNode head) {
           ListNode pre = null;
           ListNode cur = head;
           ListNode tmp = null;
           // 先反转链表
           while (cur != null){
               tmp = cur.next;
               cur.next = pre;
               pre = cur;
               cur = tmp;
           }
           ListNode cur1 = head;
           while (cur1 != null) {
               if (cur1.val != pre.val) {
                   return false;
               }
               cur1 = cur1.next;
               pre = pre.next;
           }
            return true;
        }
    }





}
