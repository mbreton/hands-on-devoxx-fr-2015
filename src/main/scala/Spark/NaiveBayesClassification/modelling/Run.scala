package spark.naiveBayesClassification.modelling

/**
 *
 * Created by Yoann on 04/03/15.
 */


import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.mllib.classification.NaiveBayes
import spark.naiveBayesClassification.tools.Utilities.getMetrics
import spark.naiveBayesClassification.features.Engineering.featuresEngineering


object Run {


  def main(args: Array[String]) : Unit = {

    val conf = new SparkConf().setAppName("Forest Cover Type Prediction").setMaster("local[4]").set("spark.executor.memory", "6g")
    val sc = new SparkContext(conf)

    // Loading
    val sep = System.getProperty("file.separator")
    val dataPathDir = s"${System.getenv("DATASETS")}${sep}smsspamcollection${sep}"
    val dataPath = dataPathDir + "SMSSpamCollection"

    val data = sc.textFile(dataPath)

    // Parsing & Feature Engineering
    val dataParsed =  featuresEngineering(data)

    //Spliting
    val Array(trainSet, testSet) = dataParsed.randomSplit(Array(0.75, 0.25))
    trainSet.cache()

    //Modelling
    val model = NaiveBayes.train(trainSet, lambda = 1.0)

    //Evaluation
    val accuracyTrain = getMetrics(model, trainSet).precision
    val confusionTrain = getMetrics(model, trainSet).confusionMatrix
    val accuracyTest = getMetrics(model, testSet).precision
    val confusionTest = getMetrics(model, testSet).confusionMatrix

    // Print results
    println(s"Train Error: ${accuracyTrain}")
    println(s"Confusion Matrix on training set: \n ${confusionTrain}")
    println(s"Test Error: ${accuracyTest}")
    println(s"Confusion Matrix on test set: \n ${confusionTest}")

  }

}
