package model.operator

import exception.InvalidParametersException
import model.operator.traits.{Operator, WithParams}

object Multiply extends Operator with WithParams {
  override val paramCount: Int = 2

  override def processParams(params: Seq[Double]): Seq[Double] = {
    if (params.size == 2 && params(1) != 0) {
      Seq(params.product)
    } else {
      throw InvalidParametersException("Invalid number of parameters or parameter(s) with invalid value")
    }
  }
}