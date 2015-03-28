package spark.randomForestRegression.stubs.features

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD

import org.joda.time.DateTime


object Engineering {

  def featureEngineering(rdd: RDD[String]): RDD[LabeledPoint] = {

    val dataParsed = rdd.map {
      line =>

        val values = line.split(',')

        // TODO 3 : Create features from dates
        // val dateString = values(0).split(" ")(0)
        // val date = new DateTime(dateString)

        // val dayOfWeek = date.dayOfWeek().get() - 1
        // val month = date.monthOfYear().get() - 1
        // val year = date.year().get()

        // Convert all features but date to double
        val valuesNoDate = values.slice(1,values.size).map(_.toDouble)

        // Convert all season categories into one categorical feature
        val season = valuesNoDate.slice(0, 4).indexOf(1.0).toDouble

        // TODO 1 : Convert all weather categories into one categorical feature
        //val weather = valuesNoDate.slice(6, 10).indexOf(1.0).toDouble

        // Put all final features into a dense Vector
        // TODO 3 : Add date features to the array of features
        val array = Array(season) ++ valuesNoDate.slice(4,6) ++ Array(weather) ++ valuesNoDate.slice(10, valuesNoDate.size-1)
        val featureVector = Vectors.dense(array)
        val label = valuesNoDate.last

        LabeledPoint(label, featureVector)
    }

    dataParsed

  }

}