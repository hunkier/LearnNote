package com.hunk.nio;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

@Slf4j
public class NioTest5 {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);

        buffer.putInt(15);
        buffer.putLong(500000000L);
        buffer.putDouble(14.123456);
        buffer.putChar('你');
        buffer.putShort((short)2);
        buffer.putChar('我');

        buffer.flip();

        log.info("buffer:  " + buffer.getInt());
        log.info("buffer:  " + buffer.getLong());
        log.info("buffer:  " + buffer.getDouble());
        log.info("buffer:  " + buffer.getChar());
        log.info("buffer:  " + buffer.getShort());
        log.info("buffer:  " + buffer.getChar());

    }
}
