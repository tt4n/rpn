package model.operator

import exception.{InsufficientParametersException, InvalidParametersException}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.collection.mutable

class SqrtSpec extends AnyFlatSpec with should.Matchers {
  behavior of "Sqrt"

  it should "process parameters and return the correct result" in {
    val params = Seq(4.0)

    Sqrt.processParams(params) shouldEqual(Seq(2.0))
  }

  it should "throw an exception if the wrong number of parameters are supplied" in {
    val params = Seq(1.0, 2.0)

    an [InvalidParametersException] should be thrownBy Sqrt.processParams(params)
  }

  it should "throw an exception if negative value is supplied" in {
    val params = Seq(-1.0)

    an [InvalidParametersException] should be thrownBy Sqrt.processParams(params)
  }
}
