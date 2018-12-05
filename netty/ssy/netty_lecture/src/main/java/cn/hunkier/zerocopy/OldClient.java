package cn.hunkier.zerocopy;

import lombok.extern.slf4j.Slf4j;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.Socket;

@Slf4j
public class OldClient {
    public static void main(String[] args)throws Exception {
        Socket socket = new Socket("localhost", 8899);
        String fileName = "E:/Downloads/WebStorm-2018.2.7.zip";
        FileInputStream inputStream = new FileInputStream(fileName);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[1024];
        long readCount ;
        long total = 0;

        long startTime = System.currentTimeMillis();
        while ((readCount = inputStream.read(buffer)) >=0 ){
            total += readCount;
            dataOutputStream.write(buffer);
        }

        log.info("发送总字节数： " + total + ", 耗时： " + (System.currentTimeMillis() - startTime));
        dataOutputStream.close();
        socket.close();
    }
}
