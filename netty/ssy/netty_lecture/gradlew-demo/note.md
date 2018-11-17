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

查看gradle的Sha256Sum

```powershell
G:\Users\hunk\.gradle\wrapper\dists\gradle-3.4-all\4bi7dnjj1pmknw0wphqavp2sz>Sha256Sum gradle-3.4-all.zip
37c2fdce55411e4c89b896c292cae1f8f437862c8433c8a74cfc3805d7670c0a *gradle-3.4-all.zip

```

得到的sha256sum值写入项目的文件`/gradle/wrapper/gradle-wrapper.properties`

```properties
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-3.4-all.zip
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
distributionSha256Sum=37c2fdce55411e4c89b896c292cae1f8f437862c8433c8a74cfc3805d7670c0a

```

