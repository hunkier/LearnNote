package cn.hunkier.netty.handler2;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        System.out.println(this);
        ChannelPipeline pipeline = ch.pipeline();
        pipeline
//                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4))
//                .addLast(new LengthFieldPrepender(4))
//                .addLast(new StringDecoder(CharsetUtil.UTF_8))
//                .addLast(new StringEncoder(CharsetUtil.UTF_8))
//                .addLast(new MyByteToLongDecoder())
//                .addLast(new MyByteToLongDecoder2())
//                .addLast(new MyLongToStringDecoder())
//                .addLast(new MyLongToBytesEncoder())
                .addLast(new MyServerHandler());
    }
}
