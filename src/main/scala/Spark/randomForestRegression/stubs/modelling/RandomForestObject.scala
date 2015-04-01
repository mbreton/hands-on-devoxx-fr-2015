package spark.randomForestRegression.stubs.modelling
import spark.randomForestRegression.stubs.tools.Utilities._

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
   * @param featuresSubsetStrategy: Strategy to select a subset of the features for splitting (use "auto")
   * @param impurity: The impurity measure to select the best feature for splitting (use "variance" for regression)
   * @param maxDepthGrid: The maximum depth of each tree
   * @param maxBinsGrid: The maximum number of leaves for each tree
   * @return The best parameters found, in a tuple.
   */
  def gridSearchRandomForestRegressor(trainSet: RDD[LabeledPoint],
                                      valSet: RDD[LabeledPoint],
                                      categoricalFeaturesInfo: Map[Int, Int] = Map[Int, Int](),
                                      numTreesGrid: Array[Int] = Array(10),
                                      featuresSubsetStrategy: String = "auto",
                                      impurity: String = "variance",
                                      maxDepthGrid: Array[Int] = Array(2),
                                      maxBinsGrid: Array[Int] = Array(4)) = {

    // TODO 3 : Implement a grid search to find the best parameters and modify the result of the function

    val gridSearch =
      for (numTrees <- numTreesGrid;
           maxDepth <- maxDepthGrid;
           maxBins <- maxBinsGrid)
        yield {
          // TODO 3 : Run a randomForestTrainRegressor with the parameters on the train set

          // TODO 3 : Compute the RMSE on the validation set

          // TODO 3 : return the 3 parameters and the RMSE

        }

    // TODO 3 : get the parameters corresponding to the min RMSE

    // TODO 3 : Replace the following line with the best parameters found
    (categoricalFeaturesInfo, numTreesGrid(0), featuresSubsetStrategy, impurity, maxDepthGrid(0), maxBinsGrid(0))

  }

}
