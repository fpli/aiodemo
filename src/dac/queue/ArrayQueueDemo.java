package dac.queue;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ArrayQueueDemo {

    public static void main(String[] args) {
        //测试
        ArrayQueue queue = new ArrayQueue(3);
        char key = ' ';//菜单key
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        while (loop){
            System.out.println("s(show):显示队列");
            System.out.println("e(exit):退出程序");
            System.out.println("a(add):添加数据");
            System.out.println("g(get):取出数据");
            System.out.println("h(head):查看队列头数据");
            key = scanner.next().charAt(0);
            switch (key){
                case 's':
                    queue.showQueue();
                    break;
                case 'a':
                    System.out.println("请输入一个数据");
                    int value = scanner.nextInt();
                    queue.addQueue(value);
                    break;
                case 'g':
                    try {
                        int reault = queue.getQueue();
                        System.out.printf("取出的数据是%d\n", reault);
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int result = queue.headQueue();
                        System.out.printf("队列头数据%d\n", result);
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case  'e':
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出");
    }
}

/*
* 队列介绍
* 队列是一个有序列表，可以使用数组或是链表来实现
* 遵循先入先出的原则。(FIFO)
* */
// 使用数组模拟队列
class ArrayQueue {
    private int maxSize; //表示数组的最大容量
    private int front;  // 队列头
    private int rear;   // 队列尾

    private int[] arr;  // 该数据用于存放数据，模拟队列

    public ArrayQueue(int arrMaxSise) {
        this.maxSize = arrMaxSise;
        this.arr = new int[maxSize];
        front = -1; //指向队列头部，分析出front指向队列头的前一个位置
        rear = -1; // 指向队列尾，指向队列尾的数据(就是队列最后一个数据)
    }

    //判断队列是否满
    public boolean isFull() {
        return rear == maxSize - 1;
    }

    // 判断队列是否已空
    public boolean isEmpty() {
        return rear == front;
    }

    // 添加数据到队列
    public void addQueue(int n) {
        // 判断队列满
        if (isFull()) {
            System.out.println("队列满，不能加入数据");
            return;
        }
        rear++; // 让rear后移
        arr[rear] = n;
    }

    // 出队列
    public int getQueue() {
        if (isEmpty()) {
            throw new NoSuchElementException("队列没有数据");
        }

        front++;
        return arr[front];
    }

    // 显示队列的所有数据
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("没有数据");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("arr[%d]=%d\n", i, arr[i]);
        }
    }

    // 显示队列的头数据，注意不是取出数据
    public int headQueue(){
        if (isEmpty()){
            throw new RuntimeException("没有数据");
        }
        return arr[front+1];
    }
}