package cn.hunkier.netty.handler3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.UUID;

@Slf4j
public class MyServerHandler extends SimpleChannelInboundHandler<PersonProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        log.info("服务端接收到的数据：");
        log.info("长度：" + length);
        log.info("内容：" + new String(content, Charset.forName("utf-8")));

        System.out.println("服务器端接收到消息数量：" + (++this.count));

        String responseMessage = UUID.randomUUID().toString();
        byte[] responseContent = responseMessage.getBytes("utf-8");
        int responseLength = responseContent.length;

        PersonProtocol personProtocol = new PersonProtocol();
        personProtocol.setLength(responseLength);
        personProtocol.setContent(responseContent);

        ctx.writeAndFlush(personProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
