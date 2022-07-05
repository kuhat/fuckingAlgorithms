package com.company.leetCode;

import com.company.leetCode.NestedInteger;
import org.junit.Test;

import java.util.*;

public class stack {

    /**
     * 1. 最小栈
     * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
     * <p>
     * 实现 MinStack 类:
     * <p>
     * MinStack() 初始化堆栈对象。
     * void push(int val) 将元素val推入堆栈。
     * void pop() 删除堆栈顶部的元素。
     * int top() 获取堆栈顶部的元素。
     * int getMin() 获取堆栈中的最小元素。
     * <p>
     * 输入：
     * ["MinStack","push","push","push","getMin","pop","top","getMin"]
     * [[],[-2],[0],[-3],[],[],[],[]]
     * <p>
     * 输出：
     * [null,null,null,null,-3,null,0,-2]
     * <p>
     * 解释：
     * MinStack minStack = new MinStack();
     * minStack.push(-2);
     * minStack.push(0);
     * minStack.push(-3);
     * minStack.getMin();   --> 返回 -3.
     * minStack.pop();
     * minStack.top();      --> 返回 0.
     * minStack.getMin();   --> 返回 -2.
     */
// Solution 1:
    class MinStack {
        private int min;
        private LinkedList<Integer> stack;

        public MinStack() {
            this.min = Integer.MAX_VALUE;
            this.stack = new LinkedList<>();
        }
        //如果添加的值val小于之前记录的最小值min,就需要把min入栈，将最小值min的值更新为val
        //需要把min入栈的原因是，如果当前val出栈后，意味着最小值就出栈了，那么再出栈的元素就是新的最小值。

        /* 栈底---------->栈顶 (-2,0，-3依次入栈)
         * -2入栈，由于min的初始值为Integer.MAX_VALUE，栈的情况：MAX_VALUE, -2; min = -2
         * 0入栈， 0 > min 栈的情况：MAX_VALUE, -2, 0; min = -2
         * -3入栈，-3 < -2, 就得先将min入栈，再将-3入栈，栈的情况：MAX_VALUE, -2, 0，-2，-3；min = -3
         *
         * 此时-3出栈，-3 = min，-3出栈后就说明当前最小值-3已经无了，就得再将-2弹出，并更新最小值为-2,栈的情况：MAX_VALUE, -2, 0；min = -2 */
        public void push(int val) {
            if (val < min) {
                this.stack.push(min);
                min = val;
            }
            this.stack.push(val);
        }

        public void pop() {
            //如果出栈元素等于记录的最小值，就说明最小值已经出栈了，那么紧接着这个出栈元素的下一个元素就是当前栈中最小值。
            if (this.stack.pop() == min) {
                min = stack.pop();
            }
        }

        public int top() {
            return this.stack.peek();
        }

        public int getMin() {
            return min;
        }
        /**
         * Your MinStack object will be instantiated and called as such:
         * MinStack obj = new MinStack();
         * obj.push(val);
         * obj.pop();
         * int param_3 = obj.top();
         * int param_4 = obj.getMin();
         */
    }

    @Test
    public void test1() {
        MinStack o = new MinStack();
        o.push(1);
        o.push(-1);
        System.out.println(o.min);
    }

    // Solution 2: auxiliary stack
    class minStack2 {

        Deque<Integer> stack;
        Deque<Integer> minStack;  // help stack to record the current minimum value of the corresponding value

        public minStack2() {
            stack = new LinkedList<Integer>();
            minStack = new LinkedList<Integer>();
            minStack.push(Integer.MAX_VALUE);
        }

        public void push(int x) {
            stack.push(x);
            minStack.push(Math.min(minStack.peek(), x));
        }

        public void pop() {
            stack.pop();
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }

    }

    /**
     * 2. 数组中的第K个最大元素
     * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
     * <p>
     * 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
     * <p>
     * 输入: [3,2,1,5,6,4] 和 k = 2
     * 输出: 5
     * <p>
     * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
     * 输出: 4
     */

