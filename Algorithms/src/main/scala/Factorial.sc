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

q
}