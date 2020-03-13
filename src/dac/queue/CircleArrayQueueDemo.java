package dac.queue;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class CircleArrayQueueDemo {

    public static void main(String[] args) {
        CircleArrayQueue queue = new CircleArrayQueue(4);
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

class CircleArrayQueue{

    private int maxSize; //表示数组的最大容量
    private int front;  // 队列头
    private int rear;   // 指向队列的最后一个元素的后一个位置，预留一个空间

    private int[] arr;  // 该数据用于存放数据，模拟队列

    public CircleArrayQueue(int arrMaxSize){
        maxSize = arrMaxSize;
        arr     = new int[maxSize];
    }

    // 判断队列是否满
    public boolean isFull(){
        return (rear + 1) % maxSize == front;
    }

    // 判断队列是否空
    public boolean isEmpty(){
        return rear ==front;
    }

    public void addQueue(int value){
        // 判断队列满
        if (isFull()) {
            System.out.println("队列满，不能加入数据");
            return;
        }
        // rear 指向预留单元， 因此直接将数据加入
        arr[rear] = value;
        // 将rear后移，注意必须考虑取模
        rear = (rear + 1) % maxSize;
    }

    public int getQueue(){
        if (isEmpty()){
            throw new NoSuchElementException("队列没有数据");
        }
        // front 指向队列的第一个元素
        int value = arr[front];
        front = (front + 1) % maxSize;
        return value;
    }

    public void showQueue(){
        if (isEmpty()){
            System.out.println("没有数据");
            return;
        }
        // 思路:从front开始遍历，遍历多少个元素
        for (int i = front; i < front + size(); i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }

    // 求出环形队列多少个有效数据
    public int size(){
        // rear = 1, front = 0  maxSize = 8 ;(1 + 8 -0) % 8 == 1
        return (rear + maxSize -front) % maxSize;
    }

    // 显示队列的第一个元素
    public int headQueue(){
        if (isEmpty()){
            throw new RuntimeException("没有数据");
        }
        return arr[front];
    }
}
