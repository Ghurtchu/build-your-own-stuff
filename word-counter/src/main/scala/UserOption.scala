sealed trait UserOption {
  override def toString: String =
    getClass.getSimpleName.init.toLowerCase
}

object UserOption {
  case object Byte extends UserOption
  case object Character extends UserOption
  case object Word extends UserOption
  case object Line extends UserOption

  def fromString: String => Option[UserOption] = {
    case "c" => Some(Byte)
    case "m" => Some(Character)
    case "w" => Some(Word)
    case "l" => Some(Line)
    case _ => None
  }
}
