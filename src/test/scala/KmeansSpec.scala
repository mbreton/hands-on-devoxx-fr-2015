import org.scalatest._

import scala.collection.mutable.Stack

class KmeansSpec extends FlatSpec with Matchers {

  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    stack.pop() should be(2)
    stack.pop() should be(1)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new Stack[Int]
    a[NoSuchElementException] should be thrownBy {
      emptyStack.pop()
    }
  }

  it should "compute correctly compute the distance" in {
    val distance: Double = Kmeans.distance(Point(0, 0), Point(3, 3))
    (Math.floor(distance * 100) / 100) should be(4.24)
  }


  it should "find the closest centroid" in {
    val point: Point = Point(0, 0)
    val centroids = Point(2, 2) :: Point(2, 3) :: Point(1, 1) :: Nil
    val closestCentroid = Kmeans.findClosestCentroid(centroids, point)
    closestCentroid should be(Point(1, 1))
  }

  it should "should group point by closest centroid" in {
    val clusters: List[List[Point]] = Kmeans.partitionUsingTheDistance(
      Point(0, 0) :: Point(4, 4) :: Nil,
      Point(0, 0) :: Point(1, 0) :: Point(0, 1) :: Point(4, 4) :: Point(4, 3) :: Nil
    )

    clusters should have length(2)
    clusters should contain(List(Point(4, 4),Point(4, 3)))
    clusters should contain(List(Point(0, 0),Point(1, 0), Point(0, 1)))
  }
}
