import domain.{Delimiter, Header, Regex}
import services.DataframeParser

import java.nio.file.{Files, Path}
import scala.util.{Failure, Success, Try}

object Main {
  def main(args: Array[String]): Unit =
    args.toList match {
      case s"-f$numbers" :: filename :: Nil =>
        readFile(filename)
          .fold(
            _ => println("file does not exist"),
            content => {
              val parser = DataframeParser.ofTab(content)
              Regex.fromSplittable(numbers) match {
                case Some(regex) =>
                  parse(numbers, regex)
                    .map(_.map(parser.getColumnByIndex))
                    .foreach(println)
                case None =>
                  Try(numbers.toInt).toOption
                    .map(parser.getColumnByIndex)
                    .fold(println("Input was not a number"))(
                      _.fold(e => println(e.msg), println),
                    )
              }
            },
          )
      case s"-f$number" :: s"-$delimiterWithValue" :: filename :: Nil =>
        readFile(filename)
          .fold(
            _ => println("file does not exist"),
            content => {
              val delimiter = Delimiter
                .fromString(delimiterWithValue.tail)
                .getOrElse(Delimiter.Tab)
              DataframeParser
                .of(delimiter)(content)
                .getColumnByIndex(number.toInt)
                .foreach(println)
            },
          )

      case _ => println("Incorrect usage, please refer to manual")
    }

  private def parse(numbers: String, regex: Regex): Option[Array[Int]] = {
    val nums = numbers.split(regex.repr)
    val numsParsed = nums.flatMap(n => Try(n.toInt).toOption)

    if (numsParsed.length == nums.length)
      Some(numsParsed)
    else None
  }

  private def readFile(filename: String): Try[String] =
    Try(Files readString (Path of filename))
}
