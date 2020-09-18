package aio.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    //用于读取半包消息和发送应答
    private AsynchronousSocketChannel channel;
    private ByteBuffer                dst;

    public ReadCompletionHandler(AsynchronousSocketChannel channel, ByteBuffer dst) {
        this.channel = channel;
        this.dst     = dst;
    }

    //读取到消息后的处理,  result是本次channel从操作系统获取数据总长度
    @Override
    public void completed(Integer result, ByteBuffer buffer) {
        //flip操作 ,切换成“读模式”
        dst.flip();
        //根据
        buffer.put(dst);
        handleData(buffer);
        //异步读  第三个参数为接收消息回调的业务Handler
        System.out.println("start next read...");
        dst.compact();
        channel.read(dst, buffer, this);
    }

    public void handleData(ByteBuffer buffer){
        int position = buffer.position();
        System.out.println("position:" + position);
        if (position > 0){
            buffer.flip();
            int length = buffer.remaining();
            byte[] message = new byte[length];
            buffer.get(message);
            String expression = new String(message, StandardCharsets.UTF_8);
            System.out.println("data: " + expression);
            //向客户端发送消息
            doWrite(expression);
            buffer.compact();
        } else {
            System.out.println("need more data...");
        }
    }

    //发送消息
    private void doWrite(String result) {
        try {
            byte[] bytes = result.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            while (writeBuffer.hasRemaining()){
                //异步写数据 参数与前面的read一样
                Future<Integer> future = channel.write(writeBuffer);
                Integer integer = future.get();
                System.out.println(integer);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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
