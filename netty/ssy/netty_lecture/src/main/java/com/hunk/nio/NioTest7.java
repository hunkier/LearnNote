package com.hunk.nio;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

/**
 * readonly buffer
 * 只读buffer，我们可以随时将一个普通Buffer调用asReadOnlyBuffer方法返回一个只读Buffer
 * 但不能将一个只读Buffer转换为读写Buffer
 */
@Slf4j
public class NioTest7 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        log.info(byteBuffer.getClass().getName());

        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte)i );
        }

        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();

        log.info(readOnlyBuffer.getClass().getName());
    }
}
