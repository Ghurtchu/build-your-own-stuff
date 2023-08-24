package domain

import munit.FunSuite

class DelimiterTest extends FunSuite {

  test("check fromString") {
    assertEquals(Delimiter.fromString(","), Some(Delimiter.Comma))
    assertEquals(Delimiter.fromString("\t"), Some(Delimiter.Tab))
    assertEquals(Delimiter.fromString("gibberish"), None)
  }

  test("check fromStringOrElse") {
    assertEquals(Delimiter.fromStringOrElse("gibberish", Delimiter.Tab), Delimiter.Tab)
    assertEquals(Delimiter.fromStringOrElse(",", Delimiter.Tab), Delimiter.Comma)
  }

}
