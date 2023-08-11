package domain

final case class Column(header: Header, values: List[Cell]) {
  override def toString: String =
    header.value concat "\n" concat
      values
        .map(_.value)
        .reduce(_ concat "\n" concat _)
}
