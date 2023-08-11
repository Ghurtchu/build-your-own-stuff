import domain.{Delimiter, Header}
import services.Parser

import java.nio.file.{Files, Path}
import scala.util.Try

object Main {
  def main(args: Array[String]): Unit =
    args.toList match {
      case s"-$colname" :: filename :: Nil =>
        readFile(filename)
          .fold(
            _ => println("file does not exist"),
            content => {
              val res = Parser.ofTab(content)
                .getColumnByHeader(Header(colname))

              res.foreach(println)
            },
          )
      case s"-$colname" :: s"-$delimiterWithValue" :: Nil => {
        val delimiter = Delimiter.fromString(delimiterWithValue)
          .getOrElse(Delimiter.Tab)

        println("unimplemented")

      }
      case _ => println("Incorrect usage, please refer to manual")
    }

  private def readFile(filename: String): Try[String] =
    Try(Files readString (Path of filename))
}
