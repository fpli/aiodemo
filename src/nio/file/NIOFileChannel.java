package nio.file;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel {

    public static void main(String[] args) throws Exception {
        String str = "it's too late";
        FileOutputStream fileOutputStream = new FileOutputStream("file.txt");

        FileChannel fileChannel = fileOutputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(str.getBytes());

        buffer.flip();

        fileChannel.write(buffer);

        fileOutputStream.close();
    }
}
