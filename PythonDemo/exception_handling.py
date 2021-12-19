# i = j

# print())

# a = '123'
# print(a[3])

# d = {'a':1, 'b':2}
# print(d['c'])

# year = int(input('请输入年份'))

# try:
#     year = int(input('请输入年份'))
# except ValueError:
#     print('年份要输入数字')

# a = 123
# a.append('f')


# except (ValueError, AttributeError, KeyError)

# try:
#     print(1/'a')
# except Exception as e:
#     print(' %s' %e)

# try:
#     raise NameError('helloError')
# except NameError:
#     print('my custom error')

try:
    a = open('name1.txt')
except Exception as e:
    print(e)
finally:
    a.close()