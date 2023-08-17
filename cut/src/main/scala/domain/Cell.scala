package domain

final case class Cell(
  position: Position,
  value: String,
) {
  override def toString: String = value
}
