package aio.new01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ConcurrentMap;

/**
 * 这个处理器，专门用来响应 ServerSocketChannel 的事件。
 * AsynchronousServerSocketChannel只有一种事件：接受客户端的连接
 */
public class AcceptSocketChannelCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, ConcurrentMap<String, AsynchronousSocketChannel>> {

    private AsynchronousServerSocketChannel serverSocketChannel;

    /**
     * @param serverSocketChannel
     */
    public AcceptSocketChannelCompletionHandler(AsynchronousServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }

    /**
     * 注意，我们分别观察 this,socketChannel,Attachment三个对象的id。
     * 来观察不同客户端连接到达时，这三个对象的变化，以说明ServerSocketChannelHandle的监听模式
     */
    @Override
    public void completed(AsynchronousSocketChannel socketChannel, ConcurrentMap<String, AsynchronousSocketChannel> attachment) {
        System.out.print("completed(AsynchronousSocketChannel result, ByteBuffer attachment)");
        //每次都要重新注册监听（一次注册，一次通知(OS调用APP)），但是由于“文件状态标示符”是独享的，所以不需要担心有“漏掉的”事件
        this.serverSocketChannel.accept(attachment, this);
        String hostName = null;
        try {
            InetSocketAddress remoteAddress = (InetSocketAddress) socketChannel.getRemoteAddress();
            hostName = remoteAddress.getHostName();
            if (attachment.containsKey(hostName)) {
                AsynchronousSocketChannel oldAsynchronousSocketChannel = attachment.get(hostName);
                oldAsynchronousSocketChannel.close();
            }
            socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
            socketChannel.setOption(StandardSocketOptions.TCP_NODELAY, true);
            attachment.put(hostName, socketChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //为这个新的SocketChannel注册“read”事件，以便操作系统在收到数据并准备好后，主动通知应用程序
        //在这里，由于我们要将这个客户端多次传输的数据累加起来一起处理，所以我们将一个StringBuffer对象作为一个“附件”依附在这个Channel上
        ByteBuffer dst = ByteBuffer.allocate(2550);//申请一块缓冲区用于存贮读的流数据
        socketChannel.read(dst, new StringBuffer(), new SocketChannelReadHandle(socketChannel, dst));
    }

    @Override
    public void failed(Throwable exc, ConcurrentMap<String, AsynchronousSocketChannel> attachment) {
        //每次都要重新注册监听（一次注册，一次响应），但是由于“文件状态标示符”是独享的，所以不需要担心有“漏掉的”事件
        this.serverSocketChannel.accept(attachment, this);
        exc.printStackTrace();
    }
}

