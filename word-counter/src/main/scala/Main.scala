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
          .run()
      case s"-$cmd" :: Nil =>
        ParseCmdAndThenCountWords
          .create(cmd, loadInputFromConsole)
          .run()
      case filepath :: Nil =>
        LoadInputAndThenCountWords
          .create(filepath, loadInputFromFile(filepath))
          .fold(println(s"could not open file $filepath"))(println)
      case _ => println("Incorrect usage, please refer to manual")
    }

  private def loadInputFromFile(filepath: String): Try[String] =
    Try(Files readString (Path of filepath))

  private def loadInputFromConsole: Try[String] =
    Try(scala.io.Source.stdin.getLines().mkString("\n"))
}
