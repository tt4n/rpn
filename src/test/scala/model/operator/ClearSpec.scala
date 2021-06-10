package model.operator

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.collection.mutable

class ClearSpec extends AnyFlatSpec with should.Matchers {
  behavior of "Clear"

  it should "pop all parameters from parameter stack" in {
    val testStack = mutable.Stack[Double]()
    testStack.push(1.0)
    testStack.push(2.0)
    testStack.push(3.0)
    testStack.push(4.0)

    val params = Clear.getParams(testStack)
    params shouldEqual(Seq(1.0, 2.0, 3.0, 4.0))
    testStack.size shouldEqual 0
  }

  it should "clear all parameters" in {
    val params = Seq(1.0, 2.0, 3.0, 4.0)

    Clear.processParams(params) shouldEqual(Seq())
  }
}
