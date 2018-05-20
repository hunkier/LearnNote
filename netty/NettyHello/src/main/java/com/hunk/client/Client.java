package com.hunk.client;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * netty客户端入门
 */
public class Client {
    public static void main(String[] args) {

        // 服务类
        ClientBootstrap bootstrap = new ClientBootstrap();

        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        // socket工厂
        bootstrap.setFactory(new NioServerSocketChannelFactory(boss,worker));

        // 管道工厂
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder",new StringDecoder());
                pipeline.addLast("encoder",new StringEncoder());
                pipeline.addLast("hiHandler", new HiHandler());
                return pipeline;
            }
        });

        // 链接服务端
        ChannelFuture connect = bootstrap.connect(new InetSocketAddress("127.0.0.1", 10101));
        Channel channel = connect.getChannel();

        System.out.println("client start");

        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("请输入");
            channel.write(scanner.next());
        }


    }
}
