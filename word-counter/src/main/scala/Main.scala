import domain.Command.DefaultCommands
import domain.{Command, CountResult}
import services.Counter

import java.nio.file.{Files, Path}
import scala.util.Try

object Main {

  def main(args: Array[String]): Unit =
    args.toList match {
      case s"-$cmd" :: filename :: Nil =>
        parseCmdAndThenCountWords(
          cmd,
          loadInputFromFile(filename),
        )
      case s"-$cmd" :: Nil =>
        parseCmdAndThenCountWords(
          cmd,
          loadInputFromConsole,
        )
      case filename :: Nil =>
        loadInputFromFile(filename)
          .fold(
            println,
            input => {
              val countResults = DefaultCommands map (countWords(_, input))

              println((countResults.map(_.amount.toString) :+ filename) mkString "\t")
            },
          )
      case _ => println("Incorrect usage, please refer to manual")
    }

  private def parseCmdAndThenCountWords(cmd: String, loadInput: => Try[String]): Unit =
    (Command fromString cmd)
      .fold(println(s"unrecognized command: $cmd")) { cmd =>
        loadInput
          .fold(
            println,
            input => println(countWords(cmd, input)),
          )
      }

  private def loadInputFromFile(fileName: String): Try[String] =
    Try(Files readString (Path of fileName))

  private def loadInputFromConsole: Try[String] =
    Try(scala.io.Source.stdin.getLines().mkString("\n"))

  private def countWords(command: Command, input: String): CountResult =
    (Counter fromCommand command) count input
}
