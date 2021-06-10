package model

import exception.InvalidTokenException
import model.operator.traits.Operator
import org.mockito.MockitoSugar.{mock, times, verify}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.collection.mutable

class CalculatorSpec extends AnyFlatSpec with should.Matchers {
  behavior of "Calculator"

  it should "handle user input with parameters only" in {
    val calculator = new Calculator()

    calculator.handle("1 2 3")

    val params = calculator.getParamsInStack()
    val operations = calculator.getOperationsInStack()

    params shouldEqual(Seq("1", "2", "3"))
    operations shouldEqual(Seq(
      Operation(List(),List(1.0)),
      Operation(List(),List(2.0)),
      Operation(List(),List(3.0))
    ))
  }

  it should "handle user input with parameters and operators" in {
    val calculator = new Calculator()

    val mockOperator = mock[Operator]
    calculator.setSupportedOperators(Map("x" -> mockOperator))

    calculator.handle("1 2 3 x")

    verify(mockOperator, times(1)).operate(
      mutable.Stack[Double](3.0, 2.0, 1.0),
      mutable.Stack[Operation](
        Operation(List(),List(3.0)),
        Operation(List(),List(2.0)),
        Operation(List(),List(1.0))
      )
    )

    val params = calculator.getParamsInStack()
    params shouldEqual(Seq("1", "2", "3"))
  }

  it should "throw an exception if input contains an invalid string" in {
    val calculator = new Calculator
    val invalidInput = "1 2 3 a"

    an[InvalidTokenException] should be thrownBy calculator.handle(invalidInput)
  }

  it should "get parameter in the stack as strings in the right format" in {
    val paramStack = mutable.Stack[Double]()
    paramStack.push(1)
    paramStack.push(1.0)
    paramStack.push(0.5)
    paramStack.push(1.0/3)
    paramStack.push(0.123450000)
    paramStack.push(0.012345678999) // to verify rounding-down when displaying with 10-digits precision

    val calculator = new Calculator()
    calculator.setParamStack(paramStack)

    calculator.getParamsInStack() shouldEqual(List(
      "1", "1", "0.5", "0.3333333333", "0.12345", "0.0123456789"
    ))
  }
}
