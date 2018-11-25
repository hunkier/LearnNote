java.io
java.nio

java.io中最为核心的一个概念是流（Stream），面向流的编程。Java中，一个流要么是输入流，要么是输出流，不可能同时即是输入流又是输出流。

java.nio中拥有3个核心概念：Selector，Channel与Buffer。在java.nio中，我们是面向块（block）或缓冲区（buffer）编程的。Buffer本身就是一块内存，底层实现上，它实际上是个数组。数据的读、写都是通过Bufer来实现的。

除了数组之外，Buffer还提供了对数据的结构化访问方式，并且可以追踪到系统的读写过程。

Java中的7种原生数据类型都有各自的Buffer类型，如IntBuffer，LongBuffer，ByteBuffer及CharBuffer等，并没有BooleanBuffer类型。

Channel指的是可以向其写入数据或是从中读取数据的对象，它类似java.io中的Stream。

所有数据的读写都是通过Buffer来进行的，永远不会出现直接向channel写入数据的情况，或是直接从channel读取数据的情况。

与Stream不同的是，Channel是双向的，一个流只可能是InputStream或是OutputStream，Channel打开后则可以进行读取、写入或是读写。

由于Channel是双向的，因此它能更好地反应出底层操作系统的真实情况；在Linux系统中，底层 操作系统的通道就是双向的。

关于NIO Buffer中的3个重要状态属性的含义：position，limit与capacity。

0 <= mark <= postion <= limit <= capacity

通过NIO读取文件涉及的3个步骤

1.从FileInputStream获取到FileChannel对象

2.创建Buffer

3.将数据从channel读取到Buffer中



绝对方法与相对方法的含义

1.相对方法：limit值与position值会在操作时被考虑到。

2.绝对方法：完全忽略limit值和position值。





