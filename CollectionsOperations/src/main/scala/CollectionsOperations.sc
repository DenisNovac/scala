object CollectionsOperations {
  /**
   * https://docs.scala-lang.org/overviews/collections-2.13/performance-characteristics.html
   *
   * В связи с наличием иммутабельных структур - в Scala
   * могут отличаться эффективности. Например, вставка в иммутабельный
   * лист будет O(n), когда как в мутабельный - O(1) (соответствует
   * хабру:
   * https://habr.com/ru/post/188010/ .
   * */


  val list = List(1,2,3)

  // добавление в лист
  1 :: Nil  // создание пустого листа
  list ::: 1 :: Nil  // по сути сначала создаём пустой лист и конкатенируем
  list ::: List(1)  // то же самое


  1 :: list // добавление листа в конец работает просто O(1)
  //list :: 1
  // добавление в конец листа уже не очень - O(n),
  // потому что иммутабельный лист не должен меняться по определению.
  // Но если хочется, то можно склеить два листа, эта операция равна
  // созданию нового листа со всеми перечисленными элементами


  // Как видно - добавление в листы - это неэффективная операция в целом
  // А вот добавление в вектор быстрое (почти константа,
  // примерно как мутабельного вектора - O(1) с оговорками)
  val vec = Vector(1,2,3)
  vec :+ 1
  1 +: vec
  vec ++ Vector(1)
  // вставка по индексу, O(1)
  list.updated(2,1)
  


}