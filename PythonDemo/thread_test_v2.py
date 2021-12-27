import threading
from threading import currentThread

class Mythread(threading.Thread):
    def run(self):
        print('%s start' %currentThread().getName())
        print('run')
        print('%s stop' %currentThread().getName())

t1 = Mythread()
t1.start()
t1.join()
print('%s end' % currentThread().getName())
