package domain

import domain.Dataframe.ColumnRetrievalError
import domain.Dataframe.ColumnRetrievalError.{NonPositiveIndex, TooLargeIndex}

final case class Dataframe(columns: List[Column]) extends AnyVal {

  def getColumnByHeader(header: Header): Option[Column] =
    columns.find(_.header == header)

  def getColumnByCell(cell: Cell): Option[Column] =
    columns.find(_.values.contains(cell))

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

  def getSliceByIndices(
    indices: Int*,
  ): Either[ColumnRetrievalError, Dataframe] = {
    val columnsOrErrors = indices.map(getColumnByIndex)

    columnsOrErrors
      .collectFirst { case Left(error) => Left(error) }
      .getOrElse {
        Right {
          Dataframe {
            columnsOrErrors.collect { case Right(column) =>
              column
            }.toList
          }
        }
      }
  }

  def display(): Unit = {
    val longestStringsForEachColumn: Map[Column, Int] =
      columns
        .map(column =>
          column -> column.values
            .maxBy(_.value.length)
            .value
            .length,
        )
        .toMap
    val headersAsString = columns
      .foldLeft("") { (acc, column) =>
        acc concat column.header.value concat " " * (longestStringsForEachColumn(
          column,
        ) - column.header.value.length + 1)
      }
    val columnsAsString =
      columns.head.values.indices.map { index =>
        columns
          .flatMap(_.values.find(_.position.rowIndex.value == index))
          .foldLeft("") { (acc, cell) =>
            acc concat cell.value concat " " * (longestStringsForEachColumn(
              getColumnByCell(cell).get,
            ) - cell.value.length + 1)
          }
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
