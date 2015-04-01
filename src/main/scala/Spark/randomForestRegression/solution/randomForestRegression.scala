package spark.randomForestRegression.solution

import spark.randomForestRegression.solution.features.Engineering.featureEngineering
import org.apache.spark.{SparkContext, SparkConf}
import spark.randomForestRegression.solution.tools.Utilities.{extractHeader, calculateRMSE}
import spark.randomForestRegression.solution.modelling.RandomForestObject.{randomForestTrainRegressor, gridSearchRandomForestRegressor}

object randomForestRegression {

  def main(args: Array[String]): Unit = {

    // Setup Spark Configurations
    val conf = new SparkConf()
      .setAppName("Bike_Demand_Prediction")
      .setMaster("local[4]")
      .set("spark.executor.memory", "6g")
    val sc = new SparkContext(conf)

    // Loading Data
    val data = sc.textFile("./source/bike_train.csv")

    // Parsing Data & Feature Engineering
    val schemaData = extractHeader(data)
    val dataParsed = featureEngineering(schemaData._2)

    // Splitting
    val Array(trainSet, valSet, testSet) = dataParsed.randomSplit(Array(0.8, 0.1, 0.1))
    trainSet.cache()
    valSet.cache()

    // Model tuning
    val categoricalFeaturesInfo = Map(0 -> 7, 1 -> 12, 3 -> 4, 6 -> 4)
    val numTreesGrid = Array(500, 1000)
    val maxDepthGrid = Array(20, 25, 30)
    val maxBinsGrid = Array(50, 100, 200)
    val bestParams = gridSearchRandomForestRegressor(trainSet, valSet,
      categoricalFeaturesInfo = categoricalFeaturesInfo, maxDepthGrid = maxDepthGrid,
      maxBinsGrid = maxBinsGrid, numTreesGrid = numTreesGrid)

    // Modelling
    val dataTrain = sc.union(trainSet, valSet)
    val model = (randomForestTrainRegressor _).tupled(bestParams)(dataTrain)

    // Evaluation
    val rmseTrain = calculateRMSE(model, dataTrain)
    val rmseTest = calculateRMSE(model, testSet)


    // Show Evaluation results
    println(s"Best Parameters: ${bestParams}")
    println(s"Train Error: $rmseTrain")
    println(s"Test Error: $rmseTest")

  }
}
