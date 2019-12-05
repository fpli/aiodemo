package aio.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncServerHandler> {

    @Override
    public void completed(AsynchronousSocketChannel channel, AsyncServerHandler serverHandler) {
        //服务端已经接收客户端成功了，为什么还要调用accept方法？因为一个channel可以接收成千上万个客户端
        //当调用asynchronousServerSocketChannel.accept(this, new AcceptCompletionHandler())方法后，又有新的
        //客户端连接接入，所以需要继续调用他的accept方法，接受其它客户端的接入，继续提供侦听服务
        Server.clientCount++;
        System.out.println("连接的客户端数：" + Server.clientCount);
        serverHandler.channel.accept(serverHandler, this);


        //创建新的Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //异步读  第三个参数为接收消息回调的业务Handler
        channel.read(buffer, buffer, new ReadHandler(channel));
    }

    @Override
    public void failed(Throwable exc, AsyncServerHandler attachment) {
        attachment.latch.countDown();
    }

}
