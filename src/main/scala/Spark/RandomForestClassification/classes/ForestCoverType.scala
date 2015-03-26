package spark.randomForestClassification.classes


/**
 *
 * Created by albanphelip on 19/03/15.
 */
case class ForestCoverType (Id: String,
                      Elevation: Double,
                      Aspect: Double,
                      Slope: Double,
                      Horizontal_Distance_To_Hydrology: Double,
                      Vertical_Distance_To_Hydrology: Double,
                      Horizontal_Distance_To_Roadways: Double,
                      Hillshade_9am: Int,
                      Hillshade_Noon: Int,
                      Hillshade_3pm: Int,
                      Horizontal_Distance_To_Fire_Points: Double,
                      Wilderness_Area: Double,
                      Soil_Type: Double,
                      Cover_Type: String
                       ) extends Product with Serializable {


  def productElement(n: Int) = n match {
    case 0 => Id
    case 1 => Elevation
    case 2 => Aspect
    case 3 => Slope
    case 4 => Horizontal_Distance_To_Hydrology
    case 5 => Vertical_Distance_To_Hydrology
    case 6 => Horizontal_Distance_To_Roadways
    case 7 => Hillshade_9am
    case 8 => Hillshade_Noon
    case 9 => Hillshade_3pm
    case 10 => Horizontal_Distance_To_Fire_Points
    case 11 => Wilderness_Area
    case 12 => Soil_Type
    case 13 => Cover_Type
  }

  def productArity: Int = 14

  def canEqual(that: Any): Boolean = that.isInstanceOf[ForestCoverType]

}
