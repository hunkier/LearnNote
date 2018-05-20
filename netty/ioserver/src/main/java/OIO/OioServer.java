package OIO;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 传统socket服务器
 */
public class OioServer {

    public static void main(String[] args) throws  Exception{
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

        // 创建socket服务，监听10101端口
        ServerSocket server = new ServerSocket(10101);

        System.out.println("服务器启动");

        while (true){
            // 获取一个套接字
            Socket socket = server.accept();
            System.out.println("来了一个客户端！");
            newCachedThreadPool.execute(()->handler(socket));
        }
    }

    /**
     * 业务处理
     * @param socket
     */
    private static void handler(Socket socket) {
        InputStream inputStream ;
        try{
            inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            while (true){
                // 读数据（阻塞）
                int read = inputStream.read(bytes);
                if (read!=-1){
                    System.out.println(new String(bytes, 0 ,read));
                }else {
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                System.out.println("socket关闭");
                socket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
