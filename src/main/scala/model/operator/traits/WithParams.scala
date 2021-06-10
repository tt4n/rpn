package model.operator.traits

import exception.InsufficientParametersException
import scala.collection.mutable

/**
 * Implemented by operators that requires parameters
 */
trait WithParams {
  val paramCount: Int

  def getParams(paramStack: mutable.Stack[Double]): Seq[Double] = {
    if (paramStack.size < paramCount) {
      throw InsufficientParametersException("Insufficient number of parameters in the stack")
    } else {
      // Pop the first X values from top of the stack and put them in the same order as they were pushed into the stack
      (0 until paramCount).map(_ => paramStack.pop()).reverse
    }
  }
}
