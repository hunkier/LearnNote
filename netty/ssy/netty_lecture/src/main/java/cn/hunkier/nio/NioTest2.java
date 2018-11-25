package cn.hunkier.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class NioTest2 {
    public static void main(String[] args) throws Exception {
        File file = new File("settings.gradle");
        log.info(file.getAbsolutePath());
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        fileChannel.read(byteBuffer);

        byteBuffer.flip();

        while (byteBuffer.remaining() > 0){
            byte b = byteBuffer.get();
            log.info("Character: " + (char)b);
        }

        fileInputStream.close();
    }
}
