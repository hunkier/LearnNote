package cn.hunkier.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class NioClient {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));

        while (true){
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            for (SelectionKey selectionKey : selectionKeys) {
                if (selectionKey.isConnectable()){
                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    if (client.isConnectionPending()){
                        client.finishConnect();
                        ByteBuffer writeBufer = ByteBuffer.allocate(1024);
                        writeBufer.put((LocalDateTime.now() + " 连接成功").getBytes());
                        writeBufer.flip();
                        client.write(writeBufer);

                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        executorService.submit(()->{
                            while (true){
                                try {
                                    writeBufer.clear();
                                    InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                                    BufferedReader br = new BufferedReader(inputStreamReader);

                                    String sendMessage = br.readLine();

                                    writeBufer.put(sendMessage.getBytes());
                                    writeBufer.flip();
                                    client.write(writeBufer);

                                }catch (Exception e){
                                    log.error("client error", e);
                                }
                            }
                        });
                    }

                    client.register(selector, SelectionKey.OP_READ);
                }else if(selectionKey.isReadable()){
                    SocketChannel client = (SocketChannel) selectionKey.channel();

                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);

                    int count = client.read(readBuffer);

                    if (count > 0 ){
                        String receivedMessage = new String(readBuffer.array());
                        log.info(receivedMessage);
                    }
                }
            }
            selectionKeys.clear();
        }
    }
}
