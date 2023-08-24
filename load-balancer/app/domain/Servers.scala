package domain
final case class Servers (private var values: List[String]) {
  def next: String = {
    val value = values.head
    values = values.tail :+ value

    value
  }
}
