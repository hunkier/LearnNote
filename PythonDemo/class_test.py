user1 = {'name': 'tom', 'hp': 100}
user2 = {'name': 'jerry', 'hp': 80}


def print_role(rolename):
    print('name is %s, hp is %s' % (rolename['name'], rolename['hp']))


print_role(user1)


class Player():  # 定义一个函数
    def __init__(self, name, hp, occu):
        self.__name = name  # 下划线开头的属性只能通过方法访问和修改
        self.hp = hp
        self.occu = occu

    def print_role(self):
        print('%s: %s  %s' % (self.__name, self.hp, self.occu))

    def update_name(self, new_name):
        self.__name = new_name


class Monster():
    '定义怪物类'

    def __init__(self, hp=100):
        self.hp = hp

    def run(self):
        print('移动到某个位置')

    def whoami(self):
        print('我是怪物父类')


class Animals(Monster):
    '普通怪物'
    def __init__(self, hp=10):
        super().__init__(hp)


class Boss(Monster):
    'Boss 类怪物'
    def __init__(self, hp=1000):
        super().__init__(hp)
    def whoami(self):
        print('我是怪物我怕谁')



a1 = Monster(200)
print(a1.hp)
a1.run()
a2 = Animals(1)
print(a2.hp)
a2.run()
a3 = Boss(800)
print(a3.hp)
a3.whoami()

print(type(2))
print(type('2'))
print('a1 的类型 %s' %type(a1))
print('a2 的类型 %s' %type(a2))
print('a3 的类型 %s' %type(a3))

print(isinstance(a2,Monster))
print(isinstance(2,object))
print(isinstance('2',object))




# user1 = Player('tom', 100, 'war') # 类实例化
# user2 = Player('jerry', 90, 'master')
# user1.print_role()
# user2.print_role()
#
# user1.update_name('wilson')
# user1.print_role()
