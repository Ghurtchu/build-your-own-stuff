import domain.{Delimiter, Header, Regex}
import services.Parser

import java.nio.file.{Files, Path}
import scala.util.Try

object Main {
  def main(args: Array[String]): Unit =
    args.toList match {
      case s"-f$numbers" :: filename :: Nil =>
        readFile(filename)
          .fold(
            _ => println("file does not exist"),
            content => {
              val parsedNumbers = for {
                regex <- Regex.fromSplittable(numbers)
                parsed <- parse(numbers, regex)
              } yield parsed

              val parser = Parser.ofTab(content)

              parsedNumbers match {
                case Some(value) =>
                  value
                    .map(parser.getColumnByIndex)
                    .foreach(println)
                case None => println("xd")
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
              Parser
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
