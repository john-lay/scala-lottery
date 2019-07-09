package models

import play.api.libs.json._

/** A model representing a line on a lottery ticket
  */
case class Line(numbers: (Int, Int, Int)) {
  val result: Int =
    if (numbers._1 + numbers._2 + numbers._3 == 2) Line.RESULT_EQUAL_TWO
    else if (numbers._1 == numbers._2 && numbers._1 == numbers._3) Line.RESULT_ALL_SAME
    else if (numbers._1 != numbers._2 && numbers._1 != numbers._3) Line.RESULT_UNIQUE_FIRST
    else Line.RESULT_DEFAULT

}

object Line {
  private val RESULT_EQUAL_TWO: Int = 10
  private val RESULT_ALL_SAME: Int = 5
  private val RESULT_UNIQUE_FIRST: Int = 1
  private val RESULT_DEFAULT: Int = 0

  implicit val lineWrites: Writes[Line] = (line: Line) => {
    val numbers: Seq[JsNumber] = Seq(JsNumber(line.numbers._1), JsNumber(line.numbers._2), JsNumber(line.numbers._3))
    JsObject(
      Map("numbers" -> JsArray(numbers),
        "result" -> JsNumber(line.result)))
  }
}
