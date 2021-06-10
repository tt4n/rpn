package model.operator

import exception.InvalidParametersException
import model.operator.traits.{Operator, WithParams}

object Divide extends Operator with WithParams {
  override val paramCount: Int = 2

  override def processParams(params: Seq[Double]): Seq[Double] = {
    if (params.size == 2 && params(1) != 0) {
      Seq(params(0) / params(1))
    } else {
      throw InvalidParametersException("Invalid number of parameters or parameter(s) with invalid value")
    }
  }
}
