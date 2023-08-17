package domain

final case class Row(values: List[Cell]) {
  override def toString: String =
    values.foldLeft[String]("") { (str, cell) =>
      str concat "\t" concat cell.value
    }
}
