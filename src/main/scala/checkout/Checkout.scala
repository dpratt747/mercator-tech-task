package checkout

import domain.*

trait CheckoutAlg {
  def computeCost: List[String] => BigDecimal
}

object Checkout extends CheckoutAlg {

  private def calculateCost(fruits: List[Fruit]): BigDecimal = {
    val (totalCountOfApples, totalCountOfOranges) = fruits.foldLeft((0, 0)) {
      case ((appleCount, orangeCount), item) => item match {
        // increment fruit type by one
        case Apple => (appleCount + 1, orangeCount)
        case Orange => (appleCount, orangeCount + 1)
      }
    }

    val appleRemainder = totalCountOfApples % 2
    val appleCost = (totalCountOfApples / 2 + appleRemainder) * Apple.cost

    val orangeRemainder = totalCountOfOranges % 3
    val orangeCost = (totalCountOfOranges / 3 * 2 + orangeRemainder) * Orange.cost

    appleCost + orangeCost
  }

  private def stringListToFruitsList(potentialFruits: List[String]): List[Fruit] = {
    val toFruitsEither = potentialFruits.map(Fruit.fromString)
    // Can collect the failures and log them here
    toFruitsEither.collect { case Right(fruit) => fruit }
  }

  override def computeCost: List[String] => BigDecimal = stringListToFruitsList andThen calculateCost
}
