import java.nio.file.{Files, Path}
import Syntax._
import scala.util.Try

object Main {

  def main(args: Array[String]): Unit =
    args.toList match {
      case s"-$userOption" :: fileName :: Nil =>
        UserOption
          .fromString(userOption)
          .fold(println(s"unrecognized option: $userOption")) { userOption =>
            val counter = Counter fromUserOption userOption
            Try(Files readString (Path of fileName))
              .fold(
                throwable => println(s"failed due to ${throwable.getMessage}"),
                input => println((counter count input).str),
              )
          }

      case Cat :: fileName :: Pipe :: programName :: userOption :: Nil => ???
      case _ => println("Incorrect usage, please check manual")
    }

  private val Pipe = "|"
  private val Cat = "cat"
  private val Hyphen = "-"

}
