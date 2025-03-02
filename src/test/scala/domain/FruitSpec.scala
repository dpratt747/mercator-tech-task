package domain

import domain.FruitSpec.*
import org.scalacheck.Gen
import org.scalatest.Checkpoints.Checkpoint
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

final class FruitSpec extends AnyFunSpec with ScalaCheckPropertyChecks with Matchers {
  describe("fruit success") {
    it("should take an Apple string and return its ADT encoding") {
      forAll(validAppleFruitString){ string =>
        Fruit.fromString(string) should ===(Right(Apple))
      }
    }
    it("should take an Orange string and return its ADT encoding") {
      forAll(validOrangeFruitString) { string =>
        Fruit.fromString(string) should ===(Right(Orange))
      }
    }
    it("should take an Banana string and return its ADT encoding") {
      forAll(validBannaFruitString) { string =>
        Fruit.fromString(string) should ===(Right(Banana))
      }
    }
    it("buy one get one free should return the correct amount") {
      val cp = new Checkpoint
      cp { Banana.buyOneGetOneFree(2) should ===(Banana.cost) }
      cp { Banana.buyOneGetOneFree(3) should ===(Banana.cost * 2) }
      cp { Banana.buyOneGetOneFree(4) should ===(Banana.cost * 2) }
      cp { Apple.buyOneGetOneFree(2) should ===(Apple.cost) }
      cp { Apple.buyOneGetOneFree(3) should ===(Apple.cost * 2) }
      cp { Apple.buyOneGetOneFree(4) should ===(Apple.cost * 2) }
      cp.reportAll()
    }
    it("three for the price of two should return the correct amount") {
      val cp = new Checkpoint
      cp { Orange.threeForThePriceOfTwo(3) should ===(Orange.cost * 2) }
      cp { Orange.threeForThePriceOfTwo(4) should ===(Orange.cost * 3) }
      cp { Orange.threeForThePriceOfTwo(6) should ===(Orange.cost * 4) }

      cp { Apple.threeForThePriceOfTwo(3) should ===(Apple.cost * 2) }
      cp { Apple.threeForThePriceOfTwo(4) should ===(Apple.cost * 3) }
      cp { Apple.threeForThePriceOfTwo(6) should ===(Apple.cost * 4) }

      cp { Banana.threeForThePriceOfTwo(3) should ===(Banana.cost * 2) }
      cp { Banana.threeForThePriceOfTwo(4) should ===(Banana.cost * 3) }
      cp { Banana.threeForThePriceOfTwo(6) should ===(Banana.cost * 4) }

      cp.reportAll()
    }
  }
  describe("fruit failure") {
    it("should take return an error when passed an unsupported string") {
      forAll(invalidFruitString) { string =>
        val failureString = s"Invalid argument [$string] is not a supported Fruit"
        Fruit.fromString(string) should ===(Left(failureString))
      }
    }
  }
}

object FruitSpec {

  private val validAppleFruitString: Gen[String] = Gen.oneOf("Apple", "APPLE", "ApPle")
  private val validOrangeFruitString: Gen[String] = Gen.oneOf("Orange", "ORANGE", "OrANge")
  private val validBannaFruitString: Gen[String] = Gen.oneOf("Banana", "banana", "baNana")
  private val invalidFruitString: Gen[String] = Gen.oneOf("Melon", "Grape", "Pear")

}
