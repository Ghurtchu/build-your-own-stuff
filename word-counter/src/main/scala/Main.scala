object Main {

  def main(args: Array[String]): Unit =
    args.toList match {
      case s"$Hyphen$userOption" :: fileName :: Nil =>
        ???

      case Cat :: fileName :: Pipe :: programName :: userOption :: Nil =>
        ???
    }

  private val Pipe = "|"
  private val Cat = "cat"
  private val Hyphen = "-"

}
