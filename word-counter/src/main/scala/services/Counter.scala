package services

import domain._

trait Counter {
  def count(input: String): CountResult
}

object Counter {
  def fromCommand(command: Command): Counter =
    (input: String) => {
      val amount = Amount
        .fromBigInt(defineAmount(command, input))
        .getOrElse(Amount.empty)

      CountResult(command, amount)
    }

  private def defineAmount(
    command: Command,
    input: String,
  ): BigInt =
    command match {
      case Command.Byte => input.getBytes.length
      case Command.Character => input.length
      case Command.Word => (input split "\\s").count(_.nonEmpty)
      case Command.Line => (input split "\n").length
    }
}
