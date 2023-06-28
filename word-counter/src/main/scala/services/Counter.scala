package services

import domain.{Amount, Command, CountResult}

import java.nio.charset.Charset
import java.util.regex.Pattern

trait Counter {
  def count(input: String): CountResult
}

object Counter {
  def fromCommand(command: Command): Counter = (input: String) =>
    CountResult(
      command = command,
      amount = Amount fromBigInt defineAmount(command, input) getOrElse Amount.empty,
    )

  private def defineAmount(command: Command, input: String): BigInt =
    command match {
      case Command.Byte => input.getBytes.length
      case Command.Character => input.length
      case Command.Word =>
        val nonLetterChars = input.toSet.filter(char => !Character.isLetter(char))
        val splitted = (input split "\n")
          .flatMap(_ split "\\s")

        nonLetterChars.foldLeft(splitted) { (acc, char) =>
          acc.flatMap(_.split(char))
        } count { text => Pattern.matches("^[a-zA-Z]+", text) }

      case Command.Line => (input split "\n").length
    }
}
