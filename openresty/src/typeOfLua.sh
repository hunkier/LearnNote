# Lua 中的数据类型不多，你可以通过 type 函数来返回一个值的类型，比如下面这样的操作：
echo '--- type ----'
resty -e '
print(type("Hello World!"))
print(type(print))
print(type(true))
print(type(360.0))
print(type{})
print(type(nil))
'

echo '----  字符串 ------'
#字符串
#在 Lua 中，字符串是不可变的值，如果你要修改某个字符串，就等于创建了一个新的字符串。这种做法显然有利有弊：好处是即使同一个字符串出现了很多次，在内存中也只有一份；但劣势也很明显，如果你想修改、拼接字符串，会额外地创建很多不必要的字符串。
#
#我们举一个例子，来说明这个弊端。下面这段代码，是把 1 到 10 这些数字当作字符串拼接起来。对了，在 Lua 中，我们使用两个点号来表示字符串的相加：

resty -e 'local s  = ""
 for i = 1, 10 do
     s = s .. tostring(i)
 end
 print(s)'


#另外，在 Lua 中，你有三种方式可以表达一个字符串：单引号、双引号，以及长括号（[[]]）。前面两种都比较好理解，别的语言一般也这么用，那么长括号有什么用处呢？
resty -e 'print([[string has \n and \r]])'

# 段字符串中包括了长括号本身，又该怎么处理呢？答案很简单，就是在长括号中间增加一个或者多个 = 符号：
resty -e 'print([=[ string has a [[]]. ]=])'

echo '---- 布尔值-----'
#
#布尔值
#这个很简单，true 和 false。但在 Lua 中，只有 nil 和 false 为假，其他都为真，包括 0 和空字符串也为真。我们可以用下面的代码印证一下：
resty -e 'local a = 0
 if a then
   print("true")
 end
 a = ""
 if a then
   print("true")
 end'

# 这种判断方式和很多常见的开发语言并不一致，所以，为了避免在这种问题上出错，你可以显式地写明比较的对象，比如下面这样：
 resty -e 'local a = 0
 if a == false then
   print("true")
 end
 '

echo '----- 数字 -----'
# 数字
#Lua 的 number 类型，是用双精度浮点数来实现的。值得一提的是，LuaJIT 支持 dual-number（双数）模式，也就是说， LuaJIT 会根据上下文来用整型来存储整数，而用双精度浮点数来存放浮点数。
#
#此外，LuaJIT 还支持长长整型的大整数，比如下面的例子：
resty -e 'print(9223372036854775807LL - 1)'