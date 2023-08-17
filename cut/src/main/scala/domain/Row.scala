package domain

final case class Row(values: List[Cell]) extends AnyVal {
  override def toString: String =
    values
      .foldLeft("")((str, cell) => str concat "\t" concat cell.value)
}
