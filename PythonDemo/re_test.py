# 日常应用比较广泛的模块是:
# 1. 文字处理的 re
# 2. 日期类型的 time datetime
# 3. 数字和数学类型的 math、random
# 4. 文件和目录访问的 pathlib、os.path
# 5. 数据压缩和归档的 tarfile
# 6. 通用操作系统的 os、logging、argparse
# 7. 多线程的 threading、queue
# 8. Internet 数据处理 base64、json、urllib
# 9. 结构化标记处理工具 html、xml
# 10. 开发工具的 unitest
# 11. 调试工具的 timeit
# 12. 软件界包发布的 venv
# 13. 运行服务的 __main__


# 正则表达式特殊符号
# . ^ $ * + ? {m} {m,n} [] \ \d \D \s ()
# ^$
# .*?
import re
p = re.compile('ca*t')
print(p.match('cat'))
print(p.match('bcaaat'))

p2 = re.compile(r'(\d+)-(\d+)-(\d+)')
p__match = p2.match('2021-12-26')
print(p__match)
print(p__match.groups())
print(p__match.group(1))
print(p__match.group(2))
year, month, day = p__match.groups()
print(year,month,day)

p__search = p2.search('aa2021-12-26bb')
print(p__search)


phone = '123-456-789 # 这是电话号码'
p3 = re.sub(r'#.*$','',phone)
print(p3)

p4 = re.sub(r'\D','',phone)
print(p4)
