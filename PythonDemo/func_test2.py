def counter(First=10):
    cnt = [First]
    def add_one():
        cnt[0] += 1
        return cnt[0]
    return add_one

num10 = counter()
num5 = counter(5)

print(num10())
print(num10())
print(num10())
print(num5())
print(num5())
