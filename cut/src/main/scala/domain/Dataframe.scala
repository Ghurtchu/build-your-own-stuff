package domain

final case class Dataframe(columns: List[Column]) {
  def getColumnByHeader(header: Header): Option[Column] =
    columns.find(_.header == header)

  def getColumnByIndex(index: Int): List[String] =
    columns(index - 1).header.value :: columns.flatMap { col =>
      col.values
        .filter(_.position.col == index - 1)
        .map(_.value)
    }
}
