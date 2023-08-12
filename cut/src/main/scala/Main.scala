import domain.{Delimiter, Header}
import services.Parser

import java.nio.charset.{Charset, StandardCharsets}
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
              val parsed = if (numbers.contains(",")) {
                val nums = numbers.split(",")
                val numsParsed = nums.flatMap { n => Try(n.toInt).toOption }
                if (numsParsed.length == nums.length) {
                  Some(numsParsed)
                } else None
              } else if (numbers.contains("\\s")) {
                val nums = numbers.split("\\s")
                val numsParsed = nums.flatMap { n => Try(n.toInt).toOption }
                if (numsParsed.length == nums.length) {
                  Some(numsParsed)
                } else None
              } else None

              val parser = Parser
                .ofTab(content)

              parsed match {
                case Some(value) => {
                  value.map { i => parser.getColumnByIndex(i) }
                    .foreach(println)
                }
                case None => println("xd")
              }
            }
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

  private def readFile(filename: String): Try[String] =
    Try(Files readString (Path of filename))
}
