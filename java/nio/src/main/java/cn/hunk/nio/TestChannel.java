package cn.hunk.nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.SortedMap;

/**
 * 一、通道 （Channel）：用于源节点与目标节点的连接。在 Java NIO 中负责缓冲区中数据的传输。 Channel 本身不存储数据，因此需要配合缓冲区进行传输
 *
 * 二、通道 （Channel）的主要实现类
 *     java.nio.channel.Channel
 *          |--FileChannel
 *          |--SocketChannel
 *          |--ServerSocketChannel
 *          |--DatagramChannel
 *
 * 三、获取通道
 *     1. Java 针对通道的类提供了 getChannel() 方法
 *          本地 IO：
 *          FileInputString/FileOutputStream
 *          RandomAccessFile
 *
 *          网络 IO：
 *          Socket
 *          ServerSocket
 *          DatagramSocket
 *
 *     2. 在 JDK 1.7 中的 NIO.2 针对各个通道提供了静态方法 open()
 *     3. 在 JDK 1.7 中的 NIO.2 的 Files 工具类的 newByteChannel()
 *
 *  四、通道之间的数据传输
 *      transferFrom()
 *      transferTo()
 *
 *  五、分散（Scatter） 与聚集（Gather）
 *      分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中
 *      聚集写入（Gathering Writes）：将多个缓冲区中的数据聚集到通道中
 *
 * 六、字符集：Charset
 *      编码：字符串 -> 字节数组
 *      解码：字节数组 -> 字符串
 *
 *
 *
 */
public class TestChannel {


    /**
     * 字符集
     */
    @Test
    public void test6()throws  Exception{
        Charset gbk = Charset.forName("GBK");

        // 获取编码器
        CharsetEncoder ce = gbk.newEncoder();

        // 获取解码器
        CharsetDecoder cd = gbk.newDecoder();

        CharBuffer cBuf = CharBuffer.allocate(1024);
        cBuf.put("编码解码");
        cBuf.flip();

        // 编码
        ByteBuffer bBuf = ce.encode(cBuf);

        for (int i = 0; i < 8; i++) {
            System.out.println(bBuf.get());
        }

        // 解码
        bBuf.flip();
        CharBuffer  cBuf2 = cd.decode(bBuf);
        System.out.println(cBuf2.toString());

        System.out.println("--------------------------------");

//        Charset cs = Charset.forName("GBK");
        Charset cs = Charset.forName("UTF-8");
        bBuf.flip();
        CharBuffer cBuf3 = cs.decode(bBuf);
        System.out.println(cBuf3.toString());

    }



    @Test
    public void test5(){

        SortedMap<String, Charset> charsets = Charset.availableCharsets();
        for (Map.Entry<String, Charset> entry : charsets.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    /**
     * 分散和聚集
     */
    @Test
    public void test4(){


        try (

                RandomAccessFile inFile = new RandomAccessFile("1.txt", "rw");
                RandomAccessFile outFile = new RandomAccessFile("target/2.txt", "rw");
                // 1.获取通道
                FileChannel inChannel = inFile.getChannel();
                FileChannel outChannel = outFile.getChannel();
                ){

            // 2.分配指定大小的缓冲区
            ByteBuffer[] bufs = {ByteBuffer.allocate(100), ByteBuffer.allocate(1024)};

            inChannel.read(bufs);

            for (ByteBuffer buf : bufs) {
                buf.flip();
            }

            System.out.println(new String(bufs[0].array(),0,bufs[0].limit()));
            System.out.println("---------------------------------------------------");
            System.out.println(new String(bufs[1].array(),0,bufs[1].limit()));

            // 4.聚集写入
            outChannel.write(bufs);



        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 通道之间的数据传输（直接缓冲区）
     */
    @Test
    public void test3(){
        Instant start = Instant.now();

        try(
                FileChannel inChannel = FileChannel.open(Paths.get("d:/1.mp4"), StandardOpenOption.READ);
                FileChannel outChannel = FileChannel.open(Paths.get("d:/2.mp4"),StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE)
        ) {
//          inChannel.transferTo(0,inChannel.size(),outChannel);
          outChannel.transferFrom(inChannel,0,inChannel.size());


        }catch (Exception e){
            e.printStackTrace();
        }


        Instant end = Instant.now();
        System.out.println("耗费时间为： " + Duration.between(start,end).toMillis());
    }


    /**
     * 利用缓冲区完成文件的复制（内存映射文件）
     */
    @Test
    public void test2(){
        Instant start = Instant.now();

        try(
                FileChannel inChannel = FileChannel.open(Paths.get("d:/1.mp4"), StandardOpenOption.READ);
                FileChannel outChannel = FileChannel.open(Paths.get("d:/2.mp4"),StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE)
                ) {
            // 内存映射文件
            MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            MappedByteBuffer outMapperBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

            // 直接对缓冲区区进行数据的读写操作
            byte[] dst = new byte[inMappedBuf.limit()];
            inMappedBuf.get(dst);
            outMapperBuf.put(dst);

        }catch (Exception e){
            e.printStackTrace();
        }




        Instant end = Instant.now();
        System.out.println("耗费时间为： " + Duration.between(start,end).toMillis());

    }


    /**
     * 使用通道完成文件的复制（非直接缓冲区）
     */
    @Test
    public void test1(){

        Instant start = Instant.now();

        try(// JDK 8 新特性，可自动释放资源
                FileInputStream fis = new FileInputStream("d:/1.mp4");
                FileOutputStream fos = new FileOutputStream("d:/2.mp4");
                // ①获取通道
                FileChannel inChannel = fis.getChannel();
                FileChannel outChannel = fos.getChannel();
        ){

            // ②分配指定大小的缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);

            // ③将通道中的数据存入缓冲区中
            while(inChannel.read(buf)!=-1){
                buf.flip(); // 切换到读取数据的模式
                // ④将缓冲区数据写入通道中
                outChannel.write(buf);
                buf.clear(); // 清空缓冲区
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        Instant end = Instant.now();
        System.out.println("耗费时间： " + Duration.between(start,end).toMillis());

    }

}
