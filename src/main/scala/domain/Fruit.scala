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

case object Banana extends Fruit("Banana", 0.20) {
  def unapply(string: String): Option[Banana.type] =
    Option.when(string.toLowerCase == stringRepr.toLowerCase)(Banana)
}

object Fruit {
  def fromString: String => Either[String, Fruit] = {
    case Apple(fruit) => Right(fruit)
    case Orange(fruit) => Right(fruit)
    case Banana(fruit) => Right(fruit)
    case str => Left(s"Invalid argument [$str] is not a supported Fruit")
  }

  extension (f: Fruit)
    def buyOneGetOneFree(totalCount: Int): BigDecimal = {
      val remainder = totalCount % 2
      (totalCount / 2 + remainder) * f.cost
    }

    def threeForThePriceOfTwo(totalCount: Int): BigDecimal = {
      val remainder = totalCount % 3
      (totalCount / 3 * 2 + remainder) * f.cost
    }
}