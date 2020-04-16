package dac.sort;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {
        int[] array = {8, 4, 5, 7, 1, 3, 6, 2};
        int[] tempArray = new int[array.length];
        mergeSort(array, 0, array.length - 1, tempArray);
        System.out.println(Arrays.toString(array));

        array = new int[8000000];
        tempArray = new int[array.length];
        for (int i = 0; i < 8000000; i++) {
            array[i] = (int) (Math.random() * 8000000);
        }

        LocalTime start = LocalTime.now();
        mergeSort(array, 0, array.length - 1, tempArray);
        LocalTime end = LocalTime.now();
        System.out.println(Duration.between(start, end).getSeconds());
    }

    public static void mergeSort(int[] array, int left, int right, int[] tempArray) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(array, left, mid, tempArray);

            mergeSort(array, mid + 1, right, tempArray);

            merge(array, left, mid, right, tempArray);
        }
    }

    /**
     * 左边有序序列， 右边有序序列
     *
     * @param array
     * @param left      左边有序序列的起始索引
     * @param mid
     * @param right     右边有序序列的起始索引
     * @param tempArray
     */
    public static void merge(int[] array, int left, int mid, int right, int[] tempArray) {
        int i = left;
        int j = mid + 1;
        int t = 0;// 指向tempArray的当前索引
        // 1
        while (i <= mid && j <= right) {
            if (array[i] <= array[j]) {
                tempArray[t] = array[i];
                t += 1;
                i += 1;
            } else {
                tempArray[t] = array[j];
                t += 1;
                j += 1;
            }
        }
        // 2
        while (i <= mid) {
            tempArray[t] = array[i];
            t += 1;
            i += 1;
        }

        while (j <= right) {
            tempArray[t] = array[j];
            t += 1;
            j += 1;
        }

        // 3
        t = 0;
        int tempLeft = left;
        while (tempLeft <= right) {
            array[tempLeft] = tempArray[t];
            t += 1;
            tempLeft += 1;
        }
    }
}
