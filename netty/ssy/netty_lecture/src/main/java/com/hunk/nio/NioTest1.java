package com.hunk.nio;

import lombok.extern.slf4j.Slf4j;

import java.nio.IntBuffer;
import java.security.SecureRandom;

@Slf4j
public class NioTest1 {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            int randomNumber = new SecureRandom().nextInt(20);
            buffer.put(randomNumber);
        }

        // Buffer的读写状态切换
        buffer.flip();

        while (buffer.hasRemaining()){
            log.info(buffer.get()+"");
        }
    }
}
