package cn.hunkier.netty.handler;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyLongToBytesEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        log.info("encode invoked!");

        System.out.println(msg);
        out.writeLong(msg);
    }

}
