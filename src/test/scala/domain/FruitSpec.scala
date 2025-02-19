package domain

import domain.FruitSpec.*
import org.scalacheck.Gen
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
  private val invalidFruitString: Gen[String] = Gen.oneOf("Banana", "Grape", "Pear")

}
