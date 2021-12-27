import matplotlib.pyplot as plt
import numpy as np

# 绘制简单的曲线
# plt.plot([1,3,5],[4,8,10])
# plt.show()

# x 轴的定义域为 -3.14~3.14，中间间隔 100 个元素
# x = np.linspace(-np.pi, np.pi, 100)
# plt.plot(x, np.sin(x))
#
# # 显示所画的图
# plt.show()

# x = np.linspace(-np.pi*2, np.pi*2, 100)
# plt.figure(1,dpi=50)
# for i in range(1,5):
#     plt.plot(x, np.sin(x/i))
#
# # 显示所画的图
# plt.show()

# plt.figure(1,dpi=50) # 创建图表1，dpi 代表图片精细度，dpi 越大文件越大，杂志要 300 以上
# data = [1,1,1,2,2,2,3,3,4,5,5,6,4]
# plt.hist(data) # 只要传入数据，直方图就会统计数据出现的次数
# plt.show()

x=np.arange(1,10)
y=x
plt.figure(1,dpi=50)
plt.scatter(x,y,c='r',marker='o') # c='r' 表示散点的颜色为红色，marker 表示指定散点多形状为圆形
plt.show()