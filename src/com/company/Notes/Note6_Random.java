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
        ArrayList<Integer> list;
        HashMap<Integer, Integer> map;

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


}
