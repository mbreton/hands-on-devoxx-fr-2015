import org.scalatest._

class KmeansSpec extends FlatSpec with Matchers {

  it should "compute correctly compute the distance" in {
    val distance: Double = Kmeans.distance(Point(0, 0), Point(3, 3))
    (Math.floor(distance * 100) / 100) should be(4.24)
  }


  it should "find the closest centroid" in {
    val point: Point = Point(0, 0)
    val centroids = List(Point(2, 2), Point(2, 3), Point(1, 1))
    val closestCentroid = Kmeans.findClosestCentroid(centroids, point)
    closestCentroid should be(Point(1, 1))
  }

  it should "group point by closest centroid" in {
    val clusters: List[List[Point]] = Kmeans.partitionUsingTheDistance(
      List(Point(0, 0), Point(4, 4)),
      List(Point(0, 0), Point(1, 0), Point(0, 1), Point(4, 4), Point(4, 3))
    )

    clusters should have length (2)
    clusters should contain(List(Point(4, 4), Point(4, 3)))
    clusters should contain(List(Point(0, 0), Point(1, 0), Point(0, 1)))
  }

  it should "return true if the previous centroids are very closed to the new ones" in {
    Kmeans.hasConverged(
      List(Point(0, 0), Point(4, 4)),
      List(Point(4.0001, 4.0001), Point(0.0001, 0.0001))
    ) should be(true)
  }

  it should "return false if the one the previous centroids are not very closed to the new ones" in {
    Kmeans.hasConverged(
      List(Point(0, 0), Point(4, 4)),
      List(Point(4.0001, 4.0001), Point(0.01, 0.01))
    ) should be(false)
  }

  it should "compute the new centroid based on the points in the cluster" in {
    val centroid: Point = Kmeans.determineNewCentroid(List(Point(1, 1), Point(2, 2), Point(1, 2), Point(2, 1)))
    centroid should be(Point(1.5, 1.5))
  }
}
