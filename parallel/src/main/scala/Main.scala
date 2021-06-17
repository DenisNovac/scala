import cats.effect.IO
import cats.implicits._
import cats.effect.implicits._

import scala.concurrent.ExecutionContext

object Main extends App {

  implicit val cs = IO.contextShift(ExecutionContext.global)



  val r = Range(0, 100).map(i => IO(print(s"$i "))).toList


  r.sequence.unsafeRunSync()  // последовательно

  println()

  r.parTraverse(identity).unsafeRunSync() // параллельно

  println()

  val t =  (
    IO(print("1 ")), IO(print("2 ")), IO(print("3 ")), IO(print("4 "))
  )

  t.parTupled.unsafeRunSync()  // параллельно

}
