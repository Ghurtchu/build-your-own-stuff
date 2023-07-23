package json

sealed trait Js

object Js {

  final case class JsStr(value: String) extends Js

  final case class JsNum(value: BigDecimal) extends Js

  final case class JsBool(value: Boolean) extends Js

  final case class JsObj(value: Map[String, Js]) extends Js

  object JsObj {
    def apply(kvs: (String, Js)*): JsObj = new JsObj(Map.from(kvs))
  }

  final case class JsArr(value: List[Js]) extends Js

  object JsArr {
    def apply(inputs: Js*): JsArr = new JsArr(List(inputs: _*))

    def strings(inputs: String*): JsArr = new JsArr(List(inputs: _*).map(JsStr))
  }

  case object JsNull extends Js
}
