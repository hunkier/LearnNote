package cn.hunkier.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

@Slf4j
public class NioTest13 {
    public static void main(String[] args) throws Exception{
        String inputFile = "settings.gradle";
        String outputFile = "out/NioTest13_out.txt";
        File file = new File(outputFile);
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        file.createNewFile();

        RandomAccessFile inputRandomAccessFile = new RandomAccessFile(inputFile, "r");
        RandomAccessFile outputRandomAccessFile = new RandomAccessFile(outputFile, "rw");

        long inputLength = new File(inputFile).length();

        FileChannel inputFileChannel = inputRandomAccessFile.getChannel();
        FileChannel outputFileChannel = outputRandomAccessFile.getChannel();
        MappedByteBuffer inputData = inputFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, inputLength);

        log.info("-----------------------------------");
        Charset.availableCharsets().forEach((k, v)->{
            log.info(k + ", " + v);
        });
        log.info("-----------------------------------");

        Charset charset = Charset.forName("utf-8");
        CharsetDecoder decoder = charset.newDecoder();
        CharsetEncoder encoder = charset.newEncoder();

        CharBuffer charBuffer = decoder.decode(inputData);
        ByteBuffer outputData = encoder.encode(charBuffer);
        outputFileChannel.write(outputData);
        inputRandomAccessFile.close();
        outputRandomAccessFile.close();

    }
}
