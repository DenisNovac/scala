import scala.language.implicitConversions

class imp_example2 {

}

implicit def list2ordered[A](x:List[A])
                            (implicit elem2ordered: A => Ordered[A]): Ordered[List[A]] =
                          new Ordered[List[A]] {
                            // заменить на более полезную реализацию
                            override def compare(that: List[A]): Int = 1
                          }

