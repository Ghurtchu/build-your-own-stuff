import domain.CmdOption
import services.Counter

import java.nio.file.{Files, Path}
import scala.util.Try

object Main {

  def main(args: Array[String]): Unit =
    args.toList match {
      case s"-$userOption" :: fileName :: Nil =>
        CmdOption
          .fromString(userOption)
          .fold(println(s"unrecognized option: $userOption")) { userOption =>
            Try(Files readString (Path of fileName))
              .fold(
                error => println(s"failed due to ${error.getMessage}"),
                input => println((Counter fromUserOption userOption) count input),
              )
          }

      case Cat :: fileName :: Pipe :: executableName :: userOption :: Nil => ???
      case _ => println("Incorrect usage, please check manual")
    }

  private val Pipe = "|"
  private val Cat = "cat"

}
