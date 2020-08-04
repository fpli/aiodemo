package aio.new01;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * 负责对每一个SocketChannel的数据获取事件进行监听。<p>
 * 
 * 重要的说明：一个SocketChannel都会有唯一的一个独立工作的SocketChannelReadHandle对象（CompletionHandler接口的实现），
 * 其中又都将独享一个“文件状态标示”对象FileDescriptor、
 * 一个独立的由程序员定义的Buffer缓存（这里我们使用的是ByteBuffer）、
 * 所以不用担心在服务器端会出现“窜对象”这种情况，因为Java AIO框架已经帮您组织好了。<p>
 * 
 * 但是最重要的,用于生成Channel的对象：AsynchronousChannelProvider是单例模式,无论在哪组SocketChannel，
 * 都是一个对象引用（但这没关系,因为您不会直接操作这个AsynchronousChannelProvider对象）。
 */
public class SocketChannelReadHandle implements CompletionHandler<Integer, StringBuffer> {

    private AsynchronousSocketChannel socketChannel;

    /**
     * 专门用于进行这个通道数据缓存操作的ByteBuffer<br>
     * 当然，您也可以作为CompletionHandler的attachment形式传入。<br>
     * 但是在这段示例代码中,attachment被我们用来记录所有传送过来的StringBuffer了。
     */
    private ByteBuffer dst;

    public SocketChannelReadHandle(AsynchronousSocketChannel socketChannel, ByteBuffer dst) {
        this.socketChannel = socketChannel;
        this.dst = dst;
    }

    @Override
    public void completed(Integer result, StringBuffer historyContext) {
        //如果条件成立，说明客户端主动终止了TCP套接字，这时服务端关闭客户端SocketChannel就可以了
        if (result == -1) {
            try {
                this.socketChannel.close();
            } catch (IOException e) {
                
            }
            return;
        }

        System.out.print("completed(Integer result, Void attachment) : 然后我们来取出通道中准备好的值");
        /*
         * 实际上，由于我们从Integer result知道了本次Channel从操作系统获取数据总长度
         * 所以实际上，我们不需要切换成“读模式”的，但是为了保证编码的规范性，还是建议进行切换。
         * 
         * 另外，无论是JAVA AIO框架还是JAVA NIO框架，都会出现“buffer的总容量”小于“当前从操作系统获取到的总数据量”即粘包问题
         * 但区别是，JAVA AIO框架中，我们不需要专门考虑处理这样的情况，因为JAVA AIO框架已经帮我们做了处理（做成了多次通知）
         * */
        byte[] contexts = new byte[result.intValue()];
        this.dst.flip();//flip是为了用户程序读作准备(数据从dst取出)
        this.dst.get(contexts, 0, result.intValue());
        try {
            String nowContent = new String(contexts , 0, result.intValue(), "UTF-8");
            historyContext.append(nowContent);
            System.out.print("================目前的传输结果：" + historyContext);
        } catch (UnsupportedEncodingException e) {
            
        }

        //=========================================================================
        //          以“over”符号作为客户端完整信息的标记
        //=========================================================================
        //如果条件成立，说明接收到“结束标记”
        if (historyContext.indexOf("over") != -1) {
            //======================================================
            //          当然接受完成后，可以在这里正式处理业务了        
            //======================================================
            System.out.print("=======收到完整信息，开始处理业务=========");
            System.out.print("客户端发来的信息======message : " + historyContext);

            //根据具体业务需要具体处理,这里直接回发数据
            ByteBuffer src = null;
            try {
                src = ByteBuffer.wrap(URLEncoder.encode("业务处理完后的数据", "UTF-8").getBytes());
                do {
                	socketChannel.write(src);
                } while (src.hasRemaining());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        dst.clear();// clear是为了SocketChannel写作准备(等待发生数据从内核取出放入dst)
        socketChannel.read(dst, historyContext, this);//读完一次，再继注册，如此便可(多次通知)
    }

    @Override
    public void failed(Throwable exc, StringBuffer historyContext) {
    	System.out.print("=====发现客户端异常关闭，服务器将关闭TCP通道");
        try {
            this.socketChannel.close();
        } catch (IOException e) {
            
        }
    }
    
}

