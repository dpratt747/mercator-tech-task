package checkout

import org.scalatest.Checkpoints.Checkpoint
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

final class CheckoutSpec extends AnyFunSpec with ScalaCheckPropertyChecks with Matchers {
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
      Checkout.computeCost(input) should ===(1.45)
    }
    it("should return price for buy one get one free apples") {
      val cp = new Checkpoint
      cp {
        Checkout.computeCost(List("apple", "apple", "apple", "apple")) should ===(1.20)
      }
      cp {
        Checkout.computeCost(List("apple", "apple", "apple")) should ===(1.20)
      }
      cp.reportAll()
    }
    it("should return price for buy one get one free bananas") {
      val cp = new Checkpoint
      cp {
        Checkout.computeCost(List("banana", "banana", "banana", "banana")) should ===(0.40)
      }
      cp {
        Checkout.computeCost(List("banana", "banana", "banana")) should ===(0.40)
      }
      cp.reportAll()
    }
    it("should ignore the cheapest one when a banana is bought together with an apple") {
      val cp = new Checkpoint
      cp {
        // banana is ignored as the apple is more expensive
        Checkout.computeCost(List("banana", "apple")) should ===(0.60)
      }
      cp {
        Checkout.computeCost(List("banana", "banana", "apple")) should ===(0.80)
      }
      cp {
        Checkout.computeCost(List("banana", "banana", "banana", "banana", "apple")) should ===(1.00)
      }
      cp {
        Checkout.computeCost(List("banana", "banana", "banana", "banana")) should ===(0.40)
      }
      cp.reportAll()
    }
    it("should return price for three for the price of two oranges") {
      val cp = new Checkpoint
      cp {
        Checkout.computeCost(List("orange", "orange", "orange")) should ===(0.5)
      }
      cp {
        Checkout.computeCost(List("orange", "orange", "orange", "orange", "orange", "orange")) should ===(1.0)
      }
      cp {
        Checkout.computeCost(List("orange", "orange", "orange", "orange", "orange")) should ===(1.0)
      }
      cp.reportAll()
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