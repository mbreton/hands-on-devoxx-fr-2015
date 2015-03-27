package spark.naiveBayesClassification.solution

import org.apache.spark.mllib.classification.NaiveBayes
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.{SparkConf, SparkContext}
import spark.naiveBayesClassification.solution.features.Engineering.featuresEngineering
import spark.naiveBayesClassification.solution.tools.Utilities.getMetrics

object NaiveBayesClassification {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("Forest Cover Type Prediction").setMaster("local[4]").set("spark.executor.memory", "6g")
    val sc = new SparkContext(conf)

    // Loading
    val sep = System.getProperty("file.separator")
    val dataPath = s"${System.getenv("DATASETS")}${sep}smsspamcollection${sep}SMSSpamCollection"

    val data = sc.textFile(dataPath)

    // Parsing & Feature Engineering
    val dataParsed = featuresEngineering(data)

    //Splitting
    val Array(trainSet, testSet) = dataParsed.randomSplit(Array(0.75, 0.25))
    trainSet.cache()

    //Modelling
    val model = NaiveBayes.train(trainSet, lambda = 1.0)

    // Evaluation
    val trainMetrics: MulticlassMetrics = getMetrics(model, trainSet)
    val testMetrics: MulticlassMetrics = getMetrics(model, testSet)

    val accuracyTrain = trainMetrics.precision
    val confusionTrain = trainMetrics.confusionMatrix
    val accuracyTest = testMetrics.precision
    val confusionTest = testMetrics.confusionMatrix

    // Print results
    println(s"Train Error: $accuracyTrain")
    println(s"Confusion Matrix on training set: \n $confusionTrain")
    println(s"Test Error: $accuracyTest")
    println(s"Confusion Matrix on test set: \n $confusionTest")
  }

}
