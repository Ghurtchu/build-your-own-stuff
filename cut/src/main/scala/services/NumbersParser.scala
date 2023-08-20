package services

import domain.Regex
import Option.when

import scala.util.Try

trait NumbersParser extends Parser[String, Option[Array[Int]]]

object NumbersParser {

  def fromRegex(regex: Regex): NumbersParser =
    (numbers: String) => {
      val nums = numbers.split(regex.repr)
      val numsParsed = nums.flatMap(n => Try(n.toInt).toOption)

      when(numsParsed.length == nums.length)(numsParsed)
    }
}
