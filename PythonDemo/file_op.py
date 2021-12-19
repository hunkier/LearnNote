# 将小说的主要人物记录在文件中
# file1 = open('name.txt', 'w')
# file1.write('诸葛亮')
# # file1.flush()
# file1.close()
# print(file1)
#
# file2 = open('name.txt')
# print(file2.read())
# file2.close()
#
# file3 = open('name.txt','a')
# file3.write('刘备')
# file3.close()
#
# file4 = open('name.txt')
# print(file4.read())
# file4.close()
#
# file5 = open('name.txt')
# for line in file5.readlines():
#     print(line)
#     print('=====')


file6 = open('name.txt')
print('当前文件指针的位置 %s ' %file6.tell())
print('当前读到了一个字符，字符内容是 %s ' %file6.read(1))
print('当前文件指针的位置 %s ' %file6.tell())

# 第一个参数代表偏移的位置，第二个参数 0 表示从文件头开始偏移  1 表示从当前位置偏移 2 从文件尾开始偏移
file6.seek(5,0)
print('我们进行了 seek 操作')

print('当前文件指针的位置 %s ' %file6.tell())
print('当前读到了一个字符，字符内容是 %s ' %file6.read(1))
print('当前文件指针的位置 %s ' %file6.tell())