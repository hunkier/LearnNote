package com.hunk.grpc;

import com.hunk.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Iterator;

@Slf4j
public class GrpcClient {

    public static void main(String[] args) throws  Exception{
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8899).usePlaintext(true).build();
        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.newBlockingStub(managedChannel);
        StudentServiceGrpc.StudentServiceStub stub = StudentServiceGrpc.newStub(managedChannel);


        MyResponse myResponse = blockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("zhangsan").build());
        log.info(myResponse.getRealname());
/*
        for (int i = 0; i < 5; i++) {
            myResponse = blockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("zhangsan").build());
            log.info(myResponse.getRealname());
        }
        */
        log.info("-----------------------------------------------");
/*

        Iterator<StudentResponse> students = blockingStub.getStudentsByAge(StudentRequest.newBuilder().setAge(20).build());
        students.forEachRemaining(e->{
            log.info(e.getName());
            log.info(e.getAge()+"");
            log.info(e.getCity());
        });

        log.info("-----------------------------------------------");
        StreamObserver<StudentResponseList> studentResponseListStreamObserver = new StreamObserver<StudentResponseList>() {
            @Override
            public void onNext(StudentResponseList value) {
                value.getStudentResponseList().forEach(stu -> {
                    log.info(stu.getName());
                    log.info(stu.getAge()+"");
                    log.info(stu.getCity());
                });
            }

            @Override
            public void onError(Throwable t) {
                log.info(t.getMessage());
            }

            @Override
            public void onCompleted() {
                log.info("onCompleted !");
            }
        };

        StreamObserver<StudentRequest> studentRequestStreamObserver = stub.getStudentsWrapperByAges(studentResponseListStreamObserver);
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(20).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(30).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(40).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(50).build());
        studentRequestStreamObserver.onCompleted();

        StreamObserver<StreamRequest> requestStreamObserver = stub.biTalk(new StreamObserver<StreamResponse>() {
            @Override
            public void onNext(StreamResponse value) {
                log.info(value.getResponseInfo());
            }

            @Override
            public void onError(Throwable t) {
                log.error(t.getMessage());
            }

            @Override
            public void onCompleted() {
                log.info("onCompleted");
            }
        });

        for (int i = 0; i < 10; i++) {
            requestStreamObserver.onNext(StreamRequest.newBuilder().setRequestInfo(LocalDateTime.now().toString()).build());
            Thread.sleep(200);
        }
*/
        Thread.sleep(10000);

    }

}
