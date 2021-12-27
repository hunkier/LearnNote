import threading
import time
from  threading import currentThread
def myThread(arg1, arg2):
    print('%s start' % currentThread().getName())
    print('%s %s' %(arg1, arg2))
    time.sleep(1)
    print('%s stop' % currentThread().getName())



for i in range(1,6,1):
    # t1 = myThread(i,i+1)
    t1 = threading.Thread(target=myThread,args=(i,i+1))
    t1.start()

print('%s end' % currentThread().getName())
