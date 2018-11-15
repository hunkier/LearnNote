package objectorention

/**
 * 1.groovy中默认都是public
 */
class Person {
    String name
    Integer age

    def increaseAge(Integer years){
        this.age += years;
    }

    /**
     * 一个方法找不到时，调用它代替
     * @param name
     * @param args
     * @return
     */
   def invokeMethod(String name, Object args){
       return "the method is ${name}, the param is ${args}"
   }

    def methodMissing(String name, Object args){
        return "the mehod ${name} is messing "
    }
}
