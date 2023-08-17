package domain

import domain.Position.Index.{ColumnIndex, RowIndex}
import PartialFunction.condOpt

final case class Position private (rowIndex: RowIndex, columnIndex: ColumnIndex)

object Position {

  sealed trait Index
  object Index {
    final case class RowIndex private (value: Int) extends Index
    object RowIndex {
      def zero: RowIndex = RowIndex(0)
      def fromInt: Int => Option[RowIndex] =
        condOpt(_) { case n if n >= 0 => RowIndex(n) }
    }

    final case class ColumnIndex private (value: Int) extends Index
    object ColumnIndex {
      def zero: ColumnIndex = ColumnIndex(0)
      def fromInt: Int => Option[ColumnIndex] =
        condOpt(_) { case n if n >= 0 => ColumnIndex(n) }
    }
  }

  def zero: Position = Position(RowIndex.zero, ColumnIndex.zero)

  def from(row: Int, col: Int): Option[Position] =
    for {
      row <- RowIndex.fromInt(row)
      col <- ColumnIndex.fromInt(col)
    } yield Position(row, col)
}
