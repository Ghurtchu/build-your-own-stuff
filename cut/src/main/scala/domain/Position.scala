package domain

final case class Position private (row: Int, col: Int)

object Position {

  def zero: Position = Position(0, 0)
  def from(row: Int, col: Int): Option[Position] =
    Option.when(row >= 0 && col >= 0)(new Position(row, col))
}
