package domain

final case class Letter(value: Char) extends AnyVal {
  override def toString: String = value.toString
}
