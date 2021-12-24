user1 = {'name': 'tom', 'hp': 100}
user2 = {'name': 'jerry', 'hp': 80}


def print_role(rolename):
    print('name is %s, hp is %s' % (rolename['name'], rolename['hp']))


print_role(user1)


class Player():     # 定义一个函数
    def __init__(self, name, hp, occu):
        self.__name = name # 下划线开头的属性只能通过方法访问和修改
        self.hp = hp
        self.occu = occu

    def print_role(self):
        print('%s: %s  %s' % (self.__name, self.hp, self.occu))

    def update_name(self,new_name):
        self.__name = new_name


class Monster():
    '定义怪物类'
    def __init__(self,hp):
        self.hp = hp
class Animals(Monster):
    '普通怪物'
    pass
class Boss(Monster):
    'Boss 类怪物'
    pass




user1 = Player('tom', 100, 'war') # 类实例化
user2 = Player('jerry', 90, 'master')
user1.print_role()
user2.print_role()

user1.update_name('wilson')
user1.print_role()
