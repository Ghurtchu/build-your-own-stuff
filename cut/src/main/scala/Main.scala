import domain.{Dataframe, Delimiter, Regex}
import services.{DataframeParser, NumbersParser}

import java.nio.file.{Files, Path}
import scala.util.Try

object Main {
  def main(args: Array[String]): Unit =
    args.toList match {
      case s"-f$columnNumbers" :: s"-d$delimiterStr" :: filename :: Nil =>
        val delimiter = Delimiter
          .fromString(delimiterStr)
          .getOrElse(Delimiter.Tab)
        val dataframe = Dataframe
          .of(filename, DataframeParser.of(delimiter))
        process(columnNumbers, dataframe)
      case s"-f$columnNumbers" :: filename :: Nil =>
        val dataframe = Dataframe
          .of(filename, DataframeParser.ofTab)
        process(columnNumbers, dataframe)
      case _ => println("Incorrect usage, please refer to manual")
    }

  private def process(
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
}
