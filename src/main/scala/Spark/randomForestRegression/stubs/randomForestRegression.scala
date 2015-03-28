package spark.randomForestRegression.stubs

import spark.randomForestRegression.solution.features.Engineering.featureEngineering
import org.apache.spark.{SparkContext, SparkConf}
import spark.randomForestRegression.stubs.tools.Utilities.{extractHeader, calculateRMSE}
import spark.randomForestRegression.stubs.modelling.RandomForestObject.{randomForestTrainRegressor, gridSearchRandomForestRegressor}

object randomForestRegression {

  def main(args: Array[String]): Unit = {

    // Setup Spark Configurations
    val conf = new SparkConf().setAppName("Bike_Demand_Prediction").setMaster("local[4]").set("spark.executor.memory", "6g")
    val sc = new SparkContext(conf)

    // Loading Data
    val data = sc.textFile("./source/bike_train.csv")

    // Parsing Data & Feature Engineering
    val schemaData = extractHeader(data)
    // TODO 1 : Modify the featureEngineering method in features/Engineering
    // TODO 3 : Modify the featureEngineering method in features/Engineering to add new features
    val dataParsed = featureEngineering(schemaData._2)

    // Splitting
    // TODO 1 : Split the dataset in a train, a validation and a test set (proportions 0.8, 0.1, 0.1)
    // val Array(trainSet, valSet, testSet) = dataParsed.randomSplit(Array(0.8, 0.1, 0.1))
    // trainSet.cache()
    // valSet.cache()

    // Model tuning
    // TODO 2 : Set better parameters of your choice
    // TODO 4 : Modify gridSearchRandomForestRegressor in modelling/RandomForestObject to perform grid search
    val categoricalFeaturesInfo = Map(0 -> 4, 3 -> 4)
    val numTreesGrid = Array(10)
    val maxDepthGrid = Array(2)
    val maxBinsGrid = Array(12)
    val bestParams = gridSearchRandomForestRegressor(trainSet, valSet,
      categoricalFeaturesInfo = categoricalFeaturesInfo, maxDepthGrid = maxDepthGrid,
      maxBinsGrid = maxBinsGrid, numTreesGrid = numTreesGrid)

    // Modelling
    val dataTrain = sc.union(trainSet, valSet)
    val model = (randomForestTrainRegressor _).tupled(bestParams)(dataTrain)

    // Evaluation
    // TODO 1 : Implement the calculateRMSE method in tools/Utilities
    // TODO 1 : Calculate the RMSE after prediction on the test set
    // TODO 1 : Do the same for the train set for comparison
    // val rmseTrain = calculateRMSE(model, dataTrain)
    // val rmseTest = calculateRMSE(model, testSet)


    // Show Evaluation results
    println(s"Best Parameters: ${bestParams}")
    println(s"Train Error: $rmseTrain")
    println(s"Test Error: $rmseTest")

  }


}
