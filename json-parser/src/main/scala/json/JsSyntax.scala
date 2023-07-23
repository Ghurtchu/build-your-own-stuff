package json
import json.Js._
object JsSyntax {
  implicit class Syntax[A](self: A) {
    def js: Js = self match {
      case _: String => JsStr(self.asInstanceOf[String])
      case _: Boolean => JsBool(self.asInstanceOf[Boolean])
      case _: Number => JsNum(self.asInstanceOf[Number].doubleValue())
    }
  }
}
