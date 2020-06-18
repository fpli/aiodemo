package dac.recursive;

import java.util.Stack;

/**
 * 算法2：
 * 题目：汉诺塔(修改版，每一步必须经过中间，比如：想要左->右，要左->中，中->右来实现)，通过非递归实现
 * 汉诺塔(修改版，每一步必须经过中间，比如：想要左->右，要左->中，中->右来实现)，通过非递归实现
 */
public class Hanoi2 {

    public static int hanoiProblem2(int num, String left, String mid, String right) {
        Stack<Integer> lS = new Stack<>();
        Stack<Integer> mS = new Stack<>();
        Stack<Integer> rS = new Stack<>();
        lS.push(Integer.MAX_VALUE);//最大值：2147483647(2的7次方-1)
        mS.push(Integer.MAX_VALUE);
        rS.push(Integer.MAX_VALUE);
        for (int i = num; i > 0; i--) {//将数字(最小数字在栈顶)压入左栈[1,2,3]
            lS.push(i);
        }
        //调用枚举
        Action[] record = {Action.No};
        int step = 0;
        //size();stack类从vector继承的方法；返回此向量中的组件数
        while (rS.size() != num + 1) {//当右栈未将数字全部存入时
            //按顺序移动
            step += fStackToStack(record, Action.MToL, Action.LToM, lS, mS, left, mid);
            step += fStackToStack(record, Action.LToM, Action.MToL, mS, lS, mid, left);
            step += fStackToStack(record, Action.RToM, Action.MToR, mS, rS, mid, right);
            step += fStackToStack(record, Action.MToR, Action.RToM, rS, mS, left, mid);
        }
        return step;
    }

    public static int fStackToStack(Action[] record, Action preNoAet, Action nowAct, Stack<Integer> fStack, Stack<Integer> tStack, String from, String to) {
        if (record[0] != preNoAet && fStack.peek() < tStack.peek()) {//发生移动且必须小的数字往大的数字上移动
            tStack.push(fStack.pop());//fStack 移动到 tStack 且删掉from的栈顶元素
            System.out.println("Move " + tStack.peek() + " from " + from + " to " + to);
            record[0] = nowAct;
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        int step = hanoiProblem2(15, "左", "中", "右");
        System.out.println("总共需要" + step + "步");
    }

}

/**
 * 枚举，不移动，左移中，中移左，中移右，右移中
 */
enum Action {
    No, LToM, MToL, MToR, RToM
}