package aio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AsyncServerHandler implements Runnable {

    public CountDownLatch latch;
    public AsynchronousServerSocketChannel channel;

    public AsyncServerHandler(int port) {
        try {
            //创建服务端通道
            channel = AsynchronousServerSocketChannel.open();
            //绑定端口
            InetSocketAddress local = new InetSocketAddress(port);
            channel.bind(local);
            System.out.println("服务器已启动，端口号：" + port);
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
        latch = new CountDownLatch(1);
        //用于接收客户端的连接
        channel.accept(this, new AcceptHandler());
        System.out.println("开始接受客户端连接");
        try {
            latch.await();//保证守护线程不会退出
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}


