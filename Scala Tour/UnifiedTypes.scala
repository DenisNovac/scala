package unified_types

object Main extends App {

  // все элементы этого листа - это подтипы Any (AnyVal)
  val list: List[Any] = List(
    "a string",
    632,
    4.20,
    true,
    'c',
    () => "anonymous function returning a string"
  )

  list.foreach(element => println(element))

  val tc = new TypeCasting
  tc.testCasting
}


class TypeCasting {

  def testCasting: Unit = {
    val x: Long = 987654321
    val y: Float = x //9.8765434E8

    val face: Char = '☺'
    val number: Int = face // 9786

    val xx: Long = 987654321
    val yy: Float = xx
    // приведение большего к меньшему вызывает ошибку
    //val zz: Long = yy

    println(x)
    println(y)
    println(face)
    println(number)

    println(xx)
    println(yy)
  }
}