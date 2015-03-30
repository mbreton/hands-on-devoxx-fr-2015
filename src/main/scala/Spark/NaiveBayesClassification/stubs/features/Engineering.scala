package spark.naiveBayesClassification.stubs.features

import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.feature.IDF
import org.apache.spark.mllib.linalg.{Vectors, Vector}

object Engineering {

  val SPAM = "spam"

  def featureEngineering(data : RDD[String]): RDD[LabeledPoint] = {

    // TODO : Create the targets rdd, composed of doubles (1.0 if "SPAM", 0.0 else)

    // RDD of words in sms
    val smsRDD: RDD[Seq[String]] = data.map(line => line.split("\t")(1)).map(_.split(" ").toSeq)

    // HashingTF
    val hashingTF = new HashingTF()
    val tf: RDD[Vector] = hashingTF.transform(smsRDD)
    tf.cache()

    // TF-IDF
    val idf = new IDF().fit(tf)
    val tfidf: RDD[Vector] = idf.transform(tf)

    // TODO : Zip targets and features and convert to LabeledPoint in a map (replace the following line with the right rdd)
    smsRDD.map(l => LabeledPoint(0, Vectors.dense(Array(0d))))
  }

}