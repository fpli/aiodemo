package dac.sort;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;

/**
 * 冒泡排序算法优化，增加flag表识变量
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] array = {3, 9, -1, 10, 20};
        bubbleSort(array);
        System.out.println(Arrays.toString(array));

        array = new int[80000];
        for (int i = 0; i < 80000; i++) {
            array[i] = (int)(Math.random() * 8000000);
        }
        LocalTime start = LocalTime.now();
        bubbleSort(array);
        LocalTime end = LocalTime.now();
        System.out.println(Duration.between(start, end).getSeconds());
    }

    public static void bubbleSort(int[] arr){
        // 冒泡排序算法的时间复杂度O(n^2) 平方阶
        int temp = 0;// 临时变量
        boolean flag = false;// 标识变量,是否进行过交换
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length -1 - i; j++) {
                // 如果前面的数比后面的数大，则交换, 从小到大排序
                if (arr[j] > arr[j+1]){
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
            //System.out.println("第"+(i + 1) + "趟排序后的数组");
            //System.out.println(Arrays.toString(arr));
            if (!flag){
                // 在一趟排序中，一次交换都没有发生过
                break;
            } else {
                flag = false;// 重置flag,进行下此判断
            }
        }
    }
}
