package dac.sort;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;

/**
 *插入排序的基本思想是:把n个待排序的元素看成为一个有序表和一个无序表，开始时有序表中只包含一个元素，无序表中包含n-1个元素，
 * 排序过程中每次从无序表中取出第一个元素，把它的排序码依次与有序表元素的排序码进行比较，将它插入到有序表的适当位置，使之成为新的有序表。
 */
public class InsertSort {

    public static void main(String[] args) {
       int[] arr = {1, 2, 3, 6, 5 ,4};
        insertSort(arr);
        System.out.println(Arrays.toString(arr));
        /*int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int)(Math.random() * 8000000);
        }
        LocalTime start = LocalTime.now();
        insertSort(arr);
        LocalTime end = LocalTime.now();
        System.out.println(Duration.between(start, end).getSeconds());*/
    }

    // 插入排序 时间复杂度 O(N^2) 平方阶
    public static void insertSort(int[] arr){
        int insertVal = 0;
        int insertIndex = 0;
        // i从1开始，不是从0 ， 初始状态分为一个元素的有序序列， 2～n 的(n-1)个无序序列
        for (int i = 1; i < arr.length ; i++) {
            // 定义待插入的数
            insertVal = arr[i];
            insertIndex = i-1; // 即arr[i]的前面这个数的下标
            /*
            * 给insertVal找到插入的位置
            * 说明
            * 1 insertIndex >= 0 保证在给insertVal找插入位置，不越界
            * 2 insertVal < arr[insertIndex] 待插入的数，还没有找到插入位置
            * 3 就需要将arr[insertIndex]后移,insertVal qian yi
            * */
            while (insertIndex >= 0 && insertVal < arr[insertIndex]){
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex--;
            }

            if (insertIndex + 1 != i){
                // 当退出while循环时，说明插入的位置找到，insertIndex + 1
                arr[insertIndex + 1] = insertVal;
            }
        }
    }
}
