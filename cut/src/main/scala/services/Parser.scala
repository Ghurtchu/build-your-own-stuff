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
      val rows = splitted.tail.zipWithIndex.map { case (rowString, rowIndex) =>
        val rowValues = rowString.split("\\s")

        Row {
          rowValues
            .zip(headers.indices)
            .map { case (rowValue, columnIndex) =>
              val pos = Position.from(rowIndex, columnIndex).getOrElse(Position.zero)

              Cell(pos, rowValue)
            }
            .toList
        }
      }.toList

      val columns = headers.zipWithIndex.map { case (header, index) =>
        val cells = rows
          .map(_.values(index))

        Column(Header(header), cells)
      }.toList

      Dataframe(columns)
    }
}
