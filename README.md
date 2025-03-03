### Running the application:

e.g.

```bash
sbt "run apple orange" 
# should return 0.85p
```

```bash
sbt "run apple orange apple apple" 
# should return 1.45p
```

### Running the tests:

```bash
sbt test
```

-----

### Additional Changes That Can be Made:

The addition of .all to Fruit
```scala
  val all: Vector[Fruit] = Vector(Apple, Orange, Banana)
```

Make the min fruit logic a bit more generic:
```scala
    val minFruit = Fruit.all.minBy(_.cost)

    def getCostIgnoringCheapest(f: Int => BigDecimal, totalCount: Int): BigDecimal = {
      if (totalCountOfBananas > 0 && totalCountOfApples > 0) {
        f(totalCount - 1)
      } else f(totalCount)
    }

    val minFruitCost = minFruit match {
      case fruit @Apple =>
        getCostIgnoringCheapest(fruit.buyOneGetOneFree, totalCountOfApples)
      case fruit @Orange =>
        getCostIgnoringCheapest(fruit.threeForThePriceOfTwo, totalCountOfOranges)
      case fruit @Banana =>
        getCostIgnoringCheapest(fruit.buyOneGetOneFree, totalCountOfBananas)
    }
```
