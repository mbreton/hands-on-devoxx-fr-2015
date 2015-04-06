name := "test-scala"

version := "1.0"

scalaVersion := "2.11.2"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.3.0"

libraryDependencies += "org.apache.spark" %% "spark-mllib" % "1.3.0"

libraryDependencies += "joda-time" % "joda-time" % "2.7"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0"

//retrieveManaged := true


//unmanagedJars in Compile <<= baseDirectory map { base => (base ** "*.jar").classpath }
//
//resolvers += Resolver.file("publishTo", file("./ivy2")) transactional()
//
//externalResolvers := Resolver.withDefaultResolvers(resolvers.value, mavenCentral = false)