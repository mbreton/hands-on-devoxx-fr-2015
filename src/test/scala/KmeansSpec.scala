import collection.mutable.Stack
import org.scalatest._

class KmeansSpec extends FlatSpec with Matchers {

  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    stack.pop() should be (2)
    stack.pop() should be (1)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new Stack[Int]
    a [NoSuchElementException] should be thrownBy {
      emptyStack.pop()
    }
  }

  it should "compute correctly the distance" in {
    val distance: Double = Kmeans.distance(Point(0,0), Point(3,3))
    (Math.floor(distance*100)/ 100) should be (4.24)
  }
}