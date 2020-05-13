import Iterative.splits
import scala.util.Random

trait GraphEntity
abstract class AbstractNode(val elem: List[Int]) extends GraphEntity

case class Start() extends AbstractNode(List.empty[Int]) {
  override def toString: String = "S"
}

case class End() extends AbstractNode(List.empty[Int]) {
  override def toString: String = "E"
}

case class Node(override val elem: List[Int]) extends AbstractNode(elem) {
  override def toString: String = s"(${elem.mkString(", ")})"
}

case class Edge(left: AbstractNode, right: AbstractNode, score: Int) extends GraphEntity {
  override def toString: String = s"$left -$score-> $right"

  def rightToString: String = s"-$score-> $right"
}

/** Линки только сверху вниз, граф направленный */
case class LinkedNode(node: AbstractNode, links: List[Edge]) {

  def +(that: LinkedNode) = {
    require(node.elem == that.node.elem)
    LinkedNode(node, links ++ that.links)
  }

  override def toString: String =
    s"""
       |$node
       |${links.map("\t" + _.rightToString).mkString("\n")}
       |""".stripMargin

}

class Graph(order: List[Int], splits: List[List[Int]]) {

  val scores: Map[List[Int], Int]       = splits.map(l => l -> Random.nextInt(l.length * 10)).toMap
  val groups: Map[Int, List[List[Int]]] = splits.groupBy(l => l.head)
  // Группы по первому элементу, по ним итерируемся через order
  //5 -> List(List(5), List(5, 6), List(5, 6, 7))
  //1 -> List(List(1), List(1, 2), List(1, 2, 3), List(1, 2, 3, 4), List(1, 2, 3, 4, 5))
  //6 -> List(List(6), List(6, 7))
  //2 -> List(List(2), List(2, 3), List(2, 3, 4), List(2, 3, 4, 5), List(2, 3, 4, 5, 6))
  //7 -> List(List(7))
  //3 -> List(List(3), List(3, 4), List(3, 4, 5), List(3, 4, 5, 6), List(3, 4, 5, 6, 7))
  //4 -> List(List(4), List(4, 5), List(4, 5, 6), List(4, 5, 6, 7))

  def buildGraph: Map[AbstractNode, LinkedNode] = {
    val edges = {
      for {
        o <- order
      } yield {

        {
          for {
            group <- groups(o)
          } yield {
            if (o == order.last) {
              List(Edge(Node(group), End(), 0))
            } else {
              val last = group.last + 1
              if (last == order.last + 1) List(Edge(Node(group), End(), 0))
              else groups(last).map(rights => Edge(Node(group), Node(rights), scores(rights)))
            }
          }
        }
      }
    }.flatten.flatten

    // Дописываем стартовые отношения S -> ...
    val full = (edges ++ edges
      .groupBy(e => e.left.elem.head)(order.head)
      .map(e => Edge(Start(), e.left, scores(e.left.elem)))
      .distinct)
      .map(edge => LinkedNode(edge.left, List(edge))) // Простой маппинг в класс, который можно потом фолднуть

    // Складываем ноды от одного и того же элемента
    val linkedNodes = for {
      group <- full.groupBy(n => n.node)
    } yield group._2.drop(1).foldRight(group._2.head)(_ + _)

    println(linkedNodes.mkString(""))

    /** Эта карта содержит ноды и ссылки на их LinkedNodes.
      * Она нужна чтобы из нод Edge находить следующие Edge
      * */
    val nodesMap: Map[AbstractNode, LinkedNode] = linkedNodes.map(ln => (ln.node, ln)).toMap
    //println(nodesMap.mkString("---------\n"))

    nodesMap
  }

  def calcLongest(graph: Map[AbstractNode, LinkedNode]) = {
    val start = graph(Start())
    //println(start)

    //List[List[Edge]] - лист путей
    // Найти все пути в End из Start

    def rec(start: LinkedNode, acc: List[Edge]): List[List[Edge]] = {
      for {
        e <- start.links
      } yield e.right match {
        case n: End  => List(acc :+ e)
        case n: Node => rec(graph(n), acc :+ e)
      }
    }.flatten

    val routesToEnd =
      rec(start, List.empty[Edge])
        .map { listOfEdges =>
          val scores = listOfEdges.foldRight(0)((e, acc) => e.score + acc)
          (scores, listOfEdges)
        }
        .sortWith((t1, t2) => t1._1 > t2._1)

    //println(routesToEnd.mkString("\n"))
    println(routesToEnd.head)
    routesToEnd.head

  }

}
