import checkout.Checkout

object Main {
  def main(args: Array[String]): Unit = {
    val result = Checkout.computeCost(args.toList)
    println(s"The total cost is ${result}p")
  }
}