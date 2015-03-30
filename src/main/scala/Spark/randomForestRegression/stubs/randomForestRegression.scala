package spark.randomForestRegression.stubs

import spark.randomForestRegression.solution.features.Engineering.featureEngineering
import org.apache.spark.{SparkContext, SparkConf}
import spark.randomForestRegression.stubs.tools.Utilities.{extractHeader, calculateRMSE}
import spark.randomForestRegression.stubs.modelling.RandomForestObject.{randomForestTrainRegressor, gridSearchRandomForestRegressor}

object RandomForestRegression {

  def main(args: Array[String]): Unit = {

    // Setup Spark Configurations
    val conf = new SparkConf().setAppName("Bike_Demand_Prediction").setMaster("local[4]").set("spark.executor.memory", "6g")
    val sc = new SparkContext(conf)

    // Loading Data
    // TODO 1 : Read File ./source/bike_train.csv

    // Parsing Data & Feature Engineering
    // TODO 1 : use function of package tools to extract header

    // TODO 1 : Modify the featureEngineering method in features/Engineering
    // TODO 3 : Modify the featureEngineering method in features/Engineering to add new features

    // TODO 1 : get the clean data

    // Splitting
    // TODO 1 : Split the dataset in a train, a validation and a test set (proportions 0.8, 0.1, 0.1)

    // Model tuning
    // TODO 1 : Set the parameters of your choice to test randomForestTrainRegressor
    // TODO 4 : Modify gridSearchRandomForestRegressor in modelling/RandomForestObject to perform grid search


    // Modelling
    // TODO 1 : Run randomForestRegressor (in package modelling) with the selected parameters

    // Evaluation
    // TODO 1 : Implement the calculateRMSE method in tools/Utilities
    // TODO 1 : Calculate the RMSE after prediction on the test set
    // TODO 1 : Do the same for the train set for comparison
    // val rmseTrain = calculateRMSE(model, dataTrain)
    // val rmseTest = calculateRMSE(model, testSet)


    // Show Evaluation results
    // TODO 1 : Print the results

  }


}
