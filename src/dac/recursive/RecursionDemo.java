package dac.recursive;

import java.util.Arrays;

public class RecursionDemo {

    public static void main(String[] args) {
        print(50);

        System.out.println(factorial(10));

        int[] array = {-9, 78, 0, 23, -567, 70, -1, 900, 4561};
        quickSort(array, 0, array.length -1);
        System.out.println("array =" + Arrays.toString(array));
    }

    // 递归打印出1..n
    public static void print(int n) {
        if (n > 1) {
            print(n - 1);
        }
        System.out.printf("n=%d \n", n);
    }

    // 求n的阶层 n! = n * (n-1)!
    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    /* 快速排序是对冒泡排序的一种改进。
        基本思想是:通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小。
        然后再按此方法对这两部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列。
     */
    public static void quickSort(int[] array, int left, int right) {
        int l = left;
        int r = right;
        // pivot 中轴值
        int pivot = array[(left + right) / 2];
        int temp;// 临时变量，作为交换时使用
        // while循环的目的是让比pivot值小的值放到左边
        // 比pivot大的值放到右边
        while (l < r) {
            // 在pivot的左边一直找，找到大于等于pivot的值才退出
            while (array[l] < pivot) {
                l += 1;
            }
            // 在pivot的右边一直找，找到小于等于pivot值，才退出
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

            // 如果交换完后，发现这个array[l] == pivot值相等， r-- , 前移
            if (array[l] == pivot) {
                r -= 1;
            }

            // 如果交换完后，发现这个array[r] == pivot 值相等 l++, 后移
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
}
