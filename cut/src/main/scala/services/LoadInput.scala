package services

import java.nio.file.{Files, Path}
import scala.util.Try

trait LoadInput {
  def apply(filename: String): String
}

object LoadInput {
  def from: LoadInput =
    (filename: String) =>
      Try(Files readString (Path of filename))
        .getOrElse(throw new RuntimeException("File does not exist"))
}
