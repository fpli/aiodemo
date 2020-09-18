package aio.client;

import java.util.Scanner;

public class Client {

    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 12345;
    private static ClientConnectCompletionHandler clientHandle;

    public static synchronized void start(String ip, int port) {
        if (clientHandle != null) {
            return;
        }
        clientHandle = new ClientConnectCompletionHandler(ip, port);
        clientHandle.init();
        new Thread(clientHandle, "Client").start();
    }

    public static void main(String[] args) throws Exception {
        start(DEFAULT_HOST, DEFAULT_PORT);
        System.out.println("type message to server：");
        Scanner scanner = new Scanner(System.in);
        while (true){
            String msg = scanner.nextLine().trim();
            System.out.println(msg.equals("quit"));
            if (msg.equals("quit")){
                System.exit(0);
            } else {
                //向服务器发送消息
                clientHandle.sendMsg(msg);
            }
        }


    }


}
