package spark.naiveBayesClassification.stubs.features

import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.feature.IDF
import org.apache.spark.mllib.linalg.{Vectors, Vector}


/**
 * Created by Yoann on 04/03/15.
 */

object Engineering {

  def featuresEngineering(data : RDD[String]): RDD[LabeledPoint] = {

    // Targets
    val targets = data.map(line => if (line.split("\t")(0) == "ham") 0.0 else 1.0)

    // RDD for the words in sms
    val sms: RDD[Seq[String]] = data.map(line => line.split("\t")(1)).map(_.split(" ").toSeq)

    // HashingTF
    val hashingTF = new HashingTF()
    val tf: RDD[Vector] = hashingTF.transform(sms)
    tf.cache()

    // TF-IDF
    val idf = new IDF().fit(tf)
    val tfidf: RDD[Vector] = idf.transform(tf)

    // Zip targets and features and convert to LabeledPoint
    val dataset = targets.zip(tfidf).map(x => LabeledPoint(x._1, x._2))

    dataset

  }

}