package features

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD

/**
 * Created by Yoann on 24/02/15.
 */

object Engineering {

  def featureEngineering(rdd: RDD[String]): RDD[LabeledPoint] = {

    val dataParsed = rdd.map {
      line =>

        val values = line.split(',')

        // Convert all features but date to double
        val valuesNoDate = values.slice(1,values.size).map(_.toDouble)

        // Convert all season categories into one categorical feature
        val season = valuesNoDate.slice(0, 4).indexOf(1.0).toDouble

        // Convert all weather categories into one categorical feature
        val weather = valuesNoDate.slice(6, 10).indexOf(1.0).toDouble

        // Put all final features into a dense Vector
        val featureVector = Vectors.dense((season +: valuesNoDate.slice(4,6) :+ weather) ++ valuesNoDate.slice(10, valuesNoDate.size-1))
        val label = valuesNoDate.last

        LabeledPoint(label, featureVector)
    }

    dataParsed

  }

}
