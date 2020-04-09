package dac.recursive;

/**
 * 经典算法猴子吃桃问题
 * 迭代方式实现和递归方式实现
 * 题目：
 * 猴子第一天摘了若干个桃子，当即吃了一半，还不解馋，又多吃了一个； 第二天，吃剩下的桃子的一半，还不过瘾，又多吃了一个；
 * 以后每天都吃前一天剩下的一半多一个，到第10天想再吃时，只剩下一个桃子了。问第一天共吃了多少个桃子？
 * 思路：我们可以采用逆向思维，第十天剩一个，前一天则为 S9 = （S10 +1）*2，以此推算前一天。可以采用递归（下面介绍三种方法）
 * f(n) = f(n-1) / 2 -1;
 * f(n-1) = (f(n) + 1) * 2;
 * f(n) = (f(n+1) + 1) * 2;
 * f(n-1) / 2 - 1 == f(n) == (f(n+1) + 1) * 2
 */
public class MonkeyTao {

    public static void main(String[] args) {
        int amount = total(1);
        System.out.println(amount);
        System.out.println(whileWay());
    }

    //解法二 递归实现
    public static int total(int day){
        if (day == 10){
            return 1;
        } else {
            return (total(day + 1) + 1) * 2;
        }
    }

    //解法三 while 循环
    public static int whileWay(){
        int next = 1;   // 第10天 剩1个
        int prev = 0;   //表示前一天
        int day = 9;
        while(day > 0){
            prev = (1+next) * 2;   // next 表示后一天
            next = prev;
            day--;
        }
        return prev;
    }

    //解法一、for 循环实现
    public static int forWay(){
        int next = 1;            // 第10天 剩1个
        int prev = 0;            //表示前一天
        for(int day = 9; day > 0; day--){
            prev = (1+next) * 2;   // next 表示后一天
            next = prev;
        }
        System.out.println(prev);
        return prev;
    }
}
