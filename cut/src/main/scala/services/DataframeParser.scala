package services

import domain._

import Option.when
import scala.util.Try

trait DataframeParser extends Parser[Dataframe]

object DataframeParser {

  def ofComma: DataframeParser = of(Delimiter.Comma)

  def ofTab: DataframeParser = of(Delimiter.Tab)

  def of(delimiter: Delimiter): DataframeParser =
    (input: String) => {
      val lines = input.split("\n")
      when(lines.length > 1) {
        val headers = extractHeaders(lines, delimiter)
        val rows = extractRows(lines, delimiter, headers.indices)
        val isRowsDimensionsCorrect =
          rows.forall(_.values.length == headers.length)
        when(isRowsDimensionsCorrect) {
          val columns = extractColumns(headers, rows)
          val columnCount = lines.length - 1 // minus header
          val isColumnsDimensionsCorrect =
            columns.forall(_.values.length == columnCount)
          when(isColumnsDimensionsCorrect) {
            Dataframe(columns)
          }
        }.flatten
      }.flatten
    }

  private def extractHeaders(
    lines: Array[String],
    delimiter: Delimiter,
  ): Array[String] =
    lines.head
      .split(delimiter.repr)
      .map(_.replaceAll("[\uFEFF-\uFFFF]", ""))

  private def extractRows(
    lines: Array[String],
    delimiter: Delimiter,
    headersRange: Range,
  ): Array[Row] =
    lines.tail.zipWithIndex.map { case (rowAsString, rowIndex) =>
      row(rowAsString, rowIndex, headersRange, delimiter)
    }

  private def extractColumns(
    headers: Array[String],
    rows: Array[Row],
  ): List[Column] =
    headers.zipWithIndex.map { case (header, index) =>
      column(header, index, rows.toList)
    }.toList

  private def row(
    rowAsString: String,
    rowIndex: Int,
    indexRange: Range,
    delimiter: Delimiter,
  ): Row =
    Row {
      rowAsString
        .split(delimiter.repr)
        .zip(indexRange)
        .flatMap { case (rowValue, columnIndex) =>
          maybeCell(rowValue, columnIndex, rowIndex)
        }
        .toList
    }

  private def maybeCell(
    rowValue: String,
    columnIndex: Int,
    rowIndex: Int,
  ): Option[Cell] =
    Position
      .from(rowIndex, columnIndex)
      .map(Cell(_, rowValue))

  private def column(header: String, index: Int, rows: List[Row]): Column =
    Column(Header(header), rows.flatMap(row => Try(row.values(index)).toOption))

}
