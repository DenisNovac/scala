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

case class ScoredRoute(score: Int, route: List[Edge])

class Graph(order: List[Int], splits: List[List[Int]]) {

  type MutableMap[K, V] = scala.collection.mutable.Map[K, V]

  val scores: Map[List[Int], Int]       = splits.map(l => l -> Random.nextInt(splits.length * 10)).toMap
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
    val t0    = System.nanoTime()
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

    //println(linkedNodes.mkString(""))
    println("Edges amount: " + linkedNodes.foldRight(0)((n, acc) => n.links.length + acc))

    /** Эта карта содержит ноды и ссылки на их LinkedNodes.
      * Она нужна чтобы из нод Edge находить следующие Edge
      * */
    val nodesMap: Map[AbstractNode, LinkedNode] = linkedNodes.map(ln => (ln.node, ln)).toMap
    //println(nodesMap.mkString("---------\n"))

    println(s"Graph build done in ${(System.nanoTime() - t0) / 1000000} ms")
    nodesMap
  }

  /** Нужно рекурсивно пройти по вершинам и для каждой вычислять и возвращать только длиннейший путь */
  def calcLongestFromAll(graph: Map[AbstractNode, LinkedNode]): ScoredRoute = {
    val t0    = System.nanoTime()
    val start = graph(Start())

    var countOfEdges = 0

    val cache: MutableMap[LinkedNode, ScoredRoute] =
      scala.collection.mutable.Map.empty[LinkedNode, ScoredRoute] // кэш хранит единственное наиболее длинное ребро

    def longestForNode(node: LinkedNode, acc: List[Edge], score: Int): ScoredRoute =
      cache.get(node) match {
        case Some(value) => value
        case None =>
          val longest = {
            for {
              edge <- node.links
            } yield {
              edge.right match {
                case End() =>
                  ScoredRoute(score, acc :+ edge)
                case n: Node =>
                  val linked = graph(n)

                  countOfEdges += 1
                  // longest хранит весь путь от старта, который мы будем возвращать
                  longestForNode(linked, acc :+ edge, score + edge.score)

              }
            }
          }.maxBy(_.score)
          /*val l = longest.route.dropWhile(_.left != node.node)
          cache += node -> ScoredRoute(l.foldRight(0)((e,a) => e.score + a), l)*/
          //cache += node -> longest
          longest
      }

    val longest = longestForNode(start, List.empty[Edge], 0)

    println()
    println(longest)
    println(s"${(System.nanoTime() - t0) / 1000000} ms")
    println("edges passed: " + countOfEdges)
    //println(cache)

    longest
  }

}
