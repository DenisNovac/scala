object CaseClasses {
  def main(args: Array[String]): Unit = {
    val point = new Point(1,2)
    val point0 = new Point(1,2)
    val point1 = new Point(1,3)

    if (point == point0)
      println(true)

    if (point==point1)
      print(true)
    else
      print(false)
  }
}

/**
 * Case Class это Класс-образец
 *
 * Это специальный тип класса. По умолчанию такие классы неизменны
 * и сравниваются по значению из конструктора.
 *
 * Отличия от обычных классов:
 * Можно создавать новые экземпляры таких классов без new;
 * Можно сравнивать по значению
 */

sealed case class Point(x: Int, y: Int)

