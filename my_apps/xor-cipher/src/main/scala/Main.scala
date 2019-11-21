import xorpackage.XORProtector
import java.nio.charset.Charset
import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {

    // первичная инициализация должна содержать хоть что-то, поэтому кладём null
    var protector: XORProtector = null

    println("XOR-шифратор строк")

    try {
      protector = new XORProtector(args(0))
    } catch {
      case index_error: ArrayIndexOutOfBoundsException => println("Недостаточно аргументов! Используйте: \n" +
        "Main.scala [password] [text]")
    }

    /**
    while( true ) {
      println("\nВведите строку для шифрования:")
      val text = StdIn.readLine()
      val enc = protector.encrypt_text(text)
      println(enc)
    }*/

    val open_text = "1234 hello world test testin test"
    println()
    println("Input:\n"+open_text)
    val open_bytes = open_text.getBytes()
    println()

    for (i <- 0 until open_bytes.length)
      print(open_bytes(i))
    println()

    val encrypted: Array[Byte] = protector.encryptTextToBytes(open_text)
    for (i <- 0 until encrypted.length)
      print(encrypted(i))

    println()

    val encrypted2: Array[Byte] = protector.encryptBytesToBytes(encrypted)
    for (i <- 0 until encrypted2.length)
      print(encrypted2(i))
    println()

    print("\nOutput:\n"+new String(encrypted2))

  }
}

