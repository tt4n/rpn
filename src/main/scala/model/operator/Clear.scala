package model.operator

import model.Operation
import model.operator.traits.Operator

import scala.collection.mutable

object Clear extends Operator {
  override def operate(paramStack: mutable.Stack[Double], operationStack: mutable.Stack[Operation]): Unit = {
    // clear all parameters
    val params = paramStack.popAll().toSeq

    // save operation to operationStack, to be used be certain operators, e.g., undo
    val operation = Operation(params, Seq())
    operationStack.push(operation)
  }
}