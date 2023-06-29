package domain

final case class CountResult(
  command: Command,
  amount: Amount,
) {
  override def toString: String =
    s"$amount $command$maybePluralPostfix"

  private def maybePluralPostfix: String =
    if (amount.value > 1) "s" else ""
}
