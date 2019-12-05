package aio.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {
	
	private AsynchronousSocketChannel clientChannel;
	private CountDownLatch latch;
	
	public ReadHandler(AsynchronousSocketChannel clientChannel, CountDownLatch latch) {
		this.clientChannel = clientChannel;
		this.latch = latch;
	}
	
	/**
	 * 读取数据完成:根据读到的消息id，从全局消息池里取出消息对象，完成响应对象的设置，并通过消息对象唤醒发送线程
	 */
	@Override
	public void completed(Integer result, ByteBuffer attachment) {
		if (result == -1){// -1 表示对方断开了连接
			try {
				clientChannel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		attachment.flip();
		byte[] bytes = new byte[attachment.remaining()];
		attachment.get(bytes);
		String body;
		try {
			body = new String(bytes, "UTF-8");
			System.out.println("客户端收到结果:"+ body);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		clientChannel.read(attachment, attachment, this);
	}
	
	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		System.err.println("数据读取失败...");
		try {
			clientChannel.close();
			latch.countDown();
		} catch (IOException e) {
			
		}
	}
}

