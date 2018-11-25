package cn.hunkier.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * NIO服务器
 */
public class NIOServer {

    // 通道管理器
    private Selector selector;


    /**
     * 获取一个ServerSocket通道，并对该通道做一些初始化的工作
     * @param port
     *          绑定的端口
     * @throws IOException
     */
    public void initServer(int port)throws IOException{
        // 获取一个ServerSocket通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        // 设置通道为非阻塞
        serverChannel.configureBlocking(false);
        // 将该通道对应的ServerSocket绑定到port端口
        serverChannel.socket().bind(new InetSocketAddress(port));
        // 获取一个通道管理器
        this.selector = Selector.open();
        // 将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_ACCEPT事件，注册该事件后，
        // 当该事件到达时，selector.select()会返回，如果该事件没到达，selector.select()会一直阻塞。
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    }


    /**
     * 采用轮询的方式监听selector上是否有需要处理的事件，如果有，则进行处理
     * @throws IOException
     */
    public void listen()throws IOException{
        System.out.println("服务器启动成功");
        // 轮询访问selector
        while(true){
            // 当注册的事件到达时，方法返回；否则，该方法会一直阻塞
            selector.select();
            // 获得selector中选中的项的迭代器，选中的项为注册的事件
            Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                // 删除已选的key，以防重复处理
               iterator.remove();
               handler(key);
            }
        }
    }

    /**
     * 处理请求
     * @param key
     */
    public void handler(SelectionKey key)throws IOException {
        // 客户端请求连接事件
        if (key.isAcceptable()){
            handlerAccept(key);
            // 获得了可读事件
        }else if(key.isReadable()){
            handlerRead(key);
        }
    }
    /**
     * 处理连接请求
     * @param key
     * @throws IOException
     */
    public void handlerAccept(SelectionKey key) throws IOException{
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        // 获取和客户端的连接通道
        SocketChannel channell = server.accept();
        // 设置成非阻塞
        channell.configureBlocking(false);

        // 在这里可以给客户端发送信息
        System.out.println("新的客户端连接");
        // 在和客户端连接成功后，为了可以接收到客户端的信息，需要给通道设置读的权限。
        channell.register(selector,SelectionKey.OP_READ);

    }



    /**
     * 处理读的事件
     * @param key
     * @throws IOException
     */
    public void handlerRead(SelectionKey key)throws IOException {
        // 服务器可读取消息：得到事件发生的Socket通道
        SocketChannel channel = (SocketChannel) key.channel();
        // 创建读的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int read = channel.read(buffer);
        if (read > 0) {
            byte[] bytes = buffer.array();
            String msg = new String(bytes,"gbk").trim();
            System.out.println(msg);

            // 回写输入数据
            ByteBuffer outBuffer = ByteBuffer.wrap("好的".getBytes("gbk"));
            channel.write(outBuffer);
        }else {
            System.out.println("客户端关闭");
            key.cancel();
        }

    }


    /**
     * 启动服务端测试
     * @param args
     */
    public static void main(String[] args) throws IOException {

        NIOServer server = new NIOServer();
        server.initServer(8000);
        server.listen();

    }

}
