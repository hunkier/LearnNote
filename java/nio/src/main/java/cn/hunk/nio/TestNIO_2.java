package cn.hunk.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;

public class TestNIO_2 {

    /**
     * 自动资源管理： 自动关闭实现 AutoCloseable 接口的资源
     */
    @Test
    public void test8(){
        try(
                FileChannel inChannel = FileChannel.open(Paths.get("1.txt"),StandardOpenOption.READ);
                FileChannel outChannel = FileChannel.open(Paths.get("target/2.txt"),StandardOpenOption.WRITE, StandardOpenOption.CREATE)
                ){
                    inChannel.transferTo(0,inChannel.size(), outChannel);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Files常用方法： 用于操作内容
     *      SeekableByteChannel newByteChannel(Path path, OpenOption...how) : 获取与指定文件的连接，how 指定打开方式
     *      DirectoryString newDirectoryStream(Path path) : 打开 path 指定的目录
     *      InputStream newInputStream(Path path, OpenOption...how) : 获取 InputStream 对象
     *      OutputStream newOutputStream(Path path, OpenOptio...how) : 获取 OutputStream 对象
     */
    @Test
    public void test7()throws  Exception{
        SeekableByteChannel newByteChannel = Files.newByteChannel(Paths.get("1.txt"), StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        newByteChannel.read(buffer);
        System.out.println(new String(buffer.array(),0, buffer.limit()));

        DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(Paths.get("/"));
        for (Path path : newDirectoryStream) {
            System.out.println(path);
        }
    }


    /**
     * Files常用方法： 用于判断
     *      boolean exists(Path path, LinkOption ...opts) : 判断文件是否存在
     *      boolean isDirectory(Path path, LinkOption ...opts) : 判断是否目录
     *      boolean isExecutable(Path path) : 判断是否执行文件
     *      boolean isHidden(Path pah) : 判断是否隐藏文件
     *      boolean isReadable(Path path) : 判断文件是否可读
     *      boolean isWriteable(Path path) : 判断文件是否可写
     *      boolean notExists(Path path, LinkOption ...opts) : 判断文件是否存在
     *      public static <A extends BasicFileAttributes> A readAttributes(Path path, Class<A> type, LinkOption...opts) : 获取与 path 指定文件相关属性。
     *
     */
    @Test
    public void test6()throws IOException {
        Path path = Paths.get("target/3.txt");
//        System.out.println(Files.exists(path, LinkOption.NOFOLLOW_LINKS));
        BasicFileAttributes readAttributes = Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
        System.out.println(readAttributes.creationTime());
        System.out.println(readAttributes.lastModifiedTime());

        DosFileAttributeView dosFileAttributeView = Files.getFileAttributeView(path, DosFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
        dosFileAttributeView.setHidden(false);
    }


    /**
     * Files常用方法：
     *      Path copy(Path src, Path dest, CopyOption ...how) : 文件复制
     *      Path createDirectory(Path path, FileAttribute<?> ...attr ) : 创建一个目录
     *      Path createFile(Path path, FileAttribute<?> ...attr) : 创建一个文件
     *      Path delete(Path path) : 删除一个文件
     *      Path move(Path src, Path dest, CopyOption ...how) : 将 src 移动到 dest  位置
     *      long size() : 返回 Path 指定文件的大小
     */
    @Test
    public void test5()throws  Exception{
        Path path1 = Paths.get("1.txt");
        Path path2 = Paths.get("target", "2.txt");
        Path path3 = Paths.get("target", "3.txt");

        Files.copy(path1,path2,StandardCopyOption.REPLACE_EXISTING);

        Files.move(path2,path3,StandardCopyOption.REPLACE_EXISTING);

    }

    @Test
    public void test4()throws  Exception{
        Path dir = Paths.get("target/test2");
//        Files.createDirectory(dir);
        System.out.println(dir.toAbsolutePath());

        Path file = Paths.get("target", "3.txt");
//        Files.createFile(file);
        Files.deleteIfExists(file);
    }


    @Test
    public void test3()throws  Exception{
        Path path = Paths.get("1.txt");
        Path path1 = Paths.get("2.txt");
        Files.copy(path, path1,StandardCopyOption.REPLACE_EXISTING);
    }


    /**
     * Paths 提供的 get() 方法获取 Path 对象：
     *      Path get(String first, String ... more) : 用于将多个字符串串连成路径。
     * Path 常用方法
     *      boolean endWith(String path) : 判断是否以 Path 路劲结束
     *      boolean startWith(String path) : 判断是否以 Path 路径开始
     *      boolean isAbsolute() : 判断是否绝对路径
     *      Path getFileName() : 返回与调用Path对象关联的文件名
     *      int getNameCount() ： 返回Path根目录后面元素的数量
     *      Path getParent() : 返回 Path 对象包含整个路径， 不包含 Path 对象指定的文件路径
     *      Path getRoot() : 返回调用 Path 对象的根路径
     *      Path resolve(Path p) : 将相对路径解析为绝对路径
     *      Path toAbsolutePath() : 作为绝对路径返回调用 Path 对象
     *      String toString() : 返回调用 Path 对象的字符串表示形式
     */
    @Test
    public void test2(){
        Path path = Paths.get("D:/Develop/workspace/learn/LearnNote/java/nio/1.txt");

        System.out.println(path.getParent());
        System.out.println(path.getRoot());

//        Path newPath = path.resolve("1.text");
//        System.out.println(newPath);

        Path path1 = Paths.get("1.txt");
        Path path2 = path1.toAbsolutePath();
        System.out.println(path2);

        System.out.println(path.toString());
    }


    @Test
    public void test1(){
        Path path = Paths.get("D:/", "Develop/workspace/learn/LearnNote/java/nio/1.txt");

        System.out.println(path.endsWith("1.txt"));
        System.out.println(path.startsWith("D:/"));

        System.out.println(path.isAbsolute());
        System.out.println(path.getFileName());

        for (int i = 0; i < path.getNameCount(); i++) {
            System.out.println(path.getName(i));
        }
    }


}
