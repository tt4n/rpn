package model.operator

import exception.InvalidParametersException
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class DivideSpec extends AnyFlatSpec with should.Matchers {
  behavior of "Divide"

  it should "process parameters and return the correct result" in {
    val params = Seq(1.0, 2.0)

    Divide.processParams(params) shouldEqual(Seq(0.5))
  }

  it should "throw an exception if the wrong number of parameters are supplied" in {
    val params = Seq(1.0)

    an [InvalidParametersException] should be thrownBy Divide.processParams(params)
  }

  it should "throw an exception if 0 is supplied as the divisor" in {
    val params = Seq(1.0, 0)

    an [InvalidParametersException] should be thrownBy Divide.processParams(params)
  }
}
