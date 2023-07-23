import json.Js._
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
    Map(
      "name" -> JsStr("Scala"),
      "popularity" -> JsStr("average"),
      "runtimes" -> JsArr(List("jvm", "native", "js").map(JsStr)),
      "meta" -> JsObj(
        Map(
          "creator" -> JsStr("Martin Odersky"),
          "libraries" -> JsArr(
            List(
              JsObj(
                Map(
                  "name" -> JsStr("akka"),
                  "isPopular" -> JsBool(true),
                  "downloads" -> JsNum(999999),
                  "isFree" -> JsNull,
                ),
              ),
              JsObj(
                Map(
                  "name" -> JsStr("ZIO"),
                  "isPopular" -> JsBool(true),
                  "downloads" -> JsNum(100000),
                  "isFree" -> JsBool(true),
                ),
              ),
            ),
          ),
        ),
      ),
    ),
  )

  assert(res.contains(expected))

}
