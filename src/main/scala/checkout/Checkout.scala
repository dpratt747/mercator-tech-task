package checkout

import domain.*

trait CheckoutAlg {
  def computeCost: List[String] => BigDecimal
}

object Checkout extends CheckoutAlg {

  private def calculateCost(fruits: List[Fruit]): BigDecimal = {

    val allFruits: Map[Fruit, Int] = fruits.groupBy(identity).view.mapValues(_.length).toMap

    val totalCountOfBananas = allFruits.getOrElse(Banana, 0)
    val totalCountOfOranges = allFruits.getOrElse(Orange, 0)
    val totalCountOfApples = allFruits.getOrElse(Apple, 0)

    // remove the cheapest item which at this point is a banana
    val bananaCost = if (totalCountOfBananas > 0 && totalCountOfApples > 0) {
      Banana.buyOneGetOneFree(totalCountOfBananas - 1)
    } else Banana.buyOneGetOneFree(totalCountOfBananas)

    val appleCost = Apple.buyOneGetOneFree(totalCountOfApples)

    val orangeCost = Orange.threeForThePriceOfTwo(totalCountOfOranges)

    appleCost + orangeCost + bananaCost
  }

  private def stringListToFruitsList(potentialFruits: List[String]): List[Fruit] = {
    val toFruitsEither = potentialFruits.map(Fruit.fromString)
    // Can collect the failures and log them here
    toFruitsEither.collect { case Right(fruit) => fruit }
  }

  override def computeCost: List[String] => BigDecimal = stringListToFruitsList andThen calculateCost
}
