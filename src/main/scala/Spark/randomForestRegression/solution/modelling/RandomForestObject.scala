package spark.randomForestRegression.solution.modelling
import spark.randomForestRegression.solution.tools.Utilities._

import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.RandomForest
import org.apache.spark.mllib.tree.model.RandomForestModel
import org.apache.spark.rdd.RDD


object RandomForestObject {

  /**
   * Train a Random Forest Regressor
   * @param input: RDD[LabeledPoint] - The training set
   * @param categoricalFeaturesInfo: A Map indicating which features are categorical and how many categories they contain
   * @param numTrees: The number of trees to train
   * @param featuresSubsetStrategy: Strategy to select a subset of the features for splitting (use "auto")
   * @param impurity: The impurity measure to select the best feature for splitting (use "variance" for regression)
   * @param maxDepth: The maximum depth of each tree
   * @param maxBins: The maximum number of leaves for each tree
   * @return A RandomForestRegressor Model, usable to predict new data
   */
  def randomForestTrainRegressor(categoricalFeaturesInfo: Map[Int, Int] = Map[Int, Int](),
                                 numTrees: Int = 10,
                                 featuresSubsetStrategy: String = "auto",
                                 impurity: String = "variance",
                                 maxDepth: Int = 2,
                                 maxBins: Int = 12)(input: RDD[LabeledPoint]) : RandomForestModel = {
    RandomForest.trainRegressor(input, categoricalFeaturesInfo, numTrees, featuresSubsetStrategy, impurity, maxDepth, maxBins)
  }


  /**
   * Perform a Grid Search to find the best parameters for the Random Forest
   * @param trainSet: RDD[LabeledPoint] - The training set
   * @param valSet: RDD[LabeledPoint] - The validation set
   * @param categoricalFeaturesInfo: A Map indicating which features are categorical and how many categories they contain
   * @param numTreesGrid: The number of trees to train
   * @param featuresSubsetStrategyGrid: Strategy to select a subset of the features for splitting (use "auto")
   * @param impurity: The impurity measure to select the best feature for splitting (use "variance" for regression)
   * @param maxDepthGrid: The maximum depth of each tree
   * @param maxBinsGrid: The maximum number of leaves for each tree
   * @return The best parameters found, in a tuple.
   */
  def gridSearchRandomForestRegressor(trainSet: RDD[LabeledPoint],
                                      valSet: RDD[LabeledPoint],
                                      categoricalFeaturesInfo: Map[Int, Int] = Map[Int, Int](),
                                      numTreesGrid: Array[Int] = Array(10),
                                      featuresSubsetStrategyGrid: Array[String] = Array("auto"),
                                      impurity: String = "variance",
                                      maxDepthGrid: Array[Int] = Array(2),
                                      maxBinsGrid: Array[Int] = Array(4)) = {

    val gridSearch =

      for (numTrees <- numTreesGrid;
           featuresSubsetStrategy <- featuresSubsetStrategyGrid;
           maxDepth <- maxDepthGrid;
           maxBins <- maxBinsGrid)
        yield {

          val model = RandomForest.trainRegressor(trainSet, categoricalFeaturesInfo, numTrees, featuresSubsetStrategy,
            impurity, maxDepth, maxBins)

          val accuracyVal = calculateRMSE(model, valSet)

          ((numTrees, featuresSubsetStrategy, maxDepth, maxBins), accuracyVal)
        }

    val params = gridSearch.sortBy(_._2).take(1)(0)._1
    val numTrees = params._1
    val featuresSubsetStrategy = params._2
    val maxDepth = params._3
    val maxBins = params._4

    (categoricalFeaturesInfo, numTrees, featuresSubsetStrategy, impurity, maxDepth, maxBins)

  }

}
