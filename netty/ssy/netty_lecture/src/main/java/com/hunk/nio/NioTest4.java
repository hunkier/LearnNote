package com.hunk.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class NioTest4 {
    public static void main(String[] args) throws  Exception{
        File outFile = new File("out/note.md");
        if (!outFile.getParentFile().exists()){
            outFile.getParentFile().mkdirs();
            outFile.createNewFile();
        }
        File inFile = new File("note.md");
        log.info(inFile.getAbsolutePath());
        FileInputStream fileInputStream = new FileInputStream(inFile);
        FileOutputStream fileOutputStream = new FileOutputStream(outFile);
        FileChannel inputStreamChannel = fileInputStream.getChannel();
        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(256);

        while (true){
            buffer.clear();

            int read = inputStreamChannel.read(buffer);

            log.info("read: " + read);

            if (-1 == read){
                break;
            }

            buffer.flip();

            outputStreamChannel.write(buffer);

        }

        inputStreamChannel.close();
        outputStreamChannel.close();


    }
}
