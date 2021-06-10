package model.operator.traits

import model.Operation
import scala.collection.mutable

trait Operator {
  /* Abstract methods to be implemented by each operator */
  def getParams(paramStack: mutable.Stack[Double]): Seq[Double]
  def processParams(params: Seq[Double]): Seq[Double]

  def operate(paramStack: mutable.Stack[Double], operationStack: mutable.Stack[Operation]): Unit = {
    val params = getParams(paramStack)

    // TODO handle error & restore params if fail
    val result = processParams(params)

    // save results into parameter stack
    result.foreach(paramStack.push(_))

    // save operation to operationStack, to be used be certain operators, e.g., undo
    val operation = Operation(params, result)
    operationStack.push(operation)
  }
}
