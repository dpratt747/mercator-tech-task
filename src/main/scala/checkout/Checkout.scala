package checkout

import domain.Fruit

trait CheckoutAlg {
  def computeCost: List[String] => Double
}

object Checkout extends CheckoutAlg {
  private def calculateCost(fruits: List[Fruit]): Double = fruits.map(_.cost).sum

  private def stringListToFruitsList(potentialFruits: List[String]): List[Fruit] = {
    val toFruits = potentialFruits.map(Fruit.fromString)
    // Can collect the failures and log them here
    toFruits.collect { case Right(fruit) => fruit }
  }

  override def computeCost: List[String] => Double = stringListToFruitsList andThen calculateCost
}
