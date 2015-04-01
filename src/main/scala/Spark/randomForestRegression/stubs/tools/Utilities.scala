package spark.randomForestRegression.stubs.tools

import org.apache.spark.SparkContext._
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.model.RandomForestModel
import org.apache.spark.rdd.RDD

object Utilities {

  /**
   * Extract header of a dataset
   * @param rdd A RDD with a header inside
   * @return A tuple2. First element of the tuple is the header. Second element is the data.
   */
  def extractHeader(rdd: RDD[String]): (String, RDD[String]) = {

    // Take the first line (csv schema)
    val schema = rdd.first()

    // Remove first line from first partition only
    (schema, rdd.mapPartitionsWithIndex( (partitionIdx: Int, lines: Iterator[String]) => {
      if (partitionIdx == 0) {
        lines.drop(1)
      }
      else {
        lines
      }
    }))
  }


  /**
   * Calculate the Root Mean Square Error
   * @param model A trained Random Forest Model
   * @param data A RDD of LabeledPoint to be tested
   * @return The RMSE
   */
  def calculateRMSE(model: RandomForestModel, data: RDD[LabeledPoint]): Double = {

    val predictionsAndLabels = data.map(example => (model.predict(example.features), example.label))

    // TODO 1 : Calculate the RMSE and change return
    0d
  }

}
