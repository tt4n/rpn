package model.operator

import exception.InvalidParametersException
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class SubtractSpec extends AnyFlatSpec with should.Matchers  {
  behavior of "Subtract"

  it should "process parameters and return the correct result" in {
    val params = Seq(1.0, 2.0)

    Subtract.processParams(params) shouldEqual(Seq(-1.0))
  }

  it should "throw an exception if the wrong number of parameters are supplied" in {
    val params = Seq(1.0)

    an [InvalidParametersException] should be thrownBy Subtract.processParams(params)
  }
}

