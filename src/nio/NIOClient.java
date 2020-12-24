package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * Reactor 模式
 * 步骤：
 * 读取操作：
 * 1. 应用程序注册读就绪事件和相关联的事件处理器
 * <p>
 * 2. 事件分离器等待事件的发生
 * <p>
 * 3. 当发生读就绪事件的时候，事件分离器调用第一步注册的事件处理器
 * <p>
 * 4. 事件处理器首先执行实际的读取操作，然后根据读取到的内容进行进一步的处理
 * <p>
 * 写入操作类似于读取操作，只不过第一步注册的是写就绪事件。
 */
public class NIOClient {

    public static void main(String[] args) throws IOException {
        //打开选择器(多路复用器)
        Selector selector = Selector.open();
        //打开通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9000));
        //配置非阻塞模式
        socketChannel.configureBlocking(false);
        String userName = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(userName + " is ok ");

        //注册事件 同时注册连接就绪事件和读就绪事件
        socketChannel.register(selector, SelectionKey.OP_READ);
        new Thread(){
            @Override
            public void run() {
                try {
                    //循环处理
                    while (true) {
                        int count = selector.select();// 该方法为阻塞方法
                        if (count > 0){
                            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                            while (keyIterator.hasNext()) {
                                SelectionKey key = keyIterator.next();
                                if (key.isReadable()) {
                                    SocketChannel channel = (SocketChannel) key.channel();
                                    ByteBuffer buffer = ByteBuffer.allocate(500 * 1024 * 1024);
                                    int receiveCount = channel.read(buffer);
                                    if (receiveCount == -1) {
                                        // the input side of a socket is shut down by one thread
                                        channel.close();
                                        keyIterator.remove();
                                        continue;
                                    }
                                    buffer.flip();
                                    //handle buffer
                                }
                                keyIterator.remove();
                            }
                        } else {
                            System.out.println("no channel to use");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        socketChannel.close();
                        selector.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }.start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            String info = userName + " say:" + line.trim();
            try {
                socketChannel.write(ByteBuffer.wrap(info.getBytes(StandardCharsets.UTF_8)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
