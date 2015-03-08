import scala.util.Random

object Kmeans {
  type NoiseFunction = (Point, Point) => Point
  def noiseIdentityFunction: NoiseFunction = (centroid, point) => point

  def main(args: Array[String]) {
    def noise: NoiseFunction = { (centroid, point) =>
      def d = distance(centroid, point)
      if (d < 0.1) {
        new Point(point.x + randFloat(0.1, 0.2), point.y + randFloat(0.1, 0.2))
      } else {
        point
      }
    }
    def dataset:Plan = kmeansGenerateDataset(4, 30, noise)
    val result:KmeansResult = kmeans(2, dataset.points)
  }

  def kmeansGenerateDataset(numClusters: Int, sizePerCluster: Int, noise: NoiseFunction): Plan = {
    var points: List[Point] = Nil // TODO: Bad
    var centroids: List[Point] = Nil // TODO: Bad
    (1 to numClusters) foreach { x =>
      def centroid = new Point(randFloat(-1, 1), randFloat(-1, 1))
      def rawPoint: List[Point] = generateRadialCluster(centroid, sizePerCluster, 100)
      points = points ++ rawPoint
        .map(rawPoint => noise(centroid, rawPoint))
        .filter( p => -1 <= p.x && p.x <= 1 && -1 <= p.y && p.y <= 1)
      centroids = centroids :+ centroid
    }
    new Plan(points, centroids)
  }

  def randFloat(a: Double, b: Double): Double = {
    def t = Math.random()
    t * a + (1 - t) * b
  }

  def generateRadialCluster(centroid: Point, clusterSize: Int, radius: Double): List[Point] = {
    def radius = 0.2
    (1 to clusterSize).toList map { x =>
      def d = randFloat(0, radius)
      def alpha = randFloat(0, 2 * Math.PI)
      new Point(centroid.x + d * Math.cos(alpha), centroid.y + d * Math.sin(alpha))
    }
  }

  // solution

  def distance(p1: Point, p2: Point): Double = {
    Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2))
  }

  def findClosestCentroid(centroids: List[Point], p: Point): Point = {
    centroids.minBy(distance(_, p))
  }

  def partitionUsingTheDistance(centroids: List[Point], points: List[Point]): List[List[Point]] = {
    points.groupBy(findClosestCentroid(centroids, _)).values.toList
  }

  def determineNewCentroid(points: List[Point]): Point = {
    def summedPoint = points.fold(points.head) (_+_)
    new Point(summedPoint.x / points.size, summedPoint.y / points.size)
  }

  def updateCentroids(clusters: List[List[Point]]): List[Point] = {
    clusters.map(determineNewCentroid)
  }

  def pickStartingCentroids(points: List[Point], numberOfStartingPoint: Int): List[Point] = {
    Random.shuffle(points).take(numberOfStartingPoint)
  }


  def kmeans(numberOfCluster: Int, points: List[Point]) = {
    var centroids: List[Point] = pickStartingCentroids(points, numberOfCluster) // TODO BAD !!
    var numOfIteration = 0 // TODO BAD !!
    var previousCentroids: List[Point] = centroids // TODO BAD !!

    while (numOfIteration < 1000 && !hasConverged(previousCentroids, centroids)) {
      val partitioned = partitionUsingTheDistance(previousCentroids, points)
      previousCentroids = centroids
      centroids = updateCentroids(partitioned)
      // TODO : Dispatch an event to inform of the current state
      numOfIteration = numberOfCluster + 1
    }

    new KmeansResult(centroids, partitionUsingTheDistance(centroids, points))
  }

  def hasConverged(previousCentroids: List[Point], centroids: List[Point]): Boolean = {
      var sortedPrevious = previousCentroids.sortBy(norm)
      var sortedCentroids = centroids.sortBy(norm)
      (sortedPrevious zip sortedCentroids).forall({ case (previousCentriod, newCentroid) =>
        distance(previousCentriod, newCentroid) > 0.001
      })
  }

  def norm(point: Point): Double = {
    distance(point, new Point(0, 0))
  }
}

case class Plan(points: List[Point], centroids: List[Point])

case class Point(x: Double, y: Double) {
  def + (p:Point):Point = new Point (x + p.x, y + p.y)
}

case class KmeansResult(centroids: List[Point], clusters: List[List[Point]])
