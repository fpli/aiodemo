package aio.new00;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * AsynchronousServerSocketChannel
 */
public class AIOServer implements Runnable {

    private final int port;
    private final int threadSize;
    protected AsynchronousChannelGroup asynchronousChannelGroup;

    protected AsynchronousServerSocketChannel serverChannel;

    public AIOServer(int port, int threadSize) {
        this.port = port;
        this.threadSize = threadSize;
        init();
    }

    private void init() {
        try {
            asynchronousChannelGroup = AsynchronousChannelGroup.withCachedThreadPool(Executors.newFixedThreadPool(5), threadSize);
            serverChannel = AsynchronousServerSocketChannel.open(asynchronousChannelGroup);
            serverChannel.bind(new InetSocketAddress(port));
            System.out.println("listening on port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            if (serverChannel == null) {
                return;
            }
            // 注册服务器端接受完成事件
            serverChannel.accept(this, new CompletionHandler<>() {
                // attachment <==> AIOServer.this
                @Override
                public void completed(final AsynchronousSocketChannel channel, AIOServer attachment) {
                    System.out.println("==============================================================");
                    System.out.println("server process begin ...");
                    try {
                        System.out.println("client host: " + channel.getRemoteAddress().toString());
                        System.out.println("local host: " + channel.getLocalAddress());
                        new Thread(() -> {
                            while (true) {
                                try {
                                    final ByteBuffer echoBuffer = ByteBuffer.allocateDirect(1024);
                                    channel.read(echoBuffer).get();
                                    echoBuffer.flip();
                                    System.out.println("received : " + Charset.defaultCharset().decode(echoBuffer));
                                    TimeUnit.SECONDS.sleep(2);
                                } catch (InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                        int random = ThreadLocalRandom.current().nextInt(2);
                        System.out.println("server deal request execute: " + random + "s");

                        for (int count = 0; count < 50; count++) {
                            String msg = "服务器推送的数据时间间隔:" + random + "秒";
                            System.out.println(msg);
                            Integer sentCount = channel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8))).get();
                            System.out.println("it has sent count is " + sentCount);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        //服务端已经接收客户端成功了，为什么还要调用accept方法？因为一个AsynchronousServerSocketChannel可以接收成千上万个客户端
                        //当调用asynchronousServerSocketChannel.accept(this, new AcceptCompletionHandler())方法后，又有新的
                        //客户端连接接入，所以需要继续调用他的accept方法，接受其它客户端的接入，最终形成一个循环
                        attachment.serverChannel.accept(attachment, this);
                    }

                }

                @Override
                public void failed(Throwable exc, AIOServer attachment) {
                    System.out.println("received failed");
                    exc.printStackTrace();
                    attachment.serverChannel.accept(attachment, this);
                }

            });
            //System.in.read();//保证守护线程不会退出
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new AIOServer(8989, 2)).start();
    }

}
