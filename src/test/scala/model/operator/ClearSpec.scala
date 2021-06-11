package model.operator

import model.Operation
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.collection.mutable

class ClearSpec extends AnyFlatSpec with should.Matchers {
  behavior of "Clear"

  it should "clear all parameters" in {
    val paramStack = mutable.Stack[Double]()
    paramStack.push(1.0)
    paramStack.push(2.0)
    paramStack.push(3.0)

    val operationStack = mutable.Stack[Operation]()

    Clear.operate(paramStack, operationStack)

    paramStack shouldEqual(mutable.Stack[Double]())
    operationStack.size shouldEqual(1)
    operationStack.pop() shouldEqual(Operation(Seq(1.0, 2.0, 3.0), Seq()))
  }
}
