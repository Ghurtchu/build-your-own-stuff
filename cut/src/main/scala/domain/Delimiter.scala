package domain

import PartialFunction.condOpt

sealed trait Delimiter { def repr: String }

object Delimiter {
  case object Comma extends Delimiter { override def repr: String = "," }
  case object Tab extends Delimiter { override def repr: String = "\t" }

  def fromString: String => Option[Delimiter] =
    condOpt(_) {
      case "," => Comma
      case "\t" => Tab
    }

  def fromStringOrElse(
    delimiter: String,
    default: Delimiter,
  ): Delimiter =
    fromString(delimiter)
      .getOrElse(default)

}
