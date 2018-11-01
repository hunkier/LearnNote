package variable

def x = 1.23
def result
switch (x){
    case 'foo':
        result = 'found foo'
        break
    case 'boo':
        result = 'bar'
        break
    case [4,5,6,'inlist']:// 列表
        result = 'list'
        break
    case 12..30:// 范围
        result = 'range'
        break
    case Integer:
        result = 'integer'
        break
    case BigDecimal:
        result = 'big decimal'
        break
    default: result = 'default'
}
println result

// 对范围的for循环
def sum = 0
for(i in 0..9){
    sum +=i
}
println sum

// 对List的循环
sum = 0
for(i in [1,2,3,4,5,6,7,8,9]){
    sum += i;
}

// 对Map的循环
sum = 0
for(i in ['lili':1, 'luck':2, 'xiaoming':3]){sum+=i.value}
println sum