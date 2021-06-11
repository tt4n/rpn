package model.operator

import model.Operation
import model.operator.traits.Operator
import org.mockito.MockitoSugar.{mock, never, times, verify, when}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.collection.mutable

class UndoSpec extends AnyFlatSpec with should.Matchers {
  behavior of "Undo"

  it should "do nothing if the operation stack is empty" in {
    val mockOperationStack = mock[mutable.Stack[Operation]]

    when(mockOperationStack.nonEmpty).thenReturn(false)

    Undo.operate(mutable.Stack[Double](), mockOperationStack)

    verify(mockOperationStack, never).pop
  }

  it should "undo previous operation correctly - operator with no result" in {
    val operation = Operation(Seq(1.0, 2.0), Seq())
    val currentStack = mutable.Stack(4.0, 3.0)

    Undo.operate(currentStack, mutable.Stack(operation))

    currentStack shouldEqual(mutable.Stack(2.0, 1.0, 4.0, 3.0))
  }

  it should "undo previous operation correctly - operator with single result" in {
    val operation = Operation(Seq(1.0, 2.0), Seq(3.0))
    val currentStack = mutable.Stack(3.0, 100)

    Undo.operate(currentStack, mutable.Stack(operation))

    currentStack shouldEqual(mutable.Stack(2.0, 1.0, 100))
  }

  it should "undo previous operation correctly - operator with multiple results" in {
    val operation = Operation(Seq(1.0, 2.0), Seq(3.0, 4.0))
    val currentStack = mutable.Stack(4.0, 3.0, 100)

    Undo.operate(currentStack, mutable.Stack(operation))

    currentStack shouldEqual(mutable.Stack(2.0, 1.0, 100))
  }

  it should "undo previous operation correctly - operator with no input" in {
    val operation = Operation(Seq(), Seq(3.0, 4.0))
    val currentStack = mutable.Stack(4.0, 3.0, 100)

    Undo.operate(currentStack, mutable.Stack(operation))

    currentStack shouldEqual(mutable.Stack(100))
  }
}
