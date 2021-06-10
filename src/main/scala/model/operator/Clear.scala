package model.operator

import model.operator.traits.Operator

import scala.collection.mutable

object Clear extends Operator {
  override def getParams(paramStack: mutable.Stack[Double]): Seq[Double] = {
    paramStack.popAll().toSeq
  }

  override def processParams(params: Seq[Double]): Seq[Double] = Seq()
}