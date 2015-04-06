package spark.randomForestRegression.stubs

import spark.randomForestRegression.stubs.features.Engineering.featureEngineering
import org.apache.spark.{SparkContext, SparkConf}
import spark.randomForestRegression.stubs.modelling.RandomForestObject.{randomForestTrainRegressor, gridSearchRandomForestRegressor}

object RandomForestRegression {

  def main(args: Array[String]): Unit = {

    // Setup Spark Configurations
    val conf = new SparkConf().setAppName("Bike_Demand_Prediction").setMaster("local[4]").set("spark.executor.memory", "6g")
    val sc = new SparkContext(conf)

    // Loading Data
    // TODO 1 : Read File ./source/bike_train.csv

    // Parsing Data
    // TODO 1 : Use function of in tools/Utilities to get an RDD without header

    // Feature Engineering
    // TODO 1 : Modify the featureEngineering method in features/Engineering and get the RDD[LabeledPoint]
    // TODO 2 : Modify the featureEngineering method in features/Engineering to add new features

    // Splitting
    // TODO 1 : Split the dataset in a train, a validation and a test set (proportions 0.8, 0.1, 0.1) using the 'randomSplit' method

    // Model tuning
    // TODO 2 : Modify categoricalFeaturesInfo to match the categorical features
    val categoricalFeaturesInfo = Map(0 -> 4, 3 -> 4)
    // TODO 3 : Modify gridSearchRandomForestRegressor in modelling/RandomForestObject to perform grid search and return the best parameters

    // Modelling
    // TODO 1 : Run randomForestTrainRegressor (method in modelling/RandomForestObject) with the parameters of your choice
    // TODO 3 : Run randomForestTrainRegressor (method in modelling/RandomForestObject) with the best parameters obtained

    // Evaluation
    // TODO 1 : Implement the calculateRMSE method in tools/Utilities
    // TODO 1 : Calculate the RMSE after prediction on the test set
    // TODO 1 : Do the same for the train set for comparison

    // Show Evaluation results
    // TODO 1 : Print the results

  }


}
