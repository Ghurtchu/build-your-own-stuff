package domain

import PartialFunction.condOpt
sealed trait Regex {
  def repr: String
}

object Regex {
  case object Comma extends Regex { override def repr: String = "," }
  case object Space extends Regex { override def repr: String = " " }

  def from: String => Option[Regex] =
    condOpt(_) {
      case s if s.contains(",") => Comma
      case s if s.contains(" ") => Space
    }
}
