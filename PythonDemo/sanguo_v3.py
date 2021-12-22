import re


def find_item(hero):
    with open('sanguo.txt') as f:
        data = f.read().replace('\n', '')
        name_num = re.findall(hero, data)
        # print('主角 %s 出现 %s 次 ' %(hero, len(name_num)))
    return len(name_num)


def find_main_charecters(main_charecters):
    with open('sanguo.txt') as f:
        data = f.read().replace('\n', '')
        main_charecters_num = re.findall(main_charecters, data)
        # print('主角 %s 出现 %s 次 ' %(hero, len(name_num)))
    return main_charecters, len(main_charecters_num)


# 读取人物信息
name_dict = {}
with open('name.txt') as f:
    for line in f:
        names = line.split('|')
        for n in names:
            # print(n)
            name_num = find_item(n)
            name_dict[n] = name_num

name_sorted = sorted(name_dict.items(), key=lambda item: item[1], reverse=True)
print(name_sorted[0:10])

# 读取武器信息
weapon_dict = {}
with open('weapon.txt') as f:
    i = 1
    for line in f:
        if i % 2 == 1:
            weapon_name, weapon_number = find_main_charecters(line.strip())
            weapon_dict[weapon_name] = weapon_number
        i += 1

weapon_sorted = sorted(weapon_dict.items(), key=lambda item: item[1], reverse=True)
print(weapon_sorted)
