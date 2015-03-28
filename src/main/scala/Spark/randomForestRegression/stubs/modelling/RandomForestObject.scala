package spark.randomForestRegression.stubs.modelling
import spark.randomForestRegression.stubs.tools.Utilities._

import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.RandomForest
import org.apache.spark.mllib.tree.model.RandomForestModel
import org.apache.spark.rdd.RDD


object RandomForestObject {

  def randomForestTrainRegressor(categoricalFeaturesInfo: Map[Int, Int] = Map[Int, Int](),
                                 numTrees: Int = 10,
                                 featuresSubsetStrategy: String = "auto",
                                 impurity: String = "variance",
                                 maxDepth: Int = 2,
                                 maxBins: Int = 12)(input: RDD[LabeledPoint]) : RandomForestModel = {
    RandomForest.trainRegressor(input, categoricalFeaturesInfo, numTrees, featuresSubsetStrategy, impurity, maxDepth, maxBins)
  }


  def gridSearchRandomForestRegressor(trainSet: RDD[LabeledPoint],
                                      valSet: RDD[LabeledPoint],
                                      categoricalFeaturesInfo: Map[Int, Int] = Map[Int, Int](),
                                      numTreesGrid: Array[Int] = Array(10),
                                      featureSubsetStrategyGrid: Array[String] = Array("auto"),
                                      impurity: String = "variance",
                                      maxDepthGrid: Array[Int] = Array(2),
                                      maxBinsGrid: Array[Int] = Array(4)) = {

    // TODO 4 : Implement a grid search to find the best parameters and modify the result of the function
//    val gridSearch =
//
//      for (numTrees <- numTreesGrid;
//           featureSubsetStrategy <- featureSubsetStrategyGrid;
//           maxDepth <- maxDepthGrid;
//           maxBins <- maxBinsGrid)
//        yield {
//
//          val model = RandomForest.trainRegressor(trainSet, categoricalFeaturesInfo, numTrees, featureSubsetStrategy,
//            impurity, maxDepth, maxBins)
//
//          val accuracyTrain = calculateRMSE(model, trainSet)
//          val accuracyVal = calculateRMSE(model, valSet)
//
//          ((numTrees, featureSubsetStrategy, maxDepth, maxBins), accuracyTrain, accuracyVal)
//        }
//
//    val params = gridSearch.sortBy(_._2).take(1)(0)._1
//    val numTrees = params._1
//    val featureSubsetStrategy = params._2
//    val maxDepth = params._3
//    val maxBins = params._4

    (categoricalFeaturesInfo, numTreesGrid(0), featureSubsetStrategyGrid(0), impurity, maxDepthGrid(0), maxBinsGrid(0))

  }

}
