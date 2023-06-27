import java.nio.file.{Files, Path}

object Main {

  def main(args: Array[String]): Unit =
    args.toList match {
      case s"-$userOption" :: fileName :: Nil =>
        UserOption.fromString(userOption) match {
          case Some(option) =>
            val counter = Counter.apply(option)
            val input = Files.readString(Path.of(fileName))
            val result = counter.count(input)
            println(result)
          case None =>
            println(s"unrecognized option: $userOption")
        }

      case Cat :: fileName :: Pipe :: programName :: userOption :: Nil =>
        ???
      case _ => println("XD")
    }

  private val Pipe = "|"
  private val Cat = "cat"
  private val Hyphen = "-"

}
