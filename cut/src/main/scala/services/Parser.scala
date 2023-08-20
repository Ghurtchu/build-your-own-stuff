package services

trait Parser[+A] extends (String => Option[A])
