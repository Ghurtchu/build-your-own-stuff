import services.LoadInputAndThenCountWords.Syntax.OptionOps
import services.ParseCmdAndThenCountWords.Syntax.EitherOps
import services.{LoadInputAndThenCountWords, ParseCmdAndThenCountWords}

import java.nio.file.{Files, Path}
import scala.util.Try

object Main {

  def main(args: Array[String]): Unit =
    args.toList match {
      case s"-$cmd" :: filepath :: Nil =>
        ParseCmdAndThenCountWords
          .ofFile(filepath)(cmd, loadInputFromFile(filepath))
          .logResult()
      case s"-$cmd" :: Nil =>
        ParseCmdAndThenCountWords
          .of(cmd, loadInputFromConsole)
          .logResult()
      case filepath :: Nil =>
        LoadInputAndThenCountWords
          .of(filepath, loadInputFromFile(filepath))
          .logResult()
      case _ => println("Incorrect usage, please refer to manual")
    }

  private def loadInputFromFile(filepath: String): Try[String] =
    Try(Files readString (Path of filepath))

  private def loadInputFromConsole: Try[String] =
    Try(io.Source.stdin.getLines().mkString("\n"))
}
