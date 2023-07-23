import services.EitherSyntax.EitherOps
import services.OptionSyntax.OptionOps
import services._

import java.nio.file.{Files, Path}
import scala.io.Source.stdin
import scala.util.Try

object Main {

  def main(args: Array[String]): Unit =
    args.toList match {
      case s"-$cmd" :: filepath :: Nil =>
        ParseCmdAndThenCountWords
          .fromFile(filepath)(cmd, loadInputFromFile(filepath))
          .logResult()
      case s"-$cmd" :: Nil =>
        ParseCmdAndThenCountWords
          .fromStdIn(cmd, loadInputFromStdIn)
          .logResult()
      case filepath :: Nil =>
        LoadInputAndThenCountWords
          .fromFile(filepath, loadInputFromFile(filepath))
          .logResult()
      case _ => println("Incorrect usage, please refer to manual")
    }

  private def loadInputFromFile(filepath: String): Try[String] =
    Try(Files readString Path.of(filepath))

  private def loadInputFromStdIn: Try[String] =
    Try(stdin.getLines.mkString("\n"))
}
