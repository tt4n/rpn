package exception

case class InvalidTokenException(message: String) extends Exception(message)
