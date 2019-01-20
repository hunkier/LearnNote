package cn.hunkier.netty.handler3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class MyClientHandler extends SimpleChannelInboundHandler<PersonProtocol> {
    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            String messageToBeSend = "send from clent";
            byte[] content = messageToBeSend.getBytes(Charset.forName("utf-8"));
            int length = content.length;

            PersonProtocol personProtocol = new PersonProtocol();
            personProtocol.setLength(length);
            personProtocol.setContent(content);

            ctx.writeAndFlush(personProtocol);

        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        log.info("客户端接收到的消息：");
        log.info("长度：" + length);
        log.info("内容：" + new String(content,Charset.forName("utf-8")));

        System.out.println("客户端接收到的消息数量：" + (++this.count));
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
