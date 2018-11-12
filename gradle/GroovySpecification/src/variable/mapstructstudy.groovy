package variable

def  colors = [red: 'ff000000',
               green: '00ff0000',
               blue: '0000ff00',
    ]
// 索引方式
//println colors['red']
//println colors.red

// 添加元素
colors.yellow = 'ffff00'
colors.complex = [a:1, b:2]
//println colors.toMapString()
//println colors.getClass()
//println colors.class

/**
 *  Map操作详解
 */
def students = [
        1: [number: '0001', name: 'Bob', score: 55, sex: 'male'],
        2: [number: '0002', name: 'Johnny', score: 62, sex: 'female'],
        3: [number: '0003', name: 'Claire', score: 73, sex: 'female'],
        4: [number: '0004', name: 'Amy', score: 66, sex: 'male'],
]

// 遍历
students.each { def student ->
//    println "the key is ${student.key}, " + " the value is ${student.value}"
}

// 带索引的遍历
students.eachWithIndex{ def student , int index ->
//    println "index is ${index}, the key is ${student.key}, " + " the value is ${student.value}"
}

students.each { key, value ->
//    println "the key is ${key}, " + " the value is ${value}"
}

students.eachWithIndex { key, value, index ->
//    println "index is ${index}, the key is ${key}, " + " the value is ${value}"
}

// Map的查找
def entry = students.find { def student ->
    return student.value.score > 60
}

//println entry
def entrys = students.findAll { def student ->
    return student.value.score > 60
}
//println entrys

def count = students.count { def student ->
    return student.value.score >=60 && student.value.sex == 'male'
}
//println count

def names = students.findAll {def student ->
    return student.value.score > 60
}.collect {
    return it.value.name
}
//println names.toListString()

def group = students.groupBy { def student ->
    return student.value.score >=60 ? '及格' : '不及格'
}
//println group.toMapString()

/**
 * 排序
 */
def  sort = students.sort{ def stu1 , def stu2 ->
    Number score1 = stu1.value.score
    Number score2 = stu2.value.score
    return score1 == score2 ? 0 : score1 < score2 ? -1 : 1
}
//println sort.toMapString()