package domain

sealed abstract class Fruit extends Product with Serializable {
  def cost: Double
}
case object Apple extends Fruit {
  override def cost: Double = 0.60
}
case object Orange extends Fruit {
  override def cost: Double = 0.25
}

object Fruit {
  def fromString(str: String): Either[String, Fruit] = {
    str.toLowerCase match
      case "apple" => Right(Apple)
      case "orange" => Right(Orange)
      case _ => Left(s"Invalid argument. [$str] is not a supported Fruit")
  }
}