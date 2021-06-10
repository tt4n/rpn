package model

import exception.{InsufficientParametersException, InvalidParametersException, InvalidTokenException}
import model.operator.traits.Operator
import model.operator.{Add, Clear, Divide, Multiply, Sqrt, Subtract, Undo}

import scala.collection.mutable
import scala.math.BigDecimal.RoundingMode

class Calculator {
  private var paramStack: mutable.Stack[Double] = mutable.Stack[Double]()

  /**
   * List of supported operators - key being the string representation of the operator
   */
  private var supportedOperators = Map(
    "+" -> Add,
    "-" -> Subtract,
    "*" -> Multiply,
    "/" -> Divide,
    "undo" -> Undo,
    "clear" -> Clear,
    "sqrt"  -> Sqrt
  )

  /**
   * Stack for tracking the operation history. Required for certain operators, e.g., undo
   */
  private val operationStack = mutable.Stack[Operation]()

  /**
   * Handle user inputs
   *
   * @param input
   */
  def handle(input: String): Unit = {
    // TODO keep current position
    val items = parseInput(input)

    items.foreach { item =>
      val maybeOperator = supportedOperators.get(item)

      // Check whether it is a valid operator first
      if (maybeOperator.nonEmpty) {
        val operator = maybeOperator.get
        operator.operate(paramStack, operationStack)
      } else {
        try {
          val doubleValue = item.toDouble

          paramStack.push(doubleValue)
          operationStack.push(Operation(Seq(), Seq(doubleValue)))
        } catch {
          case e: NumberFormatException =>
            throw InvalidTokenException(s"Unknown token $item at position <x>")
        }
      }
    }
  }

  /**
   * Return parameters in stack as strings
   * @return
   */
  def getParamsInStack(): Seq[String] = {
    paramStack.toSeq.reverse.map { param =>
      // use BigDecimal instead of String.format to avoid rounding-up
      val bd = BigDecimal.valueOf(param).setScale(10, RoundingMode.DOWN)

      // remove decimal point & trailing 0 when applicable
      bd.doubleValue.toString().replaceAll("\\.?0*$", "")
    }
  }

  def getOperationsInStack(): Seq[Operation] = {
    operationStack.toSeq.reverse
  }

  def setParamStack(stack: mutable.Stack[Double]): Unit = {
    paramStack = stack
  }
  /**
   * Set supported operators
   * @param operators
   */
  def setSupportedOperators(operators: Map[String, Operator]) = {
    supportedOperators = operators
  }

  /**
   * Parse user input
   * @param input
   * @return
   */
  private def parseInput(input: String): Seq[String] = {
    // TODO keep current position
    input.split("\\s+")
  }
}