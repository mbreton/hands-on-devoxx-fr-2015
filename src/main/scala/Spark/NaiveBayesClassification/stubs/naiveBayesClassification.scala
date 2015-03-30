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
    // TODO : Read File ./source/sms_train.csv

    // Parsing & Feature Engineering
    // TODO : Modify the featureEngineering method in features/Engineering and parse the data

    // Splitting
    // TODO : Split the dataset in a train and a test set (proportions 0.75, 0.25)

    // Modelling
    // TODO : Train a Naive Bayes model on the train set

    // Evaluation
    // TODO : Calculate the precision and the confusion matrix after prediction on the test set.
    // TODO : Do the same for the train set for comparison

    // Print results

  }

}
