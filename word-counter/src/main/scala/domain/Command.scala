package domain

sealed trait Command {
  override def toString: String =
    getClass.getSimpleName.init.toLowerCase
}

object Command {
  case object Byte extends Command
  case object Character extends Command
  case object Word extends Command
  case object Line extends Command

  def fromString: String => Option[Command] = {
    case "c" => Some(Byte)
    case "m" => Some(Character)
    case "w" => Some(Word)
    case "l" => Some(Line)
    case _ => None
  }
}
