package com.hunk.client;

import lombok.extern.slf4j.Slf4j;
import org.jboss.netty.channel.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 消息接收处理类
 */
@Slf4j
public class HiHandler extends SimpleChannelHandler {

    DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
    /**
     * 接收消息
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {


        String s = (String) e.getMessage();
        LocalDateTime now = LocalDateTime.now();
        log.info(dtf2.format(now)+"   client :"+ s);

        super.messageReceived(ctx, e);
    }

    /**
     * 捕获异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        log.info("exceptionCaught");
        super.exceptionCaught(ctx, e);
    }

    /**
     * 新连接
     */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        log.info("channelConnected");
        super.channelConnected(ctx, e);
    }

    /**
     * 必须是链接已经建立，关闭通道的时候才会触发
     */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        log.info("channelDisconnected");
        super.channelDisconnected(ctx, e);
    }

    /**
     * channel关闭的时候触发
     */
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        log.info("channelClosed");
        super.channelClosed(ctx, e);
    }
}
