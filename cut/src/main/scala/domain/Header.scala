package domain

import PartialFunction.condOpt

final case class Header private (value: String) extends AnyVal

object Header {
  def fromString: String => Option[Header] =
    condOpt(_) { case s if s.nonEmpty => Header(s) }
}
