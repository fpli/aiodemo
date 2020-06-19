package dac.recursive;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * java中浮点数计算精度有误差，所以采用BigDecimal来代替小数运算
 */
public class NumberTest {

    public static void main(String[] args) {
        double d1 = 3.124;
        double d2 = 5.0033;
        System.out.println(d1%1.0000);
        System.out.println(d2%1.00);

        BigDecimal amt = new BigDecimal("101");
        BigDecimal[] results = amt.divideAndRemainder(new BigDecimal("-20"));// 取余运算 有整数商和余数，所以是数组
        System.out.println(Arrays.toString(results));

        for (int i = 0; i < 5000; i++) {
            double f = f(i);
            if (f % 1 == 0){
                System.out.println(i);
                System.out.println(f);
            }
        }
        System.out.println("=======");
        for (int i = 0; i < 5000; i++) {
            double c5 = i;// 第5只猴子带走的一份数量
            double y5 = i * 5 + 1;// y(n) = 1.25 * y(n-1) + 1 ==> y4 = 1.25 * y5 + 1;
            for (int j = 4; j > 0 ; j--) {
                y5 = 1.25 * y5 + 1;
                if (y5 % 1 != 0){
                    break;
                }
            }
            if (y5 % 1 == 0){
                System.out.println("5th:" + c5);
                System.out.println("total:" + y5);
            }
        }
    }

    /**
     * x 代表 第5只拿的桃子数量
     * y(n) 代表 第n只猴子分桃时的桃子总量
     * n        y(n)
     * 5       y(5) = x * 5 + 1
     * 4       (y(4) -1) / 5 * 4 = y(5) ==> y(4) = 5 * y(5) / 4 +1 ==> 5 * (x * 5 +1) / 4 + 1 ==> 1.25 * y(5) + 1 ==> 1.25 * (x *5 +1 ) +1 ==> 6.25 * x + 2.25
     * 3       (y(3) -1) / 5 * 4 = y(4)  ==> y(3) = 5 * y(4) / 4 +1 ==> 5 * (5 * (x * 5 +1) / 4 + 1) / 4 + 1 ==> 1.25 * y(4) +1 ==> 1.25 * (6.25 *x + 2.25) + 1 ==> 7.8125 *x + 3.8125
     * 2       (y(2) -1) / 5 * 4 = y(3)  ==> y(2) = 1.25 * y(3) + 1 ==> 1.25 * (7.8125 *x + 3.8125) + 1 ==> 9.765625 * x + 5.765625‬
     * 1      y(1) = 1.25 (9.765625 * x + 5.765625) + 1 ==> 12.20703125 * x + 8.20703125
     * y(n) = 1.25 y(n+1) + 1;
     */

    public static double f(int x){
        return 12.20703125 * x + 8.20703125;
    }
}
