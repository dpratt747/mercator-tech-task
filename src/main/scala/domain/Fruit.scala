package domain

sealed abstract class Fruit(val stringRepr: String, val cost: BigDecimal) extends Product with Serializable

case object Apple extends Fruit("Apple", 0.60) {
  def unapply(string: String): Option[Apple.type] =
    Option.when(string.toLowerCase == stringRepr.toLowerCase)(Apple)
}

case object Orange extends Fruit("Orange", 0.25) {
  def unapply(string: String): Option[Orange.type] =
    Option.when(string.toLowerCase == stringRepr.toLowerCase)(Orange)
}

object Fruit {
  def fromString: String => Either[String, Fruit] = {
    case Apple(fruit) => Right(fruit)
    case Orange(fruit) => Right(fruit)
    case str => Left(s"Invalid argument [$str] is not a supported Fruit")
  }
}