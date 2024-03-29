package cn.hunkier.zerocopy;

import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class OldServer {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8899);
        while (true){
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            try {
                byte[] byteArray = new byte[4096];
                while (true){
                    int readCount = dataInputStream.read(byteArray, 0, byteArray.length);
                    if(-1 == readCount){
                        break;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
