package model.operator.traits

import model.Operation
import scala.collection.mutable

trait Operator {
  /* Abstract method to be implement by each operator */
  def operate(paramStack: mutable.Stack[Double], operationStack: mutable.Stack[Operation]): Unit
}
