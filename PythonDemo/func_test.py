# print('abc', end='\n')
# print('abc')
#
# def func (a, b, c):
#     print('a= %s' %a)
#     print('b= %s' %b)
#     print('c= %s' %c)
#
# func(1, c=3, b=2)


# 取得参数的个数, * 开头表示可变参数
# def howlong(first, *other):
#     print(1 + len(other))
#
# howlong(123,2,3,4)


# 函数作用域
# val1 = 123
# def func():
#     global val1
#     val1 = 456
#     print(val1)
#
# func()
# print(val1)

# iter() next()
# list1 = [1, 2, 3]
# it = iter(list1)
# print(next(it))
# print(next(it))
# print(next(it))
# print(next(it)) # except

# for i in range(10, 20, 2):
#     print(i)

# def frange(start, stop, step):
#     x = start
#     while x < stop:
#         yield x
#         x += step
#
# for i in frange(10, 20, 0.5):
#     print(i)

# lambda  x: x <= (month, day)
# def func1(x):
#     return x <= (month, day)

# lambda item: item[1]
# def func2(item):
#     return item[1]


# filter() map() reduce() zip()

# a = [1, 2, 3, 4, 5, 6, 7, 8]
# list(filter(lambda x:x>2, a))
#
# map(lambda x:x,a)
# b = [4,5,6]
# list(map(lambda x,y:x+y, a,b))
#
# # 使用 reduce 时需要单独导入
# from functools import reduce
# reduce(lambda x,y:x+y,a,0)
#
# zip_obj = zip((1,2,3),(4,5,6))
# print(list(zip_obj))
#
# dicta  = {'a':'aa','b':'bb'}
# dictb = zip(dicta.values(),dicta.keys())
# print(list(dictb))

def func():
    a = 1
    b = 2
    return a+b

def sum(a):
    def add(b):
        return a+b
    return add
# add 函数名称或者函数的引用
# add() 函数的调用
num1 = func()

num2 = sum(2)
print(num2(4))
print(sum(1)(2))

# print(type(num1))
# print(type(num2))