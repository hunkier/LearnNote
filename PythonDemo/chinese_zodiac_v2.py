# 记录生肖，根据年份来判断生肖

chinese_zodiac = '猴鸡狗猪鼠牛虎兔龙蛇马羊'

# year = int(input('请输入年份\n'))
#
# zodiac_year_ = chinese_zodiac[year % 12]
# print(zodiac_year_)
#
# if(zodiac_year_) == '狗':
#     print('狗年大吉')
# elif zodiac_year_ == '牛' :
#     print('你气冲天')
#
# for cz in chinese_zodiac:
#     print(cz)
#
# for i in range(1,13):
#     print(i)
#
# for year in range(2000, 2023):
#     print('%s 年的生肖是 %s' %(year,chinese_zodiac[year%12]))

import time
num = 5
while True:
    # print('a')
    num = num + 1
    if num > 20 :
        break
    if num == 10:
        continue
    print(num)
    time.sleep(1)