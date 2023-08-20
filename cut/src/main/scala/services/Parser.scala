package services

trait Parser[+A] {
  def apply(input: String): Option[A]
}
