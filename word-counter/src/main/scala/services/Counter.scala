package services

import domain.{Amount, CmdOption, CountResult}

trait Counter {
  def count(input: String): CountResult
}

object Counter {
  def fromUserOption(cmdOption: CmdOption): Counter = (input: String) =>
    CountResult(
      cmdOption = cmdOption,
      amount = Amount fromBigInt defineAmount(cmdOption, input) getOrElse Amount.empty,
    )

  private def defineAmount(cmdOption: CmdOption, input: String): BigInt =
    cmdOption match {
      case CmdOption.Byte => input.getBytes.sum
      case CmdOption.Character => input.length
      case CmdOption.Word => (input split " ").length
      case CmdOption.Line => (input split "\n").length
    }
}
