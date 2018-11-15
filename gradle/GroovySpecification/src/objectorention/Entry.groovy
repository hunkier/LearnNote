package objectorention

class Entry {
    public static void main(String[] args) {
        println '应用程序正在启动'
        // 初始化
        ApplicationManager.init()
        println '应用初始化完成'

        def person = PersonManager.createPerson('liuqiangdong',  36)
        println "the person name is ${person.name}, the age is ${person.age}"

    }
}
