import exception.InsufficientParametersException
import model.Calculator
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should

class CalculatorItSpec extends AnyFunSuite with should.Matchers {
  test("Test case 1: 5 2") {
    val calculator = new Calculator()

    calculator.verifyOperation("5 2", Seq("5", "2"))
  }

  test("Test case 2: 2 sqrt") {
    val calculator = new Calculator()

    calculator.verifyOperation("2 sqrt", Seq("1.4142135623"))
  }

  test("Test case 3: 5 2 -") {
    val calculator = new Calculator()

    calculator.verifyOperation("5 2 -", Seq("3"))
    calculator.verifyOperation("3 -", Seq("0"))
    calculator.verifyOperation("clear", Seq())
  }

  test("Test case 4: 5 4 3 2") {
    val calculator = new Calculator()

    calculator.verifyOperation("5 4 3 2", Seq("5", "4", "3", "2"))
    calculator.verifyOperation("undo undo *", Seq("20"))
    calculator.verifyOperation("5 *", Seq("100"))
    calculator.verifyOperation("undo", Seq("20", "5"))
  }

  test("Test case 5: 7 12 2 /") {
    val calculator = new Calculator()

    calculator.verifyOperation("7 12 2 /", Seq("7", "6"))
    calculator.verifyOperation("*", Seq("42"))
    calculator.verifyOperation("4 /", Seq("10.5"))
  }

  test("Test case 6: 1 2 3 4 5") {
    val calculator = new Calculator()

    calculator.verifyOperation("1 2 3 4 5", Seq("1", "2", "3", "4", "5"))
    calculator.verifyOperation("*", Seq("1", "2", "3", "20"))
    calculator.verifyOperation("clear 3 4 -", Seq("-1"))
  }

  test("Test case 7: 1 2 3 4 5") {
    val calculator = new Calculator()

    calculator.verifyOperation("1 2 3 4 5", Seq("1", "2", "3", "4", "5"))
    calculator.verifyOperation("* * * *", Seq("120"))
  }

  test("Test case 8: 1 2 3 * 5 + * * 6 5") {
    val calculator = new Calculator()

    an[InsufficientParametersException] should be thrownBy(calculator.handle("1 2 3 * 5 + * * 6 5"))

    calculator.getParamsInStack() shouldEqual(Seq("11"))
  }

  // Helper function on Calculator to verify input against expected values in parameter stack
  implicit class VerifyOperation(calculator: Calculator) {
    def verifyOperation(input: String, expectedParams: Seq[String]) = {
      calculator.handle(input)
      calculator.getParamsInStack() shouldEqual(expectedParams)
    }
  }
}
