package desDemo;

import java.util.Arrays;

public class BinaryStringTest {

    public static void main(String[] args) {
        byte b = 41;
        System.out.println(Integer.toBinaryString(b));
        String str = "i like java";
        byte[] strBytes = str.getBytes();
        System.out.println(str.length());
        System.out.println(Arrays.toString(str.getBytes()));
    }


}

class Node implements Comparable<Node>{

    int value;
    int weight;// 97, 101

    Node left;
    Node right;

    @Override
    public int compareTo(Node o) {
        return 0;
    }
}