import sqlContext.implicits._
import org.apache.spark.rdd.RDD

val sqlContext = new org.apache.spark.sql.SQLContext(sc)
// Changer le chemin du fichier
val textFile = sc.textFile("./hands-on-devoxx-fr-2015/source/bike_train.csv")

def extractHeader(rdd: RDD[String]): (String, RDD[String]) = {
   val schema = rdd.first()
   (schema, rdd.mapPartitionsWithIndex( (partitionIdx: Int, lines: Iterator[String]) => {
     if (partitionIdx == 0) {
       lines.drop(1)
     }
     else {
       lines
     }
   }))
}

val data = extractHeader(textFile)._2

case class Bike(date: String, season_1: String, season_2: String, season_3: String, 
    season_4: String, holiday: String, workingday: String, weather_1: String , 
    weather_2: String, weather_3: String, temperature: Double, humidity: Double, 
    windspeed: Double, count: Int)

val bike = data.map(_.split(",")).map(p => Bike(p(0), p(1), p(2), p(3), p(4), 
  p(5), p(6), p(7), p(8), p(9), p(10).trim.toDouble, p(11).trim.toDouble, 
  p(12).trim.toDouble, p(13).trim.toInt)).toDF()

bike.show()
bike.printSchema()
bike.select("date").show()
bike.select("date", "count").filter(bike("count") > 1000).show()
bike.groupBy("holiday").count().show()
