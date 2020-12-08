package nio;

import java.text.MessageFormat;

public class StringFormatTest {

    public static void main(String[] args) {
        System.out.println(String.format("age: %d, name: %s", 12, "ABC"));
        System.out.println(MessageFormat.format("age: {0}, name: {1}", 12, "abc"));
    }
}
