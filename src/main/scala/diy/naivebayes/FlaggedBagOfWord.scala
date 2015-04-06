package diy.naivebayes

/**
 * Represent a flagged bag of word
 * @param isSpam Specify if the message is a spam or not
 * @param occurrences Word occurrences by word
 */
case class FlaggedBagOfWord(isSpam: Boolean, occurrences: Map[String, Int])
