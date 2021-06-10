package model.operator
import exception.InvalidParametersException
import model.operator.traits.{Operator, WithParams}

object Sqrt extends Operator with WithParams {
  override val paramCount: Int = 1

  override def processParams(params: Seq[Double]): Seq[Double] = {
    if (params.size == 1 && params(0) >= 0) {
      Seq(math.sqrt(params(0).doubleValue))
    } else {
      throw InvalidParametersException(s"Invalid number of parameters or parameter(s) with invalid value")
    }
  }
}
