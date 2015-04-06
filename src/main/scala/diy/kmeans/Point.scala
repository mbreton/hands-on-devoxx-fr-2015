package diy.kmeans

/**
 * Created by mbreton on 06/04/15.
 */
case class Point(x: Double, y: Double) {
  def +(p: Point): Point = new Point(x + p.x, y + p.y)
}
