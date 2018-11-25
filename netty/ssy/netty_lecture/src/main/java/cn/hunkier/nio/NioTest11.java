package cn.hunkier.nio;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 *
 */
@Slf4j
public class NioTest11 {
    public static void main(String[] args) throws Exception{

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);
        serverSocketChannel.bind(address);
        int messageLength = 2 + 3 + 4;
        ByteBuffer[] buffers = new ByteBuffer[]{
                ByteBuffer.allocate(2),
                ByteBuffer.allocate(3),
                ByteBuffer.allocate(4),
        };



        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true){
            int bytesRead = 0 ;

            while (bytesRead < messageLength){
                long r = socketChannel.read(buffers);
                bytesRead += r;

                log.info("byteRead: " + bytesRead);

                Arrays.asList(buffers).stream()
                        .map(buffer -> "postion: " + buffer.position() + ", limit: " + buffer.limit())
                        .forEach(log::info);
            }

            Arrays.asList(buffers).forEach(ByteBuffer::flip);

            long bytesWritten = 0 ;
            while (bytesWritten < messageLength){
                long r = socketChannel.write(buffers);
                bytesWritten += r;
            }

            Arrays.asList(buffers).forEach(ByteBuffer::clear);

            log.info("bytesRead: " + bytesRead + ", bytesWritten: " + bytesWritten);
        }

    }
}
