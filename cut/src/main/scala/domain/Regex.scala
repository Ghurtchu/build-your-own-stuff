package domain

sealed trait Regex {
  def repr: String
}

object Regex {
  case object Comma extends Regex { override def repr: String = "," }
  case object Space extends Regex { override def repr: String = "\t" }

  def fromNumbers: String => Option[Regex] =
    PartialFunction.condOpt(_) {
      case s if s.contains(",") => Comma
      case s if s.contains("\t") => Space
    }
}
