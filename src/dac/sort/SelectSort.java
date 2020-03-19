package dac.sort;

import java.time.Duration;
import java.time.LocalTime;

public class SelectSort {
    public static void main(String[] args) {
        /*int[] arr = {101, 34, 119, 1, -1, 90,123};
        System.out.println(Arrays.toString(arr));
        System.out.println("排序后");
        selectSort(arr);
        System.out.println(Arrays.toString(arr));*/

        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int)(Math.random() * 8000000);
        }
        LocalTime start = LocalTime.now();
        selectSort(arr);
        LocalTime end = LocalTime.now();
        System.out.println(Duration.between(start, end).getSeconds());
    }

    // 选择排序算法
    public static void selectSort(int[] arr){
        // 选择排序算法时间复杂度O(n^2)
        for (int i = 0; i < arr.length -1; i++) {
            int minIndex = i;
            int min = arr[i];
            for (int j = i+1; j < arr.length; j++) {
                if (min > arr[j]){//说明假定的最小值，并不是最小的
                    min = arr[j]; // 重置min
                    minIndex = j; // 重置minIndex
                }
            }

            // 将最小值，放在arr[minIndex], 即交换
            if (minIndex != i){
                arr[minIndex] = arr[i];
                arr[i] = min;
            }
        }
    }
}
