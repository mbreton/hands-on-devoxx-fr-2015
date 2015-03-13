package tools

/**
 * Created by Yoann on 04/03/15.
 */


import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.evaluation._
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.classification._


object Utilities {

  def getMetrics(model: NaiveBayesModel, data: RDD[LabeledPoint]): MulticlassMetrics = {
    val predictionsAndLabels = data.map(example => (model.predict(example.features), example.label))
    new MulticlassMetrics(predictionsAndLabels)
  }

}

