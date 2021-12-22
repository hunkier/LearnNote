fd = open('name.txt')
try:
    for line in fd:
        print(line)
finally:
    fd.close()


# 使用 with 打开文件时，不选手动关闭
with open('name.txt') as f:
    for line in f:
        print(line)

