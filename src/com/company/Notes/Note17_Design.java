package com.company.Notes;

import java.util.HashMap;
import java.util.Random;

public class Note17_Design {

    // 519 Random flip Matrix
    /**
     * There is an m x n binary grid matrix with all the values set 0 initially. Design an algorithm to randomly pick
     * an index (i, j) where matrix[i][j] == 0 and flips it to 1. All the indices (i, j) where matrix[i][j] == 0 should
     * be equally likely to be returned.
     *
     * Optimize your algorithm to minimize the number of calls made to the built-in random function of your language
     * and optimize the time and space complexity.
     *
     * Implement the Solution class:
     *
     * Solution(int m, int n) Initializes the object with the size of the binary matrix m and n.
     * int[] flip() Returns a random index [i, j] of the matrix where matrix[i][j] == 0 and flips it to 1.
     * void reset() Resets all the values of the matrix to be 0.
     *
     *
     * Example 1:
     *
     * Input
     * ["Solution", "flip", "flip", "flip", "reset", "flip"]
     * [[3, 1], [], [], [], [], []]
     * Output
     * [null, [1, 0], [2, 0], [0, 0], null, [2, 0]]
     *
     * Explanation
     * Solution solution = new Solution(3, 1);
     * solution.flip();  // return [1, 0], [0,0], [1,0], and [2,0] should be equally likely to be returned.
     * solution.flip();  // return [2, 0], Since [1,0] was returned, [2,0] and [0,0]
     * solution.flip();  // return [0, 0], Based on the previously returned indices, only [0,0] can be returned.
     * solution.reset(); // All the values are reset to 0 and can be returned.
     * solution.flip();  // return [2, 0], [0,0], [1,0], and [2,0] should be equally likely to be returned.
     */

    class Solution {

        HashMap<Integer, Integer> map;
        int m, n;
        int total;
        Random random;

        public Solution(int m, int n) {
            map = new HashMap<>();
            random = new Random();
            this.m = m;
            this.n = n;
            reset();
        }

        public int[] flip() {
            int rand = random.nextInt(total--);
            int res = map.getOrDefault(rand, rand);
            map.put(rand, map.getOrDefault(total, total));
            map.put(total, res);
            return new int[]{res / n, res % n};
        }

        public void reset() {
            total = m * n;
        }
    }

    // 146: LRU Cache  非常重要
    /**
     * Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
     *
     * Implement the LRUCache class:
     *
     * LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
     * int get(int key) Return the value of the key if the key exists, otherwise return -1.
     * void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the key-value pair
     * to the cache. If the number of keys exceeds the capacity from this operation, evict the least recently used key.
     * The functions get and put must each run in O(1) average time complexity.
     *
     * Example 1:
     *
     * Input
     * ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
     * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
     * Output
     * [null, null, null, 1, null, -1, null, -1, 3, 4]
     *
     * Explanation
     * LRUCache lRUCache = new LRUCache(2);
     * lRUCache.put(1, 1); // cache is {1=1}
     * lRUCache.put(2, 2); // cache is {1=1, 2=2}
     * lRUCache.get(1);    // return 1
     * lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
     * lRUCache.get(2);    // returns -1 (not found)
     * lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
     * lRUCache.get(1);    // return -1 (not found)
     * lRUCache.get(3);    // return 3
     * lRUCache.get(4);    // return 4
     */

    /*
    HashMap + Double Linked List
     */
    class LRUCache {

        class Node{
            int key;
            int value;
            Node next;
            Node pre;
            public Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }

        private HashMap<Integer, Node> map;
        private int capacity;
        private Node head;
        private Node tail;

        public LRUCache(int capacity) {
            map = new HashMap<>();
            this.capacity = capacity;
            head = null;
            tail = null;
        }

        /**
         * 取出：1. 存在，调换顺序
         *      2. 不存在，返回-1
         *
         * @param key
         * @return
         */

        public int get(int key) {
            Node node = map.get(key);
            if (node == null) return -1;
                if (node != tail) {
                    if (node == head) head = head.next;
                    else{
                        node.pre.next = node.next;  // 删除node，右指针更新
                        node.next.pre = node.pre;  // 左指针更新
                    }
                    tail.next = node;
                    node.pre = tail;
                    node.next = null;
                    tail = node;
                }
            return node.value;
        }

        /**
         * 插入： 1. 存在，更新顺序
         *       2. 不存在
         *
         *
         * @param key
         * @param value
         */

        public void put(int key, int value) {
            Node node = map.get(key);  // 先看存在与否
            if (node != null) {
                node.value = value;
                if (node != tail) {
                    if (node == head) head = head.next;
                    else{
                        node.pre.next = node.next;  // 删除node，右指针更新
                        node.next.pre = node.pre;  // 左指针更新
                    }
                    tail.next = node;
                    node.pre = tail;
                    node.next = null;
                    tail = node;
                }
            } else {
                // 如果为空，新建一个node，插入
                Node newNode = new Node(key, value);
                if (capacity == 0) {  // 如果容量为0了，移除一个元素再加
                    Node temp = head;
                    head = head.next;
                    if(temp == tail) tail = null;
                    map.remove(temp.key);  // 删除一个
                    capacity++;
                }
                if (head == null && tail == null) head = newNode;
                else {
                    tail.next = newNode;
                    newNode.pre = tail;  // 双向链表左指针指向tail
                    newNode.next = null;
                }
                tail = newNode;
                map.put(key, newNode);
                capacity--;
            }
        }
    }

}
