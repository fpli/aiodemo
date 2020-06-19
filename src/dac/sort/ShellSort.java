package dac.sort;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;

/**
 * 希尔排序算法
 * 希尔排序算法基本思想
 *   希尔排序是把记录按下标的一定增量分组，对每组使用直接插入排序算法排序，随着增量逐渐减小，每组包含的关键词(数据)越来越多，
 *   当增量减至1时，整个文件(数据序列)恰被分成一组，算法便终止
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] arr = {8,9,1,7,2,3,5,4,6,0,-1};
        shellSort(arr);
        System.out.println(Arrays.toString(arr));
        int[] array2 = new int[80000];
        for (int i = 0; i < 80000; i++) {
            array2[i] = (int)(Math.random() * 8000000);
        }
        LocalTime start = LocalTime.now();
        shellSort2(array2);
        LocalTime end = LocalTime.now();
        System.out.println(Duration.between(start, end).getSeconds());
    }

    // 交换式的希尔排序
    public static void shellSort(int[] arr){
        int temp = 0;
        //int count = 0;
        for (int gap = arr.length >> 1; gap > 0; gap >>= 1){
            for (int i = gap; i < arr.length; i++) {
                // 遍历各组中所有的元素
                for (int j = i - gap; j >= 0; j -= gap) {
                    // 如果当前元素大于加上步长后的那个元素，交换
                    if (arr[j] > arr[j + gap]){
                        temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
            //System.out.println("希尔排序第"+(++count)+"轮:" + Arrays.toString(arr));
        }
    }

    // 移位式的希尔排序
    public static void shellSort2(int[] arr){
        // 增量gap,并逐步的缩小增量
        for (int gap = arr.length >> 1; gap > 0; gap >>= 2){
            // 从第gap个元素，逐个对其所在的组进行直接插入排序
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                int temp = arr[j];
                if (arr[j] < arr[j -gap]){
                    while (j-gap >= 0 && temp < arr[j-gap]){
                        // 移动
                        arr[j] = arr[j-gap];
                        j -= gap;
                    }
                    // 当退出while后，就给temp找到插入的位置
                    arr[j] = temp;
                }
            }
        }
    }
}
