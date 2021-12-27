class Testwith():
    def __enter__(self):
        print('run')

    def __exit__(self, exc_type, exc_val, exc_tb):
        if exc_tb is None:
            print('正常结束')
        else:
            print('has error %s' %exc_tb)


with Testwith():
    print('Test is runing')
    raise  NameError('testNameError')