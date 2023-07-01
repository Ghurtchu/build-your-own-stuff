import java.nio.file.{Files, NoSuchFileException, Path}
import scala.util.Try

object Main extends scala.App {

  args.toList match {
    case filename :: Nil =>
      Try(Files.readString(Path.of(filename)))
        .fold(
          println,
          content => {
            val frequency = content
              .toSet[Char]
              .map { char =>
                char -> content.count(_ == char)
              }
              .toMap

            println(frequency.get('X')) // 223000, checks
            println(frequency.get('t')) // 333, checks
          },
        )
    case _ => println("incorrect usage, please refer to manual")
  }

}
