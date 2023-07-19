package domain

final case class Amount private (value: BigInt) extends AnyVal {
  override def toString: String = value.toString
}

object Amount {
  def fromBigInt(bigInt: BigInt): Option[Amount] =
    Option.when(bigInt >= 0)(new Amount(bigInt))

  def empty: Amount = Amount(0)
}
