import model.Calculator
import scala.io.StdIn.readLine

object CalculatorApp extends App {
  val calculator = new Calculator()

  while (true) {
    val input = readLine(">")

    if (input.trim != "") {
      try {
        calculator.handle(input)
      } catch {
        case e: Exception => println(e.getMessage)
      }

      println("stack: " + calculator.getParamsInStack().mkString(" "))
    }
  }
}
