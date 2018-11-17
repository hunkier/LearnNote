Gradlew 学习笔记

新建无gradlew的gradle项目

在项目根目录下执行

```shell
gradle wraaper
```

将会在gradle项目根目录下生成gradlew相关文件

指定gradle 版本

```shell
gradle wrapper --gradle-version 3.5
```

或者在build.gradle文件中指定gradle版本

```groovy
task wrapper(type: Wrapper){
    gradleVersion = '3.4'
    distributionType = 'all'
}

```

