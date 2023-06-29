package domain

import munit.FunSuite

class AmountSpec extends FunSuite {

  test("positive amount should return Some(Amount(...))") {
    assertEquals(Amount.fromBigInt(2), Some(Amount(2)))
  }

  test("negative amount should return None") {
    assert(Amount.fromBigInt(-1).isEmpty)
  }

  test("Amount#toString should return stringified underlying value") {
    assertEquals(Amount.fromBigInt(500).map(_.toString), Some("500"))
  }
}
