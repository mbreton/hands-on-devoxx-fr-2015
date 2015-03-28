package spark.randomForestRegression.solution.features


import org.joda.time.DateTime


object Test {

  def main(args: Array[String]): Unit ={

    val dateString = "2015-03-23 00:00:00"

    val date = new DateTime(dateString.split(" ")(0))

    println(date.dayOfWeek().get())





  }

}
