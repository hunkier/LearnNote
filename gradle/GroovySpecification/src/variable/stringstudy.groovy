package variable

def thupleName = '''\
line one 
line two
line three
'''
//println thupleName
//println thupleName.class

def doubleName = "this is a common string"
//println doubleName.class

def name = 'Qndroid'

def sayHello = "Hello : ${name}"
//println sayHello
//println sayHello.class

def sum = "The sum of 2 and 3 equals ${2+3}"
//println sum

def result = echo(sum)
//println result
//println result.class

String echo(String message){
    return message
}

/* ====================字符串的方法==================== */
def str = "Hello groovy"
//println str.center(8)
//println str.center(8,'a')
//println str.padLeft(8, 'a')
//println str.padRight(8, 'a')

def str2 = 'Hello'
//println str > str2

//println str[0]
//println str[0..1]

//println str.minus(str2)
//println str - str2

//println str.reverse()
//println str.capitalize()
//println str.isNumber()
//println str.toInteger()