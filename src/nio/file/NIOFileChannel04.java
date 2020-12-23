package nio.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class NIOFileChannel04 {

    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("file.txt");
        FileChannel fileChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("file04.txt");
        FileChannel fileChannel1 = fileOutputStream.getChannel();

        fileChannel1.transferFrom(fileChannel, 0, fileChannel.size());

        fileChannel1.close();
        fileOutputStream.close();
        fileChannel.close();
        fileInputStream.close();
    }
}
