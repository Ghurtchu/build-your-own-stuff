package domain

final case class Row(values: List[Cell]) {
  def index: Int = values.head.position.row
}
