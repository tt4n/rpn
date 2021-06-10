package exception

case class InvalidParametersException(message: String) extends Exception(message)
