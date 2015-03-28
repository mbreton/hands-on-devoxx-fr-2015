package spark.naiveBayesClassification.stubs.features

import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.feature.IDF
import org.apache.spark.mllib.linalg.{Vectors, Vector}

object Engineering {

  val SPAM = "spam"

  def featureEngineering(data : RDD[String]): RDD[LabeledPoint] = {

    // TODO :
    //val targets = data.map(line => if (line.split("\t")(0) == SPAM) 1.0 else 0.0)

    // RDD of words in sms
    val smsRDD: RDD[Seq[String]] = data.map(line => line.split("\t")(1)).map(_.split(" ").toSeq)

    // HashingTF
    val hashingTF = new HashingTF()
    val tf: RDD[Vector] = hashingTF.transform(smsRDD)
    tf.cache()

    // TF-IDF
    val idf = new IDF().fit(tf)
    val tfidf: RDD[Vector] = idf.transform(tf)

    // TODO : Zip targets and features and convert to LabeledPoint in a map
    // targets.zip(tfidf).map(x => LabeledPoint(x._1, x._2))

    smsRDD.map(l => LabeledPoint(0, Vectors.dense(Array(0d))))
  }

}