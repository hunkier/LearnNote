package cn.hunkier.zerocopy;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

@Slf4j
public class NewIOClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8899));
        socketChannel.configureBlocking(true);
        String fileName = "E:/Downloads/WebStorm-2018.2.7.zip";
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long startTime = System.currentTimeMillis();

        long total = fileChannel.size();
        fileChannel.transferTo(0, total, socketChannel);
        log.info("发送总字节数： " + total + ", 耗时： " + (System.currentTimeMillis() - startTime));

        fileChannel.close();
    }
}
