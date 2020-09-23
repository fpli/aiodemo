package aio.new00;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * AsynchronousSocketChannel
 */
public class AIOClient implements Runnable {

    private AsynchronousChannelGroup group;   //异步通道组 封装处理异步通道的网络IO操作
    private final String host;
    private final int port;

    public AIOClient(String host, int port) {
        this.host = host;
        this.port = port;
        initGroup();
    }

    private void initGroup() {
        if (group == null) {
            try {
                group = AsynchronousChannelGroup.withCachedThreadPool(Executors.newFixedThreadPool(2), 5); //使用固定线程池实例化组
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void send() {
        try {
            //异步流式socket通道 open方法创建 并绑定到组 group
            final AsynchronousSocketChannel client  = AsynchronousSocketChannel.open(group);
            final AsynchronousSocketChannel client2 = AsynchronousSocketChannel.open(group);
            //注册客户端连接完成事件
            client.connect(new InetSocketAddress(host, port), null, new CompletionHandler<>() {

                @Override
                public void completed(Void result, Object attachment) {
                    String msg = "client1 test msg-" + Math.random();
                    client.write(ByteBuffer.wrap(msg.getBytes()));
                    System.out.println(Thread.currentThread().getName() + " client send data:" + msg);
                    // AIO 中应用程序需要传递缓冲区， OS负责数据读取， 应用程序关注完成事件(OS读取数据完成事件)
                    final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    // 注册读取完成事件
                    client.read(byteBuffer, this, new CompletionHandler<Integer, Object>() {

                        @Override
                        public void completed(Integer result, Object attachment) {
                            System.out.println(result == byteBuffer.position());
                            byteBuffer.flip();// 切换到读模式(channel的read操作对应buffer的写操作, 所以需要切换模式)
                            System.out.println(Thread.currentThread().getName() + " client read data: " + new String(byteBuffer.array()));
                            try {
                                byteBuffer.clear();//切换到写模式
                                client.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failed(Throwable exc, Object attachment) {
                            System.out.println("read faield");
                            exc.printStackTrace();
                        }

                    });
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("client send field...");
                    exc.printStackTrace();
                }

            });

            //连接2
            client2.connect(new InetSocketAddress(host, port), null, new CompletionHandler<>() {

                @Override
                public void completed(Void result, Object attachment) {
                    String msg = "client2 test msg-" + Math.random();
                    client2.write(ByteBuffer.wrap(msg.getBytes()));
                    System.out.println(Thread.currentThread().getName() + " client send data:" + msg);

                    final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    client2.read(byteBuffer, this, new CompletionHandler<Integer, Object>() {

                        @Override
                        public void completed(Integer result, Object attachment) {
                            System.out.println(Thread.currentThread().getName() + " client read data: " + new String(byteBuffer.array()));
                            try {
                                byteBuffer.clear();
                                client.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failed(Throwable exc, Object attachment) {
                            System.out.println("read faield");
                            exc.printStackTrace();
                        }

                    });
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("client send field...");
                    exc.printStackTrace();
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            send();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        group.awaitTermination(10000, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        try {
            new Thread(new AIOClient("127.0.0.1", 8989)).start();
            //System.in.read();//阻塞主线程，保证JVM不会退出
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}