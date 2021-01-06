package nio.file;

import java.io.UnsupportedEncodingException;
import java.nio.file.Path;

public class LinuxFileTest {

    public static void main(String[] args) throws UnsupportedEncodingException {

        Path path = Path.of("/home/fpli/Downloads/New Folder/new f");
        System.out.println(path.toFile().exists());

        String dir = "/home/fpli/Downloads/New Folder/new f";
        Path path1 = Path.of(dir);
        System.out.println(path1.toFile().exists());

        String  str1 = new StringBuilder("ali").append("baba").toString();
        System.out.println(str1);
        System.out.println(str1.intern());
        System.out.println(str1 == str1.intern());

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2);
        System.out.println(str2.intern());
        System.out.println(str2 == str2.intern());
    }
}
