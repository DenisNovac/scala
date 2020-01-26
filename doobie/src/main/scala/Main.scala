import cats._
import cats.data._
import cats.effect._
import cats.effect.implicits._
import doobie._
import doobie.implicits._
import doobie.util.compat._
import doobie.postgres.implicits._
import doobie.util.ExecutionContexts


case class Book(id: Long, title: String, primary_author: String) {
  override def toString: String = s"$id:$title:$primary_author"
}

case class BookReq(title: String, primary_author: String) {
  override def toString: String = s"$title:$primary_author"
}


object DbConnection {
  /** Контекст для транзактора */
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)


  val driver = "org.postgresql.Driver"
  val connectionString = "jdbc:postgresql:postgres"
  val user = "postgres"
  val pass = "P@ssw0rd"

  lazy val xa = Transactor.fromDriverManager[IO](driver, connectionString, user, pass)


  def find(title: String): Option[Book] =
    sql"SELECT * FROM books WHERE title = $title"
      .query[Book]
      .option
      .transact(xa)
      .unsafeRunSync()


  def insert(br: BookReq): Int =
    sql"INSERT INTO books (title, primary_author) VALUES (${br.title}, ${br.primary_author})"
      .update
      .run
      .transact(xa)
      .unsafeRunSync()


  def findNamePref(title: String): List[Book] =
    sql"SELECT * FROM books WHERE title LIKE ${title+"%"}"
      .query[Book]
      .to[List] //No implicits found for parameter f: FactoryCompat[Book, List[Book]] but works
      .transact(xa)
      .unsafeRunSync()

}

object Main extends App {

  DbConnection.find("Harry Potter") match {
    case Some(x) => println(x)
    case None => println("none")
  }

  DbConnection.insert(BookReq("The Denis", "denis"))


  println(DbConnection.findNamePref("The"))





}
