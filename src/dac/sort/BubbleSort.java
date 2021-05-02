package dac.sort;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;

/**
 * 冒泡排序算法优化，增加flag表识变量
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] array = {3, 9, -1, 10, 20, -20};
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

    public static void bubbleSort(int[] array){
        // 冒泡排序算法的时间复杂度O(n^2) 平方阶
        int temp;// 临时变量
        boolean flag;// 标识变量,是否进行过交换
        for (int i = 0; i < array.length - 1; i++) {
            flag = false;// reset flag
            for (int j = 0; j < array.length -1 - i; j++) {
                // ascend
                if (array[j] > array[j+1]){
                    flag = true;
                    temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
            //System.out.println("第"+(i + 1) + "趟排序后的数组");
            //System.out.println(Arrays.toString(arr));
            if (!flag){
                // 在一趟排序中，一次交换都没有发生过
                break;
            }
        }
    }
}
