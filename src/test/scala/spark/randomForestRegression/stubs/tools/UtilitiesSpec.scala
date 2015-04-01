package spark.randomForestRegression.stubs.tools

import org.apache.spark.{SparkContext, SparkConf}
import org.scalatest.FlatSpec


class UtilitiesSpec extends FlatSpec {

  def calculateRMSE() {
    val conf = new SparkConf().setAppName("Bike_Demand_Prediction").setMaster("local[4]").set("spark.executor.memory", "6g")
    val sc = new SparkContext(conf)

    it should "return 1" in {
      val rddToTest = sc.parallelize(Array((1d,2d),(2d,3d),(4d,5d)))

      assert(Utilities.calculateRMSE(rddToTest)==1d)

    }
  }

}
