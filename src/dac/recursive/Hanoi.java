package dac.recursive;

import java.util.Scanner;
// https://www.cnblogs.com/liuzhen1995/p/6257945.html
public class Hanoi {

    //使用递归法求解含有n个不同大小盘子的汉诺塔移动路径，参数n为盘子数，把A塔上盘子全部移动到C塔上，B为过渡塔
    public static void recursionHanoi(int n, char A, char B, char C) {
        if (n == 1) {
            System.out.print(A + "——>" + C + "\n");
        } else {
            recursionHanoi(n - 1, A, C, B);         //使用递归先把A塔最上面的n-1个盘子移动到B塔上，C为过渡塔
            System.out.print(A + "——>" + C + "\n");       //把A塔中底下最大的圆盘，移动到C塔上
            recursionHanoi(n - 1, B, A, C);         //使用递归把B塔上n-1个盘子移动到C塔上，A为过渡塔
        }
    }

    public static void noRecursionHanoi(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be >=1");
        }
        char[] hanoiPlate = new char[n];   //记录n个盘子所在的汉诺塔(hanoiPlate[1]='A'意味着第二个盘子现在在A上)
        char[][] next = new char[2][3];   //盘子下次会移动到的盘子的可能性分类
        int[] index = new int[n];

        //根据奇偶性将盘子分为两类
        for (int i = 0; i < n; i = i + 2) {
            index[i] = 0;
        }
        for (int i = 1; i < n; i = i + 2) {
            index[i] = 1;
        }

        //一开始所有盘子都在A上
        for (int i = 0; i < n; i++) {
            hanoiPlate[i] = 'A';
        }

        //n的奇偶性对移动方式的影响
        if (n % 2 == 0) {
            //数组下标为偶数的盘子移动目的塔，注意上面示例的标号为（1），其数组下标为0
            next[0][0] = 'B';
            next[0][1] = 'C';
            next[0][2] = 'A';
            //数组下标为奇数的盘子移动目的塔
            next[1][0] = 'C';
            next[1][1] = 'A';
            next[1][2] = 'B';
        } else {
            //数组下标为偶数的盘子移动目的塔，注意上面示例的标号为（1），其数组下标为0
            next[0][0] = 'C';
            next[0][1] = 'A';
            next[0][2] = 'B';
            //数组下标为奇数的盘子移动目的塔
            next[1][0] = 'B';
            next[1][1] = 'C';
            next[1][2] = 'A';
        }

        //开始移动
        for (int i = 1; i < (1 << n); i++) {                  //总共要执行2^n-1(1<<n-1)步移动
            int m = 0;                                //m代表第m块盘子hanoiPlate[m]

            //根据步骤数i来判断移动哪块盘子以及如何移动
            for (int j = i; j > 0; j = j / 2) {
                if (j % 2 != 0) {    //此步骤光看代码代码有点抽象，建议手动写一下n = 2时的具体移动路径的j、m值变化
                    System.out.println("(" + (m + 1) + ")" + hanoiPlate[m] + "->" + next[index[m]][hanoiPlate[m] - 'A']);
                    hanoiPlate[m] = next[index[m]][hanoiPlate[m] - 'A'];
                    break;                           //移动盘子后则退出这层循环
                }
                m++;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("请输入盘子总数n:");
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        recursionHanoi(n, 'A', 'B', 'C');
        System.out.println("非递归法结果：");
        noRecursionHanoi(n);
        System.out.println();
    }
}
