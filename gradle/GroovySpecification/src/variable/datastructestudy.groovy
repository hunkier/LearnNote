package variable

//def list = new ArrayList() // java的定义方式
def list = [1, 2, 3, 4, 5]
//println list.class
//println list.size()
def array = [1, 2, 3, 4, 5] as int[]
int[] array2 = [1, 2, 3, 4, 5]
//println array.class
//println array2.class
//println array.length

/**
 * 列表的排序
 */
def sortList = [6, -3, 9, 2, -7, 1, 5]


Collections.sort(sortList)
//println sortList
Comparator mc = { a, b ->
    a == b ? 0 :
            Math.abs(a) < Math.abs(b) ? -1 : 1
}
Collections.sort(sortList, mc)
//println sortList

sortList = [6, -3, 9, 2, -7, 1, 5]
sortList.sort()
//println sortList
sortList.sort({a, b ->
    a == b ? 0 :
            Math.abs(a) < Math.abs(b) ? -1 : 1})
//println sortList

def sortStringList = ['abc', 'z', 'Hello', 'groovy', 'java']
sortStringList.sort({it -> return it.size()})
//println sortStringList


/**
 * 列表的查找
 */
def findList = [-3, 9, 6, 2, -7, 1, 5]
//int result = findList.find { return  it % 2 == 0 }
//def result = findList.findAll {return  it % 2 != 0 }
//println result.toListString()
//def result = findList.any { return  it % 2 != 0}
//def result = findList.every { reuslt it % 2 == 0}
//def result = findList.min()
//def result = findList.max()
//def result = findList.min {return  Math.abs(it)}
//def result = findList.max {return  Math.abs(it)}
//def result = findList.count {return  it % 2 ==0}
//println result