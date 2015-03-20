package modelling

import features.Engineering.featureEngineering
import org.apache.spark.{SparkContext, SparkConf}
import tools.Utilities.{extractHeader, getMetrics}
import modelling.RandomForestObject.randomForestTrainRegressor

/**
 * Created by Yoann on 24/02/15.
 */

object Run {

  def main(args: Array[String]): Unit = {

    // Spark Configurations
    val conf = new SparkConf().setAppName("Forest Cover Type Prediction").setMaster("local[4]").set("spark.executor.memory", "6g")
    val sc = new SparkContext(conf)

    // Generating Data Paths
    val sep = System.getProperty("file.separator")
    val dataPathDir = s"${System.getenv("CHALLENGES_DATA")}${sep}Bike_Sharing_Demand${sep}"
    val dataPath = dataPathDir + "bike_train.csv"

    // Loading Data
    val data = sc.textFile(dataPath)

    // Extract Schema
    val schemaData = extractHeader(data)
    val schema = schemaData._1.split(",")

    // Parsing Data and Feature Engineering
    val dataParsed = featureEngineering(schemaData._2)

    // Spliting into train, validation and test sets
    val Array(trainValSet, testSet) = dataParsed.randomSplit(Array(0.9, 0.1))
    trainValSet.cache()

    // Modelling
    val categoricalFeaturesInfo = Map(0 -> 4, 3 -> 4)
//    val bestParams = bestParamsRandomForestRegressor(trainValSet, numCross=3,
//      computeGridSearch = true, categoricalFeaturesInfo = categoricalFeaturesInfo,
//      maxDepthGrid = Array(20, 30), maxBinsGrid = Array(50, 100, 200),
//      numTreesGrid = Array(10), overide = true)
//
//    val modelBest = ((randomForestTrainRegressor _).tupled(bestParams))(trainValSet)
    val modelBest = randomForestTrainRegressor(categoricalFeaturesInfo = categoricalFeaturesInfo)(trainValSet)

    // Evaluation
    val accuracyTrain = getMetrics(modelBest, trainValSet)
    val accuracyTest = getMetrics(modelBest, testSet)


    // Show Evaluation results
    //println(s"Best Parameters: ${bestParams}")
    println(s"Train Error: ${accuracyTrain}")
    println(s"Test Error: ${accuracyTest}")

  }

}
