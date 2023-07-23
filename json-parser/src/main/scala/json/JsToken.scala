package json

trait JsToken { def value: String }

object JsToken {
  object JsObjStart extends JsToken { val value: String = "{" }
  object JsObjEnd extends JsToken { val value: String = "}" }
  object JsArrStart extends JsToken { val value: String = "[" }
  object JsArrEnd extends JsToken { val value: String = "]" }
  object Colon extends JsToken { val value: String = ":" }
}
