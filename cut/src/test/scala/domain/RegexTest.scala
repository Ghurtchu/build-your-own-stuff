package domain

import munit.FunSuite

class RegexTest extends FunSuite {

  test("check from") {
    assertEquals(Regex.from(","), Some(Regex.Comma))
    assertEquals(Regex.from(" "), Some(Regex.Space))
    assertEquals(Regex.from("gibberish"), None)
  }
}
