object Fibonacci extends App{
  val fibs: LazyList[Int] = 0 #:: fibs.scanLeft(1)(_ + _)
  println((fibs take 10).toList)
}
