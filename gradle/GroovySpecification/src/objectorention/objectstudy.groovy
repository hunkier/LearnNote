package objectorention

//def person = new Person(name: 'Qndroid', age: 26)
//println "the name is ${person.name}, the age is ${person.age}"
//person.increaseAge(10)
//println "the name is ${person.name}, the age is ${person.age}"
//
//println  person.cry()

// 使注入的属性全局可用
ExpandoMetaClass.enableGlobally()

// 为类动态的添加一个属性
Person.metaClass.sex = 'male'

def  person2 = new Person(name: 'Qndroid', age: 26)
println person2.sex
person2.sex = 'female'
println "the new sex is : ${person2.sex}"

// 为类动态添加方法
Person.metaClass.sexUpperCase = { -> sex.toUpperCase()}
def person3 = new Person(name: 'Qndroid', age: 26)
println person3.sexUpperCase()

// 为类动态添加静态方法
Person.metaClass.static.createPerson = {
    String name, int age -> new Person(name:name, age:age)
}
def person4 = Person.createPerson('liuqiangong', 26)
println person4.name + " and " + person4.age



