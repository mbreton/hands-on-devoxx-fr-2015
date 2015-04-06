package diy.kmeans

case class Point(x: Double, y: Double) {
  def +(p: Point): Point = new Point(x + p.x, y + p.y)
}
