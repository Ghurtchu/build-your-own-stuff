import json.Js._
import json.JsSyntax.Syntax
import parsing.JsonParser

object Main extends App {

  val jsStr =
    """{
      |  "name": "Scala",
      |  "popularity": "average",
      |  "runtimes": ["jvm", "native", "js"],
      |  "meta": {
      |    "creator": "Martin Odersky",
      |    "libraries": [
      |      {
      |        "name": "akka",
      |        "isPopular": true,
      |        "downloads": 999999,
      |        "isFree": null
      |      },
      |      {
      |        "name": "ZIO",
      |        "isPopular": true,
      |        "downloads": 100000,
      |        "isFree": true
      |      }
      |    ]
      |  }
      |}""".stripMargin

  val res = JsonParser.parse(jsStr)

  val expected = JsObj(
    "name" -> "Scala".js,
    "popularity" -> "average".js,
    "runtimes" -> JsArr.strings("jvm", "native", "js"),
    "meta" -> JsObj(
      "creator" -> "Martin Odersky".js,
      "libraries" -> JsArr(
        JsObj(
          "name" -> "akka".js,
          "isPopular" -> true.js,
          "downloads" -> 999999.js,
          "isFree" -> JsNull,
        ),
        JsObj(
          "name" -> "ZIO".js,
          "isPopular" -> true.js,
          "downloads" -> 100000.js,
          "isFree" -> true.js,
        ),
      ),
    ),
  )

  assert(res.contains(expected))

}
