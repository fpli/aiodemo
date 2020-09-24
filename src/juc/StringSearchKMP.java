package juc;

import java.util.Arrays;

public class StringSearchKMP {
    /**
     * @param args
     */
    public static void main(String[] args) {
        int index = knuthMorrisPratt("ABCDABABCDAABCDABCABCDABDABD", "ABCDABDABD", 0);
        System.out.println(index);
        index = knuthMorrisPratt("BBC ABCDAB ABCDABCDABDE", "ABCDABD", 0);
        System.out.println(index);
        index = knuthMorrisPratt("BBC ABCDAB ABCDABCDABDE", "AB ABC", 0);
        System.out.println(index);
        int[] ints = lengthKMP("aaaab".toCharArray());
        System.out.println(Arrays.toString(ints));
    }

    /**
     * 字符串搜索KMP算法 <br>
     *
     * @param searchStr
     * @param matchStr
     * @param start
     * @return
     */
    public static int knuthMorrisPratt(String searchStr, String matchStr, int start) {
        char[] searchchar = searchStr.toCharArray();
        char[] mchar = matchStr.toCharArray();
        int[] fixNum = lengthKMP(mchar);
        System.out.println(Arrays.toString(fixNum));
        int neglect = 1;
        for (int i = start; i <= searchchar.length - mchar.length; i += neglect) {
            boolean con = true;
            for (int j = 0; j < mchar.length; j++) {
                if (searchchar[i + j] != mchar[j]) {
                    con = false;
                    neglect = j + 1 - fixNum[j == 0 ? 0 : j - 1];
                    break;
                }
            }
            if (con) {
                return i + 1;
            }
        }
        return -1;
    }

    /**
     * 获取部分匹配值的共有元素的长度 <br>
     *
     * @param mchar
     * @return
     */
    public static int[] lengthKMP(char[] mchar) {
        int[] fixNum = new int[mchar.length];
        for (int i = 1, j = 0; i < mchar.length; i++) {
            if (mchar[j] == mchar[i]) {
                fixNum[i] = j + 1;
                j++;
            } else if (j > 0) {
                j = 0;
                i -= j;
            }
        }
        // return [0, 0, 0, 0, 1, 2, 0, 1, 2, 0]ABCDABDABD
        return fixNum;
    }
}
