package aio.client;

import java.util.Scanner;

public class Client {
	
	private static final String DEFAULT_HOST = "127.0.0.1";
	private static final int DEFAULT_PORT = 12345;
	private static AsyncClientHandler clientHandle;
	
	public static synchronized void start(String ip, int port){
		if (clientHandle != null){
			return;
		}
		clientHandle = new AsyncClientHandler(ip, port);
		new Thread(clientHandle, "Client").start();
	}
	
	public static void main(String[] args) throws Exception{
		start(DEFAULT_HOST, DEFAULT_PORT);
		System.out.println("请输入请求消息：");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String msg = scanner.nextLine();
		while(!msg.equals("quit")){
			//向服务器发送消息
			clientHandle.sendMsg(msg);
			msg = scanner.nextLine();
		};
	}

	
}
