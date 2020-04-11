package dac.tree;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;

public class HeapSort {

    public static void main(String[] args) {
        // 要求将数据进行升序排列
        //int[] array = {4, 6, 8, 5, 9, -1, 90, 89, -99};
        //heapSort(array);
        //System.out.println("调整" + Arrays.toString(array));

        int[] arr = new int[8000000];
        for (int i = 0; i < 8000000; i++) {
            arr[i] = (int)(Math.random() * 8000000);
        }
        LocalTime start = LocalTime.now();
        heapSort(arr);
        LocalTime end = LocalTime.now();
        System.out.println(Duration.between(start, end).getSeconds());
    }

    //堆排序
    public static void heapSort(int[] array) {
        int temp = 0;
        //System.out.println("堆排序");
//        推导过程
//        adjustHeap(array, 1, array.length);
//        System.out.println("第1次调整" + Arrays.toString(array));// 4,9,8,5,6
//
//        adjustHeap(array, 0, array.length);
//        System.out.println("第2次调整" + Arrays.toString(array));// 9,6,8,5,4
        // 调整成大顶堆
        for (int i = array.length / 2 - 1; i >= 0; i--) {
            adjustHeap(array, i, array.length);
        }

        // 将堆顶元素与末尾元素交换，将最大元素"沉"到数组末端
        // 重新调整结构，使其满足堆定义，然后继续交换堆顶元素与末尾元素,反复执行(调整 + 交换)步骤,直到整个序列有序
        for (int j = array.length - 1; j > 0; j--) {
            // 交换
            temp = array[j];
            array[j] = array[0];
            array[0] = temp;
            adjustHeap(array, 0, j);
        }

    }

    /**
     * 功能:完成以i对应的非叶子结点的子树调整成大顶堆
     *
     * @param array  待调整的数组
     * @param i      在数组中的索引
     * @param length 表示对多少个元素进行调整，length是在减小
     */
    public static void adjustHeap(int[] array, int i, int length) {
        // 先取出当前结点的值保存在临时变量中
        int temp = array[i];
        // 开始调整
        // k = 2 * i +1 ; k指向i结点的左子结点
        for (int k = 2 * i + 1; k < length; k = k * 2 + 1) {
            if (k + 1 < length && array[k] < array[k + 1]) {// i结点的左子结点小于i结点的右子结点
                k++; // k 指向右子结点
            }
            // i结点的子结点大于i结点
            if (array[k] > temp) {
                array[i] = array[k];// 把i结点较大的子结点赋i结点
                i = k; // i结点指向k结点, 继续循环比较
            } else {
                // 从左至右，从下至上进行调整
                break;
            }
        }

        // 当for 循环结束后，已经将以i结点为根结点的树的最大值,放在了最顶(局部)
        array[i] = temp;// 将temp的值放到调整后的位置
    }
}
