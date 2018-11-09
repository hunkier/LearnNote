package variable

def  colors = [red: 'ff000000',
               green: '00ff0000',
               blue: '0000ff00',
    ]
// 索引方式
//println colors['red']
//println colors.red

// 添加元素
colors.yellow = 'ffff00'
colors.complex = [a:1, b:2]
println colors.toMapString()
println colors.getClass()
println colors.class



