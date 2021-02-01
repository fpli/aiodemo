package juc;

import java.util.HashMap;

public class HashMapTest {

    public static void main(String[] args) {
        HashMap hashMap = new HashMap(8);
        for (int i = 0; i < 17; i++) {
            hashMap.put(Integer.toString(i), i);
        }
    }
}
