import domain.{Dataframe, Delimiter, Regex}
import services.{DataframeParser, NumbersParser}

import java.nio.file.{Files, Path}
import scala.util.Try

object Main {
  def main(args: Array[String]): Unit =
    args.toList match {
      case s"-f$columnNumbers" :: filename :: Nil =>
        val input = loadOrFail(filename)
        val dataframe = DataframeParser.ofTab(input)
        parseNumbersAndLogResult(columnNumbers, dataframe)
      case s"-f$columnNumbers" :: s"-d$delimiterWithValue" :: filename :: Nil =>
        val input = loadOrFail(filename)
        val delimiter = Delimiter
          .fromString(delimiterWithValue.tail)
          .getOrElse(Delimiter.Tab)
        val dataframe = DataframeParser.of(delimiter)(input)
        parseNumbersAndLogResult(columnNumbers, dataframe)
      case _ => println("Incorrect usage, please refer to manual")
    }

  private def parseNumbersAndLogResult(
    numbers: String,
    dataframe: Dataframe,
  ): Unit =
    Regex.from(numbers) match {
      case Some(regex) =>
        NumbersParser
          .fromRegex(regex)(numbers)
          .flatMap(indices => dataframe.getDataframeByIndices(indices: _*))
          .fold(println("Index out of bounds, please select correct indices"))(
            _.display(),
          )
      case _ =>
        Try(numbers.toInt).toOption
          .map(dataframe.getColumnByIndex)
          .fold(println("Input was not a number"))(
            _.fold(e => println(e.msg), println),
          )
    }

  private def loadOrFail(filename: String): String =
    Try(Files readString (Path of filename))
      .getOrElse(throw new RuntimeException("File does not exist"))
}
