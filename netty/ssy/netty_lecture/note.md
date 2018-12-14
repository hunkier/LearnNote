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
3. Event Handler（事件处理器）：本身由多个回调方法构成，这些方法构成了与应用相关的对于某个事件







