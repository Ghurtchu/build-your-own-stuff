package domain

final case class CountResult(cmdOption: CmdOption, amount: Amount) {
  override def toString: String =
    s"$amount $cmdOption$maybePluralPostfix"

  private def maybePluralPostfix: String =
    if (amount.value > 1) "s" else ""
}
