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


  def gridSearchRandomForestRegressor(trainValSet: RDD[LabeledPoint],
                                       numCross: Int = 5,
                                       categoricalFeaturesInfo: Map[Int, Int] = Map[Int, Int](),
                                       numTreesGrid: Array[Int] = Array(100),
                                       featuresSubsetStrategyGrid: Array[String] = Array("auto"),
                                       impurityGrid: Array[String] = Array("variance"),
                                       maxDepthGrid: Array[Int] = Array(10),
                                       maxBinsGrid: Array[Int] = Array(30)) = {

    val foldsCrossValidation = trainValSet.randomSplit(Array.fill[Double](numCross)(1.toFloat/numCross))

    val gridSearch =
      for (numTrees <- numTreesGrid;
           featuresSubsetStrategy <- featuresSubsetStrategyGrid;
           impurity <- impurityGrid;
           maxDepth <- maxDepthGrid;
           maxBins <- maxBinsGrid)
      yield {



        val mseValCross =
          for (cross <- 0 to numCross-1)
          yield {

            val valSet = foldsCrossValidation(cross)
            val trainSet = buildTrainSetCrossValidation(foldsCrossValidation, numCross, cross)
            val model = RandomForest.trainRegressor(trainSet, categoricalFeaturesInfo, numTrees, featuresSubsetStrategy, impurity, maxDepth, maxBins)

            getMetrics(model, valSet)
          }

        val mseValMean = mseValCross.reduce(_+_).toFloat / numCross

        ((numTrees, featuresSubsetStrategy, impurity, maxDepth, maxBins), mseValMean)
      }

    val params = gridSearch.sortBy(_._2).take(1)(0)._1
    val numTrees = params._1
    val featuresSubsetStrategy = params._2
    val impurity = params._3
    val maxDepth = params._4
    val maxBins = params._5

    (categoricalFeaturesInfo, numTrees, featuresSubsetStrategy, impurity, maxDepth, maxBins)
  }


  def bestParamsRandomForestRegressor(trainValSet: RDD[LabeledPoint],
                                       numCross: Int = 5,
                                       computeGridSearch: Boolean = true,
                                       categoricalFeaturesInfo: Map[Int, Int] = Map[Int, Int](),
                                       numTreesGrid: Array[Int] = Array(100),
                                       featuresSubsetStrategyGrid: Array[String] = Array("auto"),
                                       impurityGrid: Array[String] = Array("variance"),
                                       maxDepthGrid: Array[Int] = Array(10),
                                       maxBinsGrid: Array[Int] = Array(30),
                                       overide: Boolean = false) = {
    if (computeGridSearch) {
      gridSearchRandomForestRegressor(trainValSet, numCross, categoricalFeaturesInfo, numTreesGrid, featuresSubsetStrategyGrid, impurityGrid, maxDepthGrid, maxBinsGrid)
    }
    else {
      if (overide) {
        (categoricalFeaturesInfo, numTreesGrid(0), featuresSubsetStrategyGrid(0), impurityGrid(0), maxDepthGrid(0), maxBinsGrid(0))
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
