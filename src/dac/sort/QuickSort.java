package dac.sort;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;

/**
 * 快速排序算法
 * 快速排序(QuickSort)是对冒泡排序的一种改进。基本思想是:通过一趟排序将要排序的数据分割程独立的两部分，
 * 其中一部分的所有数据都比另外一部分的所有数据都要小，然后再按此方法对这两部分数据分别进行快速排序，
 * 整个排序过程可以递归进行，以此达到整个数据变成有序序列
 * <p>
 * 快速排序是C.R.A.Hoare于1962年提出的一种划分交换排序。它采用了一种分治的策略，通常称其为分治法(Divide-and-ConquerMethod)。
 * 该方法的基本思想是：
 * 1．先从数列中取出一个数作为基准数。
 * 2．分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边。
 * 3．再对左右区间重复第二步，直到各区间只有一个数。
 * 虽然快速排序称为分治法，但分治法这三个字显然无法很好的概括快速排序的全部步骤。因此我的对快速排序作了进一步的说明：挖坑填数+分治法：
 */
public class QuickSort {

    public static void main(String[] args) {

        int[] array = {3, 9, -1, 10, 20};
        quickSort(array, 0, array.length -1);
        System.out.println(Arrays.toString(array));

        array = new int[8000000];
        for (int i = 0; i < 8000000; i++) {
            array[i] = (int) (Math.random() * 8000000);
        }

        LocalTime start = LocalTime.now();
        quickSort(array, 0, array.length - 1);
        LocalTime end = LocalTime.now();
        System.out.println(Duration.between(start, end).toSeconds());
    }

    /** 快速排序是对冒泡排序的一种改进。
     *   基本思想是:通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小。
     *   然后再按此方法对这两部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列。
     */
    public static void quickSort(int[] array, int left, int right) {
        int l = left;
        int r = right;
        int middle = (l + r) >>> 1;
        // pivot 中轴值
        int pivot = array[middle];
        int temp;// 临时变量，作为交换时使用
        // while循环的目的是让比pivot值小的值放到左边, 比pivot大的值放到右边
        while (l < r) {
            // 在pivot的左边一直找，找到大于等于pivot的值才退出
            while (array[l] < pivot) {
                l += 1;
            }
            // 在pivot的右边一直找，找到小于pivot值，才退出 ,保证右边的都大于等于中轴值
            while (array[r] > pivot) {
                r -= 1;
            }

            // l >= r说明pivot的左右两边值，已经按照左边全部小于等于pivot值，右边全部大于等于pivot的值
            if (l >= r) {
                break;
            }
            // 交换
            temp = array[l];
            array[l] = array[r];
            array[r] = temp;

            // 如果交换完后，发现这个array[l] == pivot值相等， r-- , r pointer 前移
            if (array[l] == pivot) {
                r -= 1;
            }

            // 如果交换完后，发现这个array[r] == pivot 值相等 l++, l pointer 后移
            if (array[r] == pivot) {
                l += 1;
            }
        }

        // 如果l == r, 必须l++, r-- 否则为出现栈溢出
        if (l == r) {
            l += 1;
            r -= 1;
        }

        // 向左递归
        if (left < r) {
            quickSort(array, left, r);
        }

        // 向右递归
        if (right > l) {
            quickSort(array, l, right);
        }
    }

    //快速排序  x取区间第一个数为基准数
    public static void quickSort2(int[] array, int left, int right){
        if (left < right){
            // 以left索引的数作为基准数
            int i = left;
            int j = right;
            int pivot = array[i];
            while (i < j){
                // 从右边找第一个小于基准数的数
                while (j > i && array[j] > pivot){
                    j--;
                }
                if (j > i){
                    array[i] = array[j];
                }
                // 从左边向右边找第一个大于基准数的数
                while (i < j && array[i] <= pivot){
                    i++;
                }
                if (i < j){
                    array[j] = array[i];
                }
            }
            array[i] = pivot;
            // 此时 i 左边都小于等于基准值的数, i右边都是大于等于基准数的数
            quickSort2(array, left, i -1);
            quickSort2(array, i + 1, right);
        }
    }

}