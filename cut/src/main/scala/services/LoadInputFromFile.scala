package services

import java.nio.file.{Files, Path}
import scala.util.Try

trait LoadInputFromFile {
  def apply(filename: String): String
}

object LoadInputFromFile {
  def from: LoadInputFromFile =
    (filename: String) =>
      Try(Files readString (Path of filename))
        .getOrElse(throw new RuntimeException("File does not exist"))
}
