package file

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import objectorention.Person


def list = [
        new Person(name: "John", age: 26),
        new Person(name: "Major", age: 27),
]

def json =  JsonOutput.toJson(list)
//println JsonOutput.prettyPrint(json)


//def jsonSlpuer = new JsonSlurper()
//jsonSlpuer.parse()

def repose = getNetworkData(
        'https://www.baidu.com/home/xman/data/tipspluslist'
)

println repose.errNo

def getNetworkData(String url){
    // 发送http请求
    def connnection = new URL(url).openConnection()
    connnection.setRequestMethod('GET')
    connnection.connect()
    def response = connnection.content.text

    // 将json转化为实体对象
    def jsonSluper = new JsonSlurper()
    return jsonSluper.parseText(response)

}