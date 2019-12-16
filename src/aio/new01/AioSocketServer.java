package aio.new01;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 在Java NIO(Reactor模式)框架中，我们说到了一个重要概念“selector”（选择器）。
 * 它负责代替应用查询中所有已注册的通道到操作系统中进行IO事件轮询、管理当前注册的通道集合，定位发生事件的通道等操操作；
 * 但是在Java AIO框架中，由于应用程序不是“轮询”方式(轮询就绪事件)，而是订阅-通知方式，所以不再需要“selector”（选择器）了，改由channel通道直接到操作系统注册监听。
 * Java AIO(Proactor模式)框架中，只实现了两种网络IO通道AsynchronousServerSocketChannel(服务器监听通道),AsynchronousSocketChannel(socket套接字通道)。
 * 但是无论哪种通道他们都有独立的FileDescriptor(文件标识符),attachment(附件,附件可以是任意对象,类似“通道上下文”)
 * 并被独立的SocketChannelReadHandle类实例引用。
 * @author C5287463
 */
public class AioSocketServer {

    private static final Object waitObject = new Object();
    private static final ConcurrentMap<String, AsynchronousSocketChannel> globalMap = new ConcurrentHashMap<>();
    
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
    	
        /**
         * 对于使用的线程池技术，我一定要多说几句
         * 1、Executors是线程池生成工具,通过这个工具我们可以很轻松的生成“固定大小的线程池”,“调度池”,“可伸缩线程数量的池”。具体请看API Doc
         * 2、当然您也可以通过ThreadPoolExecutor直接生成线程池。
         * 3、这个线程池是用来得到操作系统的“IO事件通知”的,不是用来进行“得到IO数据后的业务处理的”。要进行后者的操作,您可以再使用一个池（最好不要混用）
         * 4、您也可以不使用线程池（不推荐）,如果决定不使用线程池,直接AsynchronousServerSocketChannel.open()就行了。
         * */
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(threadPool);
        final AsynchronousServerSocketChannel serverSocket = AsynchronousServerSocketChannel.open(group);

        //设置要监听的端口“0.0.0.0”代表本机所有IP设备
        serverSocket.bind(new InetSocketAddress("0.0.0.0", 8083));
        //为AsynchronousServerSocketChannel注册监听，注意只是为AsynchronousServerSocketChannel通道注册监听
        //并不包括为 随后客户端和服务器 SocketChannel通道注册的监听
        serverSocket.accept(globalMap, new AcceptSocketChannelCompletionHandler(serverSocket));

        //等待,以便观察现象（这个和要讲解的原理本身没有任何关系，只是为了保证守护线程不会退出）
        synchronized(waitObject) {
            waitObject.wait();
        }
    }
    
}

