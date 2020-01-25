import scala.annotation.tailrec

object Factorial {

  factorial(63)
  tailrecFactorial(63)

  def factorial(n: Long): Long = n match {
    case 1 => 1
    case _ => n * factorial(n-1)
  }

  @tailrec
  def tailrecFactorial(n: Long, acc: Long = 1): Long = n match {
    case 1 => acc
    case _ => tailrecFactorial(n-1, n*acc)
  }

  val l = List(0,1,2,3,4)
  l.scanLeft(1)(_ + _)
  l.scanLeft(1)((i: Int, j: Int)=>i+j)
  l.foldLeft(1)(_+_)


  //lazy val nl: List[Int] = 0 :: nl.scanLeft(1)(_ + _)
}