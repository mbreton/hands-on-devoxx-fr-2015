package spark.naiveBayesClassification.stubs.tools

import org.apache.spark.mllib.classification._
import org.apache.spark.mllib.evaluation._
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD


object Utilities {

  def getMetrics(model: NaiveBayesModel, data: RDD[LabeledPoint]): MulticlassMetrics = {

    val predictionsAndLabels = data.map(example => (model.predict(example.features), example.label))

    new MulticlassMetrics(predictionsAndLabels)
  }

}

