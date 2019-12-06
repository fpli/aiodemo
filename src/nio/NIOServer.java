package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    public static void main(String[] args) throws IOException {
        //打开选择器
        Selector selector = Selector.open();
        //打开通到
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        //配置非阻塞模型
        socketChannel.configureBlocking(false);
        //绑定端口
        socketChannel.bind(new InetSocketAddress(9000));
        //注册事件，OP_ACCEPT只适用于ServerSocketChannel
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            selector.select();// 该方法为阻塞方法
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isValid() && key.isAcceptable()) {
                    SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
                    channel.configureBlocking(false);// 配置为非阻塞模式
                    channel.register(selector, SelectionKey.OP_READ);        // 为SocketChannel注册可读事件，等待数据到来
                    socketChannel.register(selector, SelectionKey.OP_ACCEPT);// 再次为ServerSocketChannel注册接受事件
                }

                if (key.isValid() && key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(512);
                    int receiveCount = channel.read(readBuffer);
                    if (receiveCount == -1) {// 对端已经关闭了通道
                        channel.close();
                        keyIterator.remove();
                        continue;
                    }
                    readBuffer.flip();
                    //handler Buffer
                    //一般是响应客户端的数据
                    //直接是write写不就完事了嘛，为啥需要write事件？
                    //channel.write(...)
                }

                //isWritable分支
                if (key.isValid() && key.isWritable()) {
                    ByteBuffer buffer = (ByteBuffer) key.attachment();//待发送的数据
                    SocketChannel channel = (SocketChannel) key.channel();
                    while (buffer.hasRemaining()) {
                        //该方法只会写入小于socket's output buffer空闲区域的任何字节数
                        //并返回写入的字节数，可能是0字节。
                       int count = channel.write(buffer);
                        System.out.println("write count:"+count);
                    }
                    //发送完了就取消写事件，否则下次还会进入该分支
                    key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
                }

                keyIterator.remove();
            }
        }

    }

}
