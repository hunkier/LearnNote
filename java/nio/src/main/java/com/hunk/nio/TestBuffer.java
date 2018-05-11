package com.hunk.nio;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * 一、缓冲区（Buffer）：zai Java NIO 中负责数据的存取。缓冲区就是数组。用于存储不同数据类型的数据
 *
 *  根据数据类型不同（boolean 除外），提供了相应类型的缓冲区：
 *  ByteBuffer
 *  CharBuffer
 *  ShortBuffer
 *  IntBuffer
 *  LongBuffer
 *  FloatBuffer
 *  DoubleBuffer
 *
 *  上述缓冲区的管理方式几乎一致，通过 allocate() 获取缓冲区
 *
 *  二、缓冲区存取数据的两个核心方法：
 *  put() : 存入数据到缓冲区
 *  get() : 获取缓冲区数据
 *
 *  三、缓冲区中的四个核心属性
 *  capacity ： 容量
 *  limit ： 界限，表示缓冲区中可以操作数据的大小。（limit 后数据不能进行读写）
 *  position ： 位置，表示缓冲区正在操作数据的位置
 *
 *  mark ： 标记，表示记录当前position的位置
 *
 *  0 <= mark <= postion <= limit <= capacity
 *
 *  四、直接hu缓冲区与非直接缓冲区：
 *  非直接缓冲区： 通过 allocate() 方法分配缓冲区，将缓冲区建立在JVM的内存中
 *  直接缓冲区： 通过 allocateDirect() 方法分配直接缓冲区，将缓冲区建立在物理内存中。可以提交效率
 *
 *
 */
public class TestBuffer {

    @Test
    public void test1(){
        String str = "abcde";

        // 1. 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        System.out.println("------------------- allocate -----------------");
        System.out.println("position : "+buf.position());
        System.out.println("limit : "+buf.limit());
        System.out.println("capacity : "+buf.capacity());

        // 2. 利用 put() 存入数据到缓冲区中
        buf.put(str.getBytes());

        System.out.println("------------------- put -----------------");
        System.out.println("position : "+buf.position());
        System.out.println("limit : "+buf.limit());
        System.out.println("capacity : "+buf.capacity());

        // 3.切换到读取模式
        buf.flip();

        System.out.println("------------------- flip -----------------");
        System.out.println("position : "+buf.position());
        System.out.println("limit : "+buf.limit());
        System.out.println("capacity : "+buf.capacity());

        // 4.利用 get() 读取缓冲区的数据
        byte[] dst = new byte[buf.limit()];
        buf.get(dst);
        System.out.println(new String(dst, 0, dst.length));


        System.out.println("------------------- get -----------------");
        System.out.println("position : "+buf.position());
        System.out.println("limit : "+buf.limit());
        System.out.println("capacity : "+buf.capacity());

        // 5. rewind() ： 可重复读
        buf.rewind();


        System.out.println("------------------- rewind -----------------");
        System.out.println("position : "+buf.position());
        System.out.println("limit : "+buf.limit());
        System.out.println("capacity : "+buf.capacity());

        // 6. clear() : 清空缓冲区。但是缓冲区中的数据依然存在，但是出于“被遗忘”状态
        buf.clear();

        System.out.println("------------------- clear -----------------");
        System.out.println("position : "+buf.position());
        System.out.println("limit : "+buf.limit());
        System.out.println("capacity : "+buf.capacity());

        System.out.println((char)buf.get());


    }
}
