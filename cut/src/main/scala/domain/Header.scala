package domain

final case class Header private (value: String) extends AnyVal

object Header {
  def fromString: String => Option[Header] =
    PartialFunction.condOpt(_) {
      case s if s.nonEmpty => Header(s)
    }
}
