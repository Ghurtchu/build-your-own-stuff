package domain

import domain.Dataframe.ColumnRetrievalError
import domain.Dataframe.ColumnRetrievalError.{NonPositiveIndex, TooLargeIndex}

final case class Dataframe(columns: List[Column]) {
  def getColumnByHeader(header: Header): Option[Column] =
    columns.find(_.header == header)

  def getColumnByIndex(index: Int): Either[ColumnRetrievalError, Column] =
    if (index <= 0) Left(NonPositiveIndex)
    else if (index > columns.size) Left(TooLargeIndex)
    else
      Right {
        val normalizedIndex = index - 1
        val header = columns(normalizedIndex).header
        val cells = columns.flatMap {
          _.values
            .filter(_.position.columnIndex.value == normalizedIndex)
        }

        Column(header, cells)
      }

  def getDataframeByIndices(indices: Int*): Option[Dataframe] = {
    val columns = indices
      .map(getColumnByIndex)
      .collect { case Right(value) => value }
      .toList

    Option.when(columns.size == indices.size) {
      copy(columns = columns)
    }
  }

  def display(): Unit = {
    val headersAsString = columns
      .map(_.header.value)
      .reduce(_ concat "\t" concat _)
    val columnsAsString =
      columns.head.values.indices.map { index =>
        columns
          .flatMap(_.values.find(_.position.rowIndex.value == index))
          .map(_.toString)
          .reduce(_ concat "\t" concat _)
      }.toList

    (headersAsString :: columnsAsString)
      .foreach(println)
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
