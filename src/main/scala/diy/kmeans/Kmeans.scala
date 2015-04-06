package diy.kmeans

import scala.util.Random

object Kmeans {

  def main(args: Array[String]) {
    def dataSet: DataSet = generateDataSet(4, 30)
    findCentroids(2, dataSet)
  }

  def generateDataSet(numClusters: Int, sizePerCluster: Int): DataSet = {
    new DataSet((1 to numClusters).map(buildCluster(sizePerCluster)).toList)
  }

  def buildCluster(nbPointInCluster: Int): (Int) => Cluster = {
    { clusterIdx: Int =>
      val centroid = Point(randFloat(-1, 1), randFloat(-1, 1))
      val points = generateRadialCluster(centroid, nbPointInCluster, 100)
      Cluster(centroid, points)
    }
  }

  def randFloat(a: Double, b: Double): Double = {
    def t = Math.random()
    t * a + (1 - t) * b
  }

  def generateRadialCluster(centroid: Point, clusterSize: Int, radius: Double): List[Point] = {
    def noise(centroid: Point, point: Point): Point = {
      def d = distance(centroid, point)
      if (d < 0.1) {
        new Point(point.x + randFloat(0.1, 0.2), point.y + randFloat(0.1, 0.2))
      } else {
        point
      }
    }
    def radius = 0.2
    (1 to clusterSize).map { x =>
      def d = randFloat(0, radius)
      def alpha = randFloat(0, 2 * Math.PI)
      noise(centroid, new Point(centroid.x + d * Math.cos(alpha), centroid.y + d * Math.sin(alpha)))
    }
      .filter(p => -1 <= p.x && p.x <= 1 && -1 <= p.y && p.y <= 1)
      .toList
  }

  // solution

  def distance(p1: Point, p2: Point): Double = {
    Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2))
  }

  def findClosestCentroid(centroids: List[Point], p: Point): Point = {
    centroids.minBy(distance(_, p))
  }

  def partitionUsingTheDistance(centroids: List[Point], points: List[Point]): List[Cluster] = {
    points.groupBy(findClosestCentroid(centroids, _)).map {
      case (centroid, pointsOfCluster) => Cluster(centroid, pointsOfCluster)
    }.toList
  }

  def determineNewCentroid(points: List[Point]): Point = {
    def summedPoint = points.reduce(_ + _)
    new Point(summedPoint.x / points.size, summedPoint.y / points.size)
  }

  def updateCentroids(clusters: List[Cluster]): List[Point] = {
    clusters.map((cluster) => determineNewCentroid(cluster.points))
  }

  def pickStartingCentroids(points: List[Point], numberOfStartingPoint: Int): List[Point] = {
    Random.shuffle(points).take(numberOfStartingPoint)
  }

  def findCentroids(numberOfCluster: Int, dataSet: DataSet) = {
    def points: List[Point] = dataSet.clusters.map(_.points).reduce(_ ++ _)
    def updateCentroidWhileHasNotConverged(numOfIteration: Int, previousCentroids: List[Point]): List[Point] = {
      val clusters = partitionUsingTheDistance(previousCentroids, points)
      val centroids = updateCentroids(clusters)
      if (numOfIteration < 1000 && !hasConverged(previousCentroids, centroids)) {
        updateCentroidWhileHasNotConverged(numOfIteration + 1, centroids)
      }
      else {
        centroids
      }
    }
    updateCentroidWhileHasNotConverged(0, pickStartingCentroids(points, numberOfCluster))
  }

  def hasConverged(previousCentroids: List[Point], centroids: List[Point]): Boolean = {
    val sortedPrevious = previousCentroids.sortBy(norm)
    val sortedCentroids = centroids.sortBy(norm)
    (sortedPrevious zip sortedCentroids).forall({ case (previousCentriod, newCentroid) =>
      distance(previousCentriod, newCentroid) < 0.001
    })
  }

  def norm(point: Point): Double = {
    distance(point, new Point(0, 0))
  }
}
