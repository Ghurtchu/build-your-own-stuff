import domain.{Dataframe, Delimiter, Regex}
import services.{DataframeParser, NumbersParser}

import java.nio.file.{Files, Path}
import scala.util.Try

object Main {
  def main(args: Array[String]): Unit =
    args.toList match {
      case s"-f$columnNumbers" :: filename :: Nil =>
        val input = loadInputOrFail(filename)
        val dataframe = DataframeParser.ofTab(input)

        parseAndLogResult(columnNumbers, dataframe)
      case s"-f$columnNumbers" :: s"-d$delimiterWithValue" :: filename :: Nil =>
        val input = loadInputOrFail(filename)
        val delimiter = Delimiter
          .fromString(delimiterWithValue.tail)
          .getOrElse(Delimiter.Tab)
        val dataframe = DataframeParser.of(delimiter)(input)

        parseAndLogResult(columnNumbers, dataframe)
      case _ => println("Incorrect usage, please refer to manual")
    }

  private def parseAndLogResult(
    columnNumbers: String,
    dataframe: Dataframe,
  ): Unit =
    Regex.from(columnNumbers) match {
      case Some(regex) =>
        NumbersParser
          .fromRegex(regex)(columnNumbers)
          .fold(
            println("Not a number was spotted, please provide only numbers"),
          ) { indices =>
            dataframe
              .getSliceByIndices(indices: _*)
              .fold(error => println(error.msg), _.display())
          }
      case None =>
        Try(columnNumbers.toInt).toOption
          .fold(println("Input was not a number")) { index =>
            dataframe
              .getColumnByIndex(index)
              .fold(error => println(error.msg), println)
          }
    }

  private def loadInputOrFail(filename: String): String =
    Try(Files readString (Path of filename))
      .getOrElse(throw new RuntimeException("File does not exist"))
}
