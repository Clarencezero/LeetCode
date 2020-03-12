package leetcode.heap;

import java.util.PriorityQueue;

// 使用两个堆用来存储数据
// TODO 1.如何使用Java构建大顶堆, 小顶堆
// 2.熟悉Java自带的优先队列
class MedianFinder {
    private PriorityQueue<Integer> small; // 小堆
    private PriorityQueue<Integer> large; // 大堆
    // 是否为偶数
    private boolean even = true;

    public MedianFinder() {
        small = new PriorityQueue<>((o1, o2) -> o2 - o1);
        large = new PriorityQueue<>();
    }

    public void addNum(int num) {
        if (even) {
            // 如果为偶数, 则将元素添加到大堆,
            large.add(num);
            small.add(large.poll());
        } else {
            large.add(small.poll());
            small.add(num);
        }
        even = !even;
    }

    public double findMedian() {
        if (even) {
            return (small.peek() + large.peek()) / 2.0;
        } else {
            return small.peek();
        }
    }
}


public class No295FindMedianfromDataStream {
    public static void main(String[] args) {
        int num = 3;
        MedianFinder obj = new MedianFinder();
        obj.addNum(num);
        obj.addNum(2);
        obj.addNum(7);
        double param_2 = obj.findMedian();
        System.out.println(param_2);
//        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
//        priorityQueue.add(5);
//        priorityQueue.add(4);
//        priorityQueue.add(3);
//        priorityQueue.add(2);
//
//        System.out.println(priorityQueue.peek());
//        System.out.println(priorityQueue.poll());
//        System.out.println(priorityQueue.offer(1));
//        System.out.println(priorityQueue.peek());
//        System.out.println(priorityQueue.poll());
    }

}
