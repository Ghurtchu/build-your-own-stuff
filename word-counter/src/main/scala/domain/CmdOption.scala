package domain

sealed trait CmdOption {
  override def toString: String =
    getClass.getSimpleName.init.toLowerCase
}

object CmdOption {
  case object Byte extends CmdOption
  case object Character extends CmdOption
  case object Word extends CmdOption
  case object Line extends CmdOption

  def fromString: String => Option[CmdOption] = {
    case "c" => Some(Byte)
    case "m" => Some(Character)
    case "w" => Some(Word)
    case "l" => Some(Line)
    case _ => None
  }
}
