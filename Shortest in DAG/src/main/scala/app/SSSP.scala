package app

import app.GraphBuilder.DAG

object SSSP {

  /**
    * Топологическая сортировка (Topological Sort, TopSort):
    *
    * - Взять непосещённую ноду (любую)
    * - Выполнить Depth First Search, которая ищет только по непосещённым нодам (доступным на выбранной)
    * - Рекурсивной вызывать пока не дойдешь до ситуации, когда текущая нода не имеет непосещённых
    * - Добавить ноду в порядок слева, вернуться наружу;
    *
    * https://youtu.be/eL-KzMXSXXI
    * @param graph - Направленный ациклический граф
    */
  def topSort(graph: DAG): List[LinkedNode] = {

    val t0 = System.nanoTime()

    val topOrderReversed = scala.collection.mutable.ArrayBuffer.empty[LinkedNode]
    val visited          = scala.collection.mutable.Set.empty[AbstractNode]
    topOrderReversed += LinkedNode(End(), List.empty) // очевиднее всего добавить End сюда

    /** Deep First Search */
    def DFS(node: LinkedNode): Unit =
      //println(s"In ${node}")
      node.node match {

        // по идее, сюда можно попасть только если начать с End, т.к. дальше проверяются edge.right
        case End() => ()

        case _ =>
          // перебираем все грани текущей ноды и вносим их в visited
          for {
            edge <- node.links
          } yield {
            edge.right match {
              case End()                             => ()
              case _ if visited.contains(edge.right) => ()
              case _ =>
                DFS(graph(edge.right))
                visited += edge.right
            }
          }
          // когда грани кончились - текущая нода добавляется в топологический порядок
          topOrderReversed += node
      }

    DFS(graph(Start()))
    val topOrder = topOrderReversed.reverse.toList

    println(s"Topsort done in ${(System.nanoTime() - t0) / 1000000} ms")
    //println(topOrder.map(_.node).mkString("\n"))

    topOrder
  }

  val INF = 999_999

  /**
    * Найти кратчайший путь от старта до всех узлов графа (Single Source Shortest Path(SSSP)):
    *
    * - Взять топологическую сортировку графа;
    * - Лучшую дистанцию до каждой ноды взять за INF;
    * - Дистанция до стартовой ноды равна нулю;
    * - Дойти до какой-нибудь ноды. Обновить дистанцию до неё;
    * - Когда обошли все связи текущей ноды - идем в следующую согласно топологическому порядку
    * - Если начиная с другой ноды нашли ту, что уже есть в списке посещённых - сравниваем длины и обновляем путь если нужно
    *
    * https://youtu.be/TXkDpqjDMHA
    * @param topOrder Направленный ациклический граф
    */
  def calculateSsspOnDag(graph: DAG, topOrder: List[LinkedNode]) = {

    // Храним дистанции до каждой из нод начиная от стартовой
    // Например от старта до ноды (2,3) может быть карта Map((2,3) -> ScoredRoute( S -> 1; 1 -> 2,3 ))
    //val bestRoutesToNodes = scala.collection.mutable.Map.empty[AbstractNode, ScoredRoute]
    val bestScores = scala.collection.mutable.Map.empty[AbstractNode, Int]

    // текущий роут, с позиции которого идёт поиск
    var status: ScoredRoute = ScoredRoute(INF, List())

    bestScores += topOrder.head.node -> status.score

    for {
      next <- topOrder

    } yield {
      /**
       Находясь в некоторой "текущей" ноде смотрим лучший путь до неё + путь до каждой из граней, записываем "лучшие"
      потом идём на следующие грани

      Храню вместе с лучшими очками роуты между лучшими гранями

      Например:

      A -> B = 1  (B -> ( 1 (A,B) ))
      B -> C = 3  (C -> ( 4 (B,C) ))
      C -> D = 3  (D -> ( 7 (C,D) ))

      Потом складываю их и получаю лучший роут
       */


      // тут следует апдейтить статус (?)

    }

  }
}
