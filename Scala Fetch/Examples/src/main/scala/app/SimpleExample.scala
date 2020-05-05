package app

import cats.effect.{ContextShift, IO, Timer}
import fetch.Fetch

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.global

object SimpleExample extends App {
  implicit val ec: ExecutionContext = global
  implicit val cs: ContextShift[IO] = IO.contextShift(ec) // для Fetch.run и app.ListDataSource
  implicit val timer: Timer[IO]     = IO.timer(ec) // для Fetch.run

  val list = List("a", "b", "c")
  val data = new ListSource(list)

  Fetch.run(data.fetchElem(0)).unsafeRunSync          // INFO app.ListDataSource - Processing element from index 0
  Fetch.run(data.fetchElem(1)).unsafeRunSync          // INFO app.ListDataSource - Processing element from index 1
  Fetch.run(data.fetchElem(2)).unsafeRunSync          // INFO app.ListDataSource - Processing element from index 2
  println(Fetch.run(data.fetchElem(2)).unsafeRunSync) // Some(c)
  println(Fetch.run(data.fetchElem(3)).unsafeRunSync) // None

}