    // Solution1: Brutal force, Use method in API
    public int findKthLargest1(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    // Solution2: Minimum heap

    /***
     * 优先队列的思路是很朴素的。由于找第 K 大元素，其实就是整个数组排序以后后半部分最小的那个元素。因此，我们可以维护一个有 K 个元素的最小堆：
     *
     * 如果当前堆不满，直接添加；
     * 堆满的时候，如果新读到的数小于等于堆顶，肯定不是我们要找的元素，只有新遍历到的数大于堆顶的时候，才将堆顶拿出，然后放入新读到的数，进而让堆自己去调整内部结构。
     *
     * @param nums input array
     * @param k the Kth biggest number
     * @return
     */
    public int findKthLargest2(int[] nums, int k) {
        int len = nums.length;
        // 使用一个含有 k 个元素的最小堆，PriorityQueue 底层是动态数组，为了防止数组扩容产生消耗，可以先指定数组的长度
        PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>(k, Comparator.comparing(a -> a));
        // Java 里没有 heapify ，因此我们逐个将前 k 个元素添加到 minHeap 里
        for (int i = 0; i < k; i++) {
            minHeap.offer(nums[i]);
        }

        for (int i = k; i < len; i++) {
            // 看一眼，不拿出，因为有可能没有必要替换
            int topElement = minHeap.peek();
            if (nums[i] > topElement) {
                // Java 没有 replace()，所以得先 poll() 出来，然后再放回去
                minHeap.poll();
                minHeap.offer(nums[i]);
            }
        }
        return minHeap.peek();
    }

    /**
     * 数据流的中位数
     * 中位数是有序列表中间的数。如果列表长度是偶数，中位数则是中间两个数的平均值。
     * <p>
     * 例如，
     * <p>
     * [2,3,4] 的中位数是 3
     * <p>
     * [2,3] 的中位数是 (2 + 3) / 2 = 2.5
     * <p>
     * 设计一个支持以下两种操作的数据结构：
     * <p>
     * void addNum(int num) - 从数据流中添加一个整数到数据结构中。
     * double findMedian() - 返回目前所有元素的中位数。
     * <p>
     * addNum(1)
     * addNum(2)
     * findMedian() -> 1.5
     * addNum(3)
     * findMedian() -> 2
     */

//题目要求获取一个数据流排序后的中位数，那么可以使用两个优先队列（堆）实现。
//该题使用一个大顶堆，一个小顶堆完成。
//
//大顶堆的每个节点的值大于等于左右孩子节点的值，堆顶为最大值。
//小顶堆的每个节点的值小于等于左右孩子节点的值，堆顶为最小值。
//因此我们使用 大顶堆(maxHeap) 来存储数据流中较小一半的值，用 小顶堆(minHeap) 来存储数据流中较大一半的值。即“大顶堆的堆顶”与“小顶堆的堆顶”就是排序数据流的两个中位数。
//如图所示，大顶堆(maxHeap)置于下方，小顶堆(minHeap)倒置于上方，两个堆组合起来像一个沙漏的形状。
//根据堆的性质，可以判断两个堆的值从下往上递增，即：
//maxHeap堆底 <= maxHeap堆顶 <= minHeap堆顶 <= minHeap堆底。
//题目要求获取数据流排序后的中位数，而根据数据流的奇偶性以及堆的性质，将获取中位数的情况分为两类：
//
//数据流为奇数时，保证两个堆的长度相差1，那么长度较大的堆的堆顶就是中位数；
//数据流为偶数时，保证两个堆的长度相等，两个堆的堆顶相加除二就是中位数。
//那么我们要保证每次插入元素后，两堆维持相对长度。让minHeap为长度较大的堆，每次插入元素时进行判断：
//
//当两堆总长度为偶数时，即两堆长度相等，将新元素插入到minHeap，插入后minHeap比maxHeap长度大1；
//当两堆总长度为奇数时，即两堆长度不等，将新元素插入到maxHeap，插入后两堆长度相等；
//还要保证插入元素后，两堆仍是保证从下往上递增的顺序性。如上面的偶数情况，将新元素x直接插入到minHeap，是有可能破坏两堆的顺序性的，例如：（minHeap是存储“较大一半”的值）
//
//若x的值恰好为“较大一半”，直接将插入到“较大一半”的minHeap中，是不会破坏顺序的；
//若x的值为“较小一半”，而此时却插入到“较大一半”的minHeap中，是会破坏顺序的。
//那么，每次新元素插入时，都需要先插入到另一个堆，进行重新排序后，再将最值拿出来插入正确的堆中。因此，最终得出的结论为：
//
//当两堆总大小为偶数时，即两堆大小相等，先将新元素插入maxHeap，重新排序后将新的最值拿出并插入到minHeap；
//当两堆总大小为奇数时，即两堆大小不等，先将新元素插入minHeap，重新排序后将新的最值拿出并插入到maxHeap；

    class MedianFinder {

        PriorityQueue<Integer> max_Heap;
        PriorityQueue<Integer> min_Heap;

        int max_Heap_size = 0;
        int min_Heap_size = 0;

        /**
         * initialize the data structure
         **/
        public MedianFinder() {
            max_Heap_size = 0;
            min_Heap_size = 0;

            // 大顶堆
            max_Heap = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2.intValue() - o1.intValue();
                }
            });

