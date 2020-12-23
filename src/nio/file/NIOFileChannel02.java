package nio.file;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel02 {

    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("file.txt");
        FileChannel fileChannel = fileInputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(fileInputStream.available());
        fileChannel.read(buffer);
        System.out.println(new String(buffer.array()));
        fileInputStream.close();
    }
}
