package dac.sort;

import java.util.Arrays;

public class QuickSortNonRecursion {

    /**
     * 找基准
     *
     * @param array 数列
     * @param low   数组索引 低位
     * @param high  数组索引 高位
     * @return 基准位置索引
     */
    public static int partition(int[] array, int low, int high) {
        int tmp = array[low];
        while (low < high) {
            while (low < high && array[high] >= tmp) {
                --high;
            }
            if (low >= high) {
                break;
            } else {
                array[low] = array[high];
            }
            while (low < high && array[low] <= tmp) {
                ++low;
            }
            if (low >= high) {
                break;
            } else {
                array[high] = array[low];
            }
        }
        array[low] = tmp;
        return low;
    }

    public static void quickSort(int[] array) {
        int[] stack = new int[array.length];
        int top = 0;
        int low = 0;
        int high = array.length - 1;
        int par = partition(array, low, high);
        //  入栈
        if (par > low + 1) {
            stack[top++] = low;
            stack[top++] = par - 1;
        }
        if (par < high - 1) {
            stack[top++] = par + 1;
            stack[top++] = high;
        }
        //出栈
        while (top > 0) {
            high = stack[--top];
            low = stack[--top];
            par = partition(array, low, high);
            //再入栈
            if (par > low + 1) {
                stack[top++] = low;
                stack[top++] = par - 1;
            }
            if (par < high - 1) {
                stack[top++] = par + 1;
                stack[top++] = high;
            }
        }

    }

    public static void main(String[] args) {
        int[] array = {5, 8, 4, 3, 6, 7, 2, 400, 432, 543, 43};
        quickSort(array);
        System.out.println(Arrays.toString(array));
    }
}
