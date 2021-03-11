package dac.sort;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;

public class SelectSort {

    public static void main(String[] args) {
        int[] arr = {101, 34, 119, 1, -1, 90, 123};
        System.out.println(Arrays.toString(arr));
        selectSort2(arr);
        System.out.println("排序后");
        System.out.println(Arrays.toString(arr));

        int[] array = new int[80000];
        for (int i = 0; i < 80000; i++) {
            array[i] = (int)(Math.random() * 8000000);
        }
        LocalTime start = LocalTime.now();
        selectSort(array);
        LocalTime end = LocalTime.now();
        System.out.println(Duration.between(start, end).getSeconds());
    }

    // 选择排序算法 时间复杂度O(n^2)平方阶
    public static void selectSort(int[] array) {
        int minIndex, min;
        // 选择排序算法时间复杂度O(n^2)
        for (int i = 0; i < array.length - 1; i++) {
            minIndex = i;

            for (int j = i + 1; j < array.length; j++) {
                if (array[minIndex] > array[j]) {//说明假定的最小值，并不是最小的
                    minIndex = j; // 重置minIndex
                }
            }

            // 将最小值，放在arr[minIndex], 即交换
            if (minIndex != i) {
                min = array[minIndex];
                array[minIndex] = array[i];
                array[i] = min;
            }
        }
    }

    //
    public static void selectSort2(int[] array) {
        int maxIndex, max;
        for (int i = array.length - 1; i > 0; i--) {
            maxIndex = i;
            for (int j = i - 1; j >= 0; j--) {
                if (array[j] > array[maxIndex]) {
                    maxIndex = j;
                }
            }
            if (maxIndex != i) {
                max = array[maxIndex];
                array[maxIndex] = array[i];
                array[i] = max;
            }
        }
    }
}
