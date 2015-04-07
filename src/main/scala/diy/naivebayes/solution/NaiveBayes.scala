package diy.naivebayes.solution

import diy.naivebayes.{DateSetUtils, FlaggedBagOfWord}

import scala.util.Try


class NaiveBayes(flaggedBagsOfWord: List[FlaggedBagOfWord] = List()) {

  val totalNumberOfMsg = flaggedBagsOfWord.length
  val numberOfSpam = flaggedBagsOfWord.count(_.isSpam)
  val numberOfHam = totalNumberOfMsg - numberOfSpam
  var count = Map(true -> numberOfSpam, false -> numberOfHam)
  val occurrences =
    if (flaggedBagsOfWord.nonEmpty)
      bagsOfWordToNumberOfOccurrencesByMsgType(flaggedBagsOfWord)
    else
      Map[Boolean, Map[String, Int]](true -> Map(), false -> Map())

  val spamOccurences = occurrences(true)
  val hamOccurences = occurrences(false)


  /**
   * This utility method will be used to merge occurrence lists's bag of words.
   * The expected result should contain the sum of values mapped by keys.
   *
   * See test in {@link diy.naivebayes.NaiveBayesSpec} for a real case.
   *
   * @param firstOccurrenceList
   * @param bagOfWord
   * @return The merged occurrence list
   */
  def mergeTwoOccurrenceList(firstOccurrenceList: Map[String, Int], bagOfWord: FlaggedBagOfWord): Map[String, Int] = {
    firstOccurrenceList ++ bagOfWord.occurrences.map { case (k, v) =>
      k -> (v + firstOccurrenceList.getOrElse(k, 0))
    }
  }

  /**
   * This method have to transform a bag of word list into number
   * of word occurrences by message type...
   * To do this, we advise in a first time to group by message type the bags of words
   * and then to merge all the occurrence list thanks to {@link mergeTwoOccurrenceList}
   *
   * See test in {@link diy.naivebayes.NaiveBayesSpec} for a real case.
   *
   * @param flaggedBagsOfWords Bag of word list
   * @return Frequency of word by message type
   */
  def bagsOfWordToNumberOfOccurrencesByMsgType(flaggedBagsOfWords: List[FlaggedBagOfWord]): Map[Boolean, Map[String, Int]] = {
    val booleanToFlaggedBagOfWord = flaggedBagsOfWords.groupBy(_.isSpam)
    booleanToFlaggedBagOfWord.mapValues(_.foldLeft(Map[String, Int]())(mergeTwoOccurrenceList))
  }

  /**
   * The p function compute the probability of a message's type.
   * To implement it, you can use the {@link count} and
   * the {@link totalNumberOfMsg} fields.
   *
   * Take care to return a Double and not an Int !
   *
   * See test in {@link diy.naivebayes.NaiveBayesSpec} for a real case.
   *
   * @param isSpam Define the message's type
   * @return The computed probability
   */
  def p(isSpam: Boolean): Double = {
    count(isSpam).toDouble / totalNumberOfMsg
  }

  /**
   * The pWord function compute the probability of a word knowing that the type of
   * its message.
   * To implement it, you can use the {@link count},
   * the {@link spamOccurences}, and the {@link hamOccurences} fields.
   *
   * Take care to return a Double and not an Int !
   *
   * See test in {@link diy.naivebayes.NaiveBayesSpec} for a real case.
   *
   * @param word The given word
   * @param isSpam The type of the message containing this word
   * @return The probability of the given word knowing the type of its message
   */
  def pWord(word: String, isSpam: Boolean): Double = {
    Try {
      val flaggedOccurrences = if (isSpam) spamOccurences else hamOccurences
      flaggedOccurrences(word).toDouble / count(isSpam)
    }.getOrElse(0.0001)
  }

  /**
   * The second p function compute the probability of a message knowing that its type
   * To implement it, you can use the {@link diy.naivebayes.DateSetUtils#toBagOfWord} method and then
   * compute the product of the probabilities of each word
   *
   * Take care to return a Double and not an Int !
   *
   * See test in {@link diy.naivebayes.NaiveBayesSpec} for a real case.
   *
   * @param isSpam Define the message's type
   * @return The computed probability
   */
  def p(msg: String, isSpam: Boolean): Double = {
    DateSetUtils.toBagOfWord(msg).foldLeft(1.0) {
      case (probability, (word, occ)) => probability * pWord(word, isSpam)
    }
  }

  /**
   * This last method should compute is the given msg is a spam or not !
   *
   * Remember : K = (P(M|S=1)*P(S=1))/(P(M|S=0)*P(P(S=0))
   *
   * @param msg The content of the sms
   * @return If it's a spam
   */
  def isSpam(msg: String): Boolean = {
    ((p(msg, true) * p(true)) / (p(msg, false) * p(false))) > 1
  }
}
