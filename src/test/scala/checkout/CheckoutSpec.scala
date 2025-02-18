package checkout

import org.scalatest.EitherValues
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

final class CheckoutSpec extends AnyFunSpec with ScalaCheckPropertyChecks with Matchers with EitherValues {
  describe("checkout computeCost success") {
    it("should take an empty list and return a cost of 0p") {
      val input = List.empty[String]
      Checkout.computeCost(input) should ===(0)
    }
    it("should take a single Apple and return a cost of 60p") {
      val input = List("Apple")
      Checkout.computeCost(input) should ===(0.60)
    }
    it("should take a single Orange and return a cost of 25p") {
      val input = List("Orange")
      Checkout.computeCost(input) should ===(0.25)
    }
    it("should calculate the total cost when provided a valid list of more than one strings 1") {
      val input = List("orange", "apple")
      Checkout.computeCost(input) should ===(0.85)
    }
    it("should calculate the total cost when provided a valid list of more than one strings 2") {
      val input = List("orange", "apple", "apple", "apple")
      Checkout.computeCost(input) should ===(2.05)
    }
  }

  describe("checkout computeCost failures") {
    it("should return 0 when passed an invalid fruit") {
      val input = List("grapes")
      Checkout.computeCost(input) should ===(0)
    }
    it("should filter out invalid fruits") {
      val input = List("apple", "orange", "grapes")
      Checkout.computeCost(input) should ===(0.85)
    }
  }

}