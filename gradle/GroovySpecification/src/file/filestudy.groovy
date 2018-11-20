package file

import groovy.json.JsonOutput
import objectorention.Person


def file = new File('../../GroovySpecification.iml')
file.eachLine { line->
    // println line
}

def text = file.getText()
//println text


//def result = file.readLines()
// 读取文件部分内容
def reader = file.withReader { reader ->
    char[] buffer = new char[100]
    reader.read(buffer)
    return buffer
}
//println reader
def result = copy('../../GroovySpecification.iml','../../GroovySpecification.iml2')
//println result
def copy(String sourcePath, String destationPath){
    try {
        // 首先创建目标文件
        def desFile = new File(destationPath)
        if (!desFile.exists()){
            desFile.createNewFile()
        }
        // 开始copy
        new File(sourcePath).withReader {reader ->
            def lines = reader.readLines()
            desFile.withWriter { writer ->
                lines.each { line ->
                    writer.append(line + "\r\n")
                }
            }
        }
        return  true
    }catch(Exception e){
        e.printStackTrace()
    }
}

def personFile = '../../out/person.bin'
def person = new Person(name: 'hunk', age: 28)
saveObject(person, personFile)
def  person2 = (Person)readObject(personFile)
println JsonOutput.toJson(person2)

// 保存对象到文件
def saveObject(Object object, String path){
    try {
        // 首先创建目标文件
        def desFile = new File(path)
        if (!desFile.exists()){
            desFile.createNewFile()
        }
        desFile.withObjectOutputStream { out->
            out.writeObject(object)
        }
        return  true
    }catch(Exception e){
        e.printStackTrace()
    }
}

// 从文件中读取对象
def readObject(String path){
    def obj = null
    try {
        def file = new File(path)
        if (file == null || !file.exists()) return  null
        // 从文件中读取
        file.withObjectInputStream { input ->
            obj = input.readObject()
        }
    }catch(Exception e){
        e.printStackTrace()
    }
    return obj
}