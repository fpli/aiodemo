package dac.recursive;

/**
 * 题目:5只猴子摘了一堆桃子，相约第二天早上分桃，第一只猴子来的比较早，见其他猴子没来，于是他将桃子平均分成5份，还剩下一个桃子，他就把剩下的那个桃子吃了
 * 拿走其中一份，把剩下的4份又混合成一堆。第二只猴子来了后也像第一只猴子那样做
 * 编程求出最原始有多少个桃子
 * 思路分析:使用递归实现，
 * 递归有边界条件 1 第1个猴子分完 2 桃子数-1 不够5等分 桃子数递增1，回溯算法
 * 递归有前进段   第1只猴子分完，第2只猴子分，第3只猴子分，第4只猴子分，第5只猴子分，结束返回
 * 递归有返回段    第5只猴子分完，返回，第4只猴子分完，返回，
 */
public class PeachDivideAlgorithm {

    private static int monkeyCount;// 猴子数量 可以调控， 每次分完剩余的桃子数量也是可以调控的
    private static int peachCount; // 桃子数量

    /**
     * currentMonkeyCounter 5-> 1 ,包含1 ，所以 currentMonkeyCounter == 0 是成功时的递归调用终结点
     *
     * @param currentMonkeyCounter 猴子计数器 当前第几只猴子
     * @return 1 标识分配成功 -1 标识分配失败
     */
    public static int getResult(int currentMonkeyCounter) {
        if (currentMonkeyCounter == 0) {// 递归返回段
            return 1;
        }
        if ((peachCount - 1) % 5 != 0) {// 回溯点
            return -1;
        }
        peachCount = (peachCount - 1) * 4 / 5;
        return getResult(currentMonkeyCounter - 1);// 递归前进段
    }

    public static void main(String[] args) {
        monkeyCount = 5;
        for (int i = 0; i < 10000; i++) {// 桃子总数在10000以内的穷举
            peachCount = i;
            if (getResult(monkeyCount) == 1) {
                System.out.println("the number of the peach:" + i);
            }
        }
    }

    /**
     * A、B、C、D、E 五人在某天夜里合伙去捕鱼，到第二天凌晨时都疲惫不堪，于是各自找地方睡觉。
     *
     * 日上三杆，A 第一个醒来，他将鱼分为五份，把多余的一条鱼扔掉，拿走自己的一份。
     *
     * B 第二个醒来，也将鱼分为五份，把多余的一条鱼扔掉拿走自己的一份。 。
     *
     * C、D、E依次醒来，也按同样的方法拿鱼。
     *
     * 问他们台伙至少捕了多少条鱼?以及每个人醒来时见到了多少鱼？
     */
    public void f(){
        int n, j, k, l, m;
        for (n = 5; ; n++) {
            j = 4 * (n - 1) / 5;
            k = 4 * (j - 1) / 5;
            l = 4 * (k - 1) / 5;
            m = 4 * (l - 1) / 5;
            if (n % 5 == 1 && j % 5 == 1 && k % 5 == 1 && l % 5 == 1 && m % 5 == 1) {
                System.out.printf("至少合伙捕鱼：%d条\n", n);
                System.out.printf("分别见到鱼的条数：%d %d %d %d\n", j, k, l, m);
                break;
            }
        }
    }

}
