import java.nio.file.{Files, Path}

object Main {

  def main(args: Array[String]): Unit =
    args.toList match {
      case s"-$userOption" :: fileName :: Nil =>
        UserOption
          .fromString(userOption)
          .fold(println(s"unrecognized option: $userOption")) { option =>
            val counter = Counter.apply(option)
            val input = Files.readString(Path.of(fileName))
            val result = counter.count(input)

            println(result)
          }

      case Cat :: fileName :: Pipe :: programName :: userOption :: Nil =>
        ???
      case _ => println("Incorrect usage")
    }

  private val Pipe = "|"
  private val Cat = "cat"
  private val Hyphen = "-"

}
