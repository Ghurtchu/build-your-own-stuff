package domain

final case class MultiCountResult(
  results: List[CountResult],
  filepath: String,
) {
  override def toString: String =
    (results.map(_.amount.toString) :+ filepath)
      .mkString("\t")
}
