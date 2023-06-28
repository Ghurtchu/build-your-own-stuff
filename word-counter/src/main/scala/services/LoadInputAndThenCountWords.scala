package services

import domain.Command.DefaultCommands
import domain.DefaultCountResult

import scala.util.Try

trait LoadInputAndThenCountWords {
  def apply(filepath: String, loadInput: => Try[String]): Option[DefaultCountResult]
}

object LoadInputAndThenCountWords {

  def create: LoadInputAndThenCountWords = (filepath, loadInput) =>
    loadInput.map { input =>
      val countResults =
        DefaultCommands.map { cmd =>
          (Counter fromCommand cmd) count input
        }

      DefaultCountResult(countResults, filepath)
    }.toOption
}
