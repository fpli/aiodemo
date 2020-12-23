package nio.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel03 {

    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("file.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("file02.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true){
            // very important
            buffer.clear();

            int count = fileChannel01.read(buffer);
            if (count == -1){
                break;
            }

            buffer.flip();
            fileChannel02.write(buffer);
        }

        fileOutputStream.close();
        fileInputStream.close();
    }
}
