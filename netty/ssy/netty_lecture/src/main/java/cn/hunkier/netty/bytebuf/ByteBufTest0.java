package cn.hunkier.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ByteBufTest0 {
    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        for (int i = 0; i < buffer.capacity(); i++) {
            log.info("" + buffer.getByte(i));
        }
        for (int i = 0; i < buffer.capacity(); i++) {
            log.info("" + buffer.readByte());
        }
    }
}
