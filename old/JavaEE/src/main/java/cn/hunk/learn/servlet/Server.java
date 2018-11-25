package cn.hunk.learn.servlet;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

/**
 * Created by dell on 2015/7/30.
 * socket服务器程序
 */
public class Server {
    public static void main(String[] args) throws Exception{
        // 1.创建ServerSocket
        ServerSocket server = new ServerSocket(8088);
        System.out.println("服务器启动成功啦...");
        while (true){
            // 2.接收客户端连接
            Socket socket = server.accept();
            System.out.println(socket.getRemoteSocketAddress().toString());

            URL resource = Server.class.getResource("/com/hunk/learn/xml/contact.xml");
            String str = resource.getPath();
            System.out.println(str);
            // 3.读取本地文件
            InputStream inputStream = resource.openStream();

            // 4.构建数据输出通道
            OutputStream out = socket.getOutputStream();

            // 5.发送数据
            byte[] buf = new byte[1024];
            int len = 0 ;
            while ((len=inputStream.read(buf))!=-1){
                out.write(buf, 0, len);
            }

            // 6.关闭资源
            out.close();
            inputStream.close();

        }

    }

}
