package com.hunk.nio;

import ch.qos.logback.core.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 内存映射文件
 */
@Slf4j
public class NioTest9 {
    public static void main(String[] args)throws  Exception {
        File file = new File("out/NioTest9.txt");
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if (!file.exists()){
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("HelloWorld!");
        fileWriter.flush();

        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0,(byte)'a');
        mappedByteBuffer.put(3,(byte)'b');

        randomAccessFile.close();
    }
}
