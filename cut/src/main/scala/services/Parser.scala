package services

import domain._

trait Parser {
  def apply(input: String): Dataframe
}

object Parser {
  def of: Parser =
    (input: String) => {
      val splitted = input.split("\n")
      val headers = splitted.head.split("\\s")
      val rows = splitted.tail.zipWithIndex.map {
        case (rowAsString, rowIndex) =>
          createRow(rowAsString, rowIndex, headers)
      }
      val columns = headers.zipWithIndex.map { case (header, index) =>
        createColumn(header, index, rows.toList)
      }.toList

      Dataframe(columns)
    }

  private def createRow(
    rowAsString: String,
    rowIndex: Int,
    headers: Array[String],
  ): Row =
    Row {
      rowAsString
        .split("\\s")
        .zip(headers.indices)
        .map { case (rowValue, columnIndex) =>
          createCell(rowValue, columnIndex, rowIndex)
        }
        .toList
    }

  private def createCell(
    rowValue: String,
    columnIndex: Int,
    rowIndex: Int,
  ): Cell =
    Cell(
      Position.from(rowIndex, columnIndex).getOrElse(Position.zero),
      rowValue,
    )

  private def createColumn(
    header: String,
    index: Int,
    rows: List[Row],
  ): Column =
    Column(Header(header), rows.map(_.values(index)))

}
