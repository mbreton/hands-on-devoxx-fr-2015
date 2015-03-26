package spark.randomForestRegression.stubs

import org.apache.spark.{SparkConf, SparkContext}
import spark.randomForestRegression.stubs.features.Engineering.featureEngineering
import spark.randomForestRegression.stubs.modelling.RandomForestObject.{bestParamsRandomForestRegressor, randomForestTrainRegressor}
import spark.randomForestRegression.stubs.tools.Utilities.{extractHeader, getMetrics}

object randomForestRegression {

   def main(args: Array[String]): Unit = {

     // Spark Configurations
     val conf = new SparkConf().setAppName("Forest Cover Type Prediction").setMaster("local[4]").set("spark.executor.memory", "6g")
     val sc = new SparkContext(conf)

     // Generating Data Paths
     val sep = System.getProperty("file.separator")
     val dataPathDir = s"${System.getenv("CHALLENGES_DATA")}${sep}"
     //val dataPath = dataPathDir + "bike_train.csv"
     val dataPath = "./source/bike_train.csv"


     // Loading Data
     val data = sc.textFile(dataPath)

     // Extract Schema
     val schemaData = extractHeader(data)
     val schema = schemaData._1.split(",")

     // Parsing Data and Feature Engineering
     val dataParsed = featureEngineering(schemaData._2)

     // Spliting into train, validation and test sets
     val Array(trainSet, valSet, testSet) = dataParsed.randomSplit(Array(0.8, 0.1, 0.1))
     trainSet.cache()
     valSet.cache()

     // Modelling
     val categoricalFeaturesInfo = Map(0 -> 7, 1 -> 12, 3 -> 4, 6 -> 4)
     val bestParams = bestParamsRandomForestRegressor(trainSet, valSet, computeGridSearch = true,
       categoricalFeaturesInfo = categoricalFeaturesInfo, maxDepthGrid = Array(20, 30),
       maxBinsGrid = Array(50, 100, 200), numTreesGrid = Array(100), overide = true)

     val dataTrain = sc.union(trainSet, valSet)
     val modelBest = (randomForestTrainRegressor _).tupled(bestParams)(dataTrain)
     //val modelBest = randomForestTrainRegressor(categoricalFeaturesInfo = categoricalFeaturesInfo)(dataTrain)

     // Evaluation
     val accuracyTrain = getMetrics(modelBest, dataTrain)
     val accuracyTest = getMetrics(modelBest, testSet)


     // Show Evaluation results
     println(s"Best Parameters: ${bestParams}")
     println(s"Train Error: ${accuracyTrain}")
     println(s"Test Error: ${accuracyTest}")

   }


 }
