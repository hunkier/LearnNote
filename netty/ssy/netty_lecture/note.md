netty官网：https://netty.io/

Gradle官网：https://gradle.org/

使用gradle管理编译项目

linux查看端口占用

```shell
lsof -i:8899
```

windows下查看端口占用，使用netstat -anob|find "本机IP:端口"  命令，可以列出使用这个端口的进程号

```
netstat -anob|find "127.0.0.1:8080"
netstat -anob|findstr 8080
```

然后使用tasklist |findstr 进程号查找进程

```
tasklist | findstr 5544
```

如果不需要这个进程，可以用taskkill /pid 5544 终止进程

```
taskkill /pid 5544 /f
```

