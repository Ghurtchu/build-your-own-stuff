package domain

final case class Frequency private (value: BigInt) extends AnyVal {
  override def toString: String = value.toString
}

object Frequency {

  def zero: Frequency = Frequency(0)

  def fromBigInt: BigInt => Option[Frequency] =
    PartialFunction.condOpt(_) {
      case n if n >= 0 => Frequency(n)
    }
}
