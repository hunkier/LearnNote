user1 = {'name': 'tom', 'hp': 100}
user2 = {'name': 'jerry', 'hp': 80}


def print_role(rolename):
    print('name is %s, hp is %s' % (rolename['name'], rolename['hp']))


print_role(user1)


class Player():     # 定义一个函数
    def __init__(self, name, hp, occ):
        self.__name = name # 下划线开头的属性只能通过方法访问和修改
        self.hp = hp
        self.occ = occ

    def print_role(self):
        print('%s: %s  %s' % (self.__name, self.hp, self.occ))

    def update_name(self,new_name):
        self.__name = new_name


class Monster():
    '定义怪物类'
    pass


user1 = Player('tom', 100, 'war') # 类实例化
user2 = Player('jerry', 90, 'master')
user1.print_role()
user2.print_role()

user1.update_name('wilson')
user1.print_role()
