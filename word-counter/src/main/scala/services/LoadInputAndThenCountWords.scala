package services

import domain.Command.DefaultCommands
import domain.MultiCountResult

import scala.util.Try

trait LoadInputAndThenCountWords {
  def apply(
    filepath: String,
    loadInput: => Try[String],
  ): Option[MultiCountResult]
}

object LoadInputAndThenCountWords {

  object OptionSyntax {
    implicit class OptionOps(
      self: Option[MultiCountResult],
    ) {
      def logResult(): Unit = {
        val filepath = self.fold("")(_.filepath)

        self.fold(println(s"could not open file: $filepath"))(println)
      }
    }
  }

  def fromFile: LoadInputAndThenCountWords =
    (filepath, loadInput) =>
      loadInput.map { input =>
        val countResults =
          DefaultCommands.map { cmd =>
            (Counter fromCommand cmd) count input
          }

        MultiCountResult(countResults, filepath)
      }.toOption
}
