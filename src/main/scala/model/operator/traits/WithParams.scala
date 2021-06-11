package model.operator.traits

import exception.InsufficientParametersException
import model.Operation
import scala.collection.mutable

/**
 * Implemented by operators that requires parameters
 */
trait WithParams {
  val paramCount: Int

  /* Abstract methods to be implemented by each operator */
  def processParams(params: Seq[Double]): Seq[Double]

  def getParams(paramStack: mutable.Stack[Double]): Seq[Double] = {
    if (paramStack.size < paramCount) {
      throw InsufficientParametersException("Insufficient number of parameters in the stack")
    } else {
      // Pop the first X values from top of the stack and put them in the same order as they were pushed into the stack
      (0 until paramCount).map(_ => paramStack.pop()).reverse
    }
  }

  def operate(paramStack: mutable.Stack[Double], operationStack: mutable.Stack[Operation]): Unit = {
    val params = getParams(paramStack)

    val result = try {
      processParams(params)
    } catch {
      case e: Exception =>
        // add params back to stack
        params.foreach(paramStack.push(_))
        throw e
    }

    // save results into parameter stack
    result.foreach(paramStack.push(_))

    // save operation to operationStack, to be used be certain operators, e.g., undo
    val operation = Operation(params, result)
    operationStack.push(operation)
  }
}
