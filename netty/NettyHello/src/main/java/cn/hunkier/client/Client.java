package cn.hunkier.client;

import lombok.extern.slf4j.Slf4j;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * netty客户端入门
 */
@Slf4j
public class Client {
    public static void main(String[] args) {

        // 服务类
        ClientBootstrap bootstrap = new ClientBootstrap();

        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        // socket工厂
        bootstrap.setFactory(new NioClientSocketChannelFactory(boss,worker));

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
        ChannelFuture connect = bootstrap.connect(new InetSocketAddress("127.0.0.1", 8088));
        Channel channel = connect.getChannel();

        log.info("client start");

        Scanner scanner = new Scanner(System.in);
        while (true){
            log.info("请输入");
            channel.write(scanner.next());
        }


    }
}
