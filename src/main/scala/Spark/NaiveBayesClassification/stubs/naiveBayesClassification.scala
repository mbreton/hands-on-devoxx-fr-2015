package spark.naiveBayesClassification.stubs

import org.apache.spark.mllib.classification.NaiveBayes
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import spark.naiveBayesClassification.stubs.features.Engineering.featureEngineering
import spark.naiveBayesClassification.stubs.tools.Utilities.getMetrics

class NaiveBayesClassification(sc:SparkContext) {

    // Loading Data
    // TODO : Read File ./source/sms_train.csv
    val data = sc.textFile("./source/sms_train.csv")


    // Feature Engineering
    // TODO : Complete the featureEngineering method in features/Engineering and call it on the loaded data

    // Splitting
    // TODO : Split the dataset in a train and a test set (proportions 0.75, 0.25) using the 'randomSplit' method

    // Modelling
    // TODO : Train a Naive Bayes model on the train set

    // Evaluation
    // TODO : Call the getMetrics method in tools/Utilities on the test set
    // TODO : Call the precision and confusionMatrix parameters to know the classification accuracy
    // TODO : Do the same for the train set for comparison

    // TODO : print results
}
