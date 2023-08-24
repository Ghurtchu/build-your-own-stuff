package domain
final case class Servers private (private var uris: List[String]) {

  def next: String = {
    val nextUri = uris.head
    uris = uris.tail :+ nextUri

    nextUri
  }
}

object Servers {
  def from(uris: String*): Servers =
    Option.when(uris.length >= 2)(Servers(uris.toList))
      .getOrElse(throw new RuntimeException("You must provide at least two uris for servers"))
}
