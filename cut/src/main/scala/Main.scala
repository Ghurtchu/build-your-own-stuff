import domain.Header
import services.Parser

import java.nio.file.{Files, Path}
import scala.util.Try

object Main {
  def main(args: Array[String]): Unit =
    args.toList match {
      case colname :: filename :: Nil =>
        readFile(filename)
          .fold(
            _ => println("file does not exist"),
            content => {
              val res = Parser.of(content)
                .getColumnByHeader(Header(colname))

              res.foreach(println)
            },
          )
      case _ => println("Incorrect usage, please refer to manual")
    }

  private def readFile(filename: String): Try[String] =
    Try(Files readString (Path of filename))
}
