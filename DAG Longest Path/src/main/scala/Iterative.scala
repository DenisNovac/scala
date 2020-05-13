import scala.annotation.tailrec
import scala.language.postfixOps

import scala.util.Random

object Iterative extends App {

  val l = 1 to 15 toList
  /*println(split(l, 3))
  println(split(l, 1))*/

  val maxSegmentSize = 10

  val splits: List[List[Int]] = splitMany(l, maxSegmentSize)



  val order = l // порядок - исходный лист

  println("\n")

  val graphBuilder = new Graph(order, splits)
  val graph = graphBuilder.buildGraph
  val calc = graphBuilder.calcLongest(graph)


  def splitMany(l: List[Int], maxSize: Int): List[List[Int]] = {
    val newMaxSize =
      if (maxSize > l.length) l.length
      else maxSize

    for {
      size <- 1 to newMaxSize
    } yield split(l, size)
  }.toList.flatten

  def split(l: List[Int], size: Int) = {
    @tailrec
    def recSplit(l: List[Int], size: Int, acc: List[List[Int]]): List[List[Int]] = l match {
      case Nil => acc

      case list if list.length > size =>
        val split = list.splitAt(size)
        //recSplit(split._2, size, split._1 :: acc)
        recSplit(list.drop(1), size, split._1 :: acc)

      case list => list :: acc
    }

    if (size == 1) l.map(e => List(e))
    else {
      recSplit(l, size, Nil).reverse
    }
  }
}