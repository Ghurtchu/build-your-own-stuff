package domain

import munit.FunSuite

class AmountTest extends FunSuite {

  test("positive amount should return Some(Amount(...))") {
    assert(Amount.fromBigInt(2).isDefined)
  }

  test("negative amount should return None") {
    assert(Amount.fromBigInt(-1).isEmpty)
  }

  test("Amount#toString should return stringified underlying value") {
    assertEquals(Amount.fromBigInt(500).map(_.toString), Some("500"))
  }
}
