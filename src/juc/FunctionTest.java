package juc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FunctionTest {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Function<Integer, List<String>> f1 = (count) -> {
            List<String> list = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                list.add("thing--->" + i);
            }
            return  list;
        };

        List<String> strings = f1.apply(100000000);

        System.out.println(strings.size());
        strings.forEach( str -> System.out.println(str));
        System.out.println("=============");
        List<String> strings1 = f1.apply(20000);

        strings1.stream().forEach((str)-> {
            System.out.println(str);
        });

        System.out.println((System.currentTimeMillis() - start));
    }
}
