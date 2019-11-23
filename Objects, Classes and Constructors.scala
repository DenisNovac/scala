/**
 * В Scala можно сразу создать объект, без объявления класса. Такой объект называется
 * "Объект-компаньон". Он реализует паттерн одиночка, и все его методы считаются
 * статическими. Слово static отсутствует.
 */


object OOP {
  def main(args: Array[String]): Unit = {
    val test = new Example(1, 2, 3)
    println(test.getRes)

    val oop = new OOP
    println(oop.test(1))

    println()
    /**
     * Пробуем класс с тремя конструкторами
     * */

    val uselessObject1 = UselessClass(1)
    val uselessObject2 = UselessClass(1,10)
    val uselessObject3 = UselessClass(1,10,100)

    println(uselessObject1.instanceMethod())
    println(uselessObject2.instanceMethod())
    println(uselessObject3.instanceMethod())

  }
}

/**
 * Допускается создавать класс с тем же именем, что у объекта. Они не будут разными сущностями,
 * как будет продемонстрировано ниже.
 */
class OOP {
  def test(x: Int): Int = x
}



/**
 * В Scala поля класса пишутся сразу после имени как список аргументов.
 * При этом, они объявляются как private val, если не настроено иначе.
 *
 * Ещё бывают abstract классы, как в Java
 *
 * Основное отличие: конструкторов классов здесь нет. Код конструктора пишется прямо в
 * теле класса. Что же делать, когда требуется несколько конструкторов?
 */

class Example (x: Int, y: Int, z:Int) {
  private val res = x + y + z

  def getRes(): Int = res
}




/**
 * При приминении любого объекта к некоторым аргументам по умолчанию вызывается метод
 * apply. Этим можно воспользоваться для написания класса с несколькими конструкторами.
 */

// получается, объект как бы хранит в себе конструкторы для класса
object UselessClass {
  /**
   * Статические конструкторы
   *
   * Т.к. они статические - их можно вызывать из класса!
   */
  def apply(immutableField: Int): UselessClass = new UselessClass(immutableField, 2)

  def apply(immutableField: Int, mutableField: Int): UselessClass = new UselessClass(immutableField, mutableField)

  def apply(immutableField: Int, mutableField: Int, privateField: Int) = new UselessClass(immutableField, mutableField, privateField)
}

// privateField имеет значение по умолчанию
 class UselessClass(val immutableField: Int, var mutableField: Int, privateField: Int = 8) {
  def instanceMethod(): Int = {
    val sumOfFields = immutableField + mutableField + privateField
    sumOfFields
    //UselessClass.staticMethod(sumOfFields)
  }
}






