import org.scalatest.FunSuite

class NaiveBayes$Test extends FunSuite {
  test("should parse correctly sms file") {
    val flaggedMessages = NaiveBayes.fromRawToStructured(
      """
        |spam test
        |ham test2
        |spam
        |ham test3
      """.stripMargin)

    assert(flaggedMessages.size == 3)
    assert(flaggedMessages.head == FlaggedMessage(isSpam = true, "test"))
    assert(flaggedMessages(1) == FlaggedMessage(isSpam = false, "test2"))
    assert(flaggedMessages(2) == FlaggedMessage(isSpam = false, "test3"))
  }

  test("removePunctuation should clean up the flagged message's content") {
    assert(NaiveBayes.sanitize("&lt;FOO&gt;&amp;") == List("foo"))
    assert(NaiveBayes.sanitize("yeah!!!") == List("yeah"))
    assert(NaiveBayes.sanitize("a yeah!!!") == List("yeah"))
  }

  test("toBagOfWords should group words by number of occurrence") {
    val bagOfWords = NaiveBayes.toBagOfWord("Bar   qix , Bar &amp; Foo !!! Bar fOo")
    assert(bagOfWords.size == 3)
    assert(bagOfWords("bar") == 3)
    assert(bagOfWords("foo") == 2)
    assert(bagOfWords("qix") == 1)
  }
}
