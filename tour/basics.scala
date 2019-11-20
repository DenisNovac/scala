package basics

/**
 * Составлен в соответствии с уроком
 * https://docs.scala-lang.org/tour/basics.html
 * */

object MainApp extends App {
  // выражения это вычисляемые утверждения
  1 + 1
  println(1 + 1)
  // и так тоже можно
  println("Hello, "+"world!")


  /**
   * ЗНАЧЕНИЯ
   * */
  // результаты выражений можно именовать словом val
  val x = 1 + 1
  println(x)
  // выисляемые выражения с именами называются ЗНАЧЕНИЯ
  // их нельзя менять после вычисления
  // x = 3 не скомпилируется

  // Типы значений могут быть вычислены, но можно и прописывать их
  // второй раз вызвать х в том же коде нельзя
  val y: Int = 1+1
  println((y))


  /**
   * ПЕРЕМЕННЫЕ
   * Переменные начинаются со слова var.
   */
  var z = 1+1
  z = 4
  // можно и так
  var zz: Int = 42


  /**
   * БЛОКИ
   * Можно совмещать выражения, окружая их {}. Это называется БЛОК.
   */
  println({
    val x = 1+1
    x + 1
  }) //3


  /**
   * ФУНКЦИИ
   * Функции - это выражения, которые принимают параметры.
   */
  // анонимная функция. Возвращает x плюс 1 (принимает x)
  (x: Int) => x+1

  // именованная функция
  val addOne = (x: Int) => x+1
  println((addOne(5)))

  val getAnswer = (x: Int, y: Int) => x+y
  val noAnswer = () => {
    println("Haha")
  }
  noAnswer()


  /**
   * МЕТОДЫ
   * Методы - это как функции, но они определены словом def.
   * Ещё в методах указан возвращаемый тип.
   * Методы могут принимать несколько листов параметров.
   */

  def add(x: Int, y: Int): Int = x+y
  println((add(40,2)))

  // два листа параметров
  def addThenMultiply(x: Int, y: Int)(multiplier: Int): Int = (x+y)*multiplier
  println((addThenMultiply(1,2)(3)))

  // нет параметров
  def name: String = System.getProperty("user.name")
  println(name)

  // методы могут быть многострочными
  def getSquareString(input: Double): String = {
    val square = input * input
    square.toString()
    // в Scala есть слово return, но можно и без него
  }

  println(getSquareString(12))

  println("\nКлассы")
  // создание экземпляра класса
  val greeter = new Greeter("Hello, ", "!")
  greeter.greet("Denis")

  println("\nCase-классы")
  // case-классы можно создавать без new
  val point = Point(1,2)
  val new_point = Point(2,3)

  // case-класссы сравнимы по значению
  if (point == new_point) {
    println("True")
  } else {
    println("False")
  }

  // получить доступ к объекту можно по имени
  println("\nОбъекты")
  println(IdFactory.create()) //1
  println(IdFactory.create()) //2



}

/**
 * КЛАССЫ
 * */

class Greeter(prefix: String, suffix: String) {
  /**
   * Тип возвращаемого значения Greeter - это Unit, что означает, что ничего осмысленного может не вернуться
   * По сути, это аналог void в Java и C (различие тут в том, что каждое выражение Scala обязано иметь какое-нибудь
   * значение. Даже есть синглтон-значение Unit, которое пишется как (). Оно не содержит информации)
   */
  def greet(name: String): Unit =
    println(prefix+name+suffix)

}


/**
 * Case-КЛАССЫ
 * Это специальный тип классов. По дефолту они НЕИЗМЕНЯЕМЫЕ и похожи на value.
 * */

case class Point(x: Int, y: Int)


/**
 * Объекты
 * Это сингловые экземпляры своих определений. Это что-то вроде синглтона
 * */

object IdFactory {
  private var counter = 0
  def create(): Int = {
    counter += 1
    counter
  }
}

/**
 * ТИПАЖИ
 * Типажи - это типы, содержащие поля и методы. Несколько типажей можно совместить
 * */

trait GreeterTrait {
  def greet(name: String): Unit
}

trait GreeterTraitSecond {
  def greet(name: String): Unit =
    println("Hello, "+name+"!")
}

// Их можно расширить
// не понял, в уроке так можно, а тут ошибка и просит метод переопределить абстрактный
// class DefaultGreeter extends GreeterTrait


class CustomizableGreeter(prefix: String, postfix: String) extends GreeterTraitSecond {
  override def greet(name: String): Unit = {
    println(prefix + name + postfix)
  }
}

/**
 * Метод MAIN
 * Это входная точка программы. JVM требует его имя main и один аргумент - массив строк.
 * */

object Main {
  def main(args: Array[String]): Unit =
    println("Hello, Scala developer!")
}