package aio.new00;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * AsynchronousServerSocketChannel
 */
public class AIOServer implements Runnable {

    private int port;
    private int threadSize;
    protected AsynchronousChannelGroup asynchronousChannelGroup;

    protected AsynchronousServerSocketChannel serverChannel;

    public AIOServer(int port, int threadSize) {
        this.port = port;
        this.threadSize = threadSize;
        init();
    }

    private void init() {
        try {
            asynchronousChannelGroup = AsynchronousChannelGroup.withCachedThreadPool(Executors.newCachedThreadPool(), threadSize);
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

            serverChannel.accept(this, new CompletionHandler<AsynchronousSocketChannel, AIOServer>() {
                // attachment <==> AIOServer.this
                @Override
                public void completed(final AsynchronousSocketChannel channel, AIOServer attachment) {
                    System.out.println("==============================================================");
                    System.out.println("server process begin ...");
                    try {
                        System.out.println("client host: " + channel.getRemoteAddress().toString());
                        System.out.println("local host: " + channel.getLocalAddress());
                        new Thread() {

                            public void run() {
                                while (true) {
                                    try {
                                        final ByteBuffer echoBuffer = ByteBuffer.allocateDirect(1024);
                                        echoBuffer.clear();
                                        channel.read(echoBuffer).get();
                                        echoBuffer.flip();
                                        System.out.println("received : " + Charset.defaultCharset().decode(echoBuffer));
                                        Thread.sleep(2000);
                                    } catch (InterruptedException | ExecutionException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }.start();

                        int random = ThreadLocalRandom.current().nextInt(2);
                        System.out.println("server deal request execute: " + random + "s");

                        for (int count = 0; count < 50; count++) {
                            String msg = "服务器推送的数据时间间隔:" + random + "秒";
                            System.out.println(msg);
                            channel.write(ByteBuffer.wrap(msg.getBytes("UTF-8"))).get();
                            Thread.sleep(random * 1000);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } finally {
                        //服务端已经接收客户端成功了，为什么还要调用accept方法？因为一个channel可以接收成千上万个客户端
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
            System.in.read();//保证守护线程不会退出
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new Thread(new AIOServer(8989, 10)).start();
    }

}
