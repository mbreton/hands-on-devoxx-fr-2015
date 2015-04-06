package diy.naivebayes

object DateSetUtils {

  val wordOfMoreThanTwoLettersRegex = "[\\w\\d]{2,}".r
  val lineRegex = "(spam|ham)\\s(.*)".r
  val htmlCodePattern: String = "&.*?;"

  def fromRawToStructured(data: String): List[FlaggedMessage] = {
    data.split("\n").collect {
      case lineRegex(flag, content) => FlaggedMessage(isSpam = flag == "spam", content)
    }.toList
  }

  def toBagOfWord(text: String): Map[String, Int] = {
    sanitize(text).groupBy(identity).mapValues(_.length)
  }

  def sanitize(text: String): List[String] = {
    val textWithoutHtmlCode: String = text.toLowerCase.replaceAll(htmlCodePattern, " ")
    (wordOfMoreThanTwoLettersRegex findAllIn textWithoutHtmlCode).toList
  }
}
