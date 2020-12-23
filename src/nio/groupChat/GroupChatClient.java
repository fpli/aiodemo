package nio.groupChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

public class GroupChatClient {

    private static final String HOST = "127.0.0.1";
    private static final int    PORT = 6667;

    private Selector selector;
    private SocketChannel socketChannel;
    private String userName;

    public GroupChatClient() throws IOException {
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        userName = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(userName + " is ok ");
    }

    private void sendInfo(String info){
        info = userName + " say:" + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readData(){
        try {
            while (true){
                int count = selector.select();
                if (count > 0){
                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    while (keyIterator.hasNext()){
                        SelectionKey selectionKey = keyIterator.next();
                        if (selectionKey.isReadable()){
                            SocketChannel channel = (SocketChannel) selectionKey.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            channel.read(buffer);
                            String msg = new String(buffer.array());
                            System.out.println(msg.trim());
                        }
                    }
                    keyIterator.remove();
                } else {
                    System.out.println("no channel to use");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        GroupChatClient chatClient = new GroupChatClient();

        new Thread(){

            @Override
            public void run() {
                chatClient.readData();
            }
        }.start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            System.out.println(line);
            chatClient.sendInfo(line.trim());
        }
    }
}
