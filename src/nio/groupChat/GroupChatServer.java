package nio.groupChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel listenerChannel;

    private static final Integer PORT = 6667;

    public GroupChatServer() {
        try {
            selector = Selector.open();
            listenerChannel = ServerSocketChannel.open();
            listenerChannel.socket().bind(new InetSocketAddress(PORT));
            listenerChannel.configureBlocking(false);
            listenerChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listen(){
        try {
            while (true){
                int count = selector.select();
                if (count > 0){
                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    while (keyIterator.hasNext()){
                        SelectionKey selectionKey = keyIterator.next();

                        if (selectionKey.isAcceptable()){
                            SocketChannel socketChannel = listenerChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + " is online ");
                        }

                        if (selectionKey.isReadable()){
                            readData(selectionKey);
                        }

                        keyIterator.remove();
                    }
                } else {
                    System.out.println("continue waiting...");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                listenerChannel.close();
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readData(SelectionKey selectionKey){
        SocketChannel socketChannel = null;
        try {
            socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = socketChannel.read(buffer);
            if (count > 0){
                String msg = new String(buffer.array());
                System.out.println("from client:" + msg.trim());
                sendInfoToOtherClients(msg, socketChannel);
            }
        } catch (IOException e){
            e.printStackTrace();
            try {
                System.out.println(socketChannel.getRemoteAddress() + " is offline ");
                selectionKey.cancel();
                socketChannel.close();
            } catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }

    private void sendInfoToOtherClients(String msg, SocketChannel channel) throws IOException {
        System.out.println("server is dispatching");
        for (SelectionKey selectionKey : selector.keys()){
            SocketChannel client;
            SelectableChannel channel1 = selectionKey.channel();
            if (channel1 instanceof SocketChannel){
                client = (SocketChannel) channel1;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
                if (client != channel){
                    client.write(buffer);
                }
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer chatServer = new GroupChatServer();
        chatServer.listen();
    }
}
