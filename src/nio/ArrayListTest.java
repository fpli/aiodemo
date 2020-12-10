package nio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListTest {

    public static void main(String[] args) {
        String[] strs = {"ddsdd", "fdfdfff", "gfgf"};
        List<String> dest = new ArrayList<>();
        dest.add("12345");
        System.out.println(Arrays.toString(dest.toArray(strs)));
    }
}
