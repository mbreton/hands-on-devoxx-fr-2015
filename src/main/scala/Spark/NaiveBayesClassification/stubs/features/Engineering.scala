package spark.naiveBayesClassification.stubs.features

import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.feature.IDF
import org.apache.spark.mllib.linalg.{Vectors, Vector}

object Engineering {

  val SPAM = "spam"

  def featureEngineering(data : RDD[String]): RDD[LabeledPoint] = {

    val targets = data.map(line => if (line.split("\t")(0) == SPAM) 1.0 else 0.0)

    // RDD of words in sms
    val smsRDD: RDD[Seq[String]] = data.map(line => line.split("\t")(1)).map(_.split(" ").toSeq)

    // Convert sms to dictionary of words thanks to TF-IDF transformation
    val hashingTF = new HashingTF()
    val tf: RDD[Vector] = hashingTF.transform(smsRDD)
    tf.cache()
    val idf = new IDF().fit(tf)
    val tfidf: RDD[Vector] = idf.transform(tf)

    // Zip targets and features
    val datasetRDD = targets.zip(tfidf)

    // TODO : Convert datasetRDD to a RDD[LabeledPoint] thanks to a map (replace the following line with the right map)
    datasetRDD.map(l => LabeledPoint(0, Vectors.dense(Array(0d))))
  }

}