package com.company.leetCode;

import com.company.leetCode.linkedList;

import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) {
	// write your code here
        boolean[] s =new boolean[5];
    System.out.println(s[1]);
    System.out.println("sss" + "\"");
        PriorityQueue<Integer> m = new PriorityQueue<Integer>(7);
        System.out.println(m.size());
        linkedList.ListNode ln = new linkedList.ListNode(1);
        linkedList.ListNode cur = ln;
        cur.next = new linkedList.ListNode(2);
        cur = cur.next;
        cur.next = new linkedList.ListNode(3);
        System.out.println(ln);
        System.out.println(linkedList.Solutionreverse.reverseList(ln));
    }
}
