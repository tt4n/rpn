package model.operator

import model.Operation
import model.operator.traits.Operator

import scala.collection.mutable

object Undo extends Operator {
  override def getParams(paramStack: mutable.Stack[Double]): Seq[Double] = Seq()
  override def processParams(params: Seq[Double]): Seq[Double] = Seq()

  /**
   * Undo is a special operator that need to read data from the operationStack & write data to parameter stack, hence
   * overriding the operate function directly
   *
   * @param paramStack
   * @param operationStack
   */
  override def operate(paramStack: mutable.Stack[Double], operationStack: mutable.Stack[Operation]): Unit = {
    if (operationStack.nonEmpty) {
      val lastOperation = operationStack.pop()

      // pop the result of the last operation from paramStack
      (0 until lastOperation.out.size).foreach(_ => paramStack.pop())

      // restore the params popped by the last operator
      lastOperation.in.foreach(paramStack.push(_))
    }
  }
}