            min_Heap = new PriorityQueue<>();
        }

        public void addNum(int num) {
            if (max_Heap_size == 0) {
                max_Heap.add(num);
                max_Heap_size++;
            } else {
                if (max_Heap_size == min_Heap_size) {
                    if (num >= min_Heap.peek()) {
                        // 取堆顶元素
                        int peek = min_Heap.peek();
                        min_Heap.poll();
                        min_Heap.add(num);
                        max_Heap.add(peek);
                    } else {
                        max_Heap.add(num);
                    }
                    max_Heap_size++;
                } else {
                    if (num >= max_Heap.peek()) {
                        min_Heap.add(num);
                    } else {
                        int peek = max_Heap.peek();
                        max_Heap.poll();
                        max_Heap.add(num);
                        min_Heap.add(peek);
                    }
                    min_Heap_size++;
                }
            }
        }

        public double findMedian() {
            if (max_Heap_size == 0) {
                return -1;
            }
            if (max_Heap_size != min_Heap_size) {
                return max_Heap.peek();
            } else {
                return (double) (max_Heap.peek() + min_Heap.peek()) / 2;
            }
        }
        /**
         * Your MedianFinder object will be instantiated and called as such:
         * MedianFinder obj = new MedianFinder();
         * obj.addNum(num);
         * double param_2 = obj.findMedian();
         */
    }

    /**
     * 有序矩阵中第K小的元素
     * 给你一个 n x n 矩阵 matrix ，其中每行和每列元素均按升序排序，找到矩阵中第 k 小的元素。
     * 请注意，它是 排序后 的第 k 小元素，而不是第 k 个 不同 的元素。
     * <p>
     * 你必须找到一个内存复杂度优于 O(n2) 的解决方案。
     * <p>
     * 输入：matrix = [[1,5,9],[10,11,13],[12,13,15]], k = 8
     * 输出：13
     * 解释：矩阵中的元素为 [1,5,9,10,11,12,13,13,15]，第 8 小元素是 13
     */

    //最直接的做法是将这个二维数组转成一维数组，并对该一维数组进行排序。最后这个一维数组中的第 kk 个数即为答案。
    public int kthSmallest1(int[][] matrix, int k) {
        int col = matrix[0].length;
        int row = matrix.length;
        if (k > col * row) {
            return -1;
        }
        int[] array = new int[col * row];
        int p = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                array[p] = matrix[i][j];
                p++;
            }
        }
        Arrays.sort(array);
        return array[k - 1];
    }

    //1. 找出二维矩阵中最小的数 leftleft，最大的数 rightright，那么第 kk 小的数必定在 leftleft ~ rightright 之间
    //2. mid=(left+right) / 2mid=(left+right)/2；在二维矩阵中寻找小于等于 midmid 的元素个数 countcount
    //3. 若这个 countcount 小于 kk，表明第 kk 小的数在右半部分且不包含 midmid，即 left=mid+1left=mid+1, right=rightright=right，又保证了第 kk 小的数在 leftleft ~ rightright 之间
    //4. 若这个 countcount 大于 kk，表明第 kk 小的数在左半部分且可能包含 midmid，即 left=leftleft=left, right=midright=mid，又保证了第 kk 小的数在 left~rightleft right 之间
    //5. 因为每次循环中都保证了第 kk 小的数在 leftleft ~ rightright 之间，当 left==rightleft==right 时，第 kk 小的数即被找出，等于 rightright
    public int kthSmallest2(int[][] matrix, int k) {
        int row = matrix.length;
        int col = matrix[0].length;
        int left = matrix[0][0];
        int right = matrix[row - 1][col - 1];
        while (left < right) {
            // 每次循环都保证第K小的数在start~end之间，当start==end，第k小的数就是start
            int mid = (left + right) / 2;
            // 找二维矩阵中<=mid的元素总个数
            int count = findNotBiggerThanMid(matrix, mid, row, col);
            if (count < k) {
                // 第k小的数在右半部分，且不包含mid
                left = mid + 1;
            } else {
                // 第k小的数在左半部分，可能包含mid
                right = mid;
            }
        }
        return right;
    }

    private int findNotBiggerThanMid(int[][] matrix, int mid, int row, int col) {
        // 以列为单位找，找到每一列最后一个<=mid的数即知道每一列有多少个数<=mid
        int i = row - 1;
        int j = 0;
        int count = 0;
        while (i >= 0 && j < col) {
            if (matrix[i][j] <= mid) {
                // 第j列有i+1个元素<=mid
                count += i + 1;
                j++;
            } else {
                // 第j列目前的数大于mid，需要继续在当前列往上找
                i--;
            }
        }
        return count;
    }

    /**
     * 前 K 个高频元素
     * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
     * <p>
     * 输入: nums = [1,1,1,2,2,3], k = 2
     * 输出: [1,2]
     */

    //首先遍历整个数组，并使用哈希表记录每个数字出现的次数，并形成一个「出现次数数组」。找出原数组的前 kk 个高频元素，就相当于找出「出现次数数组」的前 kk 大的值。
    //
    //最简单的做法是给「出现次数数组」排序。但由于可能有 O(N)O(N) 个不同的出现次数（其中 NN 为原数组长度），故总的算法复杂度会达到 O(N\log N)O(NlogN)，不满足题目的要求。
    //
    //在这里，我们可以利用堆的思想：建立一个小顶堆，然后遍历「出现次数数组」：
    //
    //如果堆的元素个数小于 kk，就可以直接插入堆中。
    //如果堆的元素个数等于 kk，则检查堆顶与当前出现次数的大小。如果堆顶更大，说明至少有 kk 个数字的出现次数比当前值大，故舍弃当前值；否则，就弹出堆顶，并将当前值插入堆中。
    //遍历完成后，堆中的元素就代表了「出现次数数组」中前 kk 大的值。
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> count = new HashMap<Integer, Integer>();
        int[] res = new int[k];

        // compute the count for each entity in nums[]
        for (int num :
                nums) {
            count.put(num, count.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<int[]> queue = new PriorityQueue<int[]>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });
        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            int num = entry.getKey(), occurrence = entry.getValue();
            if (queue.size() == k) {
                if (queue.peek()[1] < occurrence) {
                    queue.poll();
                    queue.offer(new int[]{num, occurrence});
                }
            } else {
                queue.offer(new int[]{num, occurrence});
            }

            for (int i = 0; i < k; i++) {
                res[i] = queue.poll()[0];
            }
        }
        return res;
    }

    /**
     * 滑动窗口最大值
     * 给你一个整数数组 nums，有一个大小为k的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k个数字。滑动窗口每次只向右移动一位。
     * <p>
     * 返回 滑动窗口中的最大值 。
     * <p>
     * 输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
     * 输出：[3,3,5,5,6,7]
     * 解释：
     * 滑动窗口的位置                最大值
     * ---------------               -----
     * [1  3  -1] -3  5  3  6  7       3
     * 1 [3  -1  -3] 5  3  6  7       3
     * 1  3 [-1  -3  5] 3  6  7       5
     * 1  3  -1 [-3  5  3] 6  7       5
     * 1  3  -1  -3 [5  3  6] 7       6
     * 1  3  -1  -3  5 [3  6  7]      7
     */

    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length < 2) return nums;
        // 双向队列 保存当前窗口最大值的数组位置 保证队列中数组位置的数值按从大到小排序
        LinkedList<Integer> queue = new LinkedList();
        // 结果数组
        int[] result = new int[nums.length - k + 1];
        // 遍历nums数组
        for (int i = 0; i < nums.length; i++) {
            // 保证从大到小 如果前面数小则需要依次弹出，直至满足要求
            while (!queue.isEmpty() && nums[queue.peekLast()] <= nums[i]) {
                queue.pollLast();
            }
            // 添加当前值对应的数组下标
            queue.addLast(i);
            // 判断当前队列中队首的值是否有效
            if (queue.peek() <= i - k) {
                queue.poll();
            }
            // 当窗口长度为k时 保存当前窗口中最大值
            if (i + 1 >= k) {
                result[i + 1 - k] = nums[queue.peek()];
            }
        }
        return result;
    }

    /**
     * 基本计算器
     * 给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值。
     * <p>
     * 整数除法仅保留整数部分。
     * <p>
     * 输入：s = "3+2*2"
     * 输出：7
     * <p>
     * 输入：s = " 3/2 "
     * 输出：1
     * <p>
     * s 由整数和算符 ('+', '-', '*', '/') 组成，中间由一些空格隔开
     */

    //1. 由于运算符有优先级，所以设计一个哈希表来存储'+'，'-'，'*'，'/' 优先级，我们将'+'和'-'设为1级优先级，将'*'和'/'设为2级优先级。
    //2. 考虑到表达式s的第一个数字可能为负数，因此我们给s开头添加一个字符0。
    //
    // 对于表达式求值这类问题，我们维护两个栈，一个num栈用来记录数字，一个op栈用来记录操作符，遍历表达式，遇到操作符时进行数的相应计算。
    // 首先我们定义一个eval()函数，用于从数字栈num中弹出两个数字a和b，再从操作符栈op中弹出操作符号，进行计算后将结果数字加入到数字栈num中。
    //1. 当遇到空格 ' '时，跳过。
    //
    //2、当遇到数字时，则读取一个连续的完整数字，再将其加入到数字栈num中。
    //
    //3、当遇到'+'，'-'，'*'，'/' 运算符时，需要考虑优先级：
    //
    //  如果操作符栈op的栈顶元素的优先级比当前遇到的操作符优先级高或者相等，则while循环进行eval()操作，即将数字栈num栈顶的两个元素取出来，然后和操作符栈op的栈顶操作符进行相应计算，并将计算结果压回数字栈num中。(这是本题的关键所在，这样的操作保证了操作符栈中的操作符自栈底到栈顶优先级递增。而由于栈的先进后出的特点，后进来的优先级高的操作符会先被用于计算)
    //  否则，将当前运算符压入操作符栈op中。
    //4、扫描完整个表达式后，如果操作符栈op中还存在元素，则while循环进行eval()操作。
    //
    //5、最终返回数字栈num的栈顶元素值。

    static class Solution {
        static Stack<Integer> num = new Stack<Integer>();
        static Stack<Character> op = new Stack<Character>();
        static HashMap<Character, Integer> map = new HashMap<Character, Integer>();

        static void eval() {
            int b = num.pop();
            int a = num.pop();
            char c = op.pop();
            int r = 0;
            if (c == '+') r = a + b;
            else if (c == '-') r = a - b;
            else if (c == '*') r = a * b;
            else r = a / b;
            num.add(r);
        }

        public int calculate(String s) {
            s = '0' + s;  // 开头对负数的处理
            map.put('+', 1);  // Define the priority of the operators
            map.put('-', 1);
            map.put('*', 2);
            map.put('/', 2);
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == ' ') continue;  // Jump the space
                if (c >= '0' && c <= '9') {  // if the character at index i is a digit, read a countinous digit
                    int x = 0, j = i;
                    while (j < s.length() && s.charAt(j) >= '0' && s.charAt(j) <= '9') {
                        x = x * 10 + s.charAt(j) - '0';
                        j++;
                    }
                    i = j - 1;
                    num.add(x);
                } else {  // if Character at index i is a operator, then evaluate it
                    ////op栈非空并且栈顶操作符优先级大于等于当前操作符c的优先级，进行eval()计算
                    while (!op.isEmpty() && map.get(op.peek()) >= map.get(c)) eval();
                    op.add(c);
                }
            }
            while (!op.isEmpty()) eval();
            return num.pop();
        }
    }

    /**
     * 扁平化嵌套列表迭代器
     *
     * 给你一个嵌套的整数列表 nestedList 。每个元素要么是一个整数，要么是一个列表；该列表的元素也可能是整数或者是其他列表。请你实现一个迭代器将其扁平化，使之能够遍历这个列表中的所有整数。
     *
     * 实现扁平迭代器类 NestedIterator ：
     *
     * NestedIterator(List<NestedInteger> nestedList) 用嵌套列表 nestedList 初始化迭代器。
     * int next() 返回嵌套列表的下一个整数。
     * boolean hasNext() 如果仍然存在待迭代的整数，返回 true ；否则，返回 false 。
     * 你的代码将会用下述伪代码检测：
     *
     * initialize iterator with nestedList
     * res = []
     * while iterator.hasNext()
     *     append iterator.next() to the end of res
     * return res
     * 如果 res 与预期的扁平化列表匹配，那么你的代码将会被判为正确。
     *
     * 输入：nestedList = [[1,1],2,[1,1]]
     * 输出：[1,1,2,1,1]
     * 解释：通过重复调用next 直到hasNext 返回 false，next返回的元素的顺序应该是: [1,1,2,1,1]。
     *
     * 输入：nestedList = [1,[4,[6]]]
     * 输出：[1,4,6]
     * 解释：通过重复调用 next 直到 hasNext 返回 false，next 返回的元素的顺序应该是: [1,4,6]。
     *
     */


    /**
     * // This is the interface that allows for creating nested lists.
     * // You should not implement it, or speculate about its implementation
     * public interface NestedInteger {
     * <p>
     * // @return true if this NestedInteger holds a single integer, rather than a nested list.
     * public boolean isInteger();
     * <p>
     * // @return the single integer that this NestedInteger holds, if it holds a single integer
     * // Return null if this NestedInteger holds a nested list
     * public Integer getInteger();
     * <p>
     * // @return the nested list that this NestedInteger holds, if it holds a nested list
     * // Return empty list if this NestedInteger holds a single integer
     * public List<NestedInteger> getList();
     * }
     */
    public class NestedIterator implements Iterator<Integer> {
        private Deque<Integer> deque = new LinkedList<>();

        public NestedIterator(List<NestedInteger> nestedList) {
            dfs(nestedList);
        }

        public void dfs(List<NestedInteger> list) {
            for (NestedInteger nestedInteger :
                    list) {
                if (nestedInteger.isInteger()) {
                    deque.addLast(nestedInteger.getInteger());
                } else {
                    dfs(nestedInteger.getList());
                }
            }
        }

        @Override
        public Integer next() {
            return deque.pollFirst();
        }

        @Override
        public boolean hasNext() {
            return !deque.isEmpty();
        }
    }

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */


}
