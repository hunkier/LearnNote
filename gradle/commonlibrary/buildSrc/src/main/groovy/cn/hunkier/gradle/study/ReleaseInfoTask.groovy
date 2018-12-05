package cn.hunkier.gradle.study

import groovy.xml.MarkupBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * 自定义Task，实现维护版本信息
 */
class ReleaseInfoTask extends DefaultTask {
    ReleaseInfoTask (){
        group = 'hunkier'
        description = 'update the release info'
    }

    /**
     * 执行于gradle执行阶段的代码
     */
    @TaskAction
    void doAction(){
        updateInfo()
    }

    // 真正的将Extension类中的信息，写入指定文件中
    private void updateInfo(){
        // 获取将要写入的信息
        String versionCodeMsg = project.extensions.hunkierReleaseInfo.versionCode
        String versionNameMsg= project.extensions.hunkierReleaseInfo.versionName
        String versionInfoMsg = project.extensions.hunkierReleaseInfo.versionInfo
        String fileName = project.extensions.hunkierReleaseInfo.fileName


        def file = project.file(fileName)
        // 将实体对象写入到xml文件中
        def sw = new StringWriter()
        def xmlBuilder = new MarkupBuilder(sw)
        if (file.text !=null && file.size() <=0){
            // 文件中没有内容
            xmlBuilder.release {
                release {
                    versionCode( versionMsg.versionCode)
                    versionName( versionMsg.versionName)
                    versionInfo( versionMsg.versionInfo)
                }
            }
            file.withWriter {writer -> writer.append(sw.toString())}
        }else{
            // 已有版本信息
            xmlBuilder.release {
                versionCode( versionMsg.versionCode)
                versionName( versionMsg.versionName)
                versionInfo( versionMsg.versionInfo)
            }

            // 将生成的xml数据插入到根节点结束前
            def lines = file.readLines()
            def lengths = lines.size() -1
            file.withWriter('UTF-8') {writer ->
                lines.eachWithIndex { String line, int index ->
                    if (index !=lengths){
                        writer.append(line + '\r\n')
                    }else if(index == lengths){
                        writer.append('\r\n' + sw.toString() + '\r\n')
                        writer.append(lines.get(lengths))
                    }
                }
            }
        }
    }
}
