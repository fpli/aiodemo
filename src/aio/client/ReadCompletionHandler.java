package aio.client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

/**
 * 读取操作完成处理器
 */
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
	
	private AsynchronousSocketChannel clientChannel;
	private ByteBuffer dst;
	
	public ReadCompletionHandler(AsynchronousSocketChannel clientChannel, ByteBuffer dst) {
		this.clientChannel = clientChannel;
		this.dst = dst;
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
		dst.flip();
		byte[] bytes = new byte[dst.remaining()];
		dst.get(bytes);
		String body = new String(bytes, StandardCharsets.UTF_8);
		System.out.println("客户端收到结果:"+ body);
		System.out.println("start next read...");
		dst.compact();
		clientChannel.read(dst, attachment, this);
	}
	
	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		System.err.println("数据读取失败...");
		try {
			clientChannel.close();
		} catch (IOException e) {
			
		}
	}
}

