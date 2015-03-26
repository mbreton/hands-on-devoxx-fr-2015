package modelling

import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.RandomForest
import org.apache.spark.mllib.tree.model.RandomForestModel
import org.apache.spark.rdd.RDD
import tools.Utilities._

/**
 * Created by Yoann on 24/02/15.
 */

object RandomForestObject {

  def randomForestTrainRegressor(categoricalFeaturesInfo: Map[Int, Int] = Map[Int, Int](),
                                   numTrees: Int = 100,
                                   featuresSubsetStrategy: String = "auto",
                                   impurity: String = "variance",
                                   maxDepth: Int = 10,
                                   maxBins: Int = 30)(input: RDD[LabeledPoint]) : RandomForestModel = {
    RandomForest.trainRegressor(input, categoricalFeaturesInfo, numTrees, featuresSubsetStrategy, impurity, maxDepth, maxBins)
  }


  def gridSearchRandomForestRegressor(trainSet: RDD[LabeledPoint], valSet: RDD[LabeledPoint], categoricalFeaturesInfo: Map[Int, Int] = Map[Int, Int](),
                                       numTreesGrid: Array[Int] = Array(100), featureSubsetStrategyGrid: Array[String] = Array("auto"),
                                       impurity: String = "variance", maxDepthGrid: Array[Int] = Array(10), maxBinsGrid: Array[Int] = Array(30)) = {

    val gridSearh =
      for (numTrees <- numTreesGrid;
           featureSubsetStrategy <- featureSubsetStrategyGrid;
           maxDepth <- maxDepthGrid;
           maxBins <- maxBinsGrid)
        yield {

          val model = RandomForest.trainRegressor(trainSet, categoricalFeaturesInfo,
            numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)

          val accuracyTrain = getMetrics(model, trainSet)
          val accuracyVal = getMetrics(model, valSet)

          ((numTrees, featureSubsetStrategy, maxDepth, maxBins), accuracyTrain, accuracyVal)
        }

    val params = gridSearh.sortBy(_._2).take(1)(0)._1
    val numTrees = params._1
    val featureSubsetStrategy = params._2
    val maxDepth = params._3
    val maxBins = params._4

    (categoricalFeaturesInfo, numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)

  }


  def bestParamsRandomForestRegressor(trainSet: RDD[LabeledPoint], valSet: RDD[LabeledPoint],
                                       computeGridSearch: Boolean = true,
                                       categoricalFeaturesInfo: Map[Int, Int] = Map[Int, Int](),
                                       numTreesGrid: Array[Int] = Array(100),
                                       featuresSubsetStrategyGrid: Array[String] = Array("auto"),
                                       impurity: String = "variance",
                                       maxDepthGrid: Array[Int] = Array(10),
                                       maxBinsGrid: Array[Int] = Array(30),
                                       overide: Boolean = false): (Map[Int,Int], Int, String, String, Int, Int) = {
    if (computeGridSearch) {
      gridSearchRandomForestRegressor(trainSet, valSet, categoricalFeaturesInfo, numTreesGrid, featuresSubsetStrategyGrid, impurity, maxDepthGrid, maxBinsGrid)
    }
    else {
      if (overide) {
        (categoricalFeaturesInfo, numTreesGrid(0), featuresSubsetStrategyGrid(0), impurity, maxDepthGrid(0), maxBinsGrid(0))
      }
      else {
        val numTrees = 100
        val featuresSubsetStrategy = "auto"
        val impurity = "variance"
        val maxDepth = 10
        val maxBins = 200

        (categoricalFeaturesInfo, numTrees, featuresSubsetStrategy, impurity, maxDepth, maxBins)
      }
    }
  }



}
