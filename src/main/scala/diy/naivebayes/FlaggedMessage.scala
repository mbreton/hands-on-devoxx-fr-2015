package diy.naivebayes

/**
 * Represent a parsed message come from dataset
 * @param isSpam Specify if the message is a spam or not
 * @param content Message's content
 */
case class FlaggedMessage(isSpam: Boolean, content: String)
