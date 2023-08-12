package services

import domain._

trait Parser extends (String => Dataframe)

object Parser {

  def ofComma: Parser = of(Delimiter.Comma)

  def ofTab: Parser = of(Delimiter.Tab)

  def of(delimiter: Delimiter): Parser =
    (input: String) => {
      implicit val d: Delimiter = delimiter
      val lines = input.split("\n")
      val headers = lines.head.split(delimiter.repr).map(_.replaceAll("[\uFEFF-\uFFFF]", ""))
      val rows = lines.tail.zipWithIndex.map { case (rowAsString, rowIndex) =>
        createRow(rowAsString, rowIndex, headers.indices)
      }
      var columns: List[Column] = null
        columns = headers.zipWithIndex.map { case (header, index) =>
          try {
            createColumn(header, index, rows.toList)
          } catch {
            case e: Exception => {
              println(header)
              println(index)
              createColumn(header, index, rows.toList)
            }
          }
        }.toList


      Dataframe(columns)
    }

  private def createRow(
    rowAsString: String,
    rowIndex: Int,
    indexRange: Range,
  )(implicit delimiter: Delimiter): Row =
    Row {
      rowAsString
        .split(delimiter.repr)
        .zip(indexRange)
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
