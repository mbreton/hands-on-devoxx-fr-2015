package features

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD
import tools.Utilities._

/**
 * Created by Yoann on 24/02/15.
 */

object Engineering {

  def featureEngineering(rdd: RDD[String], isSubmission: Boolean = false): RDD[LabeledPoint] = {

    val dataParsed = rdd.map {
      line =>

        val values = line.split(',').map(_.toDouble)

        // Convert all wilderness categories into one categorical feature
        val wilderness = values.slice(11, 15).indexOf(1.0).toDouble
        // Convert all soil categories into one categorical feature
        val soil = values.slice(15, 55).indexOf(1.0).toDouble

        val featureVector = Vectors.dense(values.slice(1,11) :+ wilderness :+ soil)
        val label = if (isSubmission) values(0) else values.last-1

        LabeledPoint(label, featureVector)
    }

    dataParsed

  }

}
