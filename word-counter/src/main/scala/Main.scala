import domain.Command.DefaultCommands
import domain.{Command, CountResult}
import services.Counter

import java.nio.file.{Files, Path}
import scala.util.Try

object Main {

  def main(args: Array[String]): Unit =
    args.toList match {
      case s"-$cmd" :: filename :: Nil =>
        parseCmdAndThenCountWords(cmd, tryReadFile(filename))
      case s"-$cmd" :: Nil =>
        parseCmdAndThenCountWords(cmd, tryReadStdIn)
      case filename :: Nil =>
        tryReadFile(filename)
          .fold(
            println,
            input => {
              val countResults = DefaultCommands map (countWords(_, input))

              println((countResults.map(_.amount.toString) :+ filename) mkString "\t")
            },
          )
      case _ => println("Incorrect usage, please refer to manual")
    }

  private def parseCmdAndThenCountWords(command: String, readInput: => Try[String]): Unit =
    (Command fromString command)
      .fold(println(s"unrecognized command: $command")) { cmd =>
        readInput
          .fold(
            println,
            input => println(countWords(cmd, input)),
          )
      }
  private def tryReadFile(fileName: String): Try[String] =
    Try(Files readString (Path of fileName))

  private def tryReadStdIn: Try[String] =
    Try(scala.io.Source.stdin.getLines().mkString("\n"))

  private def countWords(command: Command, input: String): CountResult =
    (Counter fromCommand command) count input
}
