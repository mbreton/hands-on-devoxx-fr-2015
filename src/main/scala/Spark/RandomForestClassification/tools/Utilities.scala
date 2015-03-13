package tools

import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.model.RandomForestModel
import org.apache.spark.rdd.RDD

/**
 * Created by Yoann on 24/02/15.
 */

object Utilities {

  def extractHeader(rdd: RDD[String]): (String, RDD[String]) = {

    // Take the first line (csv schema)
    val schema = rdd.first()

    // Construct dataset without the first line
    return (schema, rdd.mapPartitionsWithIndex(
      (partitionIdx: Int, lines: Iterator[String]) => {
        if (partitionIdx == 0) {
          lines.drop(1)
        }
        else {
          lines
        }
      }))
  }


  def getMetrics(model: RandomForestModel, data: RDD[LabeledPoint]): MulticlassMetrics = {
    val predictionsAndLabels = data.map(example => (model.predict(example.features), example.label))
    return new MulticlassMetrics(predictionsAndLabels)
  }


  def buildTrainSetCrossValidation(foldsCrossValidation: Array[RDD[LabeledPoint]], numCross: Int, labelValidationFold: Int) = {
    var trainSet = {
      if (labelValidationFold != 0) {
        foldsCrossValidation(0)
      }
      else {
        foldsCrossValidation(1)
      }
    }

    if (labelValidationFold != 0) {
      for (k <- 1 to numCross-1) {
        if (k != labelValidationFold) {
          trainSet = trainSet.union(foldsCrossValidation(k))
        }
      }
    }
    else {
      for (k <- 2 to numCross-1) {
        trainSet = trainSet.union(foldsCrossValidation(k))
      }
    }
    trainSet
  }

}
