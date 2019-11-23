object InterfacesAndTraits {
  def main(args: Array[String]): Unit = {
    val comp = new ComplexClass
    val f = comp.foo(1)
    val b = comp.bar(2)
    println(f)
    println(b)
  }
}

/**
 * Аналогом Java-интерфейса в Scala является трейт (Trait).
 * Как и интерфейс в Java, он содержит только объявления методов и допускает
 * множественное наследование.
 *
 * В отличие от интерфейса, в трейте можно описывать поля класса и частично реализовывать
 * методы. Наследование как трейтов, так и абстрактных классов осуществляется с помощью
 * слов extend (первый родитель) и with (последующие родители)
 */

trait FirstTrait {
  // указываем возврат явно, иначе будет Unit
  def foo(x: Int): Int
}

trait SecondTrait {
  def bar(y: Int) = y + 5
}

class ComplexClass extends FirstTrait with SecondTrait {
  // ключевое слово override необязательно, но это хорошая практика
  override def foo(x: Int) = x * 2
}



