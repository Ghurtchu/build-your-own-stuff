package domain

import munit.FunSuite

class HeaderTest extends FunSuite {

  test("check fromString") {
    assertEquals(Header.fromString("protein"), Some(Header("protein")))
    assertEquals(Header.fromString(""), None)
  }

}
