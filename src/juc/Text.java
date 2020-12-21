package juc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Text {
    public static void main(String[] args) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\c5287463\\Documents\\phone.txt");
        PrintWriter printWriter = new PrintWriter(fileOutputStream);
        //printWriter.println("15927132947");
        String start = "187";
        String end = "13";
        for (int i = 0; i < 10000; i++) {
            String temp = i + "";
            while (temp.length() < 6){
                temp = "0" + temp;
            }
            printWriter.println(start + temp + end);
        }

        printWriter.close();
        fileOutputStream.close();
    }

    public static String format(String start, String end, int count){
        String a = start;
        while (a.length() < count){
            a="0"+a;
        }
        return a;
    }
}
