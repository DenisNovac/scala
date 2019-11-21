package xorpackage

class XORProtector(password: String) {

  def getPassword(): String = {
    this.password
  }

  def encrypt_text(text: String): String = {
    var encrypted: String = "" // эту строчку вернём после выполнения

    var j = 0 // итератор по строке с паролем
    for (i <- 0 until text.length) {

      val t = text(i).toByte
      val p = this.password(j).toByte
      val xor = t ^ p
      encrypted+=xor.toChar

      j+=1
      if (j == this.password.length())
        j=0 // если пароль закончился раньше строки - прогоняем его заново
    }
    encrypted
  }


  def encryptTextToBytes(text: String): Array[Byte] = {
    val textb = text.getBytes()
    val encrypted = new Array[Byte](textb.length)
    val password = this.password.getBytes()



    var j = 0 // итератор по строке с паролем
    for (i <- 0 until text.length) {

      val t = textb(i)
      val p = password(j)
      val xor = t ^ p

      encrypted(i) = xor.toByte

      j+=1
      if (j == password.length)
        j=0 // если пароль закончился раньше строки - прогоняем его заново
    }
    encrypted
  }


  def encryptBytesToBytes(text: Array[Byte]): Array[Byte] = {
    val encrypted = new Array[Byte](text.length)
    val password = this.password.getBytes()

    var j = 0 // итератор по строке с паролем
    for (i <- 0 until text.length) {

      val t = text(i)
      val p = password(j)
      val xor = t ^ p

      encrypted(i) = xor.toByte

      j+=1
      if (j == password.length)
        j=0 // если пароль закончился раньше строки - прогоняем его заново
    }
    encrypted
  }
}