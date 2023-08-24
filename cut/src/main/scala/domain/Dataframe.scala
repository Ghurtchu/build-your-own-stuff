package domain

import domain.Dataframe.ColumnRetrievalError
import domain.Dataframe.ColumnRetrievalError.{NegativeIndex, TooLargeIndex}
import services.DataframeParser

final case class Dataframe(columns: List[Column]) extends AnyVal {
  def getColumnByCell(cell: Cell): Option[Column] =
    columns.find(_.values.contains(cell))

  def getColumnByIndex(index: Int): Either[ColumnRetrievalError, Column] =
    if (index < 0) Left(NegativeIndex)
    else if (index >= columns.size) Left(TooLargeIndex)
    else
      Right {
        val header = columns(index).header
        val cells = columns.flatMap {
          _.values
            .filter(_.position.columnIndex.value == index)
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
    val maxStringLengthForEachColumn: Map[Column, Int] =
      getMaxStringLengthForEachColumn
    val headersAsString = mapHeadersToString(maxStringLengthForEachColumn)
    val rowsAsStrings =
      mapColumnsToRowStrings(maxStringLengthForEachColumn)

    (headersAsString :: rowsAsStrings)
      .foreach(println)
  }

  private[domain] def mapColumnsToRowStrings(
    longestStringsForEachColumn: Map[Column, Int],
  ): List[String] =
    columns.head.values.indices.map { index =>
      columns
        .flatMap(_.values.find(_.position.rowIndex.value == index))
        .foldLeft("") { (acc, cell) =>
          acc concat cell.value concat " " * (longestStringsForEachColumn(
            getColumnByCell(cell).getOrElse(
              throw new RuntimeException(
                s"could not find column for cell: $cell",
              ),
            ),
          ) - cell.value.length + 1)
        }
        .trim
    }.toList

  private[domain] def mapHeadersToString(
    longestStringsForEachColumn: Map[Column, Int],
  ) =
    columns
      .foldLeft("") { (acc, column) =>
        acc concat column.header.value concat " " * (longestStringsForEachColumn(
          column,
        ) - column.header.value.length + 1)
      }

  private[domain] def getMaxStringLengthForEachColumn: Map[Column, Int] =
    columns
      .map(column => column -> column.values.map(_.value.length).max)
      .toMap

}

object Dataframe {
  sealed trait ColumnRetrievalError { def msg: String }

  object ColumnRetrievalError {
    case object NegativeIndex extends ColumnRetrievalError {
      override def msg: String = "Index was non-positive"
    }
    case object TooLargeIndex extends ColumnRetrievalError {
      override def msg: String = "Index was too large"
    }
  }

  def of(input: String, parser: DataframeParser): Option[Dataframe] =
    parser(input)
}
