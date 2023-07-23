package parsing

import json.Js
import json.Js._
import json.JsToken._

import scala.util.matching.Regex
import scala.util.parsing.combinator.RegexParsers

object JsParser {

  private val regexParser = new RegexParsers {
    override val whiteSpace: Regex = """\s+""".r
  }

  import regexParser._

  private def jsObj: Parser[JsObj] =
    JsObjStart.value ~> repsep(member, ",") <~ JsObjEnd.value ^^ (fields => JsObj(fields.toMap))

  private def jsArr: Parser[JsArr] =
    JsArrStart.value ~> repsep(js, ",") <~ JsArrEnd.value ^^ (elements => JsArr(elements))

  private def jsStr: Parser[JsStr] =
    "\"" ~> """([^"\p{Cntrl}\\]|\\[\\'"bfnrt]|\\u[a-fA-F0-9]{4})*""".r <~ "\"" ^^ JsStr

  private def jsNum: Parser[JsNum] =
    """-?(?:0|[1-9]\d*)(?:\.\d+)?(?:[eE][+-]?\d+)?""".r ^^ (num => JsNum(num.toDouble))

  private def jsBool: Parser[JsBool] =
    "true" ^^ (_ => JsBool(true)) |
      "false" ^^ (_ => JsBool(false))

  private def jsNull: Parser[JsNull.type] =
    "null" ^^ (_ => JsNull)

  private def js: Parser[Js] =
    jsObj | jsArr | jsStr | jsNum | jsBool | jsNull

  private def member: Parser[(String, Js)] =
    jsStr ~ (Colon.value ~> js) ^^ { case key ~ v => (key.value, v) }

  private def parseJson(input: String): ParseResult[Js] =
    parseAll(js, input)

  def parse(input: String): Option[Js] =
    parseAll(js, input)
      .map(Some(_))
      .getOrElse(None)
}
