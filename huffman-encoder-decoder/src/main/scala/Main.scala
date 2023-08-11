import java.nio.file.{Files, Path}
import scala.util.Try

object Main {

  def main(args: Array[String]): Unit =
    args.toList match {
      case filename :: Nil =>
        readFile(filename)
          .fold(
            _ => println("file does not exist"),
            fileContent => {
              val result = DetermineLetterFrequency of fileContent

              result.value.foreach { case (k, v) => println(s"$k->$v") }
            },
          )
      case _ => println("incorrect usage, please refer to manual")
    }

  private def readFile(filename: String): Try[String] =
    Try(Files readString (Path of filename))
}
