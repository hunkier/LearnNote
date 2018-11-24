package com.hunk.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 关于Buffer的Scattering与Gathering
 * 分散和集合
 */
@Slf4j
public class NioTest10 {
    public static void main(String[] args) throws Exception{
        File file = new File("out/NioTest11.txt");
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if (!file.exists()){
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("File lock!");
        fileWriter.flush();


        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        FileLock fileLock = fileChannel.lock(3, 6, true);
        log.info("valid: " + fileLock.isValid());
        log.info("lock type: " + fileLock.isShared());

        fileLock.release();

        randomAccessFile.close();


    }
}
