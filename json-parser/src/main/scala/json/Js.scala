package json

sealed trait Js

object Js {
  final case class JsStr(value: String) extends Js
  final case class JsNum(value: BigDecimal) extends Js
  final case class JsBool(value: Boolean) extends Js
  final case class JsObj(value: Map[String, Js]) extends Js
  final case class JsArr(value: List[Js]) extends Js
  case object JsNull extends Js
}
