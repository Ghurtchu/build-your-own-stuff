import domain.Command
import services.Counter

import java.nio.file.{Files, Path}
import scala.util.Try

object Main {

  def main(args: Array[String]): Unit =
    args.toList match {
      case s"-$command" :: fileName :: Nil =>
        Command
          .fromString(command)
          .fold(println(s"unrecognized command: $command")) { command =>
            Try(Files readString (Path of fileName))
              .fold(
                error => println(s"failed due to ${error.getMessage}"),
                input => println((Counter fromCommand command) count input),
              )
          }
      case Cat :: fileName :: Pipe :: executableName :: cmdOption :: Nil => ???
      case _ => println("Incorrect usage, please check manual")
    }

  private val Pipe = "|"
  private val Cat = "cat"

}
