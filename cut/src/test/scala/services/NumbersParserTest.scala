package services

import domain.Regex
import munit.FunSuite

class NumbersParserTest extends FunSuite {

  test("fromRegex + apply, Comma") {
    val obtained = NumbersParser.fromRegex(Regex.Comma)("1,2,3")

    assertEquals(obtained, Some(List(1, 2, 3)))
  }

  test("fromRegex + apply, Space") {
    val obtained = NumbersParser.fromRegex(Regex.Space)("1 2 3")

    assertEquals(obtained, Some(List(1, 2, 3)))
  }

  test("fromRegex + apply, Space, not a number => None") {
    val obtained = NumbersParser.fromRegex(Regex.Space)("1 2 3 d")

    assertEquals(obtained, None)
  }

  test("fromRegex + apply, Comma, not a number => None") {
    val obtained = NumbersParser.fromRegex(Regex.Space)("1,d,2,3")

    assertEquals(obtained, None)
  }
}
