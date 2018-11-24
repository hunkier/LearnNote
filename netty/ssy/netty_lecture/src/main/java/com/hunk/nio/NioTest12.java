package com.hunk.nio;


import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class NioTest12 {

    public static void main(String[] args) throws Exception{
        int[] ports = new int[]{
                5000,
                5001,
                5002,
                5003,
                5004,
        };

        Selector selector = Selector.open();

        log.info(SelectorProvider.provider().getClass().getName());
        for (int i = 0; i < ports.length; i++) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            // 设置为非阻塞方式工作
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            InetSocketAddress address = new InetSocketAddress(ports[i]);
            serverSocket.bind(address);

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            log.info("监听端口： " + ports[i]);
        }

        while (true){
            int numbers = selector.select();
            log.info("numbers: " + numbers);

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            log.info("selectedKeys: " + selectionKeys);

            Iterator<SelectionKey> iter = selectionKeys.iterator();

            while (iter.hasNext()){
                SelectionKey selectionKey = iter.next();
                if (selectionKey.isAcceptable()){
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);

                    socketChannel.register(selector, SelectionKey.OP_READ);

                    iter.remove();

                    log.info("获得客户端连接： " + socketChannel);
                }else if (selectionKey.isReadable()){
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

                    int bytesRead = 0 ;

                    ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                    while (true){

                        byteBuffer.clear();

                        int read = socketChannel.read(byteBuffer);

                        if (read <=0){
                            break;
                        }

                        byteBuffer.flip();

                        socketChannel.write(byteBuffer);

                        bytesRead += read;
                    }

                    log.info("读取： " + bytesRead + ", 来自于： " + socketChannel);

                    iter.remove();
                }
            }

        }
    }
}
