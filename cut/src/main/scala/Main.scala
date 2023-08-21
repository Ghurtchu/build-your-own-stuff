import domain.{Dataframe, Delimiter, Regex}
import services.{
  DataframeParser,
  LoadInputFromFile,
  LoadInputFromStdIn,
  NumbersParser,
}
import scala.util.Try

object Main {
  def main(args: Array[String]): Unit =
    args.toList match {
      case s"-f$columnNumbers" :: s"-d$delimiterStr" :: filename :: Nil =>
        buildDataframeAndThenProcess(
          Delimiter
            .fromString(delimiterStr)
            .getOrElse(Delimiter.Tab),
          columnNumbers,
          LoadInputFromFile.from(filename),
        )
      case s"-f$columnNumbers" :: filename :: Nil =>
        buildDataframeAndThenProcess(
          Delimiter.Tab,
          columnNumbers,
          LoadInputFromFile.from(filename),
        )
      case s"-d$delimiterStr" :: s"-f$columnNumbers" :: Nil =>
        buildDataframeAndThenProcess(
          Delimiter
            .fromString(delimiterStr)
            .getOrElse(Delimiter.Tab),
          columnNumbers,
          LoadInputFromStdIn.of.apply,
        )
      case _ => println("Incorrect usage, please refer to manual")
    }

  private def buildDataframeAndThenProcess(
    delimiter: Delimiter,
    columnNumbers: String,
    input: => String,
  ): Unit = Dataframe
    .of(input, DataframeParser.of(delimiter))
    .fold(println("Could not construct Dataframe"))(
      sliceAndDiceAndShow(columnNumbers, _),
    )

  private def sliceAndDiceAndShow(
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
