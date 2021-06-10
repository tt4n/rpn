package model.operator

import exception.InvalidParametersException
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class MultiplySpec extends AnyFlatSpec with should.Matchers {
  behavior of "Multiply"

  it should "process parameters and return the correct result" in {
    val params = Seq(2.0, 3.0)

    Multiply.processParams(params) shouldEqual(Seq(6.0))
  }

  it should "throw an exception if the wrong number of parameters are supplied" in {
    val params = Seq(1.0)

    an [InvalidParametersException] should be thrownBy Multiply.processParams(params)
  }
}
