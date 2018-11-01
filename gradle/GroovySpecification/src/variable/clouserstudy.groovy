package variable

def clouser = { String name, int age -> println "Hello ${name}! Age is ${age}"}
clouser.call('groovy', 20)
def name = 'hunk'
clouser(name,2)

// 隐式参数
clouser = { println "Hello ${it}"}
def r1 = clouser(name)
//println r1

clouser = { String n1 ->
    return "Hello ${n1}"
}
def result = clouser('l123456')
//println result

// 用来求指定number的阶乘
int fab(int number){
    int result = 1
    1.upto(number, {num -> result *= num})
    return result
}

int fab2(int number){
    int result = 1
    number.downto(1){
        num ->result *= num
    }
    return result
}

int cal(int number){
    int result =0
    number.times {
        num -> result+=num
    }
    return result
}

int x = fab(5)
//println x
int y = fab2(5)
//println y
int z = cal(101)
//println z

// 字符串与闭包的结合使用

