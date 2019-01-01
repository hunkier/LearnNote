netty官网：https://netty.io/

Gradle官网：https://gradle.org/

使用gradle管理编译项目

查看端口占用

```shell
lsof -i:8899
```
protobuf idea 插件
http://plugins.jetbrains.com/pluginManager/?action=download&id=io.protostuff.protostuff-jetbrains-plugin&build=IU-182.4892.20&uuid=c0bdfe7b-4595-4ed8-92ee-d8489abbc1d0

git submodule: Git仓库里的一个仓库

git subtree

mac homebrew 包管理



#### Reactor模式的角色构成（Reactor模式一共有5种角色构成）：
1. **Handle（句柄或是描述符）**：本质上表示一种资源，是由操作系统提供的；改资源用于表示一个个的事件，比如说文件描述符，或是针对网络编程过程中的Socket描述符。事件即可以来自于外部，可以来自于内部；外部事件比如说客户端的连接请求，客户端发送过来的数据等；内部事件，比如说操作系统产生的定时器事件等。它本质上就是一个文件描述符。
2. **Synchronous Event Demultiplexer（同步事件分离器**）：它本身是一个系统调用，用于等待事件的发生（事件可能是一个，也可能是多个）。调用方在调用它的时候会被阻塞，一直阻塞到同步事件分离器上有事件产生为止。对于Linux来说，同步事件分离器指的就是常用的I/O多路复用机制，比如说select、poll、epoll等。在Java NIO领域中，同步事件分离器对应的组件就是Slector；对应的阻塞方法就是select方法。
3. **Event Handler（事件处理器）**：本身由多个回调方法构成，这些方法构成了与应用相关的对于某个事件的反馈机制。Netty相比于Java NIO来说，在事件处理器这个角色上进行了一个升级，它为我们开发者提供了大量的回调方法，供我们在特定事件产生时实现相应的回调方法进行业务逻辑的处理。
4. **Contrete Event Handler（具体事件处理器）**：是事件处理器的实现。它本身实现了事件处理器所提供的各个回调方法，从而实现了特定于业务的逻辑。它本质上就是我们所编写的一个个的处理器实现。
5. **Initiation Dispatcher（初始分发器）**：实际上就是Reactor角色。它本身定义了一些规范，这些规范用于控制事件的调度方式，同时又提供了应用进行事件处理器的注册、删除等设施。它本身是整个事件处理器的核心所在，Initiation Dispatcher会通过同步事件分离器来等待事件的发生。一旦事件发生，Initiation Dispatcher首先会分离出每一个事件，然后调用事件处理器，最后调用相关的回调方法来处理这些事件。



------------------

Reactor模式流程

1. 当应用向Initiation Dispatcher注册具体的事件处理器时，应用会标识出该事件处理器希望Initiation Dispatcher在某个事件发生时向其通知该事件，该事件与Handle关联。
2. Initiation Dispatcher会要求每个事件处理器向其传递内部的Handle。该Handler向操作系统标识了事件处理器。
3. 当所有的事件处理器注册完毕后，应用会调用handle_events方法来启动Initiation Dispatcher的事件循环。这时，Initiation Dispatcher会将每个注册的事件管理的handle合并起来，并使用同步事件分离器等待这些事件的发生。比如说，TCP协议层会使用select同步事件分离器操作来等待客户端发送的数据到达连接的socket handle上。
4. 当与某个事件源对应的Handle变为ready状态时（比如说，TCP socket变为等待读状态时），同步事件分离器就会通知Initiation Dispatcher。
5. Initiation Dispatcher会触发事件处理器的回调方法，从而响应这个处于ready状态的Handle。当事件发生时，Initiation Dispatcher会将被事件源激活的Handle[key]来寻找并分发恰当的事件处理器回调方法。
6. Initiation Dispatcher会回调事件处理器的handle_event回调方法来执行特定于应用的功能（开发者自己所编写的功能），从而响应这个事件。所发生的事件类型可以作为该方法参数并被该方法内部使用来执行额外的特定于服务的分类和分发。



#### EventLoop

1. 一个EventLoopGroup当中会包含一个或多个EventLoop。

2. 一个EventLoop在它的整个生命周期当中都只会与唯一一个Thread进行绑定。
3. 所有由EventLoop所处理的各种I/O事件都将在它所关联的那个Thread上进行处理。
4. 一个Channel在它的整个生命周期中只会注册在一个EventLoop上。
5. 一个EventLoop在运行过程当中，会被分配给一个或多个Channel。



**重要结论**：在Netty中，Channel的实现一定是线程安全的；基于此，我们可以存储一个Channel的引用，并且在需要向远程端点发送数据时，通过引用来调用Channel相应的方法；即便当时有很多线程都在使用它也不会出现多线程问题；而且，消息一定会按照顺序发送出去。

**重要结论**：我们在业务开发中，不要将长时间执行的耗时任务放入到EventLoop的执行队列中，因为它讲会一直阻塞该线程所对应的所有Channel上的其他执行任务，如果我们需要进行阻塞调用或者是耗时的操作（实际开发中很常见），那么我们就需要使用一个专门的EventExecutor（业务线程池）。

通常会有两种实现方式：

1. 在ChannelHandler的回调方法中，使用自己定义的业务线程池，这样就可以实现异步调用。

2. 借助于Netty提供的ChannelPipeline添加ChannelHandler时调用addLast方法来传递EventExecutor。

   说明：默认情况下（调用addLast(handler)），ChannelHandler中的回调方法都是由I/O线程所执行，如果调用了ChannelHandler addLast(EventExecutorGroup group, ChannelHandler... handlers);方法，那么ChannelHandler中的回调方法就是由参数中的group线程组来执行的。



JDK所提供的Future只能通过手工的方式检查执行结果，而这个操作是会阻塞的；Netty则对ChannelFuture进行了增强，通过ChannelFutureListener以回调的方式来获取执行结果，去除了手工检查阻塞的操作；值得注意的是：ChannelFutureListener的operationComplete方法是由I/O线程执行的，因此要注意的是不要在这里执行耗时的操作，否则需要通过另外的线程或线程池来执行。

==============

在Netty中有两种发送消息的方式，可以直接写到Channel中，也可以写到与ChannelHandler所关联的那个ChannelHandlerContext中。对于前一种方式来说，消息会从ChannelPipeline的末尾开始流动；对于后一种方式来说，消息将从ChannelPipline中的下一ChannelHandler开始流动。



结论：

1. ChannelHandlerContext与ChannelHandler之间的关联关系是永远都不会发生改变的，因此对其进行缓存是没有任何问题的。
2. 对于与Channel的同名的方法来说，ChannelHandlerContext的方法将会产生更短的事件流，所以我们应该在可能的情况下利用这个特性提升应用性能。

=============

使用NIO进行文件读取所涉及的步骤：

1. 从FileInputStream对象获取到Channel对象。
2. 创建Buffer。
3. 将数据从Channel中读取到Buffer对象中。

0 <= mark <= postion <= limit <= capacity

flip()方法。

1. 将limit值设置为当前position。
2. 将position设为0。

clear()方法。

1. 将limit值设为capacity。
2. 将position值设为0。

compact()方法。

1. 将所有未读的数据复制到buffer起始位置处。
2. 将postion设为最后一个未读元素的后面。
3. 将limit设为capacity。
4. 现在buffer就准备好了，但是不会覆盖未读的数据。

**Java NIO中，关于DirectBuffer，HeapBuffer的疑问？** https://www.zhihu.com/question/57374068











