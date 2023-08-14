import domain.{Dataframe, Delimiter, Header, Regex}
import services.{DataframeParser, NumbersParser}

import java.nio.file.{Files, Path}
import scala.util.{Failure, Success, Try}

object Main {
  def main(args: Array[String]): Unit =
    args.toList match {
      case s"-f$numbers" :: filename :: Nil =>
        val input = loadInputOrFail(filename)
        val dataframe = DataframeParser.ofTab(input)
        parseNumbersAndlogResult(numbers, dataframe)
      case s"-f$numbers" :: s"-d$delimiterWithValue" :: filename :: Nil =>
        val input = loadInputOrFail(filename)
        val delimiter = Delimiter
          .fromString(delimiterWithValue.tail)
          .getOrElse(Delimiter.Tab)
        val dataframe = DataframeParser.of(delimiter)(input)
        parseNumbersAndlogResult(numbers, dataframe)
      case _ => println("Incorrect usage, please refer to manual")
    }

  private def parseNumbersAndlogResult(
    numbers: String,
    dataframe: Dataframe,
  ): Unit =
    Regex.fromNumbers(numbers) match {
      case Some(regex) =>
        NumbersParser
          .fromRegex(regex)(numbers)
          .map(_.map(dataframe.getColumnByIndex))
          .fold(println("error"))(_.foreach(println))
      case _ =>
        Try(numbers.toInt).toOption
          .map(dataframe.getColumnByIndex)
          .fold(println("Input was not a number"))(
            _.fold(e => println(e.msg), println),
          )
    }

  private def loadInputOrFail(filename: String): String =
    Try(Files readString (Path of filename))
      .getOrElse(throw new RuntimeException("File does not exist"))
}
