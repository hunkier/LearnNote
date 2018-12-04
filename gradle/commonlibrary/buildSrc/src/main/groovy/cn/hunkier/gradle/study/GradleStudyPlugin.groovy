package cn.hunkier.gradle.study

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 自定义插件
 */
class  GradleStudyPlugin implements Plugin<Project>{
    /**
     * 插件被引入时要执行的方法
     * @param project 引入当前插件的Project
     */
    @Override
    void apply(Project project) {
        println 'Helllo Plugin...' + project.name
    }
}