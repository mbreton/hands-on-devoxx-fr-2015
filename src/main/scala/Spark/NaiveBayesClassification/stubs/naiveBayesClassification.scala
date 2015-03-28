package spark.naiveBayesClassification.stubs

import org.apache.spark.mllib.classification.NaiveBayes
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import spark.naiveBayesClassification.stubs.features.Engineering.featureEngineering
import spark.naiveBayesClassification.stubs.tools.Utilities.getMetrics

object NaiveBayesClassification {

  def main(args: Array[String]): Unit = {

    // Setup Spark configurations
    val conf = new SparkConf().setAppName("SMS_Spam_Classification").setMaster("local[4]").set("spark.executor.memory", "6g")
    val sc = new SparkContext(conf)

    // Loading Data
    val data = sc.textFile("./source/sms_train.csv")

    // Parsing & Feature Engineering
    // TODO : Modify the featureEngineering method in features/Engineering
    val dataParsed: RDD[LabeledPoint] = featureEngineering(data)

    // Splitting
    // TODO : Split the dataset in a train and a test set (proportions 0.75, 0.25)
    // val Array(trainSet, testSet) = dataParsed.randomSplit(Array(0.75, 0.25))
    // trainSet.cache()

    // Modelling
    // TODO : Train a Naive Bayes model on the train set
    // val model = NaiveBayes.train(trainSet, lambda = 1.0)

    // Evaluation
    // TODO : Calculate the precision and the confusion matrix after prediction on the test set.
    // TODO : Do the same for the train set for comparison
    // val trainMetrics: MulticlassMetrics = getMetrics(model, trainSet)
    // val testMetrics: MulticlassMetrics = getMetrics(model, testSet)

    // val accuracyTrain = trainMetrics.precision
    // val confusionTrain = trainMetrics.confusionMatrix
    // val accuracyTest = testMetrics.precision
    // val confusionTest = testMetrics.confusionMatrix

    // Print results
    println(s"Train Error: $accuracyTrain")
    println(s"Confusion Matrix on training set: \n $confusionTrain")
    println(s"Test Error: $accuracyTest")
    println(s"Confusion Matrix on test set: \n $confusionTest")
  }

}
