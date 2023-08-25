package services

import java.nio.file.{Files, Path}
import scala.util.Try

trait LoadInput {
  def load: String
}

object LoadInput {
  def fromFile(filename: String): LoadInput = new LoadInput {
    override def load: String =
      Try(Files readString (Path of filename))
        .getOrElse(throw new RuntimeException("File does not exist"))
  }
  def fromStdIn: LoadInput = new LoadInput {
    override def load: String =
      scala.io.Source.stdin.getLines
        .mkString("\n")
  }

}
