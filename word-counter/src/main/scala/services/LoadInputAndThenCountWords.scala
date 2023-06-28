package services

import domain.Command.DefaultCommands
import domain.DefaultCountResult

import scala.util.Try

trait LoadInputAndThenCountWords {
  def apply(filepath: String, loadInput: => Try[String]): Option[DefaultCountResult]
}

object LoadInputAndThenCountWords {

  object Syntax {
    implicit class OptionOps(self: Option[DefaultCountResult]) {
      def logResult(): Unit = {
        val filepath = self.fold("")(_.filepath)

        self.fold(println(s"could not open file: $filepath"))(println)
      }
    }
  }

  def of: LoadInputAndThenCountWords = (filepath, loadInput) =>
    loadInput.map { input =>
      val countResults =
        DefaultCommands.map { cmd =>
          (Counter fromCommand cmd) count input
        }

      DefaultCountResult(countResults, filepath)
    }.toOption
}
