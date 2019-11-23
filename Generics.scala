object Generics {
  def main(args: Array[String]): Unit = {
    val test = new Test
    test.bar(1,2)
  }
}

/**
 * Как и в Java, в Scala классы, трейты и функции можно параметризовать
 * Типы должны быть определены в реализации интерфейса
 */
trait Foo[A, B, C] {
  def bar(a: A, b: B): C
}

class Test extends Foo[Byte, Int, Double] {
  override def bar(a: Byte, b: Int): Double = a+b
}