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
        loadInputAndThenProcess(
          delimiterStr,
          columnNumbers,
          LoadInputFromFile.from(filename),
        )
      case s"-f$columnNumbers" :: filename :: Nil =>
        Dataframe
          .of(LoadInputFromFile.from(filename), DataframeParser.ofTab)
          .fold(println("Could not construct Dataframe"))(
            process(columnNumbers, _),
          )
      case s"-d$delimiterStr" :: s"-f$columnNumbers" :: Nil =>
        loadInputAndThenProcess(
          delimiterStr,
          columnNumbers,
          LoadInputFromStdIn.of.apply,
        )
      case _ => println("Incorrect usage, please refer to manual")
    }

  private def loadInputAndThenProcess(
    delimiterStr: String,
    columnNumbers: String,
    input: => String,
  ): Unit = {
    val delimiter = Delimiter
      .fromString(delimiterStr)
      .getOrElse(Delimiter.Tab)
    Dataframe
      .of(input, DataframeParser.of(delimiter))
      .fold(println("Could not construct Dataframe"))(
        process(columnNumbers, _),
      )
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
