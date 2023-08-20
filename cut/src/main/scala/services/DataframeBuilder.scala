package services

import domain.Dataframe

import java.nio.file.{Files, Path}
import scala.util.Try

trait DataframeBuilder {
  def apply(filename: String): Dataframe
}

object DataframeBuilder {
  def of(dataframeParser: DataframeParser): DataframeBuilder =
    (filename: String) =>
      dataframeParser(loadInputOrFail(filename))
        .getOrElse(
          throw new RuntimeException("Could not construct the dataframe"),
        )

  def loadInputOrFail(filename: String): String =
    Try(Files readString (Path of filename))
      .getOrElse(throw new RuntimeException("File does not exist"))

}
