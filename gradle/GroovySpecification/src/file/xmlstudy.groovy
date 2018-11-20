package file

import groovy.xml.MarkupBuilder

final String xml = '''
<bookstore>

<book category="cooking">
  <title lang="en">Everyday Italian</title>
  <author>Giada De Laurentiis</author>
  <year>2005</year>
  <price>30.00</price>
</book>

<book category="children">
  <title lang="en">Harry Potter</title>
  <author>J K. Rowling</author>
  <year>2005</year>
  <price>29.99</price>
</book>

<book category="web">
  <title lang="en">XQuery Kick Start</title>
  <author>James McGovern</author>
  <author>Per Bothner</author>
  <author>Kurt Cagle</author>
  <author>James Linn</author>
  <author>Vaidyanathan Nagarajan</author>
  <year>2003</year>
  <price>49.99</price>
</book>

<book category="web" cover="paperback">
  <title lang="en">Learning XML</title>
  <author>Erik T. Ray</author>
  <year>2003</year>
  <price>39.95</price>
</book>

</bookstore>
'''

final String xml2 = '''
<breakfast_menu > 

   <food> 
       <name>Belgian Waffles</name> 
       <price>$5.95</price> 
       <description>Two of our famous Belgian Waffles with plenty of real maple syrup</description> 
       <calories>650</calories> </food> 
   <food> 
       <name>Strawberry Belgian Waffles</name> 
       <price>$7.95</price> 
       <description>Light Belgian waffles covered with strawberries and whipped cream</description> 
       <calories>900</calories> </food> 
   <food> 
       <name>Berry-Berry Belgian Waffles</name> 
       <price>$8.95</price> 
       <description>Light Belgian waffles covered with an assortment of fresh berries and whipped cream</description> 
       <calories>900</calories> </food> 
   <food> 
       <name>French Toast</name> 
       <price>$4.50</price> 
       <description>Thick slices made from our homemade sourdough bread</description> 
       <calories>600</calories> </food> 
   <food> 
       <name>Homestyle Breakfast</name> 
       <price>$6.95</price> 
       <description>Two eggs, bacon or sausage, toast, and our ever-popular hash browns</description> 
       <calories>950</calories> </food> 

</breakfast_menu>
'''

// 开始解析此xml数据
def xmlSluper = new XmlSlurper()
def responseFood = xmlSluper.parseText(xml2)
//def response = xmlSluper.parseText('https://www.w3schools.com/xml/books.xml')
println responseFood.food[0].name.text()

def response = xmlSluper.parseText(xml)
println response.book[0].title.text()
println response.book[0].@category
println response.book[3].@cover
println response.book[0].title.@lang

def list = []
responseFood.food.each { food ->
    def calories = food.calories.text()
    if (calories.equals('900') ){
        list.add(food.name.text())
    }
}
//println list.toListString()

// 深度遍历xml数据
def  names = responseFood.depthFirst().findAll{ food ->
    return food.calories.text() == '900'
}
//println names.toListString()

// 广度遍历
//def name = response.book.children().findAll{ node ->
def name = response.book.findAll{ node ->
    node.name() == 'book' && node.@cover == 'paperback'
}.collect { node ->
    node.title.text()
}
//println name

// 生成xml格式数据
def sw = new StringWriter()
def xmlBuilder = new MarkupBuilder(sw)
// 根节点
xmlBuilder.langs(type: 'current', count: '3', mainstream: 'true'){
    language(flavor: 'static', version: '1.5', 'Java'){
        age('16')
    }
    language(flavor: 'dynamic', version: '1.6.0', 'Groovy'){
        age('10')
    }
    language(flavor: 'dynamic', version: '1.9', 'JavaScript')
}

//println sw
def sw2 = new StringWriter()
def xmlBuilder2 = new MarkupBuilder(sw2)
def langs = new Langs()
xmlBuilder2.langs(type: langs.type, count: langs.count
        , mainstream: langs.mainstream){
    // 遍历所有的子节点
    langs.languages.each {
        lang ->
            Language(
                    flavor: lang.flavor,
                    version: lang.version,
                     lang.value
            )
    }
}
println sw2

// 对应根节点
class Langs {
    String type = 'current'
    int count = 3
    boolean mainstream = true
    def languages = [
            new Language(flavor:  'static', version: '1.5', value: 'java'),
            new Language(flavor: 'dynamic', version: '1.6.0', value: 'Groovy'),
            new Language(flavor: 'dynamic', version: '1.9', value: 'JavaScript'),
    ]
}
// 对应xml中的languang节点
class Language {
    String flavor
    String version
    String value
}