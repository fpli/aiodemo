package dac.recursive;

import java.math.BigDecimal;

/**
 * java中浮点数计算精度有误差，所以采用BigDecimal来代替小数运算
 */
public class NumberTest {

    public static void main(String[] args) {
        double d1 = 3.124;
        double d2 = 5.00000;
        System.out.println(d1%1.0000);
        System.out.println(d2%1.00);

        BigDecimal amt = new BigDecimal("1001");
        BigDecimal[] results = amt.divideAndRemainder(new BigDecimal("20"));// 取模运算 有商和余数，所以是数组
        System.out.println(results[0]);// 商
        System.out.println(results[1]);// 余数
    }
}
