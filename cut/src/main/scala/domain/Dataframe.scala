package domain

final case class Dataframe(columns: List[Column]) {
  def getColumnByHeader(header: Header): Option[Column] =
    columns.find(_.header == header)
}
