package aio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class ClientConnectCompletionHandler implements CompletionHandler<Void, ClientConnectCompletionHandler>, Runnable {
	
	private AsynchronousSocketChannel clientChannel;
	private String host;
	private int port;
	private TransferQueue<String> transferQueue = new LinkedTransferQueue();
	
	public ClientConnectCompletionHandler(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void init(){
		try {
			//创建异步的客户端通道
			clientChannel = AsynchronousSocketChannel.open();
			clientChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
			SocketAddress remote = new InetSocketAddress(host, port);
			clientChannel.connect(remote, this, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			while (true){
				String msg = transferQueue.take();
				System.out.println("take a message from transferQueue");
				Future<Integer> future = clientChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
				System.out.println(future.get());
				System.out.println("send data to server..");
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	//连接服务器成功
	//意味着TCP三次握手完成
	@Override
	public void completed(Void result, ClientConnectCompletionHandler attachment) {
		System.out.println("客户端成功连接到服务器...更改全局连接状态标识,准备接收服务端推送的数据");
		ByteBuffer dst = ByteBuffer.allocate(1024);
		ByteBuffer buffer = ByteBuffer.allocate(1024 * 8);
		clientChannel.read(dst, buffer, new ReadCompletionHandler(clientChannel, dst));
	}

	@Override
	public void failed(Throwable exc, ClientConnectCompletionHandler attachment) {
		System.err.println("连接服务器失败...");
		exc.printStackTrace();
		try {
			clientChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//向服务器发送消息
	public void sendMsg(String msg) throws InterruptedException {
		System.out.println("put a message to transferQueue");
		transferQueue.put(msg);
	}

}
