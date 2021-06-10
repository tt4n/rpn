package exception

case class InsufficientParametersException(message: String) extends Exception(message)