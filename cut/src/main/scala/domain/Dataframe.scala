package domain

import domain.Dataframe.ColumnRetrievalError
import domain.Dataframe.ColumnRetrievalError.{NonPositiveIndex, TooLargeIndex}

final case class Dataframe(columns: List[Column]) {
  def getColumnByHeader(header: Header): Option[Column] =
    columns.find(_.header == header)

  def getColumnByIndex(index: Int): Either[ColumnRetrievalError, List[String]] =
    if (index <= 0) Left(NonPositiveIndex)
    else if (index > columns.size) Left(TooLargeIndex)
    else
      Right {
        val normalizedIndex = index - 1
        columns(normalizedIndex).header.value :: columns.flatMap { col =>
          col.values
            .filter(_.position.col == normalizedIndex)
            .map(_.value)
        }
      }
}

object Dataframe {
  sealed trait ColumnRetrievalError { def msg: String }

  object ColumnRetrievalError {
    case object NonPositiveIndex extends ColumnRetrievalError {
      override def msg: String = "Index was non-positive"
    }
    case object TooLargeIndex extends ColumnRetrievalError {
      override def msg: String = "Index was too large"
    }
  }
}
