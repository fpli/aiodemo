package dac;

public class HanoiTower {

    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run() {
                hanoiTower(25, 'A', 'B', 'C');
            }
        }.start();new Thread(){
            @Override
            public void run() {
                hanoiTower(25, 'A', 'B', 'C');
            }
        }.start();new Thread(){
            @Override
            public void run() {
                hanoiTower(25, 'A', 'B', 'C');
            }
        }.start();
        hanoiTower(23, 'A', 'B', 'C');
    }

    // 汉诺塔的移动方法
    // 使用分治算法
    public static void hanoiTower(int num, char a, char b, char c){
        // 如果只有一个盘直接A塔移动到C塔
        if (num ==1){
            System.out.println("第1个盘从\t"+ a + "->" + c);
        } else {
            // 如果我们有n >= 2 情况，我们总是可以看做是两个盘 1 最下面的盘 2 上面的盘
            // 1 先把上面的盘 A->B ,移动过程会使用到c
            hanoiTower(num -1, a, c, b);
            // 2 把最下面的盘从A塔 -> c塔
            System.out.println("第" + num + "个盘从\t" + a + "->" + c);
            // 把B塔的所有盘从B -> C, 移动过程会使用到a
            hanoiTower(num-1, b, a, c);
        }
    }
}
