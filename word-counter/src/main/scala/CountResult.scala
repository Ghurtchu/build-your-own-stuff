final case class CountResult private (userOption: UserOption, amount: BigInt)

object CountResult {
  def apply(userOption: UserOption, amount: BigInt): Option[CountResult] =
    Option.when(amount >= 0)(new CountResult(userOption, amount))
}
