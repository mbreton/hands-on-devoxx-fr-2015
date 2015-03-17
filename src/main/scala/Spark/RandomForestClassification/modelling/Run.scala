package spark.randomForestClassification.modelling

import spark.randomForestClassification.features.Engineering.featureEngineering
import org.apache.spark.{SparkContext, SparkConf}
import spark.randomForestClassification.tools.Utilities.{extractHeader, getMetrics}
import spark.randomForestClassification.modelling.RandomForestObject.{randomForestTrainClassifier, bestParamsRandomForestClassifier}

/**
 *
 * Created by Yoann on 24/02/15.
 */

object Run {

  def main(args: Array[String]): Unit = {

    // Spark Configurations
    val conf = new SparkConf().setAppName("Forest Cover Type Prediction").setMaster("local[4]").set("spark.executor.memory", "6g")
    val sc = new SparkContext(conf)

    // Generating Data Paths
    val sep = System.getProperty("file.separator")
    val dataPathDir = s"${System.getenv("CHALLENGES_DATA")}${sep}Forest_Cover_Type${sep}"
    val dataPath = dataPathDir + "train.csv"
    val dataSubmissionPath = dataPathDir + "test.csv"

    // Loading Data
    val data = sc.textFile(dataPath)
    val dataSubmission = extractHeader(sc.textFile(dataSubmissionPath))._2

    // Extract Schema
    val schemaData = extractHeader(data)
    val schema = schemaData._1.split(",")

    // Parsing Data and Feature Engineering
    val dataParsed = featureEngineering(schemaData._2)
    val dataSubmissionParsed = featureEngineering(dataSubmission, isSubmission = true)

    // Spliting into train, validation and test sets
    val Array(trainValSet, testSet) = dataParsed.randomSplit(Array(0.9, 0.1))
    trainValSet.cache()

    // Modelling
    val categoricalFeaturesInfo = Map(10 -> 4, 11 -> 40)
    val bestParams = bestParamsRandomForestClassifier(trainValSet, numCross=3,
      computeGridSearch = true, categoricalFeaturesInfo = categoricalFeaturesInfo,
      maxDepthGrid = Array(20, 30), maxBinsGrid = Array(50, 100, 200),
      numTreesGrid = Array(10), overide = true)

    //val dataTrain = sc.union(trainSet, valSet)
    val modelBest = ((randomForestTrainClassifier _).tupled(bestParams))(trainValSet)

    // Evaluation
    val accuracyTrain = getMetrics(modelBest, trainValSet).precision
    val accuracyTest = getMetrics(modelBest, testSet).precision

    // Submission
    //val predSubmission = dataSubmissionParsed.map(example => (example.label, modelBest.predict(example.features) + 1))
    //predSubmission.saveAsTextFile(dataPathDir + "predictionRandomForest")

    // Show Evaluation results
    println(s"Best Parameters: ${bestParams}")
    println(s"Train Error: ${accuracyTrain}")
    println(s"Test Error: ${accuracyTest}")

  }

}
