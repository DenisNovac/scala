package app

import cats.effect.{ContextShift, IO, Timer}
import cats.instances.list._
import cats.syntax.traverse._ // traverse
import cats.syntax.option._   // some
import cats.syntax.apply._    // tupled

import fetch.Fetch
import fetch.fetchM // инстансы Fetch для синтаксиса Cats

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.global

object BatchingExample extends App {
  implicit val ec: ExecutionContext = global
  implicit val cs: ContextShift[IO] = IO.contextShift(ec) // для Fetch.run и app.ListDataSource
  implicit val timer: Timer[IO]     = IO.timer(ec) // для Fetch.run

  val list = List("a", "b", "c", "d", "e", "f", "g", "h", "i")
  val data = new ListSource(list, 2.some)

  println("tuple")
  // Два запроса выполнятся пачкой
  val tuple: Fetch[IO, (Option[String], Option[String])] = (data.fetchElem(0), data.fetchElem(1)).tupled
  println(Fetch.run(tuple).unsafeRunSync())

  println("tupleD")
  // Одинаковый запрос выполнится только один раз
  val tupleD: Fetch[IO, (Option[String], Option[String])] = (data.fetchElem(0), data.fetchElem(0)).tupled
  println(Fetch.run(tupleD).unsafeRunSync())

  def findMany: Fetch[IO, List[Option[String]]] =
    List(0, 1, 2, 3, 4, 5).traverse(data.fetchElem)

  Fetch.run(findMany).unsafeRunSync

}