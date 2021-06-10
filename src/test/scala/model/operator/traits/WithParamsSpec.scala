package model.operator.traits

import exception.InsufficientParametersException
import model.operator.Add
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.collection.mutable

class WithParamsSpec extends AnyFlatSpec with should.Matchers {
  behavior of "WithParams"

  it should "get the right number of parameters from the parameter stack" in {
    val op = new WithParams {
      override val paramCount: Int = 2
    }

    val testStack = mutable.Stack[Double]()
    testStack.push(1.0)
    testStack.push(2.0)
    testStack.push(3.0)

    val params = op.getParams(testStack)

    params shouldEqual(Seq(2.0, 3.0))
    testStack.size shouldEqual(1)
    testStack.pop() shouldEqual(1.0)
  }

  it should "thrown an exception when trying to get parameters from stack with insufficient number of parameters" in {
    val op = new WithParams {
      override val paramCount: Int = 2
    }

    val testStack = mutable.Stack[Double]()
    testStack.push(1.0)

    an[InsufficientParametersException] should be thrownBy op.getParams(testStack)
  }
}
