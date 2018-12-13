package cn.hunkier.test;

import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {
    public static void main(String[] args) {
        // netty NioEventLoopGroup 默认线程数，为cpu超线程数*2
        int DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt(
                "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        log.info("DEFAULT_EVENT_LOOP_THREADS: " + DEFAULT_EVENT_LOOP_THREADS);
    }
}
