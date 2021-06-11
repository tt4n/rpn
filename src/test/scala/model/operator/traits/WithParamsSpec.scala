package model.operator.traits

import exception.InsufficientParametersException
import model.Operation
import model.operator.Add
import org.mockito.MockitoSugar.{mock, when}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.collection.mutable

class WithParamsSpec extends AnyFlatSpec with should.Matchers {
  behavior of "WithParams"

  it should "get the right number of parameters from the parameter stack" in {
    val op = new WithParams {
      override val paramCount: Int = 2
      override def processParams(params: Seq[Double]): Seq[Double] = Seq[Double]()
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
      override def processParams(params: Seq[Double]): Seq[Double] = Seq[Double]()
    }

    val testStack = mutable.Stack[Double]()
    testStack.push(1.0)

    an[InsufficientParametersException] should be thrownBy op.getParams(testStack)
  }

  it should "operator on parameters correctly and save operation to operation stack" in {
    val op = new WithParams {
      override val paramCount: Int = 1
      override def processParams(params: Seq[Double]): Seq[Double] = Seq[Double](1.0, 2.0)
    }

    val testStack = mutable.Stack[Double](3.0)
    val operationStack = mutable.Stack[Operation]()

    op.operate(testStack, operationStack)
    testStack shouldEqual(mutable.Stack[Double](2.0, 1.0))
    operationStack.size shouldBe(1)
    operationStack.pop() shouldEqual(Operation(Seq(3.0), Seq(1.0, 2.0)))
  }

  it should "add parameters back to the stack if it fails to process the parameters" in {
    val op = new WithParams {
      override val paramCount: Int = 1
      override def processParams(params: Seq[Double]): Seq[Double] = throw new RuntimeException("fail to process parameters")
    }

    val testStack = mutable.Stack[Double](3.0)
    val operationStack = mutable.Stack[Operation]()

    an[Exception] should be thrownBy(op.operate(testStack, operationStack))
    testStack shouldEqual(mutable.Stack[Double](3.0))
    operationStack.size shouldBe(0)
  }
}
