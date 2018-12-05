package cn.hunkier.gradle.study

/**
 * 与自定义Plugin进行参数传递
 */
class ReleaseInfoExtension {
    String versionCode
    String versionName
    String versionInfo
    String fileName


    @Override
     String toString() {
        /*
        final StringBuilder sb = new StringBuilder("ReleaseInfoExtension{");
        sb.append("versionCode='").append(versionCode).append('\'');
        sb.append(", versionName='").append(versionName).append('\'');
        sb.append(", versionInfo='").append(versionInfo).append('\'');
        sb.append(", fileName='").append(fileName).append('\'');
        sb.append('}');
        return sb.toString();
        */
        """| versionCode = ${versionCode}
        | versionName = ${versionName}
        | versionInfo = ${versionInfo}
        | fileName = ${fileName}
        """.stripMargin()
    }
}
