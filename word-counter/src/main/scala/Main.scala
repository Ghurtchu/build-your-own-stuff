import java.nio.file.{Files, Path}
import Syntax._
import scala.util.Try

object Main {

  def main(args: Array[String]): Unit =
    args.toList match {
      case s"-$userOption" :: fileName :: Nil =>
        UserOption
          .fromString(userOption)
          .fold(s"unrecognized option: $userOption".log) { userOption =>
            Try(Files readString (Path of fileName))
              .fold(
                error => s"failed due to ${error.getMessage}".log,
                input => ((Counter fromUserOption userOption) count input).str.log,
              )
          }

      case Cat :: fileName :: Pipe :: executableName :: userOption :: Nil => ???
      case _ => println("Incorrect usage, please check manual")
    }

  private val Pipe = "|"
  private val Cat = "cat"

}
