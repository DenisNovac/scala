import java.util.regex.Pattern

import scala.util.matching.Regex
import scala.util.matching.Regex.Match

object Regex {
  // простейшая замена
  val reg1 = new Regex("John")
  val str1 = "My name is John"
  reg1 replaceFirstIn(str1, "Rahul") // My name is Rahul

  val reg2 = new Regex("(G|g)fG")
  val str2 = "GfG is a CS portal. I like gfG"
  (reg2 findAllIn str2 ).mkString(", ")  // список всех вхождений [GfG, gfG]

  val str3 = "AA"
  val str4 = "A"
  val reg3 = "A?$".r  // преобразование в Regex с синтаксисом
  reg3 matches str3  // false
  reg3 matches str4  // true

  val reg4: Regex = "([0-9a-zA-Z-#() ]+): ([0-9a-zA-Z-#() ]+)".r
  val str5 =
    """background-color: #A03300;
      |background-image: url(img/header100.png);
      |background-position: top center;
      |background-repeat: repeat-x;
      |background-size: 2160px 108px;
      |margin: 0;
      |height: 108px;
      |width: 100%;""".stripMargin

  // Сюда попали все строчки. Самое главное тут то, что мы их разделили по сепаратору на группы
  /*for (patternMatch <- reg4.findAllMatchIn(str5))
    println(s"key: ${patternMatch.group(1)} value: ${patternMatch.group(2)}")*/


  // PM можно использовать так
  val str6 = "Testing Test Tests Test"

  ".*Test$".r findFirstMatchIn(str6) match {
    case Some(_) => "Test at the end"
    case None => "none"
  }

  // наоборот нельзя, на лету сравнить по многим паттернам ней выйдет
  // существует проект Kaleidoscope для этого:
  /*path match {
    case r"/images/.*" => println("image")
    case r"/styles/.*" => println("stylesheet")
    case _ => println("something else")
  }*/

  // разбор паттерна на группы
  val reg5 = "(.+)@(.+)\\.(.+)".r
  "name@example.com" match {
    case reg5(userName, domain, topDomain) => s"Hi $userName from $domain.$topDomain"
    case _ => println(s"This is not a valid email.")
  } // Hi name from example.com



}