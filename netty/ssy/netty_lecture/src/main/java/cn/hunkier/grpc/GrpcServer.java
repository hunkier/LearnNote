package cn.hunkier.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class GrpcServer {
    private Server server;

    private void start()throws IOException{
      this.server = ServerBuilder.forPort(8899).addService(new StudentServiceImpl()).build().start();
      log.info("server started");
      Runtime.getRuntime().addShutdownHook(new Thread(()->{
          log.info("关闭JVM");
          System.out.println("关闭JVM");
          GrpcServer.this.stop();
      }));
      log.info("执行到这里");
    }

    private void stop(){
        if (null!=this.server){
            this.server.shutdown();
        }
    }

    private void awaitTermination() throws InterruptedException{
        if (null!=this.server){
            this.server.awaitTermination();
//            this.server.awaitTermination(3000, TimeUnit.MICROSECONDS);
        }
    }

    public static void main(String[] args) throws Exception{
        GrpcServer grpcServer = new GrpcServer();
        grpcServer.start();
        grpcServer.awaitTermination();
    }

}
