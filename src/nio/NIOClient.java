package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Reactor 模式
 * 步骤：
 * 读取操作：
 * 1. 应用程序注册读就绪事件和相关联的事件处理器
 *
 * 2. 事件分离器等待事件的发生
 *
 * 3. 当发生读就绪事件的时候，事件分离器调用第一步注册的事件处理器
 *
 * 4. 事件处理器首先执行实际的读取操作，然后根据读取到的内容进行进一步的处理
 *
 *    写入操作类似于读取操作，只不过第一步注册的是写就绪事件。
 */
public class NIOClient {

    public static void main(String[] args) throws IOException {
        //打开选择器
        Selector selector = Selector.open();
        //打开通道
        SocketChannel socketChannel = SocketChannel.open();
        //配置非阻塞模式
        socketChannel.configureBlocking(false);
        //连接远程主机
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9000));
        //注册事件
        socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);
        //循环处理
        while (true) {
            selector.select(); // 该方法为阻塞方法
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iter = keys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                if (key.isConnectable()) {
                    //连接建立或者连接建立不成功
                    SocketChannel channel = (SocketChannel) key.channel();
                    //完成连接的建立
                    if (channel.isConnectionPending()) {
                        channel.finishConnect();
                    }
                    boolean result = channel.isConnected();
                    if (result) {
                        System.out.println("已经完成连接");
                    } else {
                        System.out.print("连接服务端失败");
                    }
                }
                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(500 * 1024 * 1024);
                    buffer.clear();
                    int receiveCount = channel.read(buffer);
                    if (receiveCount == -1) {
                        // 对端关闭了channel, 本地也需要关闭通道
                        channel.close();
                        iter.remove();
                        continue;
                    }
                    //buffer Handler
                }
                iter.remove();
            }
        }

    }

}
