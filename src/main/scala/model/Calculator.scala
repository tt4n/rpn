package model

import java.util.Scanner
import exception.{InsufficientParametersException, InvalidTokenException}
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
    val items = parseInput(input)

    items.foreach { item: (String, Int) =>
      val token    = item._1

      // position of token in user input
      val position = item._2

      handleToken(position, token)
    }
  }

  /**
   * Handle a single token from user input
   * @param position
   * @param token
   * @return
   */
  private def handleToken(position: Int, token: String) = {
    val maybeOperator = supportedOperators.get(token)

    maybeOperator match {
      // Token is a valid operator
      case Some(operator) =>
        try {
          operator.operate(paramStack, operationStack)
        } catch {
          case e: InsufficientParametersException =>
            // Add more details to the exception errorMessage - change position to be 1-based
            val errorMessage = s"operator $token (position: ${position + 1}): insufficient parameters"

            throw InsufficientParametersException(errorMessage)
        }

      // Not an operator - try to process as a parameter
      case None =>
        val doubleValue = try {
          token.toDouble
        } catch {
          case e: NumberFormatException =>
            throw InvalidTokenException(s"Unknown token $token at position ${position + 1}")
        }

        handleParameter(doubleValue)
    }
  }

  /**
   * Handle a parameter
   * @param parameter
   * @return
   */
  private def handleParameter(parameter: Double): Unit = {
    paramStack.push(parameter)

    // add to operation stack - required by certain operator, e.g., undo
    operationStack.push(Operation(Seq(), Seq(parameter)))
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

  /**
   * Set parameter stack
   * @param stack
   */
  def setParamStack(stack: mutable.Stack[Double]): Unit = {
    paramStack = stack
  }

  /**
   * Get the list of operations in stack
   * @return
   */
  def getOperationsInStack(): Seq[Operation] = {
    operationStack.toSeq.reverse
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
   * @return A list of tuple containing the token value and its starting position
   */
  private def parseInput(input: String): Seq[(String, Int)] = {
    var tokensWithPosition = Vector[(String, Int)]()

    val scanner = new Scanner(input)

    while (scanner.hasNext()) {
      val token    = scanner.next()
      val position = scanner.`match`().start()

      tokensWithPosition :+= (token, position)
    }

    tokensWithPosition
  }
}