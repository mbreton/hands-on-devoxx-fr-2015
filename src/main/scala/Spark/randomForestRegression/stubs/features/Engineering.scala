package spark.randomForestRegression.stubs.features

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD

import org.joda.time.DateTime


object Engineering {

  /**
   * Perform feature engineering : Prepare and create features
   * @param rdd A RDD containing the raw data
   * @return A RDD[LabeledPoint]
   */
  def featureEngineering(rdd: RDD[String]) : RDD[LabeledPoint] = {

    val dataParsed = rdd.map {
      line =>

        val values = line.split(',')

        // TODO 2 : Create new features from dateString (joda time is in the build.sbt)
        val dateString = values(0).split(" ")(0)
        val date = new DateTime(dateString)

        // Convert all features but date to double
        val valuesNoDate = values.slice(1,values.length).map(_.toDouble)

        // Convert all season categories into one categorical feature
        val season = valuesNoDate.slice(0, 4).indexOf(1.0).toDouble

        // TODO 1 : Convert all weather categories into one categorical feature

        // Put all final features into a dense Vector
        // TODO 1 : Put all features into an array and convert it to Vectors
        // TODO 2 : Add date features to the array of features
        //val array = Array(season) ++ valuesNoDate.slice(4,6) ++ Array(weather) ++ valuesNoDate.slice(10, valuesNoDate.size-1)
        //val featureVector = Vectors.dense(array)

        val label = valuesNoDate.last

        // TODO 1 : Return proper LabeledPoint
        LabeledPoint(0, Vectors.dense(Array(0d)))
    }

    dataParsed

  }

}
