package com.hunk.nio;

import lombok.extern.slf4j.Slf4j;

import java.nio.IntBuffer;
import java.security.SecureRandom;

@Slf4j
public class NioTest1 {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);

        log.info("capacity: " + buffer.capacity());

        for (int i = 0; i < 5; i++) {
            int randomNumber = new SecureRandom().nextInt(20);
            buffer.put(randomNumber);
        }

        log.info("before flip limit: " + buffer.limit());

        // Buffer的读写状态切换
        buffer.flip();

        log.info("after flip limit: " + buffer.limit());

        log.info("enter loop");



        while (buffer.hasRemaining()){
            log.info("position: " + buffer.position() + "  limit: " + buffer.limit() + " capacity: " + buffer.capacity());
            log.info(buffer.get()+"");
        }

    }
}
