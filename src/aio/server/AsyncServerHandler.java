package aio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.Executors;

public class AsyncServerHandler implements Runnable {

    public AsynchronousServerSocketChannel channel;

    public AsyncServerHandler(int port) {
        try {
            AsynchronousChannelGroup asynchronousChannelGroup = AsynchronousChannelGroup.withCachedThreadPool(Executors.newFixedThreadPool(5), 2);
            //open server socket channel
            channel = AsynchronousServerSocketChannel.open(asynchronousChannelGroup);
            //bind port
            InetSocketAddress local = new InetSocketAddress(port);
            channel.bind(local);
            System.out.println("server socket is opened, ready to listen:" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //CountDownLatch初始化
        //它的作用：在完成一组正在执行的操作之前，允许当前的线程一直阻塞
        //此处，让线程在此阻塞，防止服务端执行完成后退出
        //也可以使用while(true)+sleep
        //生产环境就不需要担心这个问题，因为服务端是不会退出的
        //latch = new CountDownLatch(1);
        //用于接收客户端的连接
        channel.accept(this, new AcceptCompletionHandler());
    }


}


