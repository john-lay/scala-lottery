package models

import play.api.libs.json._

/** A model representing a line on a lottery ticket
  */
case class Line(numbers: (Int, Int, Int)) {
  val result: Int =
    if (!isValidLine(numbers)) Line.RESULT_INVALID
    else if (numbers._1 + numbers._2 + numbers._3 == 2) Line.RESULT_EQUAL_TWO
    else if (numbers._1 == numbers._2 && numbers._1 == numbers._3) Line.RESULT_ALL_SAME
    else if (numbers._1 != numbers._2 && numbers._1 != numbers._3) Line.RESULT_UNIQUE_FIRST
    else Line.RESULT_DEFAULT

  private def isValidLine(numbers: (Int, Int, Int)) = {
    if (isValidNumber(numbers._1)
      && isValidNumber(numbers._2)
      && isValidNumber(numbers._3)) true
    else false
  }

  private def isValidNumber(number: Int) = {
    if (number < 0) false
    else if (number > 2) false
    else true
  }

}

object Line {
  val RESULT_EQUAL_TWO: Int = 10
  val RESULT_ALL_SAME: Int = 5
  val RESULT_UNIQUE_FIRST: Int = 1
  val RESULT_DEFAULT: Int = 0
  val RESULT_INVALID: Int = -1

  implicit val lineWrites: Writes[Line] = (line: Line) => {
    val numbers: Seq[JsNumber] = Seq(JsNumber(line.numbers._1), JsNumber(line.numbers._2), JsNumber(line.numbers._3))
    JsObject(
      Map("numbers" -> JsArray(numbers),
        "result" -> JsNumber(line.result)))
  }
}
