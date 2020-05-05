package app

import cats.effect.{ContextShift, IO, Timer}
import _root_.fetch.{DataCache, Fetch, InMemoryCache}

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.global

object CacheExample extends App {

  implicit val ec: ExecutionContext = global
  implicit val cs: ContextShift[IO] = IO.contextShift(ec) // для Fetch.run и app.ListDataSource
  implicit val timer: Timer[IO]     = IO.timer(ec) // для Fetch.run

  val list = List("a", "b", "c")
  val data = new ListSource(list)

  def fetch(id: Int): Option[String] = {
    val run = Fetch.run(data.fetchElem(id))
    run.unsafeRunSync
  }

  fetch(1)
  fetch(1)
  fetch(1)

  println(s"\nCached:")

  /** Готовый наполненный кэш */
  val cacheF: DataCache[IO] = InMemoryCache.from((data, 1) -> "b", (data, 2) -> "c")
  Fetch.run(data.fetchElem(1), cacheF).unsafeRunSync
  Fetch.run(data.fetchElem(1), cacheF).unsafeRunSync
  Fetch.run(data.fetchElem(1), cacheF).unsafeRunSync
  Fetch.run(data.fetchElem(1), cacheF).unsafeRunSync
  Fetch.run(data.fetchElem(1), cacheF).unsafeRunSync
  Fetch.run(data.fetchElem(1), cacheF).unsafeRunSync

  println("\nCached from empty:")

  /** Пустой новый кэш */
  var cache: DataCache[IO] = InMemoryCache.empty

  def cachedRun(id: Int): Option[String] = {
    val (c, r) = Fetch.runCache(data.fetchElem(id), cache).unsafeRunSync
    cache = c // Пример ручного управления кэшем
    r
  }

  cachedRun(1)
  cachedRun(1)
  cachedRun(2)
  cachedRun(2)
  cachedRun(4)
  cachedRun(4)

}
