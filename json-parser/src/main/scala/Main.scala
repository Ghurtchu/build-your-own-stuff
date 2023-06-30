import json.Json

import scala.collection.mutable

object Main extends App {

  assert(Json.parse("null") == Json.Null)
  assert(Json.parse("true") == Json.Bool(true))
  assert(Json.parse("false") == Json.Bool(false))
  assert(Json.parse("15.43") == Json.Decim(BigDecimal(15.43)))
  assert(Json.parse("-4") == Json.Int(BigInt(-4)))
  assert(Json.parse("\"Scala\"") == Json.Str("Scala"))

  val input =
    """{
      |  "key": "value",
      |  "key-n": 101,
      |  "dec": 102.5,
      |  "kokoiti": true,
      |  "key-o": {
      |    "skarpetek": "holy",
      |    "scoobie": "doobie",
      |    "help": {
      |      "xd": 2,
      |      "what": {
      |        "nothing": "....",
      |        "hmm": null
      |      },
      |      "kuki": "2"
      |    }
      |  }
      |}""".stripMargin

  val res = Json.parse(input)

  println(res)

  val expected = Json.Obj(
    Map(
      "key" -> Json.Str("value"),
      "key-n" -> Json.Int(BigInt(101)),
      "dec" -> Json.Decim(BigDecimal(102.5)),
      "kokoiti" -> Json.Bool(true),
      "key-o" -> Json.Obj(
        Map(
          "skarpetek" -> Json.Str("holy"),
          "scoobie" -> Json.Str("doobie"),
          "help" -> Json.Obj(
            Map(
              "xd" -> Json.Int(BigInt(2)),
              "what" -> Json.Obj(
                Map(
                  "nothing" -> Json.Str("...."),
                  "hmm" -> Json.Null,
                ),
              ),
              "kuki" -> Json.Str("2"),
            ),
          ),
        ),
      ),
    ),
  )

  println(expected)

  assert(res == expected)

}
