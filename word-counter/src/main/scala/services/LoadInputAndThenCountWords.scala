package services

import domain.Command.DefaultCommands
import domain.{Command, CountResult, MultiCountResult}

import scala.util.Try

trait LoadInputAndThenCountWords {
  def apply(
    filepath: String,
    loadInput: => Try[String],
  ): Option[MultiCountResult]
}

object LoadInputAndThenCountWords {
  def fromFile: LoadInputAndThenCountWords =
    (filepath, loadInput) =>
      loadInput.map { input =>
        MultiCountResult(
          results = DefaultCommands.map(Counter.fromCommand(_) count input),
          filepath = filepath,
        )
      }.toOption
}
