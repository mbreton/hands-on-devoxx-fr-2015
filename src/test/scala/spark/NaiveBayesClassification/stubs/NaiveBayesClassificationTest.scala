package spark.NaiveBayesClassification.stubs

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{CancelAfterFailure, FunSuite}
import spark.naiveBayesClassification.stubs.NaiveBayesClassification

class NaiveBayesClassificationTest extends FunSuite with CancelAfterFailure with MockFactory {

  test("should read the file without problem") {
    val conf = new SparkConf().setAppName("SMS_Spam_Classification").setMaster("local[4]").set("spark.executor.memory", "6g")
    val sc = new SparkContext(conf)

    val m = stub[SparkContext]
    (m.textFile _).when("./source/sms_train.csv", 2).once()

    val classification: NaiveBayesClassification = new NaiveBayesClassification(m)
  }
}
