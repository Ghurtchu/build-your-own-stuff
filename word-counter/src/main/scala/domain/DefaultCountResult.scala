package domain

final case class DefaultCountResult(results: List[CountResult], filepath: String) {
  override def toString: String =
    (results.map(_.amount.toString) :+ filepath)
      .mkString("\t")
}
