package com.company.Notes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Note6_Random {
    // 398: Random Pick Index
    /**
     *
     *Given an integer array nums with possible duplicates, randomly output the index of a given target number.
     *  You can assume that the given target number must exist in the array.
     *
     * Implement the Solution class:
     *
     * Solution(int[] nums) Initializes the object with the array nums.
     * int pick(int target) Picks a random index i from nums where nums[i] == target. If there are multiple valid i's,
     * then each index should have an equal probability of returning.
     *
     * Input
     * ["Solution", "pick", "pick", "pick"]
     * [[[1, 2, 3, 3, 3]], [3], [1], [3]]
     * Output
     * [null, 4, 0, 2]
     *
     * Explanation
     * Solution solution = new Solution([1, 2, 3, 3, 3]);
     * solution.pick(3); // It should return either index 2, 3, or 4 randomly. Each index should have equal probability of returning.
     * solution.pick(1); // It should return 0. Since in the array only nums[0] is equal to 1.
     * solution.pick(3); // It should return either index 2, 3, or 4 randomly. Each index should have equal probability of returning.
     *
     */

    class Solution398 {
        int[] nums;
        Random random;

        public Solution398(int[] nums) {
            this.nums = nums;
            this.random = new Random();
        }

        public int pick(int target) {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == target) {
                    list.add(i);
                }
            }
            return list.get(random.nextInt(list.size()));
        }
        public int pick2(int target) {
            int res = -1;
            int count = 0;  // 有多少个重复的数字
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] != target) {
                    continue;
                }
                count ++;
                if (random.nextInt(count) == 0) {  //只取第一个
                    res = i;
                }
            }
            return res;
        }

    }

    // LeetCode 380:
    /**
     * Implement the `RandomizedSet` class:
     *
     * - `RandomizedSet()` Initializes the `RandomizedSet` object.
     * - `bool insert(int val)` Inserts an item `val` into the set if not present. Returns `true` if the item was not
     * present, `false` otherwise.
     * - `bool remove(int val)` Removes an item `val` from the set if present. Returns `true` if the item was present,
     * `false` otherwise.
     * - `int getRandom()` Returns a random element from the current set of elements (it's guaranteed that at least one
     * element exists when this method is called). Each element must have the **same probability** of being returned.
     *
     * You must implement the functions of the class such that each function works in **average** `O(1)` time complexity.
     */
    //Input
    //["RandomizedSet", "insert", "remove", "insert", "getRandom", "remove", "insert", "getRandom"]
    //[[], [1], [2], [2], [], [1], [2], []]
    //Output
    //[null, true, false, true, 2, true, false, 2]
    //
    //Explanation
    //RandomizedSet randomizedSet = new RandomizedSet();
    //randomizedSet.insert(1); // Inserts 1 to the set. Returns true as 1 was inserted successfully.
    //randomizedSet.remove(2); // Returns false as 2 does not exist in the set.
    //randomizedSet.insert(2); // Inserts 2 to the set, returns true. Set now contains [1,2].
    //randomizedSet.getRandom(); // getRandom() should return either 1 or 2 randomly.
    //randomizedSet.remove(1); // Removes 1 from the set, returns true. Set now contains [2].
    //randomizedSet.insert(2); // 2 was already in the set, so return false.
    //randomizedSet.getRandom(); // Since 2 is the only number in the set, getRandom() will always return 2.

    class RandomizedSet {

        Random rmd;
        ArrayList<Integer> list;  // 存的数的位置
        HashMap<Integer, Integer> map;  // 插入的数和对应的位置

        public RandomizedSet() {
            this.rmd = new Random();
            this.map = new HashMap<>();  // 将arrayList的index存入 map, 形成index 和数字的键值对
            this.list = new ArrayList<>();
        }

        public boolean insert(int val) {
            if (map.containsKey(val)) return false;
            map.put(val, list.size());
            list.add(val);
            return true;
        }

        public boolean remove(int val) {
            if (!map.containsKey(val)) return false;
            int index = map.remove(val);
            int lastVal = list.remove(list.size() - 1);  //删除最后一个元素
            if (index != list.size()) {
                list.set(index, lastVal);
                map.put(lastVal, index);
            }
            return true;
        }

        public int getRandom() {
            return list.get(rmd.nextInt(list.size()));
        }
    }

    // LeetCode 384: ShuffleAnArray:

    /**
     *Given an integer array nums, design an algorithm to randomly shuffle the array. All permutations of the array should be equally likely as a result of the shuffling.
     *
     * Implement the Solution class:
     *
     * Solution(int[] nums) Initializes the object with the integer array nums.
     * int[] reset() Resets the array to its original configuration and returns it.
     * int[] shuffle() Returns a random shuffling of the array.
     *
     *
     * Input
     * ["Solution", "shuffle", "reset", "shuffle"]
     * [[[1, 2, 3]], [], [], []]
     * Output
     * [null, [3, 1, 2], [1, 2, 3], [1, 3, 2]]
     *
     * Explanation
     * Solution solution = new Solution([1, 2, 3]);
     * solution.shuffle();    // Shuffle the array [1,2,3] and return its result.
     *                        // Any permutation of [1,2,3] must be equally likely to be returned.
     *                        // Example: return [3, 1, 2]
     * solution.reset();      // Resets the array back to its original configuration [1,2,3]. Return [1, 2, 3]
     * solution.shuffle();    // Returns the random shuffling of array [1,2,3]. Example: return [1, 3, 2]
     */

    class Solution384 {
        private Random rmd;
        private int[] nums;
        public Solution384(int[] nums) {
            this.nums = nums;
            this.rmd = new Random();
        }

        public int[] reset() {
            return nums;
        }

        public int[] shuffle() {
            if (nums == null) return null;
            int[] clone = nums.clone();
            // 等概率打乱顺序
            for (int i = 0; i < clone.length; i++) {
                int random = rmd.nextInt(i + 1);  // 蓄水池的大小为1
                swap(clone, i , random);
            }
            return clone;
        }

        private void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }

    // LeetCode 382: Linked List Random Node
    /**
     *
     * Given a singly linked list, return a random node's value from the linked list. Each node must have the same probability of being chosen.
     *
     * Implement the Solution class:
     *
     * Solution(ListNode head) Initializes the object with the head of the singly-linked list head.
     * int getRandom() Chooses a node randomly from the list and returns its value. All the nodes of the list should be equally likely to be chosen.
     *
     * Input
     * ["Solution", "getRandom", "getRandom", "getRandom", "getRandom", "getRandom"]
     * [[[1, 2, 3]], [], [], [], [], []]
     * Output
     * [null, 1, 3, 2, 2, 3]
     *
     * Explanation
     * Solution solution = new Solution([1, 2, 3]);
     * solution.getRandom(); // return 1
     * solution.getRandom(); // return 3
     * solution.getRandom(); // return 2
     * solution.getRandom(); // return 2
     * solution.getRandom(); // return 3
     * // getRandom() should return either 1, 2, or 3 randomly. Each element should have equal probability of returning.
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
    class Solution382 {

        private ListNode head;
        private Random rmd;

        public Solution382(ListNode head) {
            this.rmd = new Random();
            this.head = head;
        }

        public int getRandom() {
            ListNode temp = head;
            int res = temp.val;
            int i = 1;
            while (temp.next != null) {
                temp = temp.next;
                if (rmd.nextInt(++i) == 0) {  // 随机选择，第i个数字选择的概率是1/i.
                    res = temp.val;
                }
            }
            return res;
        }
    }


    // LeetCode 138: copy list with random pointer
    /**
     *
     * A linked list of length n is given such that each node contains an additional random pointer, which could point
     * to any node in the list, or null.
     *
     * Construct a deep copy of the list. The deep copy should consist of exactly n brand new nodes, where each new node
     * has its value set to the value of its corresponding original node. Both the next and random pointer of the new nodes
     * should point to new nodes in the copied list such that the pointers in the original list and copied list represent
     * the same list state. None of the pointers in the new list should point to nodes in the original list.
     *
     * For example, if there are two nodes X and Y in the original list, where X.random --> Y, then for the corresponding
     * two nodes x and y in the copied list, x.random --> y.
     *
     * Return the head of the copied linked list.
     *
     * The linked list is represented in the input/output as a list of n nodes. Each node is represented as a
     * pair of [val, random_index] where:
     *
     * val: an integer representing Node.val
     * random_index: the index of the node (range from 0 to n-1) that the random pointer points to, or null if it
     * does not point to any node.
     * Your code will only be given the head of the original linked list.
     */
    /*
    Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
    Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]
     */

    class Node{
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    class Solution138{
        public Node copyRandomList(Node head) {
            HashMap<Node, Node> map = new HashMap<>();
            Node cur = head;
            while (cur != null) {
                map.put(cur, new Node(cur.val));
                cur = cur.next;
            }
            cur = head;
            while (cur != null) {
                map.get(cur).next = map.get(cur.next);  // 复制后的节点连起来
                map.get(cur).random = map.get(cur.random);
                cur = cur.next;
            }
            return map.get(head);
        }

        // 1->1'->2->2'->3->3'
        // 1'->2'->3'
        public Node copyRandomList2(Node head) {
            Node cur = head;
            Node next;
            // 将新的节点复制在老节点之后,实现next的copy
            while (cur != null) {
                next = cur.next;
                Node copy = new Node(cur.val);
                cur.next = copy;
                copy.next = next;
                cur = next;
            }

            cur = head;
            while (cur != null) {  // 实现random的copy
                if (cur.random != null) {
                    cur.next.random = cur.random.next;
                }
                cur = cur.next.next;
            }
            // 拆分链表
            /*
            1->1'->2->2'->3->3'
            dummy: null
            cur: 1
            next : 2
            copy: 1'
             */
            cur = head;
            Node dummy = new Node(0);
            Node copy, copyCur= dummy;
            while (cur != null) {
                next = cur.next.next;
                copy = cur.next;
                copyCur.next = copy;
                cur.next = next;
                cur = next;
            }
            return dummy.next;
        }

    }
}
