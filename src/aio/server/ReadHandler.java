package aio.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

public class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {

    //用于读取半包消息和发送应答
    private AsynchronousSocketChannel channel;

    public ReadHandler(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    //读取到消息后的处理,  result是本次channel从操作系统获取数据总长度
    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        //flip操作 ,切换成“读模式”
        attachment.flip();
        //根据
        byte[] message = new byte[attachment.remaining()];
        attachment.get(message);
        String expression = new String(message, StandardCharsets.UTF_8);
        System.out.println("服务器收到消息: " + expression);
        String calrResult = expression;
        //向客户端发送消息
        doWrite(calrResult);
    }

    //发送消息
    private void doWrite(String result) {
        byte[] bytes = result.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        //异步写数据 参数与前面的read一样
        channel.write(writeBuffer, writeBuffer, new CompletionHandler<>() {

            @Override
            public void completed(Integer result, ByteBuffer buffer) {
                //如果没有发送完，就继续发送直到完成
                if (buffer.hasRemaining()) {
                    channel.write(buffer, buffer, this);
                } else {
                    //创建新的Buffer
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    //异步读  第三个参数为接收消息回调的业务Handler
                    channel.read(readBuffer, readBuffer, new ReadHandler(channel));
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    channel.close();
                } catch (IOException e) {

                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
