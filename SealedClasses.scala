package scala


class SealedClasses {
  def main(args: Array[String]): Unit = {

  }
}

/**
 * Трейты и классы могут быть помечены как sealed (запечатанные).
 * Это значит, что подтипы должны быть объявлены в одном файле, гарантируя,
 * что все подтипы будут известны.
 *
 * Это используется в pattern matching (сопоставление с примером), т.к.
 * все варианты известны и не нужен вариант "все остальные".
 */
sealed abstract class Furniture
case class Couch() extends Furniture
case class Chair() extends Furniture