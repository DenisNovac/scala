import cats.effect._
import cats.effect.concurrent.Ref


def changeRefIO(r: IO[Ref[IO, Int]]) = for {
  ref <- r
  _ <- ref.update(_ + 3)
} yield ()

val in: Int = 4
val r: IO[Ref[IO, Int]] = Ref.of[IO, Int](in)
changeRefIO(r).unsafeRunSync()
r.flatMap((f: Ref[IO, Int]) => f.get).unsafeRunSync  // still gives 4



def changeRef(r: Ref[IO, Int]) = for {
  _ <- r.update(_ + 3)
} yield ()

val myInt: Int = 4
val ref: Ref[IO, Int] = Ref.of[IO, Int](myInt).unsafeRunSync()
changeRef(ref).unsafeRunSync()
ref.get.unsafeRunSync() // gives 7

