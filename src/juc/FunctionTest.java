package juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.IntStream;

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

        List<String> strings = f1.apply(100);

        System.out.println(strings.size());
        strings.forEach( str -> System.out.println(str + Thread.currentThread().getName()));
        System.out.println("f1 apply");
        List<String> strings1 = f1.apply(200000);

        strings1.stream().parallel().forEach((str)-> {
            System.out.println(str + Thread.currentThread().getName());
        });

        System.out.println((System.currentTimeMillis() - start));

        System.out.println("Stream takeWhile ....");
        IntStream.of(12, 4, 3, 6, 8, 9).takeWhile((x) -> x % 2 == 0 ).forEach(System.out::println);
        System.out.println("Stream dropWhile Api");
        IntStream.of(12, 4, 3, 6, 8, 9).dropWhile((x) -> x % 2 == 0 ).forEach(System.out::println);
        System.out.println("filter filter");
        IntStream.of(12, 4, 3, 6, 8, 9).filter(n -> n>6).filter(n-> n< 12).forEach(System.out::println);

        System.out.println("---------------");
        int a = 4, b = 15;
        System.out.printf("a = %d, b = %d \n", a, b);
        swap(a, b);
        System.out.printf("a = %d, b = %d \n", a, b);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(()->{
            System.out.println(Thread.currentThread().getName());
        });

        System.out.println("done");
        executorService.shutdown();
    }

    /**
     * switch a and b using time space to reduce memory
     * @param a
     * @param b
     */
    public static void swap(int a, int b){
        a = a + b;
        b = a - b;
        a = a - b;
    }
}
