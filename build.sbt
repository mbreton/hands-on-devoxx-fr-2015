name := "test-scala"

version := "1.0"

scalaVersion := "2.11.2"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.2.0"

libraryDependencies += "org.apache.spark" %% "spark-mllib" % "1.2.0"

//libraryDependencies += "com.opencsv" %% "opencsv" % "3.3"

libraryDependencies += "joda-time" % "joda-time" % "2.7"
