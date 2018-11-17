package com.hunk.grpc;

import com.hunk.proto.MyRequest;
import com.hunk.proto.MyResponse;
import com.hunk.proto.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GrpcClient {

    public static void main(String[] args) throws  Exception{
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8899).usePlaintext(true).build();
        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.newBlockingStub(managedChannel);
        MyResponse myResponse = blockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("zhangsan").build());
        log.info(myResponse.getRealname());
        for (int i = 0; i < 5; i++) {
            myResponse = blockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("zhangsan").build());
            log.info(myResponse.getRealname());
        }
    }

}
