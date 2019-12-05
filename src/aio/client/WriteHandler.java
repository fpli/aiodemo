package aio.client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class WriteHandler implements CompletionHandler<Integer, ByteBuffer> {
	
	private AsynchronousSocketChannel clientChannel;
	private CountDownLatch latch;
	
	public WriteHandler(AsynchronousSocketChannel clientChannel, CountDownLatch latch) {
		this.clientChannel = clientChannel;
		this.latch = latch;
	}
	
	/**
	 * 消息对象的请求对象id使用LongAdder生成 一个JVM单例使用
	 * 发送数据完成，把消息对象放入全局消息池，并通过消息对象阻塞消息用户发送线程
	 */
	@Override
	public void completed(Integer result, ByteBuffer buffer) {
		//完成全部数据的写入
		while (buffer.hasRemaining()) {
			clientChannel.write(buffer);
		}
	}
	
	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		System.err.println("数据发送失败...");
		try {
			clientChannel.close();
			latch.countDown();
		} catch (IOException e) {
		}
	}

}
