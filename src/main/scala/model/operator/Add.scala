package model.operator

import exception.InvalidParametersException
import model.operator.traits.{Operator, WithParams}

object Add extends Operator with WithParams {
  override val paramCount: Int = 2

  override def processParams(params: Seq[Double]): Seq[Double] = {
    if (params.size == 2) {
      Seq(params.sum)
    } else {
      throw InvalidParametersException(s"${params.size} parameters passed in while 2 parameters are required")
    }
  }
}
