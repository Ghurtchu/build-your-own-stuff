sealed abstract case class CountResult private (userOption: UserOption, amount: BigInt) {
  override def toString: String =
    s"$amount $userOption$maybePluralPostfix"

  private def maybePluralPostfix: String =
    if (amount == 1) "" else "s"
}

object CountResult {
  def apply(
    userOption: UserOption,
    amount: BigInt,
  ): Option[CountResult] =
    Option.when(amount >= 0)(new CountResult(userOption, amount) {})
}
