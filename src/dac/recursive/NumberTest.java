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
    }

}
