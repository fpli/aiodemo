package nio;

import java.io.FileOutputStream;
import java.io.IOException;

public class HttpsUtilTest {

    public static void main(String[] args) throws IOException {
        String uri = "https://cn.bing.com/th?id=OHR.WoodLine_EN-CN1496881410_800x480.jpg";
        byte[] bytes = HttpsUtil.doGet(uri);
        FileOutputStream fos = new FileOutputStream("bing.picture-of-day.jpg");
        fos.write(bytes);
        fos.close();
        System.out.println("done!");
    }
}
